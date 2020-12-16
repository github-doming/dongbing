package com.ibm.old.v1.common.zjj.test.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodTimeTwo {
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time1 = "12:00:00";
		Date d1 = sdf.parse(time1);

		String dateString = sdf.format(new Date());
		Date d2 = sdf.parse(dateString);

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获得两个时间的毫秒时间差异

		long diff = d1.getTime() - d2.getTime();
		System.out.println(diff);
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		System.out.println("min:" + min);
		// 计算差多少秒//输出结果
		long sec = diff % nd % nh % nm / ns;
		System.out.println("sec:" + sec);

		long s = diff / 1000;
		System.out.println(s);
	}
	

}
