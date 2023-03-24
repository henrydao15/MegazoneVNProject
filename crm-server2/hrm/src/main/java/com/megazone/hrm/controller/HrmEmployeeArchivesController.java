package com.megazone.hrm.controller;

import com.megazone.core.common.Result;
import com.megazone.hrm.entity.BO.SendWriteArchivesBO;
import com.megazone.hrm.entity.VO.EmployeeArchivesFieldVO;
import com.megazone.hrm.entity.VO.PersonalInformationVO;
import com.megazone.hrm.entity.VO.PostInformationVO;
import com.megazone.hrm.service.IHrmEmployeeFieldService;
import com.megazone.hrm.service.IHrmEmployeePostService;
import com.megazone.hrm.service.IHrmEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hrmEmployeeArchives")
@Api(tags = "")
@Slf4j
public class HrmEmployeeArchivesController {

	@Autowired
	private IHrmEmployeeFieldService employeeFieldService;

	@Autowired
	private IHrmEmployeePostService employeePostService;

	@Autowired
	private IHrmEmployeeService employeeService;


	@PostMapping("/queryEmployeeArchivesField")
	@ApiOperation("")
	public Result<List<EmployeeArchivesFieldVO>> queryEmployeeArchivesField() {
		List<EmployeeArchivesFieldVO> archivesFields = employeeFieldService.queryEmployeeArchivesField();
		return Result.ok(archivesFields);
	}

	@PostMapping("/setEmployeeArchivesField")
	@ApiOperation("")
	public Result setEmployeeArchivesField(@RequestBody List<EmployeeArchivesFieldVO> archivesFields) {
		employeeFieldService.setEmployeeArchivesField(archivesFields);
		return Result.ok();
	}

	@PostMapping("/sendWriteArchives")
	@ApiOperation("")
	public Result sendWriteArchives(@RequestBody SendWriteArchivesBO writeArchivesBO) {
		employeeFieldService.sendWriteArchives(writeArchivesBO);
		return Result.ok();
	}

	@PostMapping("/postArchives")
	@ApiOperation("")
	public Result<PostInformationVO> postArchives() {
		PostInformationVO information = employeePostService.postArchives();
		return Result.ok(information);
	}

	@PostMapping("/personalArchives")
	@ApiOperation("")
	public Result<PersonalInformationVO> personalArchives() {
		PersonalInformationVO personalInformationVO = employeeService.personalArchives();
		return Result.ok(personalInformationVO);
	}
}
