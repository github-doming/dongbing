package com.ibm.follow.connector.app.agent.game;

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
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.entity.IbmHaGameSet;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_log_ha.entity.IbmLogHa;
import com.ibm.follow.servlet.cloud.ibm_log_ha.service.IbmLogHaService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 设置跟投倍数
 * @Author: null
 * @Date: 2019-11-29 10:20
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/agent/followMultipleSet", method = HttpConfig.Method.POST)
public class GameFollowMultipleAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
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
        //跟投倍数
        String followMultiple = dataMap.getOrDefault("BET_FOLLOW_MULTIPLE_", "").toString();

        if (StringTool.isEmpty(handicapAgentId, gameCode, followMultiple)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        try {
            //用户是否登录
            if (!IbmStateEnum.LOGIN.equal(new IbmHaInfoService().findLoginState(handicapAgentId))) {
                bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            String gameId = GameUtil.id(gameCode);
            IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
            IbmHaGameSet haGameSet = haGameSetService.find(handicapAgentId, gameId);
            if (haGameSet == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            haGameSet.setBetFollowMultipleT(NumberTool.intValueT(followMultiple));
            haGameSet.setUpdateTimeLong(System.currentTimeMillis());
            haGameSetService.update(haGameSet);
			//添加盘口代理日志信息
			saveHaLog(haGameSet);

            JSONObject content = new JSONObject();
            content.put("SET_ITEM_", IbmMethodEnum.SET_GAME.name());
            content.put("METHOD_", IbmMethodEnum.AGENT_SET.name());
            content.put("GAME_CODE_", gameCode);
            content.put("BET_STATE_", haGameSet.getBetState());
            content.put("BET_FOLLOW_MULTIPLE_T_", haGameSet.getBetFollowMultipleT());
            content.put("BET_LEAST_AMOUNT_T_", haGameSet.getBetLeastAmountT());
            content.put("BET_MOST_AMOUNT_T_", haGameSet.getBetMostAmountT());
            content.put("BET_FILTER_NUMBER_", haGameSet.getBetFilterNumber());
            content.put("BET_FILTER_TWO_SIDE_", haGameSet.getBetFilterTwoSide());
            content.put("NUMBER_OPPOSING_", haGameSet.getNumberOpposing());
            content.put("TWO_SIDE_OPPOSING_", haGameSet.getTwoSideOpposing());
			String desc= this.getClass().getName().concat("，修改跟投设置");
			boolean flag=EventThreadDefine.sendClientConfig(content,handicapAgentId, IbmTypeEnum.AGENT,desc);
			if (!flag){
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				bean.putEnum(IbmCodeEnum.CODE_403);
				return bean;
			}

            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("修改跟投信息失败"),e);

        }

        return bean;
    }

    private void saveHaLog(IbmHaGameSet haGameSet) throws Exception {
        IbmLogHa logHa = new IbmLogHa();
        logHa.setHandicapAgentId(haGameSet.getHandicapAgentId());
        logHa.setAppUserId(appUserId);
        logHa.setHandleType("UPDATE");
        logHa.setHandleContent("IBM_HA_GAME_SET_ID_:".concat(haGameSet.getIbmHaGameSetId())
                .concat(",BET_FOLLOW_MULTIPLE_:").concat(haGameSet.getBetFollowMultipleT().toString()));
        logHa.setCreateTime(new Date());
        logHa.setCreateTimeLong(System.currentTimeMillis());
        logHa.setUpdateTimeLong(System.currentTimeMillis());
        logHa.setState(IbmStateEnum.OPEN.name());
        logHa.setDesc(this.getClass().getName());
        new IbmLogHaService().save(logHa);
    }
}
