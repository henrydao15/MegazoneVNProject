package com.megazone.hrm.common.log;

import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.hrm.entity.PO.HrmSalaryGroup;
import com.megazone.hrm.service.IHrmSalaryGroupService;
import org.springframework.web.bind.annotation.PathVariable;

public class HrmSalaryGroupLog {

	private IHrmSalaryGroupService salaryGroupService = ApplicationContextHolder.getBean(IHrmSalaryGroupService.class);

	public Content deleteSalaryGroup(@PathVariable Integer groupId) {
		HrmSalaryGroup salaryGroup = salaryGroupService.getById(groupId);
		return new Content(salaryGroup.getGroupName(), "Deleted salary group:" + salaryGroup.getGroupName(), BehaviorEnum.DELETE);
	}
}
