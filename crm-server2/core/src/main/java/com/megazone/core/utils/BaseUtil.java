package com.megazone.core.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import com.megazone.core.entity.UserInfo;
import com.megazone.core.redis.Redis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;

/**
 * @author z
 */
public class BaseUtil {

	/**
	 *
	 */
	public final static String UPLOAD_PATH = BaseUtil.isWindows() ? "D:\\upload\\" : "/usr/local/upload/";
	private static final Snowflake SNOWFLAKE = IdUtil.getSnowflake(1, 1);

	/**
	 * @return redis
	 */
	public static Redis getRedis() {
		return UserCacheUtil.ME.redis;
	}

	/**
	 * @return yyyyMMdd
	 */
	public static String getDate() {
		return DateUtil.format(new Date(), "yyyyMMdd");
	}

	/**
	 * @return request
	 */

	public static HttpServletRequest getRequest() {
		return Optional.ofNullable(UserUtil.getUser()).map(UserInfo::getRequest).orElse(null);
	}

	/**
	 * @return id
	 */
	public static Long getNextId() {
		return SNOWFLAKE.nextId();
	}

	/**
	 * @return response
	 */
	public static HttpServletResponse getResponse() {
		return UserUtil.getUser().getResponse();
	}

	/**
	 * @return true
	 */
	public static boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("windows");
	}

	public static String getIp() {
		return "127.0.0.1";
	}

	public static boolean isTime(String str) {
		if (Validator.isMactchRegex(Validator.BIRTHDAY, str)) {
			Matcher matcher = Validator.BIRTHDAY.matcher(str);
			if (matcher.find()) {
				int year = Integer.parseInt(matcher.group(1));
				int month = Integer.parseInt(matcher.group(3));
				int day = Integer.parseInt(matcher.group(5));
				//
				if (month < 1 || month > 12) {
					return false;
				}

				//
				if (day < 1 || day > 31) {
					return false;
				}
				//
				if (day == 31 && (month == 4 || month == 6 || month == 9 || month == 11)) {
					return false;
				}
				if (month == 2) {
					// 2，28，29
					return day < 29 || (day == 29 && DateUtil.isLeapYear(year));
				}
				return true;
			}
		}
		return false;
	}
}
