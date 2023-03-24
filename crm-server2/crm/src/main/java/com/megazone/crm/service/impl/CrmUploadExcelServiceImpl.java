package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.common.cache.AdminCacheKey;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminMessage;
import com.megazone.core.feign.admin.entity.AdminMessageEnum;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.redis.Redis;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.upload.UploadConfig;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.AuthUtil;
import com.megazone.crm.common.CrmExcelUtil;
import com.megazone.crm.common.CrmVerify;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmContactsSaveBO;
import com.megazone.crm.entity.BO.CrmModelSaveBO;
import com.megazone.crm.entity.BO.UploadExcelBO;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrmUploadExcelServiceImpl implements CrmUploadExcelService {

	protected static final int UPLOAD_EXCEL_EXIST_TIME = 7200;
	@Autowired
	private AdminService adminService;
	@Autowired
	private Redis redis;
	@Autowired
	private ElasticsearchRestTemplate restTemplate;
	private String uploadPath = ApplicationContextHolder.getBean(UploadConfig.class).getLocal().getUploadPath().get("0");

	@Override
	public Long uploadExcel(MultipartFile file, UploadExcelBO uploadExcelBO) {
		String filePath = getFilePath(file);
		uploadExcelBO.setFilePath(filePath);
		AdminMessage adminMessage = new AdminMessage();
		adminMessage.setCreateUser(UserUtil.getUserId());
		adminMessage.setRecipientUser(UserUtil.getUserId());
		adminMessage.setLabel(6);
		UploadService uploadService;
		switch (uploadExcelBO.getCrmEnum()) {
			case CUSTOMER:
				uploadService = new CustomerUploadService();
				adminMessage.setType(AdminMessageEnum.CRM_CUSTOMER_IMPORT.getType());
				break;
			case LEADS:
				uploadService = new LeadsUploadService();
				adminMessage.setType(AdminMessageEnum.CRM_LEADS_IMPORT.getType());
				break;
			case CONTACTS:
				uploadService = new ContactsUploadService();
				adminMessage.setType(AdminMessageEnum.CRM_CONTACTS_IMPORT.getType());
				break;
			case PRODUCT:
				uploadService = new ProductUploadService();
				adminMessage.setType(AdminMessageEnum.CRM_PRODUCT_IMPORT.getType());
				break;
			default:
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		AuthUtil.queryAuthUserList(uploadExcelBO.getCrmEnum(), CrmAuthEnum.EDIT);
		Long messageId = adminService.saveOrUpdateMessage(adminMessage).getData();
		uploadExcelBO.setMessageId(messageId);
		uploadService.setUploadExcelBO(uploadExcelBO);
		redis.setex(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + messageId.toString(), UPLOAD_EXCEL_EXIST_TIME, 0);
		TaskExecutor taskExecutor = ApplicationContextHolder.getBean(TaskExecutor.class);
		taskExecutor.execute(uploadService);
		return messageId;
	}

	private String getFilePath(MultipartFile file) {
		String dirPath = FileUtil.getTmpDirPath();
		try {
			InputStream inputStream = file.getInputStream();
			File fromStream = FileUtil.writeFromStream(inputStream, dirPath + "/" + IdUtil.simpleUUID() + file.getOriginalFilename());
			return fromStream.getAbsolutePath();
		} catch (IOException e) {
			throw new CrmException(SystemCodeEnum.SYSTEM_UPLOAD_FILE_ERROR);
		}
	}

	public abstract class UploadService implements Runnable {

		protected List<List<Object>> errorList = new ArrayList<>();

		protected List<CrmModelFiledVO> fieldList = new ArrayList<>();

		protected List<CrmModelFiledVO> fixedFieldList = new ArrayList<>();

		protected List<CrmModelFiledVO> uniqueList = new ArrayList<>();
		/**
		 *
		 */
		protected Integer num = -2;
		/**
		 *
		 */
		protected Integer updateNum = 0;
		protected List<Integer> isNullList = new ArrayList<>();
		protected boolean templateErr = false;
		protected JSONObject kv = new JSONObject();
		private UploadExcelBO uploadExcelBO;
		private Map<String, Long> userCacheMap;

		public abstract void importExcel();

		public UploadExcelBO getUploadExcelBO() {
			return uploadExcelBO;
		}

		private void setUploadExcelBO(UploadExcelBO uploadExcelBO) {
			this.uploadExcelBO = uploadExcelBO;
			List<Long> longs = adminService.queryUserList(1).getData();
			List<SimpleUser> simpleUsers = UserCacheUtil.getSimpleUsers(longs);
			userCacheMap = new HashMap<>(simpleUsers.size(), 1.0f);
			for (SimpleUser simpleUser : simpleUsers) {
				userCacheMap.put(simpleUser.getRealname(), simpleUser.getUserId());
			}
		}

		@Override
		public void run() {
			boolean exists = redis.exists(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + getUploadExcelBO().getMessageId());
			if (!exists) {
				return;
			}
			try {
				UserUtil.setUser(getUploadExcelBO().getUserInfo());
				importExcel();
				restTemplate.refresh(getUploadExcelBO().getCrmEnum().getIndex());
			} catch (Exception e) {
				log.error("", e);
			} finally {
				UserUtil.removeUser();
			}

			AdminMessage adminMessage = adminService.getMessageById(getUploadExcelBO().getMessageId()).getData();
			adminMessage.setTitle(String.valueOf(Math.max(num, 0)));
			adminMessage.setContent((errorList.size() - 2) + "," + updateNum);
			if (errorList.size() > 2) {
				File file = new File(uploadPath + "/excel/" + BaseUtil.getDate() + "/" + IdUtil.simpleUUID() + ".xlsx");
				BigExcelWriter writer = ExcelUtil.getBigWriter(file);
				//
				CellStyle cellStyle = writer.getCellStyle();
				cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
				cellStyle.setBorderTop(BorderStyle.NONE);
				cellStyle.setBorderBottom(BorderStyle.NONE);
				cellStyle.setBorderLeft(BorderStyle.NONE);
				cellStyle.setBorderRight(BorderStyle.NONE);
				cellStyle.setAlignment(HorizontalAlignment.LEFT);
				Font defaultFont = writer.createFont();
				defaultFont.setFontHeightInPoints((short) 11);
				cellStyle.setFont(defaultFont);
				//
				CellStyle cellStyleForNumber = writer.getStyleSet().getCellStyleForNumber();
				cellStyleForNumber.setBorderTop(BorderStyle.NONE);
				cellStyleForNumber.setBorderBottom(BorderStyle.NONE);
				cellStyleForNumber.setBorderLeft(BorderStyle.NONE);
				cellStyleForNumber.setBorderRight(BorderStyle.NONE);
				cellStyleForNumber.setAlignment(HorizontalAlignment.LEFT);
				cellStyleForNumber.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
				cellStyleForNumber.setFont(defaultFont);

				CellStyle textStyle = writer.getWorkbook().createCellStyle();
				DataFormat format = writer.getWorkbook().createDataFormat();
				textStyle.setDataFormat(format.getFormat("@"));

				writer.merge(errorList.get(1).size() + 1, errorList.get(0).get(0).toString().trim(), true);
				writer.getHeadCellStyle().setAlignment(HorizontalAlignment.LEFT);
				writer.getHeadCellStyle().setWrapText(true);
				Font headFont = writer.createFont();
				headFont.setFontHeightInPoints((short) 11);
				writer.getHeadCellStyle().setFont(headFont);
				writer.getHeadCellStyle().setFillPattern(FillPatternType.NO_FILL);
				writer.getOrCreateRow(0).setHeightInPoints(120);
				writer.setRowHeight(-1, 20);
				for (int i = 0; i < errorList.get(1).size(); i++) {
					writer.getSheet().setDefaultColumnStyle(i, textStyle);
				}
				errorList.remove(0);

				// ，
				writer.write(errorList);
				// writer，
				writer.close();
				adminMessage.setTypeId(null);

				redis.setex(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + "file:" + adminMessage.getMessageId().toString(), 604800, file.getAbsolutePath());
			}
			adminService.saveOrUpdateMessage(adminMessage);
			redis.del(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + getUploadExcelBO().getMessageId());
			FileUtil.del(getUploadExcelBO().getFilePath());
		}


		/**
		 * @param rowList
		 * @author
		 */
		protected void queryExcelHead(List<Object> rowList) {
			switch (getUploadExcelBO().getCrmEnum()) {
				case LEADS: {
					fieldList = ApplicationContextHolder.getBean(ICrmLeadsService.class).queryField(null);
					break;
				}
				case CUSTOMER: {
					fieldList = ApplicationContextHolder.getBean(ICrmCustomerService.class).queryField(null);
					break;
				}
				case CONTACTS: {
					fieldList = ApplicationContextHolder.getBean(ICrmContactsService.class).queryField(null);
					break;
				}
				case PRODUCT: {
					fieldList = ApplicationContextHolder.getBean(ICrmProductService.class).queryField(null);
					break;
				}
			}
			fieldList.removeIf(record -> ExcelParseUtil.removeFieldByType(record.getType()));
			HashMap<String, String> nameMap = new HashMap<>();
			HashMap<String, Integer> isNullMap = new HashMap<>();
			fieldList.forEach(filed -> {
				if (Objects.equals(1, filed.getFieldType()) && !"mapAddress".equals(filed.getFieldName())) {
					fixedFieldList.add(filed);
				}
				if (Objects.equals(1, filed.getIsUnique())) {
					uniqueList.add(filed);
				}
				if (getUploadExcelBO().getCrmEnum() == CrmEnum.CUSTOMER && "mapAddress".equals(filed.getFieldName())) {
					nameMap.put("province", "province");
					nameMap.put("City", "city");
					nameMap.put("area", "site");
					nameMap.put("Detailed address", "detailAddress");
					isNullMap.put("Province", 0);
					isNullMap.put("City", 0);
					isNullMap.put("Area", 0);
					isNullMap.put("Detailed address", 0);
				} else {
					boolean isNull = Objects.equals(1, filed.getIsNull());
					nameMap.put((isNull ? "*" : "") + filed.getName(), filed.getFieldName());
					isNullMap.put((isNull ? "*" : "") + filed.getName(), filed.getIsNull());
				}
			});
			nameMap.put((getUploadExcelBO().getPoolId() != null ? "" : "*") + "responsible person", "ownerUserName");
			List<String> nameList = new ArrayList<>(nameMap.keySet());
			if (nameList.size() != rowList.size() || !nameList.containsAll(rowList)) {
				templateErr = true;
			} else {
				for (int i = 0; i < rowList.size(); i++) {
					kv.put(nameMap.get(rowList.get(i).toString()), i);
					if (Objects.equals(1, isNullMap.get(rowList.get(i).toString()))) {
						isNullList.add(i);
					}
				}
			}
			rowList.add(0, "Error Reason");
			errorList.add(rowList);
		}

		protected List<Map<String, Object>> uniqueMapList(List<CrmModelFiledVO> uniqueList) {
			if (uniqueList.size() == 0) {
				return new ArrayList<>();
			}
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			CrmEnum crmEnum = getUploadExcelBO().getCrmEnum();
			sourceBuilder.fetchSource(new String[]{crmEnum.getPrimaryKey(), "batchId", "ownerUserId"}, null);
			sourceBuilder.size(2);
			sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
			BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
			uniqueList.forEach(crmField -> {
				Object value = crmField.getValue();
				if (ObjectUtil.isNotEmpty(value)) {
					if (Objects.equals(crmField.getType(), 4) && value instanceof Long) {
						Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate((Long) value);
						crmField.setValue(DateUtil.formatDate(date));
					}
					queryBuilder.should(QueryBuilders.termQuery(crmField.getFieldName(), crmField.getValue()));
				}
			});
			queryBuilder.minimumShouldMatch(1);
			sourceBuilder.query(queryBuilder);
			SearchRequest searchRequest = new SearchRequest(crmEnum.getIndex());
			searchRequest.types("_doc");
			searchRequest.source(sourceBuilder);
			try {
				SearchResponse searchResponse = restTemplate.getClient().search(searchRequest, RequestOptions.DEFAULT);
				List<Map<String, Object>> mapList = new ArrayList<>();
				for (SearchHit hit : searchResponse.getHits().getHits()) {
					mapList.add(hit.getSourceAsMap());
				}
				return mapList;
			} catch (IOException e) {
				throw new CrmException(SystemCodeEnum.SYSTEM_ERROR);
			}
		}

		protected List<CrmModelFiledVO> addFieldArray(List<Object> rowList) {
			List<CrmModelFiledVO> array = new ArrayList<>();
			fieldList.forEach(record -> {
				if (record.getFieldType().equals(0) || record.getFieldType().equals(2)) {
					Integer columnsNum = kv.getInteger(record.getFieldName());
					Object value = rowList.get(columnsNum);
					if (Objects.equals(record.getType(), 4) && value instanceof Number) {
						Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(((Number) value).doubleValue());
						record.setValue(DateUtil.formatDate(date));
					} else if (Objects.equals(record.getType(), 13) && value instanceof Number) {
						Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(((Number) value).doubleValue());
						record.setValue(DateUtil.formatDateTime(date));
					} else {
						record.setValue(value);
					}
					array.add(record);
				}
			});
			return array;
		}

		Long getOwnerUserIdByRowList(List<Object> rowList) {
			String ownerUserName = ObjectUtil.isEmpty(rowList.get(kv.getInteger("ownerUserName"))) ? "" : rowList.get(kv.getInteger("ownerUserName")).toString();
			return userCacheMap.get(ownerUserName);
		}
	}

	public class CustomerUploadService extends UploadService {

		@Override
		public void importExcel() {

			ICrmCustomerPoolRelationService poolRelationService = ApplicationContextHolder.getBean(ICrmCustomerPoolRelationService.class);

			Map<String, List<String>> areaMap = CrmExcelUtil.getAreaMap();
			ExcelUtil.readBySax(getUploadExcelBO().getFilePath(), 0, (int sheetIndex, int rowIndex, List<Object> rowList) -> {
				num++;
				redis.setex(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + getUploadExcelBO().getMessageId().toString(), UPLOAD_EXCEL_EXIST_TIME, Math.max(num, 0));
				if (rowList.size() < kv.entrySet().size()) {
					for (int i = rowList.size() - 1; i < kv.entrySet().size(); i++) {
						rowList.add(null);
					}
				}
				if (num >= 10000) {
					rowList.add(0, "Up to 10000 pieces of data can be imported at the same time");
					errorList.add(rowList);
					return;
				}
				if (rowIndex > 1) {
					if (templateErr) {
						rowList.add(0, "Please use the latest template");
						errorList.add(rowList);
					} else {
						try {
							for (Integer integer : isNullList) {
								if (ObjectUtil.isEmpty(rowList.get(integer))) {
									rowList.add(0, "Required fields are not filled");
									errorList.add(rowList);
									return;
								}
							}
							Long ownerUserId = getOwnerUserIdByRowList(rowList);
							if (ownerUserId == null && getUploadExcelBO().getPoolId() == null) {
								rowList.add(0, "The person in charge does not exist");
								errorList.add(rowList);
								return;
							}
							for (CrmModelFiledVO record : uniqueList) {
								Object value = rowList.get(kv.getInteger(record.getFieldName()));
								record.setValue(value);
							}
							List<CrmCustomer> customerList = uniqueMapList(uniqueList).stream().map(map -> BeanUtil.mapToBean(map, CrmCustomer.class, true)).collect(Collectors.toList());
							boolean isUpdate = false;
							if (customerList.size() > 0) {
								if (Objects.equals(2, getUploadExcelBO().getRepeatHandling())) {
									return;
								}
								if (customerList.size() > 1) {
									rowList.add(0, "Data duplicates with multiple unique fields");
									errorList.add(rowList);
									return;
								}
								if (getUploadExcelBO().getPoolId() != null) {
									Integer count = poolRelationService.lambdaQuery()
											.eq(CrmCustomerPoolRelation::getPoolId, getUploadExcelBO().getPoolId())
											.eq(CrmCustomerPoolRelation::getCustomerId, customerList.get(0).getCustomerId()).count();
									if (count == 0) {
										rowList.add(0, "Duplicate data is not in the current high seas and has no right to overwrite");
										errorList.add(rowList);
										return;
									}
								}
								isUpdate = true;
							}
							String province = ObjectUtil.isEmpty(rowList.get(kv.getInteger("province"))) ? "" : rowList.get(kv.getInteger("province")).toString();
							String city = ObjectUtil.isEmpty(rowList.get(kv.getInteger("city"))) ? "" : rowList.get(kv.getInteger("city")).toString();
							String site = ObjectUtil.isEmpty(rowList.get(kv.getInteger("site"))) ? "" : rowList.get(kv.getInteger("site")).toString();
							String detailAddress = ObjectUtil.isEmpty(rowList.get(kv.getInteger("detailAddress"))) ? "" : rowList.get(kv.getInteger("detailAddress")).toString();
							if (StrUtil.isNotEmpty(city) && (!areaMap.containsKey(province) || !areaMap.get(province).contains(city))) {
								rowList.add(0, "Province and municipality do not meet the requirements, please check");
								errorList.add(rowList);
								return;
							}
							if (StrUtil.isNotEmpty(site) && (!areaMap.containsKey(city) || !areaMap.get(city).contains(site))) {
								rowList.add(0, "Province and municipality do not meet the requirements, please check");
								errorList.add(rowList);
								return;
							}
							CrmModelSaveBO object = new CrmModelSaveBO();
							JSONObject entityObject = new JSONObject();
							fixedFieldList.forEach(field -> {
								String fieldName = field.getFieldName();
								entityObject.fluentPut(fieldName, rowList.get(kv.getInteger(fieldName)));
							});
							entityObject.fluentPut("address", StrUtil.isNotEmpty(province) ? province + "," + city + "," + site : "")
									.fluentPut("ownerUserId", ownerUserId).fluentPut("detailAddress", detailAddress);
							if (customerList.size() == 1) {
								CrmCustomer customer = customerList.get(0);
								boolean auth = AuthUtil.isCrmAuth(CrmEnum.CUSTOMER, customer.getCustomerId(), CrmAuthEnum.EDIT);
								if (auth) {
									rowList.add(0, "Data does not have permission to overwrite");
									errorList.add(rowList);
									return;
								}
								entityObject.put("customerId", customer.getCustomerId());
								entityObject.put("batchId", customer.getBatchId());
							}
							object.setField(addFieldArray(rowList));
							object.setEntity(entityObject.getInnerMapObject());
							CrmVerify verify = new CrmVerify(getUploadExcelBO().getCrmEnum());
							Result result = verify.verify(object);
							if (!result.hasSuccess()) {
								rowList.add(0, result.getMsg());
								errorList.add(rowList);
								return;
							}
							try {
								ApplicationContextHolder.getBean(ICrmCustomerService.class).addOrUpdate(object, true, getUploadExcelBO().getPoolId());
							} catch (CrmException e) {
								rowList.add(0, e.getMsg());
								errorList.add(rowList);
								return;
							}
							if (isUpdate) {
								updateNum++;
							}
						} catch (Exception ex) {
							log.error("Import data exception:", ex);
							rowList.add(0, "Import exception");
							errorList.add(rowList);
						}
					}
				} else if (rowIndex == 1) {
					queryExcelHead(rowList);
				} else {
					errorList.add(0, rowList);
				}
			});
		}
	}

	public class LeadsUploadService extends UploadService {

		@Override
		public void importExcel() {
			ExcelUtil.readBySax(getUploadExcelBO().getFilePath(), 0, (int sheetIndex, int rowIndex, List<Object> rowList) -> {
				num++;
				redis.setex(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + getUploadExcelBO().getMessageId().toString(), UPLOAD_EXCEL_EXIST_TIME, Math.max(num, 0));
				if (rowList.size() < kv.entrySet().size()) {
					for (int i = rowList.size() - 1; i < kv.entrySet().size(); i++) {
						rowList.add(null);
					}
				}
				if (num > 10001) {
					rowList.add(0, "Up to 10000 pieces of data can be imported at the same time");
					errorList.add(rowList);
					return;
				}
				if (rowIndex > 1) {
					if (templateErr) {
						rowList.add(0, "Please use the latest template");
						errorList.add(rowList);
					} else {
						try {
							for (Integer integer : isNullList) {
								if (ObjectUtil.isEmpty(rowList.get(integer))) {
									rowList.add(0, "Required fields are not filled");
									errorList.add(rowList);
									return;
								}
							}
							for (CrmModelFiledVO record : uniqueList) {
								Object value = rowList.get(kv.getInteger(record.getFieldName()));
								record.setValue(value);
							}
							List<CrmLeads> leadsList = uniqueMapList(uniqueList).stream().map(map -> BeanUtil.mapToBean(map, CrmLeads.class, true)).collect(Collectors.toList());
							boolean isUpdate = false;
							if (leadsList.size() > 0) {
								if (Objects.equals(2, getUploadExcelBO().getRepeatHandling())) {
									return;
								}
								if (leadsList.size() > 1) {
									rowList.add(0, "Data duplicates with multiple unique fields");
									errorList.add(rowList);
									return;
								}
								isUpdate = true;
							}
							Long ownerUserId = getOwnerUserIdByRowList(rowList);
							if (ownerUserId == null) {
								rowList.add(0, "The person in charge does not exist");
								errorList.add(rowList);
								return;
							}
							CrmModelSaveBO object = new CrmModelSaveBO();
							JSONObject entityObject = new JSONObject();
							fixedFieldList.forEach(field -> {
								String fieldName = field.getFieldName();
								entityObject.fluentPut(fieldName, rowList.get(kv.getInteger(fieldName)));
							});
							entityObject.fluentPut("ownerUserId", ownerUserId);
							if (leadsList.size() == 1) {
								CrmLeads leads = leadsList.get(0);
								boolean auth = AuthUtil.isCrmAuth(CrmEnum.LEADS, leads.getLeadsId(), CrmAuthEnum.EDIT);
								if (auth) {
									rowList.add(0, "Data does not have permission to overwrite");
									errorList.add(rowList);
									return;
								}
								entityObject.put("leadsId", leads.getLeadsId());
								entityObject.put("batchId", leads.getBatchId());
							}
							object.setField(addFieldArray(rowList));
							object.setEntity(entityObject.getInnerMapObject());
							CrmVerify verify = new CrmVerify(getUploadExcelBO().getCrmEnum());
							Result result = verify.verify(object);
							if (!result.hasSuccess()) {
								rowList.add(0, result.getMsg());
								errorList.add(rowList);
								return;
							}
							try {
								ApplicationContextHolder.getBean(ICrmLeadsService.class).addOrUpdate(object, true);
							} catch (CrmException ex) {
								rowList.add(0, ex.getMsg());
								errorList.add(rowList);
								return;
							}
							if (isUpdate) {
								updateNum++;
							}
						} catch (Exception ex) {
							log.error("Import data exception:", ex);
							rowList.add(0, "Import exception");
							errorList.add(rowList);
						}
					}
				} else if (rowIndex == 1) {
					queryExcelHead(rowList);
				} else {
					errorList.add(0, rowList);
				}
			});
		}
	}

	public class ContactsUploadService extends UploadService {

		@Override
		public void importExcel() {
			ExcelUtil.readBySax(getUploadExcelBO().getFilePath(), 0, (int sheetIndex, int rowIndex, List<Object> rowList) -> {
				num++;
				redis.setex(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + getUploadExcelBO().getMessageId().toString(), UPLOAD_EXCEL_EXIST_TIME, Math.max(num, 0));
				if (rowList.size() < kv.entrySet().size()) {
					for (int i = rowList.size() - 1; i < kv.entrySet().size(); i++) {
						rowList.add(null);
					}
				}
				if (num > 10001) {
					rowList.add(0, "Up to 10000 pieces of data can be imported at the same time");
					errorList.add(rowList);
					return;
				}
				if (rowIndex > 1) {
					if (templateErr) {
						rowList.add(0, "Please use the latest template");
						errorList.add(rowList);
					} else {
						try {
							String customerName = rowList.get(kv.getInteger("customerId")).toString();
							SimpleCrmEntity customer = ApplicationContextHolder.getBean(ICrmCustomerService.class).queryFirstCustomerByName(customerName);
							if (customer == null) {
								rowList.add(0, "The entered customer does not exist");
								errorList.add(rowList);
								return;
							}
							for (Integer integer : isNullList) {
								if (ObjectUtil.isEmpty(rowList.get(integer))) {
									rowList.add(0, "Required fields are not filled");
									errorList.add(rowList);
									return;
								}
							}
							Long ownerUserId = getOwnerUserIdByRowList(rowList);
							if (ownerUserId == null) {
								rowList.add(0, "The person in charge does not exist");
								errorList.add(rowList);
								return;
							}
							for (CrmModelFiledVO record : uniqueList) {
								Object value = rowList.get(kv.getInteger(record.getFieldName()));
								record.setValue(value);
							}
							List<CrmContacts> contactsList = uniqueMapList(uniqueList).stream().map(map -> BeanUtil.mapToBean(map, CrmContacts.class, true)).collect(Collectors.toList());
							boolean isUpdate = false;
							if (contactsList.size() > 0) {
								if (Objects.equals(2, getUploadExcelBO().getRepeatHandling())) {
									return;
								}
								if (contactsList.size() > 1) {
									rowList.add(0, "Data duplicates with multiple unique fields");
									errorList.add(rowList);
									return;
								}
								isUpdate = true;
							}
							CrmContactsSaveBO object = new CrmContactsSaveBO();
							JSONObject entityObject = new JSONObject();
							fixedFieldList.forEach(field -> {
								if (!field.getFieldName().equals("customerId")) {
									String fieldName = field.getFieldName();
									entityObject.fluentPut(fieldName, rowList.get(kv.getInteger(fieldName)));
								}
							});
							entityObject
									.fluentPut("customerId", customer.getId())
									.fluentPut("ownerUserId", ownerUserId);
							if (contactsList.size() == 1) {
								CrmContacts contacts = contactsList.get(0);
								boolean auth = AuthUtil.isCrmAuth(CrmEnum.CONTACTS, contacts.getContactsId(), CrmAuthEnum.EDIT);
								if (auth) {
									rowList.add(0, "Data does not have permission to overwrite");
									errorList.add(rowList);
									return;
								}
								entityObject.put("contacts_id", contacts.getContactsId());
								entityObject.put("batch_id", contacts.getBatchId());
							}
							object.setField(addFieldArray(rowList));
							object.setEntity(entityObject.getInnerMapObject());
							CrmVerify verify = new CrmVerify(getUploadExcelBO().getCrmEnum());
							Result result = verify.verify(object);
							if (!result.hasSuccess()) {
								rowList.add(0, result.getMsg());
								errorList.add(rowList);
								return;
							}
							try {
								ApplicationContextHolder.getBean(ICrmContactsService.class).addOrUpdate(object, true);
							} catch (CrmException ex) {
								rowList.add(0, ex.getMsg());
								errorList.add(rowList);
								return;
							}
							if (isUpdate) {
								updateNum++;
							}
						} catch (Exception ex) {
							log.error("Import data exception:", ex);
							rowList.add(0, "Import exception");
							errorList.add(rowList);
						}
					}
				} else if (rowIndex == 1) {
					queryExcelHead(rowList);
				} else {
					errorList.add(0, rowList);
				}
			});
		}
	}

	public class ProductUploadService extends UploadService {

		@Override
		public void importExcel() {
			ExcelUtil.readBySax(getUploadExcelBO().getFilePath(), 0, (int sheetIndex, int rowIndex, List<Object> rowList) -> {
				num++;
				redis.setex(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + getUploadExcelBO().getMessageId().toString(), UPLOAD_EXCEL_EXIST_TIME, Math.max(num, 0));
				if (rowList.size() < kv.entrySet().size()) {
					for (int i = rowList.size() - 1; i < kv.entrySet().size(); i++) {
						rowList.add(null);
					}
				}
				if (num > 10001) {
					rowList.add(0, "Up to 10000 pieces of data can be imported at the same time");
					errorList.add(rowList);
					return;
				}
				if (rowIndex > 1) {
					if (templateErr) {
						rowList.add(0, "Please use the latest template");
						errorList.add(rowList);
					} else {
						try {
							String categoryName = rowList.get(kv.getInteger("categoryId")).toString();
							CrmProductCategory category = ApplicationContextHolder.getBean(ICrmProductCategoryService.class).queryFirstCategoryByName(categoryName);
							if (category == null) {
								rowList.add(0, "The product type filled in does not exist");
								errorList.add(rowList);
								return;
							}
							for (Integer integer : isNullList) {
								if (ObjectUtil.isEmpty(rowList.get(integer))) {
									rowList.add(0, "Required fields are not filled");
									errorList.add(rowList);
									return;
								}
							}
							Long ownerUserId = getOwnerUserIdByRowList(rowList);
							if (ownerUserId == null) {
								rowList.add(0, "The person in charge does not exist");
								errorList.add(rowList);
								return;
							}
							for (CrmModelFiledVO record : uniqueList) {
								Object value = rowList.get(kv.getInteger(record.getFieldName()));
								record.setValue(value);
							}
							List<CrmProduct> productList = uniqueMapList(uniqueList).stream().map(map -> BeanUtil.mapToBean(map, CrmProduct.class, true)).collect(Collectors.toList());
							boolean isUpdate = false;
							if (productList.size() > 0) {
								if (Objects.equals(2, getUploadExcelBO().getRepeatHandling())) {
									return;
								}
								if (productList.size() > 1) {
									rowList.add(0, "Data duplicates with multiple unique fields");
									errorList.add(rowList);
									return;
								}
								isUpdate = true;
							}
							CrmModelSaveBO object = new CrmModelSaveBO();
							JSONObject entityObject = new JSONObject();
							if (productList.size() == 1) {
								CrmProduct product = productList.get(0);
								boolean auth = AuthUtil.isCrmAuth(CrmEnum.PRODUCT, product.getProductId(), CrmAuthEnum.EDIT);
								if (auth) {
									rowList.add(0, "Data does not have permission to overwrite");
									errorList.add(rowList);
									return;
								}
								entityObject.put("product_id", product.getProductId());
								entityObject.put("batch_id", product.getBatchId());
							}
							fixedFieldList.forEach(field -> {
								if (!field.getFieldName().equals("categoryId")) {
									String fieldName = field.getFieldName();
									if (!"status".equals(fieldName)) {
										entityObject.fluentPut(fieldName, rowList.get(kv.getInteger(fieldName)));
									} else {
										Object value = rowList.get(kv.getInteger(fieldName));
										entityObject.fluentPut(fieldName, "list".equals(value) ? 1 : 0);
									}

								}
							});
							entityObject.fluentPut("categoryId", category.getCategoryId()).fluentPut("ownerUserId", ownerUserId);
							object.setField(addFieldArray(rowList));
							object.setEntity(entityObject.getInnerMapObject());
							CrmVerify verify = new CrmVerify(getUploadExcelBO().getCrmEnum());
							Result result = verify.verify(object);
							if (!result.hasSuccess()) {
								rowList.add(0, result.getMsg());
								errorList.add(rowList);
								return;
							}
							try {
								ApplicationContextHolder.getBean(ICrmProductService.class).addOrUpdate(object, true);
							} catch (CrmException ex) {
								rowList.add(0, ex.getMsg());
								errorList.add(rowList);
								return;
							}
							if (isUpdate) {
								updateNum++;
							}
						} catch (Exception ex) {
							log.error("Import data exception:", ex);
							rowList.add(0, "Import exception");
							errorList.add(rowList);
						}
					}
				} else if (rowIndex == 1) {
					queryExcelHead(rowList);
				} else {
					errorList.add(0, rowList);
				}
			});
		}
	}

}
