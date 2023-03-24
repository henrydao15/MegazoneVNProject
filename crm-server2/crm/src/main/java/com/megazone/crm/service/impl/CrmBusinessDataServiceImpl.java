package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmBusinessData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmBusinessDataMapper;
import com.megazone.crm.service.ICrmBusinessDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CrmBusinessDataServiceImpl extends BaseServiceImpl<CrmBusinessDataMapper, CrmBusinessData> implements ICrmBusinessDataService {

	@Autowired
	private FieldService fieldService;

	/**
	 * @param crmModel crmModel
	 */
	@Override
	public void setDataByBatchId(CrmModel crmModel) {
		List<CrmBusinessData> businessDataList = query().eq("batch_id", crmModel.getBatchId()).list();
		businessDataList.forEach(obj -> {
			crmModel.put(obj.getName(), obj.getValue());
		});
	}

	/**
	 * @param array data
	 */
	@Override
	public void saveData(List<CrmModelFiledVO> array, String batchId) {
		List<CrmBusinessData> businessDataList = new ArrayList<>();
		remove(new LambdaQueryWrapper<CrmBusinessData>().eq(CrmBusinessData::getBatchId, batchId));
		Date date = new Date();
		for (CrmModelFiledVO obj : array) {
			CrmBusinessData businessData = BeanUtil.copyProperties(obj, CrmBusinessData.class);
			businessData.setValue(fieldService.convertObjectValueToString(obj.getType(), obj.getValue(), businessData.getValue()));
			businessData.setName(obj.getFieldName());
			businessData.setCreateTime(date);
			businessData.setBatchId(batchId);
			businessDataList.add(businessData);
		}
		saveBatch(businessDataList, Const.BATCH_SAVE_SIZE);
	}

	/**
	 * @param batchId data
	 */
	@Override
	public void deleteByBatchId(List<String> batchId) {
		LambdaQueryWrapper<CrmBusinessData> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmBusinessData::getBatchId, batchId);
		remove(wrapper);
	}
}
