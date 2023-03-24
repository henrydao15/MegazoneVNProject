package com.megazone.bi.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BiFunnelMapper {

	List<JSONObject> sellFunnel(Map<String, Object> map);

	List<JSONObject> addBusinessAnalyze(Map<String, Object> map);

	List<JSONObject> win(Map<String, Object> map);

	BasePage<JSONObject> sellFunnelList(BasePage<JSONObject> page, @Param("userIds") List<Long> ids, @Param("sqlDateFormat") String sqlDateFormat, @Param("time") String time);
}
