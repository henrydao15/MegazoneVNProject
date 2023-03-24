package com.megazone.hrm.constant;

public enum QuitReasonEnum {
	/**
	 * Reason for leaving
	 * Voluntary resignation 1 Family reasons 2 Physical reasons 3 Salary reasons 4 Traffic inconvenience 5 Work pressure 6 Management problems 7 No promotion opportunities 8 Career planning 9 Contract expires and give up renewal 10 Other personal reasons
	 * Passive resignation 11 Dismissal during probationary period 12 Violation of company regulations 13 Organizational adjustment/layoff 14 Dismissal for failure to meet performance standards 15 Non-renewal of contract upon expiration 16 Passive resignation for other reasons
	 */
	FAMILY(QuitTypeEnum.INITIATIVE, 1, "Family Reasons"), HEALTH(QuitTypeEnum.INITIATIVE, 2, "Physical Reasons"), SALARY(QuitTypeEnum.INITIATIVE, 3, "Salary Reasons"),
	INCONVENIENT_TRAFFIC(QuitTypeEnum.INITIATIVE, 4, "Inconvenient transportation"), WORKING_PRESSURE(QuitTypeEnum.INITIATIVE, 5, "Work pressure"), MANAGEMENT_ISSUES(QuitTypeEnum.INITIATIVE, 6, "Management problems"),
	NO_PROMOTION_OPPORTUNITIES(QuitTypeEnum.INITIATIVE, 7, "No Promotion Opportunity"), CAREER_PLANNING(QuitTypeEnum.INITIATIVE, 8, "Career Planning"), GIVE_UP_RENEWAL(QuitTypeEnum.INITIATIVE, 9, "Contract Expiration Give Up Renewal"),
	OTHER_PERSONAL_REASONS(QuitTypeEnum.INITIATIVE, 10, "Other personal reasons"),
	DISMISSAL_OF_TRIAL_PERIOD(QuitTypeEnum.PASSIVE, 11, "Dismissal during the use period"), VIOLATION_OF_COMPANY_REGULATIONS(QuitTypeEnum.PASSIVE, 12, "Violation of company regulations"), LAYOFFS(QuitTypeEnum.PASSIVE, 13, "Organization adjustment/layoff"),
	UNDERPERFORMANCE(QuitTypeEnum.PASSIVE, 14, "Not up to standard dismissal"), THE_CONTRACT_EXPIRES_WITHOUT_RENEWAL(QuitTypeEnum.PASSIVE, 15, "Not renewing the contract upon expiration"), OTHER_REASONS(QuitTypeEnum.PASSIVE, 16, "Other reasons");

	private QuitTypeEnum quitType;
	private String name;
	private int value;

	QuitReasonEnum(QuitTypeEnum quitType, int value, String name) {
		this.quitType = quitType;
		this.value = value;
		this.name = name;
	}

	public static QuitReasonEnum parse(int type) {
		for (QuitReasonEnum value : QuitReasonEnum.values()) {
			if (value.value == type) {
				return value;
			}
		}
		return FAMILY;
	}

	public static String parseName(int type) {
		for (QuitReasonEnum value : QuitReasonEnum.values()) {
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

	public QuitTypeEnum getQuitType() {
		return quitType;
	}
}
