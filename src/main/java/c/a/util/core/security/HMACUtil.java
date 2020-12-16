package c.a.util.core.security;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * MD5、SHA、HMAC这三种加密算法，
 * 
 * 可谓是非可逆加密，就是不可解密的加密方法。
 * 
 * 我们通常只把他们作为加密的基础。
 * 
 * 单纯的以上三种的加密并不可靠。
 */
public class HMACUtil extends BASE64Util {
	public static final String KEY_MAC = "HmacMD5";

	public String encode(String key, String data) throws Exception {
		SecretKey secretKey = new SecretKeySpec(decodeBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		byte[] byteArray = mac.doFinal(data.getBytes());
		return new BigInteger(byteArray).toString();
	}
	/**
	 * 加密； 通常我们不直接使用上述HMAC加密。
	 * 
	 * 通常将HMAC 产生的字节数组交给BASE64再加密一把，
	 * 
	 * 得到相应的字符串。
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] encode(String key, byte[] data) throws Exception {
		SecretKey secretKey = new SecretKeySpec(decodeBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);
	}
	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();

		return encodeBASE64(secretKey.getEncoded());
	}
}
