package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmSalaryChangeTemplate;
import com.megazone.hrm.entity.VO.ChangeSalaryOptionVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-05
 */
public interface HrmSalaryChangeTemplateMapper extends BaseMapper<HrmSalaryChangeTemplate> {

	List<ChangeSalaryOptionVO> queryChangeSalaryOption();

}
