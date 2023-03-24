package com.megazone.crm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.BO.CrmModelSaveBO;
import com.megazone.crm.entity.BO.CrmProductStatusBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.CrmProduct;
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
 * @since 2020-05-26
 */
public interface ICrmProductService extends BaseService<CrmProduct> {
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
	public void addOrUpdate(CrmModelSaveBO crmModel, boolean isExcel);

	/**
	 * @param ids ids
	 */
	public void deleteByIds(List<Integer> ids);

	/**
	 * @param ids            id
	 * @param newOwnerUserId ID
	 */
	public void changeOwnerUser(List<Integer> ids, Long newOwnerUserId);


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
	 * @param productStatus status
	 */
	public void updateStatus(CrmProductStatusBO productStatus);

	/**
	 * @param productId id
	 * @return data
	 */
	public List<CrmModelFiledVO> information(Integer productId);

	/**
	 * @param productId id
	 * @return data
	 */
	public CrmInfoNumVO num(Integer productId);

	/**
	 * @param productId id
	 * @return file
	 */
	public List<FileEntity> queryFileList(Integer productId);

	/**
	 * @return list
	 */
	public List<SimpleCrmEntity> querySimpleEntity();

	void updateInformation(CrmUpdateInformationBO updateInformationBO);

}
