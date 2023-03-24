package com.megazone.job.crm;

import com.megazone.core.common.Const;
import com.megazone.core.common.Result;
import com.megazone.core.common.cache.CrmCacheKey;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.service.CrmService;
import com.megazone.core.redis.Redis;
import com.megazone.core.utils.UserUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@Slf4j
public class CrmCustomerJob {

	@Autowired
	private CrmService crmService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private Redis redis;

	@XxlJob("CrmCustomerJob")
	public ReturnT<String> crmCustomerJobHandler(String param) {

		try {
			Long userId = UserUtil.getSuperUser();
			UserInfo userInfo = UserUtil.setUser(userId);
			redis.setex(CrmCacheKey.CRM_CUSTOMER_JOB_CACHE_KEY, Const.MAX_USER_EXIST_TIME, userInfo);
			Result result = crmService.putInInternational();
			crmService.updateReceivablesPlan();
			if (!result.hasSuccess()) {
				ReturnT<String> fail = ReturnT.FAIL;
				fail.setMsg(result.getMsg());
				return fail;
			}
		} finally {
			redis.del(CrmCacheKey.CRM_CUSTOMER_JOB_CACHE_KEY);
			UserUtil.removeUser();
		}
		XxlJobLogger.log("");
		return ReturnT.SUCCESS;
	}
}
