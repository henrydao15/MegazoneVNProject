package com.megazone.oa.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.PO.OaAnnouncement;

public interface IOaAnnouncementService extends BaseService<OaAnnouncement> {
	void addOaAnnouncement(OaAnnouncement oaAnnouncement);

	void setOaAnnouncement(OaAnnouncement oaAnnouncement);

	void delete(Integer announcementId);

	OaAnnouncement queryById(Integer announcementId);

	BasePage<OaAnnouncement> queryList(PageEntity entity, Integer type);
}
