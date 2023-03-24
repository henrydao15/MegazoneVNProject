package com.megazone.crm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmContract;
import com.megazone.crm.entity.PO.CrmField;
import com.megazone.crm.entity.PO.CrmReceivablesPlan;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
public interface CrmContractMapper extends BaseMapper<CrmContract> {

	/**
	 * @param id     id
	 * @param userId ID
	 * @return data
	 */
	public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

	/**
	 * @param map map
	 */
	public CrmInfoNumVO queryNum(Map<String, Object> map);

	List<CrmReceivablesPlan> queryReceivablesPlansByContractId(Integer contractId);

	public BigDecimal queryReceivablesMoney(@Param("contractId") Integer contractId);

	CrmReceivablesPlan queryReceivablesPlansByReceivablesId(Integer receivablesId);

	JSONObject querySubtotalByContractId(@Param("contractId") Integer contractId);

	BasePage<JSONObject> queryProductPageList(BasePage<Object> parse, @Param("contractId") Integer contractId);

	BasePage<JSONObject> queryReturnVisit(BasePage<Object> parse, @Param("contractId") Integer contractId, @Param("conditions") String conditions, @Param("nameList") List<CrmField> nameList);

	List<String> endContract(@Param("data") CrmEventBO crmEventBO);

	List<String> receiveContract(@Param("data") CrmEventBO crmEventBO);

	List<Integer> endContractList(@Param("userId") Long userId, @Param("time") Date time, @Param("expiringDay") Integer expiringDay);

	List<JSONObject> receiveContractList(@Param("userId") Long userId, @Param("time") Date time);
}
