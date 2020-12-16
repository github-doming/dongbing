package com.ibm.follow.connector.admin.manage.user.vruser.game.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.entity.VrPiMemberPlanItem;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.service.VrPiMemberPlanItemService;
import com.ibm.follow.servlet.cloud.vr_plan_hm.service.VrPlanHmService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 虚拟用户盘口游戏方案详情
 * @Author: admin1
 * @Date: 2020/7/16 16:04
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/vruser/plan/itemSet")
public class VrUserPlanItemSetAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String memberId = StringTool.getString(dataMap.get("memberId"), "");
		String planItemTableId = StringTool.getString(dataMap.get("planItemTableId"), "");
		String fundsList = StringTool.getString(dataMap.get("fundsList"), "");
		String followPeriod = StringTool.getString(dataMap.get("followPeriod"), "");
		String monitorPeriod = StringTool.getString(dataMap.get("monitorPeriod"), "");
		String betMode = StringTool.getString(dataMap.get("betMode"), "");
		String fundSwapMode = StringTool.getString(dataMap.get("fundSwapMode"), "");
		String periodRollMode = StringTool.getString(dataMap.get("periodRollMode"), "");
		String planGroupData = StringTool.getString(dataMap.get("planGroupData"), "");
		String planGroupKey = StringTool.getString(dataMap.get("planGroupActiveKey"), "");
		String expandInfo = StringTool.getString(dataMap.get("expandInfo"), "");


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
			planItem.setFundsList(fundsList);
			planItem.setFollowPeriod(followPeriod);
			planItem.setMonitorPeriod(monitorPeriod);
			planItem.setBetMode(betMode);
			planItem.setFundSwapMode(fundSwapMode);
			planItem.setPeriodRollMode(periodRollMode);
			planItem.setPlanGroupData(planGroupData);
			planItem.setPlanGroupActiveKey(planGroupKey);
			planItem.setExpandInfo(expandInfo);
			planItem.setUpdateTime(new Date());
			planItem.setUpdateTimeLong(System.currentTimeMillis());
			itemService.update(planItem);

			Map<String, Object> hmPlanInfo = new VrPlanHmService().findInfo(memberId, planItem.getPlanId(),planItem.getGameCode());
			//发送事件
			VrClientMemberService clientMemberService = new VrClientMemberService();
			Map<String, Object> clientInfo = clientMemberService.findInfo(memberId);
			if(ContainerTool.isEmpty(clientInfo)){
				bean.success();
				return bean;
			}
			String existId = clientInfo.get("EXIST_MEMBER_VR_ID_").toString();

			BasePlanUtil.Code plan = BasePlanUtil.Code.valueOf(hmPlanInfo.get("PLAN_CODE_").toString());

			if(StringTool.isEmpty(planGroupKey)){
				bean.success();
				return bean;
			}
			JSONObject activePlanGroup = plan.getPlan().advance(planGroupKey.split(","), JSONObject.parseObject(planGroupData));



			//写入客户设置事件
			JSONObject content = new JSONObject();
			content.put("EXIST_ID_", existId);
			content.put("PLAN_GROUP_ACTIVE_DATA_", JSON.toJSONString(activePlanGroup, SerializerFeature.DisableCircularReferenceDetect));
			content.put("PLAN_STATE_", hmPlanInfo.get("PLAN_STATE_").toString());
			content.put("PLAN_CODE_", hmPlanInfo.get("PLAN_CODE_").toString());
			content.put("FUNDS_LIST_", fundsList);
			content.put("FOLLOW_PERIOD_", followPeriod);
			content.put("MONITOR_PERIOD_", monitorPeriod);
			content.put("BET_MODE_", betMode);
			content.put("FUND_SWAP_MODE_", fundSwapMode);
			content.put("PERIOD_ROLL_MODE_", periodRollMode);
			content.put("EXPAND_INFO_", expandInfo);
			content.put("SET_ITEM_", IbmMethodEnum.SET_PLAN_VR.name());
			content.put("METHOD_", IbmMethodEnum.GAME_VR.name());
			content.put("GAME_CODE_",planItem.getGameCode());
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
			log.error("用户列表错误", e);
			bean.error(e.getMessage());
			return bean;
		}
		return bean;
	}
}
