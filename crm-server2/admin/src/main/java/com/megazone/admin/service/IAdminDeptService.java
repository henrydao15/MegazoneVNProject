package com.megazone.admin.service;

import com.megazone.admin.entity.BO.AdminDeptBO;
import com.megazone.admin.entity.BO.AdminDeptQueryBO;
import com.megazone.admin.entity.BO.DeptVO;
import com.megazone.admin.entity.PO.AdminDept;
import com.megazone.admin.entity.VO.AdminDeptVO;
import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.servlet.BaseService;

import java.util.List;

public interface IAdminDeptService extends BaseService<AdminDept> {

	List<AdminDeptVO> queryDeptTree(AdminDeptQueryBO queryBO);

	List<Integer> queryChildDept(Integer parentId);

	void addDept(AdminDeptBO adminDeptBO);

	void setDept(AdminDeptBO adminDeptBO);

	void deleteDept(Integer deptId);

	String getNameByDeptId(Integer deptId);

	List<SimpleDept> queryDeptByIds(List<Integer> ids);

	List<DeptVO> queryDeptUserList();


}
