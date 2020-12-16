package c.a.util.core.uuid;
import java.net.SocketException;
import java.net.UnknownHostException;
import c.a.util.core.date.DateThreadLocal;
import c.a.util.core.request.RequestThreadLocal;
/**
 * 
 * 
 * 文件名或主键生成器(有ip)
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class UuidAy {
	public String separator = "-";
	private static UuidAy instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造子
	 */
	private UuidAy() {
	}
	public static UuidAy findInstance() {
		synchronized (key) {
			if (instance == null) {
				instance = new UuidAy();
			}
		}
		return instance;
	}
	private static long timeStamp = 0L;
	private static String mac = null;
	/**
	 * static synchronized必加
	 * 
	 * @return
	 * @throws SocketException
	 * @throws UnknownHostException
	 * @throws UuidException
	 */
	public static synchronized UuidAy create() throws Exception {
		synchronized (key) {
			if (instance == null) {
				instance = new UuidAy();
			}
			setTimeStamp();
			setMac();
		}
		return instance;
	}
	private static void setMac() throws Exception {
		mac = RequestThreadLocal.findThreadLocal().get().findMAC();
	}
	private static void setTimeStamp() {
		long newTime = System.currentTimeMillis();
		if (timeStamp != 0L) {
			if (newTime < timeStamp) {
				throw new RuntimeException("newTime < timeStamp");
			}
			if (newTime == timeStamp) {
				newTime = System.currentTimeMillis();
			}
		}
		timeStamp = newTime;
	}
	public String toString(Long id) {
		return id.toString();
	}
	/**
	 * 
	 * @Title: toString
	 * @Description:
	 * @param id
	 * @param createTimeStr
	 *            时间
	 * @param macInput
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String toString(Long id, String createTimeStr, String macInput) {
		StringBuilder buf = new StringBuilder();
		buf.append(id).append(separator);
		buf.append(createTimeStr).append(separator);
		buf.append(macInput);
		return buf.toString();
	}
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(DateThreadLocal.findThreadLocal().get().findNow2String17(timeStamp)).append(separator);
		buf.append(mac);
		return buf.toString();
	}
	public String getTime() {
		return DateThreadLocal.findThreadLocal().get().findNow2String17(timeStamp);
	}
}
