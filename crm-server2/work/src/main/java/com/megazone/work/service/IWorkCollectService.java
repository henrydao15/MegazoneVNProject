package com.megazone.work.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.work.entity.PO.WorkCollect;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
public interface IWorkCollectService extends BaseService<WorkCollect> {

	public void collect(Integer workId);
}
