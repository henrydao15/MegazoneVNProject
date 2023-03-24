package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmReceivablesPlanData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmReceivablesPlanDataMapper;
import com.megazone.crm.service.ICrmReceivablesPlanDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CrmReceivablesPlanDataServiceImpl extends BaseServiceImpl<CrmReceivablesPlanDataMapper, CrmReceivablesPlanData> implements ICrmReceivablesPlanDataService {

	@Autowired
	private FieldService fieldService;

	@Override
	public void setDataByBatchId(CrmModel crmModel) {
		List<CrmReceivablesPlanData> leadsDataList = query().eq("batch_id", crmModel.getBatchId()).list();
		leadsDataList.forEach(obj -> {
			crmModel.put(obj.getName(), obj.getValue());
		});
	}

	@Override
	public void saveData(List<CrmModelFiledVO> array, String batchId) {
		List<CrmReceivablesPlanData> receivablesPlanDataList = new ArrayList<>();
		remove(new LambdaQueryWrapper<CrmReceivablesPlanData>().eq(CrmReceivablesPlanData::getBatchId, batchId));
		Date date = new Date();
		for (CrmModelFiledVO obj : array) {
			CrmReceivablesPlanData receivablesPlanData = BeanUtil.copyProperties(obj, CrmReceivablesPlanData.class);
			receivablesPlanData.setValue(fieldService.convertObjectValueToString(obj.getType(), obj.getValue(), receivablesPlanData.getValue()));
			receivablesPlanData.setName(obj.getFieldName());
			receivablesPlanData.setCreateTime(date);
			receivablesPlanData.setBatchId(batchId);
			receivablesPlanDataList.add(receivablesPlanData);
		}
		saveBatch(receivablesPlanDataList, Const.BATCH_SAVE_SIZE);
	}

	@Override
	public void deleteByBatchId(List<String> batchId) {
		LambdaQueryWrapper<CrmReceivablesPlanData> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmReceivablesPlanData::getBatchId, batchId);
		remove(wrapper);
	}

}
