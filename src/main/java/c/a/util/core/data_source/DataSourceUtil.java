package c.a.util.core.data_source;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.Connection;
import java.sql.SQLException;
public class DataSourceUtil {
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
	public DruidDataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DruidDataSource dataSource) {
		this.dataSource = dataSource;
	}
	public DruidDataSource findDataSource(String driverClass, String jdbcUrl, String usernameInput,
			String passwordInput) throws Exception {
		// log.trace("使用druidDataSource,class="+this.getClass().getName());
		driver = driverClass;
		url = jdbcUrl;
		username = usernameInput;
		password = passwordInput;
		dataSource = new DruidDataSource();
		/**
		 * 必要配置
		 */
		int maxActive = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get("druid.local.MaxActive"), 200);
		long maxWait = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get("druid.local.MaxWait"), 10 * 1000);
		String removeAbandoned=BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get("druid.local.RemoveAbandoned"), "false");
		int  removeAbandonedTimeout=BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get("druid.local.RemoveAbandonedTimeout"), 60);
		// int maxActive = BeanThreadLocal.findThreadLocal().get()
		// .find(SysConfig.findInstance().findMap().get("druid.local.MaxActive"),
		// 5000);
		// long maxWait = BeanThreadLocal.findThreadLocal().get()
		// .find(SysConfig.findInstance().findMap().get("druid.local.MaxWait"),
		// 30 * 1000);
		// System.out.println("maxActive=" + maxActive);
		// System.out.println("maxWait=" + maxWait);
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url); // 数据库url
		dataSource.setUsername(username);// 用户名
		dataSource.setPassword(password); // 密码
		// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3
		//dataSource.setInitialSize(1000);
		dataSource.setInitialSize(30);
		//dataSource.setInitialSize(10);
		// 连接池中保留的最小连接数。
		//dataSource.setMinIdle(1000);
		// dataSource.setMinIdle(100);
		dataSource.setMinIdle(10);
		// 连接池中保留的最大连接数。Default: 15
		// 最大连接数
		// dataSource.setMaxIdle(15);
		// 最大并发连接数
		// dataSource.setMaxActive(5000);
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
		dataSource.setFilters("stat,log4j2");
		// dds.setFilters("stat");
		/**
		 * <property name="removeAbandoned" value="true" />
		 * 
		 * <property name="removeAbandonedTimeout" value="1800" />
		 * 
		 * 初始化配置的这里设置的, 这两个参数的大概意思就是,
		 * 
		 * 通过datasource.getConnontion()
		 * 取得的连接必须在removeAbandonedTimeout这么多秒内调用close(),要不我就弄死你.(
		 * 就是conn不能超过指定的租期) 然后调成2个小时~~~
		 * 
		 * 然后程序成功跑完~~~华丽丽的等了50分钟
		 * 
		 * 总结:
		 * 
		 * 连接池为了防止程序从池里取得连接后忘记归还的情况, 而提供了一些参数来设置一个租期,
		 * 
		 * 使用这个可以在一定程度上防止连接泄漏
		 * 
		 * 但是如果你的业务真要跑这么久~~~~那还是注意下这个设置.
		 */
		if("false".equals(removeAbandoned)){
				dataSource.setRemoveAbandoned(false);
		}else{
			if (false) {
				dataSource.setRemoveAbandoned(true);
				// 超时时间；单位为秒。60秒=1分钟
				dataSource.setRemoveAbandonedTimeout(60 * 60 * 24 * 10);
				// dataSource.setRemoveAbandonedTimeout(60*30);
			}
			if (true) {
				dataSource.setRemoveAbandoned(true);
				// 超时时间；单位为秒。60秒=1分钟
				//dataSource.setRemoveAbandonedTimeout(60 * 30);
				dataSource.setRemoveAbandonedTimeout(60 * 1);
				dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
			}
		}
		/**
		 * https://blog.csdn.net/lzwglory/article/details/73301886
		 * 
		 * 解决方案二： 直接关闭这个 自动回收超时连接
		 * <property name="removeAbandoned" value="false" />
		 */
		// dataSource.setRemoveAbandoned(false);
		// logAbandoned
		// 关闭abanded连接时输出错误日志
		dataSource.setLogAbandoned(true);
		// 元数据其中的列信息能显示注释
		// 获取Oracle元数据 REMARKS信息
		// properties.setProperty("remarksReporting","true");
		// 获取MySQL元数据 REMARKS信息
		// properties.setProperty("useInformationSchema","true");
		dataSource.setConnectionProperties("remarksReporting=true;useInformationSchema=true");
		return dataSource;
	}
	/**
	 * 初始化
	 * 
	 * @Title: init
	 * @Description:
	 *
	 * 				参数说明
	 * @param driverInput
	 * @param urlInput
	 * @param usernameInput
	 * @param passwordInput
	 * @throws Exception
	 *             返回类型 void
	 */
	public void init(String driverInput, String urlInput, String usernameInput, String passwordInput) throws Exception {
		dataSource = findDataSource(driverInput, urlInput, usernameInput, passwordInput);
	}
	public Connection findConnection() throws SQLException, Exception {
		return this.dataSource.getConnection();
	}
}
