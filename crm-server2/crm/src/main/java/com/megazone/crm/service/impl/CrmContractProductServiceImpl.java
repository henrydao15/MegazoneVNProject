package com.megazone.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.crm.entity.PO.CrmContractProduct;
import com.megazone.crm.mapper.CrmContractProductMapper;
import com.megazone.crm.service.ICrmContractProductService;
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
public class CrmContractProductServiceImpl extends BaseServiceImpl<CrmContractProductMapper, CrmContractProduct> implements ICrmContractProductService {

	/**
	 * @param contractId ID
	 */
	@Override
	public void deleteByContractId(Integer... contractId) {
		LambdaQueryWrapper<CrmContractProduct> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(CrmContractProduct::getContractId, Arrays.asList(contractId));
		remove(wrapper);
	}

	/**
	 * @param contractId ID
	 */
	@Override
	public List<CrmContractProduct> queryByContractId(Integer contractId) {
		return getBaseMapper().queryByContractId(contractId);
	}

	/**
	 * @param contractId ID
	 * @return data
	 */
	@Override
	public List<CrmContractProduct> queryList(Integer contractId) {
		if (contractId == null) {
			return new ArrayList<>();
		}
		return getBaseMapper().queryList(contractId);
	}
}
