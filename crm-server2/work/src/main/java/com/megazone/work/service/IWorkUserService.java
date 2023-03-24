package com.megazone.work.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.work.entity.PO.WorkUser;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
public interface IWorkUserService extends BaseService<WorkUser> {
	/**
	 * @param userId ID
	 * @param workId ID
	 * @return data
	 */
	public Integer getRoleId(Long userId, Integer workId);
}
