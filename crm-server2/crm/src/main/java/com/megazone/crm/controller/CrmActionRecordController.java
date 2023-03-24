package com.megazone.crm.controller;


import com.megazone.core.common.Result;
import com.megazone.crm.entity.VO.CrmActionRecordVO;
import com.megazone.crm.service.ICrmActionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-25
 */
@RestController
@RequestMapping("/crmActionRecord")
@Api(tags = "")
public class CrmActionRecordController {

	@Autowired
	private ICrmActionRecordService crmActionRecordService;

	@PostMapping("/queryRecordOptions")
	@ApiOperation("")
	public Result<List<String>> queryRecordOptions() {
		List<String> strings = crmActionRecordService.queryRecordOptions();
		return Result.ok(strings);
	}

	@PostMapping("/queryRecordList")
	@ApiOperation("")
	public Result<List<CrmActionRecordVO>> queryRecordList(@RequestParam("actionId") Integer actionId, @RequestParam("types") Integer types) {
		List<CrmActionRecordVO> recordVOS = crmActionRecordService.queryRecordList(actionId, types);
		return Result.ok(recordVOS);
	}
}

