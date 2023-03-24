package com.megazone.hrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.megazone.core.common.DataAuthEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminMessage;
import com.megazone.core.feign.admin.entity.AdminMessageEnum;
import com.megazone.core.feign.admin.service.AdminMessageService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.TransferUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.hrm.common.EmployeeHolder;
import com.megazone.hrm.common.HrmCodeEnum;
import com.megazone.hrm.constant.IsEnum;
import com.megazone.hrm.constant.MenuIdConstant;
import com.megazone.hrm.entity.BO.QueryInsurancePageListBO;
import com.megazone.hrm.entity.BO.QueryInsuranceRecordListBO;
import com.megazone.hrm.entity.PO.*;
import com.megazone.hrm.entity.VO.QueryInsurancePageListVO;
import com.megazone.hrm.entity.VO.QueryInsuranceRecordListVO;
import com.megazone.hrm.mapper.HrmInsuranceMonthRecordMapper;
import com.megazone.hrm.mapper.HrmInsuranceSchemeMapper;
import com.megazone.hrm.service.*;
import com.megazone.hrm.service.actionrecord.impl.insuranceActionRecordServiceImpl;
import com.megazone.hrm.utils.EmployeeCacheUtil;
import com.megazone.hrm.utils.EmployeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
@Service
public class HrmInsuranceMonthRecordServiceImpl extends BaseServiceImpl<HrmInsuranceMonthRecordMapper, HrmInsuranceMonthRecord> implements IHrmInsuranceMonthRecordService {

	@Autowired
	private HrmInsuranceMonthRecordMapper insuranceMonthRecordMapper;

	@Autowired
	private IHrmInsuranceMonthEmpRecordService monthEmpRecordService;

	@Autowired
	private IHrmInsuranceMonthEmpProjectRecordService monthEmpProjectRecordService;

	@Autowired
	private HrmInsuranceSchemeMapper insuranceSchemeMapper;

	@Autowired
	private IHrmInsuranceProjectService insuranceProjectService;

	@Autowired
	private IHrmSalaryConfigService salaryConfigService;

	@Resource
	private insuranceActionRecordServiceImpl insuranceActionRecordService;

	@Autowired
	private EmployeeUtil employeeUtil;

	@Autowired
	private AdminService adminService;


	@Override
	public Integer computeInsuranceData() {
		HrmSalaryConfig salaryConfig = salaryConfigService.getOne(Wrappers.emptyWrapper());
		if (salaryConfig == null) {
			throw new CrmException(HrmCodeEnum.NO_INITIAL_CONFIGURATION);
		}
		String socialSecurityMonth = salaryConfig.getSocialSecurityStartMonth();
		DateTime dateTime = DateUtil.parse(socialSecurityMonth, "yyyy-MM");
		int month = dateTime.month() + 1;
		int year = dateTime.year();

		Optional<HrmInsuranceMonthRecord> lastMonthRecord = lambdaQuery().orderByDesc(HrmInsuranceMonthRecord::getCreateTime).last("limit 1").oneOpt();
		if (lastMonthRecord.isPresent()) {
			HrmInsuranceMonthRecord insuranceMonthRecord = lastMonthRecord.get();
			DateTime date = DateUtil.offsetMonth(DateUtil.parse(insuranceMonthRecord.getYear() + "-" + insuranceMonthRecord.getMonth(), "yy-MM"), 1);
			month = date.month() + 1;
			year = date.year();
			List<Integer> empRecordIds = insuranceMonthRecordMapper.queryDeleteEmpRecordIds(insuranceMonthRecord.getIRecordId());
			if (CollUtil.isNotEmpty(empRecordIds)) {
				monthEmpProjectRecordService.lambdaUpdate().in(HrmInsuranceMonthEmpProjectRecord::getIEmpRecordId, empRecordIds).remove();
				monthEmpRecordService.lambdaUpdate().in(HrmInsuranceMonthEmpRecord::getIEmpRecordId, empRecordIds).remove();
			}
			insuranceMonthRecord.setStatus(IsEnum.YES.getValue());
			updateById(insuranceMonthRecord);
		}
		List<Map<String, Integer>> employeeIds = insuranceMonthRecordMapper.queryInsuranceEmployee();
		HrmInsuranceMonthRecord hrmInsuranceMonthRecord = new HrmInsuranceMonthRecord();
		hrmInsuranceMonthRecord.setTitle(month + "monthly social security report");
		hrmInsuranceMonthRecord.setYear(year);
		hrmInsuranceMonthRecord.setMonth(month);
		hrmInsuranceMonthRecord.setNum(employeeIds.size());
		save(hrmInsuranceMonthRecord);
		insuranceActionRecordService.computeInsuranceDataLog(hrmInsuranceMonthRecord);
		int finalYear = year;
		int finalMonth = month;
		employeeIds.forEach(employeeMap -> {
			Integer employeeId = employeeMap.get("employeeId");
			Integer schemeId = employeeMap.get("schemeId");
			Map<String, Object> stringObjectMap = insuranceSchemeMapper.queryInsuranceSchemeCountById(schemeId);
			HrmInsuranceMonthEmpRecord insuranceMonthEmpRecord = new HrmInsuranceMonthEmpRecord();
			BeanUtil.fillBeanWithMap(stringObjectMap, insuranceMonthEmpRecord, true);
			insuranceMonthEmpRecord.setIRecordId(hrmInsuranceMonthRecord.getIRecordId());
			insuranceMonthEmpRecord.setEmployeeId(employeeId);
			insuranceMonthEmpRecord.setSchemeId(schemeId);
			insuranceMonthEmpRecord.setYear(finalYear);
			insuranceMonthEmpRecord.setMonth(finalMonth);
			monthEmpRecordService.save(insuranceMonthEmpRecord);
			// send notification
			AdminMessage adminMessage = new AdminMessage();
			adminMessage.setCreateUser(UserUtil.getUserId());
			adminMessage.setCreateTime(DateUtil.formatDateTime(new Date()));
			adminMessage.setRecipientUser(EmployeeCacheUtil.getUserId(employeeId));
			adminMessage.setLabel(8);
			adminMessage.setType(AdminMessageEnum.HRM_EMPLOYEE_INSURANCE.getType());
			adminMessage.setTitle(finalYear + "year" + finalMonth + "monthly social security");
			ApplicationContextHolder.getBean(AdminMessageService.class).save(adminMessage);
			List<HrmInsuranceProject> insuranceProjectList = insuranceProjectService.lambdaQuery().eq(HrmInsuranceProject::getSchemeId, schemeId).list();
			List<HrmInsuranceMonthEmpProjectRecord> monthEmpProjectRecordList = TransferUtil.transferList(insuranceProjectList, HrmInsuranceMonthEmpProjectRecord.class);
			monthEmpProjectRecordList.forEach(monthEmpProjectRecord -> {
				monthEmpProjectRecord.setIEmpRecordId(insuranceMonthEmpRecord.getIEmpRecordId());
			});
			monthEmpProjectRecordService.saveBatch(monthEmpProjectRecordList);
		});
		return year;
	}

	@Override
	public BasePage<QueryInsuranceRecordListVO> queryInsuranceRecordList(QueryInsuranceRecordListBO recordListBO) {
		Collection<Integer> employeeIds = employeeUtil.queryDataAuthEmpIdByMenuId(MenuIdConstant.INSURANCE_MENU_ID);
		Integer dataAuthType = adminService.queryDataType(UserUtil.getUserId(), MenuIdConstant.INSURANCE_MENU_ID).getData();
		boolean isAll = EmployeeHolder.isHrmAdmin() || dataAuthType.equals(DataAuthEnum.ALL.getValue());
		return insuranceMonthRecordMapper.queryInsuranceRecordList(recordListBO.parse(), recordListBO, employeeIds, isAll);
	}

	@Override
	public BasePage<QueryInsurancePageListVO> queryInsurancePageList(QueryInsurancePageListBO queryInsurancePageListBO) {
		Collection<Integer> employeeIds = employeeUtil.queryDataAuthEmpIdByMenuId(MenuIdConstant.INSURANCE_MENU_ID);
		return insuranceMonthRecordMapper.queryInsurancePageList(queryInsurancePageListBO.parse(), queryInsurancePageListBO, employeeIds);
	}

	@Override
	public QueryInsuranceRecordListVO queryInsuranceRecord(String iRecordId) {
		Collection<Integer> employeeIds = employeeUtil.queryDataAuthEmpIdByMenuId(MenuIdConstant.INSURANCE_MENU_ID);
		return insuranceMonthRecordMapper.queryInsuranceRecord(iRecordId, employeeIds);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteInsurance(Integer iRecordId) {
		Integer count = lambdaQuery().count();
		if (count == 1) {
			throw new CrmException(HrmCodeEnum.INSURANCE_CANNOT_BE_DELETED);
		}
		List<Integer> iEmpRecordIds = monthEmpRecordService.lambdaQuery().select(HrmInsuranceMonthEmpRecord::getIEmpRecordId).eq(HrmInsuranceMonthEmpRecord::getIRecordId, iRecordId).list()
				.stream().map(HrmInsuranceMonthEmpRecord::getIEmpRecordId).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(iEmpRecordIds)) {
			monthEmpProjectRecordService.lambdaUpdate().in(HrmInsuranceMonthEmpProjectRecord::getIEmpRecordId, iEmpRecordIds).remove();
			monthEmpRecordService.lambdaUpdate().in(HrmInsuranceMonthEmpRecord::getIEmpRecordId, iEmpRecordIds).remove();
		}
		removeById(iRecordId);
		HrmInsuranceMonthRecord monthRecord = lambdaQuery().orderByDesc(HrmInsuranceMonthRecord::getCreateTime).last("limit 1").one();
		monthRecord.setStatus(0);
		updateById(monthRecord);
		insuranceActionRecordService.deleteInsurance(monthRecord);
	}
}
