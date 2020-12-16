package c.a.util.core.data_source.c3p0;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
public class JdbcDataSourceC3P0 {
	// 日志
	protected Logger log = LogManager.getLogger(this.getClass());
	private ComboPooledDataSource dataSource = null;
	private String driver = null;
	private String url = null;
	private String username = null;
	private String password = null;
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ComboPooledDataSource findDataSource(String driverClass, String jdbcUrl, String usernameInput, String passwordInput)
			throws Exception {
		//log.trace("使用c3p0,class=" + this.getClass().getName());
		driver = driverClass;
		url =jdbcUrl;
		username =usernameInput;
		password = passwordInput;
		ComboPooledDataSource comboPooledDataSource = null;
		comboPooledDataSource = new ComboPooledDataSource();
		/**
		 * 必要配置
		 */
		comboPooledDataSource.setDriverClass(driver); // 驱动器
		comboPooledDataSource.setJdbcUrl(url); // 数据库url
		comboPooledDataSource.setUser(username); // 用户名
		comboPooledDataSource.setPassword(password); // 密码
		// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3
		comboPooledDataSource.setInitialPoolSize(3);
		// 　　连接池中保留的最小连接数。
		comboPooledDataSource.setMinPoolSize(10); // 最少连接数
		// 连接池中保留的最大连接数。Default: 15
		comboPooledDataSource.setMaxPoolSize(100); // 最大连接数
		// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
		comboPooledDataSource.setAcquireIncrement(3); // 连接数的增量
		// 每60秒检查所有连接池中的空闲连接。Default: 0
		comboPooledDataSource.setIdleConnectionTestPeriod(60);
		// 　　如果设为true那么在取得连接的同时将校验连接的有效性。Default: false
		// comboPooledDataSource.setTestConnectionOnCheckin(false);
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
	}
	public void init(String driverInput, String urlInput, String usernameInput, String passwordInput) throws Exception {
		dataSource = findDataSource(driverInput, urlInput, usernameInput, passwordInput);
	}
	public Connection findConnection() throws SQLException, Exception {
		return this.dataSource.getConnection();
	}
}
