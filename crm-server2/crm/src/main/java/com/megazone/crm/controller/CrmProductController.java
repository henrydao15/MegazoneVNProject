package com.megazone.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.common.log.CrmProductLog;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.CrmUploadExcelService;
import com.megazone.crm.service.ICrmProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/crmProduct")
@Api(tags = "")
@SysLog(subModel = SubModelType.CRM_PRODUCT, logClass = CrmProductLog.class)
public class CrmProductController {

	@Autowired
	private ICrmProductService crmProductService;

	@PostMapping("/queryById/{productId}")
	@ApiOperation("ID")
	public Result<CrmModel> queryById(@PathVariable("productId") @ApiParam(name = "id", value = "id") Integer productId) {
		CrmModel model = crmProductService.queryById(productId);
		return R.ok(model);
	}

	@PostMapping("/selectSimpleEntity")
	@ApiExplain("")
	public Result<List<SimpleCrmEntity>> selectSimpleEntity(@RequestBody List<Integer> ids) {
		List<SimpleCrmEntity> crmEntities = crmProductService.querySimpleEntity(ids);
		return R.ok(crmEntities);
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("ID")
	@SysLogHandler(behavior = BehaviorEnum.DELETE)
	public Result deleteByIds(@ApiParam(name = "ids", value = "id") @RequestBody List<Integer> ids) {
		crmProductService.deleteByIds(ids);
		return R.ok();
	}

	@PostMapping("/add")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "#crmModel.entity[name]", detail = "'Added new product: '+#crmModel.entity[name]")
	public Result add(@RequestBody CrmModelSaveBO crmModel) {
		crmProductService.addOrUpdate(crmModel, false);
		return R.ok();
	}

	@PostMapping("/update")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result update(@RequestBody CrmModelSaveBO crmModel) {
		crmProductService.addOrUpdate(crmModel, false);
		return R.ok();
	}

	@PostMapping("/changeOwnerUser")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.CHANGE_OWNER)
	public Result changeOwnerUser(@RequestBody CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
		crmProductService.changeOwnerUser(crmChangeOwnerUserBO.getIds(), crmChangeOwnerUserBO.getOwnerUserId());
		return R.ok();
	}

	@PostMapping("/downloadExcel")
	@ApiOperation("")
	public void downloadExcel(HttpServletResponse response) throws IOException {
		crmProductService.downloadExcel(response);
	}

	@PostMapping("/allExportExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export products", detail = "Export all")
	public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) {
		search.setPageType(0);
		crmProductService.exportExcel(response, search);
	}

	@PostMapping("/batchExportExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_EXPORT, object = "Export products", detail = "Export selected")
	public void batchExportExcel(@RequestBody @ApiParam(name = "ids", value = "id") List<Integer> ids, HttpServletResponse response) {
		CrmSearchBO search = new CrmSearchBO();
		search.setPageType(0);
		search.setLabel(CrmEnum.PRODUCT.getType());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
		entity.setValues(ids.stream().map(Object::toString).collect(Collectors.toList()));
		search.getSearchList().add(entity);
		search.setPageType(0);
		crmProductService.exportExcel(response, search);
	}

	@PostMapping("/queryPageList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO search) {
		search.setPageType(1);
		BasePage<Map<String, Object>> mapBasePage = crmProductService.queryPageList(search);
		return R.ok(mapBasePage);
	}

	@PostMapping("/field")
	@ApiOperation("")
	public Result<List> queryProductField(@RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmProductService.queryField(null));
		}
		return R.ok(crmProductService.queryFormPositionField(null));
	}

	@PostMapping("/field/{id}")
	@ApiOperation("")
	public Result<List> queryField(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id,
								   @RequestParam(value = "type", required = false) String type) {
		if (StrUtil.isNotEmpty(type)) {
			return R.ok(crmProductService.queryField(id));
		}
		return R.ok(crmProductService.queryFormPositionField(id));
	}

	@PostMapping("/updateStatus")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateStatus(@RequestBody CrmProductStatusBO productStatusBO) {
		crmProductService.updateStatus(productStatusBO);
		return R.ok();
	}

	@PostMapping("/information/{id}")
	@ApiOperation("")
	public Result<List<CrmModelFiledVO>> information(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<CrmModelFiledVO> information = crmProductService.information(id);
		return R.ok(information);
	}


	@PostMapping("/queryFileList")
	@ApiOperation("")
	public Result<List<FileEntity>> queryFileList(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		List<FileEntity> fileEntities = crmProductService.queryFileList(id);
		return R.ok(fileEntities);
	}

	@PostMapping("/num")
	@ApiOperation("")
	public Result<CrmInfoNumVO> num(@RequestParam("id") @ApiParam(name = "id", value = "id") Integer id) {
		CrmInfoNumVO infoNumVO = crmProductService.num(id);
		return R.ok(infoNumVO);
	}

	@PostMapping("/querySimpleEntity")
	@ApiExplain("")
	public Result<List<SimpleCrmEntity>> querySimpleEntity() {
		List<SimpleCrmEntity> crmEntities = crmProductService.querySimpleEntity();
		return R.ok(crmEntities);
	}

	@PostMapping("/uploadExcel")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.EXCEL_IMPORT, object = "Import products", detail = "Import products")
	public Result<Long> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam("repeatHandling") Integer repeatHandling) {
		UploadExcelBO uploadExcelBO = new UploadExcelBO();
		uploadExcelBO.setUserInfo(UserUtil.getUser());
		uploadExcelBO.setCrmEnum(CrmEnum.PRODUCT);
		uploadExcelBO.setPoolId(null);
		uploadExcelBO.setRepeatHandling(repeatHandling);
		Long messageId = ApplicationContextHolder.getBean(CrmUploadExcelService.class).uploadExcel(file, uploadExcelBO);
		return R.ok(messageId);
	}

	@PostMapping("/updateInformation")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.UPDATE)
	public Result updateInformation(@RequestBody CrmUpdateInformationBO updateInformationBO) {
		crmProductService.updateInformation(updateInformationBO);
		return R.ok();
	}

	@PostMapping("/querySaleProductPageList")
	@ApiOperation("")
	public Result<BasePage<Map<String, Object>>> querySaleProductPageList(@RequestBody CrmQuerySaleProductPageBO querySaleProductPageBO) {
		CrmSearchBO search = new CrmSearchBO();
		search.setPageType(0);
		search.setLabel(CrmEnum.PRODUCT.getType());
		search.setSearch(querySaleProductPageBO.getSearch());
		search.setPage(querySaleProductPageBO.getPage());
		search.setLimit(querySaleProductPageBO.getLimit());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setName("status");
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.IS);
		entity.setValues(Collections.singletonList("1"));
		search.getSearchList().add(entity);
		return queryPageList(search);
	}
}

