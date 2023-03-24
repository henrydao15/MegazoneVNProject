package com.megazone.admin.mapper;

import com.megazone.admin.entity.BO.AdminUserBO;
import com.megazone.admin.entity.BO.UserBookBO;
import com.megazone.admin.entity.PO.AdminUser;
import com.megazone.admin.entity.VO.AdminUserVO;
import com.megazone.admin.entity.VO.HrmSimpleUserVO;
import com.megazone.admin.entity.VO.UserBookVO;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.servlet.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminUserMapper extends BaseMapper<AdminUser> {
	List<Map<String, Object>> findByUsername(@Param("username") String username);

	BasePage<AdminUserVO> queryUserList(BasePage<AdminUserVO> page, @Param("data") AdminUserBO adminUserBO);

	Integer countUserByLabel(@Param("label") Integer label, @Param("status") Integer status);

	BasePage<UserBookVO> queryListName(BasePage<UserBookVO> page, @Param("data") UserBookBO userBookBO);

	List<HrmSimpleUserVO> querySimpleUserByDeptId(Integer deptId);

	List<HrmSimpleUserVO> querySimpleUserByDeptIdAndExamine(Integer deptId);

	UserInfo queryLoginUserInfo(@Param("userId") Long userId);
}
