package com.ibs.plan.connector.admin.manage.base.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_plan.service.IbspPlanService;
import com.ibs.plan.module.cloud.ibsp_plan_game.entity.IbspPlanGame;
import com.ibs.plan.module.cloud.ibsp_plan_game.service.IbspPlanGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.*;

/**
 * @Description: 游戏方案新增
 * @Author: admin1
 * @Date: 2020/6/18 17:44
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/gamePlan/save", method = HttpConfig.Method.POST)
public class GamePlanSaveAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String gameId = StringTool.getString(dataMap, "gameId", "");
		String planCodes = StringTool.getString(dataMap, "planCodes", "");

		if (StringTool.isEmpty(gameId)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		Date nowTime = new Date();
		try {
			IbspPlanGameService planGameService = new IbspPlanGameService();
			// 删除已存在的方案
			List<String> existPlans = planGameService.findPlanCodeByGameId(gameId);
			Set<String> codes = new HashSet<>(Arrays.asList(planCodes.split(",")));
			codes.removeIf(existPlans::contains);

			IbspPlanGame planGame = new IbspPlanGame();
			planGame.setGameId(gameId);
			planGame.setCreateTime(nowTime);
			planGame.setUpdateTime(nowTime);
			planGame.setState(IbsStateEnum.OPEN.name());
			//获取方案的基本信息
			IbspPlanService planService = new IbspPlanService();
			Map<String, Map<String, Object>> planInfos = planService.findPlanInfo(codes);
			for (String code : codes) {
				Map<String, Object> planInfo = planInfos.get(code);
				planGame.setIbspPlanGameId(null);
				planGame.setPlanId(planInfo.get("PLAN_ID_"));
				planGame.setPlanName(planInfo.get("PLAN_NAME_"));
				planGame.setPlanCode(code);
				planGame.setSn(planInfo.get("SN_"));
				planGame.setCreateTimeLong(System.currentTimeMillis());
				planGame.setUpdateTimeLong(System.currentTimeMillis());
				planGameService.save(planGame);
			}

			bean.success();
		} catch (Exception e) {
			log.error("游戏添加错误", e);
			throw e;
		}
		return bean.toString();
	}
}
