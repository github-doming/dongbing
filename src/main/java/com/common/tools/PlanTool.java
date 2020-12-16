package com.common.tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.BaseGameUtil;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

/**
 * @Description: 游戏工具类
 * @Author: null
 * @Date: 2020-05-20 11:29
 * @Version: v1.0
 */
public class PlanTool extends StringTool {

	public static String getBetPosition(String betPosition, BaseGameUtil.Code gameCode) {
		if (StringTool.isEmpty(betPosition)) {
			return null;
		}
		String[] positions;
		switch (gameCode) {
			case PK10:
			case COUNTRY_10:
			case SELF_10_2:
			case JS10:
			case XYFT:
				positions=NUMBER_BET_CONTENT;
				break;
			case COUNTRY_SSC:
			case SELF_SSC_5:
			case CQSSC:
			case JSSSC:
				positions=BALL_BET_CONTENT;
				break;
			case XYNC:
			case GDKLC:
				positions=HAPPY_BET_CONTENT;
				break;
			default:
				return null;
		}
		for (String position : positions) {
			if (position.equals(betPosition)) {
				return betPosition;
			}
		}
		return null;
	}
	/**
	 * 根据索引获取中文排名
	 *
	 * @param rank 索引
	 * @return 中文排名
	 * @date 2018年7月27日 下午2:40:55
	 * @author Dongming
	 * @version v1.0
	 */
	public static String getRankCn(Integer rank, BaseGameUtil.Code gameCode) {
		if (StringTool.isEmpty(rank) || rank < 0) {
			return null;
		}
		switch (gameCode) {
			case PK10:
			case COUNTRY_10:
			case SELF_10_2:
			case JS10:
			case XYFT:
				if (rank >= 11) {
					return null;
				}
				return CHINESE_NUMBER_RANK[rank];
			case COUNTRY_SSC:
			case SELF_SSC_5:
			case CQSSC:
			case JSSSC:
				if(rank>=CHINESE_BALL_RANK.length){
					return null;
				}
				return CHINESE_BALL_RANK[rank];
			case XYNC:
			case GDKLC:
				if(rank>=CHINESE_HAPPY_RANK.length){
					return null;
				}
				return CHINESE_HAPPY_RANK[rank];
			default:
				return null;
		}
	}
	/**
	 * 匹配号码 - 反投
	 * 位置投注
	 * @param baseData 基准开奖数据
	 * @param valiData 验证开奖数据
	 * @param selects  选择位置《验证开奖数据》
	 * @param bets     投注位置《基准开奖数据》
	 * @return 匹配成功
	 */
	public static boolean matchNum(String[] baseData, String[] valiData, String[] selects, String[] bets) {
		for (String bet : bets) {
			if(baseData.length<Integer.parseInt(bet)){
				continue;
			}
			String open = baseData[Integer.parseInt(bet) - 1];
			for (String select : selects) {
				if(baseData.length<Integer.parseInt(select)){
					continue;
				}
				String vali = valiData[Integer.parseInt(select) - 1];
				if (open.equals(vali)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 匹配号码
	 * 跟上期双面
	 * @param baseData 基准开奖数据
	 * @param valiData 验证开奖数据
	 * @param state    正投/反投
	 * @param code     方案组code
	 * @param gameCode 游戏编码
	 * @return 匹配成功
	 */
	public static boolean matchNum(String[] baseData, String[] valiData, boolean state, String code, BaseGameUtil.Code gameCode) {
		String[] strs = code.split("_");
		int numIndex = Integer.parseInt(strs[0]);
		int typeIndex = Integer.parseInt(strs[1]);
		switch (gameCode) {
			case PK10:
			case JS10:
			case XYFT:
			case SELF_10_2:
			case COUNTRY_10:
				return matchNumber(baseData, valiData, state, numIndex, typeIndex);
			case SELF_SSC_5:
			case COUNTRY_SSC:
			case CQSSC:
			case JSSSC:
				return matchBall(baseData, valiData, state, numIndex, typeIndex);
			case GDKLC:
			case XYNC:
				return matchHappy(baseData, valiData, state, numIndex, typeIndex);
			default:
				throw new RuntimeException("非法的类型捕捉，类型为" + typeIndex);
		}
	}

	private static boolean matchHappy(String[] baseData, String[] valiData, boolean state, int numIndex, int typeIndex) {
		int baseNum;
		int valiNum;
		int threshold = 10;
		if (numIndex == 8) {
			//总和
			baseNum = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]) + Integer.parseInt(baseData[2])
					+ Integer.parseInt(baseData[3]) + Integer.parseInt(baseData[4]) + Integer.parseInt(baseData[5])
					+ Integer.parseInt(baseData[6]) + Integer.parseInt(baseData[7]);
			valiNum = Integer.parseInt(valiData[0]) + Integer.parseInt(valiData[1]) + Integer.parseInt(valiData[2])
					+ Integer.parseInt(valiData[3]) + Integer.parseInt(valiData[4])+ Integer.parseInt(valiData[5])
					+ Integer.parseInt(valiData[6])+ Integer.parseInt(valiData[7]);
			threshold=84;
		} else if (numIndex < 0 || numIndex > 8) {
			throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
		}else{
			baseNum = Integer.parseInt(baseData[numIndex]);
			valiNum = Integer.parseInt(valiData[numIndex]);
			if(typeIndex==4){
				numIndex=9;
			}
		}
		switch (typeIndex) {
			//大小
			case 0:
				//总和和
				if (threshold == 84 && (baseNum == threshold || valiNum == threshold)) {
					return false;
				}
				return isBoS(state, baseNum, valiNum, threshold);
			//单双
			case 1:
				return isParity(state, baseNum, valiNum);
			//尾大尾小
			case 2:
				return tailSize(baseNum) == (state == tailSize(valiNum));
			//合单合双
			case 3:
				return sumSingle(baseNum) == (state == sumSingle(valiNum));
			//龙虎
			case 4:
				if (numIndex != 9) {
					throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
				}
				int base2Num = Integer.parseInt(baseData[7]);
				int vali2Num = Integer.parseInt(valiData[7]);
				return isDoT(state, baseNum, valiNum, base2Num, vali2Num);
			default:
				throw new RuntimeException("非法的类型捕捉，类型为" + typeIndex);
		}
	}
	/**
	 * 匹配号码
	 * 跟上期双面
	 * @param baseData 基准开奖数据
	 * @param valiData 验证开奖数据
	 * @param state    正投/反投
	 * @param numIndex  排名类型
	 * @param typeIndex 玩法类型
	 * @return 匹配成功
	 */
	private static boolean matchBall(String[] baseData, String[] valiData, boolean state, int numIndex,int typeIndex) {
		int baseNum;
		int valiNum;
		int threshold = 4;
		if (numIndex == 5) {
			//总和
			baseNum = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]) + Integer.parseInt(baseData[2])
					+ Integer.parseInt(baseData[3]) + Integer.parseInt(baseData[4]);
			valiNum = Integer.parseInt(valiData[0]) + Integer.parseInt(valiData[1]) + Integer.parseInt(valiData[2])
					+ Integer.parseInt(valiData[3]) + Integer.parseInt(valiData[4]);
			threshold = 22;
		} else if (numIndex < 0 || numIndex > 5) {
			throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
		} else {
			baseNum = Integer.parseInt(baseData[numIndex]);
			valiNum = Integer.parseInt(valiData[numIndex]);
			if(typeIndex==2){
				numIndex=6;
			}
		}
		switch (typeIndex) {
			//大小
			case 0:
				return isBoS(state, baseNum, valiNum, threshold);
			//单双
			case 1:
				return isParity(state, baseNum, valiNum);
			//龙虎
			case 2:
				if (numIndex != 6) {
					throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
				}
				int base2Num = Integer.parseInt(baseData[4]);
				int vali2Num = Integer.parseInt(valiData[4]);
				//和的暂时不投注
				if(baseNum==base2Num||valiNum==vali2Num){
					return false;
				}
				return isDoT(state, baseNum, valiNum, base2Num, vali2Num);
			default:
				throw new RuntimeException("非法的类型捕捉，类型为" + typeIndex);
		}
	}
	/**
	 * 匹配号码
	 * 跟上期双面
	 * @param baseData  基准开奖数据
	 * @param valiData  验证开奖数据
	 * @param state     正投/反投
	 * @param numIndex  排名类型
	 * @param typeIndex 玩法类型
	 * @return 匹配成功
	 */
	private static boolean matchNumber(String[] baseData, String[] valiData, boolean state, int numIndex, int typeIndex) {
		int baseNum;
		int valiNum;
		int threshold = 5;
		if (numIndex == 10) {
			//冠亚和
			baseNum = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]);
			valiNum = Integer.parseInt(valiData[0]) + Integer.parseInt(valiData[1]);
			threshold = 11;
		} else if (numIndex < 0 || numIndex > 10) {
			throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
		} else {
			baseNum = Integer.parseInt(baseData[numIndex]);
			valiNum = Integer.parseInt(valiData[numIndex]);
		}
		switch (typeIndex) {
			//大小
			case 0:
				return isBoS(state, baseNum, valiNum, threshold);
			//单双
			case 1:
				return isParity(state, baseNum, valiNum);
			//龙虎
			case 2:
				if (numIndex <= 0 || numIndex > 4) {
					throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
				}
				//0-9	0-9   11-9 ___________ 4-1    length-1-index
				int base2Num = Integer.parseInt(baseData[baseData.length - numIndex - 1]);
				int vali2Num = Integer.parseInt(valiData[baseData.length - numIndex - 1]);
				return isDoT(state, baseNum, valiNum, base2Num, vali2Num);
			default:
				throw new RuntimeException("非法的类型捕捉，类型为" + typeIndex);
		}
	}

	/**
	 * 开某投某
	 *
	 * @param open    开奖号码
	 * @param vali    验证开奖号码
	 * @param selects 方案组详情
	 * @return 匹配成功
	 */
	public static boolean matchNum(String open, String vali, JSONArray selects) {
		for (Object object : selects) {
			JSONObject item = (JSONObject) object;
			String select = item.getString("select");
			if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2 || !vali.equals(select)) {
				continue;
			}
			String bets = item.getString("bet");
			if (ContainerTool.isEmpty(bets.split(","))) {
				continue;
			}
			for (String bet : bets.split(",")) {
				if (open.equals(bet)) {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * 是否匹配中---号码追踪
	 *
	 * @param baseData 基准开奖数据
	 * @param valiData 验证开奖数据
	 * @param track    追踪号码
	 * @param bets     投注号码
	 * @return 匹配 中 true
	 */
	public static boolean matchNum(String[] baseData, String[] valiData, String track, String[] bets) {
		//追踪号码在验证数据所在的位置
		int index = ContainerTool.findIndex(valiData, track);
		//基础投注比较数据
		String base = baseData[index];
		for (String bet : bets) {
			//中
			if (bet.equals(base)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 大小匹配
	 *
	 * @param state   正反
	 * @param baseNum 基准号码
	 * @param valiNum 验证号码
	 * @return 匹配大小+单双
	 */
	private static boolean isBoS(boolean state, int baseNum, int valiNum, int i) {
		return (baseNum > i) == (state == (valiNum > i));
	}

	/**
	 * 单双匹配
	 *
	 * @param state   正反
	 * @param baseNum 基准号码
	 * @param valiNum 验证号码
	 * @return 匹配正反+单双
	 */
	private static boolean isParity(boolean state, int baseNum, int valiNum) {
		return (baseNum % 2 != 0) == (state == (valiNum % 2 != 0));
	}
	/**
	 * 尾大 尾小
	 *
	 * @param number 数字
	 * @return > 5 大
	 */
	private static boolean tailSize(Integer number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		number %= 10;
		return number >= 5;
	}
	/**
	 * 合数单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	private static boolean sumSingle(Integer number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		number = number / 10 + number % 10;
		return number % 2 == 0 ;
	}
	/**
	 * 龙虎匹配
	 *
	 * @param state    正反
	 * @param baseNum  基准号码
	 * @param base2Num 基准号码2
	 * @param valiNum  验证号码
	 * @param vali2Num 验证号码2
	 * @return 匹配龙虎+单双
	 */
	private static boolean isDoT(boolean state, int baseNum, int valiNum, int base2Num, int vali2Num) {
		return (baseNum > base2Num) == (state == (valiNum > vali2Num));
	}
	/**
	 * 获取双面的编码 所对应的索引
	 *
	 * @param code 双面的编码 code 0-26
	 * @return 索引
	 */
	public static Integer[] findFollowTwoSide(String code) {
		String[] strs = code.split("_");
		Integer[] results = new Integer[2];
		results[0] = StringTool.getRankCn(strs[0]);
		results[1] = StringTool.getPKTypeCn(strs[1]);
		return results;
	}

	/**
	 * 根据分投获取投注项
	 *
	 * @param splitAmount 分投金额
	 * @param bet         投注项
	 * @param funds       资金
	 * @param limitMin    最低金额
	 * @return 投注项
	 */
	public static String[] getItem(int splitAmount, String bet, double funds, int limitMin) {
		String[] item;
		int len = (int) (funds / splitAmount);
		double surplus = funds % splitAmount;
		String betItem = bet.concat("|").concat(String.valueOf(NumberTool.longValueT(splitAmount)));
		if (surplus == 0) {
			item = new String[len];
			for (int j = 0; j < len; j++) {
				item[j] = betItem;
			}
		} else {
			item = new String[len + 1];
			for (int j = 0; j < len - 1; j++) {
				item[j] = betItem;
			}
			if (surplus > limitMin) {

				item[len - 1] = betItem;
				item[len] = bet.concat("|").concat(String.valueOf(NumberTool.longValueT(surplus)));
			} else {
				//切割后的余数小于最低限额
				item[len - 1] = bet.concat("|")
						.concat(String.valueOf(NumberTool.longValueT(splitAmount + surplus - limitMin)));
				item[len] = bet.concat("|").concat(String.valueOf(NumberTool.longValueT(limitMin)));
			}
		}
		return item;
	}

	/**
	 * 获取数组最大值的索引位
	 * @param arr	数组
	 * @return
	 */
	public static int getMaxIndex(int[] arr) {
		if(arr==null||arr.length==0){
			return 0;
		}
		int maxIndex=0;
		for(int i =0;i<arr.length-1;i++){
			if(arr[maxIndex]==0){
				maxIndex=i+1;
				continue;
			}
			if(arr[i+1]==0){
				continue;
			}
			if(arr[maxIndex]<arr[i+1]){
				maxIndex=i+1;
			}
		}
		return maxIndex;
	}

	/**
	 * 获取数组最小的索引位
	 * @param arr	数组
	 * @return
	 */
	public static int getMinIndex(int[] arr) {
		if(arr==null||arr.length==0){
			return 0;
		}
		int minIndex=0;
		for(int i =0;i<arr.length-1;i++){
			if(arr[minIndex]==0){
				minIndex=i+1;
				continue;
			}
			if(arr[i+1]==0){
				continue;
			}
			if(arr[minIndex]>arr[i+1]){
				minIndex=i+1;
			}
		}
		return minIndex;
	}
}
