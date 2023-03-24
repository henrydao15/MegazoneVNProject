package com.megazone.oa.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.core.utils.UserUtil;
import com.megazone.oa.entity.BO.QueryEventCrmBO;
import com.megazone.oa.entity.BO.QueryEventTaskBO;
import com.megazone.oa.entity.BO.UpdateTypeUserBO;
import com.megazone.oa.entity.PO.OaCalendarType;
import com.megazone.oa.entity.VO.EventTaskVO;
import com.megazone.oa.service.IOaCalendarTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Calendar Type Front Controller
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
@RestController
@RequestMapping("/oaCalendar")
@Api(tags = "Calendar")
public class OaCalendarTypeController {

	@Autowired
	private IOaCalendarTypeService calendarTypeService;

	@PostMapping("/addOrUpdate")
	@ApiOperation("Add/Update Calendar Type")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_OTHER_SETTINGS, behavior = BehaviorEnum.UPDATE, object = "Calendar Type Settings", detail = "Calendar Type Settings")
	public Result addOrUpdate(@RequestBody OaCalendarType oaCalendarType) {
		calendarTypeService.addOrUpdateType(oaCalendarType);
		return Result.ok();
	}


	@PostMapping("/delete/{typeId}")
	@ApiOperation("Delete Calendar Type")
	public Result delete(@PathVariable Integer typeId) {
		calendarTypeService.deleteType(typeId);
		return Result.ok();
	}

	@PostMapping("/queryTypeList")
	@ApiOperation("According to the query background calendar type list")
	public Result<List<OaCalendarType>> queryTypeList() {
		List<OaCalendarType> calendarTypeList = calendarTypeService.queryTypeList();
		return Result.ok(calendarTypeList);
	}

	/**
	 * List of personal calendar types based on query
	 */
	@PostMapping("/queryTypeListByUser")
	@ApiOperation("According to the query background calendar type list")
	public Result<List<OaCalendarType>> queryTypeListByUser(Long userId) {
		if (userId == null) {
			userId = UserUtil.getUserId();
		}
		List<OaCalendarType> calendarTypeList = calendarTypeService.queryTypeListByUser(userId);
		return Result.ok(calendarTypeList);
	}

	@PostMapping("/updateTypeUser")
	@ApiOperation("Modify the type of calendar displayed by the user")
	public Result updateTypeUser(@RequestBody UpdateTypeUserBO updateTypeUserBO) {
		calendarTypeService.updateTypeUser(updateTypeUserBO);
		return Result.ok();
	}


	@PostMapping("/eventTask")
	@ApiOperation("schedule task")
	public Result<List<EventTaskVO>> eventTask(@RequestBody QueryEventTaskBO eventTaskBO) {
		List<EventTaskVO> eventTaskVOList = calendarTypeService.eventTask(eventTaskBO);
		return Result.ok(eventTaskVOList);
	}

	/**
	 * crm statistics -- front-end display calendar usage
	 */
	@PostMapping("/eventCrm")
	@ApiOperation("schedule task")
	public Result<JsonNode> eventCrm(@RequestBody QueryEventCrmBO queryEventCrmBO) {
		JSONObject jsonObject = calendarTypeService.eventCrm(queryEventCrmBO);
		return Result.ok(jsonObject.node);
	}


	@PostMapping("/eventCustomer")
	@ApiOperation("schedule customer list")
	public Result<BasePage<Map<String, Object>>> eventCustomer(@RequestBody QueryEventCrmPageBO eventCrmPageBO) {
		BasePage<Map<String, Object>> page = calendarTypeService.eventCustomer(eventCrmPageBO);
		return Result.ok(page);
	}

	@PostMapping("/eventLeads")
	@ApiOperation("Agenda thread list")
	public Result<BasePage<Map<String, Object>>> eventLeads(@RequestBody QueryEventCrmPageBO eventCrmPageBO) {
		BasePage<Map<String, Object>> page = calendarTypeService.eventLeads(eventCrmPageBO);
		return Result.ok(page);
	}

	@PostMapping("/eventBusiness")
	@ApiOperation("Agenda Opportunity List")
	public Result<BasePage<Map<String, Object>>> eventBusiness(@RequestBody QueryEventCrmPageBO eventCrmPageBO) {
		BasePage<Map<String, Object>> page = calendarTypeService.eventBusiness(eventCrmPageBO);
		return Result.ok(page);
	}

	@PostMapping("/eventDealBusiness")
	@ApiOperation("List of expected transaction opportunities")
	public Result<BasePage<Map<String, Object>>> eventDealBusiness(@RequestBody QueryEventCrmPageBO eventCrmPageBO) {
		BasePage<Map<String, Object>> page = calendarTypeService.eventDealBusiness(eventCrmPageBO);
		return Result.ok(page);
	}

	@PostMapping("/eventContract")
	@ApiOperation("schedule customer list")
	public Result<BasePage<Map<String, Object>>> eventContract(@RequestBody QueryEventCrmPageBO eventCrmPageBO) {
		BasePage<Map<String, Object>> page = calendarTypeService.eventContract(eventCrmPageBO);
		return Result.ok(page);
	}
}
