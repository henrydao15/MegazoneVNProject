package com.megazone.work.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.feign.admin.service.AdminMessageService;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.work.entity.PO.Work;
import com.megazone.work.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author
 * @date 2020/11/16
 */
@Slf4j
@Service
public class WorkCommonServiceImpl implements IWorkCommonService {

	private static final String INIT_AUTH_URL = "/adminConfig/moduleInitData";
	@Autowired
	private IWorkService workService;
	@Autowired
	private IWorkCollectService workCollectService;
	@Autowired
	private IWorkOrderService workOrderService;
	@Autowired
	private IWorkTaskService workTaskService;
	@Autowired
	private IWorkTaskClassService workTaskClassService;
	@Autowired
	private IWorkTaskCommentService workTaskCommentService;
	@Autowired
	private IWorkTaskLabelService workTaskLabelService;
	@Autowired
	private IWorkTaskLabelOrderService workTaskLabelOrderService;
	@Autowired
	private IWorkTaskLogService workTaskLogService;
	@Autowired
	private IWorkTaskRelationService workTaskRelationService;
	@Autowired
	private IWorkUserService workUserService;
	@Autowired
	private AdminFileService adminFileService;
	@Autowired
	private AdminMessageService adminMessageService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean initWorkData() {
		if (!UserUtil.isAdmin()) {
			if (this.verifyInitAuth()) {
				throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
			}
		}
		log.info("！");
		this.deleteFile(workService, Work::getBatchId, Work::getBatchId);
		workService.lambdaUpdate().remove();
		workCollectService.lambdaUpdate().remove();
		workOrderService.lambdaUpdate().remove();

		workTaskService.lambdaUpdate().remove();
		workTaskClassService.lambdaUpdate().remove();
		workTaskCommentService.lambdaUpdate().remove();
		workTaskLabelService.lambdaUpdate().remove();
		workTaskLabelOrderService.lambdaUpdate().remove();
		workTaskLogService.lambdaUpdate().remove();
		workTaskRelationService.lambdaUpdate().remove();
		workUserService.lambdaUpdate().remove();

		adminMessageService.deleteByLabel(1);
		log.info("！");
		return true;
	}

	/**
	 * @param baseService
	 * @param resultColumn
	 * @param queryColumn
	 * @param mapper
	 * @return void
	 * @date 2020/11/20 15:41
	 **/
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

	/**
	 * @param
	 * @return boolean
	 * @date 2020/11/23 10:35
	 **/
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
