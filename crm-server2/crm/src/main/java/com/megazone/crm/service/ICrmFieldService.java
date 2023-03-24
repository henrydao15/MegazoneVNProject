package com.megazone.crm.service;

import com.megazone.core.common.FieldEnum;
import com.megazone.core.common.JSONObject;
import com.megazone.core.feign.crm.entity.ExamineField;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmField;
import com.megazone.crm.entity.VO.CrmFieldSortVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-19
 */
public interface ICrmFieldService extends BaseService<CrmField> {

	/**
	 * @return data
	 */
	public List<CrmFieldsBO> queryFields();

	/**
	 * @param crmFieldBO data
	 */
	public void saveField(CrmFieldBO crmFieldBO);

	/**
	 * @param label       label
	 * @param isQueryHide
	 * @return data
	 */
	public List<CrmField> list(Integer label, boolean isQueryHide);

	public List<List<CrmField>> queryFormPositionFieldList(Integer label, boolean isQueryHide);

	/**
	 * @param label label
	 * @return data
	 */
	public List<CrmFieldSortVO> queryListHead(Integer label);

	/**
	 * @param fieldStyle data
	 */
	public void setFieldStyle(CrmFieldStyleBO fieldStyle);

	/**
	 * @param fieldSort data
	 */
	public void setFieldConfig(CrmFieldSortBO fieldSort);

	/**
	 * @param label
	 */
	public JSONObject queryFieldConfig(Integer label);

	/**
	 * @param crmModel data
	 * @return data
	 */
	public List<CrmModelFiledVO> queryField(CrmModel crmModel);

	public List<List<CrmModelFiledVO>> queryFormPositionFieldVO(CrmModel crmModel);

	/**
	 * @param type
	 * @return data
	 */
//  TODO  @Cached(name = CrmConst.FIELD_SORT_CACHE_NAME)  (,es)
	public List<CrmModelFiledVO> queryField(Integer type);

	/**
	 * @param verifyBO verify
	 * @return data
	 */
	public CrmFieldVerifyBO verify(CrmFieldVerifyBO verifyBO);

	/**
	 * @return data
	 */
	public List<CrmField> queryFileField();

	/**
	 * @param record   data
	 * @param typeEnum type
	 */
	public void recordToFormType(CrmModelFiledVO record, FieldEnum typeEnum);

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	public long queryCustomerFieldDuplicateByNoFixed(String name, Object value);

	/**
	 * @param name
	 * @param value
	 * @return
	 */
	Integer queryCustomerFieldDuplicateByFixed(String name, Object value);

	void setPoolFieldStyle(CrmFieldStyleBO fieldStyle);

	public List<ExamineField> queryExamineField(Integer label);

}
