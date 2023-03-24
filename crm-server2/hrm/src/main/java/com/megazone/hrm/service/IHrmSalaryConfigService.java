package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmSalaryConfig;
import com.megazone.hrm.entity.VO.QueryInItConfigVO;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-26
 */
public interface IHrmSalaryConfigService extends BaseService<HrmSalaryConfig> {

	/**
	 * @return
	 */
	QueryInItConfigVO queryInItConfig();

	/**
	 * @param salaryConfig
	 */
	void saveInitConfig(HrmSalaryConfig salaryConfig);

	/**
	 * @param type
	 */
	void updateInitStatus(Integer type);


}
