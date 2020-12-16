package com.ibm.follow.connector.pc.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口会员投注信息
 * @Author: lxl
 * @Date: 2019-09-27 09:56
 * @Email: 2543908257@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/gameBetInfo", method = HttpConfig.Method.POST)
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
        String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        String checkTimeStr = dataMap.getOrDefault("checkTime", "").toString();
        //校验
        if (StringTool.isEmpty(gameCode, handicapMemberId)) {
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
        String handicapId = new IbmHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
        if (StringTool.isEmpty(handicapId)) {
            bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
            bean.putSysEnum(IbmCodeEnum.CODE_404);
            return bean;
        }
        GameUtil.Code game = GameUtil.Code.valueOf(gameCode);
        HandicapUtil.Code handicap = HandicapUtil.code(handicapId);

        //检查时间
        long checkTime;
        int number = 0;
        if (StringTool.isEmpty(checkTimeStr)) {
            checkTime = game.getGameFactory().period(handicap).getLotteryDrawTime();
            number = 20;
        } else {
            checkTime = NumberTool.getLong(checkTimeStr) - 200;
        }

        try {
            Map<String, Object> data = new HashMap<>(7);
            IbmHmBetItemService hmBetItemService = new IbmHmBetItemService();

            //获取当期投注总额和当期投注总数
            hmBetItemService.findSum(gameId, handicapMemberId, game.getGameFactory().period(handicap).findPeriod(), data);

            //查询新数据
            List<Map<String, Object>> betNewInfos = hmBetItemService
                    .listNewBetInfo(gameId, handicapMemberId, checkTime, number);
            String betState;
            for (Map<String, Object> betInfo : betNewInfos) {
                if ("manual".equals(betInfo.get("FOLLOW_MEMBER_ACCOUNT_"))) {
                    betInfo.put("FOLLOW_MEMBER_ACCOUNT_", IbmTypeEnum.MANUAL.getMsg());
                }
                //投注信息为空的时候
                if(StringTool.isEmpty(betInfo.get("BET_CONTENT_"))){
                    betInfo.put("BET_CONTENT_", "投注完成");
                }else{
					betInfo.put("BET_SHOW_", GameTool.getBetShow(game, betInfo.get("BET_CONTENT_").toString().split("#")));
                    betInfo.put("BET_CONTENT_",
                            GameTool.getBetContent(game, betInfo.get("BET_CONTENT_").toString().split("#")));
                }
                betState = betInfo.get("EXEC_STATE_").toString();
                if (NumberTool.getInteger(betInfo.remove("BET_TYPE_")) == IbmTypeEnum.MERGE.ordinal()) {
                    betInfo.put("FOLLOW_MEMBER_ACCOUNT_", "合并投注");
                }
                if (IbmStateEnum.FAIL.name().equals(betState)) {
                    betInfo.put("EXEC_STATE_", "投注失败");
                    betInfo.put("EXEC_CONTENT_", betInfo.remove("DESC_"));
                } else {
                    betInfo.put("EXEC_STATE_", betListFormat(betState, betInfo.get("BET_MODE_").toString()));
                }
                betInfo.put("BET_FUND_", NumberTool.doubleT(betInfo.remove("FUND_T_")));
                betInfo.put("PROFIT_", NumberTool.doubleT(betInfo.remove("PROFIT_T_")));

                betInfo.remove("ROW_NUM");
            }
            data.put("betNewInfos", betNewInfos);
            data.put("nextTime", nextTime);
            if (number != 0) {
                data.put("betInfo", new HashMap<>(1));
                bean.success(data);
                return bean;
            }
            //历史更新数据
            List<Map<String, Object>> betInfos = hmBetItemService.listDrawInfo(gameId, handicapMemberId, checkTime);
            for (Map<String, Object> betInfo : betInfos) {
                if (NumberTool.getInteger(betInfo.remove("BET_TYPE_")) == IbmTypeEnum.MERGE.ordinal()) {
                    betInfo.put("FOLLOW_MEMBER_ACCOUNT_", "合并投注");
                }
                betState = betInfo.get("EXEC_STATE_").toString();
                if (IbmStateEnum.FAIL.name().equals(betState)) {
                    betInfo.put("EXEC_STATE_", "投注失败");
                    betInfo.put("EXEC_CONTENT_", betInfo.remove("DESC_"));
                } else {
                    betInfo.put("EXEC_STATE_", betListFormat(betState, betInfo.get("BET_MODE_").toString()));
                }
                betInfo.put("PROFIT_", NumberTool.doubleT(betInfo.remove("PROFIT_T_")));
                betInfo.remove("ROW_NUM");
            }
            data.put("betInfo", betInfos);

            Map<String, Object> gameInfo = new IbmHmGameSetService().findShowInfo(handicapMemberId, gameId);
            data.put("gameInfo",gameInfo);
            bean.success(data);
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
     * @param betMode  投注模式
     * @return 完成投注数
     */
    private static String betListFormat(String betState, String betMode) {
        if (IbmTypeEnum.VIRTUAL.name().equals(betMode)) {
            return "模拟投注";
        }
        switch (IbmStateEnum.valueOf(betState)) {
            case FINISH:
                betState = "投注完成";
                break;
            case SUCCESS:
                betState = "投注成功";
                break;
            case PROCESS:
            case SEND:
                betState = "等待投注";
                break;
            default:
                log.error("投注类型错误=" + betState);
                betState = "投注成功";
                break;
        }
        return betState;
    }

}
