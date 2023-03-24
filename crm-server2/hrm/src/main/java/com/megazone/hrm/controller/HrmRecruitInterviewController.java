package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.BO.SetInterviewResultBO;
import com.megazone.hrm.entity.BO.SetRecruitInterviewBO;
import com.megazone.hrm.service.IHrmRecruitInterviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmRecruitInterview")
@Api(tags = "-")
public class HrmRecruitInterviewController {

	@Autowired
	private IHrmRecruitInterviewService recruitInterviewService;

	/**
	 *
	 */
	@PostMapping("/addInterview")
	@ApiOperation("")
	public Result addInterview(@RequestBody SetRecruitInterviewBO setRecruitInterviewBO) {
		recruitInterviewService.setInterview(setRecruitInterviewBO);
		return Result.ok();
	}


	@PostMapping("/setInterviewResult")
	@ApiOperation("")
	public Result setInterviewResult(@RequestBody SetInterviewResultBO setInterviewResultBO) {
		recruitInterviewService.setInterviewResult(setInterviewResultBO);
		return Result.ok();
	}


	@PostMapping("/addBatchInterview")
	@ApiOperation("")
	public Result addBatchInterview(@RequestBody SetRecruitInterviewBO setRecruitInterviewBO) {
		recruitInterviewService.addBatchInterview(setRecruitInterviewBO);
		return Result.ok();
	}
}

