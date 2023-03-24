package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.hrm.entity.BO.SetSalaryOptionBO;
import com.megazone.hrm.entity.VO.SalaryOptionDetailVO;
import com.megazone.hrm.service.IHrmSalaryOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/hrmSalaryOption")
@Api(tags = "-")
public class HrmSalaryOptionController {

	@Autowired
	private IHrmSalaryOptionService salaryOptionService;

	@PostMapping("/querySalaryOptionDetail")
	@ApiOperation("")
	public Result<SalaryOptionDetailVO> querySalaryOptionDetail() {
		SalaryOptionDetailVO detailVO = salaryOptionService.querySalaryOptionDetail();
		return Result.ok(detailVO);
	}


	@PostMapping("/setSalaryOption")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "Payroll setup", detail = "Payroll setup")
	public Result setSalaryOption(@RequestBody SetSalaryOptionBO setSalaryOptionBO) {
		salaryOptionService.setSalaryOption(setSalaryOptionBO);
		return Result.ok();
	}

}
