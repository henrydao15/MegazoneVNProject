package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.BO.CrmCensusBO;
import com.megazone.crm.entity.BO.CrmMarketingPageBO;
import com.megazone.crm.entity.BO.CrmSyncDataBO;
import com.megazone.crm.entity.PO.CrmMarketing;
import com.megazone.crm.entity.VO.CrmModelFiledVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-08-12
 */
public interface ICrmMarketingService extends BaseService<CrmMarketing> {

	byte[] BYTES = "2dc587a2016c4bad9f30c1dab32a121a".getBytes();

	Integer[] FIXED_CRM_TYPE = {1, 2};

	void addOrUpdate(CrmMarketing crmMarketing);

	BasePage<CrmMarketing> queryPageList(CrmMarketingPageBO crmMarketingPageBO, Integer status);

	JSONObject queryById(Integer marketingId, String device);

	void deleteByIds(List<Integer> marketingIds);

	List<CrmModelFiledVO> queryField(Integer marketingId);

	void updateStatus(String marketingIds, Integer status);

	void updateShareNum(Integer marketingId, Integer num);

	BasePage<JSONObject> census(CrmCensusBO crmCensusBO);

	JSONObject queryAddField(String marketingId);

	void saveMarketingInfo(JSONObject data);

	void syncData(CrmSyncDataBO syncDataBO);
}
