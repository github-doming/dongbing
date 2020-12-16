package c.a.tools.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.tools.crud.action.TransactionAction;

/**
 * 
 * 
 * 反射工具
 * 
 * 
 * 
 */
public class ReflectClassAyUtil {
	protected Logger log = LogManager.getLogger(ReflectClassAyUtil.class);
	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型; 比如实体类User;
	 * 
	 * @param <T>
	 * @param c_Class
	 * @return
	 */
	public <T> Class<T> findSuperClassGenricType(final Class c_Class) {
		return findSuperClassGenricType(c_Class, 0);
	}

	/**
	 * 获得主键类型; 比如Longr;
	 * 
	 * @param <T>
	 * @param c_Class
	 * @return
	 */
	public <T> Class<T> findSuperClassPK(final Class c_Class) {
		return findSuperClassGenricType(c_Class, 1);
	}

	/**
	 * 通过反射, 获得Class定义中声明的父类的泛型参数的类型
	 * 
	 * @param c_Class
	 * @param index
	 * @return
	 */
	public Class findSuperClassGenricType(final Class c_Class, final int index) {
		Type type = c_Class.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
		} else {
			// 父类没有参数
			return Object.class;
		}
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type[] params = parameterizedType.getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (params[index] instanceof Class) {
		} else {
			// 不是类
			return Object.class;
		}
		Class return_Class = (Class) params[index];
		return return_Class;
	}

	/**
	 * 获取类名，不包括包名
	 * 
	 * @param c_Class
	 * @return
	 */
	public String findClassName(Class c_Class) {
		return findClassName_e2(c_Class);
	}

	/**
	 * 获取类名，不包括包名
	 * 
	 * @param c_Class
	 * @return
	 */
	public String findClassName_e2(Class c_Class) {
		return c_Class.getSimpleName();
	}

	/**
	 * 获取类名，不包括包名
	 * 
	 * @param c_Class
	 * @return
	 */
	public String findClassName_e1(Class c_Class) {
		String package_name = c_Class.getPackage().getName() + ".";
		return c_Class.getName().replaceAll(package_name, "");
	}

	/**
	 * 
	 * 声明的域;
	 * 
	 * 直接返回;
	 * 
	 * @param c_Class
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 */
	public Field findDeclaredField_v2(Class c_Class, String fieldName)
			throws SecurityException {
		Field cField = null;
		try {
			cField = c_Class.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			// e.printStackTrace();
			// java.lang.NoSuchFieldException
			log.trace("实体类找不到Field ,fieldName=" + fieldName);
			return null;
		}
		return cField;
	}

	/**
	 * 
	 * 声明的域
	 * 
	 * 如果找不到的话，直接返回null
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param c_Class
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 */
	public Field findDeclaredField_v1(Class c_Class, String fieldName)
			throws SecurityException {
		Field cField = null;
		try {
			cField = c_Class.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			// java.lang.NoSuchFieldException
			return null;
		}
		if (cField == null) {
			log.trace("实体类找不到Field ,fieldName=" + fieldName);
			// throw new RuntimeException("实体类找不到Field ,fieldName=" +fieldName);
			return null;
		} else {
			return cField;
		}
	}
}
