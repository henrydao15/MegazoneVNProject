package com.megazone.core.feign.work;

import com.megazone.core.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "work", contextId = "taskJob")
public interface WorkService {

	@PostMapping("/workTask/updateTaskJob")
	Result updateTaskJob();


	@PostMapping("/work/initWorkData")
	Result<Boolean> initWorkData();

}
