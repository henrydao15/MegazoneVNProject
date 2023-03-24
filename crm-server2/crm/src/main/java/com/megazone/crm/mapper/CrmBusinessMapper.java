package com.megazone.crm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmBusiness;
import com.megazone.crm.entity.PO.CrmContacts;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import org.apache.ibatis.annotations.Param;

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
public interface CrmBusinessMapper extends BaseMapper<CrmBusiness> {
	/**
	 * @param id     id
	 * @param userId ID
	 * @return data
	 */
	public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

	public BasePage<CrmContacts> queryContacts(BasePage<CrmContacts> crmContactsBaseMapper, @Param("businessId") Integer businessId);

	/**
	 * @param map map
	 */
	public CrmInfoNumVO queryNum(Map<String, Object> map);

	JSONObject querySubtotalByBusinessId(Integer businessId);

	BasePage<JSONObject> queryProduct(BasePage<Object> parse, @Param("businessId") Integer businessId);

	BasePage<JSONObject> queryContract(BasePage<Object> parse, @Param("businessId") Integer businessId, @Param("conditions") String conditions);

	List<String> eventDealBusiness(@Param("data") CrmEventBO crmEventBO);

	List<Integer> eventDealBusinessPageList(@Param("userId") Long userId, @Param("time") Date time);

	List<String> eventBusiness(@Param("data") CrmEventBO crmEventBO);

	List<Integer> eventBusinessPageList(@Param("userId") Long userId, @Param("time") Date time);
}
