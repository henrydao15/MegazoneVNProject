package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.BO.QueryInsuranceScaleBO;
import com.megazone.hrm.entity.BO.QueryInsuranceTypeBO;
import com.megazone.hrm.entity.PO.HrmInsuranceScheme;
import com.megazone.hrm.service.IHrmInsuranceSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmInsuranceScheme")
@Api(tags = "-")
public class HrmInsuranceSchemeController {

	@Autowired
	private IHrmInsuranceSchemeService insuranceSchemeService;

	@PostMapping("/queryInsuranceSchemeList")
	@ApiOperation("")
	public Result<List<HrmInsuranceScheme>> queryInsuranceSchemeList() {
		List<HrmInsuranceScheme> insuranceSchemeList = insuranceSchemeService.queryInsuranceSchemeList();
		return Result.ok(insuranceSchemeList);
	}

	@PostMapping("/queryInsuranceType")
	@ApiOperation("(100)")
	public Result<String> queryInsuranceType(@RequestBody QueryInsuranceTypeBO queryInsuranceTypeBO) {
		String str = insuranceSchemeService.queryInsuranceType(queryInsuranceTypeBO);
		return Result.ok(str);
	}

	@PostMapping("/queryInsuranceScale")
	@ApiOperation("(100)")
	public Result<String> queryInsuranceScale(@RequestBody QueryInsuranceScaleBO queryInsuranceScaleBO) {
		String str = insuranceSchemeService.queryInsuranceScale(queryInsuranceScaleBO);
		return Result.ok(str);
	}
}

