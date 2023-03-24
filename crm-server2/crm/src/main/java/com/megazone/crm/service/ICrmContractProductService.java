package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmContractProduct;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
public interface ICrmContractProductService extends BaseService<CrmContractProduct> {
	/**
	 * @param contractId ID
	 */
	public void deleteByContractId(Integer... contractId);

	/**
	 * @param contractId ID
	 */
	public List<CrmContractProduct> queryByContractId(Integer contractId);

	/**
	 * @param contractId ID
	 * @return data
	 */
	public List<CrmContractProduct> queryList(Integer contractId);
}
