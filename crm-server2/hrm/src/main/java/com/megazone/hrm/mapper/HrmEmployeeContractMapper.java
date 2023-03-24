package com.megazone.hrm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.hrm.entity.PO.HrmEmployeeContract;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface HrmEmployeeContractMapper extends BaseMapper<HrmEmployeeContract> {

	List<Integer> queryToExpireContractCount();


}
