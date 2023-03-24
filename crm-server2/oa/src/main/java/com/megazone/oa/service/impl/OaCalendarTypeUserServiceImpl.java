package com.megazone.oa.service.impl;

import com.megazone.core.common.Result;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.oa.entity.PO.OaCalendarTypeUser;
import com.megazone.oa.mapper.OaCalendarTypeUserMapper;
import com.megazone.oa.service.IOaCalendarTypeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OaCalendarTypeUserServiceImpl extends BaseServiceImpl<OaCalendarTypeUserMapper, OaCalendarTypeUser> implements IOaCalendarTypeUserService {

	@Autowired
	private AdminService adminService;

	@Override
	public void saveSysCalendarType(Integer typeId) {
		Result<List<Long>> listResult = adminService.queryUserList(1);
		List<Long> userIds = listResult.getData();
		List<OaCalendarTypeUser> oaCalendarTypeUsers = new ArrayList<>();
		userIds.forEach(userId -> {
			OaCalendarTypeUser oaCalendarTypeUser = new OaCalendarTypeUser();
			oaCalendarTypeUser.setTypeId(typeId);
			oaCalendarTypeUser.setUserId(userId);
			oaCalendarTypeUsers.add(oaCalendarTypeUser);
		});
		saveBatch(oaCalendarTypeUsers);
	}
}
