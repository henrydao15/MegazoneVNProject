package com.megazone.admin.common;

public enum AdminRoleTypeEnum {
	CUSTOM(0),
	MANAGER(1),
	CUSTOMER_MANAGER(2),
	PERSONNEL(3),
	FINANCE(4),
	WORK(5),
	OA(7),
	PROJECT(8),
	HRM(9),
	JXC(10);

	private Integer type;

	AdminRoleTypeEnum(Integer type) {
		this.type = type;
	}

	public static AdminRoleTypeEnum parse(Integer type) {
		for (AdminRoleTypeEnum typeEnum : values()) {
			if (typeEnum.getType().equals(type)) {
				return typeEnum;
			}
		}
		return null;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
