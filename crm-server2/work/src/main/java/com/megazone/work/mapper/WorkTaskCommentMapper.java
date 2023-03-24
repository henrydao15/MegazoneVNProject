package com.megazone.work.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.work.entity.PO.WorkTaskComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-18
 */
public interface WorkTaskCommentMapper extends BaseMapper<WorkTaskComment> {

	@Select("SELECT * FROM `wk_oa_log` where log_id = #{logId}")
	public JSONObject queryOaLog(@Param("logId") Integer logId);

}
