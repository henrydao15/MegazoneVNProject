package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmConfig;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-13
 */
public interface IHrmConfigService extends BaseService<HrmConfig> {

	void addOrUpdate(HrmConfig hrmConfig);

	List<HrmConfig> queryListByType(Integer type);
}
