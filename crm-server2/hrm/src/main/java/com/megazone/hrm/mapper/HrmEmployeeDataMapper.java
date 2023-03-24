package com.megazone.hrm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmEmployeeData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmEmployeeDataMapper extends BaseMapper<HrmEmployeeData> {

	Integer verifyUnique(@Param("fieldId") Integer fieldId, @Param("value") String value, @Param("employeeId") Integer employeeId);

	public List<JSONObject> queryFiledListByEmployeeId(@Param("employeeId") Integer employeeId);
}
