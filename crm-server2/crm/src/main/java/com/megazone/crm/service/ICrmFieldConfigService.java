package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmFieldConfig;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-19
 */
public interface ICrmFieldConfigService extends BaseService<CrmFieldConfig> {


	/**
	 * @param label
	 * @param fieldType
	 * @param existNameList
	 * @param isCreateField
	 * @return fieldName
	 */
	public String getNextFieldName(Integer label, Integer fieldType, List<String> existNameList, Integer depth, boolean isCreateField);
}
