package c.a.util.core.data_source.druid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.config.SysConfig;
import c.a.config.core.ConfigParameterMap;
import c.a.util.core.jdbc.h2.JdbcH2Util;
import c.a.util.core.jdbc.mysql.JdbcMysqlUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.jdbc.oracle.JdbcOracleUtil;
import c.a.util.core.jdbc.sqlserver.JdbcSqlServerUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.typeconst.TypeDatabaseConst;
public class JdbcDataSourceListDruid {
	protected Logger log = LogManager.getLogger(this.getClass());
	public String Local = "local";
	public static Map<String, JdbcDataSourceDruid> map = null;
	// 单例
	private static JdbcDataSourceListDruid instance = null;
	// key 最好用Object
	private final static Object key = new Object();
	private JdbcDataSourceListDruid() {
	}
	/**
	 * 获取数据源单例
	 * 
	 * @Title: findInstance
	 * @Description:
	 *
	 * 				参数说明
	 * @return
	 * @throws Exception
	 *             返回类型 JdbcDataSourceUtil
	 */
	public static JdbcDataSourceListDruid findInstance() throws Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new JdbcDataSourceListDruid();
				}
			}
		}
		return instance;
	}
	public static Map<String, JdbcDataSourceDruid> getMap() {
		return map;
	}
	public void setMap(Map<String, JdbcDataSourceDruid> mapInput) {
		map = mapInput;
	}
	public String findDriver(String url, String username, String password) throws Exception {
		String driver = null;
		String dbType = TypeDatabaseConst.findDbType(url);
		boolean isCreate = false;
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.MYSQL)) {
				IJdbcUtil ju = new JdbcMysqlUtil();
				driver = ju.getDriver();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.H2)) {
				IJdbcUtil ju = new JdbcH2Util();
				driver = ju.getDriver();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.ORACLE)) {
				IJdbcUtil ju = new JdbcOracleUtil();
				driver = ju.getDriver();
				isCreate = true;
			}
		}
		if (!isCreate) {
			if (dbType.equals(TypeDatabaseConst.SQLSERVER)) {
				IJdbcUtil ju = new JdbcSqlServerUtil();
				driver = ju.getDriver();
				isCreate = true;
			}
		}
		if (!isCreate) {
			throw new java.lang.RuntimeException("未知的数据库类型");
		}
		return driver;
	}
	public JdbcDataSourceDruid findDataSourceCore(String driver, String url, String username, String password)
			throws Exception {
		JdbcDataSourceDruid jdbcDataSource = new JdbcDataSourceDruid();
		jdbcDataSource.init(driver, url, username, password);
		return jdbcDataSource;
	}
	public JdbcDataSourceDruid findDataSource(String url, String username, String password) throws Exception {
		String driver = this.findDriver(url, username, password);
		return this.findDataSourceCore(driver, url, username, password);
	}
	public JdbcDataSourceDruid findDataSource(String driver, String url, String username, String password) throws Exception {
		if (StringUtil.isBlank(driver)) {
			return findDataSource(url, username, password);
		} else {
			return this.findDataSourceCore(driver, url, username, password);
		}
	}
	public synchronized JdbcDataSourceDruid findDataSource(String id) throws Exception {
		if (map == null) {
			map = new HashMap<String, JdbcDataSourceDruid>();
		}
		JdbcDataSourceDruid jdbcDataSource = map.get(id);
		if (jdbcDataSource == null) {
			List<ConfigParameterMap> configList = SysConfig.findInstance().findList();
			String driver = null;
			String url = null;
			String username = null;
			String password = null;
			for (ConfigParameterMap configMap : configList) {
				if ("jdbc".equals(configMap.getName()) && id.equals(configMap.getId())) {
					if ("driver".equals(configMap.getKey())) {
						driver = configMap.getValue();
					}
					if ("url".equals(configMap.getKey())) {
						url = configMap.getValue();
					}
					if ("username".equals(configMap.getKey())) {
						username = configMap.getValue();
					}
					if ("password".equals(configMap.getKey())) {
						password = configMap.getValue();
					}
				}
			}
			jdbcDataSource = this.findDataSource(driver, url, username, password);
			map.put(id, jdbcDataSource);
		}
		log.trace("数据源id=" + id);
		log.trace("jdbcDataSource=" + jdbcDataSource);
		return jdbcDataSource;
	}
	public JdbcDataSourceDruid findLocal() throws Exception {
		return this.findDataSource(Local);
	}
}
