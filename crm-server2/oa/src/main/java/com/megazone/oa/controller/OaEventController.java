package com.megazone.oa.controller;

import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.oa.common.log.OaEventLog;
import com.megazone.oa.entity.BO.*;
import com.megazone.oa.entity.VO.QueryEventByIdVO;
import com.megazone.oa.service.IOaEventService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/oaEvent")
@Api(tags = "Calendar-Agenda")
@SysLog(subModel = SubModelType.OA_CALENDAR, logClass = OaEventLog.class)
public class OaEventController {

	@Autowired
	private IOaEventService oaEventService;

	@PostMapping("/save")
	@ApiOperation("save schedule")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#setEventBO.event.title", detail = "'New schedule:'+#setEventBO.event.title")
	public Result save(@RequestBody SetEventBO setEventBO) {
		oaEventService.saveEvent(setEventBO);
		return Result.ok();
	}

	@PostMapping("/update")
	@ApiOperation("Modify schedule")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE, object = "#setEventBO.event.title", detail = "'Modified schedule:'+#setEventBO.event.title")
	public Result update(@RequestBody SetEventBO setEventBO) {
		oaEventService.updateEvent(setEventBO);
		return Result.ok();
	}

	@PostMapping("/delete")
	@ApiOperation("Delete schedule")
	@SysLogHandler(behavior = BehaviorEnum.DELETE, object = "", detail = "Deleted schedule")
	public Result delete(@RequestBody DeleteEventBO deleteEventBO) {
		oaEventService.delete(deleteEventBO);
		return Result.ok();
	}

	/**
	 * Check the schedule
	 */
	@PostMapping("/queryList")
	@ApiOperation("Query schedule list")
	public Result<List<OaEventDTO>> queryList(@RequestBody QueryEventListBO queryEventListBO) {
		List<OaEventDTO> oaEventList = oaEventService.queryList(queryEventListBO);
		return Result.ok(oaEventList);
	}

	@PostMapping("/queryListStatus")
	@ApiOperation("Query Small Calendar")
	public Result<Set<String>> queryListStatus(@RequestBody QueryEventListBO queryEventListBO) {
		Set<String> dateList = oaEventService.queryListStatus(queryEventListBO);
		return Result.ok(dateList);
	}

	@PostMapping("/queryById")
	@ApiOperation("Query schedule details")
	public Result<QueryEventByIdVO> queryById(@RequestBody QueryEventByIdBO queryEventByIdBO) {
		QueryEventByIdVO queryEventByIdVO = oaEventService.queryById(queryEventByIdBO);
		return R.ok(queryEventByIdVO);
	}
}
