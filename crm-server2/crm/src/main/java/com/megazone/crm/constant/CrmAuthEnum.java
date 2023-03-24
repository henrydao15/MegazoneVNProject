package com.megazone.crm.constant;


/**
 * crm
 */
public enum CrmAuthEnum {

	/**
	 *
	 */
	ADD(1),
	/**
	 *
	 */
	EDIT(2),
	/**
	 *
	 */
	LIST(3),
	/**
	 *
	 */
	READ(4),
	/**
	 *
	 */
	DELETE(5),
	;


	private int value;

	private CrmAuthEnum(int value) {
		this.value = value;
	}

	/**
	 * 9
	 * 17  18  19  20  23
	 * 10
	 * 26  27  28  29  32
	 * 11
	 * 40  41  42  43  44
	 * 12
	 * 46  47  48  49  50
	 * 13
	 * 53  54  55  56  57
	 * 14
	 * 60  61  62  63  64
	 * 15
	 * 65  66  67  68  211
	 * 440
	 * 442  443  441  441  444
	 * 936
	 * 937  938  939  940  941
	 *
	 * @param crmEnum label
	 * @return menuId
	 */
	public Integer getMenuId(CrmEnum crmEnum) {
		/*

		 */
		if (crmEnum == null) {
			switch (this) {
				case ADD:
					return 442;
				case EDIT:
					return 443;
				case LIST:
				case READ:
					return 441;
				case DELETE:
					return 444;
			}
		}
		int start = 0;
		switch (crmEnum) {
			case LEADS:
				start = 16;
			case CUSTOMER: {
				if (crmEnum == CrmEnum.CUSTOMER) {
					start = 25;
				}
				if (this == DELETE) {
					return start + value + 2;
				} else {
					return start + value;
				}
			}
			case CONTACTS:
				start = 39;
			case PRODUCT:
				if (CrmEnum.PRODUCT == crmEnum) {
					start = 64;
				}
			case BUSINESS:
				if (CrmEnum.BUSINESS == crmEnum) {
					start = 45;
				}
			case CONTRACT:
				if (CrmEnum.CONTRACT == crmEnum) {
					start = 52;
				}
			case RECEIVABLES:
				if (CrmEnum.RECEIVABLES == crmEnum) {
					start = 59;
				}
				if (CrmEnum.PRODUCT == crmEnum && this == DELETE) {
					return 211;
				}
			case INVOICE:
				if (CrmEnum.INVOICE == crmEnum) {
					start = 420;
				}
			case RECEIVABLES_PLAN:
				if (CrmEnum.RECEIVABLES_PLAN == crmEnum) {
					start = 936;
				}
				return start + value;
		}
		return 0;
	}
}
