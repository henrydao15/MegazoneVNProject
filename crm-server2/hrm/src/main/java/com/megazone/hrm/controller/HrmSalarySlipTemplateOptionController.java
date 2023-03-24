package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.PO.HrmSalarySlipTemplateOption;
import com.megazone.hrm.service.IHrmSalarySlipTemplateOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/hrmSalarySlipTemplateOption")
@Api(tags = "-")
public class HrmSalarySlipTemplateOptionController {

	@Autowired
	private IHrmSalarySlipTemplateOptionService slipTemplateOptionService;

	@PostMapping("/queryTemplateOptionByTemplateId/{templateId}")
	@ApiOperation("")
	public Result<List<HrmSalarySlipTemplateOption>> queryTemplateOptionByTemplateId(@PathVariable Integer templateId) {
		List<HrmSalarySlipTemplateOption> list = slipTemplateOptionService.queryTemplateOptionByTemplateId(templateId);
		return Result.ok(list);
	}

}

