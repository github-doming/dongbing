package c.a.util.core.security.aes;
import java.io.IOException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.ProjectConfig;
import c.a.tools.jdbc.transaction.TransactionBase;
import c.a.util.core.string.StringUtil;
/**
 * 
 * AES
 * 
 * 对称加密，密钥最长只有256个bit，执行速度快，易于硬件实现。由于是对称加密，密钥需要在传输前通讯双方获知。
 * 
 * 基于以上特点，通常使用RSA来首先传输AES的密钥给对方，然后再使用AES来进行加密通讯。
 * 
 * @Description:
 * @ClassName: AESUtil
 * @date 2018年7月18日 下午1:02:45
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class AESAyUtil {
	
	private final static String HEX = "0123456789ABCDEF";
	private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";// AES是加密方式
																			// CBC是工作模式
																			// PKCS5Padding是填充模式
	private static final String AES = "AES";// AES 加密
	private static final String SHA1PRNG = "SHA1PRNG";//// SHA1PRNG 强随机种子算法,
														//// 要区别4.2以上版本的调用方法
	/*
	 * 生成随机数，可以当做动态的密钥 加密和解密的密钥必须一致，不然将不能解密
	 */
	public static String generateKey() {
		try {
			SecureRandom localSecureRandom = SecureRandom.getInstance(SHA1PRNG);
			byte[] bytes_key = new byte[20];
			localSecureRandom.nextBytes(bytes_key);
			String str_key = toHex(bytes_key);
			return str_key;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	// 对密钥进行处理
	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(AES);
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
		kgen.init(128, secureRandom); // 256 bits or 128 bits,192bits
		// AES中128位密钥版本有10个加密循环，192比特密钥版本有12个加密循环，256比特密钥版本则有14个加密循环。
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}
	/*
	 * 加密
	 */
	public static String encrypt(String key, String cleartext) {
		if (StringUtil.isBlank(cleartext)) {
			return cleartext;
		}
		try {
			byte[] result = encrypt(key, cleartext.getBytes());
			return Base64Encoder.encode(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 加密
	 */
	private static byte[] encrypt(String key, byte[] clear) throws Exception {
		byte[] raw = getRawKey(key.getBytes());
		SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
		Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}
	/*
	 * 解密
	 */
	public static String decrypt(String key, String encrypted) {
		if (StringUtil.isBlank(encrypted)) {
			return encrypted;
		}
		try {
			byte[] enc = Base64Decoder.decodeToBytes(encrypted);
			byte[] result = decrypt(key, enc);
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 解密
	 */
	private static byte[] decrypt(String key, byte[] encrypted) throws Exception {
		byte[] raw = getRawKey(key.getBytes());
		SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
		Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		byte[] decrypted = cipher.doFinal(encrypted);
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
	public static void main(String[] args) throws IOException {
		Logger log = LogManager.getLogger(ProjectConfig.class);
		// String jsonData = "你今天吃了没";
		String jsonData = "1998年世界杯回到世界杯之父雷米特的祖国法国，而之前连续两届世界杯被挡在决赛圈门外的法国队凭借九十年代以来卓有成效的青训体系，培养出了包括齐达内、德约卡夫、图拉姆、巴特斯在内的一大批世界级球星，成为国际足坛举足轻重的新兴力量。另一方面，上届冠军巴西队，在94年之后人才辈出，其中尤以在巴塞罗那和国际米兰连拿两届世界足球先生的罗纳尔多最引人注目，此外，荷兰队在三剑客退役之后以博格坎普和阿贾克斯95黄金一代为首的足坛巨星，基本完成了国家队的新老交替，神奇教练希丁克即将迈出他不可思议的世界杯执教生涯的第一步；德国队虽然两年前在英格兰获得欧洲杯冠军，但令人遗憾的是，1990年那群为联邦德国队第三次夺得世界杯的老臣们依然大部分留队，被认为是贝肯鲍尔之后德国最优秀的自由人萨默尔因重伤告别世界杯，为球队前景蒙上了阴影；意大利队人才济济，巴乔宝刀未老、皮耶罗、因扎吉风头正劲，但遗憾的是，他们的统帅老马尔蒂尼却是一位著名的食古不化者；阿根廷队在少帅帕萨雷拉上任之后清洗了大批异己，其中包括留发不留队的雷东多和卡尼吉亚，神奇教练米卢火线接管尼日利亚，在曼联出人头地的小贝却被霍德尔按在替补席上，独立后的克罗地亚继欧洲杯之后再次出现在世界杯舞台上，1998年世界杯的大幕，在1998年6月10日徐徐拉开。 [1] ";
		log.trace("MainActivity=AES加密前json数据 ---->" + jsonData);
		log.trace("MainActivity=AES加密前json数据长度 ---->" + jsonData.length());
		// 生成一个动态key
		String secretKey = AESAyUtil.generateKey();
		log.trace("MainActivity=AES动态secretKey ---->" + secretKey);
		// AES加密
		long start = System.currentTimeMillis();
		String encryStr = AESAyUtil.encrypt(secretKey, jsonData);
		long end = System.currentTimeMillis();
		log.trace("MainActivity=AES加密耗时 cost time---->" + (end - start));
		log.trace("MainActivity=AES加密后json数据 ---->" + encryStr);
		log.trace("MainActivity=AES加密后json数据长度 ---->" + encryStr.length());
		// AES解密
		start = System.currentTimeMillis();
		String decryStr = AESAyUtil.decrypt(secretKey, encryStr);
		end = System.currentTimeMillis();
		log.trace("MainActivity=AES解密耗时 cost time---->" + (end - start));
		log.trace("MainActivity=AES解密后json数据 ---->" + decryStr);
	}
}