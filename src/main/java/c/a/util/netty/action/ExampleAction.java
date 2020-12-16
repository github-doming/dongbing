package c.a.util.netty.action;
import java.util.ArrayList;
import java.util.List;
import all.gen.ga_gameplay.c.entity.GaGameplayT;
import all.gen.ga_gameplay.c.service.GaGameplayService;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonThreadLocal;
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
public class ExampleAction extends TcpAction {
	@Override
	public JsonTcpBean executeTcp() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		jrb.setData("[tcp发送测试]");
		// 指定用户发送
		List<String> userSend = new ArrayList<String>();
		jrb.setUserSend(userSend);
		if (false) {
			// 先发一次
			String str = "我们系统sysmsg childChannel ID=";
			JsonTcpBean jrb2 = new JsonTcpBean();
			jrb2.setData(str);
			jrb2.setUserSend(userSend);
			this.sendList(jrb2);
		}
		if (false) {
			GaGameplayService gaGameplayService = new GaGameplayService();
			// 去数据库查找
			List<GaGameplayT> gaGameplayTList = gaGameplayService.findAll();
			// System.out.println("2 size=" + gaGameplayTList.size());
			log.trace("2 size=" + gaGameplayTList.size());
			String resultJsonStr = JsonThreadLocal.findThreadLocal().get().array2json(gaGameplayTList);
			JsonTcpBean jrb2 = new JsonTcpBean();
			jrb2.setData(resultJsonStr);
			jrb2.setUserSend(userSend);
			this.sendList(jrb2);
		}
		if (true) {
			GaGameplayService gaGameplayService = new GaGameplayService();
			// 去数据库查找
			List<GaGameplayT> gaGameplayTList = gaGameplayService.findAll();
			// System.out.println("2 size=" + gaGameplayTList.size());
			log.trace("2 size=" + gaGameplayTList.size());
			String resultJsonStr = JsonThreadLocal.findThreadLocal().get().array2json(gaGameplayTList);
			jrb.setData(resultJsonStr);
			jrb.setUserSend(userSend);
			this.sendList(jrb);
		}
		return jrb;
	}
}
