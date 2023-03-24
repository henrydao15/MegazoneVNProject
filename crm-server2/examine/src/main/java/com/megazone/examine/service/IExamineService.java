package com.megazone.examine.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.ExamineField;
import com.megazone.core.servlet.BaseService;
import com.megazone.examine.entity.BO.ExaminePageBO;
import com.megazone.examine.entity.BO.ExaminePreviewBO;
import com.megazone.examine.entity.BO.ExamineSaveBO;
import com.megazone.examine.entity.PO.Examine;
import com.megazone.examine.entity.VO.*;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-13
 */
public interface IExamineService extends BaseService<Examine> {

	/**
	 * @param label
	 * @param categoryId ，OA
	 * @return data
	 */
	public List<ExamineField> queryField(Integer label, Integer categoryId);

	/**
	 * @param examinePageBo
	 * @return data
	 */
	public BasePage<ExamineVO> queryList(ExaminePageBO examinePageBo);

	/**
	 * @param examineSaveBO data
	 */
	public Examine addExamine(ExamineSaveBO examineSaveBO);

	/**
	 * @param label
	 * @return data
	 */
	public Examine queryExamineByLabel(Integer label);

	/**
	 * @param examineId ID
	 * @param status    1  2  3
	 */
	public Examine updateStatus(Long examineId, Integer status, boolean isRequest);


	/**
	 * @param label
	 */
	public List<ExamineFlowVO> previewExamineFlow(Integer label);

	/**
	 * @param examineId ID
	 */
	public List<ExamineFlowVO> queryExamineFlow(Long examineId);

	/**
	 * @param label
	 * @param recordId  ID
	 * @param examineId ID
	 */
	public List<ExamineFlowConditionDataVO> previewFiledName(Integer label, Integer recordId, Long examineId);

	/**
	 * @param examinePreviewBO
	 */
	public ExaminePreviewVO previewExamineFlow(ExaminePreviewBO examinePreviewBO);

	/**
	 * @param examinePageBo
	 */
	BasePage<com.megazone.core.feign.oa.entity.ExamineVO> queryOaExamineList(ExaminePageBO examinePageBo);

	/**
	 * @param examinePageBo
	 */
	BasePage<ExamineRecordInfoVO> queryCrmExamineList(ExaminePageBO examinePageBo);

	/**
	 * @param status
	 * @param categoryId ID
	 */
	List<Integer> queryOaExamineIdList(Integer status, Integer categoryId);

	/**
	 * @param label
	 * @param status
	 */
	List<Integer> queryCrmExamineIdList(Integer label, Integer status);


	/**
	 * @param userIds   ID
	 * @param examineId ID
	 */
	public List<Long> handleUserList(List<Long> userIds, Long examineId);
}
