package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.BO.QueryEmployFieldManageBO;
import com.megazone.hrm.entity.PO.HrmEmployeeFieldManage;
import com.megazone.hrm.entity.VO.EmployeeFieldManageVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2021-04-14
 */
public interface HrmEmployeeFieldManageMapper extends BaseMapper<HrmEmployeeFieldManage> {
	/**
	 * @param queryEmployFieldManageBO
	 * @return
	 */
	List<EmployeeFieldManageVO> queryEmployeeManageField(QueryEmployFieldManageBO queryEmployFieldManageBO);
}
