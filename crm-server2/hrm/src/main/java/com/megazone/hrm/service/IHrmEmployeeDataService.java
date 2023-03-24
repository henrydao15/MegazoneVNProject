package com.megazone.hrm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.PO.HrmEmployeeData;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmEmployeeDataService extends BaseService<HrmEmployeeData> {

	/**
	 * @param employeeId
	 * @return
	 */
	List<HrmEmployeeData> queryListByEmployeeId(Integer employeeId);


	/**
	 * @param employeeId
	 * @return
	 */
	List<JSONObject> queryFiledListByEmployeeId(Integer employeeId);

	/**
	 * @param fieldId
	 * @param value
	 * @param id
	 * @return
	 */
	Integer verifyUnique(Integer fieldId, String value, Integer id);
}
