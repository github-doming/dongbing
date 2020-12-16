package c.a.util.core.reflect;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.core.copy.CopyUtil;
import c.a.util.core.string.StringUtil;
/**
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class ReflectNutUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 声明的域;
	 * 
	 * 往父类找;
	 * 
	 * @param classObj
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 */
	@SuppressWarnings("rawtypes")
	public Field findDeclaredField(Class classObj, String fieldName)
			throws SecurityException {
		Field field = null;
		try {
			field = classObj.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			Class classSuper = classObj.getSuperclass();
			if (classSuper != null) {
				field = this.findDeclaredField(classSuper, fieldName);
			} else {
				// log.trace("父类=null");
				return null;
			}
		}
		return field;
	}
	/**
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * 
	 * @Title: map2object
	 * @Description: map2object 然后vo转成po
	 * @param classOrigin
	 * @param classDestination
	 * @param map
	 * @return
	 * @throws Exception 参数说明
	 * @return Object 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public Object doMap2Object(Class classOrigin, Class classDestination,
			HashMap<String, Object> map) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Object objectOrigin = this.doMap2Object(classOrigin, map);
		Object objectDestination = classDestination.newInstance();
		CopyUtil.copyProperties(objectDestination, objectOrigin);
		return objectDestination;
	}
	/**
	 * 
	 * map2object
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
	public Object doMap2Object(Class classObj, Map<String, ?> map)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// 去掉表名
		map.remove("table_name");
		Object object = classObj.newInstance();
		Set<String> keyList = map.keySet();
		for (String key : keyList) {
			Object value = map.get(key);
			// log.trace("key=" + key);
			// log.trace("value=" + value);
			Method setter = findSetterMethodByField(classObj, key);
			if (setter == null) {
				log.warn("找不到方法,key=" + key);
				// throw new NullPointerException("找不到方法,方法名=" + key);
				continue;
			} else {
				// 执行方法
				try {
					setter.invoke(object, new Object[]{value});
				} catch (Exception e) {
					log.warn("Json Map2Object报错,className=" + classObj.getName());
					log.warn("Json Map2Object报错,方法名=" + setter.getName());
					log.warn("Json Map2Object报错,value=" + value);
					// e.printStackTrace();
					continue;
				}
			}
		}
		return object;
	}
	/**
	 * 获取属性对应的getter方法
	 * 
	 * @Description:
	 * @Title: findGetterMethodByColumn
	 * @param classObj
	 * @param columnName
	 * @return
	 * @throws Exception 参数说明
	 * @return Method 返回类型
	 * @throws
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Method findGetterMethodByColumn(Class classObj, String columnName)
			throws Exception {
		Method method = null;
		String methodName = "get"
				+ StringUtil.findMethodNameByColumn(columnName);
		if (isMethod(methodName, classObj)) {
			method = classObj.getDeclaredMethod(methodName);
			return method;
		}
		return method;
	}
	/**
	 * 获取属性对应的getter方法
	 * 
	 * @Description:
	 * @Title: findGetterMethodByField
	 * @param classObj
	 * @param fieldName
	 * @return
	 * @throws Exception 参数说明
	 * @return Method 返回类型
	 * @throws
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Method findGetterMethodByField(Class classObj, String fieldName)
			throws Exception {
		Method method = null;
		String methodName = "get" + StringUtil.findMethodNameByField(fieldName);
		if (isMethod(methodName, classObj)) {
			method = classObj.getDeclaredMethod(methodName);
			return method;
		}
		return method;
	}
	/**
	 * 获取属性对应的setter方法
	 * 
	 * @Description:
	 * @Title: findSetterMethodByColumn
	 * @param classObj
	 * @param culumnName
	 * @return 参数说明
	 * @return Method 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public Method findSetterMethodByColumn(Class classObj, String culumnName) {
		Method method = null;
		String methodName = "set"
				+ StringUtil.findMethodNameByColumn(culumnName);
		method = findMethod(methodName, classObj);
		return method;
	}
	/**
	 * 获取属性对应的setter方法
	 * 
	 * @Description:
	 * @Title: findSetterMethodByField
	 * @param classObj
	 * @param fieldName
	 * @return 参数说明
	 * @return Method 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public Method findSetterMethodByField(Class classObj, String fieldName) {
		Method method = null;
		String methodName = "set" + StringUtil.findMethodNameByField(fieldName);
		method = findMethod(methodName, classObj);
		return method;
	}
	/**
	 * 是否存在方法
	 * 
	 * @param methodName
	 *            方法名
	 * @param classObj
	 *            类
	 * @return
	 */
	public boolean isMethod(String methodName, Class<?> classObj) {
		boolean flag = false;
		for (Method method : classObj.getMethods()) {
			if (method.getName().equals(methodName)) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}
	/**
	 * 找出方法
	 * 
	 * @param methodName
	 *            方法名
	 * @param classObj
	 *            类
	 * @return
	 */
	public Method findMethod(String methodName, Class<?> classObj) {
		for (Method method : classObj.getMethods()) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}
}
