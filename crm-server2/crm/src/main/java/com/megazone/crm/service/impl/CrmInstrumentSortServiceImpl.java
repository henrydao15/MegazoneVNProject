package com.megazone.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.entity.PO.CrmInstrumentSort;
import com.megazone.crm.mapper.CrmInstrumentSortMapper;
import com.megazone.crm.service.ICrmInstrumentSortService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CrmInstrumentSortServiceImpl extends BaseServiceImpl<CrmInstrumentSortMapper, CrmInstrumentSort> implements ICrmInstrumentSortService {

	@Override
	public JSONObject queryModelSort() {
		List<CrmInstrumentSort> list = lambdaQuery().select(CrmInstrumentSort::getModelId, CrmInstrumentSort::getIsHidden, CrmInstrumentSort::getList)
				.eq(CrmInstrumentSort::getUserId, UserUtil.getUserId()).orderByAsc(CrmInstrumentSort::getSort).list();
		if (list.size() == 0) {
			list.add(new CrmInstrumentSort(1, 0, 1));
			list.add(new CrmInstrumentSort(5, 0, 1));
			list.add(new CrmInstrumentSort(7, 0, 1));
			list.add(new CrmInstrumentSort(2, 0, 2));
			list.add(new CrmInstrumentSort(4, 0, 2));
			list.add(new CrmInstrumentSort(6, 0, 2));
		}
		Map<Integer, List<CrmInstrumentSort>> collect = list.stream().collect(Collectors.groupingBy(CrmInstrumentSort::getList));
		return new JSONObject().fluentPut("left", collect.get(1)).fluentPut("right", collect.get(2));
	}

	@Override
	public void setModelSort(JSONObject object) {
		List<CrmInstrumentSort> leftList = object.getJSONArray("left").toJavaList(CrmInstrumentSort.class);
		LambdaQueryWrapper<CrmInstrumentSort> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CrmInstrumentSort::getUserId, UserUtil.getUserId());
		remove(wrapper);
		List<CrmInstrumentSort> sortList = new ArrayList<>();
		for (int i = 0; i < leftList.size(); i++) {
			CrmInstrumentSort instrumentSort = leftList.get(i);
			instrumentSort.setList(1);
			instrumentSort.setUserId(UserUtil.getUserId());
			instrumentSort.setSort(i);
			sortList.add(instrumentSort);
		}
		List<CrmInstrumentSort> rightList = object.getJSONArray("right").toJavaList(CrmInstrumentSort.class);
		for (int i = 0; i < rightList.size(); i++) {
			CrmInstrumentSort crmInstrumentSort = rightList.get(i);
			crmInstrumentSort.setList(2);
			crmInstrumentSort.setUserId(UserUtil.getUserId());
			crmInstrumentSort.setSort(i);
			sortList.add(crmInstrumentSort);
		}
		saveBatch(sortList, Const.BATCH_SAVE_SIZE);
	}
}
