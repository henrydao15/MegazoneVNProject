package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.SetChangeTemplateBO;
import com.megazone.hrm.entity.PO.HrmSalaryChangeTemplate;
import com.megazone.hrm.entity.VO.ChangeSalaryOptionVO;
import com.megazone.hrm.entity.VO.QueryChangeTemplateListVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-05
 */
public interface IHrmSalaryChangeTemplateService extends BaseService<HrmSalaryChangeTemplate> {

	/**
	 * @return
	 */
	List<ChangeSalaryOptionVO> queryChangeSalaryOption();

	/**
	 * @param setChangeTemplateBO
	 */
	void setChangeTemplate(SetChangeTemplateBO setChangeTemplateBO);

	/**
	 * @return
	 */
	List<QueryChangeTemplateListVO> queryChangeTemplateList();

	/**
	 * @param id
	 */
	void deleteChangeTemplate(Integer id);
}
