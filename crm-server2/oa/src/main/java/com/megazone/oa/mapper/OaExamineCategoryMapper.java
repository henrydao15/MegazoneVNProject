package com.megazone.oa.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.oa.entity.PO.OaExamineCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OaExamineCategoryMapper extends BaseMapper<OaExamineCategory> {

	List<OaExamineCategory> queryAllExamineCategoryList(@Param("isAdmin") boolean isAdmin, @Param("userId") Long userId, @Param("deptId") Integer deptId);
}
