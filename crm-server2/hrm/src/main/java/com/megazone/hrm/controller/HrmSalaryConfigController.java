package com.megazone.hrm.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.megazone.core.common.Result;
import com.megazone.hrm.entity.PO.HrmSalaryConfig;
import com.megazone.hrm.entity.VO.QueryInItConfigVO;
import com.megazone.hrm.service.IHrmSalaryConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/hrmSalaryConfig")
@Api(tags = "-")
public class HrmSalaryConfigController {

	@Autowired
	private IHrmSalaryConfigService salaryConfigService;

	@PostMapping("/queryInItConfig")
	@ApiOperation("/")
	public Result<QueryInItConfigVO> queryInItConfig() {
		QueryInItConfigVO queryInItConfigVO = salaryConfigService.queryInItConfig();
		return Result.ok(queryInItConfigVO);
	}


	@PostMapping("/updateInitStatus/{type}")
	@ApiOperation("")
	public Result updateInitStatus(@PathVariable Integer type) {
		salaryConfigService.updateInitStatus(type);
		return Result.ok();
	}

	@PostMapping("/saveInitConfig")
	@ApiOperation("")
	public Result saveInitConfig(@RequestBody HrmSalaryConfig salaryConfig) {
		salaryConfigService.saveInitConfig(salaryConfig);
		return Result.ok();
	}


	@PostMapping("/querySalaryConfig")
	@ApiOperation("")
	public Result<HrmSalaryConfig> querySalaryConfig() {
		HrmSalaryConfig salaryConfig = salaryConfigService.getOne(Wrappers.emptyWrapper());
		return Result.ok(salaryConfig);
	}

	@PostMapping("/updateSalaryConfig")
	@ApiOperation("")
	public Result updateSalaryConfig(@RequestBody HrmSalaryConfig salaryConfig) {
		HrmSalaryConfig salaryConfig1 = salaryConfigService.getOne(Wrappers.emptyWrapper());
		if (salaryConfig1 != null) {
			salaryConfigService.lambdaUpdate()
					.set(HrmSalaryConfig::getSocialSecurityMonthType, salaryConfig.getSocialSecurityMonthType())
					.eq(HrmSalaryConfig::getConfigId, salaryConfig1.getConfigId()).update();
		}
		return Result.ok();
	}

}

