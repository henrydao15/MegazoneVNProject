package com.megazone.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.constant.CrmBackLogEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmBackLogDeal;
import com.megazone.crm.mapper.CrmBackLogDealMapper;
import com.megazone.crm.service.ICrmBackLogDealService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrmBackLogDealServiceImpl extends BaseServiceImpl<CrmBackLogDealMapper, CrmBackLogDeal> implements ICrmBackLogDealService {

	/**
	 * @param userId         ID
	 * @param crmEnum
	 * @param crmBackLogEnum
	 * @param typeId         ID
	 */
	@Override
	public void deleteByType(Long userId, CrmEnum crmEnum, CrmBackLogEnum crmBackLogEnum, Integer typeId) {
		LambdaQueryWrapper<CrmBackLogDeal> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(CrmBackLogDeal::getCreateUserId, userId);
		wrapper.eq(CrmBackLogDeal::getCrmType, crmEnum.getType());
		wrapper.eq(CrmBackLogDeal::getModel, crmBackLogEnum.getType());
		wrapper.eq(CrmBackLogDeal::getTypeId, typeId);
		remove(wrapper);
	}

	/**
	 * @param userId         ID
	 * @param crmEnum
	 * @param typeId         ID
	 * @param crmBackLogEnum
	 */
	@Override
	public void deleteByTypes(Long userId, CrmEnum crmEnum, Integer typeId, CrmBackLogEnum... crmBackLogEnum) {
		List<CrmBackLogEnum> backLogEnums = Arrays.asList(crmBackLogEnum);
		LambdaQueryWrapper<CrmBackLogDeal> wrapper = new LambdaQueryWrapper<>();
		if (userId != null) {
			wrapper.eq(CrmBackLogDeal::getCreateUserId, userId);
		}
		wrapper.eq(CrmBackLogDeal::getCrmType, crmEnum.getType());
		List<Integer> types = backLogEnums.stream().map(CrmBackLogEnum::getType).collect(Collectors.toList());
		wrapper.in(CrmBackLogDeal::getModel, types);
		wrapper.eq(CrmBackLogDeal::getTypeId, typeId);
		remove(wrapper);
	}

	/**
	 * @param model   model
	 * @param crmType
	 * @param userId  ID
	 * @return data
	 */
	@Override
	public List<String> queryTypeId(Integer model, Integer crmType, Long userId) {
		List<CrmBackLogDeal> list = lambdaQuery().select(CrmBackLogDeal::getTypeId).eq(CrmBackLogDeal::getModel, model).eq(CrmBackLogDeal::getCrmType, crmType).eq(CrmBackLogDeal::getCreateUserId, userId).list();
		return list.stream().map(log -> log.getTypeId().toString()).collect(Collectors.toList());
	}
}
