package com.megazone.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config
 *
 * @author 2017-04-28
 */
@Configuration
public class XxlJobConfig {
	private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

	@Value("${xxl.job.admin.addresses}")
	private String adminAddresses;

	@Value("${xxl.job.executor.appname}")
	private String appName;

	@Value("${xxl.job.executor.ip}")
	private String ip;

	@Value("${xxl.job.executor.port}")
	private int port;

	@Value("${xxl.job.accessToken}")
	private String accessToken;

	@Value("${xxl.job.executor.logpath}")
	private String logPath;

	@Value("${xxl.job.executor.logretentiondays}")
	private int logRetentionDays;


	@Bean
	public XxlJobSpringExecutor xxlJobExecutor() {
		logger.info(">>>>>>>>>>> xxl-job config init.");
		XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
		xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
		xxlJobSpringExecutor.setAppName(appName);
		xxlJobSpringExecutor.setIp(ip);
		xxlJobSpringExecutor.setPort(port);
		xxlJobSpringExecutor.setAccessToken(accessToken);
		xxlJobSpringExecutor.setLogPath(logPath);
		xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);

		return xxlJobSpringExecutor;
	}

	/**
	 *
	 *      1、：
	 *          <dependency>
	 *             <groupId>org.springframework.cloud</groupId>
	 *             <artifactId>spring-cloud-commons</artifactId>
	 *             <version>${version}</version>
	 *         </dependency>
	 *
	 *      2、，
	 *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
	 *
	 *      3、IP
	 *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
	 */


}
