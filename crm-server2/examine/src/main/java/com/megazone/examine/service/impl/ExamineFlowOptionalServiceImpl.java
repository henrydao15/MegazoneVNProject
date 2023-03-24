package com.megazone.examine.service.impl;

import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.examine.constant.ExamineCodeEnum;
import com.megazone.examine.constant.ExamineTypeEnum;
import com.megazone.examine.entity.BO.ExamineDataSaveBO;
import com.megazone.examine.entity.BO.ExamineUserBO;
import com.megazone.examine.entity.PO.ExamineFlow;
import com.megazone.examine.entity.PO.ExamineFlowOptional;
import com.megazone.examine.entity.PO.ExamineRecordOptional;
import com.megazone.examine.entity.VO.ExamineFlowVO;
import com.megazone.examine.mapper.ExamineFlowOptionalMapper;
import com.megazone.examine.service.ExamineTypeService;
import com.megazone.examine.service.IExamineFlowOptionalService;
import com.megazone.examine.service.IExamineRecordOptionalService;
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
@Service("optionalService")
public class ExamineFlowOptionalServiceImpl extends BaseServiceImpl<ExamineFlowOptionalMapper, ExamineFlowOptional> implements IExamineFlowOptionalService, ExamineTypeService {

	@Autowired
	private IExamineRecordOptionalService examineRecordOptionalService;

	@Autowired
	private AdminService adminService;

	/**
	 * @param dataSaveBO data
	 * @param flowId     ID
	 */
	@Override
	public void saveExamineFlowData(ExamineDataSaveBO dataSaveBO, Integer flowId, String batchId) {
		Integer rangeType = dataSaveBO.getRangeType();
		if (Objects.equals(rangeType, 3)) {
			Integer roleId = dataSaveBO.getRoleId();
			if (roleId != null) {
//                adminService.queryUserIdByRealName()
				List<UserInfo> userInfoList = adminService.queryUserInfoList().getData();
				boolean isNoUser = true;
				for (UserInfo userInfo : userInfoList) {
					if (userInfo.getRoles().contains(roleId)) {
						isNoUser = false;
						break;
					}
				}
				if (isNoUser) {
					throw new CrmException(ExamineCodeEnum.EXAMINE_ROLE_NO_USER_ERROR);
				}
				ExamineFlowOptional examineFlowOptional = new ExamineFlowOptional();
				examineFlowOptional.setChooseType(dataSaveBO.getChooseType());
				examineFlowOptional.setFlowId(flowId);
				examineFlowOptional.setRoleId(dataSaveBO.getRoleId());
				examineFlowOptional.setSort(1);
				examineFlowOptional.setType(dataSaveBO.getType());
				examineFlowOptional.setRangeType(dataSaveBO.getRangeType());
				examineFlowOptional.setBatchId(batchId);
				save(examineFlowOptional);
			}
		} else if (Objects.equals(rangeType, 2)) {
			if (dataSaveBO.getUserList().size() == 0) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
			}
			for (Long userId : dataSaveBO.getUserList()) {
				ExamineFlowOptional examineFlowOptional = new ExamineFlowOptional();
				examineFlowOptional.setChooseType(dataSaveBO.getChooseType());
				examineFlowOptional.setFlowId(flowId);
				examineFlowOptional.setUserId(userId);
				examineFlowOptional.setSort(1);
				examineFlowOptional.setType(dataSaveBO.getType());
				examineFlowOptional.setRangeType(dataSaveBO.getRangeType());
				examineFlowOptional.setBatchId(batchId);
				save(examineFlowOptional);
			}
		} else {

			ExamineFlowOptional examineFlowOptional = new ExamineFlowOptional();
			examineFlowOptional.setChooseType(dataSaveBO.getChooseType());
			examineFlowOptional.setFlowId(flowId);
			examineFlowOptional.setSort(1);
			examineFlowOptional.setType(dataSaveBO.getType());
			examineFlowOptional.setRangeType(dataSaveBO.getRangeType());
			examineFlowOptional.setBatchId(batchId);
			save(examineFlowOptional);
		}

	}

	/**
	 * @param createUserId
	 * @param recordId     ID
	 * @param examineFlow
	 * @return data
	 */
	public ExamineUserBO queryFlowUser(Long createUserId, Integer recordId, ExamineFlow examineFlow) {
		List<ExamineFlowOptional> examineFlowOptionalList = lambdaQuery().eq(ExamineFlowOptional::getFlowId, examineFlow.getFlowId()).orderByAsc(ExamineFlowOptional::getSort).list();
		ExamineUserBO examineUserBO = new ExamineUserBO();
		examineUserBO.setType(examineFlowOptionalList.get(0).getType());
		if (recordId != null) {
			/*

			 */
			List<ExamineRecordOptional> recordOptionals = examineRecordOptionalService.lambdaQuery()
					.select(ExamineRecordOptional::getUserId)
					.eq(ExamineRecordOptional::getFlowId, examineFlow.getFlowId())
					.eq(ExamineRecordOptional::getRecordId, recordId)
					.orderByAsc(ExamineRecordOptional::getSort)
					.list();
			List<Long> userIds = recordOptionals.stream().map(ExamineRecordOptional::getUserId).collect(Collectors.toList());
			examineUserBO.setUserList(handleUserList(userIds, examineFlow.getExamineId()));
		}
		return examineUserBO;
	}

	/**
	 * @param map
	 * @param batchId batchId
	 */
	@Override
	public void queryFlowListByBatchId(Map<String, Object> map, String batchId) {
		List<ExamineFlowOptional> continuousSuperiors = lambdaQuery().eq(ExamineFlowOptional::getBatchId, batchId).list();
		Map<Integer, List<ExamineFlowOptional>> collect = continuousSuperiors.stream().collect(Collectors.groupingBy(ExamineFlowOptional::getFlowId));
		map.put(ExamineTypeEnum.OPTIONAL.getServerName(), collect);
	}

	/**
	 * @param examineFlow
	 * @param map         map
	 * @return data
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ExamineFlowVO createFlowInfo(ExamineFlow examineFlow, Map<String, Object> map, List<UserInfo> userInfoList, Long ownerUserId) {
		Map<Integer, List<ExamineFlowOptional>> collect = (Map<Integer, List<ExamineFlowOptional>>) map.get(ExamineTypeEnum.OPTIONAL.getServerName());
		List<ExamineFlowOptional> flowOptionals = collect.get(examineFlow.getFlowId());
		if (flowOptionals == null || flowOptionals.size() == 0) {
			return null;
		}
		ExamineFlowVO examineFlowVO = new ExamineFlowVO();
		examineFlowVO.setChooseType(flowOptionals.get(0).getChooseType());
		examineFlowVO.setName(examineFlow.getName());
		examineFlowVO.setExamineType(examineFlow.getExamineType());
		examineFlowVO.setFlowId(examineFlow.getFlowId());
		examineFlowVO.setType(flowOptionals.get(0).getType());
		examineFlowVO.setRangeType(flowOptionals.get(0).getRangeType());
		examineFlowVO.setExamineErrorHandling(examineFlow.getExamineErrorHandling());
		examineFlowVO.setRoleId(flowOptionals.get(0).getRoleId());
		List<SimpleUser> userList = new ArrayList<>();
		Integer rangeType = flowOptionals.get(0).getRangeType();
		if (Objects.equals(rangeType, 3)) {

			Integer roleId = flowOptionals.get(0).getRoleId();
			if (roleId != null) {
				for (UserInfo userInfo : userInfoList) {
					if (userInfo.getRoles().contains(roleId)) {
						userList.add(toSimPleUser(userInfo));
					}
				}
			}
		} else if (Objects.equals(rangeType, 2)) {
			for (ExamineFlowOptional flowOptional : flowOptionals) {
				for (UserInfo userInfo : userInfoList) {
					if (flowOptional.getUserId().equals(userInfo.getUserId())) {
						userList.add(toSimPleUser(userInfo));
						break;
					}
				}
			}
		}
		examineFlowVO.setUserList(userList);
		return examineFlowVO;
	}


}
