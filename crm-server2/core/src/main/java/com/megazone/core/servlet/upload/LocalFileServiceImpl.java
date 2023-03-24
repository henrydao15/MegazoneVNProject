package com.megazone.core.servlet.upload;

import cn.hutool.core.io.FileUtil;
import com.megazone.core.utils.BaseUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author
 */
public class LocalFileServiceImpl implements FileService {

	private static final String JOIN_STR = "-";
	private UploadConfig.LocalConfig config;

	LocalFileServiceImpl(UploadConfig.LocalConfig config) {
		this.config = config;
	}

	/**
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	/**
	 * @param inputStream
	 * @param entity
	 * @return result
	 */
	@Override
	public UploadEntity uploadFile(InputStream inputStream, UploadEntity entity) {
		String key = BaseUtil.getDate() + "/" + entity.getFileId() + JOIN_STR + entity.getName();
		entity.setType(UploadFileEnum.LOCAL.getConfig());
		File file = FileUtil.writeFromStream(inputStream, config.getUploadPath().get(entity.getIsPublic()) + "/" + key);
		if ("1".equals(entity.getIsPublic())) {
			entity.setPath(config.getPublicUrl() + key);
		} else {
			entity.setPath(file.getAbsolutePath());
		}
		return entity;
	}

	/**
	 * @param entity
	 */
	@Override
	public void deleteFile(UploadEntity entity) {
		FileUtil.del(entity.getPath());
	}

	@Override
	public void deleteFileByUrl(String url) {
		String path = config.getUploadPath().get("1") + "/" + url.replace(config.getPublicUrl(), "");
		FileUtil.del(path);
	}

	/**
	 * @param entity
	 * @param fileName
	 */
	@Override
	public void renameFile(UploadEntity entity, String fileName) {

	}

	/**
	 * @param entity
	 * @return ，
	 */
	@Override
	public InputStream downFile(UploadEntity entity) {
		return null;
	}

	@Override
	public void downFileByUrl(String url, File file) {
		try {
			URL urlTmp = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) urlTmp.openConnection();

			conn.setConnectTimeout(3 * 1000);

			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

			InputStream inputStream = conn.getInputStream();

			byte[] getData = readInputStream(inputStream);

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(getData);
			fos.close();
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
