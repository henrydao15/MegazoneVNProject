package com.megazone.examine.service.impl;

import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserUtil;
import com.megazone.examine.constant.ExamineTypeEnum;
import com.megazone.examine.entity.BO.ExamineDataSaveBO;
import com.megazone.examine.entity.BO.ExamineUserBO;
import com.megazone.examine.entity.PO.ExamineFlow;
import com.megazone.examine.entity.PO.ExamineFlowContinuousSuperior;
import com.megazone.examine.entity.VO.ExamineFlowVO;
import com.megazone.examine.mapper.ExamineFlowContinuousSuperiorMapper;
import com.megazone.examine.service.ExamineTypeService;
import com.megazone.examine.service.IExamineFlowContinuousSuperiorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-13
 */
@Service("continuousSuperiorService")
public class ExamineFlowContinuousSuperiorServiceImpl extends BaseServiceImpl<ExamineFlowContinuousSuperiorMapper, ExamineFlowContinuousSuperior> implements IExamineFlowContinuousSuperiorService, ExamineTypeService {

	@Autowired
	private AdminService adminService;

	/**
	 * @param dataSaveBO data
	 * @param flowId     ID
	 */
	@Override
	public void saveExamineFlowData(ExamineDataSaveBO dataSaveBO, Integer flowId, String batchId) {
		ExamineFlowContinuousSuperior IExamineFlowContinuousSuperior = new ExamineFlowContinuousSuperior();
		IExamineFlowContinuousSuperior.setMaxLevel(dataSaveBO.getParentLevel());
		IExamineFlowContinuousSuperior.setFlowId(flowId);
		IExamineFlowContinuousSuperior.setRoleId(dataSaveBO.getRoleId());
		IExamineFlowContinuousSuperior.setType(dataSaveBO.getType());
		IExamineFlowContinuousSuperior.setBatchId(batchId);
		save(IExamineFlowContinuousSuperior);
	}

	/**
	 * @param createUserId
	 * @param recordId     ID
	 * @param examineFlow
	 * @return data
	 */
	public ExamineUserBO queryFlowUser(Long createUserId, Integer recordId, ExamineFlow examineFlow) {
		ExamineUserBO examineUserBO = new ExamineUserBO();
		List<UserInfo> userInfoList = adminService.queryUserInfoList().getData();
		/*

		 */
		ExamineFlowContinuousSuperior continuousSuperior = lambdaQuery()
				.eq(ExamineFlowContinuousSuperior::getFlowId, examineFlow.getFlowId())
				.last(" limit 1").one();
		UserInfo userInfo = null;
		/*

		 */
		for (UserInfo info : userInfoList) {
			if (info.getUserId().equals(createUserId)) {
				userInfo = info;
			}
		}
		/*

		 */
		if (userInfo == null) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		List<Long> userList;
		if (userInfo.getParentId() == null || userInfo.getParentId() == 0) {
			userList = new ArrayList<>();
			examineUserBO.setType(3);
		} else {
			userList = queryUser(userInfoList, continuousSuperior.getMaxLevel(), continuousSuperior.getRoleId(), userInfo.getParentId());
			/*

			 */
			examineUserBO.setType(1);
		}
		examineUserBO.setUserList(handleUserList(userList, examineFlow.getExamineId()));
		return examineUserBO;
	}

	/**
	 * @param batchId batchId
	 */
	@Override
	public void queryFlowListByBatchId(Map<String, Object> map, String batchId) {
		List<ExamineFlowContinuousSuperior> continuousSuperiors = lambdaQuery().eq(ExamineFlowContinuousSuperior::getBatchId, batchId).list();
		Map<Integer, List<ExamineFlowContinuousSuperior>> collect = continuousSuperiors.stream().collect(Collectors.groupingBy(ExamineFlowContinuousSuperior::getFlowId));
		map.put(ExamineTypeEnum.CONTINUOUS_SUPERIOR.getServerName(), collect);
	}

	/**
	 * @param examineFlow
	 * @param map         map
	 * @return data
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ExamineFlowVO createFlowInfo(ExamineFlow examineFlow, Map<String, Object> map, List<UserInfo> userInfoList, Long ownerUserId) {
		UserInfo userInfo = null;
		if (ownerUserId != null) {
			userInfo = adminService.getUserInfo(ownerUserId).getData();
		}
		if (userInfo == null) {
			userInfo = adminService.getUserInfo(UserUtil.getUserId()).getData();
		}
		ExamineFlowVO examineFlowVO = new ExamineFlowVO();
		Map<Integer, List<ExamineFlowContinuousSuperior>> collect = (Map<Integer, List<ExamineFlowContinuousSuperior>>) map.get(ExamineTypeEnum.CONTINUOUS_SUPERIOR.getServerName());
		List<ExamineFlowContinuousSuperior> superiors = collect.get(examineFlow.getFlowId());
		if (superiors == null || superiors.size() == 0) {
			return null;
		}
		ExamineFlowContinuousSuperior continuousSuperior = superiors.get(0);
		examineFlowVO.setExamineType(examineFlow.getExamineType());
		Integer examineErrorHandling = examineFlow.getExamineErrorHandling();
		examineFlowVO.setExamineErrorHandling(examineErrorHandling);
		List<Long> queryUser;
		if (userInfo.getParentId() == null || userInfo.getParentId() == 0) {
			queryUser = new ArrayList<>();
		} else {
			queryUser = queryUser(userInfoList, continuousSuperior.getMaxLevel(), continuousSuperior.getRoleId(), userInfo.getParentId());
		}
		queryUser = handleUserList(queryUser, examineFlow.getExamineId());
		examineFlowVO.setType(continuousSuperior.getType());
		examineFlowVO.setFlowId(examineFlow.getFlowId());
		examineFlowVO.setName(examineFlow.getName());
		examineFlowVO.setParentLevel(continuousSuperior.getMaxLevel());
		examineFlowVO.setRoleId(continuousSuperior.getRoleId());
		List<SimpleUser> userList = new ArrayList<>();
		for (Long userId : queryUser) {
			for (UserInfo info : userInfoList) {
				if (info.getUserId().equals(userId)) {
					userList.add(toSimPleUser(info));
					break;
				}
			}
		}
		examineFlowVO.setUserList(userList);
		return examineFlowVO;
	}

	private List<Long> queryUser(List<UserInfo> userInfoList, Integer maxLevel, Integer roleId, Long userId) {
		List<Long> idList = new ArrayList<>();
		if (maxLevel != null) {
			if (maxLevel < 0) {
				return idList;
			}
			maxLevel--;
		}
		for (UserInfo userInfo : userInfoList) {
			if (userId.equals(userInfo.getUserId())) {
				idList.add(userId);
				if (userInfo.getRoles().contains(roleId)) {
					return idList;
				}
				if (userInfo.getParentId() == null || userInfo.getParentId() == 0) {
					return idList;
				}
				idList.addAll(queryUser(userInfoList, maxLevel, roleId, userInfo.getParentId()));
			}
		}
		return idList;
	}
}
