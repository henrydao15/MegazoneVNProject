package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.RecursionUtil;
import com.megazone.hrm.entity.PO.HrmRecruitPostType;
import com.megazone.hrm.mapper.HrmRecruitPostTypeMapper;
import com.megazone.hrm.service.IHrmRecruitPostTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Service
public class HrmRecruitPostTypeServiceImpl extends BaseServiceImpl<HrmRecruitPostTypeMapper, HrmRecruitPostType> implements IHrmRecruitPostTypeService {

	@Override
	public List<HrmRecruitPostType> queryPostType() {
		List<HrmRecruitPostType> list = list();
		List<HrmRecruitPostType> childListTree = RecursionUtil.getChildListTree(list, "parentId", 0, "id", "children", HrmRecruitPostType.class);
		return childListTree;
	}
}
