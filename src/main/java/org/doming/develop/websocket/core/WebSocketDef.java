package org.doming.develop.websocket.core;
import javax.websocket.Session;
import java.io.IOException;
/**
 * @Description: WebSocket默认实现方法
 * @Author: Dongming
 * @Date: 2018-11-24 09:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface WebSocketDef {

	/**
	 * 连接建立成功调用
	 *
	 * @param session 客户端的连接会话
	 */
	void onOpen(Session session);

	/**
	 * 连接关闭调用
	 */
	void onClose();

	/**
	 * 发生错误
	 *
	 * @param session 错误的连接
	 * @param error   错误的信息
	 */
	void onError(Session session, Throwable error);

	/**
	 * 接收消息
	 *
	 * @param message 收到的消息
	 */
	void onMessage(String message);

	/**
	 * 发送消息
	 * @param message 发送的消息
	 * @throws IOException 同步发送异常
	 */
	void sendMessage(String message) throws IOException;
}
