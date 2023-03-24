package com.megazone.hrm.utils;

import cn.hutool.core.date.DateUtil;
import com.megazone.core.exception.CrmException;
import com.megazone.hrm.common.HrmCodeEnum;
import com.megazone.hrm.constant.EmployeeEntryStatus;
import com.megazone.hrm.constant.achievement.AppraisalEmployeeType;
import com.megazone.hrm.entity.PO.HrmDept;
import com.megazone.hrm.entity.PO.HrmEmployee;
import com.megazone.hrm.service.IHrmDeptService;
import com.megazone.hrm.service.IHrmEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Description:
 * @author: hmb
 * @date: 2020-05-11 11:56
 */
@Component
public class AchievementUtil {

	@Autowired
	private IHrmEmployeeService employeeService;

	@Autowired
	private IHrmDeptService deptService;

	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String appraisalTimeFormat(Date startTime, Date endTime) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM.dd");
		return DateUtil.format(startTime, dateFormat) + "-" + DateUtil.format(endTime, dateFormat);
	}

	/**
	 * id
	 *
	 * @param type                  1  2  3  4  5
	 * @param employeeId            id
	 * @param designationEmployeeId id
	 * @return
	 */
	public Integer findEmployeeIdByType(Integer type, Integer employeeId, Integer designationEmployeeId, String desc) {
		HrmEmployee employee;
		HrmDept dept;
		AppraisalEmployeeType employeeType = AppraisalEmployeeType.parse(type);
		switch (AppraisalEmployeeType.parse(type)) {
			case MYSELF:
				return employeeId;
			case PARENT:
				employee = employeeService.getById(employeeId);
				HrmEmployee parentEmployee = employeeService.getById(employee.getParentId());
				if (parentEmployee == null || parentEmployee.getEntryStatus().equals(EmployeeEntryStatus.ALREADY_LEAVE.getValue()) || parentEmployee.getIsDel() == 1) {
					throw new CrmException(HrmCodeEnum.PARENT_DOES_NOT_EXIST, employee.getEmployeeName(), desc);
				}
				return parentEmployee.getEmployeeId();
			case MYSELF_DEPT_MAIN:
				employee = employeeService.getById(employeeId);
				dept = deptService.getById(employee.getDeptId());
				if (dept == null) {
					throw new CrmException(HrmCodeEnum.DEPT_DOES_NOT_EXIST, employee.getEmployeeName(), desc);
				}
				HrmEmployee deptEmployee = employeeService.getById(dept.getMainEmployeeId());
				if (deptEmployee == null || deptEmployee.getEntryStatus().equals(EmployeeEntryStatus.ALREADY_LEAVE.getValue()) || deptEmployee.getIsDel() == 1) {
					throw new CrmException(HrmCodeEnum.DEPT_MAIN_EMPLOYEE_DOES_NOT_EXIST, employee.getEmployeeName(), desc);
				}
				return deptEmployee.getEmployeeId();
			case PARENT_DEPT_MAIN:
				employee = employeeService.getById(employeeId);
				dept = deptService.getById(employee.getDeptId());
				if (dept == null) {
					throw new CrmException(HrmCodeEnum.DEPT_DOES_NOT_EXIST, employee.getEmployeeName(), desc);
				}
				HrmDept parentDept = deptService.getById(dept.getPid());
				if (parentDept != null) {

					HrmEmployee parentDeptEmployee = employeeService.getById(parentDept.getMainEmployeeId());
					if (parentDeptEmployee == null || parentDeptEmployee.getEntryStatus().equals(EmployeeEntryStatus.ALREADY_LEAVE.getValue()) || parentDeptEmployee.getIsDel() == 1) {
						throw new CrmException(HrmCodeEnum.PARENT_MAIN_EMPLOYEE_DEPT_DOES_NOT_EXIST, employee.getEmployeeName(), desc);
					}
					return parentDeptEmployee.getEmployeeId();
				} else {
//                        
					HrmEmployee deptEmployee1 = employeeService.getById(dept.getMainEmployeeId());
					if (deptEmployee1 == null || deptEmployee1.getEntryStatus().equals(EmployeeEntryStatus.ALREADY_LEAVE.getValue()) || deptEmployee1.getIsDel() == 1) {
						throw new CrmException(HrmCodeEnum.DEPT_MAIN_EMPLOYEE_DOES_NOT_EXIST, employee.getEmployeeName(), desc);
					}
					return deptEmployee1.getEmployeeId();
				}
			case DESIGNATION:
				HrmEmployee designationEmployee = employeeService.getById(designationEmployeeId);
				if (designationEmployee.getEntryStatus().equals(EmployeeEntryStatus.ALREADY_LEAVE.getValue()) || designationEmployee.getIsDel() == 1) {
					throw new CrmException(HrmCodeEnum.DESIGNATION_EMPLOYEE_DEPT_DOES_NOT_EXIST, designationEmployee.getEmployeeName(), desc);
				}
				return designationEmployee.getEmployeeId();
			default:
				return null;
		}
	}
}
