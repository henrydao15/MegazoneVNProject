package com.megazone.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.admin.common.AdminCodeEnum;
import com.megazone.admin.common.AdminConst;
import com.megazone.admin.common.AdminModuleEnum;
import com.megazone.admin.common.log.AdminConfigLog;
import com.megazone.admin.entity.BO.AdminCompanyBO;
import com.megazone.admin.entity.BO.AdminInitDataBO;
import com.megazone.admin.entity.BO.LogWelcomeSpeechBO;
import com.megazone.admin.entity.BO.ModuleSettingBO;
import com.megazone.admin.entity.PO.AdminConfig;
import com.megazone.admin.entity.PO.AdminModelSort;
import com.megazone.admin.entity.PO.AdminUserConfig;
import com.megazone.admin.entity.VO.ModuleSettingVO;
import com.megazone.admin.service.IAdminConfigService;
import com.megazone.admin.service.IAdminModelSortService;
import com.megazone.admin.service.IAdminUserConfigService;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adminConfig")
@Api(tags = "System Configuration Interface")
@SysLog(logClass = AdminConfigLog.class)
public class AdminConfigController {

	@Autowired
	private IAdminConfigService adminConfigService;


	@Autowired
	private IAdminUserConfigService adminUserConfigService;

	@Autowired
	private IAdminModelSortService adminModelSortService;


	/**
	 * Set system configuration
	 */
	@ApiOperation(value = "Set Enterprise Configuration")
	@PostMapping("/setAdminConfig")
	@SysLogHandler(subModel = SubModelType.ADMIN_COMPANY_HOME, behavior = BehaviorEnum.UPDATE, object = "Enterprise Homepage Configuration", detail = "'Enterprise Homepage Configuration:'+#adminCompanyBO.companyName")
	public Result setAdminConfig(@RequestBody AdminCompanyBO adminCompanyBO) {
		adminConfigService.setAdminConfig(adminCompanyBO);
		return Result.ok();
	}

	@ApiOperation(value = "Query enterprise configuration")
	@PostMapping("/queryAdminConfig")
	public Result<AdminCompanyBO> queryAdminConfig() {
		return R.ok(adminConfigService.queryAdminConfig());
	}


	@ApiOperation(value = "Header Settings")
	@PostMapping("/queryHeaderModelSort")
	public Result<List<String>> queryHeaderModelSort() {
		List<AdminModelSort> list = adminModelSortService.lambdaQuery().select(AdminModelSort::getModel)
				.eq(AdminModelSort::getType, 1)
				.eq(AdminModelSort::getUserId, UserUtil.getUserId())
				.list();
		return Result.ok(list.stream().map(AdminModelSort::getModel).collect(Collectors.toList()));
	}

	@ApiOperation(value = "Header Settings")
	@PostMapping("/setHeaderModelSort")
	public Result setHeaderModelSort(@RequestBody List<String> list) {
		List<AdminModelSort> modelSortList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			AdminModelSort adminModelSort = new AdminModelSort();
			adminModelSort.setType(1).setModel(list.get(i)).setSort(i).setIsHidden(0).setUserId(UserUtil.getUserId());
			modelSortList.add(adminModelSort);
		}
		LambdaQueryWrapper<AdminModelSort> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(AdminModelSort::getType, 1).eq(AdminModelSort::getUserId, UserUtil.getUserId());
		adminModelSortService.remove(wrapper);
		adminModelSortService.saveBatch(modelSortList, Const.BATCH_SAVE_SIZE);
		return R.ok();
	}

	@ApiOperation(value = "Set Active Advisory Status")
	@PostMapping("/setMarketing")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_OTHER_SETTINGS, behavior = BehaviorEnum.UPDATE, object = "Activity Consulting Settings", detail = "Activity Consulting Settings")
	public Result setMarketing(@RequestParam("status") Integer status) {
		adminConfigService.setMarketing(status);
		return R.ok();
	}


	@ApiOperation(value = "Query activity consultation status")
	@PostMapping("/queryMarketing")
	public Result queryMarketing() {
		return R.ok(adminConfigService.queryMarketing());
	}

	@ApiOperation(value = "Query enterprise module configuration")
	@PostMapping("/queryModuleSetting")
	public Result<List<ModuleSettingVO>> queryModuleSetting() {
		return R.ok(adminConfigService.queryModuleSetting());
	}

	@ApiOperation(value = "Set Enterprise Module")
	@PostMapping("/setModuleSetting")
	@SysLogHandler(subModel = SubModelType.ADMIN_OTHER_SETTINGS, behavior = BehaviorEnum.UPDATE)
	public Result setModuleSetting(@Valid @RequestBody ModuleSettingBO moduleSetting) {
		AdminConfig adminConfig = adminConfigService.getById(moduleSetting.getSettingId());
		if (AdminModuleEnum.CRM.getValue().equals(adminConfig.getName())) {
			return R.error(AdminCodeEnum.ADMIN_MODULE_CLOSE_ERROR);
		}
		adminConfig.setStatus(moduleSetting.getStatus());
		adminConfigService.setModuleSetting(adminConfig);
		return Result.ok();
	}

	@ApiOperation(value = "Set log welcome message")
	@PostMapping("/setLogWelcomeSpeech")
	@SysLogHandler(subModel = SubModelType.ADMIN_OTHER_SETTINGS, behavior = BehaviorEnum.UPDATE, object = "Set log welcome", detail = "Set log welcome")
	public Result setLogWelcomeSpeech(@Valid @RequestBody List<String> stringList) {
		adminConfigService.setLogWelcomeSpeech(stringList);
		return Result.ok();
	}

	/**
	 * Get the list of welcome words in the log
	 */
	@ApiOperation(value = "Get log welcome message")
	@PostMapping("/getLogWelcomeSpeechList")
	public Result<List<LogWelcomeSpeechBO>> getLogWelcomeSpeechList() {
		List<LogWelcomeSpeechBO> adminConfigs = adminConfigService.getLogWelcomeSpeechList();
		return R.ok(adminConfigs);
	}

	/**
	 * delete configuration data
	 */
	@ApiOperation(value = "Delete configuration data")
	@PostMapping("/deleteConfigById")
	public Result deleteConfigById(@RequestBody @ApiParam(name = "settingId", value = "primary key ID", required = true) Integer settingId) {
		if (settingId == null) {
			return R.error(AdminCodeEnum.ADMIN_DATA_EXIST_ERROR);
		}
		adminConfigService.removeById(settingId);
		return R.ok();
	}

	/**
	 * Query call center settings
	 */
	@ApiOperation(value = "Query mobile phone module settings")
	@PostMapping("/queryCallModuleSetting")
	public Result<ModuleSettingVO> queryCallModuleSetting() {
		ModuleSettingVO moduleSettingVO = adminConfigService.queryCallModuleSetting();
		return R.ok(moduleSettingVO);
	}

	@ApiOperation(value = "Query custom configuration")
	@PostMapping("/queryCustomSetting/{customKey}")
	public Result<JSONArray> queryCustomSetting(@PathVariable("customKey") String customKey) {
		AdminUserConfig userConfig = adminUserConfigService.queryUserConfigByName(customKey);
		if (userConfig == null) {
			return Result.ok(new JSONArray());
		}
		return Result.ok(JSON.parseArray(userConfig.getValue()));
	}

	/**
	 * Modify mobile phone module settings
	 */

	@ApiOperation(value = "Modify custom configuration")
	@PostMapping("/setCustomSetting/{customKey}")
	public Result queryCustomSetting(@RequestBody JSONArray json, @PathVariable("customKey") String customKey) {
		AdminUserConfig userConfig = adminUserConfigService.queryUserConfigByName(customKey);
		if (userConfig != null) {
			userConfig.setValue(json.toJSONString());
			adminUserConfigService.updateById(userConfig);
		} else {
			userConfig = new AdminUserConfig();
			userConfig.setStatus(1);
			userConfig.setName(customKey);
			userConfig.setValue(json.toJSONString());
			userConfig.setUserId(UserUtil.getUserId());
			userConfig.setDescription("User-defined parameter settings");
			adminUserConfigService.save(userConfig);
		}
		return R.ok();
	}

	@ApiOperation(value = "Set follow-up record common phrases")
	@PostMapping("/setActivityPhrase")
	public Result setActivityPhrase(@RequestBody List<String> stringList) {
		String name = "ActivityPhrase";
		Long userId = UserUtil.getUserId();
		String description = "Follow-up record common words";
		adminUserConfigService.deleteUserConfigByName(name);
		List<AdminUserConfig> adminUserConfigList = new ArrayList<>(stringList.size());
		stringList.forEach(str -> {
			AdminUserConfig userConfig = new AdminUserConfig();
			userConfig.setStatus(1);
			userConfig.setName(name);
			userConfig.setValue(str);
			userConfig.setUserId(userId);
			userConfig.setDescription(description);
			adminUserConfigList.add(userConfig);
		});
		adminUserConfigService.saveBatch(adminUserConfigList, AdminConst.BATCH_SAVE_SIZE);
		return R.ok();
	}

	@ApiOperation(value = "Set follow-up record type")
	@PostMapping("/setRecordOptions")
	@SysLogHandler(subModel = SubModelType.ADMIN_OTHER_SETTINGS, behavior = BehaviorEnum.UPDATE, object = "Set follow-up record type", detail = "Set follow-up record type")
	public Result setRecordOptions(@RequestBody List<String> stringList) {
		String name = "followRecordOption";
		String description = "Follow up record options";
		adminConfigService.removeByMap(new JSONObject().fluentPut("name", name).getInnerMapObject());
		List<AdminConfig> adminUserConfigList = new ArrayList<>(stringList.size());
		stringList.forEach(str -> {
			AdminConfig userConfig = new AdminConfig();
			userConfig.setStatus(1);
			userConfig.setName(name);
			userConfig.setValue(str);
			userConfig.setDescription(description);
			adminUserConfigList.add(userConfig);
		});
		adminConfigService.saveBatch(adminUserConfigList, AdminConst.BATCH_SAVE_SIZE);
		return R.ok();
	}

	/**
	 * Common phrases for query and follow-up records
	 */
	@ApiOperation(value = "Query and follow-up records common phrases")
	@PostMapping("/queryActivityPhrase")
	public Result<List<String>> queryActivityPhrase() {
		String name = "ActivityPhrase";
		List<AdminUserConfig> adminConfigList = adminUserConfigService.queryUserConfigListByName(name);
		return Result.ok(adminConfigList.stream().map(AdminUserConfig::getValue).collect(Collectors.toList()));
	}

	@ApiExplain(value = "Query config configuration")
	@RequestMapping("/queryConfigByName")
	public Result<List<com.megazone.core.feign.admin.entity.AdminConfig>> queryConfigByName(@RequestParam("name") String name) {
		List<AdminConfig> adminConfigs = adminConfigService.queryConfigListByName(name);
		return Result.ok(adminConfigs.stream().map(config -> BeanUtil.copyProperties(config, com.megazone.core.feign.admin.entity.AdminConfig.class)).collect(Collectors.toList()));
	}

	@ApiExplain(value = "Query config configuration")
	@RequestMapping("/queryFirstConfigByName")
	public Result<com.megazone.core.feign.admin.entity.AdminConfig> queryFirstConfigByName(@RequestParam("name") String name, HttpServletRequest request) {
		AdminConfig config = adminConfigService.queryConfigByName(name);
		return Result.ok(BeanUtil.copyProperties(config, com.megazone.core.feign.admin.entity.AdminConfig.class));
	}

	@ApiExplain(value = "Modify config configuration")
	@PostMapping("/updateAdminConfig")
	public Result updateAdminConfig(@RequestBody AdminConfig adminConfig) {
		adminConfigService.updateAdminConfig(adminConfig);
		return R.ok();
	}

	@ApiExplain(value = "Query config configuration")
	@RequestMapping("/queryFirstConfigByNameAndValue")
	public Result<com.megazone.core.feign.admin.entity.AdminConfig> queryFirstConfigByNameAndValue(@RequestParam("name") String name, @RequestParam("value") String value) {
		AdminConfig config = adminConfigService.queryFirstConfigByNameAndValue(name, value);
		return Result.ok(BeanUtil.copyProperties(config, com.megazone.core.feign.admin.entity.AdminConfig.class));
	}

	/**
	 * Common phrases for query and follow-up records
	 */
	@ApiOperation(value = "Authenticate Password")
	@PostMapping("/verifyPassword")
	public Result<String> verifyPassword(@RequestBody AdminInitDataBO adminInitDataBO) {
		return Result.ok(adminConfigService.verifyPassword(adminInitDataBO));
	}


	/**
	 * Common phrases for query and follow-up records
	 */
	@ApiOperation(value = "module initialization")
	@PostMapping("/moduleInitData")
	public Result<Boolean> moduleInitData(@RequestBody AdminInitDataBO adminInitDataBO) {
		return Result.ok(adminConfigService.moduleInitData(adminInitDataBO));
	}
}
