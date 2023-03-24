package com.megazone.crm.service;

import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.BO.CrmChangeOwnerUserBO;
import com.megazone.crm.entity.BO.CrmContractSaveBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.CrmReceivables;
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
 * @since 2020-05-28
 */
public interface ICrmReceivablesService extends BaseService<CrmReceivables> {
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

	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

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
	 * @param changeOwnerUserBO BO
	 */
	public void changeOwnerUser(CrmChangeOwnerUserBO changeOwnerUserBO);

	/**
	 * @param response resp
	 * @param search
	 */
	public void exportExcel(HttpServletResponse response, CrmSearchBO search);

	/**
	 * @param id id
	 * @return data
	 */
	public CrmInfoNumVO num(Integer id);

	/**
	 * @param id id
	 * @return file
	 */
	public List<FileEntity> queryFileList(Integer id);

	/**
	 * @param receivablesId ID
	 * @return data
	 */
	public List<CrmModelFiledVO> information(Integer receivablesId);

	void updateInformation(CrmUpdateInformationBO updateInformationBO);

	BasePage<JSONObject> queryListByContractId(BasePage<JSONObject> page, Integer contractId, String conditions);
}
