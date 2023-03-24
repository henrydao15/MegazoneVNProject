package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmBusinessStatus;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
public interface ICrmBusinessStatusService extends BaseService<CrmBusinessStatus> {

	String getBusinessStatusName(int statusId);
}
