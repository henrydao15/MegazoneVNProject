package com.megazone.core.servlet.upload;

import lombok.Data;

/**
 * @author
 */
@Data
public class UploadEntity {

	/**
	 *
	 */
	private String fileId;
	/**
	 *
	 */
	private String name;
	/**
	 *
	 */
	private Long size;
	/**
	 *
	 */
	private String batchId;
	/**
	 *
	 */
	private Integer type;
	/**
	 *
	 */
	private String path;
	private String url;
	private String isPublic;

	public UploadEntity() {

	}

	public UploadEntity(String fileId, String name, Long size, String batchId, String isPublic) {
		this.fileId = fileId;
		this.name = name;
		this.size = size;
		this.batchId = batchId;
		this.isPublic = isPublic;
	}

	public String getUrl() {
		if ("1".equals(isPublic)) {
			return path;
		} else {
			return "/adminFile/down/" + fileId;
		}
	}

	public void setUrl(String url) {
		this.path = url;
	}
}
