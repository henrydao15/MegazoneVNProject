package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
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
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
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
import com.megazone.crm.constant.CrmActivityEnum;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmListBusinessStatusVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmBusinessMapper;
import com.megazone.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
@Service
@Slf4j
public class CrmBusinessServiceImpl extends BaseServiceImpl<CrmBusinessMapper, CrmBusiness> implements CrmPageService, ICrmBusinessService {

	@Autowired
	private ICrmFieldService crmFieldService;

	@Autowired
	private ICrmBusinessDataService crmBusinessDataService;

	@Autowired
	private ICrmActivityService crmActivityService;

	@Autowired
	private ICrmBusinessProductService crmBusinessProductService;

	@Autowired
	private ICrmContactsBusinessService crmContactsBusinessService;

	@Autowired
	private ICrmContractService crmContractService;

	@Autowired
	private ICrmActionRecordService crmActionRecordService;

	@Autowired
	private ICrmBusinessUserStarService crmBusinessUserStarService;

	@Autowired
	private ICrmCustomerService crmCustomerService;

	@Autowired
	private ActionRecordUtil actionRecordUtil;

	@Autowired
	private AdminFileService adminFileService;

	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Autowired
	private FieldService fieldService;
	@Autowired
	private ICrmBusinessTypeService businessTypeService;

	/**
	 * @return fields
	 */
	@Override
	public String[] appendSearch() {
		return new String[]{"businessName"};
	}

	/**
	 * @return data
	 */
	@Override
	public CrmEnum getLabel() {
		return CrmEnum.BUSINESS;
	}

	/**
	 * @return data
	 */
	@Override
	public List<CrmModelFiledVO> queryDefaultField() {
		List<CrmModelFiledVO> filedList = crmFieldService.queryField(getLabel().getType());
		filedList.add(new CrmModelFiledVO("typeId", FieldEnum.SELECT, 1));
		filedList.add(new CrmModelFiledVO("statusId", FieldEnum.SELECT, 1));
		filedList.add(new CrmModelFiledVO("lastTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("updateTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("receiveTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("nextTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("ownerUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("createUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("status", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, 0));
		return filedList;
	}

	/**
	 * @param id ID
	 * @return data
	 */
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
		}
		List<CrmModelFiledVO> crmModelFiledVOS = crmFieldService.queryField(crmModel);
		Optional<CrmModelFiledVO> optional = crmModelFiledVOS.stream().filter(record -> "remark".equals(record.getFieldName())).findFirst();
		optional.ifPresent(crmModelFiledVOS::remove);
		crmModelFiledVOS.add(new CrmModelFiledVO().setFieldName("type_id").setName("Opportunity Status Group").setValue(crmModel.get("type_id")).setFormType("business_type").setSetting(new ArrayList<>()).setIsNull(1).setFieldType(1).setValue(crmModel.get("typeId")).setAuthLevel(3));
		Object statusId = crmModel.get("statusId");
		if (!Objects.equals(0, crmModel.get("isEnd"))) {
			statusId = crmModel.get("isEnd");
		}
		crmModelFiledVOS.add(new CrmModelFiledVO().setFieldName("status_id").setName("Opportunity Stage").setValue(crmModel.get("status_id")).setFormType("business_status").setSetting(new ArrayList<>()).setIsNull(1).setFieldType(1).setValue(statusId).setAuthLevel(3));
		optional.ifPresent(crmModelFiledVOS::add);
		JSONObject object = new JSONObject();
		object.fluentPut("discountRate", crmModel.get("discountRate")).fluentPut("product", crmBusinessProductService.queryList(id)).fluentPut("totalPrice", crmModel.get("totalPrice"));
		crmModelFiledVOS.add(new CrmModelFiledVO().setFieldName("product").setName("product").setValue(object).setFormType("product").setSetting(new ArrayList<>()).setIsNull(0).setFieldType(1));
		if (appendInformation) {
			List<CrmModelFiledVO> modelFiledVOS = appendInformation(crmModel);
			crmModelFiledVOS.addAll(modelFiledVOS);
		}
		return crmModelFiledVOS;
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
		}
		List<List<CrmModelFiledVO>> crmModelFiledVOS = crmFieldService.queryFormPositionFieldVO(crmModel);
       /* Optional<CrmModelFiledVO> optional = Optional.empty();
        for (List<CrmModelFiledVO> crmModelFiledVOList : crmModelFiledVOS) {
            optional = crmModelFiledVOList.stream().filter(record -> "remark".equals(record.getFieldName())).findFirst();
        }
        if (optional.isPresent()){
            for (List<CrmModelFiledVO> crmModelFiledVOList : crmModelFiledVOS) {
                Optional<CrmModelFiledVO> finalOptional = optional;
                crmModelFiledVOList.removeIf(record -> Objects.equals(finalOptional.get().getFieldId(),record.getFieldId()));
            }
        }*/
		CrmModelFiledVO modelFiledVO = new CrmModelFiledVO().setFieldName("type_id").setName("Opportunity Status Group").setValue(crmModel.get("type_id")).setFormType("business_type").setSetting(new ArrayList<>()).setIsNull(1).setFieldType(1).setValue(crmModel.get("typeId")).setAuthLevel(3);
		Object statusId = crmModel.get("statusId");
		if (!Objects.equals(0, crmModel.get("isEnd"))) {
			statusId = crmModel.get("isEnd");
		}
		CrmModelFiledVO crmModelFiledVO = new CrmModelFiledVO().setFieldName("status_id").setName("Opportunity Stage").setValue(crmModel.get("status_id")).setFormType("business_status").setSetting(new ArrayList<>()).setIsNull(1).setFieldType(1).setValue(statusId).setAuthLevel(3);
		//default two groups
		modelFiledVO.setStylePercent(50);
		crmModelFiledVO.setStylePercent(50);
		crmModelFiledVOS.add(ListUtil.toList(modelFiledVO, crmModelFiledVO));

		JSONObject object = new JSONObject();
		object.fluentPut("discountRate", crmModel.get("discountRate")).fluentPut("product", crmBusinessProductService.queryList(id)).fluentPut("totalPrice", crmModel.get("totalPrice"));
		CrmModelFiledVO filedVO = new CrmModelFiledVO().setFieldName("product").setName("product").setValue(object).setFormType("product").setSetting(new ArrayList<>()).setIsNull(0).setFieldType(1);
       /* optional.ifPresent(vo -> {
            vo.setStylePercent(100);
            crmModelFiledVOS.add(ListUtil.toList(vo));
        });*/
		filedVO.setStylePercent(100);
		crmModelFiledVOS.add(ListUtil.toList(filedVO));
		return crmModelFiledVOS;
	}

	/**
	 * Paging query
	 *
	 * @param search
	 * @return
	 */
	@Override
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search) {
		CrmSearchBO search1 = ObjectUtil.cloneByStream(search);
		BasePage<Map<String, Object>> basePage = queryList(search, false);
		Long userId = UserUtil.getUserId();
		List<Integer> starIds = crmBusinessUserStarService.starList(userId);
		basePage.getList().forEach(map -> {
			map.put("star", starIds.contains((Integer) map.get("businessId")) ? 1 : 0);
			Integer isEnd = Integer.valueOf(map.get("isEnd").toString());
			CrmListBusinessStatusVO crmListBusinessStatusVO = businessTypeService.queryListBusinessStatus(Integer.parseInt(map.get("typeId").toString()), Integer.parseInt(map.get("statusId").toString()), isEnd);
			map.put("businessStatusCount", crmListBusinessStatusVO);
			if (Objects.equals(1, isEnd)) {
				map.put("statusName", "win order");
			} else if (Objects.equals(2, isEnd)) {
				map.put("statusName", "lose order");
			} else if (Objects.equals(3, isEnd)) {
				map.put("statusName", "invalid");
			}
		});
		SearchRequest searchRequest = new SearchRequest(getIndex());
		searchRequest.types(getDocType());
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder queryBuilder = createQueryBuilder(search1);
		log.info("Look at the search conditions for business opportunities" + search1.toString());
		sourceBuilder.query(queryBuilder);
		sourceBuilder.aggregation(AggregationBuilders.sum("businessSumMoney").field("money"));
		searchRequest.source(sourceBuilder);
		try {
			SearchResponse searchCount = elasticsearchRestTemplate.getClient().search(searchRequest, RequestOptions.DEFAULT);
			Aggregations aggregations = searchCount.getAggregations();
			Map<String, Object> countMap = new HashMap<>();
			ParsedSum businessSumMoney = aggregations.get("businessSumMoney");
			countMap.put("businessSumMoney", businessSumMoney.getValue());
			basePage.setExtraData(new JSONObject().fluentPut("money", countMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return basePage;
	}

	/**
	 * Query field configuration
	 *
	 * @param id primary key ID
	 * @return data
	 */
	@Override
	public CrmModel queryById(Integer id) {
		CrmModel crmModel;
		if (id != null) {
			crmModel = getBaseMapper().queryById(id, UserUtil.getUserId());
			crmModel.setLabel(CrmEnum.BUSINESS.getType());
			crmModel.setOwnerUserName(UserCacheUtil.getUserName(crmModel.getOwnerUserId()));
			crmModel.put("createUserName", UserCacheUtil.getUserName((Long) crmModel.get("createUserId")));
			crmBusinessDataService.setDataByBatchId(crmModel);
			List<String> stringList = ApplicationContextHolder.getBean(ICrmRoleFieldService.class).queryNoAuthField(crmModel.getLabel());
			stringList.forEach(crmModel::remove);
		} else {
			crmModel = new CrmModel(CrmEnum.BUSINESS.getType());
		}
		return crmModel;
	}


	/**
	 * Save or add information
	 *
	 * @param crmModel model
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addOrUpdate(CrmBusinessSaveBO crmModel) {
		CrmBusiness crmBusiness = BeanUtil.copyProperties(crmModel.getEntity(), CrmBusiness.class);
		List<CrmBusinessProduct> businessProductList = crmModel.getProduct();
		String batchId = StrUtil.isNotEmpty(crmBusiness.getBatchId()) ? crmBusiness.getBatchId() : IdUtil.simpleUUID();
		actionRecordUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_business_data"));
		crmBusinessDataService.saveData(crmModel.getField(), batchId);
		if (crmBusiness.getBusinessId() != null) {
			crmBusiness.setUpdateTime(DateUtil.date());
			CrmBusiness oldBusiness = getById(crmBusiness.getBusinessId());
			if (Objects.equals(crmBusiness.getTypeId(), oldBusiness.getTypeId())) {
               /*
                Do not modify the opportunity stage
                */
				crmBusiness.setStatusId(null);
			}
			actionRecordUtil.updateRecord(BeanUtil.beanToMap(oldBusiness), BeanUtil.beanToMap(crmBusiness), CrmEnum.BUSINESS, crmBusiness.getBusinessName(), crmBusiness.getBusinessId());
			updateById(crmBusiness);
			crmBusinessProductService.deleteByBusinessId(crmBusiness.getBusinessId());
			crmBusiness = getById(crmBusiness.getBusinessId());
			ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "business", crmBusiness.getBusinessId().toString(), crmBusiness.getBusinessName());
		} else {
			crmBusiness.setCreateTime(DateUtil.date());
			crmBusiness.setUpdateTime(DateUtil.date());
			crmBusiness.setCreateUserId(UserUtil.getUserId());
			crmBusiness.setOwnerUserId(UserUtil.getUserId());
			crmBusiness.setIsEnd(0);
			crmBusiness.setBatchId(batchId);
			save(crmBusiness);
			if (crmModel.getContactsId() != null) {
				crmContactsBusinessService.save(crmBusiness.getBusinessId(), crmModel.getContactsId());
			}
			crmActivityService.addActivity(2, CrmActivityEnum.BUSINESS, crmBusiness.getBusinessId());
			actionRecordUtil.addRecord(crmBusiness.getBusinessId(), CrmEnum.BUSINESS, crmBusiness.getBusinessName());
		}
		for (CrmBusinessProduct crmBusinessProduct : businessProductList) {
			crmBusinessProduct.setBusinessId(crmBusiness.getBusinessId());
		}
		crmBusinessProductService.save(businessProductList);
		crmModel.setEntity(BeanUtil.beanToMap(crmBusiness));
		savePage(crmModel, crmBusiness.getBusinessId(), false);
	}

	@Override
	public void setOtherField(Map<String, Object> map) {
		String ownerUserName = UserCacheUtil.getUserName((Long) map.get("ownerUserId"));
		map.put("ownerUserName", ownerUserName);
		String createUserName = UserCacheUtil.getUserName((Long) map.get("createUserId"));
		map.put("createUserName", createUserName);
		String customerName = crmCustomerService.getCustomerName((Integer) map.get("customerId"));
		map.put("customerName", customerName);
		ICrmBusinessStatusService businessStatusService = ApplicationContextHolder.getBean(ICrmBusinessStatusService.class);
		ICrmBusinessTypeService businessTypeService = ApplicationContextHolder.getBean(ICrmBusinessTypeService.class);
		Integer typeId = (Integer) map.get("typeId");
		Integer statusId = (Integer) map.get("statusId");
		CrmBusinessType crmBusinessType = businessTypeService.query().select("name").eq("type_id", typeId).one();
		map.put("typeName", crmBusinessType != null ? crmBusinessType.getName() : "");
		CrmBusinessStatus crmBusinessStatus = businessStatusService.query().select("name").eq("status_id", statusId).one();
		map.put("statusName", crmBusinessStatus != null ? crmBusinessStatus.getName() : "");
	}

	@Override
	public Dict getSearchTransferMap() {
		return Dict.create().set("customerId", "customerName").set("customer_id", "customerId");
	}

	/**
	 * Delete opportunity data
	 *
	 * @param ids ids
	 */
	@Override
	public void deleteByIds(List<Integer> ids) {
		ids.forEach(id -> {
			if (AuthUtil.isRwAuth(id, CrmEnum.BUSINESS, CrmAuthEnum.DELETE)) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
		});
		int number = crmContractService.lambdaQuery().in(CrmContract::getBusinessId, ids).ne(CrmContract::getCheckStatus, 7).count();
		if (number > 0) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_JOIN_ERROR);
		}
		LambdaQueryWrapper<CrmBusiness> wrapper = new LambdaQueryWrapper<>();
		wrapper.select(CrmBusiness::getBatchId);
		wrapper.in(CrmBusiness::getBusinessId, ids);
		List<String> batchIdList = listObjs(wrapper, Object::toString);

		// delete the follow-up record
		crmActivityService.deleteActivityRecord(CrmActivityEnum.BUSINESS, ids);
		//delete field operation record
		crmActionRecordService.deleteActionRecord(CrmEnum.BUSINESS, ids);
		//todo file related will not be dealt with temporarily
		//Delete opportunity and contact association
		crmContactsBusinessService.removeByBusinessId(ids.toArray(new Integer[0]));
		//delete the opportunity product association
		crmBusinessProductService.deleteByBusinessId(ids.toArray(new Integer[0]));
		// delete custom fields
		crmBusinessDataService.deleteByBatchId(batchIdList);
		//Modify the opportunity status
		LambdaUpdateWrapper<CrmBusiness> updateWrapper = new LambdaUpdateWrapper<>();
		updateWrapper.set(CrmBusiness::getStatus, 3);
		updateWrapper.in(CrmBusiness::getBusinessId, ids);
		update(updateWrapper);
		deletePage(ids);

	}

	@Override
	public BasePage<CrmContacts> queryContacts(CrmContactsPageBO pageEntity) {
		BasePage<CrmContacts> contactsBasePage = pageEntity.parse();
		return getBaseMapper().queryContacts(contactsBasePage, pageEntity.getBusinessId());
	}

	/**
	 * Query details
	 *
	 * @param businessId business opportunity id
	 * @return data
	 */
	@Override
	public List<CrmModelFiledVO> information(Integer businessId) {
		return queryField(businessId, true);
	}

	/**
	 * Modify the person in charge of the opportunity
	 *
	 * @param changOwnerUserBO data
	 */
	@Override
	public void changeOwnerUser(CrmChangeOwnerUserBO changOwnerUserBO) {
		if (changOwnerUserBO.getIds().size() == 0) {
			return;
		}
		String ownerUserName = UserCacheUtil.getUserName(changOwnerUserBO.getOwnerUserId());
		changOwnerUserBO.getIds().forEach(id -> {
			if (AuthUtil.isChangeOwnerUserAuth(id, CrmEnum.BUSINESS, CrmAuthEnum.EDIT)) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
			CrmBusiness business = getById(id);
			if (Objects.equals(2, changOwnerUserBO.getTransferType()) && !Objects.equals(business.getOwnerUserId(), changOwnerUserBO.getOwnerUserId())) {
				ApplicationContextHolder.getBean(ICrmTeamMembersService.class).addSingleMember(getLabel(), business.getBusinessId(), business.getOwnerUserId(), changOwnerUserBO.getPower(), changOwnerUserBO.getExpiresTime(), business.getBusinessName());
			}
			ApplicationContextHolder.getBean(ICrmTeamMembersService.class).deleteMember(getLabel(), new CrmMemberSaveBO(id, changOwnerUserBO.getOwnerUserId()));
			business.setOwnerUserId(changOwnerUserBO.getOwnerUserId());
			updateById(business);
			actionRecordUtil.addConversionRecord(id, CrmEnum.BUSINESS, changOwnerUserBO.getOwnerUserId(), business.getBusinessName());
		});
		//modify es
		Map<String, Object> map = new HashMap<>();
		map.put("ownerUserId", changOwnerUserBO.getOwnerUserId());
		map.put("ownerUserName", ownerUserName);
		updateField(map, changOwnerUserBO.getIds());
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
			}

			@Override
			public String getExcelName() {
				return "Opportunity";
			}
		}, headList, response);
	}

	/**
	 * star
	 *
	 * @param businessId business opportunity id
	 */
	@Override
	public void star(Integer businessId) {
		LambdaQueryWrapper<CrmBusinessUserStar> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CrmBusinessUserStar::getBusinessId, businessId);
		wrapper.eq(CrmBusinessUserStar::getUserId, UserUtil.getUserId());
		CrmBusinessUserStar star = crmBusinessUserStarService.getOne(wrapper);
		if (star == null) {
			star = new CrmBusinessUserStar();
			star.setBusinessId(businessId);
			star.setUserId(UserUtil.getUserId());
			crmBusinessUserStarService.save(star);
		} else {
			crmBusinessUserStarService.removeById(star.getId());
		}
	}

	/**
	 * Query the number of files
	 *
	 * @param businessId id
	 * @return data
	 */
	@Override
	public CrmInfoNumVO num(Integer businessId) {
		CrmBusiness crmBusiness = getById(businessId);
		String conditions = AuthUtil.getCrmAuthSql(CrmEnum.CONTRACT, 1, CrmAuthEnum.READ);
		String contactsConditions = AuthUtil.getCrmAuthSql(CrmEnum.CONTACTS, "b", 1, CrmAuthEnum.READ);
		Map<String, Object> map = new HashMap<>();
		map.put("businessId", businessId);
		map.put("conditions", conditions);
		map.put("contactsConditions", contactsConditions);
		CrmInfoNumVO infoNumVO = getBaseMapper().queryNum(map);
		List<CrmField> crmFields = crmFieldService.queryFileField();
		List<String> batchIdList = new ArrayList<>();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmBusinessData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmBusinessData::getValue);
			wrapper.eq(CrmBusinessData::getBatchId, crmBusiness.getBatchId());
			wrapper.in(CrmBusinessData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			batchIdList.addAll(crmBusinessDataService.listObjs(wrapper, Object::toString));
		}
		batchIdList.add(crmBusiness.getBatchId());
		batchIdList.addAll(crmActivityService.queryFileBatchId(crmBusiness.getBusinessId(), getLabel().getType()));
		infoNumVO.setFileCount(adminFileService.queryNum(batchIdList).getData());
		infoNumVO.setMemberCount(ApplicationContextHolder.getBean(ICrmTeamMembersService.class).queryMemberCount(getLabel(), crmBusiness.getBusinessId(), crmBusiness.getOwnerUserId()));
		Integer productCount = crmBusinessProductService.lambdaQuery().eq(CrmBusinessProduct::getBusinessId, businessId).count();
		infoNumVO.setProductCount(productCount);
		return infoNumVO;
	}

	/**
	 * Query file list
	 *
	 * @param businessId id
	 * @return file
	 */
	@Override
	public List<FileEntity> queryFileList(Integer businessId) {
		List<FileEntity> fileEntityList = new ArrayList<>();
		CrmBusiness crmBusiness = getById(businessId);
		adminFileService.queryFileList(crmBusiness.getBatchId()).getData().forEach(fileEntity -> {
			fileEntity.setSource("Attachment upload");
			fileEntity.setReadOnly(0);
			fileEntityList.add(fileEntity);
		});
		List<CrmField> crmFields = crmFieldService.queryFileField();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmBusinessData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmBusinessData::getValue);
			wrapper.eq(CrmBusinessData::getBatchId, crmBusiness.getBatchId());
			wrapper.in(CrmBusinessData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			List<FileEntity> data = adminFileService.queryFileList(crmBusinessDataService.listObjs(wrapper, Object::toString)).getData();
			data.forEach(fileEntity -> {
				fileEntity.setSource("Opportunity Details");
				fileEntity.setReadOnly(1);
				fileEntityList.add(fileEntity);
			});
		}
		List<String> stringList = crmActivityService.queryFileBatchId(crmBusiness.getBusinessId(), getLabel().getType());
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

	/**
	 * Set primary contact
	 *
	 * @param contactsBO data
	 */
	@Override
	public void setContacts(CrmFirstContactsBO contactsBO) {
		CrmBusiness crmBusiness = getById(contactsBO.getBusinessId());
		crmBusiness.setContactsId(contactsBO.getContactsId());
		updateById(crmBusiness);
	}

	/**
	 * Opportunity association contacts
	 *
	 * @param relevanceBusinessBO business object
	 */
	@Override
	public void relateContacts(CrmRelevanceBusinessBO relevanceBusinessBO) {
		relevanceBusinessBO.getContactsIds().forEach(id -> {
			crmContactsBusinessService.save(relevanceBusinessBO.getBusinessId(), id);
		});
	}

	/**
	 * Opportunity to disassociate contacts
	 *
	 * @param relevanceBusinessBO business object
	 */
	@Override
	public void unrelateContacts(CrmRelevanceBusinessBO relevanceBusinessBO) {
		CrmBusiness crmBusiness = getById(relevanceBusinessBO.getBusinessId());
		relevanceBusinessBO.getContactsIds().forEach(r -> {
			if (Objects.equals(r, crmBusiness.getContactsId())) {
				lambdaUpdate().set(CrmBusiness::getContactsId, null).eq(CrmBusiness::getBusinessId, crmBusiness.getBusinessId()).update();
			}
		});
		LambdaQueryWrapper<CrmContactsBusiness> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CrmContactsBusiness::getBusinessId, relevanceBusinessBO.getBusinessId());
		wrapper.in(CrmContactsBusiness::getContactsId, relevanceBusinessBO.getContactsIds());
		crmContactsBusinessService.remove(wrapper);
	}

	@Override
	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		List<CrmBusiness> list = lambdaQuery().select(CrmBusiness::getBusinessId, CrmBusiness::getBusinessName).in(CrmBusiness::getBusinessId, ids).list();
		return list.stream().map(crmBusiness -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmBusiness.getBusinessId());
			simpleCrmEntity.setName(crmBusiness.getBusinessName());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
	}

	@Override
	public String getBusinessName(int businessId) {
		Optional<CrmBusiness> crmBusiness = lambdaQuery().select(CrmBusiness::getBusinessName).eq(CrmBusiness::getBusinessId, businessId).oneOpt();
		return crmBusiness.map(CrmBusiness::getBusinessName).orElse("");
	}


	@Override
	public void updateInformation(CrmUpdateInformationBO updateInformationBO) {
		String batchId = updateInformationBO.getBatchId();
		Integer businessId = updateInformationBO.getId();
		updateInformationBO.getList().forEach(record -> {
			CrmBusiness oldBusiness = getById(updateInformationBO.getId());
			uniqueFieldIsAbnormal(record.getString("name"), record.getInteger("fieldId"), record.getString("value"), batchId);
			Map<String, Object> oldBusinessMap = BeanUtil.beanToMap(oldBusiness);
			if (record.getInteger("fieldType") == 1) {
				Map<String, Object> crmBusinessMap = new HashMap<>(oldBusinessMap);
				crmBusinessMap.put(record.getString("fieldName"), record.get("value"));
				CrmBusiness crmBusiness = BeanUtil.mapToBean(crmBusinessMap, CrmBusiness.class, true);
				actionRecordUtil.updateRecord(oldBusinessMap, crmBusinessMap, CrmEnum.BUSINESS, crmBusiness.getBusinessName(), crmBusiness.getBusinessId());
				update().set(StrUtil.toUnderlineCase(record.getString("fieldName")), record.get("value")).eq("business_id", updateInformationBO.getId()).update();
				if ("businessName".equals(record.getString("fieldName"))) {
					ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "business", crmBusiness.getBusinessId().toString(), crmBusiness.getBusinessName());
				}
			} else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {

				CrmBusinessData businessData = crmBusinessDataService.lambdaQuery().select(CrmBusinessData::getValue, CrmBusinessData::getId).eq(CrmBusinessData::getFieldId, record.getInteger("fieldId"))
						.eq(CrmBusinessData::getBatchId, batchId).one();
				String value = businessData != null ? businessData.getValue() : null;
				actionRecordUtil.publicContentRecord(CrmEnum.BUSINESS, BehaviorEnum.UPDATE, businessId, oldBusiness.getBusinessName(), record, value);
				String newValue = fieldService.convertObjectValueToString(record.getInteger("type"), record.get("value"), record.getString("value"));

				CrmBusinessData crmBusinessData = new CrmBusinessData();
				crmBusinessData.setId(businessData != null ? businessData.getId() : null);
				crmBusinessData.setFieldId(record.getInteger("fieldId"));
				crmBusinessData.setName(record.getString("fieldName"));
				crmBusinessData.setValue(newValue);
				crmBusinessData.setCreateTime(new Date());
				crmBusinessData.setBatchId(batchId);
				crmBusinessDataService.saveOrUpdate(crmBusinessData);

			}
			updateField(record, businessId);
		});
		this.lambdaUpdate().set(CrmBusiness::getUpdateTime, new Date()).eq(CrmBusiness::getBusinessId, businessId).update();
	}

	@Override
	public JSONObject queryProduct(CrmBusinessQueryRelationBO businessQueryProductBO) {
		Integer businessId = businessQueryProductBO.getBusinessId();
		CrmBusiness business = getById(businessId);
		JSONObject record = getBaseMapper().querySubtotalByBusinessId(businessId);
		record.put("money", business.getTotalPrice());
		BasePage<JSONObject> page = getBaseMapper().queryProduct(businessQueryProductBO.parse(), businessId);
		record.put("list", page.getList());
		return record;
	}

	@Override
	public BasePage<JSONObject> queryContract(CrmBusinessQueryRelationBO businessQueryRelationBO) {
		String conditions = AuthUtil.getCrmAuthSql(CrmEnum.BUSINESS, "a", 1, CrmAuthEnum.READ);
		Integer businessId = businessQueryRelationBO.getBusinessId();
		BasePage<JSONObject> page = getBaseMapper().queryContract(businessQueryRelationBO.parse(), businessId, conditions);
		return page;
	}

	@Override
	public List<String> eventDealBusiness(CrmEventBO crmEventBO) {
		return getBaseMapper().eventDealBusiness(crmEventBO);
	}

	@Override
	public BasePage<Map<String, Object>> eventDealBusinessPageList(QueryEventCrmPageBO eventCrmPageBO) {
		Long userId = eventCrmPageBO.getUserId();
		Long time = eventCrmPageBO.getTime();
		if (userId == null) {
			userId = UserUtil.getUserId();
		}
		List<Integer> businessIds = getBaseMapper().eventDealBusinessPageList(userId, new Date(time));
		if (businessIds.size() == 0) {
			return new BasePage<>();
		}
		List<String> collect = businessIds.stream().map(Object::toString).collect(Collectors.toList());
		CrmSearchBO crmSearchBO = new CrmSearchBO();
		crmSearchBO.setSearchList(Collections.singletonList(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, collect)));
		crmSearchBO.setLabel(CrmEnum.CUSTOMER.getType());
		crmSearchBO.setPage(eventCrmPageBO.getPage());
		crmSearchBO.setLimit(eventCrmPageBO.getLimit());
		BasePage<Map<String, Object>> page = queryPageList(crmSearchBO);
		return page;
	}

	@Override
	public List<String> eventBusiness(CrmEventBO crmEventBO) {
		return getBaseMapper().eventBusiness(crmEventBO);
	}

	@Override
	public BasePage<Map<String, Object>> eventBusinessPageList(QueryEventCrmPageBO eventCrmPageBO) {
		Long userId = eventCrmPageBO.getUserId();
		Long time = eventCrmPageBO.getTime();
		if (userId == null) {
			userId = UserUtil.getUserId();
		}
		List<Integer> businessIds = getBaseMapper().eventBusinessPageList(userId, new Date(time));
		if (businessIds.size() == 0) {
			return new BasePage<>();
		}
		List<String> collect = businessIds.stream().map(Object::toString).collect(Collectors.toList());
		CrmSearchBO crmSearchBO = new CrmSearchBO();
		crmSearchBO.setSearchList(Collections.singletonList(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, collect)));
		crmSearchBO.setLabel(CrmEnum.CUSTOMER.getType());
		crmSearchBO.setPage(eventCrmPageBO.getPage());
		crmSearchBO.setLimit(eventCrmPageBO.getLimit());
		BasePage<Map<String, Object>> page = queryPageList(crmSearchBO);
		return page;
	}
}
