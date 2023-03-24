package com.megazone.crm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CrmBusinessType;
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
public interface CrmBusinessTypeMapper extends BaseMapper<CrmBusinessType> {

	List<CrmBusinessType> queryBusinessStatusOptions(@Param("deptId") Integer deptId, @Param("isAdmin") boolean isAdmin);
}
