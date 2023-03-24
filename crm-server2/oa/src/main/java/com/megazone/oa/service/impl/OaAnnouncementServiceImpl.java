package com.megazone.oa.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.Const;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminMessageBO;
import com.megazone.core.feign.admin.entity.AdminMessageEnum;
import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminMessageService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.SeparatorUtil;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.oa.common.OaCodeEnum;
import com.megazone.oa.entity.PO.OaAnnouncement;
import com.megazone.oa.mapper.OaAnnouncementMapper;
import com.megazone.oa.service.IOaAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OaAnnouncementServiceImpl extends BaseServiceImpl<OaAnnouncementMapper, OaAnnouncement> implements IOaAnnouncementService {

	@Autowired
	private AdminService adminService;

	@Override
	public void addOaAnnouncement(OaAnnouncement oaAnnouncement) {
		oaAnnouncement.setDeptIds(SeparatorUtil.fromString(oaAnnouncement.getDeptIds()));
		oaAnnouncement.setOwnerUserIds(SeparatorUtil.fromString(oaAnnouncement.getOwnerUserIds()));
		oaAnnouncement.setCreateTime(DateUtil.date());
		oaAnnouncement.setUpdateTime(DateUtil.date());
		save(oaAnnouncement);
		List<Long> ids = new ArrayList<>();
		if (StrUtil.isAllEmpty(oaAnnouncement.getOwnerUserIds(), oaAnnouncement.getDeptIds())) {
			ids.addAll(adminService.queryUserList(2).getData());
		} else {
			ids.addAll(StrUtil.splitTrim(oaAnnouncement.getOwnerUserIds(), Const.SEPARATOR).stream().map(Long::valueOf).collect(Collectors.toList()));
			List<Integer> deptIds = StrUtil.splitTrim(oaAnnouncement.getDeptIds(), Const.SEPARATOR).stream().map(Integer::valueOf).collect(Collectors.toList());
			if (deptIds.size() > 0) {
				ids.addAll(adminService.queryUserByDeptIds(deptIds).getData());
			}
			ids.add(UserUtil.getUserId());
		}
		if (ids.size() > 0) {
			AdminMessageBO adminMessageBO = new AdminMessageBO();
			adminMessageBO.setUserId(0L);
			adminMessageBO.setMessageType(AdminMessageEnum.OA_NOTICE_MESSAGE.getType());
			adminMessageBO.setTitle(oaAnnouncement.getTitle());
			adminMessageBO.setTypeId(oaAnnouncement.getAnnouncementId());
			adminMessageBO.setIds(ids);
			ApplicationContextHolder.getBean(AdminMessageService.class).sendMessage(adminMessageBO);
		}
	}

	@Override
	public void setOaAnnouncement(OaAnnouncement oaAnnouncement) {
		oaAnnouncement.setUpdateTime(DateUtil.date());
		updateById(oaAnnouncement);
	}

	@Override
	public void delete(Integer announcementId) {
		removeById(announcementId);
	}

	@Override
	public OaAnnouncement queryById(Integer announcementId) {
		OaAnnouncement announcement = getById(announcementId);
		if (announcement == null) {
			throw new CrmException(OaCodeEnum.ANNOUNCEMENT_ALREADY_DELETE);
		}
		List<SimpleDept> deptList = adminService.queryDeptByIds(TagUtil.toSet(announcement.getDeptIds())).getData();
		announcement.setDeptList(deptList);
		List<SimpleUser> userList = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(announcement.getOwnerUserIds()));
		announcement.setOwnerUserList(userList);
		return announcement;
	}

	@Override
	public BasePage<OaAnnouncement> queryList(PageEntity entity, Integer type) {
		UserInfo userInfo = UserUtil.getUser();
		return getBaseMapper().queryList(entity.parse(), type, userInfo.getUserId(), userInfo.getDeptId());
	}
}
