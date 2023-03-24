package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmProductData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmProductDataMapper;
import com.megazone.crm.service.ICrmProductDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CrmProductDataServiceImpl extends BaseServiceImpl<CrmProductDataMapper, CrmProductData> implements ICrmProductDataService {

	@Autowired
	private FieldService fieldService;

	@Override
	public void setDataByBatchId(CrmModel crmModel) {
		List<CrmProductData> productDataList = query().eq("batch_id", crmModel.getBatchId()).list();
		productDataList.forEach(obj -> {
			crmModel.put(obj.getName(), obj.getValue());
		});
	}

	@Override
	public void saveData(List<CrmModelFiledVO> array, String batchId) {
		List<CrmProductData> productDataList = new ArrayList<>();
		remove(new LambdaQueryWrapper<CrmProductData>().eq(CrmProductData::getBatchId, batchId));
		Date date = new Date();
		for (CrmModelFiledVO obj : array) {
			CrmProductData productData = BeanUtil.copyProperties(obj, CrmProductData.class);
			productData.setValue(fieldService.convertObjectValueToString(obj.getType(), obj.getValue(), productData.getValue()));
			productData.setName(obj.getFieldName());
			productData.setCreateTime(date);
			productData.setBatchId(batchId);
			productDataList.add(productData);
		}
		saveBatch(productDataList, Const.BATCH_SAVE_SIZE);
	}

	@Override
	public void deleteByBatchId(List<String> batchId) {
		lambdaUpdate().in(CrmProductData::getBatchId, batchId).remove();
	}

}
