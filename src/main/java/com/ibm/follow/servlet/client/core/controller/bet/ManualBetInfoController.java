package com.ibm.follow.servlet.client.core.controller.bet;

import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.core.job.bet.member.BetJob;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import com.ibm.follow.servlet.client.ibmc_hm_bet.entity.IbmcHmBet;
import com.ibm.follow.servlet.client.ibmc_hm_bet.service.IbmcHmBetService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_error.service.IbmcHmBetErrorService;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.service.IbmcHmGameSetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 手动投注
 * @Author: Dongming
 * @Date: 2019-09-12 16:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ManualBetInfoController implements ClientExecutor {
	private Logger log = LogManager.getLogger(this.getClass());

	private String existHmId;
	private GameUtil.Code gameCode;
	private Object period;
	private String betContent;
	private double funds;

	public ManualBetInfoController(String existHmId, GameUtil.Code gameCode, Object period, String betContent,
			double funds) {
		this.existHmId = existHmId;
		this.gameCode = gameCode;
		this.period = period;
		this.betContent = betContent;
		this.funds = funds;
	}

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String code = new IbmcExistHmService().findHandicapCode(existHmId);
		if (StringTool.isEmpty(code)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(code);
		//获取封盘时间
		long sealTime = CustomerCache.sealTime(existHmId, gameCode) - System.currentTimeMillis();
		if (sealTime <= 0) {
			//重新获取封盘事件
			sealTime = CustomerCache.resetSealTime(existHmId, gameCode, handicapCode, period) - System.currentTimeMillis();
			if (sealTime <= 0) {
				//发送错误投注信息
				sendErrorReceipt(msgObj.getString("BET_INFO_CODE_"), "已封盘");
				bean.putEnum(CodeEnum.IBS_403_CLOSING_TIME);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
		}
		//个人余额
		double usedQuota = CustomerCache.usedQuota(existHmId, period, handicapCode, gameCode);
		if (usedQuota - funds <= 0) {
			usedQuota = CustomerCache.usedQuota(existHmId, handicapCode, gameCode);
			if (usedQuota - funds <= 0) {
				sendErrorReceipt(msgObj.getString("BET_INFO_CODE_"), "用户余额不足");
				bean.putEnum(CodeEnum.IBS_403_USED_QUOTA);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
		}
		//个人配置信息
		Map<String, Object> setGameInfo = new IbmcHmGameSetService().findInfo(existHmId, gameCode.name());
		if (!IbmTypeEnum.TRUE.equal(setGameInfo.get("BET_STATE_"))) {
            sendErrorReceipt(msgObj.getString("BET_INFO_CODE_"), "尚未开启投注");
			bean.putEnum(CodeEnum.IBS_403_BET_STATE);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		//内存除去可用余额
		CustomerCache.usedQuota(existHmId, usedQuota - funds);

		//手动投注-直接存储
		manualBet(msgObj.getString("BET_INFO_CODE_"),handicapCode);

		return bean.success();
	}

    /**
     * 手动投注
     * @param betInfoCode   投注信息code
     * @param handicapCode  盘口code
     * @throws Exception
     */
    private void manualBet(String betInfoCode,HandicapUtil.Code handicapCode) throws Exception {
        IbmcHmBet hmBet = new IbmcHmBet();
        hmBet.setExistHmId(existHmId);
        hmBet.setHaFollowBetId(IbmTypeEnum.MANUAL.getCode());
        hmBet.setGameCode(gameCode.name());
        hmBet.setPeriod(period);
        hmBet.setBetInfo(betContent);
        hmBet.setBetFundT(NumberTool.longValueT(funds));
        hmBet.setBetInfoCode(betInfoCode);
        hmBet.setCreateTime(new Date());
        hmBet.setCreateTimeLong(System.currentTimeMillis());
        hmBet.setUpdateTime(new Date());
        hmBet.setUpdateTimeLong(System.currentTimeMillis());
        hmBet.setState(IbmStateEnum.OPEN.name());
        String hmBetId = new IbmcHmBetService().save(hmBet);

        CurrentTransaction.transactionCommit();
        ThreadPoolExecutorServiceListUtil.findInstance().findLocal().findExecutorService().execute(() -> {
            try {
				BetJob.getJob(handicapCode).manualBet(existHmId, gameCode, period, hmBetId).execute(null);
            } catch (Exception e) {
                log.error("投注失败", e);
            }
        });
    }

    /**
	 * 发送错误投注信息
	 *
	 * @param betInfoCode 投注信息code
	 * @param msg         错误消息
	 */
	private void sendErrorReceipt(String betInfoCode, String msg) throws Exception {
        log.info("盘口会员【{}】游戏【{}】期数【{}】错误信息:{}",existHmId,gameCode,period,msg);
		new IbmcHmBetErrorService().save(existHmId, null, gameCode, period, betContent, msg, IbmStateEnum.CLOSE.name());
		//错误投注项
		JSONObject content = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("EXIST_HM_ID_", existHmId);
		data.put("GAME_CODE_", gameCode);
		data.put("PERIOD_", period);
		data.put("BET_INFO_CODE_", betInfoCode);
		content.put("token", data.toString());
		content.put("command", IbmMethodEnum.MANUAL_BET.name());
		content.put("requestType", IbmStateEnum.ERROR.name());
		content.put("msg", msg);
		RabbitMqTool.sendInfoReceipt(content.toString(), "bet.result");
	}

}
