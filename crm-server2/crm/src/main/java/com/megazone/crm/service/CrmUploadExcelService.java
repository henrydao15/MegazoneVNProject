package com.megazone.crm.service;

import com.megazone.crm.entity.BO.UploadExcelBO;
import org.springframework.web.multipart.MultipartFile;

public interface CrmUploadExcelService {

	/**
	 *
	 */
	public Long uploadExcel(MultipartFile file, UploadExcelBO uploadExcelBO);


}
