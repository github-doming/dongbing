package com.ibm.old.v1.common.zjj.test.time;

import org.doming.core.tools.DateTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;

import java.text.ParseException;
import java.util.Date;

public class PeriodTimeThree {
	public static void main(String[] args) throws ParseException {
		String period=PeriodTool.findXYFTPeriod();
		String number = period.split("-")[1];
		
		String time = "13:04:30";
		Date d1 =DateTool.getTime(time);
		
		long drawTime=(DateTool.getTime("13:04:00").getTime() + Integer.parseInt(number) * 5 * 60 * 1000);

		long diff = drawTime - d1.getTime();
		
//		System.out.println(DateTool.timeSec(diff));
		
		System.out.println(new Date());
		System.out.println(PeriodTool.getXYFTDrawTime());
	}
}
