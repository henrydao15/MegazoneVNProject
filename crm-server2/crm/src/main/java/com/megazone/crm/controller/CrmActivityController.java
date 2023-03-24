package com.megazone.crm.controller;


import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.crm.common.AuthUtil;
import com.megazone.crm.common.log.CrmActivityLog;
import com.megazone.crm.constant.CrmActivityEnum;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmActivityBO;
import com.megazone.crm.entity.PO.CrmActivity;
import com.megazone.crm.entity.VO.CrmActivityVO;
import com.megazone.crm.service.ICrmActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * crm
 * </p>
 *
 * @author
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/crmActivity")
@Api(tags = "")
@SysLog(logClass = CrmActivityLog.class)
public class CrmActivityController {

	@Autowired
	private ICrmActivityService crmActivityService;

	@PostMapping("/getCrmActivityPageList")
	@ApiOperation("")
	public Result<CrmActivityVO> getCrmActivityPageList(@RequestBody CrmActivityBO crmActivityBO) {
		CrmActivityVO pageList = crmActivityService.getCrmActivityPageList(crmActivityBO);
		return Result.ok(pageList);
	}

	@PostMapping("/addCrmActivityRecord")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.FOLLOW_UP)
	public Result addCrmActivityRecord(@RequestBody @Valid CrmActivity crmActivity) {
		boolean auth = AuthUtil.isCrmAuth(CrmEnum.parse(crmActivity.getActivityType()), crmActivity.getActivityTypeId(), CrmAuthEnum.READ);
		if (auth) {
			return R.noAuth();
		}
		crmActivityService.addCrmActivityRecord(crmActivity);
		return R.ok();
	}

	@PostMapping("/addActivity")
	@ApiExplain("()")
	public Result addActivity(@RequestParam("type") Integer type, @RequestParam("activityType") Integer activityType, @RequestParam("activityTypeId") Integer activityTypeId) {
		crmActivityService.addActivity(type, CrmActivityEnum.parse(activityType), activityTypeId);
		return R.ok();
	}

	/**
	 * @param activityId
	 */
	@PostMapping("/deleteCrmActivityRecord/{activityId}")
	@ApiOperation("")
	public Result deleteCrmActivityRecord(@PathVariable("activityId") Integer activityId) {
		crmActivityService.deleteCrmActivityRecord(activityId);
		return Result.ok();
	}

	/**
	 *
	 */
	@PostMapping("/updateActivityRecord")
	@ApiOperation("")
	public Result<CrmActivity> updateActivityRecord(@RequestBody CrmActivity crmActivity) {
		CrmActivity data = crmActivityService.updateActivityRecord(crmActivity);
		return Result.ok(data);
	}

	@PostMapping("/outworkSign")
	@ApiOperation("")
	public Result outworkSign(CrmActivity crmActivity) {
		crmActivityService.outworkSign(crmActivity);
		return R.ok();
	}


	@PostMapping("/queryOutworkStats")
	@ApiOperation("app")
	public Result<BasePage<JSONObject>> queryOutworkStats(PageEntity pageEntity, String startTime, String endTime) {
		BasePage<JSONObject> basePage = crmActivityService.queryOutworkStats(pageEntity, startTime, endTime);
		return R.ok(basePage);
	}

	@PostMapping("/queryOutworkList")
	@ApiOperation("app")
	public Result<BasePage<CrmActivity>> queryOutworkList(PageEntity pageEntity, String startTime, String endTime, Long userId) {
		BasePage<CrmActivity> basePage = crmActivityService.queryOutworkList(pageEntity, startTime, endTime, userId);
		return R.ok(basePage);
	}


	@PostMapping("/queryPictureSetting")
	@ApiOperation("app")
	public Result<Integer> queryPictureSetting() {
		Integer integer = crmActivityService.queryPictureSetting();
		return R.ok(integer);
	}


	@PostMapping("/setPictureSetting")
	@ApiOperation("app")
	public Result setPictureSetting(Integer status) {
		crmActivityService.setPictureSetting(status);
		return R.ok();
	}


	@PostMapping("/deleteOutworkSign")
	@ApiOperation("")
	public Result deleteOutworkSign(@RequestParam("activityId") Integer activityId) {
		crmActivityService.deleteOutworkSign(activityId);
		return R.ok();
	}

}

