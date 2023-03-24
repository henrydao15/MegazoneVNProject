package com.megazone.hrm.controller.config;

import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.hrm.common.log.HrmConfigLog;
import com.megazone.hrm.constant.ConfigType;
import com.megazone.hrm.entity.BO.AddEmployeeFieldBO;
import com.megazone.hrm.entity.BO.AddInsuranceSchemeBO;
import com.megazone.hrm.entity.BO.DeleteRecruitChannelBO;
import com.megazone.hrm.entity.BO.SetAchievementTableBO;
import com.megazone.hrm.entity.PO.*;
import com.megazone.hrm.entity.VO.AchievementTableVO;
import com.megazone.hrm.entity.VO.FiledListVO;
import com.megazone.hrm.entity.VO.InsuranceSchemeListVO;
import com.megazone.hrm.entity.VO.InsuranceSchemeVO;
import com.megazone.hrm.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hrmConfig")
@Api(tags = "")
@SysLog(logClass = HrmConfigLog.class)
public class HrmConfigController {

	@Autowired
	private IHrmConfigService configService;

	@Autowired
	private IHrmEmployeeFieldService employeeFieldService;

	@Autowired
	private IHrmInsuranceSchemeService insuranceSchemeService;

	@Autowired
	private IHrmAchievementTableService achievementTableService;

	@Autowired
	private IHrmRecruitChannelService recruitChannelService;

	@Autowired
	private IHrmEmployeeService employeeService;

	@Autowired
	private IHrmRecruitCandidateService recruitCandidateService;


	/**
	 * -----------------------------
	 */

	@PostMapping("/saveRecruitChannel")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "Save recruit channel settings", detail = "Save recruit channel settings")
	public Result saveRecruitChannel(@RequestBody List<HrmRecruitChannel> channelList) {
		recruitChannelService.saveOrUpdateBatch(channelList);
		return Result.ok();
	}


	@PostMapping("/queryRecruitChannelList")
	@ApiOperation("")
	public Result<List<HrmRecruitChannel>> queryRecruitChannelList() {
		List<HrmRecruitChannel> list = recruitChannelService.list();
		return Result.ok(list);
	}

	@PostMapping("/deleteRecruitChannel")
	@ApiOperation("")
	public Result deleteRecruitChannel(@RequestBody DeleteRecruitChannelBO deleteRecruitChannelBO) {
		employeeService.lambdaUpdate()
				.set(HrmEmployee::getChannelId, deleteRecruitChannelBO.getChangeChannelId())
				.eq(HrmEmployee::getChannelId, deleteRecruitChannelBO.getDeleteChannelId())
				.update();
		recruitCandidateService.lambdaUpdate()
				.set(HrmRecruitCandidate::getChannelId, deleteRecruitChannelBO.getChangeChannelId())
				.eq(HrmRecruitCandidate::getChannelId, deleteRecruitChannelBO.getDeleteChannelId())
				.update();
		recruitChannelService.removeById(deleteRecruitChannelBO.getDeleteChannelId());
		return Result.ok();
	}

	/**
	 * -----------------------------
	 */
	@PostMapping("/saveRecruitEliminate")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "Save reason for elimination settings", detail = "Save reason for elimination settings")
	public Result saveRecruitEliminate(@RequestBody List<String> data) {
		configService.lambdaUpdate().eq(HrmConfig::getType, ConfigType.ELIMINATION_REASONS.getValue()).remove();
		List<HrmConfig> collect = data.stream().map(value -> {
			HrmConfig hrmConfig = new HrmConfig();
			hrmConfig.setType(ConfigType.ELIMINATION_REASONS.getValue());
			hrmConfig.setValue(value);
			return hrmConfig;
		}).collect(Collectors.toList());
		configService.saveBatch(collect);
		return Result.ok();
	}

	@PostMapping("/queryRecruitEliminateList")
	@ApiOperation("")
	public Result<List<String>> queryRecruitEliminateList() {
		List<String> list = configService.lambdaQuery().eq(HrmConfig::getType, ConfigType.ELIMINATION_REASONS.getValue()).list()
				.stream().map(HrmConfig::getValue).collect(Collectors.toList());
		return Result.ok(list);
	}


	/**
	 * -----------------------------
	 */
	@PostMapping("/queryFields")
	@ApiOperation("")
	public Result<List<FiledListVO>> queryFields() {
		List<FiledListVO> fieldList = employeeFieldService.queryFields();
		return Result.ok(fieldList);
	}


	@PostMapping("/queryFieldByLabel/{labelGroup}")
	@ApiOperation("")
	public Result<List<List<HrmEmployeeField>>> queryFieldByLabel(@ApiParam("1  2  7  11 ") @PathVariable("labelGroup") Integer labelGroup) {
		List<List<HrmEmployeeField>> data = employeeFieldService.queryFieldByLabel(labelGroup);
		return Result.ok(data);
	}

	@PostMapping("/saveField")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.UPDATE)
	public Result saveField(@RequestBody AddEmployeeFieldBO addEmployeeFieldBO) {
		employeeFieldService.saveField(addEmployeeFieldBO);
		return Result.ok();
	}

	/**
	 * -----------------------------
	 */
	@PostMapping("/addInsuranceScheme")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.SAVE, object = "#addInsuranceSchemeBO.schemeName", detail = "'Added new insurance scheme: '+#addInsuranceSchemeBO.schemeName")
	public Result addInsuranceScheme(@Valid @RequestBody AddInsuranceSchemeBO addInsuranceSchemeBO) {
		insuranceSchemeService.setInsuranceScheme(addInsuranceSchemeBO);
		return Result.ok();
	}

	@PostMapping("/setInsuranceScheme")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "#addInsuranceSchemeBO.schemeName", detail = "'Added new insurance scheme: '+#addInsuranceSchemeBO.schemeName")
	public Result setInsuranceScheme(@Valid @RequestBody AddInsuranceSchemeBO addInsuranceSchemeBO) {
		insuranceSchemeService.setInsuranceScheme(addInsuranceSchemeBO);
		return Result.ok();
	}


	@PostMapping("/deleteInsuranceScheme/{schemeId}")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.DELETE)
	public Result deleteInsuranceScheme(@PathVariable("schemeId") Integer schemeId) {
		insuranceSchemeService.deleteInsuranceScheme(schemeId);
		return Result.ok();
	}

	@PostMapping("/queryInsuranceSchemePageList")
	@ApiOperation("")
	public Result<BasePage<InsuranceSchemeListVO>> queryInsuranceSchemePageList(@RequestBody PageEntity pageEntity) {
		BasePage<InsuranceSchemeListVO> page = insuranceSchemeService.queryInsuranceSchemePageList(pageEntity);
		return Result.ok(page);
	}

	@PostMapping("/queryInsuranceSchemeById/{schemeId}")
	@ApiOperation("")
	public Result<InsuranceSchemeVO> queryInsuranceSchemeById(@PathVariable("schemeId") Integer schemeId) {
		InsuranceSchemeVO insuranceSchemeVO = insuranceSchemeService.queryInsuranceSchemeById(schemeId);
		return Result.ok(insuranceSchemeVO);
	}

	/**
	 * -----------------------------
	 */
	@PostMapping("/setAchievementTable")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_HUMAN_RESOURCE_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "#setAchievementTableBO.tableName", detail = "'Modify assessment template: '+#setAchievementTableBO.tableName")
	public Result<HrmAchievementTable> setAchievementTable(@Valid @RequestBody SetAchievementTableBO setAchievementTableBO) {
		HrmAchievementTable achievementTable = achievementTableService.setAchievementTable(setAchievementTableBO);
		return Result.ok(achievementTable);
	}


	@PostMapping("/queryAchievementTableById/{tableId}")
	@ApiOperation("")
	public Result<AchievementTableVO> queryAchievementTableById(@PathVariable Integer tableId) {
		AchievementTableVO achievementTableVO = achievementTableService.queryAchievementTableById(tableId);
		return Result.ok(achievementTableVO);
	}


	@PostMapping("/queryAchievementTableList")
	@ApiOperation("")
	public Result<List<HrmAchievementTable>> queryAchievementTableList() {
		List<HrmAchievementTable> list = achievementTableService.queryAchievementTableList();
		return Result.ok(list);
	}

}
