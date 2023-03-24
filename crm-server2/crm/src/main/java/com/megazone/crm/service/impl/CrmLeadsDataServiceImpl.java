package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmLeadsData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmLeadsDataMapper;
import com.megazone.crm.service.ICrmLeadsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CrmLeadsDataServiceImpl extends BaseServiceImpl<CrmLeadsDataMapper, CrmLeadsData> implements ICrmLeadsDataService {

	@Autowired
	private FieldService fieldService;

	@Override
	public void setDataByBatchId(CrmModel crmModel) {
		List<CrmLeadsData> leadsDataList = query().eq("batch_id", crmModel.getBatchId()).list();
		leadsDataList.forEach(obj -> {
			crmModel.put(obj.getName(), obj.getValue());
		});
	}

	@Override
	public void saveData(List<CrmModelFiledVO> array, String batchId) {
		List<CrmLeadsData> leadsDataList = new ArrayList<>();
		remove(new LambdaQueryWrapper<CrmLeadsData>().eq(CrmLeadsData::getBatchId, batchId));
		Date date = new Date();
		for (CrmModelFiledVO obj : array) {
			CrmLeadsData leadsData = BeanUtil.copyProperties(obj, CrmLeadsData.class);
			leadsData.setValue(fieldService.convertObjectValueToString(obj.getType(), obj.getValue(), leadsData.getValue()));
			leadsData.setName(obj.getFieldName());
			leadsData.setCreateTime(date);
			leadsData.setBatchId(batchId);
			leadsDataList.add(leadsData);
		}
		saveBatch(leadsDataList, Const.BATCH_SAVE_SIZE);
	}

	@Override
	public void deleteByBatchId(List<String> batchId) {
		LambdaQueryWrapper<CrmLeadsData> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmLeadsData::getBatchId, batchId);
		remove(wrapper);
	}

}
