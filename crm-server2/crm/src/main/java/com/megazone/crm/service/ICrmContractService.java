package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.BiParams;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmContract;
import com.megazone.crm.entity.PO.CrmReceivablesPlan;
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
public interface ICrmContractService extends BaseService<CrmContract> {

	/**
	 * @param id ID
	 * @return data
	 */
	public List<CrmModelFiledVO> queryField(Integer id);

	public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);

	/**
	 * @param search
	 * @return
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
	public void addOrUpdate(CrmContractSaveBO crmModel);

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
	 * @param contractId ID
	 * @return data
	 */
	public List<CrmModelFiledVO> information(Integer contractId);

	/**
	 * @param crmRelationPageBO ID
	 * @return data
	 */
	public JSONObject queryProductListByContractId(CrmRelationPageBO crmRelationPageBO);

	/**
	 * @param contractId id
	 * @return data
	 */
	public CrmInfoNumVO num(Integer contractId);

	/**
	 * @param contractId id
	 * @return file
	 */
	public List<FileEntity> queryFileList(Integer contractId);

	/**
	 * @param crmRelationPageBO ID
	 * @return data
	 */
	public BasePage<CrmReceivablesPlan> queryReceivablesPlanListByContractId(CrmRelationPageBO crmRelationPageBO);

	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

	String getContractName(int contractId);

	void updateInformation(CrmUpdateInformationBO updateInformationBO);

	BasePage<JSONObject> queryListByContractId(CrmRelationPageBO crmRelationPageBO);

	List<CrmReceivablesPlan> queryReceivablesPlansByContractId(Integer contractId, Integer receivablesId);

	BasePage<JSONObject> queryReturnVisit(CrmRelationPageBO crmRelationPageBO);

	void contractDiscard(Integer contractId);

	List<String> endContract(CrmEventBO crmEventBO);

	List<String> receiveContract(CrmEventBO crmEventBO);

	BasePage<Map<String, Object>> eventContractPageList(QueryEventCrmPageBO eventCrmPageBO);

	/**
	 * @param biParams
	 * @return data
	 */
	public BasePage<Map<String, Object>> queryListByProductId(BiParams biParams);
}
