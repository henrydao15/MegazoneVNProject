package com.megazone.crm.common;

import java.util.HashMap;

/**
 * @author crm
 */
public class CrmModel extends HashMap<String, Object> {

	private transient Integer label;
	private transient Integer id;

	public CrmModel() {

	}

	public CrmModel(Integer label) {
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

	public Long getOwnerUserId() {
		return (Long) get("ownerUserId");
	}

	public void setOwnerUserId(Long ownerUserId) {
		put("ownerUserId", ownerUserId);
	}

	public String getOwnerUserName() {
		return (String) get("ownerUserName");
	}

	public void setOwnerUserName(String ownerUserName) {
		put("ownerUserName", ownerUserName);
	}

	public String getBatchId() {
		return (String) get("batchId");
	}
}
