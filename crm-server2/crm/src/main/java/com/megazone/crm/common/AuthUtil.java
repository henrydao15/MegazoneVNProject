package com.megazone.crm.common;

import cn.hutool.core.util.StrUtil;
import com.megazone.core.common.Const;
import com.megazone.core.common.cache.CrmCacheKey;
import com.megazone.core.feign.admin.service.AdminService;
import com.megazone.core.redis.Redis;
import com.megazone.core.servlet.ApplicationContextHolder;
import com.megazone.core.utils.BaseUtil;
import com.megazone.core.utils.UserUtil;
import com.megazone.crm.constant.CrmAuthEnum;
import com.megazone.crm.constant.CrmEnum;
import com.megazone.crm.entity.PO.CrmCustomer;
import com.megazone.crm.entity.PO.CrmCustomerPool;
import com.megazone.crm.mapper.CrmAuthMapper;
import com.megazone.crm.service.ICrmCustomerPoolService;
import com.megazone.crm.service.ICrmCustomerService;

import java.util.*;

/**
 * @author
 */
public class AuthUtil {

	public static boolean isCrmAuth(CrmEnum crmEnum, Integer id, CrmAuthEnum crmAuthEnum) {
		String conditions = crmEnum.getPrimaryKey(false) + " = " + id + getCrmAuthSql(crmEnum, 1, crmAuthEnum);
		Integer integer = ApplicationContextHolder.getBean(CrmAuthMapper.class).queryAuthNum("wk_crm_" + crmEnum.getTableName(), conditions);
		return integer == 0;
	}

	/**
	 *
	 */
	public static boolean isRwAuth(Integer id, CrmEnum crmEnum, CrmAuthEnum crmAuthEnum) {
		String conditions = crmEnum.getPrimaryKey(false) + " = " + id + getCrmAuthSql(crmEnum, 0, crmAuthEnum);
		Integer integer = ApplicationContextHolder.getBean(CrmAuthMapper.class).queryAuthNum("wk_crm_" + crmEnum.getTableName(), conditions);
		return integer == 0;
	}

	/**
	 *
	 */
	public static boolean isChangeOwnerUserAuth(Integer id, CrmEnum crmEnum, CrmAuthEnum crmAuthEnum) {
		String conditions = crmEnum.getPrimaryKey(false) + " = " + id + getCrmAuthSql(crmEnum, 3, crmAuthEnum);
		Integer integer = ApplicationContextHolder.getBean(CrmAuthMapper.class).queryAuthNum("wk_crm_" + crmEnum.getTableName(), conditions);
		return integer == 0;
	}

	/**
	 * @param customerId ID
	 * @return data
	 */
	public static boolean isPoolAuth(Integer customerId, CrmAuthEnum crmAuthEnum) {
		CrmCustomer customer = ApplicationContextHolder.getBean(ICrmCustomerService.class).getById(customerId);
		if (customer == null || customer.getOwnerUserId() == null) {
			return false;
		} else {
			return AuthUtil.isCrmAuth(CrmEnum.CUSTOMER, customerId, crmAuthEnum);
		}
	}

	/**
	 * @param userIds user
	 * @return data
	 */
	public static List<Long> filterUserId(List<Long> userIds) {
		if (UserUtil.isAdmin()) {
			return userIds;
		}
		Long userId = UserUtil.getUserId();
		List<Long> subUserIdList = ApplicationContextHolder.getBean(AdminService.class).queryChildUserId(userId).getData();
		subUserIdList.add(userId);
		subUserIdList.retainAll(userIds);
		return subUserIdList;
	}

	/**
	 * @param deptIds dept
	 * @return data
	 */
	public static List<Integer> filterDeptId(List<Integer> deptIds) {
		if (UserUtil.isAdmin()) {
			return deptIds;
		}
		Integer deptId = UserUtil.getUser().getDeptId();
		List<Integer> subDeptIdList = ApplicationContextHolder.getBean(AdminService.class).queryChildDeptId(deptId).getData();
		subDeptIdList.add(deptId);
		subDeptIdList.retainAll(deptIds);
		return subDeptIdList;
	}


	/**
	 * @param crmEnum
	 * @param readOnly 0  1  3
	 * @return sql
	 */
	public static String getCrmAuthSql(CrmEnum crmEnum, String alias, Integer readOnly, CrmAuthEnum crmAuthEnum) {
		if (UserUtil.isAdmin() || crmEnum.equals(CrmEnum.PRODUCT) || crmEnum.equals(CrmEnum.CUSTOMER_POOL)) {
			return "";
		}
		StringBuilder conditions = new StringBuilder();
		List<Long> longs = queryAuthUserList(crmEnum, crmAuthEnum);
		if (longs != null && longs.size() > 0) {
			if (crmEnum.equals(CrmEnum.MARKETING)) {
				conditions.append(" and (");
				longs.forEach(id -> conditions.append(" {alias}owner_user_id like CONCAT('%,','").append(id).append("',',%') or ").append("  {alias}relation_user_id like CONCAT('%,','").append(id).append("',',%') or"));
				conditions.delete(conditions.length() - 2, conditions.length());
			} else {
				conditions.append(" and ({alias}owner_user_id in (").append(StrUtil.join(",", longs)).append(")");
				boolean contains = Arrays.asList(CrmEnum.CUSTOMER, CrmEnum.CONTACTS, CrmEnum.BUSINESS, CrmEnum.RECEIVABLES, CrmEnum.CONTRACT).contains(crmEnum);
				if (contains && CrmAuthEnum.DELETE != crmAuthEnum && !Objects.equals(3, readOnly)) {
					conditions.append("or {alias}").append(crmEnum.getPrimaryKey(false));
					conditions.append(" in (");
					conditions.append("SELECT type_id FROM wk_crm_team_members where type = '")
							.append(crmEnum.getType())
							.append("' and user_id = '").append(UserUtil.getUserId()).append("'");
					if (Objects.equals(0, readOnly)) {
						conditions.append(" and power ='2'");
					}
					conditions.append(")");
				}
			}
			conditions.append(")");
		}
		Map<String, String> map = new HashMap<>();
		if (StrUtil.isEmpty(alias)) {
			map.put("alias", "");
		} else {
			map.put("alias", alias + ".");
		}
		return StrUtil.format(conditions.toString(), map);
	}

	public static String getCrmAuthSql(CrmEnum crmEnum, Integer readOnly, CrmAuthEnum crmAuthEnum) {
		return getCrmAuthSql(crmEnum, "", readOnly, crmAuthEnum);
	}

	/**
	 *
	 */
	public static List<Long> getUserIdByAuth(Integer menuId) {
		return ApplicationContextHolder.getBean(AdminService.class).queryUserByAuth(UserUtil.getUserId(), menuId).getData();
	}

	/**
	 *
	 */
	public static boolean isPoolAdmin(Integer poolId) {
		if (!UserUtil.isAdmin()) {
			CrmCustomerPool customerPool = ApplicationContextHolder.getBean(ICrmCustomerPoolService.class).getById(poolId);
			return customerPool == null || !StrUtil.splitTrim(customerPool.getAdminUserId(), Const.SEPARATOR).contains(UserUtil.getUserId().toString());
		}
		return false;
	}


	/**
	 * @param crmEnum     crm
	 * @param crmAuthEnum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Long> queryAuthUserList(CrmEnum crmEnum, CrmAuthEnum crmAuthEnum) {
		Long userId = UserUtil.getUserId();
		Redis redis = BaseUtil.getRedis();
		String key = CrmCacheKey.CRM_AUTH_USER_CACHE_KEY + userId.toString();
		Integer menuId = crmAuthEnum.getMenuId(crmEnum);
		Map<Object, Object> map = redis.getRedisMap(key);
		if (map != null && map.containsKey(menuId)) {
			return (List<Long>) map.get(menuId);
		}
		List<Long> userIds = ApplicationContextHolder.getBean(AdminService.class).queryUserByAuth(userId, menuId).getData();
		redis.put(key, menuId, userIds);

		redis.expire(key, 30 * 60);
		return userIds;
	}

	/**
	 * @param crmEnum     crm
	 * @param crmAuthEnum
	 * @return
	 */
	public static List<Long> filterUserIdList(CrmEnum crmEnum, CrmAuthEnum crmAuthEnum, List<Long> allUserIdList) {
		List<Long> authUserList = queryAuthUserList(crmEnum, crmAuthEnum);
		authUserList.retainAll(allUserIdList);
		return authUserList;
	}

	public static boolean isReadFollowRecord(Integer crmType) {
		int menuId;
		switch (crmType) {
			case 1:
				menuId = 20;
				break;
			case 2:
				menuId = 29;
				break;
			case 3:
				menuId = 43;
				break;
			case 5:
				menuId = 49;
				break;
			case 6:
				menuId = 56;
				break;
			default:
				return false;
		}
		int followRecordReadMenuId = 441;
		return ApplicationContextHolder.getBean(CrmAuthMapper.class).queryReadFollowRecord(menuId, followRecordReadMenuId, UserUtil.getUserId()) > 0;
	}
}
