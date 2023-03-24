package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmCustomerSetting;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-29
 */
public interface ICrmCustomerSettingService extends BaseService<CrmCustomerSetting> {
	/**
	 * @param type   1  2
	 * @param userId ID
	 * @param offset 1
	 * @return
	 */
	public boolean queryCustomerSettingNum(int type, Long userId, int offset);

	/**
	 * @param type   1  2
	 * @param userId ID
	 * @return
	 */
	public boolean queryCustomerSettingNum(int type, Long userId);
}
