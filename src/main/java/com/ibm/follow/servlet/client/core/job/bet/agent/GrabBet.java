package com.ibm.follow.servlet.client.core.job.bet.agent;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_ha_follow_bet.entity.IbmcHaFollowBet;
import com.ibm.follow.servlet.client.ibmc_ha_follow_bet.service.IbmcHaFollowBetService;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-28 18:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GrabBet {
	private int followFundsTh;
	private int betFundsTh;

	private String existHaId;
	private GameUtil.Code gameCode;
	private Object period;

	GrabBet(String existHaId, GameUtil.Code gameCode, Object period) {
		this.existHaId = existHaId;
		this.gameCode = gameCode;
		this.period = period;
	}

	/**
	 * 处理和发送投注结合
	 *
	 * @param setInfo     代理设置信息
	 * @param hmSendInfos 待发送会员信息
	 * @param memberInfo  会员跟单信息
	 * @param betMap      投注组合
	 */
	public void processBetMap(Map<String, Object> setInfo, List<Map<String, Object>> hmSendInfos, JSONObject memberInfo,
			Map<String, double[][]> betMap) throws Exception {
		double followMultiple = NumberTool.doubleT(setInfo.getOrDefault("BET_FOLLOW_MULTIPLE_T_", 1.0));
		int leastAmountTh = NumberTool.getInteger(setInfo.getOrDefault("BET_LEAST_AMOUNT_T_", 0));
		int mostAmountTh = NumberTool.getInteger(setInfo.getOrDefault("BET_MOST_AMOUNT_T_", Integer.MAX_VALUE));

		boolean filterNumber = Boolean.parseBoolean(setInfo.get("BET_FILTER_NUMBER_").toString());
		boolean filterTwoSide = Boolean.parseBoolean(setInfo.get("BET_FILTER_TWO_SIDE_").toString());
		boolean numberOpposing = Boolean.parseBoolean(setInfo.get("NUMBER_OPPOSING_").toString());
		boolean twoSideOpposing = Boolean.parseBoolean(setInfo.get("TWO_SIDE_OPPOSING_").toString());

		//过滤项目
		String[] filterProjects = new String[0];
		if (StringTool.notEmpty(setInfo.get("FILTER_PROJECT_"))) {
			filterProjects = setInfo.get("FILTER_PROJECT_").toString().split("#");
		}

		//发送消息
		JSONObject messageJson = new JSONObject();
		messageJson.put("GAME_CODE_", gameCode);
		messageJson.put("PERIOD_", period);
		messageJson.put("EXIST_HA_ID_", existHaId);

		Date nowTime = new Date();
		IbmcHaFollowBetService haFollowBetService = new IbmcHaFollowBetService();

		for (Map.Entry<String, double[][]> betEntry : betMap.entrySet()) {
			String memberAccount = betEntry.getKey();
			double[][] fundsMatrix = betEntry.getValue();
			double[][] numberMatrix = null;
			double[][][] twoSideMatrix = null;
			if (!filterNumber) {
				numberMatrix = GameTool.getNumberMatrix(gameCode, fundsMatrix);
				//数字反投
				if (numberOpposing) {
					GameTool.numberOpposing(gameCode, numberMatrix);
				}
			}
			if (!filterTwoSide) {
				//双面反投
				twoSideMatrix = GameTool.getTwoSideMatrix(gameCode, fundsMatrix);
				if (twoSideOpposing) {
					GameTool.twoSideOpposing(gameCode, twoSideMatrix);
				}
			}
			//投注内容
			betFundsTh = 0;
			String betContent = getBetItem(gameCode, fundsMatrix);

			//跟投倍数
			double multiple = followMultiple * (memberInfo == null ?
					1.0 :
					NumberTool.getDouble(memberInfo.getOrDefault(memberAccount, 1.0)));

			// 跟投内容
			String followContent = "";
			followFundsTh = 0;
			//号码 - 根据游戏设置进行投注项处理
			if (numberMatrix != null) {
				followContent += getNumberItem(gameCode, filterProjects, numberMatrix, multiple, leastAmountTh,
						mostAmountTh);
			}
			//双面 - 根据游戏设置进行投注项处理
			if (twoSideMatrix != null) {
				followContent += getTwoSideItem(gameCode, filterProjects, twoSideMatrix, multiple, leastAmountTh,
						mostAmountTh);
			}
			//其他 - 根据游戏设置进行投注项处理
			followContent += getOtherItem(gameCode, filterProjects, fundsMatrix, multiple, leastAmountTh, mostAmountTh);

			// 存储跟投数据
			IbmcHaFollowBet haFollowBet = new IbmcHaFollowBet();
			haFollowBet.setExistHaId(existHaId);
			haFollowBet.setMemberAccount(memberAccount);
			haFollowBet.setBetTimeLong(System.currentTimeMillis());
			haFollowBet.setGameCode(gameCode.name());
			haFollowBet.setPeriod(period);
			haFollowBet.setBetInfo(betContent);
			haFollowBet.setBetFundT(betFundsTh);
			haFollowBet.setFollowContent(followContent);
			haFollowBet.setFollowFundT(followFundsTh);
			haFollowBet.setCreateTime(nowTime);
			haFollowBet.setCreateTimeLong(System.currentTimeMillis());
			haFollowBet.setUpdateTimeLong(System.currentTimeMillis());
			haFollowBet.setState(IbmStateEnum.OPEN.name());
			String haFollowBetId = haFollowBetService.save(haFollowBet);

			//跟投数据
			messageJson.put("BET_CONTENT_", followContent);
			messageJson.put("BET_INFO_", betContent);
			messageJson.put("FUNDS_T_", followFundsTh);
			messageJson.put("BET_FUNDS_T_", betFundsTh);
			messageJson.put("HA_FOLLOW_BET_ID_", haFollowBetId);
			messageJson.put("MEMBER_ACCOUNT_", memberAccount);
			messageJson.remove("ROW_NUM");

			messageJson.put("METHOD_", IbmMethodEnum.AGENT_BET_INFO.name());
			RabbitMqTool.sendAgentBetInfo(messageJson.toString());
			//跟投信息为空时，只发送代理投注信息，不发送会员投注信息
			if (StringTool.isEmpty(followContent)) {
				return;
			}
			messageJson.remove("BET_FUNDS_T_");
			messageJson.remove("BET_INFO_");
			//投注信息code按发送的为准(除合并的)
			messageJson.put("METHOD_", IbmMethodEnum.FOLLOW_BET.name());
			for (Map<String, Object> hmSendInfo : hmSendInfos) {
				//投注模式
				String clientCode;
				messageJson.put("EXIST_HM_ID_", hmSendInfo.get("EXIST_HM_ID_"));
				if (IbmTypeEnum.VIRTUAL.equal(hmSendInfo.get("BET_MODE_"))) {
					clientCode = IbmTypeEnum.VIRTUAL.name();
				} else {
					clientCode = hmSendInfo.get("MEMBER_CLIENT_CODE_").toString();
				}

				// 发送跟投数据
				RabbitMqTool.sendMemberBetInfo(messageJson.toString(), clientCode);
			}
		}
	}

	/**
	 * 获取投注内容
	 *
	 * @param gameCode    游戏编码
	 * @param fundsMatrix 资金矩阵
	 * @return 投注项
	 */
	private String getBetItem(GameUtil.Code gameCode, double[][] fundsMatrix) {
		String betItem = "";
		for (int i = 0, len1 = fundsMatrix[0].length; i < fundsMatrix.length; i++) {
			for (int j = 0; j < len1; j++) {
				String item = GameTool.itemStr(gameCode, i, j);
				int fundsTh = NumberTool.intValueT(fundsMatrix[i][j]);
				if (fundsTh > 0) {
					betFundsTh += fundsTh;
					betItem = betItem.concat(item).concat("|").concat(Integer.toString(fundsTh)).concat("#");
				}
			}
		}
		return betItem;
	}

	/**
	 * 获取号码投注项
	 *
	 * @param gameCode       游戏编码
	 * @param filterProjects 过滤项目
	 * @param numberMatrix   号码资金矩阵
	 * @param multiple       跟投倍数
	 * @param leastAmountTh  最低金额
	 * @param mostAmountTh   最高金额
	 * @return 投注项
	 */
	private String getNumberItem(GameUtil.Code gameCode, String[] filterProjects, double[][] numberMatrix,
			double multiple, int leastAmountTh, int mostAmountTh) {
		String betItem = "";
		for (int i = 0; i < numberMatrix.length; i++) {
			for (int j = 0; j < numberMatrix[0].length; j++) {
				// 向上取整0
				int fundsTh = NumberTool.intValueT((Math.ceil(numberMatrix[i][j] * multiple)));
				if (fundsTh < leastAmountTh) {
					continue;
				}
				if (fundsTh > mostAmountTh) {
					fundsTh = mostAmountTh;
				}
				String item = GameTool.itemStr(gameCode, i, j);
				if (StringTool.contains(filterProjects, item)) {
					continue;
				}
				followFundsTh += fundsTh;
				betItem = betItem.concat(item).concat("|").concat(Integer.toString(fundsTh)).concat("#");

			}
		}
		return betItem;
	}

	/**
	 * 获取双面投注项
	 *
	 * @param gameCode       游戏编码
	 * @param filterProjects 过滤项目
	 * @param twoSideMatrix  双面资金矩阵
	 * @param leastAmountTh  最低金额
	 * @param mostAmountTh   最高金额
	 * @return 投注项
	 */
	private String getTwoSideItem(GameUtil.Code gameCode, String[] filterProjects, double[][][] twoSideMatrix,
			double multiple, int leastAmountTh, int mostAmountTh) {
		String betItem = "";
		int len = 0;
		for (double[][] sideMatrix : twoSideMatrix) {
			for (int j = 0; j < sideMatrix.length; j++) {
				for (int k = 0; k < sideMatrix[j].length; k++) {
					// 向上取整1
					int fundsTh = NumberTool.intValueT((Math.ceil(sideMatrix[j][k] * multiple)));
					if (fundsTh < leastAmountTh) {
						continue;
					}
					if (fundsTh > mostAmountTh) {
						fundsTh = mostAmountTh;
					}
					//9.29改:号码双面从19开始，球号双面从10开始
					String item = GameTool.getItemStr(gameCode, j, len + k);
					if (StringTool.contains(filterProjects, item)) {
						continue;
					}
					followFundsTh += fundsTh;
					betItem = betItem.concat(item).concat("|").concat(Integer.toString(fundsTh)).concat("#");
				}
			}
			len += sideMatrix[0].length;
		}
		return betItem;
	}

	/**
	 * 获取其他投注项
	 *
	 * @param gameCode       游戏编码
	 * @param filterProjects 过滤项目
	 * @param fundsMatrix    资金矩阵
	 * @param leastAmountTh  最低金额
	 * @param mostAmountTh   最高金额
	 * @return 投注项
	 */
	private String getOtherItem(GameUtil.Code gameCode, String[] filterProjects, double[][] fundsMatrix,
			double multiple, int leastAmountTh, int mostAmountTh) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return getNumOtherItem(gameCode, filterProjects, fundsMatrix, multiple, leastAmountTh, mostAmountTh);
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return getBallOtherItem(gameCode, filterProjects, fundsMatrix, multiple, leastAmountTh, mostAmountTh);
			case XYNC:
			case GDKLC:
				return getHappyOtherItem(gameCode, filterProjects, fundsMatrix, multiple, leastAmountTh, mostAmountTh);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取其他投注项
	 *
	 * @param gameCode       游戏编码
	 * @param filterProjects 过滤项目
	 * @param fundsMatrix    资金矩阵
	 * @param leastAmountTh  最低金额
	 * @param mostAmountTh   最高金额
	 * @return 投注项
	 */
	private String getNumOtherItem(GameUtil.Code gameCode, String[] filterProjects, double[][] fundsMatrix,
			double multiple, int leastAmountTh, int mostAmountTh) {
		double[] otherMatrix = GameTool.getNumOtherItem(fundsMatrix);
		String betItem = "";
		for (int i = 0; i < otherMatrix.length; i++) {
			// 向上取整2
			int fundsTh = NumberTool.intValueT((Math.ceil(otherMatrix[i] * multiple)));
			if (fundsTh < leastAmountTh) {
				continue;
			}
			if (fundsTh > mostAmountTh) {
				fundsTh = mostAmountTh;
			}
			String item = GameTool.itemStr(gameCode, 10, i);
			if (StringTool.contains(filterProjects, item)) {
				continue;
			}
			followFundsTh += fundsTh;
			betItem = betItem.concat(item).concat("|").concat(Integer.toString(fundsTh)).concat("#");
		}
		return betItem;
	}
	/**
	 * 获取球号其他投注项
	 *
	 * @param gameCode       游戏编码
	 * @param filterProjects 过滤项目
	 * @param fundsMatrix    资金矩阵
	 * @param leastAmountTh  最低金额
	 * @param mostAmountTh   最高金额
	 * @return 投注项
	 */
	private String getBallOtherItem(GameUtil.Code gameCode, String[] filterProjects, double[][] fundsMatrix,
			double multiple, int leastAmountTh, int mostAmountTh) {
		String betItem = "";
		double[][] otherMatrix = GameTool.getBallOtherItem(fundsMatrix);
		for (int i = 0; i < otherMatrix.length; i++) {
			for (int j = 0; j < otherMatrix[i].length; j++) {
				// 向上取整3
				int fundsTh = NumberTool.intValueT((Math.ceil(otherMatrix[i][j] * multiple)));
				if (fundsTh < leastAmountTh) {
					continue;
				}
				if (fundsTh > mostAmountTh) {
					fundsTh = mostAmountTh;
				}
				String item;
				if (i < 2) {
					item = GameTool.itemStr(gameCode, 5 + i, j + 10 + 4 * i);
				} else {
					item = GameTool.itemStr(gameCode, 5 + i, j + 17);
				}
				if (StringTool.contains(filterProjects, item)) {
					continue;
				}
				followFundsTh += fundsTh;
				betItem = betItem.concat(item).concat("|").concat(Integer.toString(fundsTh)).concat("#");
			}
		}
		return betItem;
	}

	/**
	 * 获取快乐彩类其他投注项
	 *
	 * @param gameCode       游戏编码
	 * @param filterProjects 过滤项目
	 * @param fundsMatrix    资金矩阵
	 * @param leastAmountTh  最低金额
	 * @param mostAmountTh   最高金额
	 * @return 投注项
	 */
	private String getHappyOtherItem(GameUtil.Code gameCode, String[] filterProjects, double[][] fundsMatrix,
			double multiple, int leastAmountTh, int mostAmountTh) {
		double[] otherMatrix = GameTool.getHappyOtherItem(fundsMatrix);
		String betItem = "";
		for (int i = 0; i < otherMatrix.length; i++) {
			// 向上取整2
			int fundsTh = NumberTool.intValueT((Math.ceil(otherMatrix[i] * multiple)));
			if (fundsTh < leastAmountTh) {
				continue;
			}
			if (fundsTh > mostAmountTh) {
				fundsTh = mostAmountTh;
			}
			String item = GameTool.itemStr(gameCode, 8, i);
			if (StringTool.contains(filterProjects, item)) {
				continue;
			}
			followFundsTh += fundsTh;
			betItem = betItem.concat(item).concat("|").concat(Integer.toString(fundsTh)).concat("#");
		}
		return betItem;

	}

}
