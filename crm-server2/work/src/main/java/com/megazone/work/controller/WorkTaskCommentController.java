package com.megazone.work.controller;


import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;
import com.megazone.core.utils.TagUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.work.common.WorkAuthUtil;
import com.megazone.work.common.WorkCodeEnum;
import com.megazone.work.entity.PO.Work;
import com.megazone.work.entity.PO.WorkTask;
import com.megazone.work.entity.PO.WorkTaskComment;
import com.megazone.work.service.IWorkService;
import com.megazone.work.service.IWorkTaskCommentService;
import com.megazone.work.service.IWorkTaskService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-18
 */
@RestController
@RequestMapping("/workTaskComment")
public class WorkTaskCommentController {

	@Autowired
	private WorkAuthUtil workAuthUtil;

	@Autowired
	private IWorkTaskService workTaskService;

	@Autowired
	private IWorkService workService;

	@Autowired
	private IWorkTaskCommentService workTaskCommentService;

	@ApiOperation("")
	@PostMapping("/queryCommentList")
	public Result<List<WorkTaskComment>> queryCommentList(@ApiParam("ID") @RequestParam("typeId") Integer typeId, @ApiParam("") @RequestParam("type") Integer type) {
		if (!UserUtil.isAdmin() && !UserUtil.getUser().getRoles().contains(workAuthUtil.getWorkAdminRole())) {
			if (Objects.equals(1, type)) {
				WorkTask task = workTaskService.getById(typeId);
				boolean auth = true;
				if (Objects.equals(0, task.getWorkId())) {
					auth = workAuthUtil.isOaAuth(1, typeId);
				} else {
					Work work = workService.getById(task.getWorkId());
					if (TagUtil.toLongSet(work.getOwnerUserId()).contains(UserUtil.getUserId()) || work.getIsOpen() == 1) {
						auth = false;
					}
				}
				if (auth) {
					return R.error(SystemCodeEnum.SYSTEM_NO_AUTH);
				}
			}
		}
		List<WorkTaskComment> taskComments = workTaskCommentService.queryCommentList(typeId, type);
		return R.ok(taskComments);
	}

	/**
	 * @param comment
	 * @author
	 */
	@PostMapping("/setComment")
	@ApiOperation("")
	public Result setComment(@RequestBody WorkTaskComment comment) {
		if (comment.getType() == 1) {
			if (!workAuthUtil.isTaskAuth(comment.getTypeId())) {
				throw new CrmException(WorkCodeEnum.WORK_AUTH_ERROR);
			}
		}
		workTaskCommentService.setComment(comment);
		return R.ok(comment);
	}

	@PostMapping("/deleteComment")
	@ApiOperation("")
	public Result deleteComment(@RequestParam("commentId") Integer commentId) {
		WorkTaskComment comment = workTaskCommentService.getById(commentId);
		if (comment != null) {
			if (comment.getType() == 1) {
				if (!workAuthUtil.isTaskAuth(comment.getTypeId())) {
					throw new CrmException(WorkCodeEnum.WORK_AUTH_ERROR);
				}
			}
			workTaskCommentService.removeById(commentId);
			workTaskCommentService.lambdaUpdate().eq(WorkTaskComment::getMainId, commentId).remove();
		}
		return R.ok();
	}
}

