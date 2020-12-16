package c.a.util.core.enums;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
public class EnumUtil {
	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 转换成为枚举
	 * 
	 * @Description
	 * @Title findEnumItem
	 * @param classInput
	 * @param inputObject
	 * @return 参数说明
	 * @return T 返回类型
	 * @throws
	 */
	public <T> T findEnumItem(Class<T> classInput, Object inputObject) {
		T returnT = null;
		if (classInput.isEnum()) {
			T[] tArray = classInput.getEnumConstants();
			String inputTempStr = inputObject.toString();
			for (T t : tArray) {
				Enum<?> enumTemp = (Enum<?>) t;
				String code = findInvokeValue(t, "getCode");
				if (code == null) {
					// 位置
					code = String.valueOf(enumTemp.ordinal());
				}
				if (inputTempStr.equals(code)) {
					returnT = t;
					break;
				}
			}
		}
		return returnT;
	}
	/**
	 * 获取枚举的文本内容
	 * 
	 * @Title: getMsgCn
	 * @Description:
	 * @param classInput
	 * @param code
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public Object findMsg(Class<?> classInput, Object code) {
		return findMap(classInput, "getMsg").get(code);
	}
	/**
	 * 获取枚举的文本内容
	 * 
	 * @Title: getMsgCn
	 * @Description:
	 * @param classInput
	 * @param code
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public Object findMsgCn(Class<?> classInput, Object code) {
		//log.trace("code="+code);
		return findMap(classInput, "getMsgCn").get(code);
	}
	public <T> Map<String, Object> findMapForMsgCn(Class<T> ref) {
		return this.findMap(ref, "getMsgCn");
	}
	public <T> Object find(Class<T> refClass, String methodName, Object code) {

		return findMap(refClass, methodName).get(code);
	}
	public <T> Map<String, Object> findMap(Class<T> refClass, String methodName) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();

		if (refClass.isEnum()) {
			T[] tArray = refClass.getEnumConstants();
			for (T t : tArray) {
				String msg = findInvokeValue(t, methodName);
				Enum<?> enumTemp = (Enum<?>) t;
				if (msg == null) {
					msg = enumTemp.name();
				}
				String code = findInvokeValue(t, "getCode");
				if (code == null) {
					// 位置
					code = String.valueOf(enumTemp.ordinal());
				}
				map.put(code, msg);
			}
		}

		return map;
	}
	public <T> String findInvokeValue(T t, String methodName) {
		Method method = MethodUtils.getAccessibleMethod(t.getClass(),
				methodName);
		if (null == method) {
			return null;
		}
		try {
			String value = method.invoke(t).toString();
			return value;
		} catch (Exception e) {
			return null;
		}
	}
}
