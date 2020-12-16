package org.doming.core.tools;
import org.apache.logging.log4j.Logger;
/**
 * @Description: 自定义日志
 * @Author: Dongming
 * @Date: 2018-12-06 15:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class LogTool {

	private static final String FORMAT = "类{%s}对象[%d] ~ %s" ;

	/**
	 * 打印带井号消息
	 *
	 * @param msg 消息
	 * @return 打印消息
	 */
	public static String printHashtag(String msg) {
		String format = printHashtagS() + "%s" + printHashtagS();
		return String.format(format, msg);
	}

	/**
	 * 打印井号
	 *
	 * @return 25个井号
	 */
	public static String printHashtagS() {
		return "#########################" ;
	}

	public static String printHashtagM() {
		return printHashtagS() + printHashtagS();
	}



	/**
	 * 打印 trace 日志
	 *
	 * @param log     日志对象
	 * @param obj     日志所有者
	 * @param message 日志信息
	 */
	public static void trace(Logger log, Object obj, String message) {
		log.trace(String.format(FORMAT,obj.getClass().getSimpleName(), obj.hashCode(), message));
	}

	/**
	 * 打印 debug 日志
	 *
	 * @param log     日志对象
	 * @param obj     日志所有者
	 * @param message 日志信息
	 */
	public static void debug(Logger log, Object obj, String message) {
		log.debug(String.format(FORMAT, obj.getClass().getSimpleName(),obj.hashCode(), message));
	}

	/**
	 * 打印 info 日志
	 *
	 * @param log     日志对象
	 * @param obj     日志所有者
	 * @param message 日志信息
	 */
	public static void info(Logger log, Object obj, String message) {
		log.info(String.format(FORMAT,obj.getClass().getSimpleName(), obj.hashCode(), message));
	}
	/**
	 * 打印日志
	 *
	 * @param log     日志对象
	 * @param obj     日志所有者
	 * @param message 日志信息
	 * @param e       异常信息
	 */
	public static void error(Logger log, Object obj, String message, Exception e) {
		log.error(String.format(FORMAT,obj.getClass().getSimpleName(), obj.hashCode(), message), e);
	}
	/**
	 * 打印日志
	 *
	 * @param log     日志对象
	 * @param obj     日志所有者
	 * @param message 日志信息
	 */
	public static void error(Logger log, Object obj, String message) {
		log.error(String.format(FORMAT, obj.getClass().getSimpleName(),obj.hashCode(), message));
	}
}
