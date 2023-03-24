package com.megazone.examine.service.impl;

import com.megazone.core.common.Const;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.examine.constant.ExamineTypeEnum;
import com.megazone.examine.entity.BO.ExamineDataSaveBO;
import com.megazone.examine.entity.BO.ExamineUserBO;
import com.megazone.examine.entity.PO.ExamineFlow;
import com.megazone.examine.entity.PO.ExamineFlowMember;
import com.megazone.examine.entity.VO.ExamineFlowVO;
import com.megazone.examine.mapper.ExamineFlowMemberMapper;
import com.megazone.examine.service.ExamineTypeService;
import com.megazone.examine.service.IExamineFlowMemberService;
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
@Service("memberService")
public class ExamineFlowMemberServiceImpl extends BaseServiceImpl<ExamineFlowMemberMapper, ExamineFlowMember> implements IExamineFlowMemberService, ExamineTypeService {

	/**
	 * @param dataSaveBO data
	 * @param flowId     ID
	 */
	@Override
	public void saveExamineFlowData(ExamineDataSaveBO dataSaveBO, Integer flowId, String batchId) {
		List<ExamineFlowMember> IExamineFlowMemberList = new ArrayList<>();
		int i = 0;
		for (Long userId : dataSaveBO.getUserList()) {
			ExamineFlowMember IExamineFlowMember = new ExamineFlowMember();
			IExamineFlowMember.setUserId(userId);
			IExamineFlowMember.setFlowId(flowId);
			IExamineFlowMember.setSort(i++);
			IExamineFlowMember.setType(dataSaveBO.getType());
			IExamineFlowMember.setBatchId(batchId);
			IExamineFlowMemberList.add(IExamineFlowMember);
		}
		saveBatch(IExamineFlowMemberList, Const.BATCH_SAVE_SIZE);
	}

	/**
	 * @param createUserId
	 * @param recordId     ID
	 * @param examineFlow
	 * @return data
	 */
	public ExamineUserBO queryFlowUser(Long createUserId, Integer recordId, ExamineFlow examineFlow) {
		List<ExamineFlowMember> flowMembers = lambdaQuery().eq(ExamineFlowMember::getFlowId, examineFlow.getFlowId()).orderByAsc(ExamineFlowMember::getSort).list();
		ExamineUserBO examineUserBO = new ExamineUserBO();
		examineUserBO.setType(flowMembers.get(0).getType());
		List<Long> userIds = flowMembers.stream().map(ExamineFlowMember::getUserId).collect(Collectors.toList());
		examineUserBO.setUserList(handleUserList(userIds, examineFlow.getExamineId()));
		return examineUserBO;
	}

	/**
	 * @param map
	 * @param batchId batchId
	 */
	@Override
	public void queryFlowListByBatchId(Map<String, Object> map, String batchId) {
		List<ExamineFlowMember> continuousSuperiors = lambdaQuery().eq(ExamineFlowMember::getBatchId, batchId).list();
		Map<Integer, List<ExamineFlowMember>> collect = continuousSuperiors.stream().collect(Collectors.groupingBy(ExamineFlowMember::getFlowId));
		map.put(ExamineTypeEnum.MEMBER.getServerName(), collect);
	}

	/**
	 * @param examineFlow
	 * @param map         map
	 * @return data
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ExamineFlowVO createFlowInfo(ExamineFlow examineFlow, Map<String, Object> map, List<UserInfo> allUserList, Long ownerUserId) {
		Map<Integer, List<ExamineFlowMember>> collect = (Map<Integer, List<ExamineFlowMember>>) map.get(ExamineTypeEnum.MEMBER.getServerName());
		List<ExamineFlowMember> flowMembers = collect.get(examineFlow.getFlowId());
		if (flowMembers == null || flowMembers.size() == 0) {
			return null;
		}

		flowMembers.sort(((f1, f2) -> f1.getSort() > f2.getSort() ? 1 : -1));
		ExamineFlowVO examineFlowVO = new ExamineFlowVO();
		examineFlowVO.setExamineType(examineFlow.getExamineType());
		examineFlowVO.setFlowId(examineFlow.getFlowId());
		examineFlowVO.setName(examineFlow.getName());
		examineFlowVO.setType(flowMembers.get(0).getType());
		examineFlowVO.setExamineErrorHandling(examineFlow.getExamineErrorHandling());
		List<SimpleUser> userList = new ArrayList<>();
		List<Long> userIds = flowMembers.stream().map(ExamineFlowMember::getUserId).collect(Collectors.toList());
		List<Long> userIdList = handleUserList(userIds, examineFlow.getExamineId());

		for (Long userId : userIdList) {
			for (UserInfo userInfo : allUserList) {
				if (userInfo.getUserId().equals(userId)) {
					userList.add(toSimPleUser(userInfo));
					break;
				}
			}
		}

		examineFlowVO.setUserList(userList);
		return examineFlowVO;
	}
}
