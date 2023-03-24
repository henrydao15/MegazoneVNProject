package com.megazone.hrm.controller;

import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.entity.BO.QueryInsurancePageListBO;
import com.megazone.hrm.entity.BO.QueryInsuranceRecordListBO;
import com.megazone.hrm.entity.VO.QueryInsurancePageListVO;
import com.megazone.hrm.entity.VO.QueryInsuranceRecordListVO;
import com.megazone.hrm.service.IHrmInsuranceMonthRecordService;
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
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/hrmInsuranceMonthRecord")
@Api(tags = "-")
public class HrmInsuranceMonthRecordController {


	@Autowired
	private IHrmInsuranceMonthRecordService insuranceMonthRecordService;

//    @PostMapping("/queryInsuranceNum")
//    @ApiOperation("")
//    public Result<Integer> queryInsuranceNum(){
//      Integer num =  insuranceMonthRecordService.queryInsuranceNum();
//      return Result.ok(num);
//    }

	@PostMapping("/computeInsuranceData")
	@ApiOperation("")
	public Result<Integer> computeInsuranceData() {
		Integer year = insuranceMonthRecordService.computeInsuranceData();
		return Result.ok(year);
	}

	@PostMapping("/queryInsuranceRecordList")
	@ApiOperation("")
	public Result<BasePage<QueryInsuranceRecordListVO>> queryInsuranceRecordList(@RequestBody QueryInsuranceRecordListBO recordListBO) {
		BasePage<QueryInsuranceRecordListVO> page = insuranceMonthRecordService.queryInsuranceRecordList(recordListBO);
		return Result.ok(page);
	}

	@PostMapping("/queryInsuranceRecordList/{iRecordId}")
	@ApiOperation("()")
	public Result<QueryInsuranceRecordListVO> queryInsuranceRecord(@PathVariable String iRecordId) {
		QueryInsuranceRecordListVO data = insuranceMonthRecordService.queryInsuranceRecord(iRecordId);
		return Result.ok(data);
	}

	@PostMapping("/queryInsurancePageList")
	@ApiOperation("")
	public Result<BasePage<QueryInsurancePageListVO>> queryInsurancePageList(@RequestBody QueryInsurancePageListBO queryInsurancePageListBO) {
		BasePage<QueryInsurancePageListVO> page = insuranceMonthRecordService.queryInsurancePageList(queryInsurancePageListBO);
		return Result.ok(page);
	}

	@PostMapping("/deleteInsurance/{iRecordId}")
	@ApiOperation("")
	public Result deleteInsurance(@PathVariable Integer iRecordId) {
		insuranceMonthRecordService.deleteInsurance(iRecordId);
		;
		return Result.ok();
	}

}

