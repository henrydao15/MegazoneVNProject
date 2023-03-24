package com.megazone.bi.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.utils.BiTimeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface BiEmployeeMapper {

	List<JSONObject> contractNum(Map<String, Object> map);

	List<JSONObject> contractMoney(Map<String, Object> map);

	List<JSONObject> receivables(Map<String, Object> map);

	List<JSONObject> totalContractTable(Map<String, Object> map);

	JSONObject totalContract(BiTimeUtil.BiTimeEntity entity);

	JSONObject totalInvoice(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("userIds") List<Long> userIds);

	JSONObject invoiceStatsTable(Map<String, Object> map);
}
