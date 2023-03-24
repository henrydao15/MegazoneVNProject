package com.megazone.hrm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.hrm.entity.BO.SetAchievementTableBO;
import com.megazone.hrm.entity.PO.HrmAchievementTable;
import com.megazone.hrm.entity.VO.AchievementTableVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-12
 */
public interface IHrmAchievementTableService extends BaseService<HrmAchievementTable> {

	/**
	 * @param setAchievementTableBO
	 * @return
	 */
	HrmAchievementTable setAchievementTable(SetAchievementTableBO setAchievementTableBO);

	/**
	 * @param tableId
	 * @return
	 */
	AchievementTableVO queryAchievementTableById(Integer tableId);

	/**
	 * @return
	 */
	List<HrmAchievementTable> queryAchievementTableList();

}
