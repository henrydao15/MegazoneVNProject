package com.megazone.hrm.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CandidateStatusEnum {
	/**
	 * Candidate status 1 New candidate 2 Passed the primary election 3 Arranged the interview 4 Passed the interview 5 Offered 6 Pending entry 7 Eliminated 8 Enrolled
	 */
	NEW_CANDIDATE(1, "New candidate"), PRIMARY_ELECTION(2, "Primary passed"),
	ARRANGE_AN_INTERVIEW(3, "Arrange interview"), PASS_THE_INTERVIEW(4, "Interview passed"),
	OFFER_HAS_BEEN_SENT(5, "Offer has been sent"), PENDING_ENTRY(6, "Pending entry"),
	OBSOLETE(7, "eliminated"), HAVE_JOINED(8, "joined"), CANCEL_INTERVIEW(9, "interview canceled");

	private int value;
	private String name;

	CandidateStatusEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static String parseName(int type) {
		for (CandidateStatusEnum value : CandidateStatusEnum.values()) {
			if (value.value == type) {
				return value.name;
			}
		}
		return "";
	}

	public static int valueOfType(String name) {
		for (CandidateStatusEnum value : CandidateStatusEnum.values()) {
			if (value.name.equals(name)) {
				return value.value;
			}
		}
		return -1;
	}


	public static List<Map<String, Object>> parseMap() {
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (CandidateStatusEnum value : CandidateStatusEnum.values()) {
			Map<String, Object> map = new HashMap<>();
			map.put("name", value.name);
			map.put("value", value.value);
			mapList.add(map);
		}
		return mapList;
	}


	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
