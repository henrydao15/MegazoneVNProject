package com.megazone.core.feign.admin.service.impl;

import com.megazone.core.common.Result;
import com.megazone.core.common.log.SysLogEntity;
import com.megazone.core.feign.admin.entity.LoginLogEntity;
import com.megazone.core.feign.admin.service.LogService;
import org.springframework.stereotype.Component;

@Component
public class LogServiceImpl implements LogService {


	@Override
	public Result saveSysLog(SysLogEntity sysLogEntity) {
		return Result.ok();
	}

	@Override
	public Result saveLoginLog(LoginLogEntity loginLogEntity) {
		return Result.ok();
	}
}
