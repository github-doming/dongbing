package com.ibm.follow.servlet.client.core.job.bet.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.DetailInfo;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import com.ibm.follow.servlet.client.ibmc_agent_member_info.service.IbmcAgentMemberInfoService;
import com.ibm.follow.servlet.client.ibmc_ha_game_set.service.IbmcHaGameSetService;
import com.ibm.follow.servlet.client.ibmc_ha_set.service.IbmcHaSetService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 抓取盘口投注项
 * @deprecated
 * @Author: Dongming
 * @Date: 2019-12-16 10:51
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class GrabBetJob2 extends BaseCommJob {

	protected String existHaId;
	protected GameUtil.Code gameCode;
	protected Object period;

	protected String logTitle;

	/**
	 * 获取盘口编码
	 *
	 * @return 盘口编码
	 */
	protected abstract HandicapUtil.Code handicap();

	/**
	 * 获取抓取运行信息集合
	 *
	 * @return 每个不同的继承类做不同的处理
	 */
	protected abstract Map<GameUtil.Code, Map<String, Boolean>> grabRunMap();

	/**
	 * 抓取投注信息
	 * 每个盘口按照自己的规则抓取，但是回传信息需要统一
	 *
	 * @return 抓取投注信息
	 */
	protected abstract Map<String, SummaryInfo> summaryInfo();

	/**
	 * 投注详情信息
	 *
	 * @param ramSummaryInfo 内存中摘要信息
	 * @return 投注详情信息
	 */
	protected abstract JsonResultBeanPlus betDetail(SummaryInfo ramSummaryInfo);

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		JobDataMap dataMap = context.getMergedJobDataMap();
		existHaId = dataMap.getString("existHaId");
        if(dataMap.get("gameCode") instanceof Enum){
            gameCode = (GameUtil.Code) dataMap.get("gameCode");
        }else{
            gameCode=GameUtil.Code.valueOf(dataMap.get("gameCode").toString());
        }
		period = gameCode.getGameFactory().period(handicap()).findPeriod();
		logTitle = String.format("用户【%s】的【%s】盘口，抓取【%s】游戏【%s】期:{}", existHaId, handicap(), gameCode.getName(), period);
		//是否到了可以抓取的时间
		if (gameCode.getGameFactory().period(handicap()).getDrawTime() - System.currentTimeMillis() > gameCode.getGameFactory().getInterval()) {
			log.debug(logTitle, "未到可以开始抓取的时间");
			return;
		}
		//抓取是否在运行中
		if (grabRun()) {
			log.info(logTitle, "上一次尚未抓取完成");
			return;
		}
		grabStart();
		try {
			execute();
		} catch (Exception e) {
			log.error(logTitle, "抓取投注项异常:" + e.getMessage());
			throw e;
		} finally {
			grabEnd();
			log.debug(logTitle, "抓取投注项结束");
		}
	}
	/**
	 * 执行抓取
	 *
	 * @return true 抓取成功 false 没有抓取到数据
	 */
	private boolean execute() throws Exception {
		// 获取代理游戏设置信息
		Map<String, Object> setInfo = new IbmcHaGameSetService().findSet(existHaId, gameCode.name());
		//没有开启投注状态
		if (ContainerTool.isEmpty(setInfo) || !Boolean.parseBoolean(setInfo.get("BET_STATE_").toString())) {
			log.debug(logTitle, "代理没有开启投注");
			return false;
		}
		// 获取代理跟投的会员
		Map<String, Object> followInfo = new IbmcHaSetService().findFollowInfo(existHaId);
		if (ContainerTool.isEmpty(followInfo)) {
			log.debug(logTitle, "设置信息为空");
			return false;
		}
		//代理-发送到的会员信息
		List<Map<String, Object>> hmSendInfos = new IbmcAgentMemberInfoService().listHmInfo4SendBet(existHaId);
		if (ContainerTool.isEmpty(hmSendInfos)) {
			log.debug(logTitle, "没有需要接收的会员");
			return false;
		}

		// 跟投数据
		Map<String, SummaryInfo> summaryInfo = summaryInfo();
		if (ContainerTool.isEmpty(summaryInfo)) {
			//存储空的摘要
			GrabBetInfo.summaryInfo(existHaId, gameCode, summaryInfo);
			return false;
		}

		// 跟投数据
		Map<String, double[][]> betMap = new HashMap<>(summaryInfo.size() * 3 / 4 + 1);

		JSONObject memberInfo = new JSONObject();
		//判断跟随会员类型
		if (IbmTypeEnum.APPOINT.equal(followInfo.get("FOLLOW_MEMBER_TYPE_"))) {
			if (StringTool.isEmpty(followInfo.get("MEMBER_LIST_INFO_"))) {
				log.debug(logTitle, "没有跟单的会员");
				return false;
			}
			memberInfo = JSON.parseObject(followInfo.get("MEMBER_LIST_INFO_").toString());
		} else if (IbmTypeEnum.ALL.equal(followInfo.get("FOLLOW_MEMBER_TYPE_"))) {
			memberInfo = null;
		}

		List<String> handicapAccount = new ArrayList<>();
		//解析投注模式
		for (Map<String, Object> hmSendInfo : hmSendInfos) {
			JSONObject betModeInfo = JSON.parseObject(hmSendInfo.remove("BET_MODE_INFO_").toString());
			hmSendInfo.put("BET_MODE_", betModeInfo.get(gameCode.name()));
			if (handicap().equals(hmSendInfo.get("MEMBER_HANDICAP_CODE_"))) {
				//剔除该盘口在本平台登录的会员
				handicapAccount.add(hmSendInfo.get("MEMBER_ACCOUNT_").toString());
			}
		}
		if (memberInfo == null) {
			for (Map.Entry<String, SummaryInfo> entry : summaryInfo.entrySet()) {
				if (handicapAccount.contains(entry.getKey())) {
					continue;
				}
				memberBetMap(betMap, entry.getValue());
			}
		} else {
			for (String memberAccount : memberInfo.keySet()) {
				//该跟投会员没有投注数据 或者 该会员 已经处于跟单中
				if (!summaryInfo.containsKey(memberAccount) || handicapAccount.contains(memberAccount)) {
					continue;
				}
				memberBetMap(betMap, summaryInfo.get(memberAccount));
			}
		}
		//获取到的数据为空
		if (ContainerTool.isEmpty(betMap)) {
			log.debug(logTitle, "获取未结算投注明细为空");
			return false;
		}
		// 处理和发送投注结合
		new GrabBet(existHaId, gameCode, period).processBetMap(setInfo, hmSendInfos, memberInfo, betMap);
		return true;
	}



	/**
	 * 会员投注 集合
	 * @param betMap 会员账户 - 投注金额
	 * @param summaryInfo 摘要信息
	 */
	private void memberBetMap(Map<String, double[][]> betMap, SummaryInfo summaryInfo) {

		summaryInfo.setPeriod(period);
		SummaryInfo ramMemberInfo = GrabBetInfo
				.summary(existHaId, gameCode, summaryInfo.getAccount(), summaryInfo.getPeriod());

		int ramBetCount = 0;
		//内存中不存在注单号
		if (ramMemberInfo == null) {
			ramMemberInfo = new SummaryInfo(summaryInfo.getMemberId(), summaryInfo.getAccount());
		} else if (StringTool.isEmpty(ramMemberInfo.getOddNumber())) {
			//数据紊乱
			log.error("内存数据紊乱，内存数据为：{}", ramMemberInfo.toString());
			return;
		}
		//摘要信息相同
		if (summaryInfo.equal(ramMemberInfo)) {
			return;
		}

		// 投注详情
		JsonResultBeanPlus bean = betDetail(ramMemberInfo);
		if (!bean.isSuccess()) {
			log.info("获取投注详情失败：{}", bean.toJsonString());
			return;
		}

		//投注详情为空
		if (bean.getData() == null) {
			return;
		}
		DetailBox betDetail = (DetailBox) bean.getData();
		List<DetailInfo> details = betDetail.details();

		//放入投注数
		summaryInfo.setBetCount(details.size() + ramBetCount);
		//放入最大注单号
		summaryInfo.setOddNumber(betDetail.maxOddNumber());
		//存储抓取数据
		GrabBetInfo.summaryInfo(existHaId,gameCode,summaryInfo.getAccount(),summaryInfo);

		for (DetailInfo detail:details){
			int[] item = GameTool.item(gameCode, detail.betItem());
			//存入投注资金
			if (betMap.containsKey(summaryInfo.getAccount())) {
				betMap.get(summaryInfo.getAccount())[item[0]][item[1]] += detail.funds();
			} else {
				double[][] fundsMatrix = GameTool.getFundsMatrix(gameCode);
				fundsMatrix[item[0]][item[1]] += detail.funds();
				betMap.put(summaryInfo.getAccount(), fundsMatrix);
			}
		}


	}



	private Object platformPeriod;
	/**
	 * 期数字符串
	 *
	 * @return 期数字符串
	 */
	protected Object platformPeriod() {
		if (platformPeriod == null) {
			platformPeriod = PeriodUtil.getPeriod(handicap(), gameCode, period);
		}
		return platformPeriod;
	}

	/**
	 * 是否已经处于抓取中
	 *
	 * @return 抓取中-则由上一个工作继续完成，此工作结束
	 */
	private boolean grabRun() {
		return grabRunMap().get(gameCode).getOrDefault(existHaId, false);
	}

	/**
	 * 开始抓取
	 */
	private void grabStart() {
		grabRunMap().get(gameCode).put(existHaId, true);
	}
	/**
	 * 抓取结束
	 */
	private void grabEnd() {
		grabRunMap().get(gameCode).remove(existHaId);
	}

}
