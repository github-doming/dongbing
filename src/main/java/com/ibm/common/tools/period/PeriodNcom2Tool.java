package com.ibm.common.tools.period;

import com.ibm.common.tools.PeriodTool;
import org.doming.core.tools.DateTool;

import java.util.Date;

/**
 * @Description: HQ盘口期数工具类
 * @Author: null
 * @Date: 2019-11-15 18:03
 * @Version: v1.0
 */
public class PeriodNcom2Tool extends PeriodTool {

    private static final String JS_DATA_START = "00:01:05";
    public static final long JS_PERIOD = 75 * 1000L;
    public static final long HOUR_TIME = 60*60*1000L;
    public static final String JS_START = "07:00:00";
    public static final String JS_END = "04:01:00";


    //TODO JS10
    //JS10区域

    /**
     * 获取急速赛车的期数
     *
     * @return 下一次开奖的期数
     */
    public static String findJs10Period() {
        return findJs10Period(System.currentTimeMillis() + JS_PERIOD);
    }

    /**
     * 获取急速赛车已开奖的期数
     *
     * @return 已开奖的期数
     */
    public static String findLotteryJs10Period() {
        return findLotteryJs10Period(System.currentTimeMillis());
    }

    private static String findJs10Period(long dateTime) {
        return findLotteryJs10Period(dateTime);
    }


    private static String findLotteryJs10Period(long dateTime) {
        if (dateTime > DateTool.getHm(JS_START).getTime()) {
            dateTime -= 3 * HOUR_TIME;
        }
        long period = DateTool.getPeriod(dateTime, DateTool.getHms(JS_DATA_START).getTime(), JS_PERIOD) + 1;
        return DateTool.getDay(new Date(dateTime)).concat(String.format("%04d", period));
    }

    /**
     * 获取急速赛车已开奖的开奖时间戳
     *
     * @return 已开奖的开奖时间戳
     */
    public static long getJs10DrawTime() {
        return getJs10DrawTime(findJs10Period());
    }

    /**
     * 获取急速赛车已开奖的开奖时间戳
     *
     * @param period 期数
     * @return 已开奖的开奖时间戳
     */
    public static long getJs10DrawTime(String period) {
        long diffTime = 0l;
        if (System.currentTimeMillis() > DateTool.getHm(JS_START).getTime()) {
            diffTime = 3*HOUR_TIME;
        }
        return (Integer.parseInt(period.substring(8)) - 1) * JS_PERIOD + DateTool.getTime(JS_DATA_START).getTime()+diffTime;
    }

    /**
     * 获取JS10已开奖的时间戳
     *
     * @return 已开奖的开奖时间戳
     */
    public static long getLotteryJs10DrawTime() {
        return getJs10DrawTime(findLotteryJs10Period());
    }

    //JS10区域


    public static void main(String[] args) {

        System.out.println(findJssscPeriod());
        System.out.println(findLotteryJssscPeriod());
        System.out.println(getLotteryJssscDrawTime());
    }

    //TODO JSSSC
    //JSSSC 区域


    /**
     * 获取急速时时彩的期数
     *
     * @return 下一次开奖的期数
     */
    public static String findJssscPeriod() {
        return findJssscPeriod(System.currentTimeMillis() + JS_PERIOD);
    }

    /**
     * 获取急速时时彩已开奖的期数
     *
     * @return 已开奖的期数
     */
    public static String findLotteryJssscPeriod() {
        return findLotteryJssscPeriod(System.currentTimeMillis());
    }

    private static String findJssscPeriod(long dateTime) {
        return findLotteryJssscPeriod(dateTime);
    }

    private static String findLotteryJssscPeriod(long dateTime) {
        if (dateTime > DateTool.getHm(JS_START).getTime()) {
            dateTime -= 3 * HOUR_TIME;
        }
        long period = DateTool.getPeriod(dateTime, DateTool.getHms(JS_DATA_START).getTime(), JS_PERIOD) + 1;
        return DateTool.getDay(new Date(dateTime)).concat(String.format("%04d", period));
    }

    /**
     * 获取急速时时彩下一期的开奖时间戳
     *
     * @return 已开奖的开奖时间戳
     */
    public static long getJssscDrawTime() {
        return getJssscDrawTime(findJssscPeriod());
    }

    /**
     * 获取急速赛车已开奖的开奖时间戳
     *
     * @param period 期数
     * @return 已开奖的开奖时间戳
     */
    public static long getJssscDrawTime(String period) {
        long diffTime = 0L;
        if (System.currentTimeMillis() > DateTool.getHm(JS_START).getTime()) {
            diffTime = 3*HOUR_TIME;
        }
        return (Integer.parseInt(period.substring(8)) - 1) * JS_PERIOD + DateTool.getTime(JS_DATA_START).getTime()+diffTime;
    }

    /**
     * 获取JSSSC已开奖的时间戳
     *
     * @return 已开奖的开奖时间戳
     */
    public static long getLotteryJssscDrawTime() {
        return getJssscDrawTime(findLotteryJssscPeriod());
    }
}
