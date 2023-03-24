package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.entity.BO.AddInsuranceEmpBO;
import com.megazone.hrm.entity.BO.QueryEmpInsuranceMonthBO;
import com.megazone.hrm.entity.BO.QueryInsuranceRecordListBO;
import com.megazone.hrm.entity.BO.UpdateInsuranceProjectBO;
import com.megazone.hrm.entity.PO.HrmInsuranceMonthEmpRecord;
import com.megazone.hrm.entity.VO.EmpInsuranceByIdVO;
import com.megazone.hrm.entity.VO.QueryEmpInsuranceMonthVO;
import com.megazone.hrm.entity.VO.SimpleHrmEmployeeVO;
import com.megazone.hrm.service.IHrmInsuranceMonthEmpRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/hrmInsuranceMonthEmpRecord")
@Api(tags = "-")
public class HrmInsuranceMonthEmpRecordController {


	@Autowired
	private IHrmInsuranceMonthEmpRecordService monthEmpRecordService;

	@PostMapping("/queryEmpInsuranceMonth")
	@ApiOperation("")
	public Result<BasePage<QueryEmpInsuranceMonthVO>> queryEmpInsuranceMonth(@RequestBody QueryEmpInsuranceMonthBO queryEmpInsuranceMonthBO) {
		BasePage<QueryEmpInsuranceMonthVO> page = monthEmpRecordService.queryEmpInsuranceMonth(queryEmpInsuranceMonthBO);
		return Result.ok(page);
	}

	@PostMapping("/queryById/{iempRecordId}")
	@ApiOperation("")
	public Result<EmpInsuranceByIdVO> queryById(@PathVariable String iempRecordId) {
		EmpInsuranceByIdVO empInsuranceByIdVO = monthEmpRecordService.queryById(iempRecordId);
		return Result.ok(empInsuranceByIdVO);
	}


	@PostMapping("/stop")
	@ApiOperation("")
	public Result stop(@RequestBody List<Integer> ids) {
		monthEmpRecordService.stop(ids);
		return Result.ok();
	}

	@PostMapping("/updateInsuranceProject")
	@ApiOperation("")
	public Result updateInsuranceProject(@RequestBody UpdateInsuranceProjectBO updateInsuranceProjectBO) {
		monthEmpRecordService.updateInsuranceProject(updateInsuranceProjectBO);
		return Result.ok();
	}

	@PostMapping("/batchUpdateInsuranceProject")
	@ApiOperation("")
	public Result batchUpdateInsuranceProject(@RequestBody UpdateInsuranceProjectBO updateInsuranceProjectBO) {
		monthEmpRecordService.batchUpdateInsuranceProject(updateInsuranceProjectBO);
		return Result.ok();
	}

	@PostMapping("/addInsuranceEmp")
	@ApiOperation("")
	public Result addInsuranceEmp(@RequestBody AddInsuranceEmpBO addInsuranceEmpBO) {
		monthEmpRecordService.addInsuranceEmp(addInsuranceEmpBO);
		return Result.ok();
	}

	@PostMapping("/queryNoInsuranceEmp/{iRecordId}")
	@ApiOperation("")
	public Result<List<SimpleHrmEmployeeVO>> queryNoInsuranceEmp(@PathVariable Integer iRecordId) {
		List<SimpleHrmEmployeeVO> employeeVOS = monthEmpRecordService.queryNoInsuranceEmp(iRecordId);
		return Result.ok(employeeVOS);
	}

	@PostMapping("/myInsurance")
	@ApiOperation("")
	public Result<BasePage<HrmInsuranceMonthEmpRecord>> myInsurancePageList(@RequestBody QueryInsuranceRecordListBO recordListBO) {
		BasePage<HrmInsuranceMonthEmpRecord> page = monthEmpRecordService.myInsurancePageList(recordListBO);
		return Result.ok(page);
	}

	@PostMapping("/myInsuranceDetail/{iempRecordId}")
	@ApiOperation("")
	public Result<EmpInsuranceByIdVO> myInsuranceDetail(@PathVariable String iempRecordId) {
		EmpInsuranceByIdVO empInsuranceByIdVO = monthEmpRecordService.queryById(iempRecordId);
		return Result.ok(empInsuranceByIdVO);
	}

}

