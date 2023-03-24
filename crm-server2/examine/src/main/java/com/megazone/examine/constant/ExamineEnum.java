package com.megazone.examine.constant;

import cn.hutool.core.collection.ListUtil;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;

import java.util.Arrays;
import java.util.Objects;

/**
 * Approval label enumeration 1 Contract 2 Payment 3 Invoice 4 Salary 5 Purchase Review 6 Purchase Return Review 7 Sales Review 8 Sales Return Review 9 Payment Slip Review 10 Receipt Review 11 Inventory Review 12 Transfer Review
 */
public enum ExamineEnum {
	CRM_CONTRACT(1, "Contract"),
	CRM_RECEIVABLES(2, "Collection"),
	CRM_INVOICE(3, "Invoice"),

	;

	private Integer type;
	private String remark;

	private ExamineEnum(Integer type, String remark) {
		this.type = type;
		this.remark = remark;
	}

	public static ExamineEnum valueOf(Integer type) {
		for (ExamineEnum examineEnum : values()) {
			if (examineEnum.getType().equals(type)) {
				return examineEnum;
			}
		}
		throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
	}

	public static ExamineModuleTypeEnum parseModule(Integer label) {
		if (Arrays.asList(1, 2, 3).contains(label)) {
			return ExamineModuleTypeEnum.Crm;
		}
		if (ListUtil.toList(4).contains(label)) {
			return ExamineModuleTypeEnum.Hrm;
		}
		if (Arrays.asList(5, 6, 7, 8, 9, 10, 11, 12).contains(label)) {
			return ExamineModuleTypeEnum.Jxc;
		}
		if (Objects.equals(0, label)) {
			return ExamineModuleTypeEnum.Oa;
		}
		throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
	}

	public Integer getType() {
		return type;
	}

	public String getRemark() {
		return remark;
	}
}
