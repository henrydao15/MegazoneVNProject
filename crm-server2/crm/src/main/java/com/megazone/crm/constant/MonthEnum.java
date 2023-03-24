package com.megazone.crm.constant;

/**
 * @author
 */

public enum MonthEnum {
	/**
	 * January
	 */
	JANUARY("january", "January", "01"),
	/**
	 * February
	 */
	FEBRUARY("february", "February", "02"),
	/**
	 * March
	 */
	MARCH("march", "March", "03"),
	/**
	 * April
	 */
	APRIL("april", "April", "04"),
	/**
	 * May
	 */
	MAY("may", "May", "05"),
	/**
	 * June
	 */
	JUNE("june", "June", "06"),
	/**
	 * July
	 */
	JULY("july", "July", "07"),
	/**
	 * August
	 */
	AUGUST("august", "August", "08"),
	/**
	 * September
	 */
	SEPTEMBER("september", "September", "09"),
	/**
	 * October
	 */
	OCTOBER("october", "October", "10"),
	/**
	 * November
	 */
	NOVEMBER("november", "November", "11"),
	/**
	 * December
	 */
	DECEMBER("december", "December", "12");

	private String name;
	private String remark;
	private String value;

	MonthEnum(String name, String remark, String value) {
		this.name = name;
		this.remark = remark;
		this.value = value;
	}

	public static String valueOf(int mouth) {
		String mouthe = mouth >= 10 ? (mouth + "") : ("0" + mouth);
		for (MonthEnum monthEnum : MonthEnum.values()) {
			if (monthEnum.getValue().equals(mouthe)) {
				return monthEnum.getName();
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public String getRemark() {
		return remark;
	}

	public String getValue() {
		return value;
	}

}
