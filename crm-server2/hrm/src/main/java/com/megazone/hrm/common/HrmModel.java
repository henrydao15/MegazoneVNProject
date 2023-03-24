package com.megazone.hrm.common;

import java.util.HashMap;

/**
 * hrm
 */
public class HrmModel extends HashMap<String, Object> {
	private transient Integer label;
	private transient Integer id;

	public HrmModel() {

	}

	public HrmModel(Integer label) {
		this.label = label;
	}

	public Integer getLabel() {
		return label;
	}

	public void setLabel(Integer label) {
		this.label = label;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
