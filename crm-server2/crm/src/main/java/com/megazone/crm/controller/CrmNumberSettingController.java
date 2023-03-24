package com.megazone.crm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.crm.entity.VO.CrmNumberSettingVO;
import com.megazone.crm.service.ICrmNumberSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/crmNumberSetting")
@Api(tags = "")
public class CrmNumberSettingController {

	@Autowired
	private ICrmNumberSettingService crmNumberSettingService;

	/**
	 *
	 */
	@ApiOperation(value = "")
	@PostMapping("/queryNumberSetting")
	public Result<List<CrmNumberSettingVO>> queryNumberSetting() {
		List<CrmNumberSettingVO> settingVOS = crmNumberSettingService.queryNumberSetting();
		return Result.ok(settingVOS);
	}

	/**
	 *
	 */
	@ApiOperation(value = "")
	@PostMapping("/queryInvoiceNumberSetting")
	public Result<AdminConfig> queryInvoiceNumberSetting() {
		AdminConfig adminConfig = crmNumberSettingService.queryInvoiceNumberSetting();
		return Result.ok(adminConfig);
	}

	@ApiOperation(value = "")
	@PostMapping("/setNumberSetting")
	@SysLogHandler(applicationName = "admin", subModel = SubModelType.ADMIN_CUSTOMER_MANAGEMENT, behavior = BehaviorEnum.UPDATE, object = "Modify numbering rule settings", detail = "Modify numbering rule settings")
	public Result setNumberSetting(@RequestBody @Valid List<CrmNumberSettingVO> numberSettingList) {
		crmNumberSettingService.setNumberSetting(numberSettingList);
		return Result.ok();
	}
}

