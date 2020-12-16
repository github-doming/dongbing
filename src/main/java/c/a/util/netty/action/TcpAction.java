package c.a.util.netty.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.util.StringUtil;
import c.a.util.core.enums.bean.RequestTypeEnum;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.netty.bean.NettyUser;
import c.a.util.netty.core.TcpNettyUtil;
import c.x.platform.root.common.action.CommAction;
import io.netty.channel.Channel;
import net.sf.json.JSONObject;
/**
 * 
 * 
 * @Description:
 * @date 2019年6月5日 上午11:14:15
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public abstract class TcpAction extends CommAction {
	protected JSONObject dataJSONObject = null;
	protected String data = null;
	protected Map<String, Object> dataMap = null;
	/**
	 * 
	 * 下一个action
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract JsonTcpBean executeTcp() throws Exception;
	public JsonTcpBean executeBiz() throws Exception {
		JsonTcpBean jsonTcpBean = this.getJsonTcpBean();
		if (jsonTcpBean != null) {
			// log.trace("tcp json=" + this.getJsonTcp());
			// log.trace("tcp data=" +
			// this.getJsonTcpBean().getData());
		}
		// 设置appUserId
		String channelId = jsonTcpBean.getChannelId();
		Channel channel = jsonTcpBean.getChannel();
		String appUserId = jsonTcpBean.getAppUserId();;
		if (StringUtil.isNotBlank(appUserId)) {
			NettyUser.channelUserAll.put(channelId, appUserId);
			NettyUser.userChannelAll.put(appUserId, channel);
		}
		// 取出data
		Map<String, Object> map = JsonThreadLocal.findThreadLocal().get().json2map(this.getJsonTcp());
		Object dataObject = map.get("data");
		if (JsonThreadLocal.findThreadLocal().get().isNull(dataObject)) {
		} else {
			if (dataObject instanceof JSONObject) {
				dataJSONObject = (JSONObject) dataObject;
				// log.trace("dataJSONObject="+dataJSONObject);
				dataMap = JsonThreadLocal.findThreadLocal().get().json2map(this.dataJSONObject.toString());
			}
			if (dataObject instanceof String) {
				data = dataObject.toString();
			}
		}
		// 执行具体业务
		JsonTcpBean returnJsonTcpBean = this.executeTcp();
		// 设置类型等属性
		returnJsonTcpBean.setUrl(jsonTcpBean.getUrl());
		returnJsonTcpBean.setAppUserId(jsonTcpBean.getAppUserId());
		returnJsonTcpBean.setToken(jsonTcpBean.getToken());
		returnJsonTcpBean.setRequestType(RequestTypeEnum.USER.toString());
		return returnJsonTcpBean;
	}
	@Override
	public String execute() throws Exception {
		return null;
	}
	/**
	 * 主动向指定客户端发送数据
	 * 
	 * @Title: sendList
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonTcpBean
	 * @throws Exception
	 *             返回类型 void
	 */
	public void sendList(JsonTcpBean jsonTcpBean) throws Exception {
		TcpNettyUtil nettyUtil = TcpNettyUtil.findInstance();
		nettyUtil.send(jsonTcpBean);
	}
}
