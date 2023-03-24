package com.megazone.oa.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.BO.SetExamineCategoryBO;
import com.megazone.oa.entity.BO.UpdateCategoryStatus;
import com.megazone.oa.entity.PO.OaExamineCategory;
import com.megazone.oa.entity.PO.OaExamineSort;
import com.megazone.oa.entity.VO.OaExamineCategoryVO;

import java.util.List;
import java.util.Map;

public interface IOaExamineCategoryService extends BaseService<OaExamineCategory> {

	Map<String, Integer> setExamineCategory(SetExamineCategoryBO setExamineCategoryBO);

	BasePage<OaExamineCategoryVO> queryExamineCategoryList(PageEntity pageEntity);

	List<OaExamineCategory> queryAllExamineCategoryList();

	void saveOrUpdateOaExamineSort(List<OaExamineSort> oaExamineSortList);

	void deleteExamineCategory(Integer id);

	void updateStatus(UpdateCategoryStatus updateCategoryStatus);
}
