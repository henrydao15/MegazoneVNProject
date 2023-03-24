package com.megazone.bi.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.utils.BiTimeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BiCustomerMapper {

	List<JSONObject> totalCustomerStats(Map<String, Object> map);

	List<JSONObject> totalCustomerTable(BiTimeUtil.BiTimeEntity entity);

	List<JSONObject> customerRecordStats(Map<String, Object> map);

	List<JSONObject> customerRecordInfo(BiTimeUtil.BiTimeEntity entity);

	List<JSONObject> customerRecordCategoryStats(BiTimeUtil.BiTimeEntity entity);

	JSONObject customerConversionStats(Map<String, Object> map);

	BasePage<JSONObject> customerConversionInfo(BasePage<JSONObject> page, @Param("sqlDateFormat") String sqlDateFormat, @Param("userIds") List<Long> userIds, @Param("time") String time);

	List<JSONObject> poolStats(Map<String, Object> map);

	List<JSONObject> poolTable(BiTimeUtil.BiTimeEntity entity);

	List<JSONObject> employeeCycle(Map<String, Object> map);

	List<JSONObject> employeeCycleInfo(BiTimeUtil.BiTimeEntity entity);

	List<JSONObject> districtCycle(BiTimeUtil.BiTimeEntity entity, @Param("districts") List<String> districts);

	List<JSONObject> productCycle(BiTimeUtil.BiTimeEntity entity, @Param("productList") List<SimpleCrmEntity> productList);

	JSONObject querySatisfactionOptionList();

	List<JSONObject> customerSatisfactionTable(Map<String, Object> map);

	List<JSONObject> productSatisfactionTable(Map<String, Object> map);

	String queryFirstCustomerCreteTime();
}
