package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.cache.CrmCacheKey;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.ActionRecordUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.constant.CrmActivityEnum;
import com.megazone.crm.constant.CrmBackLogEnum;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmModelSaveBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmLeadsMapper;
import com.megazone.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrmLeadsServiceImpl extends BaseServiceImpl<CrmLeadsMapper, CrmLeads> implements ICrmLeadsService, CrmPageService {


	@Autowired
	private ICrmFieldService crmFieldService;

	@Autowired
	private ICrmLeadsDataService crmLeadsDataService;

	@Autowired
	private ICrmLeadsUserStarService crmLeadsUserStarService;

	@Autowired
	private ICrmBackLogDealService crmBackLogDealService;

	@Autowired
	private ICrmActivityService crmActivityService;

	@Autowired
	private ICrmActionRecordService crmActionRecordService;

	@Autowired
	private ICrmCustomerService crmCustomerService;

	@Autowired
	private ICrmCustomerSettingService crmCustomerSettingService;

	@Autowired
	private ICrmCustomerDataService crmCustomerDataService;

	@Autowired
	private AdminFileService adminFileService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private FieldService fieldService;

	@Resource
	private CrmPageService customerService;

	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;


	@Autowired
	private ActionRecordUtil actionRecordUtil;


	@Override
	public String[] appendSearch() {
		return new String[]{"leadsName", "telephone", "mobile"};
	}

	@Override
	public CrmEnum getLabel() {
		return CrmEnum.LEADS;
	}

	@Override
	public List<CrmModelFiledVO> queryDefaultField() {
		List<CrmModelFiledVO> filedList = crmFieldService.queryField(getLabel().getType());
		filedList.add(new CrmModelFiledVO("lastTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("lastContent", FieldEnum.TEXTAREA, 1));
		filedList.add(new CrmModelFiledVO("updateTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("ownerUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("createUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("ownerUserName", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("createUserName", FieldEnum.TEXT, 1));
		return filedList;
	}

	@Override
	public List<CrmModelFiledVO> queryField(Integer id) {
		return queryField(id, false);
	}

	private List<CrmModelFiledVO> queryField(Integer id, boolean appendInformation) {
		CrmModel crmModel = queryById(id);
		List<CrmModelFiledVO> filedVOS = crmFieldService.queryField(crmModel);
		if (appendInformation) {
			List<CrmModelFiledVO> modelFiledVOS = appendInformation(crmModel);
			filedVOS.addAll(modelFiledVOS);
		}
		return filedVOS;
	}

	@Override
	public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id) {
		CrmModel crmModel = queryById(id);
		return crmFieldService.queryFormPositionFieldVO(crmModel);
	}

	@Override
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search) {
		BasePage<Map<String, Object>> basePage = queryList(search, false);
		Long userId = UserUtil.getUserId();
		List<Integer> starIds = crmLeadsUserStarService.starList(userId);
		basePage.getList().forEach(map -> {
			map.put("star", starIds.contains((Integer) map.get("leadsId")) ? 1 : 0);
		});
		return basePage;
	}

	@Override
	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		List<CrmLeads> list = lambdaQuery().select(CrmLeads::getLeadsId, CrmLeads::getLeadsName).in(CrmLeads::getLeadsId, ids).list();
		return list.stream().map(crmLeads -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmLeads.getLeadsId());
			simpleCrmEntity.setName(crmLeads.getLeadsName());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
	}

	@Override
	public CrmModel queryById(Integer id) {
		CrmModel crmModel;
		if (id != null) {
			crmModel = getBaseMapper().queryById(id, UserUtil.getUserId());
			crmModel.setLabel(CrmEnum.LEADS.getType());
			crmModel.setOwnerUserName(UserCacheUtil.getUserName(crmModel.getOwnerUserId()));
			crmLeadsDataService.setDataByBatchId(crmModel);
			List<String> stringList = ApplicationContextHolder.getBean(ICrmRoleFieldService.class).queryNoAuthField(crmModel.getLabel());
			stringList.forEach(crmModel::remove);
		} else {
			crmModel = new CrmModel(CrmEnum.LEADS.getType());
		}
		return crmModel;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdate(CrmModelSaveBO crmModel, boolean isExcel) {
		CrmLeads crmLeads = BeanUtil.copyProperties(crmModel.getEntity(), CrmLeads.class);
		String batchId = StrUtil.isNotEmpty(crmLeads.getBatchId()) ? crmLeads.getBatchId() : IdUtil.simpleUUID();
		actionRecordUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_leads_data"));
		crmLeadsDataService.saveData(crmModel.getField(), batchId);
		if (StrUtil.isEmpty(crmLeads.getEmail())) {
			crmLeads.setEmail(null);
		}
		//Modify the next contact time, the to-do items need to be reminded, and the last follow-up time needs to be synchronized
		if (crmLeads.getNextTime() != null) {
			crmLeads.setLastTime(DateUtil.date());
		}
		if (crmLeads.getLeadsId() != null) {
			crmLeads.setCustomerId(0);
			crmLeads.setUpdateTime(DateUtil.date());
			actionRecordUtil.updateRecord(BeanUtil.beanToMap(getById(crmLeads.getLeadsId())), BeanUtil.beanToMap(crmLeads), CrmEnum.LEADS, crmLeads.getLeadsName(), crmLeads.getLeadsId());
			updateById(crmLeads);
			//Query to save es once, because some fields do not save es will appear null
			crmLeads = getById(crmLeads.getLeadsId());
			crmBackLogDealService.deleteByType(crmLeads.getOwnerUserId(), CrmEnum.LEADS, CrmBackLogEnum.FOLLOW_LEADS, crmLeads.getLeadsId());
		} else {
			crmLeads.setCreateTime(DateUtil.date());
			crmLeads.setUpdateTime(DateUtil.date());
			crmLeads.setCreateUserId(UserUtil.getUserId());
			crmLeads.setIsTransform(0);
			if (!isExcel) {
				crmLeads.setFollowup(0);
			}
			crmLeads.setCreateUserId(UserUtil.getUserId());
			if (crmLeads.getOwnerUserId() == null) {
				crmLeads.setOwnerUserId(UserUtil.getUserId());
			}
			crmLeads.setBatchId(batchId);
			save(crmLeads);
			actionRecordUtil.addRecord(crmLeads.getLeadsId(), CrmEnum.LEADS, crmLeads.getLeadsName());
		}
		crmModel.setEntity(BeanUtil.beanToMap(crmLeads));
		savePage(crmModel, crmLeads.getLeadsId(), isExcel);
	}

	@Override
	public void setOtherField(Map<String, Object> map) {
		String ownerUserName = UserCacheUtil.getUserName((Long) map.get("ownerUserId"));
		map.put("ownerUserName", ownerUserName);
		String createUserName = UserCacheUtil.getUserName((Long) map.get("createUserId"));
		map.put("createUserName", createUserName);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByIds(List<Integer> ids) {
		LambdaQueryWrapper<CrmLeads> wrapper = new LambdaQueryWrapper<>();
		wrapper.select(CrmLeads::getBatchId);
		wrapper.in(CrmLeads::getLeadsId, ids);
		List<String> batchIdList = listObjs(wrapper, Object::toString);
		// delete the follow-up record
		crmActivityService.deleteActivityRecord(CrmActivityEnum.LEADS, ids);
		//delete field operation record
		crmActionRecordService.deleteActionRecord(CrmEnum.LEADS, ids);
		// delete custom fields
		crmLeadsDataService.deleteByBatchId(batchIdList);
		//todo deletes the file and does not process it for now
		removeByIds(ids);
		// delete es data
		deletePage(ids);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changeOwnerUser(List<Integer> leadsIds, Long newOwnerUserId) {
		LambdaUpdateWrapper<CrmLeads> wrapper = new LambdaUpdateWrapper<>();
		wrapper.in(CrmLeads::getLeadsId, leadsIds);
		wrapper.set(CrmLeads::getOwnerUserId, newOwnerUserId);
		wrapper.set(CrmLeads::getFollowup, 0);
		wrapper.set(CrmLeads::getIsReceive, 1);
		for (Integer leadsId : leadsIds) {
			CrmLeads crmLeads = getById(leadsId);
			BaseUtil.getRedis().del(CrmCacheKey.CRM_BACKLOG_NUM_CACHE_KEY + crmLeads.getOwnerUserId().toString());
			actionRecordUtil.addConversionRecord(leadsId, CrmEnum.LEADS, newOwnerUserId, crmLeads.getLeadsName());
		}
		update(wrapper);
		BaseUtil.getRedis().del(CrmCacheKey.CRM_BACKLOG_NUM_CACHE_KEY + newOwnerUserId.toString());
		//modify es
		String ownerUserName = UserCacheUtil.getUserName(newOwnerUserId);
		Map<String, Object> map = new HashMap<>();
		map.put("ownerUserId", newOwnerUserId);
		map.put("ownerUserName", ownerUserName);
		map.put("followup", 0);
		map.put("isReceive", 1);
		updateField(map, leadsIds);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void transfer(List<Integer> leadsIds) {
		List<Integer> customerIds = new ArrayList<>();
		Map<Integer, CrmModelSaveBO> crmModelSaveBoMap = new HashMap<>(8);
		for (Integer leadsId : leadsIds) {
			CrmModel leadsMap = queryById(leadsId);
			CrmLeads crmLeads = BeanUtil.copyProperties(leadsMap, CrmLeads.class);
			if (crmLeads.getIsTransform() == 1) {
				throw new CrmException(CrmCodeEnum.CRM_LEADS_TRANSFER_ERROR);
			}
			CrmCustomer crmCustomer = new CrmCustomer();
			crmCustomer.setCustomerName(crmLeads.getLeadsName());
			crmCustomer.setNextTime(crmLeads.getNextTime());
			crmCustomer.setMobile(crmLeads.getMobile());
			crmCustomer.setTelephone(crmLeads.getTelephone());
			crmCustomer.setDealStatus(0);
			crmCustomer.setCreateUserId(UserUtil.getUserId());
			crmCustomer.setOwnerUserId(crmLeads.getOwnerUserId());
			crmCustomer.setCreateTime(new Date());
			crmCustomer.setUpdateTime(new Date());
			crmCustomer.setReceiveTime(new Date());
			crmCustomer.setDetailAddress(crmLeads.getAddress());
			crmCustomer.setLocation("");
			crmCustomer.setAddress("");
			crmCustomer.setLng("");
			crmCustomer.setLat("");
			crmCustomer.setRemark("");
			crmCustomer.setEmail(crmLeads.getEmail());
			crmCustomer.setStatus(1);
			crmCustomer.setLastContent(crmLeads.getLastContent());
			crmCustomer.setLastTime(crmLeads.getLastTime());
			String customerBatchId = IdUtil.simpleUUID();
			crmCustomer.setBatchId(customerBatchId);
			List<CrmField> leadsFields = crmFieldService.list(CrmEnum.LEADS.getType(), false);
			List<CrmField> customerFields = crmFieldService.list(CrmEnum.CUSTOMER.getType(), true);
			List<CrmCustomerData> customerDataList = new ArrayList<>();
			Map<String, Object> customerExtraMap = new HashMap<>();
			for (CrmField leadsField : leadsFields) {
				for (CrmField customerField : customerFields) {
					Integer isUnique = customerField.getIsUnique();
					boolean bol = ("Customer Source".equals(customerField.getName()) && "Lead Source".equals(leadsField.getName())) || ("Customer Industry".equals(customerField.getName()) && "Customer Industry".equals(leadsField.getName())) || ("Customer Level".equals(customerField.getName()) && "Customer Level".equals(leadsField.getName()));
					if (bol) {
						if (isUnique == 1 && crmFieldService.queryCustomerFieldDuplicateByNoFixed(customerField.getName(), leadsMap.get(leadsField.getName())) > 0) {
							throw new CrmException(CrmCodeEnum.CRM_FIELD_EXISTED, customerField.getName());
						}
						CrmCustomerData crmCustomerData = new CrmCustomerData();
						crmCustomerData.setValue((String) leadsMap.get(leadsField.getFieldName()));
						crmCustomerData.setFieldId(customerField.getFieldId());
						crmCustomerData.setName(customerField.getName());
						crmCustomerData.setFieldName(customerField.getFieldName());
						customerDataList.add(crmCustomerData);
						continue;
					}
					if (leadsField.getRelevant() != null && customerField.getFieldId().equals(leadsField.getRelevant())) {
						if (customerField.getFieldType().equals(1)) {
							customerExtraMap.put(customerField.getFieldName(), leadsMap.get(StrUtil.toCamelCase(leadsField.getFieldName())));
						} else {
							CrmCustomerData crmCustomerData = new CrmCustomerData();
							crmCustomerData.setValue((String) leadsMap.get(StrUtil.toCamelCase(leadsField.getFieldName())));
							crmCustomerData.setFieldId(customerField.getFieldId());
							crmCustomerData.setName(customerField.getName());
							crmCustomerData.setFieldName(StrUtil.toCamelCase(customerField.getFieldName()));
							customerDataList.add(crmCustomerData);
						}
					}

				}
			}
			BeanUtil.fillBeanWithMap(customerExtraMap, crmCustomer, true);
			crmCustomer.setBatchId(customerBatchId);
			for (CrmField customerField : customerFields) {
				Integer isUnique = customerField.getIsUnique();
				String name = customerField.getName();
				Map<String, Object> customerMap = BeanUtil.beanToMap(crmCustomer);
				for (String key : customerMap.keySet()) {
					if (key.equals(StrUtil.toCamelCase(customerField.getFieldName()))) {
						Object value = customerMap.get(key);
						if (value != null && !"".equals(value.toString())) {
							if (isUnique == 1 && crmFieldService.queryCustomerFieldDuplicateByFixed(customerField.getFieldName(), value) > 0) {
								throw new CrmException(CrmCodeEnum.CRM_FIELD_EXISTED, name);
							}
						}
					}
				}
			}
			if (!crmCustomerSettingService.queryCustomerSettingNum(1, crmCustomer.getOwnerUserId())) {
				throw new CrmException(CrmCodeEnum.THE_NUMBER_OF_CUSTOMERS_HAS_REACHED_THE_LIMIT);
			}
			crmCustomerService.save(crmCustomer);
			Integer customerId = crmCustomer.getCustomerId();
			customerIds.add(customerId);
			// save custom fields
			saveCustomerField(customerDataList, customerBatchId);
			CrmModelSaveBO crmModelSaveBO = new CrmModelSaveBO();
			crmModelSaveBO.setEntity(BeanUtil.beanToMap(crmCustomer));
			List<CrmModelFiledVO> collect = customerDataList.stream().map(field -> BeanUtil.copyProperties(field, CrmModelFiledVO.class)).collect(Collectors.toList());
			crmModelSaveBO.setField(collect);
			crmModelSaveBoMap.put(customerId, crmModelSaveBO);

			actionRecordUtil.addConversionCustomerRecord(crmCustomer.getCustomerId(), CrmEnum.CUSTOMER, crmCustomer.getCustomerName());
			lambdaUpdate().set(CrmLeads::getIsTransform, 1).set(CrmLeads::getUpdateTime, new Date()).set(CrmLeads::getCustomerId, crmCustomer.getCustomerId()).eq(CrmLeads::getLeadsId, leadsId).update();

			//transfer operation record
			List<CrmActionRecord> crmActionRecordList = crmActionRecordService.lambdaQuery().eq(CrmActionRecord::getActionId, leadsId).eq(CrmActionRecord::getTypes, 1).list();
			crmActionRecordList.forEach(crmActionRecord -> {
				crmActionRecord.setId(null);
				crmActionRecord.setTypes(CrmEnum.CUSTOMER.getType());
				crmActionRecord.setActionId(crmCustomer.getCustomerId());
			});
			crmActionRecordService.saveBatch(crmActionRecordList, 500);

			//transfer activity data
			List<CrmActivity> crmActivityList = crmActivityService.lambdaQuery().eq(CrmActivity::getActivityType, 1).eq(CrmActivity::getType, 1).eq(CrmActivity::getActivityTypeId, leadsId).list();
			List<String> adminFileIdList = new ArrayList<>();
			if (crmActivityList.size() != 0) {
				crmActivityList.forEach(crmActivity -> {
					List<FileEntity> leadsRecordFiles = adminFileService.queryFileList(crmActivity.getBatchId()).getData();
					String customerRecordBatchId = IdUtil.simpleUUID();
					List<String> fileIds = leadsRecordFiles.stream().map(FileEntity::getFileId).collect(Collectors.toList());
					crmActivity.setBatchId(customerRecordBatchId);
					crmActivity.setActivityId(null);
					crmActivity.setActivityType(CrmEnum.CUSTOMER.getType());
					crmActivity.setActivityTypeId(crmCustomer.getCustomerId());
					adminFileService.saveBatchFileEntity(fileIds, customerRecordBatchId);
				});
				crmActivityService.saveBatch(crmActivityList, 100);
			}
			List<FileEntity> fileList = adminFileService.queryFileList(crmLeads.getBatchId()).getData();
			if (fileList.size() != 0) {
				fileList.forEach(adminFile -> {
					adminFileIdList.add(adminFile.getFileId());
				});
			}
			adminFileService.saveBatchFileEntity(adminFileIdList, customerBatchId);
		}
		for (int i = 0; i < leadsIds.size(); i++) {
			Integer leadsId = leadsIds.get(i);
			Integer customerId = customerIds.get(i);
			UpdateRequest updateRequest = new UpdateRequest(CrmEnum.LEADS.getIndex(), "_doc", leadsId.toString());
			Map<String, Object> map = new HashMap<>();
			map.put("isTransform", 1);
			map.put("updateTime", DateUtil.formatDateTime(new Date()));
			map.put("customerId", customerId);
			updateRequest.doc(map);
			try {
				elasticsearchRestTemplate.getClient().update(updateRequest, RequestOptions.DEFAULT);
			} catch (IOException e) {
				log.error("es update exception!");
			}
			customerService.savePage(crmModelSaveBoMap.get(customerId), customerId, false);
		}

		elasticsearchRestTemplate.refresh(getIndex());
	}

	private void saveCustomerField(List<CrmCustomerData> customerDataList, String batchId) {
		if (CollUtil.isEmpty(customerDataList) || StrUtil.isEmpty(batchId)) {
			return;
		}
		crmCustomerDataService.lambdaUpdate().eq(CrmCustomerData::getBatchId, batchId).remove();
		customerDataList.forEach(fieldv -> {
			fieldv.setId(null);
			fieldv.setName(fieldv.getFieldName());
			fieldv.setCreateTime(DateUtil.date());
			fieldv.setBatchId(batchId);
			crmCustomerDataService.save(fieldv);
		});
	}


	/**
	 * Download import template
	 *
	 * @param response thread id
	 */
	@Override
	public void downloadExcel(HttpServletResponse response) throws IOException {
		List<CrmModelFiledVO> crmModelFiledList = queryField(null);
		int k = 0;
		for (int i = 0; i < crmModelFiledList.size(); i++) {
			if ("leadsName".equals(crmModelFiledList.get(i).getFieldName())) {
				k = i;
				break;
			}
		}
		crmModelFiledList.add(k + 1, new CrmModelFiledVO("ownerUserId", FieldEnum.TEXT, "responsible person", 1).setIsNull(1));
		ExcelParseUtil.importExcel(new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "clue";
			}
		}, crmModelFiledList, response, "crm");
	}

	/**
	 * export
	 *
	 * @param response resp
	 * @param search   search object
	 */
	@Override
	public void exportExcel(HttpServletResponse response, CrmSearchBO search) {
		List<Map<String, Object>> dataList = queryList(search, true).getList();
		List<CrmFieldSortVO> headList = crmFieldService.queryListHead(getLabel().getType());
		ExcelParseUtil.exportExcel(dataList, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {
				for (String fieldName : headMap.keySet()) {
					record.put(fieldName, ActionRecordUtil.parseValue(record.get(fieldName), headMap.get(fieldName), false));
				}
			}

			@Override
			public String getExcelName() {
				return "clue";
			}
		}, headList, response);
	}

	/**
	 * star
	 *
	 * @param leads lead id
	 */
	@Override
	public void star(Integer leads) {
		LambdaQueryWrapper<CrmLeadsUserStar> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CrmLeadsUserStar::getLeadsId, leads);
		wrapper.eq(CrmLeadsUserStar::getUserId, UserUtil.getUserId());
		CrmLeadsUserStar star = crmLeadsUserStarService.getOne(wrapper);
		if (star == null) {
			star = new CrmLeadsUserStar();
			star.setLeadsId(leads);
			star.setUserId(UserUtil.getUserId());
			crmLeadsUserStarService.save(star);
		} else {
			crmLeadsUserStarService.removeById(star.getId());
		}
	}

	@Override
	public List<CrmModelFiledVO> information(Integer leadsId) {
		return queryField(leadsId, true);
	}

	/**
	 * Query the number of files
	 *
	 * @param leadsId id
	 * @return data
	 */
	@Override
	public CrmInfoNumVO num(Integer leadsId) {
		List<String> batchIdList = new ArrayList<>();
		CrmLeads crmLeads = getById(leadsId);
		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		List<CrmField> crmFields = crmFieldService.queryFileField();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmLeadsData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmLeadsData::getValue);
			wrapper.eq(CrmLeadsData::getBatchId, crmLeads.getBatchId());
			wrapper.in(CrmLeadsData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			batchIdList.addAll(crmLeadsDataService.listObjs(wrapper, Object::toString));
		}
		batchIdList.add(crmLeads.getBatchId());
		batchIdList.addAll(crmActivityService.queryFileBatchId(crmLeads.getLeadsId(), getLabel().getType()));
		CrmInfoNumVO numVO = new CrmInfoNumVO();
		numVO.setFileCount(fileService.queryNum(batchIdList).getData());
		return numVO;
	}


	/**
	 * Query file list
	 *
	 * @param leadsId id
	 * @return file
	 */
	@Override
	public List<FileEntity> queryFileList(Integer leadsId) {
		List<FileEntity> fileEntityList = new ArrayList<>();
		CrmLeads crmLeads = getById(leadsId);
		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		fileService.queryFileList(crmLeads.getBatchId()).getData().forEach(fileEntity -> {
			fileEntity.setSource("Attachment upload");
			fileEntity.setReadOnly(0);
			fileEntityList.add(fileEntity);
		});
		List<CrmField> crmFields = crmFieldService.queryFileField();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmLeadsData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmLeadsData::getValue);
			wrapper.eq(CrmLeadsData::getBatchId, crmLeads.getBatchId());
			wrapper.in(CrmLeadsData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			List<FileEntity> data = fileService.queryFileList(crmLeadsDataService.listObjs(wrapper, Object::toString)).getData();
			data.forEach(fileEntity -> {
				fileEntity.setSource("Thread Details");
				fileEntity.setReadOnly(1);
				fileEntityList.add(fileEntity);
			});
		}
		List<String> stringList = crmActivityService.queryFileBatchId(crmLeads.getLeadsId(), getLabel().getType());
		if (stringList.size() > 0) {
			List<FileEntity> data = fileService.queryFileList(stringList).getData();
			data.forEach(fileEntity -> {
				fileEntity.setSource("Follow-up record");
				fileEntity.setReadOnly(1);
				fileEntityList.add(fileEntity);
			});
		}
		return fileEntityList;
	}

	@Override
	public void updateInformation(CrmUpdateInformationBO updateInformationBO) {
		String batchId = updateInformationBO.getBatchId();
		Integer leadsId = updateInformationBO.getId();
		updateInformationBO.getList().forEach(record -> {
			CrmLeads oldLeads = getById(updateInformationBO.getId());
			uniqueFieldIsAbnormal(record.getString("name"), record.getInteger("fieldId"), record.getString("value"), batchId);
			Map<String, Object> oldLeadsMap = BeanUtil.beanToMap(oldLeads);
			if (record.getInteger("fieldType") == 1) {
				Map<String, Object> crmLeadsMap = new HashMap<>(oldLeadsMap);
				crmLeadsMap.put(record.getString("fieldName"), record.get("value"));
				CrmLeads crmLeads = BeanUtil.mapToBean(crmLeadsMap, CrmLeads.class, true);
				actionRecordUtil.updateRecord(oldLeadsMap, crmLeadsMap, CrmEnum.LEADS, crmLeads.getLeadsName(), crmLeads.getLeadsId());
				update().set(StrUtil.toUnderlineCase(record.getString("fieldName")), record.get("value")).eq("leads_id", updateInformationBO.getId()).update();
			} else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
				CrmLeadsData leadsData = crmLeadsDataService.lambdaQuery().select(CrmLeadsData::getValue, CrmLeadsData::getId).eq(CrmLeadsData::getFieldId, record.getInteger("fieldId")).eq(CrmLeadsData::getBatchId, batchId).one();
				String value = leadsData != null ? leadsData.getValue() : null;
				actionRecordUtil.publicContentRecord(CrmEnum.LEADS, BehaviorEnum.UPDATE, leadsId, oldLeads.getLeadsName(), record, value);
				String newValue = fieldService.convertObjectValueToString(record.getInteger("type"), record.get("value"), record.getString("value"));
				CrmLeadsData crmLeadsData = new CrmLeadsData();
				crmLeadsData.setId(leadsData != null ? leadsData.getId() : null);
				crmLeadsData.setFieldId(record.getInteger("fieldId"));
				crmLeadsData.setName(record.getString("fieldName"));
				crmLeadsData.setValue(newValue);
				crmLeadsData.setCreateTime(new Date());
				crmLeadsData.setBatchId(batchId);
				crmLeadsDataService.saveOrUpdate(crmLeadsData);

			}
			updateField(record, leadsId);
		});
		this.lambdaUpdate().set(CrmLeads::getUpdateTime, new Date()).eq(CrmLeads::getLeadsId, leadsId).update();
	}

	@Override
	public List<String> eventLeads(CrmEventBO crmEventBO) {
		return getBaseMapper().eventLeads(crmEventBO);
	}

	@Override
	public BasePage<Map<String, Object>> eventLeadsPageList(QueryEventCrmPageBO eventCrmPageBO) {
		Long userId = eventCrmPageBO.getUserId();
		Long time = eventCrmPageBO.getTime();
		if (userId == null) {
			userId = UserUtil.getUserId();
		}
		List<Integer> leadsIds = getBaseMapper().eventLeadsList(userId, new Date(time));
		if (leadsIds.size() == 0) {
			return new BasePage<>();
		}
		List<String> collect = leadsIds.stream().map(Object::toString).collect(Collectors.toList());
		CrmSearchBO crmSearchBO = new CrmSearchBO();
		crmSearchBO.setSearchList(Collections.singletonList(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, collect)));
		crmSearchBO.setLabel(CrmEnum.LEADS.getType());
		crmSearchBO.setPage(eventCrmPageBO.getPage());
		crmSearchBO.setLimit(eventCrmPageBO.getLimit());
		return queryPageList(crmSearchBO);
	}
}
