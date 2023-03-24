package com.megazone.core.feign.email.impl;

import com.megazone.core.common.Result;
import com.megazone.core.feign.email.EmailService;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
	/**
	 * @param userId ID
	 * @return ID
	 */
	@Override
	public Result<Integer> getEmailId(Long userId) {
		return Result.ok(null);
	}
}
