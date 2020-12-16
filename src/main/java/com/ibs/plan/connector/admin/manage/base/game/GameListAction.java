package com.ibs.plan.connector.admin.manage.base.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 游戏列表
 * @Author: null
 * @Date: 2020-04-17 11:22
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/game/list", method = HttpConfig.Method.GET)
public class GameListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//游戏名称
		String gameName = StringTool.getString(dataMap, "gameName", "");
		try {
			Map<String, Object> data = new HashMap<>(2);
			IbspGameService gameService = new IbspGameService();
			List<Map<String, Object>> gameInfo = gameService.listShow(gameName);

			data.put("gameName", gameName);
			data.put("gameInfo", gameInfo);
			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error("游戏列表错误", e);
			return bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
