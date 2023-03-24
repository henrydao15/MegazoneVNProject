package com.megazone.crm.service.impl;

import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.entity.PO.CrmExamineStep;
import com.megazone.crm.mapper.CrmExamineStepMapper;
import com.megazone.crm.service.ICrmExamineLogService;
import com.megazone.crm.service.ICrmExamineService;
import com.megazone.crm.service.ICrmExamineStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrmExamineStepServiceImpl extends BaseServiceImpl<CrmExamineStepMapper, CrmExamineStep> implements ICrmExamineStepService {

	@Autowired
	private ICrmExamineService examineService;

	@Autowired
	private ICrmExamineLogService examineLogService;

	@Autowired
	private AdminService adminService;

}
