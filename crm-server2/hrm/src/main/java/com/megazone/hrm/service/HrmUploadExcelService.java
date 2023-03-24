package com.megazone.hrm.service;

import com.megazone.hrm.entity.BO.UploadExcelBO;
import org.springframework.web.multipart.MultipartFile;

public interface HrmUploadExcelService {

	/**
	 *
	 */
	public Long uploadExcel(MultipartFile file, UploadExcelBO uploadExcelBO);


}
