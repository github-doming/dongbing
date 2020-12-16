package c.a.util.netty.core;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.RequestTypeEnum;
import c.a.util.core.json.JsonSingletonUtil;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonTcpResultBean;
import c.a.util.core.json.JsonUtil;
import c.a.util.core.uuid.Uuid;
import c.a.util.netty.bean.NettyUser;
import c.a.util.netty.config.TcpNettyConfig;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
public abstract class TcpNettyCoreUtil extends SimpleChannelInboundHandler<String> {
	protected Logger log = LogManager.getLogger("netty");
	/**
	 * 用户请求时发送数据
	 * @Title: send 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param jsonTcpBean
	 * @throws Exception 
	 * 返回类型 void
	 */
	public void send(JsonTcpBean jsonTcpBean) throws Exception {
		this.send(null, jsonTcpBean);
	}
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
	public void send(ChannelHandlerContext channelHandlerContext, JsonTcpBean jsonTcpBean) throws Exception {
		// log.trace("用户请求时发送数据,jsonTcp=" + jsonTcp);
		// 自己
		Channel channel = null;
		String channelId = null;
		if (channelHandlerContext != null) {
			channel = channelHandlerContext.channel();
			channelId = channel.id().asLongText();
			// log.trace("channelId=" + channelId);
		}
		if (jsonTcpBean == null) {
			jsonTcpBean = new JsonTcpBean();
			jsonTcpBean.setRequestType(RequestTypeEnum.SYSTEM.toString());
			jsonTcpBean.setData("服务器报错,或者找不到url对应的action");
			jsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
			jsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			jsonTcpBean.setChannelId(channelId);
			String returnStr = this.returnJsonTcpResultBean(jsonTcpBean);
			// 对所有用户发送
			// log.trace("对所有用户发送");
			for (Channel childChannel : TcpNettyServerChannelInboundHandler.group) {
				// childChannel.writeAndFlush(returnStr);
				this.writeAndFlush(childChannel, returnStr);
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
			// log.trace("NettyUser.userChannelAll.size=" +
			// NettyUser.userChannelAll.size());
			// log.trace("appUserId=" + appUserId);
			Channel childChannel = NettyUser.userChannelAll.get(appUserId);
			if (childChannel != null) {
				String childChannelId = childChannel.id().asLongText();
				// log.trace("对指定用户发送,appUserId=" + appUserId);
				// log.trace("对指定用户发送,childChannelId=" + childChannelId);
				Map<String, Object> userSendMap = jsonTcpBean.getUserSendMap();
				if (userSendMap == null) {
					String returnStr = this.returnJsonTcpResultBean(jsonTcpBean);
					// childChannel.writeAndFlush(returnStr);
					this.writeAndFlush(childChannel, returnStr);
				} else {
					// 只对自己发送数据,重新构造jsonBean
					int userSendMapSize = userSendMap.size();
					if (userSendMapSize > 0) {
						Object dataSelf = jsonTcpBean.getUserSendMap().get(appUserId);
						// log.trace("只对自己发送数据,dataSelf=" + dataSelf);
						jsonTcpBean.setDataSelf(dataSelf);
						String returnSelftStr = this.returnJsonTcpResultBean(jsonTcpBean);
						// childChannel.writeAndFlush(returnSelftStr);
						this.writeAndFlush(childChannel, returnSelftStr);
					}
					// 只对自己发送数据
				}
			} else {
				log.warn("用户请求时发送数据,childChannel=" + childChannel);
			}
		}
	}
	/**
	 * 用户请求时发送数据
	 * 
	 * @deprecated
	 * @Title: send
	 * @Description:
	 *
	 * 				参数说明
	 * @param channelHandlerContext
	 * @param jsonTcpBean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void send_v1(ChannelHandlerContext channelHandlerContext, JsonTcpBean jsonTcpBean) throws Exception {
		// log.trace("用户请求时发送数据,jsonTcp=" + jsonTcp);
		// 自己
		Channel channel = null;
		String channelId = null;
		if (channelHandlerContext != null) {
			channel = channelHandlerContext.channel();
			channelId = channel.id().asLongText();
			// log.trace("channelId=" + channelId);
		}
		if (jsonTcpBean == null) {
			jsonTcpBean = new JsonTcpBean();
			jsonTcpBean.setRequestType(RequestTypeEnum.SYSTEM.toString());
			jsonTcpBean.setData("服务器报错,或者找不到url对应的action");
			jsonTcpBean.setCodeSys(ReturnCodeEnum.code500.toString());
			jsonTcpBean.setMsgSys(ReturnCodeEnum.code500.getMsgCn());
			jsonTcpBean.setChannelId(channelId);
			String returnStr = this.returnJsonTcpResultBean(jsonTcpBean);
			// 对所有用户发送
			// log.trace("对所有用户发送");
			for (Channel childChannel : TcpNettyServerChannelInboundHandler.group) {
				// childChannel.writeAndFlush(returnStr);
				this.writeAndFlush(childChannel, returnStr);
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
		if (userSendList != null && userSendList.size() > 0) {
			// 对指定用户发送
			for (String appUserId : userSendList) {
				// log.trace("NettyUser.userChannelAll.size=" +
				// NettyUser.userChannelAll.size());
				// log.trace("appUserId=" + appUserId);
				Channel childChannel = NettyUser.userChannelAll.get(appUserId);
				if (childChannel != null) {
					String childChannelId = childChannel.id().asLongText();
					// log.trace("对指定用户发送,appUserId=" + appUserId);
					// log.trace("对指定用户发送,childChannelId=" + childChannelId);
					Map<String, Object> userSendMap = jsonTcpBean.getUserSendMap();
					if (userSendMap == null) {
						String returnStr = this.returnJsonTcpResultBean(jsonTcpBean);
						// childChannel.writeAndFlush(returnStr);
						this.writeAndFlush(childChannel, returnStr);
					} else {
						// 只对自己发送数据,重新构造jsonBean
						int userSendMapSize = userSendMap.size();
						if (userSendMapSize > 0) {
							Object dataSelf = jsonTcpBean.getUserSendMap().get(appUserId);
							// log.trace("只对自己发送数据,dataSelf=" + dataSelf);
							jsonTcpBean.setDataSelf(dataSelf);
							String returnSelftStr = this.returnJsonTcpResultBean(jsonTcpBean);
							// childChannel.writeAndFlush(returnSelftStr);
							this.writeAndFlush(childChannel, returnSelftStr);
						}
						// 只对自己发送数据
					}
				} else {
					log.warn("用户请求时发送数据,childChannel=" + childChannel);
				}
			}
		} else {
			String returnStr = this.returnJsonTcpResultBean(jsonTcpBean);
			// 对所有用户发送
			// log.trace("对所有用户发送");
			for (Channel childChannel : TcpNettyServerChannelInboundHandler.group) {
				// childChannel.writeAndFlush(returnStr);
				this.writeAndFlush(childChannel, returnStr);
			}
		}
	}
	public String returnJsonTcpBean(JsonTcpBean jsonTcpBean) throws Exception {
		JsonUtil jsonUtil = JsonSingletonUtil.findInstance();
		String resultJsonStr = jsonUtil.bean2json(jsonTcpBean);
		return resultJsonStr;
	}
	public String returnJsonTcpResultBean(JsonTcpBean jsonTcpBean) throws Exception {
		return this.returnJsonTcpResultBean_v1(jsonTcpBean);
	}
	/**
	 * @deprecated
	 * @Title: returnJsonTcpResultBean_v2
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonTcpBean
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String returnJsonTcpResultBean_v2(JsonTcpBean jsonTcpBean) throws Exception {
		JsonTcpResultBean jsonTcpResultBean = new JsonTcpResultBean();
		String traceId = jsonTcpBean.getTraceId();
		if (StringUtil.isBlank(traceId)) {
			traceId = Uuid.findInstance().toString();
		}
		jsonTcpResultBean.setTraceId(traceId);
		jsonTcpResultBean.setUrl(jsonTcpBean.getUrl());
		jsonTcpResultBean.setData(jsonTcpBean.getData());
		jsonTcpResultBean.setDataSelf(jsonTcpBean.getDataSelf());
		jsonTcpResultBean.setToken(jsonTcpBean.getToken());
		jsonTcpResultBean.setAppUserId(jsonTcpBean.getAppUserId());
		jsonTcpResultBean.setChannelId(jsonTcpBean.getChannelId());
		jsonTcpResultBean.setCode(jsonTcpBean.getCode());
		jsonTcpResultBean.setMsg(jsonTcpBean.getMsg());
		jsonTcpResultBean.setSuccess(jsonTcpBean.isSuccess());
		JsonUtil jsonUtil = JsonSingletonUtil.findInstance();
		String resultJsonStr = jsonUtil.bean2json(jsonTcpResultBean);
		resultJsonStr = resultJsonStr + TcpNettyConfig.tail;
		log.trace("返回给前端,resultJsonStr=" + resultJsonStr);
		return resultJsonStr;
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
	public void writeAndFlush(Channel childChannel, String inputStr) throws Exception {
		inputStr = inputStr + TcpNettyConfig.tail;
		childChannel.writeAndFlush(inputStr);
	}
	public void writeAndFlush(ChannelHandlerContext channelHandlerContext, String inputStr) throws Exception {
		inputStr = inputStr + TcpNettyConfig.tail;
		channelHandlerContext.writeAndFlush(inputStr);
	}
	/**
	 * Split
	 * 
	 * @deprecated
	 * @Title: writeAndFlush_v1
	 * @Description:
	 *
	 * 				参数说明
	 * @param childChannel
	 * @param inputStr
	 * @throws Exception
	 *             返回类型 void
	 */
	public void writeAndFlush_v1(Channel childChannel, String inputStr) throws Exception {
		List<String> strList = doSplit(inputStr);
		for (String str : strList) {
			str = str + TcpNettyConfig.tail;
			childChannel.writeAndFlush(str);
		}
	}
	/**
	 * Split
	 * 
	 * @deprecated
	 * @Title: writeAndFlush_v1
	 * @Description:
	 *
	 * 				参数说明
	 * @param channelHandlerContext
	 * @param inputStr
	 * @throws Exception
	 *             返回类型 void
	 */
	public void writeAndFlush_v1(ChannelHandlerContext channelHandlerContext, String inputStr) throws Exception {
		List<String> strList = doSplit(inputStr);
		for (String str : strList) {
			str = str + TcpNettyConfig.tail;
			channelHandlerContext.writeAndFlush(str);
		}
	}
	/**
	 * Split
	 * 
	 * @deprecated
	 * @Title: doSplit
	 * @Description:
	 *
	 * 				参数说明
	 * @param inputStr
	 * @return
	 * @throws Exception
	 *             返回类型 List<String>
	 */
	public List<String> doSplit(String inputStr) throws Exception {
		// byte[] byteArray = inputStr.getBytes("UTF-8");
		byte[] byteArray = inputStr.getBytes();
		List<String> strList = new ArrayList<String>();
		int inputStrLength = inputStr.length();
		// int tailLength = inputStrLength % TcpNettyConfig.delimiterSize;
		byte[] byteArrayNew = new byte[TcpNettyConfig.delimiterSize];
		for (int i = 0; i < inputStrLength;) {
			byteArrayNew = Arrays.copyOfRange(byteArray, i, i + TcpNettyConfig.delimiterSize);
			String str = new String(byteArrayNew);
			System.out.println("str=" + str);
			System.out.println("str.length=" + str.trim().length());
			strList.add(str.trim());
			i = i + TcpNettyConfig.delimiterSize;
		}
		return strList;
	}
	/**
	 * Netty ByteBuf 转 String
	 * 
	 * @Title: doByteBuf2String
	 * @Description:
	 *
	 * 				参数说明
	 * @param byteBuf
	 * @return 返回类型 String
	 */
	public String doByteBuf2String(ByteBuf byteBuf) {
		System.out.println("doByteBuf2String,length=" + byteBuf.readInt());
		String returnStr;
		// 处理堆缓冲区
		if (byteBuf.hasArray()) {
			// System.out.println("处理堆缓冲区");
			returnStr = new String(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(),
					byteBuf.readableBytes());
		} else {
			// 处理直接缓冲区以及复合缓冲区
			byte[] byteArray = new byte[byteBuf.readableBytes()];
			byteBuf.getBytes(byteBuf.readerIndex(), byteArray);
			returnStr = new String(byteArray, 0, byteBuf.readableBytes());
		}
		return returnStr;
	}
}
