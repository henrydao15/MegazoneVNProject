package com.megazone.examine.service.impl;

import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserUtil;
import com.megazone.examine.entity.PO.ExamineManagerUser;
import com.megazone.examine.mapper.ExamineManagerUserMapper;
import com.megazone.examine.service.IExamineManagerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-13
 */
@Service
public class ExamineManagerUserServiceImpl extends BaseServiceImpl<ExamineManagerUserMapper, ExamineManagerUser> implements IExamineManagerUserService {

	@Autowired
	private AdminService adminService;

	/**
	 * @param examineId ID
	 * @return data
	 */
	@Override
	public List<Long> queryExamineUserByPage(Long examineId) {
		List<ExamineManagerUser> managerUsers = lambdaQuery().eq(ExamineManagerUser::getExamineId, examineId).orderByAsc(ExamineManagerUser::getSort).list();
		return managerUsers.stream().map(ExamineManagerUser::getUserId).collect(Collectors.toList());
	}


	@Override
	public List<Long> queryExamineUser(Long examineId) {
		List<ExamineManagerUser> managerUsers = lambdaQuery().eq(ExamineManagerUser::getExamineId, examineId).orderByAsc(ExamineManagerUser::getSort).list();
		List<Long> userIds = managerUsers.stream().map(ExamineManagerUser::getUserId).collect(Collectors.toList());
		List<Long> userIdList = adminService.queryNormalUserByIds(userIds).getData();

		if (userIdList.size() == 0) {
			userIdList.add(UserUtil.getSuperUser());
		}
		return userIdList;
	}
}
