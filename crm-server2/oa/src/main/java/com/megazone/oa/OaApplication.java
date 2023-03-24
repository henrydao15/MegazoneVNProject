package com.megazone.oa;

import com.megazone.core.CoreApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {CoreApplication.class, OaApplication.class})
@ComponentScan(basePackageClasses = {CoreApplication.class, OaApplication.class})
@MapperScan(basePackages = "com.megazone.oa.mapper")

public class OaApplication {

	public static void main(String[] args) {
		System.setProperty("javax.xml.accessExternalSchema", "all");
		System.setProperty("javax.xml.accessExternalDTD", "all");
		SpringApplication.run(OaApplication.class, args);
	}
}
