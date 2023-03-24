package com.megazone.core.feign.admin.service;

import com.megazone.core.common.Result;
import com.megazone.core.feign.admin.entity.CallUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Ian
 * @date 2020/8/28
 */
@Component
@FeignClient(name = "admin", contextId = "call")
public interface CallUserService {

	/**
	 * @param callUser
	 * @return
	 * @date 2020/8/28 14:19
	 **/
	@PostMapping("/adminUserHisTable/authorize")
	Result<Boolean> authorize(@RequestBody CallUser callUser);


	/**
	 * @return
	 * @date 2020/8/28 14:19
	 **/
	@PostMapping("/adminUserHisTable/checkAuth")
	Result<Integer> checkAuth();

}
