package com.megazone.hrm.constant;

import java.util.TreeMap;

public enum WeekEnum {
	/**
	 * Chinese and English correspondence on working days
	 */
	MONDAY(1, "monday", "one"),
	TUESDAY(2, "tuesday", "Tuesday"),
	WEDNESDAY(3, "wednesday", "three"),
	THURSDAY(4, "thursday", "four"),
	FRIDAY(5, "friday", "Friday"),
	SATURDAY(6, "saturday", "saturday"),
	SUNDAY(7, "sunday", "seven");


	private final Integer order;
	private final String date;
	private final String name;

	WeekEnum(Integer order, String date, String name) {
		this.order = order;
		this.date = date;
		this.name = name;
	}

	public static TreeMap<Integer, String> getMapName(String date) {
		TreeMap<Integer, String> treeMap = new TreeMap<>();
		for (WeekEnum value : WeekEnum.values()) {
			if (value.date.equals(date)) {
				treeMap.put(value.order, value.name);
				return treeMap;
			}
		}
		return null;
	}

	public static String getName(String date) {
		for (WeekEnum value : WeekEnum.values()) {
			if (value.date.equals(date)) {
				return value.name;
			}
		}
		return "";
	}

	public static String getDayOfWeek(Integer order) {
		for (WeekEnum value : WeekEnum.values()) {
			if (value.order.equals(order)) {
				return value.date;
			}
		}
		return null;
	}
}
