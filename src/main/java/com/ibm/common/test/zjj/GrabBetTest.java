package com.ibm.common.test.zjj;

import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.game.GameUtil;
import org.doming.core.tools.StringTool;
import org.junit.Test;

/**
 * @Description:
 * @Author: null
 * @Date: 2019-09-29 15:50
 * @Version: v1.0
 */
public class GrabBetTest {
	private static int followFundsTh;

	public static void main(String[] args) {
		double[][] fundsMatrix = GameTool.getFundsMatrix(GameUtil.Code.PK10);
		//第一名|大
		fundsMatrix[0][19] += 10.0;
		//第一名|小
		fundsMatrix[0][20] += 10.0;
		//第二名|大
		fundsMatrix[1][19] += 10.0;
		//第一名|小
		fundsMatrix[1][20] += 10.0;
		//第二名|单
		fundsMatrix[1][21] += 10.0;
		//第一名|双
		fundsMatrix[1][22] += 10.0;
		//冠亚|大
		fundsMatrix[10][19] += 10.0;
		//冠亚|小
		fundsMatrix[10][20] += 10.0;
		//冠亚|单
		fundsMatrix[10][21] += 20.0;
		//冠亚|双
		fundsMatrix[10][22] += 20.0;
		//第二名|虎
		fundsMatrix[1][24] += 10.0;
		//第二名|龙
		fundsMatrix[1][23] += 10.0;

		followFundsTh = 0;
		double[][][] twoSideMatrix = GameTool.getTwoSideMatrix(GameUtil.Code.PK10, fundsMatrix);

		String followContent = getTwoSideItem(GameUtil.Code.PK10, null, twoSideMatrix, 1.0, 2000, 1000000);

		//其他 - 根据游戏设置进行投注项处理
		followContent += getOtherItem(GameUtil.Code.PK10, null, fundsMatrix, 1.0, 2000, 1000000);

		System.out.println(followContent);
		System.out.println(followFundsTh);
	}
	/**
	 * 获取双面投注项
	 *
	 * @param gameCode       游戏编码
	 * @param filterProjects 过滤项目
	 * @param twoSideMatrix  双面资金矩阵
	 * @param leastAmountTh  最低金额
	 * @param mostAmountTh   最高金额
	 * @param multiple       跟投倍数
	 * @return 投注项
	 */
	private static String getTwoSideItem(GameUtil.Code gameCode, String[] filterProjects, double[][][] twoSideMatrix,
			double multiple, int leastAmountTh, int mostAmountTh) {
		String betItem = "";
		for (int i = 0, len2 = twoSideMatrix[0].length, len3 = twoSideMatrix[0][0].length;
			 i < twoSideMatrix.length; i++) {
			for (int j = 0; j < len2; j++) {
				for (int k = 0; k < len3; k++) {
					int fundsTh = (int) (twoSideMatrix[i][j][k] * multiple * 1000);
					if (fundsTh > leastAmountTh) {
						if (fundsTh > mostAmountTh) {
							fundsTh = mostAmountTh;
						}
						//旧：(gameCode, j, 10 + 2 * i + k);
						String item = GameTool.itemStr(gameCode, j, 19 + 2 * i + k);
						if (StringTool.contains(filterProjects, item)) {
							continue;
						}
						followFundsTh += fundsTh;
						betItem = betItem.concat(item).concat("|").concat(Integer.toString(fundsTh)).concat("#");
					}
				}
			}
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
	 * @param multiple       跟投倍数
	 * @return 投注项
	 */
	private static String getOtherItem(GameUtil.Code gameCode, String[] filterProjects, double[][] fundsMatrix,
			double multiple, int leastAmountTh, int mostAmountTh) {
		double[] otherMatrix = GameTool.getNumOtherItem( fundsMatrix);
		String betItem = "";
		for (int i = 0; i < otherMatrix.length; i++) {
			int fundsTh = (int) (otherMatrix[i] * multiple * 1000);
			if (fundsTh > leastAmountTh) {
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
		}
		return betItem;

	}

    @Test
    public void test(){
	    //获取到的是未结算的摘要信息
        //1,没信息，说明上一期的已经结算了，历史数据(投注额，投注数，注单号，期数)都没有用了，可以清除历史的信息


        //2,有信息
        //(1),没历史数据，放入刚抓取的数据信息
        //(2),有历史数据，期数不一样，则清理之前的数据，返回空信息，，，期数相同，


    }
}
