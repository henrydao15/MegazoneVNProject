package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.megazone.core.common.*;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.constant.CrmActivityEnum;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmCustomerPoolVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmCustomerPoolMapper;
import com.megazone.crm.service.*;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * High sea table service implementation class
 * </p>
 *
 * @author
 * @since 2020-05-29
 */
@Service(value = "customerPoolService")
public class CrmCustomerPoolServiceImpl extends BaseServiceImpl<CrmCustomerPoolMapper, CrmCustomerPool> implements ICrmCustomerPoolService, CrmPageService {

	@Autowired
	private ICrmCustomerPoolRelationService customerPoolRelationService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminFileService adminFileService;

	@Autowired
	private ICrmActivityService crmActivityService;

	@Autowired
	private ICrmCustomerService crmCustomerService;

	@Autowired
	private ICrmCustomerPoolRuleService customerPoolRuleService;

	@Autowired
	private ICrmCustomerPoolFieldSortService crmCustomerPoolFieldSortService;

	@Autowired
	private ICrmCustomerPoolFieldSettingService customerPoolFieldSettingService;

	@Autowired
	private ICrmCustomerDataService crmCustomerDataService;

	@Autowired
	private ICrmActionRecordService crmActionRecordService;

	@Autowired
	private ICrmFieldService crmFieldService;

	@Autowired
	private ICrmBackLogDealService backLogDealService;

	@Autowired
	private FieldService fieldService;

	/**
	 * Query the list of high seas rules
	 *
	 * @param pageEntity entity
	 */
	@Override
	public BasePage<CrmCustomerPoolVO> queryPoolSettingList(PageEntity pageEntity) {
		BasePage<CrmCustomerPool> basePage = query().select("pool_id", "pool_name", "status", "admin_user_id", "member_user_id", "member_dept_id").page(pageEntity.parse());
		BasePage<CrmCustomerPoolVO> voBasePage = new BasePage<>(basePage.getCurrent(), basePage.getSize(), basePage.getTotal(), basePage.isSearchCount());
		basePage.getList().forEach(pool -> {
			Integer count = customerPoolRelationService.lambdaQuery().eq(CrmCustomerPoolRelation::getPoolId, pool.getPoolId()).count();
			CrmCustomerPoolVO customerPoolVO = new CrmCustomerPoolVO();
			customerPoolVO.setPoolId(pool.getPoolId());
			customerPoolVO.setCustomerNum(count);
			customerPoolVO.setPoolName(pool.getPoolName());
			customerPoolVO.setStatus(pool.getStatus());
			customerPoolVO.setAdminUser(UserCacheUtil.getSimpleUsers(StrUtil.splitTrim(pool.getAdminUserId(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList())));
			customerPoolVO.setMemberUser(UserCacheUtil.getSimpleUsers(StrUtil.splitTrim(pool.getMemberUserId(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList())));
			customerPoolVO.setMemberDept(adminService.queryDeptByIds(StrUtil.splitTrim(pool.getMemberDeptId(), Const.SEPARATOR).stream().map(Integer::valueOf).collect(Collectors.toList())).getData());
			voBasePage.getList().add(customerPoolVO);
		});
		return voBasePage;
	}

	/**
	 * Backstage high seas selection list
	 */
	@Override
	public List<CrmCustomerPool> queryPoolNameList() {
		return lambdaQuery().select(CrmCustomerPool::getPoolId, CrmCustomerPool::getPoolName).list();
	}

	/**
	 * Modify the state of the high seas
	 *
	 * @param poolId high sea ID
	 * @param status status
	 */
	@Override
	public void changeStatus(Integer poolId, Integer status) {
		Integer count = customerPoolRelationService.lambdaQuery().eq(CrmCustomerPoolRelation::getPoolId, poolId).count();
		if (count > 0 && status == 0) {
			throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_EXIST_USER_ERROR);
		}
		Integer poolNum = lambdaQuery().eq(CrmCustomerPool::getStatus, 1).count();
		CrmCustomerPool pool = getById(poolId);
		if (pool.getStatus().equals(1) && poolNum.equals(1) && status.equals(0)) {
			throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_LAST_ERROR);
		}
		pool.setStatus(status);
		updateById(pool);
	}

	/**
	 * @param prePoolId  original public sea ID
	 * @param postPoolId transfer public sea ID
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void transfer(Integer prePoolId, Integer postPoolId) {
		CrmCustomerPool pool = getById(postPoolId);
		if (pool == null) {
			return;
		}
		if (pool.getStatus() == 0) {
			throw new CrmException(CrmCodeEnum.CRM_POOL_TRANSFER_ERROR);
		}
		//Original high seas customer Ids
		List<Integer> oldLists = customerPoolRelationService.lambdaQuery()
				.eq(CrmCustomerPoolRelation::getPoolId, prePoolId)
				.list()
				.stream().map(CrmCustomerPoolRelation::getCustomerId)
				.collect(Collectors.toList());

		//Customer ids to put in
		List<Integer> newLists = customerPoolRelationService.lambdaQuery()
				.eq(CrmCustomerPoolRelation::getPoolId, postPoolId)
				.list()
				.stream().map(CrmCustomerPoolRelation::getCustomerId)
				.collect(Collectors.toList());
		if (oldLists.size() == 0) {
			return;
		}
		List<String> ids = new ArrayList<>();
		for (Integer id : oldLists) {
			ids.add(id.toString());
			if (ids.size() >= 1000) {
				transferPoolByEs(ids, prePoolId, postPoolId);
				ids = new ArrayList<>();
			}
		}
        /*
          Transfer the last remaining data and refresh the es index
         */
		transferPoolByEs(ids, prePoolId, postPoolId);
		getRestTemplate().refresh(CrmEnum.CUSTOMER.getIndex());

		oldLists.removeAll(newLists);
		List<CrmCustomerPoolRelation> poolRelationList = new ArrayList<>(oldLists.size());
		oldLists.forEach(id -> {
			CrmCustomerPoolRelation poolRelation = new CrmCustomerPoolRelation();
			poolRelation.setCustomerId(id);
			poolRelation.setPoolId(postPoolId);
			poolRelationList.add(poolRelation);
		});
		customerPoolRelationService.removeByMap(new JSONObject().fluentPut("pool_id", prePoolId).getInnerMapObject());
		customerPoolRelationService.saveBatch(poolRelationList);
		backLogDealService.removeByMap(new JSONObject().fluentPut("pool_id", prePoolId).getInnerMapObject());

	}

	/**
	 * es processing of high seas customer transfer
	 *
	 * @param ids        client id
	 * @param prePoolId  Pre-Pool ID
	 * @param postPoolId put the public sea ID
	 */
	@SuppressWarnings("unchecked")
	private void transferPoolByEs(List<String> ids, Integer prePoolId, Integer postPoolId) {
		if (ids.size() == 0) {
			return;
		}
		BulkRequest bulkRequest = new BulkRequest();
		SearchRequest searchRequest = new SearchRequest(getIndex());
		searchRequest.types(getDocType());
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		IdsQueryBuilder idsQuery = QueryBuilders.idsQuery();
		idsQuery.ids().addAll(ids);
		searchRequest.source(sourceBuilder.fetchSource(new String[]{"poolId"}, null).query(idsQuery));
		sourceBuilder.size(1000);
		try {
			SearchResponse searchResponse = getRestTemplate().getClient().search(searchRequest, RequestOptions.DEFAULT);
			for (SearchHit hit : searchResponse.getHits()) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				Object poolId = sourceAsMap.get("poolId");
				if (poolId instanceof Collection) {
					UpdateRequest request = new UpdateRequest(getIndex(), getDocType(), hit.getId());
					Set<Integer> set = new HashSet<>((Collection<Integer>) poolId);
					set.remove(prePoolId);
					set.add(postPoolId);
					sourceAsMap.put("poolId", set);
					request.doc(sourceAsMap);
					bulkRequest.add(request);
				}
			}
			if (bulkRequest.requests().size() > 0) {
				getRestTemplate().getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
			}
		} catch (IOException e) {
			throw new CrmException(SystemCodeEnum.SYSTEM_ERROR);
		}
	}

	/**
	 * Query high seas information based on ID
	 *
	 * @param poolId high sea ID
	 * @return data
	 */
	@Override
	public CrmCustomerPoolVO queryPoolById(Integer poolId) {
		CrmCustomerPool pool = getById(poolId);
		if (pool == null) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		Integer count = customerPoolRelationService.lambdaQuery().eq(CrmCustomerPoolRelation::getPoolId, pool.getPoolId()).count();
		CrmCustomerPoolVO customerPoolVO = BeanUtil.copyProperties(pool, CrmCustomerPoolVO.class);
		customerPoolVO.setPoolId(pool.getPoolId());
		customerPoolVO.setCustomerNum(count);
		customerPoolVO.setPoolName(pool.getPoolName());
		customerPoolVO.setStatus(pool.getStatus());
		customerPoolVO.setAdminUser(UserCacheUtil.getSimpleUsers(StrUtil.splitTrim(pool.getAdminUserId(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList())));
		customerPoolVO.setMemberUser(UserCacheUtil.getSimpleUsers(StrUtil.splitTrim(pool.getMemberUserId(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList())));
		customerPoolVO.setMemberDept(adminService.queryDeptByIds(StrUtil.splitTrim(pool.getMemberDeptId(), Const.SEPARATOR).stream().map(Integer::valueOf).collect(Collectors.toList())).getData());
		List<CrmCustomerPoolRule> ruleList = customerPoolRuleService.lambdaQuery().eq(CrmCustomerPoolRule::getPoolId, poolId).list();
		//Customer level settings are converted to the data structure required by the front end
		Map<Integer, List<CrmCustomerPoolRule>> map = ruleList.stream().collect(Collectors.groupingBy(CrmCustomerPoolRule::getType));
		List<CrmCustomerPoolRule> newRuleList = new ArrayList<>();
		map.forEach((k, v) -> {
			CrmCustomerPoolRule record = new CrmCustomerPoolRule();
			List<JSONObject> levelSettingList = new ArrayList<>();
			v.forEach(r -> {
				record.setRuleId(r.getRuleId());
				record.setPoolId(r.getPoolId());
				record.setType(k);
				record.setDealHandle(r.getDealHandle());
				record.setBusinessHandle(r.getBusinessHandle());
				record.setCustomerLevelSetting(r.getCustomerLevelSetting());
				levelSettingList.add(new JSONObject().fluentPut("level", r.getLevel()).fluentPut("limitDay", r.getLimitDay()));
			});
			record.setLevelSetting(levelSettingList);
			newRuleList.add(record);
		});
		List<CrmCustomerPoolFieldSetting> list = customerPoolFieldSettingService.lambdaQuery().eq(CrmCustomerPoolFieldSetting::getPoolId, poolId).list();
		customerPoolVO.setRule(newRuleList);
		customerPoolVO.setField(list);
		return customerPoolVO;
	}

	/**
	 * Query the default fields of high seas
	 */
	@Override
	public List<CrmModelFiledVO> queryPoolField() {
		List<CrmModelFiledVO> fieldList = crmFieldService.queryField(CrmEnum.CUSTOMER.getType());
		fieldList.removeIf(field -> FieldEnum.DESC_TEXT.getType().equals(field.getType()));
		List<Object> dealStatusList = new ArrayList<>();
		dealStatusList.add(new JSONObject().fluentPut("name", "Not dealt").fluentPut("value", 0));
		dealStatusList.add(new JSONObject().fluentPut("name", "Dealed").fluentPut("value", 1));
		fieldList.add(new CrmModelFiledVO("deal_status", FieldEnum.SELECT, "Deal Status", 1).setSetting(dealStatusList).setFormType("dealStatus"));
		fieldList.add(new CrmModelFiledVO().setFieldName("lastTime").setName("Last follow-up time").setType(13));
		fieldList.add(new CrmModelFiledVO().setFieldName("lastContent").setName("Last follow-up record").setType(2));
		fieldList.add(new CrmModelFiledVO().setFieldName("updateTime").setName("update time").setType(13));
		fieldList.add(new CrmModelFiledVO().setFieldName("createTime").setName("create time").setType(13));
		fieldList.add(new CrmModelFiledVO().setFieldName("createUserId").setName("Creator").setType(10));
		fieldList.add(new CrmModelFiledVO().setFieldName("detailAddress").setName("detailAddress").setType(1));
		fieldList.add(new CrmModelFiledVO().setFieldName("address").setName("regional positioning").setType(18));
		fieldList.add(new CrmModelFiledVO().setFieldName("preOwnerUserId").setName("Pre-OwnerUserId").setType(10));
		fieldList.add(new CrmModelFiledVO().setFieldName("poolTime").setName("Time to enter high seas").setType(13));
		fieldList.add(new CrmModelFiledVO().setFieldName("ownerDeptName").setName("Dept.").setType(1));
		fieldList.forEach(record -> {
			record.setFieldName(StrUtil.toCamelCase(record.getFieldName()));
			crmFieldService.recordToFormType(record, FieldEnum.parse(record.getType()));
		});
		return fieldList;
	}

	/**
	 * Delete customer data
	 *
	 * @param ids ids
	 */
	@Override
	public void deleteByIds(List<Integer> ids, Integer poolId) {
		CrmCustomerPool customerPool = getById(poolId);
		if (customerPool == null) {
			return;
		}
		boolean isPoolAdmin = StrUtil.splitTrim(customerPool.getAdminUserId(), Const.SEPARATOR).contains(UserUtil.getUserId().toString());
		if (!isPoolAdmin && !UserUtil.isAdmin()) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		crmCustomerService.lambdaUpdate().set(CrmCustomer::getUpdateTime, new Date()).set(CrmCustomer::getStatus, 3).in(CrmCustomer::getCustomerId, ids).update();
		LambdaQueryWrapper<CrmCustomer> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmCustomer::getCustomerId, ids);
		List<String> batchList = crmCustomerService.listObjs(wrapper, Object::toString);
		adminFileService.delete(batchList);
		// delete the follow-up record
		crmActivityService.deleteActivityRecord(CrmActivityEnum.CUSTOMER, ids);
		//delete field operation record
		crmActionRecordService.deleteActionRecord(CrmEnum.CUSTOMER, ids);
		// delete custom fields
		crmCustomerDataService.deleteByBatchId(batchList);
		ICrmBackLogDealService dealService = ApplicationContextHolder.getBean(ICrmBackLogDealService.class);
		// delete reminder
		dealService.lambdaUpdate().eq(CrmBackLogDeal::getPoolId, poolId).in(CrmBackLogDeal::getTypeId, ids).remove();
		//delete the high seas association
		customerPoolRelationService.lambdaUpdate().in(CrmCustomerPoolRelation::getCustomerId, ids).remove();
		//delete contact
		ICrmContactsService contactsService = ApplicationContextHolder.getBean(ICrmContactsService.class);
		List<CrmContacts> list = contactsService.lambdaQuery().select(CrmContacts::getContactsId).in(CrmContacts::getCustomerId, ids).list();
		contactsService.deleteByIds(list.stream().map(CrmContacts::getContactsId).collect(Collectors.toList()));
		deletePage(ids);

	}

	/**
	 * Get customer level options
	 *
	 * @return data
	 */
	@Override
	public List<String> queryCustomerLevel() {
		CrmField level = crmFieldService.lambdaQuery().eq(CrmField::getLabel, 2).eq(CrmField::getFieldName, "level").one();
		return StrUtil.splitTrim(level.getOptions(), Const.SEPARATOR);
	}

	/**
	 * Set high seas rules
	 *
	 * @param jsonObject obj
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setCustomerPool(JSONObject jsonObject) {
		CrmCustomerPool customerPool = JSONObject.toJavaObject(jsonObject, CrmCustomerPool.class);
		if (customerPool.getPoolId() == null) {
			if ((StrUtil.isEmpty(customerPool.getMemberUserId()) && StrUtil.isEmpty(customerPool.getMemberDeptId())) || StrUtil.isEmpty(customerPool.getAdminUserId())) {
				throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_USER_IS_NULL_ERROR);
			}
			customerPool.setCreateUserId(UserUtil.getUserId());
			customerPool.setCreateTime(DateUtil.date());
			save(customerPool);
		} else {
			updateById(customerPool);
			JSONObject object = new JSONObject().fluentPut("pool_id", customerPool.getPoolId());
			backLogDealService.removeByMap(object.getInnerMapObject());
			customerPoolRuleService.removeByMap(object.getInnerMapObject());
		}
		JSONArray ruleList = jsonObject.getJSONArray("rule");
		ruleList.node.forEach(object -> {
			JSONObject rule = new JSONObject((ObjectNode) object);
			JSONArray levelSettingList = rule.getJSONArray("level");
			levelSettingList.node.forEach(levelObject -> {
				CrmCustomerPoolRule crmCustomerPoolRule = JSON.parseObject(levelObject.toPrettyString(), CrmCustomerPoolRule.class);
				crmCustomerPoolRule.setPoolId(customerPool.getPoolId());
				crmCustomerPoolRule.setType(rule.getInteger("type"));
				crmCustomerPoolRule.setDealHandle(rule.getInteger("dealHandle"));
				crmCustomerPoolRule.setBusinessHandle(rule.getInteger("businessHandle"));
				crmCustomerPoolRule.setCustomerLevelSetting(rule.getInteger("customerLevelSetting"));
				customerPoolRuleService.save(crmCustomerPoolRule);
			});
		});
		List<CrmCustomerPoolFieldSetting> fieldList = jsonObject.getJSONArray("field").toJavaList(CrmCustomerPoolFieldSetting.class);
		List<CrmCustomerPoolFieldSetting> list = customerPoolFieldSettingService.lambdaQuery().eq(CrmCustomerPoolFieldSetting::getPoolId, customerPool.getPoolId()).list();
		List<String> collect = fieldList.stream().map(CrmCustomerPoolFieldSetting::getFieldName).collect(Collectors.toList());
		list.removeIf(obj -> collect.contains(obj.getFieldName()));
		if (CollUtil.isNotEmpty(list)) {
			customerPoolFieldSettingService.removeByIds(list.stream().map(CrmCustomerPoolFieldSetting::getSettingId).collect(Collectors.toList()));
		}
		for (CrmCustomerPoolFieldSetting field : fieldList) {
			field.setPoolId(customerPool.getPoolId());
			if (field.getSettingId() != null) {
				customerPoolFieldSettingService.updateById(field);
				ICrmCustomerPoolFieldSortService bean = ApplicationContextHolder.getBean(ICrmCustomerPoolFieldSortService.class);
				// Update the list page field sorting table synchronously
				String fieldName = field.getFieldName();
				if ("preOwnerUserId".equals(fieldName)) {
					fieldName = "preOwnerUserName";
				} else if ("createUserId".equals(fieldName)) {
					fieldName = "createUserName";
				}
				if (field.getIsHidden() == 1) {
					bean.removeByMap(new JSONObject().fluentPut("field_name", fieldName).fluentPut("pool_id", customerPool.getPoolId()).getInnerMapObject());
				} else {
					bean.lambdaUpdate()
							.set(CrmCustomerPoolFieldSort::getName, field.getName())
							.eq(CrmCustomerPoolFieldSort::getPoolId, customerPool.getPoolId())
							.eq(CrmCustomerPoolFieldSort::getFieldId, field.getFieldId())
							.update();
				}
			} else {
				customerPoolFieldSettingService.save(field);
			}
		}
	}

	/**
	 * Query open sea field configuration
	 *
	 * @param poolId high sea ID
	 * @return data
	 */
	@Override
	public JSONObject queryPoolFieldConfig(Integer poolId) {
		Long userId = UserUtil.getUserId();
		//Find out the custom field, check whether the field exists in the sequence table, if not, insert it and set it as hidden
		List<CrmCustomerPoolFieldSetting> fieldList = customerPoolFieldSettingService.lambdaQuery().eq(CrmCustomerPoolFieldSetting::getPoolId, poolId).eq(CrmCustomerPoolFieldSetting::getIsHidden, 0).list();
		List<String> list = crmCustomerPoolFieldSortService.lambdaQuery()
				.select(CrmCustomerPoolFieldSort::getFieldName)
				.eq(CrmCustomerPoolFieldSort::getUserId, userId)
				.eq(CrmCustomerPoolFieldSort::getPoolId, poolId)
				.list().stream().map(CrmCustomerPoolFieldSort::getFieldName).collect(Collectors.toList());
		fieldList.removeIf(setting -> list.contains(StrUtil.toCamelCase(setting.getFieldName())));
		for (CrmCustomerPoolFieldSetting record : fieldList) {
			CrmCustomerPoolFieldSort newField = new CrmCustomerPoolFieldSort();
			newField.setFieldName(StrUtil.toCamelCase(record.getFieldName())).setType(record.getType()).setName(record.getName()).setPoolId(poolId).setIsHidden(1).setUserId(userId).setSort(1);
			crmCustomerPoolFieldSortService.save(newField);
		}
		List<CrmCustomerPoolFieldSort> noHideList = crmCustomerPoolFieldSortService.lambdaQuery()
				.select(CrmCustomerPoolFieldSort::getId, CrmCustomerPoolFieldSort::getName, CrmCustomerPoolFieldSort::getFieldName)
				.eq(CrmCustomerPoolFieldSort::getPoolId, poolId)
				.eq(CrmCustomerPoolFieldSort::getUserId, userId)
				.eq(CrmCustomerPoolFieldSort::getIsHidden, 0)
				.orderByAsc(CrmCustomerPoolFieldSort::getSort)
				.list();
		List<CrmCustomerPoolFieldSort> hideList = crmCustomerPoolFieldSortService.lambdaQuery()
				.select(CrmCustomerPoolFieldSort::getId, CrmCustomerPoolFieldSort::getName, CrmCustomerPoolFieldSort::getFieldName)
				.eq(CrmCustomerPoolFieldSort::getPoolId, poolId)
				.eq(CrmCustomerPoolFieldSort::getUserId, userId)
				.eq(CrmCustomerPoolFieldSort::getIsHidden, 1)
				.orderByAsc(CrmCustomerPoolFieldSort::getSort)
				.list();
		return new JSONObject().fluentPut("value", noHideList).fluentPut("hide_value", hideList);
	}

	/**
	 * High sea display configuration
	 *
	 * @param object obj
	 */
	@Override
	public void poolFieldConfig(JSONObject object) {
		JSONArray hideFields = object.getJSONArray("hideFields");
		JSONArray noHideFields = object.getJSONArray("noHideFields");
		List<Integer> hideIds = new ArrayList<>(hideFields.size());
		List<Integer> noHideIds = new ArrayList<>(noHideFields.size());
		for (int i = 0; i < hideFields.size(); i++) {
			hideIds.add(hideFields.getJSONObject(i).getInteger("id"));
		}
		for (int i = 0; i < noHideFields.size(); i++) {
			noHideIds.add(noHideFields.getJSONObject(i).getInteger("id"));
		}
		if (noHideIds.size() < 2) {
			throw new CrmException(CrmCodeEnum.CRM_POOL_FIELD_HIDE_ERROR);
		}
		List<CrmCustomerPoolFieldSort> crmCustomerPoolFieldSorts = new ArrayList<>();
		for (int i = 0; i < noHideIds.size(); i++) {
			CrmCustomerPoolFieldSort sorts = new CrmCustomerPoolFieldSort();
			sorts.setId(noHideIds.get(i));
			sorts.setSort(i);
			sorts.setIsHidden(0);
			crmCustomerPoolFieldSorts.add(sorts);
		}
		if (CollUtil.isNotEmpty(hideIds)) {
			hideIds.forEach(sort -> crmCustomerPoolFieldSorts.add(new CrmCustomerPoolFieldSort().setId(sort).setIsHidden(1)));
		}
		crmCustomerPoolFieldSortService.updateBatchById(crmCustomerPoolFieldSorts);
	}

	/**
	 * Remove high seas
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteCustomerPool(Integer poolId) {
		Integer customerNum = customerPoolRelationService.lambdaQuery().eq(CrmCustomerPoolRelation::getPoolId, poolId).count();
		if (customerNum > 0) {
			throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_EXIST_USER_DELETE_ERROR);
		}
		Integer poolNum = lambdaQuery().eq(CrmCustomerPool::getStatus, 1).count();
		CrmCustomerPool pool = getById(poolId);
		if (pool.getStatus().equals(1) && poolNum.equals(1)) {
			throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_LAST_DELETE_ERROR);
		}
		removeById(poolId);
		JSONObject object = new JSONObject().fluentPut("pool_id", poolId);
		// delete to-do
		backLogDealService.removeByMap(object.getInnerMapObject());
		// delete high seas rules
		customerPoolRuleService.removeByMap(object.getInnerMapObject());
		// delete field settings
		customerPoolFieldSettingService.removeByMap(object.getInnerMapObject());
	}

	/**
	 * Query the list of high seas customers
	 *
	 * @param search
	 */
	@Override
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search, boolean isExcel) {
		search.setLabel(CrmEnum.CUSTOMER_POOL.getType());
		BasePage<Map<String, Object>> basePage = queryList(search, isExcel);
		basePage.getList().forEach(map -> {
			map.put("poolId", search.getPoolId());
		});
		return basePage;
	}

	/**
	 * Query the front sea list
	 */
	@Override
	public List<CrmCustomerPool> queryPoolNameListByAuth() {
		UserInfo user = UserUtil.getUser();
		List<CrmCustomerPool> list = lambdaQuery().select(CrmCustomerPool::getPoolId, CrmCustomerPool::getPoolName, CrmCustomerPool::getMemberUserId, CrmCustomerPool::getAdminUserId, CrmCustomerPool::getMemberDeptId)
				.eq(CrmCustomerPool::getStatus, 1)
				.orderByDesc(CrmCustomerPool::getCreateTime)
				.list();
		if (!UserUtil.isAdmin()) {
			list.removeIf(pool -> {
				boolean isAdmin = StrUtil.splitTrim(pool.getAdminUserId(), Const.SEPARATOR).contains(user.getUserId().toString());
				boolean isMember = StrUtil.splitTrim(pool.getMemberUserId(), Const.SEPARATOR).contains(user.getUserId().toString());
				boolean isDept = StrUtil.splitTrim(pool.getMemberDeptId(), Const.SEPARATOR).contains(user.getDeptId().toString());
				return !isAdmin && !isMember && !isDept;
			});
		}
		list.forEach(pool -> {
			pool.setMemberDeptId(null);
			pool.setMemberUserId(null);
			pool.setAdminUserId(null);
		});
		return list;
	}

	@Override
	public Boolean queryAuthListByPoolId(Integer poolId) {
		if (UserUtil.isAdmin()) {
			return true;
		}
		CrmCustomerPool pool = getById(poolId);
		boolean isAdmin = StrUtil.splitTrim(pool.getAdminUserId(), Const.SEPARATOR).contains(UserUtil.getUserId().toString());
		return isAdmin;
	}


	@Override
	public List<Integer> queryPoolIdByUserId() {
		return getBaseMapper().queryPoolIdByUserId(UserUtil.getUserId(), UserUtil.getUser().getDeptId());
	}

	/**
	 * Query the front sea field
	 */
	@Override
	public List<CrmCustomerPoolFieldSort> queryPoolListHead(Integer poolId) {
		Long userId = UserUtil.getUserId();
		LambdaQueryWrapper<CrmCustomerPoolFieldSort> wrapper = new LambdaQueryWrapper<>();
		wrapper.select(CrmCustomerPoolFieldSort::getId, CrmCustomerPoolFieldSort::getFieldId, CrmCustomerPoolFieldSort::getFieldName, CrmCustomerPoolFieldSort::getName, CrmCustomerPoolFieldSort::getType, CrmCustomerPoolFieldSort::getIsHidden);
		wrapper.eq(CrmCustomerPoolFieldSort::getPoolId, poolId).eq(CrmCustomerPoolFieldSort::getUserId, userId);
		wrapper.orderByAsc(CrmCustomerPoolFieldSort::getSort);
		List<CrmCustomerPoolFieldSort> list = crmCustomerPoolFieldSortService.list(wrapper);
		if (list.size() == 0) {
			List<CrmCustomerPoolFieldSetting> settings = customerPoolFieldSettingService.lambdaQuery().eq(CrmCustomerPoolFieldSetting::getPoolId, poolId).eq(CrmCustomerPoolFieldSetting::getIsHidden, 0).list();
			for (int i = 0; i < settings.size(); i++) {
				CrmCustomerPoolFieldSetting setting = settings.get(i);
				CrmCustomerPoolFieldSort sort = BeanUtil.copyProperties(setting, CrmCustomerPoolFieldSort.class);
				if ("preOwnerUserId".equals(setting.getFieldName())) {
					setting.setFieldName("preOwnerUserName");
				} else if ("createUserId".equals(setting.getFieldName())) {
					setting.setFieldName("createUserName");
				}
				sort.setFieldName(StrUtil.toCamelCase(setting.getFieldName()));
				sort.setUserId(userId).setSort(i).setIsHidden(0);
				sort.setFieldId(setting.getFieldId());
				list.add(sort);
			}
			crmCustomerPoolFieldSortService.saveBatch(list);
		}
		list.removeIf(fieldSort -> fieldService.equalsByType(fieldSort.getType(), FieldEnum.DESC_TEXT, FieldEnum.DETAIL_TABLE, FieldEnum.FILE));
		list.forEach(fieldSort -> {
			if ("website".equals(fieldSort.getFieldName())) {
				fieldSort.setFormType(FieldEnum.WEBSITE.getFormType());
			} else {
				fieldSort.setFormType(FieldEnum.parse(fieldSort.getType()).getFormType());
			}
		});
		ICrmCustomerPoolFieldStyleService styleService = ApplicationContextHolder.getBean(ICrmCustomerPoolFieldStyleService.class);
		list.removeIf(sort -> !sort.getIsHidden().equals(0));
		List<CrmCustomerPoolFieldStyle> fieldStyles = styleService.lambdaQuery().eq(CrmCustomerPoolFieldStyle::getPoolId, poolId).eq(CrmCustomerPoolFieldStyle::getUserId, userId).list();
		list.forEach(sort -> {
			for (CrmCustomerPoolFieldStyle fieldStyle : fieldStyles) {
				if (Objects.equals(sort.getFieldName(), fieldStyle.getFieldName())) {
					sort.setWidth(fieldStyle.getStyle());
					break;
				}
			}
			if (sort.getWidth() == null) {
				sort.setWidth(100);
			}
		});
		return list;
	}

	/**
	 * Query high seas permission
	 *
	 * @param poolId high sea ID
	 * @return auth
	 */
	@Override
	public JSONObject queryAuthByPoolId(Integer poolId) {
		LambdaQueryWrapper<CrmCustomerPool> wrapper = new LambdaQueryWrapper<>();
		wrapper.select(CrmCustomerPool::getAdminUserId);
		wrapper.eq(CrmCustomerPool::getPoolId, poolId);
		String adminUserIds = getOne(wrapper).getAdminUserId();
		List<Long> adminUserIdList = Arrays.stream(adminUserIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
		JSONObject record = new JSONObject().fluentPut("index", true).fluentPut("receive", true).fluentPut("delete", false).fluentPut("distribute", false).fluentPut("excelexport", false);
		if (adminUserIdList.contains(UserUtil.getUserId()) || UserUtil.isAdmin()) {
			record.fluentPut("delete", true).fluentPut("distribute", true);
		}
		if (queryAuthListByPoolId(poolId)) {
			record.fluentPut("excelexport", true);
		}
		return record;
	}


	@Override
	public JSONObject getOnePoolAuthByPoolIds(List<Integer> poolIdList) {
		JSONObject record = new JSONObject();
		if (CollUtil.isEmpty(poolIdList)) {
			return record;
		}
		if (!UserUtil.isAdmin()) {
			List<Integer> poolIds = this.queryPoolIdByUserId();
			if (CollUtil.isEmpty(poolIds)) {
				return record;
			}
			boolean b = poolIdList.stream().anyMatch(poolIds::contains);
			if (!b) {
				return record;
			}
		}
		LambdaQueryWrapper<CrmCustomerPool> wrapper = new LambdaQueryWrapper<>();
		wrapper.select(CrmCustomerPool::getAdminUserId, CrmCustomerPool::getPoolId);
		wrapper.in(CrmCustomerPool::getPoolId, poolIdList);
		List<CrmCustomerPool> crmCustomerPoolList = list(wrapper);
		if (CollUtil.isNotEmpty(crmCustomerPoolList)) {
			record.fluentPut("index", true).fluentPut("receive", true).fluentPut("delete", false)
					.fluentPut("distribute", false).fluentPut("excelexport", false);
			for (CrmCustomerPool crmCustomerPool : crmCustomerPoolList) {
				String adminUserIds = crmCustomerPool.getAdminUserId();
				List<Long> adminUserIdList = Arrays.stream(adminUserIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
				if (adminUserIdList.contains(UserUtil.getUserId()) || UserUtil.isAdmin()) {
					record.fluentPut("delete", true).fluentPut("distribute", true).fluentPut("excelexport", true);
					record.fluentPut("poolId", crmCustomerPool.getPoolId());
					return record;
				}
			}
			record.fluentPut("poolId", crmCustomerPoolList.get(0).getPoolId());
		}
		return record;
	}

	/**
	 * Search field with large search box
	 *
	 * @return fields
	 */
	@Override
	public String[] appendSearch() {
		return new String[]{"customerName", "telephone", "mobile"};
	}

	/**
	 * Set other redundant fields
	 *
	 * @param map
	 */
	@Override
	public void setOtherField(Map<String, Object> map) {

	}

	@Override
	public Dict getSearchTransferMap() {
		return Dict.create().set("createUserName", "createUserId").set("preOwnerUserName", "preOwnerUserId");
	}

	/**
	 * Get crm list type
	 *
	 * @return data
	 */
	@Override
	public CrmEnum getLabel() {
		return CrmEnum.CUSTOMER;
	}

	/**
	 * Query all fields
	 *
	 * @return data
	 */
	@Override
	public List<CrmModelFiledVO> queryDefaultField() {
		return ApplicationContextHolder.getBean(CrmCustomerServiceImpl.class).queryDefaultField();
	}
}
