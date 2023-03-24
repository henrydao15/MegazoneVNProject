package com.megazone.oa.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.PO.OaCalendarTypeUser;

public interface IOaCalendarTypeUserService extends BaseService<OaCalendarTypeUser> {

	void saveSysCalendarType(Integer typeId);
}
