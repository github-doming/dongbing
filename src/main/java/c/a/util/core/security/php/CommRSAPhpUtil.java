package c.a.util.core.security.php;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import c.a.util.core.security.BASE64Util;
import c.a.util.core.security.php.RSAPhpConfig;
import c.a.util.core.security.php.RSAPhpUtil;
public class CommRSAPhpUtil {
	/**
	 * 
	 * 解密 @Description: desc @Title: decode @param
	 * dataEncode @return return @throws Exception 参数说明 @return String
	 * 返回类型 @throws
	 */
	public static String decode(String dataEncode) throws Exception {
		BASE64Util base64Util = new BASE64Util();
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		PrivateKey privateKey = RSAPhpUtil.loadPrivateKey(RSAPhpConfig.PRIVATE_KEY);
		byte[] dataDecode = Base64PhpUtil.decode(dataEncode);
		// byte[] dataDecode = base64Util.decodeBASE64(dataEncode);
		byte[] plainByteArray = RSAPhpUtil.decryptData(dataDecode, privateKey);
		if (plainByteArray == null) {
			return null;
		}
		String plain = new String(plainByteArray, "utf-8");
		return plain;
	}
	/**
	 * 
	 * 加密 @Description: desc @Title: encode @param
	 * data @return return @throws Exception 参数说明 @return String
	 * 返回类型 @throws
	 */
	public static String encode(String data) throws Exception {
		RSAPhpUtil rsaPhpUtil = new RSAPhpUtil();
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		PublicKey publicKey = rsaPhpUtil.loadPublicKey(RSAPhpConfig.PUBLIC_KEY);
		byte[] encryptByteArray = RSAPhpUtil.encryptData(data.getBytes(), publicKey);
		String dataEncode = Base64PhpUtil.encode(encryptByteArray);
		// String dataEncode = base64Util.encodeBASE64(encryptByteArray);
		return dataEncode;
	}
}
