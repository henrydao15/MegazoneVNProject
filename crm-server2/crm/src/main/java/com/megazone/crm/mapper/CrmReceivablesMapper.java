package com.megazone.crm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmReceivables;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
public interface CrmReceivablesMapper extends BaseMapper<CrmReceivables> {
	/**
	 * @param id     id
	 * @param userId ID
	 * @return data
	 */
	public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

	BasePage<JSONObject> queryListByContractId(BasePage<JSONObject> page, @Param("contractId") Integer contractId, @Param("conditions") String conditions);
}
