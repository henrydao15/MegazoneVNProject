package com.megazone.core.feign.km;

import com.megazone.core.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author
 * @date 2020/11/18
 */
@Component
@FeignClient(name = "km", contextId = "knowledgeLibrary")
public interface KmService {

	@PostMapping("/kmKnowledgeLibrary/initKmData")
	Result<Boolean> initKmData();
}
