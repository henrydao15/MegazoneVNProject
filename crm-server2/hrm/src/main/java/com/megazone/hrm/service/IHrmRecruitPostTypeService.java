package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmRecruitPostType;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmRecruitPostTypeService extends BaseService<HrmRecruitPostType> {

	/**
	 * @return
	 */
	List<HrmRecruitPostType> queryPostType();
}
