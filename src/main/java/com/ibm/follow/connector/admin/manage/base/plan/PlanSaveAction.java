package com.ibm.follow.connector.admin.manage.base.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.ModeEnum;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.entity.VrPiMemberPlanItem;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.service.VrPiMemberPlanItemService;
import com.ibm.follow.servlet.cloud.vr_plan.entity.VrPlan;
import com.ibm.follow.servlet.cloud.vr_plan.service.VrPlanService;
import com.ibm.follow.servlet.cloud.vr_plan_game.entity.VrPlanGame;
import com.ibm.follow.servlet.cloud.vr_plan_game.service.VrPlanGameService;
import com.ibm.follow.servlet.cloud.vr_plan_hm.entity.VrPlanHm;
import com.ibm.follow.servlet.cloud.vr_plan_hm.service.VrPlanHmService;
import com.ibm.follow.servlet.cloud.vr_plan_item.entity.VrPlanItem;
import com.ibm.follow.servlet.cloud.vr_plan_item.service.VrPlanItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.*;

/**
 * @Description: 新增方案
 * @Author: admin1
 * @Date: 2020/6/22 11:09
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/plan/save", method = HttpConfig.Method.POST)
public class PlanSaveAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//方案名称
		String planName = StringTool.getString(dataMap, "PLAN_NAME_", "");
		//方案编号
		String planCode = StringTool.getString(dataMap, "PLAN_CODE_", "");
		//适用游戏类型
		String availableGameType = StringTool.getString(dataMap, "AVAILABLE_GAME_TYPE_", "");
		//序号
		int sn = NumberTool.getInteger(dataMap, "SN_", -1);
		//备注
		String desc = StringTool.getString(dataMap, "DESC_", "");

		if (StringTool.isEmpty(planName, planCode,   availableGameType) ||  sn == -1) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		Date nowTime = new Date();
		try {
			//验证方案编码是否存在
			VrPlanService planService = new VrPlanService();
			String planId = planService.findId(planCode);
			if(StringTool.notEmpty(planId)){
				bean.putEnum(IbmCodeEnum.IBM_403_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return super.returnJson(bean);
			}

			VrPlan plan = new VrPlan();
			plan.setPlanName(planName);
			plan.setPlanCode(planCode);
			plan.setAvailableGameType(availableGameType);
			plan.setSn(sn);
			plan.setDesc(desc);
			plan.setCreateTime(nowTime);
			plan.setCreateTimeLong(System.currentTimeMillis());
			plan.setUpdateTimeLong(System.currentTimeMillis());
			plan.setState(IbmStateEnum.OPEN.name());
			planId = planService.save(plan);

			List<String> gameCodes = getGameCodes(availableGameType);
			VrPlanGameService planGameService = new VrPlanGameService();
			VrPlanGame planGame = new VrPlanGame();
			planGame.setPlanId(planId);
			planGame.setPlanName(planName);
			planGame.setPlanCode(planCode);
			planGame.setCreateTime(nowTime);
			planGame.setCreateTimeLong(System.currentTimeMillis());
			planGame.setUpdateTimeLong(System.currentTimeMillis());
			planGame.setState(IbmStateEnum.OPEN.name());

			for (String gameCode:gameCodes){
				planGame.setGameCode(gameCode);
				planGameService.save(planGame);
				planGame.setVrPlanGameId(null);
			}
			Map<String,Object> data = new HashMap<>(3);

			bean.success(data);
		} catch (Exception e) {
			log.error(" 新增方案出错", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	private List<String> getGameCodes(String availableGameType){
		String[] gameTypes = availableGameType.split(",");
		List<String> gameCodes = new ArrayList<>();
		for (String gameType:gameTypes){
			if("BALL".equalsIgnoreCase(gameType)){
				gameCodes.add("JSSSC");
				gameCodes.add("CQSSC");
				gameCodes.add("COUNTRY_SSC");
				gameCodes.add("SELF_SSC_5");
			}else if("NUMBER".equalsIgnoreCase(gameType)){
				gameCodes.add("XYFT");
				gameCodes.add("JS10");
				gameCodes.add("PK10");
				gameCodes.add("COUNTRY_10");
				gameCodes.add("SELF_10_2");
			}else {
				gameCodes.add("XYNC");
				gameCodes.add("GDKLC");
				gameCodes.add("GXKLSF");
			}
		}
		return gameCodes;
	}

}
