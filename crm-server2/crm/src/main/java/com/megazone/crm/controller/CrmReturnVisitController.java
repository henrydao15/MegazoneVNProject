package com.megazone.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.log.CrmReturnVisitLog;
import com.megazone.crm.entity.BO.CrmBusinessSaveBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.ICrmReturnVisitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/crmReturnVisit")
@Api(tags = "")
@SysLog(subModel = SubModelType.CRM_RETURN_VISIT, logClass = CrmReturnVisitLog.class)
public class CrmReturnVisitController {

	@Autowired
	private ICrmReturnVisitService crmReturnVisitService;

	@PostMapping("/queryPageList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO search) {
		search.setPageType(1);
		BasePage<Map<String, Object>> mapBasePage = crmReturnVisitService.queryPageList(search);
		return R.ok(mapBasePage);
	}

	@PostMapping("/querySimpleEntity")
	@ApiExplain("")
	public Result<List<SimpleCrmEntity>> querySimpleEntity(@RequestBody List<Integer> ids) {
		List<SimpleCrmEntity> crmEntities = crmReturnVisitService.querySimpleEntity(ids);
		return R.ok(crmEntities);
	}

	@PostMapping("/add")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#crmModel.entity[visitNumber]")
	public Result add(@RequestBody CrmBusinessSaveBO crmModel) {
		crmReturnVisitService.addOrUpdate(crmModel);
		return R.ok();
	}

	@PostMapping("/update")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result update(@RequestBody CrmBusinessSaveBO crmModel) {
		crmReturnVisitService.addOrUpdate(crmModel);
		return R.ok();
	}


	@PostMapping("/queryById/{visitId}")
	@ApiOperation("ID")
	public Result<CrmModel> queryById(@PathVariable("visitId") @ApiParam(name = "id", value = "id") Integer visitId) {
		CrmModel model = crmReturnVisitService.queryById(visitId);
		return R.ok(model);
	}

	@PostMapping("/field")
	@ApiOperation("")
	public Result<List> queryReturnVisitField(@RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmReturnVisitService.queryField(null));
		}
		return R.ok(crmReturnVisitService.queryFormPositionField(null));
	}

	@PostMapping("/field/{id}")
	@ApiOperation("")
	public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id,
								   @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmReturnVisitService.queryField(id));
		}
		return R.ok(crmReturnVisitService.queryFormPositionField(id));
	}

	/**
	 * @param visitId id
	 * @return data
	 */
	@PostMapping("/information/{id}")
	@ApiOperation("")
	public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer visitId) {
		List<CrmModelFiledVO> information = crmReturnVisitService.information(visitId);
		return R.ok(information);
	}


	@PostMapping("/queryFileList")
	@ApiOperation("")
	public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<FileEntity> fileEntities = crmReturnVisitService.queryFileList(id);
		return R.ok(fileEntities);
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteByIds(@ApiParam(name = "ids", value = "id") @RequestBody List<Integer> ids) {
		crmReturnVisitService.deleteByIds(ids);
		return R.ok();
	}

	@PostMapping("/queryReturnVisitRemindConfig")
	@ApiOperation("")
	public Result queryReturnVisitRemindConfig() {
		AdminConfig adminConfig = crmReturnVisitService.queryReturnVisitRemindConfig();
		return R.ok(adminConfig);
	}

	@PostMapping("/updateReturnVisitRemindConfig")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "Modify customer callback reminder settings", detail = "Modify customer callback reminder settings")
	public Result updateReturnVisitRemindConfig(Integer value, @RequestParam("status") Integer status) {
		crmReturnVisitService.updateReturnVisitRemindConfig(status, value);
		return R.ok();
	}

	@PostMapping("/updateInformation")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
		crmReturnVisitService.updateInformation(updateInformationBO);
		return R.ok();
	}
}

