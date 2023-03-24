package com.megazone.core.common;

import lombok.Getter;

@Getter
public enum ModelType {
	ADMIN("admin", "Administration"),
	CRM("crm", "Customer Management"),
	OA("oa", "office management"),
	WORK("work", "Project Management"),
	HRM("hrm", "Human Resource Management"),
	JXC("jxc", "Invoicing management"),
	;


	private String modelName;
	private String name;

	ModelType(String modelName, String name) {
		this.modelName = modelName;
		this.name = name;
	}

	public static String valueOfName(String modelName) {
		for (ModelType modelType : values()) {
			if (modelName.equals(modelType.getModelName())) {
				return modelType.getName();
			}
		}
		return "";
	}
}
