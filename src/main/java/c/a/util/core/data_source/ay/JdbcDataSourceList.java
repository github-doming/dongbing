package c.a.util.core.data_source.ay;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import c.a.config.SysConfig;
import c.a.config.core.ConfigParameterMap;
import c.a.util.core.jdbc.h2.JdbcH2Util;
import c.a.util.core.jdbc.mysql.JdbcMysqlUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.jdbc.oracle.JdbcOracleUtil;
import c.a.util.core.jdbc.sqlserver.JdbcSqlServerUtil;
import c.a.util.core.string.StringUtil;
import c.a.util.core.typeconst.TypeDatabaseConst;
public class JdbcDataSourceList {
	public String Local = "local";
	public Map<String, JdbcDataSource> map = new HashMap<String, JdbcDataSource>();
	// 单例
	private static JdbcDataSourceList instance = null;
	// key 最好用Object
	private final static Object key = new Object();
	private JdbcDataSourceList() {
	}
	/**
	 * 获取数据源单例
	 * 
	 * @Description: desc @Title: findInstance @return return @throws
	 *               Exception 参数说明 @return JdbcDataSource 返回类型 @throws
	 */
	public static JdbcDataSourceList findInstance() throws Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new JdbcDataSourceList();
				}
			}
		}
		return instance;
	}
	public Map<String, JdbcDataSource> getMap() {
		return map;
	}
	public void setMap(Map<String, JdbcDataSource> map) {
		this.map = map;
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
	public JdbcDataSource findDataSourceCore(String driver, String url, String username, String password)
			throws Exception {
		JdbcDataSource jdbcDataSource = new JdbcDataSource();
		jdbcDataSource.init(driver, url, username, password);
		return jdbcDataSource;
	}
	public JdbcDataSource findDataSource(String url, String username, String password) throws Exception {
		String driver = this.findDriver(url, username, password);
		return this.findDataSourceCore(driver, url, username, password);
	}
	public JdbcDataSource findDataSource(String driver, String url, String username, String password) throws Exception {
		if (StringUtil.isBlank(driver)) {
			return findDataSource(url, username, password);
		} else {
			return this.findDataSourceCore(driver, url, username, password);
		}
	}
	public JdbcDataSource findDataSource(String id) throws Exception {
		JdbcDataSource jdbcDataSource = map.get(id);
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
		return jdbcDataSource;
	}
	public JdbcDataSource findLocal() throws Exception {
		return this.findDataSource(Local);
	}
	public void addDataSource(String id, String driver, String url, String username, String password) throws Exception {
		if (StringUtil.isBlank(driver)) {
			driver = this.findDriver(url, username, password);
		}
		JdbcDataSource jdbcDataSource = new JdbcDataSource();
		jdbcDataSource.init(driver, url, username, password);
		map.put(id, jdbcDataSource);
	}
}
