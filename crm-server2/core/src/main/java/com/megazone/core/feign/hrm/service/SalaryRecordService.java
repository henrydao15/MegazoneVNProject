package com.megazone.core.feign.hrm.service;

import com.megazone.core.common.Result;
import com.megazone.core.feign.hrm.entity.HrmSalaryMonthRecord;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "hrm", contextId = "salary")
public interface SalaryRecordService {

	@PostMapping("/hrmSalaryMonthRecord/querySalaryRecordById")
	@ApiOperation("id")
	Result<HrmSalaryMonthRecord> querySalaryRecordById(@RequestParam("sRecordId") Integer sRecordId);

	@PostMapping("/hrmSalaryMonthRecord/updateCheckStatus")
	@ApiOperation("id")
	Result updateCheckStatus(@RequestParam("sRecordId") Integer sRecordId, @RequestParam("checkStatus") Integer checkStatus);
}
