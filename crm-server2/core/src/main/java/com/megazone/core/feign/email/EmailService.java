package com.megazone.core.feign.email;

import com.megazone.core.common.Result;
import com.megazone.core.feign.email.impl.EmailServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "admin", contextId = "email", fallback = EmailServiceImpl.class)
public interface EmailService {

	/**
	 * @param userId ID
	 * @return ID
	 */
	@PostMapping("/emailAccount/queryAccountId")
	public Result<Integer> getEmailId(@RequestParam("userId") Long userId);


}
