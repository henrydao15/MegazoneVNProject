package com.megazone.bi.controller;


import com.megazone.bi.entity.BO.AchievementBO;
import com.megazone.bi.entity.PO.CrmAchievement;
import com.megazone.bi.service.ICrmAchievementService;
import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLogHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biAchievement")
@Api(tags = "Performance Target Controller")
public class CrmAchievementController {

	@Autowired
	private ICrmAchievementService achievementService;

	@PostMapping("/queryAchievementList")
	@ApiOperation("Query performance target")
	public Result<List<CrmAchievement>> queryAchievementList(AchievementBO crmAchievement) {
		List<CrmAchievement> crmAchievements = achievementService.queryAchievementList(crmAchievement);
		return Result.ok(crmAchievements);
	}

	@PostMapping("/addAchievement")
	@ApiOperation("Save performance target")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "Save performance target", detail = "Save performance target")
	public Result addAchievement(@RequestBody CrmAchievement crmAchievement) {
		achievementService.addAchievement(crmAchievement);
		return Result.ok();
	}

	@PostMapping("/setAchievement")
	@ApiOperation("Modify performance target")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "Modify performance target", detail = "Modify performance target")
	public Result setAchievement(@RequestBody List<CrmAchievement> crmAchievement) {
		achievementService.verifyCrmAchievementData(crmAchievement);
		achievementService.updateBatchById(crmAchievement);
		return Result.ok();
	}

	@PostMapping("/deleteAchievement")
	@ApiOperation("Delete performance target")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.DELETE, object = "Delete performance target", detail = "Delete performance target")
	public Result deleteAchievement(@RequestParam("achievementId") Integer achievementId) {
		CrmAchievement byId = achievementService.getById(achievementId);
		if (byId != null) {
			achievementService.removeById(achievementId);
		}
		return Result.ok();
	}
}
