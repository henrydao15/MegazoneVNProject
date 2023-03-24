package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmMyExamineBO;
import com.megazone.crm.entity.BO.CrmQueryExamineStepBO;
import com.megazone.crm.entity.BO.CrmSaveExamineBO;
import com.megazone.crm.entity.PO.CrmExamine;
import com.megazone.crm.entity.VO.CrmQueryAllExamineVO;
import com.megazone.crm.entity.VO.CrmQueryExamineStepVO;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
public interface ICrmExamineService extends BaseService<CrmExamine> {
	/**
	 * @param crmEnum enum
	 * @return data
	 */
	public Integer queryCount(CrmEnum crmEnum);

	void saveExamine(CrmSaveExamineBO crmSaveExamineBO);

	BasePage<CrmQueryAllExamineVO> queryAllExamine(PageEntity pageEntity);

	CrmQueryAllExamineVO queryExamineById(String examineId);

	void updateStatus(CrmExamine crmExamine);

	CrmQueryExamineStepVO queryExamineStep(CrmQueryExamineStepBO queryExamineStepBO);

	Boolean queryExamineStepByType(Integer categoryType);

	BasePage<JSONObject> myExamine(CrmMyExamineBO crmMyExamineBO);

}
