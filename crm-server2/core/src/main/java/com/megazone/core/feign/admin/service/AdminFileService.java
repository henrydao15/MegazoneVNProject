package com.megazone.core.feign.admin.service;

import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.servlet.upload.UploadEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Component
@FeignClient(name = "admin", contextId = "file")
public interface AdminFileService {
	@RequestMapping(value = "/adminFile/queryFileList/{batchId}", method = RequestMethod.POST)
	public Result<List<FileEntity>> queryFileList(@PathVariable("batchId") String batchId);

	@RequestMapping(value = "/adminFile/queryById/{fileId}", method = RequestMethod.POST)
	public Result<FileEntity> queryById(@PathVariable("fileId") Long fileId);

	@RequestMapping(value = "/adminFile/queryByIds", method = RequestMethod.POST)
	public Result<List<FileEntity>> queryByIds(@RequestBody Collection<Long> fileIds);

	@RequestMapping(value = "/adminFile/queryOneByBatchId/{batchId}", method = RequestMethod.POST)
	public Result<FileEntity> queryOne(@PathVariable("batchId") String batchId);

	@RequestMapping(value = "/adminFile/deleteById/{fileId}", method = RequestMethod.POST)
	public Result delete(@PathVariable("fileId") Object fileId);

	/**
	 * @param batchId batchId
	 * @return data
	 */
	@RequestMapping(value = "/adminFile/deleteByBatchIds", method = RequestMethod.POST)
	public Result delete(@RequestBody List<String> batchId);

	@PostMapping(value = "/adminFile/queryNum")
	public Result<Integer> queryNum(@RequestBody List<String> batchId);

	@PostMapping(value = "/adminFile/queryFileList")
	public Result<List<FileEntity>> queryFileList(@RequestBody List<String> batchIdList);

	@PostMapping(value = "/adminFile/copyJxcImg")
	@ApiExplain("copy")
	Result<String> copyJxcImg(@RequestParam(value = "batchId") String batchId);


	@PostMapping(value = "/adminFile/saveBatchFileEntity")
	@ApiExplain("(id,batchId)")
	void saveBatchFileEntity(@RequestParam(value = "adminFileIdList") List<String> adminFileIdList,
							 @RequestParam(value = "batchId") String batchId);

	@PostMapping(value = "/adminFile/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiExplain("")
	public Result<UploadEntity> upload(@RequestPart("file") MultipartFile file,
									   @RequestParam("batchId") String batchId,
									   @RequestParam("type") String type);
}
