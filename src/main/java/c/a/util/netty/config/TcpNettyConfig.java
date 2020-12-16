package c.a.util.netty.config;
public class TcpNettyConfig extends TcpNettyMvcConfig {
	// public static String delimiter = "#d#";
	public static int delimiterSize = 1024 * 5;

	public static String tail = "#t#";

	//public static int clientReceiveBufferSize = 1024 * 6;
	public static int clientReceiveBufferSize = 1024 * 1024 * 1024;
	// public static int clientReceiveBufferSize = 1024 * 8;
	// public static int clientReceiveBufferSize = 1024;
	// public static int clientReceiveBufferSize = Integer.MAX_VALUE;
}
