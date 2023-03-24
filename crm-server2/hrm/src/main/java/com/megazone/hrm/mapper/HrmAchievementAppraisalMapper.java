package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryAppraisalEmployeeListBO;
import com.megazone.hrm.entity.BO.QueryAppraisalPageListBO;
import com.megazone.hrm.entity.BO.QueryEmployeeListByAppraisalIdBO;
import com.megazone.hrm.entity.PO.HrmAchievementAppraisal;
import com.megazone.hrm.entity.VO.AppraisalEmployeeListVO;
import com.megazone.hrm.entity.VO.AppraisalPageListVO;
import com.megazone.hrm.entity.VO.EmployeeAppraisalVO;
import com.megazone.hrm.entity.VO.EmployeeListByAppraisalIdVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmAchievementAppraisalMapper extends BaseMapper<HrmAchievementAppraisal> {

	BasePage<AppraisalPageListVO> queryAppraisalPageList(BasePage<AppraisalPageListVO> parse, @Param("data") QueryAppraisalPageListBO queryAppraisalPageListBO);

	BasePage<EmployeeListByAppraisalIdVO> queryEmployeeListByAppraisalId(BasePage<EmployeeListByAppraisalIdVO> page,
																		 @Param("data") QueryEmployeeListByAppraisalIdBO employeeListByAppraisalIdBO);

	BasePage<AppraisalEmployeeListVO> queryEmployeePageList(BasePage<AppraisalEmployeeListVO> parse, @Param("data") QueryAppraisalEmployeeListBO employeeListBO,
															@Param("employeeIds") Collection<Integer> dataAuthEmployeeIds);

	Map<String, Object> queryEmployeeLastAppraisal(Integer employeeId);

	BasePage<EmployeeAppraisalVO> queryEmployeeAppraisal(BasePage<EmployeeAppraisalVO> parse, @Param("employeeId") Integer employeeId);
}
