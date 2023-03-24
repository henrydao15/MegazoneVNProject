package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.entity.BO.QuerySalarySlipListBO;
import com.megazone.hrm.entity.VO.QuerySalarySlipListVO;
import com.megazone.hrm.service.IHrmSalarySlipService;
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
 * @since 2020-11-03
 */
@RestController
@RequestMapping("/hrmSalarySlip")
@Api(tags = "-")
public class HrmSalarySlipController {

	@Autowired
	private IHrmSalarySlipService salarySlipService;

	@PostMapping("/querySalarySlipList")
	@ApiOperation("")
	public Result<BasePage<QuerySalarySlipListVO>> querySalarySlipList(@RequestBody QuerySalarySlipListBO querySalarySlipListBO) {
		BasePage<QuerySalarySlipListVO> page = salarySlipService.querySalarySlipList(querySalarySlipListBO);
		return Result.ok(page);
	}

}

