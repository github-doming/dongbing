package c.a.tools.log.custom.config;
public class LogConfig {
	public static Boolean out = true;
	public static Boolean trace = true;
	public static Boolean debug = true;
	public static Boolean info = true;
	public static Boolean warn = true;
	public static Boolean error = true;
	public static Boolean fatal = true;
	// public final static String log_start = "[start]";
	public final static String log_start = "^_^";
	public final static String log_trace = "[trace]";
	public final static String log_debug = "[debug]";
	public final static String log_info = "[info]";
	public final static String log_warn = "[warn]";
	public final static String log_error = "[error]";
	public final static String log_fatal = "[fatal]";
	public final static String log_fun = "[fun]";
	public final static String log_system = "[sys]";
	// 线程
	public final static String log_thread = "[thread]";
	public final static String log_end = "[end]";
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
	public final static String log_traceId = "[traceId]";
	public final static String log_msg = "[msg]";
}
