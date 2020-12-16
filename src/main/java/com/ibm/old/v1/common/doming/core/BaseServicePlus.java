package com.ibm.old.v1.common.doming.core;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.x.platform.root.common.service.BaseService;
import org.doming.core.common.DatabaseBaseMethod;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
/**
 * @Description: 数据库操作基类扩张类BaseService
 * @Author: Dongming
 * @Date: 2018-12-04 14:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BaseServicePlus extends BaseService implements DatabaseBaseMethod {

	int transaction = Connection.TRANSACTION_REPEATABLE_READ;

	/**
	 * 将寻找到的listMap转化为Map
	 * 注：需要在找到的字段中存在<key，value>关键字
	 *
	 * @param sql           执行的sql语句
	 * @param parameterList 携带的参数
	 * @return <key，value>型map，依据sql中的字段赋值
	 * @throws SQLException 寻找失败
	 */
	protected Map<Object, Object> findKVMap(String sql, List<Object> parameterList) throws SQLException {
		List<Map<String, Object>> mapList = super.dao.findMapList(sql, parameterList);
		Map<Object, Object> kvMap = new HashMap<>(mapList.size());
		mapList.forEach(map -> kvMap.put(map.get("key_"), map.get("value_")));
		return kvMap;

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
	protected Map<String, String> findKVsMap(String sql, List<Object> parameterList) throws SQLException {
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
	protected Map<String, String> findKVsMap(String sql, List<Object> parameterList, String regex) throws SQLException {
		List<Map<String, Object>> mapList = super.dao.findMapList(sql, parameterList);
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
	protected Map<String, List<String>> findKVsMapList(String sql, List<Object> parameterList) throws SQLException {
		List<Map<String, Object>> mapList = super.dao.findMapList(sql, parameterList);
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
	 * 在任务执行的时候加入事物
	 *
	 * @param task 任务
	 * @return 任务执行结果
	 */
	public Object transaction(final Callable task) throws SQLException {
		Connection conn = beginTransaction();
		Object result = null;
		try {
			result = task.call();
		} catch (Exception e) {
			rollTransaction(conn);
		} finally {
			endTransaction(conn);
		}
		return result;
	}

	/**
	 * 在任务执行的时候加入事物
	 *
	 * @param task  任务
	 * @return 任务执行结果
	 */
	public Object transactionSyn(final Callable task) throws SQLException {
		return transactionSyn(task,this.getClass());
	}

	/**
	 * 在任务执行的时候加入事物
	 *
	 * @param task  任务
	 * @param clazz 执行对象
	 * @return 任务执行结果
	 */
	public Object transactionSyn(final Callable task, final Class<?> clazz) throws SQLException {
		synchronized (clazz) {
			Connection conn = beginTransaction();
			Object result = null;
			try {
				result = task.call();
			} catch (Exception e) {
				rollTransaction(conn);
			} finally {
				endTransaction(conn);
			}
			return result;
		}
	}

	/**
	 * 开始事物
	 *
	 * @return 数据库链接
	 */
	private Connection beginTransaction() throws SQLException {
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		beginTransaction(conn, transaction);
		return conn;
	}
}
