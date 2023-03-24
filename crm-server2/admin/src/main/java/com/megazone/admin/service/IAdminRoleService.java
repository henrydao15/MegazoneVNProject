package com.megazone.admin.service;

import com.megazone.admin.common.AdminRoleTypeEnum;
import com.megazone.admin.entity.PO.AdminRole;
import com.megazone.admin.entity.VO.AdminRoleVO;
import com.megazone.core.common.JSONObject;
import com.megazone.core.servlet.BaseService;

import java.util.Collection;
import java.util.List;

public interface IAdminRoleService extends BaseService<AdminRole> {
	/**
	 * Query the permissions of the user
	 *
	 * @param userId current user ID
	 * @return obj
	 */
// @Cached(name = AdminConst.DEFAULT_AUTH_CACHE_NAME, key = "args[0]",timeUnit = TimeUnit.MINUTES,expire = 5)
	public JSONObject auth(Long userId);

	/**
	 * Query role list by user ID
	 *
	 * @param userId User ID
	 * @return data
	 */
	public List<AdminRole> queryRoleListByUserId(Long userId);

	/**
	 * Query role list by user ID
	 *
	 * @param userIds User IDs
	 * @return data
	 */
	public List<AdminRole> queryRoleListByUserId(List<Long> userIds);

	/**
	 * Query roles by type
	 *
	 * @param roleTypeEnum type
	 * @return data
	 */
	public List<AdminRole> getRoleByType(AdminRoleTypeEnum roleTypeEnum);

	/**
	 * Query all roles
	 *
	 * @return data
	 */
	public List<AdminRoleVO> getAllRoleList();

	/**
	 * Query the queryable roles when adding new employees
	 *
	 * @return list of roles
	 */
	public List<AdminRoleVO> getRoleList();

	/**
	 * Query data permission
	 *
	 * @param userId User ID
	 * @param menuId menu ID
	 * @return permission
	 */
	public Integer queryDataType(Long userId, Integer menuId);

	/**
	 * Query subordinate users
	 *
	 * @param userId User ID
	 * @param menuId menu ID
	 * @return permission
	 */
	public Collection<Long> queryUserByAuth(Long userId, Integer menuId);

	/**
	 * save character
	 *
	 * @param adminRole role
	 */
	public void add(AdminRole adminRole);

	/**
	 * delete role
	 *
	 * @param roleId roleId
	 */
	public void delete(Integer roleId);

	/**
	 * Duplicate character
	 *
	 * @param roleId roleId
	 */
	public void copy(Integer roleId);

	/**
	 * User associated role
	 *
	 * @param userIds user list
	 * @param roleIds list of roles
	 */
	public void relatedUser(List<Long> userIds, List<Integer> roleIds);

	void relatedDeptUser(List<Long> userIds, List<Integer> deptIds, List<Integer> roleIds);

	/**
	 * Cancel user association role
	 *
	 * @param userId User ID
	 * @param roleId role ID
	 */
	public void unbindingUser(Long userId, Integer roleId);

	/**
	 * Modify the role menu relationship
	 *
	 * @param adminRole adminrole
	 */
	public void updateRoleMenu(AdminRole adminRole);

	/**
	 * Query the role of project management
	 *
	 * @param label label
	 * @return roleId
	 */
	public Integer queryWorkRole(Integer label);


	/**
	 * Save project management roles
	 *
	 * @param object obj
	 */
	public void setWorkRole(JSONObject object);

	/**
	 * Remove project management role
	 *
	 * @param roleId roleId
	 */
	public void deleteWorkRole(Integer roleId);

	/**
	 * Query project management roles
	 *
	 * @return
	 */
	public List<AdminRole> queryProjectRoleList();

	List<AdminRole> queryRoleList();

	/**
	 * Get user unauthorized menu
	 */
	List<String> queryNoAuthMenu(Long userId);

	List<AdminRole> queryRoleByRoleTypeAndUserId(Integer type);

	/**
	 * Follow up role ID to query subordinate employees
	 *
	 * @param roleId role ID
	 * @return userIds
	 */
	public List<Long> queryUserIdByRoleId(Integer roleId);
}
