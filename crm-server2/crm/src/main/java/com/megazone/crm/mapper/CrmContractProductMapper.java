package com.megazone.crm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CrmContractProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-27
 */
public interface CrmContractProductMapper extends BaseMapper<CrmContractProduct> {
	/**
	 * @param contractId ID
	 */
	public List<CrmContractProduct> queryByContractId(@Param("contractId") Integer contractId);

	/**
	 * @param contractId ID
	 * @return data
	 */
	public List<CrmContractProduct> queryList(@Param("contractId") Integer contractId);
}
