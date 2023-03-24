package com.megazone.core.feign.crm.service;

import com.megazone.core.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author
 * @date 2020/9/16
 */
@Component
@FeignClient(name = "crm", contextId = "analysis")
public interface CrmAnalysisService {

	/**
	 * @return
	 * @date 2020/9/16 13:45
	 **/
	@PostMapping("/crmAnalysis/initCrmData")
	Result<Boolean> initCrmData();

}
