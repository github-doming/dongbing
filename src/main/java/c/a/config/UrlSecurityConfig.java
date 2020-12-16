package c.a.config;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.a.util.core.properties.PropertiesUtil;
/**
 * 
 * 读取url配置参数
 * 
 * @Description:
 * @date 2017年2月17日 上午11:05:42
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public class UrlSecurityConfig extends ProjectConfig{
	protected Logger log = LogManager.getLogger(this.getClass());
	public static String url = "/config/platform/url.properties";
	public  Map<String, Object> map = null;
	private static UrlSecurityConfig instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造子
	 */
	private UrlSecurityConfig() {
	}
	public static UrlSecurityConfig findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new UrlSecurityConfig();
				}
			}
		}
		return instance;
	}
	public  Map<String, Object> findMap() throws Exception {
		if (map == null) {
			PropertiesUtil propertiesUtil = new PropertiesUtil();
			map = propertiesUtil.findProperties2Map(url);
		}
		return map;
	}
}
