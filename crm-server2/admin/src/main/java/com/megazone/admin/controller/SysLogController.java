package com.megazone.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import com.megazone.admin.entity.BO.QuerySysLogBO;
import com.megazone.admin.entity.PO.LoginLog;
import com.megazone.admin.entity.PO.SysLog;
import com.megazone.admin.service.ISysLogService;
import com.megazone.core.common.ModelType;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.utils.ExcelParseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adminSysLog")
@Api(tags = "")
@Slf4j
public class SysLogController {

	@Autowired
	private ISysLogService sysLogService;

	@PostMapping("/saveSysLog")
	public Result saveSysLog(@RequestBody SysLog sysLog) {
		sysLogService.saveSysLog(sysLog);
		return Result.ok();
	}

	@PostMapping("/querySysLogPageList")
	@ApiOperation("Query system log list")
	public Result<BasePage<SysLog>> querySysLogPageList(@RequestBody QuerySysLogBO querySysLogBO) {
		BasePage<SysLog> page = sysLogService.querySysLogPageList(querySysLogBO);
		return Result.ok(page);
	}

	@PostMapping("/exportSysLog")
	@ApiOperation("Export system log")
	public void exportSysLog(@RequestBody QuerySysLogBO querySysLogBO, HttpServletResponse response) {
		querySysLogBO.setPageType(0);
		BasePage<SysLog> page = sysLogService.querySysLogPageList(querySysLogBO);
		List<Map<String, Object>> objectList = page.getList().stream().map(obj -> {
			Map<String, Object> map = BeanUtil.beanToMap(obj);
			map.remove("id");
			map.remove("className");
			map.remove("methodName");
			map.remove("args");
			map.remove("userId");
			map.put("model", ModelType.valueOfName((String) map.get("model")));
			return map;
		}).collect(Collectors.toList());
		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("realname", "User"));
		dataList.add(ExcelParseUtil.toEntity("createTime", "Time"));
		dataList.add(ExcelParseUtil.toEntity("ipAddress", "ipAddress"));
		dataList.add(ExcelParseUtil.toEntity("model", "module"));
		dataList.add(ExcelParseUtil.toEntity("subModel", "Submodule"));
		dataList.add(ExcelParseUtil.toEntity("behavior", "behavior"));
		dataList.add(ExcelParseUtil.toEntity("object", "object"));
		dataList.add(ExcelParseUtil.toEntity("detail", "operation details"));
		ExcelParseUtil.exportExcel(objectList, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "system log";
			}
		}, dataList);
	}

	@PostMapping("/saveLoginLog")
	public Result saveLoginLog(@RequestBody LoginLog loginLog) {
		sysLogService.saveLoginLog(loginLog);
		return Result.ok();
	}

	@PostMapping("/queryLoginLogPageList")
	@ApiOperation("Query login log list")
	public Result<BasePage<LoginLog>> queryLoginLogPageList(@RequestBody QuerySysLogBO querySysLogBO) {
		BasePage<LoginLog> page = sysLogService.queryLoginLogPageList(querySysLogBO);
		return Result.ok(page);
	}

	@PostMapping("/exportLoginLog")
	@ApiOperation("Export login log")
	public void exportLoginLog(@RequestBody QuerySysLogBO querySysLogBO, HttpServletResponse response) {
		querySysLogBO.setPageType(0);
		BasePage<LoginLog> page = sysLogService.queryLoginLogPageList(querySysLogBO);
		List<Map<String, Object>> objectList = page.getList().stream().map(obj -> {
			Map<String, Object> map = BeanUtil.beanToMap(obj);
			map.remove("id");
			map.remove("userId");
			map.remove("failResult");
			map.put("authResult", obj.getAuthResult() == 1 ? "Success" : "Failure");
			return map;
		}).collect(Collectors.toList());

		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("realname", "User"));
		dataList.add(ExcelParseUtil.toEntity("loginTime", "login time"));
		dataList.add(ExcelParseUtil.toEntity("ipAddress", "ipAddress"));
		dataList.add(ExcelParseUtil.toEntity("loginAddress", "login address"));
		dataList.add(ExcelParseUtil.toEntity("deviceType", "device type"));
		dataList.add(ExcelParseUtil.toEntity("core", "terminal core"));
		dataList.add(ExcelParseUtil.toEntity("platform", "platform"));
		dataList.add(ExcelParseUtil.toEntity("authResult", "Authentication Result"));
		ExcelParseUtil.exportExcel(objectList, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "Login log";
			}
		}, dataList);
	}


}

