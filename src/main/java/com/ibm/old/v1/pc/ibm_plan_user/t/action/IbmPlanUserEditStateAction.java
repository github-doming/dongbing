package com.ibm.old.v1.pc.ibm_plan_user.t.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.entity.IbmLogPlanUserT;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.service.IbmLogPlanUserTService;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.cloud.ibm_plan_item.t.service.IbmPlanItemService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.ibm_plan_user.t.service.IbmPcPlanUserTService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.*;

/**
 * @Description: 用户方案状态信息修改
 * @Author: Dongming
 * @Date: 2019-01-17 15:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmPlanUserEditStateAction extends BaseAppAction {

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
		String state = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("STATE_"), "");
		if (StringTool.isEmpty(planCode, gameCode, state)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		if (!IbmStateEnum.isState(state)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_STATE);
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
			planItemInfo.put("CODE_",planCode);
			planItemInfo.put("ID_",planUserTService.findUserPlanItemInfoById(planId, appUserId));
			if (ContainerTool.isEmpty(planItemInfo)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_PLAN);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}

			//更新用户方案状态
			planUserTService.updateState(planId, appUserId,state);

			//同步盘口会员方案状态
			IbmPlanHmTService planHmTService = new IbmPlanHmTService();
			planHmTService.updateStateByUser(appUserId,state,planId,this.getClass().getName());

			//更新用户方案详情状态
			IbmPlanItemService planItemService = new IbmPlanItemService();
			planItemService.updateState(planItemInfo,state);


			//重置方案信息
			if(IbmStateEnum.CLOSE.name().equals(state)){
				List<String> planList=new ArrayList<>();
				planList.add(planId);
				IbmExecPlanGroupTService execPlanGroupTService = new IbmExecPlanGroupTService();
				List<String> planItemIdList = planUserTService.listPlanItemIdByPlan(appUserId,planList);
				execPlanGroupTService.replayPlan(planItemIdList);
			}
			Date nowTime = new Date();
			//用户方案操作记录日志
			IbmLogPlanUserT ibmLogPlanUserT = new IbmLogPlanUserT();
			IbmLogPlanUserTService ibmLogPlanUserTService = new IbmLogPlanUserTService();
			ibmLogPlanUserT.setPlanId(planId);
			ibmLogPlanUserT.setAppUserId(appUserT.getAppUserId());
			ibmLogPlanUserT.setGameId(GameTool.findId(gameCode));
			ibmLogPlanUserT.setHandleType("REPLAYPLAN");
			ibmLogPlanUserT.setCreateTime(nowTime);
			ibmLogPlanUserT.setCreateTimeLong(nowTime.getTime());
			ibmLogPlanUserT.setState(IbmStateEnum.OPEN.name());
			ibmLogPlanUserT.setDesc("STATE_：" + state );

			ibmLogPlanUserTService.save(ibmLogPlanUserT);

			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "修改用户方案详情错误", e);
			throw e;
		}
		return returnJson(jrb);
	}
}
