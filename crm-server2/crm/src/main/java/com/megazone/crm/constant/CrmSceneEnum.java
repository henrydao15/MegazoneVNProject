package com.megazone.crm.constant;

/**
 * @author
 */
public enum CrmSceneEnum {
	/**
	 *
	 */
	ALL,
	/**
	 *
	 */
	SELF,
	/**
	 *
	 */
	CHILD,
	/**
	 *
	 */
	STAR,
	/**
	 *
	 */
	TRANSFORM,
	;

	CrmSceneEnum() {

	}

	public String getName() {
		return name().toLowerCase();
	}
}
