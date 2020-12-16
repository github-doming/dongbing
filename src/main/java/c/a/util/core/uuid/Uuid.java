package c.a.util.core.uuid;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import c.a.util.core.date.DateThreadLocal;
/**
 * 
 * 
 * 文件名或主键生成器
 * 
 * @author cxy
 * @Email:
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class Uuid {
	public String separator = "-";
	private static Uuid instance = null;
	private final static Object key = new Object();
	private static long timeStamp = 0L;
	private static String mac = null;
	/**
	 * 私有的默认构造子
	 */
	private Uuid() {
	}
	public static Uuid findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new Uuid();
				}
			}
		}
		return instance;
	}
	public static synchronized Uuid create() throws Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new Uuid();
				}
			}
		}
		return instance;
	}
	/**
	 * 转成数字; 格式：3位流水号; 长度为23位;
	 * 
	 */
	public Long toLong() {
		Random random = new Random();
		int i = random.nextInt(999);
		Calendar c = Calendar.getInstance();
		//long value=c.getTimeInMillis()-1514764800702l;
		long value=c.getTimeInMillis();
		Long result = value*1000+i;
		return result;
	}
	/**
	 * 转成数字; 格式：3位流水号; 长度为23位;
	 * 
	 */
	public Long toLong3() {
		Random random = new Random();
		int i = random.nextInt(999);
		String result = "";
		// 保留num的位数
		// 0 代表前面补充0
		// num 代表长度
		// d 代表参数为正数型
		result = String.format("%0" + 3 + "d", i);
		Long valueLong = Long.parseLong(result);
		return valueLong;
	}
	/**
	 * 转成数字; 格式：yyyyMMddHHmmssSSS + 1位流水号; 长度为18位;
	 * 
	 */
	public long toLong18() {
		Random random = new Random();
		int i = random.nextInt(9);
		String valueStr = DateThreadLocal.findThreadLocal().get().findNow2String17();
		Long valueLong = Long.parseLong(valueStr);
		long result = valueLong * 10 + i;
		return result;
	}
	/**
	 * 转成数字; 格式：3位流水号; 长度为3位;
	 * 
	 */
	public String toString3() {
		Random random = new Random();
		int i = random.nextInt(999);
		String result = "";
		// 保留num的位数
		// 0 代表前面补充0
		// num 代表长度
		// d 代表参数为正数型
		result = String.format("%0" + 3 + "d", i);
		return result;
	}
	/**
	 * 转成数字; 格式：yyyyMMddHHmmssSSS + 3位流水号; 长度为20位;
	 * 
	 */
	public String toStringDate20() {
		Random random = new Random();
		int i = random.nextInt(999);
		String result = "";
		// 保留num的位数
		// 0 代表前面补充0
		// num 代表长度
		// d 代表参数为正数型
		result = String.format("%0" + 3 + "d", i);
		result = DateThreadLocal.findThreadLocal().get().findNow2String17() + result;
		return result;
	}
	/**
	 * 转成数字; 格式：yyyyMMddHHmmssSSS + 6位流水号; 长度为23位;
	 * 
	 */
	public String toStringDate23() {
		Random random = new Random();
		int i = random.nextInt(999999);
		String result = "";
		// 保留num的位数
		// 0 代表前面补充0
		// num 代表长度
		// d 代表参数为正数型
		result = String.format("%0" + 6 + "d", i);
		result = DateThreadLocal.findThreadLocal().get().findNow2String17() + result;
		return result;
	}
	/**
	 * 转成没"-"的字符串
	 */
	public String toString() {
		UUID uuid = java.util.UUID.randomUUID();
		String str = uuid.toString().replaceAll("-", "");
		return str;
	}
	/**
	 * 
	 * @Title: toString
	 * @Description:
	 *
	 * 				参数说明
	 * @param id
	 * @param createTimeStr
	 * @param macInput
	 * @return 返回类型 String
	 */
	public String toString(Long id, String createTimeStr, String macInput) {
		UUID uuid = java.util.UUID.randomUUID();
		String str = uuid.toString().replaceAll("-", "");
		return str;
	}
}
