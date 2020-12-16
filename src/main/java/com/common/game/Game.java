package com.common.game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 游戏基础类
 *
 * @Author: Dongming
 * @Date: 2020-05-13 11:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface Game {
	Logger log = LogManager.getLogger(Game.class);

	//region 翻译区

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @param state  正投还是反投
	 * @return 大 小
	 */
	String size(int number, boolean state);



	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @param state  正投还是反投
	 * @return 单 双
	 */
	String single(int number, boolean state);



	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 数字1>数字2 龙
	 */
	String dragon(int number1, int number2, boolean state);


	//endregion

	//region 编码区

	/**
	 * 获取游戏 数字排名
	 *
	 * @param rankStr 字符类型
	 * @return 数字排名
	 */
	Integer rank(String rankStr);

	/**
	 * 获取游戏 数字类型
	 *
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	Integer type(String typeStr);

	/**
	 * 获取游戏 数字类型
	 *
	 * @param typeStr 字符类型
	 * @param rank    数字排名
	 * @return 数字类型
	 */
	Integer type(Integer rank, String typeStr);

	/**
	 * 获取字符排名
	 *
	 * @param rank 数字排名
	 * @return 字符排名
	 */
	String rankStr(Integer rank);

	/**
	 * 获取字符类型
	 *
	 * @param rank 数字类型
	 * @return 字符类型
	 */
	String typeStr(Integer rank);

	/**
	 * 根据JSON获取投注编码
	 *
	 * @param contentJson 投注正文字符串
	 * @return 投注编码
	 */
	Map<Integer, Set<Integer>> getBetCodeByJson(String contentJson);

	/**
	 * 根据获取排名和类型获取投注项
	 *
	 * @param rank  数字排名
	 * @param type  数字类型
	 * @param funds 投注金额
	 * @return 投注项
	 */
	String itemStr(int rank, int type, int funds);

	//endregion

	//region 合并区

	/**
	 * 合并号码类投注项详情信息
	 *
	 * @param betInfo 投注资金信息
	 * @param betRate 投注比例
	 * @return 投注详情信息
	 */
	List<Object> mergeItem(Map<String, int[][]> betInfo, double betRate);

	/**
	 * 放入投注资金
	 *
	 * @param betInfo     投注资金信息
	 * @param betContents 投注正文
	 */
	void putBetInfo(Map<String, int[][]> betInfo, String[] betContents);

	/**
	 * 合并号码类投注项资金信息
	 *
	 * @param betInfo 投注资金信息
	 */
	void mergeInfo(Map<String, int[][]> betInfo);

	/**
	 * 冠亚或者总和大小
	 * @param number	总和/冠亚和
	 * @param state	正投反投
	 * @return
	 */
	String sumSize(int number, boolean state);

	/**
	 * 获取尾大尾小
	 * @param number	号码
	 * @param state	正投反投
	 * @return
	 */
	String tailSize(int number, boolean state);

	/**
	 * 合单合双
	 * @param number	号码
	 * @param state	正投反投
	 * @return
	 */
	String sumSingle(int number, boolean state);
	/**
	 * 获取名次最小值
	 *
	 * @return
	 */
	int getRankMin();

	/**
	 * 获取名次最大值
	 * @return
	 */
	int getRankMax();

	/**
	 * 获取开奖号码最小值
	 * @return
	 */
	int getDrawNumberMin();

	/**
	 * 获取开奖号码最大值
	 * @return
	 */
	int getDrawNumberMax();


	//endregion
}
