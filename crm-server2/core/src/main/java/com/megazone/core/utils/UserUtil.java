package com.megazone.core.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.megazone.core.common.Const;
import com.megazone.core.entity.UserExtraInfo;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.redis.Redis;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @author z
 */
@Slf4j
public class UserUtil {

	private static ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();


	public static UserInfo getUser() {
		return threadLocal.get();
	}

	public static void setUser(UserInfo adminUser) {
		threadLocal.set(adminUser);
	}

	public static Long getUserId() {
		UserInfo user = getUser();
		return Optional.ofNullable(user).orElse(new UserInfo()).getUserId();
	}

	public static UserInfo setUser(Long userId) {
		UserInfo userInfo = UserCacheUtil.getUserInfo(userId);
		setUser(userInfo);
		return userInfo;
	}


	public static void removeUser() {
		threadLocal.remove();
	}

	/**
	 * @param key  key
	 * @param salt
	 * @param sign
	 * @return true
	 */
	public static boolean verify(String key, String salt, String sign) {
		return sign.equals(sign(key, salt));
	}

	/**
	 * @param key  key
	 * @param salt
	 * @return
	 */
	public static String sign(String key, String salt) {
		return SecureUtil.md5(key.concat("erp").concat(salt));
	}

	public static void userExpire(String token) {
		Redis redis = BaseUtil.getRedis();
		UserInfo userInfo = getUser();
		if (redis.exists(token)) {
			Integer time = Const.MAX_USER_EXIST_TIME;
			redis.expire(token, time);
			redis.expire(getUserToken(userInfo.getLoginType(), userInfo.getUserId()), time);
		}
	}

	/**
	 * @param token    token
	 * @param userInfo
	 * @param type     type 1 PC 2 mobile
	 */
	public static void userToken(String token, UserInfo userInfo, Integer type) {
		if (type == null) {
			type = 1;
		}
		userExit(userInfo.getUserId(), type, 1);
		Redis redis = BaseUtil.getRedis();
		String userToken = getUserToken(type, userInfo.getUserId());
		redis.setex(token, Const.MAX_USER_EXIST_TIME, userInfo);
		redis.setex(userToken, Const.MAX_USER_EXIST_TIME, token);
		Cookie cookie = new Cookie(Const.TOKEN_NAME, token);
		Long second = DateUtil.betweenMs(new Date(), DateUtil.parseDate("2030-01-01")) / 1000L;
		cookie.setMaxAge(second.intValue());
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		BaseUtil.getResponse().addCookie(cookie);
	}

	public static Long getSuperUser() {
		return 14773L;
	}

	public static Integer getSuperRole() {
		return 180162;
	}

	public static boolean isAdmin() {
		UserInfo userInfo = getUser();
		return userInfo.getUserId().equals(userInfo.getSuperUserId()) || userInfo.getRoles().contains(userInfo.getSuperRoleId());
	}

	public static void userExit(Long userId, Integer type) {
		if (type == null) {
			for (Integer integer : Arrays.asList(1, 2, 3, 4)) {
				userExit(userId, integer, null);
			}
		} else {
			userExit(userId, type, null);
		}

	}

	private static void userExit(Long userId, Integer type, Integer extra) {
		Redis redis = BaseUtil.getRedis();
		String token = null, key = getUserToken(type, userId);
		if (redis.exists(key)) {
			token = redis.get(key);
			redis.del(key);
		}
		//1
		if (Objects.equals(1, extra) && token != null) {
			Long time = redis.ttl(token);
			if (time > 1L) {
				redis.setex(token, time.intValue(), new UserExtraInfo(1, DateUtil.formatDateTime(new Date())));
			}
		} else {
			if (token != null) {
				redis.del(token);
			}
		}
	}

	/**
	 * @return key
	 */
	private static String getUserToken(Integer type, Long userId) {
		return Const.USER_TOKEN + ":" + type + ":" + userId.toString();
	}

}
