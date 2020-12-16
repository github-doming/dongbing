package com.ibs.plan.connector.admin.manage.base.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_game.entity.IbspGame;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 添加游戏
 * @Author: null
 * @Date: 2020-03-25 14:21
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/game/save")
public class GameSaveAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String gameCode = StringTool.getString(dataMap, "gameCode", "");
		String gameName = StringTool.getString(dataMap, "gameName", "");
		String icon = StringTool.getString(dataMap, "icon", "");

		int drawTime = NumberTool.getInteger(dataMap, "drawTime", -1);
		int sn = NumberTool.getInteger(dataMap, "sn", -1);

		if (StringTool.isEmpty(gameName, gameCode)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}

		try {
			IbspGameService gameService = new IbspGameService();
			IbspGame game = new IbspGame();
			game.setGameName(gameName);
			game.setGameCode(gameCode);
			if (StringTool.notEmpty(icon)) {
				game.setIcon(icon);
			}
			if (drawTime != -1) {
				game.setDrawTime(drawTime);
			}
			if (sn != -1) {
				game.setSn(sn);
			}
			game.setCreateUser(appUserId);
			game.setCreateTime(new Date());
			game.setCreateTimeLong(System.currentTimeMillis());
			game.setUpdateTime(new Date());
			game.setUpdateTimeLong(System.currentTimeMillis());
			game.setState(IbsStateEnum.OPEN.name());
			gameService.save(game);

			bean.success();
		} catch (Exception e) {
			log.error("游戏添加错误", e);
			throw e;
		}
		return bean.toString();
	}
}
