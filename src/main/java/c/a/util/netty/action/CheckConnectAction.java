package c.a.util.netty.action;
import c.a.util.core.json.JsonTcpBean;
/**
 * 
 * http://localhost:8080/a/all/netty/check/connect.do
 * 
 * 检测连接
 * 
 * 在线，离线，断线重连; Netty 实现心跳检测和断线重连;
 * 
 * @Description:
 * @date 2019年6月5日 上午11:14:15
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class CheckConnectAction extends TcpAction {
	@Override
	public JsonTcpBean executeTcp() throws Exception {
		JsonTcpBean jrb = this.getJsonTcpBean();
		jrb.setData("检测连接");
		return jrb;
	}
}
