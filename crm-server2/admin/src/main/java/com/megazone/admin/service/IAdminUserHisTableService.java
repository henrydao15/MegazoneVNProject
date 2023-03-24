package com.megazone.admin.service;

import com.megazone.admin.entity.PO.AdminUserHisTable;
import com.megazone.core.servlet.BaseService;

import java.util.List;

public interface IAdminUserHisTableService extends BaseService<AdminUserHisTable> {

	boolean authorize(List<Long> userIds, Integer status, Integer hisUse);

	Integer checkAuth();
}
