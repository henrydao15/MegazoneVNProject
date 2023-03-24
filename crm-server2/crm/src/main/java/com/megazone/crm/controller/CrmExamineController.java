package com.megazone.crm.controller;


import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.crm.entity.BO.CrmMyExamineBO;
import com.megazone.crm.entity.BO.CrmQueryExamineStepBO;
import com.megazone.crm.entity.BO.CrmSaveExamineBO;
import com.megazone.crm.entity.PO.CrmExamine;
import com.megazone.crm.entity.VO.CrmQueryAllExamineVO;
import com.megazone.crm.entity.VO.CrmQueryExamineStepVO;
import com.megazone.crm.service.ICrmExamineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/crmExamine")
@Api(tags = "")
public class CrmExamineController {

	@Autowired
	private ICrmExamineService examineService;


	@PostMapping("/saveExamine")
	@ApiOperation("")
	public Result saveExamine(@RequestBody CrmSaveExamineBO crmSaveExamineBO) {
		examineService.saveExamine(crmSaveExamineBO);
		return Result.ok();
	}


	@PostMapping("/queryAllExamine")
	@ApiOperation("")
	public Result<BasePage<CrmQueryAllExamineVO>> queryAllExamine(@RequestBody PageEntity pageEntity) {
		BasePage<CrmQueryAllExamineVO> page = examineService.queryAllExamine(pageEntity);
		return Result.ok(page);
	}


	@PostMapping("/queryExamineById/{examineId}")
	@ApiOperation("id examineId id")
	public Result<CrmQueryAllExamineVO> queryExamineById(@PathVariable String examineId) {
		CrmQueryAllExamineVO examineVO = examineService.queryExamineById(examineId);
		return Result.ok(examineVO);
	}

	/**
	 * examineId id
	 * status  1 0 2
	 */
	@PostMapping("/updateStatus")
	@ApiOperation("")
	public Result updateStatus(@RequestBody CrmExamine crmExamine) {
		examineService.updateStatus(crmExamine);
		return Result.ok();
	}

	/**
	 * categoryType 1  2
	 */
	@PostMapping("/queryExamineStep")
	@ApiOperation("")
	public Result<CrmQueryExamineStepVO> queryExamineStep(@RequestBody CrmQueryExamineStepBO queryExamineStepBO) {
		CrmQueryExamineStepVO examineStepVO = examineService.queryExamineStep(queryExamineStepBO);
		return Result.ok(examineStepVO);
	}

	@PostMapping("/queryExamineStepIsExist")
	@ApiOperation("")
	public Result<Boolean> queryExamineStepIsExist(@RequestParam("categoryType") Integer categoryType) {
		Boolean isExist = examineService.queryExamineStepByType(categoryType);
		return Result.ok(isExist);
	}

	@PostMapping("/myExamine")
	@ApiOperation("")
	public Result<BasePage<JSONObject>> myExamine(@RequestBody CrmMyExamineBO crmMyExamineBO) {
		BasePage<JSONObject> page = examineService.myExamine(crmMyExamineBO);
		return Result.ok(page);
	}
}

