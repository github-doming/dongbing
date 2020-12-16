package com.ibm.follow.connector.pc.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.controller.mq.ManualBetController;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap_member.service.IbmHandicapMemberService;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.entity.IbmHmBetItem;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_info.service.IbmHmInfoService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 手动投注
 * @Author: Dongming
 * @Date: 2019-09-10 11:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/member/manualBet", method = HttpConfig.Method.POST)
public class ManualBetAction extends CommCoreAction {
    private JsonResultBeanPlus bean = new JsonResultBeanPlus();
    private String handicapMemberId;
    private String betContentStr;
    private String betFundsStr;
    private String gameCode;

    @Override
    public Object run() throws Exception {
        super.findAppUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        if (!valiParameters()) {
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        if(super.denyTime()){
            bean.putEnum(IbmCodeEnum.IBM_503_SERVER_RENEW);
            bean.putSysEnum(IbmCodeEnum.CODE_503);
            return bean;
        }
        try {
            //用户是否登录
            if (!IbmStateEnum.LOGIN.equal(new IbmHmInfoService().findLoginState(handicapMemberId))) {
                bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
			String gameId =GameUtil.id(gameCode);
            if (StringTool.isEmpty(gameId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_GAME);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            //虚拟投注暂不开放手动投注
            Map<String,Object> hmGameSet=new IbmHmGameSetService().findInfo(handicapMemberId,gameId);
            if(ContainerTool.isEmpty(hmGameSet)||IbmTypeEnum.VIRTUAL.name().equals(hmGameSet.get("BET_MODE_"))){
                bean.putEnum(IbmCodeEnum.IBM_404_VR_NOT_MANUAL_BET);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            String handicapId = new IbmHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
            if (StringTool.isEmpty(handicapId)) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            Integer closeTime = new IbmHandicapGameService().findCloseTime(handicapId, gameId);
            if (closeTime == null) {
                bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
                bean.putSysEnum(IbmCodeEnum.CODE_404);
                return bean;
            }
            HandicapUtil.Code handicap = HandicapUtil.code(handicapId);
            GameUtil.Code game = GameUtil.Code.valueOf(gameCode);

            long  drawTime = game.getGameFactory().period(handicap).getDrawTime() - System.currentTimeMillis();
            if (drawTime <= closeTime  || drawTime > game.getGameFactory().getInterval()) {
                bean.putEnum(IbmCodeEnum.IBM_403_CLOSING_TIME);
                bean.putSysEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
			String period=game.getGameFactory().period(handicap).findPeriod().toString();
            //存储手动投注数据
            String hmBetItemId = manualBet(game, gameId, handicapId,period);
            if (StringTool.isEmpty(hmBetItemId)) {
                bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
                bean.putEnum(IbmCodeEnum.CODE_403);
                return bean;
            }
            CurrentTransaction.transactionCommit();
            //发送投注信息
            String result = new ManualBetController(hmBetItemId, handicapMemberId,period).execute();
            if (!Boolean.parseBoolean(result)) {
                bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
                bean.putEnum(IbmCodeEnum.CODE_403);
                return bean;
            }

            bean.success();
        } catch (Exception e) {
            log.error(IbmMainConfig.LOG_SIGN.concat("手动投注失败")+e.getMessage());
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 手动投注<br>
     * 第一名	1	<br>
     * 第三名	1	<br>
     * 第一名	大	<br>
     * 第三名	大	<br>
     *
     * @param game       游戏Code
     * @param gameId     游戏Id
     * @param handicapId
     * @return 投注信息主键
     */
    private String manualBet(GameUtil.Code game, String gameId, String handicapId,String period) throws Exception {
        int betFundsTh = NumberTool.intValueT(betFundsStr);
        int fundsTh = 0;
        int contentLen = 0;
        Map<Integer, Set<Integer>> betCode = GameTool.getBetCodeByJson(game, betContentStr);
        if (ContainerTool.isEmpty(betCode)) {
            return null;
        }
        StringBuilder content = new StringBuilder();
        for (Map.Entry<Integer, Set<Integer>> entry : betCode.entrySet()) {
            for (Integer type : entry.getValue()) {
                contentLen++;
                fundsTh += betFundsTh;
                content.append(GameTool.itemStr(game, entry.getKey(), type, betFundsTh)).append("#");
            }
        }
		if(StringTool.isContains(period,"-")){
			period=period.substring(4);
		}
        IbmHmBetItem hmBetItem = new IbmHmBetItem();
        hmBetItem.setClientHaFollowBetId(IbmTypeEnum.MANUAL.getCode());
        hmBetItem.setHandicapId(handicapId);
        hmBetItem.setHandicapMemberId(handicapMemberId);
        hmBetItem.setGameId(gameId);
        hmBetItem.setPeriod(period);
        hmBetItem.setBetMode(IbmTypeEnum.REAL.name());
        hmBetItem.setBetType(IbmTypeEnum.MANUAL.ordinal());
        hmBetItem.setFollowMemberAccount(IbmTypeEnum.MANUAL.getCode());
        hmBetItem.setBetContentLen(contentLen);
        hmBetItem.setBetContent(content.toString());
        hmBetItem.setBetInfoCode(EncryptTool.encode(EncryptTool.Type.MD5, hmBetItem.getBetContent()));
        hmBetItem.setFundT(fundsTh);
        hmBetItem.setExecState(IbmStateEnum.SEND.name());
        hmBetItem.setCreateTime(new Date());
        hmBetItem.setCreateTimeLong(System.currentTimeMillis());
        hmBetItem.setUpdateTimeLong(System.currentTimeMillis());
        hmBetItem.setState(IbmStateEnum.OPEN.name());
        return new IbmHmBetItemService().save(hmBetItem);
    }

    private boolean valiParameters() {
        handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
        gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
        betContentStr = dataMap.getOrDefault("BET_CONTENT_", "").toString();
        betFundsStr = dataMap.getOrDefault("BET_FUNDS_", "").toString();
        if (StringTool.isEmpty(handicapMemberId, gameCode, betContentStr, betFundsStr)) {
            bean.putEnum(IbmCodeEnum.IBM_401_DATA);
            return false;
        }
        return true;
    }
}
