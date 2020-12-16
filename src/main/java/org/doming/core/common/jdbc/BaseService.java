package org.doming.core.common.jdbc;

import c.a.util.core.log.LogThreadLocal;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.DaoBase;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 服务类基类
 *
 * @Author: Dongming
 * @Date: 2020-04-08 14:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BaseService {

	protected DaoBase dao = CurrentTransaction.getDao();
	private DaoBase dao() {
		if (dao == null) {
			dao = CurrentTransaction.getDao();
		}
		return dao;
	}

	/**
	 * 获取 集合列表  List<Map<String, Object>>
	 *
	 * @param sql           执行sql语句
	 * @param parameterList 执行参数
	 * @return 集合列表
	 */
	protected List<Map<String, Object>> findMapList(Object sql, List<Object> parameterList) throws SQLException {
		LogThreadLocal.setLog(true, this.getClass().getSimpleName());
		return dao().findMapList(sql.toString(), parameterList);
	}
	/**
	 * 获取 集合列表  List<Map<String, Object>>
	 *
	 * @param sql 执行sql语句
	 * @return 集合列表
	 */
	protected List<Map<String, Object>> findMapList(Object sql) throws SQLException {
		return findMapList(sql, null);
	}
	/**
	 * 获取sql执行结果 集合 Map<String, Object>
	 *
	 * @param sql           执行sql语句
	 * @param parameterList 执行参数
	 * @return 集合
	 */
	protected Map<String, Object> findMap(Object sql, List<Object> parameterList) throws SQLException {
		LogThreadLocal.setLog(true, this.getClass().getSimpleName());
		return dao().findMap(sql.toString(), parameterList);
	}

	/**
	 * 执行sql语句
	 *
	 * @param sql           执行sql语句
	 * @param parameterList 执行参数
	 * @return 如果是修改，回传修改条数
	 */
	protected int execute(Object sql, List<Object> parameterList) throws SQLException {
		LogThreadLocal.setLog(true, this.getClass().getSimpleName());
		return dao().execute(sql.toString(), parameterList);
	}
	/**
	 * 获取sql执行结果 某个字段的值
	 *
	 * @param key           字段
	 * @param sql           执行sql语句
	 * @param parameterList 执行参数
	 * @return 集合
	 */
	protected String findString(String key, Object sql, List<Object> parameterList) throws SQLException {
		LogThreadLocal.setLog(true, this.getClass().getSimpleName());
		return dao().findString(key, sql.toString(), parameterList);
	}

	/**
	 * 获取sql执行结果 某个字段的值
	 *
	 * @param key           字段
	 * @param sql           执行sql语句
	 * @param parameterList 执行参数
	 * @return 集合
	 */
	protected List<String> findStringList(String key, Object sql, List<Object> parameterList) throws SQLException {
		LogThreadLocal.setLog(true, this.getClass().getSimpleName());
		return dao().findStringList(key, sql.toString(), parameterList);
	}

	/**
	 * 查找对象
	 * @param classObj 类clazz
	 * @param sql 执行sql语句
	 * @param parameterList 执行参数
	 * @param <T> 对象类型
	 * @return 对象
	 */
	protected <T> T findObject(Class<T> classObj, String sql, List<Object> parameterList) throws Exception {
		LogThreadLocal.setLog(true, this.getClass().getSimpleName());
		return dao().findObject(classObj, sql, parameterList);
	}
}
