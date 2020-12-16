package c.x.all.simple.jdbc.single_tools.nut;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import c.a.util.core.data_source.ay.JdbcDataSourceAy;
import c.a.util.core.data_source.ay.JdbcDataSourceListAy;
import c.a.util.core.date.DateThreadLocal;
/**
 * 
 * 最精简的jdbc工具类
 * 
 * 
 * 
 */
public class JdbcTools_simple_ax {
	protected org.apache.logging.log4j.Logger log_custom = org.apache.logging.log4j.LogManager.getLogger("log_custom");
	protected static boolean isPrint = true;
	private static String line_delimiter = "\r\n";// 先回车后换行
	private static String field_delimiter = "\\|\\@\\|\\%\\|";//
	protected static int batch_size = 2;
	/**
	 * jdbc_copy
	 * 
	 * @throws Exception
	 */
	public void doCopyJdbc() throws Exception {
		if (false) {
			String sql = "select * from fun_type_str";
		}
		String sql = "select id,create_time2 from family_account_bill_info";
		List<Object> parameterList = new ArrayList<Object>();
		List<Map<String, Object>> mapList = findMapList(sql, parameterList);
		for (Map<String, Object> map : mapList) {
			System.out.println(map.get("id"));
			System.out.println(map.get("create_time2"));
			System.out.println("==============");
		}
		if (false) {
			sql = "insert into fun_type_str_t(name) values(?)";
		}
		sql = "update  family_account_bill_info set create_time=? where id=? ";
		doBatchJdbcByMapList(sql, mapList);
	}
	/**
	 * 
	 * 批处理增加;
	 * 
	 * 没有回调;
	 * 
	 * 
	 * 有事务;
	 * 
	 * @param connection
	 * @throws Exception
	 */
	public Object doJdbcTransactionBatch(String filePathInput) throws Exception {
		conn = this.findConnection();
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		try {
			// 设置事务属性
			this.doJdbcTransactionStart(conn);
			// 业务开始
			Object object = null;
			if (false) {
				this.doBuildJdbcByMapList(filePathInput);
			}
			this.doJdbcServiceBatch(filePathInput);
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
	 * 批处理增加;
	 * 
	 * 没有回调;
	 * 
	 * 
	 * 没有事务;
	 * 
	 * @param connection
	 * @throws Exception
	 */
	public Object doBatchTransactionNotJdbc(String filePathInput)
			throws Exception {
		conn = this.findConnection();
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		try {
			// 业务开始
			Object object = null;
			this.doJdbcServiceBatch(filePathInput);
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
	 * 分层
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void doJdbcServiceBatch(String filePathInput) throws Exception {
		this.doBatchDaoJdbc(filePathInput);
	}
	/**
	 * 分层;
	 * 
	 * 查询;
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void doBatchDaoJdbc(String filePathInput) throws Exception {
		this.doBuildJdbcByMapList(filePathInput);
	}
	/**
	 * 
	 * 这部分需要自己写代码
	 * 
	 * 解析文件
	 * 
	 * @param filePath
	 * @param jdbcTemplate
	 * @throws IOException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void doBuildJdbcByMapList(String filePath) throws IOException,
			SQLException, Exception {
		String sql = "insert into fun_type_str_t(id,name) values (?,?)";
		if (filePath == null) {
			throw new NullPointerException("路径不能为空");
		}
		List<Map<String, Object>> mapList = null;
		BufferedReader bufferedReader = null;
		try {
			// bufferedReader = new BufferedReader(new InputStreamReader(
			// new FileInputStream(new File(filePath)), "GBK"));
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(filePath)), "UTF-8"));
			// 计数
			int countInt = 1;
			mapList = new ArrayList<Map<String, Object>>();
			while (bufferedReader.ready()) {
				HashMap<String, Object> list = new HashMap<String, Object>();
				String strLine = bufferedReader.readLine();
				// 用旧的字符编码解码字符串。解码可能会出现异常。
				// byte[] bs = strLine.getBytes("GBK");
				// 用新的字符编码生成字符串
				// strLine = new String(bs, "UTF-8");
				if (isPrint) {
					System.out.println("每行文本strLine=" + strLine);
				}
				String[] strArray = strLine.split(field_delimiter, -1);
				if (strArray.length < 2) {
					break;
				}
				if (isPrint) {
					System.out.println("列数arrayStr.length=" + strArray.length);
					System.out.println("第1列arrayStr[0]=" + strArray[0]);
					System.out.println("第2列arrayStr[1]=" + strArray[1]);
				}
				if (true) {
					/**
					 * 
					 * 这部分需要自己写代码
					 */
					list.put("id", strArray[0]);
					list.put("name", strArray[1]);
				}
				mapList.add(list);
				if (countInt % batch_size == 0) {
					this.doBatchJdbcByMapList(sql, mapList);
					mapList = new ArrayList<Map<String, Object>>();
				}
				countInt = countInt + 1;
			}
			// 最后一批放进数据库
			this.doBatchJdbcByMapList(sql, mapList);
		} catch (FileNotFoundException e) {
			String str = "jdbc error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			String str = "error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			String str = "error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
	}
	/**
	 * 
	 * 这部分需要自己写代码
	 * 
	 * 批量增加
	 * 
	 * @param mapList
	 * @param connection
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public int doBatchJdbcByMapList(String sql,
			List<Map<String, Object>> mapList) throws SQLException {
		conn = this.findConnection();
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		if (mapList == null) {
			return -1;
		}
		if (mapList.size() < 1) {
			return -1;
		}
		if (conn == null) {
			// return -1;
			throw new NullPointerException("Connection is  null");
		}
		PreparedStatement preparedStatement = null;
		int countsUpdateInt = 0;
		preparedStatement = conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		for (Map<String, Object> map : mapList) {
			if (true) {
				/**
				 * 
				 * 这部分需要自己写代码
				 */
				if (true) {
					preparedStatement.setObject(1, map.get("id"));
					preparedStatement.setObject(2, map.get("name"));
				}
				if (false) {
					try {
						java.sql.Timestamp ts = (java.sql.Timestamp) map
								.get("create_time2");
						preparedStatement.setObject(1,
								DateThreadLocal.findThreadLocal().get().doString2Date(ts.toLocaleString())
										.getTime());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			preparedStatement.addBatch();
		}
		int[] countsUpdateIntArray = preparedStatement.executeBatch();
		countsUpdateInt = countsUpdateIntArray.length;
		if (isPrint) {
			String str = "批理处理行数insert : " + countsUpdateInt;
			System.out.println(str);
			log_custom.trace(str);
		}
		return countsUpdateInt;
	}
	/**
	 * 
	 * 
	 * 查询;
	 * 
	 * 没有回调;
	 * 
	 * 
	 * 有事务;
	 * 
	 * @param connection
	 * @throws Exception
	 */
	public String findTransactionJdbc(String id) throws Exception {
		conn = this.findConnection();
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		try {
			// 设置事务属性
			this.doJdbcTransactionStart(conn);
			// 业务开始
			Object cObject = null;
			String strReturn = findserviceJdbc(id);
			// 业务结束
			// 提交，设置事务初始值
			this.doJdbcTransactionCommit(conn);
			// 成功，返回
			// return cObject;
			return strReturn;
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
	 * 
	 * 查询;
	 * 
	 * 没有回调;
	 * 
	 * 
	 * 没有事务;
	 * 
	 * @param connection
	 * @throws Exception
	 */
	public String findTransactionNotJdbc(String id) throws Exception {
		conn = this.findConnection();
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		try {
			// 业务开始
			Object cObject = null;
			String strReturn = findserviceJdbc(id);
			// 业务结束
			// 成功，返回
			// return cObject;
			return strReturn;
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
	 * 分层
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String findserviceJdbc(String id) throws Exception {
		return findDaoJdbc(id);
	}
	/**
	 * 查询
	 * 
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public String findDaoJdbc(String id) throws SQLException {
		// 注入数据源
		HashMap<String, Object> map = this.findMapJdbc(id);
		if (map != null) {
			Object cObject = map.get("name");
			if (cObject != null) {
				return (String) cObject;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	/**
	 * 查询
	 * 
	 * @param id
	 * @param jdbcTemplate
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, Object> findMapJdbc(String id) throws SQLException {
		List<Map<String, Object>> list = this.findMapListJdbc(id);
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			HashMap<String, Object> map = (HashMap<String, Object>) iterator
					.next();
			return map;
		}
		return null;
	}
	/**
	 * 查询
	 * 
	 * @param id
	 * @param jdbcTemplate
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMapListJdbc(String id)
			throws SQLException {
		String sql = null;
		sql = "SELECT * FROM fun_type_str_t  where id=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		List<Map<String, Object>> list = this.findMapList(sql, parameterList);
		return list;
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
	 * @param connection
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMapList(String sql,
			List<Object> parameterList) throws SQLException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// preparedStatement = conn.prepareStatement(sql);
			preparedStatement = this.findConnection().prepareStatement(sql,
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
					Object object = null;
					object = resultSet.getObject("id");
					if (object != null) {
						System.out.println("id="
								+ (java.math.BigInteger) object);
						map.put("id", (java.math.BigInteger) object);
					} else {
						map.put("id", null);
					}
				}
				if (false) {
					/**
					 * 
					 * 这部分需要自己写代码
					 */
					Object object = null;
					object = resultSet.getObject("name");
					if (object != null) {
						map.put("name", (String) object);
					} else {
						map.put("name", null);
					}
				}
				if (true) {
					/**
					 * 
					 * 这部分需要自己写代码
					 */
					Object object = null;
					object = resultSet.getObject("id");
					if (object != null) {
						map.put("id", (Long) object);
					} else {
						map.put("id", null);
					}
					object = resultSet.getObject("create_time2");
					if (object != null) {
						map.put("create_time2", (java.sql.Timestamp) object);
					} else {
						map.put("create_time2", null);
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
	 * 执行Insert语句或update语句
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate_preparedStatement(Connection conn, String sql,
			List<Object> parameterList) throws SQLException {
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
			return row_count;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.doCloseJdbc(resultSet, preparedStatement);
		}
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
	public String findPathIO(String sourceNameStr)
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
	 * singleton
	 * 
	 */
	// private final static Object key = new Object();
	// private static SimpleJdbcTools_ax instance = null;
	//
	// private SimpleJdbcTools_ax() {
	// }
	//
	// public static SimpleJdbcTools_ax findInstance() throws Exception {
	// synchronized (key) {
	// if (instance == null) {
	// instance = new SimpleJdbcTools_ax();
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
	protected DataSource jdbcDataSource = null;
	protected DataSource findDataSource() throws Exception {
		if (jdbcDataSource == null) {
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					"config//example//spring//application_context.xml");
			jdbcDataSource = (DataSource) applicationContext
					.getBean("dataSource_spring");
			JdbcDataSourceAy jdbcDataSource = JdbcDataSourceListAy.findInstance()
					.findLocal();
			Connection conn = jdbcDataSource.getConnection();
		}
		return this.jdbcDataSource;
	}
}
