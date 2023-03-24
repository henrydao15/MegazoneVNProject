package com.megazone.hrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.megazone.core.common.DataAuthEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.RecursionUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.hrm.common.EmployeeHolder;
import com.megazone.hrm.common.HrmCodeEnum;
import com.megazone.hrm.constant.MenuIdConstant;
import com.megazone.hrm.entity.BO.AddDeptBO;
import com.megazone.hrm.entity.BO.QueryDeptListBO;
import com.megazone.hrm.entity.BO.QueryEmployeeByDeptIdBO;
import com.megazone.hrm.entity.PO.HrmDept;
import com.megazone.hrm.entity.PO.HrmEmployee;
import com.megazone.hrm.entity.VO.DeptEmployeeVO;
import com.megazone.hrm.entity.VO.DeptVO;
import com.megazone.hrm.entity.VO.QueryEmployeeListByDeptIdVO;
import com.megazone.hrm.entity.VO.SimpleHrmDeptVO;
import com.megazone.hrm.mapper.HrmDeptMapper;
import com.megazone.hrm.service.IHrmDeptService;
import com.megazone.hrm.service.IHrmEmployeeService;
import com.megazone.hrm.utils.EmployeeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Slf4j
@Service
public class HrmDeptServiceImpl extends BaseServiceImpl<HrmDeptMapper, HrmDept> implements IHrmDeptService {

	@Autowired
	private HrmDeptMapper deptMapper;

	@Autowired
	private IHrmEmployeeService employeeService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private EmployeeUtil employeeUtil;

	/**
	 * @param result
	 * @param node
	 * @param findDeptIds id
	 */
	private static void filterNode(DeptVO result, DeptVO node, Set<Integer> findDeptIds) {
		List<DeptVO> childList = node.getChildren();
		if (findDeptIds.contains(node.getDeptId())) {
			DeptVO newNode = BeanUtil.copyProperties(node, DeptVO.class);
			newNode.setChildren(null);
			if (CollUtil.isEmpty(result.getChildren())) {
				result.setChildren(Lists.newArrayList(newNode));
			} else {
				List<DeptVO> childList1 = result.getChildren();
				childList1.add(newNode);
			}
			findDeptIds.remove(node.getDeptId());
			if (CollUtil.isEmpty(childList)) {
				return;
			}
			for (DeptVO deptVO : childList) {
				filterNode(newNode, deptVO, findDeptIds);
			}
		} else {
			if (CollUtil.isEmpty(childList)) {
				return;
			}
			for (DeptVO deptVO : childList) {
				filterNode(result, deptVO, findDeptIds);
			}
		}
	}

	@Override
	public void addOrUpdate(AddDeptBO addDeptBO) {
		HrmDept hrmDept = BeanUtil.copyProperties(addDeptBO, HrmDept.class);
		Integer count;
		if (addDeptBO.getDeptId() == null) {
			count = lambdaQuery().eq(HrmDept::getCode, addDeptBO.getCode()).count();
		} else {
			count = lambdaQuery().eq(HrmDept::getCode, addDeptBO.getCode()).ne(HrmDept::getDeptId, addDeptBO.getDeptId()).count();
		}
		if (count > 0) {
			throw new CrmException(HrmCodeEnum.DEPT_CODE_ALREADY_EXISTS);
		}
		saveOrUpdate(hrmDept);
	}

	@Override
	public DeptVO queryById(Integer deptId) {
		Collection<Integer> employeeIds = employeeUtil.queryDataAuthEmpIdByMenuId(MenuIdConstant.EMPLOYEE_MENU_ID);
		return deptMapper.queryById(deptId, employeeIds);
	}

	@Override
	public List<DeptVO> queryTreeList(QueryDeptListBO queryDeptListBO) {
		Integer dataAuthType = adminService.queryDataType(UserUtil.getUserId(), MenuIdConstant.DEPT_MENU_ID).getData();
		List<DeptVO> deptVOList = new ArrayList<>();
		if (EmployeeHolder.isHrmAdmin() || dataAuthType.equals(DataAuthEnum.ALL.getValue())) {
			if ("tree".equals(queryDeptListBO.getType()) || queryDeptListBO.getType() == null) {
				List<DeptVO> deptList = deptMapper.queryList(queryDeptListBO);
				if (StrUtil.isNotEmpty(queryDeptListBO.getName())) {
					List<HrmDept> list = lambdaQuery().select(HrmDept::getDeptId).like(HrmDept::getName, queryDeptListBO.getName()).list();
					for (HrmDept dept : list) {
						deptVOList.addAll(createTree1(dept.getDeptId(), deptList));
					}
				} else {
					deptVOList = createTree(0, deptList);
				}
			}
			if ("update".equals(queryDeptListBO.getType())) {
				List<HrmDept> deptList = list();
				deptVOList = deptList.stream().map(dept -> {
					DeptVO deptVO = new DeptVO();
					deptVO.setDeptId(dept.getDeptId());
					deptVO.setName(dept.getName());
					deptVO.setPid(dept.getPid());
					return deptVO;
				}).collect(Collectors.toList());
				List<Integer> ids = RecursionUtil.getChildList(deptVOList, "pid", queryDeptListBO.getId(), "deptId", "deptId");
				ids.add(queryDeptListBO.getId());
				deptVOList.removeIf(dept -> ids.contains(dept.getDeptId()));
			}
		} else {
			deptVOList = getDataAuthDeptList(queryDeptListBO, dataAuthType);
		}
		return deptVOList;
	}

	/**
	 *
	 */
	private List<DeptVO> getDataAuthDeptList(QueryDeptListBO queryDeptListBO, Integer dataAuthType) {
		List<DeptVO> deptVOList = new ArrayList<>();
		Collection<Integer> employeeIds = employeeUtil.queryDataAuthEmpId(dataAuthType);
		if (CollUtil.isEmpty(employeeIds)) {
			return deptVOList;
		}
		List<DeptVO> queryDept = deptMapper.queryDeptByEmpIds(employeeIds);
		Map<Integer, DeptVO> deptIdMap = queryDept.stream().collect(Collectors.toMap(DeptVO::getDeptId, e -> e));
		List<DeptVO> deptList = deptMapper.queryList(queryDeptListBO);
		for (DeptVO deptVO : deptList) {
			if (deptIdMap.containsKey(deptVO.getDeptId())) {
				DeptVO dept = deptIdMap.get(deptVO.getDeptId());
				deptVO.setAllNum(dept.getAllNum());
				deptVO.setMyAllNum(dept.getMyAllNum());
				deptVO.setFullTimeNum(dept.getFullTimeNum());
				deptVO.setMyFullTimeNum(dept.getMyFullTimeNum());
				deptVO.setNuFullTimeNum(dept.getNuFullTimeNum());
				deptVO.setMyNuFullTimeNum(dept.getNuFullTimeNum());
			} else {
				deptVO.setAllNum(0);
				deptVO.setMyAllNum(0);
				deptVO.setFullTimeNum(0);
				deptVO.setMyFullTimeNum(0);
				deptVO.setNuFullTimeNum(0);
				deptVO.setMyNuFullTimeNum(0);
			}
		}
		if (StrUtil.isNotEmpty(queryDeptListBO.getName())) {
			List<HrmDept> list = lambdaQuery().select(HrmDept::getDeptId).in(HrmDept::getDeptId, deptIdMap.keySet()).like(HrmDept::getName, queryDeptListBO.getName()).list();
			for (HrmDept dept : list) {
				deptVOList.addAll(createTree1(dept.getDeptId(), deptList));
			}
			return deptVOList;
		} else {
			List<DeptVO> treeDept = createTree(0, deptList);
			DeptVO root = new DeptVO();
			if (CollUtil.isNotEmpty(treeDept)) {
				filterNode(root, treeDept.get(0), deptIdMap.keySet());
			}
			deptVOList = root.getChildren();
			return deptVOList;
		}
	}

	private List<DeptVO> createTree(int pid, List<DeptVO> deptList) {
		List<DeptVO> treeDept = new ArrayList<>();
		for (DeptVO dept : deptList) {
			if (pid == dept.getPid()) {
				treeDept.add(dept);
				List<DeptVO> children = createTree(dept.getDeptId(), deptList);
				for (DeptVO child : children) {
					dept.setAllNum(dept.getAllNum() + child.getAllNum());
					dept.setFullTimeNum(dept.getFullTimeNum() + child.getFullTimeNum());
					dept.setNuFullTimeNum(dept.getNuFullTimeNum() + child.getNuFullTimeNum());
				}
				dept.setChildren(children);
			}
		}
		return treeDept;
	}

	private List<DeptVO> createTree1(int id, List<DeptVO> deptList) {
		List<DeptVO> treeDept = new ArrayList<>();
		for (DeptVO dept : deptList) {
			if (id == dept.getDeptId()) {
				treeDept.add(dept);
				List<DeptVO> children = createTree(dept.getDeptId(), deptList);
				for (DeptVO child : children) {
					dept.setAllNum(dept.getAllNum() + child.getAllNum());
					dept.setFullTimeNum(dept.getFullTimeNum() + child.getFullTimeNum());
					dept.setNuFullTimeNum(dept.getNuFullTimeNum() + child.getNuFullTimeNum());
				}
			}
		}
		return treeDept;
	}

	@Override
	public List<SimpleHrmDeptVO> querySimpleDeptList(Collection<Integer> deptIds) {
		if (CollUtil.isEmpty(deptIds)) {
			return new ArrayList<>();
		}
		return lambdaQuery().select(HrmDept::getDeptId, HrmDept::getName)
				.in(HrmDept::getDeptId, deptIds)
				.list()
				.stream().map(dept -> {
					SimpleHrmDeptVO simpleHrmDeptVO = new SimpleHrmDeptVO();
					simpleHrmDeptVO.setDeptId(dept.getDeptId());
					simpleHrmDeptVO.setDeptName(dept.getName());
					return simpleHrmDeptVO;
				}).collect(Collectors.toList());
	}

	@Override
	public SimpleHrmDeptVO querySimpleDept(Integer deptId) {
		if (deptId == null) {
			return new SimpleHrmDeptVO();
		}
		HrmDept hrmDept = lambdaQuery().select(HrmDept::getDeptId, HrmDept::getName).eq(HrmDept::getDeptId, deptId).one();
		SimpleHrmDeptVO simpleHrmDeptVO = new SimpleHrmDeptVO();
		simpleHrmDeptVO.setDeptId(hrmDept.getDeptId());
		simpleHrmDeptVO.setDeptName(hrmDept.getName());
		return simpleHrmDeptVO;
	}

	@Override
	public BasePage<QueryEmployeeListByDeptIdVO> queryEmployeeByDeptId(QueryEmployeeByDeptIdBO employeeByDeptIdBO) {
		Collection<Integer> employeeIds = employeeUtil.queryDataAuthEmpIdByMenuId(MenuIdConstant.DEPT_MENU_ID);
		return deptMapper.queryEmployeeByDeptId(employeeByDeptIdBO.parse(), employeeByDeptIdBO, employeeIds);
	}

	@Override
	public void deleteDeptById(String deptId) {
		Integer empCount = employeeService.lambdaQuery().in(HrmEmployee::getEntryStatus, 1, 3).eq(HrmEmployee::getDeptId, deptId).eq(HrmEmployee::getIsDel, 0).count();
		if (empCount > 0) {
			throw new CrmException(HrmCodeEnum.THERE_ARE_EMPLOYEES_UNDER_THE_DEPARTMENT);
		}
		Integer childCount = lambdaQuery().eq(HrmDept::getPid, deptId).count();
		if (childCount > 0) {
			throw new CrmException(HrmCodeEnum.THERE_ARE_SUB_DEPARTMENTS_THAT_CANNOT_BE_DELETED);
		}
		HrmDept dept = getById(deptId);
		if ("1".equals(dept.getCode())) {
			throw new CrmException(HrmCodeEnum.TOP_LEVEL_DEPARTMENT_CANNOT_BE_DELETED);
		}
		removeById(deptId);
	}

	@Override
	public Set<Integer> queryChildDeptId(Collection<Integer> deptIds) {
		Set<Integer> newDeptIds = new HashSet<>(deptIds);
		if (CollUtil.isNotEmpty(deptIds)) {
			deptIds.forEach(deptId -> {
				Set<Integer> collect = lambdaQuery().select(HrmDept::getDeptId).eq(HrmDept::getPid, deptId).list()
						.stream().map(HrmDept::getDeptId).collect(Collectors.toSet());
				Set<Integer> integers = queryChildDeptId(collect);
				newDeptIds.addAll(integers);
			});
		}
		return newDeptIds;
	}

	@Override
	public Set<Integer> queryParentDeptId(Integer pid) {
		Set<Integer> deptIds = new HashSet<>();
		deptIds.add(pid);
		Optional<HrmDept> hrmDeptOpt = lambdaQuery().select(HrmDept::getDeptId, HrmDept::getPid).eq(HrmDept::getDeptId, pid).oneOpt();
		if (hrmDeptOpt.isPresent()) {
			HrmDept hrmDept = hrmDeptOpt.get();
			if (hrmDept.getPid() != 0) {
				Set<Integer> integers = queryParentDeptId(hrmDept.getPid());
				deptIds.addAll(integers);
			}
		}
		return deptIds;
	}

	@Override
	public List<DeptEmployeeVO> queryDeptEmployeeList() {
		return deptMapper.queryDeptEmployeeList();
	}
}
