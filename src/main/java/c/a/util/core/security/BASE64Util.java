package c.a.util.core.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * 
 * BASE64的加密解密是双向的，可以求反解。
 * 
 * MD5、SHA以及HMAC是单向加密，
 * 
 * 
 * 任何数据加密后只会产生唯一的一个加密串，
 * 
 * 通常用来校验数据在传输过程中是否被修改。
 * 
 * 
 * 其中HMAC算法有一个密钥， 增强了数据传输过程中的安全性，
 * 
 * 强化了算法外的不可控因素。
 * 
 * 
 * 单向加密的用途主要是为了校验数据在传输过程中是否被修改。
 * 
 * 
 * 
 */
public class BASE64Util {
	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] decodeBASE64(String key) throws Exception {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		return base64Decoder.decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encodeBASE64(byte[] key) throws Exception {
		BASE64Encoder base64Encoder = new BASE64Encoder();
		return base64Encoder.encodeBuffer(key);
	}

}
