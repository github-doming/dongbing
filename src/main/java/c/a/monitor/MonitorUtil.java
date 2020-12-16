package c.a.monitor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.StringTokenizer;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * 
 * 
 * @Description:
 * @ClassName: MonitorUtil
 * @date 2017年3月12日 上午10:28:37
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class MonitorUtil {
	private String logName = "MonitorUtil=";
	private String operatingSystem = null;
	private String pid = "";
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 判断服务器的系统类型
	 * 
	 * @return
	 */
	public String findOperatingSystem() {
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().startsWith("windows")) {
			operatingSystem = "windows";
		} else if (osName.toLowerCase().startsWith("linux")) {
			operatingSystem = "linux";
		}
		return operatingSystem;
	}
	public String findJvmPID() {
		// 判断操作系统类型
		operatingSystem = findOperatingSystem();
		if (operatingSystem.equals("windows")) {
			return findJvmPIDOnWindows();
		}
		if (operatingSystem.equals("linux")) {
			return findJvmPIDOnLinux();
		}
		return null;

	}
	/**
	 * 获取Linux服务器的语言环境
	 * 
	 * @return
	 */
	public String findLocale() {
		// 判断操作系统类型
		operatingSystem = findOperatingSystem();
		if (operatingSystem.equals("windows")) {
			return findLocaleOnWindows();
		}
		if (operatingSystem.equals("linux")) {
			return findLocaleOnLinux();
		}
		return null;
	}
	/**
	 * windows环境下获取JVM的PID
	 */
	public String findJvmPIDOnWindows() {
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		pid = runtime.getName().split("@")[0];
		log.trace(logName + "PID of JVM=" + pid);
		return pid;
	}
	/**
	 * linux环境下获取JVM的PID
	 */
	public String findJvmPIDOnLinux() {
		String command = "pidof java";
		BufferedReader in = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(new String[]{"sh", "-c", command});
			in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringTokenizer ts = new StringTokenizer(in.readLine());
			pid = ts.nextToken();
			log.trace(logName + "PID of JVM=" + pid);
		} catch (Exception e) {
			log.error(logName);
			log.error(logName, e);
		}
		return pid;
	}

	/**
	 * 获取 windows服务器的语言环境
	 * 
	 * @return
	 */
	public String findLocaleOnWindows() {
		return "";
	}
	/**
	 * 获取Linux服务器的语言环境
	 * 
	 * @return
	 */
	public String findLocaleOnLinux() {
		Process process = null;
		Runtime runtime = Runtime.getRuntime();
		String command = "locale";
		BufferedReader bufferedReader = null;
		StringTokenizer stringTokenizer = null;
		try {
			process = runtime.exec(command);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			stringTokenizer = new StringTokenizer(bufferedReader.readLine());
			bufferedReader.close();
			process.destroy();
		} catch (Exception e) {
			log.error(logName);
			log.error(logName, e);
		}
		return stringTokenizer.nextToken();
	}
}