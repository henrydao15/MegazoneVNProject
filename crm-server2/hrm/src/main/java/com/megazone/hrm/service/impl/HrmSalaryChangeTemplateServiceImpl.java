package com.megazone.hrm.service.impl;

import com.megazone.core.common.JSON;
import com.megazone.core.exception.CrmException;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.common.HrmCodeEnum;
import com.megazone.hrm.entity.BO.SetChangeTemplateBO;
import com.megazone.hrm.entity.PO.HrmSalaryChangeTemplate;
import com.megazone.hrm.entity.VO.ChangeSalaryOptionVO;
import com.megazone.hrm.entity.VO.QueryChangeTemplateListVO;
import com.megazone.hrm.mapper.HrmSalaryChangeTemplateMapper;
import com.megazone.hrm.service.IHrmSalaryChangeTemplateService;
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
 * @since 2020-11-05
 */
@Service
public class HrmSalaryChangeTemplateServiceImpl extends BaseServiceImpl<HrmSalaryChangeTemplateMapper, HrmSalaryChangeTemplate> implements IHrmSalaryChangeTemplateService {


	@Autowired
	private HrmSalaryChangeTemplateMapper salaryChangeTemplateMapper;

	@Override
	public List<ChangeSalaryOptionVO> queryChangeSalaryOption() {
		return salaryChangeTemplateMapper.queryChangeSalaryOption();
	}

	@Override
	public void setChangeTemplate(SetChangeTemplateBO setChangeTemplateBO) {
		HrmSalaryChangeTemplate salaryChangeTemplate = new HrmSalaryChangeTemplate();
		salaryChangeTemplate.setId(setChangeTemplateBO.getId());
		salaryChangeTemplate.setTemplateName(setChangeTemplateBO.getTemplateName());
		salaryChangeTemplate.setValue(JSON.toJSONString(setChangeTemplateBO.getValue()));
		saveOrUpdate(salaryChangeTemplate);
	}

	@Override
	public List<QueryChangeTemplateListVO> queryChangeTemplateList() {
		List<HrmSalaryChangeTemplate> list = lambdaQuery().list();
		return list.stream().map(template -> {
			QueryChangeTemplateListVO changeTemplateListVO = new QueryChangeTemplateListVO();
			changeTemplateListVO.setId(template.getId());
			changeTemplateListVO.setTemplateName(template.getTemplateName());
			changeTemplateListVO.setIsDefault(template.getIsDefault());
			changeTemplateListVO.setValue(JSON.parseArray(template.getValue(), ChangeSalaryOptionVO.class));
			return changeTemplateListVO;
		}).collect(Collectors.toList());
	}

	@Override
	public void deleteChangeTemplate(Integer id) {
		HrmSalaryChangeTemplate template = getById(id);
		if (template.getIsDefault() == 1) {
			throw new CrmException(HrmCodeEnum.DEFAULT_TEMPLATE_CANNOT_BE_DELETED);
		}
		removeById(id);
	}
}
