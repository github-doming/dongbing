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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description: 选中方案详情状态修改
 * @date 2019年2月13日 下午5:04:29 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmPlanUserEditAllStateAction extends BaseAppAction {

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
		//游戏编码
		String gameCode = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("GAME_CODE_"), "");
		//方案编码组
		String planCodeArray = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("PLAN_CODE_"), "");
		//方案状态
		String state = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("STATE_"), "");
		if (StringTool.isEmpty(planCodeArray, gameCode, state)) {
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
			String[] planCodes = planCodeArray.split(",");
			//根据游戏code，方案code获取方案id集合
			IbmPlanTService planTService = new IbmPlanTService();
			List<String> planIdList = planTService.findIdByCodes(planCodes, gameCode);
			if (ContainerTool.isEmpty(planIdList)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_PLAN);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}

			
			IbmPcPlanUserTService planUserTService = new IbmPcPlanUserTService();
			//通过方案ID集合查找出多个用户方案信息
			List<Map<String, Object>> planItemInfos = planUserTService.findUserPlanItemInfoByIds(planIdList, appUserId);
			if (ContainerTool.isEmpty(planItemInfos)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_PLAN);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}

			//更新用户选中方案状态
			planUserTService.updateAllState(planIdList, appUserId,state,this.getClass().getName());

			//同步盘口会员方案状态
			IbmPlanHmTService planHmTService = new IbmPlanHmTService();
			planHmTService.updateStateByPlanUser(appUserId,state,planIdList,this.getClass().getName());

			//更新用户选中方案详情状态
			IbmPlanItemService planItemService = new IbmPlanItemService();
			planItemService.updateAllState(planItemInfos,state);

			//重置方案信息
			if(IbmStateEnum.CLOSE.name().equals(state)){
				IbmExecPlanGroupTService execPlanGroupTService = new IbmExecPlanGroupTService();
				List<String> planItemIdList = planUserTService.listPlanItemIdByPlan(appUserId,planIdList);
				execPlanGroupTService.replayPlan(planItemIdList);
			}
			//每个方案修改后都生成一条信息记录到日志中
			for (String planId : planIdList) {
				Date nowTime = new Date();
				//用户方案操作记录日志中
				IbmLogPlanUserT ibmLogPlanUserT = new IbmLogPlanUserT();
				IbmLogPlanUserTService ibmLogPlanUserTService = new IbmLogPlanUserTService();
				ibmLogPlanUserT.setPlanId(planId);
				ibmLogPlanUserT.setAppUserId(appUserT.getAppUserId());
				ibmLogPlanUserT.setGameId(GameTool.findId(gameCode));
				ibmLogPlanUserT.setHandleType("REPLAYPLAN");
				ibmLogPlanUserT.setCreateTime(nowTime);
				ibmLogPlanUserT.setCreateTimeLong(nowTime.getTime());
				ibmLogPlanUserT.setState(IbmStateEnum.OPEN.name());
				ibmLogPlanUserT.setDesc("，STATE_：" + state);
				ibmLogPlanUserTService.save(ibmLogPlanUserT);

			}

			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "修改用户方案详情错误", e);
			throw e;
		}
		return returnJson(jrb);
	}
}
