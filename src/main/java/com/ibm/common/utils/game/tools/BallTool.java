package com.ibm.common.utils.game.tools;

/**
 * @Description: 球类游戏工具类
 * @Author: Dongming
 * @Date: 2019-09-17 11:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BallTool {

	//region 翻译区
	/**
     * 获取大小
     *
     * @param number 数字
     * @return > 5 大
     */
    public static String size(int number) {
        if (number < 1 || number > 10) {
			throw new RuntimeException("错误数值装换:" + number);
        }
        return number <= 5 ? "小" : "大";
    }

    /**
     * 获取大小
     *
     * @param number 数字
     * @return > 5 BIG
     */
    public static String sizeEn(int number) {
		return "小".equals(size(number)) ? "SMALL" : "BIG";
    }

    /**
     * 获取大小
     *
     * @param number 数字
     * @return > 11 大
     */
    public static String sizeChampionSum(int number) {
        if (number < 3 || number > 19) {
			throw new RuntimeException("错误数值装换:" + number);
        }
        return number <= 11 ? "小" : "大";
    }

    /**
     * 获取大小
     *
     * @param number 数字
     * @return > 11 BIG
     */
    public static String sizeChampionSumEn(int number) {
        if (number < 3 || number > 19) {
			throw new RuntimeException("错误数值装换:" + number);
        }
        return number <= 11 ? "SMALL" : "BIG";
    }

    /**
     * 获取单双
     *
     * @param number 数字
     * @return 奇数 单
     */
    public static String single(int number) {
        if (number < 1 || number > 10) {
            throw new RuntimeException("错误数值装换:" + number);
        }
        return number % 2 == 0 ? "双" : "单";
    }

    /**
     * 获取单双
     *
     * @param number 数字
     * @return 奇数 SINGLE
     */
    public static Object singleEn(int number) {
        return "双".equals(single(number)) ? "DOUBLE" : "SINGLE";
    }

    /**
     * 获取单双
     *
     * @param number 数字
     * @return 奇数 单
     */
    public static String singleChampionSum(int number) {
        if (number < 3 || number > 19) {
			throw new RuntimeException("错误数值装换:" + number);
        }
        return number % 2 == 0 ? "双" : "单";
    }

    /**
     * 获取单双
     *
     * @param number 数字
     * @return 奇数 SINGLE
     */
    public static String singleChampionSumEn(int number) {
        if (number < 3 || number > 19) {
			throw new RuntimeException("错误数值装换:" + number);
        }
        return number % 2 == 0 ? "DOUBLE" : "SINGLE";
    }

    /**
     * 获取龙虎
     *
     * @param number1 数字1
     * @param number2 数字2
     * @return 数字1>数字2 龙
     */
    public static Object dragon(Integer number1, Integer number2) {
        if (number1 < 1 || number1 > 10 || number2 < 1 || number2 > 10) {
			throw new RuntimeException("错误数值装换:" + number1+","+number2);
        }
        return number1 > number2 ? "龙" : "虎";
    }

    /**
     * 获取龙虎
     *
     * @param number1 数字1
     * @param number2 数字2
     * @return 数字1>数字2 龙
     */
    public static Object dragonEn(Integer number1, Integer number2) {
        if (number1 < 1 || number1 > 10 || number2 < 1 || number2 > 10) {
			throw new RuntimeException("错误数值装换:" + number1+","+number2);
        }
        return number1 > number2 ? "DRAGON" : "TIGER";
    }

    //endregion

}
