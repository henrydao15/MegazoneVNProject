package com.megazone.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.megazone.admin.entity.PO.AdminUserConfig;
import com.megazone.admin.mapper.AdminUserConfigMapper;
import com.megazone.admin.service.IAdminUserConfigService;
import com.megazone.core.common.Const;
import com.megazone.core.servlet.BaseServiceImpl;
import com.megazone.core.utils.UserUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * User configuration table Service implementation class
 * </p>
 *
 * @author
 * @since 2020-04-27
 */
@Service
public class AdminUserConfigServiceImpl extends BaseServiceImpl<AdminUserConfigMapper, AdminUserConfig> implements IAdminUserConfigService {

	/**
	 * Query user configuration information by name
	 *
	 * @param name
	 * @return data
	 */
	@Override
	public AdminUserConfig queryUserConfigByName(String name) {
		QueryWrapper<AdminUserConfig> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", name).eq("user_id", UserUtil.getUserId()).last("limit 1");
		return getOne(queryWrapper);
	}

	/**
	 * Query user configuration information list by name
	 * userId gets the current login person
	 *
	 * @param name name
	 * @return data
	 */
	@Override
	public List<AdminUserConfig> queryUserConfigListByName(String name) {
		QueryWrapper<AdminUserConfig> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", name).eq("user_id", UserUtil.getUserId());
		return list(queryWrapper);
	}

	/**
	 * Delete user configuration information by name
	 * userId gets the current login person
	 *
	 * @param name name
	 */
	@Override
	public void deleteUserConfigByName(String name) {
		QueryWrapper<AdminUserConfig> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", name).eq("user_id", UserUtil.getUserId());
		remove(queryWrapper);
	}

	/**
	 * Initialize user configuration when user registers
	 *
	 * @param userId new user ID
	 */
	@Override
	public void initUserConfig(Long userId) {
		//Save user follow-up records common phrases
		List<AdminUserConfig> adminUserConfigList = new ArrayList<>();
		adminUserConfigList.add(new AdminUserConfig(null, userId, 1, "ActivityPhrase", "Phone unanswered", "Follow-up record common phrases"));
		adminUserConfigList.add(new AdminUserConfig(null, userId, 1, "ActivityPhrase", "Customer Unintentional", "Follow-up record common phrases"));
		adminUserConfigList.add(new AdminUserConfig(null, userId, 1, "ActivityPhrase", "Customer intention is moderate, follow up", "Follow up record common phrases"));
		adminUserConfigList.add(new AdminUserConfig(null, userId, 1, "ActivityPhrase", "Customer intention is strong, and the probability of closing is high", "Follow-up record common phrases"));
		saveBatch(adminUserConfigList, Const.BATCH_SAVE_SIZE);
	}

	/**
	 * Configure users based on name and content query
	 */
	@Override
	public List<AdminUserConfig> queryUserConfigByNameAndValue(String name, String value) {
		QueryWrapper<AdminUserConfig> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", name).eq("value", value);
		return list(queryWrapper);
	}

}
