package com.megazone.bi.mapper;

import com.megazone.core.common.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BiRankMapper {

	List<JSONObject> addressAnalyse(Map<String, Object> map);

	List<JSONObject> portrait(Map<String, Object> map);

	List<JSONObject> portraitLevel(Map<String, Object> map);

	List<JSONObject> portraitSource(Map<String, Object> map);

	List<JSONObject> contractProductRanKing(Map<String, Object> map);

	List<JSONObject> contractRanKing(Map<String, Object> map);

	List<JSONObject> receivablesRanKing(Map<String, Object> map);

	List<JSONObject> contractCountRanKing(Map<String, Object> map);

	List<JSONObject> productCountRanKing(Map<String, Object> map);

	List<JSONObject> customerCountRanKing(Map<String, Object> map);

	List<JSONObject> contactsCountRanKing(Map<String, Object> map);

	List<JSONObject> customerGenjinCountRanKing(Map<String, Object> map);

	List<JSONObject> recordCountRanKing(Map<String, Object> map);

	List<JSONObject> travelCountRanKing(Map<String, Object> map);

	List<JSONObject> contractRanKing1(@Param("data") Map<String, Object> toMap, @Param("monthMap") List<Map<String, String>> monthMap);

	List<JSONObject> receivablesRanKing1(@Param("data") Map<String, Object> toMap, @Param("monthMap") List<Map<String, String>> monthMap);
}
