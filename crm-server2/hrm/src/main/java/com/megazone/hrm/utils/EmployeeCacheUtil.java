package com.megazone.hrm.utils;

import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.hrm.entity.PO.HrmEmployee;
import com.megazone.hrm.service.IHrmEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author
 */
@Component
public class EmployeeCacheUtil {
	static EmployeeCacheUtil ME;
	@Autowired
	private AdminService adminService;
	@Autowired
	private IHrmEmployeeService employeeService;

	/**
	 * @param employeeId id
	 * @return data
	 */
	public static Long getUserId(Integer employeeId) {
		if (employeeId == null) {
			return 0L;
		}
		String employeeMobile = getEmployeeMobile(employeeId);
		Long userId = ME.adminService.queryUserIdByUserName(employeeMobile).getData();
		return userId;
	}

	/**
	 * @param employeeId id
	 * @return data
	 */
	public static String getEmployeeMobile(Integer employeeId) {
		if (employeeId == null) {
			return "";
		}
		Integer key = employeeId;
		String mobile = ME.employeeService.lambdaQuery().select(HrmEmployee::getMobile).eq(HrmEmployee::getEmployeeId, employeeId).one().getMobile();

		return mobile;
	}

	@PostConstruct
	public void init() {
		ME = this;
	}


}
