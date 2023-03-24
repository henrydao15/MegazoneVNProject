package com.megazone.oa.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.BO.LogBO;
import com.megazone.oa.entity.PO.OaLog;
import com.megazone.oa.entity.VO.OaBusinessNumVO;

import java.util.List;
import java.util.Map;

public interface IOaLogService extends BaseService<OaLog> {
	BasePage<JSONObject> queryList(LogBO bo);

	String getLogWelcomeSpeech();

	JSONObject queryLogBulletin();

	JSONObject queryCompleteStats(Integer type);

	BasePage<JSONObject> queryCompleteOaLogList(LogBO bo);

	BasePage<SimpleUser> queryIncompleteOaLogList(LogBO bo);

	void saveAndUpdate(JSONObject object);

	void deleteById(Integer logId);

	BasePage<JSONObject> queryLogBulletinByType(LogBO bo);

	List<JSONObject> queryLogRecordCount(Integer logId, Integer today);

	JSONObject queryById(Integer logId);

	List<Map<String, Object>> export(LogBO logBO);

	OaBusinessNumVO queryOaBusinessNum();


}
