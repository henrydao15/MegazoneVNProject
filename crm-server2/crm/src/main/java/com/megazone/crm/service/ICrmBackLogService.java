package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.crm.entity.BO.CrmBackLogBO;
import com.megazone.crm.entity.PO.CrmReceivablesPlan;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @since 2020-05-23
 */
public interface ICrmBackLogService {

	/**
	 * @return num
	 */
	public JSONObject num();

	/**
	 * @return data
	 */
	BasePage<Map<String, Object>> todayLeads(CrmBackLogBO crmBackLogBO);

	/**
	 * @return data
	 */
	public BasePage<Map<String, Object>> todayCustomer(CrmBackLogBO crmBackLogBO);

	/**
	 * @return data
	 */
	BasePage<Map<String, Object>> todayBusiness(CrmBackLogBO crmBackLogBO);

	/**
	 * @param crmBackLogBO bo
	 * @return data
	 */
	public BasePage<Map<String, Object>> returnVisitRemind(CrmBackLogBO crmBackLogBO);

	/**
	 * @return data
	 */
	public BasePage<Map<String, Object>> followLeads(CrmBackLogBO crmBackLogBO);

	/**
	 * @return data
	 */
	public BasePage<Map<String, Object>> followCustomer(CrmBackLogBO crmBackLogBO);

	/**
	 * @param ids data
	 */
	public void setLeadsFollowup(List<Integer> ids);

	/**
	 *
	 */
	public void setCustomerFollowup(List<Integer> ids);

	/**
	 *
	 */
	public BasePage<Map<String, Object>> checkInvoice(CrmBackLogBO crmBackLogBO);

	/**
	 *
	 */
	public BasePage<Map<String, Object>> checkReceivables(CrmBackLogBO crmBackLogBO);

	/**
	 *
	 */
	public BasePage<Map<String, Object>> checkContract(CrmBackLogBO crmBackLogBO);

	/**
	 *
	 */
	public BasePage<CrmReceivablesPlan> remindReceivables(CrmBackLogBO crmBackLogBO);

	/**
	 *
	 */
	public BasePage<Map<String, Object>> endContract(CrmBackLogBO crmBackLogBO);

	/**
	 * @param model model
	 */
	public void allDeal(Integer model);

	/**
	 * @param model model
	 */
	public void dealById(Integer model, List<JSONObject> jsonObjectList);

	BasePage<Map<String, Object>> putInPoolRemind(CrmBackLogBO crmBackLogBO);

}
