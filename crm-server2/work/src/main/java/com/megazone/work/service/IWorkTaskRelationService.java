package com.megazone.work.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.work.entity.PO.WorkTaskRelation;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-18
 */
public interface IWorkTaskRelationService extends BaseService<WorkTaskRelation> {
	public void saveWorkTaskRelation(WorkTaskRelation workTaskRelation);
}
