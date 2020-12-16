package com.ibm.follow.servlet.client.core.job.bet.agent;

import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.collections.map.HashedMap;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-28 16:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GrabBetInfo {

	private static final Map<String, Map<String, Integer>> IDC_IDNO = new ConcurrentHashMap<>(20);
	private static final Map<String, Map<GameUtil.Code, Map<String, SummaryInfo>>> MEMBER_INFO = new ConcurrentHashMap<>(
			20);
	private static final Map<String, Map<GameUtil.Code, Map<String, SummaryInfo>>> AGENT_INFO = new ConcurrentHashMap<>(
			20);
	/**
	 * 获取跟随账户的最大注单号
	 *
	 * @param existHaId     存在盘口代理
	 * @param memberAccount 跟随会员
	 * @return 最大注单号
	 */
	public static int getIdno(String existHaId, String memberAccount) {
		if (IDC_IDNO.containsKey(existHaId)) {
			return IDC_IDNO.get(existHaId).getOrDefault(memberAccount, 0);
		}
		return 0;
	}

	/**
	 * 放入注单号
	 *
	 * @param existHaId     存在盘口代理
	 * @param memberAccount 跟随会员
	 * @param idno          注单号
	 */
	public static void putIdno(String existHaId, String memberAccount, int idno) {
		if (IDC_IDNO.containsKey(existHaId)) {
			IDC_IDNO.get(existHaId).put(memberAccount, Math.max(idno, getIdno(existHaId, memberAccount)));
		} else {
			Map<String, Integer> idnoMap = new HashMap<>(20);
			idnoMap.put(memberAccount, idno);
			IDC_IDNO.put(existHaId, idnoMap);
		}
	}

	/**
	 * 获取代理信息
	 *
	 * @param existHaId 存在盘口代理
	 * @param gameCode  游戏编码
	 * @return 代理信息
	 */
	public static Map<String, SummaryInfo> getAgent(String existHaId, GameUtil.Code gameCode) {
		if (AGENT_INFO.containsKey(existHaId)) {
			if (AGENT_INFO.get(existHaId).containsKey(gameCode)) {
				return AGENT_INFO.get(existHaId).get(gameCode);
			}
		}
		return new HashedMap();
	}

	/**
	 * 放入代理信息
	 *
	 * @param existHaId 存在盘口代理
	 * @param gameCode  游戏编码
	 * @param agent     代理信息
	 */
	public static void putAgent(String existHaId, GameUtil.Code gameCode, Map<String, SummaryInfo> agent) {
		if (AGENT_INFO.containsKey(existHaId)) {
			AGENT_INFO.get(existHaId).put(gameCode, agent);
		} else {
			Map<GameUtil.Code, Map<String, SummaryInfo>> gameAgent = new HashedMap(6);
			gameAgent.put(gameCode, agent);
			AGENT_INFO.put(existHaId, gameAgent);
		}
	}

	/**
	 * 获取会员信息
	 *
	 * @param existHaId   存在盘口代理
	 * @param gameCode    游戏编码
	 * @param summaryInfo 跟随会员
	 * @return 会员信息
	 */
	public static SummaryInfo getMember(String existHaId, GameUtil.Code gameCode, SummaryInfo summaryInfo) {
		if (MEMBER_INFO.containsKey(existHaId)) {
			if (MEMBER_INFO.get(existHaId).containsKey(gameCode)) {
				if (MEMBER_INFO.get(existHaId).get(gameCode).containsKey(summaryInfo.getAccount())) {
					SummaryInfo memberInfo = MEMBER_INFO.get(existHaId).get(gameCode).get(summaryInfo.getAccount());
					if (summaryInfo.getPeriod().equals(memberInfo.getPeriod())) {
						return memberInfo;
					}
					MEMBER_INFO.get(existHaId).get(gameCode).remove(summaryInfo.getAccount());
				}
			}
		}
		return null;
	}
	/**
	 * 放入会员信息
	 *
	 * @param existHaId   存在盘口代理
	 * @param gameCode    游戏code
	 * @param summaryInfo 摘要信息
	 */
	public static void putMember(String existHaId, GameUtil.Code gameCode, SummaryInfo summaryInfo, String oddNumber) {
		if (oddNumber != null) {
			summaryInfo.setOddNumber(oddNumber);
		}
		if (MEMBER_INFO.containsKey(existHaId)) {
			if (MEMBER_INFO.get(existHaId).containsKey(gameCode)) {
				SummaryInfo memberInfo = MEMBER_INFO.get(existHaId).get(gameCode).get(summaryInfo.getAccount());
				if (ContainerTool.notEmpty(memberInfo)) {
					if (StringTool.notEmpty(memberInfo.getOddNumber()) && StringTool
							.isEmpty(summaryInfo.getOddNumber())) {
						summaryInfo.setOddNumber(memberInfo.getOddNumber());
					}
				}
				MEMBER_INFO.get(existHaId).get(gameCode).put(summaryInfo.getAccount(), summaryInfo);
			} else {
				Map<String, SummaryInfo> memberMap = new ConcurrentHashMap<>();
				memberMap.put(summaryInfo.getAccount(), summaryInfo);
				MEMBER_INFO.get(existHaId).put(gameCode, memberMap);
			}
		} else {
			Map<GameUtil.Code, Map<String, SummaryInfo>> gameMember = new HashMap<>(6);
			Map<String, SummaryInfo> memberMap = new ConcurrentHashMap<>();
			memberMap.put(summaryInfo.getAccount(), summaryInfo);
			gameMember.put(gameCode, memberMap);
			MEMBER_INFO.put(existHaId, gameMember);
		}
	}

	/**
	 * 清除代理的信息
	 *
	 * @param existHaId 已存在盘口代理
	 */
	public static void removeHaInfo(String existHaId) {
		MEMBER_INFO.remove(existHaId);
		AGENT_INFO.remove(existHaId);
		IDC_IDNO.remove(existHaId);
        GRAB_SUMMARY_INFO.remove(existHaId);
        GRAB_SUMMARY_AGENT_INFO.remove(existHaId);
	}

	/**
	 * 清除代理和会员相关信息
	 *
	 * @param existHaId 已存在盘口代理
	 */
	public static void clearInfo(String existHaId, GameUtil.Code gameCode) {
		if (AGENT_INFO.containsKey(existHaId)) {
			AGENT_INFO.get(existHaId).remove(gameCode);
		}
		if (MEMBER_INFO.containsKey(existHaId)) {
			MEMBER_INFO.get(existHaId).remove(gameCode);
		}
	}

	//region 摘要信息

	private static final Map<String, Map<GameUtil.Code, Map<String, SummaryInfo>>> GRAB_SUMMARY_INFO = new HashMap<>(
			20);

	//region 代理摘要信息

	private static final Map<String, Map<GameUtil.Code, Map<String, SummaryInfo>>> GRAB_SUMMARY_AGENT_INFO = new HashMap<>(
			5);

	/**
	 * 代理摘要信息集合
	 *
	 * @param existHaId 代理主键
	 * @param gameCode  游戏
	 * @return 存在则传出 - 不存在则新建一个集合
	 */
	public static Map<String, SummaryInfo> summaryAgentInfo(String existHaId, GameUtil.Code gameCode) {
		if (GRAB_SUMMARY_AGENT_INFO.containsKey(existHaId) || GRAB_SUMMARY_AGENT_INFO.get(existHaId)
				.containsKey(gameCode)) {
			return GRAB_SUMMARY_AGENT_INFO.get(existHaId).get(gameCode);
		}
		return new HashedMap();
	}

	/**
	 * 代理摘要信息集合
	 *
	 * @param existHaId 代理主键
	 * @param gameCode  游戏
	 * @param agent     代理摘要信息集合
	 */
	public static void summaryAgentInfo(String existHaId, GameUtil.Code gameCode, Map<String, SummaryInfo> agent) {
		if (GRAB_SUMMARY_AGENT_INFO.containsKey(existHaId)) {
			GRAB_SUMMARY_AGENT_INFO.get(existHaId).put(gameCode, agent);
		} else {
			Map<GameUtil.Code, Map<String, SummaryInfo>> gameAgent = new HashedMap(6);
			gameAgent.put(gameCode, agent);
			GRAB_SUMMARY_AGENT_INFO.put(existHaId, gameAgent);
		}
	}

	//endregion

	/**
	 * 获取摘要
	 * 账户和期数匹配的 摘要 绝对匹配 则传出 不匹配则传出为null
	 *
	 * @param existHaId 代理主键
	 * @param gameCode  游戏
	 * @param account   摘要账户
	 * @param period    抓取期数
	 * @return 摘要
	 */
	public static SummaryInfo summary(String existHaId, GameUtil.Code gameCode, String account, Object period) {
		if (GRAB_SUMMARY_INFO.containsKey(existHaId) && GRAB_SUMMARY_INFO.get(existHaId).containsKey(gameCode)
				&& GRAB_SUMMARY_INFO.get(existHaId).get(gameCode).containsKey(account)) {
			SummaryInfo summary = GRAB_SUMMARY_INFO.get(existHaId).get(gameCode).get(account);
			if (summary.getPeriod().equals(period)) {
				return summary;
			} else {
				GRAB_SUMMARY_INFO.get(existHaId).get(gameCode).remove(account);
			}
		}
		return null;
	}

	/**
	 * 存储摘要信息
	 *
	 * @param existHaId   代理主键
	 * @param gameCode    游戏
	 * @param account   摘要账户
	 * @param summaryInfo 摘要信息
	 */
	public static void summaryInfo(String existHaId, GameUtil.Code gameCode, String account, SummaryInfo summaryInfo) {
		Map<GameUtil.Code, Map<String, SummaryInfo>> gameSummary = GRAB_SUMMARY_INFO.computeIfAbsent(existHaId,k->new HashedMap(6));
		Map<String, SummaryInfo> summary = gameSummary.computeIfAbsent(gameCode, k -> new HashMap<>(10));
		summary.put(account, summaryInfo);

	}

	/**
	 * 存储摘要信息
	 *
	 * @param existHaId   代理主键
	 * @param gameCode    游戏
	 * @param summaryInfo 摘要信息
	 */
	public static void summaryInfo(String existHaId, GameUtil.Code gameCode, Map<String, SummaryInfo> summaryInfo) {
		if (GRAB_SUMMARY_INFO.containsKey(existHaId)) {
			GRAB_SUMMARY_INFO.get(existHaId).put(gameCode, summaryInfo);
		} else {
			Map<GameUtil.Code, Map<String, SummaryInfo>> gameAgent = new HashedMap(6);
			gameAgent.put(gameCode, summaryInfo);
			GRAB_SUMMARY_INFO.put(existHaId, gameAgent);
		}
	}

	/**
	 * 清除代理和会员相关信息
	 *
	 * @param existHaId 代理主键
	 * @param gameCode  游戏
	 */
	public static void clearSummaryInfo(String existHaId, GameUtil.Code gameCode) {
		if (GRAB_SUMMARY_AGENT_INFO.containsKey(existHaId)) {
			GRAB_SUMMARY_AGENT_INFO.get(existHaId).remove(gameCode);
		}
		if (GRAB_SUMMARY_INFO.containsKey(existHaId)) {
			GRAB_SUMMARY_INFO.get(existHaId).remove(gameCode);
		}
	}
	//endregion

}
