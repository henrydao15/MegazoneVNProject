package com.megazone.crm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmMarketingForm;

import java.util.List;

/**
 * @author
 * @date 2020/12/2
 */
public interface ICrmMarketingFormService extends BaseService<CrmMarketingForm> {

	CrmMarketingForm saveOrUpdateCrmMarketingForm(CrmMarketingForm crmMarketingForm);

	BasePage<CrmMarketingForm> queryCrmMarketingFormList(PageEntity pageEntity);

	List<CrmMarketingForm> queryAllCrmMarketingFormList();

	void deleteCrmMarketingForm(Integer id);

	void updateStatus(CrmMarketingForm crmMarketingForm);
}
