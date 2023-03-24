package com.megazone.examine.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.Const;
import com.megazone.core.common.JSON;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.examine.entity.ExamineGeneralBO;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.examine.constant.ConditionTypeEnum;
import com.megazone.examine.constant.ExamineConst;
import com.megazone.examine.constant.ExamineTypeEnum;
import com.megazone.examine.entity.BO.ExamineConditionDataSaveBO;
import com.megazone.examine.entity.BO.ExamineConditionSaveBO;
import com.megazone.examine.entity.BO.ExamineDataSaveBO;
import com.megazone.examine.entity.BO.ExamineUserBO;
import com.megazone.examine.entity.PO.ExamineCondition;
import com.megazone.examine.entity.PO.ExamineConditionData;
import com.megazone.examine.entity.PO.ExamineFlow;
import com.megazone.examine.entity.VO.ExamineFlowConditionDataVO;
import com.megazone.examine.entity.VO.ExamineFlowConditionVO;
import com.megazone.examine.entity.VO.ExamineFlowVO;
import com.megazone.examine.mapper.ExamineConditionMapper;
import com.megazone.examine.service.ExamineTypeService;
import com.megazone.examine.service.IExamineConditionDataService;
import com.megazone.examine.service.IExamineConditionService;
import com.megazone.examine.service.IExamineFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-11-13
 */
@Service("conditionService")
public class ExamineConditionServiceImpl extends BaseServiceImpl<ExamineConditionMapper, ExamineCondition> implements IExamineConditionService, ExamineTypeService {

	@Autowired
	private IExamineConditionDataService examineConditionDataService;

	@Autowired
	private IExamineFlowService examineFlowService;

	@Autowired
	private AdminService adminService;

	/**
	 * @param dataSaveBO data
	 * @param flowId     ID
	 */
	@Override
	public void saveExamineFlowData(ExamineDataSaveBO dataSaveBO, Integer flowId, String batchId) {
		List<ExamineConditionSaveBO> conditionList = dataSaveBO.getConditionList();
		for (ExamineConditionSaveBO conditionSaveBO : conditionList) {
			ExamineCondition examineCondition = new ExamineCondition();
			examineCondition.setConditionName(conditionSaveBO.getConditionName());
			examineCondition.setCreateTime(new Date());
			examineCondition.setCreateUserId(UserUtil.getUserId());
			examineCondition.setFlowId(flowId);
			examineCondition.setBatchId(batchId);
			examineCondition.setPriority(conditionSaveBO.getSort());
			/*

			 */
			save(examineCondition);
			/*

			 */
			saveConditionData(conditionSaveBO.getConditionDataList(), examineCondition.getConditionId(), flowId, batchId);
			/*

			 */
			saveExamineFlow(conditionSaveBO.getExamineDataList(), examineCondition.getConditionId(), batchId);
		}
	}

	/**
	 * @param createUserId
	 * @param recordId     ID
	 * @param examineFlow
	 * @return data
	 */
	public ExamineUserBO queryFlowUser(Long createUserId, Integer recordId, ExamineFlow examineFlow) {

		return null;
	}

	/**
	 * @param map
	 * @param batchId batchId
	 */
	@Override
	public void queryFlowListByBatchId(Map<String, Object> map, String batchId) {
		List<ExamineCondition> conditions = lambdaQuery().eq(ExamineCondition::getBatchId, batchId).list();
		Map<Integer, List<ExamineCondition>> collect = conditions.stream().collect(Collectors.groupingBy(ExamineCondition::getFlowId));
		map.put(ExamineTypeEnum.CONDITION.getServerName(), collect);
		List<ExamineConditionData> conditionData = examineConditionDataService.lambdaQuery().eq(ExamineConditionData::getBatchId, batchId).list();
		Map<Integer, List<ExamineConditionData>> listMap = conditionData.stream().collect(Collectors.groupingBy(ExamineConditionData::getConditionId));
		map.put("conditionData", listMap);
	}

	/**
	 * @param examineFlow
	 * @param map         map
	 * @return data
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ExamineFlowVO createFlowInfo(ExamineFlow examineFlow, Map<String, Object> map, List<UserInfo> allUserList, Long ownerUserId) {
		Map<Integer, List<ExamineCondition>> collect = (Map<Integer, List<ExamineCondition>>) map.get(ExamineTypeEnum.CONDITION.getServerName());
		List<ExamineCondition> examineConditions = collect.get(examineFlow.getFlowId());

		Map<Integer, List<ExamineConditionData>> conditionDataMap = (Map<Integer, List<ExamineConditionData>>) map.get("conditionData");

		examineConditions.sort(((f1, f2) -> f1.getPriority() > f2.getPriority() ? 1 : -1));

		Map<Integer, List<ExamineFlow>> flowMap = (Map<Integer, List<ExamineFlow>>) map.get("flow");


		ExamineFlowVO examineFlowVO = new ExamineFlowVO();
		examineFlowVO.setName(examineFlow.getName());
		examineFlowVO.setFlowId(examineFlow.getFlowId());
		examineFlowVO.setExamineType(examineFlow.getExamineType());
		examineFlowVO.setExamineErrorHandling(examineFlow.getExamineErrorHandling());
		List<ExamineFlowConditionVO> conditionList = new ArrayList<>();
		for (ExamineCondition examineCondition : examineConditions) {
			ExamineFlowConditionVO examineFlowConditionVO = new ExamineFlowConditionVO();
			examineFlowConditionVO.setSort(examineCondition.getPriority());
			examineFlowConditionVO.setConditionName(examineCondition.getConditionName());
			List<ExamineConditionData> conditionDataList = conditionDataMap.get(examineCondition.getConditionId());
			if (conditionDataList == null) {
				conditionDataList = new ArrayList<>();
			}

			examineFlowConditionVO.setConditionDataList(conditionDataList.stream().map(collectData -> {
				ExamineFlowConditionDataVO examineFlowConditionDataVO = new ExamineFlowConditionDataVO();
				examineFlowConditionDataVO.setConditionType(collectData.getConditionType());
				examineFlowConditionDataVO.setFieldId(collectData.getFieldId());
				examineFlowConditionDataVO.setName(collectData.getName());
				examineFlowConditionDataVO.setType(collectData.getType());
				examineFlowConditionDataVO.setFieldName(collectData.getFieldName());
				examineFlowConditionDataVO.setBackupValue(collectData.getValue());
				if (Objects.equals(collectData.getConditionType(), ConditionTypeEnum.PERSONNEL.getType())) {
					ExamineGeneralBO examineGeneralBO = JSON.parseObject(collectData.getValue(), ExamineGeneralBO.class);
					Map<String, Object> dataMap = this.convertDataMapByExamineGeneralBO(examineGeneralBO);
					examineFlowConditionDataVO.setValues(dataMap);
				} else {
					examineFlowConditionDataVO.setValues(JSON.parseArray(collectData.getValue(), String.class));
				}
				return examineFlowConditionDataVO;
			}).collect(Collectors.toList()));
			List<ExamineFlow> examineFlows = Optional.ofNullable(flowMap.get(examineCondition.getConditionId())).orElse(new ArrayList<>());

			examineFlows.sort(((f1, f2) -> f1.getSort() > f2.getSort() ? 1 : -1));
			List<ExamineFlowVO> examineFlowVOList = new ArrayList<>();
			for (ExamineFlow flow : examineFlows) {
				ExamineTypeService examineTypeService = ApplicationContextHolder.getBean(ExamineTypeEnum.valueOf(flow.getExamineType()).getServerName());
				examineFlowVOList.add(examineTypeService.createFlowInfo(flow, map, allUserList, ownerUserId));
			}
			examineFlowConditionVO.setExamineDataList(examineFlowVOList);
			conditionList.add(examineFlowConditionVO);
		}
		examineFlowVO.setConditionList(conditionList);
		return examineFlowVO;
	}


	/**
	 * @param examineGeneralBO
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 * @date 2020/12/17 13:50
	 **/
	private Map<String, Object> convertDataMapByExamineGeneralBO(ExamineGeneralBO examineGeneralBO) {
		Map<String, Object> dataMap = new HashMap<>(3);
		dataMap.put(ExamineConst.USER_LIST, UserCacheUtil.getSimpleUsers(examineGeneralBO.getUserList()));
		List<Map<String, Object>> deptMapList = new ArrayList<>();
		List<Integer> deptList = examineGeneralBO.getDeptList();
		if (CollUtil.isNotEmpty(deptList)) {
			deptList.forEach(deptId -> {
				Map<String, Object> deptMap = new HashMap<>(2);
				deptMap.put(ExamineConst.DEPT_ID, deptId);
				deptMap.put(ExamineConst.DEPT_NAME, adminService.queryDeptName(deptId).getData());
				deptMapList.add(deptMap);
			});
		}
		dataMap.put(ExamineConst.DEPT_LIST, deptMapList);
		dataMap.put(ExamineConst.ROLE_LIST, examineGeneralBO.getRoleList());
		return dataMap;
	}


	/**
	 *
	 */
	private void saveExamineFlow(List<ExamineDataSaveBO> dataList, Integer conditionId, String batchId) {
		int i = 1;
		for (ExamineDataSaveBO dataSaveBO : dataList) {
			ExamineTypeEnum examineTypeEnum = ExamineTypeEnum.valueOf(dataSaveBO.getExamineType());
			ExamineFlow IExamineFlow = new ExamineFlow();
			IExamineFlow.setExamineType(dataSaveBO.getExamineType());
			IExamineFlow.setExamineId(0L);
			IExamineFlow.setName(dataSaveBO.getName());
			IExamineFlow.setConditionId(conditionId);
			IExamineFlow.setCreateTime(new Date());
			IExamineFlow.setCreateUserId(UserUtil.getUserId());
			IExamineFlow.setBatchId(batchId);
			IExamineFlow.setExamineErrorHandling(dataSaveBO.getExamineErrorHandling());
			IExamineFlow.setSort(i++);
			examineFlowService.save(IExamineFlow);
			ExamineTypeService examineTypeService = ApplicationContextHolder.getBean(examineTypeEnum.getServerName());
			examineTypeService.saveExamineFlowData(dataSaveBO, IExamineFlow.getFlowId(), batchId);
		}
	}

	private void saveConditionData(List<ExamineConditionDataSaveBO> conditionDataSaveBOList, Integer conditionId, Integer flowId, String batchId) {
		if (conditionDataSaveBOList.size() == 0) {
			return;
		}
		List<ExamineConditionData> conditionDataList = new ArrayList<>();
		for (ExamineConditionDataSaveBO dataSaveBO : conditionDataSaveBOList) {
			ExamineConditionData conditionData = new ExamineConditionData();
			conditionData.setConditionId(conditionId);
			conditionData.setFlowId(flowId);
			conditionData.setConditionType(dataSaveBO.getConditionType());
			Integer fieldId = dataSaveBO.getFieldId();
			conditionData.setFieldId(fieldId != null ? fieldId : 0);
			conditionData.setName(dataSaveBO.getName());
			String fieldName = dataSaveBO.getFieldName();
			conditionData.setFieldName(fieldName != null ? fieldName : "");
			Integer type = dataSaveBO.getType();
			conditionData.setType(type != null ? type : 0);
			conditionData.setBatchId(batchId);
			conditionData.setValue(JSON.toJSONString(dataSaveBO.getValues()));
			conditionDataList.add(conditionData);
		}
		examineConditionDataService.saveBatch(conditionDataList, Const.BATCH_SAVE_SIZE);
	}

	/**
	 * @param examineFlow
	 * @param conditionMap entity
	 * @return data
	 */
	@Override
	public ExamineFlow queryNextExamineFlowByCondition(ExamineFlow examineFlow, Map<String, Object> conditionMap) {

		List<ExamineCondition> conditionList = lambdaQuery().eq(ExamineCondition::getFlowId, examineFlow.getFlowId()).orderByAsc(ExamineCondition::getPriority).list();
		Map<Integer, List<ExamineConditionData>> conditionDataMap = examineConditionDataService.lambdaQuery().eq(ExamineConditionData::getFlowId, examineFlow.getFlowId()).list().stream().collect(Collectors.groupingBy(ExamineConditionData::getConditionId));
		Integer conditionId = null;
		UserInfo userInfo = UserCacheUtil.getUserInfo(Long.parseLong(conditionMap.get(ExamineConst.CREATE_USER_ID).toString()));
		for (ExamineCondition examineCondition : conditionList) {
			List<ExamineConditionData> conditionDataList = conditionDataMap.get(examineCondition.getConditionId());
            /*
              ，
             */
			if (conditionDataList == null || conditionDataList.size() == 0) {
				conditionId = examineCondition.getConditionId();
				break;
			}
			boolean isPass = handleExamineConditionData(conditionDataList, conditionMap, userInfo);
			if (isPass) {
				conditionId = examineCondition.getConditionId();
				break;
			}
		}
		if (conditionId == null) {

			if (examineFlow.getExamineErrorHandling() == 1) {
                /*
                  ，
                 */
				return examineFlowService.queryNextExamineFlow(examineFlow.getExamineId(), examineFlow.getFlowId(), conditionMap);
			}
			/*

			 */
			return examineFlow.setExamineType(6);
		}
		ExamineFlow nextExamineFlow = queryExamineFlow(conditionId, conditionMap);
		if (nextExamineFlow == null) {
            /*
               ，
            */
			return examineFlowService.queryNextExamineFlow(examineFlow.getExamineId(), examineFlow.getFlowId(), conditionMap);
		}
		return nextExamineFlow;
	}

	@Override
	public boolean handleExamineConditionData(List<ExamineConditionData> conditionDataList, Map<String, Object> conditionMap, UserInfo userInfo) {
		boolean isPass = true;
		for (ExamineConditionData conditionData : conditionDataList) {
			ConditionTypeEnum conditionTypeEnum = ConditionTypeEnum.valueOf(conditionData.getConditionType());
			List<String> valueList = new ArrayList<>();
			ExamineGeneralBO examineGeneralBO = null;
			if (ConditionTypeEnum.PERSONNEL.equals(conditionTypeEnum)) {
				String value = "{}";
				if (conditionData.getBackupValue() != null) {
					value = conditionData.getBackupValue();
				} else {
					value = conditionData.getValue();
				}
				examineGeneralBO = JSON.parseObject(JSON.parse(value).toString(), ExamineGeneralBO.class);
				if (examineGeneralBO == null) {
					continue;
				}
			} else {
				valueList = JSON.parseArray(conditionData.getValue(), String.class);
				if (valueList.size() == 0) {
					continue;
				}
			}
			if (!conditionMap.containsKey(conditionData.getFieldName()) && !ConditionTypeEnum.PERSONNEL.equals(conditionTypeEnum)) {
				isPass = false;
				break;
			}
			String fieldValue = Optional.ofNullable(conditionMap.get(conditionData.getFieldName())).orElse("").toString();
			if (StrUtil.isEmpty(fieldValue)) {
				fieldValue = "0";
			}
			switch (conditionTypeEnum) {
				case EQ: {

					BigDecimal value = new BigDecimal(valueList.get(0));

					BigDecimal data = new BigDecimal(fieldValue);
					if (!(data.compareTo(value) == 0)) {
						isPass = false;
					}
					break;
				}
				case GT: {

					BigDecimal value = new BigDecimal(valueList.get(0));

					BigDecimal data = new BigDecimal(fieldValue);
					if (!(data.compareTo(value) > 0)) {
						isPass = false;
					}
					break;
				}

				case GE: {

					BigDecimal value = new BigDecimal(valueList.get(0));

					BigDecimal data = new BigDecimal(fieldValue);
					if (!(data.compareTo(value) >= 0)) {
						isPass = false;
					}
					break;
				}
				case LT: {

					BigDecimal value = new BigDecimal(valueList.get(0));

					BigDecimal data = new BigDecimal(fieldValue);
					if (!(data.compareTo(value) < 0)) {
						isPass = false;
					}
					break;
				}
				case LE: {

					BigDecimal value = new BigDecimal(valueList.get(0));

					BigDecimal data = new BigDecimal(fieldValue);
					if (!(data.compareTo(value) <= 0)) {
						isPass = false;
					}
					break;
				}
				case AMONG: {
                        /*
                          ，4
                          0、  1、  （1  2 ） 2、 （1  2 ）3、
                         */
					if (valueList.size() != 4) {
						throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
					}
					BigDecimal startValue = new BigDecimal(valueList.get(0));
					BigDecimal endValue = new BigDecimal(valueList.get(3));

					BigDecimal data = new BigDecimal(fieldValue);
					boolean start = "2".equals(valueList.get(1)) ? startValue.compareTo(data) <= 0 : startValue.compareTo(data) < 0;
					boolean end = "2".equals(valueList.get(2)) ? data.compareTo(endValue) <= 0 : data.compareTo(endValue) < 0;
					if (!(start && end)) {
						isPass = false;
					}
					break;
				}
				case PERSONNEL: {

					List<String> deptList = examineGeneralBO.getDeptList().stream().map(Object::toString).collect(Collectors.toList());
					if (CollUtil.isNotEmpty(deptList) && !deptList.contains(userInfo.getDeptId().toString())) {
						isPass = false;
					}

					List<String> userList = examineGeneralBO.getUserList().stream().map(Object::toString).collect(Collectors.toList());
					if (CollUtil.isNotEmpty(userList) && !userList.contains(userInfo.getUserId().toString())) {
						isPass = false;
					}

					List<String> roleIds = userInfo.getRoles().stream().map(Object::toString).collect(Collectors.toList());
					//Collections.disjoint(list1,list2) false:  true:
					List<String> roleList = examineGeneralBO.getRoleList().stream().map(Object::toString).collect(Collectors.toList());
					if (CollUtil.isNotEmpty(roleList) && Collections.disjoint(roleList, roleIds)) {
						isPass = false;
					}
					break;
				}
				case CONTAIN: {
					String data = conditionMap.get(conditionData.getFieldName()).toString();
					data = data.replaceAll("\\[", "").replaceAll("\\]", "");
					List<String> dataList = StrUtil.splitTrim(data, Const.SEPARATOR);
					//Collections.disjoint(list1,list2) false:  true:
					if (Collections.disjoint(valueList, dataList)) {
						isPass = false;
					}
					break;
				}
				case EQUALS: {
					String data = conditionMap.get(conditionData.getFieldName()).toString();
					data = data.replaceAll("\\[", "").replaceAll("\\]", "");
					List<String> dataList = StrUtil.splitTrim(data, Const.SEPARATOR);
					//Collections.disjoint(list1,list2) false:  true: \
					boolean isSameSize = valueList.size() == dataList.size();
					if (!isSameSize || !valueList.containsAll(dataList)) {
						isPass = false;
					}
					break;
				}
				default:
					break;
			}
		}
		return isPass;
	}

	/**
	 * @param conditionId ID
	 * @return data
	 */
	private ExamineFlow queryExamineFlow(Integer conditionId, Map<String, Object> conditionMap) {
		ExamineFlow examineFlow = examineFlowService.lambdaQuery().eq(ExamineFlow::getConditionId, conditionId).gt(ExamineFlow::getSort, 0).orderByAsc(ExamineFlow::getSort).last(" limit 1").one();
		if (examineFlow == null) {
			return null;
		}
		if (examineFlow.getExamineType().equals(ExamineTypeEnum.CONDITION.getType())) {
			return queryNextExamineFlowByCondition(examineFlow, conditionMap);
		}
		return examineFlow;
	}
}
