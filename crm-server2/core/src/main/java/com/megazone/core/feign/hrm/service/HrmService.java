package com.megazone.core.feign.hrm.service;

import com.megazone.core.common.Result;
import com.megazone.core.feign.hrm.entity.HrmEmployee;
import com.megazone.core.feign.hrm.service.impl.HrmServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@Component
@FeignClient(name = "hrm", contextId = "hrm", fallback = HrmServiceImpl.class)
public interface HrmService {

	@PostMapping("/hrm/queryEmployeeListByIds")
	@ApiOperation("ids")
	Result<Set<HrmEmployee>> queryEmployeeListByIds(@RequestBody List<Integer> employeeIds);

	@PostMapping("/hrmJob/employeeChangeRecords")
	public void employeeChangeRecords();

	@PostMapping("/hrmEmployee/queryIsInHrm")
	@ApiOperation("")
	public Result<Boolean> queryIsInHrm();


}
