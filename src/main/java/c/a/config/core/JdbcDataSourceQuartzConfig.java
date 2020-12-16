package c.a.config.core;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
/**
 * 
 * 数据源配置
 * 
 * @Description:
 * @date 2017年2月28日 上午10:06:54
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public class JdbcDataSourceQuartzConfig extends CharsetConfigAy{
	// 日志
	protected Logger log = LogManager.getLogger(this.getClass());
	private static String driver = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	// 单例
	private static JdbcDataSourceQuartzConfig instance = null;
	// key 最好用Object
	// private final static String key = "key";
	private final static Object key = new Object();
	private JdbcDataSourceQuartzConfig() {
	}
	public static JdbcDataSourceQuartzConfig findInstance() throws Exception {
		synchronized (key) {
			if (instance == null) {
				instance = new JdbcDataSourceQuartzConfig();
				init();
			}
		}
		return instance;
	}
	private static void init() throws Exception {
		// 读取数据库配置
		String jdbcQuartzStart= BeanThreadLocal.findThreadLocal().get().find(SysConfig.findInstance().findMap().get("jdbc.quartz.start"), "");
		if("true".equals(jdbcQuartzStart)){
			driver = BeanThreadLocal.findThreadLocal().get().find(SysConfig.findInstance().findMap().get("jdbc.local.driver"), "");
			url = SysConfig.findInstance().findMap().get("jdbc.quartz.url").toString();
			username = SysConfig.findInstance().findMap().get("jdbc.quartz.username").toString();
			password = SysConfig.findInstance().findMap().get("jdbc.quartz.password").toString();
		}else{
			driver = BeanThreadLocal.findThreadLocal().get().find(SysConfig.findInstance().findMap().get("jdbc.local.driver"), "");
			url = SysConfig.findInstance().findMap().get("jdbc.local.url").toString();
			username = SysConfig.findInstance().findMap().get("jdbc.local.username").toString();
			password = SysConfig.findInstance().findMap().get("jdbc.local.password").toString();
		}
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driverInput) {
		driver = driverInput;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String urlInput) {
		url = urlInput;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String usernameInput) {
		username = usernameInput;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String passwordInput) {
		password = passwordInput;
	}
}
