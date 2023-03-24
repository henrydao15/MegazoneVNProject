package com.megazone.core.common;


public enum BaseStatusEnum {

	OPEN(1),

	CLOSE(0);

	private Integer status;

	BaseStatusEnum(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}


}
