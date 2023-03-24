package com.megazone.hrm.constant;

import lombok.Getter;

@Getter
public enum NotesType {

	/**
	 * Memo type
	 */
	NOTES(1, "Memo"), BIRTHDAY(2, "Birthday"), ENTRY(3, "Joining"), BECOME(4, "regularization"), LEAVE(5, "resignation"), RECRUIT(6, "recruitment"),
	CLOCK(7, "Punch");

	private int value;
	private String name;

	NotesType(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
