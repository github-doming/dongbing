package com.ibm.follow.connector.admin.manage.user.vruser.game.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.util.BasePlanUtil;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.entity.IbmEventConfigSet;
import com.ibm.follow.servlet.cloud.ibm_event_config_set.service.IbmEventConfigSetService;
import com.ibm.follow.servlet.cloud.vr_client_member.service.VrClientMemberService;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.service.VrPiMemberPlanItemService;
import com.ibm.follow.servlet.cloud.vr_plan_hm.entity.VrPlanHm;
import com.ibm.follow.servlet.cloud.vr_plan_hm.service.VrPlanHmService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 开启/关闭 用户游戏方案
 * @Author: admin1
 * @Date: 2020/7/17 9:45
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/vruser/plan/state")
public class VrUserPlanStateAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口游戏id
		String planHmId = StringTool.getString(dataMap, "VR_PLAN_HM_ID_", "");
		String planState = StringTool.getString(dataMap, "state", "");

		try {
			VrPlanHmService planHmService = new VrPlanHmService();
			VrPlanHm planHm = planHmService.find(planHmId);
			if(planHm == null){
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}
			planHm.setPlanState(planState);
			planHm.setUpdateTime(new Date());
			planHm.setUpdateTimeLong(System.currentTimeMillis());
			planHmService.update(planHm);
			String memberId = planHm.getVrMemberId();
			String gameCode = planHm.getGameCode();


			VrClientMemberService clientMemberService = new VrClientMemberService();
			Map<String, Object> clientInfo = clientMemberService.findInfo(memberId);
			if(ContainerTool.isEmpty(clientInfo)){
				bean.success();
				return bean;
			}
			String existId = clientInfo.get("EXIST_MEMBER_VR_ID_").toString();

			BasePlanUtil.Code plan = BasePlanUtil.Code.valueOf(planHm.getPlanCode());
			Map<String,Object> planItem = new VrPiMemberPlanItemService().findPlanItem(memberId,gameCode,planHm.getPlanId());
			String keys = planItem.remove("PLAN_GROUP_ACTIVE_KEY_").toString();
			if(StringTool.isEmpty(keys)){
				bean.success();
				return bean;
			}
			String[] activeKeys = keys.split(",");
			JSONObject activePlanGroup = plan.getPlan().advance(activeKeys, JSONObject.parseObject(planItem.remove("PLAN_GROUP_DATA_").toString()));

			//发送事件
			//写入客户设置事件 vrc_plan_item vrc_member_plan
			JSONObject content = new JSONObject();
			content.put("EXIST_ID_", existId);
			content.put("PLAN_GROUP_ACTIVE_DATA_",activePlanGroup.toString());
			content.put("PLAN_STATE_",planState);
			content.put("GAME_CODE_",planHm.getGameCode());
			content.put("PLAN_CODE_", planItem.get("PLAN_CODE_"));
			content.put("FUNDS_LIST_", planItem.get("FUNDS_LIST_"));
			content.put("FOLLOW_PERIOD_", planItem.get("FOLLOW_PERIOD_"));
			content.put("MONITOR_PERIOD_", planItem.get("MONITOR_PERIOD_"));
			content.put("BET_MODE_", planItem.get("BET_MODE_"));
			content.put("FUND_SWAP_MODE_", planItem.get("FUND_SWAP_MODE_"));
			content.put("PERIOD_ROLL_MODE_", planItem.get("PERIOD_ROLL_MODE_"));
			content.put("EXPAND_INFO_", planItem.get("EXPAND_INFO_"));
			content.put("PLAN_ITEM_", planItem);
			content.put("SET_ITEM_", IbmMethodEnum.SET_PLAN_VR.name());
			content.put("METHOD_", IbmMethodEnum.GAME_VR.name());

			IbmEventConfigSet configSet = new IbmEventConfigSet();
			configSet.setEventContent(content);
			configSet.setEventState(IbmStateEnum.SEND.name());
			configSet.setExecNumber(0);
			configSet.setCreateTime(new Date());
			configSet.setCreateTimeLong(System.currentTimeMillis());
			configSet.setUpdateTimeLong(System.currentTimeMillis());
			configSet.setState(IbmStateEnum.OPEN.name());
			String eventId = new IbmEventConfigSetService().save(configSet);

			CurrentTransaction.transactionCommit();

			content.put("EVENT_ID_", eventId);
			RabbitMqTool.sendClientConfig(content.toString(), clientInfo.get("CLIENT_CODE_").toString(), "set");

			bean.success();
		} catch (Exception e) {
			log.error("游戏方案设置错误", e);
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
