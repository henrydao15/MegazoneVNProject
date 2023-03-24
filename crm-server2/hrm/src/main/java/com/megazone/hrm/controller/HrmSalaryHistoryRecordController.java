package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.entity.BO.QueryHistorySalaryDetailBO;
import com.megazone.hrm.entity.BO.QueryHistorySalaryListBO;
import com.megazone.hrm.entity.VO.QueryHistorySalaryDetailVO;
import com.megazone.hrm.entity.VO.QueryHistorySalaryListVO;
import com.megazone.hrm.service.IHrmSalaryMonthRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@RestController
@RequestMapping("/hrmSalaryHistoryRecord")
@Api(tags = "-")
public class HrmSalaryHistoryRecordController {

	@Autowired
	private IHrmSalaryMonthRecordService salaryMonthRecordService;

	@PostMapping("/queryHistorySalaryList")
	@ApiOperation("")
	public Result<BasePage<QueryHistorySalaryListVO>> queryHistorySalaryList(@RequestBody QueryHistorySalaryListBO queryHistorySalaryListBO) {
		BasePage<QueryHistorySalaryListVO> page = salaryMonthRecordService.queryHistorySalaryList(queryHistorySalaryListBO);
		return Result.ok(page);
	}

	@PostMapping("/queryHistorySalaryDetail")
	@ApiOperation("")
	public Result<QueryHistorySalaryDetailVO> queryHistorySalaryDetail(@RequestBody QueryHistorySalaryDetailBO queryHistorySalaryDetailBO) {
		QueryHistorySalaryDetailVO historySalaryDetail = salaryMonthRecordService.queryHistorySalaryDetail(queryHistorySalaryDetailBO);
		return Result.ok(historySalaryDetail);
	}

}

