package c.a.security.util;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import c.a.util.core.security.BASE64ThreadLocal;
import c.a.util.core.security.BASE64Util;
import c.a.util.core.string.StringUtil;
public class CommASEUtil {
	private final static String HEX = "0123456789ABCDEF";
	// AES是加密方式
	// CBC是工作模式
	// PKCS5Padding是填充模式
	private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
	// AES 加密
	private static final String AES = "AES";
	// SHA1PRNG 强随机种子算法,
	// 要区别4.2以上版本的调用方法
	private static final String SHA1PRNG = "SHA1PRNG";
	/*
	 * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
	 */
	public static String generateKey() {
		try {
			SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
			byte[] bytes_key = new byte[20];
			localSecureRandom.nextBytes(bytes_key);
			String keyStr = toHex(bytes_key);
			return keyStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// 对密钥进行处理
	private static byte[] findRawKey(byte[] seed) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
		// for android
		SecureRandom secureRandom = null;
		// 在4.2以上版本中，SecureRandom获取方式发生了改变
		// if (android.os.Build.VERSION.SDK_INT >= 17) {
		// secureRandom = SecureRandom.getInstance(SHA1PRNG, "Crypto");
		// } else {
		// secureRandom = SecureRandom.getInstance(SHA1PRNG);
		// }
		// for Java
		secureRandom = SecureRandom.getInstance(SHA1PRNG);
		secureRandom.setSeed(seed);
		keyGenerator.init(128, secureRandom); // 256 bits or 128 bits,192bits
		// AES中128位密钥版本有10个加密循环，192比特密钥版本有12个加密循环，256比特密钥版本则有14个加密循环。
		SecretKey skey = keyGenerator.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}
	/*
	 * 加密
	 */
	public static String encode(String key, String cleartext) {
		BASE64Util base64Util = BASE64ThreadLocal.findThreadLocal().get();
		if (StringUtil.isBlank(cleartext)) {
			return cleartext;
		}
		try {
			byte[] result = encode(key, cleartext.getBytes());
			return base64Util.encodeBASE64(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 加密
	 */
	private static byte[] encode(String key, byte[] clearByteArray) throws Exception {
		byte[] raw = findRawKey(key.getBytes());
		SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
		Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		byte[] encodeByteArray = cipher.doFinal(clearByteArray);
		return encodeByteArray;
	}
	/*
	 * 解密
	 */
	public static String decode(String key, String encodeByteArray) {
		BASE64Util base64Util = BASE64ThreadLocal.findThreadLocal().get();
		if (StringUtil.isBlank(encodeByteArray)) {
			return encodeByteArray;
		}
		try {
			byte[] enc = base64Util.decodeBASE64(encodeByteArray);
			byte[] result = decode(key, enc);
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 解密
	 */
	private static byte[] decode(String key, byte[] encodeByteArray) throws Exception {
		byte[] raw = findRawKey(key.getBytes());
		SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
		Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		byte[] decrypted = cipher.doFinal(encodeByteArray);
		return decrypted;
	}
	// 二进制转字符
	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuilder result = new StringBuilder(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}
	private static void appendHex(StringBuilder sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
}
