package com.megazone.work.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.work.entity.PO.WorkTaskComment;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-18
 */
public interface IWorkTaskCommentService extends BaseService<WorkTaskComment> {

	/**
	 * @param typeId typeId
	 * @param type   type
	 * @return data
	 */
	public List<WorkTaskComment> queryCommentList(Integer typeId, Integer type);

	/**
	 * @param taskComment taskComment
	 */
	public void setComment(WorkTaskComment taskComment);
}
