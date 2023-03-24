package com.megazone.hrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.hrm.entity.PO.HrmEmployeeData;
import com.megazone.hrm.mapper.HrmEmployeeDataMapper;
import com.megazone.hrm.service.IHrmEmployeeDataService;
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
public class HrmEmployeeDataServiceImpl extends BaseServiceImpl<HrmEmployeeDataMapper, HrmEmployeeData> implements IHrmEmployeeDataService {

	@Override
	public List<HrmEmployeeData> queryListByEmployeeId(Integer employeeId) {
		return list(new QueryWrapper<HrmEmployeeData>().select("field_id", "field_value", "field_value_desc").eq("employee_id", employeeId));
	}

	@Override
	public List<JSONObject> queryFiledListByEmployeeId(Integer employeeId) {
		return getBaseMapper().queryFiledListByEmployeeId(employeeId);
	}

	@Override
	public Integer verifyUnique(Integer fieldId, String value, Integer id) {
		return getBaseMapper().verifyUnique(fieldId, value, id);
	}
}
