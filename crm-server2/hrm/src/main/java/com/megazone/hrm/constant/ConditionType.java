package com.megazone.hrm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConditionType {
	/**
	 * Advanced filter types
	 */
	IS("equal to", "is", 1),
	IS_NOT("Not equal to", "isNot", 2),
	CONTAINS("contains", "contains", 3),
	NOT_CONTAINS("Not Contains", "notContains", 4),
	IS_NULL("null", "isNull", 5),
	IS_NOT_NULL("Not Null", "isNotNull", 6),
	GT("greater than", "gt", 7),
	EGT("greater than or equal to", "egt", 8),
	LT("less than", "lt", 9),
	ELT("Less than or equal to", "elt", 10),
	DATE("start and end", "date", 11),
	DATETIME("start and end", "datetime", 12),
	IN("includes", "in", 13),
	NOT_IN("Not Included", "notIn", 14);


	private String name;
	private String condition;
	private int conditionType;


	public static ConditionType parse(int conditionType) {
		for (ConditionType value : ConditionType.values()) {
			if (value.conditionType == conditionType) {
				return value;
			}
		}
		return IS;
	}


}
