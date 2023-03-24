package com.megazone.crm.common.log;

import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.crm.entity.PO.CrmCustomerPool;
import com.megazone.crm.service.ICrmCustomerPoolService;

public class CrmCustomerPoolLog {

	private ICrmCustomerPoolService crmCustomerPoolService = ApplicationContextHolder.getBean(ICrmCustomerPoolService.class);

	public Content changeStatus(Integer poolId, Integer status) {
		CrmCustomerPool customerPool = crmCustomerPoolService.getById(poolId);
		String detail;
		if (status == 0) {
			detail = "Disabled:";
		} else {
			detail = "Enabled:";
		}
		return new Content(customerPool.getPoolName(), detail + customerPool.getPoolName(), BehaviorEnum.UPDATE);
	}

	public Content transfer(Integer prePoolId, Integer postPoolId) {
		CrmCustomerPool prePool = crmCustomerPoolService.getById(prePoolId);
		CrmCustomerPool postPool = crmCustomerPoolService.getById(postPoolId);
		return new Content("High sea transfer", "transfer from [" + prePool.getPoolName() + "] to high sea[" + postPool.getPoolName() + "]", BehaviorEnum.UPDATE);
	}

	public Content deleteCustomerPool(Integer poolId) {
		CrmCustomerPool customerPool = crmCustomerPoolService.getById(poolId);
		return new Content(customerPool.getPoolName(), "Deleted High Seas" + customerPool.getPoolName(), BehaviorEnum.UPDATE);
	}
}
