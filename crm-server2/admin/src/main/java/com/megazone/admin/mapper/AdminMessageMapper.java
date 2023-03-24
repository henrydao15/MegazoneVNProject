package com.megazone.admin.mapper;

import com.megazone.admin.entity.BO.AdminMessageQueryBO;
import com.megazone.admin.entity.PO.AdminMessage;
import com.megazone.admin.entity.VO.AdminMessageVO;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface AdminMessageMapper extends BaseMapper<AdminMessage> {
	BasePage<AdminMessage> queryList(BasePage<AdminMessage> parse, @Param("data") AdminMessageQueryBO adminMessageBO);

	AdminMessageVO queryUnreadCount(@Param("userId") Long userId);
}
