package com.megazone.admin.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.admin.common.AdminRoleTypeEnum;
import com.megazone.admin.service.IAdminMenuService;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminMenu")
@Api(tags = "Menu Module")
public class AdminMenuController {

	@Autowired
	private IAdminMenuService adminMenuService;

	@RequestMapping("/getMenuListByType/{type}")
	@ApiOperation("Query menu by type")
	public Result<JsonNode> getMenuListByType(@PathVariable("type") Integer type) {
		AdminRoleTypeEnum typeEnum = AdminRoleTypeEnum.parse(type);
		JSONObject byType = adminMenuService.getMenuListByType(typeEnum);
		return Result.ok(byType.node);
	}

	@RequestMapping("/getWorkMenuList")
	@ApiOperation("Query project management menu")
	public Result<JsonNode> getWorkMenuList() {
		JSONObject byType = adminMenuService.getMenuListByType(AdminRoleTypeEnum.WORK);
		return Result.ok(byType.node);
	}

	@RequestMapping("/queryMenuId")
	public Result<Integer> queryMenuId(@RequestParam("realm1") String realm1, @RequestParam("realm2") String realm2,
									   @RequestParam("realm3") String realm3) {
		Integer menuId = adminMenuService.queryMenuId(realm1, realm2, realm3);
		return Result.ok(menuId);

	}
}
