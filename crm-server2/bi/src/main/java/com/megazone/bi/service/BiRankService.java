package com.megazone.bi.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.feign.crm.entity.BiParams;

import java.util.List;

public interface BiRankService {

	/**
	 * Urban distribution analysis
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> addressAnalyse(BiParams biParams);

	/**
	 * Urban distribution analysis
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> portrait(BiParams biParams);

	/**
	 * City level analysis
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> portraitLevel(BiParams biParams);

	/**
	 * City source analysis
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> portraitSource(BiParams biParams);

	/**
	 * Product category sales analysis
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> contractProductRanKing(BiParams biParams);

	/**
	 * Contract Amount Ranking
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> contractRanKing(BiParams biParams);

	/**
	 * Repayment Amount Ranking
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> receivablesRanKing(BiParams biParams);

	/**
	 * Signed contract leaderboard
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> contractCountRanKing(BiParams biParams);

	/**
	 * Product sales rankings
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> productCountRanKing(BiParams biParams);

	/**
	 * Added a ranking of the number of customers
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> customerCountRanKing(BiParams biParams);

	/**
	 * Added contact leaderboard
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> contactsCountRanKing(BiParams biParams);

	/**
	 * Leaderboard
	 *
	 * @param biParams params
	 * @return data
	 */
	JSONObject ranking(BiParams biParams);

	/**
	 * Follow up the ranking of the number of customers
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> customerGenjinCountRanKing(BiParams biParams);

	/**
	 * Follow up leaderboard
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> recordCountRanKing(BiParams biParams);

	/**
	 * Ranking of the number of business trips
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> travelCountRanKing(BiParams biParams);


}
