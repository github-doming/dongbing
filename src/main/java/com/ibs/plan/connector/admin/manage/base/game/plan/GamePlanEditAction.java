package com.ibs.plan.connector.admin.manage.base.game.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_plan_game.entity.IbspPlanGame;
import com.ibs.plan.module.cloud.ibsp_plan_game.service.IbspPlanGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 游戏方案修改
 * @Author: admin1
 * @Date: 2020/6/18 17:33
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/game/plan/edit", method = HttpConfig.Method.POST)
public class GamePlanEditAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口游戏id
		String planGameId = StringTool.getString(dataMap, "planGameId", "");
		String planName = StringTool.getString(dataMap, "planName", "");
		String state = StringTool.getString(dataMap, "state", "");

		int sn = NumberTool.getInteger(dataMap, "sn", -1);

		try {
			IbspPlanGameService planGameService = new IbspPlanGameService();
			IbspPlanGame planGame = planGameService.find(planGameId);
			if (planGame == null) {
				bean.putEnum(CodeEnum.IBS_404_EXIST);
				bean.putSysEnum(CodeEnum.CODE_404);
				return returnJson(bean);
			}
			if(StringTool.notEmpty(planName)){
				planGame.setPlanName(planName);
			}
			if (sn != -1) {
				planGame.setSn(sn);
			}
			planGame.setState(state);
			planGameService.update(planGame);

			bean.success();
		} catch (Exception e) {
			log.error("游戏方案设置错误", e);
			throw e;
		}
		return bean.toString();
	}
}
