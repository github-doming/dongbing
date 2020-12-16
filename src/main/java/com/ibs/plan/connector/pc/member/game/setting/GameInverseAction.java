package com.ibs.plan.connector.pc.member.game.setting;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.core.EventThreadDefine;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.entity.IbspHmGameSet;
import com.ibs.plan.module.cloud.ibsp_hm_game_set.service.IbspHmGameSetService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import com.ibs.plan.module.cloud.ibsp_log_hm.entity.IbspLogHm;
import com.ibs.plan.module.cloud.ibsp_log_hm.service.IbspLogHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 修改游戏反投状态
 * @Author: null
 * @Date: 2020-06-03 15:44
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/gameInverse", method = HttpConfig.Method.POST)
public class GameInverseAction extends CommCoreAction {
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
		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();

		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		String isInverse = dataMap.getOrDefault("IS_INVERSE_", "").toString();

		if (StringTool.isEmpty(handicapMemberId,gameCode)||!IbsTypeEnum.booleanType(isInverse)) {
			return bean.put401Data();
		}
		try{
			String gameId =GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}
			// 获取该盘口会员的信息
			String loginState= new IbspHmInfoService().findLoginState(handicapMemberId);
			//是否登录
			if (StringTool.isEmpty(loginState) || !IbsStateEnum.LOGIN.name().equals(loginState)) {
				bean.putEnum(CodeEnum.IBS_404_LOGIN);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			IbspHmGameSetService gameSetService=new IbspHmGameSetService();
			IbspHmGameSet gameSet=gameSetService.findEntity(handicapMemberId,gameId);
			if (gameSet == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			gameSet.setIsInverse(isInverse);
			gameSet.setUpdateTimeLong(System.currentTimeMillis());
			gameSetService.update(gameSet);

			Map<String,Object> existInfo =new IbspClientHmService().findExistHmInfo(handicapMemberId);
			if (ContainerTool.isEmpty(existInfo)){
				bean.putSysEnum(CodeEnum.IBS_403_DATA_ERROR);
				bean.putEnum(CodeEnum.CODE_403);
				return bean;
			}
			String clientCode = existInfo.remove("CLIENT_CODE_").toString();
			//写入客户设置事件
			JSONObject content = new JSONObject();
			content.put("EXIST_HM_ID_", existInfo.get("EXIST_HM_ID_"));
			content.put("IS_INVERSE_",isInverse);
			content.put("GAME_CODE_",gameCode);
			content.put("METHOD_", IbsMethodEnum.SET_INVERSE.name());
			String eventId= EventThreadDefine.saveMemberConfigSetEvent(content,new Date());
			content.put("EVENT_ID_",eventId);

			RabbitMqTool.sendMember(content.toString(),clientCode,"set");

			//添加盘口会员日志信息
			saveHmLog(gameSet);
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "修改游戏投注状态失败", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
	private void saveHmLog(IbspHmGameSet gameSet) throws Exception {
		IbspLogHm logHm = new IbspLogHm();
		logHm.setHandicapMemberId(gameSet.getHandicapMemberId());
		logHm.setAppUserId(appUserId);
		logHm.setHandleType("UPDATE");
		logHm.setHandleContent("IS_INVERSE_".concat(gameSet.getIsInverse()));
		logHm.setCreateTime(new Date());
		logHm.setCreateTimeLong(System.currentTimeMillis());
		logHm.setUpdateTimeLong(System.currentTimeMillis());
		logHm.setState(IbsStateEnum.OPEN.name());
		logHm.setDesc(this.getClass().getName());
		new IbspLogHmService().save(logHm);
	}
}
