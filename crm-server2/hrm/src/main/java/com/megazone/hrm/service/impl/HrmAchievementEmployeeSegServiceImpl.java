package com.megazone.hrm.service.impl;

import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeEvaluatoSeg;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeSeg;
import com.megazone.hrm.entity.PO.HrmAchievementEmployeeSegItem;
import com.megazone.hrm.entity.PO.HrmEmployee;
import com.megazone.hrm.mapper.HrmAchievementEmployeeSegMapper;
import com.megazone.hrm.service.IHrmAchievementEmployeeEvaluatoSegService;
import com.megazone.hrm.service.IHrmAchievementEmployeeSegItemService;
import com.megazone.hrm.service.IHrmAchievementEmployeeSegService;
import com.megazone.hrm.service.IHrmEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Service
public class HrmAchievementEmployeeSegServiceImpl extends BaseServiceImpl<HrmAchievementEmployeeSegMapper, HrmAchievementEmployeeSeg> implements IHrmAchievementEmployeeSegService {

	@Autowired
	private IHrmAchievementEmployeeSegItemService employeeSegItemService;

	@Autowired
	private IHrmAchievementEmployeeEvaluatoSegService employeeEvaluatoSegService;

	@Autowired
	private IHrmEmployeeService employeeService;

	@Override
	public List<HrmAchievementEmployeeSeg> queryAppraisalSeg(Integer employeeAppraisalId) {
		List<HrmAchievementEmployeeSeg> hrmAchievementEmployeeSegList = lambdaQuery()
				.eq(HrmAchievementEmployeeSeg::getEmployeeAppraisalId, employeeAppraisalId).orderByAsc(HrmAchievementEmployeeSeg::getSort).list();
		hrmAchievementEmployeeSegList.forEach(employeeSeg -> {

			List<HrmAchievementEmployeeSegItem> employeeSegItems = employeeSegItemService.lambdaQuery()
					.eq(HrmAchievementEmployeeSegItem::getSegId, employeeSeg.getSegId()).orderByAsc(HrmAchievementEmployeeSegItem::getSort).list();
			employeeSeg.setItems(employeeSegItems);

			List<HrmAchievementEmployeeEvaluatoSeg> evaluatoSegList = employeeEvaluatoSegService.lambdaQuery().eq(HrmAchievementEmployeeEvaluatoSeg::getSegId, employeeSeg.getSegId())
					.eq(HrmAchievementEmployeeEvaluatoSeg::getEmployeeAppraisalId, employeeAppraisalId)
					.orderByAsc(HrmAchievementEmployeeEvaluatoSeg::getUpdateTime).list();
			evaluatoSegList.forEach(evaluatoSeg -> {
				String employeeName = employeeService.lambdaQuery().select(HrmEmployee::getEmployeeName).eq(HrmEmployee::getEmployeeId, evaluatoSeg.getEmployeeId()).last("limit 1").one().getEmployeeName();
				evaluatoSeg.setEmployeeName(employeeName);
			});
			employeeSeg.setEvaluatoSegList(evaluatoSegList);
		});
		return hrmAchievementEmployeeSegList;
	}
}
