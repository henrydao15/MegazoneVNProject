package com.megazone.hrm.common.tax;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import com.megazone.hrm.entity.PO.HrmSalaryMonthEmpRecord;
import com.megazone.hrm.entity.PO.HrmSalaryMonthOptionValue;
import com.megazone.hrm.entity.PO.HrmSalaryTaxRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author
 */
@Component("REMUNERATION_TAX_TYPE")
public class RemunerationAbstractTaxRuleStrategy extends AbstractTaxRuleStrategy {

	private static final BigDecimal REMUNERATION_LEVEL = new BigDecimal(4000);

	private static RangeMap<BigDecimal, TaxEntity> taxRateRangeMap = TreeRangeMap.create();

	static {
		taxRateRangeMap.put(Range.lessThan(new BigDecimal(0)), new TaxEntity(20, 0));
		taxRateRangeMap.put(Range.closed(new BigDecimal(0), new BigDecimal(20000)), new TaxEntity(20, 0));
		taxRateRangeMap.put(Range.openClosed(new BigDecimal(20000), new BigDecimal(50000)), new TaxEntity(30, 2000));
		taxRateRangeMap.put(Range.atLeast(new BigDecimal(50000)), new TaxEntity(40, 7000));
	}

	@Override
	List<HrmSalaryMonthOptionValue> computeSalary(HrmSalaryMonthEmpRecord salaryMonthEmpRecord, HrmSalaryTaxRule taxRule, Map<Integer, String> cumulativeTaxOfLastMonthData) {
		List<HrmSalaryMonthOptionValue> salaryMonthOptionValueList = new ArrayList<>();

		BigDecimal shouldTaxSalary;
		if (shouldPaySalary.compareTo(REMUNERATION_LEVEL) <= 0) {
			shouldTaxSalary = shouldPaySalary.subtract(new BigDecimal(800)).setScale(2, BigDecimal.ROUND_HALF_UP);
		} else {
			shouldTaxSalary = shouldPaySalary.multiply(new BigDecimal(0.8)).setScale(2, BigDecimal.ROUND_HALF_UP);
			;
		}
		TaxEntity taxEntity = taxRateRangeMap.get(shouldTaxSalary);
		BigDecimal payTaxSalary = shouldTaxSalary.multiply(new BigDecimal(taxEntity.getTaxRate())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP)
				.subtract(new BigDecimal(taxEntity.getQuickDeduction())).setScale(2, BigDecimal.ROUND_HALF_UP);
		;
		BigDecimal realPaySalary = shouldPaySalary.subtract(payTaxSalary).setScale(2, BigDecimal.ROUND_HALF_UP);
		;
		//210101	210
		//220101	220
		//230101	230
		//240101	240
		Map<Integer, String> codeValueMap = new HashMap<>();
		codeValueMap.put(210101, shouldPaySalary.toString());
		codeValueMap.put(220101, shouldTaxSalary.toString());
		codeValueMap.put(230101, payTaxSalary.toString());
		codeValueMap.put(240101, realPaySalary.toString());
		salaryMonthOptionValueService.lambdaUpdate().in(HrmSalaryMonthOptionValue::getCode, Arrays.asList(
						210101, 220101, 230101, 240101))
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
