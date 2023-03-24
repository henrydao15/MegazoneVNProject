package com.megazone.bi.service;

import com.megazone.bi.entity.VO.ProductStatisticsVO;
import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.BiParams;

import java.util.List;
import java.util.Map;

public interface BiService {
	/**
	 * Product sales statistics
	 * startTime start time endTime end time userId user ID deptId department ID
	 */
	BasePage<ProductStatisticsVO> queryProductSell(BiParams biParams);

	List<Map<String, Object>> productSellExport(BiParams biParams);

	/**
	 * Obtain business intelligence performance target completion
	 *
	 * @author wyq
	 */
	List<JSONObject> taskCompleteStatistics(String year, Integer status, Integer deptId, Long userId, Integer isUser);

	List<Map<String, Object>> taskCompleteStatisticsExport(String year, Integer status, Integer deptId, Long userId, Integer isUser);
}
