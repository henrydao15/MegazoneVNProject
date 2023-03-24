package com.megazone.crm.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CallRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Ian
 */
public interface CallRecordMapper extends BaseMapper<CallRecord> {

	/**
	 *
	 */
	CallRecord queryRecord(@Param("number") String number, @Param("startTime") Date startTime, @Param("ownerUserId") Long ownerUserId);

	/**
	 *
	 */
	BasePage<JSONObject> pageCallRecordList(BasePage<JSONObject> page, @Param("sqlDateFormat") String sqlDateFormat,
											@Param("userIds") List<Long> userIds, @Param("talkTime") Long talkTime,
											@Param("talkTimeCondition") String talkTimeCondition,
											@Param("beginTime") Integer beginTime, @Param("finalTime") Integer finalTime);

	/**
	 *
	 */
	List<JSONObject> queryContactsByCustomerId(@Param("customerId") Integer customerId);

	/**
	 *
	 */
	List<JSONObject> searchFieldValueByLeadsId(@Param("leadsId") Integer leadsId);

	/**
	 *
	 */
	List<JSONObject> searchFieldValueByContactsId(@Param("contactsId") Integer contactsId);

	/**
	 *
	 */
	BasePage<JSONObject> analysis(BasePage<JSONObject> page, @Param("userIds") List<Long> userIds,
								  @Param("sqlDateFormat") String sqlDateFormat,
								  @Param("beginTime") Integer beginTime, @Param("finalTime") Integer finalTime);

}
