package com.megazone.crm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.entity.PO.CrmCustomerUserStar;
import com.megazone.crm.mapper.CrmCustomerUserStarMapper;
import com.megazone.crm.service.ICrmCustomerUserStarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrmCustomerUserStarServiceImpl extends BaseServiceImpl<CrmCustomerUserStarMapper, CrmCustomerUserStar> implements ICrmCustomerUserStarService {
	@Override
	public Integer isStar(Object customerId, Long userId) {
		return lambdaQuery().eq(CrmCustomerUserStar::getUserId, userId).eq(CrmCustomerUserStar::getCustomerId, customerId).last(" limit 1").count();
	}

	@Override
	public List<Integer> starList(Long userId) {
		return lambdaQuery().select(CrmCustomerUserStar::getCustomerId).eq(CrmCustomerUserStar::getUserId, userId).list().stream().map(CrmCustomerUserStar::getCustomerId).collect(Collectors.toList());
	}
}
