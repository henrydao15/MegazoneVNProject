package com.megazone.authorization.common;

import cn.hutool.crypto.SecureUtil;

public class AuthPasswordUtil {

	/**
	 * Verify that the signature is correct
	 *
	 * @param key  key
	 * @param salt salt
	 * @param sign signature
	 *             Whether @return is correct true is correct
	 */
	public static boolean verify(String key, String salt, String sign) {
		return sign.equals(sign(key, salt)) || sign.equals(signP(key, salt)) || sign.equals(signP2(key, salt));
	}

	/**
	 * Signature data
	 *
	 * @param key  key
	 * @param salt salt
	 * @return encrypted string
	 */
	public static String sign(String key, String salt) {
		return SecureUtil.md5(key.concat("erp").concat(salt));
	}

	/**
	 * Signature data
	 * PHP side signature
	 *
	 * @param key  key
	 * @param salt salt
	 * @return encrypted string
	 */
	private static String signP(String key, String salt) {
		String username = key.substring(0, 11);
		String password = key.substring(11);
		return SecureUtil.md5(SecureUtil.md5(SecureUtil.sha1(username.concat(password))) + SecureUtil.md5(password.concat(salt)));
	}

	private static String signP2(String key, String salt) {
		String username = key.substring(0, 11);
		String password = key.substring(11);
		return SecureUtil.md5(SecureUtil.sha1(password) + SecureUtil.md5(password.concat(salt)));
	}
}
