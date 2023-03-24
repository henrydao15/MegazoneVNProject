package com.megazone.hrm.controller;

import com.megazone.core.common.Result;
import com.megazone.hrm.entity.PO.HrmRecruitPostType;
import com.megazone.hrm.service.IHrmRecruitPostTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/hrmRecruitPostType")
@RestController
@Api(tags = "-")
public class HrmRecruitPostTypeController {


	@Autowired
	private IHrmRecruitPostTypeService recruitPostTypeService;


	@PostMapping("/queryPostType")
	@ApiOperation("")
	public Result<List<HrmRecruitPostType>> queryPostType() {
		List<HrmRecruitPostType> list = recruitPostTypeService.queryPostType();
		return Result.ok(list);
	}

}
