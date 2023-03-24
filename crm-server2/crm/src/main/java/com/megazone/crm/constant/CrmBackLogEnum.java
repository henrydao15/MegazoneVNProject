package com.megazone.crm.constant;

import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;

/**
 * @author
 */

public enum CrmBackLogEnum {
	/**
	 * 5 6 7 8 9 10 11  12
	 */
	TODAY_CUSTOMER(1),
	FOLLOW_LEADS(2),
	FOLLOW_CUSTOMER(3),
	TO_ENTER_CUSTOMER_POOL(4),
	CHECK_CONTRACT(5),
	CHECK_RECEIVABLES(6),
	REMIND_RECEIVABLES_PLAN(7),
	END_CONTRACT(8),
	REMIND_RETURN_VISIT_CONTRACT(9),
	CHECK_INVOICE(10),
	TODAY_LEADS(11),
	TODAY_BUSINESS(12),
	;

	private Integer type;

	CrmBackLogEnum(Integer type) {
		this.type = type;
	}

	public static CrmBackLogEnum parse(int type) {
		for (CrmBackLogEnum crmBackLogEnum : CrmBackLogEnum.values()) {
			if (crmBackLogEnum.getType() == type) {
				return crmBackLogEnum;
			}
		}
		throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
	}

	public Integer getType() {
		return type;
	}
}
