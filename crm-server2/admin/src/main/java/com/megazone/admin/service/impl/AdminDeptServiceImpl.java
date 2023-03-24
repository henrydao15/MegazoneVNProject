package com.megazone.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.admin.common.AdminCodeEnum;
import com.megazone.admin.entity.BO.AdminDeptBO;
import com.megazone.admin.entity.BO.AdminDeptQueryBO;
import com.megazone.admin.entity.BO.DeptVO;
import com.megazone.admin.entity.PO.AdminDept;
import com.megazone.admin.entity.VO.AdminDeptVO;
import com.megazone.admin.mapper.AdminDeptMapper;
import com.megazone.admin.service.IAdminDeptService;
import com.megazone.admin.service.IAdminUserService;
import com.megazone.core.common.BaseStatusEnum;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.RecursionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-04-27
 */
@Service
public class AdminDeptServiceImpl extends BaseServiceImpl<AdminDeptMapper, AdminDept> implements IAdminDeptService {

	@Autowired
	private IAdminUserService adminUserService;

	/**
	 * @param queryBO ID
	 * @return data
	 */
	@Override
	public List<AdminDeptVO> queryDeptTree(AdminDeptQueryBO queryBO) {
		if (queryBO.getId() == null) {
			queryBO.setId(0);
		}
		if ("tree".equals(queryBO.getType())) {
			List<AdminDept> adminDeptList = getBaseMapper().queryDeptList();
			return RecursionUtil.getChildListTree(adminDeptList, "pid", queryBO.getId(), "deptId", "children", AdminDeptVO.class);
		}

		List<AdminDept> adminDeptList = list();
		if ("update".equals(queryBO.getType())) {
			List<Integer> ids = RecursionUtil.getChildList(adminDeptList, "pid", queryBO.getId(), "deptId", "deptId");
			adminDeptList.removeIf(dept -> ids.contains(dept.getDeptId()));
		}
		return adminDeptList.stream().map(obj -> BeanUtil.copyProperties(obj, AdminDeptVO.class)).collect(Collectors.toList());
	}

	/**
	 * @param parentId ID
	 * @return data
	 */
	@Override
	public List<Integer> queryChildDept(Integer parentId) {
		return RecursionUtil.getChildList(list(), "pid", parentId, "deptId", "deptId");
	}

	/**
	 * @param adminDeptBO
	 */
	@Override
	public void addDept(AdminDeptBO adminDeptBO) {
		//pid0
		if (BaseStatusEnum.CLOSE.getStatus().equals(adminDeptBO.getPid())) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		save(BeanUtil.copyProperties(adminDeptBO, AdminDept.class));
	}

	/**
	 * @param adminDeptBO
	 */
	@Override
	public void setDept(AdminDeptBO adminDeptBO) {
		List<Integer> deptIdList = queryChildDept(adminDeptBO.getDeptId());
		deptIdList.add(adminDeptBO.getDeptId());
		if (deptIdList.contains(adminDeptBO.getPid())) {
			throw new CrmException(AdminCodeEnum.ADMIN_PARENT_DEPT_ERROR);
		}
		AdminDept adminDept = getById(adminDeptBO.getDeptId());
		//pid0
		if ((!adminDept.getPid().equals(0)) && adminDeptBO.getPid().equals(0)) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		lambdaUpdate()
				.set(AdminDept::getName, adminDeptBO.getName())
				.set(AdminDept::getPid, adminDeptBO.getPid())
				.set(AdminDept::getOwnerUserId, adminDeptBO.getOwnerUserId())
				.eq(AdminDept::getDeptId, adminDept.getDeptId())
				.update();
	}

	/**
	 * @param deptId ID
	 */
	@Override
	public void deleteDept(Integer deptId) {
		Integer userCount = adminUserService.query().eq("dept_id", deptId).count();
		if (userCount > 0) {
			throw new CrmException(AdminCodeEnum.ADMIN_DEPT_REMOVE_EXIST_USER_ERROR);
		}
		Integer deptCount = query().eq("pid", deptId).count();
		if (deptCount > 0) {
			throw new CrmException(AdminCodeEnum.ADMIN_DEPT_REMOVE_EXIST_DEPT_ERROR);
		}
		removeById(deptId);
	}

	/**
	 * @param deptId ID
	 * @return data
	 */
	@Override
	public String getNameByDeptId(Integer deptId) {
		AdminDept adminDept = query().select("name").eq("dept_id", deptId).one();
		if (adminDept == null) {
			return "";
		}
		return adminDept.getName();
	}

	/**
	 * @param ids id
	 * @return data
	 */
	@Override
	public List<SimpleDept> queryDeptByIds(List<Integer> ids) {
		if (ids.size() == 0) {
			return new ArrayList<>();
		}
		LambdaQueryWrapper<AdminDept> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.select(AdminDept::getDeptId, AdminDept::getName).in(AdminDept::getDeptId, ids);
		return list(queryWrapper).stream().map(obj -> {
			SimpleDept simpleDept = new SimpleDept();
			simpleDept.setId(obj.getDeptId());
			simpleDept.setName(obj.getName());
			return simpleDept;
		}).collect(Collectors.toList());
	}

	@Override
	public List<DeptVO> queryDeptUserList() {
		return getBaseMapper().queryDeptUserList();
	}
}
