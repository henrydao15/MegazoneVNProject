package com.megazone.core.common.cache;

/**
 * admin cache key
 */
public interface AdminCacheKey {

	String USER_AUTH_CACHE_KET = "user_auth:";

	/**
	 * Import message cache key
	 */
	String UPLOAD_EXCEL_MESSAGE_PREFIX = "upload:excel:message:";

	/**
	 * Send SMS cache key
	 * &s: host
	 */
	String SMS_CACHE_KEY = "send:sms:";


	String PASSWORD_ERROR_CACHE_KEY = "password:error:";

	String TEMPORARY_ACCESS_CODE_CACHE_KEY = "temporary:access:code:";
}
