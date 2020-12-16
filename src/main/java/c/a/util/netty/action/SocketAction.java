package c.a.util.netty.action;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.root.common.action.CommAction;
/**
 * 
 * http://localhost:8080/a/all/netty/socket.do
 * 
 * @Description:
 * @ClassName: SocketAction
 * @date 2019年6月5日 上午11:14:15
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class SocketAction extends CommAction {
	@Override
	public String execute() throws Exception {
		String resultJsonStr = null;
		JsonTcpBean socketJrb = this.getJsonTcpBean();
		if (socketJrb != null) {
			log.trace("1 tcp json=" + this.getJsonTcp());
			log.trace("1 tcp data=" + this.getJsonTcpBean().getData());
			resultJsonStr = "[Socket发送测试]" + this.getJsonTcp();
		}
		return resultJsonStr;
	}
}
