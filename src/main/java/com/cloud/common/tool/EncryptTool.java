package com.cloud.common.tool;
import c.a.config.SysConfig;
import c.a.security.util.CommASEUtil;
import c.a.security.util.CommRSAUtil;
import org.doming.core.tools.AssertTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
/**
 * @Description: 加密工具类
 * @Author: Dongming
 * @Date: 2019-09-04 15:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class EncryptTool {

	/**
	 * 加密
	 *
	 * @param type 加密类型
	 * @param str  字符串
	 * @return 加密字符串
	 */
	public static String encode(Type type, String str) throws Exception {
		AssertTool.notNull(str, "待加密字符串为空");
		switch (type) {
			case ASE:
				String key = SysConfig.findInstance().findMap().getOrDefault("comm.local.ASE_key", "false").toString();
				str = CommASEUtil.encode(key, str.trim());
				if (StringTool.notEmpty(str)) {
					return str.trim();
				}
				return str;
			case RSA:
				return CommRSAUtil.decode(str);
			case MD5:
				return Md5Tool.generate(str);
			default:
				throw new IllegalArgumentException("未知的加密类型");
		}
	}

	/**
	 * 解密
	 *
	 * @param type 加密类型
	 * @param str  加密字符串
	 * @return 解密字符串
	 */
	public static String decode(Type type, String str) throws Exception {
		AssertTool.notNull(str, "待解密字符串为空");
		switch (type) {
			case ASE:
				String key = SysConfig.findInstance().findMap().getOrDefault("comm.local.ASE_key", "false").toString();
				str = CommASEUtil.decode(key, str.trim());
				if (StringTool.notEmpty(str)) {
					return str.trim();
				}
				return str;
			case RSA:
				return CommRSAUtil.decode(str);
			case MD5:
				throw new IllegalArgumentException("不可解密的类型");
			default:
				throw new IllegalArgumentException("未知的加密类型");
		}
	}

	public enum Type {
		/**
		 * 加密类型
		 */
		ASE, RSA, MD5

	}
}
