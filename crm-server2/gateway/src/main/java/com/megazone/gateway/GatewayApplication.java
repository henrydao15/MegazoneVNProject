package com.megazone.gateway;

import com.megazone.core.CoreApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {CoreApplication.class, GatewayApplication.class})
@ComponentScan(basePackageClasses = {CoreApplication.class, GatewayApplication.class}, excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.megazone.core.config.*"))
public class GatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
}
