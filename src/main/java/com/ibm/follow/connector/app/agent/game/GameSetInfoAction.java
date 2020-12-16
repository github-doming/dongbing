package com.ibm.follow.connector.app.agent.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 获取游戏设置信息
 * @Author: null
 * @Date: 2019-12-04 15:08
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/agent/gameInfo", method = HttpConfig.Method.GET)
public class GameSetInfoAction extends CommCoreAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();
        String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
        if (StringTool.isEmpty(handicapAgentId, gameCode)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
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

            bean.success(haGameSetInfo);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("获取游戏设置信息失败"),e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
