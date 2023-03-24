package com.megazone.hrm.common.log;

import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.hrm.constant.LabelGroupEnum;
import com.megazone.hrm.entity.BO.AddEmployeeFieldBO;
import com.megazone.hrm.entity.PO.HrmInsuranceScheme;
import com.megazone.hrm.service.IHrmInsuranceSchemeService;

public class HrmConfigLog {

	private IHrmInsuranceSchemeService insuranceSchemeService = ApplicationContextHolder.getBean(IHrmInsuranceSchemeService.class);

	public Content saveField(AddEmployeeFieldBO addEmployeeFieldBO) {
		String name = LabelGroupEnum.parse(addEmployeeFieldBO.getLabelGroup()).getName();
		return new Content(name, "modified custom field:" + name, BehaviorEnum.UPDATE);
	}

	public Content deleteInsuranceScheme(Integer schemeId) {
		HrmInsuranceScheme insuranceScheme = insuranceSchemeService.getById(schemeId);
		return new Content(insuranceScheme.getSchemeName(), "Deleted social security scheme:" + insuranceScheme.getSchemeName(), BehaviorEnum.DELETE);
	}

}
