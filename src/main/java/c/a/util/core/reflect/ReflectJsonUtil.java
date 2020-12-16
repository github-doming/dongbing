package c.a.util.core.reflect;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.core.copy.CopyUtil;
/**
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class ReflectJsonUtil extends ReflectNutUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * map2object 然后vo转成po(map的key是json格式)
	 * 
	 * @Title: doMap2ObjectByColumn
	 * @Description:
	 *
	 * 				参数说明
	 * @param classOrigin
	 * @param classDestination
	 * @param map
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *             返回类型 Object
	 */
	public Object doMap2ObjectByJson(Class classOrigin, Class classDestination, Map<String, Object> map)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object objectOrigin = this.doMap2ObjectByJson(classOrigin, map);
		Object objectDestination = classDestination.newInstance();
		CopyUtil.copyProperties(objectDestination, objectOrigin);
		return objectDestination;
	}

	/**
	 * 
	 * map2object(map的key是json格式)
	 * 
	 * @param classObj
	 * @param map
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Object doMap2ObjectByJson(Class classObj, Map<String, ?> map)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Object object = classObj.newInstance();
		Set<String> keyList = map.keySet();
		for (String key : keyList) {
			Object value = map.get(key);
			// log.trace("key=" + key);
			// log.trace("value=" + value);
			// Method setter = findSetterMethodByField(classObj,
			// key.toLowerCase());
			Method setter = this.findSetterMethodByColumn(classObj, key);
			if (setter == null) {
				log.warn("找不到方法,key=" + key);
				// throw new NullPointerException("找不到方法,方法名=" + key);
				continue;
			} else {
				// 执行方法
				try {
					setter.invoke(object, new Object[]{value});
				} catch (Exception e) {
					log.warn("报错,方法名=" + setter.getName());
					// e.printStackTrace();
					continue;
				}
			}
		}
		return object;
	}
}
