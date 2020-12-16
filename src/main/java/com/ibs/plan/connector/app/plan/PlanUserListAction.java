package com.ibs.plan.connector.app.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.List;
import java.util.Map;

/**
 * 用户方案列表
 * @Author: Dongming
 * @Date: 2020-05-25 14:43
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/plan/list", method = HttpConfig.Method.GET)
public class PlanUserListAction extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
		if (StringTool.isEmpty(gameCode)) {
			return bean.put401Data();
		}
		try {
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)){
				bean.putEnum(CodeEnum.IBS_404_GAME);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//验证用户是否有该游戏
			if (!new IbspExpUserService().hasGame(appUserId,gameCode)){
				bean.putEnum(CodeEnum.IBS_403_PERMISSION);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			IbspPlanUserService planUserService = new IbspPlanUserService();

			//方案列表展示信息
			List<Map<String,Object>> data = planUserService.listInfo(appUserId,gameId);

			data.forEach(info -> {
				info.remove("ROW_NUM");
				info.put("SN_",String.format("%03d",Integer.parseInt(info.get("SN_").toString())));
			});
			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "获取用户方案列表错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
