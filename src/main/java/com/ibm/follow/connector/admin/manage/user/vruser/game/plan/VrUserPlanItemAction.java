package com.ibm.follow.connector.admin.manage.user.vruser.game.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.entity.VrPiMemberPlanItem;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.service.VrPiMemberPlanItemService;
import com.ibm.follow.servlet.cloud.vr_plan.entity.VrPlan;
import com.ibm.follow.servlet.cloud.vr_plan.service.VrPlanService;
import com.ibm.follow.servlet.cloud.vr_plan_hm.service.VrPlanHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 虚拟用户盘口游戏方案详情
 * @Author: admin1
 * @Date: 2020/7/16 16:04
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/vruser/plan/item")
public class VrUserPlanItemAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String memberId = StringTool.getString(dataMap.get("MEMBER_ID_"), "");
		String planItemTableId = StringTool.getString(dataMap.get("PLAN_ITEM_TABLE_ID_"), "");
		try {
			VrMember member = new VrMemberService().find(memberId);
			if (member == null) {
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}

			VrPiMemberPlanItemService itemService = new VrPiMemberPlanItemService();
			VrPiMemberPlanItem planItem = itemService.find(planItemTableId);
			if (planItem == null) {
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}
			VrPlan plan = new VrPlanService().find(planItem.getPlanId());
			Map<String,Object> item = new HashMap<>(17);
			item.put("gameType",type(planItem.getGameCode()));
			item.put("vrPiMemberPlanItemId",planItem.getVrPiMemberPlanItemId());
			item.put("planId",planItem.getPlanId());
			item.put("vrMemberId",planItem.getVrMemberId());
			item.put("gameCode",planItem.getGameCode());
			item.put("planCode",plan.getPlanCode());
			item.put("profitLimitMax",planItem.getProfitLimitMax());
			item.put("lossLimitMin",planItem.getLossLimitMin());
			item.put("fundsList",planItem.getFundsList());
			item.put("followPeriod",planItem.getFollowPeriod());
			item.put("monitorPeriod",planItem.getMonitorPeriod());
			item.put("betMode",planItem.getBetMode());
			item.put("fundSwapMode",planItem.getFundSwapMode());
			item.put("periodRollMode",planItem.getPeriodRollMode());
			item.put("planGroupData",planItem.getPlanGroupData());
			item.put("planGroupActiveKey",planItem.getPlanGroupActiveKey());
			item.put("expandInfo",planItem.getExpandInfo());
			item.put("state",planItem.getState());

			bean.success(item);

		} catch (Exception e) {
			log.error("用户列表错误", e);
			bean.error(e.getMessage());
			return bean;
		}
		return bean;
	}

	public static String type(String gameCode) {
		switch (gameCode) {
			case "JSSSC":
			case "CQSSC":
			case "COUNTRY_SSC":
			case "SELF_SSC_5":
				return "BALL";
			case "XYNC":
			case "GDKLC":
			case "GXKLSF":
				return "HAPPY";
			case "XYFT":
			case "JS10":
			case "PK10":
			case "COUNTRY_10":
			case "SELF_10_2":
			default:
				return "NUMBER";
		}
	}
}
