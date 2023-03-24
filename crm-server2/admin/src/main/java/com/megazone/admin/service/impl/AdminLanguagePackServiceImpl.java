package com.megazone.admin.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.megazone.admin.common.AdminCodeEnum;
import com.megazone.admin.entity.BO.AdminLanguagePackBO;
import com.megazone.admin.entity.PO.AdminConfig;
import com.megazone.admin.entity.PO.AdminLanguagePack;
import com.megazone.admin.entity.PO.AdminUserConfig;
import com.megazone.admin.entity.VO.AdminLanguagePackVO;
import com.megazone.admin.mapper.AdminLanguagePackMapper;
import com.megazone.admin.service.IAdminConfigService;
import com.megazone.admin.service.IAdminLanguagePackService;
import com.megazone.admin.service.IAdminUserConfigService;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.R;
import com.megazone.core.common.Result;
import com.megazone.core.common.SystemCodeEnum;
import com.megazone.core.entity.BasePage;
import com.megazone.core.exception.CrmException;
import com.megazone.core.feign.admin.service.AdminFileService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.ExcelParseUtil;
import com.megazone.core.utils.UserUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * Language package table Service implementation class
 * </p>
 *
 * @author zmj
 * @since 2020-12-01
 */
@Service
public class AdminLanguagePackServiceImpl extends BaseServiceImpl<AdminLanguagePackMapper, AdminLanguagePack> implements IAdminLanguagePackService {

	private static final String DEFLAUT_LANGUAGE_PACK = "DeflautLanguagePack";
	private static final String DEFLAUT_LANGUAGE_PACK_DESCRIBE = "Set Default Language Pack";
	private static final String LANGUAGE_PACK_CHINESE = "Chinese";
	private static final int SYSTEM = 0;
	private static final int USER = 1;
	@Autowired
	private AdminFileService adminFileService;
	@Autowired
	private IAdminUserConfigService adminUserConfigService;
	@Autowired
	private IAdminConfigService adminConfigService;

	@Override
	public BasePage<AdminLanguagePackVO> queryLanguagePackList(AdminLanguagePackBO adminLanguagePackBO, Integer systemOrUser) {
		BasePage<AdminLanguagePackVO> languagePackBasePage = getBaseMapper().queryLanguagePackList(adminLanguagePackBO.parse());
		JSONObject deflautLanguagePack = queryDeflautLanguagePackSetting(systemOrUser);
		Integer languagePackId = deflautLanguagePack.getInteger("languagePackId");
		if (languagePackId != null) {
			languagePackBasePage.getList().forEach(adminLanguagePackVO -> {
				if (adminLanguagePackVO.getLanguagePackId().equals(languagePackId)) {
					adminLanguagePackVO.setDefaultLanguage(1);
				}
			});
		}
		return languagePackBasePage;
	}

	@Override
	public Result addOrUpdateLanguagePack(MultipartFile file, AdminLanguagePackBO adminLanguagePackBO) {

		AdminLanguagePack languagePack = getById(adminLanguagePackBO.getLanguagePackId());
		int count = 0;
		if (languagePack != null) {
			count = lambdaQuery().ne(AdminLanguagePack::getLanguagePackId, adminLanguagePackBO.getLanguagePackId()).
					eq(AdminLanguagePack::getLanguagePackName, adminLanguagePackBO.getLanguagePackName()).count();
		} else {
			count = lambdaQuery().eq(AdminLanguagePack::getLanguagePackName, adminLanguagePackBO.getLanguagePackName()).count();
		}
		if (count > 0) {
			throw new CrmException(AdminCodeEnum.ADMIN_LANGUAGE_PACK_NAME_ERROR);
		}

		String filePath = getFilePath(file);
		AtomicReference<Integer> num = new AtomicReference<>(0);
		StringBuilder contextStr = new StringBuilder();

		JSONObject jsonObject = new JSONObject();
		ExcelUtil.readBySax(filePath, 0, (int sheetIndex, int rowIndex, List<Object> rowList) -> {
			if (rowIndex > 0) {
				if (rowIndex > 1) {
					contextStr.append(",");
				}
				;
				num.getAndSet(num.get() + 1);
				String filedName = rowList.get(0).toString().trim();
				String filedCustom = "";
				if (rowList.size() > 2) {
					filedCustom = rowList.get(2).toString().trim();
				}
				if (filedName.contains(".")) {
					String[] filedNameArr = filedName.split("\\.");
					if (jsonObject.containsKey(filedNameArr[0])) {
						jsonObject.getJSONObject(filedNameArr[0]).put(filedNameArr[1], filedCustom);
					} else {
						JSONObject jsonObject1 = new JSONObject();
						jsonObject1.put(filedNameArr[1], filedCustom);
						jsonObject.put(filedNameArr[0], jsonObject1);
					}
				} else {
					jsonObject.put(filedName, filedCustom);
				}
			}
		});

		AdminLanguagePack adminLanguagePackPO = new AdminLanguagePack();
		adminLanguagePackPO.setLanguagePackName(adminLanguagePackBO.getLanguagePackName());
		if (languagePack != null) {
			adminLanguagePackPO.setLanguagePackId(languagePack.getLanguagePackId());
		} else {
			adminLanguagePackPO.setCreateTime(new Date());
		}
		adminLanguagePackPO.setLanguagePackContext(jsonObject.toString());
		adminLanguagePackPO.setCreateUserId(UserUtil.getUserId());
		saveOrUpdate(adminLanguagePackPO);
		return R.ok();
	}

	@Override
	public void deleteLanguagePackById(Integer id) {
		List<AdminUserConfig> AdminUserConfigs = adminUserConfigService.queryUserConfigByNameAndValue(DEFLAUT_LANGUAGE_PACK, id.toString());
		if (AdminUserConfigs.size() > 0) {
			throw new CrmException(AdminCodeEnum.ADMIN_LANGUAGE_PACK_EXIST_USER_ERROR);
		}
		removeById(id);
	}

	@Override
	public void exportLanguagePackById(Integer id, HttpServletResponse response) {
		AdminLanguagePack adminLanguagePack = getById(id);
		List<Map<String, Object>> objectList = new ArrayList<>();
		String translateContext = adminLanguagePack.getLanguagePackContext();
		AdminLanguagePack chineseLanguagePack = lambdaQuery().eq(AdminLanguagePack::getLanguagePackName, LANGUAGE_PACK_CHINESE).one();
		if (chineseLanguagePack == null) {
			throw new CrmException(AdminCodeEnum.ADMIN_LANGUAGE_PACK_CHOINESE_ERROR);
		}
		Map<String, String> translateMap = getFilePath(translateContext);
		Map<String, String> chineseMap = getFilePath(chineseLanguagePack.getLanguagePackContext());
		translateMap.forEach((k, v) -> {
			Map<String, Object> record = new HashMap<>();
			record.put("fileName", k);
			record.put("chinese", chineseMap.get(k));
			record.put("translateName", v);
			objectList.add(record);
		});

		List<ExcelParseUtil.ExcelDataEntity> dataList = new ArrayList<>();
		dataList.add(ExcelParseUtil.toEntity("fileName", "Original field"));
		if (LANGUAGE_PACK_CHINESE.equals(adminLanguagePack.getLanguagePackName())) {
			dataList.add(ExcelParseUtil.toEntity("chinese", "field name"));
		} else {
			dataList.add(ExcelParseUtil.toEntity("chinese", LANGUAGE_PACK_CHINESE));
		}
		dataList.add(ExcelParseUtil.toEntity("translateName", adminLanguagePack.getLanguagePackName()));
		ExcelParseUtil.exportExcel(objectList, new ExcelParseUtil.ExcelParseService() {
			@Override
			public void castData(Map<String, Object> record, Map<String, Integer> headMap) {

			}

			@Override
			public String getExcelName() {
				return "language pack";
			}
		}, dataList);
	}

	/**
	 * Parse json language package
	 */
	private Map<String, String> getFilePath(String languagePackContext) {
		JSONObject jsonObject = JSONObject.parseObjectOrdered(languagePackContext);

		HashMap<String, String> fileMap = new LinkedHashMap<>();
		if (jsonObject != null) {
			for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
				Object o = entry.getValue();
				if (o instanceof String) {
					fileMap.put(entry.getKey(), entry.getValue().toString());
				} else if (o instanceof JSONObject) {
					JSONObject jsonObject2 = (JSONObject) o;
					for (Map.Entry<String, Object> entry2 : jsonObject2.entrySet()) {
						Object o2 = entry2.getValue();
						if (o2 instanceof String) {
							fileMap.put(entry.getKey() + "." + entry2.getKey(), entry2.getValue().toString());
						}
					}
				}
			}
		}
		return fileMap;
	}

	@Override
	public String queryLanguagePackContextById(Integer id) {
		return getById(id).getLanguagePackContext();
	}

	@Override
	public void downloadExcel(HttpServletResponse response) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		try (ExcelWriter writer = ExcelUtil.getWriter()) {
			writer.addHeaderAlias("file", "Original field");
			writer.addHeaderAlias("chinese", "Chinese");
			writer.addHeaderAlias("translate", "English");
			Map<String, Object> record = new HashMap<>();
			record.put("file", "customer");
			record.put("chinese", "customer");
			record.put("translate", "Customer");
			dataList.add(record);
			writer.setOnlyAlias(true);
			writer.write(dataList, true);
			writer.setRowHeight(0, 20);
			for (int i = 0; i < 3; i++) {
				writer.setColumnWidth(i, 30);
			}
			Cell cell = writer.getCell(0, 0);
			CellStyle cellStyle = cell.getCellStyle();
			cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			Font font = writer.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) 16);
			cellStyle.setFont(font);
			cell.setCellStyle(cellStyle);
			// custom title alias
			//response is the HttpServletResponse object
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			//test.xls is the file name of the pop-up download dialog, which cannot be Chinese, please encode the Chinese by yourself
			response.setHeader("Content-Disposition", "attachment;filename=languagePack_imp.xls");
			ServletOutputStream out = response.getOutputStream();
			writer.flush(out);
		} catch (Exception e) {
			log.error("Error exporting language pack: ", e);
		}
	}

	@Override
	public void updateLanguagePackNameById(AdminLanguagePackBO adminLanguagePackBO) {
		AdminLanguagePack oldAdminLanguagePac = lambdaQuery().ne(AdminLanguagePack::getLanguagePackId, adminLanguagePackBO.getLanguagePackId()).
				eq(AdminLanguagePack::getLanguagePackName, adminLanguagePackBO.getLanguagePackName()).one();
		if (oldAdminLanguagePac != null) {
			throw new CrmException(AdminCodeEnum.ADMIN_LANGUAGE_PACK_NAME_ERROR);
		}
		AdminLanguagePack languagePack = getById(adminLanguagePackBO.getLanguagePackId());
		languagePack.setLanguagePackName(adminLanguagePackBO.getLanguagePackName());
		saveOrUpdate(languagePack);
	}

	@Override
	public void setDeflautLanguagePackSetting(Integer id, Integer systemOrUser) {
		//Modify the default language systemOrUser(0: system user, 1: personal user)
		adminConfigService.queryConfigByName(DEFLAUT_LANGUAGE_PACK);
		if (systemOrUser == SYSTEM) {
			AdminConfig adminConfig = adminConfigService.queryConfigByName(DEFLAUT_LANGUAGE_PACK);
			if (adminConfig != null) {
				adminConfig.setValue(id.toString());
				adminConfigService.updateById(adminConfig);
			} else {
				adminConfig = new AdminConfig();
				adminConfig.setStatus(1);
				adminConfig.setName(DEFLAUT_LANGUAGE_PACK);
				adminConfig.setValue(id.toString());
				adminConfig.setDescription(DEFLAUT_LANGUAGE_PACK_DESCRIBE);
				adminConfigService.save(adminConfig);
			}
		} else if (systemOrUser == USER) {
			AdminUserConfig userConfig = adminUserConfigService.queryUserConfigByName(DEFLAUT_LANGUAGE_PACK);
			if (userConfig != null) {
				userConfig.setValue(id.toString());
				adminUserConfigService.updateById(userConfig);
			} else {
				userConfig = new AdminUserConfig();
				userConfig.setStatus(1);
				userConfig.setName(DEFLAUT_LANGUAGE_PACK);
				userConfig.setValue(id.toString());
				userConfig.setUserId(UserUtil.getUserId());
				userConfig.setDescription(DEFLAUT_LANGUAGE_PACK_DESCRIBE);
				adminUserConfigService.save(userConfig);
			}
		}
	}

	@Override
	public JSONObject queryDeflautLanguagePackSetting(Integer systemOrUser) {
		//Query the default language systemOrUser(0: system user, 1: personal user)
		int languagePackId = 0;
		String languagePackName = null;
		String languagePackContext = null;

		if (systemOrUser == SYSTEM) {
			AdminConfig adminConfig = adminConfigService.queryConfigByName(DEFLAUT_LANGUAGE_PACK);
			if (adminConfig != null) {
				languagePackId = Integer.parseInt(adminConfig.getValue());
				AdminLanguagePack adminLanguagePack = getById(languagePackId);
				languagePackName = adminLanguagePack.getLanguagePackName();
				languagePackContext = adminLanguagePack.getLanguagePackContext();
			}
		} else if (systemOrUser == USER) {
			//Personal and system have default language packs, personal priority is higher than system
			AdminUserConfig userConfig = adminUserConfigService.queryUserConfigByName(DEFLAUT_LANGUAGE_PACK);
			if (userConfig != null) {
				languagePackId = Integer.parseInt(userConfig.getValue());
				AdminLanguagePack adminLanguagePack = getById(languagePackId);
				languagePackName = adminLanguagePack.getLanguagePackName();
				languagePackContext = adminLanguagePack.getLanguagePackContext();
			} else {
				AdminConfig adminConfig = adminConfigService.queryConfigByName(DEFLAUT_LANGUAGE_PACK);
				if (adminConfig != null) {
					languagePackId = Integer.parseInt(adminConfig.getValue());
					AdminLanguagePack adminLanguagePack = getById(languagePackId);
					languagePackName = adminLanguagePack.getLanguagePackName();
					languagePackContext = adminLanguagePack.getLanguagePackContext();
				}
			}
		}
		return new JSONObject().fluentPut("languagePackId", languagePackId).fluentPut("languagePackName", languagePackName).fluentPut("languagePackContext", languagePackContext);
	}

	private String getFilePath(MultipartFile file) {
		String dirPath = FileUtil.getTmpDirPath();
		try {
			InputStream inputStream = file.getInputStream();
			File fromStream = FileUtil.writeFromStream(inputStream, dirPath + "/" + IdUtil.simpleUUID() + file.getOriginalFilename());
			return fromStream.getAbsolutePath();
		} catch (IOException e) {
			throw new CrmException(SystemCodeEnum.SYSTEM_UPLOAD_FILE_ERROR);
		}
	}

}
