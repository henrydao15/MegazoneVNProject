package com.megazone.examine.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.Const;
import com.megazone.core.common.JSON;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.SimpleDept;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.feign.crm.entity.ExamineField;
import com.megazone.core.feign.crm.entity.SimpleCrmInfo;
import com.megazone.core.feign.crm.service.CrmExamineService;
import com.megazone.core.feign.examine.entity.ExamineConditionDataBO;
import com.megazone.core.feign.oa.OaService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.examine.constant.*;
import com.megazone.examine.entity.BO.ExamineDataSaveBO;
import com.megazone.examine.entity.BO.ExaminePageBO;
import com.megazone.examine.entity.BO.ExaminePreviewBO;
import com.megazone.examine.entity.BO.ExamineSaveBO;
import com.megazone.examine.entity.PO.*;
import com.megazone.examine.entity.VO.*;
import com.megazone.examine.mapper.ExamineMapper;
import com.megazone.examine.mapper.ExamineRecordLogMapper;
import com.megazone.examine.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
public class ExamineServiceImpl extends BaseServiceImpl<ExamineMapper, Examine> implements IExamineService {

	@Autowired
	private IExamineFlowService examineFlowService;

	@Autowired
	private IExamineManagerUserService examineManagerUserService;

	@Autowired
	private IExamineConditionService examineConditionService;

	@Autowired
	private IExamineRecordOptionalService examineRecordOptionalService;

	@Autowired
	private IExamineRecordService examineRecordService;

	@Autowired
	private ExamineRecordLogMapper examineRecordLogMapper;

	@Autowired
	private AdminService adminService;

	@Autowired
	private OaService oaService;

	@Autowired
	private CrmExamineService crmExamineService;


	/**
	 * @param label
	 * @param categoryId ，OA
	 * @return data
	 */
	@Override
	public List<ExamineField> queryField(Integer label, Integer categoryId) {
		ExamineModuleService moduleService = ApplicationContextHolder.getBean(ExamineEnum.parseModule(label).getServerName());
		return moduleService.queryExamineField(label, categoryId);
	}

	/**
	 * @param examinePageBo
	 * @return data
	 */
	@Override
	public BasePage<ExamineVO> queryList(ExaminePageBO examinePageBo) {
		UserInfo user = UserUtil.getUser();
		BasePage<Examine> basePage = this.getBaseMapper().selectPartExaminePage(examinePageBo.parse(), examinePageBo.getLabel(), UserUtil.isAdmin(),
				examinePageBo.getIsPart(), user.getUserId(), user.getDeptId());
		BasePage<ExamineVO> page = new BasePage<>(basePage.getCurrent(), basePage.getSize(), basePage.getTotal(), basePage.isSearchCount());
		List<ExamineVO> examineVoList = new ArrayList<>();
		for (Examine examine : basePage.getList()) {
			ExamineVO examineVO = new ExamineVO();
			BeanUtil.copyProperties(examine, examineVO);
			if (!examinePageBo.getIsPart()) {
				examineVO.setCreateUserName(UserCacheUtil.getUserName(examine.getCreateUserId()));
				examineVO.setUpdateUserName(UserCacheUtil.getUserName(examine.getUpdateUserId()));
				examineVO.setManagerList(examineManagerUserService.queryExamineUserByPage(examineVO.getExamineId()));

				if (StrUtil.isNotEmpty(examine.getUserIds())) {
					Set<Long> userIds = TagUtil.toLongSet(examine.getUserIds());
					if (CollUtil.isNotEmpty(userIds)) {
						List<SimpleUser> listResult = UserCacheUtil.getSimpleUsers(userIds);
						examineVO.setUserList(listResult);
					} else {
						examineVO.setUserList(new ArrayList<>());
					}
				}
				if (StrUtil.isNotEmpty(examine.getDeptIds())) {
					Set<Integer> deptIds = TagUtil.toSet(examine.getDeptIds());
					if (CollUtil.isNotEmpty(deptIds)) {
						Result<List<SimpleDept>> listResult = adminService.queryDeptByIds(deptIds);
						examineVO.setDeptList(listResult.getData());
					} else {
						examineVO.setDeptList(new ArrayList<>());
					}
				}
			}
			examineVoList.add(examineVO);
		}
		page.setList(examineVoList);
		return page;
	}

	/**
	 * @param examineSaveBO data
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Examine addExamine(ExamineSaveBO examineSaveBO) {

		String batchId = IdUtil.simpleUUID();
		/*

		 */
		Examine examine = saveExamine(examineSaveBO, batchId);
		Long examineId = examine.getExamineId();
		/*

		 */

		List<Long> longList = examineSaveBO.getManagerList();
		List<ExamineManagerUser> managerUserList = new ArrayList<>();
		for (int i = 0; i < longList.size(); i++) {
			ExamineManagerUser managerUser = new ExamineManagerUser();
			managerUser.setExamineId(examineId);
			managerUser.setSort(i + 1);
			managerUser.setUserId(longList.get(i));
			managerUserList.add(managerUser);
		}
		examineManagerUserService.saveBatch(managerUserList, Const.BATCH_SAVE_SIZE);

		/*

		 */
		saveExamineFlow(examineSaveBO.getDataList(), examineId, batchId);

		return examine;
	}

	/**
	 * @param label
	 * @return data
	 */
	@Override
	public Examine queryExamineByLabel(Integer label) {
		return lambdaQuery().eq(Examine::getLabel, label).eq(Examine::getStatus, ExamineStatusEnum.PASS.getStatus()).last(" limit 1").one();
	}

	/**
	 * @param examineSaveBO data
	 * @return ID
	 */
	private Examine saveExamine(ExamineSaveBO examineSaveBO, String batchId) {
		Examine oldExamine = null;
		if (examineSaveBO.getExamineId() != null) {
			/*

			 */
			oldExamine = updateStatus(examineSaveBO.getExamineId(), 3, false);
		}
		String examineName = examineSaveBO.getExamineName();
		Integer count;
		if (examineSaveBO.getLabel() == 0) {
			count = lambdaQuery().eq(Examine::getExamineName, examineName).eq(Examine::getLabel, 0).ne(Examine::getStatus, 3).count();
		} else {
			count = lambdaQuery().eq(Examine::getExamineName, examineName).ne(Examine::getLabel, 0).ne(Examine::getStatus, 3).count();
		}
		if (count > 0) {
			throw new CrmException(ExamineCodeEnum.EXAMINE_NAME_NO_USER_ERROR);
		}
		Examine examine = new Examine();

		if (examineSaveBO.getLabel() == 0) {
			examine.setStatus(oldExamine != null && oldExamine.getStatus() == 2 ? 2 : 1);
		} else {
			examine.setStatus(oldExamine != null && oldExamine.getStatus() == 1 ? 1 : 2);
		}
		examine.setExamineName(examineName);
		if (oldExamine != null) {
			examine.setExamineInitId(oldExamine.getExamineInitId());
		}
		examine.setLabel(examineSaveBO.getLabel());
		examine.setExamineIcon(examineSaveBO.getExamineIcon());
		examine.setRecheckType(examineSaveBO.getRecheckType());
		examine.setRemarks(examineSaveBO.getRemarks());
		examine.setCreateTime(new Date());
		examine.setCreateUserId(UserUtil.getUserId());
		examine.setExamineId(examineSaveBO.getExamineId());
		examine.setBatchId(batchId);
		examine.setUpdateUserId(UserUtil.getUserId());
		examine.setUpdateTime(new Date());
		if (examineSaveBO.getUserList() != null) {
			List<Long> list = examineSaveBO.getUserList();
			examine.setUserIds(TagUtil.fromLongSet(list));
		}
		if (examineSaveBO.getDeptList() != null) {
			List<Integer> list = examineSaveBO.getDeptList();
			examine.setDeptIds(TagUtil.fromSet(list));
		}
		save(examine);
		if (examine.getLabel() == 0) {
			if (examineSaveBO.getExamineId() != null) {
				oaService.updateFieldCategoryId(examine.getExamineId(), examineSaveBO.getExamineId());
			} else {
				oaService.saveDefaultField(examine.getExamineId());
			}
		}
		if (oldExamine == null) {
			lambdaUpdate()
					.set(Examine::getExamineInitId, examine.getExamineId())
					.eq(Examine::getExamineId, examine.getExamineId())
					.update();
		}
		return examine;
	}

	/**
	 *
	 */
	private void saveExamineFlow(List<ExamineDataSaveBO> dataList, Long examineId, String batchId) {
		int i = 1;
		for (ExamineDataSaveBO dataSaveBO : dataList) {
			ExamineTypeEnum examineTypeEnum = ExamineTypeEnum.valueOf(dataSaveBO.getExamineType());
			ExamineFlow IExamineFlow = new ExamineFlow();
			IExamineFlow.setExamineType(dataSaveBO.getExamineType());
			IExamineFlow.setExamineId(examineId);
			IExamineFlow.setName(dataSaveBO.getName());
			IExamineFlow.setConditionId(0);
			IExamineFlow.setCreateTime(new Date());
			IExamineFlow.setCreateUserId(UserUtil.getUserId());
			IExamineFlow.setExamineErrorHandling(dataSaveBO.getExamineErrorHandling());
			IExamineFlow.setSort(i++);
			IExamineFlow.setBatchId(batchId);
			examineFlowService.save(IExamineFlow);
			ExamineTypeService examineTypeService = ApplicationContextHolder.getBean(examineTypeEnum.getServerName());
			examineTypeService.saveExamineFlowData(dataSaveBO, IExamineFlow.getFlowId(), batchId);
		}
	}


	/**
	 * @param examineId ID
	 * @param status    1  2  3
	 */
	@Override
	public Examine updateStatus(Long examineId, Integer status, boolean isRequest) {
		if (!ObjectUtil.isAllNotEmpty(examineId, status)) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_VALID);
		}
		if (!Arrays.asList(1, 2, 3).contains(status)) {
			return null;
		}
		Examine examine = getById(examineId);
		if (examine == null) {
			return null;
		}

		if (isRequest && Objects.equals(3, status) && ListUtil.toList(1, 2, 3, 4, 5, 6).contains(examine.getOaType())) {
			throw new CrmException(ExamineCodeEnum.EXAMINE_SPECIAL_TYPE_NOT_DELETE_ERROR);
		}
		/*

		 */
		Integer oldStatus = examine.getStatus();
		if (oldStatus.equals(status)) {
			return examine;
		}

		/*

		 */
		if (Objects.equals(3, oldStatus)) {
			return examine;
		}
		/*

		 */
		if (Objects.equals(1, status)) {
			Integer label = examine.getLabel();
			if (label != 0) {
				Integer count = lambdaQuery().eq(Examine::getLabel, examine.getLabel()).eq(Examine::getStatus, 1).count();
				if (count > 0) {
					throw new CrmException(ExamineCodeEnum.EXAMINE_START_ERROR);
				}
			}
		}

		examine.setStatus(status);
		examine.setUpdateUserId(UserUtil.getUserId());
		updateById(examine);
		return examine;
	}

	/**
	 * @param label
	 */
	@Override
	public List<ExamineFlowVO> previewExamineFlow(Integer label) {
		Examine examine = lambdaQuery().eq(Examine::getLabel, label).eq(Examine::getStatus, 1).last(" limit 1").one();
		return getExamineFlowVOList(examine, null);
	}

	@Override
	public List<ExamineFlowVO> queryExamineFlow(Long examineId) {
		Examine examine = this.getById(examineId);
		return getExamineFlowVOList(examine, null);
	}

	@Override
	public List<ExamineFlowConditionDataVO> previewFiledName(Integer label, Integer recordId, Long examineId) {
		Examine examine;
		if (examineId == null) {
			if (recordId == null) {
				examine = lambdaQuery().eq(Examine::getLabel, label).eq(Examine::getStatus, 1).last(" limit 1").one();
			} else {

				ExamineRecord record = examineRecordService.getById(recordId);
				Long exId = Optional.ofNullable(record).orElse(new ExamineRecord()).getExamineId();
				examine = this.getById(exId);
			}
		} else {
			//OA
			examine = this.getById(examineId);
		}
		if (examine == null) {
			return null;
		}
		List<ExamineFlowVO> examineFlowVOList = getExamineFlowVOList(examine, null);
		List<ExamineFlowConditionDataVO> conditionDataVoS = new ArrayList<>();
		this.getAllFiledName(examineFlowVOList, conditionDataVoS);
		conditionDataVoS.removeIf(conditionDataVo -> ConditionTypeEnum.PERSONNEL.getType().equals(conditionDataVo.getConditionType()));
		return conditionDataVoS;
	}

	/**
	 * @param examineFlowVOList
	 * @param conditionDataVoS
	 * @return void
	 * @date 2020/12/16 11:12
	 **/
	private void getAllFiledName(List<ExamineFlowVO> examineFlowVOList, List<ExamineFlowConditionDataVO> conditionDataVoS) {
		for (ExamineFlowVO examineFlowVO : examineFlowVOList) {
			List<ExamineFlowConditionVO> conditionList = examineFlowVO.getConditionList();
			if (CollUtil.isNotEmpty(conditionList)) {
				for (ExamineFlowConditionVO examineFlowConditionVO : conditionList) {
					List<ExamineFlowConditionDataVO> conditionDataList = examineFlowConditionVO.getConditionDataList();
					if (CollUtil.isNotEmpty(conditionDataList)) {
						for (ExamineFlowConditionDataVO examineFlowConditionDataVO : conditionDataList) {
							examineFlowConditionDataVO.setValues(null);
							//ExamineFlowConditionDataVOequals，
							if (!conditionDataVoS.contains(examineFlowConditionDataVO)) {
								conditionDataVoS.add(examineFlowConditionDataVO);
							}
						}
					}
					List<ExamineFlowVO> examineDataList = examineFlowConditionVO.getExamineDataList();
					if (CollUtil.isNotEmpty(examineDataList)) {
						this.getAllFiledName(examineDataList, conditionDataVoS);
					}
				}
			}
		}
	}

	@Override
	public ExaminePreviewVO previewExamineFlow(ExaminePreviewBO examinePreviewBO) {
		ExaminePreviewVO examinePreviewVO = new ExaminePreviewVO();
		Examine examine;
		Integer recordId = examinePreviewBO.getRecordId();
		if (examinePreviewBO.getExamineId() == null) {
			if (recordId == null) {
				examine = lambdaQuery().eq(Examine::getLabel, examinePreviewBO.getLabel()).eq(Examine::getStatus, 1).last(" limit 1").one();
			} else {
				ExamineRecord record = examineRecordService.getById(recordId);
				Long examineId = Optional.ofNullable(record).orElse(new ExamineRecord()).getExamineId();
				examine = this.getById(examineId);
			}
		} else {
			//OA
			examine = this.getById(examinePreviewBO.getExamineId());
		}
		if (examine == null) {
			return examinePreviewVO;
		}
		examinePreviewVO.setRemarks(examine.getRemarks());
		List<ExamineFlowVO> examineFlowVOList = getExamineFlowVOList(examine, examinePreviewBO.getOwnerUserId());
		Map<String, Object> dataMap = examinePreviewBO.getDataMap();
		List<ExamineFlowVO> examineFlowVoS = new ArrayList<>();
		this.getAllConformExamineFlow(examineFlowVOList, dataMap, examineFlowVoS);
		if (Objects.equals(examinePreviewBO.getStatus(), 1)) {

			this.supplementOptionalUserInfo(examineFlowVoS, examinePreviewBO.getRecordId(), examine.getExamineId());
		}
		examinePreviewVO.setExamineFlowList(examineFlowVoS);
		return examinePreviewVO;
	}


	/**
	 * @param examineFlowVoS
	 * @param recordId
	 * @param examineId
	 * @return void
	 **/
	private void supplementOptionalUserInfo(List<ExamineFlowVO> examineFlowVoS, Integer recordId, Long examineId) {
		for (ExamineFlowVO examineFlowVO : examineFlowVoS) {
			if (ExamineTypeEnum.OPTIONAL.getType().equals(examineFlowVO.getExamineType())) {
				List<ExamineRecordOptional> optionalUsers = examineRecordOptionalService.lambdaQuery()
						.eq(ExamineRecordOptional::getFlowId, examineFlowVO.getFlowId())
						.eq(ExamineRecordOptional::getRecordId, recordId)
						.orderByAsc(ExamineRecordOptional::getSort).list();
				if (CollUtil.isNotEmpty(optionalUsers)) {
					List<Long> userIds = optionalUsers.stream().map(ExamineRecordOptional::getUserId).collect(Collectors.toList());
					List<SimpleUser> simpleUsers = UserCacheUtil.getSimpleUsers(handleUserList(userIds, examineId));
					examineFlowVO.setUserList(simpleUsers);
				}
			}
		}
	}

	@Override
	public List<Long> handleUserList(List<Long> userIds, Long examineId) {
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


	/**
	 * @param examineFlowVOList
	 * @param dataMap
	 * @param examineFlowVoS
	 * @return void
	 **/
	private void getAllConformExamineFlow(List<ExamineFlowVO> examineFlowVOList, Map<String, Object> dataMap, List<ExamineFlowVO> examineFlowVoS) {
		for (ExamineFlowVO examineFlowVO : examineFlowVOList) {
			List<ExamineFlowConditionVO> conditionList = examineFlowVO.getConditionList();
			if (CollUtil.isNotEmpty(conditionList)) {
				for (ExamineFlowConditionVO examineFlowConditionVO : conditionList) {
					List<ExamineFlowConditionDataVO> conditionDataList = examineFlowConditionVO.getConditionDataList();
					if (CollUtil.isNotEmpty(conditionDataList)) {
						List<ExamineConditionData> conditions = new ArrayList<>();
						conditionDataList.forEach(examineFlowConditionDataVO -> {
							ExamineConditionData examineConditionData = new ExamineConditionData();
							BeanUtil.copyProperties(examineFlowConditionDataVO, examineConditionData);
							examineConditionData.setValue(JSON.toJSONString(examineFlowConditionDataVO.getValues()));

							examineConditionData.setBackupValue(JSON.toJSONString(examineFlowConditionDataVO.getBackupValue()));
							conditions.add(examineConditionData);
						});
						Object createUserId = dataMap.get(ExamineConst.CREATE_USER_ID);
						UserInfo userInfo;
						if (createUserId != null) {
							userInfo = UserCacheUtil.getUserInfo(Long.parseLong(createUserId.toString()));
						} else {
							userInfo = UserUtil.getUser();
						}
						boolean isPass = examineConditionService.handleExamineConditionData(conditions, dataMap, userInfo);
						if (isPass) {
							List<ExamineFlowVO> examineDataList = examineFlowConditionVO.getExamineDataList();
							if (CollUtil.isNotEmpty(examineDataList)) {
								this.getAllConformExamineFlow(examineDataList, dataMap, examineFlowVoS);
							}
							break;
						}
					}
				}
			} else {
				examineFlowVoS.add(examineFlowVO);
			}
		}
	}


	/**
	 * @param examine
	 * @param ownerUserId
	 * @return java.util.List<com.megazone.examine.entity.VO.ExamineFlowVO>
	 **/
	private List<ExamineFlowVO> getExamineFlowVOList(Examine examine, Long ownerUserId) {
		if (examine == null) {
			return new ArrayList<>();
		}
		String batchId = examine.getBatchId();
		List<ExamineFlow> examineFlowList = examineFlowService.lambdaQuery()
				.eq(ExamineFlow::getBatchId, batchId)
				.list();
		Map<Integer, List<ExamineFlow>> listMap = examineFlowList.stream().collect(Collectors.groupingBy(ExamineFlow::getConditionId));

		List<ExamineFlow> examineFlows = listMap.remove(0);
		if (CollUtil.isEmpty(examineFlows)) {
			return new ArrayList<>();
		}

		examineFlows.sort(((f1, f2) -> f1.getSort() > f2.getSort() ? 1 : -1));
		Map<String, Object> map = new HashMap<>(10, 1.0f);
		map.put("flow", listMap);

		for (ExamineTypeEnum examineTypeEnum : ExamineTypeEnum.values()) {
			if (examineTypeEnum.equals(ExamineTypeEnum.MANAGER)) {
				continue;
			}
			ExamineTypeService examineTypeService = ApplicationContextHolder.getBean(examineTypeEnum.getServerName());
			examineTypeService.queryFlowListByBatchId(map, batchId);
		}

		List<ExamineFlowVO> examineFlowVOList = new ArrayList<>();
		List<UserInfo> data = adminService.queryUserInfoList().getData();
		for (ExamineFlow examineFlow : examineFlows) {
			ExamineTypeService examineTypeService = ApplicationContextHolder.getBean(ExamineTypeEnum.valueOf(examineFlow.getExamineType()).getServerName());
			ExamineFlowVO flowInfo = examineTypeService.createFlowInfo(examineFlow, map, data, ownerUserId);
			examineFlowVOList.add(flowInfo);
		}

		return examineFlowVOList;
	}


	@Override
	public BasePage<com.megazone.core.feign.oa.entity.ExamineVO> queryOaExamineList(ExaminePageBO examinePageBo) {
		UserInfo user = UserUtil.getUser();
		if (examinePageBo.getCategoryId() != null) {
			Examine examine = getById(examinePageBo.getCategoryId());
			examinePageBo.setCategoryId(examine != null ? examine.getExamineInitId().intValue() : null);
		}
		examinePageBo.setLabel(0);
		BasePage<ExamineRecord> basePage = examineRecordLogMapper.selectRecordLogListByUser(examinePageBo.parse(), examinePageBo, user.getUserId(), user.getRoles());
		BasePage<com.megazone.core.feign.oa.entity.ExamineVO> page = new BasePage<>(basePage.getCurrent(), basePage.getSize(), basePage.getTotal(), basePage.isSearchCount());
		List<com.megazone.core.feign.oa.entity.ExamineVO> examineVoList = new ArrayList<>();
		for (ExamineRecord examineRecord : basePage.getList()) {
			Integer typeId = examineRecord.getTypeId();
			if (typeId == null) {
				continue;
			}
			com.megazone.core.feign.oa.entity.ExamineVO examineVO = oaService.getOaExamineById(typeId).getData();
			examineVO.setCreateTime(examineRecord.getCreateTime());
			examineVoList.add(examineVO);
		}
		page.setList(examineVoList);
		return page;
	}


	@Override
	public BasePage<ExamineRecordInfoVO> queryCrmExamineList(ExaminePageBO examinePageBo) {
		UserInfo user = UserUtil.getUser();
		BasePage<ExamineRecord> basePage = examineRecordLogMapper.selectRecordLogListByUser(examinePageBo.parse(), examinePageBo, user.getUserId(), user.getRoles());
		BasePage<ExamineRecordInfoVO> page = new BasePage<>(basePage.getCurrent(), basePage.getSize(), basePage.getTotal(), basePage.isSearchCount());
		List<ExamineRecordInfoVO> examineRecordInfoVoS = new ArrayList<>();
		for (ExamineRecord examineRecord : basePage.getList()) {
			ExamineRecordInfoVO examineRecordInfoVO = new ExamineRecordInfoVO();
			BeanUtil.copyProperties(examineRecord, examineRecordInfoVO);
			Integer typeId = examineRecord.getTypeId();
			ExamineConditionDataBO examineConditionDataBO = new ExamineConditionDataBO();
			examineConditionDataBO.setLabel(examinePageBo.getLabel());
			examineConditionDataBO.setTypeId(typeId);
			SimpleCrmInfo simpleCrmInfo = crmExamineService.getCrmSimpleInfo(examineConditionDataBO).getData();

			if (simpleCrmInfo == null) {
				continue;
			}
			BeanUtil.copyProperties(simpleCrmInfo, examineRecordInfoVO);
			examineRecordInfoVoS.add(examineRecordInfoVO);
		}
		page.setList(examineRecordInfoVoS);
		return page;
	}

	@Override
	public List<Integer> queryOaExamineIdList(Integer status, Integer categoryId) {
		ExaminePageBO examinePageBo = new ExaminePageBO();
		UserInfo user = UserUtil.getUser();
		examinePageBo.setLabel(0);
		if (status != null && status < 0) {
			status = null;
		}
		examinePageBo.setStatus(status);
		examinePageBo.setCategoryId(categoryId);
		return examineRecordLogMapper.selectRecordTypeIdListByUser(examinePageBo, user.getUserId(), user.getRoles());
	}

	@Override
	public List<Integer> queryCrmExamineIdList(Integer label, Integer status) {
		ExaminePageBO examinePageBo = new ExaminePageBO();
		UserInfo user = UserUtil.getUser();
		examinePageBo.setLabel(label);
		examinePageBo.setStatus(status);
		return examineRecordLogMapper.selectRecordTypeIdListByUser(examinePageBo, user.getUserId(), user.getRoles());
	}
}
