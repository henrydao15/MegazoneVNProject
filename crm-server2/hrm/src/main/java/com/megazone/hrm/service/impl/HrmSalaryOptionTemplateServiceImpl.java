package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.RecursionUtil;
import com.megazone.hrm.entity.PO.HrmSalaryOptionTemplate;
import com.megazone.hrm.entity.VO.SalaryOptionVO;
import com.megazone.hrm.mapper.HrmSalaryOptionMapper;
import com.megazone.hrm.mapper.HrmSalaryOptionTemplateMapper;
import com.megazone.hrm.service.IHrmSalaryOptionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class HrmSalaryOptionTemplateServiceImpl extends BaseServiceImpl<HrmSalaryOptionTemplateMapper, HrmSalaryOptionTemplate> implements IHrmSalaryOptionTemplateService {

	@Autowired
	private HrmSalaryOptionMapper salaryOptionMapper;

	@Override
	public List<SalaryOptionVO> querySalaryOptionTemplateList() {
		List<HrmSalaryOptionTemplate> list = salaryOptionMapper.querySalaryOptionTemplateList();
		return RecursionUtil.getChildListTree(list, "parentCode", 0, "code", "children", SalaryOptionVO.class);
	}
}
