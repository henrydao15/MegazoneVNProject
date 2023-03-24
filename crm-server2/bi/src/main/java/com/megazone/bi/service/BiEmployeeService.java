package com.megazone.bi.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.feign.crm.entity.BiParams;

import java.util.List;

public interface BiEmployeeService {
	List<JSONObject> contractNumStats(BiParams biParams);

	JSONObject totalContract(BiParams biParams);

	JSONObject invoiceStats(BiParams biParams);
}
