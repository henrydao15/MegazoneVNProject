package com.megazone.crm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CrmExamineRecord;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
public interface CrmExamineRecordMapper extends BaseMapper<CrmExamineRecord> {
	public JSONObject queryExamineRecordById(@Param("recordId") Integer recordId);

	public JSONObject queryInfoByRecordId(@Param("recordId") Integer recordId, @Param("tableName") String tableName);
}
