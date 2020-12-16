package c.a.util.core.security;
import java.net.URLDecoder;
import java.net.URLEncoder;
/**
 * 
 * 
 */
public class UrlUtil {
	/**
	 * 解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String decode(String key) throws Exception {
		return URLDecoder.decode(key, "UTF-8");
	}
	/**
	 * 加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encode(String key) throws Exception {
		return URLEncoder.encode(key, "UTF-8");
	}
}
