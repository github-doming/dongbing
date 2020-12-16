package c.a.util.netty.action;
import java.util.ArrayList;
import java.util.List;

import c.a.util.core.json.JsonTcpBean;
/**
 * 
 * http://localhost:8080/a/all/netty/example.do
 * 
 * @Description:
 * @ClassName: SocketAction
 * @date 2019年6月5日 上午11:14:15
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class MsgAction extends TcpAction {
	@Override
	public JsonTcpBean executeTcp() throws Exception {
		JsonTcpBean jrb=this.getJsonTcpBean();
		jrb.setData("MsgAction发送测试,data="+jrb.getData());
		//指定用户发送
		List<String> userSend=new ArrayList<String>();
		jrb.setUserSend(userSend);
		return jrb;
	}
}
