package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.PO.CrmCustomerPool;
import com.megazone.crm.entity.PO.CrmCustomerPoolFieldSort;
import com.megazone.crm.entity.VO.CrmCustomerPoolVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-29
 */
public interface ICrmCustomerPoolService extends BaseService<CrmCustomerPool> {

	/**
	 * @param pageEntity entity
	 */
	public BasePage<CrmCustomerPoolVO> queryPoolSettingList(PageEntity pageEntity);

	/**
	 * @return data
	 */
	public List<CrmCustomerPool> queryPoolNameList();

	/**
	 * @param poolId ID
	 * @param status
	 */
	public void changeStatus(Integer poolId, Integer status);

	/**
	 * @param prePoolId  ID
	 * @param postPoolId ID
	 */
	public void transfer(Integer prePoolId, Integer postPoolId);

	/**
	 * @param poolId ID
	 * @return data
	 */
	public CrmCustomerPoolVO queryPoolById(Integer poolId);

	/**
	 *
	 */
	public List<CrmModelFiledVO> queryPoolField();

	/**
	 * @param ids ids
	 */
	public void deleteByIds(List<Integer> ids, Integer poolId);

	/**
	 * @return data
	 */
	public List<String> queryCustomerLevel();

	/**
	 * @param jsonObject obj
	 */
	public void setCustomerPool(JSONObject jsonObject);

	/**
	 * @param poolId ID
	 * @return data
	 */
	public JSONObject queryPoolFieldConfig(Integer poolId);

	/**
	 * @param object obj
	 */
	public void poolFieldConfig(JSONObject object);

	/**
	 *
	 */
	public void deleteCustomerPool(Integer poolId);

	/**
	 *
	 */
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search, boolean isExcel);

	/**
	 *
	 */
	public List<CrmCustomerPool> queryPoolNameListByAuth();

	/**
	 *
	 */
	public Boolean queryAuthListByPoolId(Integer poolId);

	/**
	 *
	 */
	public List<CrmCustomerPoolFieldSort> queryPoolListHead(Integer poolId);

	/**
	 * @param poolId ID
	 * @return auth
	 */
	public JSONObject queryAuthByPoolId(Integer poolId);

	/**
	 * @param poolIdList ID
	 * @return auth
	 */
	public JSONObject getOnePoolAuthByPoolIds(List<Integer> poolIdList);

	public List<Integer> queryPoolIdByUserId();

}
