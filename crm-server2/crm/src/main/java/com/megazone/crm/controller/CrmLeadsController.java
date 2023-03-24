package com.megazone.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.log.CrmLeadsLog;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmLeads;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.CrmUploadExcelService;
import com.megazone.crm.service.ICrmLeadsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-21
 */
@RestController
@RequestMapping("/crmLeads")
@Api(tags = "")
@SysLog(subModel = SubModelType.CRM_LEADS, logClass = CrmLeadsLog.class)
public class CrmLeadsController {

	@Autowired
	private ICrmLeadsService crmLeadsService;

	@Autowired
	private CrmUploadExcelService uploadExcelService;

	@PostMapping("/queryById/{leadsId}")
	@ApiOperation("ID")
	public Result<CrmModel> queryById(@PathVariable("leadsId") @ApiParam(name = "id", value = "id") Integer leadsId) {
		Integer number = crmLeadsService.lambdaQuery().eq(CrmLeads::getLeadsId, leadsId).count();
		if (number == 0) {
			throw new CrmException(CrmCodeEnum.CRM_DATA_DELETED, "");
		}
		CrmModel model = crmLeadsService.queryById(leadsId);
		return R.ok(model);
	}

	@PostMapping("/querySimpleEntity")
	@ApiExplain("")
	public Result<List<SimpleCrmEntity>> querySimpleEntity(@RequestBody List<Integer> ids) {
		List<SimpleCrmEntity> crmEntities = crmLeadsService.querySimpleEntity(ids);
		return R.ok(crmEntities);
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("ID")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteByIds(@ApiParam(name = "ids", value = "id") @RequestBody List<Integer> ids) {
		crmLeadsService.deleteByIds(ids);
		return R.ok();
	}

	@PostMapping("/transfer")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.TRANSFER)
	public Result transfer(@ApiParam(name = "ids", value = "id") @RequestBody List<Integer> ids) {
		crmLeadsService.transfer(ids);
		return R.ok();
	}

	@PostMapping("/downloadExcel")
	@ApiOperation("")
	public void downloadExcel(HttpServletResponse response) throws IOException {
		crmLeadsService.downloadExcel(response);
	}

	@PostMapping("/allExportExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Lead export", detail = "Export all")
	public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) {
		search.setPageType(0);
		crmLeadsService.exportExcel(response, search);
	}

	@PostMapping("/batchExportExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Lead export", detail = "Export selected")
	public void batchExportExcel(@RequestBody @ApiParam(name = "ids", value = "id") List<Integer> ids, HttpServletResponse response) {
		CrmSearchBO search = new CrmSearchBO();
		search.setPageType(0);
		search.setLabel(CrmEnum.LEADS.getType());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
		entity.setValues(ids.stream().map(Object::toString).collect(Collectors.toList()));
		search.getSearchList().add(entity);
		search.setPageType(0);
		crmLeadsService.exportExcel(response, search);
	}

	@PostMapping("/queryPageList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO search) {
		search.setPageType(1);
		BasePage<Map<String, Object>> mapBasePage = crmLeadsService.queryPageList(search);
		return R.ok(mapBasePage);
	}

	@PostMapping("/changeOwnerUser")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.CHANGE_OWNER)
	public Result changeOwnerUser(@RequestBody CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
		crmLeadsService.changeOwnerUser(crmChangeOwnerUserBO.getIds(), crmChangeOwnerUserBO.getOwnerUserId());
		return R.ok();
	}

	@PostMapping("/field")
	@ApiOperation("")
	public Result<List> queryLeadsField(@RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmLeadsService.queryField(null));
		}
		return R.ok(crmLeadsService.queryFormPositionField(null));
	}

	@PostMapping("/field/{id}")
	@ApiOperation("")
	public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id,
								   @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmLeadsService.queryField(id));
		}
		return R.ok(crmLeadsService.queryFormPositionField(id));
	}

	@PostMapping("/add")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#crmModel.entity[leadsName]", detail = "'New leads added: ' + #crmModel.entity[leadsName]")
	public Result add(@RequestBody CrmModelSaveBO crmModel) {
		crmLeadsService.addOrUpdate(crmModel, false);
		return R.ok();
	}

	@PostMapping("/update")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result update(@RequestBody CrmModelSaveBO crmModel) {
		crmLeadsService.addOrUpdate(crmModel, false);
		return R.ok();
	}

	@PostMapping("/queryFileList")
	@ApiOperation("")
	public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<FileEntity> fileEntities = crmLeadsService.queryFileList(id);
		return R.ok(fileEntities);
	}

	@PostMapping("/num")
	@ApiOperation("")
	public Result<CrmInfoNumVO> num(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		CrmInfoNumVO num = crmLeadsService.num(id);
		return R.ok(num);
	}

	@PostMapping("/star/{id}")
	@ApiOperation("")
	public Result star(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		crmLeadsService.star(id);
		return R.ok();
	}

	@PostMapping("/information/{id}")
	@ApiOperation("")
	public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<CrmModelFiledVO> information = crmLeadsService.information(id);
		return R.ok(information);
	}

	@PostMapping("/uploadExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_IMPORT, object = "Import leads", detail = "Import leads")
	public Result<Long> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam("repeatHandling") Integer repeatHandling) {
		UploadExcelBO uploadExcelBO = new UploadExcelBO();
		uploadExcelBO.setUserInfo(UserUtil.getUser());
		uploadExcelBO.setCrmEnum(CrmEnum.LEADS);
		uploadExcelBO.setPoolId(null);
		uploadExcelBO.setRepeatHandling(repeatHandling);
		Long messageId = uploadExcelService.uploadExcel(file, uploadExcelBO);
		return R.ok(messageId);
	}

	@PostMapping("/updateInformation")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
		crmLeadsService.updateInformation(updateInformationBO);
		return R.ok();
	}
}

