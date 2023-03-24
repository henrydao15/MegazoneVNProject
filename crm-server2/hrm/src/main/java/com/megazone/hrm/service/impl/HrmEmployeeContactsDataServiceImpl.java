package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.entity.PO.HrmEmployeeContactsData;
import com.megazone.hrm.mapper.HrmEmployeeContactsDataMapper;
import com.megazone.hrm.service.IHrmEmployeeContactsDataService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Service
public class HrmEmployeeContactsDataServiceImpl extends BaseServiceImpl<HrmEmployeeContactsDataMapper, HrmEmployeeContactsData> implements IHrmEmployeeContactsDataService {

	@Override
	public Integer verifyUnique(Integer fieldId, String value, Integer id) {
		return getBaseMapper().verifyUnique(fieldId, value, id);
	}
}
