package c.a.util.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import c.a.util.core.json.JsonTcpBean;
/**
 * 
 * http://localhost:8080/a/pages/example/c/x/all/simple/websocket/e3/index1.jsp
 * 
 * http://localhost:8080/a/pages/example/c/x/all/simple/websocket/e3/index2.jsp
 * 
 * @Description:
 * @ClassName: 
 * @date 2018年5月3日 下午4:11:29
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
@ServerEndpoint("/websocket_user/{userId}")
public class WebSocketHandler {
	protected Logger log = LogManager.getLogger(this.getClass());
	private String logFun = "websocket功能,";
	private String logFunError = "websocket功能出错,";
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	// 若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	// public static CopyOnWriteArraySet<WebSocketHandler> webSocketMap = new
	// CopyOnWriteArraySet<WebSocketHandler>();
	public static ConcurrentHashMap<String, WebSocketHandler> webSocketMap = new ConcurrentHashMap<String, WebSocketHandler>();
	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(@PathParam("userId") String userId, Session session) {
		System.out.println("onOpen,来自客户端的消息,userId=" + userId);
		this.session = session;
		// 加入set中
		webSocketMap.put(userId, this);
		// 在线数加1
		addOnlineCount();
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(@PathParam("userId") String userId) {
		// 从set中删除
		webSocketMap.remove(userId);
		// 在线数减1
		subOnlineCount();
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param jsonTcp
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	@OnMessage
	public void onMessage(@PathParam("userId") String userId, String jsonTcp, Session session) {
		try {
			System.out.println("onMessage,来自客户端的消息=" + jsonTcp);
			System.out.println("onMessage,来自客户端的消息,userId=" + userId);
			// log.trace("onMessage,来自客户端的消息=" + jsonTcp);
			// log.trace("onMessage,来自客户端的消息,userId=" + userId);
			JsonTcpBean jsonTcpBean = WebSocketUtil.findInstance().executeTime(jsonTcp);
			WebSocketUtil.findInstance().send(jsonTcpBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketHandler.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketHandler.onlineCount--;
	}
}