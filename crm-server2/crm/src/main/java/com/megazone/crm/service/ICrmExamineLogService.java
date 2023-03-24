package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmExamineLog;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
public interface ICrmExamineLogService extends BaseService<CrmExamineLog> {

	List<JSONObject> queryByRecordId(Integer recordId);
}
