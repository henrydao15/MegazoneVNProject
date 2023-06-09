package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.BO.QueryRecordListBO;
import com.megazone.hrm.entity.VO.QueryRecordListVO;
import com.megazone.hrm.service.IHrmActionRecordService;
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
 * hrm
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmActionRecord")
@Api(tags = "-")
public class HrmActionRecordController {

	@Autowired
	private IHrmActionRecordService actionRecordService;


	@PostMapping("/queryRecordList")
	@ApiOperation("")
	public Result<List<QueryRecordListVO>> queryRecordList(@RequestBody QueryRecordListBO queryRecordListBO) {
		List<QueryRecordListVO> list = actionRecordService.queryRecordList(queryRecordListBO);
		return Result.ok(list);
	}

}

