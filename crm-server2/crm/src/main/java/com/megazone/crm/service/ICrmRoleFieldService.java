package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmField;
import com.megazone.crm.entity.PO.CrmRoleField;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-22
 */
public interface ICrmRoleFieldService extends BaseService<CrmRoleField> {

	/**
	 * @param label
	 * @param authLevel
	 * @return data
	 */
	public List<CrmRoleField> queryUserFieldAuth(Integer label, Integer authLevel);

	/**
	 * @param label label
	 * @return data
	 */
	public List<String> queryNoAuthField(Integer label);

	/**
	 * @param roleId ID
	 * @param label
	 */
	public List<CrmRoleField> queryRoleField(Integer roleId, Integer label);

	/**
	 * @param crmEnum  crm
	 * @param list
	 * @param maskType 0  1  2
	 */
	public void replaceMaskFieldValue(CrmEnum crmEnum, List<? extends Map<String, Object>> list, Integer maskType);

	/**
	 * @param fields
	 */
	public void saveRoleField(List<CrmRoleField> fields);

	/**
	 * @param crmField
	 */
	public void saveRoleField(CrmField crmField);

	/**
	 * @param fieldId field
	 */
	public void deleteRoleField(Integer fieldId);

	/**
	 * @param type
	 * @param value
	 * @return
	 */
	public Object parseValue(Integer type, Object value);


}
