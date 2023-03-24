package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.BO.SetTaxRuleBO;
import com.megazone.hrm.entity.PO.HrmSalaryTaxRule;
import com.megazone.hrm.service.IHrmSalaryTaxRuleService;
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
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/hrmSalaryTaxRule")
@Api(tags = "-")
public class HrmSalaryTaxRuleController {

	@Autowired
	private IHrmSalaryTaxRuleService salaryTaxRuleService;

	@PostMapping("/queryTaxRuleList")
	@ApiOperation("")
	public Result<List<HrmSalaryTaxRule>> queryTaxRuleList() {
		List<HrmSalaryTaxRule> list = salaryTaxRuleService.queryTaxRuleList();
		return Result.ok(list);
	}

	@PostMapping("/setTaxRule")
	@ApiOperation("")
	public Result setTaxRule(@RequestBody SetTaxRuleBO setTaxRuleBO) {
		salaryTaxRuleService.setTaxRule(setTaxRuleBO);
		return Result.ok();
	}

}

