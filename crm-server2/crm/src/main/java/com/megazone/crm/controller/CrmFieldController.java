package com.megazone.crm.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.feign.crm.entity.ExamineField;
import com.megazone.crm.common.ElasticUtil;
import com.megazone.crm.common.log.CrmFieldLog;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmField;
import com.megazone.crm.entity.PO.CrmRoleField;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.service.ICrmFieldService;
import com.megazone.crm.service.ICrmRoleFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-19
 */
@RestController
@RequestMapping("/crmField")
@Api(tags = "")
@SysLog(logClass = CrmFieldLog.class)
public class CrmFieldController {

	@Autowired
	private ICrmFieldService crmFieldService;

	@Autowired
	private ElasticsearchRestTemplate restTemplate;

	@Autowired
	private ICrmRoleFieldService crmRoleFieldService;

	@ApiOperation("")
	@RequestMapping(value = "/queryFields", method = RequestMethod.POST)
	public Result<List<CrmFieldsBO>> queryFields() {
		List<CrmFieldsBO> fieldsBOList = crmFieldService.queryFields();
		return Result.ok(fieldsBOList);
	}

	@ApiOperation("")
	@RequestMapping(value = "/saveField", method = RequestMethod.POST)
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.SAVE)
	public Result saveField(@RequestBody CrmFieldBO crmFieldBO) {
		crmFieldService.saveField(crmFieldBO);
		return Result.ok();
	}

	@ApiOperation("")
	@RequestMapping(value = "/list/{label}", method = RequestMethod.POST)
	public Result<List<List<CrmField>>> list(@PathVariable("label") Integer label) {
		List<List<CrmField>> crmFieldList = crmFieldService.queryFormPositionFieldList(label, true);
		return Result.ok(crmFieldList);
	}

	@ApiOperation("")
	@PostMapping(value = "/queryListHead/{label}")
	public Result<List<CrmFieldSortVO>> queryListHead(@NotNull @PathVariable("label") Integer label) {
		List<CrmFieldSortVO> voList = crmFieldService.queryListHead(label);
		return Result.ok(voList);
	}

	@ApiOperation("")
	@PostMapping(value = "/setFieldStyle")
	public Result setFieldStyle(@RequestBody CrmFieldStyleBO fieldStyle) {
		crmFieldService.setFieldStyle(fieldStyle);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping(value = "/setPoolFieldStyle")
	public Result setPoolFieldStyle(@RequestBody CrmFieldStyleBO fieldStyle) {
		crmFieldService.setPoolFieldStyle(fieldStyle);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping(value = "/setFieldConfig")
	public Result setFieldConfig(@RequestBody @Valid CrmFieldSortBO fieldSort) {
		crmFieldService.setFieldConfig(fieldSort);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping(value = "/queryFieldConfig")
	public Result<JsonNode> queryFieldConfig(@RequestParam("label") Integer label) {
		JSONObject object = crmFieldService.queryFieldConfig(label);
		return Result.ok(object.node);
	}

	@ApiOperation("")
	@PostMapping(value = "/verify")
	public Result<CrmFieldVerifyBO> verify(@RequestBody CrmFieldVerifyBO fieldVerifyBO) {
		CrmFieldVerifyBO verify = crmFieldService.verify(fieldVerifyBO);
		return Result.ok(verify);
	}

	@PostMapping(value = "/queryRoleField")
	@ApiOperation("")
	public Result<List<CrmRoleField>> queryRoleField(@RequestParam("roleId") Integer roleId, @RequestParam("label") Integer label) {
		List<CrmRoleField> crmRoleFields = crmRoleFieldService.queryRoleField(roleId, label);
		return R.ok(crmRoleFields);
	}

	@PostMapping(value = "/saveRoleField")
	@ApiOperation("")
	public Result saveRoleField(@RequestBody List<CrmRoleField> fields) {
		crmRoleFieldService.saveRoleField(fields);
		return R.ok();
	}


	@PostMapping(value = "/batchUpdateEsData")
	@ApiExplain("es")
	public Result batchUpdateEsData(@RequestParam("id") String id, @RequestParam("name") String name) {
		ElasticUtil.batchUpdateEsData(restTemplate.getClient(), "user", id, name);
		return R.ok();
	}

	@PostMapping("/queryExamineField")
	@ApiExplain("")
	public Result<List<ExamineField>> queryExamineField(@RequestParam("label") Integer label) {
		List<ExamineField> fieldList = crmFieldService.queryExamineField(label);
		return R.ok(fieldList);
	}

}

