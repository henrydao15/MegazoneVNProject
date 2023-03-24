package com.megazone.core.common;

import cn.hutool.core.util.StrUtil;

import java.util.Objects;

/**
 * Invoicing module enumeration
 */
public enum JxcEnum {

	PRODUCT(1, "Product"),
	SUPPLIER(2, "Supplier"),
	PURCHASE(3, "Purchase Order"),
	RETREAT(4, "Purchase Return Order"),
	SALE(5, "Sales Order"),
	SALE_RETURN(6, "Sales Return"),
	RECEIPT(7, "Receipt slip"),
	OUTBOUND(8, "Outbound order"),
	PAYMENT(9, "Payment slip"),
	COLLECTION(10, "Cash receipt"),
	INVENTORY(11, "Inventory"),
	ALLOCATION(12, "Allocation"),
	DETAILED(13, "In/Out Warehouse Details"),
	INVENTORY_RECEIPT(14, "Inventory Inventory"),
	NULL(0, "NULL");

	private final int type;

	private final String remarks;

	JxcEnum(int type, String remarks) {
		this.type = type;
		this.remarks = remarks;
	}

	public static JxcEnum parse(Integer type) {
		for (JxcEnum jxcEnum : JxcEnum.values()) {
			if (Objects.equals(jxcEnum.getType(), type)) {
				return jxcEnum;
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

	public String getIndex() {
		return "jxc_" + name().toLowerCase();
	}

	public String getTable() {
		return name().toLowerCase();
	}

	public String getTableId() {
		return StrUtil.toCamelCase(name().toLowerCase()) + "Id";
	}
}
