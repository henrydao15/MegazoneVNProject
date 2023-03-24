package com.megazone.examine.constant;

import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;

/**
 *
 */
public enum ExamineStatusEnum {
	AWAIT(0),
	PASS(1),
	REJECT(2),
	UNDERWAY(3),
	RECHECK(4),
	UN_SUBMITTED(5),
	CREATE(6),
	REMOVE(7),
	INVALID(8),
	IGNORE(10),
	;

	private Integer status;

	private ExamineStatusEnum(Integer status) {
		this.status = status;
	}

	public static ExamineStatusEnum valueOf(Integer status) {
		for (ExamineStatusEnum examineStatusEnum : values()) {
			if (examineStatusEnum.getStatus().equals(status)) {
				return examineStatusEnum;
			}
		}
		throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
	}

	public Integer getStatus() {
		return status;
	}
}
