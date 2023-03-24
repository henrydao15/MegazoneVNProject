package com.megazone.job;

import com.megazone.core.CoreApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {CoreApplication.class, JobApplication.class})
@ComponentScan(basePackageClasses = {CoreApplication.class, JobApplication.class})

public class JobApplication {
	public static void main(String[] args) {
		SpringApplication.run(JobApplication.class, args);
	}
}
