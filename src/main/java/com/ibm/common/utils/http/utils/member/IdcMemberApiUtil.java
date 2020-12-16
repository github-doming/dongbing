package com.ibm.common.utils.http.utils.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.IdcConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.IdcMemberApiTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import com.ibm.common.utils.http.utils.entity.MemberUserInfo;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: IDC会员工具类
 * @Author: null
 * @Date: 2020-04-23 16:13
 * @Version: v1.0
 */
public class IdcMemberApiUtil extends BaseMemberUtil {
    private static volatile IdcMemberApiUtil instance = null;

    private HandicapUtil.Code handicapCode=HandicapUtil.Code.IDC;

    public static IdcMemberApiUtil findInstance() {
        if (instance == null) {
            synchronized (IdcMemberApiUtil.class) {
                if (instance == null) {
                    IdcMemberApiUtil instance = new IdcMemberApiUtil();
                    // 初始化
                    instance.init();
                    IdcMemberApiUtil.instance = instance;
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
        if(ContainerTool.notEmpty(instance.memberCrawlers)){
            for (MemberCrawler memberCrawler : instance.memberCrawlers.values()) {
                memberCrawler.getHcConfig().destroy();
            }
        }
        instance.memberCrawlers=null;
        instance = null;
    }
    //region 登录
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
    public JsonResultBeanPlus login(String existHmId, String memberAccount, String memberPassword, String handicapUrl, String handicapCaptcha) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //已存在数据
        MemberCrawler member;
        if(memberCrawlers.containsKey(existHmId)){
            member=  memberCrawlers.get(existHmId);
            if(StringTool.notEmpty(member.getProjectHost())){
                bean.setData(memberCrawlers.get(existHmId));
                bean.success();
                return bean;
            }
        }else{
            member=  new MemberCrawler();
        }
        AccountInfo accountInfo=member.getAccountInfo();
        accountInfo.setItemInfo(memberAccount,memberPassword,handicapUrl,handicapCaptcha);

        try {
            //获取配置类
            HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(member);
            httpConfig.httpTimeOut(10 * 1000);
            bean = login(httpConfig, accountInfo);
            if (!bean.isSuccess()) {
                return bean;
            }
            Map<String, String> data = (Map<String, String>) bean.getData();

            member.getMemberUserInfo().setMemberAccount(memberAccount);
            member.setTicket(data.get("ticket"));
            member.setProjectHost(data.get("projectHost"));
            if(!memberCrawlers.containsKey(existHmId)){
                memberCrawlers.put(existHmId,member);
            }
        } catch (Exception e) {
            log.error(message,handicapCode.name(),existHmId, "登录失败,失败原因为："+e);
            bean.error(e.getMessage());
        }
        return bean;
    }
    /**
     * 登录
     * @param httpConfig        htpp请求配置类
     * @param accountInfo       账号信息对象
     * @return
     */
    @Override
    public JsonResultBeanPlus login(HttpClientConfig httpConfig, AccountInfo accountInfo) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        httpConfig.headers(null);
        httpConfig.httpContext(null);
        try {
            JSONObject loginHtml = IdcMemberApiTool.getLoginUrl(httpConfig, accountInfo.getHandicapUrl(), accountInfo.getHandicapCaptcha());
            if (ContainerTool.isEmpty(loginHtml) || "1".equals(loginHtml.getString("code"))) {
                log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登陆URL=" + loginHtml);
                bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            String loginUrl = loginHtml.getString("login_url");

            String sign = String
                    .format("accounts=%s&pwd=%s&extwagerno=%s&extwagerkey=%s", accountInfo.getAccount(), accountInfo.getPassword(),
                            IdcConfig.PERMISSION_CODE, IdcConfig.PERMISSION_KEY);

            sign = Md5Tool.md5Hex(sign);

            String parameters = String
                    .format("&accounts=%s&pwd=%s&extwagerno=%s&sign=%s", accountInfo.getAccount(),  accountInfo.getPassword(),
                            IdcConfig.PERMISSION_CODE, sign);

            JSONObject loginInfo = IdcMemberApiTool.getLoginTicket(httpConfig, loginUrl, parameters);
            if (ContainerTool.isEmpty(loginInfo)) {
                bean.putEnum(HcCodeEnum.IBS_403_LOGIN_FAIL);
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            if ("1".equals(loginInfo.get("code"))) {
                log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取登录信息=" + loginInfo);
                bean.putEnum(IdcMemberApiTool.loginError(loginInfo.getString("msg")));
                bean.putSysEnum(HcCodeEnum.CODE_403);
                return bean;
            }
            Map<String, String> data = new HashMap<>(2);
            data.put("projectHost", loginInfo.getString("api_url"));
            data.put("ticket", loginInfo.getString("ticket"));

            bean.setData(data);
            bean.success();
        } catch (Exception e) {
            log.error(message,handicapCode.name(),accountInfo.getAccount(), "登录失败,失败原因为："+e);
            bean.error(e.getMessage());
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
    public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String memberAccount, String memberPassword) {
        // 获取配置类
        HttpClientConfig httpConfig = new HttpClientConfig();
        httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
        AccountInfo accountInfo=new AccountInfo();
        accountInfo.setItemInfo(memberAccount,memberPassword,handicapUrl,handicapCaptcha);

        JsonResultBeanPlus bean = login(httpConfig, accountInfo);
        if (bean.isSuccess()) {
            String existHmId = RandomTool.getNumLetter32();
            MemberCrawler member=  new MemberCrawler();
            //存储爬虫信息
            Map<String, String> data = (Map<String, String>) bean.getData();
            member.setExistId(existHmId);
            member.setAccountInfo(accountInfo);
            member.setHcConfig(httpConfig);
            member.setProjectHost(data.get("projectHost"));
            member.setTicket(data.get("ticket"));

            member.getMemberUserInfo().setMemberAccount(memberAccount);
            memberCrawlers.put(existHmId,member);

            bean.setData(existHmId);
        }
        return bean;
    }
    //endregion

    //region 个人信息
    /**
     * 用户基本信息
     *
     * @param existHmId 已存在id
     * @param index     循环次数
     * @return 用户基本信息
     */
    private JsonResultBeanPlus userInfo(String existHmId, int... index) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        // 获取用户信息
        MemberCrawler member=memberCrawlers.get(existHmId);
        if(StringTool.isEmpty(member.getProjectHost(),member.getHcConfig())){
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        JSONObject userObj;
        try {
            userObj = IdcMemberApiTool
                    .getUserInfo(member.getHcConfig(), member.getProjectHost(),member.getTicket());
            if (ContainerTool.isEmpty(userObj)) {
                log.info(message,handicapCode.name(),existHmId, "获取会员账号信息失败");
                member.setProjectHost(null);
                return userInfo(existHmId, ++index[0]);
            }
            if ("1".equals(userObj.get("code"))) {
                log.info(message,handicapCode.name(),existHmId, "获取会员账号信息错误"+ userObj);
                bean.setCode(CodeEnum.IBS_404_DATA.getCode());
                bean.setMsg(userObj.getString("msg"));
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            if ("2".equals(userObj.get("code"))) {
                log.info(message,handicapCode.name(),existHmId, "获取会员列表信息失败"+ userObj);
                member.setProjectHost(null);
                return userInfo(existHmId, ++index[0]);
            }
            if (StringTool.notEmpty(userObj.get("ticket"))) {
                member.setTicket(userObj.getString("ticket"));
            }
            //获取用户信息
            MemberUserInfo memberUserInfo=member.getMemberUserInfo();
            JSONObject info = userObj.getJSONObject("data");
            //会员盘
            memberUserInfo.setMemberType(info.getString("wagerroundno"));
            //信用额度
            memberUserInfo.setCreditQuota(String.valueOf(info.getDouble("creditquota")));
            //可用额度
            memberUserInfo.setUsedQuota(String.valueOf(info.getDouble("allowcreditquota")));
            //使用金额
            memberUserInfo.setUsedAmount(String.valueOf(info.getDouble("usecreditquota")));
            //盈亏金额
            memberUserInfo.setProfitAmount(String.valueOf(info.getDouble("winningprofit")));

            bean.success();
            bean.setData(memberUserInfo);
            return bean;
        } catch (Exception e) {
            log.error(message,handicapCode.name(),existHmId, "获取用户信息失败"+ e);
            member.setProjectHost(null);
            return userInfo(existHmId, ++index[0]);
        }
    }

    /**
     * 获取用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @param flag      执行状态
     * @return 用户信息
     */
    @Override
    public MemberUserInfo getUserInfo(String existHmId, boolean flag) {
        if(!memberCrawlers.containsKey(existHmId)){
            return new MemberUserInfo();
        }
        if(flag){
            //获取用户信息
            JsonResultBeanPlus bean = userInfo(existHmId);
            //获取用户信息失败，返回空
            if (!bean.isSuccess()) {
                return new MemberUserInfo();
            }
        }
        return memberCrawlers.get(existHmId).getMemberUserInfo();
    }
    /**
     * 检验信息,上次成功检验时间超过两分钟，删除用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @return 校验信息
     */
    @Override
    public JsonResultBeanPlus checkInfo(String existHmId) {
        synchronized (existHmId) {
            if(!memberCrawlers.containsKey(existHmId)){
                return new JsonResultBeanPlus();
            }
            JsonResultBeanPlus bean;
            MemberCrawler member= memberCrawlers.get(existHmId);
            //上次校验时间
            long lastTime;
            if(member.getCheckTime()==0){
                lastTime = System.currentTimeMillis();
                member.setCheckTime(lastTime);
            }else{
                lastTime=member.getCheckTime();
            }
            //是否大于上次校验时间
            boolean flag = System.currentTimeMillis() - lastTime > MIN_CHECK_TIME;
            if(flag||member.getMemberUserInfo().getUsedQuota()==null){
                //获取用户信息
                bean = userInfo(existHmId);
                //获取用户信息失败，返回空
                if (!bean.isSuccess()) {
                    return bean;
                }
            }else {
                //使用内存数据
                bean = new JsonResultBeanPlus().success(member.getMemberUserInfo());
            }
            if (ContainerTool.isEmpty(bean.getData())) {
                if (System.currentTimeMillis() - lastTime > MAX_CHECK_TIME) {
                    member.setProjectHost(null);
                    member.setCheckTime(System.currentTimeMillis());
                }
            }
            if (flag) {
                member.setCheckTime(System.currentTimeMillis());
            }
            return bean;
        }
    }
    //endregion


    //region 游戏限额
    /**
     * 获取游戏限额
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @return 游戏限额
     */
    @Override
    public JsonResultBeanPlus getQuotaList(String existHmId, GameUtil.Code gameCode) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        if(!memberCrawlers.containsKey(existHmId)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        MemberCrawler member=  memberCrawlers.get(existHmId);
        if(StringTool.isEmpty(member.getProjectHost(),member.getHcConfig())){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        try {
            JSONObject quotaList = IdcMemberApiTool
                    .getQuotaList(member.getHcConfig(), member.getProjectHost(), member.getTicket(),
                            Integer.parseInt(IdcConfig.BET_CODE.get(gameCode.name())));
            if (ContainerTool.isEmpty(quotaList)) {
                log.info(message,handicapCode.name(),existHmId, "获取游戏【" + gameCode + "】限额信息失败");
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            if ("1".equals(quotaList.get("code"))) {
                log.info(message,handicapCode.name(),existHmId, "获取游戏【" + gameCode + "】限额信息错误="+ quotaList);
                bean.setCode(CodeEnum.IBS_404_DATA.getCode());
                bean.setMsg(quotaList.getString("msg"));
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            if ("2".equals(quotaList.get("code"))) {
                log.info(message,handicapCode.name(),existHmId, "获取游戏【" + gameCode + "】限额信息失败="+ quotaList);
                member.setProjectHost(null);
                bean.setCode(CodeEnum.IBS_404_DATA.getCode());
                bean.setMsg(quotaList.getString("msg"));
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            if (StringTool.notEmpty(quotaList.get("ticket"))) {
                member.setTicket(quotaList.getString("ticket"));
            }
            //过滤限额信息
            JSONArray quotas = IdcMemberApiTool.filterQuotaInfo(quotaList.getJSONArray("data"));

            bean.success();
            bean.setData(quotas);
        } catch (Exception e) {
            log.error(message,handicapCode.name(),existHmId, "获取游戏【" + gameCode + "】限额信息失败"+ e);
        }
        return bean;
    }
    //endregion

    //region 投注
    /**
     * 投注
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @param period    期数
     * @param betItems  投注信息
     * @param errorInfo 错误信息
     * @param index     循环次数
     * @return 投注结果
     */
    public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode, String period, List<String> betItems,
                                      List<String> errorInfo, int... index) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        if(!memberCrawlers.containsKey(existHmId)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        MemberCrawler member=memberCrawlers.get(existHmId);

        if(StringTool.isEmpty(member.getProjectHost(),member.getHcConfig())){
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }

        String wagers = IdcMemberApiTool.getBetItemInfo(gameCode, betItems);
        if (StringTool.isEmpty(wagers)) {
            log.info(message,handicapCode.name(),existHmId, "游戏【" + gameCode + "】期数【" + period + "】投注失败,未存在正确的投注项");
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        try {
            JSONObject betInfo = IdcMemberApiTool.betting(member.getHcConfig(),member.getProjectHost(),member.getTicket(),
                            Integer.parseInt(IdcConfig.BET_CODE.get(gameCode.name())), period, wagers);
            //投注结果
            log.trace(message,handicapCode.name(),existHmId, "游戏【" + gameCode + "】期数【" + period + "】投注结果为：" + betInfo);
            if (ContainerTool.isEmpty(betInfo)) {
                log.info(message,handicapCode.name(),existHmId, "游戏【" + gameCode + "】期数【" + period + "】投注失败");
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            if ("1".equals(betInfo.get("code"))) {
                log.info(message,handicapCode.name(),existHmId, "游戏【" + gameCode + "】期数【" + period + "】投注错误="+ betInfo);
                if (StringTool.contains(betInfo.getString("msg"), "指定期数为非交易状态","非交易时间,不允许下注")) {
                    bean.putEnum(HcCodeEnum.IBS_403_SEAL_HANDICAP);
                    bean.putSysEnum(HcCodeEnum.CODE_403);
                    return bean;
                }
                String failBetItem = IdcMemberApiTool.getFailBetItem(gameCode, betInfo.getString("msg"));
                if (StringTool.notEmpty(failBetItem)) {
                    for (String items : betItems) {
                        if (StringTool.isContains(items, failBetItem)) {
                            errorInfo.add(items);
                            betItems.remove(items);
                            return betting(existHmId, gameCode, period, betItems, errorInfo);
                        }
                    }
                }
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            if ("2".equals(betInfo.get("code"))) {
                log.info(message,handicapCode.name(),existHmId, "游戏【" + gameCode + "】期数【" + period + "】获取投注信息失败="+ betInfo);
                member.setProjectHost(null);
                return betting(existHmId, gameCode, period, betItems, errorInfo, ++index[0]);
            }
            //更换旧票证
            if (StringTool.notEmpty(betInfo.get("ticket"))) {
                member.setTicket(betInfo.getString("ticket"));
            }
            //保存余额信息
            member.getMemberUserInfo().setUsedQuota(betInfo.getString("balance"));

            //投注成功项信息
            JSONArray betResult = IdcMemberApiTool.getBetResult(gameCode, betInfo.getJSONArray("data"));

            bean.success();
            bean.setData(betResult);
        } catch (Exception e) {
            log.error(message,handicapCode.name(),existHmId, "游戏【" + gameCode + "】期数【" + period + "】投注失败"+ e);
        }
        return bean;
    }
    /**
     * 获取注单明细
     *
     * @param existHmId 已存在盘口会员id
     * @param gameCode  游戏code
     * @param date      查询时间
     * @param isjs      是否结算数据（""为全部、1 已结帐、0 未结帐）
     * @param index     循环次数
     * @return 注单明细
     */
    public JsonResultBeanPlus getBetDetail(String existHmId, GameUtil.Code gameCode, String date, String isjs,
                                           int... index) {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        if(!memberCrawlers.containsKey(existHmId)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        MemberCrawler member=memberCrawlers.get(existHmId);
        if(StringTool.isEmpty(member.getProjectHost(),member.getHcConfig())){
            bean = login(existHmId);
            if (!bean.isSuccess()) {
                return bean;
            }
            bean.setData(null);
            bean.setSuccess(false);
        }
        try {
            JSONObject betDetail = IdcMemberApiTool
                    .getBetDetail(member.getHcConfig(), member.getProjectHost(),member.getTicket(),
                            IdcConfig.BET_CODE.get(gameCode.name()), date, isjs);
            if (ContainerTool.isEmpty(betDetail)) {
                log.info(message,handicapCode.name(),existHmId, "获取注单明细错误");
                member.setProjectHost(null);
                return getBetDetail(existHmId, gameCode, date, isjs, ++index[0]);
            }
            if ("1".equals(betDetail.get("code"))) {
                log.info(message,handicapCode.name(),existHmId, "获取注单明细错误="+ betDetail);
                bean.setCode(CodeEnum.IBS_404_DATA.getCode());
                bean.setMsg(betDetail.getString("msg"));
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
            }
            if ("2".equals(betDetail.get("code"))) {
                log.info(message,handicapCode.name(),existHmId, "获取注单明细失败="+ betDetail);
                member.setProjectHost(null);
                return getBetDetail(existHmId, gameCode, date, isjs, ++index[0]);
            }
            if (StringTool.notEmpty(betDetail.get("ticket"))) {
                member.setTicket(betDetail.getString("ticket"));
            }
            JSONArray detailInfo = IdcMemberApiTool.getDetailInfo(gameCode, betDetail.getJSONArray("data"));

            bean.success();
            bean.setData(detailInfo);
        } catch (Exception e) {
            log.info(message,handicapCode.name(),existHmId, "获取注单明细信息失败"+  e);
        }
        return bean;
    }
}
