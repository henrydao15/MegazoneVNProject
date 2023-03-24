package com.megazone.crm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmBusinessSaveBO;
import com.megazone.crm.entity.BO.CrmReceivablesPlanBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.CrmReceivablesPlan;
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
 * @since 2020-05-28
 */
public interface ICrmReceivablesPlanService extends BaseService<CrmReceivablesPlan> {


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
	 * @param receivablesPlans
	 */
	public void batchSave(List<CrmReceivablesPlanBO> receivablesPlans);


	/**
	 * @param contractId ID
	 */
	public void deleteByContractId(Integer contractId);

	/**
	 * @param crmEnum       crmEnum
	 * @param object        PO
	 * @param examineStatus
	 */
	public void updateReceivedStatus(CrmEnum crmEnum, Object object, Integer examineStatus);


	/**
	 *
	 */
	public void updateReceivedStatus();

	/**
	 * @param response resp
	 * @param search
	 */
	public void exportExcel(HttpServletResponse response, CrmSearchBO search);

	/**
	 * @param crmModel data
	 */
	public void addOrUpdate(CrmBusinessSaveBO crmModel);

	/**
	 * @param receivablesPlanId id
	 * @return data
	 */
	public List<CrmModelFiledVO> information(Integer receivablesPlanId);

	/**
	 * @param ids ids
	 */
	public void deleteByIds(List<Integer> ids);

	/**
	 * @param updateInformationBO data
	 */
	public void updateInformation(CrmUpdateInformationBO updateInformationBO);

	/**
	 * @param id id
	 */
	public List<CrmModelFiledVO> queryField(Integer id);

	/**
	 * @param id id
	 * @return list
	 */
	public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);

	/**
	 * @param crmReceivablesPlanBO param
	 * @return data
	 */
	public List<CrmReceivablesPlan> queryByContractAndCustomer(CrmReceivablesPlanBO crmReceivablesPlanBO);

	String getReceivablesPlanNum(Integer receivablesPlanId);

	/**
	 * @param receivablesPlanId id
	 * @return file
	 */
	public List<FileEntity> queryFileList(Integer receivablesPlanId);
}
