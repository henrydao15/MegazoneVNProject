package com.megazone.crm.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmSceneConfigBO;
import com.megazone.crm.entity.PO.CrmScene;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.ICrmSceneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-06-06
 */
@RestController
@RequestMapping("/crmScene")
@Api(tags = "")
public class CrmSceneController {

	@Autowired
	private ICrmSceneService crmSceneService;

	@PostMapping("/queryScene")
	@ApiOperation("")
	public Result<List<CrmScene>> queryScene(@ApiParam(name = "type", value = "") @RequestParam("type") Integer type) {
		List<CrmScene> sceneList = crmSceneService.queryScene(CrmEnum.parse(type));
		return Result.ok(sceneList);
	}

	@PostMapping("/queryField")
	@ApiOperation("")
	public Result<List<CrmModelFiledVO>> queryField(@RequestParam("label") Integer label) {
		List<CrmModelFiledVO> filedVOS = crmSceneService.queryField(label);
		return Result.ok(filedVOS);
	}

	@PostMapping("/addScene")
	@ApiOperation("")
	public Result addScene(@RequestBody @Valid CrmScene adminScene) {
		crmSceneService.addScene(adminScene);
		return Result.ok();
	}

	@PostMapping("/updateScene")
	@ApiOperation("")
	public Result updateScene(@RequestBody @Valid CrmScene adminScene) {
		crmSceneService.updateScene(adminScene);
		return Result.ok();
	}

	@PostMapping("/setDefaultScene")
	@ApiOperation("")
	public Result setDefaultScene(@RequestParam("sceneId") Integer sceneId) {
		crmSceneService.setDefaultScene(sceneId);
		return Result.ok();
	}

	@PostMapping("/deleteScene")
	@ApiOperation("")
	public Result deleteScene(@RequestParam("sceneId") Integer sceneId) {
		crmSceneService.deleteScene(sceneId);
		return Result.ok();
	}


	@PostMapping("/querySceneConfig")
	@ApiOperation("")
	public Result<JsonNode> querySceneConfig(@RequestParam("type") Integer type) {
		JSONObject jsonObject = crmSceneService.querySceneConfig(type);
		return Result.ok(jsonObject.node);
	}


	@PostMapping("/sceneConfig")
	@ApiOperation("")
	public Result sceneConfig(@RequestBody CrmSceneConfigBO sceneConfig) {
		crmSceneService.sceneConfig(sceneConfig);
		return Result.ok();
	}
}

