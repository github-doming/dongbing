package com.ibm.follow.servlet.client.core.job.bet.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.agent.FCAgentUtil;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.DetailInfo;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 抓取FC盘口投注项
 * @Author: null
 * @Date: 2020-04-27 18:11
 * @Version: v1.0
 */
public class GrabBetFCJob extends GrabBetJob {


	private static final Map<GameUtil.Code, Map<String, Boolean>> GRAB_CHECK_MAP = new HashMap<>(GameUtil.codes().length);

	static {
		for (GameUtil.Code code : GameUtil.codes()) {
			GRAB_CHECK_MAP.put(code, new ConcurrentHashMap<>(10));
		}
	}

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		handicapCode = HandicapUtil.Code.FC;
		super.executeJob(context);
		if (StringTool.isEmpty(new IbmcExistHaService().findState(existHaId))) {
			log.error(message, handicapCode, existHaId, "抓取盘口投注项异常，清除盘口抓取");
			GRAB_CHECK_MAP.get(gameCode).remove(existHaId);
			QuartzTool.removeGrabBetJob(existHaId, handicapCode, gameCode);
			return;
		}

		if (GRAB_CHECK_MAP.get(gameCode).getOrDefault(existHaId, false)) {
			log.info(message, handicapCode, existHaId, gameCode.name(), period, "上一次尚未抓取完成");
			return;
		}
		GRAB_CHECK_MAP.get(gameCode).put(existHaId, true);
		try {
			if (gameCode.getGameFactory().period(handicapCode).getDrawTime() - System.currentTimeMillis() >
					gameCode.getGameFactory().getInterval()) {
				log.info(message, handicapCode, existHaId, gameCode.name(), period, "未到可以开始抓取的时间");
				return;
			}
			if (initInfo()) {
				return;
			}
			execute();
		} catch (Exception e) {
			log.error(message, handicapCode, existHaId, gameCode.name(), period, e);
			throw e;
		} finally {
			GRAB_CHECK_MAP.get(gameCode).remove(existHaId);
			log.info(message, handicapCode, existHaId, gameCode.name(), period, "抓取投注项完成");
		}
	}

	private void execute() throws Exception {
		JSONObject memberInfo = new JSONObject();
		//判断跟随会员类型
		if (IbmTypeEnum.APPOINT.equal(followInfo.get("FOLLOW_MEMBER_TYPE_"))) {
			if (StringTool.isEmpty(followInfo.get("MEMBER_LIST_INFO_"))) {
				log.debug(message, handicapCode, existHaId, gameCode.name(), period, "没有跟单的会员");
				return;
			}
			memberInfo = JSON.parseObject(followInfo.get("MEMBER_LIST_INFO_").toString());
		} else if (IbmTypeEnum.ALL.equal(followInfo.get("FOLLOW_MEMBER_TYPE_"))) {
			memberInfo = null;
		}
		//盘口时间字符串
		String date = PeriodUtil.getHandicapGameDateStr(handicapCode, gameCode, period);
		//期数数字型
		Object noPeriod = PeriodUtil.getPeriod(handicapCode, gameCode,period);
		// 获取跟投会员的未结算摘要信息
		FCAgentUtil agentUtil = FCAgentUtil.findInstance();
		JsonResultBeanPlus bean = agentUtil.getBetSummary(existHaId, gameCode, noPeriod, date);
		if (!bean.isSuccess()) {
			log.warn(message, handicapCode, existHaId, gameCode.name(), period, "获取未结算摘要信息失败:" + bean.toJsonString());
			return;
		}
		if (ContainerTool.isEmpty(bean.getData())) {
			//没信息，说明上一期的已经结算了，历史数据(投注额，投注数，注单号，期数)都没有用了，可以清除历史的信息
			GrabBetInfo.clearInfo(existHaId, gameCode);
			log.debug(message, handicapCode, existHaId, gameCode.name(), period, "获取投注摘要为空");
			return;
		}

		Map<String, SummaryInfo> member = (Map<String, SummaryInfo>) bean.getData();
		// 跟投数据
		Map<String, double[][]> betMap = new HashMap<>(member.size() * 3 / 4 + 1);

		if (memberInfo == null) {
			for (Map.Entry<String, SummaryInfo> entry : member.entrySet()) {
				if (handicapAccount.contains(entry.getKey())) {
					continue;
				}
				putBetMap(agentUtil, betMap, entry.getValue(), date);
			}
		} else {
			for (String memberAccoFCt : memberInfo.keySet()) {
				//该跟投会员没有投注数据
				if (!member.containsKey(memberAccoFCt) || handicapAccount.contains(memberAccoFCt)) {
					continue;
				}
				putBetMap(agentUtil, betMap, member.get(memberAccoFCt), date);
			}
		}
		//获取到的数据为空
		if (ContainerTool.isEmpty(betMap)) {
			log.debug(message, handicapCode, existHaId, gameCode.name(), period, "获取未结算投注明细为空");
			return;
		}
		// 处理和发送投注结合
		new GrabBet(existHaId, gameCode, period).processBetMap(setInfo, hmSendInfos, memberInfo, betMap);
	}


	private void putBetMap(FCAgentUtil agentUtil, Map<String, double[][]> betMap, SummaryInfo summaryInfo, String date) {
		//期数数字型
		Object noPeriod = PeriodUtil.getPeriod(handicapCode, gameCode,period);
		JsonResultBeanPlus bean;

		summaryInfo.setPeriod(noPeriod);

		SummaryInfo ramMemberInfo = GrabBetInfo.getMember(existHaId, gameCode, summaryInfo);
		//内存中不存在数据
		int ramBetCoFCt = 0;
		if (ramMemberInfo == null) {
			bean = agentUtil.getBetDetail(existHaId, gameCode, "", summaryInfo, date);
		} else {
			if (StringTool.isEmpty(ramMemberInfo.getOddNumber())) {
				log.error(message, handicapCode, existHaId, gameCode.name(), period, "没有抓取详情成功，内存数据为=" + ramMemberInfo.toString());
				return;
			}
			//投注数没变,投注额没变
			ramBetCoFCt = ramMemberInfo.getBetCount();
			int ramBetAmoFCt = ramMemberInfo.getBetAmount();
			if (ramBetCoFCt - summaryInfo.getBetCount() == 0 && ramBetAmoFCt - summaryInfo.getBetAmount() == 0) {
				return;
			}

			log.error("代理【" + existHaId + "】期数【" + period + "】重复抓取,旧内存信息【" + ramMemberInfo + "】,新内存信息【" + summaryInfo);
			bean = agentUtil.getBetDetail(existHaId, gameCode, ramMemberInfo.getOddNumber(), summaryInfo, date);
		}
		if (!bean.isSuccess()) {
			log.warn(message, handicapCode, existHaId, gameCode.name(), period, "获取投注详情失败:" + bean.toJsonString());
			return;
		}
		//投注详情为空
		if (ContainerTool.isEmpty(bean.getData())) {
			return;
		}
		DetailBox betDetail = (DetailBox) bean.getData();

		List<DetailInfo> details = betDetail.details();
		//放入最大注单号
		summaryInfo.setBetCount(details.size() + ramBetCoFCt);
		log.info("代理【" + existHaId + "】期数【" + period + "】抓取,旧内存信息【" + ramMemberInfo + "】,新内存信息【" + summaryInfo);
		GrabBetInfo.putMember(existHaId, gameCode, summaryInfo, betDetail.maxOddNumber());
		for (DetailInfo detail : details) {
			int[] item = GameTool.item(gameCode, detail.betItem());

			//存入投注资金
			if (betMap.containsKey(summaryInfo.getAccount())) {
				betMap.get(summaryInfo.getAccount())[item[0]][item[1]] += detail.funds();
			} else {
				double[][] fFCdsMatrix = GameTool.getFundsMatrix(gameCode);
				fFCdsMatrix[item[0]][item[1]] += detail.funds();
				betMap.put(summaryInfo.getAccount(), fFCdsMatrix);
			}
		}
	}


}
