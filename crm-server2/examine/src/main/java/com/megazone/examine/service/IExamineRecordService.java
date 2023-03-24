package com.megazone.examine.service;

import com.megazone.core.feign.examine.entity.ExamineRecordReturnVO;
import com.megazone.core.feign.examine.entity.ExamineRecordSaveBO;
import com.megazone.core.servlet.BaseService;
import com.megazone.examine.entity.BO.ExamineBO;
import com.megazone.examine.entity.PO.ExamineRecord;
import com.megazone.examine.entity.VO.ExamineRecordVO;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-19
 */
public interface IExamineRecordService extends BaseService<ExamineRecord> {

	/**
	 * @param examineRecordSaveBO data
	 */
	public ExamineRecordReturnVO addExamineRecord(ExamineRecordSaveBO examineRecordSaveBO);

	/**
	 * @param examineBO data
	 */
	public void auditExamine(ExamineBO examineBO);


	/**
	 * @param recordId
	 * @param ownerUserId
	 */
	public ExamineRecordVO queryExamineRecordList(Integer recordId, Long ownerUserId);

	/**
	 * @param recordId
	 */
	public ExamineRecordReturnVO queryExamineRecordInfo(Integer recordId);

	/**
	 * @param recordId
	 */
	Boolean deleteExamineRecord(Integer recordId);

	/**
	 * @param recordId
	 * @param examineStatus
	 */
	Boolean updateExamineRecord(Integer recordId, Integer examineStatus);

	/**
	 * @param label
	 */
	Boolean deleteExamineRecordAndLog(Integer label);
}
