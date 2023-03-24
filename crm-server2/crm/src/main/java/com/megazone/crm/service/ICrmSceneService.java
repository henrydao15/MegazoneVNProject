package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmSceneConfigBO;
import com.megazone.crm.entity.PO.CrmScene;
import com.megazone.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-06-06
 */
public interface ICrmSceneService extends BaseService<CrmScene> {

	/**
	 * @param crmEnum
	 * @return data
	 */
	public List<CrmScene> queryScene(CrmEnum crmEnum);

	/**
	 * @param label label
	 * @return data
	 */
	public List<CrmModelFiledVO> queryField(Integer label);

	/**
	 * @param crmScene data
	 */
	public void addScene(CrmScene crmScene);

	/**
	 * @param crmScene data
	 */
	public void updateScene(CrmScene crmScene);

	/**
	 * @param sceneId sceneId
	 */
	public void setDefaultScene(Integer sceneId);

	/**
	 * @param sceneId sceneId
	 */
	public void deleteScene(Integer sceneId);

	/**
	 * @param type type
	 * @return data
	 */
	public JSONObject querySceneConfig(Integer type);

	/**
	 *
	 */
	public void sceneConfig(CrmSceneConfigBO config);
}
