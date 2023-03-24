package com.megazone.hrm.common.log;

import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.hrm.entity.BO.SetChangeSalaryRecordBO;
import com.megazone.hrm.entity.BO.SetFixSalaryRecordBO;
import com.megazone.hrm.entity.PO.HrmEmployee;
import com.megazone.hrm.service.IHrmEmployeeService;

public class HrmSalaryArchivesLog {

	private IHrmEmployeeService employeeService = ApplicationContextHolder.getBean(IHrmEmployeeService.class);

	public Content setFixSalaryRecord(SetFixSalaryRecordBO setFixSalaryRecordBO) {
		HrmEmployee employee = employeeService.getById(setFixSalaryRecordBO.getEmployeeId());
		return new Content(employee.getEmployeeName(), "give employee" + employee.getEmployeeName() + "fixed salary", BehaviorEnum.SAVE);
	}

	public Content setChangeSalaryRecord(SetChangeSalaryRecordBO setChangeSalaryRecordBO) {
		HrmEmployee employee = employeeService.getById(setChangeSalaryRecordBO.getEmployeeId());
		return new Content(employee.getEmployeeName(), "give employee" + employee.getEmployeeName() + "salary adjustment", BehaviorEnum.SAVE);
	}
}
