package com.megazone.core.common;

import lombok.Getter;

@Getter
public enum SubModelType {
	NULL(0, ""),
	ADMIN_COMPANY_HOME(1, "Enterprise Home"),
	ADMIN_APPLICATION_MANAGEMENT(2, "Application Management"),
	ADMIN_STAFF_MANAGEMENT(3, "Employee Management"),
	ADMIN_DEPARTMENT_MANAGEMENT(4, "Department Management"),
	ADMIN_ROLE_PERMISSIONS(5, "Role permissions"),
	ADMIN_PROJECT_MANAGEMENT(6, "Project Management"),
	ADMIN_CUSTOMER_MANAGEMENT(7, "Customer Management"),
	ADMIN_HUMAN_RESOURCE_MANAGEMENT(8, "Human Resource Management"),
	ADMIN_INVOICING_MANAGEMENT(9, "Invoicing management"),
	ADMIN_OTHER_SETTINGS(10, "Other Settings"),
	//crm
	CRM_LEADS(21, "Leads"),
	CRM_CUSTOMER(22, "Customer"),
	CRM_CONTACTS(23, "Contact"),
	CRM_BUSINESS(24, "Business Opportunity"),
	CRM_CONTRACT(25, "Contract"),
	CRM_RECEIVABLES(26, "Collection"),
	CRM_INVOICE(27, "Invoice"),
	CRM_RETURN_VISIT(28, "Return visit"),
	CRM_PRODUCT(29, "Product"),
	CRM_MARKETING(30, "Marketing Activities"),
	//oa
	OA_CALENDAR(41, "Calendar"),
	OA_LOG(42, "Log"),
	//work
	WORK_PROJECT(51, "Project"),
	WORK_TASK(52, "Task"),
	//hrm
	HRM_DEPT(61, "Organization Management"),
	HRM_RECRUITMENT(62, "Recruitment position"),
	HRM_CANDIDATE(63, "Candidate"),
	HRM_EMPLOYEE(64, "Employee Management"),
	HRM_SOCIAL_SECURITY(65, "Social Security Management"),
	HRM_SALARY(66, "Payroll Management"),
	HRM_SALARY_FILE(67, "salary file"),
	HRM_SALARY_SLIP(68, "Salary Slip"),
	HRM_APPRAISAL(69, "Performance Appraisal"),
	//jxc
	JXC_SUPPLIER(81, "Supplier"),
	JXC_PURCHASE_ORDER(82, "Purchase Order"),
	JXC_PURCHASE_RETURN(83, "Purchase Return"),
	JXC_PRODUCT_MANAGEMENT(84, "Product Management"),
	JXC_SALES_ORDER(85, "Sales Order"),
	JXC_SALES_RETURN(86, "Sales Return"),
	JXC_WAREHOUSE_MANAGEMENT(87, "Warehouse Management"),
	JXC_PRODUCT_INVENTORY(88, "Product Inventory"),
	JXC_PRODUCT_IN_STOCK(89, "Product storage"),
	JXC_PRODUCT_OUT_STOCK(90, "Product out of stock"),
	JXC_STOCK_TRANSFER(91, "Inventory Transfer"),
	JXC_INVENTORY_CHECK(92, "Inventory count"),
	JXC_RECEIVABLES(93, "Collection"),
	JXC_PAYMENT(94, "Payment"),
	;


	private Integer label;
	private String name;

	SubModelType(Integer label, String name) {
		this.label = label;
		this.name = name;
	}

	public static String valueOfName(Integer label) {
		for (SubModelType modelType : values()) {
			if (label.equals(modelType.getLabel())) {
				return modelType.getName();
			}
		}
		return "";
	}
}
