package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmBusiness;
import com.megazone.crm.entity.PO.CrmContacts;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
public interface ICrmBusinessService extends BaseService<CrmBusiness> {

	/**
	 * @param id ID
	 * @return data
	 */
	public List<CrmModelFiledVO> queryField(Integer id);

	public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);

	/**
	 * @param search
	 * @return data
	 */
	public BasePage<Map<String, Object>> queryPageList(CrmSearchBO search);

	/**
	 * @param id ID
	 * @return data
	 */
	public CrmModel queryById(Integer id);

	/**
	 * @param crmModel model
	 */
	public void addOrUpdate(CrmBusinessSaveBO crmModel);

	/**
	 * @param ids ids
	 */
	public void deleteByIds(List<Integer> ids);

	/**
	 * @param changOwnerUserBO data
	 */
	public void changeOwnerUser(CrmChangeOwnerUserBO changOwnerUserBO);

	/**
	 * @param response resp
	 * @param search
	 */
	public void exportExcel(HttpServletResponse response, CrmSearchBO search);

	/**
	 * @param pageEntity entity
	 * @return data
	 */
	public BasePage<CrmContacts> queryContacts(CrmContactsPageBO pageEntity);

	/**
	 * @param businessId id
	 * @return data
	 */
	public List<CrmModelFiledVO> information(Integer businessId);

	/**
	 * @param businessId id
	 */
	public void star(Integer businessId);

	/**
	 * @param businessId id
	 * @return data
	 */
	public CrmInfoNumVO num(Integer businessId);

	/**
	 * @param businessId id
	 * @return file
	 */
	public List<FileEntity> queryFileList(Integer businessId);

	/**
	 * @param contactsBO data
	 */
	public void setContacts(CrmFirstContactsBO contactsBO);

	/**
	 * @param relevanceBusinessBO
	 */
	public void relateContacts(CrmRelevanceBusinessBO relevanceBusinessBO);

	/**
	 * @param relevanceBusinessBO
	 */
	public void unrelateContacts(CrmRelevanceBusinessBO relevanceBusinessBO);

	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

	String getBusinessName(int businessId);

	void updateInformation(CrmUpdateInformationBO updateInformationBO);

	JSONObject queryProduct(CrmBusinessQueryRelationBO businessQueryProductBO);

	BasePage<JSONObject> queryContract(CrmBusinessQueryRelationBO businessQueryRelationBO);

	List<String> eventDealBusiness(CrmEventBO crmEventBO);

	BasePage<Map<String, Object>> eventDealBusinessPageList(QueryEventCrmPageBO eventCrmPageBO);

	List<String> eventBusiness(CrmEventBO crmEventBO);

	BasePage<Map<String, Object>> eventBusinessPageList(QueryEventCrmPageBO eventCrmPageBO);
}
