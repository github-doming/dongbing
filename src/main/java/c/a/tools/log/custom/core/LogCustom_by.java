package c.a.tools.log.custom.core;
import c.a.tools.log.custom.config.LogConfig;
import c.a.util.core.log.LogThreadLocal;
public class LogCustom_by extends LogCustom_ay {
	public static String msg(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append("traceId=");
		sb.append(LogThreadLocal.findLog().getTraceId());
		sb.append("]");
		sb.append("[");
		sb.append(str);
		return sb.toString();
	}
	public static void out(String str) {
		if (LogConfig.out) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_traceId);
			sb.append(LogThreadLocal.findLog().getTraceId());
			sb.append(LogConfig.log_msg);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append("");
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 * @param log
	 */
	public static void out(String str, String fun) {
		if (LogConfig.out) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_traceId);
			sb.append(LogThreadLocal.findLog().getTraceId());
			sb.append(LogConfig.log_msg);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append(fun);
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	public static void trace(String str) {
		if (LogConfig.trace) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_trace);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append("");
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 * @param log
	 */
	public static void trace(String str, String fun) {
		if (LogConfig.trace) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_trace);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append(fun);
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	public static void debug(String str) {
		if (LogConfig.debug) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_debug);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append("");
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 * @param log
	 */
	public static void debug(String str, String fun) {
		if (LogConfig.debug) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_debug);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append(fun);
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	public static void info(String str) {
		if (LogConfig.info) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_info);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append("");
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 */
	public static void info(String str, String fun) {
		if (LogConfig.info) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_info);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append(fun);
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	public static void warn(String str) {
		if (LogConfig.warn) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_warn);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append("");
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 */
	public static void warn(String str, String fun) {
		if (LogConfig.warn) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_warn);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append(fun);
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	public static void error(String str) {
		if (LogConfig.error) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_error);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append("");
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 */
	public static void error(String str, String fun) {
		if (LogConfig.error) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_error);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append(fun);
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	public static void fatal(String str) {
		if (LogConfig.fatal) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_fatal);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append("");
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 */
	public static void fatal(String str, String fun) {
		if (LogConfig.fatal) {
			StringBuilder sb = new StringBuilder();
			StackTraceElement[] stacks = new Throwable().getStackTrace();
			sb.append(LogConfig.log_start);
			sb.append(LogConfig.log_fatal);
			sb.append(str);
			sb.append(LogConfig.log_fun);
			sb.append(fun);
			sb.append(LogConfig.log_system);
			sb.append("class=");
			sb.append(stacks[1].getClassName());
			sb.append(";");
			sb.append("line=");
			sb.append(stacks[1].getLineNumber());
			sb.append(LogConfig.log_thread);
			sb.append("");
			sb.append(java.lang.Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
		}
	}
}
