package com.megazone.examine.service;

import cn.hutool.core.collection.CollUtil;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.examine.entity.BO.ExamineDataSaveBO;
import com.megazone.examine.entity.BO.ExamineUserBO;
import com.megazone.examine.entity.PO.ExamineFlow;
import com.megazone.examine.entity.VO.ExamineFlowVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ExamineTypeService {


	/**
	 * @param dataSaveBO data
	 * @param flowId     ID
	 */
	public void saveExamineFlowData(ExamineDataSaveBO dataSaveBO, Integer flowId, String batchId);


	/**
	 * @param createUserId
	 * @param recordId     ID
	 * @param examineFlow
	 * @return data
	 */
	public ExamineUserBO queryFlowUser(Long createUserId, Integer recordId, ExamineFlow examineFlow);

	/**
	 * @param map
	 * @param batchId batchId
	 */
	public void queryFlowListByBatchId(Map<String, Object> map, String batchId);


	/**
	 * @param examineFlow
	 * @param map         map
	 * @return data
	 */
	public ExamineFlowVO createFlowInfo(ExamineFlow examineFlow, Map<String, Object> map, List<UserInfo> allUserList, Long ownerUserId);

	default public SimpleUser toSimPleUser(UserInfo userInfo) {
		SimpleUser simpleUser = new SimpleUser();
		simpleUser.setUserId(userInfo.getUserId());
		simpleUser.setRealname(userInfo.getRealname());
		simpleUser.setImg(userInfo.getImg());
		return simpleUser;
	}


	default public List<Long> handleUserList(List<Long> userIds, Long examineId) {
		List<Long> userList = new ArrayList<>();
		if (CollUtil.isNotEmpty(userIds)) {
			AdminService adminService = ApplicationContextHolder.getBean(AdminService.class);
			userList = adminService.queryNormalUserByIds(userIds).getData();
		}
		if (userList.size() == 0) {
			IExamineManagerUserService examineManagerUserService = ApplicationContextHolder.getBean(IExamineManagerUserService.class);
			userList = examineManagerUserService.queryExamineUser(examineId);
		}
		return userList;
	}


}
