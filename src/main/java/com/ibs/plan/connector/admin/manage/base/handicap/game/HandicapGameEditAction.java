package com.ibs.plan.connector.admin.manage.base.handicap.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_handicap_game.entity.IbspHandicapGame;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 盘口游戏修改
 * @Author: null
 * @Date: 2020-04-18 13:37
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/game/edit",method = HttpConfig.Method.POST)
public class HandicapGameEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口游戏id
		String handicapGameId = StringTool.getString(dataMap, "handicapGameId", "");
		// 盘口游戏名
		String handicapGameName = StringTool.getString(dataMap, "handicapGameName", "");
		//游戏类型
		String type = StringTool.getString(dataMap, "type", "");
		//盘口游戏图标
		String icon = StringTool.getString(dataMap, "icon", "");
		//状态
		String state = StringTool.getString(dataMap, "state", "");
		//次序
		int sn = NumberTool.getInteger(dataMap, "sn", -1);
		//封盘时间
		int closeTime = NumberTool.getInteger(dataMap, "closeTime", -1);
		//开奖时间
		int drawTime = NumberTool.getInteger(dataMap, "drawTime", -1);

		if (StringTool.isEmpty(handicapGameId, handicapGameName, type, closeTime, icon, state, sn)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			IbspHandicapGameService handicapGameService = new IbspHandicapGameService();
			IbspHandicapGame handicapGame = handicapGameService.find(handicapGameId);
			if (handicapGame == null) {
				bean.putEnum(CodeEnum.IBS_404_HANDICAP_GAME);
				bean.putSysEnum(CodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			handicapGame.setGameDrawType(type);
			if (closeTime != -1) {
				handicapGame.setCloseTime(closeTime);
			}
			if (drawTime != -1) {
				handicapGame.setDrawTime(drawTime);
			}

			if (sn != -1) {
				handicapGame.setSn(sn);
			}
			handicapGame.setIcon(icon);
			handicapGame.setHandicapGameName(handicapGameName);
			handicapGame.setState(state);
			handicapGame.setUpdateUser(appUserId);
			handicapGame.setUpdateTime(new Date());
			handicapGame.setUpdateTimeLong(System.currentTimeMillis());
			handicapGameService.update(handicapGame);

			bean.success();
		} catch (Exception e) {
			log.error("盘口游戏修改错误", e);
			throw e;
		}
		return bean.toString();
	}
}
