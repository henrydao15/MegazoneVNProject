package com.megazone.core.servlet.upload;

import com.megazone.core.servlet.ApplicationContextHolder;

/**
 * @author
 */
public class FileServiceFactory {

	public static FileService build() {
		UploadConfig uploadConfig = ApplicationContextHolder.getBean(UploadConfig.class);
		Integer config = uploadConfig.getConfig();
		return new LocalFileServiceImpl(uploadConfig.getLocal());
	}

}
