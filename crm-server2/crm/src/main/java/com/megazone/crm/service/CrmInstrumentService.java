package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.crm.entity.BO.CrmSearchParamsBO;
import com.megazone.crm.entity.PO.CrmActivity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface CrmInstrumentService {

	public JSONObject queryBulletin(BiParams biParams);

	public BasePage<Map<String, Object>> queryBulletinInfo(BiParams biParams);

	public JSONObject forgottenCustomerCount(BiParams biParams);

	public JSONObject sellFunnel(BiParams biParams);


	BasePage<Map<String, Object>> sellFunnelBusinessList(CrmSearchParamsBO crmSearchParamsBO);

	public JSONObject salesTrend(BiParams biParams);

	public JSONObject queryDataInfo(BiParams biParams);

	public JSONObject queryPerformance(BiParams biParams);

	BasePage<CrmActivity> queryRecordList(BiParams biParams);

	public List<Map<String, Object>> exportRecordList(BiParams biParams);

	List<JSONObject> queryRecordCount(BiParams biParams);

	BasePage<Map<String, Object>> forgottenCustomerPageList(BiParams biParams);

	BasePage<Map<String, Object>> unContactCustomerPageList(BiParams biParams);

	JSONObject importRecordList(MultipartFile file, Integer crmType);
}
