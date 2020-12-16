package com.ibs.plan.connector.app.plan.item;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.ibsp_plan_item.service.IbspPlanItemService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 方案详情信息
 * @Author: null
 * @Date: 2020-06-01 16:55
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/plan/itemInfo", method = HttpConfig.Method.GET)
public class PlanItemInfoAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		String planCode = dataMap.getOrDefault("PLAN_CODE_", "").toString();

		if (StringTool.isEmpty(gameCode, planCode)) {
			return bean.put401Data();
		}
		try {
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}
			IbspPlanUserService planUserService = new IbspPlanUserService();
			Map<String, Object> planItemTableInfo = planUserService.findItemTable(appUserId, planCode, gameId);
			if (ContainerTool.isEmpty(planItemTableInfo)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			IbspPlanItemService planItemService = new IbspPlanItemService();
			Map<String, Object> planItemInfo = planItemService.findPlanItemInfo(planItemTableInfo);
			if (ContainerTool.isEmpty(planItemInfo)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			planItemInfo.put("PLAN_NAME_",planItemTableInfo.get("PLAN_NAME_"));
			bean.success(planItemInfo);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "获取方案详情信息失败", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
