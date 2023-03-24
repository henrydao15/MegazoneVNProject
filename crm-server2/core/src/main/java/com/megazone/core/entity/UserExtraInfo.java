package com.megazone.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Some additional information parameters for users
 */
@Data
@AllArgsConstructor
public class UserExtraInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * whether
	 */
	public Integer extra = -1;

	public String extraTime;
}
