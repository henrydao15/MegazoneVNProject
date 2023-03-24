package com.megazone.hrm.common;

import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.entity.AdminRole;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.utils.UserUtil;
import com.megazone.hrm.entity.VO.EmployeeInfo;
import com.megazone.hrm.service.IHrmEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * @author user
 */
@Aspect
@Component
@Slf4j
@Order(10)
public class EmployeeAspect {

	@Autowired
	private IHrmEmployeeService employeeService;

	@Autowired
	private AdminService adminService;

	@Around("execution(* com.megazone.hrm.controller..*.*(..)) && !execution(@(com.megazone.core.common.ParamAspect) * *(..)) ")
	public Object before(ProceedingJoinPoint point) throws Throwable {
		try {
			UserInfo user = UserUtil.getUser();
			EmployeeInfo employeeInfo = employeeService.queryEmployeeInfoByMobile(user.getUsername());
			if (employeeInfo == null) {
				employeeInfo = new EmployeeInfo();
			}
			AdminRole adminRole;
			if (UserUtil.isAdmin()) {
				adminRole = new AdminRole();
				adminRole.setLabel(91);
			} else {
				List<AdminRole> roles = adminService.queryRoleByRoleTypeAndUserId(9).getData();
				adminRole = roles.stream().min(Comparator.comparingInt(AdminRole::getLabel)).orElse(null);
			}
			employeeInfo.setRole(adminRole);
			EmployeeHolder.setEmployeeInfo(employeeInfo);
			return point.proceed();
		} finally {
			EmployeeHolder.remove();
		}
	}
}
