package com.megazone.hrm.common.tax;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import com.megazone.hrm.entity.PO.HrmSalaryMonthEmpRecord;
import com.megazone.hrm.entity.PO.HrmSalaryMonthOptionValue;
import com.megazone.hrm.entity.PO.HrmSalaryTaxRule;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author
 */
@Component("SALARY_TAX_TYPE")
public class SalaryAbstractTaxRuleStrategy extends AbstractTaxRuleStrategy {

	private static RangeMap<BigDecimal, TaxEntity> taxRateRangeMap = TreeRangeMap.create();

	static {
		taxRateRangeMap.put(Range.lessThan(new BigDecimal(0)), new TaxEntity(0, 0));
		taxRateRangeMap.put(Range.closed(new BigDecimal(0), new BigDecimal(36000)), new TaxEntity(3, 0));
		taxRateRangeMap.put(Range.openClosed(new BigDecimal(36000), new BigDecimal(144000)), new TaxEntity(10, 2520));
		taxRateRangeMap.put(Range.openClosed(new BigDecimal(144000), new BigDecimal(300000)), new TaxEntity(20, 16920));
		taxRateRangeMap.put(Range.openClosed(new BigDecimal(300000), new BigDecimal(420000)), new TaxEntity(25, 31920));
		taxRateRangeMap.put(Range.openClosed(new BigDecimal(420000), new BigDecimal(660000)), new TaxEntity(30, 52920));
		taxRateRangeMap.put(Range.openClosed(new BigDecimal(660000), new BigDecimal(960000)), new TaxEntity(35, 85920));
		taxRateRangeMap.put(Range.atLeast(new BigDecimal(960000)), new TaxEntity(45, 181920));
	}

	@Override
	public List<HrmSalaryMonthOptionValue> computeSalary(HrmSalaryMonthEmpRecord salaryMonthEmpRecord, HrmSalaryTaxRule taxRule,
														 Map<Integer, String> cumulativeTaxOfLastMonthData) {
		List<HrmSalaryMonthOptionValue> salaryMonthOptionValueList = new ArrayList<>();
		BigDecimal shouldTaxSalary = new BigDecimal(0);

		BigDecimal baseShouldTaxSalary = shouldPaySalary.add(specialTaxSalary).subtract(proxyPaySalary);
		if (baseShouldTaxSalary.compareTo(new BigDecimal(taxRule.getMarkingPoint())) > 0) {
			shouldTaxSalary = baseShouldTaxSalary.subtract(new BigDecimal(taxRule.getMarkingPoint()));
		}

		Map<Integer, Integer> lastTaxOptionCodeMap = new HashMap<>();
		lastTaxOptionCodeMap.put(270101, 250101);
		lastTaxOptionCodeMap.put(270102, 250102);
		lastTaxOptionCodeMap.put(270103, 250103);
		lastTaxOptionCodeMap.put(270106, 250105);
		Map<Integer, String> lastTaxOptionValueMap;
		List<HrmSalaryMonthOptionValue> lastTaxOptionValueList = salaryMonthOptionValueService.lambdaQuery().eq(HrmSalaryMonthOptionValue::getSEmpRecordId, salaryMonthEmpRecord.getSEmpRecordId())
				.in(HrmSalaryMonthOptionValue::getCode, lastTaxOptionCodeMap.values()).list();
		if (CollUtil.isNotEmpty(lastTaxOptionValueList) && cumulativeTaxOfLastMonthData == null) {

			lastTaxOptionValueMap = lastTaxOptionValueList.stream().collect(Collectors.toMap(HrmSalaryMonthOptionValue::getCode, HrmSalaryMonthOptionValue::getValue));
		} else {
			List<HrmSalaryMonthOptionValue> lastTaxOptionValueList1;

			Optional<HrmSalaryMonthEmpRecord> lastSalaryMonthEmpRecordOpt = salaryMonthEmpRecordService.lambdaQuery().eq(HrmSalaryMonthEmpRecord::getYear, salaryMonthEmpRecord.getYear())
					.eq(HrmSalaryMonthEmpRecord::getMonth, salaryMonthEmpRecord.getMonth() - 1).eq(HrmSalaryMonthEmpRecord::getEmployeeId, salaryMonthEmpRecord.getEmployeeId()).oneOpt();

			if (lastSalaryMonthEmpRecordOpt.isPresent()) {
				HrmSalaryMonthEmpRecord lastSalaryMonthEmpRecord = lastSalaryMonthEmpRecordOpt.get();
				lastTaxOptionValueList1 = salaryMonthOptionValueService.lambdaQuery().eq(HrmSalaryMonthOptionValue::getSEmpRecordId, lastSalaryMonthEmpRecord.getSEmpRecordId())
						.in(HrmSalaryMonthOptionValue::getCode, lastTaxOptionCodeMap.keySet()).list();
				if (CollUtil.isEmpty(lastTaxOptionValueList1)) {
					lastTaxOptionCodeMap.keySet().forEach(code -> {
						HrmSalaryMonthOptionValue salaryMonthOptionValue = new HrmSalaryMonthOptionValue();
						salaryMonthOptionValue.setSEmpRecordId(salaryMonthEmpRecord.getSEmpRecordId());
						salaryMonthOptionValue.setCode(code);
						salaryMonthOptionValue.setValue("0");
						lastTaxOptionValueList1.add(salaryMonthOptionValue);
					});
				}
			} else {

				lastTaxOptionValueList1 = new ArrayList<>();
				lastTaxOptionCodeMap.keySet().forEach(code -> {
					HrmSalaryMonthOptionValue salaryMonthOptionValue = new HrmSalaryMonthOptionValue();
					salaryMonthOptionValue.setSEmpRecordId(salaryMonthEmpRecord.getSEmpRecordId());
					salaryMonthOptionValue.setCode(code);
					salaryMonthOptionValue.setValue("0");
					lastTaxOptionValueList1.add(salaryMonthOptionValue);
				});
			}
			lastTaxOptionValueMap = lastTaxOptionValueList1.stream().peek(option -> option.setCode(lastTaxOptionCodeMap.get(option.getCode())))
					.collect(Collectors.toMap(HrmSalaryMonthOptionValue::getCode, HrmSalaryMonthOptionValue::getValue));
			if (CollUtil.isNotEmpty(cumulativeTaxOfLastMonthData)) {

				cumulativeTaxOfLastMonthData.forEach(lastTaxOptionValueMap::put);
			}
		}


		BigDecimal cumulativeIncome = new BigDecimal(lastTaxOptionValueMap.get(250101)).add(shouldPaySalary);

		BigDecimal cumulativeDeductions = new BigDecimal(lastTaxOptionValueMap.get(250102)).add(new BigDecimal(5000));

		BigDecimal cumulativeSpecialDeduction = new BigDecimal(lastTaxOptionValueMap.get(250103)).add(proxyPaySalary);

		BigDecimal cumulativeSpecialAdditionalDeduction = taxSpecialGrandTotal;


		BigDecimal cumulativeTaxableIncome = cumulativeIncome.subtract(cumulativeDeductions).subtract(cumulativeSpecialDeduction).subtract(cumulativeSpecialAdditionalDeduction);

		TaxEntity taxEntity = taxRateRangeMap.get(cumulativeTaxableIncome);
		BigDecimal cumulativeTaxPayable = cumulativeTaxableIncome.multiply(new BigDecimal(taxEntity.getTaxRate())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP)
				.subtract(new BigDecimal(taxEntity.getQuickDeduction())).setScale(2, BigDecimal.ROUND_HALF_UP);
		;

		BigDecimal payTaxSalary = cumulativeTaxPayable.subtract(new BigDecimal(lastTaxOptionValueMap.get(250105)));

		BigDecimal realPaySalary = shouldPaySalary.subtract(proxyPaySalary).subtract(payTaxSalary).add(taxAfterPaySalary);

		//210101	210
		//220101	220
		//230101	230
		//240101	240
		Map<Integer, String> codeValueMap = new HashMap<>();
		codeValueMap.put(210101, shouldPaySalary.toString());
		codeValueMap.put(220101, shouldTaxSalary.toString());
		codeValueMap.put(230101, payTaxSalary.toString());
		codeValueMap.put(240101, realPaySalary.toString());

		lastTaxOptionValueMap.forEach(codeValueMap::put);

		codeValueMap.put(270101, cumulativeIncome.toString());
		codeValueMap.put(270102, cumulativeDeductions.toString());
		codeValueMap.put(270103, cumulativeSpecialDeduction.toString());
		codeValueMap.put(270104, cumulativeSpecialAdditionalDeduction.toString());
		codeValueMap.put(270105, cumulativeTaxableIncome.toString());
		codeValueMap.put(270106, cumulativeTaxPayable.toString());
		salaryMonthOptionValueService.lambdaUpdate().in(HrmSalaryMonthOptionValue::getCode, Arrays.asList(
						210101, 220101, 230101, 240101,
						250101, 250102, 250103, 250105,
						270101, 270102, 270103, 270104, 270105, 270106))
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
