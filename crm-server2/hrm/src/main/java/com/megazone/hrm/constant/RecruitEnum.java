package com.megazone.hrm.constant;

/**
 * Recruitment enumeration class
 */
public class RecruitEnum {

	public enum RecruitPostWorkTime {
		/**
		 * Job duration
		 */
		UNLIMITED(1, "unlimited"), WITHIN_A_YEAR(2, "1 year or less"), ONE_TO_THREE_YEAR(3, "1-3 years"),
		THREE_TO_FIVE_YEAR(4, "3-5 years"), FIVE_TO_TEN_YEAR(5, "5-10 years"), MORE_THAN_TEN_YEAR(6, "10 years or more");

		private String name;
		private int value;

		RecruitPostWorkTime(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public static String parseName(int type) {
			for (RecruitPostWorkTime value : RecruitPostWorkTime.values()) {
				if (value.value == type) {
					return value.name;
				}
			}
			return "";
		}

		public String getName() {
			return name;
		}

		public int getValue() {
			return value;
		}
	}

	public enum RecruitPostEmergencyLevel {
		/**
		 * Job urgency
		 */
		URGENT(1, "Urgent"), GENERAL(2, "General");

		private String name;
		private int value;

		RecruitPostEmergencyLevel(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public static String parseName(int type) {
			for (RecruitPostEmergencyLevel value : RecruitPostEmergencyLevel.values()) {
				if (value.value == type) {
					return value.name;
				}
			}
			return "";
		}

		public String getName() {
			return name;
		}

		public int getValue() {
			return value;
		}
	}

	//Interview 1 Interview not completed 2 Interview passed 3 Interview failed 4 Interview cancelled

	public enum RecruitInterviewResult {
		/**
		 * Job urgency
		 */
		UNFINISHED(1, "Interview not completed"), PASS(2, "Interview passed"), NOT_PASS(3, "Interview failed"), CANCEL(4, "Interview cancelled");

		private String name;
		private int value;

		RecruitInterviewResult(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public static String parseName(int type) {
			for (RecruitInterviewResult value : RecruitInterviewResult.values()) {
				if (value.value == type) {
					return value.name;
				}
			}
			return "";
		}

		public String getName() {
			return name;
		}

		public int getValue() {
			return value;
		}
	}
}
