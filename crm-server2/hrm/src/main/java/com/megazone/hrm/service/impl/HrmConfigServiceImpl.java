package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.entity.PO.HrmConfig;
import com.megazone.hrm.mapper.HrmConfigMapper;
import com.megazone.hrm.service.IHrmConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-13
 */
@Service
public class HrmConfigServiceImpl extends BaseServiceImpl<HrmConfigMapper, HrmConfig> implements IHrmConfigService {
	@Override
	public void addOrUpdate(HrmConfig hrmConfig) {
		saveOrUpdate(hrmConfig);
	}

	@Override
	public List<HrmConfig> queryListByType(Integer type) {
		return lambdaQuery().eq(HrmConfig::getType, type).orderByAsc(HrmConfig::getCreateTime).list();
	}
}
