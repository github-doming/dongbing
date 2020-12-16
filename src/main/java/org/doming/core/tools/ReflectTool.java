package org.doming.core.tools;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-02-14 10:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class ReflectTool {


	public static void set(Object obj, String methodName, Object value) {
		set(obj,methodName,String.class,value.toString());
	}


	public static void set(Object obj, String methodName, Class clazz, Object value) {
		try {
			Method method = obj.getClass().getDeclaredMethod(methodName,clazz);
			method.invoke(obj,value);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
