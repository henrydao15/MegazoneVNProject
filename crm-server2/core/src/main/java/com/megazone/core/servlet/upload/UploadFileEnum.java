package com.megazone.core.servlet.upload;

/**
 * @author
 */

public enum UploadFileEnum {
	/**
	 *
	 */
	LOCAL(1),
	/**
	 *
	 */
	ALI_OSS(2),
	/**
	 *
	 */
	ALI_COS(3),
	/**
	 *
	 */
	ALI_QNC(4);

	private Integer config;

	private UploadFileEnum(Integer config) {
		this.config = config;
	}

	public Integer getConfig() {
		return this.config;
	}
}
