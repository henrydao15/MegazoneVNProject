package com.megazone.gateway.config;

//import com.alibaba.cloud.nacos.ribbon.NacosRule;
//import com.netflix.loadbalancer.BestAvailableRule;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClients(
		value = {
				@RibbonClient(name = "crm", configuration = LBConfigRule.class),
				@RibbonClient(name = "jxc", configuration = LBConfigRule.class),
				@RibbonClient(name = "admin", configuration = LBConfigRule.class),
		},
		defaultConfiguration = LBConfig.class)
public class LBConfig {

//    @Bean
//    public IRule commonRule() {
//        return new NacosRule();
//    }
}
