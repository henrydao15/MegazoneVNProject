package com.megazone.hrm.utils;

import cn.hutool.core.collection.CollUtil;
import com.megazone.core.common.DataAuthEnum;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.utils.UserUtil;
import com.megazone.hrm.common.EmployeeHolder;
import com.megazone.hrm.entity.PO.HrmDept;
import com.megazone.hrm.entity.PO.HrmEmployee;
import com.megazone.hrm.entity.VO.EmployeeInfo;
import com.megazone.hrm.service.IHrmDeptService;
import com.megazone.hrm.service.IHrmEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeUtil {

	@Autowired
	private AdminService adminService;

	@Autowired
	private IHrmEmployeeService employeeService;

	@Autowired
	private IHrmDeptService deptService;

	public static String computeCompanyAge(Integer companyAge) {
		if (companyAge <= 0) {
			return "";
		}
		int year = companyAge / 365;
		int month = (companyAge % 365) / 30;
		int day = (companyAge % 365) % 30;
		StringBuilder sb = new StringBuilder();
		if (year != 0) {
			sb.append(year).append("year");
		}
		if (month != 0) {
			sb.append(month).append("month");
		}
		if (day != 0) {
			sb.append(day).append("day");
		}
		return sb.toString();
	}

	/**
	 * Get the employee id in the permission according to the menu id
	 *
	 * @param menuId
	 * @return
	 */
	public Collection<Integer> queryDataAuthEmpIdByMenuId(Integer menuId) {
		Collection<Integer> dataAuthEmployeeIds = null;
		Integer dataAuthType = adminService.queryDataType(UserUtil.getUserId(), menuId).getData();
		if (!EmployeeHolder.isHrmAdmin() && !dataAuthType.equals(DataAuthEnum.ALL.getValue())) {
			dataAuthEmployeeIds = queryDataAuthEmpId(dataAuthType);
		} else {
			dataAuthEmployeeIds = employeeService.lambdaQuery().select(HrmEmployee::getEmployeeId).eq(HrmEmployee::getIsDel, 0).list()
					.stream().map(HrmEmployee::getEmployeeId).collect(Collectors.toList());
		}
		return dataAuthEmployeeIds;
	}

	/**
	 * Query the employee id in the data permission
	 *
	 * @param dataAuthType
	 * @return
	 */
	public Collection<Integer> queryDataAuthEmpId(Integer dataAuthType) {
		EmployeeInfo employeeInfo = EmployeeHolder.getEmployeeInfo();
		Set<Integer> employeeIds = new HashSet<>();
		employeeIds.add(employeeInfo.getEmployeeId());
		DataAuthEnum dataAuthEnum = DataAuthEnum.valueOf(dataAuthType);
		Set<Integer> deptIds = new HashSet<>();
		switch (dataAuthEnum) {
			case ALL:
				employeeIds.addAll(employeeService.lambdaQuery().select(HrmEmployee::getEmployeeId).list()
						.stream().map(HrmEmployee::getEmployeeId).collect(Collectors.toList()));
				break;
			case THIS_DEPARTMENT_AND_SUBORDINATE:
				deptIds.add(employeeInfo.getDeptId());
				deptIds.addAll(deptService.queryChildDeptId(Collections.singletonList(employeeInfo.getDeptId())));
				break;
			case THIS_DEPARTMENT:
				deptIds.add(employeeInfo.getDeptId());
				break;
			case MYSELF_AND_SUBORDINATE:
				employeeIds.add(employeeInfo.getEmployeeId());
				employeeIds.addAll(employeeService.queryChildEmployeeId(Collections.singletonList(employeeInfo.getEmployeeId())));
				break;
			case MYSELF:
				employeeIds.add(employeeInfo.getEmployeeId());
				break;
			default:
				break;
		}
		if (CollUtil.isNotEmpty(deptIds)) {
			employeeIds.addAll(employeeService.lambdaQuery().select(HrmEmployee::getEmployeeId).in(HrmEmployee::getDeptId, deptIds).list()
					.stream().map(HrmEmployee::getEmployeeId).collect(Collectors.toList()));
		}
		return employeeIds;
	}

	public Collection<Integer> queryDataAuthDeptIdByMenuId(Integer menuId) {
		Collection<Integer> dataAuthDeptIds = null;
		Integer dataAuthType = adminService.queryDataType(UserUtil.getUserId(), menuId).getData();
		if (!EmployeeHolder.isHrmAdmin() && !dataAuthType.equals(DataAuthEnum.ALL.getValue())) {
			dataAuthDeptIds = queryDataAuthDeptId(dataAuthType);
		}
		return dataAuthDeptIds;
	}

	/**
	 * Query the department id in the data permission
	 *
	 * @param dataAuthType
	 * @return
	 */
	public Collection<Integer> queryDataAuthDeptId(Integer dataAuthType) {
		EmployeeInfo employeeInfo = EmployeeHolder.getEmployeeInfo();
		Set<Integer> employeeIds = new HashSet<>();
		DataAuthEnum dataAuthEnum = DataAuthEnum.valueOf(dataAuthType);
		Set<Integer> deptIds = new HashSet<>();
		switch (dataAuthEnum) {
			case ALL:
				deptIds.addAll(deptService.lambdaQuery().select(HrmDept::getDeptId).list()
						.stream().map(HrmDept::getDeptId).collect(Collectors.toList()));
				break;
			case THIS_DEPARTMENT_AND_SUBORDINATE:
				deptIds.add(employeeInfo.getDeptId());
				deptIds.addAll(deptService.queryChildDeptId(Collections.singletonList(employeeInfo.getDeptId())));
				break;
			case THIS_DEPARTMENT:
				deptIds.add(employeeInfo.getDeptId());
				break;
			case MYSELF_AND_SUBORDINATE:
				employeeIds.add(employeeInfo.getEmployeeId());
				employeeIds.addAll(employeeService.queryChildEmployeeId(Collections.singletonList(employeeInfo.getEmployeeId())));
				break;
			case MYSELF:
				employeeIds.add(employeeInfo.getEmployeeId());
				break;
			default:
				break;
		}
		if (CollUtil.isNotEmpty(employeeIds)) {
			deptIds.addAll(employeeService.lambdaQuery().select(HrmEmployee::getDeptId).in(HrmEmployee::getEmployeeId, employeeIds).list()
					.stream().filter(employee -> employee != null && employee.getDeptId() != null).map(HrmEmployee::getDeptId).collect(Collectors.toList()));
		}
		return deptIds;
	}
}
