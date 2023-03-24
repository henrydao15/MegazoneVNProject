package com.megazone.examine.constant;

import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;

import java.util.Objects;

/**
 * @date 20201117
 */
public enum ExamineTypeEnum {

	/*

	 */
	CONDITION(0),
	/**
	 *
	 */
	MEMBER(1),
	/**
	 *
	 */
	SUPERIOR(2),
	/**
	 *
	 */
	ROLE(3),
	/**
	 *
	 */
	OPTIONAL(4),
	/**
	 *
	 */
	CONTINUOUS_SUPERIOR(5),

	/**
	 *
	 */
	MANAGER(6);

	private Integer type;

	private ExamineTypeEnum(Integer type) {
		this.type = type;
	}

	public static ExamineTypeEnum valueOf(Integer type) {
		for (ExamineTypeEnum examineTypeEnum : values()) {
			if (Objects.equals(type, examineTypeEnum.getType())) {
				return examineTypeEnum;
			}
		}
		throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
	}

	public Integer getType() {
		return type;
	}

	public String getServerName() {
		return StrUtil.toCamelCase(name().toLowerCase()) + "Service";
	}
}
