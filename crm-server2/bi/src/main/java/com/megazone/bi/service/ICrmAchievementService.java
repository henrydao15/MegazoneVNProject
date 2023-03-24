package com.megazone.bi.service;

import com.megazone.bi.entity.BO.AchievementBO;
import com.megazone.bi.entity.PO.CrmAchievement;
import com.megazone.core.servlet.BaseService;

import java.util.List;

public interface ICrmAchievementService extends BaseService<CrmAchievement> {

	List<CrmAchievement> queryAchievementList(AchievementBO achievementBO);

	void addAchievement(CrmAchievement achievement);

	void verifyCrmAchievementData(List<CrmAchievement> crmAchievements);

}
