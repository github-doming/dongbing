package c.a.config;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.config.core.ConfigParameterMap;
import c.a.util.core.properties.PropertiesUtil;
/**
 * 
 * 读取配置参数
 * 
 * @Description:
 * @ClassName: SysConfig
 * @date 2017年2月17日 上午11:05:42
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class SysConfig extends ProjectConfig {
	protected Logger log = LogManager.getLogger(this.getClass());
	public static String keyWxPubDomainName = "wx.pub.domainName";

	public static String servletContextPath = null;

	// public static String commLocalProject = "comm.local.project";
	// public static String commLocalProjectValue = null;
	public static String commLocalTenant = "comm.local.tenant";
	// public static String commLocalTenantValue = null;
	public static String commLocalType = "comm.local.type";
	public static String commLocalTypeValue = null;
	public static String commLocalDebug = "comm.local.debug";
	public static String commLocalSysLogRequest = "comm.local.sys_log_request";
	public static String commLocalAppLogRequest = "comm.local.app_log_request";
	public static String commLocalLog = "comm.local.log";
	public static String commLocalMachine = "comm.local.machine";
	public static String commLocalView = "comm.local.view";
	public static String contextLocalPath = "context.local.path";
	public static String configValueTrue = "true";
	public static String configValueFalse = "false";
	public static String CurrentSysUserId = "CurrentSysUserId";
	public static String CurrentSysUserName = "CurrentSysUserName";
	public static String CurrentAppUserId = "CurrentAppUserId";
	public static String CurrentAppUserName = "CurrentAppUserName";
	public static String url = "/config/platform/core.properties";
	public Map<String, Object> map = null;
	private List<ConfigParameterMap> list = null;
	private static SysConfig instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造子
	 */
	private SysConfig() {
	}
	public static SysConfig findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new SysConfig();
				}
			}
		}
		return instance;
	}
	public List<ConfigParameterMap> findList() throws Exception {
		// log.trace("1 class="+this.getClass().getName());
		if (list == null) {
			// log.trace("2 class="+this.getClass().getName());
			// 读取配置
			PropertiesUtil propertiesUtil = new PropertiesUtil();
			list = new ArrayList<ConfigParameterMap>();
			map = propertiesUtil.findProperties2Map(url);
			Set<String> keyList = map.keySet();
			for (String keyPropertie : keyList) {
				String value = map.get(keyPropertie).toString();
				String[] keyArray = keyPropertie.split("\\.");
				if (keyArray.length == 3) {
					String name = keyArray[0];
					String id = keyArray[1];
					String key = keyArray[2];
					ConfigParameterMap configParameterMap = new ConfigParameterMap();
					configParameterMap.setName(name);
					configParameterMap.setId(id);
					configParameterMap.setKey(key);
					configParameterMap.setValue(value);
					list.add(configParameterMap);
				} else {
				}
			}
		}
		return list;
	}
	public Map<String, Object> findMap() throws Exception {
		if (map == null) {
			findList();
		}
		return map;
	}
}
