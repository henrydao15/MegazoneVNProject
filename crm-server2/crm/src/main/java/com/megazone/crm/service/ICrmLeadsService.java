package com.megazone.crm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.CrmEventBO;
import com.megazone.core.feign.crm.entity.QueryEventCrmPageBO;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.BO.CrmModelSaveBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.CrmLeads;
import com.megazone.crm.entity.VO.CrmInfoNumVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import org.springframework.web.bind.annotation.RequestBody;

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
 * @since 2020-05-21
 */
public interface ICrmLeadsService extends BaseService<CrmLeads> {
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
	public BasePage<Map<String, Object>> queryPageList(@RequestBody CrmSearchBO search);


	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

	/**
	 * @param id ID
	 * @return data
	 */
	public CrmModel queryById(Integer id);

	/**
	 * @param crmModel model
	 * @param isExcel  excel
	 */
	public void addOrUpdate(CrmModelSaveBO crmModel, boolean isExcel);

	/**
	 * @param ids ids
	 */
	public void deleteByIds(List<Integer> ids);

	/**
	 * @param leadsIds       id
	 * @param newOwnerUserId ID
	 */
	public void changeOwnerUser(List<Integer> leadsIds, Long newOwnerUserId);

	/**
	 * @param leadsIds id
	 */
	public void transfer(List<Integer> leadsIds);

	/**
	 * @param response id
	 * @throws IOException exception
	 */
	public void downloadExcel(HttpServletResponse response) throws IOException;

	/**
	 * @param response resp
	 * @param search
	 */
	public void exportExcel(HttpServletResponse response, CrmSearchBO search);

	/**
	 * @param leads id
	 */
	public void star(Integer leads);

	/**
	 * @param leadsId id
	 * @return data
	 */
	public List<CrmModelFiledVO> information(Integer leadsId);

	/**
	 * @param leadsId id
	 * @return data
	 */
	public CrmInfoNumVO num(Integer leadsId);

	/**
	 * @param leadsId id
	 * @return file
	 */
	public List<FileEntity> queryFileList(Integer leadsId);

	void updateInformation(CrmUpdateInformationBO updateInformationBO);

	List<String> eventLeads(CrmEventBO crmEventBO);

	BasePage<Map<String, Object>> eventLeadsPageList(QueryEventCrmPageBO eventCrmPageBO);
}
