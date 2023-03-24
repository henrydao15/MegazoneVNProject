package com.megazone.admin.service;

import com.megazone.admin.entity.BO.AdminCompanyBO;
import com.megazone.admin.entity.BO.AdminInitDataBO;
import com.megazone.admin.entity.BO.LogWelcomeSpeechBO;
import com.megazone.admin.entity.PO.AdminConfig;
import com.megazone.admin.entity.VO.ModuleSettingVO;
import com.megazone.core.servlet.BaseService;

import java.util.List;

public interface IAdminConfigService extends BaseService<AdminConfig> {

	List<AdminConfig> queryConfigListByName(Object... names);

	void setAdminConfig(AdminCompanyBO adminConfig);

	AdminCompanyBO queryAdminConfig();

	List<ModuleSettingVO> queryModuleSetting();

	void setModuleSetting(AdminConfig adminConfig);

	void setLogWelcomeSpeech(List<String> stringList);

	List<LogWelcomeSpeechBO> getLogWelcomeSpeechList();

	AdminConfig queryConfigByName(String name);

	ModuleSettingVO queryCallModuleSetting();


	void updateAdminConfig(AdminConfig adminConfig);

	AdminConfig queryFirstConfigByNameAndValue(String name, String value);

	void setMarketing(Integer status);

	Integer queryMarketing();

	String verifyPassword(AdminInitDataBO adminInitDataBO);

	boolean moduleInitData(AdminInitDataBO adminInitDataBO);
}
