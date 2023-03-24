package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmCustomerData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmCustomerDataMapper;
import com.megazone.crm.service.ICrmCustomerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CrmCustomerDataServiceImpl extends BaseServiceImpl<CrmCustomerDataMapper, CrmCustomerData> implements ICrmCustomerDataService {

	@Autowired
	private FieldService fieldService;

	@Override
	public void setDataByBatchId(CrmModel crmModel) {
		List<CrmCustomerData> customerDataList = query().eq("batch_id", crmModel.getBatchId()).list();
		customerDataList.forEach(obj -> {
			crmModel.put(obj.getName(), obj.getValue());
		});
	}

	@Override
	public void saveData(List<CrmModelFiledVO> array, String batchId) {
		List<CrmCustomerData> customerDataList = new ArrayList<>();
		remove(new LambdaQueryWrapper<CrmCustomerData>().eq(CrmCustomerData::getBatchId, batchId));
		Date date = new Date();
		for (CrmModelFiledVO obj : array) {
			CrmCustomerData customerData = BeanUtil.copyProperties(obj, CrmCustomerData.class);
			customerData.setValue(fieldService.convertObjectValueToString(obj.getType(), obj.getValue(), customerData.getValue()));
			customerData.setName(obj.getFieldName());
			customerData.setCreateTime(date);
			customerData.setBatchId(batchId);
			customerDataList.add(customerData);
		}
		saveBatch(customerDataList, Const.BATCH_SAVE_SIZE);
	}

	@Override
	public void deleteByBatchId(List<String> batchId) {
		LambdaQueryWrapper<CrmCustomerData> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmCustomerData::getBatchId, batchId);
		remove(wrapper);
	}
}
