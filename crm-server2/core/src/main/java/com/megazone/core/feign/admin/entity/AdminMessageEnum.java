package com.megazone.core.feign.admin.entity;


public enum AdminMessageEnum {

	NULL(0, 0, "NULL"),
	OA_TASK_ALLOCATION(1, 1, "task assigned to me"),
	OA_TASK_JOIN(2, 1, "The task I am participating in"),
	OA_TASK_OVER(3, 1, "Task end notification"),
	OA_LOG_REPLY(4, 2, "Log reply reminder"),
	OA_LOG_SEND(5, 2, "Log sending reminder"),
	OA_EXAMINE_REJECT(6, 3, "OA approval rejection reminder"),
	OA_EXAMINE_PASS(7, 3, "OA approval pass reminder"),
	OA_NOTICE_MESSAGE(8, 4, "Announcement notification reminder"),
	OA_EVENT_MESSAGE(9, 5, "Schedule Notification"),
	CRM_CONTRACT_REJECT(10, 6, "Contract Rejection Notice"),
	CRM_CONTRACT_PASS(11, 6, "Contract all passed notification"),
	CRM_RECEIVABLES_REJECT(12, 6, "Notice of Refusal of Payment"),
	CRM_RECEIVABLES_PASS(13, 6, "Collection pass notification"),
	CRM_CUSTOMER_IMPORT(14, 6, "Import Customer Notification"),
	CRM_CUSTOMER_IMPORT_CANCEL(15, 6, "Import Customer Cancellation Notice"),
	CRM_CONTACTS_IMPORT(16, 6, "Import Contact Notification"),
	CRM_CONTACTS_IMPORT_CANCEL(17, 6, "Import Contact Cancellation Notification"),
	CRM_LEADS_IMPORT(18, 6, "Import Lead Notification"),
	CRM_LEADS_IMPORT_CANCEL(19, 6, "Import lead cancellation notice"),
	CRM_PRODUCT_IMPORT(20, 6, "Import product notification"),
	CRM_PRODUCT_IMPORT_CANCEL(21, 6, "Import product cancellation notice"),
	CRM_BUSINESS_USER(22, 6, "Business Opportunity Team Member Notification"),
	CRM_CUSTOMER_USER(23, 6, "Customer team member notification"),
	CRM_CONTRACT_USER(24, 6, "Contract team member notification"),
	OA_EXAMINE_NOTICE(25, 3, "OA pending approval reminder"),
	CRM_CONTRACT_EXAMINE(26, 6, "Contract pending approval reminder"),
	CRM_RECEIVABLES_EXAMINE(27, 6, "Reminder for Receipt of Receipt for Review and Approval"),
	CRM_BUSINESS_TEAM_EXIT(28, 6, "Opportunity team member exit reminder"),
	CRM_CUSTOMER_TEAM_EXIT(29, 6, "Customer team member exit reminder"),
	CRM_CONTRACT_TEAM_EXIT(30, 6, "Contract team member exit reminder"),
	CRM_BUSINESS_REMOVE_TEAM(31, 6, "Remove Opportunity Team Member Reminder"),
	CRM_CUSTOMER_REMOVE_TEAM(32, 6, "Remove Account Team Member Reminder"),
	CRM_CONTRACT_REMOVE_TEAM(33, 6, "Remove Contract Team Member Reminder"),
	OA_COMMENT_REPLY(34, 2, "Comment reply reminder"),
	CRM_INVOICE_REJECT(35, 6, "Invoice Rejection Notice"),
	CRM_INVOICE_PASS(36, 6, "Invoice Pass Notification"),
	CRM_INVOICE_EXAMINE(37, 6, "Invoice pending approval reminder"),

	KM_DOCUMENT_SHARE_OPEN(41, 7, "Knowledge base document open sharing"),
	KM_DOCUMENT_SHARE_CLOSE(42, 7, "Knowledge base document close sharing"),
	//HR
	HRM_EMPLOYEE_IMPORT(50, 8, "HR Employee Import Notification"),
	HRM_SEND_SLIP(80, 8, "Human Resources send salary slip notice"),
	HRM_FIX_SALARY_IMPORT(81, 8, "Notice of Salary Import of Human Resources"),
	HRM_CHANGE_SALARY_IMPORT(82, 8, "Notice of Human Resources Import Salary Adjustment"),
	HRM_WRITE_ARCHIVES(83, 8, "Invitation to fill in file information"),
	HRM_EMPLOYEE_SALARY_PASS(84, 8, "Human Salary Pass Notification"),
	HRM_EMPLOYEE_SALARY_REJECT(85, 8, "Human Salary Rejection Notice"),
	HRM_EMPLOYEE_SALARY_EXAMINE(86, 8, "Reminder of Human Resources Salary Pending Review"),
	HRM_EMPLOYEE_APPRAISAL_WRITE(87, 8, "Notice of Human Resources Employee Performance Target To Be Filled"),
	HRM_EMPLOYEE_APPRAISAL_CONFIRMATION(88, 8, "Human resources employee performance target pending confirmation notice"),
	HRM_EMPLOYEE_APPRAISAL_ASSESSED(89, 8, "Human Resources Employee Performance Target Results Pending Evaluation Notice"),
	HRM_EMPLOYEE_APPRAISAL_CONFIRMED(90, 8, "Human resources employee performance target result pending confirmation notice"),
	HRM_EMPLOYEE_APPRAISAL_COMPLETE(91, 8, "Notice that HR employee performance appraisal has been completed"),
	HRM_EMPLOYEE_APPRAISAL_WRITE_REJECT(92, 8, "HR Employee Performance Target Rejection Notice"),
	HRM_EMPLOYEE_APPRAISAL_ASSESSED_REJECT(93, 8, "HR Employee Performance Evaluation Rejection Notice"),
	HRM_APPRAISAL_WRITE_COMPLETE(94, 8, "Notice that HR employee performance has been completed"),
	HRM_APPRAISAL_ASSESSED_COMPLETE(95, 8, "Notice that the performance of HR staff has been fully completed"),
	HRM_APPRAISAL_ARCHIVE(96, 8, "Notice of timely archiving of HR employee performance"),
	HRM_EMPLOYEE_OPEN(97, 8, "HR Employee Open Notification"),
	HRM_EMPLOYEE_INSURANCE(98, 8, "Human Resources Employee Social Security Notice"),
	HRM_INTERVIEW_ARRANGEMENTS(99, 8, "Human Resources Interviewer Interview Arrangement Notice"),
	//JXC
	JXC_PURCHASE_EXAMINE(53, 9, "Purchase order pending approval reminder"),
	JXC_PURCHASE_REJECT(54, 9, "Purchase Order Rejection Notice"),
	JXC_PURCHASE_PASS(55, 9, "Purchase Order Pass Notification"),
	JXC_RETREAT_EXAMINE(56, 9, "Purchase return order pending review and approval reminder"),
	JXC_RETREAT_REJECT(57, 9, "Purchase Return Rejection Notice"),
	JXC_RETREAT_PASS(58, 9, "Purchase Return Order Pass Notification"),
	JXC_SALE_EXAMINE(59, 9, "Sales order pending approval reminder"),
	JXC_SALE_REJECT(60, 9, "Sales Order Rejection Notice"),
	JXC_SALE_PASS(61, 9, "Sales Order Pass Notification"),
	JXC_SALE_RETURN_EXAMINE(62, 9, "Sales return order pending review and approval reminder"),
	JXC_SALE_RETURN_REJECT(63, 9, "Notice of rejection of sales return order"),
	JXC_SALE_RETURN_PASS(64, 9, "Sales Return Order Pass Notification"),
	JXC_PAYMENT_EXAMINE(65, 9, "Payment order pending approval reminder"),
	JXC_PAYMENT_REJECT(66, 9, "Notice of Rejection of Payment Order Pending Review"),
	JXC_PAYMENT_PASS(67, 9, "Notice of pending approval of payment order"),
	JXC_COLLECTION_EXAMINE(68, 9, "Reminder of payment receipt pending review and approval"),
	JXC_COLLECTION_REJECT(69, 9, "Notice of Rejection of Payment Receipt"),
	JXC_COLLECTION_PASS(70, 9, "Collection receipt pass notification"),
	JXC_INVENTORY_EXAMINE(71, 9, "Reminder for inventory pending review and approval"),
	JXC_INVENTORY_REJECT(72, 9, "Inventory rejection notice"),
	JXC_INVENTORY_PASS(73, 9, "Notice of passing inventory"),
	JXC_ALLOCATION_EXAMINE(74, 9, "Allocation pending review and approval reminder"),
	JXC_ALLOCATION_REJECT(75, 9, "Allocation Rejection Notification"),
	JXC_ALLOCATION_PASS(76, 9, "Allocation pass notification"),
	OA_LOG_FAVOUR(77, 2, "Log Like Notification"),
	CRM_CONTACTS_USER(120, 6, "Contact team member notification"),
	CRM_RECEIVABLES_USER(121, 6, "Collection team member notification"),
	CRM_CONTACTS_TEAM_EXIT(122, 6, "Contact team member exit reminder"),
	CRM_RECEIVABLES_TEAM_EXIT(123, 6, "Reminder of withdrawal of payment team members"),
	CRM_CONTACTS_REMOVE_TEAM(124, 6, "Remove Contact Team Member Reminder"),
	CRM_RECEIVABLES_REMOVE_TEAM(125, 6, "Reminder to remove payment team members"),
	;

	private final int type;
	private final int label;
	private final String remarks;

	AdminMessageEnum(Integer type, Integer label, String remarks) {
		this.type = type;
		this.label = label;
		this.remarks = remarks;
	}

	public static AdminMessageEnum parse(int type) {
		for (AdminMessageEnum Enum : AdminMessageEnum.values()) {
			if (Enum.getType() == type) {
				return Enum;
			}
		}
		return NULL;
	}

	public int getType() {
		return type;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getLabel() {
		return label;
	}
}
