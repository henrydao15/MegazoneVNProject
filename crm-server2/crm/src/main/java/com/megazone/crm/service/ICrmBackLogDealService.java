package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.constant.CrmBackLogEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmBackLogDeal;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-23
 */
public interface ICrmBackLogDealService extends BaseService<CrmBackLogDeal> {
	/**
	 * @param userId         ID
	 * @param crmEnum
	 * @param crmBackLogEnum
	 * @param typeId         ID
	 */
	public void deleteByType(Long userId, CrmEnum crmEnum, CrmBackLogEnum crmBackLogEnum, Integer typeId);

	/**
	 * @param userId         ID
	 * @param crmEnum
	 * @param crmBackLogEnum
	 * @param typeId         ID
	 */
	public void deleteByTypes(Long userId, CrmEnum crmEnum, Integer typeId, CrmBackLogEnum... crmBackLogEnum);

	/**
	 * @param model   model
	 * @param crmType
	 * @param userId  ID
	 * @return data
	 */
	public List<String> queryTypeId(Integer model, Integer crmType, Long userId);
}
