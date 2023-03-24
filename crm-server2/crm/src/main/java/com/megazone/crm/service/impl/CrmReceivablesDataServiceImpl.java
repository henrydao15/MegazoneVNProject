package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmReceivablesData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmReceivablesDataMapper;
import com.megazone.crm.service.ICrmReceivablesDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CrmReceivablesDataServiceImpl extends BaseServiceImpl<CrmReceivablesDataMapper, CrmReceivablesData> implements ICrmReceivablesDataService {

	@Autowired
	private FieldService fieldService;

	@Override
	public void setDataByBatchId(CrmModel crmModel) {
		List<CrmReceivablesData> receivablesDataList = query().eq("batch_id", crmModel.getBatchId()).list();
		receivablesDataList.forEach(obj -> {
			crmModel.put(obj.getName(), obj.getValue());
		});
	}

	@Override
	public void saveData(List<CrmModelFiledVO> array, String batchId) {
		List<CrmReceivablesData> receivablesDataList = new ArrayList<>();
		remove(new LambdaQueryWrapper<CrmReceivablesData>().eq(CrmReceivablesData::getBatchId, batchId));
		Date date = new Date();
		for (CrmModelFiledVO obj : array) {
			CrmReceivablesData receivablesData = BeanUtil.copyProperties(obj, CrmReceivablesData.class);
			receivablesData.setValue(fieldService.convertObjectValueToString(obj.getType(), obj.getValue(), receivablesData.getValue()));
			receivablesData.setName(obj.getFieldName());
			receivablesData.setCreateTime(date);
			receivablesData.setBatchId(batchId);
			receivablesDataList.add(receivablesData);
		}
		saveBatch(receivablesDataList, Const.BATCH_SAVE_SIZE);
	}

	@Override
	public void deleteByBatchId(List<String> batchId) {
		LambdaQueryWrapper<CrmReceivablesData> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmReceivablesData::getBatchId, batchId);
		remove(wrapper);
	}
}
