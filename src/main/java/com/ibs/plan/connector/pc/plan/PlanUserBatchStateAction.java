package com.ibs.plan.connector.pc.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.PlanUtil;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_log_plan.entity.IbspLogPlan;
import com.ibs.plan.module.cloud.ibsp_log_plan.service.IbspLogPlanService;
import com.ibs.plan.module.cloud.ibsp_plan_hm.service.IbspPlanHmService;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import com.ibs.plan.module.cloud.ibsp_profit_plan.service.IbspProfitPlanService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 批量修改用户方案状态
 * @Author: null
 * @Date: 2020-06-01 15:49
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/plan/batchState", method = HttpConfig.Method.POST)
public class PlanUserBatchStateAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if(super.denyTime()){
			bean.putEnum(CodeEnum.IBS_503_TIME);
			bean.putSysEnum(CodeEnum.CODE_503);
			return bean;
		}
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		String planState = dataMap.getOrDefault("PLAN_STATE_", "").toString();

		String planCodes = dataMap.getOrDefault("PLAN_CODES_", "").toString();

		if (StringTool.isEmpty(gameCode,planCodes)||!IbsStateEnum.stateType(planState)||
				ContainerTool.isEmpty(planCodes.split(","))) {
			return bean.put401Data();
		}

		try {
			String[] codes=planCodes.split(",");

			String gameId =GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}
			IbspPlanUserService planUserService=new IbspPlanUserService();
			boolean flag=planUserService.updatePlanState(appUserId,codes,gameId,planState);
			if(!flag){
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			List<Map<String,Object>> existInfos= new IbspClientHmService().findMemberInfos(appUserId);
			if (ContainerTool.notEmpty(existInfos)){
				//写入客户设置事件
				JSONObject content = new JSONObject();
				content.put("PLAN_STATE_",planState);
				content.put("PLAN_CODES_",planCodes);
				content.put("GAME_CODE_",gameCode);
				content.put("METHOD_", IbsMethodEnum.SET_PLAN_BATCH_STATE.name());

				for(String planCode:codes){
					//修改方案会员
					new IbspPlanHmService().updateState(existInfos,PlanUtil.id(planCode) ,gameId,planState);
					//重置会员方案盈亏信息
					new IbspProfitPlanService().resetProfit(existInfos,PlanUtil.id(planCode),gameId,"ibsp_profit_plan");
					new IbspProfitPlanService().resetProfit(existInfos,PlanUtil.id(planCode),gameId,"ibsp_profit_plan_vr");
				}
				if(IbsStateEnum.OPEN.name().equals(planState)){
					//获取方案详情信息
					List<Map<String,Object>> planInfo=planUserService.findPlanInfo(appUserId,gameId,codes);
					List<Map<String,Object>> planItems=planUserService.findPlanItems(planInfo);
					content.put("PLAN_ITEM_",planItems);
				}
				for (Map<String,Object> map: existInfos){
					content.put("EXIST_HM_ID_", map.get("EXIST_HM_ID_"));
					String eventId= EventThreadDefine.saveMemberConfigSetEvent(content,new Date());
					content.put("EVENT_ID_",eventId);

					RabbitMqTool.sendMember(content.toString(), map.get("CLIENT_CODE_").toString(),"set");
				}
			}

			//添加方案用户日志信息
			savePlanLog(planState,planCodes);
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "修改用户方案状态失败", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}

	private void savePlanLog(String planState, String planCodes) throws Exception {
		IbspLogPlan logPlan=new IbspLogPlan();
		logPlan.setAppUserId(appUserId);
		logPlan.setPlanCode(planCodes);
		logPlan.setHandleType("BATCH_UPDATE");
		logPlan.setHandleContent("PLAN_STATE_:".concat(planState).concat(",PLAN_CODE_:").concat(planCodes));
		logPlan.setCreateTime(new Date());
		logPlan.setCreateTimeLong(System.currentTimeMillis());
		logPlan.setUpdateTimeLong(System.currentTimeMillis());
		logPlan.setState(IbsStateEnum.OPEN.name());
		logPlan.setDesc(this.getClass().getName());
		new IbspLogPlanService().save(logPlan);
	}
}
