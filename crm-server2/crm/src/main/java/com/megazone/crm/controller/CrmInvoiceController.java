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
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.log.CrmInvoiceLog;
import com.megazone.crm.entity.BO.CrmChangeOwnerUserBO;
import com.megazone.crm.entity.BO.CrmContractSaveBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.CrmInvoice;
import com.megazone.crm.entity.PO.CrmInvoiceInfo;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.ICrmInvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/crmInvoice")
@Api(tags = "")
@SysLog(subModel = SubModelType.CRM_INVOICE, logClass = CrmInvoiceLog.class)
public class CrmInvoiceController {

	@Autowired
	private ICrmInvoiceService crmInvoiceService;

	@PostMapping("/queryPageList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO search) {
		search.setPageType(1);
		BasePage<Map<String, Object>> mapBasePage = crmInvoiceService.queryPageList(search);
		return R.ok(mapBasePage);
	}

	@PostMapping("/field")
	@ApiOperation("")
	public Result<List> queryInvoiceField(@RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmInvoiceService.queryField(null));
		}
		return R.ok(crmInvoiceService.queryFormPositionField(null));
	}

	@PostMapping("/field/{id}")
	@ApiOperation("")
	public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id,
								   @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmInvoiceService.queryField(id));
		}
		return R.ok(crmInvoiceService.queryFormPositionField(id));
	}

	@PostMapping("/add")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#crmModel.entity[invoiceName]", detail = "'Added invoice: ' + #crmModel.entity[invoiceName]")
	public Result add(@RequestBody CrmContractSaveBO crmModel) {
		crmInvoiceService.addOrUpdate(crmModel, false);
		return R.ok();
	}

	@PostMapping("/querySimpleEntity")
	@ApiExplain("")
	public Result<List<SimpleCrmEntity>> querySimpleEntity(@RequestBody List<Integer> ids) {
		List<SimpleCrmEntity> crmEntities = crmInvoiceService.querySimpleEntity(ids);
		return R.ok(crmEntities);
	}

	@PostMapping("/update")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInvoice(@RequestBody CrmContractSaveBO crmModel) {
		crmInvoiceService.addOrUpdate(crmModel, false);
		return R.ok();
	}

	@PostMapping("/queryById/{invoiceId}")
	@ApiOperation("")
	public Result<CrmInvoice> queryById(@PathVariable("invoiceId") Integer invoiceId) {
		return R.ok(crmInvoiceService.queryById(invoiceId));
	}

	@PostMapping("/information/{id}")
	@ApiOperation("")
	public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<CrmModelFiledVO> information = crmInvoiceService.information(id);
		return R.ok(information);
	}

	@PostMapping("/updateInvoiceStatus")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInvoiceStatus(@RequestBody CrmInvoice crmInvoice) {
		crmInvoiceService.updateInvoiceStatus(crmInvoice);
		return R.ok();
	}

	@PostMapping("/resetInvoiceStatus")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result resetInvoiceStatus(@RequestBody CrmInvoice crmInvoice) {
		crmInvoiceService.updateInvoiceStatus(crmInvoice);
		return R.ok();
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteByIds(@ApiParam(name = "ids", value = "id") @RequestBody List<Integer> ids) {
		crmInvoiceService.deleteByIds(ids);
		return R.ok();
	}


	@PostMapping("/changeOwnerUser")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.CHANGE_OWNER)
	public Result changeOwnerUser(@RequestBody CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
		crmInvoiceService.changeOwnerUser(crmChangeOwnerUserBO.getIds(), crmChangeOwnerUserBO.getOwnerUserId());
		return R.ok();
	}

	@PostMapping("/queryFileList")
	@ApiOperation("")
	public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<FileEntity> fileEntities = crmInvoiceService.queryFileList(id);
		return R.ok(fileEntities);
	}

	@PostMapping("/saveInvoiceInfo")
	@ApiOperation("")
	public Result saveInvoiceInfo(@RequestBody CrmInvoiceInfo crmInvoiceInfo) {
		crmInvoiceService.saveInvoiceInfo(crmInvoiceInfo);
		return R.ok();
	}

	@PostMapping("/updateInvoiceInfo")
	@ApiOperation("")
	public Result updateInvoiceInfo(@RequestBody CrmInvoiceInfo crmInvoiceInfo) {
		crmInvoiceService.updateInvoiceInfo(crmInvoiceInfo);
		return R.ok();
	}

	@PostMapping("/deleteInvoiceInfo")
	@ApiOperation("")
	public Result deleteInvoiceInfo(@RequestParam("infoId") Integer infoId) {
		crmInvoiceService.deleteInvoiceInfo(infoId);
		return R.ok();
	}

	@PostMapping("/allExportExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Invoice export", detail = "Export all")
	public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) {
		search.setPageType(0);
		crmInvoiceService.exportExcel(response, search);
	}

	@PostMapping("/updateInformation")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
		crmInvoiceService.updateInformation(updateInformationBO);
		return R.ok();
	}

}

