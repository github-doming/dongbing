package com.ibm.follow.connector.admin.manage.base.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_plan.entity.VrPlan;
import com.ibm.follow.servlet.cloud.vr_plan.service.VrPlanService;
import com.ibm.follow.servlet.cloud.vr_plan_game.entity.VrPlanGame;
import com.ibm.follow.servlet.cloud.vr_plan_game.service.VrPlanGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.*;

/**
 * @Description: 方案修改
 * @Author: admin1
 * @Date: 2020/6/20 14:10
 * @Version: v1.0
 */

@ActionMapping(value = "/ibm/admin/manage/plan/edit", method = HttpConfig.Method.POST)
public class PlanEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口id
		String planId = StringTool.getString(dataMap, "PLAN_ID_", "");

		//序号
		int sn = NumberTool.getInteger(dataMap.get("SN_"), -1);
		//备注
		String desc = StringTool.getString(dataMap, "DESC_", "");
		//方案状态
		String state = StringTool.getString(dataMap, "STATE_", "");
		//适用游戏类型
		String availableGameType = StringTool.getString(dataMap, "AVAILABLE_GAME_TYPE_", "");
		if (StringTool.isEmpty(planId, sn, state, desc)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}

		try {
			VrPlanService planService = new VrPlanService();
			VrPlan plan = planService.find(planId);
			if (plan == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return super.returnJson(bean);
			}

			plan.setDesc(desc);
			plan.setSn(sn);
			plan.setUpdateTimeLong(System.currentTimeMillis());
			if (!plan.getState().equals(state)) {
				//更新状态 ibsp_plan_game,ibsp_plan_hm,ibsp_plan_user
				planService.updatePlanState(planId, state);
				plan.setState(state);
			}
			//方案游戏设置
			setPlanGame(availableGameType, plan);
			plan.setAvailableGameType(availableGameType);


			planService.update(plan);


			bean.success();
		} catch (Exception e) {
			log.error(" 方案修改错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	private void setPlanGame(String newGameType, VrPlan plan) throws Exception {
		List<String> newDate = Arrays.asList(newGameType.split(","));
		List<String> oldData = Arrays.asList(plan.getAvailableGameType().split(","));

		Set<Object> saveGameType = new HashSet<>(ContainerTool.getDifferent(newDate, oldData));

		Set<Object> delGameType = new HashSet<>(ContainerTool.getDifferent(newDate, oldData));


		VrPlanGameService planGameService = new VrPlanGameService();
		if (ContainerTool.notEmpty(saveGameType)) {
			VrPlanGame planGame = new VrPlanGame();
			planGame.setPlanId(plan.getVrPlanId());
			planGame.setPlanName(plan.getPlanName());
			planGame.setPlanCode(plan.getPlanCode());
			planGame.setCreateTime(new Date());
			planGame.setCreateTimeLong(System.currentTimeMillis());
			planGame.setUpdateTimeLong(System.currentTimeMillis());
			planGame.setState(IbmStateEnum.OPEN.name());
			for (Object gameType : saveGameType) {
				List<String> gameCodes = getGameCodes(gameType.toString());
				for (String gameCode : gameCodes) {
					planGame.setGameCode(gameCode);
					planGameService.save(planGame);
					planGame.setVrPlanGameId(null);
				}
			}
		}
		if (ContainerTool.notEmpty(delGameType)) {
			List<String> delGameCodes = new ArrayList<>();
			for (Object gameType : saveGameType) {
				List<String> gameCodes = getGameCodes(gameType.toString());
				delGameCodes.addAll(gameCodes);
			}
			if(ContainerTool.notEmpty(delGameCodes)){
				planGameService.delPlanGame(plan.getPlanCode(),delGameCodes);
			}
		}

	}

	private List<String> getGameCodes(String availableGameType) {
		String[] gameTypes = availableGameType.split(",");
		List<String> gameCodes = new ArrayList<>();
		for (String gameType : gameTypes) {
			if ("BALL".equalsIgnoreCase(gameType)) {
				gameCodes.add("JSSSC");
				gameCodes.add("CQSSC");
				gameCodes.add("COUNTRY_SSC");
				gameCodes.add("SELF_SSC_5");
			} else if ("NUMBER".equalsIgnoreCase(gameType)) {
				gameCodes.add("XYFT");
				gameCodes.add("JS10");
				gameCodes.add("PK10");
				gameCodes.add("COUNTRY_10");
				gameCodes.add("SELF_10_2");
			} else {
				gameCodes.add("XYNC");
				gameCodes.add("GDKLC");
				gameCodes.add("GXKLSF");
			}
		}
		return gameCodes;
	}


}
