package com.megazone.hrm.controller;


import com.megazone.core.common.Result;
import com.megazone.hrm.entity.BO.DeleteLeaveInformationBO;
import com.megazone.hrm.entity.BO.UpdateInformationBO;
import com.megazone.hrm.entity.PO.HrmEmployeeQuitInfo;
import com.megazone.hrm.entity.VO.PostInformationVO;
import com.megazone.hrm.service.IHrmEmployeePostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/hrmEmployeePost")
@Api(tags = "-")
public class HrmEmployeePostController {

	@Autowired
	private IHrmEmployeePostService employeePostService;

	@PostMapping("/postInformation/{employeeId}")
	@ApiOperation("")
	public Result<PostInformationVO> postInformation(@PathVariable("employeeId") Integer employeeId) {
		PostInformationVO postInformationVO = employeePostService.postInformation(employeeId);
		return Result.ok(postInformationVO);
	}

	@PostMapping("/updatePostInformation")
	@ApiOperation("")
	public Result updatePostInformation(@RequestBody UpdateInformationBO updateInformationBO) {
		employeePostService.updatePostInformation(updateInformationBO);
		return Result.ok();
	}


	@PostMapping("/addLeaveInformation")
	@ApiOperation("")
	public Result addLeaveInformation(@RequestBody HrmEmployeeQuitInfo quitInfo) {
		employeePostService.addOrUpdateLeaveInformation(quitInfo);
		return Result.ok();
	}

	@PostMapping("/setLeaveInformation")
	@ApiOperation("")
	public Result setLeaveInformation(@RequestBody HrmEmployeeQuitInfo quitInfo) {
		employeePostService.addOrUpdateLeaveInformation(quitInfo);
		return Result.ok();
	}

	@PostMapping("/deleteLeaveInformation")
	@ApiOperation("")
	public Result deleteLeaveInformation(@RequestBody DeleteLeaveInformationBO deleteLeaveInformationBO) {
		employeePostService.deleteLeaveInformation(deleteLeaveInformationBO);
		return Result.ok();
	}

}

