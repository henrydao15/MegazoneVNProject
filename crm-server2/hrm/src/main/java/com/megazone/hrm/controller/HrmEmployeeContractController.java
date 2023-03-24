package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.PO.HrmEmployeeContract;
import com.megazone.hrm.entity.VO.ContractInformationVO;
import com.megazone.hrm.service.IHrmEmployeeContractService;
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
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmEmployeeContract")
@Api(tags = "-")
public class HrmEmployeeContractController {

	@Autowired
	private IHrmEmployeeContractService employeeContractService;

	/**
	 *
	 */
	@PostMapping("/contractInformation/{employeeId}")
	@ApiOperation("")
	public Result<List<ContractInformationVO>> contractInformation(@PathVariable("employeeId") Integer employeeId) {
		List<ContractInformationVO> contractInformationVOList = employeeContractService.contractInformation(employeeId);
		return Result.ok(contractInformationVOList);
	}

	@PostMapping("/addContract")
	@ApiOperation("/")
	public Result addContract(@RequestBody HrmEmployeeContract employeeContract) {
		employeeContractService.addOrUpdateContract(employeeContract);
		return Result.ok();
	}

	@PostMapping("/setContract")
	@ApiOperation("/")
	public Result setContract(@RequestBody HrmEmployeeContract employeeContract) {
		employeeContractService.addOrUpdateContract(employeeContract);
		return Result.ok();
	}

	@PostMapping("/deleteContract/{contractId}")
	@ApiOperation("")
	public Result deleteContract(@PathVariable Integer contractId) {
		employeeContractService.deleteContract(contractId);
		return Result.ok();
	}
}

