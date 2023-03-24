package com.megazone.bi.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.oa.entity.ExamineVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BiWorkMapper {

	List<JSONObject> logStatistics(Map<String, Object> map);

	List<JSONObject> queryExamineCategory();

	List<JSONObject> examineStatistics(Map<String, Object> map);

	BasePage<ExamineVO> myInitiate(BasePage<JSONObject> page, @Param("sqlDateFormat") String sqlDateFormat,
								   @Param("userId") Long userId, @Param("categoryId") String categoryId,
								   @Param("beginTime") Integer beginTime, @Param("finalTime") Integer finalTime);

	JSONObject queryExamineCount(@Param("sqlDateFormat") String sqlDateFormat,
								 @Param("userId") Long userId, @Param("categoryId") String categoryId,
								 @Param("beginTime") Integer beginTime, @Param("finalTime") Integer finalTime);
}
