package com.megazone.core.servlet.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author
 */
@Data
@Component
@ConfigurationProperties(prefix = "crm.upload")
public class UploadConfig {
	/**
	 *
	 */
	private Integer config;

	/**
	 * oss
	 */
	private OssConfig oss;

	/**
	 *
	 */
	private CosConfig cos;

	/**
	 *
	 */
	private QncConfig qnc;

	/**
	 *
	 */
	private LocalConfig local;

	@Data
	public static class OssConfig {
		private String endpoint;
		private String accessKeyId;
		private String accessKeySecret;
		private Map<String, String> bucketName;
		private String publicUrl;

	}

	@Data
	public static class CosConfig {
		private String region;
		private String secretId;
		private String secretKey;
		private Map<String, String> bucketName;
		private String publicUrl;
	}

	@Data
	public static class QncConfig {
		private String accessKey;
		private String secretKey;
		private Map<String, String> bucketName;
		private String publicUrl;
		private String privateUrl;
	}


	@Data
	public static class LocalConfig {
		private Map<String, String> uploadPath;
		private String publicUrl;
	}

}
