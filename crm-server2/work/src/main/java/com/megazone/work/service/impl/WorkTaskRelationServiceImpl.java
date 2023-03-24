package com.megazone.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.megazone.core.feign.crm.service.CrmService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.work.entity.PO.WorkTaskRelation;
import com.megazone.work.mapper.WorkTaskRelationMapper;
import com.megazone.work.service.IWorkTaskRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-18
 */
@Service
public class WorkTaskRelationServiceImpl extends BaseServiceImpl<WorkTaskRelationMapper, WorkTaskRelation> implements IWorkTaskRelationService {
	@Autowired
	private CrmService crmService;

	@Override
	public void saveWorkTaskRelation(WorkTaskRelation workTaskRelation) {
		if (workTaskRelation.getBusinessIds() != null || workTaskRelation.getContactsIds() != null || workTaskRelation.getContractIds() != null || workTaskRelation.getCustomerIds() != null) {
			crmService.addActivity(2, 11, workTaskRelation.getTaskId());
		}
		remove(new QueryWrapper<WorkTaskRelation>().eq("task_id", workTaskRelation.getTaskId()));
		save(workTaskRelation);
	}
}
