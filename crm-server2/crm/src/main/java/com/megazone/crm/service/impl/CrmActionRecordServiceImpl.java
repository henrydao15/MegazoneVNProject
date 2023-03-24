package com.megazone.crm.service.impl;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.JSON;
import com.megazone.core.feign.admin.entity.AdminConfig;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmActionRecord;
import com.megazone.crm.entity.VO.CrmActionRecordVO;
import com.megazone.crm.entity.VO.CrmModelFiledVO;
import com.megazone.crm.mapper.CrmActionRecordMapper;
import com.megazone.crm.service.ICrmActionRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrmActionRecordServiceImpl extends BaseServiceImpl<CrmActionRecordMapper, CrmActionRecord> implements ICrmActionRecordService {

	@Override
	public void deleteActionRecord(CrmEnum crmEnum, List<Integer> ids) {
		LambdaQueryWrapper<CrmActionRecord> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CrmActionRecord::getTypes, crmEnum.getType());
		wrapper.in(CrmActionRecord::getActionId, ids);
		remove(wrapper);
	}

	@Override
	public List<String> queryRecordOptions() {
		AdminService bean = ApplicationContextHolder.getBean(AdminService.class);
		List<AdminConfig> option = bean.queryConfigByName("followRecordOption").getData();
		return option.stream().map(AdminConfig::getValue).collect(Collectors.toList());
	}

	@Override
	public List<CrmActionRecordVO> queryRecordList(Integer actionId, Integer crmTypes) {
		List<CrmActionRecordVO> recordList = getBaseMapper().queryRecordList(actionId, crmTypes);
		recordList.forEach(record -> {
			try {
				List<String> list = JSON.parseArray((String) record.getContent(), String.class);
				record.setContent(list);
			} catch (Exception e) {
				List<String> list = new ArrayList<>();
				list.add((String) record.getContent());
				record.setContent(list);
			}
		});
		return recordList;
	}

	@Override
	public List<CrmModelFiledVO> queryFieldValue(Dict kv) {
		return getBaseMapper().queryFieldValue(kv);
	}
}
