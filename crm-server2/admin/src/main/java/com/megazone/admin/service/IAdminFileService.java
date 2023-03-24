package com.megazone.admin.service;

import com.megazone.admin.entity.BO.AdminDeleteByBatchIdBO;
import com.megazone.admin.entity.BO.RenameFileBO;
import com.megazone.admin.entity.PO.AdminFile;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.servlet.upload.UploadEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface IAdminFileService extends BaseService<AdminFile> {
	UploadEntity upload(MultipartFile file, String batchId, String type, String isPublic) throws IOException;

	UploadEntity uploadBySingle(MultipartFile file, String batchId, String type, String isPublic) throws IOException;

	List<FileEntity> queryFileList(String batchId);

	List<FileEntity> queryFileList(List<String> batchIdList);

	FileEntity queryById(Long fileId);

	FileEntity queryOneByBatchId(String batchId);

	void deleteById(Long fileId);

	void deleteByBatchId(List<String> batchId);

	void down(HttpServletResponse response, Long fileId);

	void renameFileById(RenameFileBO renameFileBO);

	Integer queryNum(List<String> batchId);


	String copyJxcImg(String batchId);

	void saveBatchFileEntity(List<String> adminFileIdList, String batchId);

	List<FileEntity> queryByIds(Collection<Long> fileIds);

	void deleteByBatchId(AdminDeleteByBatchIdBO deleteByBatchIdBO);
}
