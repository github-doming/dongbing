package org.doming.develop.websocket;
import all.app.common.service.AppUserSysService;
import all.gen.app_user.t.entity.AppUserT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.json.JsonThreadLocal;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;
/**
 * @Description: websocket游戏服务类
 * @Author: Dongming
 * @Date: 2018-11-22 15:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseGameWebSocket extends BaseGameRoomWebSocketMap {

	protected JSONObject dataJSONObject = null;
	protected String appUserId = null;
	protected String userToken = null;

	/**
	 * 调用方法，初始化数据 返回用户对象
	 */
	public AppUserT findAppUser() throws Exception {
		AppUserSysService appUserSysService = new AppUserSysService();
		Map<String, Object> map = JsonThreadLocal.findThreadLocal().get().json2map(super.message);
		if (ContainerTool.notEmpty(map)) {
			userToken = BeanThreadLocal.findThreadLocal().get().find(map.get("token"), "");
			if (StringTool.notEmpty(userToken)) {
				AppUserT appUserT = appUserSysService.findAppUserByToken(userToken);
				if (appUserT != null) {
					// 得到用户的appUserId
					appUserId = appUserT.getAppUserId();
					return appUserT;
				}
			}
		}
		return null;
	}
	/**
	 * 调用方法，初始化数据 返回用户对象
	 */
	public AppUserT findAppUserByJson() throws Exception {
		AppUserSysService appUserSysService = new AppUserSysService();
		Map<String, Object> map = JsonThreadLocal.findThreadLocal().get().json2map(super.message);
		if (ContainerTool.notEmpty(map)) {
			userToken = BeanThreadLocal.findThreadLocal().get().find(map.get("token"), "");
			dataJSONObject = (JSONObject) map.get("data");
			if (StringTool.notEmpty(userToken)) {
				AppUserT appUserT = appUserSysService.findAppUserByToken(userToken);
				if (appUserT != null) {
					// 得到用户的appUserId
					appUserId = appUserT.getAppUserId();
					return appUserT;
				}
			}
		}
		return null;
	}
	/**
	 * 将JsonResultBean对象转化为json字符串
	 */
	public String returnJson(JsonResultBean jsonResultBean) {
		return JsonThreadLocal.bean2json(jsonResultBean);
	}
}
