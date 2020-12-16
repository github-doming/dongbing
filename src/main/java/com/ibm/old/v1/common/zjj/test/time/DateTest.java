package com.ibm.old.v1.common.zjj.test.time;

import org.doming.core.tools.DateTool;

import java.text.ParseException;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) throws ParseException {
		Date date = new Date();
		//60秒
		long time = 60*1000;
		//60秒后的时间
		Date afterDate = new Date(date.getTime() + time);
		//60秒前的时间
		Date beforeDate = new Date(date.getTime() - time);

		System.out.println(date);
		System.out.println(afterDate);
		System.out.println(beforeDate);
		//开始天
		String startDay="2019-04-22";
		//结束天
		String endDay="2019-04-23";
		//开始时间
		String startTime="09:30";
		//结束时间
		String endTime="16:30";

		System.out.println(DateTool.getDate(startDay));
		System.out.println(DateTool.getDate(endDay));

		DateTool.getDaysBetween(DateTool.getDate(startDay),DateTool.getDate(endDay));

		System.out.println(DateTool.getDaysBetween(DateTool.getDate(startDay),DateTool.getDate(endDay)));

		startTime=startDay.concat(" ").concat(startTime);
		System.out.println(startTime);

		endTime=endDay.concat(" ").concat(endTime);
		System.out.println(startTime);

		Date start=DateTool.getMinute(startTime);
		Date end=DateTool.getMinute(endTime);

		System.out.println(start);
		System.out.println(end);
	}

}
