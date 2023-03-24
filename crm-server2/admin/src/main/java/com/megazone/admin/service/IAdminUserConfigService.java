package com.megazone.admin.service;

import com.megazone.admin.entity.PO.AdminUserConfig;
import com.megazone.core.servlet.BaseService;

import java.util.List;

/**
 * <p>
 * User configuration table Service class
 * </p>
 *
 * @author
 * @since 2020-04-27
 */
public interface IAdminUserConfigService extends BaseService<AdminUserConfig> {

	/**
	 * Query user configuration information by name
	 * userId gets the current login person
	 *
	 * @param name name
	 * @return data
	 */
	public AdminUserConfig queryUserConfigByName(String name);

	/**
	 * Query user configuration information list by name
	 * userId gets the current login person
	 *
	 * @param name name
	 * @return data
	 */
	public List<AdminUserConfig> queryUserConfigListByName(String name);

	/**
	 * Delete user configuration information by name
	 * userId gets the current login person
	 *
	 * @param name name
	 */
	public void deleteUserConfigByName(String name);


	/**
	 * Initialize user configuration when user registers
	 *
	 * @param userId new user ID
	 */
	public void initUserConfig(Long userId);

	/**
	 * Configure users based on name and content query
	 *
	 * @param name  name
	 * @param value content
	 * @return data
	 */
	public List<AdminUserConfig> queryUserConfigByNameAndValue(String name, String value);

}
