package com.megazone.work.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.work.entity.PO.WorkTaskLabel;
import com.megazone.work.entity.VO.WorkTaskByLabelVO;
import com.megazone.work.entity.VO.WorkTaskLabelOrderVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
public interface IWorkTaskLabelService extends BaseService<WorkTaskLabel> {
	public void saveLabel(WorkTaskLabel workTaskLabel);

	public void setLabel(WorkTaskLabel workTaskLabel);

	public void deleteLabel(Integer labelId);

	public List<WorkTaskLabelOrderVO> getLabelList();

	public void updateOrder(List<Integer> labelIdList);

	public WorkTaskLabel queryById(Integer labelId);

	public List<WorkTaskByLabelVO> getTaskList(Integer labelId);
}
