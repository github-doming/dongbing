package c.a.util.core.collections;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 集合工具类;
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class CollectionUtil {
	/**
	 * 全部删除
	 * 
	 * @param set
	 */
	@SuppressWarnings("rawtypes")
	public static void doSetDeleteAll(Set set) {
		set.clear();
	}
	/**
	 * 全部删除
	 * 
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	public static void doListDeleteAll(List list) {
		list.clear();
	}
	/**
	 * 转换器byte2string
	 * 
	 * @param obj
	 * @return
	 */
	public static String doByteObject2String(Object obj) {
		byte[] byteArray = (byte[]) obj;
		String str = "";
		try {
			// str = new String(b, BaseConfig.CharacterEncoding_gbk);
			str = new String(byteArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 
	 * byte数组到16进制字符串
	 * 
	 * @Description:
	 * @Title: doByteArray2StringHex
	 * @param data
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public static String doByteArray2StringHex(byte[] data) {
		if (data == null || data.length <= 1)
			return "0x";
		if (data.length > 200000)
			return "0x";
		StringBuilder stringBuffer = new StringBuilder();
		int buf[] = new int[data.length];
		// byte数组转化成十进制
		for (int k = 0; k < data.length; k++) {
			buf[k] = data[k] < 0 ? (data[k] + 256) : (data[k]);
		}
		// 十进制转化成十六进制
		for (int k = 0; k < buf.length; k++) {
			if (buf[k] < 16)
				stringBuffer.append("0" + Integer.toHexString(buf[k]));
			else
				stringBuffer.append(Integer.toHexString(buf[k]));
		}
		return "0x" + stringBuffer.toString().toUpperCase();
	}
	/**
	 * 转换器bytes2String
	 * 
	 * @Description:
	 * @Title: doByteArray2String
	 * @param arrayByte
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public static String doByteArray2String(byte[] byteArray) {
		String str = new String(byteArray);
		return str;
	}
	/**
	 * 转换器string2Byte;
	 * 
	 * @Description:
	 * @Title: doString2byteArray
	 * @param str
	 * @return 参数说明
	 * @return byte[] 返回类型
	 * @throws
	 */
	public static byte[] doString2byteArray(String str) {
		byte[] byteArray = str.getBytes();
		return byteArray;
	}
	/**
	 * stringObject2bytes
	 * 
	 * @Description:
	 * @Title: doStringObject2byteArray
	 * @param obj
	 * @return 参数说明
	 * @return byte[] 返回类型
	 * @throws
	 */
	public static byte[] doStringObject2byteArray(Object obj) {
		String str = (String) obj;
		byte[] byteArray = str.getBytes();
		return byteArray;
	}
	/**
	 * 转换器SetToArray
	 * 
	 * @Description:
	 * @Title: doSet2Array
	 * @param list
	 * @return 参数说明
	 * @return String[] 返回类型
	 * @throws
	 */
	public static String[] doSet2Array(Set<String> list) {
		if (list == null) {
			try {
				throw new Exception("set不能为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String[] strArray = null;
		if (list.size() >= 1) {
			strArray = new String[list.size()];
			list.toArray(strArray);
		}
		return strArray;
	}
	/**
	 * 
	 * 转换器ListToArray
	 * 
	 * @param list
	 * @return
	 */
	public static String[] doList2Array(List<String> list) {
		if (list == null) {
			try {
				throw new Exception("list不能为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String[] array = null;
		if (list.size() >= 1) {
			array = new String[list.size()];
			list.toArray(array);
		}
		return array;
	}
	/**
	 * 
	 * 转换器array2list
	 * 
	 * @param array
	 * @return
	 */
	public static List<String> doArray2List(String... array) {
		if (array == null) {
			try {
				throw new Exception("array不能为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<String> list = new ArrayList<String>();
		for (String str : array) {
			list.add(str);
		}
		return list;
	}
	/**
	 * 
	 * 去掉重复记录
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> doListUnion(List<String> list) {
		if (list == null) {
			try {
				throw new Exception("list不能为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return doSet2List(doList2Set(list));
	}
	/**
	 * 
	 * 转换器ListToSet 去掉重复记录
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static Set<String> doList2Set(List<String> list) {
		if (list == null) {
			try {
				throw new Exception("list不能为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Set<String> set = new LinkedHashSet(list);
		return set;
	}
	/**
	 * 
	 * 转换器SetToList
	 * 
	 * @param set
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static List<String> doSet2List(Set<String> set) {
		if (set == null) {
			try {
				throw new Exception("set不能为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<String> list = new ArrayList(set);
		return list;
	}
	/**
	 * 取出MAP中所有的KEY
	 * 
	 * @param variables
	 * @return
	 */
	public static List<String> doMapKey2List(Map<String, Object> variables) {
		if (variables == null) {
			try {
				throw new Exception("variables不能为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<String> list = new ArrayList<String>();
		for (String key : variables.keySet()) {
			list.add(key);
		}
		return list;
	}
	/**
	 * 取出MAP中所有的VALUE
	 * 
	 * @Description:
	 * @Title: doMapValue2List
	 * @param variableMap
	 * @return 参数说明
	 * @return List<String> 返回类型
	 * @throws
	 */
	public static List<String> doMapValue2List(Map<String, Object> variableMap) {
		if (variableMap == null) {
			try {
				throw new Exception("variable不能为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<String> list = new ArrayList<String>();
		for (Object value : variableMap.values()) {
			list.add(value.toString());
		}
		return list;
	}
}
