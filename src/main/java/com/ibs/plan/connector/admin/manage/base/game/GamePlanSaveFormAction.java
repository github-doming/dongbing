package com.ibs.plan.connector.admin.manage.base.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.ibsp_game.entity.IbspGame;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import com.ibs.plan.module.cloud.ibsp_plan_game.service.IbspPlanGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description: 游戏方案表单
 * @Author: admin1
 * @Date: 2020/6/18 17:23
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/gamePlan/form", method = HttpConfig.Method.GET)
public class GamePlanSaveFormAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口游戏id
		String gameId = StringTool.getString(dataMap, "GAME_ID_", "");

		try {
			IbspGameService gameService = new IbspGameService();
			IbspGame game = gameService.find(gameId);
			if (game == null) {
				bean.putEnum(CodeEnum.IBS_401_DATA);
				bean.putSysEnum(CodeEnum.CODE_401);
				return super.returnJson(bean);
			}
			IbspPlanGameService planGameService = new IbspPlanGameService();
			List<String> gamePlanInfo = planGameService.findPlanCodeByGameId(gameId);

			String gameType = GameUtil.type(game.getIbspGameId());
			IbspPlanService planService = new IbspPlanService();
			List<Map<String, Object>> allPlanInfo = planService.findPlanList();
			Iterator<Map<String, Object>> iterator = allPlanInfo.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> map = iterator.next();
				String availableGameType = map.remove("AVAILABLE_GAME_TYPE_").toString();
				//剔除掉不适用该游戏的方案信息
				if (!availableGameType.contains(gameType)) {
					iterator.remove();
				}
				map.put("has", false);
				if (gamePlanInfo.contains(map.get("PLAN_CODE_").toString())) {
					map.put("has", true);
				}
			}

			bean.setData(allPlanInfo);
			bean.success();
		} catch (Exception e) {
			log.error("盘口方案表单错误", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}


}
