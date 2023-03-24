package com.megazone.admin.controller;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.admin.entity.BO.AdminDeleteByBatchIdBO;
import com.megazone.admin.entity.BO.RenameFileBO;
import com.megazone.admin.service.IAdminFileService;
import com.megazone.core.common.ApiExplain;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.exception.CrmException;
import com.megazone.core.servlet.LoginFromCookie;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.core.servlet.upload.UploadEntity;
import com.megazone.core.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/adminFile")
@Api(tags = "File operation related interface")
public class AdminFileController {

	@Autowired
	private IAdminFileService adminFileService;

	@PostMapping("/upload")
	@ApiOperation("Upload file")
	public Result<UploadEntity> upload(@RequestParam("file")
									   @ApiParam("file") MultipartFile file,
									   @ApiParam("batchId") String batchId,
									   @ApiParam("file type") String type,
									   @RequestParam(value = "isPublic", required = false) String isPublic) throws IOException {
		if (StrUtil.isEmpty(isPublic)) {
			isPublic = "0";
		}
		UploadEntity entity = adminFileService.upload(file, batchId, type, isPublic);
		return Result.ok(entity);
	}

	@PostMapping("/uploadBySingle")
	@ApiOperation("Upload file")
	public Result<UploadEntity> uploadBySingle(@RequestParam("file")
											   @ApiParam("file") MultipartFile file,
											   @ApiParam("batchId") String batchId,
											   @ApiParam("file type") String type,
											   @RequestParam(value = "isPublic", required = false) String isPublic) throws IOException {
		if (StrUtil.isEmpty(isPublic)) {
			isPublic = "0";
		}
		UploadEntity entity = adminFileService.uploadBySingle(file, batchId, type, isPublic);
		return Result.ok(entity);
	}

	@RequestMapping(value = "/queryFileList/{batchId}", method = RequestMethod.POST)
	@ApiOperation(value = "Query file list by batch ID", httpMethod = "POST")
	public Result<List<FileEntity>> queryFileList(@NotNull @PathVariable String batchId) {
		List<FileEntity> entityList = adminFileService.queryFileList(batchId);
		return Result.ok(entityList);
	}

	@RequestMapping(value = "/queryById/{fileId}", method = RequestMethod.POST)
	@ApiOperation(value = "Query file by ID", httpMethod = "POST")
	public Result<FileEntity> queryById(@NotNull @PathVariable @ApiParam("File ID") Long fileId) {
		FileEntity fileEntity = adminFileService.queryById(fileId);
		return Result.ok(fileEntity);
	}

	@RequestMapping(value = "/queryByIds", method = RequestMethod.POST)
	@ApiOperation(value = "Query file by ID", httpMethod = "POST")
	public Result<List<FileEntity>> queryByIds(@RequestBody Collection<Long> fileIds) {
		List<FileEntity> fileEntitys = adminFileService.queryByIds(fileIds);
		return Result.ok(fileEntitys);
	}

	@RequestMapping(value = "/queryOneByBatchId/{batchId}", method = RequestMethod.POST)
	@ApiOperation(value = "Query a single file by batch ID", httpMethod = "POST")
	public Result<FileEntity> queryOneByBatchId(@NotNull @PathVariable @ApiParam("batchId") String batchId) {
		FileEntity fileEntity = adminFileService.queryOneByBatchId(batchId);
		return Result.ok(fileEntity);
	}

	@RequestMapping(value = "/deleteById/{fileId}", method = RequestMethod.POST)
	@ApiOperation(value = "Delete file by ID", httpMethod = "POST")
	public Result deleteById(@NotNull @PathVariable @ApiParam("File ID") String fileId) {
		if (NumberUtil.isLong(fileId)) {
			adminFileService.deleteById(Long.parseLong(fileId));
		} else {
			AdminDeleteByBatchIdBO bo = new AdminDeleteByBatchIdBO();
			bo.setBatchId(fileId);
			adminFileService.deleteByBatchId(bo);
		}

		return Result.ok();
	}

	@RequestMapping(value = "/deleteByBatchId", method = RequestMethod.POST)
	@ApiOperation(value = "Delete files by batch ID and file type", httpMethod = "POST")
	public Result deleteByBatchId(@RequestBody AdminDeleteByBatchIdBO deleteByBatchIdBO) {
		adminFileService.deleteByBatchId(deleteByBatchIdBO);
		return Result.ok();
	}

	@RequestMapping(value = "/deleteByBatchIds", method = RequestMethod.POST)
	@ApiOperation(value = "Delete files by batch ID", httpMethod = "POST")
	public Result deleteByBatchId(@RequestBody @ApiParam("batchId") List<String> batchId) {
		adminFileService.deleteByBatchId(batchId);
		return Result.ok();
	}

	@RequestMapping(value = "/down/{fileId}")
	@ApiOperation(value = "download file interface", httpMethod = "POST")
	@LoginFromCookie
	public void down(@PathVariable("fileId") @ApiParam("File ID") Long fileId, HttpServletResponse response) {
		if (UserUtil.getUser() == null) {
			throw new CrmException(SystemCodeEnum.SYSTEM_NO_AUTH);
		}
		adminFileService.down(response, fileId);
	}

	@PostMapping(value = "/renameFileById")
	@ApiOperation(value = "Modify attachment name", httpMethod = "POST")
	public Result renameFileById(@RequestBody RenameFileBO renameFileBO) {
		adminFileService.renameFileById(renameFileBO);
		return Result.ok();
	}

	@PostMapping(value = "/queryNum")
	@ApiExplain("Query the number of files")
	public Result<Integer> queryNum(@RequestBody List<String> batchId) {
		Integer num = adminFileService.queryNum(batchId);
		return Result.ok(num);
	}

	@PostMapping(value = "/queryFileList")
	@ApiExplain("Query file")
	public Result<List<FileEntity>> queryFileList(@RequestBody List<String> batchIdList) {
		List<FileEntity> fileEntities = adminFileService.queryFileList(batchIdList);
		return Result.ok(fileEntities);
	}


	@PostMapping(value = "/copyJxcImg")
	@ApiExplain("copy invoicing product details map")
	public Result<String> copyJxcImg(@RequestParam(value = "batchId") String batchId) {
		String newBatchId = adminFileService.copyJxcImg(batchId);
		return Result.ok(newBatchId);
	}

	@PostMapping(value = "/saveBatchFileEntity")
	@ApiExplain("Batch save attachments (query attachment id, modify batchId)")
	public Result saveBatchFileEntity(@RequestParam(value = "adminFileIdList") List<String> adminFileIdList,
									  @RequestParam(value = "batchId") String batchId) {
		adminFileService.saveBatchFileEntity(adminFileIdList, batchId);
		return Result.ok();
	}
}
