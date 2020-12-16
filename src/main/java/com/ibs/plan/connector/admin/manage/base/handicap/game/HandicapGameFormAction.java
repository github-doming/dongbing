package com.ibs.plan.connector.admin.manage.base.handicap.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口游戏表单
 * @Author: null
 * @Date: 2020-04-18 10:53
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/game/form", method = HttpConfig.Method.GET)
public class HandicapGameFormAction extends CommAdminAction {
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
		//盘口id
		String handicapId = StringTool.getString(dataMap, "handicapId", "");
		try {
			Map<String, Object> data = new HashMap<>(3);
			IbspHandicapGameService handicapGameService = new IbspHandicapGameService();
			if (StringTool.notEmpty(handicapGameId)) {
				Map<String, Object> handicapGameInfo = handicapGameService.findInfoById(handicapGameId);
				data.put("handicapGameInfo", handicapGameInfo);
			}
			//新增盘口游戏页面
			if (StringTool.notEmpty(handicapId)) {
				IbspGameService gameService = new IbspGameService();
				List<Map<String, Object>> list = handicapGameService.findByHandicapId(handicapId);
				List<String> gameCodes = gameService.findAllGameCode();
				for (Map<String, Object> map : list) {
					gameCodes.remove(map.get("GAME_CODE_"));
				}
				if (ContainerTool.isEmpty(gameCodes)) {
					bean.putEnum(CodeEnum.IBS_404_HANDICAP_GAME);
					bean.putSysEnum(CodeEnum.CODE_404);
					return bean.toString();
				}

				List<Map<String, Object>> gameInfos = new ArrayList<>(gameCodes.size());
				for (String gameCode : gameCodes) {
					GameUtil.Code code = GameUtil.Code.valueOf(gameCode);
					Map<String, Object> gameInfo = new HashMap<>(2);
					String gameName = code.getName();
					gameInfo.put("gameCode", gameCode);
					gameInfo.put("gameName", gameName);
					gameInfos.add(gameInfo);


				}
				data.put("gameInfo", gameInfos);
				data.put("handicapId", handicapId);
			}

			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error("盘口游戏表单错误", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
