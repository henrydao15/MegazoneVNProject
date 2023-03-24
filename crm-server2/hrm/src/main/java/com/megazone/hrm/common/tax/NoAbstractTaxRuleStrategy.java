package com.megazone.hrm.common.tax;

import com.megazone.hrm.entity.PO.HrmSalaryMonthEmpRecord;
import com.megazone.hrm.entity.PO.HrmSalaryMonthOptionValue;
import com.megazone.hrm.entity.PO.HrmSalaryTaxRule;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author
 */
@Component("NO_TAX_TYPE")
public class NoAbstractTaxRuleStrategy extends AbstractTaxRuleStrategy {
	@Override
	List<HrmSalaryMonthOptionValue> computeSalary(HrmSalaryMonthEmpRecord salaryMonthEmpRecord, HrmSalaryTaxRule taxRule, Map<Integer, String> cumulativeTaxOfLastMonthData) {
		List<HrmSalaryMonthOptionValue> salaryMonthOptionValueList = new ArrayList<>();
		//210101	210
		//220101	220
		//230101	230
		//240101	240
		Map<Integer, String> codeValueMap = new HashMap<>();
		codeValueMap.put(210101, shouldPaySalary.toString());
		codeValueMap.put(240101, shouldPaySalary.toString());
		salaryMonthOptionValueService.lambdaUpdate().in(HrmSalaryMonthOptionValue::getCode, Arrays.asList(
						210101, 240101))
				.eq(HrmSalaryMonthOptionValue::getSEmpRecordId, salaryMonthEmpRecord.getSEmpRecordId()).remove();
		codeValueMap.forEach((code, value) -> {
			HrmSalaryMonthOptionValue salaryMonthOptionValue = new HrmSalaryMonthOptionValue();
			salaryMonthOptionValue.setSEmpRecordId(salaryMonthEmpRecord.getSEmpRecordId());
			salaryMonthOptionValue.setCode(code);
			salaryMonthOptionValue.setValue(value);
			salaryMonthOptionValueList.add(salaryMonthOptionValue);
		});
		return salaryMonthOptionValueList;
	}
}
