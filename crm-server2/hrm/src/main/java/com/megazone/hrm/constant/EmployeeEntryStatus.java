package com.megazone.hrm.constant;

import lombok.Getter;

@Getter
public enum EmployeeEntryStatus {

	IN(1, "Incumbent"), TO_IN(2, "To be employed"), TO_LEAVE(3, "To be resigned"), ALREADY_LEAVE(4, "To be resigned");

	private int value;
	private String name;

	EmployeeEntryStatus(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
