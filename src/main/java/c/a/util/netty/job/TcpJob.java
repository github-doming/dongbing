package c.a.util.netty.job;
import java.util.List;
import org.quartz.JobExecutionContext;
import c.a.util.core.json.JsonSingletonUtil;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonUtil;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.job.CommJob;
import c.a.util.netty.bean.NettyUser;
import c.a.util.netty.core.TcpNettyServerChannelInboundHandler;
import io.netty.channel.Channel;
/**
 * 
 * 
 * @Description:
 * @date 2018年7月13日 上午9:22:16
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public abstract class TcpJob extends CommJob {
	public TcpJob() {
		// 需要数据库操作
		this.database = true;
		// 需要事务操作
		transaction = false;
	}
	public abstract JsonTcpBean executeTcp(JobExecutionContext context) throws Exception;
	@Override
	public String executeJob(JobExecutionContext context) throws Exception {
		String returnStr = null;
		JsonTcpBean returnJsonTcpBean = executeTcp(context);
		returnStr = returnTcpJson(returnJsonTcpBean);
		// 当有用户发送消息的时候，对其他用户发送信息
		List<String> userSendList = returnJsonTcpBean.getUserSend();
		if (userSendList != null && userSendList.size() > 0) {
			// 对指定用户发送
			for (Channel childChannel : TcpNettyServerChannelInboundHandler.group) {
				String childChannelId = childChannel.id().asLongText();
				// boolean
				// isSend=NettyUser.userSend.contains(NettyUser.channelUserAll.get(childChannelId));
				boolean isSend = userSendList.contains(NettyUser.channelUserAll.get(childChannelId));
				if (isSend) {
					log.trace("JOB,对指定用户发送,appUserId=" + NettyUser.channelUserAll.get(childChannelId));
					log.trace("JOB,对指定用户发送,childChannelId=" + childChannelId);
					childChannel.writeAndFlush(returnStr);
				}
			}
		} else {
			// 对所有用户发送
			log.trace("JOB,对所有用户发送");
			for (Channel childChannel : TcpNettyServerChannelInboundHandler.group) {
				childChannel.writeAndFlush(returnTcpJson(executeTcp(context)));
			}
		}
		return null;
	}
	public String returnTcpJson(JsonTcpBean _JsonTcpBean) throws Exception {
		JsonTcpBean logJsonTcpBean = LogThreadLocal.findLog();
		_JsonTcpBean.setTraceId(logJsonTcpBean.getTraceId());
		JsonUtil jsonUtil = JsonSingletonUtil.findInstance();
		String resultJsonStr = jsonUtil.bean2json(_JsonTcpBean);
		return resultJsonStr;
	}
}
