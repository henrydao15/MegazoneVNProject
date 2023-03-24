package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.hrm.entity.BO.*;
import com.megazone.hrm.entity.PO.HrmRecruitCandidate;
import com.megazone.hrm.entity.VO.CandidatePageListVO;
import com.megazone.hrm.service.IHrmRecruitCandidateService;
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
@RequestMapping("/hrmRecruitCandidate")
@Api(tags = "-")
public class HrmRecruitCandidateController {

	@Autowired
	private IHrmRecruitCandidateService recruitCandidateService;


	@PostMapping("/queryPageList")
	@ApiOperation("")
	public Result<BasePage<CandidatePageListVO>> queryCandidatePageList(@RequestBody QueryCandidatePageListBO queryCandidatePageListBO) {
		BasePage<CandidatePageListVO> page = recruitCandidateService.queryCandidateList(queryCandidatePageListBO);
		return Result.ok(page);
	}


	@PostMapping("/queryById/{candidateId}")
	@ApiOperation("")
	public Result<CandidatePageListVO> queryById(@PathVariable("candidateId") String candidateId) {
		CandidatePageListVO candidate = recruitCandidateService.queryById(candidateId);
		return Result.ok(candidate);
	}


	@PostMapping("/addCandidate")
	@ApiOperation("")
	public Result addCandidate(@Validated @RequestBody HrmRecruitCandidate hrmRecruitCandidate) {
		recruitCandidateService.addCandidate(hrmRecruitCandidate);
		return Result.ok();
	}

	@PostMapping("/setCandidate")
	@ApiOperation("")
	public Result setCandidate(@Validated @RequestBody HrmRecruitCandidate hrmRecruitCandidate) {
		recruitCandidateService.setCandidate(hrmRecruitCandidate);
		return Result.ok();
	}

	/**
	 *
	 */
	@PostMapping("/deleteById/{candidateId}")
	@ApiOperation("")
	public Result deleteById(@PathVariable("candidateId") Integer candidateId) {
		recruitCandidateService.deleteById(candidateId);
		return Result.ok();
	}

	@PostMapping("/deleteByIds")
	@ApiOperation("")
	public Result deleteByIds(@RequestBody List<Integer> candidateIds) {
		recruitCandidateService.deleteByIds(candidateIds);
		return Result.ok();
	}


	@PostMapping("/updateCandidateStatus")
	@ApiOperation("")
	public Result updateCandidateStatus(@RequestBody UpdateCandidateStatusBO updateCandidateStatusBO) {
		recruitCandidateService.updateCandidateStatus(updateCandidateStatusBO);
		return Result.ok();
	}

	@PostMapping("/updateCandidatePost")
	@ApiOperation("")
	public Result updateCandidatePost(@RequestBody UpdateCandidatePostBO updateCandidatePostBO) {
		recruitCandidateService.updateCandidatePost(updateCandidatePostBO);
		return Result.ok();
	}

	@PostMapping("/updateCandidateRecruitChannel")
	@ApiOperation("")
	public Result updateCandidateRecruitChannel(@RequestBody UpdateCandidateRecruitChannelBO updateCandidateRecruitChannelBO) {
		recruitCandidateService.updateCandidateRecruitChannel(updateCandidateRecruitChannelBO);
		return Result.ok();
	}

	@PostMapping("/eliminateCandidate")
	@ApiOperation("/")
	public Result eliminateCandidate(@RequestBody EliminateCandidateBO eliminateCandidateBO) {
		recruitCandidateService.eliminateCandidate(eliminateCandidateBO);
		return Result.ok();
	}


	@PostMapping("/queryCleanCandidateIds")
	@ApiOperation(",")
	public Result<List<Integer>> queryCleanCandidateIds(@RequestBody QueryCleanCandidateBO queryCleanCandidateBO) {
		List<Integer> candidateIds = recruitCandidateService.queryCleanCandidateIds(queryCleanCandidateBO);
		return Result.ok(candidateIds);
	}

	@PostMapping("/queryFile/{candidateId}")
	@ApiOperation("")
	public Result<List<FileEntity>> queryFile(@PathVariable("candidateId") Integer candidateId) {
		return recruitCandidateService.queryFile(candidateId);
	}

	@PostMapping("/queryCandidateStatusNum")
	@ApiOperation("")
	public Result<Map<Integer, Long>> queryCandidateStatusNum() {
		Map<Integer, Long> statusMap = recruitCandidateService.queryCandidateStatusNum();
		return Result.ok(statusMap);
	}


}

