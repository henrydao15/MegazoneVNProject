package com.megazone.crm.mapper;

import cn.hutool.core.lang.Dict;
import com.megazone.core.common.JSONObject;
import com.megazone.core.utils.BiTimeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface CrmInstrumentMapper {

	public List<JSONObject> sellFunnel(Map<String, Object> map);

	public List<String> sellFunnelBusinessList(Map<String, Object> map);

	/**
	 * @param biTimeEntity time
	 * @param map          map
	 * @return data
	 */
	public Map<String, Object> queryBulletin(@Param("time") BiTimeUtil.BiTimeEntity biTimeEntity, @Param("map") Map<String, Object> map);

	/**
	 * @param day
	 * @param userIds ids
	 * @return data
	 */
	public Integer forgottenCustomerCount(@Param("day") Integer day, @Param("userIds") List<Long> userIds);

	/**
	 * @param userIds ids
	 * @return data
	 */
	public Integer unContactCustomerCount(@Param("userIds") List<Long> userIds);

	public Map<String, Object> salesTrend(@Param("time") BiTimeUtil.BiTimeEntity entity, @Param("data") JSONObject object);

	/**
	 * @param entity time
	 * @param ids    ids
	 * @return data
	 */
	public Map<String, Object> dataInfoCustomer(@Param("time") BiTimeUtil.BiTimeEntity entity, @Param("ids") List<Long> ids);

	/**
	 * @param entity time
	 * @param ids    ids
	 * @return data
	 */
	public Map<String, Object> dataInfoBusiness(@Param("time") BiTimeUtil.BiTimeEntity entity, @Param("ids") List<Long> ids);

	/**
	 * @param entity time
	 * @param ids    ids
	 * @return data
	 */
	public Map<String, Object> dataInfoContract(@Param("time") BiTimeUtil.BiTimeEntity entity, @Param("ids") List<Long> ids);

	/**
	 * @param entity time
	 * @param ids    ids
	 * @return data
	 */
	public Map<String, Object> dataInfoReceivables(@Param("time") BiTimeUtil.BiTimeEntity entity, @Param("ids") List<Long> ids);

	/**
	 * @param entity time
	 * @param ids    ids
	 * @return data
	 */
	public Map<String, Object> dataInfoReceivablesPlan(@Param("time") BiTimeUtil.BiTimeEntity entity, @Param("ids") List<Long> ids);

	/**
	 * @param entity time
	 * @param ids    ids
	 * @return data
	 */
	public Map<String, Object> dataInfoActivity(@Param("time") BiTimeUtil.BiTimeEntity entity, @Param("ids") List<Long> ids);

	/**
	 * @param entity time
	 * @return data
	 */
	public JSONObject queryPerformance(Map<String, Object> entity);

	public List<JSONObject> queryRecordCount(@Param("data") Dict kv);

	/**
	 * @param type 1  2
	 * @return
	 */
	public Integer dataInfoCustomerPoolNum(@Param("time") BiTimeUtil.BiTimeEntity entity, @Param("ids") List<Long> ids, @Param("type") Integer type);

	/**
	 * @param startTime ，
	 * @param endTime   ，
	 * @param ids
	 * @return data
	 */
	public Map<String, Object> dataInfoEndContractNum(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("ids") List<Long> ids);
}
