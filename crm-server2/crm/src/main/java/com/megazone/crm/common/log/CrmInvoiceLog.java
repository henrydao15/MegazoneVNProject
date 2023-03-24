package com.megazone.crm.common.log;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.log.Content;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.BO.CrmChangeOwnerUserBO;
import com.megazone.crm.entity.BO.CrmContractSaveBO;
import com.megazone.crm.entity.PO.CrmInvoice;
import com.megazone.crm.service.ICrmInvoiceService;

import java.util.ArrayList;
import java.util.List;

public class CrmInvoiceLog {

	private SysLogUtil sysLogUtil = ApplicationContextHolder.getBean(SysLogUtil.class);

	private ICrmInvoiceService crmInvoiceService = ApplicationContextHolder.getBean(ICrmInvoiceService.class);


	public Content updateInvoice(CrmContractSaveBO crmModel) {
		CrmInvoice crmInvoice = BeanUtil.copyProperties(crmModel.getEntity(), CrmInvoice.class);
		String batchId = StrUtil.isNotEmpty(crmInvoice.getBatchId()) ? crmInvoice.getBatchId() : IdUtil.simpleUUID();
		sysLogUtil.updateRecord(crmModel.getField(), Dict.create().set("batchId", batchId).set("dataTableName", "wk_crm_invoice_data"));
		return sysLogUtil.updateRecord(BeanUtil.beanToMap(crmInvoiceService.getById(crmInvoice.getInvoiceId())), BeanUtil.beanToMap(crmInvoice), CrmEnum.INVOICE, crmInvoice.getInvoiceApplyNumber());
	}

	public Content updateInvoiceStatus(CrmInvoice crmInvoice) {
		CrmInvoice crmInvoice1 = crmInvoiceService.getById(crmInvoice.getInvoiceId());
		String detail = "Mark the invoice " + crmInvoice1.getInvoiceApplyNumber() + " as invoiced.";
		return new Content(crmInvoice1.getInvoiceApplyNumber(), detail);
	}

	public Content resetInvoiceStatus(CrmInvoice crmInvoice) {
		CrmInvoice crmInvoice1 = crmInvoiceService.getById(crmInvoice.getInvoiceId());
		String detail = "Invoice" + crmInvoice1.getInvoiceApplyNumber() + "Reset invoicing status.";
		return new Content(crmInvoice1.getInvoiceApplyNumber(), detail);
	}

	public List<Content> deleteByIds(List<Integer> ids) {
		List<Content> contentList = new ArrayList<>();
		for (Integer id : ids) {
			CrmInvoice crmInvoice1 = crmInvoiceService.getById(id);
			contentList.add(sysLogUtil.addDeleteActionRecord(CrmEnum.INVOICE, crmInvoice1.getInvoiceApplyNumber()));
		}
		return contentList;
	}

	public List<Content> changeOwnerUser(CrmChangeOwnerUserBO crmChangeOwnerUserBO) {
		List<Content> contentList = new ArrayList<>();
		for (Integer id : crmChangeOwnerUserBO.getIds()) {
			CrmInvoice invoice = crmInvoiceService.getById(id);
			contentList.add(sysLogUtil.addConversionRecord(CrmEnum.INVOICE, crmChangeOwnerUserBO.getOwnerUserId(), invoice.getInvoiceApplyNumber()));
		}
		return contentList;
	}


}
