package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.BO.MarketingFieldBO;
import com.megazone.crm.entity.PO.CrmMarketingField;

import java.util.List;

/**
 * @author
 * @date 2020/12/2
 */
public interface ICrmMarketingFieldService extends BaseService<CrmMarketingField> {

	List<CrmMarketingField> queryField(Integer id);

	void recordToFormType(List<CrmMarketingField> list);

	void transferFieldList(List<CrmMarketingField> recordList, Integer isDetail);

	void saveField(MarketingFieldBO marketingFieldBO);
}
