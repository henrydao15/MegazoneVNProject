package com.megazone.examine;

import com.megazone.core.CoreApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {CoreApplication.class, ExamineApplication.class})
@ComponentScan(basePackageClasses = {CoreApplication.class, ExamineApplication.class})
@MapperScan(basePackages = "com.megazone.examine.mapper")

public class ExamineApplication {

	public static void main(String[] args) {
		System.setProperty("javax.xml.accessExternalSchema", "all");
		System.setProperty("javax.xml.accessExternalDTD", "all");
		SpringApplication.run(ExamineApplication.class, args);
	}
}
