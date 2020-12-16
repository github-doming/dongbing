package c.a.tools.log.custom.core;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import c.a.util.core.file.FileThreadLocal;
import c.a.util.core.file.FileUtil;
import c.a.tools.log.custom.config.LogConfig;
/**
 * 
 * 输出到文件
 * 
 * 
 * 
 */
public class LogCustom_file extends LogCustom_ay {
	public static String file_parent = "d:\\log\\";
	//分割符号
	private static String separator = "$";
	private static ArrayList<String> printStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();
		LineNumberReader reader = new LineNumberReader(new StringReader(sw.toString()));
		ArrayList<String> lines = new ArrayList();
		try {
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
		} catch (IOException ex) {
			lines.add(ex.toString());
		}
		// for (String str : lines) {
		// System.out.println(str);
		//
		// }
		return lines;
	}
	public static void out(String str) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_trace.txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 * @param fun
	 */
	public static void out(String str, String fun) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_trace" + separator + fun + separator
						+ Thread.currentThread().getName() + ".txt", sb.toString());
				fileUtil.appendByAbsolutePath(file_parent + "by_trace" + separator + fun + "" + ".txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void trace(String str) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_debug.txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 * @param fun
	 */
	public static void trace(String str, String fun) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			sb.append(Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_debug" + separator + fun + separator
						+ Thread.currentThread().getName() + ".txt", sb.toString());
				fileUtil.appendByAbsolutePath(file_parent + "by_debug" + separator + fun + "" + ".txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void debug(String str) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_debug.txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 * @param fun
	 */
	public static void debug(String str, String fun) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			sb.append(Thread.currentThread().getName());
			sb.append(LogConfig.log_end);
			System.out.println(sb.toString());
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_debug" + separator + fun + separator
						+ Thread.currentThread().getName() + ".txt", sb.toString());
				fileUtil.appendByAbsolutePath(file_parent + "by_debug" + separator + fun + "" + ".txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void info(String str) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_info.txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 * @param fun
	 */
	public static void info(String str, String fun) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_info" + separator + fun + separator + Thread.currentThread().getName()
						+ ".txt", sb.toString());
				fileUtil.appendByAbsolutePath(file_parent + "by_info" + separator + fun + "" + ".txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void warn(String str) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_warn.txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 * @param fun
	 */
	public static void warn(String str, String fun) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_warn" + separator + fun + separator + Thread.currentThread().getName()
						+ ".txt", sb.toString());
				fileUtil.appendByAbsolutePath(file_parent + "by_warn" + separator + fun + "" + ".txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void error(String str, Throwable t) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			ArrayList<String> lines = printStackTrace(t);
			for (String line : lines) {
				sb.append(FileUtil.line_delimiter);
				sb.append(line);
			}
			System.out.println(sb.toString());
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_error.txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 * @param fun
	 * @param t
	 */
	public static void error(String str, String fun, Throwable t) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			ArrayList<String> lines = printStackTrace(t);
			for (String line : lines) {
				sb.append(FileUtil.line_delimiter);
				sb.append(line);
			}
			System.out.println(sb.toString());
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_error" + separator + fun + separator
						+ Thread.currentThread().getName() + ".txt", sb.toString());
				fileUtil.appendByAbsolutePath(file_parent + "by_error" + separator + fun + "" + ".txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void fatal(String str, Throwable t) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			ArrayList<String> lines = printStackTrace(t);
			for (String line : lines) {
				sb.append(FileUtil.line_delimiter);
				sb.append(line);
			}
			System.out.println(sb.toString());
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_fatal.txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 打印日志
	 * 
	 * @param str
	 * @param fun
	 * @param t
	 */
	public static void fatal(String str, String fun, Throwable t) {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
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
			ArrayList<String> lines = printStackTrace(t);
			for (String line : lines) {
				sb.append(FileUtil.line_delimiter);
				sb.append(line);
			}
			System.out.println(sb.toString());
			try {
				fileUtil.appendByAbsolutePath(file_parent + "ay_fatal" + separator + fun + separator
						+ Thread.currentThread().getName() + ".txt", sb.toString());
				fileUtil.appendByAbsolutePath(file_parent + "by_fatal" + separator + fun + "" + ".txt", sb.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
