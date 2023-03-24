package com.megazone.core.common.log;

public enum BehaviorEnum {

	/**
	 * Operation record behavior
	 */
	SAVE(1, "New"),
	UPDATE(2, "Edit"),
	DELETE(3, "Delete"),
	CHANGE_OWNER(4, "Transfer"),
	TRANSFER(5, "Transform"),
	EXCEL_IMPORT(6, "Import"),
	EXCEL_EXPORT(7, "Export"),
	PUT_IN_POOL(8, "Put in the high sea"),
	RECEIVE(9, "Receive"),
	DISTRIBUTE(10, "Assig"),
	LOCK(11, "Lock"),
	UNLOCK(12, "Unlock"),
	CHANGE_DEAL_STATUS(13, "Change deal status"),
	ADD_MEMBER(14, "Add Team Member"),
	UPDATE_MEMBER(15, "Edit team members"),
	REMOVE_MEMBER(16, "Remove team members"),
	EXIT_MEMBER(17, "Exit Team"),
	UPLOAD(18, "Upload"),
	UPDATE_BUSINESS_STATUS(19, "Edit Business Status"),
	SUBMIT_EXAMINE(20, "Submit for review"),
	CANCEL_EXAMINE(21, "Cancel contract"),
	PUT_ON_SALE(22, "Available"),
	PUT_OFF_SALE(23, "Unavailable"),
	START(24, "Enable"),
	STOP(25, "Disable"),
	FOLLOW_UP(26, "New follow-up record"),
	PASS_EXAMINE(27, "Approved"),
	REJECT_EXAMINE(28, "Rejected"),
	RECHECK_EXAMINE(29, "Approval Withdrawn"),
	ARCHIVE(30, "Archive"),
	RESTORE(31, "Restore"),
	EXIT(32, "Exit"),
	ACTIVE(33, "Activate"),
	CLEAN(34, "Complete Delete"),
	FORBID(35, "Disabled"),
	RESET(36, "Reset"),
	COPY(37, "Copy"),
	RELATE(38, "Relationship"),
	UNBINDING(39, "Unbind"),
	NULL(0, "null");

	private int type;
	private String name;

	BehaviorEnum(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public static BehaviorEnum parse(int type) {
		for (BehaviorEnum Enum : BehaviorEnum.values()) {
			if (Enum.getType() == type) {
				return Enum;
			}
		}
		return NULL;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
