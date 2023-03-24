package com.megazone.admin.mapper;

import com.megazone.admin.entity.PO.AdminRole;
import com.megazone.core.servlet.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminRoleMapper extends BaseMapper<AdminRole> {

	List<Integer> getRoleMenu(@Param("parentId") Integer parentId, @Param("roleId") Integer roleId);

	Integer queryDataType(@Param("userId") Long userId, @Param("menuId") Integer menuId);

	void deleteWorkRole(@Param("roleId") Integer roleId, @Param("editRoleId") Integer editRoleId);

	List<AdminRole> queryRoleByRoleTypeAndUserId(@Param("type") Integer type, @Param("userId") Long userId);

}
