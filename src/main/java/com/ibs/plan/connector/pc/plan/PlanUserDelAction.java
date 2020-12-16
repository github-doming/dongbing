package com.ibs.plan.connector.pc.plan;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_plan_hm.service.IbspPlanHmService;
import com.ibs.plan.module.cloud.ibsp_plan_item.service.IbspPlanItemService;
import com.ibs.plan.module.cloud.ibsp_plan_user.entity.IbspPlanUser;
import com.ibs.plan.module.cloud.ibsp_plan_user.service.IbspPlanUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 删除用户方案
 * @Author: null
 * @Date: 2020-06-06 15:25
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/plan/del", method = HttpConfig.Method.POST)
public class PlanUserDelAction extends CommCoreAction {
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

		String planCode = dataMap.getOrDefault("PLAN_CODE_", "").toString();

		if (StringTool.isEmpty(gameCode,planCode)) {
			return bean.put401Data();
		}
		try {
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}
			IbspPlanUserService planUserService=new IbspPlanUserService();
			IbspPlanUser planUser=planUserService.findEntity(appUserId,planCode,gameId);
			if(planUser==null){
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//删除方案详情信息
			new IbspPlanItemService().delPlanItem(planUser);

			Map<String, String> existInfos =new IbspClientHmService().findUserMemberInfos(appUserId);
			if (ContainerTool.notEmpty(existInfos)){
				//删除会员方案信息
				IbspPlanHmService planHmService=new IbspPlanHmService();
				planHmService.delPlanHm(planUser);

				//写入客户设置事件
				JSONObject content = new JSONObject();
				content.put("GAME_CODE_",gameCode);
				content.put("PLAN_CODE_",planCode);
				content.put("METHOD_", IbsMethodEnum.DEL_GAME_PLAN.name());
				for(Map.Entry<String,String> entry:existInfos.entrySet()){
					content.put("EXIST_HM_ID_", entry.getKey());
					String eventId= EventThreadDefine.saveMemberConfigSetEvent(content,new Date());
					content.put("EVENT_ID_",eventId);

					RabbitMqTool.sendMember(content.toString(), entry.getValue(),"set");
				}
			}
			//删除用户游戏方案
			planUserService.del(planUser.getIbspPlanUserId());

			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "删除用户方案失败", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
