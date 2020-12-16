package c.a.util.core.log;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.file.FileUtil;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;
import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
/**
 * 
 * @Description:
 * @ClassName: LogUtil
 * @date 2015年10月16日 下午7:05:10
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class LogUtil {
	// 单例
	private volatile static LogUtil instance = null;
	private final static Object key = new Object();
	// 构造函数
	private LogUtil() {
	}
	public static LogUtil findInstance() throws Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new LogUtil();
				}
			}
		}
		return instance;
	}
	/**
	 * 转换并打印最终的log
	 * 
	 * @Title: doPrintLogTarget
	 * @Description:
	 *
	 * 				参数说明
	 * @param logJsonTcpBean
	 * @param content
	 * @return 返回类型 String
	 */
	public String doPrintLogTarget(JsonTcpBean logJsonTcpBean, String content) {
		String traceId = logJsonTcpBean.getTraceId();
		String code = logJsonTcpBean.getCode();
		String msg = logJsonTcpBean.getMsg();
		StringBuilder sb = new StringBuilder();
		sb.append(content);
		sb.append("{");
		sb.append("traceId=");
		sb.append(traceId);
		sb.append(",");
		sb.append("code=");
		sb.append(code);
		sb.append(",");
		sb.append("msg=");
		sb.append(msg);
		sb.append("}");
		return sb.toString();
	}
	/**
	 * 初始化
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void init(String servletContextPath) throws Exception {
		String commLocalLog4j = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("comm.local.log4j"), "2");
		if ("1".equals(commLocalLog4j)) {
			init_log4j_v1(servletContextPath);
		}
		if ("2".equals(commLocalLog4j)) {
			init_log4j2_v3(servletContextPath);
		}
	}
	/**
	 * 初始化
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public static void init_log4j2_v3(String servletContextPath) throws Exception {
		System.setProperty("webapp.root", servletContextPath);
		if (false) {
			FileUtil fileUtil = new FileUtil();
			StringBuilder sb = fileUtil.read(LogConfig.url2);
			System.out.println("log4j2 xml=" + sb.toString());
		}
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		String path = pathUtil.findPath(LogConfig.url2);
		// System.out.println("path=" + path);
		File file = new File(path);
		FileInputStream fileInputStream = new FileInputStream(path);
		ConfigurationSource configurationSource = new ConfigurationSource(fileInputStream, file);
		LoggerContext loggerContext = Configurator.initialize(null, configurationSource);
		XmlConfiguration xmlConfiguration = new XmlConfiguration(loggerContext, configurationSource);
		loggerContext.start(xmlConfiguration);
	}
	/**
	 * 初始化
	 * 
	 * @deprecated
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void init_log4j2_v2(String servletContextPath) throws Exception {
		System.setProperty("webapp.root", servletContextPath);
		if (false) {
			FileUtil fileUtil = new FileUtil();
			StringBuilder sb = fileUtil.read(LogConfig.url2);
			System.out.println("log4j2 xml=" + sb.toString());
		}
		URL url = this.getClass().getResource(LogConfig.url2);
		FileInputStream fileInputStream = new FileInputStream(new File(url.getPath()));
		ConfigurationSource configurationSource = new ConfigurationSource(fileInputStream, url);
		LoggerContext loggerContext = Configurator.initialize(null, configurationSource);
		XmlConfiguration xmlConfiguration = new XmlConfiguration(loggerContext, configurationSource);
		loggerContext.start(xmlConfiguration);
	}
	/**
	 * 初始化
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param servletContextPath
	 * @throws Exception
	 *             返回类型 void
	 */
	public static void init_log4j_v1(String servletContextPath) throws Exception {
		System.setProperty("webapp.root", servletContextPath);
		PathUtil pathUtil = PathThreadLocal.findThreadLocal().get();
		// log4j日志
		String path = pathUtil.findPath(LogConfig.url);
		PropertyConfigurator.configure(path);
	}
	/**
	 * 初始化
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @throws Exception
	 *             返回类型 void
	 */
	public void init_v0(String servletContextPath) throws Exception {
		System.setProperty("webapp.root", servletContextPath);
	}
}
