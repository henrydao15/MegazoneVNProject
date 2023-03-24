package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.entity.BO.QuerySalaryListBO;
import com.megazone.hrm.entity.PO.HrmEmployeeSalaryCard;
import com.megazone.hrm.entity.PO.HrmEmployeeSocialSecurityInfo;
import com.megazone.hrm.entity.VO.QuerySalaryListVO;
import com.megazone.hrm.entity.VO.SalaryOptionHeadVO;
import com.megazone.hrm.entity.VO.SalarySocialSecurityVO;
import com.megazone.hrm.service.IHrmEmployeeSocialSecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/hrmEmployee/SocialSecurity")
@Api(tags = "-")
public class HrmEmployeeSocialSecurityController {

	@Autowired
	private IHrmEmployeeSocialSecurityService socialSecurityService;


	@PostMapping("/salarySocialSecurityInformation/{employeeId}")
	@ApiOperation("")
	public Result<SalarySocialSecurityVO> salarySocialSecurityInformation(@PathVariable("employeeId") Integer employeeId) {
		SalarySocialSecurityVO salarySocialSecurityVO = socialSecurityService.salarySocialSecurityInformation(employeeId);
		return Result.ok(salarySocialSecurityVO);
	}

	@PostMapping("/addSalaryCard")
	@ApiOperation("")
	public Result addSalaryCard(@RequestBody HrmEmployeeSalaryCard salaryCard) {
		socialSecurityService.addOrUpdateSalaryCard(salaryCard);
		return Result.ok();
	}

	@PostMapping("/setSalaryCard")
	@ApiOperation("")
	public Result setSalaryCard(@RequestBody HrmEmployeeSalaryCard salaryCard) {
		socialSecurityService.addOrUpdateSalaryCard(salaryCard);
		return Result.ok();
	}

	/**
	 *
	 */
	@PostMapping("/deleteSalaryCard/{salaryCardId}")
	@ApiOperation("")
	public Result deleteSalaryCard(@PathVariable("salaryCardId") Integer salaryCardId) {
		socialSecurityService.deleteSalaryCard(salaryCardId);
		return Result.ok();
	}

	@PostMapping("/addSocialSecurity")
	@ApiOperation("")
	public Result addSocialSecurity(@RequestBody HrmEmployeeSocialSecurityInfo socialSecurityInfo) {
		socialSecurityService.addOrUpdateSocialSecurity(socialSecurityInfo);
		return Result.ok();
	}

	@PostMapping("/setSocialSecurity")
	@ApiOperation("")
	public Result setSocialSecurity(@RequestBody HrmEmployeeSocialSecurityInfo socialSecurityInfo) {
		socialSecurityService.addOrUpdateSocialSecurity(socialSecurityInfo);
		return Result.ok();
	}

	@PostMapping("/deleteSocialSecurity/{socialSecurityInfoId}")
	@ApiOperation("")
	public Result deleteSocialSecurity(@PathVariable("socialSecurityInfoId") Integer socialSecurityInfoId) {
		socialSecurityService.deleteSocialSecurity(socialSecurityInfoId);
		return Result.ok();
	}

	@PostMapping("/querySalaryList")
	@ApiOperation("")
	public Result<BasePage<QuerySalaryListVO>> querySalaryList(@RequestBody QuerySalaryListBO querySalaryListBO) {
		BasePage<QuerySalaryListVO> page = socialSecurityService.querySalaryList(querySalaryListBO);
		return Result.ok(page);
	}

	@PostMapping("/querySalaryDetail/{sEmpRecordId}")
	@ApiOperation("")
	public Result<List<SalaryOptionHeadVO>> querySalaryDetail(@PathVariable String sEmpRecordId) {
		List<SalaryOptionHeadVO> salaryOptionHeadVOList = socialSecurityService.querySalaryDetail(sEmpRecordId);
		return Result.ok(salaryOptionHeadVOList);
	}


}

