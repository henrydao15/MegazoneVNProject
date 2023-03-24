package com.megazone.hrm.common.log;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.megazone.hrm.service.IHrmDeptService;
import com.megazone.hrm.service.IHrmEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class SysLogUtil {

	private static Map<String, Dict> propertiesMap = new HashMap<>();

	static {
		propertiesMap.put("dept", Dict.create().set("pid", "parent organization").set("deptType", "department type")
				.set("name", "Organization Name").set("code", "Organization ID").set("mainEmployeeId", "Organization Leader").set("leaderEmployeeId", "Leader in charge"));
		propertiesMap.put("", Dict.create().set("candidateName", "candidateName").set("mobile", "mobile").set("sex", "sex").set("age", "age")
				.set("postId", "Position").set("workTime", "Working Years").set("education", "Education").set("graduateSchool", "Graduate School").set("latestWorkPlace", "latest work place")
				.set("channelId", "Recruitment Channel").set("remark", "Remarks"));
	}

	@Autowired
	private IHrmDeptService deptService;
	@Autowired
	private IHrmEmployeeService employeeService;

	public List<String> updateRecord(Map<String, Object> oldColumns, Map<String, Object> newColumns, String type) {
		Dict properties = propertiesMap.get(type);
		List<String> contentList = new ArrayList<>();
		String defaultValue = "null";
		for (String oldFieldKey : oldColumns.keySet()) {
			if (!properties.containsKey(oldFieldKey)) {
				continue;
			}
			Object oldValueObj = oldColumns.get(oldFieldKey);
			if (newColumns.containsKey(oldFieldKey)) {
				Object newValueObj = newColumns.get(oldFieldKey);
				String oldValue;
				String newValue;
				// convert value
				if (newValueObj instanceof Date || oldValueObj instanceof Date) {
					oldValue = DateUtil.formatDateTime(Convert.toDate(oldValueObj));
					newValue = DateUtil.formatDateTime(Convert.toDate(newValueObj));
				} else if (newValueObj instanceof BigDecimal || oldValueObj instanceof BigDecimal) {
					oldValue = Convert.toBigDecimal(oldValueObj, new BigDecimal(0)).setScale(2, BigDecimal.ROUND_UP).toString();
					newValue = Convert.toBigDecimal(newValueObj, new BigDecimal(0)).setScale(2, BigDecimal.ROUND_UP).toString();
				} else {
					oldValue = Convert.toStr(oldValueObj);
					newValue = Convert.toStr(newValueObj);
				}
				if (StrUtil.isEmpty(oldValue)) {
					oldValue = defaultValue;
				}
				if (StrUtil.isEmpty(newValue)) {
					newValue = defaultValue;
				}
				if (!Objects.equals(oldValue, newValue)) {
					if ("dept".equals(type)) {
						if ("pid".equals(oldFieldKey)) {
							if (!"null".equals(oldValue)) {
								oldValue = deptService.getById(Integer.parseInt(oldValue)).getName();
							}
							if (!"null".equals(newValue)) {
								newValue = deptService.getById(Integer.parseInt(newValue)).getName();
							}
						} else if ("deptType".equals(oldFieldKey)) {
							if (!"null".equals(oldValue)) {
								if (Integer.parseInt(oldValue) == 1) {
									oldValue = "Company";
								} else {
									oldValue = "Department";
								}
							}
							if (!"null".equals(newValue)) {
								if (Integer.parseInt(newValue) == 1) {
									newValue = "Company";
								} else {
									newValue = "Department";
								}
							}
						} else if ("mainEmployeeId".equals(oldFieldKey) || "leaderEmployeeId".equals(oldFieldKey)) {
							if (!"null".equals(oldValue)) {
								oldValue = employeeService.getById(Integer.parseInt(oldValue)).getEmployeeName();
							}
							if (!"null".equals(newValue)) {
								newValue = employeeService.getById(Integer.parseInt(newValue)).getEmployeeName();
							}
						}
					}
					String detail = "Change " + properties.getStr(oldFieldKey) + " from " + oldValue + " to " + newValue;
					contentList.add(detail);
				}
			}
		}
		return contentList;
	}
}
