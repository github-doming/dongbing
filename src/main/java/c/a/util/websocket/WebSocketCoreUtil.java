package c.a.util.websocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.websocket.server.ServerEndpoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.tools.mvc.action.MvcAction;
import c.a.tools.mvc.dto.MvcActionDto;
import c.a.tools.mvc.exception.BizRuntimeException;
import c.a.tools.mvc.nut.MvcConfig;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.RequestTypeEnum;
import c.a.util.core.json.JsonSingletonUtil;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonTcpResultBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.json.JsonUtil;
import c.a.util.core.uuid.Uuid;
import c.a.util.netty.config.TcpNettyConfig;
/**
 * 
 * @Description:
 * @date 2018年5月3日 下午4:11:29
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class WebSocketCoreUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	private String logFun = "websocket功能,";
	private String logFunError = "websocket功能出错,";
	/**
	 * 用户请求时发送数据
	 * 
	 * @Title: send
	 * @Description:
	 *
	 * 				参数说明
	 * @param channelHandlerContext
	 * @param jsonTcpBean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void send(JsonTcpBean jsonTcpBean) throws Exception {
		// log.trace("用户请求时发送数据,jsonTcp=" + jsonTcp);
		if (jsonTcpBean == null) {
			jsonTcpBean = new JsonTcpBean();
			jsonTcpBean.setRequestType(RequestTypeEnum.SYSTEM.toString());
			jsonTcpBean.setData("服务器报错,或者找不到url对应的action");
			jsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
			jsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			String returnStr = this.returnJsonTcpResultBean(jsonTcpBean);
			// 对所有用户发送
			// log.trace("对所有用户发送");
			// 群发消息
			Collection<WebSocketHandler> _Collection = WebSocketHandler.webSocketMap.values();
			for (WebSocketHandler item : _Collection) {
				this.writeAndFlush(item, returnStr);
			}
			return;
		}
		// 如果没有指定用户发送,则设置原来的用户
		List<String> userSendList = jsonTcpBean.getUserSend();
		if (userSendList == null || userSendList.size() == 0) {
			userSendList = new ArrayList<String>();
			userSendList.add(jsonTcpBean.getAppUserId());
			jsonTcpBean.setUserSend(userSendList);
		}
		// 对指定用户发送
		for (String appUserId : userSendList) {
			if (StringUtil.isNotBlank(appUserId)) {
				WebSocketHandler webSocketHandler = WebSocketHandler.webSocketMap.get(appUserId);
				if (webSocketHandler != null) {
					log.trace("对指定用户发送,appUserId=" + appUserId);
					log.trace("对指定用户发送,webSocketHandler=" +webSocketHandler);
					Map<String, Object> userSendMap = jsonTcpBean.getUserSendMap();
					if (userSendMap == null) {
						String returnStr = this.returnJsonTcpResultBean(jsonTcpBean);
						// webSocketHandler.writeAndFlush(returnStr);
						this.writeAndFlush(webSocketHandler, returnStr);
					} else {
						// 只对自己发送数据,重新构造jsonBean
						int userSendMapSize = userSendMap.size();
						if (userSendMapSize > 0) {
							Object dataSelf = jsonTcpBean.getUserSendMap().get(appUserId);
							// log.trace("只对自己发送数据,dataSelf=" + dataSelf);
							jsonTcpBean.setDataSelf(dataSelf);
							String returnSelftStr = this.returnJsonTcpResultBean(jsonTcpBean);
							// webSocketHandler.writeAndFlush(returnSelftStr);
							this.writeAndFlush(webSocketHandler, returnSelftStr);
						}
						// 只对自己发送数据
					}
				} else {
					log.warn("用户请求时发送数据,webSocketHandler=" + webSocketHandler);
				}
			}

		}
	}
	public void writeAndFlush(WebSocketHandler webSocketHandler, String inputStr) throws Exception {
		inputStr = inputStr + TcpNettyConfig.tail;
		webSocketHandler.sendMessage(inputStr);
	}
	public String returnJsonTcpResultBean(JsonTcpBean jsonTcpBean) throws Exception {
		return this.returnJsonTcpResultBean_v1(jsonTcpBean);
	}
	public String returnJsonTcpResultBean_v1(JsonTcpBean jsonTcpBean) throws Exception {
		JsonTcpResultBean jsonTcpResultBean = new JsonTcpResultBean();
		String traceId = jsonTcpBean.getTraceId();
		if (StringUtil.isBlank(traceId)) {
			traceId = Uuid.findInstance().toString();
		}
		jsonTcpResultBean.setTraceId(traceId);
		jsonTcpResultBean.setSuccess(jsonTcpBean.isSuccess());
		jsonTcpResultBean.setUrl(jsonTcpBean.getUrl());
		jsonTcpResultBean.setUrlSend(jsonTcpBean.getUrlSend());
		jsonTcpResultBean.setData(jsonTcpBean.getData());
		jsonTcpResultBean.setDataSelf(jsonTcpBean.getDataSelf());
		jsonTcpResultBean.setToken(jsonTcpBean.getToken());
		jsonTcpResultBean.setAppUserId(jsonTcpBean.getAppUserId());
		jsonTcpResultBean.setChannelId(jsonTcpBean.getChannelId());
		jsonTcpResultBean.setCode(jsonTcpBean.getCode());
		jsonTcpResultBean.setMsg(jsonTcpBean.getMsg());
		JsonUtil jsonUtil = JsonSingletonUtil.findInstance();
		String resultJsonStr = jsonUtil.bean2json(jsonTcpResultBean);
		// resultJsonStr = resultJsonStr + TcpNettyConfig.tail;
		log.trace("返回给前端,resultJsonStr=" + resultJsonStr);
		return resultJsonStr;
	}
	public JsonTcpBean executeTime(String jsonTcp) {
		// 返回方法的值
		JsonTcpBean jsonTcpBean = null;
		// Calendar calendar = Calendar.getInstance();
		// long timeStart = calendar.getTimeInMillis();
		long timeStart = System.currentTimeMillis();
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		// 执行业务
		// log.trace("BootServletBy 初始化开始");
		// log.trace("class=" + this.getClass().getName());
		// log.trace("start");
		String servletPath = null;
		try {
			jsonTcpBean = this.execute(jsonTcp);
		} catch (Exception e) {
			String logStr = "Exception,出错的url=" + servletPath;
			logStr = logFunError + logStr;
			log.error(logStr);
			log.error(logStr, e);
			// 打印到控制台
			e.printStackTrace();
			// 不需要跳转,直接打印JSON
			// 必须重新抛出异常给系统才能跳转
			// throw e;
		}
		contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
			// 对象设置为空，自动回收内存
			contextUtil = null;
		}
		// calendar = Calendar.getInstance();
		// long timeEnd = calendar.getTimeInMillis();
		long timeEnd = System.currentTimeMillis();
		long timeSpend = timeEnd - timeStart;
		log.info("花费时间timeSpend=" + timeSpend);
		return jsonTcpBean;
	}
	public JsonTcpBean execute(String jsonTcp) throws Exception {
		// 返回方法的值
		JsonTcpBean jsonTcpBean = null;
		try {
			jsonTcpBean = (JsonTcpBean) JsonThreadLocal.findThreadLocal().get().json2Bean(jsonTcp, JsonTcpBean.class);
		} catch (Exception eJson) {
			log.error("解析json出错,json=" + jsonTcp);
			eJson.printStackTrace();
		}
		log.trace("tcp url=" + jsonTcpBean.getUrl());
		log.trace("tcp appUserId=" + jsonTcpBean.getAppUserId());
		log.trace("tcp data=" + jsonTcpBean.getData());
		String servletPath = jsonTcpBean.getUrl();
		List<MvcActionDto> actionList = MvcConfig.findInstance().findList();
		for (MvcActionDto mvcAction : actionList) {
			String url = mvcAction.getUrl();
			// log.trace("url=" + url);
			// String socket = mvcAction.getSocket();
			// log.trace("boot=" + boot);
			// if ("true".equals(socket)) {
			if (url.equals(servletPath)) {
				Class classObj = null;
				try {
					String logStr = "调用action=" + mvcAction.getActionClass();
					log.info(logStr);
					classObj = Class.forName(mvcAction.getActionClass());
				} catch (ClassNotFoundException e) {
					String logStr = "找不到类异常，加载action类出错,出错的url=" + url;
					logStr = logFunError + logStr;
					log.error(logStr);
					log.error(logStr, e);
					e.printStackTrace();
					jsonTcpBean.setMsg(logStr);
					jsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
					jsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
					jsonTcpBean.setSuccess(false);
					return jsonTcpBean;
				}
				MvcAction baseAction = null;
				try {
					baseAction = (MvcAction) classObj.newInstance();
				} catch (InstantiationException e) {
					String logStr = "实例化异常，实例化action出错,出错的url=" + url;
					logStr = logFunError + logStr;
					log.error(logStr);
					log.error(logStr, e);
					e.printStackTrace();
					jsonTcpBean.setMsg(logStr);
					jsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
					jsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
					jsonTcpBean.setSuccess(false);
					return jsonTcpBean;
				} catch (IllegalAccessException e) {
					String logStr = "不合法访问异常，实例化action出错,出错的url=" + url;
					logStr = logFunError + logStr;
					log.error(logStr);
					log.error(logStr, e);
					e.printStackTrace();
					jsonTcpBean.setMsg(logStr);
					jsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
					jsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
					jsonTcpBean.setSuccess(false);
					return jsonTcpBean;
				}
				// 不要用反射,因为反射不能与业务异常bizException绑定起来
				if (true) {
					try {
						baseAction.setJsonTcp(jsonTcp);
						baseAction.setJsonTcpBean(jsonTcpBean);
						jsonTcpBean = baseAction.doExecute();
					} catch (BizRuntimeException e1) {
						String logStr = "BizRuntimeException，业务出错,出错的url=" + url;
						logStr = logFunError + logStr;
						log.error(logStr);
						log.error(logStr, e1);
						// 打印到控制台
						e1.printStackTrace();
						// 必须重新抛出异常给系统才能跳转
						// throw e1;
						jsonTcpBean.setMsg(logStr);
						jsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
						jsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
						jsonTcpBean.setSuccess(false);
						return jsonTcpBean;
					} catch (RuntimeException e2) {
						String logStr = "运行中RuntimeException，业务出错,出错的url=" + url;
						logStr = logFunError + logStr;
						log.error(logStr);
						log.error(logStr, e2);
						// 打印到控制台
						e2.printStackTrace();
						// 不需要跳转,直接打印JSON
						// 必须重新抛出异常给系统才能跳转
						// throw e2;
						jsonTcpBean.setMsg(logStr);
						jsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
						jsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
						jsonTcpBean.setSuccess(false);
						return jsonTcpBean;
					} catch (Throwable t) {
						String logStr = "Throwable，业务出错,出错的url=" + url;
						logStr = logFunError + logStr;
						log.error(logStr);
						log.error(logStr, t);
						// 打印到控制台
						t.printStackTrace();
						jsonTcpBean.setMsg(logStr);
						jsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
						jsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
						jsonTcpBean.setSuccess(false);
						return jsonTcpBean;
					}
				}
			}
		}
		return jsonTcpBean;
	}
}