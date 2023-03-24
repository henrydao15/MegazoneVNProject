package com.megazone.examine.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.examine.entity.PO.ExamineRecordLog;
import com.megazone.examine.entity.VO.ExamineRecordLogVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-19
 */
public interface IExamineRecordLogService extends BaseService<ExamineRecordLog> {

	/**
	 * @param batchId ID
	 * @param sort
	 * @return log
	 */
	public ExamineRecordLog queryNextExamineRecordLog(String batchId, Integer sort, Integer logId);


	/**
	 * @param examineLogId
	 * @return log
	 */
	public ExamineRecordLog queryExamineLogById(Integer examineLogId);

	/**
	 * @param recordId
	 * @param ownerUserId
	 * @return log
	 */
	List<ExamineRecordLogVO> queryExamineRecordLog(Integer recordId, String ownerUserId);
}
