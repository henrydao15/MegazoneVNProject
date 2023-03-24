package com.megazone.crm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.BO.*;
import com.megazone.crm.entity.PO.CrmContacts;
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
 * @since 2020-05-26
 */
public interface ICrmContactsService extends BaseService<CrmContacts> {
	/**
	 * @param id ID
	 * @return data
	 */
	public List<CrmModelFiledVO> queryField(Integer id);

	public List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);

	/**
	 * @param search
	 * @param isExcel true:  false:
	 * @return
	 */
	public BasePage<Map<String, Object>> queryPageList(@RequestBody CrmSearchBO search);

	/**
	 * @param id ID
	 * @return data
	 */
	public CrmModel queryById(Integer id);

	/**
	 * @param crmModel model
	 */
	public void addOrUpdate(CrmContactsSaveBO crmModel, boolean isExcel);

	/**
	 * @param ids ids
	 */
	public void deleteByIds(List<Integer> ids);

	/**
	 * @param crmChangeOwnerUserBO BO
	 */
	public void changeOwnerUser(CrmChangeOwnerUserBO crmChangeOwnerUserBO);


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
	 * @param contactsId id
	 */
	public void star(Integer contactsId);

	/**
	 * @param contactsId id
	 * @return data
	 */
	public List<CrmModelFiledVO> information(Integer contactsId);

	/**
	 * @param contactsId id
	 * @return data
	 */
	public CrmInfoNumVO num(Integer contactsId);

	/**
	 * @param contactsId id
	 * @return file
	 */
	public List<FileEntity> queryFileList(Integer contactsId);

	public List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

	String getContactsName(int contactsId);

	void updateInformation(CrmUpdateInformationBO updateInformationBO);

	BasePage<Map<String, Object>> queryBusiness(CrmBusinessPageBO businessPageBO);

	void relateBusiness(CrmRelateBusinessBO relateBusinessBO);

	void unrelateBusiness(CrmRelateBusinessBO relateBusinessBO);
}
