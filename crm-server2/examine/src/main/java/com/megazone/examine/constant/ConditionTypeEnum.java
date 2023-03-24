package com.megazone.examine.constant;

import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;

/**
 *
 */
public enum ConditionTypeEnum {

	EQ(1),
	GT(2),
	LT(3),
	GE(4),
	LE(5),
	AMONG(6),
	CONTAIN(7),
	PERSONNEL(8),
	EQUALS(11),
	;

	private Integer type;

	private ConditionTypeEnum(Integer type) {
		this.type = type;
	}

	public static ConditionTypeEnum valueOf(Integer type) {
		for (ConditionTypeEnum conditionEnum : values()) {
			if (conditionEnum.getType().equals(type)) {
				return conditionEnum;
			}
		}
		throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
	}

	public Integer getType() {
		return type;
	}
}
