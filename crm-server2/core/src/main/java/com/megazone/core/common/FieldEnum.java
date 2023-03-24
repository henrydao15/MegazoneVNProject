package com.megazone.core.common;

import lombok.Getter;

public enum FieldEnum {

	/**
	 * Field Type 1 Single Line Text 2 Multi Line Text 3 Single Choice 4 Date 5 Number 6 Decimal 7 Mobile Phone 8 File 9 Multiple Choice 10 Person
	 * 11 Attachments 12 Departments 13 Dates 14 Emails 15 Customers 16 Opportunities 17 Contacts 18 Maps 19 Product Types 20 Contracts 21 Payment Plans 29 Pictures
	 */
	TEXT(1, "text", "Single line text"),
	TEXTAREA(2, "textarea", "multiline text"),
	SELECT(3, "select", "single selection"),
	DATE(4, "date", "date"),
	NUMBER(5, "number", "number"),
	FLOATNUMBER(6, "floatnumber", "decimal"),
	MOBILE(7, "mobile", "mobile"),
	FILE(8, "file", "file"),
	CHECKBOX(9, "checkbox", "multiple choice"),
	USER(10, "user", "person"),
	ATTACHMENT(11, "attachment", "attachment"),
	STRUCTURE(12, "structure", "department"),
	DATETIME(13, "datetime", "datetime"),
	EMAIL(14, "email", "Mail"),
	CUSTOMER(15, "customer", "customer"),
	BUSINESS(16, "business", "business opportunity"),
	CONTACTS(17, "contacts", "contacts"),
	MAP_ADDRESS(18, "map_address", "map"),
	CATEGORY(19, "category", "Product Type"),
	CONTRACT(20, "contract", "Contract"),
	RECEIVABLES_PLAN(21, "receivables_plan", "Receivables Plan"),
	BUSINESS_CAUSE(22, "business_cause", "Business Opportunity Business"),
	EXAMINE_CAUSE(23, "examine_cause", "Approval business"),
	ADDRESS(24, "address", "address"),
	WEBSITE(25, "website", "URL"),
	RETURN_VISIT(26, "return_visit", "Return visit"),
	RETURN_VISIT_CONTRACT(27, "return_visit_contract", "Return visit contract"),
	SINGLE_USER(28, "single_user", "single person"),
	/**
	 * Invoicing
	 */
	PIC(29, "pic", "picture"),
	SUPPLIER_CAUSE(30, "supplier_cause", "supplier"),
	PURCHASE_CAUSE(31, "purchase_cause", "Purchase Order"),
	SALE_CAUSE(32, "sale_cause", "Sales Order"),
	WAREHOUSE_CAUSE(33, "warehouse_cause", "Warehouse"),
	RELATED_ID(34, "related_id", "related object"),
	COLLECTION_OBJECT(35, "collection_object", "collection"),
	RETREAT_CAUSE(36, "retreat_cause", "Purchase Return Order"),
	SALE_RETURN_CAUSE(37, "sale_return_cause", "Sales Return"),
	STATE_CAUSE(39, "state_cause", "state flag"),
	/**
	 * HR
	 */
	AREA(40, "area", "province and city"),

	BOOLEAN_VALUE(41, "boolean_value", "Boolean value"),
	PERCENT(42, "percent", "percent"),
	AREA_POSITION(43, "position", "address"),
	CURRENT_POSITION(44, "location", "location"),
	DETAIL_TABLE(45, "detail_table", "detail table"),
	HANDWRITING_SIGN(46, "handwriting_sign", "Handwriting signature"),
	DATE_INTERVAL(48, "date_interval", "date interval"),
	OPTIONS_TYPE(49, "options_type", "Option field: logical form, batch editing, other"),
	DESC_TEXT(50, "desc_text", "description text"),
	CALCULATION_FUNCTION(51, "calculation_function", "Calculation function"),
	RELATE_CAUSE(52, "relate_cause", "Related business"),
	QUOTE_TYPE(53, "quote_type", "quote field"),
	CITY(54, "city", "province"),
	RECRUIT_CHANNEL(55, "recruit_channel", "recruitment channel"),
	;

	@Getter
	private Integer type;

	@Getter
	private String formType;

	@Getter
	private String desc;

	FieldEnum() {
	}

	FieldEnum(Integer type, String formType, String desc) {
		this.type = type;
		this.formType = formType;
		this.desc = desc;
	}


	public static FieldEnum parse(Integer type) {
		for (FieldEnum fieldTypeEnum : FieldEnum.values()) {
			if (fieldTypeEnum.getType().equals(type)) {
				return fieldTypeEnum;
			}
		}
		return TEXT;
	}

	public static FieldEnum parse(String formType) {
		for (FieldEnum fieldTypeEnum : FieldEnum.values()) {
			if (fieldTypeEnum.getFormType().equals(formType)) {
				return fieldTypeEnum;
			}
		}
		return TEXT;
	}
}
