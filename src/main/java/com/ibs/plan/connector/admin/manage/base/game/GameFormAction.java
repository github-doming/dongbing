package com.ibs.plan.connector.admin.manage.base.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_game.entity.IbspGame;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 游戏表单页面
 * @Author: null
 * @Date: 2020-03-25 11:31
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/game/form", method = HttpConfig.Method.GET)
public class GameFormAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String gameId = StringTool.getString(dataMap, "gameId", "");
		try {
			IbspGameService gameService = new IbspGameService();
			IbspGame game = gameService.find(gameId);
			if (game == null) {
				bean.putEnum(CodeEnum.IBS_404_GAME);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			Map<String, Object> map = gameService.findInfoById(gameId);
			bean.setData(map);
			bean.success();
		} catch (Exception e) {
			log.error("游戏表单页面错误", e);
			return bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
