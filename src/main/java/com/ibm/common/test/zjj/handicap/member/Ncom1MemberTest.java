package com.ibm.common.test.zjj.handicap.member;


import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.member.Ncom1MemberTool;
import com.ibm.common.utils.http.utils.member2.Ncom1MemberUtil2;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: FG会员
 * @Author: null
 * @Date: 2019-12-26 15:38
 * @Version: v1.0
 */
public class Ncom1MemberTest {

    @Test
    public void test02(){
        String id = "123";

        String memberAccount = "zj12302";
        String memberPassword = "Aa123123.";
        String handicapUrl = "http://fg58.vip/";
        String handicapCaptcha = "956133";

        GameUtil.Code gameCode= GameUtil.Code.PK10;
        String[] betItem = {"第一名|6|2000", "第一名|7|2000", "第一名|8|2000"};
        List<String> betItems=new ArrayList<>(Arrays.asList(betItem));

        Ncom1MemberUtil2 memberUtil=Ncom1MemberUtil2.findInstance();

        JsonResultBeanPlus bean= memberUtil.login(id,memberAccount,memberPassword,handicapUrl,handicapCaptcha);
        System.out.println("登录结果="+bean.toJsonString());

        Map<String, String> userMap=memberUtil.getUserInfo(id,null,null,null,true);
        System.out.println("用户信息="+userMap);
		Object period=gameCode.getGameFactory().period(HandicapUtil.Code.NCOM1).findPeriod();
        String roundno = PeriodUtil.getHandicapGamePeriod(HandicapUtil.Code.NCOM1, gameCode,period);
        //获取赔率信息
        memberUtil.getOddsInfo(id, gameCode,roundno, "ballNO");
        //投注
        memberUtil.betting(id,gameCode,roundno,betItems,"ballNO");
    }

    @Test
    public void test() throws InterruptedException {
        String id = "123";

        String memberAccount = "zj12301";
        String memberPassword = "Aa123123.";
        String handicapUrl = "https://fg58.vip/";
        String handicapCaptcha = "956133";

        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.httpContext(HttpClientContext.create());
        httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

        //获取线路页面
        String routeHtml=Ncom1MemberTool.getSelectRoutePage(httpConfig,handicapUrl,handicapCaptcha);
        if(StringTool.isEmpty(routeHtml)){
            return ;
        }
        //4条会员线路数组
        String[] routes = Ncom1MemberTool.getMemberRoute(httpConfig,routeHtml);

        //选择登录线路
        String loginSrc =Ncom1MemberTool.getLoginHtml(httpConfig,routes);
        System.out.println(loginSrc);
        if(StringTool.isEmpty(loginSrc)){
            return ;
        }

        //登录页面
        String loginInfo = Ncom1MemberTool.getLogin(httpConfig,memberAccount,memberPassword,loginSrc);
        System.out.println(loginInfo);

        //获取用户金额：https://mem1.1188fg.com/index/info?_=1577351293691

        //获取游戏赔率信息 https://mem1.1188fg.com/order/info/
    }
}
