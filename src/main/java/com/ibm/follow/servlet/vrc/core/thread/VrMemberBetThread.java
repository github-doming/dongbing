package com.ibm.follow.servlet.vrc.core.thread;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.game.GameUtil;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 虚拟会员投注线程
 * @Author: null
 * @Date: 2020-07-15 14:19
 * @Version: v1.0
 */
public class VrMemberBetThread extends BaseCommThread {

	private Map<String, Object> gameInfo;
	private GameUtil.Code gameCode;
	private JSONObject content;
	private double[][] fundsMatrix;
	private int followFundsTh;
	private int betFundsTh;

	public VrMemberBetThread(Map<String, Object> gameInfo, GameUtil.Code gameCode, JSONObject content, double[][] fundsMatrix) {
		this.gameInfo = gameInfo;
		this.gameCode = gameCode;
		this.content = content;
		this.fundsMatrix = fundsMatrix;
	}

	@Override
	public String execute(String inVar) throws Exception {
		//处理游戏跟投信息
		double followMultiple = NumberTool.doubleT(gameInfo.getOrDefault("BET_FOLLOW_MULTIPLE_T_", 1.0));
		int leastAmountTh = NumberTool.getInteger(gameInfo.getOrDefault("BET_LEAST_AMOUNT_T_", 0));
		int mostAmountTh = NumberTool.getInteger(gameInfo.getOrDefault("BET_MOST_AMOUNT_T_", Integer.MAX_VALUE));

		boolean filterNumber = Boolean.parseBoolean(gameInfo.get("BET_FILTER_NUMBER_").toString());
		boolean filterTwoSide = Boolean.parseBoolean(gameInfo.get("BET_FILTER_TWO_SIDE_").toString());
		boolean numberOpposing = Boolean.parseBoolean(gameInfo.get("NUMBER_OPPOSING_").toString());
		boolean twoSideOpposing = Boolean.parseBoolean(gameInfo.get("TWO_SIDE_OPPOSING_").toString());

		//过滤项目
		String[] filterProjects = new String[0];
		if (StringTool.notEmpty(gameInfo.get("FILTER_PROJECT_"))) {
			filterProjects = gameInfo.get("FILTER_PROJECT_").toString().split("#");
		}
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
		String betContent = getBetItem();
		// 跟投内容
		String followContent = "";
		followFundsTh = 0;
		//号码 - 根据游戏设置进行投注项处理
		if (numberMatrix != null) {
			followContent += getNumberItem(filterProjects, numberMatrix, followMultiple, leastAmountTh,
					mostAmountTh);
		}
		//双面 - 根据游戏设置进行投注项处理
		if (twoSideMatrix != null) {
			followContent += getTwoSideItem(filterProjects, twoSideMatrix, followMultiple, leastAmountTh,
					mostAmountTh);
		}
		//其他 - 根据游戏设置进行投注项处理
		followContent += getOtherItem(filterProjects, followMultiple, leastAmountTh, mostAmountTh);

		//发送消息
		content.put("BET_FUNDS_T_", betFundsTh);
		content.put("USER_ID_", gameInfo.get("USER_ID_"));
		content.put("BET_INFO_", betContent);
		content.put("BET_CONTENT_", followContent);
		content.put("FUNDS_T_", followFundsTh);
		content.put("METHOD_", IbmMethodEnum.FOLLOW_VR_MEMBER_BET.name());

		RabbitMqTool.sendVrMemberBetInfo(content.toString());
		return null;
	}

	private String getBetItem() {
		String betItem = "";
		for (int i = 0, len1 = fundsMatrix[0].length; i < fundsMatrix.length; i++) {
			for (int j = 0; j < len1; j++) {
				String item = GameTool.itemStr(gameCode, i, j);
				double fundsTh = fundsMatrix[i][j];
				if (fundsTh > 0) {
					betFundsTh += fundsTh;
					betItem = betItem.concat(item).concat("|").concat(fundsTh / 1000 + "#");
				}
			}
		}
		return betItem;
	}

	private String getNumberItem(String[] filterProjects, double[][] numberMatrix,
								 double multiple, int leastAmountTh, int mostAmountTh) {
		String betItem = "";
		for (int i = 0; i < numberMatrix.length; i++) {
			for (int j = 0; j < numberMatrix[0].length; j++) {
				int fundsTh = Double.valueOf(Math.ceil(numberMatrix[i][j] * multiple)).intValue();
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
				betItem = betItem.concat(item).concat("|").concat(fundsTh + "#");

			}
		}
		return betItem;
	}

	private String getTwoSideItem(String[] filterProjects, double[][][] twoSideMatrix,
								  double multiple, int leastAmountTh, int mostAmountTh) {
		String betItem = "";
		int len = 0;
		for (double[][] sideMatrix : twoSideMatrix) {
			for (int j = 0; j < sideMatrix.length; j++) {
				for (int k = 0; k < sideMatrix[j].length; k++) {
					int fundsTh = Double.valueOf((Math.ceil(sideMatrix[j][k] * multiple))).intValue();
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
					betItem = betItem.concat(item).concat("|").concat(fundsTh + "#");
				}
			}
			len += sideMatrix[0].length;
		}
		return betItem;
	}

	private String getOtherItem(String[] filterProjects, double multiple, int leastAmountTh, int mostAmountTh) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return getNumOtherItem(gameCode, filterProjects, multiple, leastAmountTh, mostAmountTh);
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return getBallOtherItem(gameCode, filterProjects, multiple, leastAmountTh, mostAmountTh);
			case XYNC:
			case GDKLC:
				return getHappyOtherItem(gameCode, filterProjects, multiple, leastAmountTh, mostAmountTh);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	private String getNumOtherItem(GameUtil.Code gameCode, String[] filterProjects,
								   double multiple, int leastAmountTh, int mostAmountTh) {
		double[] otherMatrix = GameTool.getNumOtherItem(fundsMatrix);
		String betItem = "";
		for (int i = 0; i < otherMatrix.length; i++) {
			int fundsTh = Double.valueOf(Math.ceil(otherMatrix[i] * multiple)).intValue();
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
			betItem = betItem.concat(item).concat("|").concat(fundsTh + "#");
		}
		return betItem;
	}

	private String getBallOtherItem(GameUtil.Code gameCode, String[] filterProjects,
									double multiple, int leastAmountTh, int mostAmountTh) {
		String betItem = "";
		double[][] otherMatrix = GameTool.getBallOtherItem(fundsMatrix);
		for (int i = 0; i < otherMatrix.length; i++) {
			for (int j = 0; j < otherMatrix[i].length; j++) {
				int fundsTh = Double.valueOf(Math.ceil(otherMatrix[i][j] * multiple)).intValue();
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
				betItem = betItem.concat(item).concat("|").concat(fundsTh + "#");
			}
		}
		return betItem;
	}

	private String getHappyOtherItem(GameUtil.Code gameCode, String[] filterProjects,
									 double multiple, int leastAmountTh, int mostAmountTh) {
		double[] otherMatrix = GameTool.getHappyOtherItem(fundsMatrix);
		String betItem = "";
		for (int i = 0; i < otherMatrix.length; i++) {
			int fundsTh = Double.valueOf(Math.ceil(otherMatrix[i] * multiple)).intValue();
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
			betItem = betItem.concat(item).concat("|").concat(fundsTh + "#");
		}
		return betItem;

	}
}
