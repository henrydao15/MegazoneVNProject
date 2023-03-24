package com.megazone.hrm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminMessage;
import com.megazone.core.feign.admin.entity.AdminMessageEnum;
import com.megazone.core.feign.admin.service.AdminMessageService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.TransferUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.hrm.common.EmployeeHolder;
import com.megazone.hrm.common.HrmCodeEnum;
import com.megazone.hrm.constant.EmployeeEntryStatus;
import com.megazone.hrm.constant.IsEnum;
import com.megazone.hrm.constant.achievement.AppraisalStatus;
import com.megazone.hrm.constant.achievement.EmployeeAppraisalStatus;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.*;
import com.megazone.hrm.entity.VO.*;
import com.megazone.hrm.mapper.HrmAchievementEmployeeAppraisalMapper;
import com.megazone.hrm.service.*;
import com.megazone.hrm.service.actionrecord.impl.EmployeeAppraisalActionRecordServiceImpl;
import com.megazone.hrm.utils.AchievementUtil;
import com.megazone.hrm.utils.EmployeeCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@Service
public class HrmAchievementEmployeeAppraisalServiceImpl extends BaseServiceImpl<HrmAchievementEmployeeAppraisalMapper, HrmAchievementEmployeeAppraisal> implements IHrmAchievementEmployeeAppraisalService {

	@Autowired
	private HrmAchievementEmployeeAppraisalMapper employeeAppraisalMapper;

	@Autowired
	private IHrmAchievementAppraisalService appraisalService;

	@Autowired
	private IHrmAchievementTableService achievementTableService;

	@Autowired
	private IHrmAchievementSegService achievementSegService;

	@Autowired
	private IHrmAchievementSegItemService achievementSegItemService;

	@Autowired
	private IHrmAchievementEmployeeSegService employeeSegService;

	@Autowired
	private IHrmAchievementEmployeeSegItemService employeeSegItemService;


	@Autowired
	private IHrmAchievementAppraisalTargetConfirmorsService appraisalTargetConfirmorsService;

	@Autowired
	private IHrmAchievementAppraisalEvaluatorsService appraisalEvaluatorsService;

	@Autowired
	private IHrmAchievementEmployeeEvaluatoService employeeEvaluatoService;

	@Autowired
	private IHrmAchievementEmployeeEvaluatoSegService employeeEvaluatoSegService;

	@Autowired
	private IHrmAchievementAppraisalScoreLevelService appraisalScoreLevelService;

	@Autowired
	private IHrmAchievementEmployeeResultConfirmorsService employeeResultConfirmorsService;

	@Resource
	private EmployeeAppraisalActionRecordServiceImpl employeeAppraisalActionRecordService;

	@Autowired
	private AchievementUtil achievementUtil;
	@Autowired
	private IHrmAchievementAppraisalScoreLevelService scoreLevelService;
	@Autowired
	private AdminMessageService adminMessageService;
	@Autowired
	private IHrmEmployeeService employeeService;

	@Override
	public Map<Integer, Integer> queryAppraisalNum() {
		Map<Integer, Integer> map = new HashMap<>(4);
		if (EmployeeHolder.getEmployeeInfo() == null) {
			return map;
		}
		Integer myAppraisalCount = lambdaQuery().eq(HrmAchievementEmployeeAppraisal::getEmployeeId, EmployeeHolder.getEmployeeId()).count();

		map.put(1, myAppraisalCount);
		Integer targetConfirmCount = lambdaQuery().eq(HrmAchievementEmployeeAppraisal::getFollowUpEmployeeId, EmployeeHolder.getEmployeeId())
				.eq(HrmAchievementEmployeeAppraisal::getStatus, EmployeeAppraisalStatus.PENDING_CONFIRMATION.getValue()).count();

		map.put(2, targetConfirmCount);
		List<HrmAchievementEmployeeEvaluato> evaluatoList = employeeEvaluatoService.lambdaQuery().select(HrmAchievementEmployeeEvaluato::getStatus)
				.eq(HrmAchievementEmployeeEvaluato::getEmployeeId, EmployeeHolder.getEmployeeId()).list();
		Map<Integer, List<HrmAchievementEmployeeEvaluato>> collect = evaluatoList.stream().collect(Collectors.groupingBy(HrmAchievementEmployeeEvaluato::getStatus));

		map.put(3, ObjectUtil.isNull(collect.get(0)) ? 0 : collect.get(0).size());
		map.put(5, ObjectUtil.isNull(collect.get(0)) ? 0 : collect.get(0).size());
		map.put(6, ObjectUtil.isNull(collect.get(1)) ? 0 : collect.get(1).size());
		Integer resultConfirmorsCount = employeeResultConfirmorsService.lambdaQuery().eq(HrmAchievementEmployeeResultConfirmors::getEmployeeId, EmployeeHolder.getEmployeeId()).eq(HrmAchievementEmployeeResultConfirmors::getStatus, 0).count();

		map.put(4, resultConfirmorsCount);
		Integer myUnDoAppraisalCount = lambdaQuery().eq(HrmAchievementEmployeeAppraisal::getEmployeeId, EmployeeHolder.getEmployeeId()).eq(HrmAchievementEmployeeAppraisal::getStatus, EmployeeAppraisalStatus.TO_BE_FILLED.getValue()).count();

		map.put(7, myUnDoAppraisalCount);
		Integer myDoAppraisalCount = lambdaQuery().eq(HrmAchievementEmployeeAppraisal::getEmployeeId, EmployeeHolder.getEmployeeId()).ne(HrmAchievementEmployeeAppraisal::getStatus, EmployeeAppraisalStatus.TO_BE_FILLED.getValue()).count();

		map.put(8, myDoAppraisalCount);
		return map;
	}

	@Override
	public BasePage<QueryMyAppraisalVO> queryMyAppraisal(BasePageBO basePageBO) {
		if (EmployeeHolder.getEmployeeInfo() == null) {
			return new BasePage<>();
		}
		BasePage<QueryMyAppraisalVO> page = employeeAppraisalMapper.queryMyAppraisal(basePageBO.parse(), EmployeeHolder.getEmployeeId(), basePageBO.getStatus());
		page.getList().forEach(queryMyAppraisalVO -> {
			queryMyAppraisalVO.setAppraisalTime(AchievementUtil.appraisalTimeFormat(queryMyAppraisalVO.getStartTime(), queryMyAppraisalVO.getEndTime()));
		});
		return page;
	}

	@Override
	public BasePage<TargetConfirmListVO> queryTargetConfirmList(BasePageBO basePageBO) {
		if (EmployeeHolder.getEmployeeInfo() == null) {
			return new BasePage<>();
		}
		return employeeAppraisalMapper.queryTodoAppraisalByStatus(basePageBO.parse(), EmployeeHolder.getEmployeeId(), EmployeeAppraisalStatus.PENDING_CONFIRMATION.getValue(), basePageBO.getSearch(), basePageBO.getAppraisalId());
	}

	@Override
	public BasePage<EvaluatoListVO> queryEvaluatoList(EvaluatoListBO evaluatoListBO) {
		if (EmployeeHolder.getEmployeeInfo() == null) {
			return new BasePage<>();
		}
		return employeeAppraisalMapper.evaluatoList(evaluatoListBO.parse(), EmployeeHolder.getEmployeeId(), evaluatoListBO);
	}

	@Override
	public EmployeeAppraisalDetailVO queryEmployeeAppraisalDetail(Integer employeeAppraisalId) {
		EmployeeAppraisalDetailVO employeeAppraisalDetailVO = appraisalService.queryEmployeeDetail(employeeAppraisalId);
		HrmAchievementEmployeeAppraisal employeeAppraisal = getById(employeeAppraisalId);
		Integer appraisalId = employeeAppraisal.getAppraisalId();
		HrmAchievementAppraisal appraisal = appraisalService.getById(appraisalId);
		Integer tableId = appraisal.getTableId();
		AchievementTableTempVO achievementTableTempVO = new AchievementTableTempVO();
		HrmAchievementTable achievementTable = achievementTableService.getById(tableId);
		BeanUtil.copyProperties(achievementTable, achievementTableTempVO);
		List<HrmAchievementSeg> achievementSegs = achievementSegService.lambdaQuery().eq(HrmAchievementSeg::getTableId, tableId)
				.orderByAsc(HrmAchievementSeg::getSort).list();
		achievementSegs.forEach(seg -> {
			List<HrmAchievementSegItem> items = achievementSegItemService.lambdaQuery().eq(HrmAchievementSegItem::getSegId, seg.getSegId())
					.orderByAsc(HrmAchievementSegItem::getSort).list();
			seg.setItems(items);
		});
		Map<Integer, List<HrmAchievementSeg>> collect = achievementSegs.stream().collect(Collectors.groupingBy(HrmAchievementSeg::getIsFixed));
		achievementTableTempVO.setFixedSegListTemp(CollUtil.isNotEmpty(collect.get(IsEnum.YES.getValue())) ? collect.get(IsEnum.YES.getValue()) : new ArrayList<>());
		achievementTableTempVO.setNoFixedSegListTemp(CollUtil.isNotEmpty(collect.get(IsEnum.NO.getValue())) ? collect.get(IsEnum.NO.getValue()) : new ArrayList<>());
		employeeAppraisalDetailVO.setTableTemp(achievementTableTempVO);
		Integer count = employeeEvaluatoService.lambdaQuery().eq(HrmAchievementEmployeeEvaluato::getEmployeeAppraisalId, employeeAppraisalId)
				.eq(HrmAchievementEmployeeEvaluato::getStatus, IsEnum.YES.getValue()).count();
		int isWrite = 0;
		if (employeeAppraisal.getStatus() < 2 || (employeeAppraisal.getStatus() == 2 && count == 0)) {
			isWrite = 1;
		}
		employeeAppraisalDetailVO.setIsWrite(isWrite);
		return employeeAppraisalDetailVO;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void writeAppraisal(WriteAppraisalBO writeAppraisalBO) {
		Integer employeeId = EmployeeHolder.getEmployeeId();
		Integer employeeAppraisalId = writeAppraisalBO.getEmployeeAppraisalId();

		List<Integer> oldSegIds = employeeSegService.lambdaQuery().select(HrmAchievementEmployeeSeg::getSegId)
				.eq(HrmAchievementEmployeeSeg::getEmployeeId, employeeId)
				.eq(HrmAchievementEmployeeSeg::getEmployeeAppraisalId, employeeAppraisalId)
				.list()
				.stream().map(HrmAchievementEmployeeSeg::getSegId).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(oldSegIds)) {
			employeeSegService.removeByIds(oldSegIds);
			employeeSegItemService.lambdaUpdate().in(HrmAchievementEmployeeSegItem::getSegId, oldSegIds).remove();
		}
		employeeEvaluatoService.lambdaUpdate().eq(HrmAchievementEmployeeEvaluato::getEmployeeAppraisalId, employeeAppraisalId).remove();

		List<WriteAppraisalBO.SegListBean> segList = writeAppraisalBO.getSegList();
		List<HrmAchievementEmployeeSegItem> itemList = new ArrayList<>();
		for (int i = 0; i < segList.size(); i++) {
			WriteAppraisalBO.SegListBean segBean = segList.get(i);
			HrmAchievementEmployeeSeg hrmAchievementEmployeeSeg = BeanUtil.copyProperties(segBean, HrmAchievementEmployeeSeg.class);
			hrmAchievementEmployeeSeg.setEmployeeAppraisalId(employeeAppraisalId);
			hrmAchievementEmployeeSeg.setEmployeeId(employeeId);
			hrmAchievementEmployeeSeg.setSort(i + 1);
			employeeSegService.save(hrmAchievementEmployeeSeg);
			List<HrmAchievementEmployeeSegItem> items = TransferUtil.transferList(segBean.getItems(), HrmAchievementEmployeeSegItem.class);
			for (int j = 0; j < items.size(); j++) {
				HrmAchievementEmployeeSegItem item = items.get(j);
				item.setSegId(hrmAchievementEmployeeSeg.getSegId());
				item.setSort(j + 1);
				itemList.add(item);
			}
		}
		employeeSegItemService.saveBatch(itemList);
		if (writeAppraisalBO.getIsDraft() == 1) {

			HrmAchievementEmployeeAppraisal employeeAppraisal = getById(employeeAppraisalId);
			employeeAppraisal.setIsDraft(1);
			updateById(employeeAppraisal);
		} else {
			employeeAppraisalActionRecordService.submitAppraisalRecord(employeeAppraisalId);

			HrmAchievementEmployeeAppraisal employeeAppraisal = getById(employeeAppraisalId);

			employeeAppraisal.setIsDraft(0);
			updateById(employeeAppraisal);
			employeeAppraisal.setStatus(EmployeeAppraisalStatus.TO_BE_FILLED.getValue());
			addTargetConfirm(employeeAppraisal);

			HrmAchievementAppraisal hrmAchievementAppraisal = appraisalService.getById(employeeAppraisal.getAppraisalId());
			hrmAchievementAppraisal.setAppraisalSteps(0);
			appraisalService.updateById(hrmAchievementAppraisal);
		}
	}

	/**
	 *
	 */
	public void addTargetConfirm(HrmAchievementEmployeeAppraisal employeeAppraisal) {
		int sort;
		if (employeeAppraisal.getStatus().equals(EmployeeAppraisalStatus.TO_BE_FILLED.getValue())) {
			employeeAppraisal.setStatus(EmployeeAppraisalStatus.PENDING_CONFIRMATION.getValue());
			sort = 1;
		} else {
			sort = employeeAppraisal.getFollowSort() + 1;
		}
		Optional<HrmAchievementAppraisalTargetConfirmors> targetConfirmorsOpt = appraisalTargetConfirmorsService.lambdaQuery()
				.eq(HrmAchievementAppraisalTargetConfirmors::getAppraisalId, employeeAppraisal.getAppraisalId())
				.eq(HrmAchievementAppraisalTargetConfirmors::getSort, sort).oneOpt();
		if (targetConfirmorsOpt.isPresent()) {
			HrmAchievementAppraisalTargetConfirmors targetConfirmors = targetConfirmorsOpt.get();

			Integer employeeId = achievementUtil.findEmployeeIdByType(targetConfirmors.getType(), employeeAppraisal.getEmployeeId(), targetConfirmors.getEmployeeId(), "");
			employeeAppraisal.setFollowUpEmployeeId(employeeId);
			employeeAppraisal.setFollowSort(sort);
			updateById(employeeAppraisal);

			HrmAchievementAppraisal hrmAchievementAppraisal = appraisalService.getById(employeeAppraisal.getAppraisalId());
			sendMessage(EmployeeCacheUtil.getUserId(employeeAppraisal.getEmployeeId()), employeeId, employeeAppraisal.getEmployeeAppraisalId(), AdminMessageEnum.HRM_EMPLOYEE_APPRAISAL_CONFIRMATION.getType(), hrmAchievementAppraisal.getAppraisalName());
		} else {

			// ,
			employeeAppraisal.setStatus(EmployeeAppraisalStatus.TO_BE_ASSESSED.getValue());
			HrmAchievementAppraisal appraisal = appraisalService.getById(employeeAppraisal.getAppraisalId());
			if (appraisal.getStatus() >= AppraisalStatus.UNDER_EVALUATION.getValue()) {
				addEvaluato(employeeAppraisal, 1);
			} else {
				updateById(employeeAppraisal);
				Integer unAssessedCount = lambdaQuery().eq(HrmAchievementEmployeeAppraisal::getStatus, EmployeeAppraisalStatus.TO_BE_ASSESSED.getValue()).eq(HrmAchievementEmployeeAppraisal::getAppraisalId, employeeAppraisal.getAppraisalId()).count();
				Integer count = lambdaQuery().eq(HrmAchievementEmployeeAppraisal::getAppraisalId, employeeAppraisal.getAppraisalId()).count();
				if (count.equals(unAssessedCount)) {
					sendSponsorMessage(UserUtil.getUserId(), appraisal.getCreateUserId(), appraisal.getAppraisalId(), AdminMessageEnum.HRM_APPRAISAL_WRITE_COMPLETE.getType(), appraisal.getAppraisalName());
				}
			}
		}
	}

	/**
	 *
	 */
	private void addEvaluato(HrmAchievementEmployeeAppraisal employeeAppraisal, Integer type) {
		employeeAppraisal.setStatus(EmployeeAppraisalStatus.TO_BE_ASSESSED.getValue());
		HrmAchievementAppraisal appraisal = appraisalService.getById(employeeAppraisal.getAppraisalId());
		Optional<HrmAchievementEmployeeEvaluato> lastEmployeeEvaluatoOpt = employeeEvaluatoService.lambdaQuery()
				.eq(HrmAchievementEmployeeEvaluato::getEmployeeAppraisalId, employeeAppraisal.getEmployeeAppraisalId())
				.orderByDesc(HrmAchievementEmployeeEvaluato::getSort).last("limit 1").oneOpt();
		int sort = lastEmployeeEvaluatoOpt.map(hrmAchievementEmployeeEvaluato -> hrmAchievementEmployeeEvaluato.getSort() + 1).orElse(1);
		Optional<HrmAchievementAppraisalEvaluators> appraisalEvaluatorsOpt = appraisalEvaluatorsService.lambdaQuery()
				.eq(HrmAchievementAppraisalEvaluators::getAppraisalId, employeeAppraisal.getAppraisalId())
				.eq(HrmAchievementAppraisalEvaluators::getSort, sort).oneOpt();
		if (appraisalEvaluatorsOpt.isPresent()) {
			HrmAchievementAppraisalEvaluators evaluators = appraisalEvaluatorsOpt.get();
			Integer evaluatoEmployeeId = achievementUtil.findEmployeeIdByType(evaluators.getType(), employeeAppraisal.getEmployeeId(), evaluators.getEmployeeId(), "");
			HrmAchievementEmployeeEvaluato employeeEvaluato = new HrmAchievementEmployeeEvaluato();
			employeeEvaluato.setEmployeeAppraisalId(employeeAppraisal.getEmployeeAppraisalId());
			employeeEvaluato.setEmployeeId(evaluatoEmployeeId);
			employeeEvaluato.setWeight(evaluators.getWeight());
			employeeEvaluato.setSort(sort);
			employeeEvaluato.setAppraisalId(employeeAppraisal.getAppraisalId());
			employeeEvaluatoService.save(employeeEvaluato);
			employeeAppraisal.setFollowUpEmployeeId(evaluatoEmployeeId);
			employeeAppraisal.setFollowSort(sort);
			updateById(employeeAppraisal);

			if (appraisal.getStatus() >= AppraisalStatus.UNDER_EVALUATION.getValue()) {
				HrmEmployee hrmEmployee = employeeService.getById(employeeAppraisal.getEmployeeId());
				HrmAchievementAppraisal hrmAchievementAppraisal = appraisalService.getById(employeeAppraisal.getAppraisalId());
				sendMessage(EmployeeCacheUtil.getUserId(employeeAppraisal.getEmployeeId()), evaluatoEmployeeId, employeeAppraisal.getEmployeeAppraisalId(), AdminMessageEnum.HRM_EMPLOYEE_APPRAISAL_ASSESSED.getType(), hrmAchievementAppraisal.getAppraisalName());
			}
		} else {
			Integer count = lambdaQuery().eq(HrmAchievementEmployeeAppraisal::getAppraisalId, appraisal.getAppraisalId()).count();

			employeeAppraisal.setStatus(EmployeeAppraisalStatus.TO_BE_CONFIRMED.getValue());
			updateById(employeeAppraisal);
			Integer unConfirmedCount = lambdaQuery().eq(HrmAchievementEmployeeAppraisal::getStatus, EmployeeAppraisalStatus.TO_BE_CONFIRMED.getValue()).eq(HrmAchievementEmployeeAppraisal::getAppraisalId, appraisal.getAppraisalId()).count();
			if (appraisal.getStatus() == AppraisalStatus.CONFIRMING.getValue()) {
				appraisalService.computeScore(employeeAppraisal);
				if (count.equals(unConfirmedCount)) {
					List<Integer> resultConfirmorsList = Lists.newArrayList(TagUtil.toSet(appraisal.getResultConfirmors()));

					resultConfirmorsList.forEach(employeeId -> {
						HrmAchievementEmployeeResultConfirmors employeeResultConfirmors = new HrmAchievementEmployeeResultConfirmors();
						HrmEmployee employee = employeeService.getById(employeeId);

						if (employee.getIsDel() == 1 || employee.getEntryStatus() == EmployeeEntryStatus.ALREADY_LEAVE.getValue()) {
							throw new CrmException(HrmCodeEnum.RESULT_CONFIRM_EMPLOYEE_NO_EXIST, employee.getEmployeeName());
						}
						HrmAchievementEmployeeResultConfirmors esultConfirmors = employeeResultConfirmorsService.lambdaQuery().
								eq(HrmAchievementEmployeeResultConfirmors::getEmployeeId, employeeId).eq(HrmAchievementEmployeeResultConfirmors::getAppraisalId, appraisal.getAppraisalId()).one();
						if (null == esultConfirmors) {
							employeeResultConfirmors.setEmployeeId(employeeId);
							employeeResultConfirmors.setAppraisalId(appraisal.getAppraisalId());
							employeeResultConfirmorsService.save(employeeResultConfirmors);
						}

						sendMessage(UserUtil.getUserId(), employeeId, appraisal.getAppraisalId(), AdminMessageEnum.HRM_EMPLOYEE_APPRAISAL_CONFIRMED.getType(), appraisal.getAppraisalName());
					});
				}
			} else {
				// ，
				if (count.equals(unConfirmedCount)) {
					sendSponsorMessage(UserUtil.getUserId(), appraisal.getCreateUserId(), appraisal.getAppraisalId(), AdminMessageEnum.HRM_APPRAISAL_ASSESSED_COMPLETE.getType(), appraisal.getAppraisalName());
				}
			}
		}
		if (type == 2) {   // type   1 2
			employeeAppraisalActionRecordService.submitEvaluatoRecord(employeeAppraisal.getEmployeeAppraisalId());
		}
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void targetConfirm(TargetConfirmBO targetConfirmBO) {
		HrmAchievementEmployeeAppraisal employeeAppraisal = getById(targetConfirmBO.getEmployeeAppraisalId());
		if (!employeeAppraisal.getStatus().equals(EmployeeAppraisalStatus.PENDING_CONFIRMATION.getValue()) ||
				!employeeAppraisal.getFollowUpEmployeeId().equals(EmployeeHolder.getEmployeeId())) {
			throw new CrmException(HrmCodeEnum.YOU_ARE_NOT_THE_CURRENT_REVIEWER);
		}
		if (targetConfirmBO.getStatus() == 1) {
			addTargetConfirm(employeeAppraisal);
			employeeAppraisalActionRecordService.confirmAppraisalRecord(employeeAppraisal.getEmployeeAppraisalId());
		} else {
			rejectTarget(employeeAppraisal, targetConfirmBO.getRejectReason());
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void resultEvaluato(ResultEvaluatoBO resultEvaluatoBO) {
		HrmAchievementEmployeeAppraisal employeeAppraisal = getById(resultEvaluatoBO.getEmployeeAppraisalId());
		HrmAchievementAppraisal hrmAchievementAppraisal = appraisalService.getById(employeeAppraisal.getAppraisalId());
		if (!employeeAppraisal.getStatus().equals(EmployeeAppraisalStatus.TO_BE_ASSESSED.getValue()) ||
				!employeeAppraisal.getFollowUpEmployeeId().equals(EmployeeHolder.getEmployeeId())) {
			throw new CrmException(HrmCodeEnum.YOU_ARE_NOT_THE_CURRENT_REVIEWER);
		}
		HrmAchievementEmployeeEvaluato employeeEvaluato = employeeEvaluatoService.lambdaQuery()
				.eq(HrmAchievementEmployeeEvaluato::getEmployeeAppraisalId, employeeAppraisal.getEmployeeAppraisalId())
				.eq(HrmAchievementEmployeeEvaluato::getEmployeeId, EmployeeHolder.getEmployeeId())
				.orderByDesc(HrmAchievementEmployeeEvaluato::getSort).last("limit 1").one();
		if (resultEvaluatoBO.getStatus() == 1) {
			BeanUtil.copyProperties(resultEvaluatoBO, employeeEvaluato);
			employeeEvaluatoService.updateById(employeeEvaluato);

			employeeEvaluatoSegService.lambdaUpdate().eq(HrmAchievementEmployeeEvaluatoSeg::getEmployeeEvaluatoId, employeeEvaluato.getEmployeeEvaluatoId())
					.remove();
			List<ResultEvaluatoBO.ResultEvaluatoSegBO> resultEvaluatoSegBOList = resultEvaluatoBO.getResultEvaluatoSegBOList();
			List<HrmAchievementEmployeeEvaluatoSeg> employeeEvaluatoSegList = new ArrayList<>();
			resultEvaluatoSegBOList.forEach(evaluatoSegBO -> {
				HrmAchievementEmployeeEvaluatoSeg employeeEvaluatoSeg = BeanUtil.copyProperties(evaluatoSegBO, HrmAchievementEmployeeEvaluatoSeg.class);
				employeeEvaluatoSeg.setEmployeeId(EmployeeHolder.getEmployeeId());
				employeeEvaluatoSeg.setEmployeeAppraisalId(employeeAppraisal.getEmployeeAppraisalId());
				employeeEvaluatoSeg.setEmployeeEvaluatoId(employeeEvaluato.getEmployeeEvaluatoId());
				employeeEvaluatoSegList.add(employeeEvaluatoSeg);
			});
			employeeEvaluatoSegService.saveBatch(employeeEvaluatoSegList);
			addEvaluato(employeeAppraisal, 2);
		} else if (resultEvaluatoBO.getStatus() == 2) {
			rejectTarget(employeeAppraisal, resultEvaluatoBO.getRejectReason());
		} else if (resultEvaluatoBO.getStatus() == 3) {
			HrmAchievementEmployeeEvaluato lastEmployeeEvaluato = employeeEvaluatoService.lambdaQuery()
					.eq(HrmAchievementEmployeeEvaluato::getEmployeeAppraisalId, employeeAppraisal.getEmployeeAppraisalId())
					.eq(HrmAchievementEmployeeEvaluato::getSort, employeeEvaluato.getSort() - 1).last("limit 1").one();
			if (lastEmployeeEvaluato != null) {

				sendContentMessage(EmployeeCacheUtil.getUserId(employeeAppraisal.getEmployeeId()), lastEmployeeEvaluato.getEmployeeId(), resultEvaluatoBO.getEmployeeAppraisalId(), AdminMessageEnum.HRM_EMPLOYEE_APPRAISAL_ASSESSED_REJECT.getType(), hrmAchievementAppraisal.getAppraisalName(), UserUtil.getUser().getRealname());
			}
			rejectEvaluato(employeeAppraisal, resultEvaluatoBO.getRejectReason(), employeeEvaluato);
		}
	}

	/**
	 *
	 */
	/**
	 * @param employeeAppraisal
	 * @param rejectReason
	 * @param employeeEvaluato
	 */
	private void rejectEvaluato(HrmAchievementEmployeeAppraisal employeeAppraisal, String rejectReason, HrmAchievementEmployeeEvaluato employeeEvaluato) {
		HrmAchievementEmployeeEvaluato lastEmployeeEvaluato = employeeEvaluatoService.lambdaQuery()
				.eq(HrmAchievementEmployeeEvaluato::getEmployeeAppraisalId, employeeAppraisal.getEmployeeAppraisalId())
				.eq(HrmAchievementEmployeeEvaluato::getSort, employeeEvaluato.getSort() - 1).last("limit 1").one();
		if (lastEmployeeEvaluato == null) {
			throw new CrmException(HrmCodeEnum.LAST_RATING_IS_EMPTY);
		}
		lastEmployeeEvaluato.setStatus(0);
		employeeEvaluatoService.updateById(lastEmployeeEvaluato);
		employeeEvaluatoSegService.lambdaUpdate().set(HrmAchievementEmployeeEvaluatoSeg::getStatus, 0).eq(HrmAchievementEmployeeEvaluatoSeg::getEmployeeEvaluatoId, lastEmployeeEvaluato.getEmployeeEvaluatoId()).update();
		employeeEvaluatoService.removeById(employeeEvaluato.getEmployeeEvaluatoId());
		employeeAppraisal.setFollowSort(lastEmployeeEvaluato.getSort());
		employeeAppraisal.setFollowUpEmployeeId(lastEmployeeEvaluato.getEmployeeId());
		updateById(employeeAppraisal);
		HrmAchievementAppraisal appraisal = appraisalService.getById(employeeAppraisal.getAppraisalId());
		employeeAppraisalActionRecordService.reject(appraisal.getAppraisalName(), employeeAppraisal.getEmployeeAppraisalId(), 3, rejectReason);
	}

	/**
	 *
	 */
	private void rejectTarget(HrmAchievementEmployeeAppraisal employeeAppraisal, String reason) {
		employeeAppraisal.setStatus(EmployeeAppraisalStatus.TO_BE_FILLED.getValue());
		HrmAchievementAppraisal appraisal = appraisalService.getById(employeeAppraisal.getAppraisalId());

		appraisal.setAppraisalSteps(0);
		appraisalService.updateById(appraisal);

		sendContentMessage(UserUtil.getUserId(), employeeAppraisal.getEmployeeId(), employeeAppraisal.getEmployeeAppraisalId(), AdminMessageEnum.HRM_EMPLOYEE_APPRAISAL_WRITE_REJECT.getType(), appraisal.getAppraisalName(), UserUtil.getUser().getRealname());
		employeeEvaluatoService.lambdaUpdate()
				.eq(HrmAchievementEmployeeEvaluato::getEmployeeAppraisalId, employeeAppraisal.getEmployeeAppraisalId())
				.remove();
		employeeEvaluatoSegService.lambdaUpdate()
				.eq(HrmAchievementEmployeeEvaluatoSeg::getEmployeeAppraisalId, employeeAppraisal.getEmployeeAppraisalId())
				.remove();
		updateById(employeeAppraisal);
		employeeAppraisalActionRecordService.reject(appraisal.getAppraisalName(), employeeAppraisal.getEmployeeAppraisalId(), 2, reason);
	}

	@Override
	public BasePage<ResultConfirmListVO> queryResultConfirmList(BasePageBO basePageBO) {
		if (EmployeeHolder.getEmployeeInfo() == null) {
			return new BasePage<>();
		}
		BasePage<ResultConfirmListVO> page = employeeAppraisalMapper.queryResultConfirmList(basePageBO.parse(), EmployeeHolder.getEmployeeId(), basePageBO.getSearch());
		page.getList().forEach(resultConfirmListVO -> {
			resultConfirmListVO.setAppraisalTime(AchievementUtil.appraisalTimeFormat(resultConfirmListVO.getStartTime(), resultConfirmListVO.getEndTime()));
			List<ResultConfirmListVO.ScoreLevelsBean> scoreLevels = new ArrayList<>();
			List<Map<String, Object>> mapList = employeeAppraisalMapper.queryScoreLevels(resultConfirmListVO.getAppraisalId());
			mapList.forEach(map -> {
				scoreLevels.add(BeanUtil.mapToBean(map, ResultConfirmListVO.ScoreLevelsBean.class, true));
			});
			resultConfirmListVO.setScoreLevels(scoreLevels);
		});
		return page;
	}

	@Override
	public ResultConfirmByAppraisalIdVO queryResultConfirmByAppraisalId(String appraisalId) {
		ResultConfirmByAppraisalIdVO resultConfirmByAppraisalIdVO = new ResultConfirmByAppraisalIdVO();
		Map<String, Object> appraisalIdInfoMap = employeeAppraisalMapper.queryAppraisalIdInfo(appraisalId);
		BeanUtil.fillBeanWithMap(appraisalIdInfoMap, resultConfirmByAppraisalIdVO, true);
		List<HrmAchievementAppraisalScoreLevel> scoreLevelList = appraisalScoreLevelService.lambdaQuery().eq(HrmAchievementAppraisalScoreLevel::getAppraisalId, appraisalId)
				.orderByAsc(HrmAchievementAppraisalScoreLevel::getSort).list();
		List<ResultConfirmByAppraisalIdVO.LevelDetailInfosBean> levelDetailInfosBeanList = new ArrayList<>();
		AtomicInteger hasInRange = new AtomicInteger(1);
		scoreLevelList.forEach(scoreLevel -> {
			ResultConfirmByAppraisalIdVO.LevelDetailInfosBean levelDetailInfosBean = new ResultConfirmByAppraisalIdVO.LevelDetailInfosBean();
			BeanUtil.copyProperties(scoreLevel, levelDetailInfosBean);
			List<Map<String, Object>> mapList = employeeAppraisalMapper.queryEmployeeByLevelId(scoreLevel.getLevelId(), scoreLevel.getAppraisalId());
			List<ResultConfirmByAppraisalIdVO.EmployeesBean> employeesBeanList = new ArrayList<>();
			mapList.forEach(map -> {
				employeesBeanList.add(BeanUtil.mapToBean(map, ResultConfirmByAppraisalIdVO.EmployeesBean.class, true));
			});
			levelDetailInfosBean.setEmployees(employeesBeanList);
			levelDetailInfosBean.setActualNum(employeesBeanList.size());

			BigDecimal minNum = new BigDecimal(scoreLevel.getMinNum());
			BigDecimal maxNum = new BigDecimal(scoreLevel.getMaxNum());
			BigDecimal actualWeight = NumberUtil.round(((double) employeesBeanList.size() / resultConfirmByAppraisalIdVO.getTotalNum()) * 100, 2);
			if (actualWeight.compareTo(minNum) < 0 || actualWeight.compareTo(maxNum) > 0) {
				hasInRange.set(0);
			}
			levelDetailInfosBean.setActualWeight(actualWeight);
			levelDetailInfosBeanList.add(levelDetailInfosBean);
		});
		resultConfirmByAppraisalIdVO.setLevelDetailInfos(levelDetailInfosBeanList);
		resultConfirmByAppraisalIdVO.setHasInRange(hasInRange.get());
		return resultConfirmByAppraisalIdVO;
	}

	@Override
	public void updateScoreLevel(UpdateScoreLevelBO updateScoreLevelBO) {
		lambdaUpdate().set(HrmAchievementEmployeeAppraisal::getScore, updateScoreLevelBO.getScore())
				.set(HrmAchievementEmployeeAppraisal::getLevelId, updateScoreLevelBO.getLevelId())
				.eq(HrmAchievementEmployeeAppraisal::getEmployeeAppraisalId, updateScoreLevelBO.getEmployeeAppraisalId())
				.update();
	}

	@Override
	public void resultConfirm(String appraisalId) {
		Integer waitingNum = employeeAppraisalMapper.queryWaitingNum(appraisalId);
		Integer totalNum = employeeAppraisalMapper.queryTotalNum(appraisalId);
		if (!totalNum.equals(waitingNum)) {
			throw new CrmException(HrmCodeEnum.RESULTS_ASSESSMENT_NOT_COMPLETED);
		}
		HrmAchievementAppraisal appraisal = appraisalService.getById(appraisalId);
		if (appraisal.getIsForce() == 1) {

			List<HrmAchievementAppraisalScoreLevel> scoreLevelList = appraisalScoreLevelService.lambdaQuery()
					.eq(HrmAchievementAppraisalScoreLevel::getAppraisalId, appraisalId)
					.orderByAsc(HrmAchievementAppraisalScoreLevel::getSort).list();
			scoreLevelList.forEach(scoreLevel -> {
				List<Map<String, Object>> mapList = employeeAppraisalMapper.queryEmployeeByLevelId(scoreLevel.getLevelId(), scoreLevel.getAppraisalId());
				BigDecimal minNum = new BigDecimal(scoreLevel.getMinNum());
				BigDecimal maxNum = new BigDecimal(scoreLevel.getMaxNum());
				BigDecimal actualWeight = NumberUtil.round(((double) mapList.size() / totalNum) * 100, 2);
				if (minNum.compareTo(actualWeight) > 0 || maxNum.compareTo(actualWeight) < 0) {
					throw new CrmException(HrmCodeEnum.CAN_T_PASS);
				}
			});
		}
		employeeResultConfirmorsService.lambdaUpdate().set(HrmAchievementEmployeeResultConfirmors::getStatus, 1)
				.eq(HrmAchievementEmployeeResultConfirmors::getAppraisalId, appraisalId)
				.eq(HrmAchievementEmployeeResultConfirmors::getEmployeeId, EmployeeHolder.getEmployeeId())
				.update();
		Integer count = employeeResultConfirmorsService.lambdaQuery()
				.eq(HrmAchievementEmployeeResultConfirmors::getStatus, 0)
				.eq(HrmAchievementEmployeeResultConfirmors::getAppraisalId, appraisalId).count();
		List<Integer> employeeAppraisalIds = lambdaQuery().select(HrmAchievementEmployeeAppraisal::getEmployeeAppraisalId)
				.eq(HrmAchievementEmployeeAppraisal::getAppraisalId, appraisalId)
				.list()
				.stream().map(HrmAchievementEmployeeAppraisal::getEmployeeAppraisalId).collect(Collectors.toList());
		if (count == 0) {
			lambdaUpdate().set(HrmAchievementEmployeeAppraisal::getStatus, EmployeeAppraisalStatus.COMPLETE.getValue())
					.eq(HrmAchievementEmployeeAppraisal::getAppraisalId, appraisalId).update();

			sendSponsorMessage(UserUtil.getUserId(), appraisal.getCreateUserId(), Integer.valueOf(appraisalId), AdminMessageEnum.HRM_APPRAISAL_ARCHIVE.getType(), appraisal.getAppraisalName());

			List<Integer> employeeIds = employeeResultConfirmorsService.lambdaQuery()
					.eq(HrmAchievementEmployeeResultConfirmors::getStatus, 1)
					.eq(HrmAchievementEmployeeResultConfirmors::getAppraisalId, appraisalId).list().stream().map(HrmAchievementEmployeeResultConfirmors::getEmployeeId).collect(Collectors.toList());
			employeeIds.forEach(employeeId -> {
				HrmAchievementEmployeeAppraisal employeeAppraisal = lambdaQuery().eq(HrmAchievementEmployeeAppraisal::getEmployeeId, employeeId).eq(HrmAchievementEmployeeAppraisal::getAppraisalId, appraisalId).one();
				if (null != employeeAppraisal) {
					sendMessage(UserUtil.getUserId(), employeeId, employeeAppraisal.getEmployeeAppraisalId(), AdminMessageEnum.HRM_EMPLOYEE_APPRAISAL_COMPLETE.getType(), appraisal.getAppraisalName());
				}
			});
		}
		employeeAppraisalActionRecordService.confirmResult(appraisal.getAppraisalName(), employeeAppraisalIds, count == 0);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateSchedule(UpdateScheduleBO updateScheduleBO) {
		HrmAchievementEmployeeSeg seg = employeeSegService.getById(updateScheduleBO.getSegId());
		HrmAchievementEmployeeAppraisal employeeAppraisal = getById(seg.getEmployeeAppraisalId());
		HrmAchievementAppraisal appraisal = appraisalService.getById(employeeAppraisal.getAppraisalId());
		if (appraisal.getStatus() > AppraisalStatus.UNDER_EVALUATION.getValue() || appraisal.getIsStop() == 1) {
			throw new CrmException(HrmCodeEnum.CAN_T_MODIFY_PROGRESS);
		}
		if (StrUtil.isEmpty(updateScheduleBO.getExplainDesc())) {
			updateScheduleBO.setExplainDesc("");
		}
		employeeSegService.lambdaUpdate()
				.set(HrmAchievementEmployeeSeg::getSchedule, updateScheduleBO.getSchedule())
				.set(HrmAchievementEmployeeSeg::getExplainDesc, updateScheduleBO.getExplainDesc())
				.eq(HrmAchievementEmployeeSeg::getSegId, updateScheduleBO.getSegId())
				.update();
		employeeAppraisalActionRecordService.updateSchedule(appraisal.getAppraisalName(), updateScheduleBO, employeeSegService.getById(updateScheduleBO.getSegId()));
	}

	@Override
	public List<AchievementAppraisalVO> queryTargetConfirmScreen(Integer employeeId, Integer status) {

		List<TargetConfirmListVO> targetConfirmListVOList = employeeAppraisalMapper.queryTargetConfirmScreen(employeeId, status);
		if (targetConfirmListVOList.size() > 0) {
			List<Integer> collect = targetConfirmListVOList.stream().map(TargetConfirmListVO::getAppraisalId).distinct().collect(Collectors.toList());
			List<HrmAchievementAppraisal> list = appraisalService.query().select("appraisal_id", "appraisal_name").in("appraisal_id", collect).list();
			return list.stream().map(data -> BeanUtil.copyProperties(data, AchievementAppraisalVO.class)).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	public List<AchievementAppraisalVO> queryEvaluatoScreen(Integer employeeId, Integer status) {
		List<EvaluatoListVO> evaluatoListVOBaseList = employeeAppraisalMapper.queryEvaluatoScreen(employeeId, status);
		if (evaluatoListVOBaseList.size() > 0) {
			List<Integer> collect = evaluatoListVOBaseList.stream().map(EvaluatoListVO::getAppraisalId).distinct().collect(Collectors.toList());
			List<HrmAchievementAppraisal> list = appraisalService.query().select("appraisal_id", "appraisal_name").in("appraisal_id", collect).list();
			return list.stream().map(data -> BeanUtil.copyProperties(data, AchievementAppraisalVO.class)).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	public Integer queryLevelIdByScore(QueryLevelIdByScoreBO queryLevelIdByScoreBO) {
		HrmAchievementEmployeeAppraisal hrmAchievementEmployeeAppraisal = employeeAppraisalMapper.selectById(queryLevelIdByScoreBO.getEmployeeAppraisalId());

		List<HrmAchievementAppraisalScoreLevel> scoreLevelList = scoreLevelService.lambdaQuery()
				.eq(HrmAchievementAppraisalScoreLevel::getAppraisalId, hrmAchievementEmployeeAppraisal.getAppraisalId())
				.orderByAsc(HrmAchievementAppraisalScoreLevel::getSort).list();

		RangeMap<BigDecimal, Integer> scoreRangeMap = TreeRangeMap.create();
		scoreLevelList.forEach(scoreLevel -> scoreRangeMap.put(Range.closed(scoreLevel.getMinScore(), scoreLevel.getMaxScore()), scoreLevel.getLevelId()));

		Integer levelId = scoreRangeMap.get(queryLevelIdByScoreBO.getScore());
		return levelId;
	}

	/**
	 * @param employeeId
	 * @param typeId     Id
	 * @param type
	 * @param title
	 */
	public void sendMessage(Long userId, Integer employeeId, Integer typeId, Integer type, String title) {
		AdminMessage adminMessage = new AdminMessage();
		adminMessage.setCreateUser(userId);
		adminMessage.setCreateTime(DateUtil.formatDateTime(new Date()));
		adminMessage.setRecipientUser(EmployeeCacheUtil.getUserId(employeeId));
		adminMessage.setTypeId(typeId);
		adminMessage.setLabel(8);
		adminMessage.setType(type);
		adminMessage.setTitle(title);
		adminMessageService.save(adminMessage);
	}

	/**
	 * @param userId
	 * @param employeeId
	 * @param typeId
	 * @param type
	 * @param title
	 * @param content
	 */
	public void sendContentMessage(Long userId, Integer employeeId, Integer typeId, Integer type, String title, String content) {
		AdminMessage adminMessage = new AdminMessage();
		adminMessage.setCreateUser(userId);
		adminMessage.setCreateTime(DateUtil.formatDateTime(new Date()));
		adminMessage.setRecipientUser(EmployeeCacheUtil.getUserId(employeeId));
		adminMessage.setTypeId(typeId);
		adminMessage.setLabel(8);
		adminMessage.setType(type);
		adminMessage.setTitle(title);
		adminMessage.setContent(content);
		adminMessageService.save(adminMessage);
	}

	/**
	 * @param userId
	 * @param recipientUserId
	 * @param typeId
	 * @param type
	 * @param title
	 */
	public void sendSponsorMessage(Long userId, Long recipientUserId, Integer typeId, Integer type, String title) {
		AdminMessage adminMessage = new AdminMessage();
		adminMessage.setCreateUser(userId);
		adminMessage.setCreateTime(DateUtil.formatDateTime(new Date()));
		adminMessage.setRecipientUser(recipientUserId);
		adminMessage.setTypeId(typeId);
		adminMessage.setLabel(8);
		adminMessage.setType(type);
		adminMessage.setTitle(title);
		adminMessageService.save(adminMessage);
	}
}
