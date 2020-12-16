package com.ibm.old.v1.common.doming.test;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.LogTool;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @Description: 以下默认时间日期显示方式都是汉语语言方式
 * 一般语言就默认汉语就可以了，时间日期的格式默认为MEDIUM风格，比如：2008-6-16 20:54:53
 * 以下显示的日期时间都是再Date类的基础上的来的，还可以利用Calendar类来实现见类TestDate2.java
 * @Author: Dongming
 * @Date: 2018-12-07 11:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DateTest {

	@Test public void test01() {
		Date now = new Date();
		//此方法显示的结果和Calendar.getInstance().getTime()一样
		System.out.println(LogTool.printHashtagM());
		System.out.println("用Date方式显示时间: " + now);
		System.out.println(LogTool.printHashtagM());

		//默认语言（汉语）下的默认风格（MEDIUM风格，比如：2008-6-16 20:54:53）
		DateFormat d1 = DateFormat.getDateInstance();
		String str1 = d1.format(now);
		System.out.println("用DateFormat.getDateInstance()格式化时间后为：" + str1);
		System.out.println(LogTool.printHashtagM());

		DateFormat d2 = DateFormat.getDateTimeInstance();
		String str2 = d2.format(now);
		System.out.println("用DateFormat.getDateTimeInstance()格式化时间后为：" + str2);
		System.out.println(LogTool.printHashtagM());

		DateFormat d3 = DateFormat.getTimeInstance();
		String str3 = d3.format(now);
		System.out.println("用DateFormat.getTimeInstance()格式化时间后为：" + str3);
		System.out.println(LogTool.printHashtagM());

		//使用SHORT风格显示日期和时间
		DateFormat d4 = DateFormat.getInstance();
		String str4 = d4.format(now);
		System.out.println("用DateFormat.getInstance()格式化时间后为：" + str4);
		System.out.println(LogTool.printHashtagM());

		//显示日期，周，时间（精确到秒）
		DateFormat d5 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		String str5 = d5.format(now);
		System.out.println("用DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL)格式化时间后为：" + str5);
		System.out.println(LogTool.printHashtagM());

		//显示日期。时间（精确到秒）
		DateFormat d6 = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		String str6 = d6.format(now);
		System.out.println("用DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG)格式化时间后为：" + str6);
		System.out.println(LogTool.printHashtagM());

		//显示日期，时间（精确到分）
		DateFormat d7 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		String str7 = d7.format(now);
		System.out.println("用DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT)格式化时间后为：" + str7);
		System.out.println(LogTool.printHashtagM());

		//与SHORT风格相比，这种方式最好用
		DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		String str8 = d8.format(now);
		System.out.println("用DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM)格式化时间后为：" + str8);
		System.out.println(LogTool.printHashtagM());
	}


	@Test public void test02() throws ParseException {
		String time = "12:16:59";
		System.out.println(DateTool.getTime(time));
	}

	@Test public void test03() throws ParseException {
		String date1 = "2019-02-01";
		String date2 = "2019-01-01";
		long time = DateTool.getDate(date1).getTime();
		System.out.println(time);

		String str =Long.toString(time,36);
		System.out.println(str);
		System.out.println(Long.parseLong(str,36));
		System.out.println(Long.toString(time).substring(2,11));
		System.out.println(DateTool.getDate(date2).getTime());
	}

	@Test public void test04() throws ParseException {
		Date testDate = DateTool.get("2019-1-17 23:56:59");
		System.out.println(PeriodTool.findPK10Period(testDate));

	}

	@Test public void test(){

		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd EEEE");
		String currSun = dateFm.format(new Date());
		System.out.println(currSun);
	}





}
