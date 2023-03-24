package com.megazone.work.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.megazone.core.common.JSONObject;
import com.megazone.core.feign.admin.entity.AdminMessageBO;
import com.megazone.core.feign.admin.entity.AdminMessageEnum;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminMessageService;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserCacheUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.work.entity.PO.WorkTaskComment;
import com.megazone.work.mapper.WorkTaskCommentMapper;
import com.megazone.work.service.IWorkTaskCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-18
 */
@Service
public class WorkTaskCommentServiceImpl extends BaseServiceImpl<WorkTaskCommentMapper, WorkTaskComment> implements IWorkTaskCommentService {

	@Autowired
	private AdminService adminService;

	/**
	 * @param typeId typeId
	 * @param type   type
	 * @return data
	 */
	@Override
	public List<WorkTaskComment> queryCommentList(Integer typeId, Integer type) {
		LambdaQueryChainWrapper<WorkTaskComment> chainWrapper = lambdaQuery();
		chainWrapper.eq(WorkTaskComment::getType, type);
		chainWrapper.eq(WorkTaskComment::getTypeId, typeId);
		chainWrapper.orderByAsc(WorkTaskComment::getCreateTime);
		List<WorkTaskComment> taskCommentList = chainWrapper.list();
		if (taskCommentList == null || taskCommentList.size() == 0) {
			return new ArrayList<>();
		}
		taskCommentList.forEach(record -> {
			if (record.getUserId() != null) {
				List<SimpleUser> data = UserCacheUtil.getSimpleUsers(Collections.singleton(record.getUserId()));
				record.setUser(data.size() > 0 ? data.get(0) : null);
			}
			if (!Objects.equals(0L, record.getPid())) {
				List<SimpleUser> data = UserCacheUtil.getSimpleUsers(Collections.singleton(record.getPid()));
				record.setReplyUser(data.size() > 0 ? data.get(0) : null);
			}
		});
		Map<Integer, List<WorkTaskComment>> pMap = taskCommentList.stream().collect(Collectors.groupingBy(WorkTaskComment::getMainId));
		taskCommentList = pMap.get(0);
		taskCommentList.forEach(record -> {
			Integer commentId = record.getCommentId();
			if (pMap.get(commentId) != null) {
				record.setChildCommentList(pMap.get(commentId));
			} else {
				record.setChildCommentList(new ArrayList<>());
			}
		});
		return taskCommentList;
	}

	/**
	 * @param comment taskComment
	 */
	@Override
	public void setComment(WorkTaskComment comment) {
		if (comment.getCommentId() == null) {
			comment.setCreateTime(new Date());
			comment.setUserId(UserUtil.getUserId());
			save(comment);
			if (comment.getType().equals(2)) {
				AdminMessageBO adminMessageBO = new AdminMessageBO();
				JSONObject object = getBaseMapper().queryOaLog(comment.getTypeId());
				adminMessageBO.setUserId(comment.getUserId());
				adminMessageBO.setTitle(DateUtil.formatDate(object.getDate("createTime")));
				adminMessageBO.setContent(comment.getContent());
				adminMessageBO.setTypeId(comment.getTypeId());
				if (comment.getMainId() != null && !comment.getMainId().equals(0)) {
					adminMessageBO.setMessageType(AdminMessageEnum.OA_COMMENT_REPLY.getType());
					adminMessageBO.setIds(Collections.singletonList(comment.getPid()));
				} else {
					adminMessageBO.setMessageType(AdminMessageEnum.OA_LOG_REPLY.getType());
					adminMessageBO.setIds(Collections.singletonList(object.getLong("createUserId")));
				}
				ApplicationContextHolder.getBean(AdminMessageService.class).sendMessage(adminMessageBO);
			}
		} else {
			updateById(comment);
		}
	}
}
