package com.megazone.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "crm.gateway")
public class GatewayConfiguration {
	/**
	 *
	 */
	public List<String> ignoreUrl;


}
