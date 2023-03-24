package com.megazone.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmBusinessSaveBO;
import com.megazone.crm.entity.BO.CrmReceivablesPlanBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.CrmReceivablesPlan;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.ICrmReceivablesPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/crmReceivablesPlan")
@Api(tags = "")
public class CrmReceivablesPlanController {

	@Autowired
	private ICrmReceivablesPlanService crmReceivablesPlanService;

	@PostMapping("/add")
	@ApiOperation("")
	public Result add(@RequestBody CrmBusinessSaveBO crmModel) {
		crmReceivablesPlanService.addOrUpdate(crmModel);
		return R.ok();
	}

	@PostMapping("/batchSave")
	@ApiOperation("")
	public Result batchSave(@RequestBody List<CrmReceivablesPlanBO> receivablesPlans) {
		crmReceivablesPlanService.batchSave(receivablesPlans);
		return R.ok();
	}

	@PostMapping("/queryById/{receivablesPlanId}")
	@ApiOperation("ID")
	public Result<CrmModel> queryById(@PathVariable("receivablesPlanId") @ApiParam(name = "id", value = "id") Integer receivablesPlanId) {
		CrmModel model = crmReceivablesPlanService.queryById(receivablesPlanId);
		return R.ok(model);
	}

	@PostMapping("/queryPageList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO search) {
		search.setPageType(1);
		BasePage<Map<String, Object>> mapBasePage = crmReceivablesPlanService.queryPageList(search);
		return R.ok(mapBasePage);
	}

	@PostMapping("/update")
	@ApiOperation("")
	public Result update(@RequestBody CrmBusinessSaveBO crmModel) {
		crmReceivablesPlanService.addOrUpdate(crmModel);
		return R.ok();
	}

	@PostMapping("/information/{id}")
	@ApiOperation("")
	public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<CrmModelFiledVO> information = crmReceivablesPlanService.information(id);
		return R.ok(information);
	}

	@PostMapping("/queryFileList")
	@ApiOperation("")
	public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<FileEntity> fileEntities = crmReceivablesPlanService.queryFileList(id);
		return R.ok(fileEntities);
	}

	@PostMapping("/queryByContractAndCustomer")
	@ApiOperation("")
	public Result<List<CrmReceivablesPlan>> queryByContractAndCustomer(@RequestBody CrmReceivablesPlanBO crmReceivablesPlanBO) {
		List<CrmReceivablesPlan> crmReceivablesPlans = crmReceivablesPlanService.queryByContractAndCustomer(crmReceivablesPlanBO);
		return R.ok(crmReceivablesPlans);
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("ID")
	public Result deleteByIds(@ApiParam(name = "ids", value = "id") @RequestBody List<Integer> ids) {
		crmReceivablesPlanService.deleteByIds(ids);
		return R.ok();
	}

	@PostMapping("/field")
	@ApiOperation("")
	public Result<List> queryField(@RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmReceivablesPlanService.queryField(null));
		}
		return R.ok(crmReceivablesPlanService.queryFormPositionField(null));
	}

	@PostMapping("/batchExportExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export receivable plans", detail = "Export selected")
	public void batchExportExcel(@RequestBody @ApiParam(name = "ids", value = "id") List<Integer> ids, HttpServletResponse response) {
		CrmSearchBO search = new CrmSearchBO();
		search.setPageType(0);
		search.setLabel(CrmEnum.RECEIVABLES.getType());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
		entity.setValues(ids.stream().map(Object::toString).collect(Collectors.toList()));
		search.getSearchList().add(entity);
		search.setPageType(0);
		crmReceivablesPlanService.exportExcel(response, search);
	}

	@PostMapping("/updateInformation")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
		crmReceivablesPlanService.updateInformation(updateInformationBO);
		return R.ok();
	}

	@PostMapping("/allExportExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export receivables", detail = "Export all")
	public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) {
		search.setPageType(0);
		crmReceivablesPlanService.exportExcel(response, search);
	}

	@PostMapping("/field/{id}")
	@ApiOperation("")
	public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id, @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmReceivablesPlanService.queryField(id));
		}
		return R.ok(crmReceivablesPlanService.queryFormPositionField(id));
	}
}

