package com.ibm.follow.connector.pc.agent.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_ha_member_bet_item.service.IbmHaMemberBetItemService;
import com.ibm.follow.servlet.cloud.ibm_handicap_agent.service.IbmHandicapAgentService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口代理会员投注信息
 * @Author: lxl
 * @Date: 2019-09-27 09:16
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/agent/gameBetInfo")
public class GameBetInfoAction extends CommCoreAction {

    @Override
    public Object run() throws Exception {
        long nextTime = System.currentTimeMillis();
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }

        String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
        String handicapAgentId = dataMap.getOrDefault("HANDICAP_AGENT_ID_", "").toString();
        String checkTimeStr = dataMap.getOrDefault("checkTime", "").toString();
        //校验

        if (StringTool.isEmpty(gameCode,handicapAgentId)) {
            bean.putEnum(IbmCodeEnum.IBM_404_DATA);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return super.returnJson(bean);
        }
		String gameId =GameUtil.id(gameCode);
        if (StringTool.isEmpty(gameId)) {
            bean.putEnum(IbmCodeEnum.IBM_404_GAME);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return bean;
        }
        String handicapId =new IbmHandicapAgentService().findHandicapId(handicapAgentId,appUserId);
        if (StringTool.isEmpty(handicapId)) {
            bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_AGENT);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return bean;
        }
        HandicapUtil.Code handicap=HandicapUtil.code(handicapId);
        GameUtil.Code game = GameUtil.Code.valueOf(gameCode);

        //检查时间
        long checkTime;
        int number=0;
        if (StringTool.isEmpty(checkTimeStr)) {
            checkTime = game.getGameFactory().period(handicap).getLotteryDrawTime();
            number=20;
        } else {
            checkTime = NumberTool.getLong(checkTimeStr)-200;
        }
        try {
            Map<String, Object> data = new HashMap<>(1);
            IbmHaMemberBetItemService haGameSetService = new IbmHaMemberBetItemService();
            //查询新数据
            List<Map<String, Object>> betNewInfos = haGameSetService
                    .listNewBetInfo(gameId, handicapAgentId, checkTime,number);

            for (Map<String, Object> betInfo : betNewInfos) {
                betInfo.put("BET_SHOW_",GameTool.getBetShow(game,betInfo.get("BET_CONTENT_").toString().split("#")));
                betInfo.put("BET_CONTENT_",GameTool.getBetContent(game,betInfo.get("BET_CONTENT_").toString().split("#")));
                betInfo.put("FOLLOW_SHOW_",GameTool.getBetShow(game,betInfo.get("FOLLOW_CONTENT_").toString().split("#")));
                betInfo.put("FOLLOW_CONTENT_",GameTool.getBetContent(game,betInfo.get("FOLLOW_CONTENT_").toString().split("#")));
                betInfo.put("EXEC_STATE_", betListFormat(String.valueOf(betInfo.get("EXEC_STATE_"))));
                betInfo.put("BET_FUND_", NumberTool.doubleT(betInfo.remove("BET_FUND_T_")));
                betInfo.put("FOLLOW_FUND_", NumberTool.doubleT(betInfo.remove("FOLLOW_FUND_T_")));
                betInfo.remove("ROW_NUM");
            }
            data.put("betNewInfos",betNewInfos);
            data.put("nextTime", nextTime);
            if(number!=0){
                data.put("betInfo", new HashMap<>(1));
                bean.success(data);
                return bean;
            }
            //历史更新数据
            List<Map<String, Object>> betInfos = haGameSetService
                    .listDrawInfo(gameId, handicapAgentId, checkTime);

            for (Map<String, Object> betInfo : betInfos) {
                betInfo.put("EXEC_STATE_", betListFormat(String.valueOf(betInfo.get("EXEC_STATE_"))));
                betInfo.remove("ROW_NUM");
            }
            data.put("betInfo", betInfos);
            bean.success(data);
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("查询盘口代理会员投注信息-出错"), e);
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
    private String betListFormat(String betState) {
        switch (IbmStateEnum.valueOf(betState)) {
            case PROCESS:
                return "等待投注";
            case FAIL:
                return "投注失败";
            case SUCCESS:
                return "投注成功";
            default:
                log.error("投注类型错误="+betState);
                return "投注成功";
        }
    }
}
