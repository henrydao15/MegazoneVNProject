package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.BO.UpdateFieldConfigBO;
import com.megazone.hrm.entity.BO.UpdateFieldWidthBO;
import com.megazone.hrm.entity.BO.VerifyUniqueBO;
import com.megazone.hrm.entity.VO.EmployeeHeadFieldVO;
import com.megazone.hrm.service.IHrmEmployeeFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmEmployeeField")
@Api(tags = "-")
public class HrmEmployeeFieldController {

	@Autowired
	private IHrmEmployeeFieldService employeeFieldService;


	@PostMapping("/queryListHeads")
	@ApiOperation("")
	public Result<List<EmployeeHeadFieldVO>> queryListHeads() {
		List<EmployeeHeadFieldVO> employeeListHeadBOField = employeeFieldService.queryListHeads();
		return Result.ok(employeeListHeadBOField);
	}


	@PostMapping("/updateFieldConfig")
	@ApiOperation("")
	public Result updateFieldConfig(@RequestBody List<UpdateFieldConfigBO> updateFieldConfigBOList) {
		employeeFieldService.updateFieldConfig(updateFieldConfigBOList);
		return Result.ok();
	}

	@PostMapping("/verify")
	@ApiOperation("")
	public Result verifyUnique(@RequestBody VerifyUniqueBO verifyUniqueBO) {
		VerifyUniqueBO verify = employeeFieldService.verifyUnique(verifyUniqueBO);
		return Result.ok(verify);
	}

	@PostMapping("/updateFieldWidth")
	@ApiOperation("")
	public Result updateFieldWidth(@RequestBody UpdateFieldWidthBO updateFieldWidthBO) {
		employeeFieldService.updateFieldWidth(updateFieldWidthBO);
		return Result.ok();
	}

}

