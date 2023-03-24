package com.megazone.crm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.entity.PO.CrmContactsUserStar;
import com.megazone.crm.mapper.CrmContactsUserStarMapper;
import com.megazone.crm.service.ICrmContactsUserStarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrmContactsUserStarServiceImpl extends BaseServiceImpl<CrmContactsUserStarMapper, CrmContactsUserStar> implements ICrmContactsUserStarService {

	/**
	 * @param contactsId id
	 * @param userId     ID
	 * @return int
	 */
	@Override
	public Integer isStar(Object contactsId, Long userId) {
		return lambdaQuery().eq(CrmContactsUserStar::getUserId, userId).eq(CrmContactsUserStar::getContactsId, contactsId).last(" limit 1").count();
	}

	@Override
	public List<Integer> starList(Long userId) {
		return lambdaQuery().select(CrmContactsUserStar::getContactsId).eq(CrmContactsUserStar::getUserId, userId).list()
				.stream().map(CrmContactsUserStar::getContactsId).collect(Collectors.toList());
	}
}
