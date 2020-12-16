package c.a.util.core.security.php;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.ProjectConfig;
public class RSAPhpUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	private static String RSA = "RSA";
	private static String RSAP = "RSA/ECB/PKCS1Padding";
	// 加密填充方式
	public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
	// 秘钥默认长度
	// public static final int DEFAULT_KEY_SIZE = 2048;
	// 秘钥默认长度
	public static final int DEFAULT_KEY_SIZE = 1024;
	// 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
	public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();
	// 当前秘钥支持加密的最大字节数
	public static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11;
	/**
	 * 随机生成RSA密钥对(默认密钥长度为1024)
	 *
	 * @return
	 */
	public static KeyPair generateRSAKeyPair() {
		return generateRSAKeyPair(1024);
	}
	/**
	 * 随机生成RSA密钥对
	 *
	 * @param keyLength
	 *            密钥长度，范围：512～2048<br>
	 *            一般1024
	 * @return
	 */
	public static KeyPair generateRSAKeyPair(int keyLength) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA, "BC");
			kpg.initialize(keyLength);
			return kpg.genKeyPair();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 用公钥加密 <br>
	 * 每次加密的字节数，不能超过密钥的长度值减去11
	 *
	 * @param data
	 *            需加密数据的byte数据
	 * @param publicKey
	 *            公钥
	 * @return 加密后的byte型数据
	 */
	public static byte[] encryptData(byte[] data, PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSAP);
			// 编码前设定编码方式及密钥
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			// 传入编码数据并返回编码结果
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 用公钥对字符串进行分段加密
	 */
	public static byte[] encryptByPublicKeyForSpilt(byte[] data, PublicKey publicKey) throws Exception {
		int dataLen = data.length;
		if (dataLen <= DEFAULT_BUFFERSIZE) {
			return encryptData(data, publicKey);
		}
		List<Byte> allBytes = new ArrayList<Byte>(1024);
		int bufIndex = 0;
		int subDataLoop = 0;
		byte[] buf = new byte[DEFAULT_BUFFERSIZE];
		for (int i = 0; i < dataLen; i++) {
			buf[bufIndex] = data[i];
			if (++bufIndex == DEFAULT_BUFFERSIZE || i == dataLen - 1) {
				subDataLoop++;
				if (subDataLoop != 1) {
					for (byte b : DEFAULT_SPLIT) {
						allBytes.add(b);
					}
				}
				byte[] encryptBytes = encryptData(buf, publicKey);
				for (byte b : encryptBytes) {
					allBytes.add(b);
				}
				bufIndex = 0;
				if (i == dataLen - 1) {
					buf = null;
				} else {
					buf = new byte[Math.min(DEFAULT_BUFFERSIZE, dataLen - i - 1)];
				}
			}
		}
		byte[] bytes = new byte[allBytes.size()];
		{
			int i = 0;
			for (Byte b : allBytes) {
				bytes[i++] = b.byteValue();
			}
		}
		return bytes;
	}
	/**
	 * 用私钥解密
	 *
	 * @param encryptedData
	 *            经过encryptedData()加密返回的byte数据
	 * @param privateKey
	 *            私钥
	 * @return
	 */
	public static byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSAP);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 通过公钥byte[](publicKey.getEncoded())将公钥还原，适用于RSA算法
	 * 
	 * @Title: findPublicKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param keyBytes
	 * @return
	 * @throws Exception
	 * @throws InvalidKeySpecException
	 *             返回类型 PublicKey
	 */
	public static PublicKey findPublicKey(byte[] keyBytes) throws Exception, InvalidKeySpecException {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA, "BC");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}
	/**
	 * 通过私钥byte[]将公钥还原，适用于RSA算法
	 * 
	 * @Title: findPrivateKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param keyBytes
	 * @return
	 * @throws Exception
	 * @throws InvalidKeySpecException
	 *             返回类型 PrivateKey
	 */
	public static PrivateKey findPrivateKey(byte[] keyBytes) throws Exception, InvalidKeySpecException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA, "BC");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	/**
	 * 使用N、e值还原公钥
	 * 
	 * @Title: findPublicKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param modulus
	 * @param publicExponent
	 * @return
	 * @throws Exception
	 *             返回类型 PublicKey
	 */
	public static PublicKey findPublicKey(String modulus, String publicExponent) throws Exception {
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(publicExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA, "BC");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}
	/**
	 * 使用N、d值还原私钥
	 * 
	 * @Title: findPrivateKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param modulus
	 * @param privateExponent
	 * @return
	 * @throws Exception
	 *             返回类型 PrivateKey
	 */
	public static PrivateKey findPrivateKey(String modulus, String privateExponent) throws Exception {
		BigInteger bigIntModulus = new BigInteger(modulus);
		BigInteger bigIntPrivateExponent = new BigInteger(privateExponent);
		RSAPublicKeySpec keySpec = new RSAPublicKeySpec(bigIntModulus, bigIntPrivateExponent);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA, "BC");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	/**
	 * 从字符串中加载公钥
	 *
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public PublicKey loadPublicKey(String publicKeyStr) throws Exception {
		try {
			byte[] buffer = Base64PhpUtil.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA, "BC");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("公钥数据为空");
		}
	}
	/**
	 * 从字符串中加载私钥<br>
	 * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
	 *
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			byte[] buffer = Base64PhpUtil.decode(privateKeyStr);
			// X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA, "BC");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("私钥数据为空");
		}
	}
	/**
	 * 从文件中输入流中加载公钥
	 *
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public  PublicKey loadPublicKey(InputStream in) throws Exception {
		try {
			return loadPublicKey(readKey(in));
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("公钥输入流为空");
		}
	}
	/**
	 * 从文件中加载私钥
	 * <p>
	 * keyFileName 私钥文件名
	 *
	 * @return 是否成功
	 * @throws Exception
	 */
	public static PrivateKey loadPrivateKey(InputStream in) throws Exception {
		try {
			return loadPrivateKey(readKey(in));
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new Exception("私钥输入流为空");
		}
	}
	/**
	 * 读取密钥信息
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static String readKey(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String readLine = null;
		StringBuilder sb = new StringBuilder();
		while ((readLine = br.readLine()) != null) {
			if (readLine.charAt(0) == '-') {
				continue;
			} else {
				sb.append(readLine);
				sb.append('\r');
			}
		}
		return sb.toString();
	}
	/**
	 * 打印公钥信息
	 *
	 * @param publicKey
	 */
	public void printPublicKeyInfo(PublicKey publicKey) {
		RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
		log.trace("----------RSAPublicKey----------");
		log.trace("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
		log.trace("Modulus=" + rsaPublicKey.getModulus().toString());
		log.trace("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
		log.trace("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
	}
	public void printPrivateKeyInfo(PrivateKey privateKey) {
		RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
		log.trace("----------RSAPrivateKey ----------");
		log.trace("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
		log.trace("Modulus=" + rsaPrivateKey.getModulus().toString());
		log.trace("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
		log.trace("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());
	}
	public static void main(String[] args) {
		Logger log = LogManager.getLogger(ProjectConfig.class);
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			RSAPhpUtil rsaPhpUtil = new RSAPhpUtil();
			// 获取
			// 公钥和私钥
			rsaPhpUtil.printPublicKeyInfo(rsaPhpUtil.loadPublicKey(RSAPhpConfig.PUBLIC_KEY));
			log.trace("----------------------------------");
			rsaPhpUtil.printPrivateKeyInfo(rsaPhpUtil.loadPrivateKey(RSAPhpConfig.PRIVATE_KEY));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.trace("end");
	}
}
