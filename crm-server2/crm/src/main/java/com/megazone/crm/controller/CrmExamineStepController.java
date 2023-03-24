package com.megazone.crm.controller;


import com.megazone.crm.service.ICrmExamineStepService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/crmExamineStep")
@Api(tags = "")
public class CrmExamineStepController {

	@Autowired
	private ICrmExamineStepService examineStepService;
}

