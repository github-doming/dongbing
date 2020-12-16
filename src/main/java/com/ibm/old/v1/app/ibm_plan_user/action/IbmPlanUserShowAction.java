package com.ibm.old.v1.app.ibm_plan_user.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.cloud.ibm_plan_user.t.service.IbmPlanUserTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAsynCommAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmModeEnum;
import com.ibm.old.v1.common.doming.tools.IbmCmdTool;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 用户方案信息展示
 * @Author: Dongming
 * @Date: 2019-08-02 15:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(name = "planUserShow", value = "/ibm/app/ibm_plan_user/show.dm") public class IbmPlanUserShowAction
		extends BaseAsynCommAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		String gameCode = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("GAME_CODE_"), "");
		String planCode = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("PLAN_CODE_"), "");
		if (StringTool.isEmpty(planCode, gameCode, cmd)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		try {
			String planId = new IbmPlanTService().findIdByCode(planCode, gameCode);
			if (StringTool.isEmpty(planId)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_PLAN);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}
			String planItemTableId = new IbmPlanUserTService()
					.findUserPlanItemInfoById(planId, appUserT.getAppUserId());
			if (StringTool.isEmpty(planId)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_PLAN);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}
			Map<String, Object> planItemInfo = new HashMap<>(2);
			planItemInfo.put("Code", planCode);
			planItemInfo.put("ID_", planItemTableId);
			Map<String, Object> showData = new IbmPlanItemService().findShow(planItemInfo);
			if (ContainerTool.isEmpty(showData)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_PLAN_ITEM);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}

			jrb.success();
			Map<String, Object> data = new HashMap<>(5);
			data.put("PLAN_CODE_", planCode);
			data.put("ID_", planItemTableId);
			switch (IbmCmdTool.Plan.valueOf(cmd)) {
				case PROFIT_LIMIT:
					data.put("PROFIT_LIMIT_MAX_", showData.get("PROFIT_LIMIT_MAX_"));
					data.put("LOSS_LIMIT_MIN_", showData.get("LOSS_LIMIT_MIN_"));
					jrb.setData(data);
					break;
				case FUNDS_BET_RULE:
					planItemInfo.put("FUNDS_LIST_", showData.get("FUNDS_LIST_"));
					planItemInfo.put("FUNDS_LIST_ID_", showData.get("FUNDS_LIST_ID_"));
					planItemInfo.put("FOLLOW_PERIOD_", showData.get("FOLLOW_PERIOD_"));
					planItemInfo.put("MONITOR_PERIOD_", showData.get("MONITOR_PERIOD_"));
					planItemInfo.put("BET_MODE_", showData.get("BET_MODE_"));
					planItemInfo.put("FUND_SWAP_MODE_", showData.get("FUND_SWAP_MODE_"));
					planItemInfo.put("PERIOD_ROLL_MODE_", showData.get("PERIOD_ROLL_MODE_"));
					data.put("planItemInfo", planItemInfo);
					data.put("betMode", IbmModeEnum.listBetMode());
					data.put("fundSwapMode", IbmModeEnum.getFundSwapMode());
					data.put("periodRollMode", IbmModeEnum.getPeriodRollMode());
					jrb.setData(data);
					break;
				case BET_ITEM:
					data.put("PLAN_GROUP_DATA_", showData.get("PLAN_GROUP_DATA_"));
					jrb.setData(data);
					break;
				default:
					jrb.setSuccess(false);
					jrb.putEnum(IbmCodeEnum.IBM_404_CMD);
					jrb.putSysEnum(IbmCodeEnum.CODE_404);
			}
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "打开用户方案列表错误", e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return jrb;
	}
}
