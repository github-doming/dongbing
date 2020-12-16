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
 * @Description: 修改游戏跟投设置
 * @Author: null
 * @Date: 2019-11-28 17:56
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/agent/gameFollowSet", method = HttpConfig.Method.POST)
public class GameFollowSetAction extends CommCoreAction {

    private String handicapAgentId;
    private String gameCode;
    private String followMultiple;
    private String leastAmount;
    private String mostAmount;
    private String filterNumber;
    private String filterTwoSide;
    private String numberOpposing;
    private String twoSideOpposing;

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        if (valiParameters()) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
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
                return super.returnJson(bean);
            }
            haGameSet.setBetFollowMultipleT(NumberTool.intValueT(followMultiple));
            haGameSet.setBetLeastAmountT(NumberTool.intValueT(leastAmount));
            haGameSet.setBetMostAmountT(NumberTool.intValueT(mostAmount));
            haGameSet.setBetFilterNumber(filterNumber);
            haGameSet.setBetFilterTwoSide(filterTwoSide);
            haGameSet.setNumberOpposing(numberOpposing);
            haGameSet.setTwoSideOpposing(twoSideOpposing);
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
            bean.error(e.getMessage());
        }
        return bean;
    }

    private void saveHaLog(IbmHaGameSet haGameSet) throws Exception {
        IbmLogHa logHa = new IbmLogHa();
        logHa.setHandicapAgentId(handicapAgentId);
        logHa.setAppUserId(appUserId);
        logHa.setHandleType("UPDATE");
        logHa.setHandleContent("IBM_HA_GAME_SET_ID_:".concat(haGameSet.getIbmHaGameSetId())
                .concat(",BET_FOLLOW_MULTIPLE_:").concat(haGameSet.getBetFollowMultipleT().toString())
                .concat(",BET_LEAST_AMOUNT_:").concat(haGameSet.getBetLeastAmountT().toString()).concat(",BET_MOST_AMOUNT_:")
                .concat(haGameSet.getBetMostAmountT().toString()).concat(",BET_FILTER_NUMBER_:").concat(haGameSet.getBetFilterNumber())
                .concat(",BET_FILTER_TWO_SIDE_:").concat(haGameSet.getBetFilterTwoSide()).concat(",NUMBER_OPPOSING_:")
                .concat(haGameSet.getNumberOpposing()).concat(",TWO_SIDE_OPPOSING_:").concat(haGameSet.getTwoSideOpposing()));
        logHa.setCreateTime(new Date());
        logHa.setCreateTimeLong(System.currentTimeMillis());
        logHa.setUpdateTimeLong(System.currentTimeMillis());
        logHa.setState(IbmStateEnum.OPEN.name());
        logHa.setDesc(this.getClass().getName());
        new IbmLogHaService().save(logHa);
    }

    private boolean valiParameters() {
        handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();
        gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
        //跟投倍数
        followMultiple = dataMap.getOrDefault("BET_FOLLOW_MULTIPLE_", "").toString();
        //最低金额
        leastAmount = dataMap.getOrDefault("BET_LEAST_AMOUNT_", "").toString();
        //最高金额
        mostAmount = dataMap.getOrDefault("BET_MOST_AMOUNT_", "").toString();
        //过滤数字
        filterNumber = dataMap.getOrDefault("BET_FILTER_NUMBER_", "").toString();
        //过滤双面
        filterTwoSide = dataMap.getOrDefault("BET_FILTER_TWO_SIDE_", "").toString();
        //数字反投
        numberOpposing = dataMap.getOrDefault("NUMBER_OPPOSING_", "").toString();
        //双面反投
        twoSideOpposing = dataMap.getOrDefault("TWO_SIDE_OPPOSING_", "").toString();

        return StringTool.isEmpty(handicapAgentId, gameCode, followMultiple, leastAmount,
                mostAmount, filterNumber, filterTwoSide, numberOpposing, twoSideOpposing);
    }
}
