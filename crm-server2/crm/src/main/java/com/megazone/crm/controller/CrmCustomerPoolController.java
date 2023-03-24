package com.megazone.crm.controller;


import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.megazone.core.common.*;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.exception.CrmException;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.common.log.CrmCustomerPoolLog;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmCustomerPoolBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.UploadExcelBO;
import com.megazone.crm.entity.PO.CrmCustomerPool;
import com.megazone.crm.entity.PO.CrmCustomerPoolFieldSort;
import com.megazone.crm.entity.VO.CrmCustomerPoolVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.service.CrmUploadExcelService;
import com.megazone.crm.service.ICrmCustomerPoolService;
import com.megazone.crm.service.ICrmCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-29
 */
@RestController
@RequestMapping("/crmCustomerPool")
@Api(tags = "")
@Slf4j
@SysLog(logClass = CrmCustomerPoolLog.class)
public class CrmCustomerPoolController {

	@Autowired
	private ICrmCustomerPoolService crmCustomerPoolService;

	@Autowired
	private ICrmCustomerService customerService;

	@Autowired
	private CrmUploadExcelService uploadExcelService;

	@ApiOperation("")
	@PostMapping("/queryPageList")
	public Result<BasePage<Map<String, Object>>> queryPageList(@RequestBody CrmSearchBO crmSearchBO) {
		BasePage<Map<String, Object>> basePage = crmCustomerPoolService.queryPageList(crmSearchBO, false);
		return Result.ok(basePage);
	}

	@ApiOperation("")
	@PostMapping("/queryPoolSettingList")
	public Result<BasePage<CrmCustomerPoolVO>> queryPoolSettingList(@RequestBody PageEntity entity) {
		BasePage<CrmCustomerPoolVO> poolVOBasePage = crmCustomerPoolService.queryPoolSettingList(entity);
		return Result.ok(poolVOBasePage);
	}

	@ApiOperation("")
	@PostMapping("/queryPoolNameList")
	public Result<List<CrmCustomerPool>> queryPoolNameList() {
		List<CrmCustomerPool> crmCustomerPools = crmCustomerPoolService.queryPoolNameList();
		return Result.ok(crmCustomerPools);
	}

	@ApiOperation("")
	@PostMapping("/changeStatus")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE)
	public Result changeStatus(@RequestParam("poolId") Integer poolId, @RequestParam("status") Integer status) {
		crmCustomerPoolService.changeStatus(poolId, status);
		return Result.ok();
	}

	@ApiOperation("ID")
	@PostMapping("/queryPoolById")
	public Result<CrmCustomerPoolVO> queryPoolById(@RequestParam("poolId") Integer poolId) {
		CrmCustomerPoolVO customerPoolVO = crmCustomerPoolService.queryPoolById(poolId);
		return Result.ok(customerPoolVO);
	}

	@ApiOperation("")
	@PostMapping("/queryPoolField")
	public Result<List<CrmModelFiledVO>> queryPoolField() {
		List<CrmModelFiledVO> filedVOS = crmCustomerPoolService.queryPoolField();
		return Result.ok(filedVOS);
	}

	@PostMapping("/downloadExcel")
	@ApiOperation("")
	public void downloadExcel(HttpServletResponse response) throws IOException {
		customerService.downloadExcel(true, response);
	}

	@PostMapping("/uploadExcel")
	@ApiOperation("")
	public Result<Long> uploadExcel(@RequestParam("file") MultipartFile file, @RequestParam("repeatHandling") Integer repeatHandling, @RequestParam("poolId") Integer poolId) {
		Boolean isAdmin = crmCustomerPoolService.queryAuthListByPoolId(poolId);
		if (!isAdmin) {
			throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_NOT_IS_ADMIN);
		}
		UploadExcelBO uploadExcelBO = new UploadExcelBO();
		uploadExcelBO.setUserInfo(UserUtil.getUser());
		uploadExcelBO.setCrmEnum(CrmEnum.CUSTOMER);
		uploadExcelBO.setPoolId(poolId);
		uploadExcelBO.setRepeatHandling(repeatHandling);
		Long messageId = uploadExcelService.uploadExcel(file, uploadExcelBO);
		return R.ok(messageId);
	}


	@ApiOperation("")
	@PostMapping("/allExportExcel")
	public void allExportExcel(@RequestBody CrmSearchBO search, HttpServletResponse response) throws IOException {
		Boolean isAdmin = crmCustomerPoolService.queryAuthListByPoolId(search.getPoolId());
		if (!isAdmin) {
			throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_NOT_IS_ADMIN);
		}
		search.setPageType(0);
		BasePage<Map<String, Object>> basePage = crmCustomerPoolService.queryPageList(search, true);
		export(basePage.getList(), search.getPoolId(), response);
	}

	@ApiOperation("")
	@PostMapping("/batchExportExcel")
	public void batchExportExcel(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
		Integer poolId = jsonObject.getInteger("poolId");
		Boolean isAdmin = crmCustomerPoolService.queryAuthListByPoolId(poolId);
		if (!isAdmin) {
			throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_NOT_IS_ADMIN);
		}
		String ids = jsonObject.getString("ids");
		CrmSearchBO search = new CrmSearchBO();
		search.setPoolId(poolId);
		search.setPageType(0);
		search.setLabel(CrmEnum.CUSTOMER.getType());
		CrmSearchBO.Search entity = new CrmSearchBO.Search();
		entity.setFormType(FieldEnum.TEXT.getFormType());
		entity.setSearchEnum(CrmSearchBO.FieldSearchEnum.ID);
		entity.setValues(StrUtil.splitTrim(ids, Const.SEPARATOR));
		search.getSearchList().add(entity);
		search.setPageType(0);
		BasePage<Map<String, Object>> basePage = crmCustomerPoolService.queryPageList(search, false);
		export(basePage.getList(), search.getPoolId(), response);
	}

	private void export(List<Map<String, Object>> recordList, Integer poolId, HttpServletResponse response) {
		List<CrmCustomerPoolFieldSort> headList = crmCustomerPoolService.queryPoolListHead(poolId);
		ExcelParseUtil.exportExcel(recordList, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {
				record.put("dealStatus", Objects.equals(1, record.get("dealStatus")) ? "Dealed" : "Undealed");
			}

			@Override
			public String getExcelName() {
				return "High Seas Client";
			}
		}, headList);
	}

	@ApiOperation("")
	@PostMapping("/queryCustomerLevel")
	public Result<List<String>> queryCustomerLevel() {
		List<String> strings = crmCustomerPoolService.queryCustomerLevel();
		return Result.ok(strings);
	}

	@ApiOperation("")
	@PostMapping("/setCustomerPool")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.SAVE, object = "#jsonObject[poolName]", detail = "'Added or modified pool rules: '+#jsonObject[poolName]")
	public Result setCustomerPool(@RequestBody JSONObject jsonObject) {
		crmCustomerPoolService.setCustomerPool(jsonObject);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping("/queryPoolFieldConfig")
	public Result<JsonNode> queryPoolFieldConfig(@RequestParam("poolId") Integer poolId) {
		JSONObject object = crmCustomerPoolService.queryPoolFieldConfig(poolId);
		return Result.ok(object.node);
	}

	@ApiOperation("")
	@PostMapping("/poolFieldConfig")
	public Result poolFieldConfig(@RequestBody JSONObject jsonObject) {
		crmCustomerPoolService.poolFieldConfig(jsonObject);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping("/deleteCustomerPool")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.DELETE)
	public Result deleteCustomerPool(@RequestParam("poolId") Integer poolId) {
		crmCustomerPoolService.deleteCustomerPool(poolId);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping("/queryPoolNameListByAuth")
	public Result queryPoolNameListByAuth() {
		List<CrmCustomerPool> crmCustomerPools = crmCustomerPoolService.queryPoolNameListByAuth();
		return Result.ok(crmCustomerPools);
	}

	@ApiOperation("")
	@PostMapping("/queryPoolListHead")
	public Result<List<CrmCustomerPoolFieldSort>> queryPoolListHead(Integer poolId) {
		if (poolId == null) {
			List<CrmCustomerPool> crmCustomerPools = crmCustomerPoolService.queryPoolNameListByAuth();
			if (crmCustomerPools.size() == 0) {
				throw new CrmException(CrmCodeEnum.CRM_CUSTOMER_POOL_NOT_EXIST_ERROR);
			}
			poolId = crmCustomerPools.get(0).getPoolId();
		}
		List<CrmCustomerPoolFieldSort> crmCustomerPoolFieldSorts = crmCustomerPoolService.queryPoolListHead(poolId);
		return Result.ok(crmCustomerPoolFieldSorts);
	}

	@PostMapping("/transfer")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE)
	public Result transfer(@RequestParam("prePoolId") Integer prePoolId, @RequestParam("postPoolId") Integer postPoolId) {
		crmCustomerPoolService.transfer(prePoolId, postPoolId);
		return R.ok();
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("ID")
	public Result deleteByIds(@RequestBody CrmCustomerPoolBO poolBO) {
		crmCustomerPoolService.deleteByIds(poolBO.getIds(), poolBO.getPoolId());
		return R.ok();
	}

	@ApiOperation("")
	@PostMapping("/queryAuthByPoolId")
	public Result<JsonNode> queryAuthByPoolId(@RequestParam("poolId") Integer poolId) {
		JSONObject object = crmCustomerPoolService.queryAuthByPoolId(poolId);
		return Result.ok(object.node);
	}
}

