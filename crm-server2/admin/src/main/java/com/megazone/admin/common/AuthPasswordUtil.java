package com.megazone.admin.common;

import cn.hutool.crypto.SecureUtil;

public class AuthPasswordUtil {

	public static boolean verify(String key, String salt, String sign) {
		return sign.equals(sign(key, salt));
	}

	public static String sign(String key, String salt) {
		return SecureUtil.md5(key.concat("erp").concat(salt));
	}
}
