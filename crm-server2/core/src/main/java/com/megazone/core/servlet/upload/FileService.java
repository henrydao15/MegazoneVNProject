package com.megazone.core.servlet.upload;


import java.io.File;
import java.io.InputStream;

/**
 * @author z
 */
public interface FileService {

	/**
	 * @param inputStream
	 * @param entity
	 * @return result
	 */
	public UploadEntity uploadFile(InputStream inputStream, UploadEntity entity);

	/**
	 * @param entity
	 */
	public void deleteFile(UploadEntity entity);

	/**
	 * @param url url
	 */
	void deleteFileByUrl(String url);

	/**
	 * @param entity
	 * @param fileName
	 */
	public void renameFile(UploadEntity entity, String fileName);

	/**
	 * @param entity
	 * @return ，
	 */
	public InputStream downFile(UploadEntity entity);

	void downFileByUrl(String url, File file);

}
