package com.megazone.admin.service;

import com.megazone.admin.entity.BO.AdminLanguagePackBO;
import com.megazone.admin.entity.PO.AdminLanguagePack;
import com.megazone.admin.entity.VO.AdminLanguagePackVO;
import com.megazone.core.common.JSONObject;
import com.megazone.core.common.Result;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


public interface IAdminLanguagePackService extends BaseService<AdminLanguagePack> {

	BasePage<AdminLanguagePackVO> queryLanguagePackList(AdminLanguagePackBO adminLanguagePackBO, Integer systemOrUser);

	Result addOrUpdateLanguagePack(MultipartFile file, AdminLanguagePackBO adminLanguagePackBO);

	void deleteLanguagePackById(Integer id);

	void exportLanguagePackById(Integer id, HttpServletResponse response);

	String queryLanguagePackContextById(Integer id);

	void downloadExcel(HttpServletResponse response);

	void updateLanguagePackNameById(AdminLanguagePackBO adminLanguagePackBO);

	void setDeflautLanguagePackSetting(Integer id, Integer systemOrUser);

	JSONObject queryDeflautLanguagePackSetting(Integer systemOrUser);

}
