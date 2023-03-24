package com.megazone.crm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.entity.PO.CrmBusinessStatus;
import com.megazone.crm.mapper.CrmBusinessStatusMapper;
import com.megazone.crm.service.ICrmBusinessStatusService;
import org.springframework.stereotype.Service;

@Service
public class CrmBusinessStatusServiceImpl extends BaseServiceImpl<CrmBusinessStatusMapper, CrmBusinessStatus> implements ICrmBusinessStatusService {

	@Override
	public String getBusinessStatusName(int statusId) {
		return lambdaQuery().select(CrmBusinessStatus::getName).eq(CrmBusinessStatus::getStatusId, statusId).oneOpt()
				.map(CrmBusinessStatus::getName).orElse("");
	}
}
