package com.ibm.old.v1.pc.ibm_plan_user.t.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmModeEnum;
import com.ibm.old.v1.pc.ibm_plan_user.t.service.IbmPcPlanUserTService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 用户方案详情列表
 * @Author: Dongming
 * @Date: 2019-01-15 14:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmPlanUserShowAction extends BaseAppAction {

	@Override public String run() throws Exception {
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
		if (StringTool.isEmpty(planCode, gameCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		try {
			IbmPlanTService planTService = new IbmPlanTService();
			String planId = planTService.findIdByCode(planCode, gameCode);
			if (StringTool.isEmpty(planId)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_PLAN);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}

			IbmPcPlanUserTService planUserTService = new IbmPcPlanUserTService();
			Map<String,Object> planItemInfo = new HashMap<>(2);
			planItemInfo.put("Code",planCode);
			planItemInfo.put("ID_",planUserTService.findUserPlanItemInfoById(planId, appUserId));



			if (ContainerTool.isEmpty(planItemInfo)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_PLAN);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}

			Map<String, Object> data = new HashMap<>(5);

			//方案详情
			IbmPlanItemService planItemService = new IbmPlanItemService();

			data.put("planItemInfo",planItemInfo);
			data.put("showData",planItemService.findShow(planItemInfo));
			data.put("betMode",IbmModeEnum.listBetMode());
			data.put("fundSwapMode",IbmModeEnum.getFundSwapMode());
			data.put("periodRollMode",IbmModeEnum.getPeriodRollMode());

			jrb.success();
			jrb.setData(data);
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "打开用户方案列表错误", e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return returnJson(jrb);
	}

}
