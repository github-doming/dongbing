package org.doming.develop.websocket.core;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @Description: map型WebSocket链接
 * 注：ServerEndpoint需要携带map的key eg：/url/{key}
 * @Author: Dongming
 * @Date: 2018-11-22 14:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.1
 */
public class WebSocketMap extends WebSocketBase {

	/**
	 * 记录key键在线数
	 */
	private static Map<String, AtomicInteger> keyOnlineCount = new ConcurrentHashMap<>();

	/**
	 * concurrent包的线程安全map，用来存放每个客户端对应的MyWebSocket对象。
	 */
	public static final Map<String, List<WebSocketMap>> webSocketMap = new ConcurrentHashMap<>();

	/**
	 * 连接建立成功调用
	 *
	 * @param key     建立连接的key
	 * @param session 客户端的连接会话
	 */
	public void onOpen(String key, Session session) {
		super.onOpen(session);
		// 加入新的连接
		addOnline(key);
		addKeyOnlineCount(key);
	}


	public void onClose(String key) {
		super.onClose();
		// 从map中删除
		subOnline(key);
		subKeyOnlineCount(key);
	}

	/**
	 * 发送消息时调用
	 * @param key 发送的key
	 * @param message 发送的消息
	 * @param session 发送者
	 */
	public void onMessage(String key, String message, Session session) {
		super.onMessage(message);
	}
	/**
	 * 发送消息
	 *
	 * @param key     key键
	 * @param message 消息
	 * @return 发送结果
	 */
	protected void sendKeyMessage(String key, String message) throws IOException {
		for (WebSocketMap item : webSocketMap.get(key)) {
			item.sendMessage(message);
		}
	}

	/**
	 * 加入新的连接
	 *
	 * @param key key键
	 */
	protected void addOnline(String key) {
		if (webSocketMap.get(key) == null) {
			List<WebSocketMap> list = new CopyOnWriteArrayList<>();
			list.add(this);
			webSocketMap.put(key, list);
		} else {
			webSocketMap.get(key).add(this);
		}
	}

	/**
	 * key键在线人数加一
	 *
	 * @param key key键
	 */
	protected void addKeyOnlineCount(String key) {
		if (keyOnlineCount.get(key) != null) {
			keyOnlineCount.get(key).incrementAndGet();
		} else {
			keyOnlineCount.put(key, new AtomicInteger(1));
		}
	}

	/**
	 * 获取在线人数
	 *
	 * @param key key键
	 * @return 在线人数
	 */
	public static Integer getKeyOnlineCount(String key) {
		AtomicInteger count = keyOnlineCount.get(key);
		if (count != null) {
			return count.intValue();
		}
		return 0;
	}

	/**
	 * 在线人数减一
	 *
	 * @param key key键
	 */
	protected void subKeyOnlineCount(String key) {
		if (keyOnlineCount.get(key) != null) {
			int count = keyOnlineCount.get(key).decrementAndGet();
			if (count == 0) {
				keyOnlineCount.remove(key);
			}
		}
	}

	/**
	 * 移除连接
	 *
	 * @param key key键
	 */
	protected synchronized void subOnline(String key) {
		webSocketMap.get(key).remove(this);
	}

}
