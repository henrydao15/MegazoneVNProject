package com.megazone.work.controller;


import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.work.service.IWorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wyq
 * @since 2020-05-15
 */
@RestController
@RequestMapping("/workOrder")
@Api(tags = "")
public class WorkOrderController {
	@Autowired
	private IWorkOrderService workOrderService;

	@PostMapping("/updateWorkOrder")
	@ApiOperation("")
	public Result updateWorkOrder(@RequestBody List<Integer> workIdList) {
		workOrderService.updateWorkOrder(workIdList);
		return R.ok();
	}
}

