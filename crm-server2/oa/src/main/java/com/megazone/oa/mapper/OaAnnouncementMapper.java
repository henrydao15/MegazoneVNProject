package com.megazone.oa.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.oa.entity.PO.OaAnnouncement;
import org.apache.ibatis.annotations.Param;

public interface OaAnnouncementMapper extends BaseMapper<OaAnnouncement> {
	public BasePage<OaAnnouncement> queryList(BasePage<OaAnnouncement> parse, @Param("type") Integer type, @Param("userId") Long userId, @Param("deptId") Integer deptId);
}
