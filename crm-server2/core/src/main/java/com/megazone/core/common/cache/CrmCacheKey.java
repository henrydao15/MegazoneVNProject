package com.megazone.core.common.cache;

/**
 * crm cache key
 */
public interface CrmCacheKey {
	/**
	 * Print template cache key
	 */
	String CRM_PRINT_TEMPLATE_CACHE_KEY = "CRM:PRINT:TEMPLATE:";

	/**
	 * Regularly put the open sea cache key
	 */
	String CRM_CUSTOMER_JOB_CACHE_KEY = "CrmCustomerJob:";

	/**
	 * crm to-do number cache key
	 */
	String CRM_BACKLOG_NUM_CACHE_KEY = "queryBackLogNum:";

	/**
	 * crm call center cache key
	 */
	String CRM_CALL_CACHE_KEY = "call:";

	/**
	 * Data permission cache key
	 */
	String CRM_AUTH_USER_CACHE_KEY = "userAuth:Menu:";
}
