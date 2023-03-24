package com.megazone.work.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.work.entity.PO.WorkUser;
import com.megazone.work.mapper.WorkUserMapper;
import com.megazone.work.service.IWorkUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
@Service
public class WorkUserServiceImpl extends BaseServiceImpl<WorkUserMapper, WorkUser> implements IWorkUserService {

	/**
	 * @param userId ID
	 * @param workId ID
	 * @return data
	 */
	@Override
	public Integer getRoleId(Long userId, Integer workId) {
		WorkUser workUser = lambdaQuery().eq(WorkUser::getUserId, userId).eq(WorkUser::getWorkId, workId).last(" limit 1").one();
		return workUser != null ? workUser.getRoleId() : null;
	}
}
