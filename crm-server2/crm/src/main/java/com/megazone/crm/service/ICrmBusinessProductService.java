package com.megazone.crm.service;

import com.megazone.core.servlet.BaseService;
import com.megazone.crm.entity.PO.CrmBusinessProduct;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
public interface ICrmBusinessProductService extends BaseService<CrmBusinessProduct> {
	/**
	 * @param ids ids
	 */
	public void deleteByBusinessId(Integer... ids);

	/**
	 * @param crmBusinessProductList data
	 */
	public void save(List<CrmBusinessProduct> crmBusinessProductList);

	/**
	 * @param ids ids
	 */
	public void deleteByProductId(Integer... ids);

	/**
	 * @param businessId ID
	 * @return data
	 */
	public List<CrmBusinessProduct> queryList(Integer businessId);
}
