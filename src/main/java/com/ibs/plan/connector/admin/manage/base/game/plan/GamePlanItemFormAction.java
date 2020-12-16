package com.ibs.plan.connector.admin.manage.base.game.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_plan_game.service.IbspPlanGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 游戏方案详情表单
 * @Author: admin1
 * @Date: 2020/6/18 17:23
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/game/plan/form", method = HttpConfig.Method.GET)
public class GamePlanItemFormAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口游戏id
		String planGameId = StringTool.getString(dataMap, "PLAN_GAME_ID_", "");

		try {
			IbspPlanGameService planGameService = new IbspPlanGameService();
			Map<String, Object> planInfo = planGameService.findPlanGameItem(planGameId);

			bean.setData(planInfo);
			bean.success();
		} catch (Exception e) {
			log.error("盘口方案表单错误", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
