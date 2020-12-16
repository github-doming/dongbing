package c.a.util.netty.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import all.gen.ga_gameplay.c.entity.GaGameplayT;
import all.gen.ga_gameplay.c.service.GaGameplayService;
import c.a.util.core.json.JsonTcpBean;
/**
 * 
 * http://localhost:8080/a/all/netty/card.do
 * 
 * 初始化牌
 * @Description:
 * @ClassName: SocketAction
 * @date 2019年6月5日 上午11:14:15
 * @author cxy
 * @Email:
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class CardAction extends TcpAction {
	@Override
	public JsonTcpBean executeTcp() throws Exception {
		JsonTcpBean jrb = this.getJsonTcpBean();
		jrb.setData("CardAction发送测试,data=" + jrb.getData());
		GaGameplayService gaGameplayService = new GaGameplayService();
		// 去数据库查找
		List<GaGameplayT> gaGameplayTList = gaGameplayService.findAll();
		jrb.setData(gaGameplayTList );
		//jrb.setData("a1234567890");
		// 指定用户发送
		List<String> userSend = new ArrayList<String>();
		//userSend.add(jrb.getAppUserId());
		userSend.add("6e7e5328279846f689f5ea22744955ff");
		userSend.add("6aaa046d83b640558cad1638fe0cfb25");
		userSend.add("181fe99a9de74622abded68f1b787021");
		jrb.setUserSend(userSend);
		// 只对自己发送数据
		Map<String, Object> userSendMap = new HashMap<String, Object>();
		List<String>  listStr1=new ArrayList<String>();
		listStr1.add("1");
		listStr1.add("2");
		listStr1.add("3");
		Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("card", listStr1);
		userSendMap.put("6e7e5328279846f689f5ea22744955ff", map1);
		List<String> listStr2=new ArrayList<String>();
		listStr2.add("5");
		listStr2.add("6");
		listStr2.add("7");
		Map<String,Object> map2=new HashMap<String,Object>();
		map2.put("card", listStr2);
		userSendMap.put("6aaa046d83b640558cad1638fe0cfb25", map2);
		List<String>  listStr3=new ArrayList<String>();
		listStr3.add("8");
		listStr3.add("9");
		listStr3.add("10");
		Map<String,Object> map3=new HashMap<String,Object>();
		map3.put("card", listStr3);
		userSendMap.put("181fe99a9de74622abded68f1b787021", map3);
		//userSendMap.put(jrb.getAppUserId(), "a1");
		jrb.setUserSendMap(userSendMap);
		if (true) {
			// 先发一次
			JsonTcpBean jrb2 = new JsonTcpBean();
			String str = "我们系统sysmsg childChannel ID=";
			jrb2.setUrl("/all/netty/card1.do");
			jrb2.setData(str);
			jrb2.setUserSend(userSend);
			this.sendList(jrb2);
		}
		return jrb;
	}
}
