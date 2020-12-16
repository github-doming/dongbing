package com.ibm.follow.servlet.cloud.core.controller.mq;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.core.CloudCustomerTool;
import com.ibm.follow.servlet.cloud.core.CloudTool;
import com.ibm.follow.servlet.cloud.core.controller.tool.ProfitPeriodTool;
import com.ibm.follow.servlet.cloud.core.job.CloudMergeBetJob;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_ha_member_bet_item.service.IbmHaMemberBetItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.entity.IbmHmBetItem;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_game_set.service.IbmHmGameSetService;
import com.ibm.follow.servlet.cloud.ibm_hm_set.service.IbmHmSetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 跟随投注
 * @Author: Dongming
 * @Date: 2019-09-21 11:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FollowController {
    private static final Logger log = LogManager.getLogger(FollowController.class);
	private GameUtil.Code gameCode;
	private Object period;
	private String betContent;

	public FollowController(GameUtil.Code gameCode, Object period, String betContent) {
		this.gameCode = gameCode;
		this.period = period;
		this.betContent = betContent;
	}
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String existHmId = msgObj.getString("EXIST_HM_ID_");

		Map<String, Object> hmInfo =  new IbmClientHmService().findHmInfo(existHmId);
		if (StringTool.isEmpty(hmInfo)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		String existHaId = msgObj.getString("EXIST_HA_ID_");
		String handicapAgentId = new IbmClientHaService().findHaId(existHaId);
		if (StringTool.isEmpty(handicapAgentId)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(hmInfo.get("HANDICAP_CODE_").toString());
		String handicapMemberId = hmInfo.get("HANDICAP_MEMBER_ID_").toString();
		String handicapId = HandicapUtil.id(handicapCode, IbmTypeEnum.MEMBER);

		Map<String, Object> setGameInfo = new IbmHmGameSetService().findInfo(handicapMemberId, GameUtil.id(gameCode));
		if (!IbmTypeEnum.TRUE.equal(setGameInfo.get("BET_STATE_"))) {
			bean.putEnum(CodeEnum.IBS_403_BET_STATE);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		Map<String, Object> setInfo = new IbmHmSetService().findInfo(handicapMemberId);

		double betRate = NumberTool.doubleT(setInfo.getOrDefault("BET_RATE_T_", 1000));
		//封盘前投注时间
		long betSecond = NumberTool.longValueT(setGameInfo.get("BET_SECOND_"));

		//距离封盘大于投注设定时间，开启了投注合并
		String betMode = setGameInfo.get("BET_MODE_").toString();


		long sealTime =
				gameCode.getGameFactory().period(handicapCode).getDrawTime(period) - CloudTool.getSealTime(handicapCode, gameCode)
						- System.currentTimeMillis();
		if (sealTime <= 0) {
			saveHmBet(msgObj, handicapId, handicapMemberId, betMode, betRate, IbmStateEnum.FAIL.name(), "已封盘");
			bean.putEnum(CodeEnum.IBS_403_CLOSING_TIME);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
        if (sealTime <= betSecond || betSecond == 0) {
            if (IbmStateEnum.OPEN.equal(setInfo.get("BET_MERGER_"))) {
                CloudCustomerTool.putBetItem(handicapMemberId, gameCode, betContent.split("#"),msgObj.get("HA_FOLLOW_BET_ID_").toString());
                mergeBet(handicapMemberId,handicapId, betRate,betMode);
                return  bean.success();
            }
        }
		if (IbmStateEnum.OPEN.equal(setInfo.get("BET_MERGER_"))) {
			Date startTime = new Date(sealTime - betSecond + System.currentTimeMillis());
			CloudCustomerTool.putBetItem(handicapMemberId, gameCode, betContent.split("#"),msgObj.get("HA_FOLLOW_BET_ID_").toString());
			// 开启合并投注定时器
			return QuartzTool
					.saveCloudMergeBetJob(handicapId, handicapMemberId, handicapCode, gameCode, period, betRate,
							betMode, startTime);
		}
		//存入会员投注数据
		saveHmBet(msgObj, handicapId, handicapMemberId, betMode, betRate, IbmStateEnum.PROCESS.name(), null);
		return bean;
	}
    private static ThreadPoolExecutor getExecutor(){
        return ThreadExecuteUtil.findInstance().getJobExecutor();
    }

    private void mergeBet(String handicapMemberId,String handicapId, double betRate,String betMode) {
        getExecutor().execute(() -> {
            try {
                new CloudMergeBetJob().mergeBet(handicapMemberId, gameCode, period, betMode,handicapId, betRate).execute(null);
            } catch (Exception e) {
                log.error("投注失败", e);
            }
        });
    }

    private void saveHmBet(JSONObject msgObj, String handicapId, String handicapMemberId, String betMode,
			double betRate, String execState, String msg) throws Exception {
		int fundsTh = 0;
		int betLen = 0;
		StringBuilder content = new StringBuilder();
		//获取投注code
		Map<Integer, Map<Integer, Integer>> betCode = GameTool.getBetCode(gameCode, betContent.split("#"), betRate);
		if (ContainerTool.isEmpty(betCode)) {
			return;
		}
		//获取投注详情信息
		for (Map.Entry<Integer, Map<Integer, Integer>> codeEntry : betCode.entrySet()) {
			for (Map.Entry<Integer, Integer> typeEntry : codeEntry.getValue().entrySet()) {
				betLen++;
				fundsTh += typeEntry.getValue();
				content.append(GameTool.itemStr(gameCode, codeEntry.getKey(), typeEntry.getKey(), typeEntry.getValue()))
						.append("#");
			}
		}
		if(StringTool.isContains(period.toString(),"-")){
			period=period.toString().substring(4);
		}
		IbmHmBetItem hmBetItem = new IbmHmBetItem();
		hmBetItem.setClientHaFollowBetId(msgObj.get("HA_FOLLOW_BET_ID_"));
		hmBetItem.setHandicapId(handicapId);
		hmBetItem.setHandicapMemberId(handicapMemberId);
		hmBetItem.setGameId(GameUtil.id(gameCode));
		hmBetItem.setPeriod(period);
		hmBetItem.setBetMode(betMode);
		hmBetItem.setBetType(IbmTypeEnum.FOLLOW.ordinal());
		hmBetItem.setFollowMemberAccount(msgObj.get("MEMBER_ACCOUNT_"));
		hmBetItem.setBetContentLen(betLen);
		hmBetItem.setBetContent(content.toString());
		hmBetItem.setFundT(fundsTh);
		hmBetItem.setBetInfoCode(EncryptTool.encode(EncryptTool.Type.MD5, hmBetItem.getBetContent()));
		hmBetItem.setCreateTime(new Date());
		hmBetItem.setCreateTimeLong(System.currentTimeMillis());
		hmBetItem.setUpdateTime(new Date());
		hmBetItem.setUpdateTimeLong(System.currentTimeMillis());
		hmBetItem.setState(IbmStateEnum.OPEN.name());
		hmBetItem.setDesc(msg);

		//投注为虚拟投注时，汇总到当期投注信息,并且修改代理跟投信息
		if (IbmTypeEnum.VIRTUAL.name().equals(betMode)) {
			ProfitPeriodTool
					.saveHmProfitPeriodVr(handicapMemberId, handicapId, GameUtil.id(gameCode), period, fundsTh, betLen);

            //修改代理跟投信息
            new IbmHaMemberBetItemService().updateProcessInfo(msgObj.get("HA_FOLLOW_BET_ID_").toString());
            execState=IbmStateEnum.SUCCESS.name();
		}
        hmBetItem.setExecState(execState);
        new IbmHmBetItemService().save(hmBetItem);
	}
}
