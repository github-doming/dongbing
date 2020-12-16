package c.a.util.core.security;

import java.math.BigInteger;
import java.security.MessageDigest;

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
public class SHAUtil {
	public static final String KEY_SHA = "SHA";
	
	public String encode(String data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);  
        sha.update(data.getBytes());  
  
        
        
		byte[] byteArray =sha.digest();
		return new BigInteger(byteArray).toString();
	}
	/**
	 * 加密； 通常我们不直接使用上述SHA加密。
	 * 
	 * 通常将SHA产生的字节数组交给BASE64再加密一把，
	 * 
	 * 得到相应的字符串。
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public byte[] encode(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);  
        sha.update(data);  
  
        return sha.digest();  
	}

}
