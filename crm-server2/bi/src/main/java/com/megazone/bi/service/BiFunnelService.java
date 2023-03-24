package com.megazone.bi.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.BiParams;

import java.util.List;

public interface BiFunnelService {
	/**
	 * Sales funnel
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> sellFunnel(BiParams biParams);

	/**
	 * Added business opportunity analysis chart
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> addBusinessAnalyze(BiParams biParams);

	/**
	 * New business opportunity analysis table
	 *
	 * @param biParams params
	 * @return data
	 */
	BasePage<JSONObject> sellFunnelList(BiParams biParams);

	/**
	 * Business opportunity conversion rate analysis
	 *
	 * @param biParams params
	 * @return data
	 */
	List<JSONObject> win(BiParams biParams);

}
