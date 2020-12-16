package all.netty.action;
import c.a.util.netty.core.TcpNettyServerChannelInboundHandler;
import c.x.platform.root.common.action.AyAction;
import io.netty.channel.Channel;
public class TcpNettyAction extends AyAction {
	@Override
	public String execute() throws RuntimeException, Exception {
		// http://localhost:8080/a/all/netty/index.do
		/**
		 * 主动推送
		 */
		StringBuilder stringBuilder = new StringBuilder();
		try {
			for (Channel childChannel : TcpNettyServerChannelInboundHandler.group) {
				String str = "我们系统sysmsg childChannel ID=" + childChannel.id().asLongText();
				childChannel.writeAndFlush(str);
			}
			stringBuilder.append("end");
			response.getWriter().print(stringBuilder.toString());
		} catch (Exception e) {
			log.error("error");
			log.error("error", e);
		}
		return null;
	}
}
