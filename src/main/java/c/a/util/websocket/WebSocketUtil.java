package c.a.util.websocket;

/**
 * 
 * @Description:
 * @date 2018年5月3日 下午4:11:29
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class WebSocketUtil extends WebSocketCoreUtil{
	private static WebSocketUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private WebSocketUtil() {
	}
	public static WebSocketUtil findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new WebSocketUtil();
				}
			}
		}
		return instance;
	}
}