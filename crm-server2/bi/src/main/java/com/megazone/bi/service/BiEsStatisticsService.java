package com.megazone.bi.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.utils.BiTimeUtil;

import java.util.List;

public interface BiEsStatisticsService {

	List<JSONObject> getStatisticsCustomerInfo(BiTimeUtil.BiTimeEntity timeEntity, boolean isNeedDealNum);

	List<JSONObject> mergeJsonObjectList(List<JSONObject> customerNumList, List<JSONObject> dealNumList);
}
