package com.megazone.oa.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.oa.entity.PO.OaExamineField;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OaExamineFieldMapper extends BaseMapper<OaExamineField> {

	void deleteByChooseId(@Param("ids") List<Integer> arr, @Param("categoryId") Integer categoryId);

	void deleteByFieldValue(@Param("ids") List<Integer> arr, @Param("categoryId") Integer categoryId);
}
