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
import com.megazone.examine.entity.PO.ExamineFlowSuperior;
import com.megazone.examine.entity.VO.ExamineFlowVO;
import com.megazone.examine.mapper.ExamineFlowSuperiorMapper;
import com.megazone.examine.service.ExamineTypeService;
import com.megazone.examine.service.IExamineFlowSuperiorService;
import com.megazone.examine.service.IExamineManagerUserService;
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
@Service("superiorService")
public class ExamineFlowSuperiorServiceImpl extends BaseServiceImpl<ExamineFlowSuperiorMapper, ExamineFlowSuperior> implements IExamineFlowSuperiorService, ExamineTypeService {

	@Autowired
	private AdminService adminService;

	@Autowired
	private IExamineManagerUserService examineManagerUserService;

	/**
	 * @param dataSaveBO data
	 * @param flowId     ID
	 */
	@Override
	public void saveExamineFlowData(ExamineDataSaveBO dataSaveBO, Integer flowId, String batchId) {
		ExamineFlowSuperior IExamineFlowSuperior = new ExamineFlowSuperior();
		IExamineFlowSuperior.setParentLevel(dataSaveBO.getParentLevel());
		IExamineFlowSuperior.setFlowId(flowId);
		IExamineFlowSuperior.setType(dataSaveBO.getType());
		IExamineFlowSuperior.setBatchId(batchId);
		save(IExamineFlowSuperior);
	}

	/**
	 * @param createUserId
	 * @param recordId     ID
	 * @param examineFlow
	 * @return data
	 */
	public ExamineUserBO queryFlowUser(Long createUserId, Integer recordId, ExamineFlow examineFlow) {
		List<UserInfo> userInfoList = adminService.queryUserInfoList().getData();
		/*

		 */
		ExamineFlowSuperior flowSuperior = lambdaQuery()
				.eq(ExamineFlowSuperior::getFlowId, examineFlow.getFlowId())
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
		ExamineUserBO examineUserBO = new ExamineUserBO();
		Integer examineErrorHandling = examineFlow.getExamineErrorHandling();
		List<Long> ids;
		if (userInfo.getParentId() == null || userInfo.getParentId() == 0) {
			ids = new ArrayList<>();
		} else {
			List<Long> longList = queryUser(userInfoList, userInfo.getParentId());
			/*

			 */
			ids = new ArrayList<>();
			if (flowSuperior.getParentLevel() > longList.size()) {
				ids.add(longList.get(longList.size() - 1));
			} else {
				ids.add(longList.get(flowSuperior.getParentLevel() - 1));
			}
		}
		examineUserBO.setUserList(handleUserList(ids, examineFlow.getExamineId()));
		examineUserBO.setType(3);
		return examineUserBO;
	}

	/**
	 * @param map
	 * @param batchId batchId
	 */
	@Override
	public void queryFlowListByBatchId(Map<String, Object> map, String batchId) {
		List<ExamineFlowSuperior> continuousSuperiors = lambdaQuery().eq(ExamineFlowSuperior::getBatchId, batchId).list();
		Map<Integer, List<ExamineFlowSuperior>> collect = continuousSuperiors.stream().collect(Collectors.groupingBy(ExamineFlowSuperior::getFlowId));
		map.put(ExamineTypeEnum.SUPERIOR.getServerName(), collect);
	}

	/**
	 * @param examineFlow
	 * @param map         map
	 * @return data
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ExamineFlowVO createFlowInfo(ExamineFlow examineFlow, Map<String, Object> map, List<UserInfo> userInfoList, Long ownerUserId) {
		UserInfo userInfo;
		if (ownerUserId != null) {
			userInfo = adminService.getUserInfo(ownerUserId).getData();
		} else {
			userInfo = adminService.getUserInfo(UserUtil.getUserId()).getData();
		}
		ExamineFlowVO examineFlowVO = new ExamineFlowVO();
		Map<Integer, List<ExamineFlowSuperior>> collect = (Map<Integer, List<ExamineFlowSuperior>>) map.get(ExamineTypeEnum.SUPERIOR.getServerName());
		List<ExamineFlowSuperior> superiors = collect.get(examineFlow.getFlowId());
		if (superiors == null || superiors.size() == 0) {
			return null;
		}
		ExamineFlowSuperior continuousSuperior = superiors.get(0);
		List<SimpleUser> userList = new ArrayList<>();
		examineFlowVO.setExamineType(examineFlow.getExamineType());
		Integer examineErrorHandling = examineFlow.getExamineErrorHandling();
		examineFlowVO.setExamineErrorHandling(examineErrorHandling);
		examineFlowVO.setType(continuousSuperior.getType());
		List<Long> userIds;
		if (userInfo.getParentId() == null || userInfo.getParentId() == 0) {
			userIds = new ArrayList<>();
		} else {
			List<Long> longList = queryUser(userInfoList, userInfo.getParentId());
			/*

			 */
			Long userId;
			if (continuousSuperior.getParentLevel() > longList.size()) {
				userId = longList.get(longList.size() - 1);
			} else {
				userId = longList.get(continuousSuperior.getParentLevel() - 1);
			}
			userIds = new ArrayList<>();
			userIds.add(userId);
		}
		Long userId = handleUserList(userIds, examineFlow.getExamineId()).get(0);
		for (UserInfo info : userInfoList) {
			if (userId.equals(info.getUserId())) {
				userList.add(toSimPleUser(info));
				break;
			}
		}
		examineFlowVO.setFlowId(examineFlow.getFlowId());
		examineFlowVO.setName(examineFlow.getName());
		examineFlowVO.setParentLevel(continuousSuperior.getParentLevel());
		examineFlowVO.setUserList(userList);
		return examineFlowVO;
	}

	private List<Long> queryUser(List<UserInfo> userInfoList, Long userId) {
		List<Long> idList = new ArrayList<>();

		if (userId == null || userId == 0) {
			idList.add(userId);
			return idList;
		}
		for (UserInfo userInfo : userInfoList) {
			if (userId.equals(userInfo.getUserId())) {
				idList.add(userId);
				if (userInfo.getParentId() == null || userInfo.getParentId() == 0) {
					return idList;
				}
				idList.addAll(queryUser(userInfoList, userInfo.getParentId()));
			}
		}
		return idList;
	}
}
