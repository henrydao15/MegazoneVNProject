package com.megazone.oa.mapper;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.oa.entity.BO.ExamineExportBO;
import com.megazone.oa.entity.BO.ExaminePageBO;
import com.megazone.oa.entity.PO.OaExamine;
import com.megazone.oa.entity.PO.OaExamineField;
import com.megazone.oa.entity.VO.ExamineVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OaExamineMapper extends BaseMapper<OaExamine> {

	BasePage<ExamineVO> myInitiate(BasePage<Object> parse, @Param("data") ExaminePageBO examinePageBO, @Param("userId") Long userId, @Param("isAdmin") boolean isAdmin, @Param("biStatus") Integer biStatus);

	List<Long> queryExamineUserByExamineLog(@Param("record") ExamineVO record);

	BasePage<ExamineVO> myOaExamine(BasePage<Object> parse, @Param("data") ExaminePageBO examinePageBO, @Param("userId") Long userId, @Param("isAdmin") boolean isAdmin);

	ExamineVO queryExamineById(String id);

	JSONObject queryExamineRecordById(String recordId);

	List<JSONObject> myInitiateExcel(@Param("data") ExamineExportBO examineExportBO, @Param("userId") Long userId, @Param("isAdmin") boolean isAdmin);

	List<JSONObject> myOaExamineExcel(@Param("data") ExamineExportBO examineExportBO, @Param("userId") Long userId, @Param("isAdmin") boolean isAdmin);

	List<JSONObject> queryTravelExamineList(@Param("examineIdList") List<Integer> examineIdList);

	List<JSONObject> queryCustomExamineList(@Param("examineIdList") List<Integer> examineIdList, @Param("batchIds") List<String> batchIdList, @Param("fieldMap") List<OaExamineField> fields);

	List<JSONObject> queryExamineList(@Param("examineIdList") List<Integer> examineIdList);


	BasePage<ExamineVO> myInitiateOaExamine(BasePage<Object> parse, @Param("data") ExaminePageBO examinePageBO, @Param("userId") Long userId, @Param("isAdmin") boolean isAdmin);

	List<JSONObject> myInitiateOaExcel(@Param("data") ExamineExportBO examineExportBO, @Param("userId") Long userId, @Param("isAdmin") boolean isAdmin);


}
