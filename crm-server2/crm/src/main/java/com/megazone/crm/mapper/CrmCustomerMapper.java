package com.megazone.crm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.BO.CrmDataCheckBO;
import com.megazone.crm.entity.PO.CrmContacts;
import com.megazone.crm.entity.PO.CrmCustomer;
import com.megazone.crm.entity.PO.CrmField;
import com.megazone.crm.entity.VO.CrmDataCheckVO;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
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
public interface CrmCustomerMapper extends BaseMapper<CrmCustomer> {
	/**
	 * @param id     id
	 * @param userId ID
	 * @return data
	 */
	public CrmModel queryById(@Param("id") Integer id, @Param("userId") Long userId);

	/**
	 * @param id     id
	 * @param userId ID
	 * @return data
	 */
	public Integer queryIsRoUser(@Param("id") Integer id, @Param("userId") Long userId);

	/**
	 * @param id id
	 * @return data
	 */
	@Select("select city_name from wk_crm_area where parent_id = #{id}")
	public List<String> queryCityList(@Param("id") Integer id);

	public BasePage<CrmContacts> queryContacts(BasePage<CrmContacts> crmContactsBaseMapper, @Param("customerId") Integer customerId, @Param("search") String search
			, @Param("condition") String condition);

	public BasePage<Map<String, Object>> queryBusiness(BasePage<Map<String, Object>> crmContactsBaseMapper, @Param("customerId") Integer customerId,
													   @Param("search") String search, @Param("condition") String condition);

	public BasePage<Map<String, Object>> queryContract(BasePage<Map<String, Object>> crmContactsBaseMapper, @Param("customerId") Integer customerId,
													   @Param("search") String search, @Param("checkStatus") Integer checkStatus, @Param("condition") String condition);

	/**
	 * @param map map
	 */
	public CrmInfoNumVO queryNum(Map<String, Object> map);

	Integer ownerNum(@Param("ids") List<Integer> ids, @Param("ownerUserId") Long ownerUserId);

	List<CrmDataCheckVO> dataCheck(@Param("data") CrmDataCheckBO dataCheckBO);

	String queryPoolIdsByCustomer(@Param("customerId") Integer customerId);

	BasePage<JSONObject> queryReceivablesPlan(BasePage<JSONObject> page, @Param("customerId") Integer customerId, @Param("conditions") String conditions);


	BasePage<JSONObject> queryReceivables(BasePage<JSONObject> page, @Param("customerId") Integer customerId, @Param("conditions") String conditions);

	BasePage<JSONObject> queryReturnVisit(BasePage<JSONObject> page, @Param("customerId") Integer customerId, @Param("conditions") String conditions, @Param("nameList") List<CrmField> nameList);

	BasePage<JSONObject> queryInvoice(BasePage<JSONObject> page, @Param("customerId") Integer customerId, @Param("conditions") String conditions);

	BasePage<JSONObject> queryInvoiceInfo(BasePage<JSONObject> page, @Param("customerId") Integer customerId);

	BasePage<JSONObject> queryCallRecord(BasePage<JSONObject> page, @Param("customerId") Integer customerId);

	public Integer queryOutDays(@Param("customerId") Integer customerId, @Param("userId") Long userId);

	List<JSONObject> nearbyCustomer(@Param("lng") String lng, @Param("lat") String lat, @Param("type") Integer type,
									@Param("radius") Integer radius, @Param("ownerUserId") Long ownerUserId, @Param("userIds") List<Long> authUserIdList,
									@Param("poolIdList") List<Integer> poolIdList);

	List<String> eventCustomer(@Param("data") CrmEventBO crmEventBO);

	List<Integer> eventCustomerList(@Param("userId") Long userId, @Param("time") Date time);

	List<Integer> forgottenCustomer(@Param("day") Integer day, @Param("userIds") List<Long> userIds, @Param("search") String search);

	List<Integer> unContactCustomer(@Param("search") String search, @Param("userIds") List<Long> userIds);
}
