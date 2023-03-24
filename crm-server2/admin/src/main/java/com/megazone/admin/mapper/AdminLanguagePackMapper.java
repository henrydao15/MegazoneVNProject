package com.megazone.admin.mapper;

import com.megazone.admin.entity.PO.AdminLanguagePack;
import com.megazone.admin.entity.VO.AdminLanguagePackVO;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseMapper;

public interface AdminLanguagePackMapper extends BaseMapper<AdminLanguagePack> {

	BasePage<AdminLanguagePackVO> queryLanguagePackList(BasePage<AdminLanguagePackVO> page);
}
