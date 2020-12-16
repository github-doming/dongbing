package c.a.util.core.path;
/**
 * 
 *  Path工具类(单例)
 * 
 * @Description:
 * @date 2017年3月28日 下午6:18:58
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class PathSingletonUtil extends PathUtil{
	private static PathUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private PathSingletonUtil() {
	}
	public static PathUtil findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new PathUtil();
				}
			}
		}
		return instance;
	}
}
