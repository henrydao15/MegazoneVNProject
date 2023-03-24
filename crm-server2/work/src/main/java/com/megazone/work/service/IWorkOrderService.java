package com.megazone.work.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.work.entity.PO.WorkOrder;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
public interface IWorkOrderService extends BaseService<WorkOrder> {
	public void updateWorkOrder(List<Integer> workIdList);
}
