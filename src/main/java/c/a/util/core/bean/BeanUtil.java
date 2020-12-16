package c.a.util.core.bean;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.sys_bytes.t.entity.SysBytesT;
import c.a.util.core.copy.CopyConverter;
import c.a.util.core.date.DateThreadLocal;
import c.a.util.core.date.DateUtil;
import c.a.util.core.json.JsonUtil;
import c.a.util.core.string.StringUtil;
/**
 * 
 * bean工具类
 * 
 * @Description:
 * @ClassName: BeanUtil
 * @date 2017年3月28日 下午6:18:58
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class BeanUtil{
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 通过注解,使用Annotation进行转换
	 * 
	 * @Title: doMap2BeanByAnnotation
	 * @Description:
	 *
	 * 				参数说明
	 * @param listMap
	 * @param classObject
	 * @return
	 * @throws Exception
	 *             返回类型 List<Object>
	 */
	public List<Object> doMap2BeanByAnnotation(List<Map<String, Object>> listMap, Class<?> classObject)
			throws Exception {
		List<Object> listObject = new ArrayList<Object>();
		for (Map<String, Object> map : listMap) {
			Object object = classObject.newInstance();
			object = this.doMap2BeanByAnnotation(map, classObject);
			listObject.add(object);
		}
		return listObject;
	}
	/**
	 * 通过注解,使用Annotation进行转换
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @param classObject
	 * @return
	 * @throws Exception
	 *             返回类型 Object
	 */
	public Object doMap2BeanByAnnotation(Map<String, Object> map, Class<?> classObject) throws Exception {
		if (map == null) {
			return null;
		}
		Object object = classObject.newInstance();
		Field[] fieldArray = object.getClass().getDeclaredFields();
		for (Field field : fieldArray) {
			Column column = field.getAnnotation(Column.class);
			String columnName = null;
			if (column != null) {
				columnName = column.name();
			}
			int ModifiersInt = field.getModifiers();
			if (Modifier.isStatic(ModifiersInt) || Modifier.isFinal(ModifiersInt)) {
				continue;
			}
			field.setAccessible(true);
			field.set(object, map.get(columnName));
		}
		return object;
	}
	public Object doMapStr2Bean(Map<String, String> map, Class classObject) throws Exception {
		return doMapStr2BeanByBeanutils(map, classObject);
	}
	public Map<String, String> doBean2MapStr(Object objectSource)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return doBean2MapStrByIntrospector(objectSource);
	}
	/**
	 * 去掉表名并转成bean
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param tableName
	 * @param inputMap
	 * @param classObject
	 * @return
	 * @throws Exception
	 *             返回类型 Object
	 */
	public Object doMap2BeanByTableName(String tableName, Map<String, Object> inputMap, Class<?> classObject)
			throws Exception {
		Map<String, Object> map = this.doMap2MapByTableName(tableName, inputMap);
		return this.doMap2Bean(map, classObject);
	}
	/**
	 * 去掉表名
	 * 
	 * @Title: doMap2MapByTableName
	 * @Description:
	 *
	 * 				参数说明
	 * @param tableName
	 * @param map
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,Object>
	 */
	public Map<String, Object> doMap2MapByTableName(String tableName, Map<String, Object> map) throws Exception {
		Set<String> keyList = map.keySet();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		for (String key : keyList) {
			String[] inputKeyArray = key.split("\\.");
			if (inputKeyArray.length == 2) {
				String inputTableName = inputKeyArray[0];
				String returnKey = inputKeyArray[1];
				if (inputTableName.equals(tableName)) {
					returnMap.put(returnKey, map.get(key));
				}
			}
		}
		return returnMap;
	}
	/**
	 * 转成bean
	 * 
	 * @Title: doMap2Bean
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @param classObject
	 * @return
	 * @throws Exception
	 *             返回类型 Object
	 */
	public Object doMap2Bean(Map<String, Object> map, Class<?> classObject) throws Exception {
		return this.doMap2BeanByBeanutils(map, classObject);
	}
	/**
	 * @deprecated
	 * @Title: doMap2Bean
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @param objectSource
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *             返回类型 void
	 */
	public void doMap2Bean(Map<String, Object> map, Object objectSource)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		doMap2BeanByIntrospector(map, objectSource);
	}
	public Map<String, Object> doBean2Map(Object objectSource)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return doBean2MapByIntrospector(objectSource);
	}
	/**
	 * Map --> Bean;
	 * 
	 * 利用Introspector,PropertyDescriptor实现 Map --> Bean;
	 * 
	 * @Title: doMapStr2BeanByIntrospector
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @param objectSource
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *             返回类型 void
	 */
	public void doMapStr2BeanByIntrospector(Map<String, String> map, Object objectSource)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(objectSource.getClass());
		PropertyDescriptor[] propertyDescriptorArray = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptorArray) {
			String name = property.getName();
			// log.trace("key="+key);
			if (map.containsKey(name)) {
				String value = map.get(name);
				// 得到property对应的setter方法
				Method setterMethod = property.getWriteMethod();
				if (setterMethod != null) {
					setterMethod.invoke(objectSource, value);
				}
			}
		}
	}
	/**
	 * Bean --> Map;
	 * 
	 * 利用Introspector和PropertyDescriptor 将Bean --> Map;
	 * 
	 * @Title: doBean2MapStrByIntrospector
	 * @Description:
	 *
	 * 				参数说明
	 * @param objectSource
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *             返回类型 Map<String,String>
	 */
	public Map<String, String> doBean2MapStrByIntrospector(Object objectSource)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (objectSource == null) {
			return null;
		}
		DateUtil dateUtil = DateThreadLocal.findThreadLocal().get();
		Map<String, String> map = new HashMap<String, String>();
		BeanInfo beanInfo = Introspector.getBeanInfo(objectSource.getClass());
		PropertyDescriptor[] propertyDescriptorArray = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
			String name = propertyDescriptor.getName();
			// log.trace("name=" + name);
			// if (name .compareToIgnoreCase("class") == 0) {
			// continue;
			// }
			// 过滤class属性
			if (name.equals("class")) {
			} else {
				// 得到property对应的getter方法
				Method getterMethod = propertyDescriptor.getReadMethod();
				Object value = null;
				if (getterMethod != null) {
					value = getterMethod.invoke(objectSource);
				}
				if (value != null) {
					if (value instanceof Date) {
						Date date = (Date) value;
						String dateStr = dateUtil.doUtilDate2String(date);
						map.put(name, dateStr);
					} else {
						map.put(name, value.toString());
					}
				}
			}
		}
		return map;
	}
	/**
	 * Map --> Bean;
	 * 
	 * 利用Introspector,PropertyDescriptor实现 Map --> Bean;
	 * 
	 * @Title: doMap2BeanByIntrospector
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @param objectSource
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *             返回类型 void
	 */
	public void doMap2BeanByIntrospector(Map<String, Object> map, Object objectSource)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(objectSource.getClass());
		PropertyDescriptor[] propertyDescriptorArray = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptorArray) {
			String name = property.getName();
			//System.out.println("name =" + name);
			//log.trace("name =" + name);
			if (map.containsKey(name)) {
				Object value = map.get(name);
				// 得到property对应的setter方法
				Method setterMethod = property.getWriteMethod();
				if (setterMethod != null) {
					setterMethod.invoke(objectSource, value);
				}
			}
		}
	}
	/**
	 * Bean --> Map;
	 * 
	 * 利用Introspector和PropertyDescriptor 将Bean --> Map;
	 * 
	 * @Title: doBean2MapByIntrospector
	 * @Description:
	 *
	 * 				参数说明
	 * @param objectSource
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *             返回类型 Map<String,Object>
	 */
	public Map<String, Object> doBean2MapByIntrospector(Object objectSource)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (objectSource == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(objectSource.getClass());
		PropertyDescriptor[] propertyDescriptorArray = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
			String name = propertyDescriptor.getName();
			// log.trace("name=" + name);
			// if (name .compareToIgnoreCase("class") == 0) {
			// continue;
			// }
			// 过滤class属性
			if (name.equals("class")) {
			} else {
				// 得到property对应的getter方法
				Method getterMethod = propertyDescriptor.getReadMethod();
				Object value = null;
				if (getterMethod != null) {
					value = getterMethod.invoke(objectSource);
				}
				map.put(name, value);
			}
		}
		return map;
	}
	/**
	 * 使用reflect进行转换
	 * 
	 * @Title: doMap2BeanByReflect
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @param classObject
	 * @return
	 * @throws Exception
	 *             返回类型 Object
	 */
	public Object doMapStr2BeanByReflect(Map<String, String> map, Class<?> classObject) throws Exception {
		if (map == null) {
			return null;
		}
		Object object = classObject.newInstance();
		Field[] fieldArray = object.getClass().getDeclaredFields();
		for (Field field : fieldArray) {
			int ModifiersInt = field.getModifiers();
			if (Modifier.isStatic(ModifiersInt) || Modifier.isFinal(ModifiersInt)) {
				continue;
			}
			field.setAccessible(true);
			field.set(object, map.get(field.getName()));
		}
		return object;
	}
	/**
	 * 使用reflect进行转换
	 * 
	 * @Title: doMap2BeanByReflect
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @param classObject
	 * @return
	 * @throws Exception
	 *             返回类型 Object
	 */
	public Object doMap2BeanByReflect(Map<String, Object> map, Class<?> classObject) throws Exception {
		if (map == null) {
			return null;
		}
		Object object = classObject.newInstance();
		Field[] fieldArray = object.getClass().getDeclaredFields();
		for (Field field : fieldArray) {
			int ModifiersInt = field.getModifiers();
			if (Modifier.isStatic(ModifiersInt) || Modifier.isFinal(ModifiersInt)) {
				continue;
			}
			field.setAccessible(true);
			field.set(object, map.get(field.getName()));
		}
		return object;
	}
	/**
	 * 使用reflect进行转换
	 * 
	 * @Title: doBean2MapByReflect
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,Object>
	 */
	public Map<String, Object> doBean2MapByReflect(Object object) throws Exception {
		if (object == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] declaredFieldArray = object.getClass().getDeclaredFields();
		for (Field declaredField : declaredFieldArray) {
			declaredField.setAccessible(true);
			map.put(declaredField.getName(), declaredField.get(object));
		}
		return map;
	}
	/**
	 * 使用org.apache.commons.beanutils进行转换
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @param classObject
	 * @return
	 * @throws Exception
	 *             返回类型 Object
	 */
	public Object doMapStr2BeanByBeanutils(Map<String, String> map, Class<?> classObject) throws Exception {
		if (map == null) {
			return null;
		}
		Object obj = classObject.newInstance();
		// 注册一个日期转换器
		CopyConverter typeConverter = new CopyConverter();
		// 注册类型
		ConvertUtils.register(typeConverter, java.util.Date.class);
		ConvertUtils.register(typeConverter, String.class);
		ConvertUtils.register(typeConverter, byte[].class);
		ConvertUtils.register(typeConverter, BigDecimal.class);
		org.apache.commons.beanutils.BeanUtils.populate(obj, map);
		return obj;
	}
	/**
	 * 使用org.apache.commons.beanutils进行转换
	 * 
	 * @Title: doBean2MapByBeanutils
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @return 返回类型 Map<?,?>
	 */
	public Map<String, String> doBean2MapStrByBeanutils(Object object) {
		if (object == null) {
			return null;
		}
		return new org.apache.commons.beanutils.BeanMap(object);
	}
	/**
	 * 使用org.apache.commons.beanutils进行转换
	 * 
	 * @Title: doMap2BeanByBeanutils
	 * @Description:
	 *
	 * 				参数说明
	 * @param map
	 * @param classObject
	 * @return
	 * @throws Exception
	 *             返回类型 Object
	 */
	public Object doMap2BeanByBeanutils(Map<String, Object> map, Class<?> classObject) throws Exception {
		if (map == null) {
			return null;
		}
		Object obj = classObject.newInstance();
		org.apache.commons.beanutils.BeanUtils.populate(obj, map);
		return obj;
	}
	/**
	 * 使用org.apache.commons.beanutils进行转换
	 * 
	 * @Title: doBean2MapByBeanutils
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @return 返回类型 Map<?,?>
	 */
	public Map<?, ?> doBean2MapByBeanutils(Object object) {
		if (object == null) {
			return null;
		}
		return new org.apache.commons.beanutils.BeanMap(object);
	}
	/**
	 * 取值,空值则返回缺省值
	 * 
	 * @Title: findObjectCore
	 * @Description:
	 *
	 * 				参数说明
	 * @param objectSource
	 * @param objectDefault
	 * @param isSecurity
	 * @return 返回类型 Object
	 */
	public Object findObjectCore(Object objectSource, Object objectDefault, boolean isSecurity) {
		try {
			if (objectSource == null) {
				return objectDefault;
			} else {
				// String sourceClassName = objectSource.getClass().getName();
				String defaultClassName = objectDefault.getClass().getName();
				if (objectSource instanceof String) {
					if (StringUtil.isBlank(objectSource)) {
						return objectDefault;
					} else {
						if (objectDefault instanceof String) {
							// 安全字符过滤
							if (isSecurity) {
								objectSource = StringUtil.requestEncode(objectSource.toString());
							}
							return objectSource;
						}
						if (objectDefault instanceof Integer) {
							// log.trace("sourceClassName="+sourceClassName);
							// log.trace("defaultClassName="+defaultClassName);
							// log.trace("objectSource="+objectSource);
							// log.trace("objectDefault="+objectDefault);
							return Integer.parseInt(objectSource.toString());
						}
						if (objectDefault instanceof Long) {
							return Long.parseLong(objectSource.toString());
						}
						if (objectDefault instanceof Double) {
							return Double.parseDouble(objectSource.toString());
						}
						if (objectDefault instanceof Boolean) {
							return Boolean.parseBoolean(objectSource.toString());
						}
						throw new RuntimeException("类型有错=" + defaultClassName);
					}
				}
				if (objectSource instanceof net.sf.json.JSONNull) {
					return objectDefault;
				}
				if (objectSource instanceof BigDecimal) {
					return objectSource.toString();
				}
				return objectSource;
			}
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			return objectDefault;
		}
	}
	public Object findObject(Object objectSource, Object objectDefault) {
		return findObjectCore(objectSource, objectDefault, false);
	}
	/**
	 * 取值,空值则返回缺省值;
	 * 
	 * 安全过滤;
	 * 
	 * @Description @Title findObjectSecurity @param objectSource @param
	 *              objectDefault @return 参数说明 @return Object 返回类型 @throws
	 */
	public Object findObjectSecurity(Object objectSource, Object objectDefault) {
		return findObjectCore(objectSource, objectDefault, true);
	}
	/**
	 * 取值,空值则返回缺省值;
	 * 
	 * 
	 * 
	 * @Description @Title find @param objectSource @param strDefault @return
	 *              参数说明 @return String 返回类型 @throws
	 */
	public String find(Object objectSource, String strDefault) {
		return (String) findObject(objectSource, strDefault);
	}
	/**
	 * 取值,空值则返回缺省值;
	 * 
	 * 安全过滤;
	 * 
	 * @Description @Title findSecurity @param objectSource @param
	 *              strDefault @return 参数说明 @return String 返回类型 @throws
	 */
	public String findSecurity(Object objectSource, String strDefault) {
		return (String) findObjectSecurity(objectSource, strDefault);
	}
	public boolean find(Boolean objectSource) {
		if (objectSource == null) {
			return false;
		} else {
			return objectSource.booleanValue();
		}
	}
	public Boolean find(Object objectSource, Boolean defaultBoolean) {
		return (Boolean) findObject(objectSource, defaultBoolean);
	}
	public Integer find(Object objectSource, Integer intDefault) {
		return (Integer) findObject(objectSource, intDefault);
	}
	public Long find(Object objectSource, Long longDefault) {
		return (Long) findObject(objectSource, longDefault);
	}
	public Double find(Object objectSource, Double doubleDefault) {
		return (Double) findObject(objectSource, doubleDefault);
	}
	/**
	 * 取值,空值则返回缺省值;
	 * 
	 * @Title: find
	 * @Description:
	 *
	 * 				参数说明
	 * @param request
	 * @param key
	 * @param strDefault
	 * @return 返回类型 String
	 */
	public String find(HttpServletRequest request, String key, String strDefault) {
		Object objectSource = request.getParameter(key);
		return find(objectSource, strDefault);
	}
	/**
	 * 取值,空值则返回缺省值;
	 * 
	 * 安全过滤;
	 * 
	 */
	public String findSecurity(HttpServletRequest request, String key, String strDefault) {
		Object objectSource = request.getParameter(key);
		return findSecurity(objectSource, strDefault);
	}
	public boolean findBoolean(HttpServletRequest request, String key) {
		Boolean objectSource = Boolean.valueOf(request.getParameter(key));
		return find(objectSource);
	}
	public boolean find(HttpServletRequest request, String key, Boolean defaultBoolean) {
		Object objectSource = request.getParameter(key);
		return find(objectSource, defaultBoolean);
	}
	public Integer find(HttpServletRequest request, String key, Integer intDefault) {
		Object objectSource = request.getParameter(key);
		return find(objectSource, intDefault);
	}
	public Long find(HttpServletRequest request, String key, Long longDefault) {
		Object objectSource = request.getParameter(key);
		return find(objectSource, longDefault);
	}
	public Double find(HttpServletRequest request, String key, Double doubleDefault) {
		Object objectSource = request.getParameter(key);
		return find(objectSource, doubleDefault);
	}
}
