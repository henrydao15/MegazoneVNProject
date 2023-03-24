package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmInvoiceData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmInvoiceDataMapper;
import com.megazone.crm.service.ICrmInvoiceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CrmInvoiceDataServiceImpl extends BaseServiceImpl<CrmInvoiceDataMapper, CrmInvoiceData> implements ICrmInvoiceDataService {

	@Autowired
	private FieldService fieldService;

	@Override
	public void setDataByBatchId(CrmModel crmModel) {
		List<CrmInvoiceData> crmInvoiceDataList = query().eq("batch_id", crmModel.getBatchId()).list();
		crmInvoiceDataList.forEach(obj -> {
			crmModel.put(obj.getName(), obj.getValue());
		});
	}

	@Override
	public void saveData(List<CrmModelFiledVO> array, String batchId) {
		List<CrmInvoiceData> invoiceDataList = new ArrayList<>();
		remove(new LambdaQueryWrapper<CrmInvoiceData>().eq(CrmInvoiceData::getBatchId, batchId));
		Date date = new Date();
		for (CrmModelFiledVO obj : array) {
			CrmInvoiceData crmInvoiceData = BeanUtil.copyProperties(obj, CrmInvoiceData.class);
			crmInvoiceData.setValue(fieldService.convertObjectValueToString(obj.getType(), obj.getValue(), crmInvoiceData.getValue()));
			crmInvoiceData.setName(obj.getFieldName());
			crmInvoiceData.setCreateTime(date);
			crmInvoiceData.setBatchId(batchId);
			invoiceDataList.add(crmInvoiceData);
		}
		saveBatch(invoiceDataList, Const.BATCH_SAVE_SIZE);
	}

	@Override
	public void deleteByBatchId(List<String> batchId) {
		LambdaQueryWrapper<CrmInvoiceData> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmInvoiceData::getBatchId, batchId);
		remove(wrapper);
	}


}
