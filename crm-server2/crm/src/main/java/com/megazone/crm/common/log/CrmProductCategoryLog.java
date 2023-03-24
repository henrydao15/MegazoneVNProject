package com.megazone.crm.common.log;

import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.crm.service.ICrmProductCategoryService;

public class CrmProductCategoryLog {

	private ICrmProductCategoryService crmProductCategoryService = ApplicationContextHolder.getBean(ICrmProductCategoryService.class);

	public Content deleteById(Integer id) {
		String productCategoryName = crmProductCategoryService.getProductCategoryName(id);
		return new Content(productCategoryName, "Deleted product type:" + productCategoryName, BehaviorEnum.DELETE);
	}
}
