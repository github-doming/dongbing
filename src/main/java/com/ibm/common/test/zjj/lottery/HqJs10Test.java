package com.ibm.common.test.zjj.lottery;

import org.doming.core.tools.DateTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * @Description: HQ极速10
 * @Author: null
 * @Date: 2019-11-15 17:42
 * @Version: v1.0
 */
public class HqJs10Test {

    @Test
    public void test() throws IOException {
        String handicapUrl = "https://1.cc138001.com/kaijiang/jssc.json?v="+System.currentTimeMillis();
        System.out.println(handicapUrl);
        String html=HttpClientTool.doGet(handicapUrl);

        System.out.println(html);



    }
    @Test
    public void test01() throws IOException {

        String date= DateTool.getDate(new Date());

        System.out.println(date);

        String handicapUrl = "https://1.cc138001.com/kaijiang/history/jssc_"+date+".json?v="+System.currentTimeMillis();
        System.out.println(handicapUrl);

        String html=HttpClientTool.doGet(handicapUrl);

        System.out.println(html);
    }
}
