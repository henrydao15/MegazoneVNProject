package com.megazone.crm.controller;


import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.crm.common.log.CrmProductCategoryLog;
import com.megazone.crm.entity.BO.CrmProductCategoryBO;
import com.megazone.crm.entity.PO.CrmProductCategory;
import com.megazone.crm.service.ICrmProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/crmProductCategory")
@Api(tags = "")
@SysLog(logClass = CrmProductCategoryLog.class)
public class CrmProductCategoryController {

	@Autowired
	private ICrmProductCategoryService crmProductCategoryService;

	@PostMapping("/queryList")
	@ApiOperation("")
	public Result<List<CrmProductCategoryBO>> queryList(@ApiParam("type") String type) {
		List<CrmProductCategoryBO> list = crmProductCategoryService.queryList(type);
		return R.ok(list);
	}

	@PostMapping("/queryById/{id}")
	@ApiOperation("ID")
	public Result<CrmProductCategory> queryById(@PathVariable("id") @ApiParam(name = "id", value = "id") Integer productId) {
		CrmProductCategory category = crmProductCategoryService.queryById(productId);
		return R.ok(category);
	}

	@PostMapping("/save")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.SAVE, object = "#productCategory.name", detail = "'Added product category: '+#productCategory.name")
	public Result save(@RequestBody CrmProductCategory productCategory) {
		crmProductCategoryService.saveAndUpdate(productCategory);
		return R.ok();
	}

	@PostMapping("/update")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "#productCategory.name", detail = "'Modify product category:'+#productCategory.name")
	public Result update(@RequestBody CrmProductCategory productCategory) {
		crmProductCategoryService.saveAndUpdate(productCategory);
		return R.ok();
	}

	@PostMapping("/deleteById/{id}")
	@ApiOperation("")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.DELETE)
	public Result deleteById(@PathVariable("id") Integer id) {
		crmProductCategoryService.deleteById(id);
		return R.ok();
	}
}

