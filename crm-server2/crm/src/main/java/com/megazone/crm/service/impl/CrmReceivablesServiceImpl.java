package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import com.megazone.core.feign.examine.entity.ExamineInfoVo;
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
import com.megazone.crm.common.ElasticUtil;
import com.megazone.crm.constant.*;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmReceivablesMapper;
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
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrmReceivablesServiceImpl extends BaseServiceImpl<CrmReceivablesMapper, CrmReceivables> implements ICrmReceivablesService, CrmPageService {

	@Autowired
	private ICrmFieldService crmFieldService;

	@Autowired
	private ICrmReceivablesDataService crmReceivablesDataService;

	@Autowired
	private ICrmContractService crmContractService;

	@Autowired
	private ICrmExamineRecordService examineRecordService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ICrmNumberSettingService crmNumberSettingService;

	@Autowired
	private ICrmActivityService crmActivityService;

	@Autowired
	private ICrmReceivablesPlanService crmReceivablesPlanService;

	@Autowired
	private ICrmActionRecordService crmActionRecordService;

	@Autowired
	private ICrmCustomerService crmCustomerService;

	@Autowired
	private ActionRecordUtil actionRecordUtil;

	@Autowired
	private AdminFileService adminFileService;

	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Autowired
	private ExamineService examineService;

	@Autowired
	private FieldService fieldService;


	/**
	 * @return fields
	 */
	@Override
	public String[] appendSearch() {
		return new String[]{"number", "customerName"};
	}

	@Override
	public CrmEnum getLabel() {
		return CrmEnum.RECEIVABLES;
	}

	/**
	 * @return data
	 */
	@Override
	public List<CrmModelFiledVO> queryDefaultField() {
		List<CrmModelFiledVO> filedList = crmFieldService.queryField(getLabel().getType());
		filedList.add(new CrmModelFiledVO("lastTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("updateTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("ownerUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("createUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("checkStatus", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("receivablesPlanId", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, 0));
		return filedList;
	}

	/**
	 * @param id ID
	 * @return data
	 */
	@Override
	public List<CrmModelFiledVO> queryField(Integer id) {
		CrmModel crmModel = queryById(id);
		if (id != null) {
			List<JSONObject> customerList = new ArrayList<>();
			JSONObject customer = new JSONObject();
			customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
			crmModel.put("customerId", customerList);
			crmModel.put("contractId", Collections.singletonList(new JSONObject().fluentPut("contractId", crmModel.get("contractId")).fluentPut("contractNum", crmModel.get("contractNum"))));
		}
		return crmFieldService.queryField(crmModel);
	}

	private List<CrmModelFiledVO> queryField(Integer id, boolean appendInformation) {
		CrmModel crmModel = queryById(id);
		if (id != null) {
			List<JSONObject> customerList = new ArrayList<>();
			JSONObject customer = new JSONObject();
			customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
			crmModel.put("customerId", customerList);
			crmModel.put("contractId", Collections.singletonList(new JSONObject().fluentPut("contractId", crmModel.get("contractId")).fluentPut("contractNum", crmModel.get("contractNum"))));
		}
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
		if (id != null) {
			List<JSONObject> customerList = new ArrayList<>();
			JSONObject customer = new JSONObject();
			customerList.add(customer.fluentPut("customerId", crmModel.get("customerId")).fluentPut("customerName", crmModel.get("customerName")));
			crmModel.put("customerId", customerList);
			crmModel.put("contractId", Collections.singletonList(new JSONObject().fluentPut("contractId", crmModel.get("contractId")).fluentPut("contractNum", crmModel.get("contractNum"))));
		}
		return crmFieldService.queryFormPositionFieldVO(crmModel);
	}

	/**
	 * @param search
	 * @return
	 */
	@Override
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search) {
		CrmSearchBO search1 = ObjectUtil.cloneByStream(search);
		BasePage<Map<String, Object>> basePage = queryList(search, false);
		SearchRequest searchRequest = new SearchRequest(getIndex());
		searchRequest.types(getDocType());
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder queryBuilder = createQueryBuilder(search1);
		queryBuilder.must(QueryBuilders.termQuery("checkStatus", 1));
		sourceBuilder.query(queryBuilder);
		sourceBuilder.aggregation(AggregationBuilders.sum("receivablesMoney").field("money"));
		searchRequest.source(sourceBuilder);
		try {
			SearchResponse searchCount = elasticsearchRestTemplate.getClient().search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregations = searchCount.getAggregations();
			Map<String, Object> countMap = new HashMap<>();
			ParsedSum receivablesMoney = aggregations.get("receivablesMoney");
			countMap.put("receivablesMoney", receivablesMoney.getValue());
			basePage.setExtraData(new JSONObject().fluentPut("money", countMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return basePage;
	}

	@Override
	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		List<CrmReceivables> list = lambdaQuery().select(CrmReceivables::getReceivablesId, CrmReceivables::getNumber).in(CrmReceivables::getReceivablesId, ids).list();
		return list.stream().map(crmLeads -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmLeads.getReceivablesId());
			simpleCrmEntity.setName(crmLeads.getNumber());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
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
			crmModel.setLabel(CrmEnum.RECEIVABLES.getType());
			crmModel.setOwnerUserName(UserCacheUtil.getUserName(crmModel.getOwnerUserId()));
			crmModel.put("createUserName", UserCacheUtil.getUserName((Long) crmModel.get("createUserId")));
			crmReceivablesDataService.setDataByBatchId(crmModel);
			List<String> stringList = ApplicationContextHolder.getBean(ICrmRoleFieldService.class).queryNoAuthField(crmModel.getLabel());
			stringList.forEach(crmModel::remove);
		} else {
			crmModel = new CrmModel(CrmEnum.RECEIVABLES.getType());
		}
		return crmModel;
	}

	/**
	 * @param crmModel model
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdate(CrmContractSaveBO crmModel) {
		CrmReceivables crmReceivables = BeanUtil.copyProperties(crmModel.getEntity(), CrmReceivables.class);
		CrmContract crmContract = crmContractService.getById(crmReceivables.getContractId());
		if (crmContract == null || !Arrays.asList(1, 10).contains(crmContract.getCheckStatus())) {
			throw new CrmException(CrmCodeEnum.CRM_RECEIVABLES_ADD_ERROR);
		}
		crmReceivables.setCreateUserId(UserUtil.getUserId());
		String batchId = StrUtil.isNotEmpty(crmReceivables.getBatchId()) ? crmReceivables.getBatchId() : IdUtil.simpleUUID();
		actionRecordUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_business_data"));
		crmReceivablesDataService.saveData(crmModel.getField(), batchId);
		ExamineRecordSaveBO examineRecordSaveBO = crmModel.getExamineFlowData();
		ExamineRecordReturnVO examineData = null;
		if (crmReceivables.getReceivablesId() == null) {
			List<AdminConfig> configList = adminService.queryConfigByName("numberSetting").getData();
			AdminConfig adminConfig = configList.stream().filter(config -> Objects.equals(getLabel().getType().toString(), config.getValue())).collect(Collectors.toList()).get(0);
			if (adminConfig.getStatus() == 1 && StrUtil.isEmpty(crmReceivables.getNumber())) {
				String result = crmNumberSettingService.generateNumber(adminConfig, null);
				crmReceivables.setNumber(result);
			}
			Integer count = lambdaQuery().eq(CrmReceivables::getNumber, crmReceivables.getNumber()).ne(CrmReceivables::getCheckStatus, 7).count();
			if (count != null && count > 0) {
				throw new CrmException(CrmCodeEnum.CRM_RECEIVABLES_NUM_ERROR);
			}
			crmReceivables.setCreateTime(DateUtil.date());
			crmReceivables.setUpdateTime(DateUtil.date());
			crmReceivables.setBatchId(batchId);
			crmReceivables.setOwnerUserId(UserUtil.getUserId());
			save(crmReceivables);
			if (crmReceivables.getCheckStatus() == null || crmReceivables.getCheckStatus() != 5) {
				this.supplementFieldInfo(2, crmReceivables.getReceivablesId(), null, examineRecordSaveBO);
				examineRecordSaveBO.setTitle(crmReceivables.getNumber());
				examineData = examineService.addExamineRecord(examineRecordSaveBO).getData();
				crmReceivables.setExamineRecordId(examineData.getRecordId());
				crmReceivables.setCheckStatus(examineData.getExamineStatus());
			}
			updateById(crmReceivables);
			CrmReceivablesPlan crmReceivablesPlan = crmReceivablesPlanService.getById(crmReceivables.getReceivablesPlanId());
			if (crmReceivablesPlan != null) {
				crmReceivablesPlan.setReceivablesId(crmReceivables.getReceivablesId());
				crmReceivablesPlan.setUpdateTime(DateUtil.date());
				ElasticUtil.updateField(getRestTemplate(), "receivablesId", crmReceivables.getReceivablesId(), Collections.singletonList(crmReceivablesPlan.getReceivablesPlanId()), CrmEnum.RECEIVABLES_PLAN.getIndex());
				crmReceivablesPlanService.updateById(crmReceivablesPlan);
			}
			crmActivityService.addActivity(2, CrmActivityEnum.RECEIVABLES, crmReceivables.getReceivablesId());
			actionRecordUtil.addRecord(crmReceivables.getReceivablesId(), CrmEnum.RECEIVABLES, crmReceivables.getNumber());
			crmReceivables = getById(crmReceivables.getReceivablesId());
			if (crmReceivables.getCheckStatus() == 1 || crmReceivables.getCheckStatus() == 10) {
				examineRecordService.updateContractMoney(crmReceivables.getReceivablesId());
				crmReceivablesPlanService.updateReceivedStatus(CrmEnum.RECEIVABLES, crmReceivables, crmReceivables.getCheckStatus());
			}
		} else {
			CrmReceivables receivables = getById(crmReceivables.getReceivablesId());
			if (receivables.getCheckStatus() == 1) {
				throw new CrmException(CrmCodeEnum.CRM_RECEIVABLES_EXAMINE_PASS_ERROR);
			}
			if (!Arrays.asList(2, 4, 5, 10).contains(receivables.getCheckStatus())) {
				throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EDIT_ERROR);
			}
			if (crmReceivables.getCheckStatus() == null || crmReceivables.getCheckStatus() != 5) {
				this.supplementFieldInfo(2, receivables.getReceivablesId(), receivables.getExamineRecordId(), examineRecordSaveBO);
				examineRecordSaveBO.setTitle(crmReceivables.getNumber());
				examineData = examineService.addExamineRecord(examineRecordSaveBO).getData();
				crmReceivables.setExamineRecordId(examineData.getRecordId());
				crmReceivables.setCheckStatus(examineData.getExamineStatus());
			}
			crmReceivables.setUpdateTime(DateUtil.date());
			crmReceivablesPlanService.update().eq("receivables_id", receivables.getReceivablesId()).set("receivables_id", null).update();
			CrmReceivablesPlan crmReceivablesPlan = crmReceivablesPlanService.getById(crmReceivables.getReceivablesPlanId());
			if (crmReceivablesPlan != null) {
				crmReceivablesPlan.setReceivablesId(crmReceivables.getReceivablesId());
				crmReceivablesPlan.setUpdateTime(DateUtil.date());
				ElasticUtil.updateField(getRestTemplate(), "receivablesId", crmReceivables.getReceivablesId(), Collections.singletonList(crmReceivablesPlan.getReceivablesPlanId()), CrmEnum.RECEIVABLES_PLAN.getIndex());
				crmReceivablesPlanService.updateById(crmReceivablesPlan);
			}
			ApplicationContextHolder.getBean(ICrmBackLogDealService.class).deleteByTypes(null, CrmEnum.RECEIVABLES, crmReceivables.getReceivablesId(), CrmBackLogEnum.CHECK_RECEIVABLES);
			actionRecordUtil.updateRecord(BeanUtil.beanToMap(receivables), BeanUtil.beanToMap(crmReceivables), CrmEnum.CONTRACT, crmContract.getName(), crmContract.getContractId());
			updateById(crmReceivables);
			crmReceivables = getById(crmReceivables.getReceivablesId());
			if (crmReceivables.getCheckStatus() == 10) {
				examineRecordService.updateContractMoney(crmReceivables.getReceivablesId());
				crmReceivablesPlanService.updateReceivedStatus(CrmEnum.RECEIVABLES, crmReceivables, crmReceivables.getCheckStatus());
			}
		}
		crmModel.setEntity(BeanUtil.beanToMap(crmReceivables));
		savePage(crmModel, crmReceivables.getReceivablesId(), false);
	}

	@Override
	public void setOtherField(Map<String, Object> map) {
		String customerName = crmCustomerService.getCustomerName((Integer) map.get("customerId"));
		map.put("customerName", customerName);
		CrmContract contract = crmContractService.getById((Serializable) map.get("contractId"));
		if (contract != null) {
			map.put("contractNum", contract.getNum());
			map.put("contractMoney", contract.getMoney());
		}
		String ownerUserName = UserCacheUtil.getUserName((Long) map.get("ownerUserId"));
		map.put("ownerUserName", ownerUserName);
		String createUserName = UserCacheUtil.getUserName((Long) map.get("createUserId"));
		map.put("createUserName", createUserName);
		Integer receivablesPlanId = (Integer) map.get("receivablesPlanId");
		if (receivablesPlanId != null) {
			CrmReceivablesPlan receivablesPlan = crmReceivablesPlanService.lambdaQuery().select(CrmReceivablesPlan::getNum).eq(CrmReceivablesPlan::getReceivablesPlanId, receivablesPlanId).one();
			map.put("planNum", receivablesPlan.getNum());
		}
	}

	@Override
	public Dict getSearchTransferMap() {
		return Dict.create()
				.set("customerId", "customerName").set("contractId", "contractNum").set("receivablesPlanId", "planNum");
	}

	/**
	 * @param ids ids
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByIds(List<Integer> ids) {
		Integer count = crmReceivablesPlanService.lambdaQuery().in(CrmReceivablesPlan::getReceivablesId, ids).count();
		if (count != 0) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_JOIN_ERROR);
		}
		for (Integer id : ids) {
			CrmReceivables receivables = getById(id);
			boolean bol = (receivables.getCheckStatus() != 4 && receivables.getCheckStatus() != 5) && !UserUtil.isAdmin();
			if (bol) {
				throw new CrmException(CrmCodeEnum.CAN_ONLY_DELETE_WITHDRAWN_AND_SUBMITTED_EXAMINE);
			}

			crmActivityService.deleteActivityRecord(CrmActivityEnum.RECEIVABLES, Collections.singletonList(id));

			crmActionRecordService.deleteActionRecord(CrmEnum.RECEIVABLES, Collections.singletonList(receivables.getReceivablesId()));

			crmReceivablesDataService.deleteByBatchId(Collections.singletonList(receivables.getBatchId()));

			adminFileService.delete(Collections.singletonList(receivables.getBatchId()));
			if (ObjectUtil.isNotEmpty(receivables.getExamineRecordId())) {
				examineService.deleteExamineRecord(receivables.getExamineRecordId());
			}
			receivables.setCheckStatus(7);
			updateById(receivables);

			examineRecordService.updateContractMoney(id);
		}
		deletePage(ids);
	}

	/**
	 * @param changeOwnerUserBO BO
	 */
	public void changeOwnerUser(CrmChangeOwnerUserBO changeOwnerUserBO) {
		LambdaUpdateWrapper<CrmReceivables> wrapper = new LambdaUpdateWrapper<>();
		Long newOwnerUserId = changeOwnerUserBO.getOwnerUserId();
		List<Integer> ids = changeOwnerUserBO.getIds();
		wrapper.in(CrmReceivables::getReceivablesId, ids);
		wrapper.set(CrmReceivables::getOwnerUserId, newOwnerUserId);
		update(wrapper);
		for (Integer id : ids) {
			if (AuthUtil.isChangeOwnerUserAuth(id, CrmEnum.RECEIVABLES, CrmAuthEnum.EDIT)) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
			CrmReceivables receivables = getById(id);
			actionRecordUtil.addConversionRecord(id, CrmEnum.RECEIVABLES, newOwnerUserId, receivables.getNumber());
			if (2 == changeOwnerUserBO.getTransferType() && !changeOwnerUserBO.getOwnerUserId().equals(receivables.getOwnerUserId())) {
				ApplicationContextHolder.getBean(ICrmTeamMembersService.class).addSingleMember(getLabel(), receivables.getReceivablesId(), receivables.getOwnerUserId(), changeOwnerUserBO.getPower(), changeOwnerUserBO.getExpiresTime(), receivables.getNumber());
			}
			ApplicationContextHolder.getBean(ICrmTeamMembersService.class).deleteMember(getLabel(), new CrmMemberSaveBO(id, newOwnerUserId));
		}

		String ownerUserName = UserCacheUtil.getUserName(newOwnerUserId);
		Map<String, Object> map = new HashMap<>();
		map.put("ownerUserId", newOwnerUserId);
		map.put("ownerUserName", ownerUserName);
		updateField(map, ids);
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
				return "Cash back";
			}
		}, headList, response);
	}

	/**
	 * @param id id
	 * @return data
	 */
	@Override
	public CrmInfoNumVO num(Integer id) {
		List<String> batchIdList = new ArrayList<>();
		CrmReceivables crmReceivables = getById(id);
		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		List<CrmField> crmFields = crmFieldService.queryFileField();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmReceivablesData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmReceivablesData::getValue);
			wrapper.eq(CrmReceivablesData::getBatchId, crmReceivables.getBatchId());
			wrapper.in(CrmReceivablesData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			batchIdList.addAll(crmReceivablesDataService.listObjs(wrapper, Object::toString));
		}
		CrmInfoNumVO infoNumVO = new CrmInfoNumVO();
		infoNumVO.setFileCount(fileService.queryNum(batchIdList).getData());
		infoNumVO.setMemberCount(ApplicationContextHolder.getBean(ICrmTeamMembersService.class).queryMemberCount(getLabel(), crmReceivables.getReceivablesId(), crmReceivables.getOwnerUserId()));
		return infoNumVO;
	}

	/**
	 * Query file list
	 *
	 * @param id id
	 * @return file
	 */
	@Override
	public List<FileEntity> queryFileList(Integer id) {
		List<FileEntity> fileEntityList = new ArrayList<>();
		CrmReceivables crmReceivables = getById(id);
		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		fileService.queryFileList(crmReceivables.getBatchId()).getData().forEach(fileEntity -> {
			fileEntity.setSource("Attachment upload");
			fileEntity.setReadOnly(0);
			fileEntityList.add(fileEntity);
		});
		List<CrmField> crmFields = crmFieldService.queryFileField();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmReceivablesData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmReceivablesData::getValue);
			wrapper.eq(CrmReceivablesData::getBatchId, crmReceivables.getBatchId());
			wrapper.in(CrmReceivablesData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			List<FileEntity> data = fileService.queryFileList(crmReceivablesDataService.listObjs(wrapper, Object::toString)).getData();
			data.forEach(fileEntity -> {
				fileEntity.setSource("Collection details");
				fileEntity.setReadOnly(1);
				fileEntityList.add(fileEntity);
			});
		}
		return fileEntityList;
	}

	@Override
	public List<CrmModelFiledVO> information(Integer contractId) {
		return queryField(contractId, true);
	}


	@Override
	public void updateInformation(CrmUpdateInformationBO updateInformationBO) {
		String batchId = updateInformationBO.getBatchId();
		Integer receivablesId = updateInformationBO.getId();
		List<ExamineInfoVo> infoVos = examineService.queryNormalExamine(2).getData();
		CrmReceivables receivables = getById(receivablesId);
		if (!receivables.getCreateUserId().equals(UserUtil.getUserId()) && CollUtil.isNotEmpty(infoVos)) {
			throw new CrmException(CrmCodeEnum.CRM_RECEIVABLES_EDIT_ERROR);
		}
		if (receivables.getCheckStatus() == 1) {
			throw new CrmException(CrmCodeEnum.CRM_RECEIVABLES_EXAMINE_PASS_ERROR);
		}
		if (!Arrays.asList(2, 4, 5, 10).contains(receivables.getCheckStatus())) {
			throw new CrmException(CrmCodeEnum.CRM_CONTRACT_EDIT_ERROR);
		}
		updateInformationBO.getList().forEach(record -> {
			CrmReceivables oldReceivables = getById(updateInformationBO.getId());
			uniqueFieldIsAbnormal(record.getString("name"), record.getInteger("fieldId"), record.getString("value"), batchId);
			Map<String, Object> oldReceivablesMap = BeanUtil.beanToMap(oldReceivables);
			if (record.getInteger("fieldType") == 1) {
				Map<String, Object> crmReceivablesMap = new HashMap<>(oldReceivablesMap);
				crmReceivablesMap.put(record.getString("fieldName"), record.get("value"));
				CrmReceivables crmReceivables = BeanUtil.mapToBean(crmReceivablesMap, CrmReceivables.class, true);
				actionRecordUtil.updateRecord(oldReceivablesMap, crmReceivablesMap, CrmEnum.RECEIVABLES, crmReceivables.getNumber(), crmReceivables.getReceivablesId());
				update().set(StrUtil.toUnderlineCase(record.getString("fieldName")), record.get("value")).eq("receivables_id", updateInformationBO.getId()).update();
			} else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
				CrmReceivablesData receivablesData = crmReceivablesDataService.lambdaQuery().select(CrmReceivablesData::getValue, CrmReceivablesData::getId).eq(CrmReceivablesData::getFieldId, record.getInteger("fieldId"))
						.eq(CrmReceivablesData::getBatchId, batchId).one();
				String value = receivablesData != null ? receivablesData.getValue() : null;
				actionRecordUtil.publicContentRecord(CrmEnum.RECEIVABLES, BehaviorEnum.UPDATE, receivablesId, oldReceivables.getNumber(), record, value);
				String newValue = fieldService.convertObjectValueToString(record.getInteger("type"), record.get("value"), record.getString("value"));
				CrmReceivablesData crmReceivabelsData = new CrmReceivablesData();
				crmReceivabelsData.setId(receivablesData != null ? receivablesData.getId() : null);
				crmReceivabelsData.setFieldId(record.getInteger("fieldId"));
				crmReceivabelsData.setName(record.getString("fieldName"));
				crmReceivabelsData.setValue(newValue);
				crmReceivabelsData.setCreateTime(new Date());
				crmReceivabelsData.setBatchId(batchId);
				crmReceivablesDataService.saveOrUpdate(crmReceivabelsData);

			}
			updateField(record, receivablesId);
		});
		this.lambdaUpdate().set(CrmReceivables::getUpdateTime, new Date()).eq(CrmReceivables::getReceivablesId, receivablesId).update();
	}

	@Override
	public BasePage<JSONObject> queryListByContractId(BasePage<JSONObject> page, Integer contractId, String conditions) {
		BasePage<JSONObject> jsonObjects = getBaseMapper().queryListByContractId(page, contractId, conditions);
		for (JSONObject jsonObject : jsonObjects.getList()) {
			Long ownerUserId = jsonObject.getLong("ownerUserId");
			String ownerUserName = UserCacheUtil.getUserName(ownerUserId);
			jsonObject.put("ownerUserName", ownerUserName);
		}
		return jsonObjects;
	}
}
