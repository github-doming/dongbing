package com.ibm.old.v1.pc.ibm_plan_user.t.controller;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.pc.ibm_plan_user.t.service.IbmPcPlanUserTService;

import java.sql.SQLException;
import java.util.*;

/**
 * @Description: 用户方案控制器
 * @Author: Dongming
 * @Date: 2019-01-10 16:40
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmPlanUserController {

	/**
	 * 获取用户主界面的游戏方案信息
	 *
	 * @param userId 用户地点
	 * @return 用户主界面的游戏方案信息
	 */
	public Collection<Map<String, Object>> listHomeGamePlanOrder(String userId) throws SQLException {
		//游戏列表
		IbmGameTService gameTService = new IbmGameTService();
		List<Map<String, Object>> gameInfo = gameTService.listInfo();

		List<String> gameIds = new ArrayList<>(gameInfo.size());
		Map<String,Map<String,Object>> gameKeyInfo = new HashMap<>(gameInfo.size());
		gameInfo.forEach(info -> {
			String gameId = info.get("IBM_GAME_ID_").toString();
			info.remove("IBM_GAME_ID_");
			info.remove("ROW_NUM");
			gameIds.add(gameId);
			gameKeyInfo.put(gameId,info);

		});
		IbmPcPlanUserTService planUserTService = new IbmPcPlanUserTService();
		Map<String, List<Map<String, Object>>> planInfo = planUserTService.listHomeInfoOrder(gameIds, userId);
		planInfo.forEach((gameId,info)->
			gameKeyInfo.get(gameId).put("planList",info)
		);

		return gameKeyInfo.values();
	}
}
