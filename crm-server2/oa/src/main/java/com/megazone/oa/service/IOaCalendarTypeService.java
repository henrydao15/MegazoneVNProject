package com.megazone.oa.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.BO.QueryEventCrmBO;
import com.megazone.oa.entity.BO.QueryEventTaskBO;
import com.megazone.oa.entity.BO.UpdateTypeUserBO;
import com.megazone.oa.entity.PO.OaCalendarType;
import com.megazone.oa.entity.VO.EventTaskVO;

import java.util.List;
import java.util.Map;

public interface IOaCalendarTypeService extends BaseService<OaCalendarType> {

	void addOrUpdateType(OaCalendarType oaCalendarType);

	void deleteType(Integer typeId);

	List<OaCalendarType> queryTypeList();

	List<OaCalendarType> queryTypeListByUser(Long userId);

	void updateTypeUser(UpdateTypeUserBO updateTypeUserBO);

	List<EventTaskVO> eventTask(QueryEventTaskBO eventTaskBO);

	JSONObject eventCrm(QueryEventCrmBO queryEventCrmBO);

	List<String> queryFixedTypeByUserId(Long userId);

	BasePage<Map<String, Object>> eventCustomer(QueryEventCrmPageBO eventCrmPageBO);

	BasePage<Map<String, Object>> eventContract(QueryEventCrmPageBO eventCrmPageBO);

	BasePage<Map<String, Object>> eventLeads(QueryEventCrmPageBO eventCrmPageBO);

	BasePage<Map<String, Object>> eventBusiness(QueryEventCrmPageBO eventCrmPageBO);

	BasePage<Map<String, Object>> eventDealBusiness(QueryEventCrmPageBO eventCrmPageBO);
}
