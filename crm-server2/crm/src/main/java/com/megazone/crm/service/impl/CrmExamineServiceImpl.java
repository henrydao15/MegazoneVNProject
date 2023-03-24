package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.hrm.entity.HrmSalaryMonthRecord;
import com.megazone.core.feign.hrm.service.SalaryRecordService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.constant.CrmCodeEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmMyExamineBO;
import com.megazone.crm.entity.BO.CrmQueryExamineStepBO;
import com.megazone.crm.entity.BO.CrmSaveExamineBO;
import com.megazone.crm.entity.PO.*;
import com.megazone.crm.entity.VO.CrmQueryAllExamineVO;
import com.megazone.crm.entity.VO.CrmQueryExamineStepVO;
import com.megazone.crm.mapper.CrmExamineLogMapper;
import com.megazone.crm.mapper.CrmExamineMapper;
import com.megazone.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CrmExamineServiceImpl extends BaseServiceImpl<CrmExamineMapper, CrmExamine> implements ICrmExamineService {

	@Autowired
	private ICrmExamineStepService examineStepService;

	@Autowired
	private CrmExamineMapper examineMapper;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ICrmExamineLogService examineLogService;

	@Autowired
	private ICrmContractService crmContractService;

	@Autowired
	private ICrmReceivablesService crmReceivablesService;

	@Autowired
	private SalaryRecordService salaryRecordService;


	@Override
	public Integer queryCount(CrmEnum crmEnum) {
		int type;
		if (crmEnum.equals(CrmEnum.CONTRACT)) {
			type = 1;
		} else if (crmEnum.equals(CrmEnum.RECEIVABLES)) {
			type = 2;
		} else if (crmEnum.equals(CrmEnum.INVOICE)) {
			type = 3;
		} else {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		return lambdaQuery().eq(CrmExamine::getCategoryType, type).eq(CrmExamine::getStatus, 1).count();
	}

	@Override
	public void saveExamine(CrmSaveExamineBO crmSaveExamineBO) {
		CrmExamine crmExamine = BeanUtil.copyProperties(crmSaveExamineBO, CrmExamine.class);
		crmExamine.setDeptIds(TagUtil.fromSet(crmSaveExamineBO.getDeptIds()));
		crmExamine.setUserIds(TagUtil.fromLongSet(crmSaveExamineBO.getUserIds()));
		crmExamine.setUpdateUserId(UserUtil.getUserId());
		crmExamine.setUpdateTime(new Date());
		if (crmExamine.getExamineId() == null) {

//            select * from wk_admin_examine where  category_type = ? and status = 1 order by update_time desc limit 1
			CrmExamine examine = lambdaQuery().eq(CrmExamine::getCategoryType, crmExamine.getCategoryType()).eq(CrmExamine::getStatus, 1)
					.orderByDesc(CrmExamine::getUpdateTime).last("limit 1").one();
			if (examine != null) {

				examine.setStatus(0);
				examine.setUpdateUserId(UserUtil.getUserId());
				updateById(examine);
			}
			crmExamine.setUpdateUserId(UserUtil.getUserId());
			crmExamine.setStatus(1);
			save(crmExamine);
//            adminLogUtil.addObjectLog(AdminModelEnum.CRM_EXAMINE, "", adminExamine.getName());
		} else {

			CrmExamine examine = getById(crmExamine.getExamineId());
			examine.setStatus(2);
			updateById(examine);
			crmExamine.setExamineId(null);
			crmExamine.setStatus(1);
			save(crmExamine);
//            String name = Db.queryStr("select name from wk_admin_examine where examine_id = ?", examine.getExamineId());
//            adminLogUtil.updateObjectLog(examine, adminExamine, AdminModelEnum.CRM_EXAMINE, "crmExamine", name);
		}
		if (crmExamine.getExamineType() == 1) {

			int i = 1;
			List<CrmSaveExamineBO.Step> step = crmSaveExamineBO.getStep();
			if (step.size() == 0) {
				throw new CrmException(CrmCodeEnum.NO_APPROVAL_STEP_CANNOT_BE_SAVED);
			}
			for (CrmSaveExamineBO.Step step1 : step) {
				CrmExamineStep examineStep = BeanUtil.copyProperties(step1, CrmExamineStep.class);
				examineStep.setExamineId(crmExamine.getExamineId());
				examineStep.setCreateTime(DateUtil.date());
				examineStep.setStepNum(i++);
				examineStep.setCheckUserId(TagUtil.fromLongSet(step1.getCheckUserId()));
				examineStepService.save(examineStep);
			}
		}
	}

	@Override
	public BasePage<CrmQueryAllExamineVO> queryAllExamine(PageEntity pageEntity) {
		BasePage<CrmQueryAllExamineVO> page = examineMapper.queryExaminePage(pageEntity.parse());
		page.getList().forEach(adminExamine -> {
			List<CrmExamineStep> stepList = examineStepService.lambdaQuery().eq(CrmExamineStep::getExamineId, adminExamine.getExamineId()).list();
			if (CollUtil.isNotEmpty(stepList)) {
				stepList.forEach(step -> {
					Set<Long> userIds = TagUtil.toLongSet(step.getCheckUserId());
					List<SimpleUser> listResult = UserCacheUtil.getSimpleUsers(userIds);
					step.setUserList(listResult);
				});
				adminExamine.setStepList(stepList);
			} else {
				adminExamine.setStepList(new ArrayList<>());
			}

			adminExamine.setUpdateUserName(UserCacheUtil.getUserName(adminExamine.getUpdateUserId()));
			if (StrUtil.isNotEmpty((String) adminExamine.getUserIds())) {
				adminExamine.setUserIds(UserCacheUtil.getSimpleUsers(TagUtil.toLongSet((String) adminExamine.getUserIds())));
			} else {
				adminExamine.setUserIds(new ArrayList<>());
			}
			if (StrUtil.isNotEmpty((String) adminExamine.getDeptIds())) {
				adminExamine.setDeptIds(adminService.queryDeptByIds(TagUtil.toSet((String) adminExamine.getDeptIds())).getData());
			} else {
				adminExamine.setDeptIds(new ArrayList<>());
			}

		});
		return page;
	}

	@Override
	public CrmQueryAllExamineVO queryExamineById(String examineId) {
		CrmExamine examine = getById(examineId);
		CrmQueryAllExamineVO examineVO = BeanUtil.copyProperties(examine, CrmQueryAllExamineVO.class);
		List<CrmExamineStep> stepList = examineStepService.lambdaQuery().eq(CrmExamineStep::getExamineId, examineId).list();
		if (CollUtil.isNotEmpty(stepList)) {
			stepList.forEach(step -> {
				Set<Long> userIds = TagUtil.toLongSet(step.getCheckUserId());
				List<SimpleUser> listResult = UserCacheUtil.getSimpleUsers(userIds);
				step.setUserList(listResult);
			});
			examineVO.setStepList(stepList);
		}
		return examineVO;
	}

	@Override
	public void updateStatus(CrmExamine crmExamine) {
		crmExamine.setUpdateUserId(UserUtil.getUserId());
		crmExamine.setUpdateTime(DateUtil.date());
		if (crmExamine.getStatus() == null) {
			crmExamine.setStatus(2);
		}
		updateById(crmExamine);
	}

	@Override
	public CrmQueryExamineStepVO queryExamineStep(CrmQueryExamineStepBO queryExamineStepBO) {
		Integer categoryType = queryExamineStepBO.getCategoryType();
		Integer id = queryExamineStepBO.getId();
		CrmExamine examine = examineMapper.selectCrmExamineByUser(categoryType, UserUtil.isAdmin(), UserUtil.getUserId(), UserUtil.getUser().getDeptId());
		if (examine != null) {
			CrmQueryExamineStepVO examineStepVO = BeanUtil.copyProperties(examine, CrmQueryExamineStepVO.class);
			if (examine.getExamineType() == 1) {
				List<CrmExamineStep> stepList = examineStepService.lambdaQuery().eq(CrmExamineStep::getExamineId, examine.getExamineId()).list();
				stepList.forEach(step -> {

					List<SimpleUser> userList = new ArrayList<>();
					if (step.getCheckUserId() != null) {
						List<SimpleUser> listResult = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(step.getCheckUserId()));
						userList = listResult;
					}
					step.setUserList(userList);
				});
				examineStepVO.setStepList(stepList);
			} else {
				if (queryExamineStepBO.getId() != null) {
					Integer recordId = null;
					if (categoryType == 1) {
						CrmContract contract = crmContractService.getById(id);
						if (contract.getExamineRecordId() != null) {
							recordId = contract.getExamineRecordId();
						}
					} else if (categoryType == 2) {
						CrmReceivables receivables = crmReceivablesService.getById(id);
						if (receivables.getExamineRecordId() != null) {
							recordId = receivables.getExamineRecordId();
						}
					} else if (categoryType == 3) {
						CrmInvoice crmInvoice = ApplicationContextHolder.getBean(ICrmInvoiceService.class).getById(id);
						if (crmInvoice.getExamineRecordId() != null) {
							recordId = crmInvoice.getExamineRecordId();
						}
					} else if (categoryType == 4) {
						Result<HrmSalaryMonthRecord> hrmSalaryMonthRecordResult = salaryRecordService.querySalaryRecordById(id);
						if (hrmSalaryMonthRecordResult.hasSuccess() && hrmSalaryMonthRecordResult.getData().getExamineRecordId() != null) {
							recordId = hrmSalaryMonthRecordResult.getData().getExamineRecordId();
						}
					}
					if (recordId != null) {
						CrmExamineLog examineLog = examineLogService.lambdaQuery().eq(CrmExamineLog::getIsRecheck, 0).eq(CrmExamineLog::getRecordId, recordId).last("limit 1").one();
						if (examineLog != null) {
							String examineUserName = UserCacheUtil.getUserName(examineLog.getExamineUser());
							examineStepVO.setExamineUser(examineLog.getExamineUser());
							examineStepVO.setExamineUserName(examineUserName);
						}
					}
				}
			}
			return examineStepVO;
		}
		return null;
	}

	@Override
	public Boolean queryExamineStepByType(Integer categoryType) {
		return lambdaQuery().eq(CrmExamine::getCategoryType, categoryType)
				.eq(CrmExamine::getStatus, 1).orderByDesc(CrmExamine::getUpdateTime).last("limit 1").oneOpt().isPresent();
	}

	@Override
	public BasePage<JSONObject> myExamine(CrmMyExamineBO crmMyExamineBO) {
		BasePage<JSONObject> page = getBaseMapper().myExamine(crmMyExamineBO.parse(), crmMyExamineBO, UserUtil.isAdmin(), UserUtil.getUserId());
		transfer(page.getList());
		return page;
	}

	public void transfer(List<JSONObject> recordList) {
		Long userId = UserUtil.getUserId();
		recordList.forEach(record -> {
			List<CrmExamineLog> list = examineLogService.lambdaQuery().select(CrmExamineLog::getExamineUser)
					.eq(CrmExamineLog::getRecordId, record.getInteger("recordId"))
					.eq(CrmExamineLog::getExamineStatus, record.getInteger("examineStatus"))
					.eq(record.containsKey("examineStepId") && record.getInteger("examineStepId") != null, CrmExamineLog::getExamineStepId, record.getInteger("examineStepId"))
					.list();
			if (CollUtil.isNotEmpty(list)) {
				List<Long> examineUserIds = list.stream().filter(log -> log != null && log.getExamineUser() != null).map(CrmExamineLog::getExamineUser).collect(Collectors.toList());
				String examineName = UserCacheUtil.getSimpleUsers(examineUserIds).stream().map(SimpleUser::getRealname).collect(Collectors.joining(","));
				record.put("examineName", examineName);
			} else {
				record.put("examineName", "");
			}
			record.put("createUser", UserCacheUtil.getSimpleUser(record.getLong("createUserId")));
			Integer examineStatus = record.getInteger("examineStatus");
			Map<String, Integer> permission = new HashMap<>();
			Long createUserId = record.getLong("createUserId");

			if ((createUserId.equals(userId) || userId.equals(UserUtil.getSuperUser())) && (examineStatus == 0 || examineStatus == 3)) {
				permission.put("isRecheck", 1);
			} else {
				permission.put("isRecheck", 0);
			}
			JSONObject examineLog = ApplicationContextHolder.getBean(CrmExamineLogMapper.class).queryExamineLog(userId, record.getInteger("recordId"), record.getLong("examineStepId"));
			if (examineLog != null) {
				permission.put("isCheck", 1);
			} else {
				permission.put("isCheck", 0);
			}
			record.put("permission", permission);
		});
	}
}
