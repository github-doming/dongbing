package com.cloud.lottery;


import com.cloud.common.game.*;

/**
 * @Description: 期数工具类
 * @Author: Dongming
 * @Date: 2019-01-19 10:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class PeriodTool {

	public static final Pk10 PK10 = new Pk10();
	public static final Xyft XYFT = new Xyft();
	public static final Cqssc CQSSC = new Cqssc();
	public static final Gdklc GDKLC = new Gdklc();
	public static final Xync XYNC = new Xync();

	//region 工具方法

	/**
	 * 比对期数
	 *
	 * @param period     原期数
	 * @param valiPeriod 校对期数
	 * @return 相同true
	 */
	public static boolean equals(Object period, Object valiPeriod) {
		return (Integer.parseInt(period.toString()) - Integer.parseInt(valiPeriod.toString())) == 0;
	}
	//endregion
}
