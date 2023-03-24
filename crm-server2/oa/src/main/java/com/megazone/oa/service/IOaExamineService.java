package com.megazone.oa.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.examine.entity.ExamineConditionDataBO;
import com.megazone.core.feign.examine.entity.ExamineInfoVo;
import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.BO.AuditExamineBO;
import com.megazone.oa.entity.BO.ExamineExportBO;
import com.megazone.oa.entity.BO.ExaminePageBO;
import com.megazone.oa.entity.BO.GetExamineFieldBO;
import com.megazone.oa.entity.PO.OaExamine;
import com.megazone.oa.entity.PO.OaExamineCategory;
import com.megazone.oa.entity.PO.OaExamineField;
import com.megazone.oa.entity.VO.ExamineVO;

import java.util.List;
import java.util.Map;

public interface IOaExamineService extends BaseService<OaExamine> {

	BasePage<ExamineVO> myInitiate(ExaminePageBO examinePageBO);

	BasePage<ExamineVO> myOaExamine(ExaminePageBO examinePageBO);

	List<OaExamineField> getField(GetExamineFieldBO getExamineFieldBO);

	List<List<OaExamineField>> getFormPositionField(GetExamineFieldBO getExamineFieldBO);

	void setOaExamine(JSONObject jsonObject);

	void oaExamine(AuditExamineBO auditExamineBO);

	ExamineVO queryOaExamineInfo(String examineId);

	JSONObject queryExamineRecordList(String recordId);

	List<JSONObject> queryExamineLogList(Integer recordId);

	void deleteOaExamine(Integer examineId);

	OaExamineCategory queryExaminStep(String categoryId);

	List<Map<String, Object>> export(ExamineExportBO examineExportBO, ExamineInfoVo examineInfoVo, List<OaExamineField> fieldList);

	List<ExamineVO> transfer(List<ExamineVO> recordList);

	Map<String, Object> getDataMapForNewExamine(ExamineConditionDataBO examineConditionDataBO);

	Boolean updateCheckStatusByNewExamine(ExamineConditionDataBO examineConditionDataBO);

	ExamineVO getOaExamineById(Integer oaExamineId);
}
