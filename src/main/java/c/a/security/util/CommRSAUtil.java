package c.a.security.util;
import java.security.Security;

import c.a.util.core.security.BASE64ThreadLocal;
import c.a.util.core.security.BASE64Util;
public class CommRSAUtil {
	/**
	 * 
	 * 解密
	 * @Description:
	 * @Title: decode 
	 * @param dataEncode
	 * @return
	 * @throws Exception  参数说明 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String decode(String dataEncode) throws Exception {
		BASE64Util base64Util=BASE64ThreadLocal.findThreadLocal().get();
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		byte[] encryptByteArray = base64Util.decodeBASE64(dataEncode);
		// 私钥解密
		byte[] plainTextByteArray = RSAAndroidUtil.decodeByPrivateKeyForSpilt(encryptByteArray,
				base64Util.decodeBASE64(RSAConfig.PRIVATE_KEY));
		if (plainTextByteArray == null) {
			return null;
		}
		String plain = new String(plainTextByteArray, "utf-8");
		return plain;
	}
	/**
	 * 
	 * 加密
	 * @Description:
	 * @Title: encode 
	 * @param data
	 * @return
	 * @throws Exception  参数说明 
	 * @return String    返回类型 
	 * @throws
	 */
	public static String encode(String plainText) throws Exception {
		BASE64Util base64Util=BASE64ThreadLocal.findThreadLocal().get();
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		// 公钥加密
		 byte[] encodeByteArray = RSAAndroidUtil.encodeByPublicKeyForSpilt(plainText.getBytes(),
				 base64Util.decodeBASE64(RSAConfig.PUBLIC_KEY));
		String dataEncode = base64Util.encodeBASE64(encodeByteArray);
		return dataEncode;
	}
}
