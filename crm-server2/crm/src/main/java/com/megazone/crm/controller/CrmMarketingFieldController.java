package com.megazone.crm.controller;

import com.megazone.core.common.Result;
import com.megazone.crm.entity.BO.MarketingFieldBO;
import com.megazone.crm.entity.PO.CrmMarketingField;
import com.megazone.crm.service.ICrmMarketingFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @date 2020/12/2
 */
@RestController
@RequestMapping("/crmMarketingField")
@Api(tags = "")
public class CrmMarketingFieldController {

	@Autowired
	private ICrmMarketingFieldService crmMarketingFieldService;

	/**
	 * @author wyq
	 */
	@ApiOperation("")
	@PostMapping("/queryField/{formId}")
	public Result<List<CrmMarketingField>> queryField(@PathVariable Integer formId) {
		List<CrmMarketingField> list = crmMarketingFieldService.queryField(formId);
		return Result.ok(list);
	}

	@ApiOperation("")
	@PostMapping("/saveField")
	public Result saveField(@RequestBody MarketingFieldBO marketingFieldBO) {
		crmMarketingFieldService.saveField(marketingFieldBO);
		return Result.ok();
	}
}
