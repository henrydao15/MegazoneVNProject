package com.megazone.crm.controller;

import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.crm.service.ICrmCommonService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @date 2020/9/16
 */
@RestController
@RequestMapping("/crmAnalysis")
@Api(tags = "crm")
public class CrmAnalysisController {

	@Autowired
	private ICrmCommonService crmCommonService;

	@PostMapping("/initCrmData")
	@ApiExplain("crm")
	public Result<Boolean> initCrmData() {
		return Result.ok(crmCommonService.initCrmData());
	}

}
