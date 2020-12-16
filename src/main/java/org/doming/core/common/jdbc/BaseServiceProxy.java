package org.doming.core.common.jdbc;

import org.doming.core.tools.ContainerTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 数据库操作基类代理类
 * @Author: Dongming
 * @Date: 2019-09-19 11:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BaseServiceProxy extends BaseService {

	/**
	 * 执行sql语句
	 *
	 * @param sql 执行sql语句
	 * @return 如果是修改，回传修改条数
	 */
	protected int execute(String sql) throws SQLException {
		return execute(sql, null);
	}

	/**
	 * 获取sql执行结果 某个字段的值
	 *
	 * @param sql           执行sql语句
	 * @param parameterList 执行参数
	 * @return 集合
	 */
	protected String findString(Object sql, List<Object> parameterList) throws SQLException {
		return findString("key_", sql.toString(), parameterList);
	}

	/**
	 * 获取sql执行结果 某个字段的值
	 *
	 * @param sql           执行sql语句
	 * @param parameterList 执行参数
	 * @return 集合
	 */
	protected List<String> findStringList(Object sql, List<Object> parameterList) throws SQLException {
		return findStringList("key_", sql, parameterList);
	}

	/**
	 * 将寻找到的listMap转化为Map
	 * 注：需要在找到的字段中存在<key，value>关键字
	 *
	 * @param sql           执行的sql语句
	 * @param parameterList 携带的参数
	 * @return <key，value>型map，依据sql中的字段赋值
	 * @throws SQLException 寻找失败
	 */
	protected <Key, Value> Map<Key, Value> findKVMap(Object sql, List<Object> parameterList) throws SQLException {
		List<Map<String, Object>> mapList = findMapList(sql, parameterList);
		if (ContainerTool.isEmpty(mapList)) {
			return new HashMap<>(1);
		}
		Map<Key, Value> kvMap = new HashMap<>(mapList.size());
		mapList.forEach(map -> kvMap.put((Key) map.get("key_"), (Value) map.get("value_")));
		return kvMap;
	}
	/**
	 * 将寻找到的listMap转化为Map
	 * 注：需要在找到的字段中存在<key，value>关键字
	 *
	 * @param sql 执行的sql语句
	 * @return <key，value>型map，依据sql中的字段赋值
	 * @throws SQLException 寻找失败
	 */
	protected <Key, Value> Map<Key, Value> findKVMap(Object sql) throws SQLException {
		return findKVMap(sql, null);
	}

	/**
	 * 将寻找到的listMap转化为Map <br>
	 * 多个value之间以#分隔
	 * 注：需要在找到的字段中存在<key，values>关键字
	 *
	 * @param sql           执行的sql语句
	 * @param parameterList 携带的参数
	 * @return <key，value>型map，依据sql中的字段赋值
	 * @throws SQLException 寻找失败
	 */
	protected Map<String, String> findKVsMap(Object sql, List<Object> parameterList) throws SQLException {
		return findKVsMap(sql, parameterList, "#");
	}

	/**
	 * 将寻找到的listMap转化为Map <br>
	 * 多个value之间以regex分隔
	 * 注：需要在找到的字段中存在<key，values>关键字
	 *
	 * @param sql           执行的sql语句
	 * @param parameterList 携带的参数
	 * @param regex         分隔符
	 * @return <key，value>型map，依据sql中的字段赋值
	 * @throws SQLException 寻找失败
	 */
	protected Map<String, String> findKVsMap(Object sql, List<Object> parameterList, String regex) throws SQLException {
		List<Map<String, Object>> mapList = findMapList(sql, parameterList);
		Map<String, String> kvsMap = new HashMap<>(mapList.size());
		mapList.forEach(map -> {
			String key = map.get("key_").toString();
			kvsMap.put(key, kvsMap.containsKey(key) ?
					kvsMap.get(key) + regex + map.get("value_").toString() :
					map.get("value_").toString());
		});
		return kvsMap;
	}

	/**
	 * 将寻找到的listMap转化为MapList <br>
	 * 多个value之间组成List
	 * 注：需要在找到的字段中存在<key，values>关键字
	 *
	 * @param sql           执行的sql语句
	 * @param parameterList 携带的参数
	 * @return <key，value>型map，依据sql中的字段赋值
	 * @throws SQLException 寻找失败
	 */
	protected Map<String, List<String>> findKVsMapList(Object sql, List<Object> parameterList) throws SQLException {
		List<Map<String, Object>> mapList = findMapList(sql, parameterList);
		Map<String, List<String>> kvsMapList = new HashMap<>(mapList.size());
		mapList.forEach(map -> {
			String key = map.get("key_").toString();
			if (kvsMapList.containsKey(key)) {
				kvsMapList.get(key).add(map.get("value_").toString());
			} else {
				List<String> list = new ArrayList<>();
				list.add(map.get("value_").toString());
				kvsMapList.put(key, list);
			}
		});
		return kvsMapList;
	}
	/**
	 * 将寻找到的listMap转化为KeyMap <br>
	 *
	 * @param sql           执行的sql语句
	 * @param parameterList 携带的参数
	 * @param key           外层map的键
	 * @return <key，value>型map，依据sql中的字段赋值
	 * @throws SQLException 寻找失败
	 */
	protected Map<String, Map<String, Object>> findKeyMap(Object sql, List<Object> parameterList, String key)
			throws SQLException {
		List<Map<String, Object>> mapList = findMapList(sql, parameterList);
		Map<String, Map<String, Object>> keyMap = new HashMap<>(mapList.size());
		mapList.forEach(map -> {
			String obj = map.remove(key).toString();
			keyMap.put(obj,map);
		});
		return keyMap;
	}	/**
	 * 将寻找到的listMap转化为 KeyMaps <br>
	 * 多个value之间组成List
	 *
	 * @param sql           执行的sql语句
	 * @param parameterList 携带的参数
	 * @param key           外层map的键
	 * @return <key，value>型map，依据sql中的字段赋值
	 * @throws SQLException 寻找失败
	 */
	protected Map<String, List<Map<String, Object>>> findKeyMaps(Object sql, List<Object> parameterList, String key)
			throws SQLException {
		List<Map<String, Object>> mapList = findMapList(sql, parameterList);
		Map<String, List<Map<String, Object>>> keyMaps = new HashMap<>(mapList.size());
		mapList.forEach(map -> {
			String obj = map.remove(key).toString();
			if (keyMaps.containsKey(obj)){
				keyMaps.get(obj).add(map);
			}else {
				List<Map<String, Object>> maps = new ArrayList<>();
				maps.add(map);
				keyMaps.put(obj,maps);
			}
		});
		return keyMaps;
	}
}
