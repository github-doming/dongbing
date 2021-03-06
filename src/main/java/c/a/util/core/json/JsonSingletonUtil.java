package c.a.util.core.json;
/**
 * 
 *  JSON工具类(单例)
 * 
 * @Description:
 * @date 2017年3月28日 下午6:18:58
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class JsonSingletonUtil extends JsonUtil{
	private static JsonUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private JsonSingletonUtil() {
	}
	public static JsonUtil findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new JsonUtil();
				}
			}
		}
		return instance;
	}
}
