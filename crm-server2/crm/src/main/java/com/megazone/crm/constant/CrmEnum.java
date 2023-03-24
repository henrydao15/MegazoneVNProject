package com.megazone.crm.constant;

import cn.hutool.core.util.StrUtil;

/**
 * @author crm module enumeration
 */

public enum CrmEnum {
	/**
	 * clue
	 */
	LEADS(1, "Leads"),
	/**
	 * client
	 */
	CUSTOMER(2, "Customer"),
	/**
	 * contact
	 */
	CONTACTS(3, "Contact"),
	/**
	 * product
	 */
	PRODUCT(4, "Product"),
	/**
	 * Business
	 */
	BUSINESS(5, "Business Opportunity"),
	/**
	 * contract
	 */
	CONTRACT(6, "Contract"),
	/**
	 * Repayment
	 */
	RECEIVABLES(7, "Refund"),
	/**
	 * Repayment plan
	 */
	RECEIVABLES_PLAN(8, "Repayment Plan"),
	/**
	 * High seas
	 */
	CUSTOMER_POOL(9, "High Seas"),
	/**
	 * marketing activity
	 */
	MARKETING(10, "Marketing Activity"),
	/**
	 * return visit
	 */
	RETURN_VISIT(17, "Customer return visit"),
	/**
	 * bill
	 */
	INVOICE(18, "Invoice"),

	/**
	 * NULL
	 */
	NULL(0, "");

	private Integer type;
	private String remarks;

	CrmEnum(Integer type, String remarks) {
		this.type = type;
		this.remarks = remarks;
	}

	public static CrmEnum parse(Integer type) {
		for (CrmEnum crmEnum : CrmEnum.values()) {
			if (crmEnum.getType().equals(type)) {
				return crmEnum;
			}
		}
		return NULL;
	}

	public static CrmEnum parse(String name) {
		for (CrmEnum crmEnum : CrmEnum.values()) {
			if (crmEnum.name().equals(name)) {
				return crmEnum;
			}
		}
		return NULL;
	}

	public String getRemarks() {
		return remarks;
	}

	public Integer getType() {
		return type;
	}

	public String getIndex() {
		return "wk_single_" + name().toLowerCase();
	}

	public String getTableName() {
		return name().toLowerCase();
	}

	/**
	 * Get the primary key ID
	 * Whether @param camelCase camelCase
	 *
	 * @return primaryKey
	 */
	public String getPrimaryKey(boolean camelCase) {
		String name;
		if (this == CrmEnum.RETURN_VISIT) {
			name = "visit";
		} else {
			name = name().toLowerCase();
		}
		if (camelCase) {
			return StrUtil.toCamelCase(name) + "Id";
		}
		return name + "_id";
	}

	public String getPrimaryKey() {
		return getPrimaryKey(true);
	}

	@Override
	public String toString() {
		return this.type.toString();
	}
}
