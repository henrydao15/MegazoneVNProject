package com.megazone.oa.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.oa.entity.PO.OaLogRule;

import java.util.List;

public interface IOaLogRuleService extends BaseService<OaLogRule> {

	List<OaLogRule> queryOaLogRuleList();


	void setOaLogRule(List<OaLogRule> ruleList);
}
