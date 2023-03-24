package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.constant.ConfigType;
import com.megazone.hrm.entity.PO.HrmConfig;
import com.megazone.hrm.service.IHrmConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/hrmRecruitEliminate")
@Api(tags = "()")
public class HrmRecruitEliminateController {

	@Autowired
	private IHrmConfigService configService;

	@PostMapping("/queryRecruitEliminateList")
	@ApiOperation("")
	public Result<List<String>> queryRecruitEliminateList() {
		List<String> list = configService.lambdaQuery().eq(HrmConfig::getType, ConfigType.ELIMINATION_REASONS.getValue()).list()
				.stream().map(HrmConfig::getValue).collect(Collectors.toList());
		return Result.ok(list);
	}

}

