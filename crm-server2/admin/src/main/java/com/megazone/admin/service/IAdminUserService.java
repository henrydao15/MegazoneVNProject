package com.megazone.admin.service;

import com.megazone.admin.entity.BO.*;
import com.megazone.admin.entity.PO.AdminUser;
import com.megazone.admin.entity.VO.AdminUserVO;
import com.megazone.admin.entity.VO.HrmSimpleUserVO;
import com.megazone.admin.entity.VO.UserBookVO;
import com.megazone.core.common.JSONObject;
import com.megazone.core.entity.BasePage;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.servlet.BaseService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * User table Service class
 * </p>
 *
 * @author
 * @since 2020-04-27
 */
public interface IAdminUserService extends BaseService<AdminUser> {

	/**
	 * Query user by username
	 *
	 * @param username
	 * @return
	 */
	public List<Map<String, Object>> findByUsername(String username);

	/**
	 * Query all users under the enterprise
	 *
	 * @param adminUserBO business object
	 * @return ids
	 */
	public BasePage<AdminUserVO> queryUserList(AdminUserBO adminUserBO);


	public JSONObject countUserByLabel();

	/**
	 * Query the subordinate users of the user
	 *
	 * @param userId User ID 0 means all
	 * @return data
	 */
	public List<Long> queryChildUserId(Long userId);

	/**
	 * Modify user
	 *
	 * @param adminUserVO data
	 */
	public void setUser(AdminUserVO adminUserVO);

	/**
	 * Batch modify user departments
	 */
	public void setUserDept(AdminUserBO adminUserBO);

	/**
	 * New users
	 *
	 * @param adminUserVO data
	 */
	public void addUser(AdminUserVO adminUserVO);

	/**
	 * Modify user information
	 *
	 * @param adminUser
	 */
	public void updateUser(AdminUser adminUser);

	/**
	 * Modify user account function
	 *
	 * @param id       User ID
	 * @param username new username
	 * @param password new password
	 * @return operation status
	 */
	public Integer usernameEdit(Integer id, String username, String password);


	/**
	 * excel import employee
	 *
	 * @param file file
	 */
	public JSONObject excelImport(MultipartFile file);

	/**
	 * set state
	 *
	 * @param adminUserStatusBO status
	 */
	public void setUserStatus(AdminUserStatusBO adminUserStatusBO);

	/**
	 * set state
	 *
	 * @param adminUserStatusBO status
	 */
	public void activateUser(AdminUserStatusBO adminUserStatusBO);

	/**
	 * reset Password
	 *
	 * @param adminUserStatusBO status
	 */
	public void resetPassword(AdminUserStatusBO adminUserStatusBO);

	/**
	 * Query role ID based on user ID
	 *
	 * @param userId userId
	 * @return ids
	 */
	public List<Integer> queryUserRoleIds(Long userId);

	/**
	 * Contacts query
	 *
	 * @param userBookBO data
	 */
	public BasePage<UserBookVO> queryListName(UserBookBO userBookBO);

	/**
	 * Toggle attention status
	 *
	 * @param userId User ID
	 * @return data
	 */
	public void attention(Long userId);

	/**
	 * Query user information based on ids
	 *
	 * @param ids list of ids
	 * @return data
	 */
	public List<SimpleUser> queryUserByIds(List<Long> ids);

	public List<Long> queryNormalUserByIds(List<Long> ids);

	/**
	 * Query user list based on department ids
	 *
	 * @param ids list of ids
	 * @return data
	 */
	public List<Long> queryUserByDeptIds(List<Integer> ids);

	void hrmAddUser(HrmAddUserBO hrmAddUserBO);

	DeptUserListVO queryDeptUserList(Integer deptId, boolean isAllUser);

	Set<HrmSimpleUserVO> queryDeptUserListByHrm(DeptUserListByHrmBO deptUserListByHrmBO);

	List<Long> queryUserIdByRealName(List<String> realNames);

	UserInfo queryLoginUserInfo(Long userId);


	/**
	 * Query whether the current system is initialized
	 *
	 * @return data
	 */
	public Integer querySystemStatus();

	/**
	 * System user initialization
	 */
	public void initUser(SystemUserBO systemUserBO);


	/**
	 * Query all employees
	 *
	 * @return
	 */
	public List<UserInfo> queryAllUserInfoList();

	/**
	 * Download employee import template
	 */
	public void downloadExcel(HttpServletResponse response) throws IOException;
}
