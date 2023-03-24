package com.megazone.hrm.service.impl;

import com.megazone.core.exception.CrmException;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.common.HrmCodeEnum;
import com.megazone.hrm.entity.BO.SetTaxRuleBO;
import com.megazone.hrm.entity.PO.HrmSalaryGroup;
import com.megazone.hrm.entity.PO.HrmSalaryTaxRule;
import com.megazone.hrm.mapper.HrmSalaryTaxRuleMapper;
import com.megazone.hrm.service.IHrmSalaryGroupService;
import com.megazone.hrm.service.IHrmSalaryTaxRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@Service
public class HrmSalaryTaxRuleServiceImpl extends BaseServiceImpl<HrmSalaryTaxRuleMapper, HrmSalaryTaxRule> implements IHrmSalaryTaxRuleService {

	@Autowired
	private IHrmSalaryGroupService salaryGroupService;

	@Override
	public List<HrmSalaryTaxRule> queryTaxRuleList() {
		return list().stream().peek(hrmSalaryTaxRule -> {
			Integer count = salaryGroupService.lambdaQuery().eq(HrmSalaryGroup::getRuleId, hrmSalaryTaxRule.getRuleId())
					.count();
			hrmSalaryTaxRule.setSalaryGroupCount(count);
		}).collect(Collectors.toList());
	}

	@Override
	public void setTaxRule(SetTaxRuleBO setTaxRuleBO) {
		HrmSalaryTaxRule taxRule = getById(setTaxRuleBO.getRuleId());
		if (taxRule.getTaxType() != 1) {
			throw new CrmException(HrmCodeEnum.CAN_ONLY_MODIFY_SALARY_AND_INCOME_TAX);
		}
		lambdaUpdate().set(HrmSalaryTaxRule::getCycleType, setTaxRuleBO.getCycleType())
				.eq(HrmSalaryTaxRule::getRuleId, setTaxRuleBO.getRuleId())
				.update();
	}
}
