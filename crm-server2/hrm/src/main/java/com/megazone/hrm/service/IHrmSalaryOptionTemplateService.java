package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmSalaryOptionTemplate;
import com.megazone.hrm.entity.VO.SalaryOptionVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmSalaryOptionTemplateService extends BaseService<HrmSalaryOptionTemplate> {

	/**
	 * @return
	 */
	List<SalaryOptionVO> querySalaryOptionTemplateList();
}
