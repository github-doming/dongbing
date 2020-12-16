package com.ibm.common.test.wwj.Ncom2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.test.wwj.handicap.HttpType;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.handicap.config.Ncom2BallConfig;
import com.ibm.common.utils.handicap.config.Ncom2NumberConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/27 10:41
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class Ncom2HandicpTest {

    private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";
    @Test
    public void test2(){
        String account = "a1227012";
        String password = "Aa1231231";
        String handicapUrl = "http://xxt6699.com";
        String handicapCaptcha = "190018";

        HttpClientUtil clientUtil =HttpClientUtil.findInstance();
        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.httpContext(HttpClientContext.create());
        httpConfig.httpClient(clientUtil.createHttpClient());
        httpConfig.setHeader("Content-Type","pplication/x-www-form-urlencoded; charset=UTF-8");

        //
        String na = clientUtil.getHtml(httpConfig.url("http://vip.w1.hahha320.com:99/L_PK10/Html/pk10_lmp.txt"));
        System.out.println(na);
    }
    @Test
    public void test1() throws IOException {
        String account = "a122702";
        String password = "Aa123123";
        String handicapUrl = "http://xxt6699.com";
        String handicapCaptcha = "190018";

        HttpClientUtil clientUtil =HttpClientUtil.findInstance();
        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.httpContext(HttpClientContext.create());
        httpConfig.httpClient(clientUtil.createHttpClient());

        String navigationHtml = clientUtil.getHtml(httpConfig.url(handicapUrl));

//        System.out.println(navigationHtml);
        if (StringTool.isEmpty(navigationHtml) || navigationHtml.contains("百度一下")) {
            System.out.println("获取导航页面失败,结果信息为空");
        }
        String url = handicapUrl.concat("/index.php?s=/Home/Index/navi");
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("code",handicapCaptcha);
        dataMap.put("Submit","登 録");
        // 获取线路
        String routeHtml =  clientUtil.postHtml(httpConfig.url(url).map(dataMap));
        List<String> hrefs =  getRoute(routeHtml);
        String[] routes = routes(httpConfig,hrefs);
        String hostUrl = routes[0];
        // 获取登录页
        String loginHtml = clientUtil.getHtml(httpConfig.url(hostUrl));
//        System.out.println("loginHtml="+loginHtml);

        String content = getVerifyCode(httpConfig,hostUrl,null);


//        System.out.println("ValidateCode=="+ValidateCode);
        String loginUrl = hostUrl.concat("/Handler/LoginHandler.ashx?action=user_login");
        dataMap = new HashMap<>();
        dataMap.put("ValidateCode","");
        dataMap.put("loginName",account);
        dataMap.put("loginPwd",password);
        String login = clientUtil.postHtml(httpConfig.url(loginUrl).map(dataMap));
//        System.out.println("login=="+login);
        JSONObject js = (JSONObject) JSONObject.parse(login);
        System.out.println(js);
        if(login.contains("200")){
            System.out.println("登录成功！");


            // 用户信息
            String userHtml = clientUtil.getHtml(httpConfig.url(hostUrl.concat("/Handler/QueryHandler.ashx")));
            JSONObject userInfo= JSONObject.parseObject(userHtml).getJSONObject("data").getJSONObject("game_2");
            System.out.println("userInfo=="+userInfo);

            url = hostUrl.concat("/CreditInfo.aspx?p=1&id=2");
            String ququ = clientUtil.getHtml(httpConfig.url(url));
            Document document = Jsoup.parse(ququ);
            Elements tableClass = document.getElementsByClass("hover_list");
            JSONArray quota = new JSONArray();
            JSONArray array;
            for (Element table: tableClass){
                Elements trs = table.select("tbody tr");
                for(Element tr:trs){
                    array=new JSONArray();
                    String text=tr.text();
                    if(StringTool.notEmpty(text)){
                        String[] limits=text.split(" ");
                        if(limits.length==6){
                            array.add(Integer.parseInt(limits[3]));
                            array.add(Integer.parseInt(limits[4]));
                            array.add(Integer.parseInt(limits[5]));
                        }else{
                            array.add(Integer.parseInt(limits[2]));
                            array.add(Integer.parseInt(limits[3]));
                            array.add(Integer.parseInt(limits[4]));
                        }
                        quota.add(array);
                    }
                }
            }
            System.out.println(quota);

            Map<String,String> betParameter = getBetParameter(GameUtil.Code.PK10,"ballNO");
            String html = null;
            Map<String, Object> join = new HashMap<>(3);
            join.put("action", "get_oddsinfo");
            join.put("playid",betParameter.get("playid"));
            join.put("playpage",betParameter.get("playpage") );
            String urlOdds = hostUrl.concat("/").concat(betParameter.get("codePath") ).concat("/Handler/Handler.ashx");
            html = HttpClientUtil.findInstance().postHtml(httpConfig.url(urlOdds).map(join));
            if (ContainerTool.isEmpty(html)) {
                System.out.println("获取游戏赔率失败");

            }

        }
    }

    private static Map<String, String> getBetParameter(GameUtil.Code gameCode, String betType) {
        Map<String,String> betParameter = new HashMap<>();
        String playid="",playpage="",codePath="";
        switch (gameCode) {
            case JSSSC:
                playpage = "speed5_";
                codePath = "L_SPEED5";
                break;
            case PK10:
                playpage = "pk10_";
                codePath = "L_PK10";
                break;
            case XYFT:
                playpage = "xyft5_";
                codePath = "L_XYFT5";
                break;
            case CQSSC:
                playpage = "cqsc_";
                codePath = "L_CQSC";
                break;
            case JS10:
                playpage = "jscar_";
                codePath = "L_JSCAR";


        }
        switch (gameCode){
            case PK10:
            case JS10:
            case XYFT:
                switch (betType){
                    case "dobleSides":
                        playpage += "lmp";
                        break;
                    case "ballNO":
                        playpage += "d1_10";
                        break;
                    case "sumDT":
                        playpage += "12";
                        break;
                }
                playid = Ncom2NumberConfig.GAME_ODDS_ID.get(betType);
                break;
            case CQSSC:
            case JSSSC:
                switch (betType){
                    case "dobleSides":
                        playpage += "lmp";
                        break;
                    case "ballNO":
                        playpage += "d1_5";
                        break;
                    case "sumDT":
                        playpage += "d1";
                        break;
                }
                playid = Ncom2BallConfig.GAME_ODDS_ID.get(betType);
                break;
        }
        betParameter.put("playid",playid);
        betParameter.put("playpage",playpage);
        betParameter.put("codePath",codePath);
        return betParameter;

    }

    /**
     * 获取验证码
     *
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param code        验证码地址
     * @return 验证码
     */
    public String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String code){
        String content;
        //获取验证码内容,code等于空时，刷新验证码
        if (StringTool.isEmpty(code)) {
            content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat("/Handler/ValidateImgHandler.ashx?tm=%20") + String.format("%.15f", Math.random())));
        } else {
            content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
        }



        return content;
    }

    /**
     * 根据线路页面解析的到线路并完成测速排序后输出
     *
     * @param httpConfig 请求配置
     * @param hrefs  获取线路
     * @return 测速后的线路数组
     */

    public String[] routes(HttpClientConfig httpConfig, List<String> hrefs){
        String[] routes = new String[hrefs.size()];
        long[] arr = new long[hrefs.size()];
        //判断时间延迟
        long t1, t2;
        for (int i = 0; i < hrefs.size(); i++) {
            t1 = System.currentTimeMillis();
            String url = hrefs.get(i);
            HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
            t2 = System.currentTimeMillis();
            long myTime = t2 - t1;
            routes[i] = url;
            arr[i] = myTime;
        }

        //线路按延时从小到大排序
        for (int x = 0; x < arr.length; x++) {
            for (int j = 0; j < arr.length - x - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    long t = arr[j];
                    String route = routes[j];

                    arr[j] = arr[j + 1];
                    routes[j] = routes[j + 1];

                    arr[j + 1] = t;
                    routes[j + 1] = route;
                }
            }
        }
        return routes;
    }

    /**
     * 解析线路
     *
     * @param routeHtml 代理页面
     * @return 代理线路
     */
    public  List<String> getRoute(String routeHtml) {
        Document routeDocument = Jsoup.parse(routeHtml);

        Elements trs = routeDocument.getElementsByClass("left1");

        List<String> hrefs = new ArrayList<>();

        for (Element tr : trs) {
            String type = tr.getElementsByClass("title").text();
            System.out.println(type);
            if (StringTool.isContains(type, "會員")) {
                System.out.println("會員");
                Elements lis = tr.select("li");
                for (Element li : lis){
                    String href = li.select("a").attr("href");
                    System.out.println(href);
                    hrefs.add(href);
                }

            }
        }
        return hrefs;
    }


}
