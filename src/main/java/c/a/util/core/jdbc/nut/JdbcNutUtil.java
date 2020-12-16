package c.a.util.core.jdbc.nut;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.SysConfig;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.transaction.TransactionBase;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.date.DateThreadLocal;
import c.a.util.core.enums.EnumUtil;
import c.a.util.core.jdbc.bean.create.CreateTableBean;
import c.a.util.core.jdbc.bean.create.CreateTableListBean;
import c.a.util.core.jdbc.bean.create.RowBean;
import c.a.util.core.jdbc.bean.create.TableBean;
import c.a.util.core.jdbc.bean.data_row.JdbcDataDto;
import c.a.util.core.jdbc.bean.data_row.JdbcRowDto;
import c.a.util.core.jdbc.bean.nut.ColumnBean;
import c.a.util.core.jdbc.bean.nut.DatabaseBean;
import c.a.util.core.jdbc.bean.nut.JdbcPrepareStatementDto;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.bean.sql_custom.SqlCustomBean;
import c.a.util.core.jdbc.bean.sql_custom.TypeOperateBean;
import c.a.util.core.jdbc.config.ColumnTypeEnum;
import c.a.util.core.jdbc.config.SqlCustomEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.log.LogUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.thread.courser.CourserUtil;
import c.a.util.mq.bean.MqSendBean;
import c.a.util.mq.rabbitmq.RabbitMqUtil;
/**
 * 
 * 
 * 数据库的核心api
 * 
 * @Description:
 * @ClassName: JdbcCoreUtil
 * @date 2017年3月10日 上午10:25:37
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public abstract class JdbcNutUtil implements IJdbcUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	private String logSendSQL = "发送SQL功能出错,";
	// 序列编号(序号)(注意跟主键ID的不同)
	public static String ROW_NUM = "ROW_NUM";
	protected Connection connection = null;
	protected String keyExecute = "execute";
	/**
	 * 1返回数据库类型
	 * 
	 * @Description:
	 * 
	 * @Title: getDbType
	 * 
	 * @return 参数说明
	 * 
	 * @throws
	 */
	public abstract String getDbType();
	/**
	 * 2设置数据库类型
	 * 
	 * @Description:
	 * 
	 * @Title: setDbType
	 * 
	 * @param dbType 参数说明
	 * 
	 * @throws
	 */
	public abstract void setDbType(String dbType);
	/**
	 * 3返回驱动
	 * 
	 * @Description:
	 * 
	 * @Title: getDriver
	 * 
	 * @return 参数说明
	 * 
	 * @throws
	 */
	public abstract String getDriver();
	/**
	 * 4设置驱动
	 * 
	 * @Description:
	 * 
	 * @Title: setDriver
	 * 
	 * @param driver 参数说明
	 * 
	 * @throws
	 */
	public abstract void setDriver(String driver);
	/**
	 * 5得到数据库的连接
	 * 
	 * @Description:
	 * 
	 * @Title: getConnection
	 * 
	 * @return 参数说明
	 * 
	 * @throws
	 */
	public Connection getConnection() {
		return connection;
	}
	/**
	 * 6设置数据库的连接
	 * 
	 * @Description:
	 * 
	 * @Title: setConnection
	 * 
	 * @param connection 参数说明
	 * 
	 * @throws
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	/**
	 * 
	 * 7打开并得到数据库的连接 @Description:
	 * 
	 * @Title: openConnectionCore
	 * 
	 * @param url
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws ClassNotFoundException
	 * 
	 * @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	@Override
	public abstract Connection openConnectionCore(String url, String username, String password)
			throws ClassNotFoundException, SQLException;
	/**
	 * 8打开并得到数据库的连接
	 * 
	 * @Description:
	 * 
	 * @Title: openConnectionCore
	 * 
	 * @param driver
	 * 
	 * @param url
	 * 
	 * @param username
	 * 
	 * @param password
	 * 
	 * @return
	 * 
	 * @throws ClassNotFoundException
	 * 
	 * @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	@Override
	public Connection openConnectionCore(String driver, String url, String username, String password)
			throws ClassNotFoundException, SQLException {
		Properties properties = new Properties();
		// 元数据其中的列信息能显示注释
		// 获取Oracle元数据 REMARKS信息
		properties.setProperty("remarksReporting", "true");
		// 获取MySQL元数据 REMARKS信息
		properties.setProperty("useInformationSchema", "true");
		properties.put("user", username);
		properties.put("password", password);
		Class.forName(driver);
		Connection conn = java.sql.DriverManager.getConnection(url, properties);
		return conn;
	}
	/**
	 * 9打开并得到数据库的连接
	 * 
	 */
	@Override
	public abstract Connection openConnection(String url, String username, String password) throws Exception;
	/**
	 * 10 打开并得到数据库的连接
	 * 
	 * @param driver
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * 
	 * @throws SQLException
	 */
	@Override
	public Connection openConnection(final String driver, final String url, final String username,
			final String password) throws Exception {
		String logFun = "连接数据库时,";
		String logStr = null;
		Connection conn = null;
		// 使用代理模式的调用第三方jar包的method
		Callable<Connection> task = new Callable<Connection>() {
			public Connection call() throws Exception {
				return openConnectionCore(driver, url, username, password);
			}
		};
		try {
			conn = CourserUtil.call(task, TimeUnit.SECONDS, 50);
		} catch (TimeoutException e) {
			logStr = "网络超时";
			logStr = logFun + logStr;
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		} catch (java.util.concurrent.RejectedExecutionException e) {
			logStr = "队列超出范围";
			logStr = logFun + logStr;
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		} catch (Exception e) {
			logStr = "出现异常";
			logStr = logFun + logStr;
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
			throw new Exception(e.getMessage(), e);
		}
		return conn;
	}
	/**
	 * 11关闭数据库连接
	 * 
	 * @param conn
	 */
	public void closeConnection(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}
	/**
	 * 12关闭PreparedStatement
	 * 
	 * @param ps
	 */
	public void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
		if (preparedStatement != null) {
			preparedStatement.close();
		}
	}
	/**
	 * 13关闭Statement
	 * 
	 * @param statement
	 */
	public void closeStatement(Statement statement) throws SQLException {
		if (statement != null) {
			statement.close();
		}
	}
	/**
	 * 14关闭ResultSet
	 * 
	 * @param rs
	 */
	public void closeResultSet(ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
	}
	/**
	 * 15 关闭Statement
	 * 
	 * 关闭ResultSet
	 * 
	 * @param rs
	 * @param statement
	 * @param conn
	 */
	public void close(ResultSet resultSet, Statement statement, Connection conn) throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (conn != null) {
			conn.close();
		}
	}
	/**
	 * 
	 * 16
	 * 
	 * 关闭ResultSet
	 * 
	 * 关闭PreparedStatement
	 * 
	 * @param ps
	 */
	public void close(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
		this.closeResultSet(resultSet);
		this.closePreparedStatement(preparedStatement);
	}
	/**
	 * 17 关闭Statement
	 * 
	 * @param statement
	 * @param conn
	 */
	public void close(Statement statement, Connection conn) throws SQLException {
		if (statement != null) {
			statement.close();
		}
		if (conn != null) {
			conn.close();
		}
	}
	/**
	 * 18
	 * 
	 * 事务启动
	 * 
	 * 设置事务隔离级别
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public abstract void doTransactionStart(Connection conn) throws SQLException;
	/**
	 * 19提交事务
	 * 
	 * @param conn
	 * 
	 * @throws SQLException
	 */
	public void doTransactionCommit(Connection conn) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Connection不能为空");
		}
		if (conn != null) {
			conn.commit();
			conn.setAutoCommit(true);
		}
	}
	/**
	 * 20回滚事务
	 * 
	 * @param conn
	 * 
	 * @throws SQLException
	 */
	public void doTransactionRollback(Connection conn) throws SQLException {
		if (conn != null) {
			if (conn.getAutoCommit()) {
			} else {
				conn.rollback();
				conn.setAutoCommit(true);
			}
		}
	}
	/**
	 * 
	 * 
	 * 21 执行insert,delete,update语句( 如果主键是自动增长的数字，将得到主键)
	 * 
	 * @param conn
	 * @param sql
	 * @return 影响的行数
	 * @throws SQLException
	 */
	public String execute2PK(Connection conn, String sql) throws SQLException {
		return this.execute2PK(conn, sql, null);
	}
	/**
	 * 22执行insert,delete,update语句( 如果主键是自动增长的数字，将得到主键)
	 * 
	 * @Description:
	 * @Title: execute2PK
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * 
	 * @throws SQLException
	 *             参数说明
	 * 
	 */
	public String execute2PK(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Connection不能为空");
		}
		this.doPrintSqlTarget(keyExecute, sql, parameterList);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		/**
		 * 主键id
		 */
		String primaryKey = null;
		try {
			preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (parameterList != null) {
				for (int i = 0; i < parameterList.size(); i++) {
					int index = i + 1;
					if (parameterList.get(i) == null) {
						preparedStatement.setObject(index, null);
						continue;
					}
					// 日期转换
					if (parameterList.get(i) instanceof java.util.Date) {
						Timestamp ts = DateThreadLocal.findThreadLocal().get()
								.doUtilDate2Timestamp((java.util.Date) parameterList.get(i));
						preparedStatement.setTimestamp(index, ts);
						continue;
					} else {
						preparedStatement.setObject(index, parameterList.get(i));
						continue;
					}
				}
			}
			/**
			 * 影响的行数
			 */
			// int countRow = ps.executeUpdate();
			/**
			 * 得到主键
			 */
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				primaryKey = resultSet.getString(1);
			}
			return primaryKey;
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closePreparedStatement(preparedStatement);
		}
	}
	/**
	 * 23执行insert,delete,update语句(返回影响的行数)
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @throws SQLException
	 */
	public int execute(Connection conn, String sql) throws SQLException {
		return this.execute(this.keyExecute, conn, sql, null);
	}
	/**
	 * 24执行insert,delete,update语句(返回影响的行数)
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return 返回影响的行数
	 * @throws SQLException
	 */
	public int execute(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return this.execute(this.keyExecute, conn, sql, parameterList);
	}
	/**
	 * 24执行insert,delete,update语句(返回影响的行数)
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return 返回影响的行数
	 * @throws SQLException
	 */
	private int execute(String fun, Connection conn, String sql, List<Object> parameterList) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Connection不能为空");
		}
		this.doPrintSqlTarget(fun, sql, parameterList);
		PreparedStatement preparedStatement = null;
		// ResultSet resultSet = null;
		/**
		 * 主键id
		 */
		// String primaryKey = null;
		try {
			preparedStatement = conn.prepareStatement(sql);
			if (parameterList != null) {
				for (int i = 0; i < parameterList.size(); i++) {
					int index = i + 1;
					if (parameterList.get(i) == null) {
						preparedStatement.setObject(index, null);
						continue;
					}
					// 日期转换
					if (parameterList.get(i) instanceof java.util.Date) {
						Timestamp ts = DateThreadLocal.findThreadLocal().get()
								.doUtilDate2Timestamp((java.util.Date) parameterList.get(i));
						preparedStatement.setTimestamp(index, ts);
						continue;
					} else {
						preparedStatement.setObject(index, parameterList.get(i));
						continue;
					}
				}
			}
			/**
			 * 影响的行数
			 */
			int countRow = preparedStatement.executeUpdate();
			return countRow;
		} catch (SQLException e) {
			throw e;
		} finally {
			this.closePreparedStatement(preparedStatement);
		}
	}
	/**
	 * 
	 * 25
	 * 
	 * 一,执行Insert语句，得到主键;
	 * 
	 * 二,如果主键是自动增长的数字，将得到主键;
	 * 
	 * 三,打印sql;
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 */
	public String executeInsert(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		return this.executeInsert(true, conn, sql, parameterList);
	}
	/**
	 * 26
	 * 
	 * 一,执行Insert语句，得到主键;
	 * 
	 * 二,如果主键是自动增长的数字，将得到主键;
	 * 
	 * 三,打印sql;
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 */
	public String executeInsert(Boolean isPrintSqlOriginal, Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Connection不能为空");
		}
		if (isPrintSqlOriginal) {
			this.doPrintSqlTarget(keyExecute, sql, parameterList);
		}
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		/**
		 * 主键id
		 */
		String primaryKey = null;
		try {
			preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (parameterList != null) {
				for (int i = 0; i < parameterList.size(); i++) {
					int index = i + 1;
					if (parameterList.get(i) == null) {
						preparedStatement.setObject(index, null);
						continue;
					}
					// 日期转换
					if (parameterList.get(i) instanceof java.util.Date) {
						Timestamp ts = DateThreadLocal.findThreadLocal().get()
								.doUtilDate2Timestamp((java.util.Date) parameterList.get(i));
						preparedStatement.setTimestamp(index, ts);
						continue;
					} else {
						preparedStatement.setObject(index, parameterList.get(i));
						continue;
					}
				}
			}
			/**
			 * 影响的行数
			 */
			// int countRow = preparedStatement.executeUpdate();
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				primaryKey = resultSet.getString(1);
			}
			return primaryKey;
		} catch (SQLException e) {
			throw e;
		} finally {
			this.close(resultSet, preparedStatement);
		}
	}
	/**
	 * 27根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0或1开始
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract int findStart(int pageNo, int pageSize);
	/**
	 * 28获得每页的记录数量,默认为Integer.MAX_VALU.
	 * 
	 * @param pageSize
	 * @return
	 */
	public abstract int findLimit(int pageSize);
	/**
	 * 29 根据pageNo和pageSize计算当前页最后一条记录在总结果集中的位置
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract int findEnd(int pageNo, int pageSize);
	/**
	 * 
	 * 30 找出最终解析后的sql
	 * 
	 * @param sqlOriginal
	 * @param parameterList
	 * @return
	 * @throws Exception
	 */
	public String findSqlTarget(String sqlOriginal, List<Object> parameterList) {
		if (parameterList == null) {
			return sqlOriginal;
		}
		String sqlParse = sqlOriginal;
		for (int i = 0; i < parameterList.size(); i++) {
			Object object = parameterList.get(i);
			/**
			 * 这里不直接替换?，因为?是正则表达式中的保留字，直接替换?会出错
			 */
			if (object instanceof String) {
				// log.trace("sqlParse="+sqlParse);
				// log.trace("object="+object);
				try {
					sqlParse = sqlParse.replaceFirst("[?]", "'" + object + "'");
				} catch (Exception e) {
					log.error(e);
					e.printStackTrace();
				}
				continue;
			}
			if (object instanceof Date) {
				String d = DateThreadLocal.findThreadLocal().get().doUtilDate2String24hEn((Date) object);
				sqlParse = sqlParse.replaceFirst("[?]", "'" + d + "'");
				continue;
			}
			if (object instanceof Timestamp) {
				sqlParse = sqlParse.replaceFirst("[?]",
						"'" + DateThreadLocal.findThreadLocal().get().doUtilDate2String24hEn((Date) object) + "'");
				continue;
			}
			if (object instanceof byte[]) {
				sqlParse = sqlParse.replaceFirst("[?]", "byte[]");
				continue;
			}
			sqlParse = sqlParse.replaceFirst("[?]", String.valueOf(object));
		}
		return sqlParse;
	}
	/**
	 * 31 解析并打印最终的sql
	 * 
	 * @param sqlOriginal
	 * @param parameterList
	 * @throws Exception
	 */
	public void doPrintSqlTarget(String fun, String sqlOriginal, List<Object> parameterList) {
		try {
			// 解析并打印prepareSQL
			String sql = this.findSqlTarget(sqlOriginal, parameterList);
			JsonTcpBean logJsonTcpBean = LogThreadLocal.findLog();
			if (logJsonTcpBean != null) {
				log.info(LogUtil.findInstance().doPrintLogTarget(logJsonTcpBean, "sql="+sql));
			}else{
				log.info("sql=" + sql);
			}
			// System.out.println("sql=" + sql);
			if (keyExecute.equals(fun)) {
				String jdbcSecurityStart = BeanThreadLocal
						.find(SysConfig.findInstance().findMap().get("jdbc.security.start"), "false");
				if ("true".equals(jdbcSecurityStart)) {
					// 发送SQL到队列
					// this.doSendSQLByRabbitmq(sql);
					// 发送SQL到security数据库
					this.doSendSQLByJdbc(sql);
				}
			}
		} catch (Exception e) {
			String logStr = "解析并打印最终的sql";
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
		}
	}
	public void doSendSQLByJdbc(String sql) {
		TransactionBase transactionBase = new TransactionBase();
		IJdbcTool jdbcTool = null;
		IJdbcUtil jdbcUtil = null;
		Connection connection = null;
		try {
			jdbcTool = transactionBase.findJdbcTool("security");
			jdbcUtil = jdbcTool.getJdbcUtil();
			connection = jdbcUtil.getConnection();
			transactionBase.transactionStart(jdbcTool);
			this.execute("", connection, sql, null);
			transactionBase.transactionEnd(jdbcTool);
		} catch (Exception e) {
			String logStr = "发送sql到security数据库";
			logStr = logSendSQL + logStr;
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
			transactionBase.transactionRoll(jdbcTool);
		}
	}
	/**
	 * 发送sql到队列
	 * 
	 * @Title: doSendSQLByRabbitmq
	 * @Description:
	 *
	 * 				参数说明
	 * @param sql
	 * @throws Exception
	 *             返回类型 void
	 */
	public void doSendSQLByRabbitmq(String sql) {
		try {
			String rabbitmqStart = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("rabbitmq.local.start"),
					"false");
			String rabbitmqSecuritySend = BeanThreadLocal
					.find(SysConfig.findInstance().findMap().get("rabbitmq.security.send"), "false");
			if ("true".equals(rabbitmqStart) && ("true".equals(rabbitmqSecuritySend))) {
				RabbitMqUtil mqUtil = RabbitMqUtil.findInstance();
				MqSendBean bean = new MqSendBean();
				bean.setText(sql);
				mqUtil.sendQueueSimple("queue.sql", bean);
			}
		} catch (Exception e) {
			String logStr = "发送sql到队列";
			logStr = logSendSQL + logStr;
			log.error(logStr);
			log.error(logStr, e);
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 32 查找分页sql
	 */
	public abstract String findSqlPage(String sql, long start, long end, long pageIndex, long pageSize);
	/**
	 * 33查找统计总数的sql
	 * 
	 * 
	 */
	public abstract String findSqlCount(String sql, long start, long limit) throws SQLException;
	/**
	 * 
	 * 34执行select语句( PreparedStatement 重新调用)
	 * 
	 */
	public JdbcPrepareStatementDto findResultSet(PreparedStatement preparedStatement, String sql,
			List<Object> parameterList) throws SQLException {
		this.doPrintSqlTarget("", sql, parameterList);
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		try {
			if (parameterList != null) {
				for (int i = 0; i < parameterList.size(); i++) {
					preparedStatement.setObject(i + 1, parameterList.get(i));
				}
			}
			resultSet = preparedStatement.executeQuery();
			// 返回
			bean = new JdbcPrepareStatementDto();
			bean.setResultSet(resultSet);
			bean.setPreparedStatement(preparedStatement);
			return bean;
		} catch (SQLException e) {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			throw e;
		} finally {
		}
	}
	/**
	 * 
	 * 35
	 * 
	 * 一,分页通过resultSet.absolute(游标)来查询;
	 * 
	 * 二,每页取数据最好不要超过1万条;
	 * 
	 * 三,执行select语句( PreparedStatement 没有重新调用，创建新的PreparedStatement);
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public JdbcPrepareStatementDto findResultSet(Connection conn, String sql, List<Object> parameterList, int pageNo,
			int pageSize) throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Connection不能为空");
		}
		this.doPrintSqlTarget("", sql, parameterList);
		JdbcPrepareStatementDto bean = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// 创建新的PreparedStatement
			// 性能不行
			preparedStatement = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			if (parameterList != null) {
				for (int i = 0; i < parameterList.size(); i++) {
					preparedStatement.setObject(i + 1, parameterList.get(i));
				}
			}
			// setFetchSize 最主要是为了减少网络交互次数设计的。
			// 访问ResultSet时，如果它每次只从服务器上取一行数据，则会产生大量的开销。
			// setFetchSize的意 思是当调用rs.next时，
			// ResultSet会一次性从服务器上取得多少行数据回来，
			// 这样在下次rs.next时，它可以直接从内存中获取出数据而不 需要网络交互，
			// 提高了效率。 这个设置可能会被某些JDBC驱动忽略的，而且设置过大也会造成内存的上升。
			preparedStatement.setFetchSize(pageSize);
			preparedStatement.setMaxRows(pageNo * pageSize);
			// executeQuery
			resultSet = preparedStatement.executeQuery();
			// 定位
			int row = (pageNo - 1) * pageSize;
			if (row > 0) {
				resultSet.absolute(row);
			}
			// 返回
			bean = new JdbcPrepareStatementDto();
			bean.setResultSet(resultSet);
			bean.setPreparedStatement(preparedStatement);
			return bean;
		} catch (SQLException e) {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			throw e;
		} finally {
		}
	}
	/**
	 * 36 查找记录集
	 * 
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @throws SQLException
	 */
	public JdbcPrepareStatementDto findResultSet(Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Connection不能为空");
		}
		this.doPrintSqlTarget("", sql, parameterList);
		JdbcPrepareStatementDto bean = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// 创建新的PreparedStatement
			preparedStatement = conn.prepareStatement(sql);
			if (parameterList != null) {
				for (int i = 0; i < parameterList.size(); i++) {
					preparedStatement.setObject(i + 1, parameterList.get(i));
				}
			}
			resultSet = preparedStatement.executeQuery();
			// 游标定位
			// resultSet .beforeFirst();
			// 返回
			bean = new JdbcPrepareStatementDto();
			bean.setResultSet(resultSet);
			bean.setPreparedStatement(preparedStatement);
			return bean;
		} catch (SQLException e) {
			this.close(resultSet, preparedStatement);
			throw e;
		} finally {
		}
	}
	/**
	 * 37将查询数据库结果转化为List<Map<String, Object>>;
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> doResultSet2ListMap(ResultSet resultSet) throws SQLException {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		// 添加序列编号(序号)(注意跟主键ID的不同)
		int sys_no = 0;
		while (resultSet.next()) {
			Map<String, Object> map = this.doResultSet2Map(resultSetMetaData, resultSet);
			// 添加序列编号(序号)(注意跟主键ID的不同)
			sys_no = sys_no + 1;
			map.put(ROW_NUM, new Integer(sys_no).toString());
			listMap.add(map);
		}
		return listMap;
	}
	/**
	 * 
	 * 38 将查询数据库结果转化为Map，值均转化为Object类型;
	 * 
	 * @param resultSetMetaData
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> doResultSet2Map(ResultSetMetaData resultSetMetaData, ResultSet resultSet)
			throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
			int index = i + 1;
			// String columnName = resultSetMetaData.getColumnName(index);
			String field = resultSetMetaData.getColumnLabel(index);
			int columnType = resultSetMetaData.getColumnType(index);
			// 转换类型
			switch (columnType) {
				case java.sql.Types.BIGINT :
					Long long_value = resultSet.getLong(index);
					map.put(field, long_value);
					break;
				case java.sql.Types.INTEGER :
					Integer int_value = resultSet.getInt(index);
					map.put(field, int_value);
					break;
				default :
					Object object_value = resultSet.getObject(index);
					map.put(field, object_value);
					break;
			}
		}
		return map;
	}
	/**
	 * 39
	 * 
	 * 一,通过resultSet.absolute查询;
	 * 
	 * 二,添加序列编号(序号);
	 * 
	 * 三,通过sql, 将查询数据库结果转化为List<Map>
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMapList(Connection conn, String sql, List<Object> parameterList, int pageNo,
			int pageSize) throws SQLException {
		List<Map<String, Object>> listMap = null;
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			bean = findResultSet(conn, sql, parameterList, pageNo, pageSize);
			resultSet = bean.getResultSet();
			preparedStatement = bean.getPreparedStatement();
			listMap = doResultSet2ListMap(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
		return listMap;
	}
	/**
	 * 40通过sql, 将查询数据库结果转化为List<Map>
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMapList(Connection conn, String sql) throws SQLException {
		return this.findMapList(conn, sql, null);
	}
	/**
	 * 41 通过sql,将查询数据库结果转化为List<Map>
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMapList(Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		List<Map<String, Object>> listMap = null;
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		try {
			bean = findResultSet(conn, sql, parameterList);
			resultSet = bean.getResultSet();
			listMap = doResultSet2ListMap(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bean != null) {
				if (bean.getResultSet() != null) {
					bean.getResultSet().close();
				}
				if (bean.getPreparedStatement() != null) {
					bean.getPreparedStatement().close();
				}
			}
		}
		return listMap;
	}
	/**
	 * 42 获取第一个结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findMap(Connection conn, String sql) throws SQLException {
		return this.findMap(conn, sql, null);
	}
	/**
	 * 
	 * 43获取第一个结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findMap(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		List<Map<String, Object>> listMap = findMapList(conn, sql, parameterList);
		if (listMap == null) {
			return null;
		}
		int size = listMap.size();
		if (size == 0) {
			return null;
		}
		if (size >= 1) {
			return listMap.get(0);
		}
		return null;
	}
	/**
	 * 44 获取唯一结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findMapUnique(Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		List<Map<String, Object>> listMap = findMapList(conn, sql, parameterList);
		if (listMap == null) {
			return null;
		}
		int size = listMap.size();
		if (size == 0) {
			return null;
		}
		if (size > 1) {
			throw new RuntimeException("取值不唯一");
		}
		if (size == 1) {
			return listMap.get(0);
		}
		return null;
	}
	/**
	 * 45获取第一个结果并转换成Double型
	 * 
	 * @Description:
	 * 
	 * @Title: findDouble
	 * 
	 * @param @param conn
	 * 
	 * @param @param sql
	 * 
	 * @param @param parameterList
	 * 
	 * @param @return
	 * 
	 * @param @throws SQLException 参数说明
	 * 
	 * @throws
	 */
	@Override
	public Double findDouble(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		Double returnValue = 0.0;
		Map<String, Object> map = this.findMap(conn, sql, parameterList);
		for (String key : map.keySet()) {
			returnValue = Double.parseDouble(map.get(key).toString());
			break;
		}
		return returnValue;
	}
	/**
	 * 46获取第一个结果并转换成Integer型
	 */
	@Override
	public Integer findInteger(String key, Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		Integer returnValue = 0;
		Map<String, Object> map = this.findMap(conn, sql, parameterList);
		if (map.get(key) != null) {
			returnValue = Integer.parseInt(map.get(key).toString());
		}
		return returnValue;
	}
	/**
	 * 46获取第一个结果并转换成Integer型
	 */
	@Override
	public Integer findInteger(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		Integer returnValue = 0;
		Map<String, Object> map = this.findMap(conn, sql, parameterList);
		for (String key : map.keySet()) {
			returnValue = Integer.parseInt(map.get(key).toString());
			break;
		}
		return returnValue;
	}
	/**
	 * 47获取第一个结果并转换成Long型
	 * 
	 */
	@Override
	public Long findLong(String key, Connection conn, String sql, List<Object> parameterList) throws SQLException {
		Long returnValue = 0l;
		Map<String, Object> map = this.findMap(conn, sql, parameterList);
		if (map.get(key) != null) {
			returnValue = Long.parseLong(map.get(key).toString());
		}
		return returnValue;
	}
	/**
	 * 48 获取第一列的多行结果并转换成String型
	 * 
	 */
	@Override
	public List<String> findStringList(String key, Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		List<String> strList = new ArrayList<String>();
		List<Map<String, Object>> mapList = this.findMapList(conn, sql, parameterList);
		for (Map<String, Object> map : mapList) {
			Object object = map.get(key);
			if (object != null) {
				String str = map.get(key).toString();
				strList.add(str);
			}
		}
		return strList;
	}
	/**
	 * 48 获取第一个结果并转换成String型
	 */
	@Override
	public String findString(String key, Connection conn, String sql, List<Object> parameterList) throws SQLException {
		String returnValue = null;
		Map<String, Object> map = this.findMap(conn, sql, parameterList);
		if (map != null) {
			if (map.get(key) != null) {
				returnValue = map.get(key).toString();
			}
		}
		return returnValue;
	}
	/**
	 * 48 获取第一个结果并转换成String型
	 * 
	 */
	@Override
	public String findString(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		String returnValue = null;
		Map<String, Object> map = this.findMap(conn, sql, parameterList);
		if (map != null && map.size() > 0) {
			for (String key : map.keySet()) {
				returnValue = map.get(key).toString();
				break;
			}
		}
		return returnValue;
	}
	/**
	 * 
	 * 49分页返回PageCoreBean
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize,
			String sql, String sqlCount) throws SQLException;
	/**
	 * 
	 * 50 分页返回PageCoreBean
	 * 
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize,
			String sql, List<Object> parameterList, String sqlCount) throws SQLException;
	/**
	 * 
	 * 51分页返回PageCoreBean
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBean(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, String sqlCount) throws SQLException;
	/**
	 * 
	 * 52分页返回PageCoreBean
	 * 
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBean(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, List<Object> parameterList, String sqlCount)
					throws SQLException;
	/**
	 * 53分页返回PageCoreBean
	 * 
	 */
	@Override
	public abstract PageCoreBean<Map<String, Object>> findPageBean(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount) throws SQLException;
	/**
	 * 
	 * 54分页返回PageCoreBean
	 * 
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBean(Connection conn, int pageIndex, int pageSize,
			String sql, List<Object> parameterList, String sqlCount, List<Object> parameterListCount, String sqlPage,
			List<Object> parameterListPage) throws SQLException;
	/**
	 * 
	 * 55分页返回PageCoreBean
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBean(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, List<Object> parameterList, String sqlCount,
			List<Object> parameterListCount, String sqlPage, List<Object> parameterListPage) throws SQLException;
	/**
	 * 56将查询数据库结果转化为List<ArrayList<String>> ;
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> doResultSet2listList(ResultSet resultSet) throws SQLException {
		List<List<String>> listList = new ArrayList<List<String>>();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		List<String> list = new ArrayList<String>();
		/**
		 * 添加列名
		 */
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			int index = i + 1;
			String field = rsmd.getColumnLabel(index);
			list.add(field);
		}
		listList.add(list);
		while (resultSet.next()) {
			list = this.doResultSet2List(rsmd, resultSet);
			listList.add(list);
		}
		return listList;
	}
	/**
	 * 
	 * 57将查询数据库结果转化为List，值均转化为String类型;
	 * 
	 * @param rsmd
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<String> doResultSet2List(ResultSetMetaData rsmd, ResultSet resultSet) throws SQLException {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			int index = i + 1;
			Object objectValue = resultSet.getObject(index);
			if (objectValue != null) {
				list.add(objectValue.toString());
			} else {
				list.add("");
			}
		}
		return list;
	}
	/**
	 * 58通过sql, 将查询数据库结果转化为List<ArrayList<String>>
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> findListList(Connection conn, String sql) throws SQLException {
		return this.findListList(conn, sql, null);
	}
	/**
	 * 59 通过sql, 将查询数据库结果转化为List<List<String>>
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> findListList(Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		List<List<String>> listList = null;
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		try {
			bean = findResultSet(conn, sql, parameterList);
			AssertUtil.isNull(bean, "bean is null ");
			resultSet = bean.getResultSet();
			listList = doResultSet2listList(resultSet);
		} catch (SQLException e) {
			// log.trace("SQLException jdbc map");
			e.printStackTrace();
			throw e;
		} finally {
			if (bean != null) {
				if (bean.getResultSet() != null) {
					bean.getResultSet().close();
				}
				if (bean.getPreparedStatement() != null) {
					bean.getPreparedStatement().close();
				}
			}
		}
		return listList;
	}
	/**
	 * 
	 * 60获取第一个结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<String> findListFirst(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		List<List<String>> listList = findListList(conn, sql, parameterList);
		if (listList == null) {
			return null;
		}
		int size = listList.size();
		if (size == 0) {
			return null;
		}
		if (size >= 1) {
			return listList.get(0);
		}
		return null;
	}
	/**
	 * 
	 * 61获取唯一结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public List<String> findListUnique(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		List<List<String>> listList = findListList(conn, sql, parameterList);
		if (listList == null) {
			return null;
		}
		int size = listList.size();
		if (size == 0) {
			return null;
		}
		if (size > 1) {
			throw new RuntimeException("取值不唯一");
		}
		if (size == 1) {
			return listList.get(0);
		}
		return null;
	}
	/**
	 * 62将查询数据库结果转化为List<JdbcRow> ;
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public JdbcDataDto doResultSet2JdbcData(ResultSet resultSet) throws SQLException {
		JdbcDataDto jdbcData = new JdbcDataDto();
		ArrayList<String> fieldList = new ArrayList<String>();
		ArrayList<JdbcRowDto> rowList = new ArrayList<JdbcRowDto>();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		/**
		 * 添加列名
		 */
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			int index = i + 1;
			String field = rsmd.getColumnLabel(index);
			fieldList.add(field);
		}
		jdbcData.setFieldList(fieldList);
		// 数据
		JdbcRowDto jdbcRow = null;
		while (resultSet.next()) {
			jdbcRow = new JdbcRowDto();
			jdbcRow = this.doResultSet2JdbcRow(rsmd, resultSet);
			rowList.add(jdbcRow);
		}
		jdbcData.setRowList(rowList);
		return jdbcData;
	}
	/**
	 * 
	 * 
	 * 63将查询数据库结果转化为JdbcRow ;
	 * 
	 * @param rsmd
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public JdbcRowDto doResultSet2JdbcRow(ResultSetMetaData rsmd, ResultSet resultSet) throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		JdbcRowDto jdbcRow = new JdbcRowDto();
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			int index = i + 1;
			String field = rsmd.getColumnLabel(index);
			Object valueObject = resultSet.getObject(index);
			if (valueObject != null) {
				list.add(valueObject.toString());
				map.put(field, valueObject);
			} else {
				list.add("");
				map.put(field, null);
			}
		}
		jdbcRow.setList(list);
		jdbcRow.setMap(map);
		return jdbcRow;
	}
	/**
	 * 
	 * 64获取第一个结果
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public JdbcRowDto findJdbcRow(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		List<JdbcRowDto> jdbcRowList = findJdbcData(conn, sql, parameterList).getRowList();
		if (jdbcRowList == null) {
			return null;
		}
		int size = jdbcRowList.size();
		if (size == 0) {
			return null;
		}
		if (size >= 1) {
			return jdbcRowList.get(0);
		}
		return null;
	}
	/**
	 * 65 通过sql,将查询数据库结果转化为List<JdbcRow>
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws SQLException
	 */
	public JdbcDataDto findJdbcData(Connection conn, String sql, List<Object> parameterList) throws SQLException {
		JdbcDataDto jdbcData = null;
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		try {
			bean = findResultSet(conn, sql, parameterList);
			resultSet = bean.getResultSet();
			jdbcData = doResultSet2JdbcData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bean != null) {
				if (bean.getResultSet() != null) {
					bean.getResultSet().close();
				}
				if (bean.getPreparedStatement() != null) {
					bean.getPreparedStatement().close();
				}
			}
		}
		return jdbcData;
	}
	/**
	 * 66找出数据库信息
	 * 
	 * @param driver
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DatabaseBean findDatabaseInfo(String url, String username, String password)
			throws ClassNotFoundException, SQLException {
		Connection conn = null;
		try {
			conn = openConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return this.findDatabaseInfo(conn);
	}
	/**
	 * 67 找出数据库信息
	 * 
	 * @param conn
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DatabaseBean findDatabaseInfo(Connection conn) throws ClassNotFoundException, SQLException {
		DatabaseBean info = null;
		try {
			info = new DatabaseBean();
			DatabaseMetaData dbMetaData = conn.getMetaData();
			// 数据库产品
			info.setDatabaseProductName(dbMetaData.getDatabaseProductName());
			// 数据库产品版本
			info.setDatabaseProductVersion(dbMetaData.getDatabaseProductVersion());
			// 驱动版本
			info.setDriverVersion(dbMetaData.getDriverVersion());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return info;
	}
	/**
	 * 68通过java的api找到表信息;
	 * tableNamePattern
	 *               表名称;可包含单字符通配符("_"),或多字符通配符("%") ;
	 * 
	 */
	public abstract List<TableBean> findTableBeanListByApi(Connection conn, String schemata, String tableNamePattern)
			throws SQLException;
	/**
	 * 69 首个单词首个字母小写，其它单词头个字母转换成大写，并且去掉下划线;
	 * 
	 * 规则:sys_user_id -> sysUserId
	 * 
	 * @param columnName
	 * @return
	 * @see
	 */
	public String findFieldName(String columnName) {
		return StringUtil.findFieldName(columnName);
	}
	/**
	 * 70 头个字母转换成大写，并且去掉下划线;
	 * 
	 * 
	 * 规则:sys_user_id -> SysUserId;
	 * 
	 * @Description: desc @Title: findMethodName @param columnName @return
	 *               参数说明 @throws throws
	 */
	public String findMethodName(String columnName) {
		return StringUtil.findMethodNameByColumn(columnName);
	}
	/**
	 * 71sql列类型所关联的操作类型
	 * 
	 * @Description: desc @Title: findTypeOperate @param columnType @return
	 *               参数说明 @return String 返回类型 @throws
	 */
	public String findTypeOperate(String columnType) {
		// columnType需要全部转换为大写
		String dataType = "";
		EnumUtil enumUtil = new EnumUtil();
		ColumnTypeEnum cte = enumUtil.findEnumItem(ColumnTypeEnum.class, columnType.toUpperCase());
		if (cte != null) {
			dataType = cte.findTypeOperate();
		} else {
			dataType = ColumnTypeEnum.VARCHAR.findTypeOperate();
		}
		return dataType;
	}
	/**
	 * 72 sql列类型所关联的操作类型
	 * 
	 */
	public List<TypeOperateBean> findTypeOperateList(String columnType) {
		// columnType需要全部转换为大写
		List<TypeOperateBean> dataType = null;
		EnumUtil enumUtil = new EnumUtil();
		ColumnTypeEnum cte = enumUtil.findEnumItem(ColumnTypeEnum.class, columnType.toUpperCase());
		if (cte != null) {
			dataType = cte.findTypeOperateBeanList();
		} else {
			dataType = ColumnTypeEnum.VARCHAR.findTypeOperateBeanList();
		}
		return dataType;
	}
	/**
	 * sql列类型转换成数据类型
	 * 
	 */
	public String findDataType_v1(String columnType) {
		String dataType = "";
		// sqlserver需要全部转换为大写
		columnType = columnType.toUpperCase();
		if (columnType.equals("CHAR")) {
			dataType = ColumnBean.dataType_String;
			return dataType;
		}
		if (columnType.equals("VARCHAR")) {
			dataType = ColumnBean.dataType_String;
			return dataType;
		}
		if (columnType.equals("BIT")) {
			dataType = ColumnBean.dataType_Boolean;
			return dataType;
		}
		if (columnType.equals("DECIMAL")) {
			dataType = ColumnBean.dataType_BigDecimal;
			return dataType;
		}
		if (columnType.equals("TINYINT")) {
			dataType = ColumnBean.dataType_Byte;
			return dataType;
		}
		if (columnType.equals("TINYINT UNSIGNED")) {
			dataType = ColumnBean.dataType_Byte;
		}
		if (columnType.equals("SMALLINT")) {
			dataType = ColumnBean.dataType_Short;
			return dataType;
		}
		if (columnType.equals("SMALLINT UNSIGNED")) {
			dataType = ColumnBean.dataType_Short;
			return dataType;
		}
		if (columnType.equals("INT")) {
			dataType = ColumnBean.dataType_Integer;
			return dataType;
		}
		if (columnType.equals("INT UNSIGNED")) {
			dataType = ColumnBean.dataType_Integer;
			return dataType;
		}
		if (columnType.equals("BIGINT")) {
			dataType = ColumnBean.dataType_Long;
			return dataType;
		}
		if (columnType.equals("BIGINT UNSIGNED")) {
			dataType = ColumnBean.dataType_Long;
			return dataType;
		}
		if (columnType.equals("FLOAT")) {
			dataType = ColumnBean.dataType_Float;
			return dataType;
		}
		if (columnType.equals("DOUBLE")) {
			dataType = ColumnBean.dataType_Double;
			return dataType;
		}
		if (columnType.equals("DATE")) {
			dataType = ColumnBean.dataType_Date;
			return dataType;
		}
		if (columnType.equals("TIME")) {
			dataType = ColumnBean.dataType_Date;
			return dataType;
		}
		if (columnType.equals("DATETIME")) {
			dataType = ColumnBean.dataType_Date;
			return dataType;
		}
		if (columnType.equals("TIMESTAMP")) {
			dataType = ColumnBean.dataType_Date;
			return dataType;
		}
		if (columnType.equals("BLOB")) {
			dataType = ColumnBean.dataType_byteArray;
			return dataType;
		}
		if (columnType.equals("LONGBLOB")) {
			dataType = ColumnBean.dataType_byteArray;
			return dataType;
		}
		dataType = ColumnBean.dataType_String;
		return dataType;
	}
	/**
	 * 73sql列类型转换成数据类型
	 * 
	 */
	public String findDataType(String columnType) {
		// columnType需要全部转换为大写
		String dataType = "";
		EnumUtil enumUtil = new EnumUtil();
		ColumnTypeEnum cte = enumUtil.findEnumItem(ColumnTypeEnum.class, columnType.toUpperCase());
		if (cte != null) {
			dataType = cte.getDataType();
		} else {
			dataType = ColumnBean.dataType_String;
		}
		return dataType;
	}
	/**
	 * 74sql列类型转换成数据类型
	 * 
	 */
	public abstract String findDataTypeAll(String columnType);
	/**
	 * 75jdbc元数据,通过java的api找到列信息
	 * 
	 */
	@Override
	public abstract List<ColumnBean> findColumnBeanListByApi(Connection conn, String schemata, String tableName,
			String columnName) throws SQLException;
	/**
	 * 76 jdbc元数据 通过sql查找所有列信息
	 * 
	 */
	public List<ColumnBean> findColumnBeanListByApi(Connection conn, String sql) throws SQLException {
		return this.findColumnBeanListByApi(conn, sql, null);
	}
	/**
	 * 77 jdbc元数据通过sql查找所有列信息
	 * 
	 */
	public List<ColumnBean> findColumnBeanListByApi(Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		List<ColumnBean> columnBeanList = null;
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		try {
			bean = findResultSet(conn, sql, parameterList);
			resultSet = bean.getResultSet();
			columnBeanList = this.findColumnBeanListByApi(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bean != null) {
				if (bean.getResultSet() != null) {
					bean.getResultSet().close();
				}
				if (bean.getPreparedStatement() != null) {
					bean.getPreparedStatement().close();
				}
			}
		}
		return columnBeanList;
	}
	/**
	 * 78jdbc元数据 通过sql查找所有列信息
	 * 
	 */
	public List<ColumnBean> findColumnBeanListByApi(ResultSet resultSet) throws SQLException {
		List<ColumnBean> columnBeanList = new ArrayList<ColumnBean>();
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
			int index = i + 1;
			ColumnBean columnBean = new ColumnBean();
			columnBean.setColumnLabel(resultSetMetaData.getColumnLabel(index));
			columnBean.setCatalog(resultSetMetaData.getCatalogName(index));
			// mysql:getColumnName返回的是sql语句中field的原始名字。getColumnLabel是field的SQL
			// AS的值。
			// oracle:getColumnName与getColumnLabel都是field的SQL AS的值。
			columnBean.setColumnName(resultSetMetaData.getColumnName(index));
			columnBean.setColumnLabel(resultSetMetaData.getColumnLabel(index));
			// 小写
			columnBean.setFieldName(this.findFieldName(columnBean.getColumnLabel()));
			columnBean.setMethodName(this.findMethodName(columnBean.getColumnLabel()));
			// Java 编程语言中类的完全限定名称，方法 ResultSet.getObject
			// 将使用该名称获取指定列中的值。此名称为用于自定义映射关系的类名称
			columnBean.setColumnClassName(resultSetMetaData.getColumnClassName(index));
			// 来自 java.sql.Types 的 SQL 类型
			columnBean.setSqlTypeInt(resultSetMetaData.getColumnType(index));
			// 数据库使用的类型名称。如果列类型是用户定义的类型，则返回完全限定的类型名称。
			columnBean.setSqlTypeStr(resultSetMetaData.getColumnTypeName(index));
			// 允许作为指定列宽度的最大标准字符数
			// log.trace("ColumnDisplaySize=" +
			// resultSetMetaData.getColumnDisplaySize(index));
			columnBean.setColumnDisplaySize(Long.valueOf(resultSetMetaData.getColumnDisplaySize(index)));
			// 获取指定列的指定列宽。对于数值型数据，是指最大精度。对于字符型数据，是指字符串长度。对于日期时间的数据类型，
			// 是指String 表示形式的字符串长度（假定为最大允许的小数秒组件）。对于二进制型数据，是指字节长度。对于 ROWID
			// 数据类型，是指字节长度。对于其列大小不可用的数据类型，则返回 0。
			// log.trace("precision=" +
			// resultSetMetaData.getPrecision(index));
			columnBean.setPrecision(Long.valueOf(resultSetMetaData.getPrecision(index)));
			// 获取指定列的小数点右边的位数。对于其标度不可用的数据类型，则返回 0。
			columnBean.setScale(Long.valueOf(resultSetMetaData.getScale(index)));
			// 给定列的状态是否可以为 null 的判断，此状态值是 columnNoNulls、columnNullable 或
			// columnNullableUnknown 之一
			columnBean.setIsNullInt(Long.valueOf(resultSetMetaData.isNullable(index)));
			// 自定义长度
			columnBean.setLength(columnBean.getColumnDisplaySize());
			// 添加
			columnBeanList.add(columnBean);
		}
		return columnBeanList;
	}
	/**
	 * 82找出列的类型
	 * 
	 */
	public abstract String findSqlType(String dataType, Long length, Long scale);
	/**
	 * 83添加新的一列
	 * 
	 */
	public abstract void addColumn(Connection conn, String tableName, String columnName, String columnType, Long length,
			Long scale) throws SQLException;
	/**
	 * 84构造新的一列的sql
	 * 
	 */
	public abstract String createSqlForAddColumn(String tableName, String columnName, String columnType, Long length,
			Long scale);
	/**
	 * 85 修改列的定义
	 * 
	 */
	public abstract void doChangeColumn(Connection conn, String tableName, String columnName, String columnType,
			Long length, Long scale) throws SQLException;
	/**
	 * 86构造sql,修改列的定义
	 * 
	 */
	public abstract String createSqlForChangeColumn(String tableName, String columnName, String columnType, Long length,
			Long scale);
	/**
	 * 87添加外键
	 * 
	 */
	public abstract void addForeignKey(Connection conn, String pkTableName, String pkField, String fkTableName,
			String fkField) throws SQLException;
	/**
	 * 88构造添加外键的sql
	 * 
	 */
	public abstract String createSqlForAddForeignKey(String pkTableName, String pkField, String fkTableName,
			String fkField) throws SQLException;
	/**
	 * 89删除外键
	 * 
	 */
	public abstract void doDropForeignKey(Connection conn, String tableName, String keyName) throws SQLException;
	/**
	 * 90构造删除外键的sql
	 * 
	 */
	public abstract String createSqlForDropForeignKey(String tableName, String keyName) throws SQLException;
	/**
	 * 91创建表
	 * 
	 */
	@Override
	public void createTable(Connection conn, CreateTableListBean bean) throws SQLException {
		CreateTableBean parentBean = bean.getParentTableBean();
		this.createTable(conn, parentBean);
		List<CreateTableBean> jdbcTableBeanList = bean.getChildTableBeanList();
		for (CreateTableBean childBean : jdbcTableBeanList) {
			this.createTable(conn, childBean);
		}
	}
	/**
	 * 92创建表
	 * 
	 */
	public abstract void createTable(Connection conn, CreateTableBean bean) throws SQLException;
	/**
	 * 93修改表结构
	 * 
	 */
	@Override
	public void alterTable(Connection conn, CreateTableListBean bean) throws SQLException {
		CreateTableBean parentBean = bean.getParentTableBean();
		this.alterTable(conn, parentBean);
		List<CreateTableBean> jdbcTableBeanList = bean.getChildTableBeanList();
		for (CreateTableBean childBean : jdbcTableBeanList) {
			this.alterTable(conn, childBean);
		}
	}
	/**
	 * 94 修改表结构
	 * 
	 */
	public abstract void alterTable(Connection conn, CreateTableBean bean) throws SQLException;
	/**
	 * 95检查表是否存在
	 * 
	 * @throws SQLException
	 */
	public abstract boolean isTableExist(Connection conn, String tableName) throws SQLException;
	/**
	 * 96 创建或修改表的结构
	 * 
	 */
	@Override
	public void createOrAlterTable(Connection conn, CreateTableListBean bean) throws SQLException {
		CreateTableBean parentBean = bean.getParentTableBean();
		this.createOrAlterTable(conn, parentBean);
		List<CreateTableBean> jdbcTableBeanList = bean.getChildTableBeanList();
		for (CreateTableBean childBean : jdbcTableBeanList) {
			this.createOrAlterTable(conn, childBean);
		}
	}
	/**
	 * 97创建或修改表的结构
	 * 
	 */
	public abstract void createOrAlterTable(Connection conn, CreateTableBean bean) throws SQLException;
	/**
	 * 98 往表插入数据
	 * 
	 */
	@Override
	public RowBean insertTable(Connection conn, RowBean rowBean, TableBean tableBean) throws SQLException {
		List<ColumnBean> columnBeanList = rowBean.getColumnBeanList();
		// 先构造key与value
		StringBuilder keySb = new StringBuilder();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		StringBuilder valueSb = new StringBuilder();
		for (ColumnBean columnBean : columnBeanList) {
			// 追加key
			keySb.append(columnBean.getColumnLabel().toUpperCase()).append(",");
			// 追加value
			valueSb.append("?").append(",");
			if (columnBean.getDataType().equals(ColumnBean.dataType_Number)) {
				if (StringUtil.isBlank(columnBean.getValue())) {
					double d = 0.0;
					parameterList.add(d);
				} else {
					parameterList.add(columnBean.getValue());
				}
			} else if (columnBean.getDataType().equals(ColumnBean.dataType_Date)) {
				if (StringUtil.isBlank(columnBean.getValue())) {
					parameterList.add(null);
				} else {
					parameterList.add(columnBean.getValue());
				}
			} else {
				parameterList.add(columnBean.getValue());
			}
		}
		// 删除最后的逗号
		if (keySb.length() > 0) {
			keySb.deleteCharAt(keySb.length() - 1);
		}
		// 删除最后的逗号
		if (valueSb.length() > 0) {
			valueSb.deleteCharAt(valueSb.length() - 1);
		}
		this.insertObject(conn, tableBean.getTableName(), keySb, valueSb, parameterList);
		return rowBean;
	}
	/**
	 * 
	 * 99 对象转为sql(insert)
	 * 
	 * @param conn
	 * @param tableName
	 * @param keySb
	 * @param valueSb
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 */
	public String insertObject(Connection conn, String tableName, StringBuilder keySb, StringBuilder valueSb,
			ArrayList<Object> parameterList) throws SQLException {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("insert into ");
		sqlSb.append(tableName);
		sqlSb.append("(");
		sqlSb.append(keySb);
		sqlSb.append(")");
		sqlSb.append(" values ");
		sqlSb.append("(");
		sqlSb.append(valueSb);
		sqlSb.append(")");
		String sql = sqlSb.toString();
		if (keySb.length() == 0) {
			log.trace("sql=" + sql);
			throw new NullPointerException("实体类的域是不是没加上@Column");
		}
		// 解析并打印prepareSQL
		// this.doPrintSqlTarget(sql, parameterList);
		// 执行sql
		// 执行Insert语句，得到主键
		return executeInsert(conn, sql, parameterList);
	}
	/**
	 * 100往表更新数据
	 * 
	 */
	@Override
	public RowBean updateTable(Connection conn, RowBean rowBean, TableBean tableBean) throws SQLException {
		String pk = null;
		String pkValue = null;
		List<ColumnBean> columnBeanList = rowBean.getColumnBeanList();
		for (ColumnBean columnBean : columnBeanList) {
			if (columnBean.getIsPk()) {
				pk = columnBean.getColumnLabel();
				pkValue = columnBean.getValue();
				break;
			}
		}
		// 先构造key与value
		StringBuilder sb = new StringBuilder();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		for (ColumnBean columnBean : columnBeanList) {
			if (columnBean.getIsPk()) {
			} else {
				// 追加key
				sb.append(columnBean.getColumnLabel().toUpperCase()).append("=");
				// 追加value
				sb.append("?");
				sb.append(",");
				if (columnBean.getDataType().equals(ColumnBean.dataType_Number)) {
					if (StringUtil.isBlank(columnBean.getValue())) {
						double d = 0.0;
						parameterList.add(d);
					} else {
						parameterList.add(columnBean.getValue());
					}
				} else if (columnBean.getDataType().equals(ColumnBean.dataType_Date)) {
					if (StringUtil.isBlank(columnBean.getValue())) {
						parameterList.add(null);
					} else {
						parameterList.add(columnBean.getValue());
					}
				} else {
					parameterList.add(columnBean.getValue());
				}
			}
		}
		// 删除最后的逗号
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		this.updateObject(conn, pk, pkValue, tableBean.getTableName(), sb, parameterList);
		return rowBean;
	}
	/**
	 * 
	 * 101更新对象(构造更新数据的sql)
	 * 
	 * @param conn
	 * @param primaryKey
	 * @param pkValue
	 * @param tableName
	 * @param sb
	 * @param parameterList
	 * @throws SQLException
	 * @throws SQLException
	 */
	public int updateObject(Connection conn, String primaryKey, String pkValue, String tableName, StringBuilder sb,
			ArrayList<Object> parameterList) throws SQLException {
		StringBuilder sqlStringBuilder = new StringBuilder();
		if (sb.length() == 0) {
			throw new NullPointerException("实体类的域是不是没加上@Column");
		}
		sqlStringBuilder.append("UPDATE  ");
		sqlStringBuilder.append(tableName);
		sqlStringBuilder.append(" SET  ");
		sqlStringBuilder.append(sb);
		// id
		sqlStringBuilder.append(" WHERE  ");
		sqlStringBuilder.append(primaryKey).append("=");
		sqlStringBuilder.append("'" + pkValue.trim() + "'");
		String sql = sqlStringBuilder.toString();
		// 执行sql
		return execute(conn, sql, parameterList);
	}
	/**
	 * 102往表插入或更新数据
	 * 
	 */
	@Override
	public CreateTableListBean insertOrUpdateTable(Connection conn, CreateTableListBean bean) throws SQLException {
		CreateTableBean parentBean = bean.getParentTableBean();
		// 主表
		parentBean = this.insertOrUpdateTable(conn, parentBean);
		// 明细表
		List<CreateTableBean> childBeanList = bean.getChildTableBeanList();
		for (CreateTableBean childBean : childBeanList) {
			childBean = this.insertOrUpdateTable(conn, childBean);
		}
		return bean;
	}
	/**
	 * 103 往表插入或更新数据
	 * 
	 */
	@Override
	public CreateTableBean insertOrUpdateTable(Connection conn, CreateTableBean bean) throws SQLException {
		// 主键的列
		String pk = null;
		// 主键的值
		String pkValue = null;
		// 是否存在主键
		String isPk = null;
		TableBean tableBean = bean.getTableBean();
		List<RowBean> rowBeanList = bean.getRowBeanList();
		for (RowBean rowBean : rowBeanList) {
			List<ColumnBean> ColumnBeanList = rowBean.getColumnBeanList();
			for (ColumnBean columnBean : ColumnBeanList) {
				if (columnBean.getIsPk()) {
					pk = columnBean.getColumnLabel();
					pkValue = columnBean.getValue();
					break;
				}
			}
			if (StringUtil.isNotBlank(pkValue)) {
				// 表名不再加双引号
				// 是否存在主键
				String sql = "select " + pk + " from " + tableBean.getTableName() + " where " + pk + "=?";
				List<Object> parameterList = new ArrayList<Object>();
				parameterList.add(pkValue);
				isPk = this.findString(conn, sql, parameterList);
				break;
			}
		}
		if (StringUtil.isNotBlank(pkValue)) {
			for (RowBean rowBean : rowBeanList) {
				if (StringUtil.isNotBlank(isPk)) {
					rowBean = this.updateTable(conn, rowBean, tableBean);
				} else {
					rowBean = this.insertTable(conn, rowBean, tableBean);
				}
			}
		}
		return bean;
	}
	/**
	 * 104取得当前连接的Schemata
	 * 
	 */
	public String findSchemata(Connection conn) throws SQLException {
		String schemata = conn.getCatalog();
		return schemata;
	}
	/**
	 * 105主键是否存在
	 * 
	 */
	public boolean isPkExist(CreateTableBean jdbcTableBean) {
		// 检查主键
		List<RowBean> rowBeanList = jdbcTableBean.getRowBeanList();
		for (RowBean rowBean : rowBeanList) {
			List<ColumnBean> ColumnBeanList = rowBean.getColumnBeanList();
			for (ColumnBean cm : ColumnBeanList) {
				Boolean isPkExist = cm.getIsPk();
				if (isPkExist) {
					return isPkExist;
				}
			}
		}
		return false;
	}
	/**
	 * 106 得到表的主键id(列名)
	 * 
	 * @param conn
	 * @param schemaPattern
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public String findTablePrimaryKeyByApi(Connection conn, String schemaPattern, String tableName)
			throws SQLException {
		if (conn == null) {
			throw new RuntimeException("Connection不能为空");
		}
		String catalog = conn.getCatalog();
		// schemaPattern指数据名
		if (StringUtil.isBlank(schemaPattern)) {
			// log.trace("catalog =" + catalog);
			schemaPattern = catalog;
		}
		if (schemaPattern != null) {
			// oracle需要大写
			// schemaPattern = schemaPattern.toUpperCase();
		}
		String primaryKey = null;
		ResultSet resultSet = null;
		DatabaseMetaData dbmd = conn.getMetaData();
		// resultSet = dbmd.getPrimaryKeys(null, schemaPattern,
		// tableName.toUpperCase());
		resultSet = dbmd.getPrimaryKeys(catalog, schemaPattern, tableName);
		if (resultSet == null) {
			return null;
		}
		if (resultSet.next()) {
			primaryKey = resultSet.getString("column_name");
			// primarykey = resultSet.getString("COLUMN_NAME");
		}
		return primaryKey;
	}
	/**
	 * 
	 * 110map参数转成sql语句,拼装where条件的sql
	 * 
	 */
	@Override
	public String findSqlWhere(Map<String, Object> parameterMap) {
		// 构建where条件的sql
		StringBuilder sqlWhereStringBuilder = new StringBuilder();
		Set<String> keyList = parameterMap.keySet();
		if (keyList.size() > 0) {
			sqlWhereStringBuilder.append(" where ");
			for (String key : keyList) {
				sqlWhereStringBuilder.append(key);
				sqlWhereStringBuilder.append("=");
				Object valueObject = parameterMap.get(key);
				if (valueObject instanceof String) {
					sqlWhereStringBuilder.append("'");
					sqlWhereStringBuilder.append(valueObject);
					sqlWhereStringBuilder.append("'");
				} else {
					sqlWhereStringBuilder.append(valueObject);
				}
				sqlWhereStringBuilder.append(",");
			}
			// 删除最后的逗号
			sqlWhereStringBuilder.deleteCharAt(sqlWhereStringBuilder.length() - 1);
		}
		return sqlWhereStringBuilder.toString();
	}
	/**
	 * 111分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 * @param conn
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @param parameterMap
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn, int pageIndex, int pageSize,
			String sql, Map<String, Object> parameterMap, String sqlCount) throws SQLException;
	/**
	 * 
	 * 112分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount)
					throws SQLException;
	/**
	 * 
	 * 113分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount,
			Map<String, Object> parameterMapCount) throws SQLException;
	/**
	 * 
	 * 114分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn, int pageIndex, int pageSize,
			String sql, Map<String, Object> parameterMap, String sqlCount, Map<String, Object> parameterMapCount,
			String sqlPage, Map<String, Object> parameterMapPage) throws SQLException;
	/**
	 * 
	 * 115分页返回PageCoreBean( 构建where条件的sql)
	 * 
	 * @param conn
	 * @param sql
	 * @param parameters
	 * @param page
	 * @param sqlCount
	 * @return
	 * @throws SQLException
	 */
	public abstract PageCoreBean<Map<String, Object>> findPageBeanForWhere(Connection conn,
			PageCoreBean<Map<String, Object>> page, String sql, Map<String, Object> parameterMap, String sqlCount,
			Map<String, Object> parameterMapCount, String sqlPage, Map<String, Object> parameterMapPage)
					throws SQLException;
	/**
	 * 116 自定义SQL查询(where条件)
	 * 
	 */
	public SqlCustomBean doBuildSql(String sql, Map<String, Object> parameterMap) {
		SqlCustomBean bean = new SqlCustomBean();
		bean.setPage(false);
		bean.setSqlSub(sql);
		Set<String> keyList = parameterMap.keySet();
		for (String key : keyList) {
			bean.getWhereFieldList().add(key);
			bean.getValueMap().put(key, parameterMap.get(key));
		}
		String sqlFinal = this.doBuildSql(bean);
		bean.setSql(sqlFinal);
		return bean;
	}
	/**
	 * 117自定义SQL查询
	 * 
	 */
	public String doBuildSql(SqlCustomBean bean) {
		if (StringUtil.isNotBlank(bean.getSqlSub())) {
			StringBuilder stringBuffer = new StringBuilder();
			stringBuffer.append("(");
			stringBuffer.append(bean.getSqlSub());
			stringBuffer.append(") as t");
			bean.setTableName(stringBuffer.toString());
		}
		return doBuildSqlByTable(bean);
	}
	/**
	 * 118自定义SQL查询(通过表名来查询)
	 * 
	 */
	public String doBuildSqlByTable(SqlCustomBean bean) {
		EnumUtil enumUtil = new EnumUtil();
		StringBuilder stringBuffer = null;
		String sqlQuery = "*";
		String sqlOrder = "";
		String sqlWhere = "";
		List<String> queryFieldList = bean.getQueryFieldList();
		List<String> orderFieldList = bean.getOrderFieldList();
		List<String> whereFieldList = bean.getWhereFieldList();
		if (queryFieldList != null && queryFieldList.size() > 0) {
			stringBuffer = new StringBuilder();
			for (int i = 0; i < queryFieldList.size(); i++) {
				String queryFieldStr = queryFieldList.get(i);
				String[] queryFieldArray = queryFieldStr.split("\\#");
				String typeQuery = queryFieldArray[0];
				if (queryFieldArray.length == 2 && SqlCustomBean.TypeQuery.equals(typeQuery)) {
					String queryField = queryFieldArray[1];
					stringBuffer.append(queryField).append(",");
				}
			}
			if (StringUtil.isNotBlank(stringBuffer.toString())) {
				stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			}
			stringBuffer.append(" ");
			sqlQuery = stringBuffer.toString();
		}
		if (whereFieldList != null && whereFieldList.size() > 0) {
			stringBuffer = new StringBuilder();
			for (int i = 0; i < whereFieldList.size(); i++) {
				boolean isAddValue = false;
				String whereFieldStr = whereFieldList.get(i);
				Object whereValueList = bean.getValueMap().get(whereFieldStr);
				String[] whereFieldArray = whereFieldStr.split("\\#");
				String typeWhere = whereFieldArray[0];
				// 值是否为空
				if (StringUtil.isBlank(whereValueList)) {
					continue;
				}
				String[] whereValueArray = whereValueList.toString().split(",");
				// 数组的值是否为空
				if (StringUtil.isBlank(whereValueArray)) {
					continue;
				}
				if (whereFieldArray.length == 3 && SqlCustomBean.TypeWhere.equals(typeWhere)) {
					String whereField = whereFieldArray[1];
					String where = whereFieldArray[2];
					if (!isAddValue) {
						if (where.equals(SqlCustomBean.TypeOperateIn)) {
							isAddValue = true;
							if (stringBuffer.toString().contains(" where ")) {
								stringBuffer.append(" and ");
							} else {
								stringBuffer.append(" where ");
							}
							stringBuffer.append(" ");
							stringBuffer.append(whereField);
							stringBuffer.append(" ");
							stringBuffer.append(enumUtil.find(SqlCustomEnum.class, "getType", where));
							stringBuffer.append(" ( ");
							for (String whereValue : whereValueArray) {
								stringBuffer.append("?");
								stringBuffer.append(",");
							}
							stringBuffer.deleteCharAt(stringBuffer.length() - 1);
							stringBuffer.append(" ) ");
							stringBuffer.append(" ");
						}
					}
					if (!isAddValue) {
						if (where.equals(SqlCustomBean.TypeOperateBetween)) {
							// Between如果只有一个值的话，不查询这个条件
							if (whereValueArray.length == 1) {
								continue;
							}
							isAddValue = true;
							if (stringBuffer.toString().contains(" where ")) {
								stringBuffer.append(" and ");
							} else {
								stringBuffer.append(" where ");
							}
							stringBuffer.append(" ");
							stringBuffer.append(whereField);
							stringBuffer.append(" ");
							stringBuffer.append(enumUtil.find(SqlCustomEnum.class, "getType", where));
							stringBuffer.append("  ");
							stringBuffer.append(" ? ");
							stringBuffer.append(" and  ");
							stringBuffer.append(" ? ");
							stringBuffer.append("  ");
							stringBuffer.deleteCharAt(stringBuffer.length() - 1);
							stringBuffer.append("  ");
							stringBuffer.append(" ");
						}
					}
					if (!isAddValue) {
						if (stringBuffer.toString().contains(" where ")) {
							stringBuffer.append(" and ");
						} else {
							stringBuffer.append(" where ");
						}
						stringBuffer.append(" ");
						stringBuffer.append(whereField);
						stringBuffer.append(" ");
						stringBuffer.append(enumUtil.find(SqlCustomEnum.class, "getType", where));
						stringBuffer.append(" ? ");
						stringBuffer.append(" ");
					}
				}
			}
			if (StringUtil.isNotBlank(stringBuffer.toString())) {
				stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			}
			stringBuffer.append(" ");
			sqlWhere = stringBuffer.toString();
		}
		if (orderFieldList != null && orderFieldList.size() > 0) {
			stringBuffer = new StringBuilder();
			for (int i = 0; i < orderFieldList.size(); i++) {
				String orderFieldStr = orderFieldList.get(i);
				String[] orderFieldArray = orderFieldStr.split("\\#");
				String typeOrder = orderFieldArray[0];
				if (orderFieldArray.length == 3 && SqlCustomBean.TypeOrder.equals(typeOrder)) {
					if (i == 0) {
						stringBuffer.append(" order by ");
					}
					String orderFileld = orderFieldArray[1];
					String order = orderFieldArray[2];
					stringBuffer.append(" ");
					stringBuffer.append(orderFileld);
					stringBuffer.append(" ");
					stringBuffer.append(enumUtil.find(SqlCustomEnum.class, "getType", order));
					stringBuffer.append(",");
				}
			}
			if (StringUtil.isNotBlank(stringBuffer.toString())) {
				stringBuffer.deleteCharAt(stringBuffer.length() - 1);
			}
			stringBuffer.append(" ");
			sqlOrder = stringBuffer.toString();
		}
		stringBuffer = new StringBuilder();
		stringBuffer.append(" select ");
		stringBuffer.append(sqlQuery);
		stringBuffer.append(" from ");
		stringBuffer.append(bean.getTableName());
		stringBuffer.append(sqlWhere);
		stringBuffer.append(sqlOrder);
		return stringBuffer.toString();
	}
	/**
	 * 
	 * 119找出整个数据库的所有表名
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @param schemata
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public abstract List<String> findTableNameList_v1(String schemata, String url, String username, String password)
			throws Exception;
	/**
	 * 120通过sql找到表信息
	 * 
	 * @Description @Title findTableBeanList @param conn @param
	 *              tableName @return return @throws SQLException 参数说明 @return
	 *              List <TableBean> 返回类型 @throws
	 */
	public abstract List<TableBean> queryTableBeanList(Connection conn, String schemata, String tableName)
			throws SQLException;
	/**
	 * 121
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @param schemata
	 * @param tableName
	 * @param columnName
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public abstract List<ColumnBean> queryColumnBeanList(Connection conn) throws SQLException;
	/**
	 * 122 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @param schemata
	 * @param tableName
	 * @param columnName
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public abstract List<ColumnBean> queryColumnBeanList(Connection conn, String tableName, String columnName)
			throws SQLException;
	/**
	 * 123
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 */
	public abstract List<ColumnBean> queryColumnBeanList(Connection conn, String schemata, String tableName,
			String columnName) throws SQLException;
	/**
	 * 121
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @param schemata
	 * @param tableName
	 * @param columnName
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public abstract List<ColumnBean> findColumnBeanList(Connection conn) throws SQLException;
	/**
	 * 122 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 * @param schemata
	 * @param tableName
	 * @param columnName
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public abstract List<ColumnBean> findColumnBeanList(Connection conn, String tableName, String columnName)
			throws SQLException;
	/**
	 * 123
	 * 
	 * 通过jdbc的系统表sql查找列信息
	 * 
	 * mysql不指定表名和列名将查找所有表的所有列的信息
	 * 
	 * oracle需要指定表名和列名
	 * 
	 */
	public abstract List<ColumnBean> findColumnBeanList(Connection conn, String schemata, String tableName,
			String columnName) throws SQLException;
	/**
	 * 124
	 * 
	 * 通过schemata查找所有的视图名
	 * 
	 * @param conn
	 * @param schemata
	 * @return
	 * @throws SQLException
	 */
	public abstract List<TableBean> findViewList(Connection conn, String schemata) throws SQLException;
	/**
	 * 125
	 * 
	 * 通过schemata和name查找视图
	 * 
	 * @param conn
	 * @param schemata
	 * @param viewName
	 *            指定视图名
	 * @return
	 * @throws SQLException
	 */
	public abstract List<TableBean> findViewList(Connection conn, String schemata, String viewName) throws SQLException;
	/**
	 * 126
	 * 
	 * 通过sql找到表或视图
	 * 
	 * @Description @Title findObjectList @param conn @param tableName @return
	 *              return @throws SQLException 参数说明 @return List
	 *              <TableBean> 返回类型 @throws
	 */
	public List<TableBean> findSysObjectList(Connection conn, String schemata, String tableName) throws SQLException {
		List<TableBean> tableBeanList = this.queryTableBeanList(conn, schemata, tableName);
		tableBeanList.addAll(this.findViewList(conn, schemata, tableName));
		return tableBeanList;
	}
	/**
	 * 将查询数据库结果转化为Object类型;
	 * 
	 * @Title: doResultSet2ObjectByColumnKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param key
	 * @param rsmd
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 *             返回类型 Object
	 */
	public Object doResultSet2ObjectByColumnKey(String key, ResultSetMetaData rsmd, ResultSet resultSet)
			throws SQLException {
		Object returnObject = null;
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			int index = i + 1;
			// String columnName = rsmd.getColumnName(index);
			String field = rsmd.getColumnLabel(index);
			int columnType = rsmd.getColumnType(index);
			// 转换类型
			switch (columnType) {
				case java.sql.Types.BIGINT :
					Long long_value = resultSet.getLong(index);
					returnObject = long_value;
					break;
				case java.sql.Types.INTEGER :
					Integer int_value = resultSet.getInt(index);
					returnObject = int_value;
					break;
				default :
					Object object_value = resultSet.getObject(index);
					returnObject = object_value;
					break;
			}
			// 如果需要查询的列名相等
			if (field.equals(key)) {
				break;
			}
		}
		return returnObject;
	}
	/**
	 * 将查询数据库结果转化为List< Object>
	 * 
	 * @Title:
	 * @Description:
	 *
	 * 				参数说明
	 * @param key
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 *             返回类型 List<Object>
	 */
	public List<Object> doResultSet2ListObjectByColumnKey(String key, ResultSet resultSet) throws SQLException {
		List<Object> listObject = new ArrayList<Object>();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		while (resultSet.next()) {
			Object object = this.doResultSet2ObjectByColumnKey(key, rsmd, resultSet);
			listObject.add(object);
		}
		return listObject;
	}
	/**
	 * 通过sql, 将查询数据库结果转化为List< Object>
	 * 
	 * @Title: findObjectListByColumnKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param key
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 *             返回类型 List<Object>
	 */
	public List<Object> findObjectListByColumnKey(String key, Connection conn, String sql) throws SQLException {
		return findObjectListByColumnKey(key, conn, sql, null);
	}
	/**
	 * 通过sql,将查询数据库结果转化为List<Object>
	 * 
	 * @Title: findObjectListByColumnKey
	 * @Description:
	 *
	 * 				参数说明
	 * @param key
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws SQLException
	 *             返回类型 List<Object>
	 */
	public List<Object> findObjectListByColumnKey(String key, Connection conn, String sql, List<Object> parameterList)
			throws SQLException {
		List<Object> listObject = null;
		JdbcPrepareStatementDto bean = null;
		ResultSet resultSet = null;
		try {
			bean = findResultSet(conn, sql, parameterList);
			resultSet = bean.getResultSet();
			listObject = doResultSet2ListObjectByColumnKey(key, resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bean != null) {
				if (bean.getResultSet() != null) {
					bean.getResultSet().close();
				}
				if (bean.getPreparedStatement() != null) {
					bean.getPreparedStatement().close();
				}
			}
		}
		return listObject;
	}
}
