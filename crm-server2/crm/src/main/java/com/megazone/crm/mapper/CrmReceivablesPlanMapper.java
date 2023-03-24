package com.megazone.crm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmReceivablesPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
public interface CrmReceivablesPlanMapper extends BaseMapper<CrmReceivablesPlan> {

	/**
	 * @param id id
	 * @return data
	 */
	public CrmModel queryById(@Param("id") Integer id);


	/**
	 * @param contractId ID
	 * @return data
	 */
	public List<CrmReceivablesPlan> queryReceivablesPlansByContractId(@Param("contractId") Integer contractId);

	/**
	 * @param contractId ID
	 * @return data
	 */
	public BasePage<CrmReceivablesPlan> queryReceivablesPlanListByContractId(BasePage<JSONObject> page, @Param("contractId") Integer contractId);

	/**
	 *
	 */
	public List<CrmReceivablesPlan> queryReceivablesPlans();

}
