package com.megazone.hrm.controller;

import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.hrm.entity.BO.QueryRecruitPostPageListBO;
import com.megazone.hrm.entity.BO.UpdateRecruitPostStatusBO;
import com.megazone.hrm.entity.PO.HrmRecruitPost;
import com.megazone.hrm.entity.VO.RecruitPostVO;
import com.megazone.hrm.service.IHrmRecruitPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmRecruitPost")
@Api(tags = "-")
public class HrmRecruitPostController {

	@Autowired
	private IHrmRecruitPostService recruitPostService;

	@ApiOperation("")
	@PostMapping("/addRecruitPost")
	public Result addRecruitPost(@Validated @RequestBody HrmRecruitPost recruitPost) {
		recruitPostService.addRecruitPost(recruitPost);
		return Result.ok();
	}

	@ApiOperation("")
	@PostMapping("/setRecruitPost")
	public Result setRecruitPost(@Validated @RequestBody HrmRecruitPost recruitPost) {
		recruitPostService.setRecruitPost(recruitPost);
		return Result.ok();
	}

	/**
	 * @param postId
	 */
	@ApiOperation("")
	@PostMapping("/queryById/{postId}")
	public Result<RecruitPostVO> queryById(@PathVariable("postId") Integer postId) {
		RecruitPostVO recruitPostVO = recruitPostService.queryById(postId);
		if (recruitPostVO.getRecruitNum() != null && recruitPostVO.getRecruitNum() != 0) {
			recruitPostVO.setRecruitSchedule((recruitPostVO.getHasEntryNum() * 100 / recruitPostVO.getRecruitNum()) + "");
		}
		return Result.ok(recruitPostVO);
	}

	@ApiOperation("")
	@PostMapping("/queryRecruitPostPageList")
	public Result<BasePage<RecruitPostVO>> queryRecruitPostPageList(@RequestBody QueryRecruitPostPageListBO queryRecruitPostPageListBO) {
		BasePage<RecruitPostVO> page = recruitPostService.queryRecruitPostPageList(queryRecruitPostPageListBO);
		page.getList().forEach(recruitPostVO -> {
			if (recruitPostVO.getRecruitNum() != null && recruitPostVO.getRecruitNum() != 0) {
				recruitPostVO.setRecruitSchedule((recruitPostVO.getHasEntryNum() * 100 / recruitPostVO.getRecruitNum()) + "");
			}
		});
		return Result.ok(page);
	}

	@ApiOperation("")
	@PostMapping("/updateRecruitPostStatus")
	public Result updateRecruitPostStatus(@RequestBody UpdateRecruitPostStatusBO updateRecruitPostStatusBO) {
		recruitPostService.updateRecruitPostStatus(updateRecruitPostStatusBO);
		return Result.ok();
	}

	@PostMapping("/queryPostStatusNum")
	@ApiOperation("")
	public Result<Map<Integer, Long>> queryPostStatusNum() {
		Map<Integer, Long> statusMap = recruitPostService.queryPostStatusNum();
		return Result.ok(statusMap);
	}

	@PostMapping("/queryAllRecruitPostList")
	@ApiOperation("()")
	public Result<List<HrmRecruitPost>> queryAllRecruitPostList() {
		List<HrmRecruitPost> postList = recruitPostService.queryAllRecruitPostList();
		return Result.ok(postList);
	}
}

