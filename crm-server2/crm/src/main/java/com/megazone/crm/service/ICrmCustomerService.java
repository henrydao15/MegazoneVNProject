package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.PageEntity;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmContacts;
import com.megazone.crm.entity.PO.CrmCustomer;
import com.megazone.crm.entity.PO.CrmCustomerSetting;
import com.megazone.crm.entity.VO.CrmDataCheckVO;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-29
 */
public interface ICrmCustomerService extends BaseService<CrmCustomer> {
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
	public CrmModel queryById(Integer id, Integer poolId);

	/**
	 * @param crmModel model
	 */
	public Map<String, Object> addOrUpdate(CrmModelSaveBO crmModel, boolean isExcel, Integer poolId);

	/**
	 * @param ids ids
	 */
	public void deleteByIds(List<Integer> ids);


	/**
	 * @param ids ids
	 */
	JSONObject detectionDataCanBeDelete(List<Integer> ids);

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
	 * @param poolBO bo
	 */
	public void updateCustomerByIds(CrmCustomerPoolBO poolBO);

	/**
	 * @param customerId id
	 */
	public void star(Integer customerId);

	/**
	 * @param contactsBO data
	 */
	public void setContacts(CrmFirstContactsBO contactsBO);

	/**
	 * @param poolBO    bo
	 * @param isReceive
	 */
	public void getCustomersByIds(CrmCustomerPoolBO poolBO, Integer isReceive);

	/**
	 * @param isPool
	 * @param response resp
	 * @throws IOException ex
	 */
	public void downloadExcel(boolean isPool, HttpServletResponse response) throws IOException;

	/**
	 * @param customerSetting setting
	 */
	public void customerSetting(CrmCustomerSetting customerSetting);

	/**
	 * @param settingId settingId
	 */
	public void deleteCustomerSetting(Integer settingId);

	/**
	 * @param customerId id
	 * @param poolId     ID
	 * @return data
	 */
	public List<CrmModelFiledVO> information(Integer customerId, Integer poolId);

	/**
	 * @param dealStatus
	 * @param ids        ids
	 */
	public void setDealStatus(Integer dealStatus, List<Integer> ids);

	/**
	 * @param pageEntity entity
	 * @param type       type
	 */
	public BasePage<CrmCustomerSetting> queryCustomerSetting(PageEntity pageEntity, Integer type);

	/**
	 * @param pageEntity entity
	 * @return data
	 */
	public BasePage<CrmContacts> queryContacts(CrmContactsPageBO pageEntity);

	/**
	 * @param pageEntity entity
	 * @return data
	 */
	public BasePage<Map<String, Object>> queryBusiness(CrmContactsPageBO pageEntity);

	/**
	 * @param pageEntity entity
	 * @return data
	 */
	public BasePage<Map<String, Object>> queryContract(CrmContactsPageBO pageEntity);

	/**
	 * @param status
	 * @param ids    ids
	 */
	public void lock(Integer status, List<String> ids);

	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

	List<SimpleCrmEntity> queryByNameCustomerInfo(String name);

	List<SimpleCrmEntity> queryNameCustomerInfo(String name);

	/**
	 * @param name name
	 * @return data
	 */
	public SimpleCrmEntity queryFirstCustomerByName(String name);

	/**
	 * @param customerId id
	 * @return data
	 */
	public CrmInfoNumVO num(Integer customerId);

	/**
	 * @param customerId id
	 * @return file
	 */
	public List<FileEntity> queryFileList(Integer customerId);

	/**
	 * @param customerId id
	 * @return data
	 */
	public String getCustomerName(Integer customerId);

	boolean isMaxOwner(Long ownerUserId, List<Integer> ids);

	void updateInformation(CrmUpdateInformationBO updateInformationBO);

	List<CrmDataCheckVO> dataCheck(CrmDataCheckBO dataCheckBO);

	BasePage<JSONObject> queryReceivablesPlan(CrmRelationPageBO crmRelationPageBO);

	BasePage<JSONObject> queryReceivables(CrmRelationPageBO crmRelationPageBO);

	BasePage<JSONObject> queryReturnVisit(CrmRelationPageBO crmRelationPageBO);

	BasePage<JSONObject> queryInvoice(CrmRelationPageBO crmRelationPageBO);

	BasePage<JSONObject> queryInvoiceInfo(CrmRelationPageBO crmRelationPageBO);

	BasePage<JSONObject> queryCallRecord(CrmRelationPageBO crmRelationPageBO);

	List<JSONObject> nearbyCustomer(String lng, String lat, Integer type, Integer radius, Long ownerUserId);

	List<String> eventCustomer(CrmEventBO crmEventBO);

	BasePage<Map<String, Object>> eventCustomerPageList(QueryEventCrmPageBO eventCrmPageBO);

	List<Integer> forgottenCustomer(Integer day, List<Long> userIds, String search);

	List<Integer> unContactCustomer(String search, List<Long> userIds);
}
