package c.a.util.core.bean;

/**
 * bean工具类
 * 
 * @author cxy
 * @Email:  使用范围：
 */
public class BeanTUtil<T> extends BeanUtil {
	/**
	 * 返回缺省值
	 * 
	 * @param objectSource
	 * @param objectDefault
	 * @return
	 */

	public T get2T(T objectSource, T objectDefault) {
		return (T) findObject(objectSource, objectDefault);
	}
}
