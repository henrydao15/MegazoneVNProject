package com.megazone.admin.mapper;

import com.megazone.admin.entity.BO.DeptVO;
import com.megazone.admin.entity.PO.AdminDept;
import com.megazone.core.servlet.BaseMapper;

import java.util.List;

public interface AdminDeptMapper extends BaseMapper<AdminDept> {

	List<DeptVO> queryDeptUserList();

	List<AdminDept> queryDeptList();
}
