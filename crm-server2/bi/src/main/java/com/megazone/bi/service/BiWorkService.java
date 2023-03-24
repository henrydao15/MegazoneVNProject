package com.megazone.bi.service;

import com.megazone.bi.entity.VO.BiPageVO;
import com.megazone.bi.entity.VO.BiParamVO;
import com.megazone.core.common.JSONObject;
import com.megazone.core.feign.crm.entity.BiParams;

import java.util.List;

public interface BiWorkService {
	List<JSONObject> logStatistics(BiParams biParams);

	JSONObject examineStatistics(BiParams biParams);

	BiPageVO<JSONObject> examineInfo(BiParamVO biParams);
}
