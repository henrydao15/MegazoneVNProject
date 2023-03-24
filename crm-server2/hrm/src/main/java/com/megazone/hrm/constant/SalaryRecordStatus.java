package com.megazone.hrm.constant;

import lombok.Getter;

/**
 * Payroll record status
 *
 * @author
 */

@Getter
public enum SalaryRecordStatus {
	//Status 0 pending, 1 passed, 2 rejected, 3 under review 4: withdrawn 5 not submitted
	WAIT_EXAMINE(0, "to be reviewed"), PASS(1, "approved (archived)"), REFUSE(2, "rejection for review"), UNDER_EXAMINE(3, "under review"), RECALL(4, "withdrawn"), CREATED(5, "Newly created, salary not generated"), HISTORY(10, "Historical salary"), COMPUTE(11, "Accounting completed");


	private String name;
	private int value;

	SalaryRecordStatus(int value, String name) {
		this.value = value;
		this.name = name;
	}
}
