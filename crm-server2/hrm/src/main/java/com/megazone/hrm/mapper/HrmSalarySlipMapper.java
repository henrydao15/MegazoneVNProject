package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QuerySalarySlipListBO;
import com.megazone.hrm.entity.PO.HrmSalarySlip;
import com.megazone.hrm.entity.VO.QuerySalarySlipListVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
public interface HrmSalarySlipMapper extends BaseMapper<HrmSalarySlip> {

	BasePage<QuerySalarySlipListVO> querySalarySlipList(BasePage<QuerySalarySlipListVO> parse, @Param("data") QuerySalarySlipListBO querySalarySlipListBO, @Param("employeeId") Integer employeeId);
}
