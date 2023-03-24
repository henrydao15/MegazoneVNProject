package com.megazone.hrm.controller;

import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.VO.*;
import com.megazone.hrm.service.IHrmAchievementAppraisalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmAchievementAppraisal")
@Api(tags = "-")
public class HrmAchievementAppraisalController {


	@Autowired
	private IHrmAchievementAppraisalService achievementAppraisalService;

	/**
	 *
	 */
	@PostMapping("/addAppraisal")
	@ApiOperation("")
	public Result addAppraisal(@Valid @RequestBody SetAppraisalBO setAppraisalBO) {
		achievementAppraisalService.addAppraisal(setAppraisalBO);
		return Result.ok();
	}

	@PostMapping("/setAppraisal")
	@ApiOperation("")
	public Result setAppraisal(@Valid @RequestBody SetAppraisalBO setAppraisalBO) {
		achievementAppraisalService.setAppraisal(setAppraisalBO);
		return Result.ok();
	}


	@PostMapping("/delete/{appraisalId}")
	@ApiOperation("")
	public Result deleteAppraisal(@PathVariable Integer appraisalId) {
		achievementAppraisalService.deleteAppraisal(appraisalId);
		return Result.ok();
	}

	@PostMapping("/stopAppraisal/{appraisalId}")
	@ApiOperation("")
	public Result stopAppraisal(@PathVariable Integer appraisalId) {
		achievementAppraisalService.stopAppraisal(appraisalId);
		return Result.ok();
	}

	@PostMapping("/updateAppraisalStatus")
	@ApiOperation("")
	public Result updateAppraisalStatus(@RequestBody UpdateAppraisalStatusBO updateAppraisalStatusBO) {
		achievementAppraisalService.updateAppraisalStatus(updateAppraisalStatusBO);
		return Result.ok();
	}

	@PostMapping("/queryAppraisalStatusNum")
	@ApiOperation("")
	public Result<Map<Integer, Long>> queryAppraisalStatusNum() {
		Map<Integer, Long> map = achievementAppraisalService.queryAppraisalStatusNum();
		return Result.ok(map);
	}

	@PostMapping("/queryAppraisalPageList")
	@ApiOperation("")
	public Result<BasePage<AppraisalPageListVO>> queryAppraisalPageList(@RequestBody QueryAppraisalPageListBO queryAppraisalPageListBO) {
		BasePage<AppraisalPageListVO> page = achievementAppraisalService.queryAppraisalPageList(queryAppraisalPageListBO);
		return Result.ok(page);
	}

	@PostMapping("/queryAppraisalById/{appraisalId}")
	@ApiOperation("")
	public Result<AppraisalInformationBO> queryAppraisalById(@PathVariable Integer appraisalId) {
		AppraisalInformationBO informationBO = achievementAppraisalService.queryAppraisalById(appraisalId);
		return Result.ok(informationBO);
	}


	@PostMapping("/queryEmployeeListByAppraisalId")
	@ApiOperation("id")
	public Result<BasePage<EmployeeListByAppraisalIdVO>> queryEmployeeListByAppraisalId(@RequestBody QueryEmployeeListByAppraisalIdBO employeeListByAppraisalIdBO) {
		BasePage<EmployeeListByAppraisalIdVO> page = achievementAppraisalService.queryEmployeeListByAppraisalId(employeeListByAppraisalIdBO);
		return Result.ok(page);
	}

	@PostMapping("/queryAppraisalEmployeeList")
	@ApiOperation("")
	public Result<BasePage<AppraisalEmployeeListVO>> queryAppraisalEmployeeList(@RequestBody QueryAppraisalEmployeeListBO employeeListBO) {
		BasePage<AppraisalEmployeeListVO> page = achievementAppraisalService.queryAppraisalEmployeeList(employeeListBO);
		return Result.ok(page);
	}

	@PostMapping("/queryEmployeeAppraisal")
	@ApiOperation("")
	public Result<BasePage<EmployeeAppraisalVO>> queryEmployeeAppraisal(@RequestBody QueryEmployeeAppraisalBO queryEmployeeAppraisalBO) {
		BasePage<EmployeeAppraisalVO> page = achievementAppraisalService.queryEmployeeAppraisal(queryEmployeeAppraisalBO);
		return Result.ok(page);
	}

	@PostMapping("/queryEmployeeAppraisalCount/{employeeId}")
	@ApiOperation("")
	public Result<Map<String, Object>> queryEmployeeAppraisalCount(@PathVariable Integer employeeId) {
		Map<String, Object> map = achievementAppraisalService.queryEmployeeAppraisalCount(employeeId);
		return Result.ok(map);
	}

	@PostMapping("/queryEmployeeDetail/{employeeAppraisalId}")
	@ApiOperation("")
	public Result<EmployeeAppraisalDetailVO> queryEmployeeDetail(@PathVariable Integer employeeAppraisalId) {
		EmployeeAppraisalDetailVO employeeAppraisalDetailVO = achievementAppraisalService.queryEmployeeDetail(employeeAppraisalId);
		return Result.ok(employeeAppraisalDetailVO);

	}

}

