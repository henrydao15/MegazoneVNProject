package com.megazone.hrm.service.impl;


import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.common.EmployeeHolder;
import com.megazone.hrm.entity.BO.QuerySalarySlipListBO;
import com.megazone.hrm.entity.PO.HrmSalarySlip;
import com.megazone.hrm.entity.VO.QuerySalarySlipListVO;
import com.megazone.hrm.mapper.HrmSalarySlipMapper;
import com.megazone.hrm.service.IHrmSalarySlipOptionService;
import com.megazone.hrm.service.IHrmSalarySlipRecordService;
import com.megazone.hrm.service.IHrmSalarySlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
@Service
public class HrmSalarySlipServiceImpl extends BaseServiceImpl<HrmSalarySlipMapper, HrmSalarySlip> implements IHrmSalarySlipService {


	@Autowired
	private HrmSalarySlipMapper salarySlipMapper;

	@Autowired
	private IHrmSalarySlipOptionService salarySlipOptionService;

	@Autowired
	private IHrmSalarySlipRecordService slipRecordService;

	@Override
	public BasePage<QuerySalarySlipListVO> querySalarySlipList(QuerySalarySlipListBO querySalarySlipListBO) {
		BasePage<QuerySalarySlipListVO> page = salarySlipMapper.querySalarySlipList(querySalarySlipListBO.parse(), querySalarySlipListBO, EmployeeHolder.getEmployeeId());
		page.getList().forEach(slip -> {
			if (slip.getReadStatus() == 0) {
				lambdaUpdate().set(HrmSalarySlip::getReadStatus, 1).eq(HrmSalarySlip::getId, slip.getId()).update();
			}
			slip.setSalarySlipOptionList(slipRecordService.querySlipDetail(slip.getId()));
		});
		return page;
	}
}
