package c.a.util.netty.core;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.tools.log.custom.common.BaseLog;
import c.a.tools.mvc.action.MvcAction;
import c.a.tools.mvc.dto.MvcActionDto;
import c.a.tools.mvc.exception.BizRuntimeException;
import c.a.tools.mvc.nut.MvcConfig;
import c.a.util.core.bean.BeanSingletonUtil;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.RequestTypeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.netty.bean.NettyUser;
import c.a.util.netty.config.TcpNettyConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
/**
 * netty服务器Handler
 */
public class TcpNettyServerChannelInboundHandler extends TcpNettyCoreUtil {
	protected Logger log = LogManager.getLogger("netty");
	private String logFun = "netty功能,";
	private String logFunError = "netty功能出错,";
	// 保存所有活动的用户
	public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	public JsonTcpBean executeTime(Channel channel, String channelId, String jsonTcp) {
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
			jsonTcpBean = this.execute(channel, channelId, jsonTcp);
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
	public JsonTcpBean execute(Channel channel, String channelId, String jsonTcp) throws Exception {
		// 返回方法的值
		JsonTcpBean jsonTcpBean = null;
		try {
			jsonTcpBean = (JsonTcpBean) JsonThreadLocal.findThreadLocal().get().json2Bean(jsonTcp, JsonTcpBean.class);
		} catch (Exception eJson) {
			log.error("解析json出错,json=" + jsonTcp);
			eJson.printStackTrace();
		}
		jsonTcpBean.setChannel(channel);
		jsonTcpBean.setChannelId(channelId);
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
	/**
	 * 在线，离线，断线重连; Netty 实现心跳检测和断线重连;
	 * 
	 * 一段时间未进行读写操作;
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object eventObject) throws Exception {
		// 已经10秒未收到客户端的消息了！
		String logMsg=null;
		long allIdleTime = BeanSingletonUtil.findInstance().find(SysConfig.findInstance().findMap().get("netty.local.allIdleTime"), 10);
		TcpNettyUtil nettyUtil = TcpNettyUtil.findInstance();
		JsonTcpBean _JsonTcpBean = LogThreadLocal.findLog();
		_JsonTcpBean.setUrl(TcpNettyConfig.userEventTriggered);
		_JsonTcpBean.setSuccess(true);
		_JsonTcpBean.setRequestType(RequestTypeEnum.SYSTEM.toString());
		Channel channel = channelHandlerContext.channel();
		String channelId = channel.id().asLongText();
		logMsg="已经" + allIdleTime + "秒未收到客户端的消息了,channelId=" + channelId;
		BaseLog.out(logMsg);
		log.trace(BaseLog.msg(logMsg));
		String data = "服务端userEventTriggered[" + channel.remoteAddress() + "]" + "is userEventTriggered";
		super.userEventTriggered(channelHandlerContext, eventObject);
		if (eventObject instanceof IdleStateEvent) {
			IdleStateEvent idleStateEvent = (IdleStateEvent) eventObject;
			// 有操作
			logMsg="没有操作,IDLE,channelId=" + channelId;
			BaseLog.out(logMsg);
			log.trace(BaseLog.msg(logMsg));
			if (idleStateEvent.state().equals(IdleState.ALL_IDLE)) {
				data = "服务端userEventTriggered,ALL_IDLE[" + channel.remoteAddress() + "]" + "is userEventTriggered";
				// 未进行读写
				logMsg="没有读写操作,ALL_IDLE,channelId=" + channelId;
				BaseLog.out(logMsg);
				log.trace(BaseLog.msg(logMsg));
				// 发送心跳消息
				_JsonTcpBean.setData(data);
				// channelHandlerContext.writeAndFlush(nettyUtil.returnJsonTcpResultBean(jsonResultBean));
				nettyUtil.writeAndFlush(channel, nettyUtil.returnJsonTcpResultBean(_JsonTcpBean));
			}
			if (idleStateEvent.state().equals(IdleState.WRITER_IDLE)) {
				// 未进行WRITER_IDLE操作
				logMsg="没有写操作,WRITER_IDLE,channelId=" + channelId;
				BaseLog.out(logMsg);
				log.trace(BaseLog.msg(logMsg));
			}
			if (idleStateEvent.state().equals(IdleState.READER_IDLE)) {
				// NettyUser.channelUserAll.remove
				NettyUser.channelUserAll.remove(channelId);
				// 未进行读操作
				logMsg="没有读操作,READER_IDLE,channelId=" + channelId;
				BaseLog.out(logMsg);
				log.trace(BaseLog.msg(logMsg));
				// 超时关闭channel
				channelHandlerContext.close();
				logMsg="关闭这个不活跃通道,channelId=" + channelId;
				BaseLog.out(logMsg);
				log.trace(BaseLog.msg(logMsg));
				channelHandlerContext.channel().close();
			}
		}
	}
	/**
	 * @deprecated
	 * @Title: messageReceived
	 * @Description:
	 *
	 * 				参数说明
	 * @param channelHandlerContext
	 * @param socketMsgJson
	 * @throws Exception
	 *             返回类型 void
	 */
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, String jsonTcp) throws Exception {
		Channel channel = channelHandlerContext.channel();
		// 当有用户发送消息的时候，对其他用户发送信息
		for (Channel childChannel : group) {
			if (childChannel == channel) {
				childChannel.writeAndFlush("服务端 messageReceived_1[you]" + jsonTcp + "\n");
			} else {
				childChannel.writeAndFlush("服务端messageReceived_2[" + channel.remoteAddress() + "]" + jsonTcp + "\n");
			}
		}
		log.trace("服务端messageReceived_3[" + channel.remoteAddress() + "],msg=" + jsonTcp + "\n");
	}
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, String jsonTcp) throws Exception {
		try {
			log.trace("服务端channelRead0,jsonTcp=" + jsonTcp);
			// System.out.println("channelRead0,jsonTcp=" + jsonTcp);
			// 自己的Channel
			Channel channel = channelHandlerContext.channel();
			String channelId = channel.id().asLongText();
			log.trace("channelRead0,channelId=" + channelId);
			JsonTcpBean jsonTcpBean = executeTime(channel, channelId, jsonTcp);
			this.send(channelHandlerContext, jsonTcpBean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(logFunError, e);
			throw e;
		}
	}
	@Override
	public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
		try {
			TcpNettyUtil nettyUtil = TcpNettyUtil.findInstance();
			JsonTcpBean _JsonTcpBean = LogThreadLocal.findLog();
			_JsonTcpBean.setUrl(TcpNettyConfig.handlerAdded);
			_JsonTcpBean.setSuccess(false);
			_JsonTcpBean.setRequestType(RequestTypeEnum.SYSTEM.toString());
			Channel channel = channelHandlerContext.channel();
			for (Channel childChannel : group) {
				String data = "服务端handlerAdded[" + channel.remoteAddress() + "]" + "is ADD comming";
				log.trace(data);
				_JsonTcpBean.setData(data);
				_JsonTcpBean.setAppUserId("");
				_JsonTcpBean.setSuccess(true);
				// childChannel.writeAndFlush(nettyUtil.returnJsonTcpResultBean(jsonResultBean));
				nettyUtil.writeAndFlush(childChannel, nettyUtil.returnJsonTcpResultBean(_JsonTcpBean));
			}
			group.add(channel);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(logFunError, e);
			throw e;
		}
	}
	@Override
	public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
		try {
			TcpNettyUtil nettyUtil = TcpNettyUtil.findInstance();
			JsonTcpBean _JsonTcpBean = LogThreadLocal.findLog();
			_JsonTcpBean.setUrl(TcpNettyConfig.handlerRemoved);
			_JsonTcpBean.setSuccess(false);
			_JsonTcpBean.setRequestType(RequestTypeEnum.SYSTEM.toString());
			Channel channel = channelHandlerContext.channel();
			for (Channel childChannel : group) {
				String data = "服务端handlerRemoved[" + channel.remoteAddress() + "]" + "is remove comming";
				log.trace(data);
				_JsonTcpBean.setSuccess(true);
				_JsonTcpBean.setAppUserId("");
				_JsonTcpBean.setData(data);
				// childChannel.writeAndFlush(nettyUtil.returnJsonTcpResultBean(jsonResultBean));
				nettyUtil.writeAndFlush(childChannel, nettyUtil.returnJsonTcpResultBean(_JsonTcpBean));
			}
			group.remove(channel);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(logFunError, e);
			throw e;
		}
	}
	// 在建立连接时发送信息
	@Override
	public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
		try {
			JsonTcpBean _JsonTcpBean = LogThreadLocal.findLog();
			_JsonTcpBean.setUrl(TcpNettyConfig.channelActive);
			_JsonTcpBean.setSuccess(true);
			_JsonTcpBean.setRequestType(RequestTypeEnum.SYSTEM.toString());
			TcpNettyUtil nettyUtil = TcpNettyUtil.findInstance();
			Channel channel = channelHandlerContext.channel();
			// 把 channel的ID加到内存之中
			String channelId = channel.id().asLongText();
			// log.trace("服务端channelActive[server welcome]");
			String data = "服务端channelActive[server welcome][" + channel.remoteAddress() + "]msg=" + "online";
			log.trace(data);
			log.trace("channelActive,channelId=" + channelId);
			_JsonTcpBean.setData(data);
			// channelHandlerContext
			// .writeAndFlush(nettyUtil.returnJsonTcpResultBean(jsonResultBean));
			nettyUtil.writeAndFlush(channelHandlerContext, nettyUtil.returnJsonTcpResultBean(_JsonTcpBean));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(logFunError, e);
			throw e;
		}
	}
	// 退出连接
	@Override
	public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
		try {
			JsonTcpBean _JsonTcpBean = LogThreadLocal.findLog();
			_JsonTcpBean.setUrl(TcpNettyConfig.channelInactive);
			_JsonTcpBean.setSuccess(true);
			_JsonTcpBean.setRequestType(RequestTypeEnum.SYSTEM.toString());
			TcpNettyUtil nettyUtil = TcpNettyUtil.findInstance();
			Channel channel = channelHandlerContext.channel();
			String channelId = channel.id().asLongText();
			// NettyUser.channelUserAll.remove
			NettyUser.channelUserAll.remove(channelId);
			String data = "服务端 channelInactive[" + channel.remoteAddress() + "]msg=" + "offline";
			log.trace(data);
			log.trace("channelInactive,channelId=" + channelId);
			_JsonTcpBean.setData(data);
			// channelHandlerContext
			// .writeAndFlush(nettyUtil.returnJsonTcpResultBean(jsonResultBean));
			nettyUtil.writeAndFlush(channelHandlerContext, nettyUtil.returnJsonTcpResultBean(_JsonTcpBean));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(logFunError, e);
			throw e;
		}
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
		try {
			JsonTcpBean _JsonTcpBean = LogThreadLocal.findLog();
			_JsonTcpBean.setUrl(TcpNettyConfig.exceptionCaught);
			_JsonTcpBean.setSuccess(true);
			_JsonTcpBean.setRequestType(RequestTypeEnum.SYSTEM.toString());
			TcpNettyUtil nettyUtil = TcpNettyUtil.findInstance();
			String data = "服务端exceptionCaught[" + channelHandlerContext.channel().remoteAddress() + "]msg="
					+ "exit the room";
			log.trace(data);
			Channel channel = channelHandlerContext.channel();
			// 把 channel的ID加到内存之中
			String channelId = channel.id().asLongText();
			_JsonTcpBean.setChannelId(channelId);
			_JsonTcpBean.setData(data);
			// channelHandlerContext
			// .writeAndFlush(nettyUtil.returnJsonTcpResultBean(jsonResultBean));
			nettyUtil.writeAndFlush(channelHandlerContext, nettyUtil.returnJsonTcpResultBean(_JsonTcpBean));
			channelHandlerContext.close().sync();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(logFunError, e);
			throw e;
		}
	}
}
