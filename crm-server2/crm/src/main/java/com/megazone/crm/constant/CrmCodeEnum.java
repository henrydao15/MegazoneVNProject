package com.megazone.crm.constant;

import com.megazone.core.common.Const;
import com.megazone.core.common.ResultCode;

/**
 * @author crm response error code enumeration class
 */

public enum CrmCodeEnum implements ResultCode {
	//Customer module management
	ADMIN_MODULE_CLOSE_ERROR(2001, "Customer management module cannot be closed"),
	CRM_FIELD_NUM_ERROR(2002, "At most each module exists" + Const.QUERY_MAX_SIZE + "fields"),
	CRM_LEADS_TRANSFER_ERROR(2003, "Transformed leads cannot be repeatedly converted"),
	CRM_DATE_REMOVE_ERROR(2004, "Data has been deleted"),
	CRM_PRODUCT_CATEGORY_ERROR(2005, "This product category is already associated with a product and cannot be deleted!"),
	CRM_PRODUCT_CATEGORY_CHILD_ERROR(2006, "There are other product categories under this category!"),
	CRM_MEMBER_ADD_ERROR(2007, "The person in charge cannot be selected as a team member repeatedly!"),
	CRM_MEMBER_DELETE_ERROR(2008, "The person in charge cannot leave the team!"),
	CRM_CONTRACT_DATE_ERROR(2009, "Contract end time should be greater than start time!"),
	CRM_CONTRACT_NUM_ERROR(2010, "Contract number already exists, please proofread before adding!"),
	CRM_CONTRACT_EXAMINE_STEP_ERROR(2011, "No audit step started, cannot be added!"),
	CRM_CONTRACT_EXAMINE_USER_ERROR(2012, "No approvers, can't add!"),
	CRM_CONTRACT_CANCELLATION_ERROR(2013, "The contract has been cancelled and cannot be edited"),
	CRM_CONTRACT_EXAMINE_PASS_ERROR(2014, "The contract that has passed cannot be edited"),
	CRM_CONTRACT_EDIT_ERROR(2015, "Cannot be edited, please withdraw and edit!"),
	CRM_CONTRACT_TRANSFER_ERROR(2016, "Cannot transfer voided contracts!"),
	CRM_DATA_JOIN_ERROR(2017, "This piece of data is necessary to be associated with other data, please do not delete"),
	CRM_RECEIVABLES_ADD_ERROR(2018, "The current contract has not been approved, and the payment cannot be added"),
	CRM_RECEIVABLES_NUM_ERROR(2019, "The receipt number already exists, please proofread and add it!"),
	CRM_RECEIVABLES_EDIT_ERROR(2020, "You can only edit the money you created by yourself"),
	CRM_RECEIVABLES_PLAN_ERROR(2021, "The refund plan has received the payment, please do not edit"),
	CRM_CUSTOMER_SETTING_USER_ERROR(2022, "The customer has reached the upper limit and cannot be added"),
	NO_APPROVAL_STEP_CANNOT_BE_SAVED(2023, "Cannot save without approval step"),
	CRM_CUSTOMER_LOCK_ERROR(2024, "There is no need to lock the closed customer"),
	CRM_CUSTOMER_LOCK_MAX_ERROR(2025, "The maximum number of locked customers with employees"),
	CRM_EXAMINE_RECHECK_ERROR(2026, "The approval has been withdrawn"),
	CRM_EXAMINE_AUTH_ERROR(2027, "The current user does not have approval authority"),
	CRM_EXAMINE_RECHECK_PASS_ERROR(2028, "The review has passed and cannot be withdrawn"),
	CRM_NEXT_TIME_ERROR(2029, "The last follow-up time must be greater than the current time"),
	CRM_CRMRETURNVISIT_NUM_ERROR(2030, "The return visit number already exists, please proofread before adding!"),
	CRM_ILLEGAL_CHARACTERS_ERROR(2031, "Contains illegal characters"),
	CRM_PHONE_FORMAT_ERROR(2032, "The format of the mobile phone number is incorrect"),
	CRM_PRICE_FORMAT_ERROR(2033, "Price should be in numeric format"),
	CRM_DATETIME_FORMAT_ERROR(2034, "Date format error, for example: 2020-01-01 00:00:00"),
	CRM_DATE_FORMAT_ERROR(2035, "The date format is incorrect, for example: 2020-01-01"),
	CRM_CUSTOMER_POOL_EXIST_USER_ERROR(2036, "There is a customer in the high seas, which cannot be deactivated"),
	CRM_CUSTOMER_POOL_LAST_ERROR(2037, "The last one enabled on high seas cannot be disabled"),
	CRM_FIELD_EXISTED(2038, "%s already exists"),
	THE_NUMBER_OF_CUSTOMERS_HAS_REACHED_THE_LIMIT(2039, "The number of customers this employee has reached the upper limit"),
	CRM_NO_AUTH(2040, "No authority"),
	CRM_CUSTOMER_POOL_USER_IS_NULL_ERROR(2041, "High seas administrator or high seas member cannot be null"),
	CRM_CUSTOMER_POOL_EXIST_USER_DELETE_ERROR(2042, "There is a customer in the high seas, which cannot be deleted"),
	CRM_CUSTOMER_POOL_LAST_DELETE_ERROR(2043, "The last enabled open ocean cannot be deleted"),
	CRM_PRINT_TEMPLATE_NOT_EXIST_ERROR(2044, "The print template used cannot be empty"),
	CRM_PRINT_PRE_VIEW_ERROR(2045, "Only support pdf and word format preview"),
	CRM_BUSINESS_TYPE_RATE_ERROR(2046, "Win rate cannot be greater than 100"),
	CRM_BUSINESS_TYPE_OCCUPY_ERROR(2047, "The business group in use cannot be modified"),
	CRM_CONTRACT_CONFIG_ERROR(2048, "Please set the number of days in advance to remind"),
	CRM_CUSTOMER_SETTING_USER_EXIST_ERROR(2049, "There is already employee or department information included in other rules"),
	CRM_NUMBER_SETTING_LENGTH_ERROR(2050, "Numbering sequence must not be less than two levels"),
	CRM_NUMBER_SETTING_DATE_NOTNULL_ERROR(2051, "A date numbering sequence is required to enable restart numbering"),
	CRM_CAN_ONLY_DELETE_FOLLOW_UP_RECORDS(2052, "Only follow-up records can be deleted"),
	CRM_RECEIVABLES_EXAMINE_PASS_ERROR(2053, "The approved payment cannot be edited"),
	CRM_CUSTOMER_POOL_NOT_EXIST_ERROR(2054, "No active high seas exist"),
	CRM_MARKETING_UNSYNCHRONIZED_DATA(2055, "There is unsynchronized data and cannot be deleted!"),
	CRM_MARKETING_STOP(2056, "This promotion is discontinued"),
	CRM_MARKETING_QR_CODE_EXPIRED(2057, "QR code expired"),
	CRM_MARKETING_CAN_ONLY_BE_FILLED_ONCE(2058, "Only fill in once"),
	CRM_MARKETING_DATA_SYNCED(2059, "Data has been synchronized"),
	CRM_CUSTOMER_POOL_DISTRIBUTE_ERROR(2060, "No right to assign"),
	CRM_CUSTOMER_POOL_RECEIVE_ERROR(2061, "The number of customers to receive exceeds the limit"),
	CRM_CUSTOMER_POOL_RECEIVE_NUMBER_ERROR(2062, "Today's collection times exceeded the upper limit"),
	CRM_CUSTOMER_POOL_PRE_USER_RECEIVE_ERROR(2063, "The former person in charge can't receive it within the time limit"),
	CRM_DATA_DELETED(2064, "%s has been deleted"),
	CRM_CUSTOMER_POOL_REMIND_ERROR(2065, "The high seas rules for the reminder to enter the high seas are not enabled"),
	CRM_CONTRACT_EXPIRATION_REMIND_ERROR(2066, "Contract expiration reminder is not enabled"),
	CRM_RETURN_VISIT_REMIND_ERROR(2067, "Return visit reminder is not enabled"),
	CRM_INVOICE_EXAMINE_PASS_ERROR(2068, "The passed invoice cannot be edited"),
	CRM_POOL_FIELD_HIDE_ERROR(2069, "Show at least 2 columns"),
	CRM_POOL_TRANSFER_ERROR(2070, "Cannot transfer customers to deactivated high seas"),
	CRM_CALL_DATA_UPDATE_ERROR(2071, "Edit operation is not supported yet!"),
	CRM_CALL_DATA_QUERY_ERROR(2072, "%s is incorrect!"),
	CRM_CALL_UPLOAD_ERROR(2073, "File upload failed!"),
	CRM_CALL_DOWNLOAD_ERROR(2075, "No recording file!"),
	CAN_ONLY_DELETE_WITHDRAWN_AND_SUBMITTED_EXAMINE(2076, "Only withdraw and unsubmitted approvals can be deleted!"),
	CRM_SYNC_FAILED(2077, "Sync failed: %s!"),
	CRM_ONLY_SYNC_DATA_FOR_WHICH_YOU_ARE_RESPONSIBLE(2078, "Only synchronize the data that I am responsible for!"),
	CRM_FIELD_ALREADY_EXISTS(2079, "%s already exists!"),
	SYSTEM_RELATED_FIELDS_CANNOT_BE_HIDDEN(2080, "System association fields cannot be hidden!"),
	REQUIRED_OPTIONS_CANNOT_BE_HIDDEN(2081, "Required options cannot be hidden!"),
	INDEX_CREATE_FAILED(2082, "%s index creation failed, data initialization is abnormal!"),
	CRM_CONTACTS_DATA_ERROR(2083, "Detected that there is no binding customer, please confirm!"),
	CRM_CONTRACT_EXAMINE_PASS_HINT_ERROR(2085, "Contracts that have been passed need to be cancelled before they can be edited!"),
	CRM_ACTIVITY_FORM_NONENTITY_ERROR(2086, "Activity form no longer exists!"),
	THE_FIELD_NAME_OF_THE_FORM_CANNOT_BE_REPEATED(2087, "Custom form field name cannot be repeated"),
	THE_FIELD_NUM_RESTRICT_ERROR(2088, "The numerical value of the custom form limitformat error"),
	THE_FIELD_DETAIL_TABLE_FORMAT_ERROR(2089, "Clear the specific fields in the setting table!"),
	CRM_RECEIVABLES_PLAN_ADD_ERROR(2090, "Only contracts that have been approved or are under review can add a payment plan!"),
	CRM_CUSTOMER_POOL_NOT_IS_ADMIN(2091, "Can't operate without the authority of the high seas"),
	;


	private int code;
	private String msg;

	CrmCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}
}
