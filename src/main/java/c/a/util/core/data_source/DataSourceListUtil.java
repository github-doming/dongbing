package c.a.util.core.data_source;
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
/**
 * 
 * 多个数据源
 * @Description: 
 * @ClassName: DataSourceListUtil 
 * @date 2018年6月28日 上午9:28:47 
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class DataSourceListUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	public static String Local = "local";
	public static Map<String, DataSourceUtil> map = null;
	// 单例
	private static DataSourceListUtil instance = null;
	// key 最好用Object
	private final static Object key = new Object();
	private DataSourceListUtil() {
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
	public static DataSourceListUtil findInstance() throws Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new DataSourceListUtil();
				}
			}
		}
		return instance;
	}
	public static Map<String, DataSourceUtil> getMap() {
		return map;
	}
	public void setMap(Map<String, DataSourceUtil> mapInput) {
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
	public DataSourceUtil findDataSourceCore(String driver, String url, String username, String password)
			throws Exception {
		DataSourceUtil jdbcDataSource = new DataSourceUtil();
		jdbcDataSource.init(driver, url, username, password);
		return jdbcDataSource;
	}
	public DataSourceUtil findDataSource(String url, String username, String password) throws Exception {
		String driver = this.findDriver(url, username, password);
		return this.findDataSourceCore(driver, url, username, password);
	}
	public DataSourceUtil findDataSource(String driver, String url, String username, String password) throws Exception {
		if (StringUtil.isBlank(driver)) {
			return findDataSource(url, username, password);
		} else {
			return this.findDataSourceCore(driver, url, username, password);
		}
	}
	public synchronized DataSourceUtil findDataSource(String id) throws Exception {
		if (map == null) {
			map = new HashMap<String, DataSourceUtil>();
		}
		DataSourceUtil jdbcDataSource = map.get(id);
		if (jdbcDataSource == null) {
			List<ConfigParameterMap> configList = SysConfig.findInstance().findList();
			String driver = null;
			String url = null;
			String username = null;
			String password = null;
			for (ConfigParameterMap configMap : configList) {
				if ("jdbc".equals(configMap.getName()) && id.equals(configMap.getId())) {
					if ("driver".equals(configMap.getKey())) {
						driver = configMap.getValue().trim();
					}
					if ("url".equals(configMap.getKey())) {
						url = configMap.getValue().trim();
					}
					if ("username".equals(configMap.getKey())) {
						username = configMap.getValue().trim();
					}
					if ("password".equals(configMap.getKey())) {
						password = configMap.getValue().trim();
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
	public DataSourceUtil findLocal() throws Exception {
		return this.findDataSource(Local);
	}
}
