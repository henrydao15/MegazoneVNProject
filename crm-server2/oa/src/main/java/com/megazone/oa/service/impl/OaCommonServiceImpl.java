package com.megazone.oa.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminMessageService;
import com.megazone.core.feign.examine.service.ExamineService;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.oa.entity.PO.OaCalendarType;
import com.megazone.oa.entity.PO.OaCalendarTypeUser;
import com.megazone.oa.entity.PO.OaExamineSort;
import com.megazone.oa.entity.PO.OaLog;
import com.megazone.oa.mapper.OaExamineSortMapper;
import com.megazone.oa.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OaCommonServiceImpl implements IOaCommonService {


	private static final String INIT_AUTH_URL = "/adminConfig/moduleInitData";
	@Autowired
	private IOaAnnouncementService oaAnnouncementService;
	@Autowired
	private IOaCalendarTypeService oaCalendarTypeService;
	@Autowired
	private IOaCalendarTypeUserService oaCalendarTypeUserService;
	@Autowired
	private IOaEventService oaEventService;
	@Autowired
	private IOaEventNoticeService oaEventNoticeService;
	@Autowired
	private IOaEventRelationService oaEventRelationService;
	@Autowired
	private IOaEventUpdateRecordService oaEventUpdateRecordService;
	@Autowired
	private IOaExamineService oaExamineService;
	@Autowired
	private IOaExamineLogService oaExamineLogService;
	@Autowired
	private IOaExamineRecordService oaExamineRecordService;
	@Autowired
	private IOaExamineRelationService oaExamineRelationService;
	@Autowired
	private OaExamineSortMapper oaExamineSortMapper;
	@Autowired
	private IOaExamineStepService oaExamineStepService;
	@Autowired
	private IOaExamineTravelService oaExamineTravelService;
	@Autowired
	private IOaLogService oaLogService;
	@Autowired
	private IOaLogBulletinService oaLogBulletinService;
	@Autowired
	private IOaLogRecordService oaLogRecordService;
	@Autowired
	private IOaLogRelationService oaLogRelationService;
	@Autowired
	private AdminFileService adminFileService;
	@Autowired
	private AdminMessageService adminMessageService;
	@Autowired
	private ExamineService examineService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean initOaData() {
		if (!UserUtil.isAdmin()) {
			if (this.verifyInitAuth()) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
		}
		log.info("Log module data initialization completed!");
		oaAnnouncementService.lambdaUpdate().remove();

		this.deleteFile(oaLogService, OaLog::getBatchId, OaLog::getBatchId);
		oaLogService.lambdaUpdate().remove();
		oaLogBulletinService.lambdaUpdate().remove();
		oaLogRecordService.lambdaUpdate().remove();
		oaLogRelationService.lambdaUpdate().remove();
		log.info("Log module data initialization completed!");
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean initCalendarData() {
		if (!UserUtil.isAdmin()) {
			if (this.verifyInitAuth()) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
		}
		log.info("Start initializing calendar module data!");
		LambdaQueryWrapper<OaCalendarType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.select(OaCalendarType::getTypeId);
		lambdaQueryWrapper.eq(OaCalendarType::getType, 2);
		List<OaCalendarType> oaCalendarTypes = oaCalendarTypeService.list(lambdaQueryWrapper);
		if (CollUtil.isNotEmpty(oaCalendarTypes)) {
			List<Integer> typeIds = oaCalendarTypes.stream().map(OaCalendarType::getTypeId).collect(Collectors.toList());
			oaCalendarTypeUserService.lambdaUpdate().in(OaCalendarTypeUser::getTypeId, typeIds).remove();
			oaCalendarTypeService.lambdaUpdate().eq(OaCalendarType::getType, 2).remove();
		}

		oaEventService.lambdaUpdate().remove();
		oaEventNoticeService.lambdaUpdate().remove();
		oaEventRelationService.lambdaUpdate().remove();
		oaEventUpdateRecordService.lambdaUpdate().remove();

		adminMessageService.deleteByLabel(5);
		log.info("Calendar module data initialization completed!");
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean initOaExamineData() {
		if (!UserUtil.isAdmin()) {
			if (this.verifyInitAuth()) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
		}
		log.info("Start initializing task approval module data!");
		oaExamineService.lambdaUpdate().remove();
		oaExamineLogService.lambdaUpdate().remove();
		oaExamineRecordService.lambdaUpdate().remove();
		oaExamineRelationService.lambdaUpdate().remove();
		LambdaQueryWrapper<OaExamineSort> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		oaExamineSortMapper.delete(lambdaQueryWrapper);
		oaExamineStepService.lambdaUpdate().remove();
		oaExamineTravelService.lambdaUpdate().remove();

		adminMessageService.deleteByLabel(1);
		adminMessageService.deleteByLabel(3);

		examineService.deleteExamineRecordAndLog(0);
		log.info("Task approval module data initialization completed!");
		return true;
	}

	private <T> void deleteFile(BaseService<T> baseService, SFunction<T, String> resultColumn, Function<T, String> mapper) {
		LambdaQueryWrapper<T> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.select(resultColumn);
		List<T> list = baseService.list(lambdaQueryWrapper);
		if (CollUtil.isNotEmpty(list)) {
			List<String> batchIds = list.stream().map(mapper).collect(Collectors.toList());
			batchIds = batchIds.stream().distinct().collect(Collectors.toList());
			adminFileService.delete(batchIds);
		}
	}

	private boolean verifyInitAuth() {
		boolean isNoAuth = false;
		Long userId = UserUtil.getUserId();
		String key = userId.toString();
		List<String> noAuthMenuUrls = BaseUtil.getRedis().get(key);
		if (noAuthMenuUrls != null && noAuthMenuUrls.contains(INIT_AUTH_URL)) {
			isNoAuth = true;
		}
		return isNoAuth;
	}
}
