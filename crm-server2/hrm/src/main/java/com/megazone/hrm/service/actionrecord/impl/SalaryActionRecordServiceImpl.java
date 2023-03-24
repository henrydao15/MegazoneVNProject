package com.megazone.hrm.service.actionrecord.impl;

import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.hrm.entity.PO.HrmSalaryMonthRecord;
import com.megazone.hrm.service.actionrecord.AbstractHrmActionRecordService;
import org.springframework.stereotype.Service;

@Service("salaryActionRecordService")
@SysLog(subModel = SubModelType.HRM_SALARY)
public class SalaryActionRecordServiceImpl extends AbstractHrmActionRecordService {

	@SysLogHandler(isReturn = true)
	public Content addNextMonthSalaryLog(HrmSalaryMonthRecord salaryMonthRecord) {
		String detail = "Add" + salaryMonthRecord.getYear() + "-" + salaryMonthRecord.getTitle();
		return new Content(salaryMonthRecord.getTitle(), detail, BehaviorEnum.SAVE);
	}

	@SysLogHandler(isReturn = true)
	public Content computeSalaryDataLog(HrmSalaryMonthRecord salaryMonthRecord) {
		String detail = "Accounting" + salaryMonthRecord.getYear() + "-" + salaryMonthRecord.getTitle();
		return new Content(salaryMonthRecord.getTitle(), detail, BehaviorEnum.SAVE);
	}

	@SysLogHandler(isReturn = true)
	public Content deleteSalaryLog(HrmSalaryMonthRecord salaryMonthRecord) {
		String detail = "Delete" + salaryMonthRecord.getYear() + "-" + salaryMonthRecord.getTitle();
		return new Content(salaryMonthRecord.getTitle(), detail, BehaviorEnum.SAVE);
	}
}
