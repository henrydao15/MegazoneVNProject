package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmSalaryOption;
import com.megazone.hrm.entity.PO.HrmSalaryOptionTemplate;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface HrmSalaryOptionMapper extends BaseMapper<HrmSalaryOption> {

	List<HrmSalaryOptionTemplate> querySalaryOptionTemplateList();

}
