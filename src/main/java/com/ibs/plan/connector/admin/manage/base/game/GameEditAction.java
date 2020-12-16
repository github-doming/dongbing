package com.ibs.plan.connector.admin.manage.base.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_game.entity.IbspGame;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 游戏设置
 * @Author: null
 * @Date: 2020-03-25 14:05
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/game/edit", method = HttpConfig.Method.POST)
public class GameEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String gameId = StringTool.getString(dataMap, "GAME_ID_", "");
		String icon = StringTool.getString(dataMap, "ICON_", "");
		String drawTime = StringTool.getString(dataMap, "DRAW_TIME_", "");
		String repDrawTableName = StringTool.getString(dataMap, "REP_DRAW_TABLE_NAME_", "");
		String state = StringTool.getString(dataMap, "STATE_", "");
		int sn = NumberTool.getInteger(dataMap, "SN_", 0);
		if (StringTool.isEmpty(gameId, state)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {

			IbspGameService gameService = new IbspGameService();
			IbspGame game = gameService.find(gameId);
			if (game == null) {
				bean.putEnum(CodeEnum.IBS_404_GAME);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			if (!game.getState().equals(state)) {
				game.setState(state);
				new IbspHandicapGameService().updateByHandicapId(game.getIbspGameId(), state, appUserId);
			}

			if (StringTool.notEmpty(icon)) {
				game.setIcon(icon);
			}
			if (StringTool.notEmpty(icon)) {
				game.setDrawTime(Integer.parseInt(drawTime));
			}
			if (sn != 0) {
				game.setSn(sn);
			}

			if (StringTool.notEmpty(repDrawTableName)) {
				game.setRepDrawTableName(repDrawTableName);
			}
			game.setUpdateUser(appUserId);
			game.setUpdateTime(new Date());
			game.setUpdateTimeLong(System.currentTimeMillis());
			gameService.update(game);

			bean.success();
		} catch (Exception e) {
			log.error("游戏设置错误", e);
			throw e;
		}
		return bean.toString();
	}
}
