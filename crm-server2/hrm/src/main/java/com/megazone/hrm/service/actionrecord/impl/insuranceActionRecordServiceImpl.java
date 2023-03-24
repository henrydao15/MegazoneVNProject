package com.megazone.hrm.service.actionrecord.impl;

import com.megazone.core.common.SubModelType;
import com.megazone.core.common.log.BehaviorEnum;
import com.megazone.core.common.log.Content;
import com.megazone.core.common.log.SysLog;
import com.megazone.core.common.log.SysLogHandler;
import com.megazone.hrm.entity.PO.HrmInsuranceMonthRecord;
import com.megazone.hrm.service.actionrecord.AbstractHrmActionRecordService;
import org.springframework.stereotype.Service;

@Service("insuranceActionRecordService")
@SysLog(subModel = SubModelType.HRM_SOCIAL_SECURITY)
public class insuranceActionRecordServiceImpl extends AbstractHrmActionRecordService {

	@SysLogHandler(isReturn = true)
	public Content computeInsuranceDataLog(HrmInsuranceMonthRecord insuranceMonthRecord) {
		String detail = "Generate" + insuranceMonthRecord.getYear() + "=" + insuranceMonthRecord.getTitle();
		return new Content(insuranceMonthRecord.getTitle(), detail, BehaviorEnum.SAVE);
	}

	@SysLogHandler(isReturn = true)
	public Content deleteInsurance(HrmInsuranceMonthRecord insuranceMonthRecord) {
		String detail = "Deleted" + insuranceMonthRecord.getYear() + "=" + insuranceMonthRecord.getTitle();
		return new Content(insuranceMonthRecord.getTitle(), detail, BehaviorEnum.DELETE);
	}
}
