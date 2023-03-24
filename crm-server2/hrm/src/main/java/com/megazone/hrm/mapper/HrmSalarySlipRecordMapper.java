package com.megazone.hrm.mapper;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QuerySendDetailListBO;
import com.megazone.hrm.entity.BO.QuerySendRecordListBO;
import com.megazone.hrm.entity.BO.QuerySlipEmployeePageListBO;
import com.megazone.hrm.entity.BO.SendSalarySlipBO;
import com.megazone.hrm.entity.PO.HrmSalarySlipRecord;
import com.megazone.hrm.entity.VO.QuerySendDetailListVO;
import com.megazone.hrm.entity.VO.QuerySendRecordListVO;
import com.megazone.hrm.entity.VO.SlipEmployeeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
public interface HrmSalarySlipRecordMapper extends BaseMapper<HrmSalarySlipRecord> {

	BasePage<SlipEmployeeVO> querySlipEmployeePageList(BasePage<SlipEmployeeVO> page, @Param("sRecordId") Integer sRecordId, @Param("data") QuerySlipEmployeePageListBO slipEmployeePageListBO);

	BasePage<QuerySendRecordListVO> querySendRecordList(BasePage<QuerySendRecordListVO> page, @Param("data") QuerySendRecordListBO querySendRecordListBO);

	BasePage<QuerySendDetailListVO> querySendDetailList(BasePage<QuerySendDetailListVO> page, @Param("data") QuerySendDetailListBO querySendRecordListBO);

	List<Integer> querySlipEmployeeIds(@Param("sRecordId") Integer sRecordId, @Param("data") SendSalarySlipBO sendSalarySlipBO);
}
