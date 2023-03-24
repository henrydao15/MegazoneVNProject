package com.megazone.core.feign.admin.entity;


public enum  DataTypeEnum {
	/**
	 *
	 */
	SELF(1),
	SELF_AND_CHILD(2),
	DEPT(3),
	DEPT_AND_CHILD(4),
	ALL(5);

	private Integer type;

	DataTypeEnum(Integer type) {
		this.type = type;
	}

	public static DataTypeEnum parse(Integer type) {
		for (DataTypeEnum typeEnum : values()) {
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
