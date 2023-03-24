package com.megazone.crm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.common.CrmModel;
import com.megazone.crm.entity.PO.CrmInvoice;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper
 * </p>
 *
 * @author
 * @since 2020-07-06
 */
public interface CrmInvoiceMapper extends BaseMapper<CrmInvoice> {

	CrmInvoice queryById(@Param("id") Integer id);

	CrmModel queryByIds(@Param("id") Integer id, @Param("userId") Long userId);
}
