package org.doming.develop.websocket;
import c.a.config.core.CommContextUtil;
import c.a.tools.jdbc.IJdbcTool;
import org.doming.develop.DefaultConfig;
import org.doming.develop.websocket.core.WebSocketMap;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
/**
 * @Description: 房间的websocket基类
 * @Author: Dongming
 * @Date: 2018-11-22 14:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseRoomWebSocketMap extends WebSocketMap {

	protected String roomId;

	@Override @OnOpen public void onOpen(@PathParam("roomId") String roomId, Session session) {
		super.onOpen(roomId, session);
		log.info(DefaultConfig.SIGN_WS + roomId + "中有新连接加入！当前在线人数为:" + getKeyOnlineCount(roomId));
		log.info(DefaultConfig.SIGN_WS + "有新连接加入！当前在线人数为:" + getOnlineCount());
	}

	@Override @OnClose public void onClose(@PathParam("roomId") String roomId) {
		super.onClose(roomId);
		log.info(DefaultConfig.SIGN_WS + roomId + "中有一连接关闭！当前在线人数为:" + getKeyOnlineCount(roomId));
		log.info(DefaultConfig.SIGN_WS + "有一连接关闭！当前在线人数为:" + getOnlineCount());
	}

	@Override @OnError public void onError(Session session, Throwable error) {
		super.onError(session, error);
		log.error(DefaultConfig.SIGN_WS + "发生错误", error);
	}

	@Override @OnMessage public void onMessage(@PathParam("roomId") String roomId, String message, Session session) {
		log.info(DefaultConfig.SIGN_WS + roomId + "中有一连接发送消息！消息内容为:" + message);
		this.roomId= roomId;
		Long startTime = System.currentTimeMillis();
		super.onMessage(roomId, message, session);
		String result = run();
		try {
			sendKeyMessage(roomId, result);
		} catch (IOException e) {
			log.error(DefaultConfig.SIGN_WS + "发送同步消息失败，消息为:" + message,e);
		}
		Long endTIme = System.currentTimeMillis();
		log.trace(DefaultConfig.SIGN_WS + "WebSocket执行时间=" + (endTIme - startTime) + "ms");
	}

	/**
	 * 开始执行事物，每个不同的项目执行的方式不一样
	 *
	 * @return 执行结果
	 */
	public String run() {
		CommContextUtil commContextUtil=new CommContextUtil();
		long startCalendarLong = 0;
		startCalendarLong =commContextUtil.start();
		String result = null;
		IJdbcTool jdbcTool = null;
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			result = execute();
		} catch (Exception e) {
			log.error(DefaultConfig.SIGN_WS + "执行错误", e);
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
			commContextUtil.end(startCalendarLong);
		}
		return result;
	}

	/**
	 * 进行发送前执行
	 *
	 * @return 执行结果
	 */
	public abstract String execute() throws Exception;

	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/

}
