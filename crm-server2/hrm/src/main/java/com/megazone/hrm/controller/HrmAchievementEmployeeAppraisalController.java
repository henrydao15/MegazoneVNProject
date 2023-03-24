package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.common.EmployeeHolder;
import com.megazone.hrm.constant.achievement.EmployeeAppraisalStatus;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.VO.*;
import com.megazone.hrm.service.IHrmAchievementEmployeeAppraisalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequestMapping("/hrmAchievementEmployeeAppraisal")
@Api(tags = "-")
public class HrmAchievementEmployeeAppraisalController {

	@Autowired
	private IHrmAchievementEmployeeAppraisalService employeeAppraisalService;

	@PostMapping("/queryAppraisalNum")
	@ApiOperation("")
	public Result<Map<Integer, Integer>> queryAppraisalNum() {
		Map<Integer, Integer> map = employeeAppraisalService.queryAppraisalNum();
		return Result.ok(map);
	}

	@PostMapping("/queryMyAppraisal")
	@ApiOperation("")
	public Result<BasePage<QueryMyAppraisalVO>> queryMyAppraisal(@RequestBody BasePageBO basePageBO) {
		BasePage<QueryMyAppraisalVO> list = employeeAppraisalService.queryMyAppraisal(basePageBO);
		return Result.ok(list);
	}

	@PostMapping("/queryTargetConfirmList")
	@ApiOperation("")
	public Result<BasePage<TargetConfirmListVO>> queryTargetConfirmList(@RequestBody BasePageBO basePageBO) {
		BasePage<TargetConfirmListVO> page = employeeAppraisalService.queryTargetConfirmList(basePageBO);
		return Result.ok(page);
	}


	@PostMapping("/queryEvaluatoList")
	@ApiOperation("")
	public Result<BasePage<EvaluatoListVO>> queryEvaluatoList(@RequestBody EvaluatoListBO evaluatoListBO) {
		BasePage<EvaluatoListVO> page = employeeAppraisalService.queryEvaluatoList(evaluatoListBO);
		return Result.ok(page);
	}

	@PostMapping("/queryResultConfirmList")
	@ApiOperation("")
	public Result<BasePage<ResultConfirmListVO>> queryResultConfirmList(@RequestBody BasePageBO basePageBO) {
		BasePage<ResultConfirmListVO> page = employeeAppraisalService.queryResultConfirmList(basePageBO);
		return Result.ok(page);
	}


	@PostMapping("/queryEmployeeAppraisalDetail/{employeeAppraisalId}")
	@ApiOperation("")
	public Result<EmployeeAppraisalDetailVO> queryEmployeeAppraisalDetail(@PathVariable Integer employeeAppraisalId) {
		EmployeeAppraisalDetailVO employeeAppraisalDetailVO = employeeAppraisalService.queryEmployeeAppraisalDetail(employeeAppraisalId);
		return Result.ok(employeeAppraisalDetailVO);
	}

	@PostMapping("/writeAppraisal")
	@ApiOperation("")
	public Result writeAppraisal(@RequestBody WriteAppraisalBO writeAppraisalBO) {
		employeeAppraisalService.writeAppraisal(writeAppraisalBO);
		return Result.ok();
	}


	@PostMapping("/targetConfirm")
	@ApiOperation("")
	public Result targetConfirm(@RequestBody TargetConfirmBO targetConfirmBO) {
		employeeAppraisalService.targetConfirm(targetConfirmBO);
		return Result.ok();
	}


	@PostMapping("/resultEvaluato")
	@ApiOperation("")
	public Result resultEvaluato(@RequestBody ResultEvaluatoBO resultEvaluatoBO) {
		employeeAppraisalService.resultEvaluato(resultEvaluatoBO);
		return Result.ok();
	}


	@PostMapping("/queryResultConfirmByAppraisalId/{appraisalId}")
	@ApiOperation("")
	public Result<ResultConfirmByAppraisalIdVO> queryResultConfirmByAppraisalId(@PathVariable String appraisalId) {
		ResultConfirmByAppraisalIdVO resultConfirmByAppraisalIdVO = employeeAppraisalService.queryResultConfirmByAppraisalId(appraisalId);
		return Result.ok(resultConfirmByAppraisalIdVO);
	}


	@PostMapping("/updateScoreLevel")
	@ApiOperation("")
	public Result updateScoreLevel(@RequestBody UpdateScoreLevelBO updateScoreLevelBO) {
		employeeAppraisalService.updateScoreLevel(updateScoreLevelBO);
		return Result.ok();
	}

	@PostMapping("/resultConfirm/{appraisalId}")
	@ApiOperation("")
	public Result resultConfirm(@PathVariable String appraisalId) {
		employeeAppraisalService.resultConfirm(appraisalId);
		return Result.ok();
	}

	@PostMapping("/updateSchedule")
	@ApiOperation("")
	public Result updateSchedule(@RequestBody UpdateScheduleBO updateScheduleBO) {
		employeeAppraisalService.updateSchedule(updateScheduleBO);
		return Result.ok();
	}

	@PostMapping("/queryTargetConfirmScreen")
	@ApiOperation("")
	public Result<List<AchievementAppraisalVO>> queryTargetConfirmScreen() {
		List<AchievementAppraisalVO> list = employeeAppraisalService.queryTargetConfirmScreen(EmployeeHolder.getEmployeeId(), EmployeeAppraisalStatus.PENDING_CONFIRMATION.getValue());
		return Result.ok(list);
	}

	@PostMapping("/queryEvaluatoScreen")
	@ApiOperation("")
	public Result<List<AchievementAppraisalVO>> queryEvaluatoScreen(Integer status) {
		List<AchievementAppraisalVO> list = employeeAppraisalService.queryEvaluatoScreen(EmployeeHolder.getEmployeeId(), status);
		return Result.ok(list);
	}

	@PostMapping("/queryLevelIdByScore")
	@ApiOperation("id")
	public Result queryLevelIdByScore(@RequestBody QueryLevelIdByScoreBO queryLevelIdByScoreBO) {
		Integer levelId = employeeAppraisalService.queryLevelIdByScore(queryLevelIdByScoreBO);
		return Result.ok(levelId);
	}
}

