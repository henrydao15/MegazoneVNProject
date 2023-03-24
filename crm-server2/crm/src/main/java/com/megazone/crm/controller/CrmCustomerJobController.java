package com.megazone.crm.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.common.cache.CrmCacheKey;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.redis.Redis;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.TagUtil;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmCustomerPoolBO;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.mapper.CrmCustomerPoolMapper;
import com.megazone.crm.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author
 */
@RestController
@RequestMapping("/crmCustomerJob")
@Slf4j
public class CrmCustomerJobController {

	@Autowired
	private ICrmCustomerPoolService crmCustomerPoolService;

	@Autowired
	private ICrmActionRecordService actionRecordService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ICrmCustomerPoolRuleService crmCustomerPoolRuleService;

	@Autowired
	private CrmCustomerPoolMapper crmCustomerPoolMapper;

	@Autowired
	private ICrmCustomerPoolRelationService crmCustomerPoolRelationService;

	@Autowired
	private ICrmCustomerService crmCustomerService;

	@Autowired
	private ICrmOwnerRecordService crmOwnerRecordService;

	@Resource
	private CrmPageService customerPoolService;

	@Autowired
	private ICrmReceivablesPlanService crmReceivablesPlanService;

	@Autowired
	private Redis redis;


	@ApiExplain("")
	@PostMapping("/putInInternational")
	@Transactional(rollbackFor = Exception.class)
	public Result putInInternational() {

		ApplicationContextHolder.getBean(ICrmTeamMembersService.class).removeOverdueTeamMembers();
		try {
			UserInfo userInfo = redis.get(CrmCacheKey.CRM_CUSTOMER_JOB_CACHE_KEY);
			List<CrmCustomerPool> poolList = crmCustomerPoolService.lambdaQuery()
					.eq(CrmCustomerPool::getStatus, 1).eq(CrmCustomerPool::getPutInRule, 1).list();
			poolList.forEach(pool -> {
				List<Long> userIdsList = new ArrayList<>();
				if (StrUtil.isNotEmpty(pool.getMemberDeptId())) {
					userIdsList = adminService.queryUserByDeptIds(TagUtil.toSet(pool.getMemberDeptId())).getData();
				}
				if (StrUtil.isNotEmpty(pool.getMemberUserId())) {
					userIdsList.addAll(TagUtil.toLongSet(pool.getMemberUserId()));
				}
				List<CrmCustomerPoolRule> ruleList = crmCustomerPoolRuleService.lambdaQuery().eq(CrmCustomerPoolRule::getPoolId, pool.getPoolId()).list();
				Set<Integer> customerIdSet = new HashSet<>();
				Date date = DateUtil.beginOfDay(new Date());
				List<CrmActionRecord> crmActionRecordList = new ArrayList<>();
				for (CrmCustomerPoolRule rule : ruleList) {
					Map<String, Object> record = BeanUtil.beanToMap(rule);
					if (CollectionUtil.isNotEmpty(userIdsList)) {
						record.put("ids", userIdsList);
					}
					Set<Integer> ids;
					if (rule.getType().equals(1)) {
						ids = crmCustomerPoolMapper.putInPoolByRecord(record);
					} else if (rule.getType().equals(2)) {
						ids = crmCustomerPoolMapper.putInPoolByBusiness(record);
					} else if (rule.getType().equals(3)) {
						ids = crmCustomerPoolMapper.putInPoolByDealStatus(record);
					} else {
						continue;
					}
					for (Integer id : ids) {
						crmActionRecordList.add(addPutPoolRecord(CrmEnum.CUSTOMER.getType(), id, rule.getType(), rule.getLimitDay(), date));
					}
					customerIdSet.addAll(ids);
				}
				customerIdSet.forEach(customerId -> {
					CrmCustomer crmCustomer = crmCustomerService.getById(customerId);
					if (ObjectUtil.isNotEmpty(crmCustomer.getOwnerUserId())) {
						CrmOwnerRecord crmOwnerRecord = new CrmOwnerRecord();
						crmOwnerRecord.setTypeId(customerId);
						crmOwnerRecord.setType(CrmEnum.CUSTOMER_POOL.getType());
						crmOwnerRecord.setPreOwnerUserId(crmCustomer.getOwnerUserId());
						crmOwnerRecordService.save(crmOwnerRecord);
						crmCustomerService.lambdaUpdate()
								.set(CrmCustomer::getPreOwnerUserId, crmOwnerRecord.getPreOwnerUserId())
								.set(CrmCustomer::getOwnerUserId, null)
								.set(CrmCustomer::getPoolTime, new Date())
								.set(CrmCustomer::getIsReceive, null)
								.eq(CrmCustomer::getCustomerId, customerId)
								.update();
					}
					CrmCustomerPoolRelation customerPoolRelation = new CrmCustomerPoolRelation();
					customerPoolRelation.setCustomerId(customerId);
					customerPoolRelation.setPoolId(pool.getPoolId());
					crmCustomerPoolRelationService.save(customerPoolRelation);
				});
				if (CollUtil.isNotEmpty(customerIdSet)) {
					ApplicationContextHolder.getBean(ICrmContactsService.class).lambdaUpdate()
							.set(CrmContacts::getOwnerUserId, null)
							.in(CrmContacts::getCustomerId, customerIdSet).update();
					CrmCustomerPoolBO crmCustomerPoolBO = new CrmCustomerPoolBO();
					crmCustomerPoolBO.setIds(Lists.newArrayList(customerIdSet));
					crmCustomerPoolBO.setPoolId(pool.getPoolId());
					customerPoolService.putInPool(crmCustomerPoolBO);
				}

				if (!crmActionRecordList.isEmpty()) {
					actionRecordService.saveBatch(crmActionRecordList);
				}
			});
			return Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(500, e.getMessage());
		}
	}

	@ApiExplain("")
	@PostMapping("/updateReceivablesPlan")
	@Transactional(rollbackFor = Exception.class)
	public Result updateReceivablesPlan() {
		try {
			crmReceivablesPlanService.updateReceivedStatus();
			return Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error(500, e.getMessage());
		}
	}

	/**
	 * Assemble the high seas into the operation record object
	 */
	private CrmActionRecord addPutPoolRecord(Integer type, Integer actionId, Integer putType, Integer day, Date date) {
		CrmActionRecord actionRecord = new CrmActionRecord();
		actionRecord.setCreateUserId(0L);
		actionRecord.setCreateTime(date);
		actionRecord.setIpAddress(BaseUtil.getIp());
		actionRecord.setTypes(type);
		actionRecord.setBehavior(BehaviorEnum.PUT_IN_POOL.getType());
		actionRecord.setActionId(actionId);
		switch (putType) {
			case 1: {
				actionRecord.setDetail("More than " + day + " days without new follow-up records automatically enter the open sea");
				break;
			}
			case 2: {
				actionRecord.setDetail("More than " + day + " days without new business opportunities automatically enter the high seas");
				break;
			}
			case 3: {
				actionRecord.setDetail("More than" + day + "days without transaction will automatically enter the high seas");
				break;
			}
			default:
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		actionRecord.setObject("");
		return actionRecord;
	}

}
