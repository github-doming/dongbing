package c.x.all.simple.jdbc.single_tools.nut;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import c.a.util.core.data_source.ay.JdbcDataSourceAy;
import c.a.util.core.data_source.ay.JdbcDataSourceListAy;

/**
 * 
 * 比c.x.all.simple.jdbc.single_tools.nut.SimpleJdbcTools_ax复杂
 * 
 * 
 */
public class JdbcTools_simple_cy {
	protected org.apache.logging.log4j.Logger log_custom = org.apache.logging.log4j.LogManager.getLogger("log_custom");
	protected static boolean isPrint = true;
	// /**
	// * 单例
	// */
	// private final static Object key = new Object();
	// private static SimpleTools instance = null;
	//
	// private SimpleTools() {
	// }
	//
	// public static SimpleTools findInstance() throws Exception {
	//
	// synchronized (key) {
	// if (instance == null) {
	// instance = new SimpleTools();
	//
	// }
	// return instance;
	// }
	// }
	protected Connection conn = null;
	protected Connection findConnection() {
		if (conn == null) {
			// 注入数据源
			try {
				conn = this.findDataSource().getConnection();
			} catch (Exception e) {
				String str = "jdbc error";
				if (isPrint) {
					System.err.println(str);
				}
				log_custom.error(str, e);
				e.printStackTrace();
				// throw e;
				return null;
			}
		}
		return this.conn;
	}
	protected DataSource dataSource = null;
	protected DataSource findDataSource() throws Exception {
		if (dataSource == null) {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					"config//example//spring//application_context.xml");
			dataSource = (DataSource) applicationContext
					.getBean("dataSource_spring");
			JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance()
					.findLocal();

			Connection conn = jdbcDataSource.getConnection();
		}
		return this.dataSource;
	}
	/**
	 * 
	 * path
	 */
	/**
	 * 
	 * 找出路径
	 * 
	 * @param sourceNameStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String findPath(String sourceNameStr)
			throws UnsupportedEncodingException {
		URL sourceNameUrl = this.getClass().getResource(sourceNameStr);
		String pathStr = null;
		if (sourceNameUrl == null) {
			String str = "找不到文件sourceName=" + sourceNameStr;
			if (isPrint) {
				System.out.println(str);
			}
			return null;
		} else {
		}
		pathStr = java.net.URLDecoder.decode(sourceNameUrl.getPath(), "UTF-8");
		return pathStr;
	}
	/**
	 * 断言
	 */
	/**
	 * 不能为空
	 * 
	 * @param object
	 * @param message
	 */
	public static void nullNotAssert(Object object, String message) {
		isNull_v2(object, message);
	}
	/**
	 * 
	 * 不能为空
	 * 
	 * @param object
	 * @param message
	 */
	public static void isNull_v2(Object object, String message) {
		if (object == null) {
			throw new NullPointerException(message);
		}
	}
	/**
	 * 
	 * 日期
	 */
	/**
	 * 
	 * java.util.Date转java.sql.Timestamp
	 * 
	 * @param utilDate
	 * @return
	 */
	public java.sql.Timestamp doUtilDate2Timestamp(java.util.Date utilDate) {
		java.sql.Timestamp timestamp = new java.sql.Timestamp(
				utilDate.getTime());
		return timestamp;
	}
	/**
	 * 年月日;时分秒;24小时制;英文;
	 */
	public final static String str_datetime_24h_en = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 年月日时分秒;24小时制；英文;
	 */
	public final static SimpleDateFormat dateFormat_datetime_24h_en = new SimpleDateFormat(
			str_datetime_24h_en);
	/**
	 * dateToString方法( 年月日时分秒;24小时制；英文)
	 * 
	 * 
	 * 写进数据库;
	 * 
	 * @param d
	 * @return
	 */
	public static String date2String_For_db(Date d) {
		return date2String_datetime_24h_en(d);
	}
	/**
	 * dateToString方法( 年月日时分秒;24小时制；英文)
	 * 
	 * @param d
	 * @return
	 */
	public static String date2String_datetime_24h_en(Date d) {
		if (d == null)
			return "";
		return dateFormat_datetime_24h_en.format(d);
	}
	/**
	 * 
	 * jdbc
	 */
	/**
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public void doCloseConnectionJdbc(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				String str = "jdbc close Connection error";
				if (isPrint) {
					System.err.println(str);
				}
				log_custom.error(str, e);
				e.printStackTrace();
				throw e;
			}
		}
	}
	/**
	 * 关闭PreparedStatement
	 * 
	 * @param preparedStatement
	 */
	public void doClosePreparedStatementJdbc(PreparedStatement preparedStatement)
			throws SQLException {
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				String str = " jdbc close PreparedStatement error";
				if (isPrint) {
					System.err.println(str);
				}
				log_custom.error(str, e);
				e.printStackTrace();
				throw e;
			}
		}
	}
	/**
	 * 关闭ResultSet
	 * 
	 * @param resultSet
	 */
	public void doCloseResultSetJdbc(ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception e) {
				String str = " jdbc close ResultSet error";
				if (isPrint) {
					System.err.println(str);
				}
				log_custom.error(str, e);
				e.printStackTrace();
				throw e;
			}
		}
	}
	/**
	 * 关闭ResultSet 关闭PreparedStatement
	 * 
	 * @param resultSet
	 * @param preparedStatement
	 * @throws SQLException
	 */
	public void doCloseJdbc(ResultSet resultSet,
			PreparedStatement preparedStatement) throws SQLException {
		this.doCloseResultSetJdbc(resultSet);
		this.doClosePreparedStatementJdbc(preparedStatement);
	}
	/**
	 * 设置事务隔离级别
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public void doJdbcTransactionStart(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				// 关闭自动提交
				conn.setAutoCommit(false);
				if (conn.getTransactionIsolation() != Connection.TRANSACTION_NONE) {
					conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
				}
			} catch (Exception e) {
				String str = "jdbc transaction_start error";
				if (isPrint) {
					System.err.println(str);
				}
				log_custom.error(str, e);
				e.printStackTrace();
				throw e;
			}
		}
	}
	/**
	 * 提交事务
	 * 
	 * @param conn
	 * 
	 * @throws SQLException
	 */
	public void doJdbcTransactionCommit(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.commit();
				conn.setAutoCommit(true);
			} catch (Exception e) {
				String str = "jdbc transaction_commit error";
				if (isPrint) {
					System.err.println(str);
				}
				log_custom.error(str, e);
				e.printStackTrace();
				throw e;
			}
		}
	}
	/**
	 * 回滚事务
	 * 
	 * @param conn
	 * 
	 * @throws SQLException
	 */
	public void doJdbcTransactionRollback(Connection conn) throws SQLException {
		if (conn != null) {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (Exception e) {
				String str = "jdbc transaction_rollback error";
				if (isPrint) {
					System.err.println(str);
				}
				log_custom.error(str, e);
				e.printStackTrace();
				throw e;
			}
		}
	}
	/**
	 * 
	 * 解析prepareSQL
	 * 
	 * @param sql_prepare
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	protected String preparedStatement_sql_build(String sql_prepare,
			List<Object> parameterList) {
		if (parameterList == null) {
			return sql_prepare;
		}
		String sql = sql_prepare;
		for (int i = 0; i < parameterList.size(); i++) {
			Object cObject = parameterList.get(i);
			/**
			 * 这里不直接替换?，因为?是正则表达式中的保留字，直接替换?会出错
			 */
			if (cObject != null) {
				// System.out.println("cObject="+cObject.getClass().getName());
			}
			if (cObject instanceof String) {
				sql = sql.replaceFirst("[?]", "'" + cObject + "'");
				continue;
			}
			if (cObject instanceof Date) {
				// System.out.println("11  cObject="+cObject.getClass().getName());
				String d = date2String_For_db((Date) cObject);
				sql = sql.replaceFirst("[?]", "'" + d + "'");
				continue;
			}
			if (cObject instanceof Timestamp) {
				sql = sql.replaceFirst("[?]", "'"
						+ date2String_For_db((Date) cObject) + "'");
				continue;
			}
			if (cObject instanceof byte[]) {
				sql = sql.replaceFirst("[?]", "byte[]");
				continue;
			}
			sql = sql.replaceFirst("[?]", String.valueOf(cObject));
		}
		return sql;
	}
	/**
	 * 
	 * 解析并打印prepareSQL
	 * 
	 * @param sql_prepare
	 * @param parameters
	 * @throws Exception
	 */
	protected void preparedStatement_sql_prepare_print(String sql_prepare,
			List<Object> parameterList) {
		String sql = this.preparedStatement_sql_build(sql_prepare,
				parameterList);
		log_custom.trace("prepare sql=" + sql);
	}
	class JdbcPrepareStatementBean {
		private ResultSet resultSet = null;
		private PreparedStatement preparedStatement = null;
		public ResultSet getResultSet() {
			return resultSet;
		}
		public void setResultSet(ResultSet resultSet) {
			this.resultSet = resultSet;
		}
		public PreparedStatement getPreparedStatement() {
			return preparedStatement;
		}
		public void setPreparedStatement(PreparedStatement preparedStatement) {
			this.preparedStatement = preparedStatement;
		}
	}
	/**
	 * 执行select语句
	 * 
	 * PreparedStatement 没有重新调用，创建新的PreparedStatement
	 * 
	 * 
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @throws SQLException
	 */
	public JdbcPrepareStatementBean preparedStatement_executeQuery_findResultSet(
			Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Connection不能为空");
		}
		this.preparedStatement_sql_prepare_print(sql, parameterList);
		log_custom.trace("final sql=" + sql);
		JdbcPrepareStatementBean bean = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// 创建新的PreparedStatement
			// preparedStatement = conn.prepareStatement(sql);
			preparedStatement = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (parameterList != null) {
				for (int i = 0; i < parameterList.size(); i++) {
					preparedStatement.setObject(i + 1, parameterList.get(i));
				}
			}
			resultSet = preparedStatement.executeQuery();
			// 返回
			bean = new JdbcPrepareStatementBean();
			bean.setResultSet(resultSet);
			bean.setPreparedStatement(preparedStatement);
			return bean;
		} catch (Exception e) {
			this.doCloseJdbc(resultSet, preparedStatement);
			String str = "jdbc error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
		}
	}
	/**
	 * 
	 * 
	 * 
	 * 将查询数据库结果转化为HashMap，值均转化为String类型;
	 * 
	 * @param resultSetMetaData
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected HashMap<String, Object> doResultSet2Map_v3(
			ResultSetMetaData resultSetMetaData, ResultSet resultSet)
			throws SQLException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (false) {
			// 影响性能
			// ResultSetMetaData针对的是整个表,不是单独一行的记录,最好把这行代码放到外面
			ResultSetMetaData resultSetMetaData_v1 = resultSet.getMetaData();
		}
		for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
			int index = i + 1;
			String columnName = resultSetMetaData.getColumnName(index);
			String field = resultSetMetaData.getColumnLabel(index);
			int columnType = resultSetMetaData.getColumnType(index);
			// if (true) {
			if (false) {
				// int t=java.sql.Types.INTEGER;
				log_custom.trace("列名=" + columnName);
				log_custom.trace(";类型=" + columnType);
				log_custom.trace("");
			}
			// 转换类型
			switch (columnType) {
				case java.sql.Types.BIGINT :
					long valueLong = resultSet.getLong(index);
					map.put(field, valueLong);
					break;
				case java.sql.Types.INTEGER :
					int valueInt = resultSet.getInt(index);
					// out("field=" + field);
					// out("int_value=" + int_value);
					map.put(field, valueInt);
					break;
				default :
					Object valueObject = resultSet.getObject(index);
					// if (true) {
					if (false) {
						if (valueObject != null) {
							log_custom.trace("类型="
									+ valueObject.getClass().getSimpleName());
						}
					}
					if (true) {
						// if (false) {
						if (valueObject != null) {
							String name = valueObject.getClass().getName();
							log_custom.trace("值类名=" + name);
						} else {
							log_custom.trace("值类名=空值");
						}
						log_custom.trace("值value=" + valueObject);
					}
					map.put(field, valueObject);
					break;
			}
		}
		return map;
	}
	public final static String C_ROWNUM = "c_rownum";
	/**
	 * 添加序列编号(序号);
	 * 
	 * 
	 * 性能改进;
	 * 
	 * 将查询数据库结果转化为HashMap，值均转化为String类型;
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> doResultSet2ListMap_v3_setSysNo(
			ResultSet resultSet) throws SQLException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		// 添加序列编号(序号)(注意跟主键ID的不同)
		// {
		int sys_no = 0;
		// }
		// 添加序列编号(序号)(注意跟主键ID的不同)
		while (resultSet.next()) {
			HashMap<String, Object> map = this.doResultSet2Map_v3(
					resultSetMetaData, resultSet);
			// 添加序列编号(序号)(注意跟主键ID的不同)
			// {
			sys_no = sys_no + 1;
			map.put(C_ROWNUM, new Integer(sys_no).toString());
			// }
			// 添加序列编号(序号)(注意跟主键ID的不同)
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 
	 * 添加序列编号(序号);
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> doResultSet2ListMap_setSysNo(
			ResultSet resultSet) throws SQLException {
		return this.doResultSet2ListMap_v3_setSysNo(resultSet);
	}
	/**
	 * 
	 * 添加序列编号(序号);
	 * 
	 * 通过sql;
	 * 
	 * 
	 * 将查询数据库结果转化为List<Map>
	 * 
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> preparedStatement_executeQuery_findMapList_setSysNo(
			Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		List<Map<String, Object>> listMap = null;
		JdbcPrepareStatementBean bean = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			bean = preparedStatement_executeQuery_findResultSet(conn, sql,
					parameterList);
			this.nullNotAssert(bean, "bean is null ");
			resultSet = bean.getResultSet();
			preparedStatement = bean.getPreparedStatement();
			listMap = doResultSet2ListMap_setSysNo(resultSet);
		} catch (Exception e) {
			String str = "jdbc error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
			this.doCloseJdbc(resultSet, preparedStatement);
		}
		return listMap;
	}
	/**
	 * 查询并转成listMap
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMapListJdbc(Connection conn,
			String sql, List<Object> parameterList) throws SQLException {
		return this.preparedStatement_executeQuery_findMapList_setSysNo(conn,
				sql, parameterList);
	}
	/**
	 * 
	 * 
	 * 需要自己写代码
	 * 
	 * 
	 * 查询并转成listMap
	 * 
	 * 
	 * 
	 * 该方法暂时弃用
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findResultSet_executeQuery_preparedStatement_v1(
			Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// preparedStatement = conn.prepareStatement(sql);
			preparedStatement = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (parameterList != null) {
				for (int i = 0; i < parameterList.size(); i++) {
					preparedStatement.setObject(i + 1, parameterList.get(i));
				}
			}
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			this.doCloseJdbc(resultSet, preparedStatement);
			String str = "jdbc error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
		}
		if (resultSet == null) {
			return null;
		}
		try {
			while (resultSet.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				if (false) {
					/**
					 * 
					 * 这部分需要自己写代码
					 */
					Object object = resultSet.getObject("id");
					if (object != null) {
						map.put("id", (String) object);
					} else {
						map.put("id", null);
					}
				}
				mapList.add(map);
			}
			return mapList;
		} catch (Exception e) {
			String str = "error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
			this.doCloseJdbc(resultSet, preparedStatement);
		}
	}
	/**
	 * 
	 * 批量增加
	 * 
	 * @param listMap
	 * @param conn
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public int doBatchListListJdbc(String sql,
			List<ArrayList<Object>> listList, Connection conn)
			throws SQLException {
		if (listList == null) {
			return -1;
		}
		if (listList.size() < 1) {
			return -1;
		}
		if (conn == null) {
			// return -1;
			throw new NullPointerException("Connection is  null");
		}
		PreparedStatement preparedStatement = null;
		int countUpdateInt = 0;
		preparedStatement = conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		for (List<Object> list : listList) {
			for (int i = 0; i < list.size(); i++) {
				int index = i + 1;
				if (list.get(i) == null) {
					preparedStatement.setObject(index, null);
					continue;
				}
				// 日期转换
				if (list.get(i) instanceof java.util.Date) {
					Timestamp ts = doUtilDate2Timestamp((java.util.Date) list
							.get(i));
					preparedStatement.setTimestamp(index, ts);
					continue;
				} else {
					preparedStatement.setObject(index, list.get(i));
					continue;
				}
			}
			preparedStatement.addBatch();
		}
		int[] countUpdateIntArray = preparedStatement.executeBatch();
		countUpdateInt = countUpdateIntArray.length;
		if (isPrint) {
			String str = "批理处理行数insert=" + countUpdateInt;
			System.out.println(str);
			log_custom.trace(str);
		}
		return countUpdateInt;
	}
	/**
	 * 
	 * 这部分需要自己写代码
	 * 
	 * 批量增加
	 * 
	 * @param listMap
	 * @param connection
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public int doBatchMapListJdbc(String sql,
			List<HashMap<String, Object>> listMap) throws SQLException {
		conn = this.findConnection();
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		if (listMap == null) {
			return -1;
		}
		if (listMap.size() < 1) {
			return -1;
		}
		if (conn == null) {
			// return -1;
			throw new NullPointerException("Connection is  null");
		}
		PreparedStatement preparedStatement = null;
		int countUpdateInt = 0;
		preparedStatement = conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		for (HashMap<String, Object> map : listMap) {
			if (true) {
				/**
				 * 
				 * 这部分需要自己写代码
				 */
				preparedStatement.setString(1, map.get("id").toString());
				preparedStatement.setString(2, map.get("name").toString());
			}
			preparedStatement.addBatch();
		}
		int[] countUpdateIntArray = preparedStatement.executeBatch();
		countUpdateInt = countUpdateIntArray.length;
		if (isPrint) {
			String str = "批理处理行数insert : " + countUpdateInt;
			System.out.println(str);
			log_custom.trace(str);
		}
		return countUpdateInt;
	}
	/**
	 * 
	 * 执行Insert语句，得到主键
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public String executeUpdate_findPrimaryKey_preparedStatement(
			Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Connection不能为空");
		}
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			/**
			 * 主键id
			 */
			String primaryKey = null;
			preparedStatement = conn.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			if (parameterList != null) {
				for (int i = 0; i < parameterList.size(); i++) {
					int index = i + 1;
					if (parameterList.get(i) == null) {
						preparedStatement.setObject(index, null);
						continue;
					}
					// 日期转换
					if (parameterList.get(i) instanceof java.util.Date) {
						Timestamp ts = doUtilDate2Timestamp((java.util.Date) parameterList
								.get(i));
						preparedStatement.setTimestamp(index, ts);
						continue;
					} else {
						preparedStatement
								.setObject(index, parameterList.get(i));
						continue;
					}
				}
			}
			/**
			 * 影响的行数
			 */
			int row_count = preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				primaryKey = resultSet.getString(1);
			}
			// return row_count;
			return primaryKey;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.doCloseJdbc(resultSet, preparedStatement);
		}
	}
	/**
	 * 
	 * 有回调;
	 * 
	 * 
	 * 没有事务;
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public Object doTransactionNotJdbc(Connection conn,
			JdbcTools_simple_ay callback) throws Exception {
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		try {
			// 业务开始
			Object object = callback.doConnectionJdbc(conn);
			// 业务结束
			// 成功，返回
			return object;
		} catch (Exception e) {
			String str = "jdbc error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
			this.doCloseConnectionJdbc(conn);
		}
	}
	/**
	 * 
	 * 有回调;
	 * 
	 * 
	 * 事务;
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public Object doTransactionJdbc(Connection conn,
			JdbcTools_simple_ay callback) throws Exception {
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		try {
			// 设置事务属性
			this.doJdbcTransactionStart(conn);
			// 业务开始
			Object cObject = callback.doConnectionJdbc(conn);
			// 业务结束
			// 提交，设置事务初始值
			this.doJdbcTransactionCommit(conn);
			// 成功，返回
			return cObject;
		} catch (Exception e) {
			this.doJdbcTransactionRollback(conn);
			String str = "jdbc error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
			this.doCloseConnectionJdbc(conn);
		}
	}
	/**
	 * 
	 * 没有回调;
	 * 
	 * 
	 * 有事务;
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public Object doTransactionJdbc(Connection conn) throws Exception {
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		try {
			// 设置事务属性
			this.doJdbcTransactionStart(conn);
			// 业务开始
			Object object = null;
			// 业务结束
			// 提交，设置事务初始值
			this.doJdbcTransactionCommit(conn);
			// 成功，返回
			return object;
		} catch (Exception e) {
			this.doJdbcTransactionRollback(conn);
			String str = "jdbc error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
			this.doCloseConnectionJdbc(conn);
		}
	}
	/**
	 * 
	 * jdbc_copy
	 * 
	 * @param resultSetMetaData
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	protected ArrayList<Object> doCopy_resultSet2List_jdbc(
			ResultSetMetaData resultSetMetaData, ResultSet resultSet)
			throws SQLException {
		ArrayList<Object> list = new ArrayList<Object>();
		for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
			int index = i + 1;
			String columnName = resultSetMetaData.getColumnName(index);
			String field = resultSetMetaData.getColumnLabel(index);
			int columnType = resultSetMetaData.getColumnType(index);
			// if (true) {
			if (false) {
				// int t=java.sql.Types.INTEGER;
				log_custom.trace("列名=" + columnName);
				log_custom.trace(";类型=" + columnType);
				log_custom.trace("");
			}
			// 转换类型
			switch (columnType) {
				case java.sql.Types.BIGINT :
					long valueLong = resultSet.getLong(index);
					list.add(valueLong);
					break;
				case java.sql.Types.INTEGER :
					int valueInt = resultSet.getInt(index);
					// out("field=" + field);
					// out("int_value=" + int_value);
					list.add(valueInt);
					break;
				default :
					Object valueObject = resultSet.getObject(index);
					// if (true) {
					if (false) {
						if (valueObject != null) {
							log_custom.trace("类型="
									+ valueObject.getClass().getSimpleName());
						}
					}
					if (true) {
						// if (false) {
						if (valueObject != null) {
							String name = valueObject.getClass().getName();
							log_custom.trace("值类名=" + name);
						} else {
							log_custom.trace("值类名=空值");
						}
						log_custom.trace("值value=" + valueObject);
					}
					list.add(valueObject);
					break;
			}
		}
		return list;
	}
	/**
	 * jdbc_copy
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public List<ArrayList<Object>> doCopy_resultSet2ListList_jdbc(
			ResultSet resultSet) throws SQLException {
		List<ArrayList<Object>> mapList = new ArrayList<ArrayList<Object>>();
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		// 添加序列编号(序号)(注意跟主键ID的不同)
		// {
		int sys_no = 0;
		// }
		// 添加序列编号(序号)(注意跟主键ID的不同)
		while (resultSet.next()) {
			ArrayList<Object> list = this.doCopy_resultSet2List_jdbc(
					resultSetMetaData, resultSet);
			mapList.add(list);
		}
		return mapList;
	}
	/**
	 * jdbc_copy
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<ArrayList<Object>> doCopy_findListList_executeQuery_preparedStatement_jdbc(
			Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		List<ArrayList<Object>> listMap = null;
		JdbcPrepareStatementBean bean = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			bean = preparedStatement_executeQuery_findResultSet(conn, sql,
					parameterList);
			this.nullNotAssert(bean, "bean is null ");
			resultSet = bean.getResultSet();
			preparedStatement = bean.getPreparedStatement();
			listMap = doCopy_resultSet2ListList_jdbc(resultSet);
		} catch (Exception e) {
			String str = "jdbc error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
			this.doCloseJdbc(resultSet, preparedStatement);
		}
		return listMap;
	}
	/**
	 * jdbc_copy
	 * 
	 * @throws Exception
	 */
	public void doCopyJdbc() throws Exception {
		JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance()
				.findLocal();

		Connection conn = jdbcDataSource.getConnection();
		String sql = "select * from fun_type_str";
		List<Object> parameterList = new ArrayList<Object>();
		List<ArrayList<Object>> listList = this
				.doCopy_findListList_executeQuery_preparedStatement_jdbc(
						jdbcDataSource.getConnection(), sql, parameterList);
		for (ArrayList<Object> list : listList) {
			for (Object object : list) {
				System.out.println(object);
			}
			System.out.println("==============");
		}
		sql = "insert into fun_type_str_t(name) values(?)";
		this.doBatchListListJdbc(sql, listList, jdbcDataSource.getConnection());
	}
}
