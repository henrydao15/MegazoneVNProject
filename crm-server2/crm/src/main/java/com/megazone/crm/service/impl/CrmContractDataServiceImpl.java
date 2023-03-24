package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmContractData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmContractDataMapper;
import com.megazone.crm.service.ICrmContractDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
@Service
public class CrmContractDataServiceImpl extends BaseServiceImpl<CrmContractDataMapper, CrmContractData> implements ICrmContractDataService {

	@Autowired
	private FieldService fieldService;

	/**
	 * @param crmModel crmModel
	 */
	@Override
	public void setDataByBatchId(CrmModel crmModel) {
		List<CrmContractData> contractDataList = query().eq("batch_id", crmModel.getBatchId()).list();
		contractDataList.forEach(obj -> {
			crmModel.put(obj.getName(), obj.getValue());
		});
	}

	/**
	 * @param array data
	 */
	@Override
	public void saveData(List<CrmModelFiledVO> array, String batchId) {
		List<CrmContractData> contractDataList = new ArrayList<>();
		remove(new LambdaQueryWrapper<CrmContractData>().eq(CrmContractData::getBatchId, batchId));
		Date date = new Date();
		for (CrmModelFiledVO obj : array) {
			CrmContractData crmContractData = BeanUtil.copyProperties(obj, CrmContractData.class);
			crmContractData.setValue(fieldService.convertObjectValueToString(obj.getType(), obj.getValue(), crmContractData.getValue()));
			crmContractData.setName(obj.getFieldName());
			crmContractData.setCreateTime(date);
			crmContractData.setBatchId(batchId);
			contractDataList.add(crmContractData);
		}
		saveBatch(contractDataList, Const.BATCH_SAVE_SIZE);
	}

	/**
	 * @param batchId data
	 */
	@Override
	public void deleteByBatchId(List<String> batchId) {
		LambdaQueryWrapper<CrmContractData> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmContractData::getBatchId, batchId);
		remove(wrapper);
	}
}
