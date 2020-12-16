package com.ibm.follow.servlet.client.core.controller.bet;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.CustomerTool;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.core.job.bet.ClientMergeBetJob;
import com.ibm.follow.servlet.client.core.job.bet.member.BetJobDefine;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import com.ibm.follow.servlet.client.ibmc_hm_bet.entity.IbmcHmBet;
import com.ibm.follow.servlet.client.ibmc_hm_bet.service.IbmcHmBetService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_error.service.IbmcHmBetErrorService;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.service.IbmcHmGameSetService;
import com.ibm.follow.servlet.client.ibmc_hm_set.service.IbmcHmSetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 跟随虚拟投注
 * @Author: null
 * @Date: 2020-07-15 15:32
 * @Version: v1.0
 */
public class VrFollowBetInfoController implements ClientExecutor {
	private static final Logger log = LogManager.getLogger(VrFollowBetInfoController.class);
	private String existHmId;
	private GameUtil.Code gameCode;
	private Object period;
	private String betContent;
	private double funds;

	public VrFollowBetInfoController(String existHmId, GameUtil.Code gameCode, Object period, String betContent, double funds) {
		this.existHmId = existHmId;
		this.gameCode = gameCode;
		this.period = period;
		this.betContent = betContent;
		this.funds = funds;
	}

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapCode = new IbmcExistHmService().findHandicapCode(existHmId);
		if (StringTool.isEmpty(handicapCode)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		HandicapUtil.Code code = HandicapUtil.Code.valueOf(handicapCode);

		Map<String, Object> setInfo = new IbmcHmSetService().findInfo(existHmId);
		double betRate = NumberTool.doubleT(setInfo.getOrDefault("BET_RATE_T_", 1000));
		// 金额 向上取整
		funds = Math.ceil(funds * betRate);
		//获取距离封盘时间
		long sealTime = CustomerCache.sealTime(existHmId, gameCode) - System.currentTimeMillis();
		if (sealTime <= 0) {
			//重新获取封盘事件
			sealTime = CustomerCache.resetSealTime(existHmId, gameCode, code, period) - System.currentTimeMillis();
			if (sealTime <= 0) {
				sendErrorReceipt(msgObj.getString("FOLLOW_BET_ID_"), "已封盘");
				bean.putEnum(CodeEnum.IBS_403_CLOSING_TIME);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
		}
		//个人配置信息
		Map<String, Object> setGameInfo = new IbmcHmGameSetService().findInfo(existHmId, gameCode.name());
		if (!IbmTypeEnum.TRUE.equal(setGameInfo.get("BET_STATE_"))) {
			bean.putEnum(CodeEnum.IBS_403_BET_STATE);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		//个人余额
		double usedQuota = CustomerCache.usedQuota(existHmId, period, code, gameCode);
		if (usedQuota - funds <= 0) {
			usedQuota = CustomerCache.usedQuota(existHmId, code, gameCode);
			if (usedQuota - funds <= 0) {
				//余额不足，发送错误消息
				sendErrorReceipt(msgObj.getString("FOLLOW_BET_ID_"), "余额不足");
				bean.putEnum(CodeEnum.IBS_403_USED_QUOTA);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
		}
		//内存除去可用余额
		CustomerCache.usedQuota(existHmId, usedQuota - funds);
		//距离封盘小于投注设定时间
		long betSecond = NumberTool.longValueT(setGameInfo.get("BET_SECOND_"));
		if (sealTime <= betSecond || betSecond == 0) {
			if (IbmStateEnum.OPEN.equal(setInfo.get("BET_MERGER_"))) {
				CustomerTool.putBetItem(existHmId, gameCode, betContent.split("#"),msgObj.getString("FOLLOW_BET_ID_"));
				mergeBet(code, betRate);
				return  bean.success();
			} else {
				//存入数据
				saveHmBet(msgObj, betRate);
				//投注
				FollowBetInfoController.followBet(existHmId, gameCode, code, period);
				return bean.success();
			}
		}
		Date startTime = new Date(sealTime - betSecond + System.currentTimeMillis());
		//开启合并
		if (IbmStateEnum.OPEN.equal(setInfo.get("BET_MERGER_"))) {
			CustomerTool.putBetItem(existHmId, gameCode, betContent.split("#"),msgObj.getString("FOLLOW_BET_ID_"));
			// 开启合并投注定时器
			return QuartzTool
					.saveClientMergeBetJob(existHmId, HandicapUtil.Code.valueOf(handicapCode), gameCode, period,
							betRate, startTime);
		}
		//存入数据
		saveHmBet(msgObj, betRate);

		//开启定时投注
		return QuartzTool.saveBetJob(existHmId, HandicapUtil.Code.valueOf(handicapCode), gameCode, period, startTime);
	}
	private static ThreadPoolExecutor getExecutor(){
		return ThreadExecuteUtil.findInstance().getJobExecutor();
	}
	/**
	 * 存入投注数据
	 *
	 * @param msgObj  投注消息-获取跟随会员信息
	 * @param betRate 投注比例
	 */
	private void saveHmBet(JSONObject msgObj, double betRate) throws Exception {
		int fundsTh = 0;
		StringBuilder content = new StringBuilder();
		//获取投注code
		Map<Integer, Map<Integer, Integer>> betCode = GameTool.getBetCode(gameCode, betContent.split("#"), betRate);
		if (ContainerTool.isEmpty(betCode)) {
			sendErrorReceipt(msgObj.get("FOLLOW_BET_ID_").toString(), "投注项错误");
			return;
		}
		//获取投注详情信息
		for (Map.Entry<Integer, Map<Integer, Integer>> codeEntry : betCode.entrySet()) {
			for (Map.Entry<Integer, Integer> typeEntry : codeEntry.getValue().entrySet()) {
				fundsTh += typeEntry.getValue();
				content.append(GameTool.itemStr(gameCode, codeEntry.getKey(), typeEntry.getKey(), typeEntry.getValue()))
						.append("#");
			}
		}
		//存储投注信息
		IbmcHmBet hmBet = new IbmcHmBet();
		hmBet.setExistHmId(existHmId);
		hmBet.setHaFollowBetId(msgObj.get("FOLLOW_BET_ID_"));
		hmBet.setGameCode(gameCode.name());
		hmBet.setPeriod(period);
		hmBet.setBetInfo(content.toString());
		hmBet.setBetFundT(fundsTh);
		hmBet.setBetInfoCode(EncryptTool.encode(EncryptTool.Type.MD5, hmBet.getBetInfo()));
		hmBet.setCreateTime(new Date());
		hmBet.setCreateTimeLong(System.currentTimeMillis());
		hmBet.setUpdateTime(new Date());
		hmBet.setUpdateTimeLong(System.currentTimeMillis());
		hmBet.setState(IbmStateEnum.OPEN.name());
		new IbmcHmBetService().save(hmBet);
	}
	/**
	 * 开启一个线程马上合并投注
	 *
	 * @param handicapCode 盘口编码
	 * @param betRate      投注比例
	 */
	private void mergeBet(HandicapUtil.Code handicapCode, double betRate) {
		getExecutor().execute(() -> {
			try {
				new ClientMergeBetJob().mergeBet(existHmId, gameCode, period, handicapCode, betRate).execute(null);
			} catch (Exception e) {
				log.error("投注失败", e);
			}
		});
	}
	/**
	 * 发送错误投注信息
	 *
	 * @param followBetId 跟投id
	 */
	private void sendErrorReceipt(String followBetId, String msg) throws Exception {
		log.info("盘口会员【{}】游戏【{}】期数【{}】错误信息:{}",existHmId,gameCode,period,msg);
		new IbmcHmBetErrorService().save(existHmId, null, gameCode, period, betContent, msg, IbmStateEnum.CLOSE.name());

		BetJobDefine.sendErrorReceipt(existHmId,gameCode,period,followBetId,
				IbmMethodEnum.FOLLOW_BET.name(),null,msg);
	}
}
