package org.doming.core.tools;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.*;
/**
 * @Description: 集合工具类
 * @Author: Dongming
 * @Date: 2018-08-18 11:43
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class ContainerTool {
	public static final int CAPACITY_THREAD = 1000;

	/**
	 * 为空容器
	 *
	 * @param obj 集合
	 * @return 空true
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof Collection) {
			Collection<?> collection = (Collection<?>) obj;
			if (collection.isEmpty()) {
				return true;
			}
			for (Object entity : collection) {
				if (StringTool.notEmpty(entity)) {
					return false;
				}
			}
			return true;
		}
		if (obj instanceof Map) {
			Map<?,?> map = (Map<?,?>) obj;
			return map.isEmpty();
		}
		return StringTool.isEmpty(obj.toString());
	}

	/**
	 * 非空集合
	 *
	 * @param obj 集合
	 * @return 非空true
	 */
	public static boolean notEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 为空数组
	 *
	 * @param objs 数组
	 * @return 空-true
	 */
	public static boolean isEmpty(Object[] objs) {
		if (objs == null || objs.length == 0) {
			return true;
		}
		for (Object obj : objs) {
			if (isEmpty(obj)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 为空数组
	 *
	 * @param objs 数组
	 * @return 空-true
	 */
	public static boolean isEmpty(Object[][] objs) {
		if (objs == null || objs.length == 0) {
			return true;
		}
		for (Object[] obj : objs) {
			if (isEmpty(obj)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 非空集合
	 *
	 * @param arr 数组
	 * @return 非空true
	 */
	public static boolean notEmpty(Object[] arr) {
		return !isEmpty(arr);
	}

	/**
	 * map集合中是否包含key键
	 *
	 * @param map map集合
	 * @param key key键
	 * @return 包含true
	 */
	public static boolean isContain(Map<?,?> map, String key) {
		if (notEmpty(map)) {
			return map.containsKey(key) && ContainerTool.notEmpty(map.get(key));
		}
		return false;
	}

	/**
	 * 是否包含
	 *
	 * @param arr   数组
	 * @param value 待匹配值
	 * @return 包含-true
	 */
	public static boolean isContain(Object[] arr, Object value) {
		if (isEmpty(arr)) {
			return false;
		}
		if (arr.length > 1000) {
			return binaryContain(arr, value);
		} else {
			return loopContain(arr, value);
		}
	}

	/**
	 * 是否包含
	 * 二进制比较
	 *
	 * @param arr   数组
	 * @param value 待匹配值
	 * @return 包含-true
	 */
	public static boolean binaryContain(Object[] arr, Object value) {
		if (isEmpty(arr)) {
			return false;
		}
		int result = Arrays.binarySearch(arr, value);
		return result > 0;
	}

	/**
	 * 是否包含
	 * 循环比较
	 *
	 * @param arr   数组
	 * @param value 待匹配值
	 * @return 包含-true
	 */
	public static boolean loopContain(Object[] arr, Object value) {
		if (isEmpty(arr)) {
			return false;
		}
		for (Object s : arr) {
			if (s.equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取最小val的key
	 *
	 * @param map String, Integer型map
	 * @return 最小val的key
	 */
	public static String getKey4MinVal(Map<String, Integer> map) {
		int val = Integer.MAX_VALUE;
		String key = null;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() < val) {
				key = entry.getKey();
			}
		}
		return key;
	}

	/**
	 * 获取最大val的key
	 *
	 * @param map String, Integer型map
	 * @return 最小val的key
	 */
	public static String getKey4MaxVal(Map<String, Integer> map) {
		int val = Integer.MIN_VALUE;
		String key = null;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() > val) {
				key = entry.getKey();
			}
		}
		return key;
	}

	/**
	 * 获取集合列表中某个Key的值集合
	 *
	 * @param maps 集合列表
	 * @param key  Key
	 * @return Key值集合
	 */
	public static <K,T> Set<T> getValSet4Key(List<Map<K, T>> maps, K key) {
		Set<T> valSet = new HashSet<>(maps.size());
		maps.forEach(map -> valSet.add(map.get(key)));
		return valSet;
	}
	/**
	 * 获取集合列表中某个Key的值集合
	 *
	 * @param arrays 集合列表
	 * @param key  Key
	 * @return Key值集合
	 */
	public static Set<Object> getValSet4Key(JSONArray arrays, String key) {
		Set<Object> valSet = new HashSet<>(arrays.size());
		for(int i = 0; i < arrays.size(); i++) {
			valSet.add(arrays.getJSONObject(i).get(key));
		}
		return valSet;
	}

	/**
	 * 在数组中寻找元素所在索引
	 *
	 * @param arr 元素数组
	 * @param obj 元素
	 * @return 元素所在索引
	 */
	public static int findIndex(Object[] arr, Object obj) {
		if (ContainerTool.isEmpty(arr) || ContainerTool.isEmpty(obj)) {
			throw new RuntimeException("查询的对象为空");
		}

		for (int i = 0; i < arr.length; i++) {
			if (obj.equals(arr[i])) {
				return i;
			}
		}
		throw new RuntimeException("没有查询到相同项");
	}

	/**
	 * map集合是否包含 keys
	 *
	 * @param map  map集合
	 * @param keys 包含 键
	 * @return keys为空和全包含为 true
	 */
	public static boolean containsKey(Map<?, ?> map, Object... keys) {
		if (ContainerTool.isEmpty(keys)) {
			return true;
		}
		for (Object key : keys) {
			if (!map.containsKey(key)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取 不同的元素
	 *
	 * @param baseColl  基准集合类
	 * @param checkColl 校验存在类
	 * @return 不同的元素
	 */
	public static Collection<?> getDifferent(Collection<?> baseColl, Collection<?> checkColl) {
		Map<Object, Character> map = new HashMap<>(baseColl.size());

		for (Object obj : baseColl) {
			map.put(obj, 'A');
		}
		for (Object obj : checkColl) {
			if (map.containsKey(obj)) {
				map.put(obj, 'B');
			}
		}
		Set<Object> diffSet = new HashSet<>();
		for (Map.Entry<Object, Character> entry : map.entrySet()) {
			if (entry.getValue() == 'A') {
				diffSet.add(entry.getKey());
			}
		}
		return diffSet;
	}

	/**
	 * 将listMap 转换为map
	 * @param arrays 转换前的map
	 * @param keyKey 想要转换Map的Key键
	 * @param valueKey 想要转换Map的Value键
	 * @return 转换后的Map
	 */
	public static Map<Object, Object> list2Map(JSONArray arrays, String keyKey, String valueKey) {
		Map<Object,Object> map = new HashMap<>(arrays.size());
		for(int i = 0; i < arrays.size(); i++) {
			JSONObject json = arrays.getJSONObject(i);
			map.put(json.get(keyKey),json.get(valueKey));
		}
		return map;
	}

	/**
	 * 比较数据是否相等
	 *
	 * @param map      数据存储Map
	 * @param equalKey 比较的 Map 键
	 * @param equalVal 比较的 Map 值
	 * @return 比较结果
	 */
	public static<K,V> boolean equals(Map<K,V> map, K equalKey, V equalVal) {
		return equalVal.equals(map.get(equalKey));
	}

	/**
	 * 深复制
	 * @param src 赋值原集合
	 * @param <T> 集合泛型
	 * @return 赋值后的集合
	 */
	public static <T> Collection<T> deepCopy(Collection<T> src) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		return (Collection<T>) in.readObject();
	}
}
