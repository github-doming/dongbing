package c.a.util.netty.core;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
public class TcpNettyClientChannelInboundHandler extends SimpleChannelInboundHandler<String> {
	private static Logger log = LogManager.getLogger("netty");
	// @Override
	/**
	 * @deprecated
	 * @Title: messageReceived
	 * @Description:
	 *
	 * 				参数说明
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 *             返回类型 void
	 */
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
		// 客户端主要用来接收服务器发送的消息
		// log.trace( "客户端messageReceived,msg="+msg);
		log.debug("客户端messageReceived,msg=" + msg);
	}
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
		// 客户端主要用来接收服务器发送的消息
		// log.trace( "客户端channelRead0,msg="+msg);
		log.debug("客户端channelRead0,msg=" + msg);
	}
}
