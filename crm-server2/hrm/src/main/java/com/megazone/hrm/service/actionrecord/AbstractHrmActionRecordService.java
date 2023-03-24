package com.megazone.hrm.service.actionrecord;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import com.megazone.hrm.constant.LabelGroupEnum;
import com.megazone.hrm.entity.PO.HrmDept;
import com.megazone.hrm.entity.PO.HrmEmployee;
import com.megazone.hrm.service.IHrmActionRecordService;
import com.megazone.hrm.service.IHrmDeptService;
import com.megazone.hrm.service.IHrmEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

public abstract class AbstractHrmActionRecordService {

	@Autowired
	protected IHrmActionRecordService actionRecordService;

	@Autowired
	private IHrmEmployeeService employeeService;

	@Autowired
	private IHrmDeptService hrmDeptService;

	protected List<String> entityCommonUpdateRecord(LabelGroupEnum labelGroupEnum, Dict properties, Map<String, Object> oldColumns, Map<String, Object> newColumns) {
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
					contentList.add(compare(labelGroupEnum, properties, oldFieldKey, oldValue, newValue));
				}
			}
		}
		return contentList;
	}

	protected String compare(LabelGroupEnum labelGroupEnum, Dict properties, String newFieldKey, String oldValue, String newValue) {
		return "Change " + properties.getStr(newFieldKey) + " from " + oldValue + " to " + newValue;
	}

	protected String employeeCompare(String fieldName, String oldValue, String newValue) {
		HrmEmployee oldEmployee = employeeService.getById(oldValue);
		HrmEmployee newEmployee = employeeService.getById(newValue);
		String oldDesc = "None";
		String newDesc = "None";
		if (oldEmployee != null) {
			oldDesc = oldEmployee.getEmployeeName();
		}
		if (newEmployee != null) {
			newDesc = newEmployee.getEmployeeName();
		}
		return "Change " + fieldName + " from " + oldDesc + " to " + newDesc;
	}

	protected String hrmDeptCompare(String fieldName, String oldValue, String newValue) {
		HrmDept oldDept = hrmDeptService.getById(oldValue);
		HrmDept newDept = hrmDeptService.getById(newValue);
		String oldDesc = "None";
		String newDesc = "None";
		if (oldDept != null) {
			oldDesc = oldDept.getName();
		}
		if (newDept != null) {
			newDesc = newDept.getName();
		}
		return "Change " + fieldName + " from " + oldDesc + " to " + newDesc;
	}

}
