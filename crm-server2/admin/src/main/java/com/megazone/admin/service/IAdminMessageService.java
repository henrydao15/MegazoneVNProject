package com.megazone.admin.service;

import com.megazone.admin.entity.BO.AdminMessageQueryBO;
import com.megazone.admin.entity.PO.AdminMessage;
import com.megazone.admin.entity.VO.AdminMessageVO;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.entity.AdminMessageBO;
import com.megazone.core.servlet.BaseService;

public interface IAdminMessageService extends BaseService<AdminMessage> {
	Long saveOrUpdateMessage(com.megazone.core.feign.admin.entity.AdminMessage message);

	BasePage<AdminMessage> queryList(AdminMessageQueryBO adminMessageBO);

	AdminMessageVO queryUnreadCount();

	void addMessage(AdminMessageBO adminMessageBO);

	void deleteEventMessage(Integer eventId);

	void deleteById(Integer messageId);

	void deleteByLabel(Integer label);
}
