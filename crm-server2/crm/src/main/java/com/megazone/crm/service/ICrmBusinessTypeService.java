package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.BO.CrmBusinessStatusBO;
import com.megazone.crm.entity.PO.CrmBusinessType;
import com.megazone.crm.entity.VO.CrmBusinessStatusVO;
import com.megazone.crm.entity.VO.CrmListBusinessStatusVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
public interface ICrmBusinessTypeService extends BaseService<CrmBusinessType> {

	/**
	 * @return data
	 */
	public List<CrmBusinessType> queryBusinessStatusOptions();

	/**
	 * @param businessStatusBO data
	 */
	public void boostBusinessStatus(CrmBusinessStatusBO businessStatusBO);

	CrmListBusinessStatusVO queryListBusinessStatus(Integer typeId, Integer statusId, Integer isEnd);

	/**
	 * @param businessId ID
	 * @return data
	 */
	public CrmBusinessStatusVO queryBusinessStatus(Integer businessId);

	String getBusinessTypeName(int businessTypeId);

	/**
	 * @param entity entity
	 * @return data
	 */
	public BasePage<CrmBusinessType> queryBusinessTypeList(PageEntity entity);

	/**
	 * @param typeId typeId
	 * @return data
	 */
	public CrmBusinessType getBusinessType(Integer typeId);

	/**
	 * @param object obj
	 */
	public void addBusinessType(JSONObject object);

	/**
	 * @param typeId typeId
	 */
	public void deleteById(Integer typeId);

	/**
	 * @param typeId typeId
	 * @param status
	 */
	public void updateStatus(Integer typeId, Integer status);
}
