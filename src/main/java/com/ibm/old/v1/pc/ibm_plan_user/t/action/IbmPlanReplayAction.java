package com.ibm.old.v1.pc.ibm_plan_user.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_log_plan_user.t.service.IbmLogPlanUserTService;
import com.ibm.old.v1.cloud.ibm_plan.t.service.IbmPlanTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_plan_user.t.service.IbmPcPlanUserSetService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Description: 重置方案
 * @date 2019年1月24日 下午5:04:53 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmPlanReplayAction extends BaseAppAction {

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(jrb);
		}
		//游戏CODE
		String gameCode = BeanThreadLocal.find(dataMap.get("GAME_CODE_"), "");
		//方案CODE
		String planCodeArray = BeanThreadLocal.find(dataMap.get("PLAN_CODE_"), "");
		
		if (StringTool.isEmpty(gameCode,planCodeArray)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		String[] planCodes = planCodeArray.split(",");
		
		try {
			//获取游戏ID
			IbmGameTService ibmGameTService = new IbmGameTService();
			String gameId = ibmGameTService.findId(gameCode);
			if (StringTool.isEmpty(gameId)) {
				jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
				jrb.putSysEnum(IbmCodeEnum.CODE_401);
				return this.returnJson(jrb);
			}
			//获取方案id和方案详情表
			IbmPlanTService ibmPlanTService = new IbmPlanTService();
			Map<Object,Object> planMap=ibmPlanTService.findIdAndTableName(planCodes,gameId);

			if(ContainerTool.isEmpty(planMap)){
				jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
				jrb.putSysEnum(IbmCodeEnum.CODE_401);
				return this.returnJson(jrb);
			}
			//获取需要重置的方案详情表id
			IbmPcPlanUserSetService planUserSetService = new IbmPcPlanUserSetService();
			List<String> planItemTableIds =planUserSetService.resetPlanId(planMap,appUserId);
			if(ContainerTool.isEmpty(planItemTableIds)){
				jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
				jrb.putSysEnum(IbmCodeEnum.CODE_401);
				return this.returnJson(jrb);
			}

			//重置方案投注项状态
			IbmExecPlanGroupTService execPlanGroupTService = new IbmExecPlanGroupTService();
			execPlanGroupTService.replayPlan(planItemTableIds);

			//写入日志
			IbmLogPlanUserTService ibmLogPlanUserTService=new IbmLogPlanUserTService();
			ibmLogPlanUserTService.save(planMap,gameId,appUserT.getAppUserId());
			
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "方案重置失败", e);
			throw e;
		}
		return returnJson(jrb);
	}

}
