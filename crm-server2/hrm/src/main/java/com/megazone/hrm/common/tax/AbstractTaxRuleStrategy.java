package com.megazone.hrm.common.tax;

import com.megazone.hrm.constant.IsEnum;
import com.megazone.hrm.entity.DTO.ComputeSalaryDTO;
import com.megazone.hrm.entity.PO.HrmSalaryMonthEmpRecord;
import com.megazone.hrm.entity.PO.HrmSalaryMonthOptionValue;
import com.megazone.hrm.entity.PO.HrmSalaryTaxRule;
import com.megazone.hrm.service.IHrmSalaryMonthEmpRecordService;
import com.megazone.hrm.service.IHrmSalaryMonthOptionValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Component
public abstract class AbstractTaxRuleStrategy {
	/**
	 *
	 */
	BigDecimal proxyPaySalary;
	/**
	 *
	 */
	BigDecimal shouldPaySalary;
	/**
	 *
	 */
	BigDecimal taxAfterPaySalary;

	/**
	 *
	 */
	BigDecimal taxSpecialGrandTotal;

	/**
	 *
	 */
	BigDecimal specialTaxSalary;

	/**
	 *
	 */
//    BigDecimal cumulativeTaxFreeIncome;

	@Autowired
	IHrmSalaryMonthEmpRecordService salaryMonthEmpRecordService;

	@Autowired
	IHrmSalaryMonthOptionValueService salaryMonthOptionValueService;

	/**
	 * @return
	 */
	abstract List<HrmSalaryMonthOptionValue> computeSalary(HrmSalaryMonthEmpRecord salaryMonthEmpRecord, HrmSalaryTaxRule taxRule,
														   Map<Integer, String> cumulativeTaxOfLastMonthData);

	AbstractTaxRuleStrategy baseComputeSalary(HrmSalaryMonthEmpRecord salaryMonthEmpRecord) {

		List<ComputeSalaryDTO> computeSalaryDTOS = salaryMonthOptionValueService.querySalaryOptionValue(salaryMonthEmpRecord.getSEmpRecordId());


		List<Integer> shouldPayCodeList = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90, 130, 140, 180, 200);
		shouldPaySalary = new BigDecimal(0);


		proxyPaySalary = new BigDecimal(0);

		specialTaxSalary = new BigDecimal(0);

		taxAfterPaySalary = new BigDecimal(0);

		taxSpecialGrandTotal = new BigDecimal(0);
		for (ComputeSalaryDTO computeSalaryDTO : computeSalaryDTOS) {

			if (computeSalaryDTO.getParentCode().equals(100)) {
				proxyPaySalary = proxyPaySalary.add(new BigDecimal(computeSalaryDTO.getValue()));
			}
			if (computeSalaryDTO.getParentCode().equals(170)) {
				specialTaxSalary = specialTaxSalary.add(new BigDecimal(computeSalaryDTO.getValue()));
			}
			if (computeSalaryDTO.getParentCode().equals(150)) {
				taxAfterPaySalary = taxAfterPaySalary.add(new BigDecimal(computeSalaryDTO.getValue()));
			}
			if (computeSalaryDTO.getParentCode().equals(160)) {
				taxAfterPaySalary = taxAfterPaySalary.subtract(new BigDecimal(computeSalaryDTO.getValue()));
			}
			if (computeSalaryDTO.getParentCode().equals(260)) {
				taxSpecialGrandTotal = taxSpecialGrandTotal.add(new BigDecimal(computeSalaryDTO.getValue()));
			}

			if (shouldPayCodeList.contains(computeSalaryDTO.getParentCode())) {
				if (computeSalaryDTO.getIsPlus() == IsEnum.YES.getValue()) {
					shouldPaySalary = shouldPaySalary.add(new BigDecimal(computeSalaryDTO.getValue()));
				} else if (computeSalaryDTO.getIsPlus() == IsEnum.NO.getValue()) {
					shouldPaySalary = shouldPaySalary.subtract(new BigDecimal(computeSalaryDTO.getValue()));
				}
			}
//            if (computeSalaryDTO.getIsCompute() == 1 && computeSalaryDTO.getIsPlus() == 1 && computeSalaryDTO.getIsTax() == 1){
//                cumulativeTaxFreeIncome = cumulativeTaxFreeIncome.add(new BigDecimal(computeSalaryDTO.getValue()));
//            }
		}
		return this;
	}


}
