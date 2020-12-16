package com.ibm.common.test.zjj.handicap.member;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.member.ComMemberTool;
import com.ibm.common.utils.http.utils.entity.MemberUserInfo;
import com.ibm.common.utils.http.utils.member.ComMemberUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-04-18 13:49
 * @Version: v1.0
 */
public class ComMemberTest {

    @Test
    public void test02() throws InterruptedException {
        String id = "123";

        String memberAccount = "azxcc001";
        String memberPassword = "Aa123123.";
        String handicapUrl = "http://859116.com/";
        String handicapCaptcha = "337942";

        ComMemberUtil memberUtil=ComMemberUtil.findInstance();

        GameUtil.Code gameCode= GameUtil.Code.GDKLC;
        String[] betItem = {"第一球|东|2000", "第二球|南|2000","第三球|南|2000"};
        List<String> betItems=new ArrayList<>(Arrays.asList(betItem));


        JsonResultBeanPlus bean=memberUtil.login(id,memberAccount,memberPassword,handicapUrl,handicapCaptcha);
        System.out.println("登录结果="+bean.toJsonString());

//		bean= memberUtil.getQuotaList(id,gameCode);
//		System.out.println("限额信息="+bean.toJsonString());


        MemberUserInfo userMap=memberUtil.getUserInfo(id,true);
        System.out.println("用户信息="+userMap.toString());

        //获取赔率信息
        memberUtil.getOddsInfo(id, gameCode, "sumDT");
        String roundno = PeriodUtil.getHandicapGamePeriod(HandicapUtil.Code.COM, gameCode,gameCode.getGameFactory().period(HandicapUtil.Code.COM).findPeriod());

        //投注2020050728
        bean = memberUtil.betting(id, gameCode, roundno, betItems, "sumDT");
        System.out.println("投注结果="+bean.toJsonString());
    }

    @Test
    public void test() throws InterruptedException {
        String id = "123";

        String memberAccount = "cfdda00";
        String memberPassword = "Aa123123.";
        String handicapUrl = "http://863126.com/";
        String handicapCaptcha = "782495";

        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.socketTimeout(2000);
        httpConfig.httpContext(HttpClientContext.create());
        httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());


        //获取线路页面
        String routeHtml= ComMemberTool.getSelectRoutePage(httpConfig,handicapUrl,handicapCaptcha);
        if(StringTool.isEmpty(routeHtml)){
            return ;
        }
        //会员线路数组
        String[] routes =  ComMemberTool.getMemberRoute(routeHtml);
        httpConfig.httpContext().getCookieStore().clear();
        //选择登录线路
        String loginSrc =ComMemberTool.getLoginHtml(httpConfig,routes);
        System.out.println(loginSrc);
        if(StringTool.isEmpty(loginSrc)){
            return ;
        }
        //登录页面   /Handler/LoginHandler.ashx?action=user_login
        String loginInfo = ComMemberTool.getLogin(httpConfig,memberAccount,memberPassword,loginSrc);
        System.out.println(loginInfo);

        //            url=projectHost.concat("/LoginValidate.aspx");
//
//            loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(url));
//            if (StringTool.isEmpty(loginInfo) || !StringTool.isContains(loginInfo, "用戶協議與規則")) {
//                log.error("登录失败，错误的页面=" + loginInfo);
//                Thread.sleep(2 * SLEEP);
//                return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
//            }


    }

    public static void main(String[] args) {
        String html="var lastData = {};\n"
                + "\tvar ajaxErrorLogSwitch = 'true';//前端错误日志记录开关\n"
                + "    var browserCode='2798';\n"
                + "\tvar topDialog = null;\n"
                + "\t// 六合彩可用额度\n"
                + "\tvar sixUsableCredit = 0;";


        String browserCode=StringUtils.substringBetween(html,"var broserCode='","';");

        System.out.println(browserCode);
    }
}
