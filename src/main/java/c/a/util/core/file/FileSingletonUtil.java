package c.a.util.core.file;
/**
 * 
 *  File工具类(单例)
 * 
 * @Description:
 * @date 2017年3月28日 下午6:18:58
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class FileSingletonUtil extends FileUtil{
	private static FileUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private FileSingletonUtil() {
	}
	public static FileUtil findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new FileUtil();
				}
			}
		}
		return instance;
	}
}
