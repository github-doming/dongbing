package com.ibm.follow.connector.app.agent.game.refresh;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_follow_period.service.IbmHaFollowPeriodService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口代理投注信息
 * @Author: null
 * @Date: 2019-11-29 17:49
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/agent/gameBetInfo", method = HttpConfig.Method.GET)
public class GameBetInfoAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
        String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();
        String checkTimeStr = dataMap.getOrDefault("checkTime", "").toString();

        if (StringTool.isEmpty(gameCode, handicapAgentId)) {
            bean.putEnum(IbmCodeEnum.IBM_404_GAME);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return bean;
        }
		String gameId =GameUtil.id(gameCode);
        if (StringTool.isEmpty(gameId)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return super.returnJson(bean);
        }
        //检查时间为空时，获取最近20期投注数据
        long number = 1;
        if (StringTool.isEmpty(checkTimeStr)) {
            number = 20;
        }
        try {
            String handicapId =new IbmHandicapAgentService().findHandicapId(handicapAgentId,appUserId);
            if (StringTool.isEmpty(handicapId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            IbmHaFollowPeriodService haFollowPeriodService = new IbmHaFollowPeriodService();
            List<Map<String, Object>> betInfos = haFollowPeriodService.findBetInfo(handicapAgentId, gameId, number);
            for (Map<String, Object> betInfo : betInfos) {
                betInfo.remove("ROW_NUM");
                betInfo.put("BET_FUNDS_", NumberTool.doubleT(betInfo.remove("BET_FUNDS_T_")));
                betInfo.put("FOLLOW_FUND_", NumberTool.doubleT(betInfo.remove("FOLLOW_FUND_T_")));
                betInfo.put("EXEC_STATE_", betListFormat(betInfo.get("EXEC_STATE_").toString()));
            }
            Map<String, Object> data = new HashMap<>(2);
            IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
            Map<String,Object> haGameSetInfo=haGameSetService.findGameSet(handicapAgentId,gameId);
            if(ContainerTool.isEmpty(haGameSetInfo)){
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return super.returnJson(bean);
            }
            haGameSetInfo.put("BET_FOLLOW_MULTIPLE_", NumberTool.doubleT(haGameSetInfo.remove("BET_FOLLOW_MULTIPLE_T_")));
            haGameSetInfo.put("BET_LEAST_AMOUNT_", NumberTool.doubleT(haGameSetInfo.remove("BET_LEAST_AMOUNT_T_")));
            haGameSetInfo.put("BET_MOST_AMOUNT_", NumberTool.doubleT(haGameSetInfo.remove("BET_MOST_AMOUNT_T_")));
            haGameSetInfo.remove("ROW_NUM");
            data.put("betInfos",betInfos);
            data.put("haGameSetInfo",haGameSetInfo);
            bean.success(betInfos);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("查询盘口会员投注信息-出错"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 输出格式化
     *
     * @param betState 投注状态
     * @return 完成投注数
     */
    private static String betListFormat(String betState) {
        if (IbmStateEnum.SUCCESS.name().equals(betState)) {
            betState = "跟投成功";
        } else if (IbmStateEnum.FAIL.name().equals(betState)) {
            betState = "跟投失败";
        } else {
            betState = "等待投注";
        }
        return betState;
    }
}
