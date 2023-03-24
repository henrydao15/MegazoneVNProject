package com.megazone.examine.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.examine.entity.PO.ExamineRecordOptional;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-12-02
 */
public interface IExamineRecordOptionalService extends BaseService<ExamineRecordOptional> {

	/**
	 * @param flowId   ID
	 * @param recordId ID
	 * @param userList
	 */
	public void saveRecordOptionalInfo(Integer flowId, Integer recordId, List<Long> userList);
}
