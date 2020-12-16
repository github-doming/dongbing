package com.ibm.common.test.wwj.handicap.idc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.IdcConfig;
import com.ibm.common.test.wwj.handicap.AbsHandicapGrab;
import com.ibm.common.test.wwj.handicap.HttpType;
import com.ibm.common.utils.HandicapUtil;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/4 15:59
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class AbsIdcGrab extends AbsHandicapGrab {
    AbsIdcGrab() {
        super(HandicapUtil.Code.IDC);
    }
    @Override
    public String selectRoute(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, int... index) {
        return null;
    }

    @Override
    public String[] routes(HttpClientConfig httpConfig, String routeHtml, int... index) {
        return new String[0];
    }

    @Override
    public Map<String, String> loginInfo(HttpClientConfig httpConfig, String[] routes, int... index) {
        return null;
    }

    @Override
    public String login(HttpClientConfig httpConfig, String loginUrl, String verifyCode, String account, String password) {
        return null;
    }

    @Override
    public String home(HttpClientConfig httpConfig, String homeUrl) {
        return null;
    }


    // TODO 开启页面函数

    //  开启页面函数

    /**
     * 获取登录url
     *
     * @param httpConfig      http请求配置类
     * @param handicapUrl     盘口url
     * @param handicapCaptcha 盘口验证码
     * @return 登录url
     */
    protected JSONObject getLoginUrl(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,int... index){
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        try{
            Map<String, Object> map = new HashMap<>(1);
            map.put("code", handicapCaptcha);
            httpConfig.url(handicapUrl.concat("/get_bet_url.ashx")).map(map);
            String result = html(httpConfig.method(HttpConfig.Method.POST), HttpType.Normal);
            if (StringTool.isEmpty(result)) {
                log.error("获取登录url失败");
                sleep("获取登录url");
                return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha,++index[0]);
            }
            if(StringTool.isContains(result,"Gateway Time-out")){
                log.error("获取登录url失败，网关超时，页面为="+result);
                sleep("获取登录url");
                return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha,++index[0]);
            }
            if (!StringTool.isContains(result, "code", "msg")) {
                log.error("获取登录url失败，错误信息=" + result);
                sleep("获取登录url");
                return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha,++index[0]);
            }
            //转换为json
            JSONObject json = parseObject(result);
            if (ContainerTool.isEmpty(json)) {
                sleep("获取登录url");
                return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha,++index[0]);
            }
            return json;
        }catch (Exception e) {
            log.error("获取登录url失败", e);
            sleep("获取登录url");
            return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha,++index[0]);
        }
    }

    /**
     * 登录并获取票证Ticket
     *
     * @param httpConfig http请求配置类
     * @param loginUrl   登录url
     * @param parameters 请求参数
     * @return 票证Ticket
     */
    public JSONObject getLoginTicket(HttpClientConfig httpConfig, String loginUrl, String parameters,int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        try {
            String result = html(httpConfig.url(loginUrl + parameters).method(HttpConfig.Method.POST),HttpType.Normal);
            if (StringTool.isEmpty(result)) {
                log.error("获取票证失败");
                longSleep("获取票证Ticket");
                return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
            }
            if(StringTool.isContains(result,"Gateway Time-out")){
                log.error("获取登录url失败，网关超时");
                log.fatal("页面为="+result);
                longSleep("获取票证Ticket");
                return getLoginTicket(httpConfig, loginUrl, parameters,++index[0]);
            }
            if (!StringTool.isContains(result, "code", "msg")) {
                log.error("获取票证失败，错误信息=" + result);
                longSleep("获取票证Ticket");
                return getLoginTicket(httpConfig, loginUrl, parameters,++index[0]);
            }
            //转换为json
            JSONObject json = parseObject(result);
            if (ContainerTool.isEmpty(json)) {
                longSleep("获取票证Ticket");
                return getLoginTicket(httpConfig, loginUrl, parameters,++index[0]);
            }
            return json;
        } catch (Exception e) {
            log.error("获取票证失败", e);
            longSleep("获取票证Ticket");
            return getLoginTicket(httpConfig, loginUrl, parameters,++index[0]);
        }
    }







    //  开启页面函数

    // TODO 页面解析函数

    //  页面解析函数


    //  页面解析函数

    // TODO 功能函数


    //  功能函数

    protected JsonResultBeanPlus baseLogin(JsonResultBeanPlus bean, HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, String account, String password) {
        try{
            JSONObject loginHtml = getLoginUrl(httpConfig, handicapUrl, handicapCaptcha);
            if (ContainerTool.isEmpty(loginHtml) || "1".equals(loginHtml.getString("code"))) {
                log.info("获取登陆URL=" + loginHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            String loginUrl = loginHtml.getString("login_url");

            String sign = String.format("accounts=%s&pwd=%s&extwagerno=%s&extwagerkey=%s", account, password,
                    IdcConfig.PERMISSION_CODE, IdcConfig.PERMISSION_KEY);
            sign = Md5Tool.md5Hex(sign);

            String parameters = String.format("&accounts=%s&pwd=%s&extwagerno=%s&sign=%s", account, password,
                    IdcConfig.PERMISSION_CODE, sign);

            JSONObject loginInfo = getLoginTicket(httpConfig, loginUrl, parameters);
            if (ContainerTool.isEmpty(loginInfo)) {
                bean.putEnum(HcCodeEnum.IBS_403_LOGIN_FAIL);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            if ("1".equals(loginInfo.get("code"))) {
                log.info("获取登录信息=" + loginInfo);
                bean.putEnum(loginError(loginInfo.getString("msg")));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            Map<String, String> data = new HashMap<>(2);
            data.put("projectHost", loginInfo.getString("api_url"));
            data.put("ticket", loginInfo.getString("ticket"));

            bean.setData(data);
            bean.success();
        } catch (Exception e) {
            bean.error(e.getMessage());
        }

        return bean;
    }


    /**
     * 将结果转换为json
     *
     * @param html 结果界面
     * @return 结果json
     */
    private JSONObject parseObject(String html) {
        try {
            return JSONObject.parseObject(html);
        } catch (Exception e) {
            log.error("转换结果页面失败【" + html + "】", e);
        }
        return null;
    }

    /**
     * 登陆错误
     *
     * @param msg 错误信息
     * @return 登录错误
     */
    public HcCodeEnum loginError(String msg) {
        if (StringTool.contains(msg, "帐号与密码不匹配")) {
            return HcCodeEnum.IBS_403_USER_ACCOUNT;
        } else if (StringTool.contains(msg, "帐号被锁定")) {
            return HcCodeEnum.IBS_403_USER_STATE;
        } else if (StringTool.contains(msg, "请求过于频繁")) {
            return HcCodeEnum.IBS_403_LOGIN_OFTEN;
        } else if (StringTool.contains(msg, "您的帐户为初次登陆", "密码由后台重新设定")) {
            return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
        } else {
            return HcCodeEnum.IBS_403_UNKNOWN;
        }
    }

    /**
     * 过滤限额信息
     *
     * @param quotaList 游戏限额信息
     * @return 过滤限额信息
     */
    public JSONArray filterQuotaInfo(JSONArray quotaList) {
        JSONArray quota = new JSONArray();
        JSONObject info;
        JSONArray array;
        for (int i = 0; i < quotaList.size(); i++) {
            array = new JSONArray();
            info = quotaList.getJSONObject(i);
            array.add(info.get("noquota"));
            array.add(info.get("stakequota"));
            array.add(info.get("wagerquota"));
            quota.add(array);
        }
        return quota;
    }
    //  功能函数

}
