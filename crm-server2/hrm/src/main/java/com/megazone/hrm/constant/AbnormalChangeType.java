package com.megazone.hrm.constant;

import lombok.Getter;

/**
 * Employee transaction type enumeration
 */
@Getter
public enum AbnormalChangeType {
	// Transaction type 1 Entry 2 Resignation 3 Regularization 4 Transfer
	NEW_ENTRY(1, "New entry"), RESIGNATION(2, "Resignation"),
	BECOME(3, "Convert to positive"), CHANGE_POST(4, "Transfer post");


	private String name;
	private int value;

	AbnormalChangeType(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
