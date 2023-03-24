package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.feign.examine.entity.ExamineRecordReturnVO;
import com.megazone.core.feign.examine.entity.ExamineRecordSaveBO;
import com.megazone.core.feign.examine.service.ExamineService;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.*;
import com.megazone.crm.common.ActionRecordUtil;
import com.megazone.crm.common.AuthUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.ElasticUtil;
import com.megazone.crm.constant.*;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmContractMapper;
import com.megazone.crm.mapper.CrmReceivablesPlanMapper;
import com.megazone.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.sum.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrmContractServiceImpl extends BaseServiceImpl<CrmContractMapper, CrmContract> implements ICrmContractService, CrmPageService {

	@Autowired
	private ICrmFieldService crmFieldService;

	@Autowired
	private ICrmContractDataService crmContractDataService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ICrmNumberSettingService crmNumberSettingService;

	@Autowired
	private ICrmActionRecordService crmActionRecordService;

	@Autowired
	private ICrmActivityService crmActivityService;

	@Autowired
	private ICrmBackLogDealService crmBackLogDealService;

	@Autowired
	private ICrmContractProductService crmContractProductService;

	@Autowired
	private ICrmBusinessProductService crmBusinessProductService;

	@Autowired
	private ICrmReceivablesPlanService crmReceivablesPlanService;

	@Autowired
	private ICrmCustomerService crmCustomerService;

	@Autowired
	private ActionRecordUtil actionRecordUtil;

	@Autowired
	private AdminFileService adminFileService;

	@Autowired
	private ICrmReceivablesService crmReceivablesService;

	@Autowired
	private ExamineService examineService;

	@Autowired
	private FieldService fieldService;
	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;
	@Autowired
	private ICrmBusinessService crmBusinessService;
	@Autowired
	private ICrmContactsService crmContactsService;

	@Override
	public String[] appendSearch() {
		return new String[]{"name", "num", "customerName"};
	}

	@Override
	public CrmEnum getLabel() {
		return CrmEnum.CONTRACT;
	}

	@Override
	public List<CrmModelFiledVO> queryDefaultField() {
		List<CrmModelFiledVO> filedList = crmFieldService.queryField(getLabel().getType());
		filedList.add(new CrmModelFiledVO("lastTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("updateTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("ownerUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("createUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("companyUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("checkStatus", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("contractId", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("receivedMoney", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("unreceivedMoney", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, 0));
		return filedList;
	}

	@Override
	public List<CrmModelFiledVO> queryField(Integer id) {
		return queryField(id, false);
	}

	private List<CrmModelFiledVO> queryField(Integer id, boolean appendInformation) {
		CrmModel crmModel = queryById(id);
		if (id != null) {
			List<JSONObject> customerList = new ArrayList<>();
			if (crmModel.get("customerId") != null) {
				JSONObject customer = new JSONObject();
				customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
			}
			crmModel.put("customerId", customerList);
			if (crmModel.get("businessId") != null) {
				crmModel.put("businessId", Collections.singletonList(new JSONObject().fluentPut("businessId", crmModel.get("businessId")).fluentPut("businessName", crmModel.get("businessName"))));
			} else {
				crmModel.put("businessId", new ArrayList<>());
			}
			if (crmModel.get("contactsId") != null) {
				crmModel.put("contactsId", Collections.singletonList(new JSONObject().fluentPut("contactsId", crmModel.get("contactsId")).fluentPut("name", crmModel.get("contactsName"))));
			} else {
				crmModel.put("contactsId", new ArrayList<>());
			}
		}
		List<CrmModelFiledVO> vos = crmFieldService.queryField(crmModel);
		JSONObject object = new JSONObject();
		object.fluentPut("discountRate", crmModel.get("discountRate")).fluentPut("product", crmContractProductService.queryList(id)).fluentPut("totalPrice", crmModel.get("totalPrice"));
		vos.add(new CrmModelFiledVO().setFieldName("product").setName("product").setValue(object).setFormType("product").setSetting(new ArrayList<>()).setIsNull(0).setFieldType(1));
		if (appendInformation) {
			List<CrmModelFiledVO> modelFiledVOS = appendInformation(crmModel);
			vos.addAll(modelFiledVOS);
		}
		return vos;
	}

	@Override
	public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id) {
		CrmModel crmModel = queryById(id);
		if (id != null) {
			List<JSONObject> customerList = new ArrayList<>();
			if (crmModel.get("customerId") != null) {
				JSONObject customer = new JSONObject();
				customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
			}
			crmModel.put("customerId", customerList);
			if (crmModel.get("businessId") != null) {
				crmModel.put("businessId", Collections.singletonList(new JSONObject().fluentPut("businessId", crmModel.get("businessId")).fluentPut("businessName", crmModel.get("businessName"))));
			} else {
				crmModel.put("businessId", new ArrayList<>());
			}
			if (crmModel.get("contactsId") != null) {
				crmModel.put("contactsId", Collections.singletonList(new JSONObject().fluentPut("contactsId", crmModel.get("contactsId")).fluentPut("name", crmModel.get("contactsName"))));
			} else {
				crmModel.put("contactsId", new ArrayList<>());
			}
		}
		List<List<CrmModelFiledVO>> vos = crmFieldService.queryFormPositionFieldVO(crmModel);
		JSONObject object = new JSONObject();
		object.fluentPut("discountRate", crmModel.get("discountRate")).fluentPut("product", crmContractProductService.queryList(id)).fluentPut("totalPrice", crmModel.get("totalPrice"));
		CrmModelFiledVO crmModelFiledVO = new CrmModelFiledVO().setFieldName("product").setName("product").setValue(object).setFormType("product").setSetting(new ArrayList<>()).setIsNull(0).setFieldType(1);
		crmModelFiledVO.setStylePercent(100);
		vos.add(ListUtil.toList(crmModelFiledVO));
		return vos;
	}

	/**
	 * @param search
	 * @return
	 */
	@Override
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search) {
		CrmSearchBO search1 = ObjectUtil.cloneByStream(search);
		BasePage<Map<String, Object>> basePage = queryList(search, false);
		for (Map<String, Object> map : basePage.getList()) {
			Double contractMoney = StrUtil.isNotEmpty(map.get("money").toString()) ? Double.parseDouble(map.get("money").toString()) : 0D;
			BigDecimal receivedProgress = new BigDecimal(100);
			if (!contractMoney.equals(0D)) {
				receivedProgress = BigDecimal.valueOf(map.get("receivedMoney") != null ? Double.parseDouble(map.get("receivedMoney").toString()) : 0D).divide(new BigDecimal(contractMoney), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			}
			map.put("receivedProgress", receivedProgress);
		}
		SearchRequest searchRequest = new SearchRequest(getIndex());
		searchRequest.types(getDocType());
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder queryBuilder = createQueryBuilder(search1);
		queryBuilder.must(QueryBuilders.termQuery("checkStatus", 1));
		sourceBuilder.query(queryBuilder);
		sourceBuilder.aggregation(AggregationBuilders.sum("contractMoney").field("money"))
				.aggregation(AggregationBuilders.sum("receivedMoney").field("receivedMoney"))
				.aggregation(AggregationBuilders.sum("unreceivedMoney").field("unreceivedMoney"));
		searchRequest.source(sourceBuilder);
		try {
			SearchResponse searchCount = elasticsearchRestTemplate.getClient().search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregations = searchCount.getAggregations();
			Map<String, Object> countMap = new HashMap<>();
			ParsedSum contractMoney = aggregations.get("contractMoney");
			ParsedSum receivedMoney = aggregations.get("receivedMoney");
			ParsedSum unreceivedMoney = aggregations.get("unreceivedMoney");
			countMap.put("contractMoney", contractMoney.getValue());
			countMap.put("receivedMoney", receivedMoney.getValue());
			countMap.put("unReceivedMoney", unreceivedMoney.getValue());
			basePage.setExtraData(new JSONObject().fluentPut("money", countMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return basePage;
	}

	/**
	 * @param id ID
	 * @return data
	 */
	@Override
	public CrmModel queryById(Integer id) {
		CrmModel crmModel;
		if (id != null) {
			crmModel = getBaseMapper().queryById(id, UserUtil.getUserId());
			crmModel.setLabel(CrmEnum.CONTRACT.getType());
			crmModel.setOwnerUserName(UserCacheUtil.getUserName(crmModel.getOwnerUserId()));
			List<String> nameList = StrUtil.splitTrim((String) crmModel.get("companyUserId"), Const.SEPARATOR);
			String name = nameList.stream().map(str -> UserCacheUtil.getUserName(Long.valueOf(str))).collect(Collectors.joining(Const.SEPARATOR));
			crmModel.put("companyUserName", name);
			crmModel.put("createUserName", UserCacheUtil.getUserName((Long) crmModel.get("createUserId")));
			crmContractDataService.setDataByBatchId(crmModel);
			List<String> stringList = ApplicationContextHolder.getBean(ICrmRoleFieldService.class).queryNoAuthField(crmModel.getLabel());
			stringList.forEach(crmModel::remove);
			Double contractMoney = crmModel.get("money") != null ? Double.parseDouble(crmModel.get("money").toString()) : 0D;
			BigDecimal receivedProgress = new BigDecimal(100);
			if (!contractMoney.equals(0D)) {
				receivedProgress = new BigDecimal(crmModel.get("receivedMoney") != null ? Double.parseDouble(crmModel.get("receivedMoney").toString()) : 0D).divide(new BigDecimal(contractMoney), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
			}
			crmModel.put("receivedProgress", receivedProgress);
		} else {
			crmModel = new CrmModel(CrmEnum.CONTRACT.getType());
		}
		return crmModel;
	}

	/**
	 * @param crmModel model
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdate(CrmContractSaveBO crmModel) {
		CrmContract crmContract = BeanUtil.copyProperties(crmModel.getEntity(), CrmContract.class);
		String batchId = StrUtil.isNotEmpty(crmContract.getBatchId()) ? crmContract.getBatchId() : IdUtil.simpleUUID();
		actionRecordUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_contract_data"));
		crmContractDataService.saveData(crmModel.getField(), batchId);
		if (crmContract.getStartTime() != null && crmContract.getEndTime() != null && crmContract.getStartTime().compareTo(crmContract.getEndTime()) > 0) {
			throw new CrmException(CrmCodeEnum.CRM_CONTRACT_DATE_ERROR);
		}
		if (crmContract.getDiscountRate() == null) {
			crmContract.setDiscountRate(new BigDecimal("0"));
		}
		if (crmContract.getMoney() == null) {
			crmContract.setMoney(BigDecimal.ZERO);
		}
		ExamineRecordSaveBO examineRecordSaveBO = crmModel.getExamineFlowData();
		ExamineRecordReturnVO examineData = null;
		if (crmContract.getContractId() == null) {
			List<AdminConfig> configList = adminService.queryConfigByName("numberSetting").getData();
			AdminConfig adminConfig = configList.stream().filter(config -> Objects.equals(getLabel().getType().toString(), config.getValue())).collect(Collectors.toList()).get(0);
			if (adminConfig.getStatus() == 1 && StrUtil.isEmpty(crmContract.getNum())) {
				String result = crmNumberSettingService.generateNumber(adminConfig, crmContract.getOrderDate());
				crmContract.setNum(result);
			}
			Integer contract = lambdaQuery().eq(CrmContract::getNum, crmContract.getNum()).ne(CrmContract::getCheckStatus, 7).count();
			if (contract != 0) {
				throw new CrmException(CrmCodeEnum.CRM_CONTRACT_NUM_ERROR);
			}
			crmContract.setUnreceivedMoney(crmContract.getMoney());
			crmContract.setReceivedMoney(BigDecimal.ZERO);
			crmContract.setCreateUserId(UserUtil.getUserId());
			crmContract.setBatchId(batchId);
			crmContract.setCreateTime(DateUtil.date());
			crmContract.setUpdateTime(DateUtil.date());
			crmContract.setOwnerUserId(UserUtil.getUserId());
			save(crmContract);
			if (crmContract.getCheckStatus() != null && crmContract.getCheckStatus() == 5) {
				crmContract.setCheckStatus(5);
			} else {
				this.supplementFieldInfo(1, crmContract.getContractId(), null, examineRecordSaveBO);
				examineRecordSaveBO.setTitle(crmContract.getName());
				examineData = examineService.addExamineRecord(examineRecordSaveBO).getData();
				crmContract.setExamineRecordId(examineData.getRecordId());
				crmContract.setCheckStatus(examineData.getExamineStatus());
			}
			if (crmContract.getCheckStatus() == 1 || crmContract.getCheckStatus() == 10) {
				CrmCustomer customer = ApplicationContextHolder.getBean(ICrmCustomerService.class).getById(crmContract.getCustomerId());
				customer.setDealStatus(1);
				Date dealTime = crmContract.getOrderDate() != null ? crmContract.getOrderDate() : new Date();
				customer.setDealTime(dealTime);
				ApplicationContextHolder.getBean(ICrmCustomerService.class).updateById(customer);
				Map<String, Object> map = new HashMap<>();
				map.put("dealTime", DateUtil.formatDateTime(dealTime));
				map.put("dealStatus", 1);
				ElasticUtil.updateField(elasticsearchRestTemplate, map, customer.getCustomerId(), CrmEnum.CUSTOMER.getIndex());
			}
			updateById(crmContract);
			crmActivityService.addActivity(2, CrmActivityEnum.CONTRACT, crmContract.getContractId());
			actionRecordUtil.addRecord(crmContract.getContractId(), CrmEnum.CONTRACT, crmContract.getName());
		} else {
			CrmContract contract = getById(crmContract.getContractId());
			if (contract.getCheckStatus() == 8) {
				contract.setCheckStatus(5);
			}
			if (contract.getCheckStatus() == 1) {
				throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EXAMINE_PASS_HINT_ERROR);
			}
			if (contract.getCheckStatus() == 8) {
				throw new CrmException(CrmCodeEnum.CRM_CONTRACT_CANCELLATION_ERROR);
			}
			if (!Arrays.asList(2, 4, 5, 10).contains(contract.getCheckStatus())) {
				throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EDIT_ERROR);
			}
			if (crmContract.getCheckStatus() != null && crmContract.getCheckStatus() == 5) {
				crmContract.setCheckStatus(5);
			} else {
				this.supplementFieldInfo(1, crmContract.getContractId(), contract.getExamineRecordId(), examineRecordSaveBO);
				examineRecordSaveBO.setTitle(crmContract.getName());
				examineData = examineService.addExamineRecord(examineRecordSaveBO).getData();
				crmContract.setExamineRecordId(examineData.getRecordId());
				crmContract.setCheckStatus(examineData.getExamineStatus());
			}
			BigDecimal decimal = getBaseMapper().queryReceivablesMoney(crmContract.getContractId());
			crmContract.setUnreceivedMoney(crmContract.getMoney().subtract(decimal));
			crmContract.setReceivedMoney(decimal);
			crmBackLogDealService.deleteByTypes(null, CrmEnum.CONTRACT, crmContract.getContractId(), CrmBackLogEnum.END_CONTRACT, CrmBackLogEnum.REMIND_RETURN_VISIT_CONTRACT, CrmBackLogEnum.CHECK_CONTRACT);
			crmContract.setUpdateTime(DateUtil.date());
			actionRecordUtil.updateRecord(BeanUtil.beanToMap(contract), BeanUtil.beanToMap(crmContract), CrmEnum.CONTRACT, crmContract.getName(), crmContract.getContractId());
			updateById(crmContract);
			crmContract = getById(crmContract.getContractId());
			ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "contract", crmContract.getContractId().toString(), crmContract.getName());

		}


		if (0 == crmContract.getCheckStatus()) {
			if (examineData != null) {
				actionRecordUtil.addCrmExamineActionRecord(CrmEnum.CONTRACT, examineData.getRecordId(), BehaviorEnum.SUBMIT_EXAMINE, crmContract.getNum());
			}
		}
		if (3 == crmContract.getCheckStatus() || 0 == crmContract.getCheckStatus() || 10 == crmContract.getCheckStatus()) {
			List<Integer> statuss = new ArrayList<>();
			statuss.add(3);
			List<CrmReceivablesPlan> planList = crmReceivablesPlanService.lambdaQuery().eq(CrmReceivablesPlan::getContractId, crmContract.getContractId())
					.in(CrmReceivablesPlan::getReceivedStatus, statuss).list();
			if (planList.size() > 0) {
				crmReceivablesPlanService.lambdaUpdate().set(CrmReceivablesPlan::getReceivedStatus, 5)
						.eq(CrmReceivablesPlan::getContractId, crmContract.getContractId())
						.in(CrmReceivablesPlan::getReceivedStatus, statuss).update();
				for (CrmReceivablesPlan plan : planList) {
					Map<String, Object> map = new HashMap<>();
					map.put("receivedStatus", 5);
					map.put("updateTime", DateUtil.formatDateTime(new Date()));
					ElasticUtil.updateField(getRestTemplate(), map, plan.getReceivablesPlanId(), CrmEnum.RECEIVABLES_PLAN.getIndex());
				}
			}
		}
		List<CrmContractProduct> contractProductList = crmModel.getProduct();
		crmContractProductService.deleteByContractId(crmContract.getContractId());
		if (crmContract.getBusinessId() != null) {
			crmBusinessProductService.deleteByBusinessId(crmContract.getBusinessId());
		}
		if (contractProductList != null) {
			for (CrmContractProduct crmContractProduct : contractProductList) {
				crmContractProduct.setContractId(crmContract.getContractId());
				crmContractProductService.save(crmContractProduct);
				if (crmContract.getBusinessId() != null) {
					CrmBusinessProduct crmBusinessProduct = BeanUtil.copyProperties(crmContractProduct, CrmBusinessProduct.class);
					crmBusinessProduct.setRId(null);
					crmBusinessProduct.setBusinessId(crmContract.getBusinessId());
					crmBusinessProductService.save(crmBusinessProduct);
				}
			}
		}
		crmModel.setEntity(BeanUtil.beanToMap(crmContract));
		savePage(crmModel, crmContract.getContractId(), false);
	}

	@Override
	public void setOtherField(Map<String, Object> map) {
		String customerName = crmCustomerService.getCustomerName((Integer) map.get("customerId"));
		map.put("customerName", customerName);
		if (map.containsKey("businessId") && ObjectUtil.isNotEmpty(map.get("businessId"))) {
			String businessName = crmBusinessService.getBusinessName((Integer) map.get("businessId"));
			map.put("businessName", businessName);
		} else {
			map.put("businessName", "");
		}
		if (map.containsKey("contactsId") && ObjectUtil.isNotEmpty(map.get("contactsId"))) {
			String contactsName = crmContactsService.getContactsName((Integer) map.get("contactsId"));
			map.put("contactsName", contactsName);
		} else {
			map.put("contactsName", "");
		}
		if (map.containsKey("companyUserId") && ObjectUtil.isNotEmpty(map.get("companyUserId"))) {
			String companyUserName = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(map.get("companyUserId").toString()))
					.stream().map(SimpleUser::getRealname).collect(Collectors.joining(","));
			map.put("companyUserName", companyUserName);
		} else {
			map.put("companyUserName", "");
		}
		if (map.containsKey("ownerUserId") && ObjectUtil.isNotEmpty(map.get("ownerUserId"))) {
			String ownerUserName = UserCacheUtil.getUserName((Long) map.get("ownerUserId"));
			map.put("ownerUserName", ownerUserName);
		} else {
			map.put("ownerUserName", "");
		}
		String createUserName = UserCacheUtil.getUserName((Long) map.get("createUserId"));
		map.put("createUserName", createUserName);
	}

	@Override
	public Dict getSearchTransferMap() {
		return Dict.create().set("customerId", "customerName").set("businessId", "businessName").set("contactsId", "contactsName").set("customer_id", "customerId");
	}

	/**
	 * @param ids ids
	 */
	@Override
	public void deleteByIds(List<Integer> ids) {

		Integer number = ApplicationContextHolder.getBean(ICrmReceivablesService.class).lambdaQuery().in(CrmReceivables::getContractId, ids).ne(CrmReceivables::getCheckStatus, 7).count();
		if (number > 0) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_JOIN_ERROR);
		}
		ids.forEach(id -> {
			CrmContract contract = getById(id);
			boolean bol = (contract.getCheckStatus() != 4 && contract.getCheckStatus() != 5) && (!UserUtil.isAdmin());
			if (bol) {
				throw new CrmException(CrmCodeEnum.CAN_ONLY_DELETE_WITHDRAWN_AND_SUBMITTED_EXAMINE);
			}

			crmContractProductService.deleteByContractId(contract.getContractId());

			crmActivityService.deleteActivityRecord(CrmActivityEnum.CONTRACT, Collections.singletonList(id));

			crmActionRecordService.deleteActionRecord(CrmEnum.CONTRACT, Collections.singletonList(contract.getContractId()));

			crmContractDataService.deleteByBatchId(Collections.singletonList(contract.getBatchId()));

			adminFileService.delete(Collections.singletonList(contract.getBatchId()));
			if (ObjectUtil.isNotEmpty(contract.getExamineRecordId())) {
				examineService.deleteExamineRecord(contract.getExamineRecordId());
			}
			crmReceivablesPlanService.deleteByContractId(contract.getContractId());
			contract.setCheckStatus(7);
			updateById(contract);
		});
		deletePage(ids);
	}

	/**
	 * @param changOwnerUserBO data
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changeOwnerUser(CrmChangeOwnerUserBO changOwnerUserBO) {
		if (changOwnerUserBO.getIds().size() == 0) {
			return;
		}
		String ownerUserName = UserCacheUtil.getUserName(changOwnerUserBO.getOwnerUserId());
		changOwnerUserBO.getIds().forEach(id -> {
			if (AuthUtil.isChangeOwnerUserAuth(id, CrmEnum.CONTRACT, CrmAuthEnum.EDIT)) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
			CrmContract contract = getById(id);
			if (contract.getCheckStatus() == 8) {
				throw new CrmException(CrmCodeEnum.CRM_CONTRACT_TRANSFER_ERROR);
			}
			contract = getById(id);
			if (2 == changOwnerUserBO.getTransferType() && !changOwnerUserBO.getOwnerUserId().equals(contract.getOwnerUserId())) {
				ApplicationContextHolder.getBean(ICrmTeamMembersService.class).addSingleMember(getLabel(), contract.getContractId(), contract.getOwnerUserId(), changOwnerUserBO.getPower(), changOwnerUserBO.getExpiresTime(), contract.getName());
			}
			ApplicationContextHolder.getBean(ICrmTeamMembersService.class).deleteMember(getLabel(), new CrmMemberSaveBO(id, changOwnerUserBO.getOwnerUserId()));
			contract.setOwnerUserId(changOwnerUserBO.getOwnerUserId());
			updateById(contract);
			actionRecordUtil.addConversionRecord(id, CrmEnum.CONTRACT, changOwnerUserBO.getOwnerUserId(), contract.getName());

			Map<String, Object> map = new HashMap<>();
			map.put("ownerUserId", changOwnerUserBO.getOwnerUserId());
			map.put("ownerUserName", ownerUserName);
			updateField(map, Collections.singletonList(id));
		});

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
				//0 pending review, 1 passed, 2 rejected, 3 under review 4: withdrawn 5 not submitted
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
					default:
						checkStatus = "Pending review";
				}
				record.put("checkStatus", checkStatus);
			}

			@Override
			public String getExcelName() {
				return "Contract";
			}
		}, headList, response);
	}

	@Override
	public List<CrmModelFiledVO> information(Integer contractId) {
		return queryField(contractId, true);
	}

	/**
	 * Query product by contract ID
	 *
	 * @param crmRelationPageBO contract ID
	 * @return data
	 */
	@Override
	public JSONObject queryProductListByContractId(CrmRelationPageBO crmRelationPageBO) {
		JSONObject record = getBaseMapper().querySubtotalByContractId(crmRelationPageBO.getContractId());
		if (record.getString("money") == null) {
			record.put("money", 0);
		}
		BasePage<JSONObject> page = getBaseMapper().queryProductPageList(crmRelationPageBO.parse(), crmRelationPageBO.getContractId());
		for (JSONObject jsonObject : page.getList()) {
			Integer status = jsonObject.getInteger("status");
			if (status == 1) {
				jsonObject.put("Whether to be off the shelf", "Yes");
			} else {
				jsonObject.put("Whether to remove or not", "No");
			}
		}
		record.put("pageNumber", page.getPageNumber());
		record.put("pageSize", page.getPageSize());
		record.put("totalPage", page.getTotalPage());
		record.put("totalRow", page.getTotalRow());
		record.put("list", page.getList());
		return record;
	}

	/**
	 * Query the number of files
	 *
	 * @param contractId id
	 * @return data
	 */
	@Override
	public CrmInfoNumVO num(Integer contractId) {
		CrmContract crmContract = getById(contractId);
		List<CrmField> crmFields = crmFieldService.queryFileField();
		List<String> batchIdList = new ArrayList<>();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmContractData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmContractData::getValue);
			wrapper.eq(CrmContractData::getBatchId, crmContract.getBatchId());
			wrapper.in(CrmContractData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			batchIdList.addAll(crmContractDataService.listObjs(wrapper, Object::toString));
		}
		batchIdList.add(crmContract.getBatchId());
		batchIdList.addAll(crmActivityService.queryFileBatchId(crmContract.getContractId(), getLabel().getType()));
		String receivableCon = AuthUtil.getCrmAuthSql(CrmEnum.RECEIVABLES, 1, CrmAuthEnum.READ);
		String returnVisitCon = AuthUtil.getCrmAuthSql(CrmEnum.RETURN_VISIT, 1, CrmAuthEnum.READ);
		String invoiceCon = AuthUtil.getCrmAuthSql(CrmEnum.INVOICE, 1, CrmAuthEnum.READ);
		Map<String, Object> map = new HashMap<>();
		map.put("contractId", contractId);
		map.put("receivableCon", receivableCon);
		map.put("returnVisitCon", returnVisitCon);
		map.put("invoiceCon", invoiceCon);
		CrmInfoNumVO infoNumVO = getBaseMapper().queryNum(map);
		infoNumVO.setFileCount(adminFileService.queryNum(batchIdList).getData());
		infoNumVO.setMemberCount(ApplicationContextHolder.getBean(ICrmTeamMembersService.class).queryMemberCount(getLabel(), crmContract.getContractId(), crmContract.getOwnerUserId()));
		return infoNumVO;
	}

	/**
	 * Query file list
	 *
	 * @param contractId id
	 * @return file
	 */
	@Override
	public List<FileEntity> queryFileList(Integer contractId) {
		List<FileEntity> fileEntityList = new ArrayList<>();
		CrmContract crmContract = getById(contractId);
		adminFileService.queryFileList(crmContract.getBatchId()).getData().forEach(fileEntity -> {
			fileEntity.setSource("Attachment upload");
			fileEntity.setReadOnly(0);
			fileEntityList.add(fileEntity);
		});
		List<CrmField> crmFields = crmFieldService.queryFileField();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmContractData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmContractData::getValue);
			wrapper.eq(CrmContractData::getBatchId, crmContract.getBatchId());
			wrapper.in(CrmContractData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			List<FileEntity> data = adminFileService.queryFileList(crmContractDataService.listObjs(wrapper, Object::toString)).getData();
			data.forEach(fileEntity -> {
				fileEntity.setSource("Contract Details");
				fileEntity.setReadOnly(1);
				fileEntityList.add(fileEntity);
			});
		}
		List<String> stringList = crmActivityService.queryFileBatchId(crmContract.getContractId(), getLabel().getType());
		if (stringList.size() > 0) {
			List<FileEntity> data = adminFileService.queryFileList(stringList).getData();
			data.forEach(fileEntity -> {
				fileEntity.setSource("Follow-up record");
				fileEntity.setReadOnly(1);
				fileEntityList.add(fileEntity);
			});
		}
		return fileEntityList;
	}

	@Override
	public BasePage<CrmReceivablesPlan> queryReceivablesPlanListByContractId(CrmRelationPageBO crmRelationPageBO) {
		CrmReceivablesPlanMapper mapper = (CrmReceivablesPlanMapper) crmReceivablesPlanService.getBaseMapper();
		return mapper.queryReceivablesPlanListByContractId(crmRelationPageBO.parse(), crmRelationPageBO.getContractId());
	}

	@Override
	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		List<CrmContract> list = lambdaQuery().select(CrmContract::getContractId, CrmContract::getName).in(CrmContract::getContractId, ids).list();
		return list.stream().map(crmContract -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmContract.getContractId());
			simpleCrmEntity.setName(crmContract.getName());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
	}

	@Override
	public String getContractName(int contractId) {
		return lambdaQuery().select(CrmContract::getName).eq(CrmContract::getContractId, contractId).oneOpt()
				.map(CrmContract::getName).orElse("");
	}


	@Override
	public void updateInformation(CrmUpdateInformationBO updateInformationBO) {
		String batchId = updateInformationBO.getBatchId();
		Integer contractId = updateInformationBO.getId();
		CrmContract contract = getById(contractId);
		if (contract.getCheckStatus() == 8) {
			throw new CrmException(CrmCodeEnum.CRM_CONTRACT_CANCELLATION_ERROR);
		}
		if (contract.getCheckStatus() == 1) {
			throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EXAMINE_PASS_ERROR);
		}
		if (!Arrays.asList(2, 4, 5, 10).contains(contract.getCheckStatus())) {
			throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EDIT_ERROR);
		}
		updateInformationBO.getList().forEach(record -> {
			CrmContract oldContract = getById(updateInformationBO.getId());
			uniqueFieldIsAbnormal(record.getString("name"), record.getInteger("fieldId"), record.getString("value"), batchId);
			Map<String, Object> oldContractMap = BeanUtil.beanToMap(oldContract);
			if (record.getInteger("fieldType") == 1) {
				Map<String, Object> crmContractMap = new HashMap<>(oldContractMap);
				crmContractMap.put(record.getString("fieldName"), record.get("value"));
				CrmContract crmContract = BeanUtil.mapToBean(crmContractMap, CrmContract.class, true);
				actionRecordUtil.updateRecord(oldContractMap, crmContractMap, CrmEnum.CONTRACT, crmContract.getName(), crmContract.getContractId());
				update().set(StrUtil.toUnderlineCase(record.getString("fieldName")), record.get("value")).eq("contract_id", updateInformationBO.getId()).update();
				if ("name".equals(record.getString("fieldName"))) {
					ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "contract", crmContract.getContractId().toString(), crmContract.getName());
				}
			} else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
				CrmContractData contractData = crmContractDataService.lambdaQuery()
						.select(CrmContractData::getValue, CrmContractData::getId)
						.eq(CrmContractData::getFieldId, record.getInteger("fieldId"))
						.eq(CrmContractData::getBatchId, batchId).last("limit 1").one();
				String value = contractData != null ? contractData.getValue() : null;
				actionRecordUtil.publicContentRecord(CrmEnum.CONTRACT, BehaviorEnum.UPDATE, contractId, oldContract.getName(), record, value);
				String newValue = fieldService.convertObjectValueToString(record.getInteger("type"), record.get("value"), record.getString("value"));
				CrmContractData crmContractData = new CrmContractData();
				crmContractData.setId(contractData != null ? contractData.getId() : null);
				crmContractData.setFieldId(record.getInteger("fieldId"));
				crmContractData.setName(record.getString("fieldName"));
				crmContractData.setValue(newValue);
				crmContractData.setCreateTime(new Date());
				crmContractData.setBatchId(batchId);
				crmContractDataService.saveOrUpdate(crmContractData);

			}
			updateField(record, contractId);
		});
		this.lambdaUpdate().set(CrmContract::getUpdateTime, new Date()).eq(CrmContract::getContractId, contractId).update();
	}


	@Override
	public BasePage<JSONObject> queryListByContractId(CrmRelationPageBO crmRelationPageBO) {
		String conditions = AuthUtil.getCrmAuthSql(CrmEnum.RECEIVABLES, "rec", 1, CrmAuthEnum.READ);
		return crmReceivablesService.queryListByContractId(crmRelationPageBO.parse(), crmRelationPageBO.getContractId(), conditions);
	}

	@Override
	public List<CrmReceivablesPlan> queryReceivablesPlansByContractId(Integer contractId, Integer receivablesId) {
		List<CrmReceivablesPlan> recordList = getBaseMapper().queryReceivablesPlansByContractId(contractId);
		if (receivablesId != null) {
			CrmReceivablesPlan receivables = getBaseMapper().queryReceivablesPlansByReceivablesId(receivablesId);
			if (receivables != null) {
				recordList.add(receivables);
			}
		}
		return recordList;
	}

	@Override
	public BasePage<JSONObject> queryReturnVisit(CrmRelationPageBO crmRelationPageBO) {
		List<CrmField> nameList = crmFieldService.lambdaQuery().select(CrmField::getFieldId, CrmField::getFieldName).eq(CrmField::getLabel, CrmEnum.RETURN_VISIT.getType())
				.eq(CrmField::getIsHidden, 0).ne(CrmField::getFieldType, 1).list();
		String conditions = AuthUtil.getCrmAuthSql(CrmEnum.RECEIVABLES, "a", 1, CrmAuthEnum.READ);
		BasePage<JSONObject> basePage = getBaseMapper().queryReturnVisit(crmRelationPageBO.parse(), crmRelationPageBO.getContractId(), conditions, nameList);
		for (JSONObject jsonObject : basePage.getList()) {
			String ownerUserName = UserCacheUtil.getUserName(jsonObject.getLong("ownerUserId"));
			jsonObject.put("ownerUserName", ownerUserName);
		}
		return basePage;
	}

	@Override
	public void contractDiscard(Integer contractId) {
		CrmContract contract = getById(contractId);
		actionRecordUtil.addObjectActionRecord(CrmEnum.CONTRACT, contractId, BehaviorEnum.CANCEL_EXAMINE, contract.getName());
		lambdaUpdate().set(CrmContract::getCheckStatus, 8).set(CrmContract::getUpdateTime, new Date()).eq(CrmContract::getContractId, contractId).update();
		Map<String, Object> map = new HashMap<>();
		map.put("checkStatus", 8);
		map.put("updateTime", DateUtil.formatDateTime(new Date()));
		updateField(map, Collections.singletonList(contractId));
		List<Integer> statuss = new ArrayList<>();
		statuss.add(0);
		statuss.add(5);
		List<CrmReceivablesPlan> planList = crmReceivablesPlanService.lambdaQuery().eq(CrmReceivablesPlan::getContractId, contractId)
				.in(CrmReceivablesPlan::getReceivedStatus, statuss).list();
		crmReceivablesPlanService.lambdaUpdate().set(CrmReceivablesPlan::getReceivedStatus, 3)
				.eq(CrmReceivablesPlan::getContractId, contractId)
				.in(CrmReceivablesPlan::getReceivedStatus, statuss).update();
		for (CrmReceivablesPlan plan : planList) {
			map = new HashMap<>();
			map.put("receivedStatus", 3);
			map.put("updateTime", DateUtil.formatDateTime(new Date()));
			ElasticUtil.updateField(getRestTemplate(), map, plan.getReceivablesPlanId(), CrmEnum.RECEIVABLES_PLAN.getIndex());
		}


	}

	@Override
	public List<String> endContract(CrmEventBO crmEventBO) {
		return getBaseMapper().endContract(crmEventBO);
	}

	@Override
	public List<String> receiveContract(CrmEventBO crmEventBO) {
		return getBaseMapper().receiveContract(crmEventBO);
	}

	@Override
	public BasePage<Map<String, Object>> eventContractPageList(QueryEventCrmPageBO eventCrmPageBO) {
		Long userId = eventCrmPageBO.getUserId();
		Long time = eventCrmPageBO.getTime();
		Integer type = eventCrmPageBO.getType();
		if (userId == null) {
			userId = UserUtil.getUserId();
		}
		List<Integer> contractIds;
		List<JSONObject> records = null;
		if (type == 1) {
			AdminConfig adminConfig = adminService.queryFirstConfigByName("expiringContractDays").getData();
			if (adminConfig.getStatus() == 0 || ObjectUtil.isNull(adminConfig)) {
				contractIds = new ArrayList<>();
			} else {
				contractIds = getBaseMapper().endContractList(userId, new Date(time), Integer.valueOf(adminConfig.getValue()));
			}
		} else {
			records = getBaseMapper().receiveContractList(userId, new Date(time));
			contractIds = records.stream().map(record -> record.getInteger("contractId")).collect(Collectors.toList());
		}
		if (contractIds.size() == 0) {
			return new BasePage<>();
		}
		List<String> collect = contractIds.stream().map(Object::toString).collect(Collectors.toList());
		CrmSearchBO crmSearchBO = new CrmSearchBO();
		crmSearchBO.setSearchList(Collections.singletonList(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, collect)));
		crmSearchBO.setLabel(CrmEnum.CONTRACT.getType());
		crmSearchBO.setPage(eventCrmPageBO.getPage());
		crmSearchBO.setLimit(eventCrmPageBO.getLimit());
		if (type == 2) {
			BasePage<Map<String, Object>> page = queryPageList(crmSearchBO);
			for (JSONObject record : records) {
				for (Map<String, Object> map : page.getList()) {
					if (map.get("contractId").equals(record.getInteger("contractId"))) {
						map.putAll(record.getInnerMapObject());
					}
				}
			}
			return page;
		} else {
			return queryPageList(crmSearchBO);
		}
	}

	/**
	 * @param biParams
	 * @return data
	 */
	@Override
	public BasePage<Map<String, Object>> queryListByProductId(BiParams biParams) {
		List<CrmContractProduct> products = crmContractProductService
				.lambdaQuery()
				.select(CrmContractProduct::getContractId)
				.eq(CrmContractProduct::getProductId, biParams.getTypeId()).list();
		Integer menuId = 118;
		biParams.setMenuId(menuId);
		BiTimeUtil.BiTimeEntity timeEntity = BiTimeUtil.analyzeType(biParams);

		CrmSearchBO searchBo = new CrmSearchBO();
		searchBo.setPage(biParams.getPage());
		searchBo.setLimit(biParams.getLimit());
		searchBo.setLabel(getLabel().getType());
		searchBo.setSearch(biParams.getSearch());
		searchBo.setOrder(biParams.getOrder());
		searchBo.setSortField(biParams.getSortField());
		List<String> stringList = products.stream().map(crmContractProduct -> crmContractProduct.getContractId().toString()).collect(Collectors.toList());
		List<CrmSearchBO.Search> searchList = searchBo.getSearchList();
		searchList.add(new CrmSearchBO.Search("contractId", "text", CrmSearchBO.FieldSearchEnum.ID, stringList));
		List<String> userIds = timeEntity.getUserIds().stream().map(Object::toString).collect(Collectors.toList());
		searchList.add(new CrmSearchBO.Search("ownerUserId", "text", CrmSearchBO.FieldSearchEnum.IS, userIds));
		searchList.add(new CrmSearchBO.Search("orderDate", "date", CrmSearchBO.FieldSearchEnum.RANGE, Arrays.asList(DateUtil.formatDate(timeEntity.getBeginDate()), DateUtil.formatDate(DateUtil.offsetDay(timeEntity.getEndDate(), 1)))));
		return queryPageList(searchBo);
	}
}
