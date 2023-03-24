package com.megazone.examine.service.impl;

import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.examine.constant.ExamineTypeEnum;
import com.megazone.examine.entity.BO.ExamineDataSaveBO;
import com.megazone.examine.entity.BO.ExamineUserBO;
import com.megazone.examine.entity.PO.ExamineFlow;
import com.megazone.examine.entity.PO.ExamineFlowRole;
import com.megazone.examine.entity.VO.ExamineFlowVO;
import com.megazone.examine.mapper.ExamineFlowRoleMapper;
import com.megazone.examine.service.ExamineTypeService;
import com.megazone.examine.service.IExamineFlowRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-13
 */
@Service("roleService")
public class ExamineFlowRoleServiceImpl extends BaseServiceImpl<ExamineFlowRoleMapper, ExamineFlowRole> implements IExamineFlowRoleService, ExamineTypeService {

	@Autowired
	private AdminService adminService;


	/**
	 * @param dataSaveBO data
	 * @param flowId     ID
	 */
	@Override
	public void saveExamineFlowData(ExamineDataSaveBO dataSaveBO, Integer flowId, String batchId) {
		ExamineFlowRole IExamineFlowRole = new ExamineFlowRole();
		IExamineFlowRole.setFlowId(flowId);
		IExamineFlowRole.setRoleId(dataSaveBO.getRoleId());
		IExamineFlowRole.setType(dataSaveBO.getType());
		IExamineFlowRole.setBatchId(batchId);
		save(IExamineFlowRole);
	}

	/**
	 * @param createUserId
	 * @param recordId     ID
	 * @param examineFlow
	 * @return data
	 */
	public ExamineUserBO queryFlowUser(Long createUserId, Integer recordId, ExamineFlow examineFlow) {
		ExamineFlowRole examineFlowRole = lambdaQuery().eq(ExamineFlowRole::getFlowId, examineFlow.getFlowId()).last(" limit 1").one();
		ExamineUserBO examineUserBO = new ExamineUserBO();
		examineUserBO.setType(examineFlowRole.getType());
		examineUserBO.setRoleId(examineFlowRole.getRoleId());
		List<Long> userList = new ArrayList<>();
		List<UserInfo> userInfoList = adminService.queryUserInfoList().getData();
		for (UserInfo userInfo : userInfoList) {
			if (userInfo.getRoles().contains(examineFlowRole.getRoleId())) {
				userList.add(userInfo.getUserId());
			}
		}
		examineUserBO.setUserList(handleUserList(userList, examineFlow.getExamineId()));
		return examineUserBO;
	}

	/**
	 * @param map
	 * @param batchId batchId
	 */
	@Override
	public void queryFlowListByBatchId(Map<String, Object> map, String batchId) {
		List<ExamineFlowRole> continuousSuperiors = lambdaQuery().eq(ExamineFlowRole::getBatchId, batchId).list();
		Map<Integer, List<ExamineFlowRole>> collect = continuousSuperiors.stream().collect(Collectors.groupingBy(ExamineFlowRole::getFlowId));
		map.put(ExamineTypeEnum.ROLE.getServerName(), collect);
	}

	/**
	 * @param examineFlow
	 * @param map         map
	 * @return data
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ExamineFlowVO createFlowInfo(ExamineFlow examineFlow, Map<String, Object> map, List<UserInfo> userInfoList, Long ownerUserId) {
		Map<Integer, List<ExamineFlowRole>> collect = (Map<Integer, List<ExamineFlowRole>>) map.get(ExamineTypeEnum.ROLE.getServerName());
		List<ExamineFlowRole> flowRoles = collect.get(examineFlow.getFlowId());
		if (flowRoles == null || flowRoles.size() == 0) {
			return null;
		}
		ExamineFlowRole examineFlowRole = flowRoles.get(0);
		ExamineFlowVO examineFlowVO = new ExamineFlowVO();
		examineFlowVO.setType(examineFlowRole.getType());
		examineFlowVO.setName(examineFlow.getName());
		examineFlowVO.setExamineType(examineFlow.getExamineType());
		examineFlowVO.setFlowId(examineFlow.getFlowId());
		examineFlowVO.setExamineErrorHandling(examineFlow.getExamineErrorHandling());
		examineFlowVO.setRoleId(examineFlowRole.getRoleId());
		List<SimpleUser> userList = new ArrayList<>();
		List<Long> userIdList = new ArrayList<>();
		for (UserInfo userInfo : userInfoList) {
			if (userInfo.getRoles().contains(examineFlowRole.getRoleId())) {
				userIdList.add(userInfo.getUserId());
			}
		}
		userIdList = handleUserList(userIdList, examineFlow.getExamineId());
		for (Long userId : userIdList) {
			for (UserInfo userInfo : userInfoList) {
				if (Objects.equals(userInfo.getUserId(), userId)) {
					userList.add(toSimPleUser(userInfo));
					break;
				}
			}
		}
		examineFlowVO.setUserList(userList);
		return examineFlowVO;
	}
}
