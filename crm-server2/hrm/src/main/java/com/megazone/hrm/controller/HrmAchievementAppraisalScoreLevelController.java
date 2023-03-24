package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.PO.HrmAchievementAppraisalScoreLevel;
import com.megazone.hrm.service.IHrmAchievementAppraisalScoreLevelService;
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
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmAchievementAppraisalScoreLevel")
@Api(tags = "-")
public class HrmAchievementAppraisalScoreLevelController {

	@Autowired
	private IHrmAchievementAppraisalScoreLevelService scoreLevelService;

	@PostMapping("/queryScoreLevelList/{employeeAppraisalId}")
	@ApiOperation("")
	public Result<List<HrmAchievementAppraisalScoreLevel>> queryScoreLevelList(@PathVariable Integer employeeAppraisalId) {
		List<HrmAchievementAppraisalScoreLevel> levelList = scoreLevelService.queryScoreLevelList(employeeAppraisalId);
		return Result.ok(levelList);
	}

	@PostMapping("/queryScoreLevelListByAppraisalId/{appraisalId}")
	@ApiOperation("")
	public Result<List<HrmAchievementAppraisalScoreLevel>> queryScoreLevelListByAppraisalId(@PathVariable Integer appraisalId) {
		List<HrmAchievementAppraisalScoreLevel> levelList = scoreLevelService.queryScoreLevelListByAppraisalId(appraisalId);
		return Result.ok(levelList);
	}


}

