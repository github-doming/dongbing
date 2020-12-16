package com.ibm.follow.servlet.cloud.core.controller.mq;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_ha_follow_period.entity.IbmHaFollowPeriod;
import com.ibm.follow.servlet.cloud.ibm_ha_follow_period.service.IbmHaFollowPeriodService;
import com.ibm.follow.servlet.cloud.ibm_ha_member_bet_item.entity.IbmHaMemberBetItem;
import com.ibm.follow.servlet.cloud.ibm_ha_member_bet_item.service.IbmHaMemberBetItemService;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 代理跟随投注信息
 * @Author: null
 * @Date: 2019-10-07 14:02
 * @Version: v1.0
 */
public class FollowAgentController {

	private GameUtil.Code gameCode;
	private Object period;
	private String betContent;

	public FollowAgentController (GameUtil.Code gameCode, Object period, String betContent) {
		this.gameCode = gameCode;
		this.period = period;
		this.betContent = betContent;
	}

	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String existHaId = msgObj.getOrDefault("EXIST_HA_ID_", "").toString();

		IbmClientHaService clientHaService=new IbmClientHaService();
		String handicapAgentId =clientHaService.findHaId(existHaId);
		if (StringTool.isEmpty(handicapAgentId)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		//存入代理会员投注信息
        IbmHaMemberBetItem haMemberBetItem =saveHaBet(msgObj,handicapAgentId);
		//统计当期投注数据
        saveFollowPeriod(haMemberBetItem);

		bean.success();
		return bean;
	}

    private void saveFollowPeriod(IbmHaMemberBetItem haMemberBetItem) throws Exception {
        IbmHaFollowPeriodService followPeriodService=new IbmHaFollowPeriodService();
        IbmHaFollowPeriod haFollowPeriod=followPeriodService.find(haMemberBetItem.getHandicapAgentId(),haMemberBetItem.getGameId(),period);
        if(haFollowPeriod==null){
            haFollowPeriod=new IbmHaFollowPeriod();
            haFollowPeriod.setHandicapAgentId(haMemberBetItem.getHandicapAgentId());
            haFollowPeriod.setGameId(haMemberBetItem.getGameId());
            haFollowPeriod.setPeriod(haMemberBetItem.getPeriod());
            haFollowPeriod.setBetLen(haMemberBetItem.getBetContentLen());
            haFollowPeriod.setBetFundsT(haMemberBetItem.getBetFundT());
            haFollowPeriod.setFollowFundT(haMemberBetItem.getFollowFundT());
            haFollowPeriod.setExecState(IbmStateEnum.PROCESS.name());
            haFollowPeriod.setCreateTime(new Date());
            haFollowPeriod.setCreateTimeLong(System.currentTimeMillis());
            haFollowPeriod.setUpdateTime(new Date());
            haFollowPeriod.setUpdateTimeLong(System.currentTimeMillis());
            haFollowPeriod.setState(IbmStateEnum.OPEN.name());
            followPeriodService.save(haFollowPeriod);
        }else{
            haFollowPeriod.setBetLen(haFollowPeriod.getBetLen()+haMemberBetItem.getBetContentLen());
            haFollowPeriod.setBetFundsT(haFollowPeriod.getBetFundsT()+haMemberBetItem.getBetFundT());
            haFollowPeriod.setFollowFundT(haFollowPeriod.getFollowFundT()+haMemberBetItem.getFollowFundT());
            followPeriodService.update(haFollowPeriod);
        }
    }

    private IbmHaMemberBetItem saveHaBet(JSONObject msgObj, String handicapAgentId) throws Exception {
		IbmHaMemberBetItem haMemberBetItem = new IbmHaMemberBetItem();
		haMemberBetItem.setClientHaFollowBetId(msgObj.get("HA_FOLLOW_BET_ID_"));
		haMemberBetItem.setHandicapAgentId(handicapAgentId);
		haMemberBetItem.setGameId(GameUtil.id(gameCode));
		haMemberBetItem.setFollowMemberAccount(msgObj.getString("MEMBER_ACCOUNT_"));
		haMemberBetItem.setPeriod(period);
		haMemberBetItem.setBetContentLen(msgObj.getString("BET_INFO_").split("#").length);
		haMemberBetItem.setBetContent(msgObj.getString("BET_INFO_"));
		haMemberBetItem.setBetFundT(msgObj.get("BET_FUNDS_T_"));
		haMemberBetItem.setFollowContent(betContent);
		haMemberBetItem.setFollowFundT(msgObj.get("FUNDS_T_"));
		haMemberBetItem.setExecState(IbmStateEnum.PROCESS.name());
		haMemberBetItem.setCreateTime(new Date());
		haMemberBetItem.setCreateTimeLong(System.currentTimeMillis());
		haMemberBetItem.setUpdateTime(new Date());
		haMemberBetItem.setUpdateTimeLong(System.currentTimeMillis());
		haMemberBetItem.setState(IbmStateEnum.OPEN.name());
		new IbmHaMemberBetItemService().save(haMemberBetItem);
		return haMemberBetItem;
	}
}
