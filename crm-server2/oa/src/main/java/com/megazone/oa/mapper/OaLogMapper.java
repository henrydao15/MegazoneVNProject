package com.megazone.oa.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.oa.entity.PO.OaLog;
import com.megazone.oa.entity.PO.OaLogBulletin;
import com.megazone.oa.entity.VO.OaBusinessNumVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OaLogMapper extends BaseMapper<OaLog> {
	BasePage<JSONObject> queryList(BasePage<JSONObject> parse, @Param("data") JSONObject data);

	JSONObject queryLogBulletin(@Param("userId") Long userId, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

	Integer queryCompleteStats(JSONObject object);

	BasePage<JSONObject> queryCompleteOaLogList(BasePage<JSONObject> page, @Param("data") JSONObject object);

	BasePage<JSONObject> queryLogBulletinByType(BasePage<JSONObject> page, @Param("data") JSONObject object);

	BasePage<SimpleUser> queryIncompleteOaLogList(BasePage<SimpleUser> page, @Param("data") JSONObject object);

	JSONObject queryBulletinByLog(@Param("userIds") List<Long> userIds);

	List<OaLogBulletin> queryBulletinByLogInfo(@Param("userIds") List<Long> userIds);

	List<JSONObject> queryLogRecordCount(@Param("typeIds") List<Integer> typeIds);

	JSONObject queryById(Integer logId);

	List<JSONObject> queryExportList(JSONObject object);

	List<JSONObject> queryCommentList(@Param("typeId") Integer typeId);

	OaBusinessNumVO queryOaBusinessNum(Map<String, Object> map);
}
