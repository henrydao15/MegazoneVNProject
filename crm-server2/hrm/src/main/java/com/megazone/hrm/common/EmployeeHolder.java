package com.megazone.hrm.common;

import com.megazone.core.feign.admin.entity.AdminRole;
import com.megazone.core.utils.UserUtil;
import com.megazone.hrm.entity.VO.EmployeeInfo;

public class EmployeeHolder {

	private static ThreadLocal<EmployeeInfo> employeeInfoThreadLocal = new ThreadLocal<>();


	public static EmployeeInfo getEmployeeInfo() {
		return employeeInfoThreadLocal.get();
	}

	public static void setEmployeeInfo(EmployeeInfo employeeInfo) {
		employeeInfoThreadLocal.set(employeeInfo);
	}

	public static Integer getEmployeeId() {
		if (employeeInfoThreadLocal.get() != null) {
			return employeeInfoThreadLocal.get().getEmployeeId();
		} else {
			return -1;
		}
	}

	/**
	 * @return
	 */
	public static boolean isHrmAdmin() {
		EmployeeInfo employeeInfo = employeeInfoThreadLocal.get();
		AdminRole role = employeeInfo.getRole();
		return UserUtil.isAdmin() || (role != null && role.getLabel() == 91);
	}

	public static void remove() {
		employeeInfoThreadLocal.remove();
	}
}
