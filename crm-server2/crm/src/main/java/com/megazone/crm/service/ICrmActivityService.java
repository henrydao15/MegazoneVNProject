package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.constant.CrmActivityEnum;
import com.megazone.crm.entity.BO.CrmActivityBO;
import com.megazone.crm.entity.PO.CrmActivity;
import com.megazone.crm.entity.VO.CrmActivityVO;

import java.util.List;

/**
 * <p>
 * crm
 * </p>
 *
 * @author
 * @since 2020-05-25
 */
public interface ICrmActivityService extends BaseService<CrmActivity> {

	/**
	 * @param crmActivityEnum
	 * @param ids             ids
	 */
	public void deleteActivityRecord(CrmActivityEnum crmActivityEnum, List<Integer> ids);

	/**
	 * @param type           type
	 * @param activityEnum
	 * @param activityTypeId ID
	 * @param businessChange
	 */
	public void addActivity(Integer type, CrmActivityEnum activityEnum, Integer activityTypeId, String businessChange);

	/**
	 * @param type           type
	 * @param activityEnum
	 * @param activityTypeId ID
	 */
	public void addActivity(Integer type, CrmActivityEnum activityEnum, Integer activityTypeId);

	/**
	 * @param crmActivity activity
	 * @return data
	 */
	public CrmActivityVO getCrmActivityPageList(CrmActivityBO crmActivity);

	/**
	 * @param crmActivity crmActivity
	 */
	public void addCrmActivityRecord(CrmActivity crmActivity);

	/**
	 * @param id           id
	 * @param activityType
	 * @return data
	 */
	public List<String> queryFileBatchId(Integer id, Integer activityType);

	public void updateNextTime(CrmActivity crmActivity);

	public void buildActivityRelation(CrmActivity record);

	void deleteCrmActivityRecord(Integer activityId);

	CrmActivity updateActivityRecord(CrmActivity crmActivity);

	/**
	 *
	 */
	public void outworkSign(CrmActivity crmActivity);

	/**
	 * app
	 */
	public BasePage<JSONObject> queryOutworkStats(PageEntity entity, String startTime, String endTime);

	/**
	 * app
	 */
	public BasePage<CrmActivity> queryOutworkList(PageEntity entity, String startTime, String endTime, Long userId);

	/**
	 * app
	 */
	public Integer queryPictureSetting();

	/**
	 * app
	 */
	public void setPictureSetting(Integer status);

	/**
	 *
	 */
	public void deleteOutworkSign(Integer activityId);
}
