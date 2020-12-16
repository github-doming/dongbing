package c.x.all.complex.c3p0.nut;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource_c3p0 {

	/**
	 * 得到数据库的连接
	 * 
	 * @Title: findConnection_sqlserver
	 * @Description:
	 * @return
	 * @throws SQLException
	 * @throws PropertyVetoException 参数说明
	 * @return Connection 返回类型
	 * @throws
	 */
	public Connection findConnection_sqlserver() throws SQLException,
			PropertyVetoException {

		// sql server 2000
		// String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		// sql server
		String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		String url = null;
		String user = null;
		String password = null;
		if (true) {

			url = "jdbc:sqlserver://kp99test.sqlserver.rds.aliyuncs.com:3433;databasename=KPmini";
			user = "kp";
			password = "kp99cn";
		}

		return findComboPooledDataSource_v3(driver, url, user, password)
				.getConnection();
	}

	/**
	 * 得到数据库的连接
	 * 
	 * @Title: findConnection_mysql
	 * @Description:
	 * @return
	 * @throws SQLException
	 * @throws PropertyVetoException 参数说明
	 * @return Connection 返回类型
	 * @throws
	 */
	public Connection findConnection_mysql() throws SQLException,
			PropertyVetoException {
		String driver = "com.mysql.jdbc.Driver";
		// String driver = "org.gjt.mm.mysql.Driver";

			String url = "jdbc:mysql://127.0.0.1:3306/cjx?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Hongkong&zeroDateTimeBehavior=CONVERT_TO_NULL";

		String user = "root";
		// String password = "1";
		String password = "";
		return findComboPooledDataSource_v3(driver, url, user, password)
				.getConnection();
	}

	public synchronized static ComboPooledDataSource findComboPooledDataSource_v3(
			String driverClass, String jdbcUrl, String user, String password)
			throws PropertyVetoException {
		ComboPooledDataSource comboPooledDataSource = null;

		try {
			comboPooledDataSource = new ComboPooledDataSource();
			/**
			 * 必要配置
			 */
			comboPooledDataSource.setDriverClass(driverClass); // 驱动器
			comboPooledDataSource.setJdbcUrl(jdbcUrl); // 数据库url
			comboPooledDataSource.setUser(user); // 用户名
			comboPooledDataSource.setPassword(password); // 密码
			// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3
			comboPooledDataSource.setInitialPoolSize(3);
			// 　　连接池中保留的最小连接数。
			comboPooledDataSource.setMinPoolSize(10); // 最少连接数
			// 连接池中保留的最大连接数。Default: 15
			comboPooledDataSource.setMaxPoolSize(15); // 最大连接数
			// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
			comboPooledDataSource.setAcquireIncrement(3); // 连接数的增量
			// 每60秒检查所有连接池中的空闲连接。Default: 0
			comboPooledDataSource.setIdleConnectionTestPeriod(60);

			// 　　如果设为true那么在取得连接的同时将校验连接的有效性。Default: false
			//comboPooledDataSource.setTestConnectionOnCheckin(false);
			comboPooledDataSource.setTestConnectionOnCheckin(true);
			
			
			// 　　因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的
			//
			//
			// 　　时候都将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable
			//
			//
			// 　　等方法来提升连接测试的性能。Default: false
			comboPooledDataSource.setTestConnectionOnCheckout(false);

			return comboPooledDataSource;
		} catch (PropertyVetoException e) {

			e.printStackTrace();
			throw e;
		}

	}

	public synchronized static ComboPooledDataSource findComboPooledDataSource_v2(
			String driverClass, String jdbcUrl, String user, String password)
			throws PropertyVetoException {
		ComboPooledDataSource comboPooledDataSource = null;
		try {
			comboPooledDataSource = new ComboPooledDataSource();
			/**
			 * 必要配置
			 */
			comboPooledDataSource.setDriverClass(driverClass); // 驱动器
			comboPooledDataSource.setJdbcUrl(jdbcUrl); // 数据库url
			comboPooledDataSource.setUser(user); // 用户名
			comboPooledDataSource.setPassword(password); // 密码
			// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3
			comboPooledDataSource.setInitialPoolSize(3);
			// 　　连接池中保留的最小连接数。
			comboPooledDataSource.setMinPoolSize(10); // 最少连接数
			// 连接池中保留的最大连接数。Default: 15
			comboPooledDataSource.setMaxPoolSize(15); // 最大连接数
			// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
			comboPooledDataSource.setAcquireIncrement(3); // 连接数的增量
			// 每60秒检查所有连接池中的空闲连接。Default: 0
			comboPooledDataSource.setIdleConnectionTestPeriod(60);

			// 　　如果设为true那么在取得连接的同时将校验连接的有效性。Default: false
			comboPooledDataSource.setTestConnectionOnCheckin(true);
			// 　　因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的
			//
			//
			// 　　时候都将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable
			//
			//
			// 　　等方法来提升连接测试的性能。Default: false
			comboPooledDataSource.setTestConnectionOnCheckout(false);

			return comboPooledDataSource;
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public synchronized static ComboPooledDataSource findComboPooledDataSource_v1(
			String driverClass, String jdbcUrl, String user, String password)
			throws PropertyVetoException {
		ComboPooledDataSource comboPooledDataSource = null;
		try {
			comboPooledDataSource = new ComboPooledDataSource();
			/**
			 * 必要配置
			 */
			comboPooledDataSource.setDriverClass(driverClass); // 驱动器
			comboPooledDataSource.setJdbcUrl(jdbcUrl); // 数据库url
			comboPooledDataSource.setUser(user); // 用户名
			comboPooledDataSource.setPassword(password); // 密码
			// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3
			comboPooledDataSource.setInitialPoolSize(3);
			// 　　连接池中保留的最小连接数。
			comboPooledDataSource.setMinPoolSize(10); // 最少连接数
			// 连接池中保留的最大连接数。Default: 15
			comboPooledDataSource.setMaxPoolSize(15); // 最大连接数
			// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
			comboPooledDataSource.setAcquireIncrement(3); // 连接数的增量
			// 每60秒检查所有连接池中的空闲连接。Default: 0
			comboPooledDataSource.setIdleConnectionTestPeriod(60);

			// 　　如果设为true那么在取得连接的同时将校验连接的有效性。Default: false
			comboPooledDataSource.setTestConnectionOnCheckin(true);

			// 　　因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的
			//
			//
			// 　　时候都将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable
			//
			//
			// 　　等方法来提升连接测试的性能。Default: false
			comboPooledDataSource.setTestConnectionOnCheckout(false);
			/**
			 * 其它详细配置
			 */

			// 定义在从数据库获取新连接失败后重复尝试的次数。Default: 30
			comboPooledDataSource.setAcquireRetryAttempts(30);
			// 两次连接中间隔时间，单位毫秒。Default: 1000
			comboPooledDataSource.setAcquireRetryDelay(1000);
			// 连接关闭时默认将所有未提交的操作回滚。Default: false
			comboPooledDataSource.setAutoCommitOnClose(false);
			// 获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效
			//
			//
			// 　　保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试
			//
			//
			// 　　获取连接失败后该数据源将申明已断开并永久关闭。Default: false
			comboPooledDataSource.setBreakAfterAcquireFailure(false);
			// 当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出
			//
			//
			// 　　SQLException,如设为0则无限期等待。单位毫秒。Default: 0
			comboPooledDataSource.setCheckoutTimeout(100);
			// 　最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
			comboPooledDataSource.setMaxIdleTime(60);

			/*
			 * 　JDBC的标准参数，用以控制数据源内加载的PreparedStatements数量。但由于预缓存的statements
			 * 
			 * 
			 * 　　属于单个connection而不是整个连接池。所以设置这个参数需要考虑到多方面的因素。
			 * 
			 * 
			 * 　　如果maxStatements与maxStatementsPerConnection均为0，则缓存被关闭。Default:0
			 */

			// cpds.setMaxStatements(100);
			/*
			 * 　maxStatementsPerConnection定义了连接池内单个连接所拥有的最大缓存statements数。Default:
			 * 0
			 */

			// cpds.setMaxStatementsPerConnection(0);
			// 　　c3p0是异步操作的，缓慢的JDBC操作通过帮助进程完成。扩展这些操作可以有效的提升性能
			//
			//
			// 　　通过多线程实现多个操作同时被执行。Default: 3
			comboPooledDataSource.setNumHelperThreads(3);
			/*
			 * 　　当用户调用getConnection()时使root用户成为去获取连接的用户。主要用于连接池连接非c3p0
			 * 
			 * 
			 * 　　的数据源时。Default: null
			 */
			// cpds.setOverrideDefaultUser("root");
			/*
			 * 　与overrideDefaultUser参数对应使用的一个参数。Default: null
			 */
			// cpds.setOverrideDefaultPassword("password");
			// 　　用户修改系统配置参数执行前最多等待300秒。Default: 300
			// cpds.setPropertyCycle(300);
			return comboPooledDataSource;
		} catch (PropertyVetoException e) {
			e.printStackTrace();
			throw e;

		}
	}

}
