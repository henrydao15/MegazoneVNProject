package com.megazone.crm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.entity.PO.CrmLeadsUserStar;
import com.megazone.crm.mapper.CrmLeadsUserStarMapper;
import com.megazone.crm.service.ICrmLeadsUserStarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrmLeadsUserStarServiceImpl extends BaseServiceImpl<CrmLeadsUserStarMapper, CrmLeadsUserStar> implements ICrmLeadsUserStarService {

	@Override
	public Integer isStar(Object leadsId, Long userId) {
		return lambdaQuery().eq(CrmLeadsUserStar::getUserId, userId).eq(CrmLeadsUserStar::getLeadsId, leadsId).last(" limit 1").count();
	}

	@Override
	public List<Integer> starList(Long userId) {
		return lambdaQuery().select(CrmLeadsUserStar::getLeadsId).eq(CrmLeadsUserStar::getUserId, userId).list().stream()
				.map(CrmLeadsUserStar::getLeadsId).collect(Collectors.toList());
	}
}
