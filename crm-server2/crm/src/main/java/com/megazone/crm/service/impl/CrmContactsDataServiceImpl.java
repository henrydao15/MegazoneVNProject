package com.megazone.crm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.field.FieldService;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmContactsData;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmContactsDataMapper;
import com.megazone.crm.service.ICrmContactsDataService;
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
 * @since 2020-05-26
 */
@Service
public class CrmContactsDataServiceImpl extends BaseServiceImpl<CrmContactsDataMapper, CrmContactsData> implements ICrmContactsDataService {

	@Autowired
	private FieldService fieldService;

	/**
	 * @param crmModel crmModel
	 */
	@Override
	public void setDataByBatchId(CrmModel crmModel) {
		List<CrmContactsData> contactsDataList = query().eq("batch_id", crmModel.getBatchId()).list();
		contactsDataList.forEach(obj -> {
			crmModel.put(obj.getName(), obj.getValue());
		});
	}

	/**
	 * @param array data
	 */
	@Override
	public void saveData(List<CrmModelFiledVO> array, String batchId) {
		List<CrmContactsData> contactsDataList = new ArrayList<>();
		remove(new LambdaQueryWrapper<CrmContactsData>().eq(CrmContactsData::getBatchId, batchId));
		Date date = new Date();
		for (CrmModelFiledVO obj : array) {
			CrmContactsData contactsData = BeanUtil.copyProperties(obj, CrmContactsData.class);
			contactsData.setValue(fieldService.convertObjectValueToString(obj.getType(), obj.getValue(), contactsData.getValue()));
			contactsData.setName(obj.getFieldName());
			contactsData.setCreateTime(date);
			contactsData.setBatchId(batchId);
			contactsDataList.add(contactsData);
		}
		saveBatch(contactsDataList, Const.BATCH_SAVE_SIZE);
	}

	/**
	 * @param batchId data
	 */
	@Override
	public void deleteByBatchId(List<String> batchId) {
		LambdaQueryWrapper<CrmContactsData> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmContactsData::getBatchId, batchId);
		remove(wrapper);
	}
}
