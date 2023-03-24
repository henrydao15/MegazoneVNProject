package com.megazone.crm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.BO.CrmBusinessSaveBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.CrmReturnVisit;
import com.megazone.crm.entity.VO.CrmModelFiledVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
public interface ICrmReturnVisitService extends BaseService<CrmReturnVisit> {

	BasePage<Map<String, Object>> queryPageList(CrmSearchBO search);

	List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

	void addOrUpdate(CrmBusinessSaveBO crmModel);

	CrmModel queryById(Integer visitId);

	List<CrmModelFiledVO> queryField(Integer id);

	List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);

	List<CrmModelFiledVO> information(Integer visitId);

	List<FileEntity> queryFileList(Integer id);

	void deleteByIds(List<Integer> ids);

	/**
	 * @param status
	 * @param value
	 */
	public void updateReturnVisitRemindConfig(Integer status, Integer value);

	/**
	 * @return data
	 */
	public AdminConfig queryReturnVisitRemindConfig();

	public void updateInformation(CrmUpdateInformationBO updateInformationBO);
}
