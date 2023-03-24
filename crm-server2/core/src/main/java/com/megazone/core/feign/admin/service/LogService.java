package com.megazone.core.feign.admin.service;

import com.megazone.core.common.Result;
import com.megazone.core.common.log.SysLogEntity;
import com.megazone.core.feign.admin.entity.LoginLogEntity;
import com.megazone.core.feign.admin.service.impl.LogServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author
 */
@Component
@FeignClient(name = "admin", contextId = "log", fallback = LogServiceImpl.class)
public interface LogService {

	@PostMapping("/adminSysLog/saveSysLog")
	Result saveSysLog(@RequestBody SysLogEntity sysLogEntity);

	@PostMapping("/adminSysLog/saveLoginLog")
	Result saveLoginLog(@RequestBody LoginLogEntity loginLogEntity);
}
