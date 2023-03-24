package com.megazone.crm.service;

import com.megazone.core.entity.BasePage;
import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.BO.CrmPrintTemplateBO;
import com.megazone.crm.entity.PO.CrmPrintRecord;
import com.megazone.crm.entity.PO.CrmPrintTemplate;
import com.megazone.crm.entity.VO.CrmPrintFieldVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-04-27
 */
public interface ICrmPrintTemplateService extends BaseService<CrmPrintTemplate> {

	/**
	 * @param templateBO search
	 * @return data
	 */
	public BasePage<CrmPrintTemplate> queryPrintTemplateList(CrmPrintTemplateBO templateBO);

	/**
	 * @param templateId templateId
	 */
	public void deletePrintTemplate(Integer templateId);

	/**
	 * @param crmType type
	 * @return data
	 */
	public CrmPrintFieldVO queryFields(Integer crmType);

	/**
	 * @param templateId templateId
	 * @param id         id
	 * @return data
	 */
	public String print(Integer templateId, Integer id);

	/**
	 * @param content content
	 * @param type    type
	 * @return path
	 */
	public String preview(String content, String type);

	/**
	 * @param templateId templateId
	 */
	public void copyTemplate(Integer templateId);

	/**
	 * @param crmPrintRecord record
	 */
	public void savePrintRecord(CrmPrintRecord crmPrintRecord);

	/**
	 * @param crmType crm
	 * @param typeId  ID
	 */
	public List<CrmPrintRecord> queryPrintRecord(Integer crmType, Integer typeId);

	/**
	 * @param recordId recordId
	 * @return data
	 */
	public CrmPrintRecord queryPrintRecordById(Integer recordId);
}
