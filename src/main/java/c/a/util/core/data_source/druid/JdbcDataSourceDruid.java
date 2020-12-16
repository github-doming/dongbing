package c.a.util.core.data_source.druid;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.alibaba.druid.pool.DruidDataSource;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
public class JdbcDataSourceDruid {
	// 日志
	protected Logger log = LogManager.getLogger(this.getClass());
	private DruidDataSource dataSource = null;
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
	public DruidDataSource findDataSource(String driverClass, String jdbcUrl, String usernameInput,
			String passwordInput) throws Exception {
		log.trace("使用druidDataSource,class=" + this.getClass().getName());
		driver = driverClass;
		url = jdbcUrl;
		username = usernameInput;
		password = passwordInput;
		dataSource = new DruidDataSource();
		/**
		 * 必要配置
		 */
		int maxActive = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get("druid.local.MaxActive"), 1000);
		long maxWait = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get("druid.local.MaxWait"), 10 * 1000);
		// int maxActive = BeanThreadLocal.findThreadLocal().get()
		// .find(SysConfig.findInstance().findMap().get("druid.local.MaxActive"),
		// 5000);
		// long maxWait = BeanThreadLocal.findThreadLocal().get()
		// .find(SysConfig.findInstance().findMap().get("druid.local.MaxWait"),
		// 30 * 1000);
		//System.out.println("maxActive=" + maxActive);
		//System.out.println("maxWait=" + maxWait);
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url); // 数据库url
		dataSource.setUsername(username);// 用户名
		dataSource.setPassword(password); // 密码
		// 初始化时获取三个连接， 取值应在minPoolSize与maxPoolSize之间。Default: 3
		// dataSource.setInitialSize(3);
		dataSource.setInitialSize(1);
		// 连接池中保留的最小连接数。
		// dataSource.setMinIdle(10);
		dataSource.setMinIdle(1);
		// 连接池中保留的最大连接数。Default: 15
		// cpds.setMaxIdle(15); // 最大连接数
		// 最大并发连接数
		dataSource.setMaxActive(maxActive);
		// 配置获取连接等待超时的时间
		dataSource.setMaxWait(maxWait);
		// 归还连接时执行validationQuery检测连接是否有效，
		// 做了这个配置会降低性能
		dataSource.setTestOnReturn(true);
		// 申请连接时执行validationQuery检测连接是否有效，
		// 做了这个配置会降低性能。
		dataSource.setTestOnBorrow(true);
		// 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
		// 监控统计用的filter:stat
		// 日志用的filter:log4j
		// 防御SQL注入的filter:wall
		// dataSource.setFilters("stat");
		//dataSource.setFilters("stat,log4j");
		dataSource.setFilters("stat,log4j2");
		// 超时时间；单位为秒。60秒=1分钟
		dataSource.setRemoveAbandonedTimeout(10);
		// logAbandoned
		// 关闭abanded连接时输出错误日志
		dataSource.setLogAbandoned(true);
		return dataSource;
	}
	public void init(String driverInput, String urlInput, String usernameInput, String passwordInput) throws Exception {
		dataSource = findDataSource(driverInput, urlInput, usernameInput, passwordInput);
	}
	public Connection findConnection() throws SQLException, Exception {
		return this.dataSource.getConnection();
	}
}
