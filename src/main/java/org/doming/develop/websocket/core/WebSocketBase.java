package org.doming.develop.websocket.core;
import org.doming.develop.websocket.db.WebSocketTransaction;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @Description: WebSocket链接核心类
 * @Author: Dongming
 * @Date: 2018-11-22 14:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class WebSocketBase extends WebSocketTransaction implements WebSocketDef {

	/**
	 * 记录当前在线连接数
	 */
	protected static final AtomicInteger onlineCount = new AtomicInteger(0);

	/**
	 * 与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	protected Session session;

	/**
	 * 通信内容
	 */
	protected String message;

	@Override public void onOpen(Session session) {
		this.session = session;
		// 在线数加1
		addOnlineCount();
	}

	@Override public void onClose() {
		// 在线数减1
		subOnlineCount();
	}

	@Override public void onError(Session session, Throwable error) {
	}

	@Override public void onMessage(String message) {
		this.message = message;
	}

	@Override public void sendMessage(String message) throws IOException {
		sendMessage(message,true,true);
	}

	public void sendMessage(String message, boolean isSyn, boolean isAsy) throws IOException {
		if (isSyn) {
			sendSynMessage(message, isAsy);
		} else {
			sendMessage(message, isAsy);
		}
	}
	private void sendSynMessage(String message, boolean isAsy) throws IOException {
		if (session.isOpen()) {
			synchronized (WebSocketBase.class) {
				// 异步发送
				if (isAsy) {
					session.getAsyncRemote().sendText(message);
				} else {
					// 同步发送
					session.getBasicRemote().sendText(message);
				}
			}
		}
	}
	private void sendMessage(String message, boolean isAsy) throws IOException {
		if (session.isOpen()) {
			// 异步发送
			if (isAsy) {
				session.getAsyncRemote().sendText(message);
			} else {
				// 同步发送
				session.getBasicRemote().sendText(message);
			}
		}

	}

	/**
	 * 在线人数加一
	 */
	private void addOnlineCount() {
		onlineCount.incrementAndGet();
	}

	/**
	 * 获取在线人数
	 */
	public static int getOnlineCount() {
		return onlineCount.intValue();
	}

	/**
	 * 在线人数减一
	 */
	private static void subOnlineCount() {
		onlineCount.decrementAndGet();
	}
}
