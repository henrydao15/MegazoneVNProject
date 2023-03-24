package com.megazone.admin.mapper;

import com.megazone.admin.entity.PO.AdminMenu;
import com.megazone.core.servlet.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminMenuMapper extends BaseMapper<AdminMenu> {

	List<AdminMenu> queryMenuList(Long userId);

	Map<String, Long> queryPoolReadAuth(@Param("userId") Long userId, @Param("deptId") Integer deptId);

	Integer queryMenuId(@Param("realm1") String realm1, @Param("realm2") String realm2, @Param("realm3") String realm3);
}
