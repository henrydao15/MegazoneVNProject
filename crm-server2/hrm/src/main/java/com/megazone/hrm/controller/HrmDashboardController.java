package com.megazone.hrm.controller;

import com.megazone.core.common.Result;
import com.megazone.hrm.entity.BO.QueryNotesByTimeBO;
import com.megazone.hrm.entity.BO.QueryNotesStatusBO;
import com.megazone.hrm.entity.PO.HrmNotes;
import com.megazone.hrm.entity.VO.*;
import com.megazone.hrm.service.IHrmDashboardService;
import com.megazone.hrm.service.IHrmNotesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/hrmDashboard")
@Api(tags = "")
public class HrmDashboardController {

	@Autowired
	private IHrmDashboardService dashboardService;

	@Autowired
	private IHrmNotesService notesService;


	@PostMapping("/employeeSurvey")
	@ApiOperation("")
	public Result<Map<Integer, Long>> employeeSurvey() {
		Map<Integer, Long> map = dashboardService.employeeSurvey();
		return Result.ok(map);
	}

	@PostMapping("/recruitSurvey")
	@ApiOperation("")
	public Result<RecruitSurveyCountVO> recruitSurvey() {
		RecruitSurveyCountVO recruitSurveyCountVO = dashboardService.recruitSurvey();
		return Result.ok(recruitSurveyCountVO);
	}

	@PostMapping("/lastSalarySurvey")
	@ApiOperation("")
	public Result<LastSalarySurveyVO> lastSalarySurvey() {
		LastSalarySurveyVO lastSalarySurveyVO = dashboardService.lastSalarySurvey();
		return Result.ok(lastSalarySurveyVO);
	}


	@PostMapping("/appraisalCountSurvey")
	@ApiOperation("")
	public Result<Map<Integer, Integer>> appraisalCountSurvey() {
		Map<Integer, Integer> map = dashboardService.appraisalCountSurvey();
		return Result.ok(map);
	}

	@PostMapping("/appraisalSurvey/{status}")
	@ApiOperation("")
	public Result<List<AppraisalSurveyVO>> appraisalSurvey(@PathVariable String status) {
		List<AppraisalSurveyVO> appraisalSurveyVOList = dashboardService.appraisalSurvey(status);
		return Result.ok(appraisalSurveyVOList);
	}

	@PostMapping("/toDoRemind")
	@ApiOperation("")
	public Result<DoRemindVO> toDoRemind() {
		DoRemindVO doRemindVO = dashboardService.toDoRemind();
		return Result.ok(doRemindVO);
	}

	@PostMapping("/addNotes")
	@ApiOperation("")
	public Result addNotes(@RequestBody HrmNotes notes) {
		notesService.addNotes(notes);
		return Result.ok();
	}

	@PostMapping("/deleteNotes/{notesId}")
	@ApiOperation("")
	public Result deleteNotes(@PathVariable String notesId) {
		notesService.removeById(notesId);
		return Result.ok();
	}

	@PostMapping("/queryNotesByTime")
	@ApiOperation("")
	public Result<List<NotesVO>> queryNotesByTime(@RequestBody QueryNotesByTimeBO queryNotesByTimeBO) {
		List<NotesVO> notesVOList = dashboardService.queryNotesByTime(queryNotesByTimeBO);
		return Result.ok(notesVOList);
	}

	@PostMapping("/queryNotesStatus")
	@ApiOperation("")
	public Result<Set<String>> queryNotesStatus(@RequestBody QueryNotesStatusBO queryNotesByTimeBO) {
		Set<String> timeList = dashboardService.queryNotesStatus(queryNotesByTimeBO);
		return Result.ok(timeList);
	}


	@PostMapping("/myTeam")
	@ApiOperation("()")
	public Result<Map<Integer, Long>> myTeam() {
		Map<Integer, Long> map = dashboardService.myTeam();
		return Result.ok(map);
	}

	@PostMapping("/teamSurvey")
	@ApiOperation("()")
	public Result<TeamSurveyVO> teamSurvey() {
		TeamSurveyVO teamSurveyVO = dashboardService.teamSurvey();
		return Result.ok(teamSurveyVO);
	}

	@PostMapping("/mySurvey")
	@ApiOperation("()")
	public Result<MySurveyVO> mySurvey() {
		MySurveyVO data = dashboardService.mySurvey();
		return Result.ok(data);
	}

	@PostMapping("/myNotesByTime")
	@ApiOperation("()")
	public Result<List<NotesVO>> myNotesByTime(@RequestBody QueryNotesByTimeBO queryNotesByTimeBO) {
		List<NotesVO> notesVOList = dashboardService.myNotesByTime(queryNotesByTimeBO);
		return Result.ok(notesVOList);
	}

	@PostMapping("/myNotesStatus")
	@ApiOperation("()")
	public Result<Set<String>> myNotesStatus(@RequestBody QueryNotesStatusBO queryNotesByTimeBO) {
		Set<String> timeList = dashboardService.myNotesStatus(queryNotesByTimeBO);
		return Result.ok(timeList);
	}

}
