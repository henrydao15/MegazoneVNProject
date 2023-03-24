package com.megazone.hrm.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.RecursionUtil;
import com.megazone.hrm.entity.BO.SetSalaryOptionBO;
import com.megazone.hrm.entity.PO.HrmSalaryOption;
import com.megazone.hrm.entity.VO.SalaryOptionDetailVO;
import com.megazone.hrm.entity.VO.SalaryOptionVO;
import com.megazone.hrm.mapper.HrmSalaryOptionMapper;
import com.megazone.hrm.service.IHrmSalaryOptionService;
import com.megazone.hrm.service.IHrmSalaryOptionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@Service
public class HrmSalaryOptionServiceImpl extends BaseServiceImpl<HrmSalaryOptionMapper, HrmSalaryOption> implements IHrmSalaryOptionService {


	@Autowired
	private IHrmSalaryOptionTemplateService salaryOptionTemplateService;

	@Override
	public List<SalaryOptionVO> querySalaryOptionList() {
		List<HrmSalaryOption> list = lambdaQuery().eq(HrmSalaryOption::getIsOpen, 1).orderByAsc(HrmSalaryOption::getCode).list();
		return RecursionUtil.getChildListTree(list, "parentCode", 0, "code", "children", SalaryOptionVO.class);
	}

	@Override
	public SalaryOptionDetailVO querySalaryOptionDetail() {
		List<SalaryOptionVO> templateOption = salaryOptionTemplateService.querySalaryOptionTemplateList();
		List<SalaryOptionVO> useOption = querySalaryOptionList();
		useOption.removeIf(salaryOptionVO -> CollUtil.isEmpty(salaryOptionVO.getChildren()));
		return new SalaryOptionDetailVO(templateOption, useOption);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setSalaryOption(SetSalaryOptionBO setSalaryOptionBO) {
//        List<Integer> hideList = setSalaryOptionBO.getHideList();
//        if (CollUtil.isNotEmpty(hideList)){
//            lambdaUpdate().in(HrmSalaryOption::getCode,hideList).remove();
////            lambdaUpdate().set(HrmSalaryOption::getIsOpen,1).notIn(HrmSalaryOption::getCode,hideList).or().notIn(HrmSalaryOption::getParentCode,hideList).update();
//        }
		lambdaUpdate().ne(HrmSalaryOption::getParentCode, 0).eq(HrmSalaryOption::getIsOpen, 1).remove();
		List<HrmSalaryOption> optionList = setSalaryOptionBO.getOptionList();
		optionList.forEach(option -> {
			option.setOptionId(null);
			option.setIsOpen(1);
		});
		saveBatch(optionList);
	}
}
