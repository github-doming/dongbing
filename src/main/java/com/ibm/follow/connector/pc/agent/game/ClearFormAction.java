package com.ibm.follow.connector.pc.agent.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_member_bet_item.service.IbmHaMemberBetItemService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 清空表格
 * @Author: null
 * @Date: 2019-12-13 18:13
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/agent/clearForm", method = HttpConfig.Method.GET)
public class ClearFormAction extends CommCoreAction {
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
        String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
        String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();

        if (StringTool.isEmpty(gameCode, handicapAgentId)) {
            bean.putEnum(IbmCodeEnum.IBM_404_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return super.returnJson(bean);
        }
        try {
			String gameId =GameUtil.id(gameCode);
            if (StringTool.isEmpty(gameId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_GAME);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            String handicapId = new IbmHandicapAgentService().findHandicapId(handicapAgentId, appUserId);
            if (StringTool.isEmpty(handicapId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }

            HandicapUtil.Code handicap = HandicapUtil.code(handicapId);
            GameUtil.Code game = GameUtil.Code.valueOf(gameCode);


            long time = game.getGameFactory().period(handicap).getLotteryDrawTime();

            IbmHaMemberBetItemService memberBetItemService = new IbmHaMemberBetItemService();
            memberBetItemService.clearForm(gameId, handicapAgentId, time);

            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("清空表格数据信息错误"), e);
            bean.error(e.getMessage());
        }
        return bean;
    }
}
