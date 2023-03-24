package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeSeg;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmAchievementEmployeeSegService extends BaseService<HrmAchievementEmployeeSeg> {
	/**
	 * @param employeeAppraisalId
	 * @return
	 */
	List<HrmAchievementEmployeeSeg> queryAppraisalSeg(Integer employeeAppraisalId);
}
