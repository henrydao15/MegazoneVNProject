package com.megazone.oa.constart;

import lombok.Getter;

@Getter
public enum RepetitionType {
	/**
	 * RepetitionType
	 */
	NO_REPETITION(1, "Never Repeat", "NoRepetition"),
	DAILY(2, "Daily", "Daily"),
	WEEKLY(3, "Weekly", "Weekly"),
	MONTHLY(4, "Monthly", "Monthly"),
	ANNUALLY(5, "Annually", "Annually");

	private Integer type;
	private String remark;
	private String name;

	RepetitionType(Integer type, String remark, String name) {
		this.type = type;
		this.remark = remark;
		this.name = name;
	}

	public static RepetitionType valueOf(Integer type) {
		for (RepetitionType c : RepetitionType.values()) {
			if (c.getType().equals(type)) {
				return c;
			}
		}
		return RepetitionType.NO_REPETITION;
	}

}
