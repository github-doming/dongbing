package all.app.common.action;
import java.util.Map;

import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.json.JsonTcpBean;
public abstract class AppController {
	/**
	 * 
	 * @Title: execute 
	 * @Description: 
	 *
	 * 参数说明 
	 * @param dataMap
	 * @param appUserT 当前用户
	 * @return
	 * @throws Exception 
	 * 返回类型 JsonTcpBean
	 */
	public abstract JsonTcpBean execute(	Map<String, Object> dataMap,  AppUserT appUserT) throws Exception ;
}
