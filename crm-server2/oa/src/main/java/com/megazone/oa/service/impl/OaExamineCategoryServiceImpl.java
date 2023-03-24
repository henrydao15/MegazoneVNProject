package com.megazone.oa.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.oa.entity.BO.SetExamineCategoryBO;
import com.megazone.oa.entity.BO.UpdateCategoryStatus;
import com.megazone.oa.entity.PO.OaExamineCategory;
import com.megazone.oa.entity.PO.OaExamineField;
import com.megazone.oa.entity.PO.OaExamineSort;
import com.megazone.oa.entity.PO.OaExamineStep;
import com.megazone.oa.entity.VO.OaExamineCategoryVO;
import com.megazone.oa.mapper.OaExamineCategoryMapper;
import com.megazone.oa.mapper.OaExamineSortMapper;
import com.megazone.oa.service.IOaExamineCategoryService;
import com.megazone.oa.service.IOaExamineFieldService;
import com.megazone.oa.service.IOaExamineStepService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OaExamineCategoryServiceImpl extends BaseServiceImpl<OaExamineCategoryMapper, OaExamineCategory> implements IOaExamineCategoryService {

	@Autowired
	private IOaExamineFieldService examineFieldService;

	@Autowired
	private IOaExamineStepService examineStepService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private OaExamineCategoryMapper examineCategoryMapper;

	@Autowired
	private OaExamineSortMapper oaExamineSortMapper;

	@Override
	public Map<String, Integer> setExamineCategory(SetExamineCategoryBO setExamineCategoryBO) {
		OaExamineCategory oaExamineCategory = new OaExamineCategory();
		List<OaExamineStep> oaExamineSteps = new ArrayList<>();
		oaExamineCategory.setCategoryId(setExamineCategoryBO.getId());
		oaExamineCategory.setTitle(setExamineCategoryBO.getTitle());
		oaExamineCategory.setRemarks(setExamineCategoryBO.getRemarks());
		oaExamineCategory.setIcon(setExamineCategoryBO.getIcon());
		oaExamineCategory.setExamineType(setExamineCategoryBO.getExamineType());
		oaExamineCategory.setUpdateTime(new Date());
		if (setExamineCategoryBO.getUserIds() != null) {
			Set<Long> list = setExamineCategoryBO.getUserIds();
			oaExamineCategory.setUserIds(TagUtil.fromLongSet(list));
		}
		if (setExamineCategoryBO.getDeptIds() != null) {
			Set<Integer> list = setExamineCategoryBO.getDeptIds();
			oaExamineCategory.setDeptIds(TagUtil.fromSet(list));
		}
		List<SetExamineCategoryBO.Step> steps = setExamineCategoryBO.getStep();
		for (int i = 0; i < steps.size(); i++) {
			OaExamineStep oaExamineStep = new OaExamineStep();
			SetExamineCategoryBO.Step step = steps.get(i);
			if (step.getCheckUserId() != null) {
				oaExamineStep.setCheckUserId(TagUtil.fromLongSet(step.getCheckUserId()));
			}
			oaExamineStep.setStepNum(i + 1);
			oaExamineStep.setStepType(step.getStepType());
			oaExamineSteps.add(oaExamineStep);
		}
		Integer categoryId;
		if (oaExamineCategory.getCategoryId() == null) {
			oaExamineCategory.setIsSys(0);
			save(oaExamineCategory);
			categoryId = oaExamineCategory.getCategoryId();
			OaExamineField content = new OaExamineField();
			content.setName("Reason for approval");
			content.setFieldName("content");
			content.setMaxLength(0);
			content.setType(2);
			content.setIsNull(1);
			content.setUpdateTime(new Date());
			content.setOperating(1);
			content.setFieldType(1);
			content.setExamineCategoryId(categoryId);
			examineFieldService.save(content);
			content.setFieldId(null);
			content.setFieldName("remark");
			content.setIsNull(0);
			content.setName("Remarks");
			examineFieldService.save(content);
		} else {

			Integer oldCategoryId = oaExamineCategory.getCategoryId();
			OaExamineCategory newOaExamineCategory = getById(oldCategoryId);
			Integer isSys = newOaExamineCategory.getIsSys();
			Integer type = newOaExamineCategory.getType();
			deleteExamineCategory(oldCategoryId);
			BeanUtil.copyProperties(oaExamineCategory, newOaExamineCategory, "createTime");
			newOaExamineCategory.setIsSys(isSys);
			newOaExamineCategory.setType(type);
			newOaExamineCategory.setUpdateTime(new Date());
			save(newOaExamineCategory);
			categoryId = newOaExamineCategory.getCategoryId();
			List<OaExamineField> examineFields = examineFieldService.lambdaQuery().eq(OaExamineField::getExamineCategoryId, oldCategoryId).list();
			examineFields.forEach(field -> {
				field.setFieldId(null);
				field.setExamineCategoryId(categoryId);
			});
			examineFieldService.saveBatch(examineFields, 50);
		}
		//Set the approval step
		if (oaExamineCategory.getExamineType() == 1 && oaExamineSteps.size() != 0) {
			examineStepService.lambdaUpdate().eq(OaExamineStep::getCategoryId, categoryId).remove();
			oaExamineSteps.forEach(x -> {
				x.setCategoryId(categoryId);
			});
			examineStepService.saveBatch(oaExamineSteps, 50);
		}
		Map<String, Integer> map = new HashMap<>();
		map.put("categoryId", categoryId);
		return map;
	}

	@Override
	public BasePage<OaExamineCategoryVO> queryExamineCategoryList(PageEntity pageEntity) {
		BasePage<OaExamineCategoryVO> pageVO = new BasePage<>(pageEntity.getPage(), pageEntity.getLimit());
		BasePage<OaExamineCategory> page = lambdaQuery().eq(OaExamineCategory::getIsDeleted, 0).orderByDesc(OaExamineCategory::getCreateTime)
				.orderByAsc(OaExamineCategory::getIsSys).page(pageEntity.parse());
		List<OaExamineCategoryVO> list = new ArrayList<>();
		page.getList().forEach(category -> {
			List<OaExamineStep> stepList = examineStepService.lambdaQuery().eq(OaExamineStep::getCategoryId, category.getCategoryId()).list();
			stepList.forEach(step -> {
				Set<Long> checkUserIds = TagUtil.toLongSet(step.getCheckUserId());
				if (CollUtil.isNotEmpty(checkUserIds)) {
					List<SimpleUser> listResult = UserCacheUtil.getSimpleUsers(checkUserIds);
					step.setUserList(listResult);
				} else {
					step.setUserList(new ArrayList<>());
				}
			});
			category.setStepList(stepList);
			Set<Long> userIds = TagUtil.toLongSet(category.getUserIds());
			if (CollUtil.isNotEmpty(userIds)) {
				List<SimpleUser> listResult = UserCacheUtil.getSimpleUsers(userIds);
				category.setUserList(listResult);
			} else {
				category.setUserList(new ArrayList<>());
			}
			Set<Integer> deptIds = TagUtil.toSet(category.getDeptIds());
			if (CollUtil.isNotEmpty(deptIds)) {
				Result<List<SimpleDept>> listResult = adminService.queryDeptByIds(deptIds);
				category.setDeptList(listResult.getData());
			} else {
				category.setDeptList(new ArrayList<>());
			}
			OaExamineCategoryVO oaExamineCategoryVO = new OaExamineCategoryVO();
			BeanUtils.copyProperties(category, oaExamineCategoryVO);
			oaExamineCategoryVO.setUserList(category.getUserList());
			oaExamineCategoryVO.setDeptList(category.getDeptList());
			list.add(oaExamineCategoryVO);
		});
		pageVO.setRecords(list);
		pageVO.setTotal(page.getTotal());
		pageVO.setPageNumber(page.getPageNumber());
		pageVO.setSize(page.getSize());
		return pageVO;
	}

	@Override
	public List<OaExamineCategory> queryAllExamineCategoryList() {
		UserInfo user = UserUtil.getUser();
		return examineCategoryMapper.queryAllExamineCategoryList(UserUtil.isAdmin(), user.getUserId(), user.getDeptId());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdateOaExamineSort(List<OaExamineSort> oaExamineSortList) {
		LambdaQueryWrapper<OaExamineSort> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(OaExamineSort::getUserId, UserUtil.getUserId());
		oaExamineSortMapper.delete(lambdaQueryWrapper);
		for (int i = 0; i < oaExamineSortList.size(); i++) {
			OaExamineSort oaExamineSort = oaExamineSortList.get(i);
			oaExamineSort.setUserId(UserUtil.getUserId());
			oaExamineSort.setSort(i);
			oaExamineSortMapper.insert(oaExamineSort);
		}
	}

	@Override
	public void deleteExamineCategory(Integer id) {
		lambdaUpdate().set(OaExamineCategory::getIsDeleted, 1).set(OaExamineCategory::getDeleteUserId, UserUtil.getUserId()).set(OaExamineCategory::getDeleteTime, new Date())
				.eq(OaExamineCategory::getCategoryId, id).update();
	}

	@Override
	public void updateStatus(UpdateCategoryStatus updateCategoryStatus) {
		lambdaUpdate().set(OaExamineCategory::getStatus, updateCategoryStatus.getStatus()).eq(OaExamineCategory::getCategoryId, updateCategoryStatus.getId()).update();
	}
}
