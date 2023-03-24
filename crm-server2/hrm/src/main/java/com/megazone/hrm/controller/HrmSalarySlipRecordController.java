package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.HrmSalarySlipOption;
import com.megazone.hrm.entity.VO.QuerySendDetailListVO;
import com.megazone.hrm.entity.VO.QuerySendRecordListVO;
import com.megazone.hrm.entity.VO.SlipEmployeeVO;
import com.megazone.hrm.service.IHrmSalarySlipRecordService;
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
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/hrmSalarySlipRecord")
@Api(tags = "-")
@SysLog(subModel = SubModelType.HRM_SALARY_SLIP)
public class HrmSalarySlipRecordController {


	@Autowired
	private IHrmSalarySlipRecordService slipRecordService;

	@PostMapping("/querySlipEmployeePageList")
	@ApiOperation("")
	public Result<BasePage<SlipEmployeeVO>> querySlipEmployeePageList(@RequestBody QuerySlipEmployeePageListBO slipEmployeePageListBO) {
		BasePage<SlipEmployeeVO> page = slipRecordService.querySlipEmployeePageList(slipEmployeePageListBO);
		return Result.ok(page);
	}

	@PostMapping("/sendSalarySlip")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "Send payroll", detail = "Send payroll")
	public Result sendSalarySlip(@RequestBody SendSalarySlipBO sendSalarySlipBO) {
		slipRecordService.sendSalarySlip(sendSalarySlipBO);
		return Result.ok();
	}

	@PostMapping("/querySendRecordList")
	@ApiOperation("")
	public Result<BasePage<QuerySendRecordListVO>> querySendRecordList(@RequestBody QuerySendRecordListBO querySendRecordListBO) {
		BasePage<QuerySendRecordListVO> page = slipRecordService.querySendRecordList(querySendRecordListBO);
		return Result.ok(page);
	}

	@PostMapping("/deleteSendRecord/{id}")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "Delete send records", detail = "Delete send records")
	public Result deleteSendRecord(@PathVariable String id) {
		slipRecordService.deleteSendRecord(id);
		return Result.ok();
	}

	@PostMapping("/querySendDetailList")
	@ApiOperation("")
	public Result<BasePage<QuerySendDetailListVO>> querySendDetailList(@RequestBody QuerySendDetailListBO querySendRecordListBO) {
		BasePage<QuerySendDetailListVO> page = slipRecordService.querySendDetailList(querySendRecordListBO);
		return Result.ok(page);
	}

	@PostMapping("/querySlipDetail/{id}")
	@ApiOperation("")
	public Result<List<HrmSalarySlipOption>> querySlipDetail(@PathVariable Integer id) {
		List<HrmSalarySlipOption> list = slipRecordService.querySlipDetail(id);
		return Result.ok(list);
	}

	@PostMapping("/setSlipRemarks")
	@ApiOperation("")
	@SysLogHandler(behavior = BehaviorEnum.SAVE, object = "payroll note", detail = "'Add or modify payroll notes: '+#setSlipRemarksBO.remarks")
	public Result setSlipRemarks(@RequestBody SetSlipRemarksBO setSlipRemarksBO) {
		slipRecordService.setSlipRemarks(setSlipRemarksBO);
		return Result.ok();
	}

}

