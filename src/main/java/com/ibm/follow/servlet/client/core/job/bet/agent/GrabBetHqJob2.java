package com.ibm.follow.servlet.client.core.job.bet.agent;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.agent.HqAgentUtil;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.doming.core.tools.ContainerTool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @deprecated
 * @Author: Dongming
 * @Date: 2019-12-18 17:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GrabBetHqJob2 extends GrabBetJob2 {

	private static final HandicapUtil.Code HANDICAP = HandicapUtil.Code.HQ;
	private static final Map<GameUtil.Code, Map<String, Boolean>> GRAB_RUN_MAP = new HashMap<>(GameUtil.codes().length);

	static {
		for (GameUtil.Code code : GameUtil.hqCodes()) {
			GRAB_RUN_MAP.put(code, new ConcurrentHashMap<>(10));
		}
	}

	private HqAgentUtil agentUtil() {
		return HqAgentUtil.findInstance();
	}
	@Override protected HandicapUtil.Code handicap() {
		return HANDICAP;
	}
	@Override protected Map<GameUtil.Code, Map<String, Boolean>> grabRunMap() {
		return GRAB_RUN_MAP;
	}
	@Override protected Map<String, SummaryInfo> summaryInfo() {
        //内存中的代理信息
        Map<String, SummaryInfo> agent = GrabBetInfo.getAgent(existHaId, gameCode);
		// 获取跟投会员的未结算摘要信息
		JsonResultBeanPlus bean = agentUtil().getBetSummary(existHaId, gameCode, platformPeriod(),agent);
		if (!bean.isSuccess()) {
			log.warn(logTitle, String.format("获取未结算摘要信息失败【%s】", bean.toJsonString()));
			return null;
		}
		if (ContainerTool.isEmpty(bean.getData())) {
			//没信息，说明上一期的已经结算了，清除历史的信息
			GrabBetInfo.clearSummaryInfo(existHaId, gameCode);
			log.debug(logTitle, "获取投注摘要为空");
			return null;
		}
		return (Map<String, SummaryInfo>) bean.getData();
	}
	@Override protected JsonResultBeanPlus betDetail(SummaryInfo ramSummaryInfo) {
		return agentUtil().getBetDetail(existHaId, gameCode, platformPeriod(), ramSummaryInfo.getMemberId(),
				ramSummaryInfo.getOddNumber() != null ? ramSummaryInfo.getOddNumber() : "");
	}
}
