package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.common.cache.CrmCacheKey;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.field.FieldService;
import com.megazone.core.redis.Redis;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.*;
import com.megazone.crm.constant.*;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.*;
import com.megazone.crm.mapper.CrmCustomerMapper;
import com.megazone.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "customerService")
@Slf4j
public class CrmCustomerServiceImpl extends BaseServiceImpl<CrmCustomerMapper, CrmCustomer> implements ICrmCustomerService, CrmPageService {

	@Autowired
	private ICrmFieldService crmFieldService;

	@Autowired
	private ICrmCustomerDataService crmCustomerDataService;

	@Autowired
	private ICrmActivityService crmActivityService;

	@Autowired
	private ICrmActionRecordService crmActionRecordService;

	@Autowired
	private ICrmBackLogDealService crmBackLogDealService;

	@Autowired
	private ICrmCustomerSettingService crmCustomerSettingService;

	@Autowired
	private ICrmBusinessService crmBusinessService;

	@Autowired
	private ICrmContactsService crmContactsService;

	@Autowired
	private ICrmContractService crmContractService;

	@Autowired
	private ICrmCustomerUserStarService crmCustomerUserStarService;

	@Autowired
	private ActionRecordUtil actionRecordUtil;


	@Autowired
	private ElasticsearchRestTemplate elasticsearchRestTemplate;

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminFileService adminFileService;

	@Autowired
	private ICrmCustomerPoolService crmCustomerPoolService;

	@Autowired
	private ICrmCustomerPoolRelationService customerPoolRelationService;

	@Autowired
	private FieldService fieldService;
	@Autowired
	private ICrmBusinessTypeService businessTypeService;

	/**
	 * Calculate the customer's time to enter the high seas
	 *
	 * @param startTime
	 * @param date
	 * @param limitDay
	 * @param levelSetting
	 * @param map
	 * @return void
	 * @date 2020/10/26 10:02
	 **/
	private static void setPoolDayForCustomer(Date startTime, Date date, Integer limitDay, Integer levelSetting, Map<String, Object> map) {
		if (startTime == null) {
			return;
		}
		long betweenDay = DateUtil.betweenDay(startTime, date, true);
		Integer poolDay = limitDay - (int) betweenDay;
		Integer customerPoolDay = (Integer) map.get("poolDay");
		if (customerPoolDay != null) {
			poolDay = poolDay < customerPoolDay ? poolDay : customerPoolDay;
		}
		poolDay = poolDay > 0 ? poolDay : 0;
		if (Objects.equals(levelSetting, 1)) {
			//all clients
			map.put("poolDay", poolDay);
		} else if (Objects.equals(levelSetting, 2)) {
			//customer level
			map.put("poolDay", poolDay);
		}
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
		CrmModel crmModel = queryById(id, null);
		List<CrmModelFiledVO> vos = crmFieldService.queryField(crmModel);
		JSONObject value = new JSONObject();
		value.put("location", crmModel.get("location"));
		value.put("address", crmModel.get("address"));
		value.put("detailAddress", crmModel.get("detailAddress"));
		value.put("lng", crmModel.get("lng"));
		value.put("lat", crmModel.get("lat"));
		vos.add(new CrmModelFiledVO("map_address", FieldEnum.MAP_ADDRESS, "Location", 1).setIsNull(0).setValue(value));
		if (appendInformation) {
			List<CrmModelFiledVO> modelFiledVOS = appendInformation(crmModel);
			if (crmModel.get("preOwnerUserId") != null) {
				CrmModelFiledVO filedVO = new CrmModelFiledVO("preOwnerUserName", FieldEnum.SINGLE_USER, "Pre-Owner", 1);
				List<SimpleUser> data = UserCacheUtil.getSimpleUsers(Collections.singleton((Long) crmModel.get("preOwnerUserId")));
				filedVO.setValue(data.get(0));
				modelFiledVOS.add(filedVO.setSysInformation(1));
			}
			String ownerDeptName = UserCacheUtil.getDeptName(UserCacheUtil.getUserInfo((Long) crmModel.get("createUserId")).getDeptId());
			modelFiledVOS.add(new CrmModelFiledVO("receive_time", FieldEnum.DATETIME, "The person in charge gets the customer time", 1).setValue(crmModel.get("receiveTime")).setSysInformation(1));
			modelFiledVOS.add(new CrmModelFiledVO("ownerDeptName", FieldEnum.TEXT, "Department", 1).setValue(ownerDeptName).setSysInformation(1));
			vos.addAll(modelFiledVOS);
		}
		return vos;
	}

	@Override
	public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id) {
		CrmModel crmModel = queryById(id, null);
		List<List<CrmModelFiledVO>> vos = crmFieldService.queryFormPositionFieldVO(crmModel);
		JSONObject value = new JSONObject();
		value.put("location", crmModel.get("location"));
		value.put("address", crmModel.get("address"));
		value.put("detailAddress", crmModel.get("detailAddress"));
		value.put("lng", crmModel.get("lng"));
		value.put("lat", crmModel.get("lat"));
		CrmModelFiledVO crmModelFiledVO = new CrmModelFiledVO("map_address", FieldEnum.MAP_ADDRESS, "Location", 1).setIsNull(0).setValue(value);
		crmModelFiledVO.setStylePercent(100);
		vos.add(ListUtil.toList(crmModelFiledVO));
		return vos;
	}

	/**
	 * Query all data when exporting
	 *
	 * @param search business query object
	 * @return data
	 */
	@Override
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search) {
		BasePage<Map<String, Object>> basePage = queryList(search, false);
		Long userId = UserUtil.getUserId();
		List<Integer> starIds = crmCustomerUserStarService.starList(userId);
		basePage.getList().forEach(map -> {
			Integer customerId = (Integer) map.get("customerId");
			map.put("star", starIds.contains((customerId)) ? 1 : 0);
			Integer businessCount = crmBusinessService.lambdaQuery().eq(CrmBusiness::getCustomerId, customerId).eq(CrmBusiness::getStatus, 1).count();
			map.put("businessCount", businessCount);
			// Query contacts, new contract association needs
			Object contactsId = map.get("contactsId");
			if (ObjectUtil.isNotEmpty(contactsId)) {
				CrmContacts contacts = crmContactsService.lambdaQuery().select(CrmContacts::getName, CrmContacts::getMobile, CrmContacts::getAddress)
						.eq(CrmContacts::getContactsId, contactsId).one();
				if (contacts != null) {
					map.put("contactsName", contacts.getName());
					map.put("contactsMobile", contacts.getMobile());
					map.put("contactsAddress", contacts.getAddress());
				}
			}
		});
		setPoolDay(basePage.getList());
		return basePage;
	}

	/**
	 * Set the time before entering the high seas for customers
	 *
	 * @param list
	 * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
	 * @date 2020/10/26 10:16
	 **/
	private void setPoolDay(List<Map<String, Object>> list) {
		Date date = new Date();
		List<CrmCustomerPool> poolList = crmCustomerPoolService.lambdaQuery().eq(CrmCustomerPool::getStatus, 1).eq(CrmCustomerPool::getPutInRule, 1).eq(CrmCustomerPool::getRemindSetting, 1).list();
		poolList.forEach(pool -> {
			List<Long> userIdsList = new ArrayList<>();
			List<Integer> deptIds = StrUtil.splitTrim(pool.getMemberDeptId(), Const.SEPARATOR).stream().map(Integer::valueOf).collect(Collectors.toList());
			if (deptIds.size() > 0) {
				userIdsList.addAll(adminService.queryUserByDeptIds(deptIds).getData());
			}
			if (StrUtil.isNotEmpty(pool.getMemberUserId())) {
				userIdsList.addAll(Arrays.stream(pool.getMemberUserId().split(Const.SEPARATOR)).map(Long::parseLong).collect(Collectors.toList()));
			}
			List<CrmCustomerPoolRule> ruleList = ApplicationContextHolder.getBean(ICrmCustomerPoolRuleService.class).lambdaQuery().eq(CrmCustomerPoolRule::getPoolId, pool.getPoolId()).list();
			for (CrmCustomerPoolRule rule : ruleList) {
				//Whether the completed customer enters the high seas 0 does not enter 1 enters
				Integer dealHandle = rule.getDealHandle();
				//Whether there is a business opportunity for the customer to enter the high seas 0 do not enter 1 enter
				Integer businessHandle = rule.getBusinessHandle();
				Integer limitDay = rule.getLimitDay();
				//Customer level settings 1 all 2 are set according to the level
				Integer levelSetting = rule.getCustomerLevelSetting();
				String level = rule.getLevel();
				for (Map<String, Object> map : list) {
					//Deal status 0 not dealt 1 dealt
					Integer dealStatus = (Integer) map.get("dealStatus");
					//Number of business opportunities
					Integer businessCount = (Integer) map.get("businessCount");
					Long ownerUserId = Long.valueOf(map.get("ownerUserId").toString());
					String customerLevel = map.get("level").toString();
					// Judge the person in charge
					if (!userIdsList.contains(ownerUserId)) {
						continue;
					}
					//Deal status
					if (Objects.equals(dealHandle, 0) && Objects.equals(dealStatus, 1)) {
						continue;
					}
					//Number of business opportunities
					if (Objects.equals(businessHandle, 0) && businessCount > 0) {
						continue;
					}
					//customer level
					if (Objects.equals(levelSetting, 2) && !Objects.equals(level, customerLevel)) {
						continue;
					}
					Date receiveTime = map.get("receiveTime") != null ? DateUtil.parse((String) map.get("receiveTime")) : null;
					if (rule.getType().equals(1)) {
						// follow up time
						Date lastTime = DateUtil.parse((String) map.get("lastTime"));
						if (lastTime == null) {
							lastTime = DateUtil.parse((String) map.get("createTime"));
						}
						if (receiveTime != null) {
							lastTime = lastTime.getTime() > receiveTime.getTime() ? lastTime : receiveTime;
						}
						setPoolDayForCustomer(lastTime, date, limitDay, levelSetting, map);
					}
					if (rule.getType().equals(2)) {
						setPoolDayForCustomer(receiveTime, date, limitDay, levelSetting, map);
					}
					if (rule.getType().equals(3)) {
						setPoolDayForCustomer(receiveTime, date, limitDay, levelSetting, map);
					}
				}
			}
		});
	}

	/**
	 * Query field configuration
	 *
	 * @param id primary key ID
	 * @return data
	 */
	@Override
	public CrmModel queryById(Integer id, Integer poolId) {
		CrmModel crmModel;
		if (id != null) {
			crmModel = getBaseMapper().queryById(id, UserUtil.getUserId());
			crmModel.setLabel(CrmEnum.CUSTOMER.getType());
			crmModel.setOwnerUserName(UserCacheUtil.getUserName(crmModel.getOwnerUserId()));
			crmCustomerDataService.setDataByBatchId(crmModel);
			List<String> stringList = ApplicationContextHolder.getBean(ICrmRoleFieldService.class).queryNoAuthField(crmModel.getLabel());
			stringList.forEach(crmModel::remove);
			if (ObjectUtil.isNotEmpty(poolId)) {
				LambdaQueryWrapper<CrmCustomerPoolFieldSetting> wrapper = new LambdaQueryWrapper<>();
				wrapper.select(CrmCustomerPoolFieldSetting::getFieldName);
				wrapper.eq(CrmCustomerPoolFieldSetting::getPoolId, poolId).eq(CrmCustomerPoolFieldSetting::getIsHidden, 1);
				List<String> nameList = ApplicationContextHolder.getBean(ICrmCustomerPoolFieldSettingService.class).listObjs(wrapper, Object::toString);
				nameList.forEach(crmModel::remove);
				JSONObject poolAuthList = crmCustomerPoolService.queryAuthByPoolId(poolId);
				crmModel.put("poolAuthList", poolAuthList);
			} else {
				Long isPool = (Long) crmModel.get("isPool");
				if (Objects.equals(isPool, 1L)) {
					String poolIdStr = baseMapper.queryPoolIdsByCustomer(id);
					if (StrUtil.isNotEmpty(poolIdStr)) {
						List<String> poolIds = StrUtil.splitTrim(poolIdStr, Const.SEPARATOR);
						List<Integer> poolIdList = poolIds.stream().map(Integer::valueOf).collect(Collectors.toList());
						JSONObject poolAuthList = crmCustomerPoolService.getOnePoolAuthByPoolIds(poolIdList);
						crmModel.put("poolAuthList", poolAuthList);
					}
				}
			}
			Integer contactsId = (Integer) crmModel.get("contactsId");
			if (contactsId != null) {
				CrmContacts contacts = crmContactsService.getById(contactsId);
				crmModel.put("contactsName", contacts.getName());
				crmModel.put("contactsMobile", contacts.getMobile());
				crmModel.put("contactsAddress", contacts.getAddress());
			}
		} else {
			crmModel = new CrmModel(CrmEnum.CUSTOMER.getType());
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
	public Map<String, Object> addOrUpdate(CrmModelSaveBO crmModel, boolean isExcel, Integer poolId) {
		CrmCustomer crmCustomer = BeanUtil.copyProperties(crmModel.getEntity(), CrmCustomer.class);
		if (crmCustomer.getCustomerId() != null) {
			if (!UserUtil.isAdmin() && getBaseMapper().queryIsRoUser(crmCustomer.getCustomerId(), UserUtil.getUserId()) > 0) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
		}
		String batchId = StrUtil.isNotEmpty(crmCustomer.getBatchId()) ? crmCustomer.getBatchId() : IdUtil.simpleUUID();
		actionRecordUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_customer_data"));
		crmCustomerDataService.saveData(crmModel.getField(), batchId);
		if (StrUtil.isEmpty(crmCustomer.getEmail())) {
			crmCustomer.setEmail(null);
		}
		if (crmCustomer.getCustomerId() != null) {
			crmBackLogDealService.deleteByType(crmCustomer.getOwnerUserId(), getLabel(), CrmBackLogEnum.TODAY_CUSTOMER, crmCustomer.getCustomerId());
			crmBackLogDealService.deleteByType(crmCustomer.getOwnerUserId(), getLabel(), CrmBackLogEnum.FOLLOW_CUSTOMER, crmCustomer.getCustomerId());
			crmCustomer.setUpdateTime(DateUtil.date());
			actionRecordUtil.updateRecord(BeanUtil.beanToMap(getById(crmCustomer.getCustomerId())), BeanUtil.beanToMap(crmCustomer), CrmEnum.CUSTOMER, crmCustomer.getCustomerName(), crmCustomer.getCustomerId());
			updateById(crmCustomer);
			crmCustomer = getById(crmCustomer.getCustomerId());
			ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "customer", crmCustomer.getCustomerId().toString(), crmCustomer.getCustomerName());
		} else {
			crmCustomer.setCreateTime(DateUtil.date());
			crmCustomer.setUpdateTime(DateUtil.date());
			crmCustomer.setReceiveTime(DateUtil.date());
			crmCustomer.setCreateUserId(UserUtil.getUserId());
			if (!isExcel && crmCustomer.getOwnerUserId() == null) {
				//The import will manually select the person in charge, which needs to be judged
				crmCustomer.setOwnerUserId(UserUtil.getUserId());
			}
			crmCustomer.setBatchId(batchId);
			crmCustomer.setLastTime(new Date());
			crmCustomer.setStatus(1);
			crmCustomer.setDealStatus(0);
			if (crmCustomer.getOwnerUserId() != null) {
				if (!crmCustomerSettingService.queryCustomerSettingNum(1, crmCustomer.getOwnerUserId())) {
					throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_SETTING_USER_ERROR);
				}
			}
			save(crmCustomer);
			crmActivityService.addActivity(2, CrmActivityEnum.CUSTOMER, crmCustomer.getCustomerId(), "");
			actionRecordUtil.addRecord(crmCustomer.getCustomerId(), CrmEnum.CUSTOMER, crmCustomer.getCustomerName());
			if (isExcel && poolId != null) {
				CrmCustomerPoolRelation relation = new CrmCustomerPoolRelation();
				relation.setCustomerId(crmCustomer.getCustomerId());
				relation.setPoolId(poolId);
				customerPoolRelationService.save(relation);
			}
		}
		crmModel.setEntity(BeanUtil.beanToMap(crmCustomer));
		if (isExcel && poolId != null) {
			List<CrmCustomerPoolRelation> poolRelations = customerPoolRelationService.lambdaQuery()
					.select(CrmCustomerPoolRelation::getPoolId)
					.eq(CrmCustomerPoolRelation::getCustomerId, crmCustomer.getCustomerId())
					.list();
			crmModel.getEntity().put("poolId", poolRelations.stream().map(CrmCustomerPoolRelation::getPoolId).collect(Collectors.toList()));
		}
		savePage(crmModel, crmCustomer.getCustomerId(), isExcel);
		Map<String, Object> map = new HashMap<>();
		map.put("customerId", crmCustomer.getCustomerId());
		map.put("customerName", crmCustomer.getCustomerName());
		return map;
	}

	@Override
	public void setOtherField(Map<String, Object> map) {
		String ownerUserName = UserCacheUtil.getUserName((Long) map.get("ownerUserId"));
		map.put("ownerUserName", ownerUserName);
		String createUserName = UserCacheUtil.getUserName((Long) map.get("createUserId"));
		map.put("createUserName", createUserName);
	}

	/**
	 * Delete customer data
	 *
	 * @param ids ids
	 */
	@Override
	public void deleteByIds(List<Integer> ids) {
		Integer contactsNum = crmContactsService.lambdaQuery().in(CrmContacts::getCustomerId, ids).count();
		Integer businessNum = crmBusinessService.lambdaQuery().in(CrmBusiness::getCustomerId, ids).eq(CrmBusiness::getStatus, 1).count();
		if (contactsNum > 0 || businessNum > 0) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_JOIN_ERROR);
		}
		lambdaUpdate().set(CrmCustomer::getUpdateTime, new Date()).set(CrmCustomer::getStatus, 3).in(CrmCustomer::getCustomerId, ids).update();
		LambdaQueryWrapper<CrmCustomer> wrapper = new LambdaQueryWrapper<>();
		wrapper.select(CrmCustomer::getBatchId);
		wrapper.in(CrmCustomer::getCustomerId, ids);
		List<String> batchList = listObjs(wrapper, Object::toString);
		//Delete Files
		adminFileService.delete(batchList);
		// delete the follow-up record
		crmActivityService.deleteActivityRecord(CrmActivityEnum.CUSTOMER, ids);
		//delete field operation record
		crmActionRecordService.deleteActionRecord(CrmEnum.CUSTOMER, ids);
		// delete custom fields
		crmCustomerDataService.deleteByBatchId(batchList);
		deletePage(ids);

	}

	@Override
	public JSONObject detectionDataCanBeDelete(List<Integer> ids) {
		Integer contactsNum = crmContactsService.lambdaQuery().in(CrmContacts::getCustomerId, ids).count();
		Integer businessNum = crmBusinessService.lambdaQuery().in(CrmBusiness::getCustomerId, ids).eq(CrmBusiness::getStatus, 1).count();
		JSONObject record = new JSONObject();
		record.fluentPut("contactsNum", contactsNum).fluentPut("businessNum", businessNum).fluentPut("isMore", ids.size() > 1);
		return record;
	}

	/**
	 * Modify the person in charge of the customer
	 *
	 * @param changOwnerUserBO data
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changeOwnerUser(CrmChangeOwnerUserBO changOwnerUserBO) {
		if (!isMaxOwner(changOwnerUserBO.getOwnerUserId(), changOwnerUserBO.getIds())) {
			throw new CrmException(CrmCodeEnum.THE_NUMBER_OF_CUSTOMERS_HAS_REACHED_THE_LIMIT);
		}
		String ownerUserName = UserCacheUtil.getUserName(changOwnerUserBO.getOwnerUserId());
		List<Long> userList = new ArrayList<>();
		if (UserUtil.isAdmin()) {
			userList = adminService.queryUserList(1).getData();
		} else {
			userList.add(UserUtil.getUserId());
			userList.addAll(adminService.queryChildUserId(UserUtil.getUserId()).getData());
		}
		List<Long> finalUserList = userList;
		BaseUtil.getRedis().del(CrmCacheKey.CRM_BACKLOG_NUM_CACHE_KEY + changOwnerUserBO.getOwnerUserId().toString());
		changOwnerUserBO.getIds().forEach(id -> {
			if (AuthUtil.isChangeOwnerUserAuth(id, CrmEnum.CUSTOMER, CrmAuthEnum.EDIT)) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
			CrmCustomer customer = getById(id);
			if (2 == changOwnerUserBO.getTransferType() && !changOwnerUserBO.getOwnerUserId().equals(customer.getOwnerUserId())) {
				ApplicationContextHolder.getBean(ICrmTeamMembersService.class).addSingleMember(getLabel(), customer.getCustomerId(), customer.getOwnerUserId(), changOwnerUserBO.getPower(), changOwnerUserBO.getExpiresTime(), customer.getCustomerName());
			}
			ApplicationContextHolder.getBean(ICrmTeamMembersService.class).deleteMember(getLabel(), new CrmMemberSaveBO(id, changOwnerUserBO.getOwnerUserId()));
			customer.setOwnerUserId(changOwnerUserBO.getOwnerUserId());
			customer.setFollowup(0);
			customer.setIsReceive(1);
			customer.setReceiveTime(DateUtil.date());
			BaseUtil.getRedis().del(CrmCacheKey.CRM_BACKLOG_NUM_CACHE_KEY + customer.getOwnerUserId().toString());
			updateById(customer);
			actionRecordUtil.addConversionRecord(id, CrmEnum.CUSTOMER, changOwnerUserBO.getOwnerUserId(), customer.getCustomerName());
			changOwnerUserBO.getChangeType().forEach(type -> {
				switch (type) {
					case 1: {
						List<Integer> ids = crmContactsService.lambdaQuery()
								.select(CrmContacts::getContactsId)
								.eq(CrmContacts::getCustomerId, id)
								.in(CrmContacts::getOwnerUserId, finalUserList)
								.list().stream().map(CrmContacts::getContactsId).collect(Collectors.toList());
						changOwnerUserBO.setIds(ids);
						crmContactsService.changeOwnerUser(changOwnerUserBO);
						break;
					}
					case 2: {
						List<Integer> ids = crmBusinessService.lambdaQuery()
								.select(CrmBusiness::getBusinessId)
								.eq(CrmBusiness::getCustomerId, id)
								.in(CrmBusiness::getOwnerUserId, finalUserList)
								.list().stream().map(CrmBusiness::getBusinessId).collect(Collectors.toList());
						CrmChangeOwnerUserBO changOwnerUser = new CrmChangeOwnerUserBO();
						changOwnerUser.setPower(changOwnerUserBO.getPower());
						changOwnerUser.setTransferType(changOwnerUserBO.getTransferType());
						changOwnerUser.setIds(ids);
						changOwnerUser.setOwnerUserId(changOwnerUserBO.getOwnerUserId());
						crmBusinessService.changeOwnerUser(changOwnerUser);
						break;
					}
					case 3: {
						List<Integer> ids = crmContractService.lambdaQuery()
								.select(CrmContract::getContractId)
								.eq(CrmContract::getCustomerId, id)
								.in(CrmContract::getOwnerUserId, finalUserList)
								.list().stream().map(CrmContract::getContractId).collect(Collectors.toList());
						CrmChangeOwnerUserBO changOwnerUser = new CrmChangeOwnerUserBO();
						changOwnerUser.setTransferType(changOwnerUserBO.getTransferType());
						changOwnerUser.setPower(changOwnerUserBO.getPower());
						changOwnerUser.setIds(ids);
						changOwnerUser.setOwnerUserId(changOwnerUserBO.getOwnerUserId());
						crmContractService.changeOwnerUser(changOwnerUser);
						break;
					}
					default:
						break;
				}
			});
			//modify es
			Map<String, Object> map = new HashMap<>();
			map.put("ownerUserId", changOwnerUserBO.getOwnerUserId());
			map.put("ownerUserName", ownerUserName);
			map.put("followup", 0);
			map.put("isReceive", 1);
			map.put("receiveTime", DateUtil.formatDateTime(new Date()));
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
				record.put("dealStatus", Objects.equals(1, record.get("dealStatus")) ? "Dealed" : "Undealed");
				record.put("status", Objects.equals(1, record.get("status")) ? "unlocked" : "locked");
			}

			@Override
			public String getExcelName() {
				return "Customer";
			}
		}, headList, response);
	}

	/**
	 * Customers into the high seas
	 *
	 * @param poolBO bo
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateCustomerByIds(CrmCustomerPoolBO poolBO) {
		if (poolBO.getIds().size() == 0) {
			return;
		}
		Long userId = UserUtil.getUserId();
		List<CrmOwnerRecord> ownerRecordList = new ArrayList<>();
		List<CrmCustomerPoolRelation> poolRelationList = new ArrayList<>();
		for (Integer id : poolBO.getIds()) {
			CrmCustomer crmCustomer = getById(id);
			if (crmCustomer.getOwnerUserId() == null) {
				continue;
			}
			/* The same permissions required to put in the high seas and modify the leader */
			if (AuthUtil.isChangeOwnerUserAuth(id, getLabel(), CrmAuthEnum.EDIT)) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
			CrmOwnerRecord crmOwnerRecord = new CrmOwnerRecord();
			crmOwnerRecord.setTypeId(id);
			crmOwnerRecord.setType(CrmEnum.CUSTOMER_POOL.getType());
			crmOwnerRecord.setPreOwnerUserId(crmCustomer.getOwnerUserId());
			crmOwnerRecord.setCreateTime(DateUtil.date());
			ownerRecordList.add(crmOwnerRecord);
			lambdaUpdate()
					.set(CrmCustomer::getOwnerUserId, null)
					.set(CrmCustomer::getPreOwnerUserId, userId)
					.set(CrmCustomer::getPoolTime, new Date())
					.set(CrmCustomer::getIsReceive, null)
					.eq(CrmCustomer::getCustomerId, crmCustomer.getCustomerId()).update();
			CrmCustomerPoolRelation relation = new CrmCustomerPoolRelation();
			relation.setCustomerId(id);
			relation.setPoolId(poolBO.getPoolId());
			poolRelationList.add(relation);
			actionRecordUtil.addPutIntoTheOpenSeaRecord(id, getLabel(), crmCustomer.getCustomerName());
		}
		if (ownerRecordList.size() > 0) {
			ApplicationContextHolder.getBean(ICrmOwnerRecordService.class).saveBatch(ownerRecordList);
		}
		if (poolRelationList.size() > 0) {
			customerPoolRelationService.saveBatch(poolRelationList);
		}
		LambdaUpdateWrapper<CrmContacts> wrapper = new LambdaUpdateWrapper<>();
		wrapper.set(CrmContacts::getOwnerUserId, null);
		wrapper.in(CrmContacts::getCustomerId, poolBO.getIds());
		crmContactsService.update(wrapper);
		putInPool(poolBO);
	}

	/**
	 * star
	 *
	 * @param customerId customer id
	 */
	@Override
	public void star(Integer customerId) {
		LambdaQueryWrapper<CrmCustomerUserStar> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CrmCustomerUserStar::getCustomerId, customerId);
		wrapper.eq(CrmCustomerUserStar::getUserId, UserUtil.getUserId());
		CrmCustomerUserStar star = crmCustomerUserStarService.getOne(wrapper);
		if (star == null) {
			star = new CrmCustomerUserStar();
			star.setCustomerId(customerId);
			star.setUserId(UserUtil.getUserId());
			crmCustomerUserStarService.save(star);
		} else {
			crmCustomerUserStarService.removeById(star.getId());
		}
	}

	/**
	 * Set primary contact
	 *
	 * @param contactsBO data
	 */
	@Override
	public void setContacts(CrmFirstContactsBO contactsBO) {
		lambdaUpdate().set(CrmCustomer::getContactsId, contactsBO.getContactsId())
				.eq(CrmCustomer::getCustomerId, contactsBO.getCustomerId()).update();
	}

	/**
	 * Pick up or assign customers
	 *
	 * @param poolBO    bo
	 * @param isReceive receive or assign
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void getCustomersByIds(CrmCustomerPoolBO poolBO, Integer isReceive) {
		if (poolBO.getIds().size() == 0) {
			return;
		}
		if (poolBO.getUserId() == null) {
			poolBO.setUserId(UserUtil.getUserId());
		}
		if (AuthUtil.isPoolAdmin(poolBO.getPoolId()) && isReceive == 1) {
			throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_DISTRIBUTE_ERROR);
		}
		if (!isMaxOwner(poolBO.getUserId(), poolBO.getIds())) {
			throw new CrmException(CrmCodeEnum.THE_NUMBER_OF_CUSTOMERS_HAS_REACHED_THE_LIMIT);
		}
		CrmCustomerPool pool = crmCustomerPoolService.getById(poolBO.getPoolId());
		if (isReceive == 2) {
			if (pool.getReceiveSetting() != null && pool.getReceiveSetting() == 1) {
				if (poolBO.getIds().size() > pool.getReceiveNum()) {
					throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_RECEIVE_ERROR);
				}
				Redis redis = BaseUtil.getRedis();
				String key = "receiveNum:poolId_" + poolBO.getPoolId() + ":userId_" + poolBO.getUserId();
				Integer num = redis.get(key);
				if (ObjectUtil.isNotEmpty(num) && (num + poolBO.getIds().size() > pool.getReceiveNum())) {
					throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_RECEIVE_NUMBER_ERROR);
				}
				long expireTime = (DateUtil.endOfDay(DateUtil.date()).getTime() - System.currentTimeMillis()) / 1000;
				redis.setex(key, (int) expireTime, ObjectUtil.isEmpty(num) ? poolBO.getIds().size() : num + poolBO.getIds().size());
			}
		}
		List<CrmOwnerRecord> records = new ArrayList<>();
		for (Integer id : poolBO.getIds()) {
			CrmCustomer customer = query().select("customer_id", "customer_name").eq("customer_id", id).one();
			actionRecordUtil.addDistributionRecord(id, CrmEnum.CUSTOMER, isReceive.equals(1) ? poolBO.getUserId() : null, customer.getCustomerName());
			//The former person in charge receives the limit, and the number of days is calculated from the time when the former person in charge leaves
			if (isReceive == 2) {
				if (pool.getPreOwnerSetting() == 1) {
					Integer days = getBaseMapper().queryOutDays(id, poolBO.getUserId());
					if (days != null && days <= pool.getPreOwnerSettingDay()) {
						throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_PRE_USER_RECEIVE_ERROR);
					}
				}
			}
			CrmOwnerRecord crmOwnerRecord = new CrmOwnerRecord();
			crmOwnerRecord.setTypeId(id);
			crmOwnerRecord.setType(CrmEnum.CUSTOMER_POOL.getType());
			crmOwnerRecord.setPostOwnerUserId(poolBO.getUserId());
			crmOwnerRecord.setCreateTime(DateUtil.date());
			records.add(crmOwnerRecord);
		}
		ApplicationContextHolder.getBean(ICrmOwnerRecordService.class).saveBatch(records);
		LambdaQueryWrapper<CrmCustomerPoolRelation> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmCustomerPoolRelation::getCustomerId, poolBO.getIds());
		customerPoolRelationService.remove(wrapper);
		List<Integer> contactsIds = crmContactsService.lambdaQuery().select(CrmContacts::getContactsId).in(CrmContacts::getCustomerId, poolBO.getIds()).list()
				.stream().map(CrmContacts::getContactsId).collect(Collectors.toList());
		crmContactsService.lambdaUpdate().set(CrmContacts::getOwnerUserId, poolBO.getUserId()).in(CrmContacts::getCustomerId, poolBO.getIds()).update();
		lambdaUpdate()
				.set(CrmCustomer::getOwnerUserId, poolBO.getUserId())
				.set(CrmCustomer::getFollowup, 0)
				.set(CrmCustomer::getReceiveTime, new Date())
				.set(CrmCustomer::getUpdateTime, new Date())
				.set(CrmCustomer::getIsReceive, isReceive)
				.in(CrmCustomer::getCustomerId, poolBO.getIds())
				.update();
		receiveCustomer(poolBO, isReceive, contactsIds);
	}

	/**
	 * Download import template
	 *
	 * @param response resp
	 * @throws IOException ex
	 */
	@Override
	public void downloadExcel(boolean isPool, HttpServletResponse response) throws IOException {
		List<CrmModelFiledVO> crmModelFiledList = queryField(null);
		int k = 0;
		for (int i = 0; i < crmModelFiledList.size(); i++) {
			if (crmModelFiledList.get(i).getFieldName().equals("customerName")) {
				k = i;
				break;
			}
		}
		crmModelFiledList.add(k + 1, new CrmModelFiledVO("ownerUserId", FieldEnum.TEXT, "responsible person", 1).setIsNull(isPool ? 0 : 1));
		ExcelParseUtil.importExcel(new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Customer";
			}

			@Override
			public int addCell(ExcelWriter writer, Integer x, Integer y, String fieldName) {
				if (writer == null) {
					if ("mapAddress".equals(fieldName)) {
						return 3;
					}
					return 0;
				}
				if ("mapAddress".equals(fieldName)) {
					Workbook wb = writer.getWorkbook();
					Sheet sheet = writer.getSheet();
					for (int i = 0; i < 4; i++) {
						writer.setColumnWidth(x + i, 20);
					}
					Cell cell1 = writer.getOrCreateCell(x, y);
					cell1.setCellValue("province");
					Cell cell2 = writer.getOrCreateCell(x + 1, y);
					cell2.setCellValue("City");
					Cell cell3 = writer.getOrCreateCell(x + 2, y);
					cell3.setCellValue("area");
					Cell cell4 = writer.getOrCreateCell(x + 3, y);
					cell4.setCellValue("Detailed address");
					Sheet hideSheet = wb.createSheet(fieldName);
					wb.setSheetHidden(wb.getSheetIndex(hideSheet), true);
					int rowId = 0;
					// Set the first line, save the information
					Row provinceRow = hideSheet.createRow(rowId++);
					provinceRow.createCell(0).setCellValue("Province List");
					String[] provinceList = getBaseMapper().queryCityList(100000).toArray(new String[0]);
					for (int line = 0; line < provinceList.length; line++) {
						Cell provinceCell = provinceRow.createCell(line + 1);
						provinceCell.setCellValue(provinceList[line]);
					}
					// Write specific data into each line, the beginning of the line is the parent area, followed by the child area.
					Map<String, List<String>> areaMap = CrmExcelUtil.getAreaMap();
					for (String key : areaMap.keySet()) {
						List<String> son = areaMap.get(key);
						Row subRow = hideSheet.createRow(rowId++);
						subRow.createCell(0).setCellValue(key);
						for (int line = 0; line < son.size(); line++) {
							Cell cell = subRow.createCell(line + 1);
							cell.setCellValue(son.get(line));
						}
						// add name manager
						String range = CrmExcelUtil.getRange(1, rowId, son.size());
						Name name = wb.createName();
						// key cannot be repeated
						name.setNameName(key);
						String formula = fieldName + "!" + range;
						name.setRefersToFormula(formula);
					}
					// Provincial drop-down box
					CellRangeAddressList provRangeAddressList = new CellRangeAddressList(2, 10004, x, x);
					DataValidationHelper validationHelper = sheet.getDataValidationHelper();
					DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(provinceList);
					//Set the dropdown box data
					DataValidation dataValidation = validationHelper.createValidation(constraint, provRangeAddressList);
					dataValidation.createErrorBox("error", "Please select the correct province");
					sheet.addValidationData(dataValidation);
					//Downtown drop-down box
					for (int line = 2; line < 10004; line++) {
						CrmExcelUtil.setDataValidation(CrmExcelUtil.getCorrespondingLabel(x + 1), sheet, line, x + 1);
						CrmExcelUtil.setDataValidation(CrmExcelUtil.getCorrespondingLabel(x + 2), sheet, line, x + 2);
					}
					return 3;
				}
				return 0;
			}
		}, crmModelFiledList, response, "crm");
	}

	/**
	 * Save customer rule settings
	 *
	 * @param customerSetting setting
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void customerSetting(CrmCustomerSetting customerSetting) {
		ICrmCustomerSettingUserService settingUserService = ApplicationContextHolder.getBean(ICrmCustomerSettingUserService.class);
		settingUserService.removeByMap(new JSONObject().fluentPut("setting_id", customerSetting.getSettingId()).getInnerMapObject());
		customerSetting.setCreateTime(DateUtil.date());
		crmCustomerSettingService.saveOrUpdate(customerSetting);
		Integer type = customerSetting.getType();
		List<Integer> settingIds = crmCustomerSettingService.lambdaQuery()
				.select(CrmCustomerSetting::getSettingId)
				.eq(CrmCustomerSetting::getType, type).list()
				.stream().map(CrmCustomerSetting::getSettingId).collect(Collectors.toList());
		List<CrmCustomerSettingUser> userList = new ArrayList<>();
		for (SimpleDept dept : customerSetting.getDeptIds()) {
			Integer count = settingUserService.lambdaQuery()
					.eq(CrmCustomerSettingUser::getDeptId, dept.getId())
					.eq(CrmCustomerSettingUser::getType, 2)
					.in(CrmCustomerSettingUser::getSettingId, settingIds).count();
			if (count > 0) {
				throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_SETTING_USER_EXIST_ERROR);
			}
			CrmCustomerSettingUser crmCustomerSettingUser = new CrmCustomerSettingUser();
			crmCustomerSettingUser.setDeptId(dept.getId());
			crmCustomerSettingUser.setSettingId(customerSetting.getSettingId());
			crmCustomerSettingUser.setType(2);
			userList.add(crmCustomerSettingUser);
		}
		for (SimpleUser user : customerSetting.getUserIds()) {
			Integer count = settingUserService.lambdaQuery()
					.eq(CrmCustomerSettingUser::getUserId, user.getUserId())
					.eq(CrmCustomerSettingUser::getType, 1)
					.in(CrmCustomerSettingUser::getSettingId, settingIds).count();
			if (count > 0) {
				throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_SETTING_USER_EXIST_ERROR);
			}
			CrmCustomerSettingUser crmCustomerSettingUser = new CrmCustomerSettingUser();
			crmCustomerSettingUser.setUserId(user.getUserId());
			crmCustomerSettingUser.setSettingId(customerSetting.getSettingId());
			crmCustomerSettingUser.setType(1);
			userList.add(crmCustomerSettingUser);
		}
		settingUserService.saveBatch(userList);
	}

	/**
	 * Remove customer rule settings
	 *
	 * @param settingId settingId
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteCustomerSetting(Integer settingId) {
		CrmCustomerSetting setting = crmCustomerSettingService.getById(settingId);
		if (setting != null) {
			ICrmCustomerSettingUserService settingUserService = ApplicationContextHolder.getBean(ICrmCustomerSettingUserService.class);
			settingUserService.removeByMap(new JSONObject().fluentPut("setting_id", settingId).getInnerMapObject());
			crmCustomerSettingService.removeById(settingId);
		}
	}

	@Override
	public List<CrmModelFiledVO> information(Integer customerId, Integer poolId) {
		List<CrmModelFiledVO> collect = queryField(customerId, true);
		if (ObjectUtil.isNotEmpty(poolId)) {
			LambdaQueryWrapper<CrmCustomerPoolFieldSetting> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmCustomerPoolFieldSetting::getName, CrmCustomerPoolFieldSetting::getIsHidden);
			wrapper.eq(CrmCustomerPoolFieldSetting::getPoolId, poolId);
			List<CrmCustomerPoolFieldSetting> fieldSettings = ApplicationContextHolder.getBean(ICrmCustomerPoolFieldSettingService.class).list(wrapper);
			List<String> nameList = fieldSettings.stream().filter(setting -> setting.getIsHidden().equals(1)).map(CrmCustomerPoolFieldSetting::getName).collect(Collectors.toList());
			//Query the name of the newly added custom field and the field name that has not been set in the sea
			List<String> collect1 = collect.stream().map(CrmModelFiledVO::getName).collect(Collectors.toList());
			List<String> collect2 = fieldSettings.stream().map(CrmCustomerPoolFieldSetting::getName).collect(Collectors.toList());
			Collection<String> disjunction = CollUtil.disjunction(collect1, collect2);
			collect.removeIf(r -> disjunction.contains(r.getName()) || nameList.contains(r.getName()) || "owner_user_name".equals(r.getFieldName()) || "receive_time".equals(r.getFieldName()));
		} else {
			collect.removeIf(r -> "owner_user_name".equals(r.getFieldName()));
		}
		return collect;
	}

	/**
	 * Query customer rule settings
	 *
	 * @param pageEntity entity
	 * @param type       type
	 */
	@Override
	public BasePage<CrmCustomerSetting> queryCustomerSetting(PageEntity pageEntity, Integer type) {
		BasePage<CrmCustomerSetting> page = crmCustomerSettingService.lambdaQuery().eq(CrmCustomerSetting::getType, type).page(pageEntity.parse());
		ICrmCustomerSettingUserService settingUserService = ApplicationContextHolder.getBean(ICrmCustomerSettingUserService.class);
		page.getList().forEach(crmCustomerSetting -> {
			List<CrmCustomerSettingUser> list = settingUserService.lambdaQuery().eq(CrmCustomerSettingUser::getSettingId, crmCustomerSetting.getSettingId()).list();
			List<Integer> deptIds = new ArrayList<>();
			List<Long> userIds = new ArrayList<>();
			list.forEach(settingUser -> {
				if (settingUser.getType().equals(1)) {
					userIds.add(settingUser.getUserId());
				} else if (settingUser.getType().equals(2)) {
					deptIds.add(settingUser.getDeptId());
				}
			});
			if (userIds.size() > 0) {
				List<SimpleUser> data = UserCacheUtil.getSimpleUsers(userIds);
				crmCustomerSetting.setUserIds(data);
				crmCustomerSetting.setRange(data.stream().map(SimpleUser::getRealname).collect(Collectors.joining(Const.SEPARATOR)));
			} else {
				crmCustomerSetting.setUserIds(new ArrayList<>());
			}

			if (deptIds.size() > 0) {
				List<SimpleDept> data = adminService.queryDeptByIds(deptIds).getData();
				crmCustomerSetting.setDeptIds(data);
				String range = crmCustomerSetting.getRange();
				if (StrUtil.isNotEmpty(range)) {
					range = range + Const.SEPARATOR;
				} else {
					range = "";
				}
				range = range + data.stream().map(SimpleDept::getName).collect(Collectors.joining(Const.SEPARATOR));
				crmCustomerSetting.setRange(range);
			} else {
				crmCustomerSetting.setDeptIds(new ArrayList<>());
			}
		});
		return page;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setDealStatus(Integer dealStatus, List<Integer> ids) {
		BulkRequest bulkRequest = new BulkRequest();
		String index = CrmEnum.CUSTOMER.getIndex();
		for (Integer id : ids) {
			if (!UserUtil.getUserId().equals(UserUtil.getSuperUser()) && !UserUtil.getUser().getRoles().contains(UserUtil.getSuperRole())
					&& getBaseMapper().queryIsRoUser(id, UserUtil.getUserId()) > 0) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
			CrmCustomer byId = getById(id);
			if (byId != null) {
				byId.setDealStatus(dealStatus);
				byId.setDealTime(new Date());
				updateById(byId);
				UpdateRequest updateRequest = new UpdateRequest(index, "_doc", id.toString());
				Map<String, Object> map = new HashMap<>();
				map.put("dealTime", DateUtil.formatDateTime(new Date()));
				map.put("dealStatus", dealStatus);
				updateRequest.doc(map);
				bulkRequest.add(updateRequest);
			}
		}
		try {
			elasticsearchRestTemplate.getClient().bulk(bulkRequest, RequestOptions.DEFAULT);
			elasticsearchRestTemplate.refresh(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BasePage<CrmContacts> queryContacts(CrmContactsPageBO pageEntity) {
		BasePage<CrmContacts> contactsBasePage = pageEntity.parse();
		String conditions = AuthUtil.getCrmAuthSql(CrmEnum.CONTACTS, 1, CrmAuthEnum.READ);
		return getBaseMapper().queryContacts(contactsBasePage, pageEntity.getCustomerId(), pageEntity.getSearch(), conditions);
	}

	@Override
	public BasePage<Map<String, Object>> queryBusiness(CrmContactsPageBO pageEntity) {
		BasePage<Map<String, Object>> basePage = pageEntity.parse();
		String condition = AuthUtil.getCrmAuthSql(CrmEnum.BUSINESS, "a", 1, CrmAuthEnum.READ);
		BasePage<Map<String, Object>> page = getBaseMapper().queryBusiness(basePage, pageEntity.getCustomerId(), pageEntity.getSearch(), condition);
		for (Map<String, Object> map : page.getList()) {
			Integer isEnd = Integer.valueOf(map.get("isEnd").toString());
			CrmListBusinessStatusVO crmListBusinessStatusVO = businessTypeService.queryListBusinessStatus((Integer) map.get("typeId"), (Integer) map.get("statusId"), isEnd);
			map.put("businessStatusCount", crmListBusinessStatusVO);
			if (Objects.equals(1, isEnd)) {
				map.put("statusName", "win order");
			} else if (Objects.equals(2, isEnd)) {
				map.put("statusName", "lose order");
			} else if (Objects.equals(3, isEnd)) {
				map.put("statusName", "invalid");
			}
		}
		return page;
	}

	@Override
	public BasePage<Map<String, Object>> queryContract(CrmContactsPageBO pageEntity) {
		BasePage<Map<String, Object>> basePage = pageEntity.parse();
		String conditions = AuthUtil.getCrmAuthSql(CrmEnum.CONTRACT, "a", 1, CrmAuthEnum.READ);
		BasePage<Map<String, Object>> page = getBaseMapper().queryContract(basePage, pageEntity.getCustomerId(), pageEntity.getSearch(), pageEntity.getCheckStatus(), conditions);
		for (Map<String, Object> map : page.getList()) {
			Double contractMoney = map.get("money") != null ? Double.parseDouble(map.get("money").toString()) : 0D;
			BigDecimal receivedProgress = new BigDecimal(100);
			if (!(contractMoney.intValue() == 0)) {
				receivedProgress = ((map.get("receivedMoney") != null ? (BigDecimal) map.get("receivedMoney") : new BigDecimal(0)).divide(new BigDecimal(contractMoney), 4, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
			}
			map.put("receivedProgress", receivedProgress);
		}
		return page;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void lock(Integer status, List<String> ids) {
		if (status != 1 && status != 2) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}

		for (String id : ids) {
			if (!UserUtil.isAdmin() && getBaseMapper().queryIsRoUser(Integer.parseInt(id), UserUtil.getUserId()) > 0) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
		}
		if (status == 2) {
			QueryWrapper<CrmCustomer> wrapper = new QueryWrapper<>();
			wrapper.select("count(owner_user_id) as num", "owner_user_id");
			wrapper.isNotNull("owner_user_id").eq("status", 1);
			wrapper.in("customer_id", ids).groupBy("owner_user_id");
			List<Map<String, Object>> maps = listMaps(wrapper);
			for (Map<String, Object> map : maps) {
				boolean b = crmCustomerSettingService.queryCustomerSettingNum(2, (Long) map.get("ownerUserId"), Integer.parseInt(map.get("num").toString()));
				if (!b) {
					throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_LOCK_MAX_ERROR);
				}
			}
		}
		actionRecordUtil.addIsLockRecord(ids, CrmEnum.CUSTOMER, status);
		lambdaUpdate().set(CrmCustomer::getStatus, status).in(CrmCustomer::getCustomerId, ids).update();
		updateField("status", status, ids.stream().map(Integer::valueOf).collect(Collectors.toList()));
	}

	@Override
	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		List<CrmCustomer> list = lambdaQuery().select(CrmCustomer::getCustomerId, CrmCustomer::getCustomerName).in(CrmCustomer::getCustomerId, ids).list();
		return list.stream().map(crmCustomer -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmCustomer.getCustomerId());
			simpleCrmEntity.setName(crmCustomer.getCustomerName());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
	}

	@Override
	public List<SimpleCrmEntity> queryByNameCustomerInfo(String name) {
		List<CrmCustomer> list = lambdaQuery().select(CrmCustomer::getCustomerId, CrmCustomer::getCustomerName).like(CrmCustomer::getCustomerName, name).list();
		return list.stream().map(crmCustomer -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmCustomer.getCustomerId());
			simpleCrmEntity.setName(crmCustomer.getCustomerName());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
	}

	@Override
	public List<SimpleCrmEntity> queryNameCustomerInfo(String name) {
		List<CrmCustomer> list = query().select("customer_id", "customer_name").eq("customer_name", name).list();
		return list.stream().map(crmCustomer -> {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmCustomer.getCustomerId());
			simpleCrmEntity.setName(crmCustomer.getCustomerName());
			return simpleCrmEntity;
		}).collect(Collectors.toList());
	}

	/**
	 * Follow up customer name query customer
	 *
	 * @param name name
	 * @return data
	 */
	@Override
	public SimpleCrmEntity queryFirstCustomerByName(String name) {
		CrmCustomer crmCustomer = query().select("customer_id", "customer_name").eq("customer_name", name).eq("status", 1).one();
		if (crmCustomer != null) {
			SimpleCrmEntity simpleCrmEntity = new SimpleCrmEntity();
			simpleCrmEntity.setId(crmCustomer.getCustomerId());
			simpleCrmEntity.setName(crmCustomer.getCustomerName());
			return simpleCrmEntity;
		} else {
			return null;
		}
	}

	/**
	 * Query the number of files
	 *
	 * @param customerId id
	 * @return data
	 */
	@Override
	public CrmInfoNumVO num(Integer customerId) {
		CrmCustomer customer = getById(customerId);
		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		List<CrmField> crmFields = crmFieldService.queryFileField();
		List<String> batchIdList = new ArrayList<>();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmCustomerData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmCustomerData::getValue);
			wrapper.eq(CrmCustomerData::getBatchId, customer.getBatchId());
			wrapper.in(CrmCustomerData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			batchIdList.addAll(crmCustomerDataService.listObjs(wrapper, Object::toString));
		}
		batchIdList.add(customer.getBatchId());
		batchIdList.addAll(crmActivityService.queryFileBatchId(customer.getCustomerId(), getLabel().getType()));
		String businessCon = AuthUtil.getCrmAuthSql(CrmEnum.BUSINESS, 1, CrmAuthEnum.READ);
		String contractCon = AuthUtil.getCrmAuthSql(CrmEnum.CONTRACT, 1, CrmAuthEnum.READ);
		String receivablesCon = AuthUtil.getCrmAuthSql(CrmEnum.RECEIVABLES, 1, CrmAuthEnum.READ);
		String contactsCon = AuthUtil.getCrmAuthSql(CrmEnum.CONTACTS, 1, CrmAuthEnum.READ);
		String returnVisitCon = AuthUtil.getCrmAuthSql(CrmEnum.RETURN_VISIT, 1, CrmAuthEnum.READ);
		String invoiceCon = AuthUtil.getCrmAuthSql(CrmEnum.INVOICE, 1, CrmAuthEnum.READ);
		Map<String, Object> map = new HashMap<>();
		map.put("businessCon", businessCon);
		map.put("contractCon", contractCon);
		map.put("receivablesCon", receivablesCon);
		map.put("contactsCon", contactsCon);
		map.put("returnVisitCon", returnVisitCon);
		map.put("invoiceCon", invoiceCon);
		map.put("customerId", customerId);
		CrmInfoNumVO infoNumVO = getBaseMapper().queryNum(map);
		infoNumVO.setFileCount(fileService.queryNum(batchIdList).getData());
		infoNumVO.setMemberCount(ApplicationContextHolder.getBean(ICrmTeamMembersService.class).queryMemberCount(getLabel(), customer.getCustomerId(), customer.getOwnerUserId()));
		return infoNumVO;
	}

	/**
	 * Query file list
	 *
	 * @param customerId id
	 * @return file
	 */
	@Override
	public List<FileEntity> queryFileList(Integer customerId) {
		List<FileEntity> fileEntityList = new ArrayList<>();
		CrmCustomer crmCustomer = getById(customerId);
		boolean auth = AuthUtil.isRwAuth(customerId, CrmEnum.CUSTOMER, CrmAuthEnum.READ);

		AdminFileService fileService = ApplicationContextHolder.getBean(AdminFileService.class);
		fileService.queryFileList(crmCustomer.getBatchId()).getData().forEach(fileEntity -> {
			fileEntity.setSource("Attachment upload");
			if (auth && !fileEntity.getCreateUserId().equals(UserUtil.getUserId())) {
				fileEntity.setReadOnly(1);
			} else {
				fileEntity.setReadOnly(0);
			}
			fileEntityList.add(fileEntity);
		});
		List<CrmField> crmFields = crmFieldService.queryFileField();
		if (crmFields.size() > 0) {
			LambdaQueryWrapper<CrmCustomerData> wrapper = new LambdaQueryWrapper<>();
			wrapper.select(CrmCustomerData::getValue);
			wrapper.eq(CrmCustomerData::getBatchId, crmCustomer.getBatchId());
			wrapper.in(CrmCustomerData::getFieldId, crmFields.stream().map(CrmField::getFieldId).collect(Collectors.toList()));
			List<FileEntity> data = fileService.queryFileList(crmCustomerDataService.listObjs(wrapper, Object::toString)).getData();
			data.forEach(fileEntity -> {
				fileEntity.setSource("Customer Details");
				fileEntity.setReadOnly(1);
				fileEntityList.add(fileEntity);
			});
		}
		List<String> stringList = crmActivityService.queryFileBatchId(crmCustomer.getCustomerId(), getLabel().getType());
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


	/**
	 * Get customer name
	 *
	 * @param customerId id
	 * @return data
	 */
	@Override
	public String getCustomerName(Integer customerId) {
		if (customerId == null) {
			return "";
		}
		return lambdaQuery().select(CrmCustomer::getCustomerName).eq(CrmCustomer::getCustomerId, customerId)
				.oneOpt().map(CrmCustomer::getCustomerName).orElse("");
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
		List<CrmModelFiledVO> filedList = crmFieldService.queryField(getLabel().getType());
		filedList.add(new CrmModelFiledVO("lastTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("lastContent", FieldEnum.TEXTAREA, 1));
		filedList.add(new CrmModelFiledVO("updateTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("dealTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("receiveTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("poolTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("createTime", FieldEnum.DATETIME, 1));
		filedList.add(new CrmModelFiledVO("ownerUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("createUserId", FieldEnum.USER, 1));
		filedList.add(new CrmModelFiledVO("status", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("ownerUserName", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("createUserName", FieldEnum.TEXT, 1));
		filedList.add(new CrmModelFiledVO("teamMemberIds", FieldEnum.USER, 0));
		return filedList;
	}

	@Override
	public boolean isMaxOwner(Long ownerUserId, List<Integer> ids) {
		Integer number = getBaseMapper().ownerNum(ids, ownerUserId);
		return crmCustomerSettingService.queryCustomerSettingNum(1, ownerUserId, number);
	}


	@Override
	public void updateInformation(CrmUpdateInformationBO updateInformationBO) {
		String batchId = updateInformationBO.getBatchId();
		Integer customerId = updateInformationBO.getId();
		if (!UserUtil.isAdmin() && getBaseMapper().queryIsRoUser(customerId, UserUtil.getUserId()) > 0) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		updateInformationBO.getList().forEach(record -> {
			CrmCustomer oldCustomer = getById(customerId);
			uniqueFieldIsAbnormal(record.getString("name"), record.getInteger("fieldId"), record.getString("value"), batchId);
			Map<String, Object> oldCustomerMap = BeanUtil.beanToMap(oldCustomer);
			if (record.getInteger("fieldType") == 1) {
				Map<String, Object> crmCustomerMap = new HashMap<>(oldCustomerMap);
				crmCustomerMap.put(record.getString("fieldName"), record.get("value"));
				CrmCustomer crmCustomer = BeanUtil.mapToBean(crmCustomerMap, CrmCustomer.class, true);
				actionRecordUtil.updateRecord(oldCustomerMap, crmCustomerMap, CrmEnum.CUSTOMER, crmCustomer.getCustomerName(), crmCustomer.getCustomerId());
				update().set(StrUtil.toUnderlineCase(record.getString("fieldName")), record.get("value")).eq("customer_id", updateInformationBO.getId()).update();
				if ("customerName".equals(record.getString("fieldName"))) {
					ElasticUtil.batchUpdateEsData(elasticsearchRestTemplate.getClient(), "customer", crmCustomer.getCustomerId().toString(), crmCustomer.getCustomerName());
				}
			} else if (record.getInteger("fieldType") == 0 || record.getInteger("fieldType") == 2) {
				CrmCustomerData customerData = crmCustomerDataService.lambdaQuery().select(CrmCustomerData::getValue, CrmCustomerData::getId).eq(CrmCustomerData::getFieldId, record.getInteger("fieldId"))
						.eq(CrmCustomerData::getBatchId, batchId).last("limit 1").one();
				String value = customerData != null ? customerData.getValue() : null;
				actionRecordUtil.publicContentRecord(CrmEnum.CUSTOMER, BehaviorEnum.UPDATE, customerId, oldCustomer.getCustomerName(), record, value);
				String newValue = fieldService.convertObjectValueToString(record.getInteger("type"), record.get("value"), record.getString("value"));
				CrmCustomerData crmCustomerData = new CrmCustomerData();
				crmCustomerData.setId(customerData != null ? customerData.getId() : null);
				crmCustomerData.setFieldId(record.getInteger("fieldId"));
				crmCustomerData.setName(record.getString("fieldName"));
				crmCustomerData.setValue(newValue);
				crmCustomerData.setCreateTime(new Date());
				crmCustomerData.setBatchId(batchId);
				crmCustomerDataService.saveOrUpdate(crmCustomerData);

			}
			updateField(record, customerId);
		});
		this.lambdaUpdate().set(CrmCustomer::getUpdateTime, new Date()).eq(CrmCustomer::getCustomerId, customerId).update();
	}

	@Override
	public List<CrmDataCheckVO> dataCheck(CrmDataCheckBO dataCheckBO) {
		List<CrmDataCheckVO> list = getBaseMapper().dataCheck(dataCheckBO);
		for (CrmDataCheckVO crmDataCheckVO : list) {
			if (StrUtil.isNotEmpty(crmDataCheckVO.getPoolIds())) {
				List<String> poolIds = StrUtil.splitTrim(crmDataCheckVO.getPoolIds(), Const.SEPARATOR);
				List<Integer> poolIdList = poolIds.stream().map(Integer::valueOf).collect(Collectors.toList());
				crmDataCheckVO.setPoolAuthList(crmCustomerPoolService.getOnePoolAuthByPoolIds(poolIdList));
			}
			if (crmDataCheckVO.getOwnerUserId() != null) {
				crmDataCheckVO.setOwnerUserName(UserCacheUtil.getUserName(crmDataCheckVO.getOwnerUserId()));
			}
			if (crmDataCheckVO.getContactsId() != null) {
				crmDataCheckVO.setContactsName(Optional.ofNullable(crmContactsService.getById(crmDataCheckVO.getContactsId())).map(CrmContacts::getName).orElse(null));
			}
		}
		return list;
	}

	@Override
	public BasePage<JSONObject> queryReceivablesPlan(CrmRelationPageBO crmRelationPageBO) {
		String conditions = AuthUtil.getCrmAuthSql(CrmEnum.CONTRACT, "c", 1, CrmAuthEnum.READ);
		return getBaseMapper().queryReceivablesPlan(crmRelationPageBO.parse(), crmRelationPageBO.getCustomerId(), conditions);
	}

	@Override
	public BasePage<JSONObject> queryReceivables(CrmRelationPageBO crmRelationPageBO) {
		String conditions = AuthUtil.getCrmAuthSql(CrmEnum.RECEIVABLES, "a", 1, CrmAuthEnum.READ);
		BasePage<JSONObject> jsonObjects = getBaseMapper().queryReceivables(crmRelationPageBO.parse(), crmRelationPageBO.getCustomerId(), conditions);
		for (JSONObject jsonObject : jsonObjects.getList()) {
			String ownerUserName = UserCacheUtil.getUserName(jsonObject.getLong("ownerUserId"));
			jsonObject.put("ownerUserName", ownerUserName);
		}
		return jsonObjects;
	}

	@Override
	public BasePage<JSONObject> queryReturnVisit(CrmRelationPageBO crmRelationPageBO) {
		List<CrmField> nameList = crmFieldService.lambdaQuery().select(CrmField::getFieldId, CrmField::getFieldName).eq(CrmField::getLabel, CrmEnum.RETURN_VISIT.getType())
				.eq(CrmField::getIsHidden, 0).ne(CrmField::getFieldType, 1).list();
		String conditions = AuthUtil.getCrmAuthSql(CrmEnum.RETURN_VISIT, "a", 1, CrmAuthEnum.READ);
		BasePage<JSONObject> jsonObjects = getBaseMapper().queryReturnVisit(crmRelationPageBO.parse(), crmRelationPageBO.getCustomerId(), conditions, nameList);
		for (JSONObject jsonObject : jsonObjects.getList()) {
			String ownerUserName = UserCacheUtil.getUserName(jsonObject.getLong("ownerUserId"));
			jsonObject.put("ownerUserName", ownerUserName);
		}
		return jsonObjects;
	}

	@Override
	public BasePage<JSONObject> queryInvoice(CrmRelationPageBO crmRelationPageBO) {
		String conditions = AuthUtil.getCrmAuthSql(CrmEnum.INVOICE, "a", 0, CrmAuthEnum.READ);
		BasePage<JSONObject> jsonObjects = getBaseMapper().queryInvoice(crmRelationPageBO.parse(), crmRelationPageBO.getCustomerId(), conditions);
		for (JSONObject jsonObject : jsonObjects.getList()) {
			String ownerUserName = UserCacheUtil.getUserName(jsonObject.getLong("ownerUserId"));
			jsonObject.put("ownerUserName", ownerUserName);
		}
		return jsonObjects;
	}

	@Override
	public BasePage<JSONObject> queryInvoiceInfo(CrmRelationPageBO crmRelationPageBO) {
		BasePage<JSONObject> jsonObjects = getBaseMapper().queryInvoiceInfo(crmRelationPageBO.parse(), crmRelationPageBO.getCustomerId());
		for (JSONObject jsonObject : jsonObjects.getList()) {
			String ownerUserName = UserCacheUtil.getUserName(jsonObject.getLong("ownerUserId"));
			jsonObject.put("ownerUserName", ownerUserName);
		}
		return jsonObjects;
	}

	@Override
	public BasePage<JSONObject> queryCallRecord(CrmRelationPageBO crmRelationPageBO) {
		BasePage<JSONObject> jsonObjects = getBaseMapper().queryCallRecord(crmRelationPageBO.parse(), crmRelationPageBO.getCustomerId());
		for (JSONObject jsonObject : jsonObjects.getList()) {
			String ownerUserName = UserCacheUtil.getUserName(jsonObject.getLong("ownerUserId"));
			jsonObject.put("ownerUserName", ownerUserName);
		}
		return jsonObjects;
	}


	@Override
	public List<JSONObject> nearbyCustomer(String lng, String lat, Integer type, Integer radius, Long ownerUserId) {
		Integer menuId = adminService.queryMenuId("crm", "customer", "nearbyCustomer").getData();
		List<Long> authUserIdList = AuthUtil.getUserIdByAuth(menuId);
		List<Integer> poolIdList = new ArrayList<>();
		if (ObjectUtil.isEmpty(type) || type.equals(9)) {
			if (UserUtil.isAdmin()) {
				poolIdList = crmCustomerPoolService.lambdaQuery().select(CrmCustomerPool::getPoolId).list().stream().map(CrmCustomerPool::getPoolId).collect(Collectors.toList());
			} else {
				poolIdList = crmCustomerPoolService.queryPoolIdByUserId();
			}
		}
		List<JSONObject> jsonObjects = getBaseMapper().nearbyCustomer(lng, lat, type, radius, ownerUserId, authUserIdList, poolIdList);
		for (JSONObject jsonObject : jsonObjects) {
			String ownerUserName = UserCacheUtil.getUserName(jsonObject.getLong("ownerUserId"));
			jsonObject.put("ownerUserName", ownerUserName);
		}
		return jsonObjects;
	}

	@Override
	public List<String> eventCustomer(CrmEventBO crmEventBO) {
		return getBaseMapper().eventCustomer(crmEventBO);
	}

	@Override
	public BasePage<Map<String, Object>> eventCustomerPageList(QueryEventCrmPageBO eventCrmPageBO) {
		Long userId = eventCrmPageBO.getUserId();
		Long time = eventCrmPageBO.getTime();
		if (userId == null) {
			userId = UserUtil.getUserId();
		}
		List<Integer> customerIds = getBaseMapper().eventCustomerList(userId, new Date(time));
		if (customerIds.size() == 0) {
			return new BasePage<>();
		}
		List<String> collect = customerIds.stream().map(Object::toString).collect(Collectors.toList());
		CrmSearchBO crmSearchBO = new CrmSearchBO();
		crmSearchBO.setSearchList(Collections.singletonList(new CrmSearchBO.Search("_id", "text", CrmSearchBO.FieldSearchEnum.ID, collect)));
		crmSearchBO.setLabel(CrmEnum.CUSTOMER.getType());
		crmSearchBO.setPage(eventCrmPageBO.getPage());
		crmSearchBO.setLimit(eventCrmPageBO.getLimit());
		BasePage<Map<String, Object>> page = queryPageList(crmSearchBO);
		return page;
	}

	@Override
	public List<Integer> forgottenCustomer(Integer day, List<Long> userIds, String search) {
		return getBaseMapper().forgottenCustomer(day, userIds, search);
	}

	@Override
	public List<Integer> unContactCustomer(String search, List<Long> userIds) {
		return getBaseMapper().unContactCustomer(search, userIds);
	}
}
