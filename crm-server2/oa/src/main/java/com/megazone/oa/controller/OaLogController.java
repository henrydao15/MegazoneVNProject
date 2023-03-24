package com.megazone.oa.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.oa.common.log.OaLogLog;
import com.megazone.oa.entity.BO.LogBO;
import com.megazone.oa.entity.PO.OaLogRule;
import com.megazone.oa.entity.VO.OaBusinessNumVO;
import com.megazone.oa.service.IOaCommonService;
import com.megazone.oa.service.IOaLogRuleService;
import com.megazone.oa.service.IOaLogService;
import com.megazone.oa.service.IOaLogUserFavourService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oaLog")
@Api(tags = "Log module")
@Slf4j
@SysLog(subModel = SubModelType.OA_LOG, logClass = OaLogLog.class)
public class OaLogController {

	@Autowired
	private IOaLogService oaLogService;

	@Autowired
	private IOaLogRuleService oaLogRuleService;

	@Autowired
	private IOaCommonService oaCommonService;

	@Autowired
	private IOaLogUserFavourService oaLogUserFavourService;

	@PostMapping("/initOaData")
	@ApiExplain("Initialize log data")
	public Result<Boolean> initOaData() {
		return R.ok(oaCommonService.initOaData());
	}

	@PostMapping("/initCalendarData")
	@ApiExplain("Initialize calendar data")
	public Result<Boolean> initCalendarData() {
		return R.ok(oaCommonService.initCalendarData());
	}

	@PostMapping("/initOaExamineData")
	@ApiExplain("Initialize OA approval data")
	public Result<Boolean> initOaExamineData() {
		return R.ok(oaCommonService.initOaExamineData());
	}

	@PostMapping("/queryList")
	@ApiOperation("log list")
	public Result<BasePage<JSONObject>> queryList(@RequestBody LogBO bo) {
		BasePage<JSONObject> basePage = oaLogService.queryList(bo);
		return R.ok(basePage);
	}

	@PostMapping("/favourOrCancel")
	@ApiOperation("User likes or cancels")
	public Result<JsonNode> favourOrCancel(@RequestParam("isFavour") boolean isFavour, @RequestParam("logId") Integer logId) {
		return R.ok(oaLogUserFavourService.userFavourOrCancel(isFavour, logId).node);
	}

	/**
	 * Get the log welcome
	 */
	@PostMapping("/getLogWelcomeSpeech")
	@ApiOperation("Get log welcome message")
	public Result<String> getLogWelcomeSpeech() {
		String value = oaLogService.getLogWelcomeSpeech();
		return R.ok(value);
	}

	@PostMapping("/queryLogBulletin")
	@ApiOperation("Query log statistics")
	public Result<JsonNode> queryLogBulletin() {
		JSONObject object = oaLogService.queryLogBulletin();
		return R.ok(object.node);
	}

	@PostMapping("/queryCompleteStats")
	@ApiOperation("Query log completion statistics")
	public Result<JsonNode> queryCompleteStats(@RequestParam("type") Integer type) {
		JSONObject object = oaLogService.queryCompleteStats(type);
		return R.ok(object.node);
	}

	@PostMapping("/queryCompleteOaLogList")
	@ApiOperation("Query log completion statistics list")
	public Result<BasePage<JSONObject>> queryCompleteOaLogList(@RequestBody LogBO bo) {
		BasePage<JSONObject> basePage = oaLogService.queryCompleteOaLogList(bo);
		return R.ok(basePage);
	}

	@PostMapping("/queryIncompleteOaLogList")
	@ApiOperation("Query log incomplete statistics list")
	public Result<BasePage<SimpleUser>> queryIncompleteOaLogList(@RequestBody LogBO bo) {
		BasePage<SimpleUser> basePage = oaLogService.queryIncompleteOaLogList(bo);
		return R.ok(basePage);
	}

	@PostMapping("/addOrUpdate")
	@ApiOperation("Modify or add")
	@SysLogHandler
	public Result addOrUpdate(@RequestBody JSONObject jsonObject) {
		oaLogService.saveAndUpdate(jsonObject);
		return R.ok();
	}

	@PostMapping("/deleteById")
	@ApiOperation("Modify")
	@SysLogHandler(behavior = BehaviorEnum.DELETE, object = "", detail = "Deleted log")
	public Result deleteById(@RequestParam("logId") Integer logId) {
		oaLogService.deleteById(logId);
		return R.ok();
	}

	@PostMapping("/queryLogBulletinByType")
	@ApiOperation("Query log completion statistics list 2")
	public Result<BasePage<JSONObject>> queryLogBulletinByType(@RequestBody LogBO bo) {
		BasePage<JSONObject> basePage = oaLogService.queryLogBulletinByType(bo);
		return R.ok(basePage);
	}

	@PostMapping("/queryLogRecordCount")
	@ApiOperation("Query logging statistics")
	public Result<List<JSONObject>> queryLogRecordCount(Integer logId, Integer today) {
		List<JSONObject> objectList = oaLogService.queryLogRecordCount(logId, today);
		return R.ok(objectList);
	}

	@PostMapping("/queryById")
	@ApiOperation("Get logs based on log id")
	public Result<JsonNode> queryById(@RequestParam("logId") Integer logId) {
		JSONObject oaLog = oaLogService.queryById(logId);
		return Result.ok(oaLog.node);
	}

	@PostMapping("/export")
	@ApiOperation("Export")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export log", detail = "Export log")
	public void export(@RequestBody LogBO logBO, HttpServletResponse response) {
		List<Map<String, Object>> list = oaLogService.export(logBO);
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("category", "Log Type"));
		dataList.add(ExcelParseUtil.toEntity("createTime", "created date"));
		dataList.add(ExcelParseUtil.toEntity("createUserName", "Creator"));
		dataList.add(ExcelParseUtil.toEntity("sendName", "Send to"));
		dataList.add(ExcelParseUtil.toEntity("content", "Today's work content"));
		dataList.add(ExcelParseUtil.toEntity("tomorrow", "Tomorrow's work content"));
		dataList.add(ExcelParseUtil.toEntity("question", "Problem encountered"));
		dataList.add(ExcelParseUtil.toEntity("relateCrmWork", "Associated Business"));
		dataList.add(ExcelParseUtil.toEntity("comment", "Reply"));
		ExcelParseUtil.exportExcel(list, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "log";
			}
		}, dataList);
	}


	@PostMapping("/queryOaLogRuleList")
	@ApiOperation("Query log rules")
	public Result<List<OaLogRule>> queryOaLogRuleList() {
		List<OaLogRule> logRules = oaLogRuleService.queryOaLogRuleList();
		return Result.ok(logRules);
	}

	/**
	 * Set log commit settings
	 */
	@PostMapping("/setOaLogRule")
	@ApiOperation("Set log commit settings")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_OTHER_SETTINGS, behavior = BehaviorEnum.UPDATE, object = "Set log submission settings", detail = "Set log submission settings")
	public Result setOaLogRule(@RequestBody List<OaLogRule> ruleList) {
		oaLogRuleService.setOaLogRule(ruleList);
		return R.ok();
	}

	/**
	 * app side
	 *
	 * @return
	 */
	@PostMapping("/queryOaBusinessNum")
	@ApiOperation("A query quantity function on the app side")
	public Result<OaBusinessNumVO> queryOaBusinessNum() {
		OaBusinessNumVO businessNumVO = oaLogService.queryOaBusinessNum();
		return Result.ok(businessNumVO);
	}
}
