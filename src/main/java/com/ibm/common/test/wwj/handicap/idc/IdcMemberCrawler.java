//package com.ibm.common.test.wwj.handicap.idc;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.ibm.common.core.JsonResultBeanPlus;
//import com.ibm.common.core.configs.HqConfig;
//import com.ibm.common.core.configs.IdcConfig;
//import com.ibm.common.enums.IbmCodeEnum;
//import com.ibm.common.enums.IbmHcCodeEnum;
//import com.ibm.common.test.wwj.handicap.AbsMemberCrawler;
//import com.ibm.common.test.wwj.handicap.DataCustomer;
//import com.ibm.common.utils.HandicapUtil;
//import com.ibm.common.utils.game.GameUtil;
//import org.doming.core.tools.ContainerTool;
//import org.doming.core.tools.StringTool;
//import org.doming.develop.http.httpclient.HttpClientConfig;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Description:
// * @Author: wwj
// * @Date: 2019/12/4 16:03
// * @Email: 97085010@qq.com
// * @Version: v1.0
// */
//public class IdcMemberCrawler extends AbsMemberCrawler<IdcMemberGrab> {
//    public IdcMemberCrawler() {
//        super.handicap(HandicapUtil.Code.IDC);
//    }
//
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
//        try{
//            bean = grab.baseLogin(bean,httpConfig,handicapUrl,handicapCaptcha,account,password);
//        }catch (Exception e) {
//            log.error(getMsgTitle()+"会员账号【" + account + "】登录失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        }
//        return bean;
//    }
//
//    /**
//     * 用户基本信息
//     *
//     * @return 用户信息
//     */
//    @Override
//    protected JsonResultBeanPlus userInfo() {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        if(member.customer()==null){
//            bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//            bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//            return bean;
//        }
//        try {
//            DataCustomer customer = member.customer();
//            if (customer.crawler() == null) {
//                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
//                if (!bean.isSuccess()) {
//                    return bean;
//                }
//                bean.setSuccess(false);
//            }
//            Map<String, String> crawler = customer.crawler();
//            JSONObject userObj = grab.getUserInfo(customer.httpConfig(), crawler.get("projectHost"),crawler.get("ticket"));
//            if(ContainerTool.isEmpty(userObj)){
//                member.customer(null);
//                log.info(getMsgTitle()+"获取会员账号信息失败");
//                bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            if ("1".equals(userObj.get("code"))) {
//                log.info(getMsgTitle()+"获取会员账号信息错误=" + userObj);
//                bean.setCode(IbmCodeEnum.IBM_404_DATA.getCode());
//                bean.setMsg(userObj.getString("msg"));
//                bean.putSysEnum(IbmCodeEnum.CODE_404);
//                return bean;
//            }
//            if ("2".equals(userObj.get("code"))) {
//                member.customer(null);
//                log.info(getMsgTitle()+"获取会员账号信息失败=" + userObj);
//                bean.setCode(IbmCodeEnum.IBM_404_DATA.getCode());
//                bean.setMsg(userObj.getString("msg"));
//                bean.putSysEnum(IbmCodeEnum.CODE_404);
//                return bean;
//            }
//            if (StringTool.notEmpty(userObj.get("ticket"))) {
//                crawler.put("ticket", userObj.getString("ticket"));
//                member.customer().crawler(crawler);
//            }
//            if(userObj.containsKey("data")){
//                if ("login".equals(userObj.getString("data"))){
//                    member.customer(null);
//                }
//                bean.putEnum(IbmCodeEnum.IBM_404_DATA);
//                bean.putSysEnum(IbmCodeEnum.CODE_404);
//                return bean;
//            }
//            JSONObject info = userObj.getJSONObject("data");
//
//            Map<String, String> userInfo = new HashMap<>(4);
//            //会员账户
//            userInfo.put("memberAccount", customer.account());
//            //会员盘
//            userInfo.put("memberType", info.getString("wagerroundno"));
//            //信用额度
//            userInfo.put("creditQuota", String.valueOf(info.getDouble("creditquota")));
//            //可用额度
//            userInfo.put("usedQuota", String.valueOf(info.getDouble("allowcreditquota")));
//            //使用金额
//            userInfo.put("usedAmount", String.valueOf(info.getDouble("usecreditquota")));
//            //盈亏金额
//            userInfo.put("profitAmount", String.valueOf(info.getDouble("winningprofit")));
//
//            member.userMap(userInfo);
//
//            bean.success(userInfo);
//        } catch (Exception e) {
//            log.error(getMsgTitle()+"获取会员账号信息失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        }
//        return bean;
//    }
//    /**
//     * 某个游戏的限额信息
//     *
//     * @param gameCode 游戏编码
//     * @return 游戏限额信息
//     */
//    @Override
//    public JsonResultBeanPlus gameLimit(GameUtil.Code gameCode) {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        if(member.customer()==null){
//            bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//            bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//            return bean;
//        }
//        try {
//            DataCustomer customer = member.customer();
//            if (customer.crawler() == null) {
//                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
//                if (!bean.isSuccess()) {
//                    return bean;
//                }
//                bean.setSuccess(false);
//            }
//
//            //获取盘口游戏code
//            String game = HqConfig.BET_CODE.get(gameCode.name());
//            JSONObject gameLimit = grab.getGameLimit(customer.httpConfig(), customer.crawler().get("projectHost"), customer.crawler().get("ticket"),
//                    Integer.parseInt(IdcConfig.BET_CODE.get(gameCode.name())));
//            if (ContainerTool.isEmpty(gameLimit)) {
//                log.info(getMsgTitle()+"获取游戏【" + gameCode + "】限额信息失败");
//                member.customer(null);
//                log.info(getMsgTitle()+"获取会员账号信息失败");
//                bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            if ("1".equals(gameLimit.get("code"))) {
//                log.info(getMsgTitle()+" 获取游戏【" + gameCode + "】限额信息错误=" + gameLimit);
//                bean.setCode(IbmCodeEnum.IBM_404_DATA.getCode());
//                bean.setMsg(gameLimit.getString("msg"));
//                bean.putSysEnum(IbmCodeEnum.CODE_404);
//                return bean;
//            }
//            if ("2".equals(gameLimit.get("code"))) {
//                log.info(getMsgTitle()+" 获取游戏【" + gameCode + "】限额信息失败=" + gameLimit);
//                member.customer(null);
//                log.info(getMsgTitle()+"获取会员账号信息失败");
//                bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            if (StringTool.notEmpty(gameLimit.get("ticket"))) {
//                customer.crawler().put("ticket", gameLimit.getString("ticket"));
//            }
//            //过滤限额信息
//            JSONArray quotas = grab.filterQuotaInfo(gameLimit.getJSONArray("data"));
//            bean.success(quotas);
//        } catch (Exception e) {
//            log.error(getMsgTitle()+"获取游戏限额信息失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        }
//        return bean;
//    }
//
//    /**
//     * 游戏赔率信息
//     *
//     * @param gameCode 游戏编码
//     * @param betType
//     * @return 游戏赔率信息
//     */
//    @Override
//    public JsonResultBeanPlus oddsInfo(GameUtil.Code gameCode, String betType) {
//        return null;
//    }
//
//    /**
//     * 投注
//     *
//     * @param gameCode 游戏编码
//     * @param betItems 投注项列表
//     * @param odds     赔率信息
//     * @param period   期数
//     * @return 投注结果
//     */
//    @Override
//    public JsonResultBeanPlus betting(GameUtil.Code gameCode, List<String> betItems, Object odds, String period) {
//        JsonResultBeanPlus bean = new JsonResultBeanPlus();
//        if(member.customer()==null){
//            bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
//            bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//            return bean;
//        }
//        try {
//            DataCustomer customer = member.customer();
//            if (customer.crawler() == null) {
//                bean = login(customer.handicapUrl(), customer.handicapCaptcha(),customer.account(), customer.password());
//                if (!bean.isSuccess()) {
//                    return bean;
//                }
//                bean.setSuccess(false);
//            }
//
//            //获取盘口游戏code
//            String game = HqConfig.BET_CODE.get(gameCode.name());
//            JSONObject gameLimit = grab.getGameLimit(customer.httpConfig(), customer.crawler().get("projectHost"), customer.crawler().get("ticket"),
//                    Integer.parseInt(IdcConfig.BET_CODE.get(gameCode.name())));
//            if (ContainerTool.isEmpty(gameLimit)) {
//                log.info(getMsgTitle()+"获取游戏【" + gameCode + "】限额信息失败");
//                member.customer(null);
//                log.info(getMsgTitle()+"获取会员账号信息失败");
//                bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            if ("1".equals(gameLimit.get("code"))) {
//                log.info(getMsgTitle()+" 获取游戏【" + gameCode + "】限额信息错误=" + gameLimit);
//                bean.setCode(IbmCodeEnum.IBM_404_DATA.getCode());
//                bean.setMsg(gameLimit.getString("msg"));
//                bean.putSysEnum(IbmCodeEnum.CODE_404);
//                return bean;
//            }
//            if ("2".equals(gameLimit.get("code"))) {
//                log.info(getMsgTitle()+" 获取游戏【" + gameCode + "】限额信息失败=" + gameLimit);
//                member.customer(null);
//                log.info(getMsgTitle()+"获取会员账号信息失败");
//                bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
//                bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//                return bean;
//            }
//            if (StringTool.notEmpty(gameLimit.get("ticket"))) {
//                customer.crawler().put("ticket", gameLimit.getString("ticket"));
//            }
//            //过滤限额信息
//            JSONArray quotas = grab.filterQuotaInfo(gameLimit.getJSONArray("data"));
//            bean.success(quotas);
//        } catch (Exception e) {
//            log.error(getMsgTitle()+"获取游戏限额信息失败,失败原因为：", e);
//            bean.error(e.getMessage());
//        }
//        return bean;
//    }
//
//
//}
