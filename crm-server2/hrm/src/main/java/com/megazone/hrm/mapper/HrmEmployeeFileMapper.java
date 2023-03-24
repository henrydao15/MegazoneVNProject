package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmEmployeeFile;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmEmployeeFileMapper extends BaseMapper<HrmEmployeeFile> {


	Map<String, Object> queryFileNum(@Param("employeeId") Integer employeeId);
}
