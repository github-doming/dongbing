package com.ibm.common.utils.http.utils.member2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.Ncom2Config;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.Ncom2MemberTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/27 10:50
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class Ncom2MemberUtil2 extends BaseMemberUtil2 {

    protected Logger log = LogManager.getLogger(this.getClass());

    private static volatile Ncom2MemberUtil2 instance = null;

    public static Ncom2MemberUtil2 findInstance() {
        if (instance == null) {
            synchronized (Ncom2MemberUtil2.class) {
                if (instance == null) {
                    Ncom2MemberUtil2 instance = new Ncom2MemberUtil2();
                    // 初始化
                    instance.init();
                    Ncom2MemberUtil2.instance = instance;
                }
            }
        }
        return instance;
    }

    /**
     * 销毁工厂
     */
    public static void destroy() {
        if (instance == null) {
            return;
        }
        if (ContainerTool.notEmpty(instance.hcConfigMap)) {
            for (HttpClientConfig clientConfig : instance.hcConfigMap.values()) {
                clientConfig.destroy();
            }
        }
        instance.userMap = null;
        instance.accountMap = null;
        instance.memberMap = null;
        instance.oddsMap = null;
        instance.checkMap = null;
        instance = null;
    }

    /**
     * 登陆
     *
     * @param existHmId       盘口会员存在id
     * @param memberAccount   会员账号
     * @param memberPassword  会员密码
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     */
    @Override
    public JsonResultBeanPlus login(String existHmId, String memberAccount, String memberPassword, String handicapUrl,
									String handicapCaptcha) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //已存在数据
        if (memberMap.containsKey(existHmId)) {
            bean.setData(memberMap.get(existHmId));
            bean.success();
            return bean;
        }
        accountInfo(existHmId,memberAccount,memberPassword,handicapUrl,handicapCaptcha);
        try {
            //获取配置类
            HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);
            httpConfig.httpTimeOut(10 * 1000);
            bean = login(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
            if (!bean.isSuccess()) {
                return bean;
            }
            Map<String, String> data = (Map<String, String>) bean.getData();

            Map<String, String> hostMap = new HashMap<>(1);
            hostMap.put("projectHost", data.remove("projectHost"));

            userMap.put(existHmId, data);
            memberMap.put(existHmId, hostMap);
        } catch (Exception e) {
            log.error("Ncom2盘口会员【" + existHmId + "】登录失败,失败原因为：", e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 登录
     *
     * @param httpConfig      请求配置类
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @param memberAccount   账号
     * @param memberPassword  密码
     * @return 登录
     */
    public JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
                                    String memberAccount, String memberPassword) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        httpConfig.headers(null);
        httpConfig.httpContext(null);
        try {
            //获取线路选择页面
            String routeHtml = Ncom2MemberTool.getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha);
            if (StringTool.isEmpty(routeHtml) ) {
                log.info("Ncom2获取线路页面失败=" + routeHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
//            String[] routes = {"http://vip.w1.hahha320.com:99","http://vip.w2.hahha320.com:99","http://vip.w3.hahha320.com:99","http://vip.w6.hahha320.com:99"};
            //5条会员线路数组
            String[] routes = Ncom2MemberTool.getMemberRoute(routeHtml);
           //获取登录信息map
            String loginSrc = Ncom2MemberTool.getLoginHtml(httpConfig, routes);
            if (ContainerTool.isEmpty(loginSrc)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            //盘口协议页面
            JSONObject loginInfo = Ncom2MemberTool.getLogin(httpConfig, memberAccount, memberPassword, loginSrc);
            if (StringTool.isEmpty(loginInfo)) {
                bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }

            //错误处理和是否等一次登录盘口
            String msg = loginInfo.getString("tipinfo");
            if(StringTool.notEmpty(msg) &&StringTool.contains( msg,"帳號或者密碼錯誤","帳號已經停用","重置密碼")){
                bean.putEnum(Ncom2MemberTool.loginError(msg));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            //获取用户信息
            Map<String, String> userData = new HashMap<>(2);
            userData.put("projectHost", loginSrc);
            userData.put("memberAccount", memberAccount);

            bean.setData(userData);
            bean.success();
        } catch (Exception e) {
            log.error("Ncom2盘口账号【" + memberAccount + "】登录失败,失败原因为：", e);
            bean.error(e.getMessage());
        } finally {
            httpConfig.defTimeOut();
        }
        return bean;
    }

    /**
     * 校验登录
     *
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @param memberAccount   盘口账号
     * @param memberPassword  盘口密码
     * @return 校验登录结果
     */
    @Override
    public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String memberAccount,
                                        String memberPassword) {
        // 获取配置类
        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
        JsonResultBeanPlus bean = login(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
        if (bean.isSuccess()) {
            String existHmId = RandomTool.getNumLetter32();
            //存储账号信息
            Map<String, String> account = new HashMap<>(4);
            account.put("memberAccount", memberAccount);
            account.put("memberPassword", memberPassword);
            account.put("handicapUrl", handicapUrl);
            account.put("handicapCaptcha", handicapCaptcha);
            accountMap.put(existHmId, account);

            //存储爬虫信息
            Map<String, String> data = (Map<String, String>) bean.getData();
            Map<String, String> hostMap = new HashMap<>(1);
            hostMap.put("projectHost", data.remove("projectHost"));

            userMap.put(existHmId, data);
            memberMap.put(existHmId, hostMap);

            hcConfigMap.put(existHmId, httpConfig);
            bean.setData(existHmId);
        }
        return bean;
    }

    /**
     * 登陆
     *
     * @param existHmId 盘口会员存在id
     */
    public JsonResultBeanPlus login(String existHmId) {
        synchronized (existHmId) {
            if (!memberMap.containsKey(existHmId)) {
                JsonResultBeanPlus bean = new JsonResultBeanPlus();
                if (!accountMap.containsKey(existHmId)) {
                    bean.putEnum(HcCodeEnum.IBS_404_EXIST_INFO);
                    bean.putSysEnum(HcCodeEnum.CODE_404);
                    return bean;
                }
                Map<String, String> data = accountMap.get(existHmId);
                return login(existHmId, data.get("memberAccount"), data.get("memberPassword"), data.get("handicapUrl"),
                        data.get("handicapCaptcha"));
            }
        }
        return new JsonResultBeanPlus().success();
    }

    /**
     * 用户基本信息
     *
     * @param existHmId 盘口会员存在id
     * @param flag 是否刷新
     * @return 用户基本信息
     */
    public JsonResultBeanPlus userInfo(String existHmId,boolean flag, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
            log.error("Ncom2盘口会员【" + existHmId + "】用户基本信息获取失败！");
            return null;
        }
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        // 获取用户信息
        if (!memberMap.containsKey(existHmId) || flag) {
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        Map<String, String> accountInfo = memberMap.get(existHmId);
        if (ContainerTool.isEmpty(accountInfo)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }

        // 获取配置类
        HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);
        try {
            Map<String, String> userInfo = new HashMap<>();
            if (userMap.containsKey(existHmId)) {
                userInfo = userMap.get(existHmId);
            }
            // 获取登录信息
            Map<String, String> grabUserInfo = Ncom2MemberTool.getUserInfo(httpConfig, accountInfo.get("projectHost"));
            // 登录超时
            if(ContainerTool.isEmpty(grabUserInfo)){
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            if(grabUserInfo.containsKey("snatchMsg")){
                // 登陸超時、抢登、請重新登陸
                memberMap.remove(existHmId);
                userMap.remove(existHmId);
                return userInfo(existHmId,flag,++index[0]);
            }
            userInfo.putAll(grabUserInfo);
            userMap.put(existHmId, userInfo);

            bean.success();
            bean.setData(userInfo);
        } catch (Exception e) {
            log.error("Ncom2盘口会员【" + existHmId + "】获取基本信息异常，失败原因为:", e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 获取用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @param flag      执行状态
     * @return 用户信息
     */
    @Override
    public Map<String, String> getUserInfo(String existHmId, boolean flag) {
        if(!userMap.containsKey(existHmId)||flag){
            //获取用户信息
            JsonResultBeanPlus bean = userInfo(existHmId,flag);
            //获取用户信息失败，返回空
            if (!bean.isSuccess()) {
                return new HashMap<>(1);
            }
        }
        return userMap.get(existHmId);
    }



    /**
     * 获取游戏限额
     *  http://vip.w1.hahha320.com:99/CreditInfo.aspx?p=1&id=2
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @return 游戏限额
     */
    @Override
    public JsonResultBeanPlus getQuotaList(String existHmId, GameUtil.Code gameCode) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        // 获取用户信息
        if (!memberMap.containsKey(existHmId)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        Map<String, String> accountInfo = memberMap.get(existHmId);
        if (ContainerTool.isEmpty(accountInfo)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        //获取盘口游戏code
        String game = Ncom2Config.GAME_CODE_ID.get(gameCode.name());
        // 获取配置类
        HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);

        try {
            JSONArray gameLimit = Ncom2MemberTool.getQuotaList(httpConfig, accountInfo.get("projectHost"), game);
            log.trace("Ncom2盘口会员【" + existHmId + "】游戏【" + gameCode.name() + "】限额信息为：" + gameLimit);
            if (ContainerTool.isEmpty(gameLimit)) {
                bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            bean.setData(gameLimit);
            bean.success();
        } catch (Exception e) {
            log.error("Ncom2盘口会员【" + existHmId + "】获取游戏限额信息失败,失败原因为：", e);
            bean.error(e.getMessage());
        }
        return bean;
    }

    /**
     * 获取盘口赔率
     * http://vip.w1.hahha320.com:99/L_PK10/Handler/Handler.ashx
     * action: get_oddsinfo
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @param betType   投注类型
     * @return
     */
    public  void getOddsInfo(String existHmId, GameUtil.Code gameCode,String betType, int... index) {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] >= MAX_RECURSIVE_SIZE) {
            log.error("Ncom2盘口会员【" + existHmId + "】获取赔率信息失败");
            return;
        }
        if (!memberMap.containsKey(existHmId)) {
            JsonResultBeanPlus bean = login(existHmId);
            if (!bean.isSuccess()) {
                log.error("Ncom2盘口会员【" + existHmId + "】重新登录失败");
                return;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        Map<String, String> accountInfo = memberMap.get(existHmId);
        if (ContainerTool.isEmpty(accountInfo)) {
            log.info("Ncom2盘口会员【" + existHmId + "】会员信息为空");
            return ;
        }
        //赔率信息
        Map<String, Object> odds;
        if (!oddsMap.containsKey(existHmId)) {
            Map<GameUtil.Code, Map<String, Object>> map = new HashMap<>(10);
            odds = new HashMap<>(3);
            map.put(gameCode, odds);
            oddsMap.put(existHmId, map);
        } else {
            if (!oddsMap.get(existHmId).containsKey(gameCode)) {
                odds = new HashMap<>(3);
                oddsMap.get(existHmId).put(gameCode, odds);
            } else {
                odds = oddsMap.get(existHmId).get(gameCode);
            }
        }
        HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);
        try {
            JSONObject data = Ncom2MemberTool.getOddsInfo(httpConfig, accountInfo.get("projectHost"), gameCode,betType);
            if(data != null && !"y".equals(data.getString("openning"))
                    || StringTool.isEmpty(data.get("play_odds"))){
                log.info("Ncom2盘口会员【" + existHmId + "】账号被冻结");
                return;
            }

            // 获取赔率信息
            JSONObject oddsInfo = data.getJSONObject("play_odds");
            Map<String,Object> newOdds = new HashMap<>();
            Set<Map.Entry<String, Object>> set =  oddsInfo.entrySet();
            for (Map.Entry entry :set){
                String key = (String) entry.getKey();
                JSONObject value = (JSONObject) entry.getValue();
                newOdds.put(key.split("_")[1],value.get("pl"));
            }
            odds.put(betType, newOdds);

            memberMap.get(existHmId).put("phaseid",(String)data.get("p_id"));
        } catch (Exception e) {
            log.error("Ncom2盘口会员【" + existHmId + "】获取盘口赔率失败,失败原因为：", e);
        }
    }

    /**
     * 检验信息,上次成功检验时间超过两分钟，删除用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @return
     */
    @Override
    public JsonResultBeanPlus checkInfo(String existHmId) {
        synchronized (existHmId) {
            JsonResultBeanPlus bean;
            long lastTime;
            if (checkMap.containsKey(existHmId)) {
                lastTime = checkMap.get(existHmId);
            } else {
                lastTime = System.currentTimeMillis();
                checkMap.put(existHmId, lastTime);
            }
            boolean flag = System.currentTimeMillis() - lastTime > MIN_CHECK_TIME;
            //重新获取数据 
            if(!userMap.containsKey(existHmId)||flag){
                //获取用户信息
                bean = userInfo(existHmId, flag);
                //获取用户信息失败，返回空
                if (!bean.isSuccess()) {
                    return bean;
                }
            }else {
                //使用内存数据
                bean = new JsonResultBeanPlus().success(userMap.get(existHmId));
            }
            if (ContainerTool.isEmpty(bean.getData())) {
                if (System.currentTimeMillis() - lastTime > MAX_CHECK_TIME) {
                    memberMap.remove(existHmId);
                    checkMap.put(existHmId, System.currentTimeMillis());
                }
            }
            if (flag) {
                checkMap.put(existHmId, System.currentTimeMillis());
            }
            return bean;
        }
    }

    /**
     * 投注
     *
     * @param existHmId  已存在id
     * @param gameCode   游戏code
     * @param betItems   投注项
     * @param betType    投注类型
     * @return 投注结果
     */
    public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode,List<String> betItems, String betType) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if (!memberMap.containsKey(existHmId)) {
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        Map<String, String> accountInfo = memberMap.get(existHmId);
        if (ContainerTool.isEmpty(accountInfo) || ContainerTool.isEmpty(oddsMap.get(existHmId).get(gameCode).get(betType))) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }

        //请求参数
        Map<String,String> betParameter = Ncom2MemberTool.getBetParameter(gameCode,betType);
        String phaseid = memberMap.get(existHmId).get("phaseid");
        String playpage = betParameter.get("playpage");
        String codePath = betParameter.get("codePath");

        Map<String,Object> odds = (Map<String, Object>) oddsMap.get(existHmId).get(gameCode).get(betType);

        HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHmId);
        httpConfig.httpTimeOut(20 * 1000);
        try {
            //获取投注信息
            Map<String, String> ballCode = BallCodeTool.getBallCode(HandicapUtil.Code.NCOM2, gameCode);
            Map<String, String> betsMap =Ncom2MemberTool.getBetItemInfo(ballCode, betItems, odds);
            if (ContainerTool.isEmpty(betsMap)) {
                bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            JSONObject betInfo =  Ncom2MemberTool.betting(httpConfig, accountInfo.get("projectHost"), gameCode,betsMap,playpage,codePath,phaseid);
            log.trace("Ncom2盘口会员【" + existHmId + "】投注结果为：" + betInfo);
            //处理投注结果
            bean = resultProcess(betInfo);
            if (!bean.isSuccess()) {
                return bean;
            }
            // 获取投注结果
            JSONObject betResultHtml = Ncom2MemberTool.getbetResult(httpConfig,accountInfo.get("projectHost"), gameCode,playpage,codePath);
            int status = betResultHtml.getInteger("success");
            if(status!=200){
                bean.putEnum(HcCodeEnum.IBS_404_DATA);
                bean.putSysEnum(HcCodeEnum.CODE_404);
                return bean;
            }
            JSONArray orders = betResultHtml.getJSONObject("data").getJSONArray("order_num");
            JSONArray betResult = new JSONArray();
            JSONArray betInfos;

            for (int i = 0; i <betItems.size() ; i++) {
                betInfos = new JSONArray();
                String item = betItems.get(i);
                item.substring(item.lastIndexOf("|"),item.length()-1);
                String[] items =  item.split("\\|");
                String bet = items[0].concat("|").concat(items[1]);
                // 注单
                betInfos.add(orders.get(i));
                // 投注项
                betInfos.add(bet);
                // 金额
                betInfos.add(NumberTool.doubleT(items[2]));
                // 赔率
                betInfos.add(odds.get(ballCode.get(bet)));
                betResult.add(betInfos);
            }


            //投注结果返回信息
            bean.success();
            bean.setData(betResult);
        } catch (Exception e) {
            log.error("SgWin盘口会员【" + existHmId + "】投注失败,失败原因为：", e);
            bean.error(e.getMessage());
        } finally {
            httpConfig.defTimeOut();
        }
        return bean;
    }



    /**
     * 处理投注结果
     *
     * @param betInfo   投注结果
     * @return 投注结果
     */
    private JsonResultBeanPlus resultProcess(JSONObject betInfo) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if (ContainerTool.isEmpty(betInfo)) {
            bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }
        String message = betInfo.getString("tipinfo");
        //status==200,投注成功
        int status = betInfo.getInteger("success");
        if (status != 200) {
            if (status == 400) {
                // 已分盘
                if("已截止".equals(message)){
                    bean.putEnum(HcCodeEnum.IBS_403_SEAL_HANDICAP);
                    bean.putSysEnum(HcCodeEnum.CODE_403);
                }
                if("頻率太快".equals(message)){
                   log.debug("下注頻率太快！");
                }
                //低于或高于限额

                return bean;
            } else if (status == 600) {
                //赔率不对，补投
                bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
        }

        if (!StringTool.isContains(message, "下注成功")) {
            if(StringTool.contains(message, "額度")){
                bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
            }else{
                log.error("未知的错误信息="+betInfo);
                bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
            }
            bean.putSysEnum(HcCodeEnum.CODE_403);
            return bean;
        }


        bean.success();
        return bean;
    }
}
