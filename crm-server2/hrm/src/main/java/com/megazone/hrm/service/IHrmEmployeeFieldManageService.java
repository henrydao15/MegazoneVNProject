package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
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
public interface IHrmEmployeeFieldManageService extends BaseService<HrmEmployeeFieldManage> {
	/**
	 * @param queryEmployFieldManageBO
	 * @return
	 */
	List<EmployeeFieldManageVO> queryEmployeeManageField(QueryEmployFieldManageBO queryEmployFieldManageBO);

	/**
	 * @param manageFields
	 */
	void setEmployeeManageField(List<EmployeeFieldManageVO> manageFields);

	/**
	 * @param entryStatus
	 * @return
	 */
	List<HrmEmployeeFieldManage> queryManageField(Integer entryStatus);
}
