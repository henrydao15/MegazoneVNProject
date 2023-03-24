package com.megazone.core.common.cache;

/**
 * HR cache key
 */
public interface HrmCacheKey {

	/**
	 * Social security type cache key
	 */
	String INSURANCE_TYPE_CACHE_KEY = "HRM:INSURANCE_TYPE:";
	/**
	 * Social security ratio cache key
	 */
	String INSURANCE_SCALE_CACHE_KEY = "HRM:INSURANCE_SCALE:";
	/**
	 * Employee mobile phone number cache key
	 */
	String HRM_EMPLOYEE_MOBILE_CACHE = "HRM:EMPLOYEE:MOBILE:CACHE:";

	/**
	 * Employee user id cache key
	 */
	String HRM_EMPLOYEE_USER_CACHE = "HRM:EMPLOYEE:USER:CACHE:";
	/**
	 * HR calendar cache key
	 */
	String HRM_NOTES_CACHE_KEY = "HRM:NOTES:%s:%s-%s";
}
