package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.BO.AddFileBO;
import com.megazone.hrm.entity.BO.QueryFileBySubTypeBO;
import com.megazone.hrm.entity.PO.HrmEmployeeFile;
import com.megazone.hrm.service.IHrmEmployeeFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmEmployeeFile")
@Api(tags = "-")
public class HrmEmployeeFileController {

	@Autowired
	private IHrmEmployeeFileService employeeFileService;

	@PostMapping("/queryFileNum/{employeeId}")
	@ApiOperation("")
	public Result<Map<String, Object>> queryFileNum(@PathVariable Integer employeeId) {
		Map<String, Object> map = employeeFileService.queryFileNum(employeeId);
		return Result.ok(map);
	}

	@PostMapping("/queryFileBySubType")
	@ApiOperation("")
	public Result<List<HrmEmployeeFile>> queryFileBySubType(@RequestBody QueryFileBySubTypeBO queryFileBySubTypeBO) {
		List<HrmEmployeeFile> list = employeeFileService.queryFileBySubType(queryFileBySubTypeBO);
		return Result.ok(list);
	}

	@PostMapping("/addFile")
	@ApiOperation("")
	public Result addFile(@RequestBody AddFileBO addFileBO) {
		employeeFileService.addFile(addFileBO);
		return Result.ok();
	}


	@PostMapping("/deleteFile/{employeeFileId}")
	@ApiOperation("")
	public Result deleteFile(@PathVariable Integer employeeFileId) {
		employeeFileService.deleteFile(employeeFileId);
		return Result.ok();
	}
}

