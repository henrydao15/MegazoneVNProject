package com.megazone.core.common;

/**
 * Data permission enumeration
 */

public enum DataAuthEnum {
	/**
	 * Data permission 1. Me, 2. Me and my subordinates, 3. This department, 4. This department and its subordinate departments, 5. All
	 */
	MYSELF(1),
	MYSELF_AND_SUBORDINATE(2),
	THIS_DEPARTMENT(3),
	THIS_DEPARTMENT_AND_SUBORDINATE(4),
	ALL(5);


	private Integer value;

	DataAuthEnum(Integer value) {
		this.value = value;
	}

	public static DataAuthEnum valueOf(Integer value) {
		for (DataAuthEnum dataAuthEnum : values()) {
			if (dataAuthEnum.getValue().equals(value)) {
				return dataAuthEnum;
			}
		}
		return MYSELF;
	}

	public Integer getValue() {
		return value;
	}

}
