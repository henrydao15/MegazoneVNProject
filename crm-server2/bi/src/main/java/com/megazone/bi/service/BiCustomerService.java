package com.megazone.bi.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.BiParams;

import java.util.List;

public interface BiCustomerService {

	/**
	 * Query the total number of customers analysis chart
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> totalCustomerStats(BiParams biParams);

	/**
	 * Query the total number of customers analysis chart
	 *
	 * @param biParams params
	 * @return data
	 */
	JSONObject totalCustomerTable(BiParams biParams);

	/**
	 * Analysis of customer follow-up times
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> customerRecordStats(BiParams biParams);

	/**
	 * Customer follow-up times analysis table
	 *
	 * @param biParams params
	 * @return data
	 */
	JSONObject customerRecordInfo(BiParams biParams);

	/**
	 * Customer follow-up method analysis table
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> customerRecodCategoryStats(BiParams biParams);

	/**
	 * Customer conversion rate analysis chart
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> customerConversionStats(BiParams biParams);

	/**
	 * Customer conversion rate analysis table
	 *
	 * @param biParams params
	 * @return data
	 */
	BasePage<JSONObject> customerConversionInfo(BiParams biParams);

	/**
	 * High seas customer analysis chart
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> poolStats(BiParams biParams);

	/**
	 * High seas customer analysis table
	 *
	 * @param biParams params
	 * @return data
	 */
	JSONObject poolTable(BiParams biParams);

	/**
	 * Employee customer transaction cycle diagram
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> employeeCycle(BiParams biParams);

	/**
	 * Staff customer transaction cycle table
	 *
	 * @param biParams params
	 * @return data
	 */
	JSONObject employeeCycleInfo(BiParams biParams);

	/**
	 * Regional transaction cycle chart
	 *
	 * @param biParams params
	 * @return data
	 */
	JSONObject districtCycle(BiParams biParams);

	/**
	 * Product transaction cycle chart
	 *
	 * @param biParams params
	 * @return data
	 */
	JSONObject productCycle(BiParams biParams);

	/**
	 * Customer satisfaction analysis
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> customerSatisfactionTable(BiParams biParams);

	/**
	 * Product satisfaction analysis
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> productSatisfactionTable(BiParams biParams);
}
