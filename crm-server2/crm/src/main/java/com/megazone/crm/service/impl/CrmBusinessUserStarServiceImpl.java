package com.megazone.crm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.entity.PO.CrmBusinessUserStar;
import com.megazone.crm.mapper.CrmBusinessUserStarMapper;
import com.megazone.crm.service.ICrmBusinessUserStarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrmBusinessUserStarServiceImpl extends BaseServiceImpl<CrmBusinessUserStarMapper, CrmBusinessUserStar> implements ICrmBusinessUserStarService {
	@Override
	public Integer isStar(Object businessId, Long userId) {
		return lambdaQuery().eq(CrmBusinessUserStar::getUserId, userId).eq(CrmBusinessUserStar::getBusinessId, businessId).last(" limit 1").count();
	}

	@Override
	public List<Integer> starList(Long userId) {
		return lambdaQuery().select(CrmBusinessUserStar::getBusinessId).eq(CrmBusinessUserStar::getUserId, userId).list()
				.stream().map(CrmBusinessUserStar::getBusinessId).collect(Collectors.toList());
	}
}
