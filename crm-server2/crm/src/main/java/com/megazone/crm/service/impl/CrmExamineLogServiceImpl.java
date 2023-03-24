package com.megazone.crm.service.impl;

import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.entity.PO.CrmExamineLog;
import com.megazone.crm.mapper.CrmExamineLogMapper;
import com.megazone.crm.service.ICrmExamineLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmExamineLogServiceImpl extends BaseServiceImpl<CrmExamineLogMapper, CrmExamineLog> implements ICrmExamineLogService {

	@Override
	public List<JSONObject> queryByRecordId(Integer recordId) {
		return baseMapper.queryByRecordIdAndStatus(recordId);
	}
}
