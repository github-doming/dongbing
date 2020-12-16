//package com.ibm.common.test.wwj.handicap.sgwin;
//
//import com.ibm.common.core.JsonResultBeanPlus;
//import com.ibm.common.core.configs.SgWinConfig;
//import com.ibm.common.enums.IbmHcCodeEnum;
//import com.ibm.common.test.wwj.handicap.AbsAgentCrawler;
//import com.ibm.common.test.wwj.handicap.DataCustomer;
//import com.ibm.common.utils.HandicapUtil;
//import com.ibm.common.utils.game.GameUtil;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.doming.core.tools.ContainerTool;
//import org.doming.core.tools.DateTool;
//import org.doming.core.tools.NumberTool;
//import org.doming.core.tools.StringTool;
//import org.doming.develop.http.httpclient.HttpClientConfig;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Description:
// * @Author: wwj
// * @Date: 2019/12/2 15:38
// * @Email: 97085010@qq.com
// * @Version: v1.0
// */
//public class SgWinAgentCrawler extends AbsAgentCrawler<SgWinAgentGrab> {
//    public SgWinAgentCrawler() {
//        super.handicap(HandicapUtil.Code.SGWIN);
//    }
//
//    /**
//     * 登陆
//     *
//     * @param httpConfig      请求配置类
//     * @param handicapUrl     盘口地址
//     * @param handicapCaptcha 盘口验证码
//     * @param account         盘口账户
//     * @param password        盘口密码
//     * @return 登录结果
//     */
//    @Override
//    protected JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, String account, String password) {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        //如果盘口地址不包含http://则添加
//        String http = "http://";
//        if (!handicapUrl.startsWith(http) && !handicapUrl.startsWith("https://")) {
//            handicapUrl = http.concat(handicapUrl);
//        }
//        httpConfig.headers(null);
//        httpConfig.httpContext(null);
//
//        try {
//            //获取线路选择页面
//            String routeHtml = grab.selectRoute(httpConfig, handicapUrl, handicapCaptcha);
//            if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "线路选择")) {
//                bean.putEnum(IbmHcCodeEnum.IBM_403_PAGE_NAVIGATE);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                return bean;
//            }
//            //4条代理线路数组
//            String[] routes = grab.routes(httpConfig,routeHtml);
//            if (ContainerTool.isEmpty(routes)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_ROUTE);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            //获取登录信息map
//            Map<String, String> loginInfoMap = grab.loginInfo(httpConfig, routes);
//            if (ContainerTool.isEmpty(loginInfoMap)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            String loginSrc = loginInfoMap.get("loginSrc");
//            //获取验证码
//            String verifyCode = grab.getVerifyCode(httpConfig, loginSrc, loginInfoMap.remove("code"));
//
//            httpConfig.setHeader("Referer", loginSrc.concat("login"));
//            httpConfig.httpContext(HttpClientContext.create());
//
//            String loginInfo = grab.login(httpConfig, loginSrc.concat(loginInfoMap.remove("action")), verifyCode, account, password);
//
//            if (StringTool.isEmpty(loginInfo)) {
//                bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            //错误处理和是否等一次登录盘口
//            if (StringTool.isContains(loginInfo, "alert", "修改密码")) {
//                bean.putEnum(grab.loginError(loginInfo));
//                bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//                return bean;
//            }
//            httpConfig.setHeader("Referer", loginSrc.concat("agent/index"));
//
//            Map<String, String> data = new HashMap<>(1);
//            data.put("projectHost", loginSrc);
//            bean.success(data);
//        } catch (Exception e) {
//            log.error(getMsgTitle()+"账号【" + account + "】登录失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        } finally {
//            httpConfig.defTimeOut();
//        }
//
//        return bean;
//    }
//
//    /**
//     * 获取会员账号信息
//     *
//     * @return 会员账号信息
//     */
//    @Override
//    protected JsonResultBeanPlus memberListInfo() {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        try {
//            DataCustomer customer = agent.customer();
//            List<Map<String,String>> memberList = grab.memberListInfo(customer.httpConfig(),customer.crawler().get("projectHost"));
//            agent.memberList(memberList);
//            bean.success();
//        } catch (Exception e) {
//            log.error(getMsgTitle()+"获取会员账号信息失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        }
//        return bean;
//    }
//
//    /**
//     * 投注未结算摘要
//     *
//     * @param gameCode 游戏编码
//     * @return 未结算摘要
//     */
//    @Override
//    public JsonResultBeanPlus betSummary(GameUtil.Code gameCode) {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        if(agent.customer()==null){
//            bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//            bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//            return bean;
//        }
//        try {
//            DataCustomer customer = agent.customer();
//            if (customer.crawler() == null) {
//                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
//                if (!bean.isSuccess()) {
//                    return bean;
//                }
//                bean.setSuccess(false);
//            }
//            String day = DateTool.getDate(new Date());
//            String lottery = SgWinConfig.BET_CODE.get(gameCode.name());
//            Map<String, Map<String, Integer>> betSummary = grab.getBetSummary(customer.httpConfig(), customer.crawler().get("projectHost"), lottery, day);
//            if(ContainerTool.isEmpty(betSummary)){
//                customer.crawler(null);
//                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
//                betSummary = grab.getBetSummary(customer.httpConfig(), customer.crawler().get("projectHost"), lottery, day);
//            }
//            if (ContainerTool.isEmpty(betSummary)) {
//                return bean.success();
//            }
//            Map<String, Integer> agent = betSummary.get("agent");
//            getBetSummary(customer.httpConfig(), customer.crawler().get("projectHost"), lottery, day, betSummary,agent);
//
//            bean.success(betSummary);
//        } catch (Exception e) {
//            log.error(getMsgTitle()+"获取投注未结算摘要失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        }
//        return bean;
//    }
//
//    /**
//     * 获取子代理未结算摘要
//     *
//     * @param httpConfig  http请求配置类
//     * @param projectHost 主机地址
//     * @param lottery     平台游戏编码
//     * @param day         获取日期
//     * @param betSummary  未结算摘要
//     * @param goAgent     将要获取的代理数据
//     */
//    private void getBetSummary(HttpClientConfig httpConfig, String projectHost, String lottery, String day,
//                               Map<String, Map<String, Integer>> betSummary, Map<String, Integer> goAgent)
//            throws InterruptedException {
//        if (ContainerTool.isEmpty(goAgent)) {
//            return;
//        }
//        for (Map.Entry<String, Integer> entry : goAgent.entrySet()) {
//            //获取子代理的未结算摘要
//            Map<String, Map<String, Integer>> newBetSummary = grab
//                    .getBetSummary(httpConfig, projectHost, lottery, day, entry.getKey());
//            if (ContainerTool.isEmpty(newBetSummary)) {
//                continue;
//            }
//
//            //子代理存在新的会员投注-追加
//            Map<String, Integer> newMember = newBetSummary.get("member");
//            if (ContainerTool.notEmpty(newMember)) {
//                betSummary.get("member").putAll(newMember);
//            }
//            //子代理存在新的子子代理的未结算摘要
//            Map<String, Integer> newAgent = newBetSummary.get("agent");
//            getBetSummary(httpConfig, projectHost, lottery, day, betSummary,newAgent);
//
//            if (ContainerTool.notEmpty(newMember)) {
//                //将子子代理未结算数据存储
//                betSummary.get("agent").putAll(newMember);
//            }
//        }
//    }
//
//    /**
//     * 投注未结算详情
//     *
//     * @param gameCode  游戏编码
//     * @param period    期数
//     * @param member    会员
//     * @param oddNumber 注单号
//     * @return 未结算详情
//     */
//    @Override
//    public JsonResultBeanPlus betDetail(GameUtil.Code gameCode, String period, Object member, String oddNumber) {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        if(agent.customer()==null){
//            bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//            bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//            return bean;
//        }
//        try {
//            DataCustomer customer = agent.customer();
//            if (customer.crawler() == null) {
//                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
//                if (!bean.isSuccess()) {
//                    return bean;
//                }
//                bean.setSuccess(false);
//            }
//            String day = DateTool.getDate(new Date());
//            String lottery = SgWinConfig.BET_CODE.get(gameCode.name());
//            //获取未结算明细
//            String html = grab.betDetail(customer.httpConfig(), customer.crawler().get("projectHost"), null, lottery, day, (String)member);
//            Map<String, Object> betDetail = grab.getBetDetail(null, gameCode, html, oddNumber);
//            if (betDetail == null) {
//                return bean.success();
//            }
//            //循环所有的页码
//            int pages = NumberTool.getInteger(betDetail.get("pages"));
//            for (int page = 1; page < pages; page++) {
//                html = grab.betDetail(customer.httpConfig(), customer.crawler().get("projectHost"), null, lottery, day, (String)member);
//
//                //数据弹出
//                if (grab.getBetDetail(betDetail, gameCode, html, oddNumber) == null) {
//                    break;
//                }
//            }
//            bean.success();
//            bean.success(betDetail);
//        } catch (Exception e) {
//            log.error(getMsgTitle()+"获取投注未结算详情失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        }
//        return bean;
//    }
//}
