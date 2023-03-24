package com.megazone.admin.controller;


import com.megazone.admin.entity.BO.AdminLanguagePackBO;
import com.megazone.admin.entity.VO.AdminLanguagePackVO;
import com.megazone.admin.entity.VO.AdminUserVO;
import com.megazone.admin.service.IAdminLanguagePackService;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/adminLanguagePack")
@Api(tags = "language pack related interface")
public class AdminLanguagePackController {

	@Autowired
	private IAdminLanguagePackService iAdminLanguagePackService;

	@ApiOperation("Pagination query language package list")
	@PostMapping("/queryLanguagePackList")
	public Result<BasePage<AdminLanguagePackVO>> queryLanguagePackList(@RequestBody AdminLanguagePackBO adminLanguagePackBO) {
		return R.ok(iAdminLanguagePackService.queryLanguagePackList(adminLanguagePackBO, 0));
	}

	@ApiOperation("Add or edit language pack")
	@PostMapping("/addOrUpdateLanguagePack")
	public Result<BasePage<AdminUserVO>> addOrUpdateLanguagePack(@RequestParam("file") MultipartFile file, @RequestParam("languagePackName") String languagePackName, @RequestParam(name = "languagePackId", required = false) Integer languagePackId) {
		AdminLanguagePackBO adminLanguagePackBO = new AdminLanguagePackBO();
		adminLanguagePackBO.setLanguagePackName(languagePackName);
		adminLanguagePackBO.setLanguagePackId(languagePackId);
		return iAdminLanguagePackService.addOrUpdateLanguagePack(file, adminLanguagePackBO);
	}

	@PostMapping("/deleteLanguagePackById")
	@ApiOperation("Remove language pack")
	public Result deleteLanguagePackById(@RequestParam("languagePackId") @ApiParam(name = "languagePackId", value = "languagePackId") Integer id) {
		iAdminLanguagePackService.deleteLanguagePackById(id);
		return R.ok();
	}

	@PostMapping("/exportLanguagePackById")
	@ApiOperation("Export language pack")
	public void exportLanguagePackById(@RequestParam("languagePackId") @ApiParam(name = "languagePackId", value = "languagePackId") Integer id, HttpServletResponse response) {

		iAdminLanguagePackService.exportLanguagePackById(id, response);
	}

	@PostMapping("/queryLanguagePackContextById")
	@ApiOperation("Query language pack field information")
	public Result queryLanguagePackContextById(@RequestParam("languagePackId") @ApiParam(name = "languagePackId", value = "languagePackId") Integer id) {
		return R.ok(iAdminLanguagePackService.queryLanguagePackContextById(id));
	}

	@PostMapping("/downloadExcel")
	@ApiOperation("Export Template")
	public void downloadExcel(HttpServletResponse response) {
		iAdminLanguagePackService.downloadExcel(response);
	}

	@ApiOperation("Edit language pack name")
	@PostMapping("/updateLanguagePackNameById")
	public Result updateLanguagePackNameById(@RequestBody AdminLanguagePackBO adminLanguagePackBO) {
		iAdminLanguagePackService.updateLanguagePackNameById(adminLanguagePackBO);
		return Result.ok();
	}

	@ApiOperation("Modify the default language pack configuration")
	@PostMapping("/setDeflautLanguagePackSetting")
	public Result setDeflautLanguagePackSetting(@RequestParam("languagePackId") @ApiParam(name = "languagePackId", value = "languagePackId") Integer id) {
		iAdminLanguagePackService.setDeflautLanguagePackSetting(id, 0);
		return R.ok();
	}

	@ApiOperation(value = "Query system default language")
	@PostMapping("/queryDeflautLanguagePackSetting")
	public JSONObject queryDeflautLanguagePackSetting() {
		return iAdminLanguagePackService.queryDeflautLanguagePackSetting(0);
	}
}

