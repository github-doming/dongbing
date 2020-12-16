package com.ibs.plan.module.client.core.executor;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.google.common.collect.ImmutableList;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.common.utils.ThreadExecuteUtil;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.job.bet.BetJobDefine;
import com.ibs.plan.module.client.ibsc_bet.service.IbscBetService;
import com.ibs.plan.module.client.ibsc_hm_set.service.IbscHmSetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 手动投注
 *
 * @Author: null
 * @Date: 2020-06-08 10:47
 * @Version: v1.0
 */
public class ManualBetExecutor implements ClientMqExecutor {
	protected JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private GameUtil.Code gameCode;
	private HandicapUtil.Code handicapCode;
	private Object period;
	private String betContent;
	private String gameDrawType;
	private long funds;

	private String existHmId;
	public ManualBetExecutor(String existHmId) {
		this.existHmId = existHmId;
	}
	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		IbscBetService betService = new IbscBetService();
		Date nowTime = new Date();
		if (valiParameter(msgObj)) {
			bean.putSysEnum(CodeEnum.CODE_401);
			bean.setRequestType(IbsStateEnum.FAIL.name());
			//数据验证错误一般为程序错误再怎么补正也没办法处理，需要开发人员介入 - 故接收端接收即可 accept
			return bean;
		}

		//存储数据到数据库
		String betId = betService
				.save(existHmId, gameCode, gameDrawType, period, IbsTypeEnum.MANUAL, betContent,funds,nowTime);
		Map<String,Object> data = new HashMap<>(2);
		data.put("BET_ITEM_ID_",msgObj.get("BET_ITEM_ID_"));
		data.put("CLIENT_BET_ID_",betId);
		bean.process(data);
		bean.setRequestType(IbsStateEnum.PROCESS.name());
		RabbitMqTool.sendBet(bean.toString(), "result");

		List<String> betIds = ImmutableList.<String>builder().add(betId).build();
		// 是否已封盘
		long sealTime = CustomerCache.sealTime(existHmId, gameCode) - System.currentTimeMillis();
		if (sealTime <= 0) {
			//重新获取封盘事件
			sealTime = CustomerCache.resetSealTime(existHmId, gameCode, handicapCode, period) - System.currentTimeMillis();
			if (sealTime <= 0) {
				betService.update(betIds, "已封盘", IbsStateEnum.SUCCESS, nowTime);
				BetJobDefine.sendErrorReceipt(betIds, IbsTypeEnum.MANUAL, "已封盘");
				bean.putEnum(CodeEnum.IBS_403_CLOSING_TIME);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
		}

		double betRate = new IbscHmSetService().findBetRate(existHmId);
		// 金额 向上取整
		double fund = Math.ceil(NumberTool.doubleT(funds) * betRate);
		double usedQuota = CustomerCache.usedQuota(existHmId, handicapCode, period);
		if (usedQuota - fund <= 0) {
			usedQuota = CustomerCache.usedQuota(existHmId, handicapCode);
			if (usedQuota - fund <= 0) {
				//余额不足，发送错误消息
				betService.update(betIds, "余额不足", IbsStateEnum.SUCCESS, nowTime);
				BetJobDefine.sendErrorReceipt( betIds,IbsTypeEnum.MANUAL, "余额不足");
				bean.putEnum(CodeEnum.IBS_403_USED_QUOTA);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
		}
		//内存除去可用余额
		CustomerCache.usedQuota(existHmId, usedQuota - fund);
		//重启事务
		CurrentTransaction.transactionCommit();
		//距离封盘小于投注设定时间
		ThreadExecuteUtil.findInstance().getJobExecutor().execute(() -> {
			Logger log = LogManager.getLogger(ManualBetExecutor.class);
			try {
				BetJobDefine.getJob(handicapCode).bet(existHmId, gameCode, period, betIds).execute(null);
			} catch (Exception e) {
				log.error("投注失败", e);
			}

		});

		return bean.success();
	}
	private boolean valiParameter(JSONObject msgObj) {
		String gameCodeObj = msgObj.getOrDefault("GAME_CODE_", "").toString();
		String handicapCodeObj = msgObj.getOrDefault("HANDICAP_CODE_", "").toString();
		period = msgObj.getOrDefault("PERIOD_", "");
		betContent = msgObj.getOrDefault("BET_CONTENT_", "").toString();
		gameDrawType = msgObj.getOrDefault("GAME_DRAW_TYPE_", "").toString();
		funds =msgObj.getLong("FUNDS_T_");

		if (StringTool.isEmpty(gameCodeObj, period, betContent, gameDrawType)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			return true;
		}
		gameCode = GameUtil.Code.valueOf(gameCodeObj);
		handicapCode = HandicapUtil.Code.valueOf(handicapCodeObj);
		return false;
	}
}
