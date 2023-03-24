package com.megazone.hrm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.AddInsuranceSchemeBO;
import com.megazone.hrm.entity.BO.QueryInsuranceScaleBO;
import com.megazone.hrm.entity.BO.QueryInsuranceTypeBO;
import com.megazone.hrm.entity.PO.HrmInsuranceScheme;
import com.megazone.hrm.entity.VO.InsuranceSchemeListVO;
import com.megazone.hrm.entity.VO.InsuranceSchemeVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmInsuranceSchemeService extends BaseService<HrmInsuranceScheme> {

	/**
	 * @param addInsuranceSchemeBO
	 */
	void setInsuranceScheme(AddInsuranceSchemeBO addInsuranceSchemeBO);

	/**
	 * @param schemeId
	 */
	void deleteInsuranceScheme(Integer schemeId);

	/**
	 * @param pageEntity
	 * @return
	 */
	BasePage<InsuranceSchemeListVO> queryInsuranceSchemePageList(PageEntity pageEntity);

	/**
	 * @param schemeId
	 * @return
	 */
	InsuranceSchemeVO queryInsuranceSchemeById(Integer schemeId);

	/**
	 * @return
	 */
	List<HrmInsuranceScheme> queryInsuranceSchemeList();


	/**
	 * @param queryInsuranceTypeBO
	 * @return
	 */
	String queryInsuranceType(QueryInsuranceTypeBO queryInsuranceTypeBO);

	/**
	 * @param queryInsuranceScaleBO
	 * @return
	 */
	String queryInsuranceScale(QueryInsuranceScaleBO queryInsuranceScaleBO);

}
