package c.a.security.util;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;
public class RSAAndroidUtil {
	// 非对称加密密钥算法
	public static final String RSA = "RSA";
	// 加密填充方式
	public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
	// 秘钥默认长度
	public static final int DEFAULT_KEY_SIZE = 2048;
	// 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
	public static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();
	// 当前秘钥支持加密的最大字节数
	public static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11;
	/**
	 * 随机生成RSA密钥对
	 *
	 * @param keyLength
	 *            密钥长度，范围：512～2048 一般1024
	 * @return
	 */
	public static KeyPair generateRSAKeyPair(int keyLength) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
			kpg.initialize(keyLength);
			return kpg.genKeyPair();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 用公钥对字符串进行加密
	 *
	 * @param data
	 *            原文
	 */
	public static byte[] encodeByPublicKey(byte[] data, byte[] publicKey) throws Exception {
		// 得到公钥
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PublicKey keyPublic = kf.generatePublic(keySpec);
		// 加密数据
		Cipher cp = Cipher.getInstance(ECB_PKCS1_PADDING);
		cp.init(Cipher.ENCRYPT_MODE, keyPublic);
		return cp.doFinal(data);
	}
	/**
	 * 私钥加密
	 *
	 * @param data
	 *            待加密数据
	 * @param privateKey
	 *            密钥
	 * @return byte[] 加密数据
	 */
	public static byte[] encodeByPrivateKey(byte[] data, byte[] privateKey) throws Exception {
		// 得到私钥
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PrivateKey keyPrivate = kf.generatePrivate(keySpec);
		// 数据加密
		Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, keyPrivate);
		return cipher.doFinal(data);
	}
	/**
	 * 公钥解密
	 *
	 * @param data
	 *            待解密数据
	 * @param publicKey
	 *            密钥
	 * @return byte[] 解密数据
	 */
	public static byte[] decodeByPublicKey(byte[] data, byte[] publicKey) throws Exception {
		// 得到公钥
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PublicKey keyPublic = kf.generatePublic(keySpec);
		// 数据解密
		Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
		cipher.init(Cipher.DECRYPT_MODE, keyPublic);
		return cipher.doFinal(data);
	}
	/**
	 * 使用私钥进行解密
	 */
	public static byte[] decodeByPrivateKey(byte[] encodeByteArray, byte[] privateKey) throws Exception {
		// 得到私钥
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
		KeyFactory kf = KeyFactory.getInstance(RSA);
		PrivateKey keyPrivate = kf.generatePrivate(keySpec);
		// 解密数据
		Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
		cipher.init(Cipher.DECRYPT_MODE, keyPrivate);
		byte[] byteArray = cipher.doFinal(encodeByteArray);
		return byteArray;
	}
	/**
	 * 用公钥对字符串进行分段加密
	 *
	 */
	public static byte[] encodeByPublicKeyForSpilt(byte[] data, byte[] publicKey) throws Exception {
		int dataLen = data.length;
		if (dataLen <= DEFAULT_BUFFERSIZE) {
			return encodeByPublicKey(data, publicKey);
		}
		List<Byte> byteList = new ArrayList<Byte>(2048);
		int bufIndex = 0;
		int subDataLoop = 0;
		byte[] byteArray = new byte[DEFAULT_BUFFERSIZE];
		for (int i = 0; i < dataLen; i++) {
			byteArray[bufIndex] = data[i];
			if (++bufIndex == DEFAULT_BUFFERSIZE || i == dataLen - 1) {
				subDataLoop++;
				if (subDataLoop != 1) {
					for (byte b : DEFAULT_SPLIT) {
						byteList.add(b);
					}
				}
				byte[] encryptBytes = encodeByPublicKey(byteArray, publicKey);
				for (byte b : encryptBytes) {
					byteList.add(b);
				}
				bufIndex = 0;
				if (i == dataLen - 1) {
					byteArray = null;
				} else {
					byteArray = new byte[Math.min(DEFAULT_BUFFERSIZE, dataLen - i - 1)];
				}
			}
		}
		byte[] byteArrayReturn = new byte[byteList.size()];
		{
			int i = 0;
			for (Byte b : byteList) {
				byteArrayReturn[i++] = b.byteValue();
			}
		}
		return byteArrayReturn;
	}
	/**
	 * 分段加密
	 *
	 * @param data
	 *            要加密的原始数据
	 * @param privateKey
	 *            秘钥
	 */
	public static byte[] encodeByPrivateKeyForSpilt(byte[] data, byte[] privateKey) throws Exception {
		int dataLen = data.length;
		if (dataLen <= DEFAULT_BUFFERSIZE) {
			return encodeByPrivateKey(data, privateKey);
		}
		List<Byte> byteList = new ArrayList<Byte>(2048);
		int bufIndex = 0;
		int subDataLoop = 0;
		byte[] byteArray = new byte[DEFAULT_BUFFERSIZE];
		for (int i = 0; i < dataLen; i++) {
			byteArray[bufIndex] = data[i];
			if (++bufIndex == DEFAULT_BUFFERSIZE || i == dataLen - 1) {
				subDataLoop++;
				if (subDataLoop != 1) {
					for (byte b : DEFAULT_SPLIT) {
						byteList.add(b);
					}
				}
				byte[] encryptBytes = encodeByPrivateKey(byteArray, privateKey);
				for (byte b : encryptBytes) {
					byteList.add(b);
				}
				bufIndex = 0;
				if (i == dataLen - 1) {
					byteArray = null;
				} else {
					byteArray = new byte[Math.min(DEFAULT_BUFFERSIZE, dataLen - i - 1)];
				}
			}
		}
		byte[] byteArrayReturn = new byte[byteList.size()];
		{
			int i = 0;
			for (Byte b : byteList) {
				byteArrayReturn[i++] = b.byteValue();
			}
		}
		return byteArrayReturn;
	}
	/**
	 * 公钥分段解密
	 *
	 * @param encodeByteArray
	 *            待解密数据
	 * @param publicKey
	 *            密钥
	 */
	public static byte[] decodeByPublicKeyForSpilt(byte[] encodeByteArray, byte[] publicKey) throws Exception {
		int splitLen = DEFAULT_SPLIT.length;
		if (splitLen <= 0) {
			return decodeByPublicKey(encodeByteArray, publicKey);
		}
		int dataLen = encodeByteArray.length;
		List<Byte> allBytes = new ArrayList<Byte>(1024);
		int latestStartIndex = 0;
		for (int i = 0; i < dataLen; i++) {
			byte bt = encodeByteArray[i];
			boolean isMatchSplit = false;
			if (i == dataLen - 1) {
				// 到data的最后了
				byte[] part = new byte[dataLen - latestStartIndex];
				System.arraycopy(encodeByteArray, latestStartIndex, part, 0, part.length);
				byte[] decryptPart = decodeByPublicKey(part, publicKey);
				for (byte b : decryptPart) {
					allBytes.add(b);
				}
				latestStartIndex = i + splitLen;
				i = latestStartIndex - 1;
			} else if (bt == DEFAULT_SPLIT[0]) {
				// 这个是以split[0]开头
				if (splitLen > 1) {
					if (i + splitLen < dataLen) {
						// 没有超出data的范围
						for (int j = 1; j < splitLen; j++) {
							if (DEFAULT_SPLIT[j] != encodeByteArray[i + j]) {
								break;
							}
							if (j == splitLen - 1) {
								// 验证到split的最后一位，都没有break，则表明已经确认是split段
								isMatchSplit = true;
							}
						}
					}
				} else {
					// split只有一位，则已经匹配了
					isMatchSplit = true;
				}
			}
			if (isMatchSplit) {
				byte[] part = new byte[i - latestStartIndex];
				System.arraycopy(encodeByteArray, latestStartIndex, part, 0, part.length);
				byte[] decryptPart = decodeByPublicKey(part, publicKey);
				for (byte b : decryptPart) {
					allBytes.add(b);
				}
				latestStartIndex = i + splitLen;
				i = latestStartIndex - 1;
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
	 * 使用私钥分段解密
	 *
	 */
	public static byte[] decodeByPrivateKeyForSpilt(byte[] encodeByteArray, byte[] privateKey) throws Exception {
		int splitLen = DEFAULT_SPLIT.length;
		if (splitLen <= 0) {
			return decodeByPrivateKey(encodeByteArray, privateKey);
		}
		int dataLen = encodeByteArray.length;
		List<Byte> allBytes = new ArrayList<Byte>(1024);
		int latestStartIndex = 0;
		for (int i = 0; i < dataLen; i++) {
			byte bt = encodeByteArray[i];
			boolean isMatchSplit = false;
			if (i == dataLen - 1) {
				// 到data的最后了
				byte[] part = new byte[dataLen - latestStartIndex];
				System.arraycopy(encodeByteArray, latestStartIndex, part, 0, part.length);
				byte[] decryptPart = decodeByPrivateKey(part, privateKey);
				for (byte b : decryptPart) {
					allBytes.add(b);
				}
				latestStartIndex = i + splitLen;
				i = latestStartIndex - 1;
			} else if (bt == DEFAULT_SPLIT[0]) {
				// 这个是以split[0]开头
				if (splitLen > 1) {
					if (i + splitLen < dataLen) {
						// 没有超出data的范围
						for (int j = 1; j < splitLen; j++) {
							if (DEFAULT_SPLIT[j] != encodeByteArray[i + j]) {
								break;
							}
							if (j == splitLen - 1) {
								// 验证到split的最后一位，都没有break，则表明已经确认是split段
								isMatchSplit = true;
							}
						}
					}
				} else {
					// split只有一位，则已经匹配了
					isMatchSplit = true;
				}
			}
			if (isMatchSplit) {
				byte[] part = new byte[i - latestStartIndex];
				System.arraycopy(encodeByteArray, latestStartIndex, part, 0, part.length);
				byte[] decryptPart = decodeByPrivateKey(part, privateKey);
				for (byte b : decryptPart) {
					allBytes.add(b);
				}
				latestStartIndex = i + splitLen;
				i = latestStartIndex - 1;
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
}
