package com.megazone.core.utils;

import com.megazone.core.common.Const;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.feign.admin.entity.SimpleUser;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.redis.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author
 */
@Component
public class UserCacheUtil {
	static UserCacheUtil ME;
	@Autowired
	Redis redis;
	@Autowired
	private AdminService adminService;

	/**
	 * @param userIds userIds
	 * @return data
	 */
	public static <T> String getUserNameList(List<T> userIds) {
		List<String> stringList = new ArrayList<>();
		for (T obj : userIds) {
			String name;
			if (obj instanceof Long) {
				name = getUserName((Long) obj);
			} else if (obj instanceof String) {
				name = getUserName(Long.valueOf((String) obj));
			} else {
				name = "";
			}
			if (!"".equals(name)) {
				stringList.add(name);
			}
		}
		return stringList.size() > 0 ? String.join(Const.SEPARATOR, stringList) : "";
	}

	/**
	 * @param userId ID
	 * @return data
	 */
	public static UserInfo getUserInfo(Long userId) {
		return ME.adminService.getUserInfo(userId).getData();
	}

	/**
	 * @param userId ID
	 * @return data
	 */
	public static String getUserName(Long userId) {
		if (userId == null) {
			return "";
		}
		return getSimpleUser(userId).getRealname();
	}

	public static SimpleUser getSimpleUser(Long userId) {
		if (userId == null) {
			return new SimpleUser();
		}

		SimpleUser simpleUser = ME.adminService.queryUserById(userId).getData();
		if (simpleUser == null) {
			simpleUser = new SimpleUser();
		}

		return simpleUser;
	}

	public static List<SimpleUser> getSimpleUsers(Collection<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		List<SimpleUser> simpleUserList = new ArrayList<>(ids.size());
		for (Long userId : ids) {
			SimpleUser simpleUser = ME.adminService.queryUserById(userId).getData();
			if (simpleUser == null) {
				continue;
			}

			simpleUserList.add(simpleUser);
		}
		return simpleUserList;
	}

	/**
	 * @param deptIds deptIds
	 * @return data
	 */
	public static <T> String getDeptNameList(List<T> deptIds) {
		List<String> stringList = new ArrayList<>();
		for (T obj : deptIds) {
			String name;
			if (obj instanceof Integer) {
				name = getDeptName((Integer) obj);
			} else if (obj instanceof String) {
				name = getDeptName(Integer.valueOf((String) obj));
			} else {
				name = "";
			}
			if (!"".equals(name)) {
				stringList.add(name);
			}
		}
		return stringList.size() > 0 ? String.join(Const.SEPARATOR, stringList) : "";
	}

	/**
	 * @param deptId ID
	 * @return data
	 */
	public static String getDeptName(Integer deptId) {
		if (deptId == null) {
			return "";
		}
		String name = ME.adminService.queryDeptName(deptId).getData();
		return name;
	}

	/**
	 * @param userId ID 0
	 * @return data
	 */
	public static List<Long> queryChildUserId(Long userId) {
		return ME.adminService.queryChildUserId(userId).getData();
	}

	/**
	 * @param deptId ID
	 * @return data
	 */
	public static List<Integer> queryChildDeptId(Integer deptId) {
		return ME.adminService.queryChildDeptId(deptId).getData();
	}

	@PostConstruct
	public void init() {
		ME = this;
	}
}
