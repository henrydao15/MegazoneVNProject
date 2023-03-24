package com.megazone.admin.service;

import com.megazone.admin.entity.BO.QuerySysLogBO;
import com.megazone.admin.entity.PO.LoginLog;
import com.megazone.admin.entity.PO.SysLog;
import com.megazone.core.entity.BasePage;

public interface ISysLogService {

	void saveSysLog(SysLog sysLog);

	void saveLoginLog(LoginLog loginLog);

	BasePage<SysLog> querySysLogPageList(QuerySysLogBO querySysLogBO);

	BasePage<LoginLog> queryLoginLogPageList(QuerySysLogBO querySysLogBO);
}
