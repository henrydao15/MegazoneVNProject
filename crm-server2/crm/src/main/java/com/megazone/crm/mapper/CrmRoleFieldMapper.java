package com.megazone.crm.mapper;

import com.megazone.core.servlet.BaseMapper;
import com.megazone.crm.entity.PO.CrmRoleField;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-05-22
 */
public interface CrmRoleFieldMapper extends BaseMapper<CrmRoleField> {

	/**
	 * @return data
	 */
	public List<CrmRoleField> queryRoleFieldList(@Param("roleId") Integer roleId, @Param("label") Integer label);

	public List<CrmRoleField> queryMaskFieldList(@Param("maskType") Integer maskType, @Param("label") Integer label);
}
