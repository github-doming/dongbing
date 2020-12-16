package org.doming.develop.websocket;
import c.a.config.core.CommContextUtil;
import c.a.tools.jdbc.IJdbcTool;
import c.a.util.core.json.JsonResultBean;
import org.doming.develop.DefaultConfig;
import org.doming.develop.websocket.core.WebSocketMap;

import javax.websocket.*;
import javax.websocket.server.PathParam;
/**
 * @Description: 房间的websocket基类
 * @Author: Dongming
 * @Date: 2018-11-22 14:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseGameRoomWebSocketMap extends WebSocketMap {

	protected String roomId;
	protected String gameCode;

	@Override @OnOpen public void onOpen(@PathParam("var") String var, Session session) {
		super.onOpen(session);
		super.addOnline(var);
		String[] arr = var.split("_");
		String gameCode = arr[0];
		String roomId = arr[1];
		super.addKeyOnlineCount(roomId);
		log.info(DefaultConfig.SIGN_WS + "房间【" + roomId + "】中【" + gameCode + "】游戏有新连接加入！当前房间在线人数为:" + getKeyOnlineCount(
				roomId));
		log.info(DefaultConfig.SIGN_WS + "有新连接加入！当前在线人数为:" + getOnlineCount());
	}

	@Override @OnClose public void onClose(@PathParam("var") String var) {
		super.onClose();
		// 从map中删除
		super.subOnline(var);
		String roomId = var.split("_")[1];
		super.subKeyOnlineCount(roomId);
		log.info(DefaultConfig.SIGN_WS + roomId + "中有一连接关闭！当前在线人数为:" + getKeyOnlineCount(roomId));
		log.info(DefaultConfig.SIGN_WS + "有一连接关闭！当前在线人数为:" + getOnlineCount());
	}

	@Override @OnError public void onError(Session session, Throwable error) {
		super.onError(session, error);
		log.error(DefaultConfig.SIGN_WS + "发生错误", error);
	}

	@Override @OnMessage public void onMessage(@PathParam("var") String var, String message, Session session) {
		CommContextUtil commContextUtil=new CommContextUtil();
		long startCalendarLong = 0;
		startCalendarLong =commContextUtil.start();
		log.info(DefaultConfig.SIGN_WS + roomId + "中有一连接发送消息！消息内容为:" + message);
		String[] arr = var.split("_");
		this.gameCode = arr[0];
		this.roomId = arr[1];
		Long startTime = System.currentTimeMillis();
		super.onMessage(roomId, message, session);
		JsonResultBean bean = run();
		try {
			transmitMessage(bean);
		} catch (Exception e) {
			log.error(DefaultConfig.SIGN_WS + "发送消息失败，消息为:" + message, e);
		} finally {
			this.transactionClose(jdbcTool);
			commContextUtil.end(startCalendarLong);
		}
		Long endTIme = System.currentTimeMillis();
		log.trace(DefaultConfig.SIGN_WS + "WebSocket执行时间=" + (endTIme - startTime) + "ms");
	}

	/**
	 * 开始执行事物，每个不同的项目执行的方式不一样
	 *
	 * @return 执行结果
	 */
	public JsonResultBean run() {
		JsonResultBean result = null;
		IJdbcTool jdbcTool = null;
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			result = execute();
		} catch (Exception e) {
			log.error(DefaultConfig.SIGN_WS + "消息处理错误", e);
			this.transactionRoll(jdbcTool);
		}
		return result;
	}

	/**
	 * 消息处理
	 *
	 * @return 处理结果
	 * @throws Exception 处理异常
	 */
	protected abstract JsonResultBean execute() throws Exception;

	/**
	 * 发射消息
	 *
	 * @param result 消息
	 * @throws Exception 发射异常
	 */
	protected abstract void transmitMessage(JsonResultBean result) throws Exception;

	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/

}
