package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmInsuranceScheme;
import com.megazone.hrm.entity.VO.InsuranceSchemeListVO;

import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmInsuranceSchemeMapper extends BaseMapper<HrmInsuranceScheme> {


	BasePage<InsuranceSchemeListVO> queryInsuranceSchemePageList(BasePage<InsuranceSchemeListVO> parse);

	/**
	 * @param schemeId
	 * @return
	 */
	Map<String, Object> queryInsuranceSchemeCountById(Integer schemeId);
}
