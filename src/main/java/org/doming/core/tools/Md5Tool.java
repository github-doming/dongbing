package org.doming.core.tools;
import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * @Description: Md5校验
 * @Author: Dongming
 * @Date: 2019-09-10 15:36
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Md5Tool {

	public static final String SALT = "dongmingdongming";
	/**
	 * 生成含有随机盐的加密码
	 *
	 * @param strData 未加密字符串
	 * @return 加密后的字符串
	 */
	public static String generate(String strData) throws NoSuchAlgorithmException {
		strData = md5Hex(strData + SALT);
		char[] cs = new char[48];
		for (int i = 0; i < 48; i += 3) {
			cs[i] = strData.charAt(i / 3 * 2);
			char c = SALT.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = strData.charAt(i / 3 * 2 + 1);
		}
		return new String(cs);
	}

	/**
	 * 校验密码是否正确
	 *
	 * @param strData   未加密字符串
	 * @param verifyStr 加密后的字符串
	 * @return 校验结果
	 */
	public static boolean verify(String strData, String verifyStr) throws NoSuchAlgorithmException {
		char[] cs1 = new char[32];
		char[] cs2 = new char[16];
		for (int i = 0; i < 48; i += 3) {
			cs1[i / 3 * 2] = verifyStr.charAt(i);
			cs1[i / 3 * 2 + 1] = verifyStr.charAt(i + 2);
			cs2[i / 3] = verifyStr.charAt(i + 1);
		}
		String salt = new String(cs2);
		return md5Hex(strData + salt).equals(new String(cs1));
	}

	/**
	 * 获取十六进制字符串形式的MD5摘要
	 *
	 * @param str 输入字符串
	 * @return MD5摘要字符串
	 */
	public static String md5Hex(String str) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] bs = md5.digest(str.getBytes());
		return new String(new Hex().encode(bs));

	}
}
