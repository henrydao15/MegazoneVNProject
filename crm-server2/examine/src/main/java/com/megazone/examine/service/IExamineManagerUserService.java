package com.megazone.examine.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.examine.entity.PO.ExamineManagerUser;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-13
 */
public interface IExamineManagerUserService extends BaseService<ExamineManagerUser> {

	/**
	 * @param examineId ID
	 * @return data
	 */
	public List<Long> queryExamineUserByPage(Long examineId);


	public List<Long> queryExamineUser(Long examineId);
}
