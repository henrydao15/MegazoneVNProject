package com.megazone.oa.service.impl;

import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.oa.entity.PO.OaLogRule;
import com.megazone.oa.mapper.OaLogRuleMapper;
import com.megazone.oa.service.IOaLogRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OaLogRuleServiceImpl extends BaseServiceImpl<OaLogRuleMapper, OaLogRule> implements IOaLogRuleService {

	@Override
	public List<OaLogRule> queryOaLogRuleList() {
		List<OaLogRule> list = list();
		for (OaLogRule oaLogRule : list) {
			String memberUserId = oaLogRule.getMemberUserId();
			List<SimpleUser> data = UserCacheUtil.getSimpleUsers(TagUtil.toLongSet(memberUserId));
			oaLogRule.setMemberUser(data);
		}
		return list;
	}

	@Override
	public void setOaLogRule(List<OaLogRule> ruleList) {
		updateBatchById(ruleList);
	}
}
