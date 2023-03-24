package com.megazone.crm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.feign.crm.entity.SimpleCrmEntity;
import com.megazone.core.servlet.BaseService;
import com.megazone.core.servlet.upload.FileEntity;
import com.megazone.crm.entity.BO.CrmContractSaveBO;
import com.megazone.crm.entity.BO.CrmSearchBO;
import com.megazone.crm.entity.BO.CrmUpdateInformationBO;
import com.megazone.crm.entity.PO.CrmInvoice;
import com.megazone.crm.entity.PO.CrmInvoiceInfo;
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
 * @since 2020-07-06
 */
public interface ICrmInvoiceService extends BaseService<CrmInvoice> {

	List<SimpleCrmEntity> querySimpleEntity(List<Integer> ids);

	CrmInvoice queryById(Integer invoiceId);

	void updateInvoiceStatus(CrmInvoice crmInvoice);

	void deleteByIds(List<Integer> ids);

	void changeOwnerUser(List<Integer> ids, Long ownerUserId);

	List<FileEntity> queryFileList(Integer id);

	void saveInvoiceInfo(CrmInvoiceInfo crmInvoiceInfo);

	void updateInvoiceInfo(CrmInvoiceInfo crmInvoiceInfo);

	void deleteInvoiceInfo(Integer infoId);

	BasePage<Map<String, Object>> queryPageList(CrmSearchBO search);

	List<CrmModelFiledVO> queryField(Integer id);

	List<List<CrmModelFiledVO>> queryFormPositionField(Integer id);

	void addOrUpdate(CrmContractSaveBO crmModel, boolean isExcel);

	/**
	 * @param response resp
	 * @param search
	 */
	void exportExcel(HttpServletResponse response, CrmSearchBO search);

	List<CrmModelFiledVO> information(Integer invoiceId);

	void updateInformation(CrmUpdateInformationBO updateInformationBO);
}
