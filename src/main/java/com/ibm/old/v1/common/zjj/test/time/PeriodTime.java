package com.ibm.old.v1.common.zjj.test.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodTime {
	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 *
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateString = formatter.format(new Date());
		return dateString;
	}	
	
	public static void main(String[] args) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time1 = "13:04:00";
		// 开奖期数
		String period = "20190216-01";
		String str = period.split("-")[1];
		System.out.println("--------:" + str);

		Date d1 = sdf.parse(time1);

		Date afterDate = new Date(d1.getTime() + (Integer.parseInt(str)) * 5 * 60 * 1000);
		System.out.println("date:" + sdf.format(afterDate));
		System.out.println(afterDate.getTime());
		
		String date = sdf.format(afterDate);
		Date d2 = sdf.parse(date);
		System.out.println("d2:" + d2.getTime());

		Date d3 = sdf.parse(getTimeShort());
		System.out.println("d3:" + d3.getTime());
		
		long diff = d2.getTime() - d3.getTime();
		System.out.println("diff:" + diff);

		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异

		long min = diff % nd % nh / nm;
		System.out.println("min:" + min);

		Date d = new Date();
		System.out.println("d:" + d);

		// Calendar nowTime = Calendar.getInstance();
		// nowTime.add(Calendar.MINUTE, 5);
		// System.out.println(sdf.format(nowTime.getTime()));

	}

	public static String getDatePoor(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		long min = diff % nd % nh / nm;
		return null;
	}

	public static void main1(String[] args) throws Exception {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		String time1 = "13:04:00";

		String time2 = getTimeShort();

		Date d1 = df.parse(time1);
		System.out.println("d1:" + d1);

		long a = d1.getTime() + 5 * 60 * 1000;

		System.out.println(a);

		Date d2 = df.parse(time1);
		System.out.println("d2:" + d2);

		long diff = d1.getTime() - d2.getTime();
		System.out.println("diff:" + diff);

		long min = diff / (1000 * 60);
		System.out.println("时间间隔:" + min + "分钟");

		long period = min / 5;
		System.out.println("period:" + period);

	}
}
