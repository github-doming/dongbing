package all.netty.action;
import java.util.Set;

import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.netty.bean.NettyUser;
import c.a.util.netty.core.TcpNettyServerChannelInboundHandler;
import c.x.platform.root.common.action.AyAction;
import io.netty.channel.Channel;
public class TcpNettyMonitorAction extends AyAction {
	@Override
	public String execute() throws RuntimeException, Exception {
		/**
		 * 监控所有netty
		 */
		// http://localhost:8080/a/all/netty/list.do
		System.out.println("TcpNettyServerChannelInboundHandler.group.size="+TcpNettyServerChannelInboundHandler.group.size());
		System.out.println("NettyUser.channelUserAll.size="+NettyUser.channelUserAll.size());
		System.out.println("NettyUser.userChannelAll.size="+NettyUser.userChannelAll.size());
		StringBuilder stringBuilder = new StringBuilder();
		String str = null;
		try {
			stringBuilder.append("通道与用户 NettyUser.channelUserAll</br>");
			stringBuilder.append("<table class='table table-hover' border='0'>");
			stringBuilder.append("<tr>");
			stringBuilder.append("<td>");
			stringBuilder.append("appUserId");
			stringBuilder.append("</td>");
			stringBuilder.append("<td>");
			stringBuilder.append("channelId");
			stringBuilder.append("</td>");
			stringBuilder.append("</tr>");
		
			for (Channel childChannel : TcpNettyServerChannelInboundHandler.group) {
				String childChannelId = childChannel.id().asLongText();
				stringBuilder.append("<tr>");
				stringBuilder.append("<td>");
				str = "appUserId=" + NettyUser.channelUserAll.get(childChannelId);
				stringBuilder.append(str);
				stringBuilder.append("<td>");
				str = "childChannel ID=" + childChannelId;
				stringBuilder.append(str);
				stringBuilder.append("</td>");
				stringBuilder.append("</tr>");

			}
			stringBuilder.append("</table>");
			stringBuilder.append("用户与通道NettyUser.userChannelAll</br>");
			stringBuilder.append("<table class='table table-hover' border='0'>");
			stringBuilder.append("<tr>");
			stringBuilder.append("<td>");
			stringBuilder.append("appUserId");
			stringBuilder.append("</td>");
			stringBuilder.append("<td>");
			stringBuilder.append("channelId");
			stringBuilder.append("</td>");
			stringBuilder.append("<td>");
			stringBuilder.append("remoteAddress");
			stringBuilder.append("</td>");
			stringBuilder.append("</tr>");
			 Set<String> userList=NettyUser.userChannelAll.keySet();
			for (String appUserId: userList) {
				Channel channel=NettyUser.userChannelAll.get(appUserId);
				String childChannelId = channel.id().asLongText();
				String remoteAddress= channel.remoteAddress().toString();
				stringBuilder.append("<tr>");
				stringBuilder.append("<td>");
				str = "appUserId=" +appUserId;
				stringBuilder.append(str);
				stringBuilder.append("<td>");
				str = "childChannel ID=" + childChannelId;
				stringBuilder.append(str);
				stringBuilder.append("</td>");
				stringBuilder.append("<td>");
				str = " remoteAddress=" +  remoteAddress;
				stringBuilder.append(str);
				stringBuilder.append("</td>");
				stringBuilder.append("</tr>");

			}
			stringBuilder.append("</table>");
			request.setAttribute("s", stringBuilder.toString());
		} catch (Exception e) {
			log.error("error");
			log.error("error", e);
		}
		return CommViewEnum.Default.toString();
	}
}
