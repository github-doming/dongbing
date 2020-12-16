package com.ibm.follow.connector.pc.agent.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_log_ha.entity.IbmLogHa;
import com.ibm.follow.servlet.cloud.ibm_log_ha.service.IbmLogHaService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 游戏跟投状态
 * @Author: null
 * @Date: 2019-09-12 15:00
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/agent/gameBetState", method = HttpConfig.Method.POST) public class GameBetStateAction
		extends CommCoreAction {

	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
		String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();

		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		String betState = dataMap.getOrDefault("BET_STATE_", "").toString();
		if (StringTool.isEmpty(handicapAgentId, gameCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		if (!IbmTypeEnum.booleanType(betState)) {
			bean.putEnum(IbmCodeEnum.IBM_401_STATE);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			//用户是否登录
			if (!IbmStateEnum.LOGIN.equal(new IbmHaInfoService().findLoginState(handicapAgentId))){
				bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}

			IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
            String gameSetId=haGameSetService.findId(handicapAgentId,gameId);
            if(StringTool.isEmpty(gameSetId)){
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return super.returnJson(bean);
            }
            haGameSetService.updateBetState(gameSetId,betState);
			//添加盘口会员日志信息
			saveHaLog(handicapAgentId,betState);
			//写入客户设置事件
			JSONObject content = new JSONObject();
			content.put("BET_STATE_", betState);
			content.put("GAME_CODE_", gameCode);
			content.put("SET_ITEM_", IbmMethodEnum.SET_BET_STATE.name());
			content.put("METHOD_", IbmMethodEnum.AGENT_SET.name());

			String desc= this.getClass().getName().concat("，跟投游戏跟投状态设置");
			boolean flag=EventThreadDefine.sendClientConfig(content,handicapAgentId,IbmTypeEnum.AGENT,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
			}
			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("修改游戏跟投状态失败"),e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	private void saveHaLog(String handicapAgentId,String betState) throws Exception {
		IbmLogHa logHa = new IbmLogHa();
		logHa.setHandicapAgentId(handicapAgentId);
		logHa.setAppUserId(appUserId);
		logHa.setHandleType("UPDATE");
		logHa.setHandleContent("BET_STATE_:".concat(betState));
		logHa.setCreateTime(new Date());
		logHa.setCreateTimeLong(System.currentTimeMillis());
		logHa.setUpdateTimeLong(System.currentTimeMillis());
		logHa.setState(IbmStateEnum.OPEN.name());
		logHa.setDesc(this.getClass().getName());
		new IbmLogHaService().save(logHa);
	}
}
