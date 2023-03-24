package com.megazone.crm.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.megazone.core.common.JSON;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.cache.CrmCacheKey;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.LoginFromCookie;
import com.megazone.core.utils.BaseUtil;
import com.megazone.crm.common.log.CrmPrintTemplateLog;
import com.megazone.crm.entity.BO.CrmPrintTemplateBO;
import com.megazone.crm.entity.PO.CrmPrintRecord;
import com.megazone.crm.entity.PO.CrmPrintTemplate;
import com.megazone.crm.entity.VO.CrmPrintFieldVO;
import com.megazone.crm.service.ICrmPrintTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/crmPrint")
@Api(tags = "")
@Slf4j
@SysLog(logClass = CrmPrintTemplateLog.class)
public class CrmPrintTemplateController {

	@Autowired
	private ICrmPrintTemplateService printTemplateService;

	@ApiOperation("")
	@PostMapping("/queryPrintTemplateList")
	public Result<BasePage<CrmPrintTemplate>> queryPrintTemplateList(@RequestBody CrmPrintTemplateBO printTemplateBO) {
		BasePage<CrmPrintTemplate> adminPrintTemplateBasePage = printTemplateService.queryPrintTemplateList(printTemplateBO);
		return Result.ok(adminPrintTemplateBasePage);
	}

	@ApiOperation("ID")
	@PostMapping("/queryPrintTemplateById")
	public Result<CrmPrintTemplate> queryPrintTemplateById(@RequestParam("templateId") Integer templateId) {
		CrmPrintTemplate byId = printTemplateService.getById(templateId);
		return Result.ok(byId);
	}

	@ApiOperation("")
	@PostMapping("/addPrintTemplate")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.SAVE, object = "#adminPrintTemplate.templateName", detail = "'New print template added: '+#adminPrintTemplate.templateName")
	public Result addPrintTemplate(@RequestBody CrmPrintTemplate adminPrintTemplate) {
		adminPrintTemplate.setUpdateTime(new Date());
		printTemplateService.save(adminPrintTemplate);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping("/updatePrintTemplate")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "#adminPrintTemplate.templateName", detail = "'Modified print template:'+#adminPrintTemplate.templateName")
	public Result updatePrintTemplate(@RequestBody CrmPrintTemplate adminPrintTemplate) {
		printTemplateService.updateById(adminPrintTemplate);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping("/deletePrintTemplate")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.DELETE)
	public Result deletePrintTemplate(@RequestParam("templateId") Integer templateId) {
		printTemplateService.deletePrintTemplate(templateId);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping("/queryFields")
	public Result<CrmPrintFieldVO> queryFields(@RequestParam("type") Integer type) {
		CrmPrintFieldVO crmPrintFieldVO = printTemplateService.queryFields(type);
		return Result.ok(crmPrintFieldVO);
	}

	@ApiOperation("")
	@PostMapping("/print")
	public Result<String> print(@RequestParam("templateId") Integer templateId, @RequestParam("id") Integer id) {
		String print = printTemplateService.print(templateId, id);
		return Result.ok(print);
	}

	@ApiOperation("")
	@PostMapping("/preview")
	public Result<String> preview(@RequestParam("content") String content, @RequestParam("type") String type) {
		String s = printTemplateService.preview(content, type);
		return Result.ok(s);
	}

	@ApiOperation("")
	@RequestMapping("/down")
	@LoginFromCookie
	public void down(@RequestParam("type") Integer type, @RequestParam("key") String key, HttpServletResponse response) {
		String object = BaseUtil.getRedis().get(CrmCacheKey.CRM_PRINT_TEMPLATE_CACHE_KEY + key);
		if (StrUtil.isNotEmpty(object)) {
			JSONObject parse = JSON.parseObject(object);
			String path;
			if (type == 2) {
				path = parse.getString("word");
			} else {
				path = parse.getString("pdf");
			}
			if (FileUtil.exist(path)) {
				ServletUtil.write(response, FileUtil.file(path));
				return;
			}
		}
		ServletUtil.write(response, Result.ok().toJSONString(), "text/plain");
	}

	@ApiOperation("iframe")
	@RequestMapping("/preview.pdf")
	@LoginFromCookie
	public void preview(String key, HttpServletResponse response) {
		String object = BaseUtil.getRedis().get(CrmCacheKey.CRM_PRINT_TEMPLATE_CACHE_KEY + key);
		if (StrUtil.isNotEmpty(object)) {
			JSONObject parse = JSON.parseObject(object);
			String path = parse.getString("pdf");
			if (FileUtil.exist(path)) {
				File file = FileUtil.file(path);
				BufferedInputStream in = null;
				ServletOutputStream out = null;
				try {
					in = FileUtil.getInputStream(file);
					response.setContentType("application/pdf");
					IoUtil.copy(in, response.getOutputStream());
				} catch (Exception ex) {
					log.error("", ex);
				} finally {
					IoUtil.close(in);
					IoUtil.close(out);
				}
				return;
			}
		}
		ServletUtil.write(response, Result.ok().toJSONString(), "text/plain");
	}

	@ApiOperation("")
	@PostMapping("/copyTemplate")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.COPY)
	public Result copyTemplate(@RequestParam("templateId") Integer templateId) {
		printTemplateService.copyTemplate(templateId);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping("/savePrintRecord")
	public Result savePrintRecord(@RequestBody CrmPrintRecord crmPrintRecord) {
		printTemplateService.savePrintRecord(crmPrintRecord);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping("/queryPrintRecord")
	public Result<List<CrmPrintRecord>> queryPrintRecord(@RequestParam("crmType") Integer crmType, @RequestParam("typeId") Integer typeId) {
		List<CrmPrintRecord> crmPrintRecords = printTemplateService.queryPrintRecord(crmType, typeId);
		return Result.ok(crmPrintRecords);
	}

	@ApiOperation("")
	@PostMapping("/queryPrintRecordById")
	public Result<CrmPrintRecord> queryPrintRecordById(@RequestParam("recordId") Integer recordId) {
		CrmPrintRecord crmPrintRecord = printTemplateService.queryPrintRecordById(recordId);
		return Result.ok(crmPrintRecord);
	}

}

