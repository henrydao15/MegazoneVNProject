package com.megazone.core.common;

import java.io.Serializable;

/**
 * Default configuration constant information
 */
public class Const implements Serializable {

	/**
	 * Project version
	 */
	public static final String PROJECT_VERSION = "11.3.3";

	/**
	 * default delimiter
	 */
	public static final String SEPARATOR = ",";

	/**
	 * Query data permission recursion times, which can be modified by inheriting this class
	 */
	public static final int AUTH_DATA_RECURSION_NUM = 20;

	/**
	 * The name of the business token in the header
	 */
	public static final String TOKEN_NAME = "Admin-Token";

	/**
	 * Default encoding
	 */
	public static final String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

	/**
	 * The maximum number of sql queries is limited, and the number of fields is limited
	 */
	public static final Integer QUERY_MAX_SIZE = 100;

	/**
	 * userKey for PC login
	 */
	public static final String USER_TOKEN = "WK:USER:TOKEN:";

	/**
	 * The maximum expiration time of the user token
	 */
	public static final Integer MAX_USER_EXIST_TIME = 3600 * 24 * 7;

	/**
	 * The number of records saved in batches
	 */
	public static final int BATCH_SAVE_SIZE = 200;

	/**
	 * Enterprise information cache KEY
	 */
	public static final String COMPANY_MANAGE_CACHE_NAME = "COMPANY:MANAGE:STATUS:";

	/**
	 * Username cache key
	 */
	public static final String ADMIN_USER_NAME_CACHE_NAME = "ADMIN:USER:CACHE:";

	/**
	 * Department name cache key
	 */
	public static final String ADMIN_DEPT_NAME_CACHE_NAME = "ADMIN:DEPT:CACHE:";

	/**
	 * User ID and department ID correspond to the cache key
	 */
	public static final String ADMIN_USER_DEPT_CACHE_NAME = "ADMIN:USER:DEPT:CACHE:";


}
