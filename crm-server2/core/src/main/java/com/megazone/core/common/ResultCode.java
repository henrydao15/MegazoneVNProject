package com.megazone.core.common;

/**
 * The default response class, all return messages need to inherit this class
 */
public interface ResultCode {

	/**
	 * System response code
	 *
	 * @return code
	 */
	public int getCode();

	/**
	 * Default system response prompt, this is empty when code=0
	 *
	 * @return msg
	 */
	public String getMsg();
}
