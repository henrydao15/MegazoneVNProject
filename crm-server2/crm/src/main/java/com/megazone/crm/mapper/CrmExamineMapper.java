package com.megazone.crm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.BO.CrmMyExamineBO;
import com.megazone.crm.entity.PO.CrmExamine;
import com.megazone.crm.entity.VO.CrmQueryAllExamineVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
public interface CrmExamineMapper extends BaseMapper<CrmExamine> {

	BasePage<CrmQueryAllExamineVO> queryExaminePage(BasePage<Object> parse);

	BasePage<JSONObject> myExamine(BasePage<Object> parse, @Param("data") CrmMyExamineBO crmMyExamineBO, @Param("isAdmin") boolean isAdmin,
								   @Param("userId") Long userId);

	CrmExamine selectCrmExamineByUser(@Param("categoryType") Integer categoryType, @Param("isAdmin") boolean isAdmin, @Param("userId") Long userId, @Param("deptId") Integer deptId);
}
