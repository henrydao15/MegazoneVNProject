package com.megazone.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.common.Const;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.entity.PO.CrmBusinessProduct;
import com.megazone.crm.mapper.CrmBusinessProductMapper;
import com.megazone.crm.service.ICrmBusinessProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
public class CrmBusinessProductServiceImpl extends BaseServiceImpl<CrmBusinessProductMapper, CrmBusinessProduct> implements ICrmBusinessProductService {

	/**
	 * @param ids ids
	 */
	@Override
	public void deleteByBusinessId(Integer... ids) {
		LambdaQueryWrapper<CrmBusinessProduct> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmBusinessProduct::getBusinessId, Arrays.asList(ids));
		remove(wrapper);
	}

	/**
	 * @param crmBusinessProductList data
	 */
	@Override
	public void save(List<CrmBusinessProduct> crmBusinessProductList) {
		saveBatch(crmBusinessProductList, Const.BATCH_SAVE_SIZE);
	}

	/**
	 * @param ids ids
	 */
	@Override
	public void deleteByProductId(Integer... ids) {
		LambdaQueryWrapper<CrmBusinessProduct> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmBusinessProduct::getProductId, Arrays.asList(ids));
		remove(wrapper);
	}

	/**
	 * @param businessId ID
	 * @return data
	 */
	@Override
	public List<CrmBusinessProduct> queryList(Integer businessId) {
		if (businessId == null) {
			return new ArrayList<>();
		}
		return getBaseMapper().queryList(businessId);
	}
}
