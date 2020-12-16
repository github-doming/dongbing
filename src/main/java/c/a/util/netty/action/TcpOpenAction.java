package c.a.util.netty.action;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.util.StringUtil;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.netty.bean.NettyUser;
import io.netty.channel.Channel;
/**
 * 
 * http://localhost:8080/a/all/netty/open.do
 * 
 * @Description:
 * @date 2019年6月5日 上午11:14:15
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class TcpOpenAction extends TcpAction {
	@Override
	public JsonTcpBean executeTcp() throws Exception {
		JsonTcpBean jsonTcpBean = this.getJsonTcpBean();
		String channelId = jsonTcpBean.getChannelId();
		Channel channel = jsonTcpBean.getChannel();
		String appUserId = jsonTcpBean.getAppUserId();
		JsonTcpBean jrb = new JsonTcpBean();
		if (StringUtil.isNotBlank(appUserId)) {
			NettyUser.channelUserAll.put(channelId, appUserId);
			NettyUser.userChannelAll.put(appUserId, channel);
		}
		jrb.setData("[tcp连接成功]");
		// 指定用户发送
		List<String> userSend = new ArrayList<String>();
		userSend.add(jrb.getAppUserId());
		jrb.setCode(ReturnCodeEnum.code200.toString());
		jrb.setMsg(ReturnCodeEnum.code200.getMsgCn());
		jrb.setSuccess(true);
		return jrb;
	}
}
