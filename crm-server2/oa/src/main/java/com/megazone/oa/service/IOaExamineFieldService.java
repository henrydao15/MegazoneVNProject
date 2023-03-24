package com.megazone.oa.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.BO.ExamineFieldBO;
import com.megazone.oa.entity.PO.OaExamineField;

import java.util.List;
import java.util.Map;

public interface IOaExamineFieldService extends BaseService<OaExamineField> {

	List<OaExamineField> queryField(Integer id);

	List<List<OaExamineField>> queryFormPositionField(Integer id);

	Boolean updateFieldCategoryId(Long newCategoryId, Long oldCategoryId);

	Map<Integer, String> queryFieldData(String batchId);

	void transferFieldList(List<OaExamineField> recordList, Integer isDetail);

	void saveField(ExamineFieldBO examineFieldBO);

	void saveDefaultField(Long categoryId);
}
