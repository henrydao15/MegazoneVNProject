package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.feign.examine.entity.ExamineRecordReturnVO;
import com.megazone.core.feign.examine.entity.ExamineRecordSaveBO;
import com.megazone.core.feign.examine.service.ExamineService;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.ActionRecordUtil;
import com.megazone.crm.common.AuthUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmBackLogEnum;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmContractSaveBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.CrmContract;
import com.megazone.crm.entity.PO.CrmInvoice;
import com.megazone.crm.entity.PO.CrmInvoiceData;
import com.megazone.crm.entity.PO.CrmInvoiceInfo;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmInvoiceMapper;
import com.megazone.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrmInvoiceServiceImpl extends BaseServiceImpl<CrmInvoiceMapper, CrmInvoice> implements ICrmInvoiceService, CrmPageService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private ICrmNumberSettingService crmNumberSettingService;

	@Autowired
	private ICrmInvoiceInfoService crmInvoiceInfoService;

	@Autowired
	private ActionRecordUtil actionRecordUtil;

	@Autowired
	private ExamineService examineService;

	@Autowired
	private ICrmFieldService crmFieldService;

	@Autowired
	private ICrmInvoiceDataService crmInvoiceDataService;

	@Autowired
	private FieldService fieldService;

	@Override
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search) {
		return queryList(search, false);
	}


	private void supplementFieldInfo(Integer typeId, Integer recordId, ExamineRecordSaveBO examineRecordSaveBO) {
		examineRecordSaveBO.setLabel(3);
		examineRecordSaveBO.setTypeId(typeId);
		examineRecordSaveBO.setRecordId(recordId);
		if (examineRecordSaveBO.getDataMap() != null) {
			examineRecordSaveBO.getDataMap().put("createUserId", UserUtil.getUserId());
		} else {
			Map<String, Object> entityMap = new HashMap<>(1);
			entityMap.put("createUserId", UserUtil.getUserId());
			examineRecordSaveBO.setDataMap(entityMap);
		}
	}

	@Override
	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		List<CrmInvoice> list = lambdaQuery().select(CrmInvoice::getInvoiceId, CrmInvoice::getInvoiceApplyNumber).in(CrmInvoice::getInvoiceId, ids).list();
		return list.stream().map(crmInvoice -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmInvoice.getInvoiceId());
			simpleCrmEntity.setName(crmInvoice.getInvoiceApplyNumber());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
	}

	@Override
	public CrmInvoice queryById(Integer invoiceId) {
		return getBaseMapper().queryById(invoiceId);
	}

	@Override
	public void updateInvoiceStatus(CrmInvoice crmInvoice) {
		CrmInvoice invoice = new CrmInvoice();
		invoice.setInvoiceId(crmInvoice.getInvoiceId());
		invoice.setInvoiceStatus(1);
		invoice.setInvoiceNumber(crmInvoice.getInvoiceNumber());
		invoice.setRealInvoiceDate(crmInvoice.getRealInvoiceDate());
		invoice.setLogisticsNumber(crmInvoice.getLogisticsNumber());
		updateById(invoice);
		Map<String, Object> objectMap = BeanUtil.beanToMap(invoice, false, true);
		objectMap.put("realInvoiceDate", DateUtil.formatDate(crmInvoice.getRealInvoiceDate()));
		objectMap.put("updateTime", DateUtil.formatDateTime(invoice.getUpdateTime()));
		updateField(objectMap, Collections.singletonList(invoice.getInvoiceId()));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByIds(List<Integer> ids) {
		for (Integer id : ids) {
			CrmInvoice crmInvoice = getById(id);
			if (crmInvoice == null) {
				continue;
			}
			boolean bol = (crmInvoice.getCheckStatus() != 4 && crmInvoice.getCheckStatus() != 5) && (!UserUtil.getUserId().equals(UserUtil.getSuperUser()) && !UserUtil.getUser().getRoles().contains(UserUtil.getSuperRole()));
			if (bol) {
				throw new CrmException(CrmCodeEnum.CAN_ONLY_DELETE_WITHDRAWN_AND_SUBMITTED_EXAMINE);
			}
			ApplicationContextHolder.getBean(AdminFileService.class).delete(Collections.singletonList(crmInvoice.getBatchId()));
			if (ObjectUtil.isNotEmpty(crmInvoice.getExamineRecordId())) {
				examineService.deleteExamineRecord(crmInvoice.getExamineRecordId());
			}
			crmInvoiceDataService.deleteByBatchId(Collections.singletonList(crmInvoice.getBatchId()));
			removeById(crmInvoice.getInvoiceId());
		}
		deletePage(ids);
	}

	@Override
	public void changeOwnerUser(List<Integer> ids, Long ownerUserId) {
		LambdaUpdateWrapper<CrmInvoice> lambdaUpdateWrapper = new LambdaUpdateWrapper<CrmInvoice>();
		lambdaUpdateWrapper.in(CrmInvoice::getInvoiceId, ids).set(CrmInvoice::getOwnerUserId, ownerUserId);
		update(lambdaUpdateWrapper);
		for (Integer id : ids) {
			CrmInvoice invoice = getById(id);
			actionRecordUtil.addConversionRecord(id, CrmEnum.INVOICE, ownerUserId, invoice.getInvoiceApplyNumber());
		}
		//modify es
		Map<String, Object> map = new HashMap<>();
		map.put("ownerUserId", ownerUserId);
		map.put("ownerUserName", UserCacheUtil.getUserName(ownerUserId));
		updateField(map, ids);
	}

	@Override
	public List<FileEntity> queryFileList(Integer id) {
		List<FileEntity> fileEntityList = new ArrayList<>();
		CrmInvoice crmInvoice = getById(id);
		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		fileService.queryFileList(crmInvoice.getBatchId()).getData().forEach(fileEntity -> {
			fileEntity.setSource("Attachment upload");
			fileEntity.setReadOnly(0);
			fileEntityList.add(fileEntity);
		});
		return fileEntityList;
	}

	@Override
	public void saveInvoiceInfo(CrmInvoiceInfo crmInvoiceInfo) {
		crmInvoiceInfo.setCreateUserId(UserUtil.getUserId()).setCreateTime(new Date());
		crmInvoiceInfoService.save(crmInvoiceInfo);
	}

	@Override
	public void updateInvoiceInfo(CrmInvoiceInfo crmInvoiceInfo) {
		boolean auth = AuthUtil.isRwAuth(crmInvoiceInfo.getCustomerId(), CrmEnum.CUSTOMER, CrmAuthEnum.EDIT);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		crmInvoiceInfoService.updateById(crmInvoiceInfo);
	}

	@Override
	public void deleteInvoiceInfo(Integer infoId) {
		CrmInvoiceInfo invoiceInfo = crmInvoiceInfoService.getById(infoId);
		boolean auth = AuthUtil.isRwAuth(invoiceInfo.getCustomerId(), CrmEnum.CUSTOMER, CrmAuthEnum.DELETE);
		if (auth) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		crmInvoiceInfoService.removeById(infoId);

	}


	@Override
	public void setOtherField(Map<String, Object> map) {
		String ownerUserName = UserCacheUtil.getUserName((Long) map.get("ownerUserId"));
		map.put("ownerUserName", ownerUserName);
		String createUserName = UserCacheUtil.getUserName((Long) map.get("createUserId"));
		map.put("createUserName", createUserName);
		String customerName = ApplicationContextHolder.getBean(ICrmCustomerService.class).getCustomerName((Integer) map.get("customerId"));
		map.put("customerName", customerName);
		CrmContract contract = ApplicationContextHolder.getBean(ICrmContractService.class).getById((Serializable) map.get("contractId"));
		if (contract != null) {
			map.put("contractNum", contract.getNum());
			map.put("contractMoney", contract.getMoney());
			map.put("contractName", contract.getName());
		}
	}

	/**
	 * Get crm list type*
	 *
	 * @return data
	 */
	public CrmEnum getLabel() {
		return CrmEnum.INVOICE;
	}

	@Override
	public List<CrmModelFiledVO> queryDefaultField() {
		List<CrmModelFiledVO> filedList = crmFieldService.queryField(getLabel().getType());
		filedList.add(new CrmModelFiledVO("updateTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("ownerUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("createUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("ownerUserName", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("createUserName", FieldEnum.TEXT, 1));
		return filedList;
	}


	/**
	 * Query field configuration
	 *
	 * @param id primary key ID
	 * @return data
	 */
	@Override
	public List<CrmModelFiledVO> queryField(Integer id) {
		return queryField(id, false);
	}

	private List<CrmModelFiledVO> queryField(Integer id, boolean appendInformation) {
		CrmModel crmModel = queryByIds(id);
		if (id != null) {
			List<JSONObject> customerList = new ArrayList<>();
			if (crmModel.get("customerId") != null) {
				JSONObject customer = new JSONObject();
				customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
			}
			crmModel.put("customerId", customerList);
			if (crmModel.get("contractId") != null) {
				crmModel.put("contractId", Collections.singletonList(new JSONObject().fluentPut("contractId", crmModel.get("contractId")).fluentPut("contractNum", crmModel.get("contractNum")).fluentPut("contractMoney", crmModel.get("contractMoney"))));
			} else {
				crmModel.put("contractId", new ArrayList<>());
			}
		}
		List<CrmModelFiledVO> vos = crmFieldService.queryField(crmModel);
		if (appendInformation) {
			List<CrmModelFiledVO> modelFiledVOS = appendInformation(crmModel);
			vos.addAll(modelFiledVOS);
		}
		return vos;
	}

	@Override
	public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id) {
		CrmModel crmModel = queryByIds(id);
		if (id != null) {
			List<JSONObject> customerList = new ArrayList<>();
			if (crmModel.get("customerId") != null) {
				JSONObject customer = new JSONObject();
				customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
			}
			crmModel.put("customerId", customerList);
			if (crmModel.get("contractId") != null) {
				crmModel.put("contractId", Collections.singletonList(new JSONObject().fluentPut("contractId", crmModel.get("contractId")).fluentPut("contractNum", crmModel.get("contractNum")).fluentPut("contractMoney", crmModel.get("contractMoney"))));
			} else {
				crmModel.put("contractId", new ArrayList<>());
			}
		}
		return crmFieldService.queryFormPositionFieldVO(crmModel);
	}

	@Override
	public List<CrmModelFiledVO> information(Integer contractId) {
		return queryField(contractId, true);
	}


	/**
	 * Save or add information
	 *
	 * @param crmModel model
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdate(CrmContractSaveBO crmModel, boolean isExcel) {
		CrmInvoice crmInvoice = BeanUtil.copyProperties(crmModel.getEntity(), CrmInvoice.class);
		String batchId = StrUtil.isNotEmpty(crmInvoice.getBatchId()) ? crmInvoice.getBatchId() : IdUtil.simpleUUID();
		actionRecordUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_invoice_data"));
		crmInvoiceDataService.saveData(crmModel.getField(), batchId);
		if (crmInvoice.getInvoiceId() != null) {
			CrmInvoice oldInvoice = getById(crmInvoice.getInvoiceId());
			if (oldInvoice.getCheckStatus() == 1) {
				throw new CrmException(CrmCodeEnum.CRM_INVOICE_EXAMINE_PASS_ERROR);
			}
			if (!Arrays.asList(2, 4, 5, 10).contains(oldInvoice.getCheckStatus())) {
				throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EDIT_ERROR);
			}
			ExamineRecordSaveBO examineRecordSaveBO = crmModel.getExamineFlowData();
			this.supplementFieldInfo(oldInvoice.getInvoiceId(), oldInvoice.getExamineRecordId(), examineRecordSaveBO);
			examineRecordSaveBO.setTitle(crmInvoice.getInvoiceApplyNumber());
			ExamineRecordReturnVO crmExamineData = examineService.addExamineRecord(examineRecordSaveBO).getData();
			crmInvoice.setExamineRecordId(crmExamineData.getRecordId());
			crmInvoice.setCheckStatus(crmExamineData.getExamineStatus());
			Map<String, Object> oldInvoiceMap = BeanUtil.beanToMap(oldInvoice);
			Map<String, Object> newInvoiceMap = BeanUtil.beanToMap(crmInvoice);
			if (oldInvoiceMap.containsKey("invoiceType")) {
				oldInvoiceMap.put("invoiceType", parseInvoiceType(Integer.valueOf(oldInvoiceMap.get("invoiceType").toString())));
			}
			if (newInvoiceMap.containsKey("invoiceType")) {
				newInvoiceMap.put("invoiceType", parseInvoiceType(Integer.valueOf(newInvoiceMap.get("invoiceType").toString())));
			}
			ApplicationContextHolder.getBean(ICrmBackLogDealService.class).deleteByTypes(null, CrmEnum.INVOICE, crmInvoice.getInvoiceId(), CrmBackLogEnum.CHECK_INVOICE);
			actionRecordUtil.updateRecord(oldInvoiceMap, newInvoiceMap, CrmEnum.INVOICE, crmInvoice.getInvoiceApplyNumber(), crmInvoice.getInvoiceId());
			crmInvoice.setUpdateTime(new Date());
			updateById(crmInvoice);
			crmInvoice = getById(crmInvoice.getInvoiceId());
		} else {
			List<AdminConfig> configList = adminService.queryConfigByName("numberSetting").getData();
			AdminConfig adminConfig = configList.stream().filter(config -> Objects.equals(getLabel().getType().toString(), config.getValue())).collect(Collectors.toList()).get(0);
			if (adminConfig.getStatus() == 1 && StrUtil.isEmpty(crmInvoice.getInvoiceApplyNumber())) {
				String result = crmNumberSettingService.generateNumber(adminConfig, null);
				crmInvoice.setInvoiceApplyNumber(result);
			}
			crmInvoice.setInvoiceStatus(0);
			crmInvoice.setOwnerUserId(UserUtil.getUserId());
			ExamineRecordSaveBO examineRecordSaveBO = crmModel.getExamineFlowData();
			crmInvoice.setCreateUserId(UserUtil.getUserId()).setCreateTime(new Date()).setUpdateTime(new Date()).setBatchId(batchId);
			save(crmInvoice);
			this.supplementFieldInfo(crmInvoice.getInvoiceId(), null, examineRecordSaveBO);
			examineRecordSaveBO.setTitle(crmInvoice.getInvoiceApplyNumber());
			ExamineRecordReturnVO crmExamineData = examineService.addExamineRecord(examineRecordSaveBO).getData();
			crmInvoice.setExamineRecordId(crmExamineData.getRecordId());
			crmInvoice.setCheckStatus(crmExamineData.getExamineStatus());
			updateById(crmInvoice);
			actionRecordUtil.addRecord(crmInvoice.getInvoiceId(), CrmEnum.INVOICE, crmInvoice.getInvoiceApplyNumber());
		}
		crmModel.setEntity(BeanUtil.beanToMap(crmInvoice));
		crmModel.getEntity().put("realInvoiceDate", DateUtil.formatDate((Date) crmModel.getEntity().get("realInvoiceDate")));
		savePage(crmModel, crmInvoice.getInvoiceId(), isExcel);
	}


	/**
	 * Query field configuration
	 *
	 * @param id primary key ID
	 * @return data
	 */
	public CrmModel queryByIds(Integer id) {
		CrmModel crmModel;
		if (id != null) {
			crmModel = getBaseMapper().queryByIds(id, UserUtil.getUserId());
			crmModel.setLabel(CrmEnum.INVOICE.getType());
			crmModel.setOwnerUserName(UserCacheUtil.getUserName(crmModel.getOwnerUserId()));
			List<String> nameList = StrUtil.splitTrim((String) crmModel.get("companyUserId"), Const.SEPARATOR);
			String name = nameList.stream().map(str -> UserCacheUtil.getUserName(Long.valueOf(str))).collect(Collectors.joining(Const.SEPARATOR));
			crmModel.put("companyUserName", name);
			crmModel.put("createUserName", UserCacheUtil.getUserName((Long) crmModel.get("createUserId")));
			crmInvoiceDataService.setDataByBatchId(crmModel);
			List<String> stringList = ApplicationContextHolder.getBean(ICrmRoleFieldService.class).queryNoAuthField(crmModel.getLabel());
			stringList.forEach(crmModel::remove);
		} else {
			crmModel = new CrmModel(CrmEnum.INVOICE.getType());
		}
		return crmModel;
	}


	/**
	 * export all
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
				if (ObjectUtil.isEmpty(record.get("checkStatus"))) {
					return;
				}
				String checkStatus;
				//0 to be reviewed, 1 to pass, 2 to reject, 3 to be reviewed 4: withdrawn 5 not submitted 6 created 7 deleted 8 void
				switch (Integer.parseInt(record.get("checkStatus").toString())) {
					case 1:
						checkStatus = "Pass";
						break;
					case 2:
						checkStatus = "Rejected";
						break;
					case 3:
						checkStatus = "Under review";
						break;
					case 4:
						checkStatus = "Withdrawn";
						break;
					case 5:
						checkStatus = "Uncommitted";
						break;
					case 7:
						checkStatus = "Deleted";
						break;
					case 8:
						checkStatus = "Void";
						break;
					default:
						checkStatus = "Pending review";
				}
				record.put("checkStatus", checkStatus);
				Integer invoiceType;
				try {
					invoiceType = Integer.parseInt(record.get("invoiceType").toString());
				} catch (Exception e) {
					invoiceType = null;
				}
				if (invoiceType != null) {
					record.put("invoiceType", parseInvoiceType(invoiceType));
				}
				Integer invoiceStatus;
				try {
					invoiceStatus = Integer.parseInt(record.get("invoiceStatus").toString());
				} catch (Exception e) {
					invoiceStatus = null;
				}
				if (invoiceStatus != null) {
					record.put("invoiceStatus", Objects.equals(1, invoiceStatus) ? "Invoiced" : "Not Invoiced");
				}
			}

			@Override
			public String getExcelName() {
				return "Invoice";
			}
		}, headList, response);
	}

	private String parseInvoiceType(Integer invoiceType) {
		switch (invoiceType) {
			case 1:
				return "VAT special invoice";
			case 2:
				return "VAT ordinary invoice";
			case 3:
				return "National tax general machine-printed invoice";
			case 4:
				return "Land tax general machine-printed invoice";
			case 5:
				return "Receipt";
			default:
				return "";
		}
	}

	/**
	 * Search field with large search box
	 *
	 * @return fields
	 */
	@Override
	public String[] appendSearch() {
		return new String[]{"invoiceNumber", "contractNum", "customerName"};
	}

	@Override
	public Dict getSearchTransferMap() {
		return Dict.create().set("customerId", "customerName").set("contractId", "contractNum").set("customer_id", "customerId");
	}

	@Override
	public void updateInformation(CrmUpdateInformationBO updateInformationBO) {
		String batchId = updateInformationBO.getBatchId();
		Integer invoiceId = updateInformationBO.getId();
		CrmInvoice invoice = getById(invoiceId);
		if (invoice.getCheckStatus() == 8) {
			throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EDIT_ERROR);
		}
		if (invoice.getCheckStatus() == 1) {
			throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EDIT_ERROR);
		}
		if (!Arrays.asList(2, 4, 5, 10).contains(invoice.getCheckStatus())) {
			throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EDIT_ERROR);
		}
		updateInformationBO.getList().forEach(record -> {
			CrmInvoice crmInvoice = getById(updateInformationBO.getId());
			uniqueFieldIsAbnormal(record.getString("name"), record.getInteger("fieldId"), record.getString("value"), batchId);
			Map<String, Object> oldInvoiceMap = BeanUtil.beanToMap(crmInvoice);
			if (record.getInteger("fieldType") == 1) {
				Map<String, Object> crmInvoiceMap = new HashMap<>(oldInvoiceMap);
				crmInvoiceMap.put(record.getString("fieldName"), record.get("value"));
				CrmInvoice crmInvoices = BeanUtil.mapToBean(crmInvoiceMap, CrmInvoice.class, true);
				if (oldInvoiceMap.containsKey("invoiceType")) {
					oldInvoiceMap.put("invoiceType", parseInvoiceType(Integer.valueOf(oldInvoiceMap.get("invoiceType").toString())));
				}
				if (crmInvoiceMap.containsKey("invoiceType")) {
					crmInvoiceMap.put("invoiceType", parseInvoiceType(Integer.valueOf(crmInvoiceMap.get("invoiceType").toString())));
				}
				actionRecordUtil.updateRecord(oldInvoiceMap, crmInvoiceMap, CrmEnum.INVOICE, crmInvoices.getInvoiceApplyNumber(), crmInvoices.getInvoiceId());
				update().set(StrUtil.toUnderlineCase(record.getString("fieldName")), record.get("value")).eq("invoice_id", updateInformationBO.getId()).update();
			} else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
				CrmInvoiceData invoiceData = crmInvoiceDataService.lambdaQuery().select(CrmInvoiceData::getValue, CrmInvoiceData::getId).eq(CrmInvoiceData::getFieldId, record.getInteger("fieldId")).eq(CrmInvoiceData::getBatchId, batchId).one();
				String value = invoiceData != null ? invoiceData.getValue() : null;
				actionRecordUtil.publicContentRecord(CrmEnum.INVOICE, BehaviorEnum.UPDATE, invoiceId, crmInvoice.getInvoiceApplyNumber(), record, value);
				String newValue = fieldService.convertObjectValueToString(record.getInteger("type"), record.get(
						"value"), record.getString("value"));

				CrmInvoiceData crmInvoiceData = new CrmInvoiceData();
				crmInvoiceData.setId(invoiceData != null ? invoiceData.getId() : null);
				crmInvoiceData.setFieldId(record.getInteger("fieldId"));
				crmInvoiceData.setName(record.getString("fieldName"));
				crmInvoiceData.setValue(newValue);
				crmInvoiceData.setCreateTime(new Date());
				crmInvoiceData.setBatchId(batchId);
				crmInvoiceDataService.saveOrUpdate(crmInvoiceData);

			}
			updateField(record, invoiceId);
		});
		this.lambdaUpdate().set(CrmInvoice::getUpdateTime, new Date()).eq(CrmInvoice::getInvoiceId, invoiceId).update();
	}
}
