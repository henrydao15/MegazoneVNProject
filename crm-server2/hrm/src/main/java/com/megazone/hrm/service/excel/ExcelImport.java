package com.megazone.hrm.service.excel;

import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.common.cache.AdminCacheKey;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.entity.AdminMessage;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.redis.Redis;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.upload.UploadConfig;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.hrm.entity.BO.UploadExcelBO;
import com.megazone.hrm.service.IHrmEmployeeService;
import com.megazone.hrm.service.IHrmSalaryArchivesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class ExcelImport implements Runnable {

	protected static final int UPLOAD_EXCEL_EXIST_TIME = 7200;
	protected IHrmSalaryArchivesService archivesService = ApplicationContextHolder.getBean(IHrmSalaryArchivesService.class);
	protected Redis redis = ApplicationContextHolder.getBean(Redis.class);

	protected AdminService adminService = ApplicationContextHolder.getBean(AdminService.class);

	protected IHrmEmployeeService employeeService = ApplicationContextHolder.getBean(IHrmEmployeeService.class);

	protected UploadExcelBO uploadExcelBO;
	protected List<List<Object>> errorList = new CopyOnWriteArrayList<>();
	protected volatile AtomicInteger num = new AtomicInteger(0);
	protected volatile AtomicInteger updateNum = new AtomicInteger(0);
	private String uploadPath = ApplicationContextHolder.getBean(UploadConfig.class).getLocal().getUploadPath().get("0");

	protected static Date parseDate(String dateStr) {
		Date date;
		try {
			date = DateUtil.parse(dateStr, "yyyy-MM-dd");
		} catch (DateException e) {
			try {
				date = DateUtil.parse(dateStr, "yyyy.MM.dd");
			} catch (DateException e1) {
				date = DateUtil.parse(dateStr, "yyyy/MM/dd");
			}
		}
		return date;
	}

	public abstract void importExcel();

	public UploadExcelBO getUploadExcelBO() {
		return uploadExcelBO;
	}

	@Override
	public void run() {
		boolean exists = redis.exists(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + getUploadExcelBO().getMessageId());
		if (!exists) {
			return;
		}
		try {
			UserUtil.setUser(getUploadExcelBO().getUserInfo());
			importExcel();
		} catch (Exception e) {
			log.error("Import error", e);
		} finally {
			redis.del(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + getUploadExcelBO().getMessageId());
			UserUtil.removeUser();
		}
		AdminMessage adminMessage = adminService.getMessageById(getUploadExcelBO().getMessageId()).getData();
		adminMessage.setTitle(String.valueOf(num.get() - 7));
		adminMessage.setContent((errorList.size() - 1) + "," + updateNum.get());
		if (errorList.size() >= 2) {
			File file = new File(uploadPath + "excel/" + BaseUtil.getDate() + "/" + IdUtil.simpleUUID() + ".xlsx");
			BigExcelWriter writer = ExcelUtil.getBigWriter(file);
			CellStyle textStyle = writer.getWorkbook().createCellStyle();
			DataFormat format = writer.getWorkbook().createDataFormat();
			textStyle.setDataFormat(format.getFormat("@"));
			for (int i = 0; i < errorList.get(1).size(); i++) {
				writer.setColumnWidth(i, 20);
				writer.getSheet().setDefaultColumnStyle(i, textStyle);
			}
			writer.merge(errorList.get(1).size() - 1, "basic information");
			writer.setDefaultRowHeight(20);
			Cell cell = writer.getCell(0, 0);
			CellStyle cellStyle = cell.getCellStyle();
			cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = writer.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) 16);
			cellStyle.setFont(font);
			cell.setCellStyle(cellStyle);
			// Write the content at once, using the default style
			writer.write(errorList);
			// close the writer, free memory
			writer.close();
			adminMessage.setTypeId(null);
			//Save the excel of the error message for seven days 604800 seconds for seven days
			redis.setex(AdminCacheKey.UPLOAD_EXCEL_MESSAGE_PREFIX + "file:" + adminMessage.getMessageId().toString(), 604800, file.getAbsolutePath());
		}
		adminService.saveOrUpdateMessage(adminMessage);
		FileUtil.del(getUploadExcelBO().getFilePath());
	}

	/**
	 * Get the temporary file path, remember to delete it after use
	 *
	 * @param file file
	 * @return path
	 */
	protected String getFilePath(MultipartFile file) {
		String dirPath = FileUtil.getTmpDirPath();
		try {
			InputStream inputStream = file.getInputStream();
			File fromStream = FileUtil.writeFromStream(inputStream, dirPath + "/" + IdUtil.simpleUUID() + file.getOriginalFilename());
			return fromStream.getAbsolutePath();
		} catch (IOException e) {
			throw new CrmException(SystemCodeEnum.SYSTEM_UPLOAD_FILE_ERROR);
		}
	}

	protected String getValue(Object obj) {
		return Optional.ofNullable(obj).map(o -> {
			String s = String.valueOf(o);
			if (StrUtil.isEmpty(s)) {
				return "0";
			} else {
				return s;
			}
		}).orElse("0");
	}
}
