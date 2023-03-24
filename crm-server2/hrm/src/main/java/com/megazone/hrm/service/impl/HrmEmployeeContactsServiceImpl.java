package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.entity.PO.HrmEmployeeContacts;
import com.megazone.hrm.mapper.HrmEmployeeContactsMapper;
import com.megazone.hrm.service.IHrmEmployeeContactsService;
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
public class HrmEmployeeContactsServiceImpl extends BaseServiceImpl<HrmEmployeeContactsMapper, HrmEmployeeContacts> implements IHrmEmployeeContactsService {

	@Override
	public Integer verifyUnique(String fieldName, String value, Integer contactsId) {
		return getBaseMapper().verifyUnique(fieldName, value, contactsId);
	}
}
