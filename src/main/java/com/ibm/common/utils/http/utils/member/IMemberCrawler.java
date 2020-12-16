package com.ibm.common.utils.http.utils.member;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.MemberUserInfo;
import org.doming.develop.http.httpclient.HttpClientConfig;

/**
 * @Description: 会员爬虫抽象类
 * @Author: zjj
 * @Date: 2020-04-24 16:39
 * @Version: v1.0
 */
public interface IMemberCrawler {
    long MAX_CHECK_TIME = 120 * 1000;
    long MIN_CHECK_TIME = 10 * 1000;

    Integer MAX_RECURSIVE_SIZE = 5;
    /**
     * 放入账户信息
     *
     * @param existHmId       盘口会员存在id
     * @param memberAccount   会员账号
     * @param memberPassword  会员密码
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     */
    void accountInfo(String existHmId, String memberAccount, String memberPassword, String handicapUrl,
                String handicapCaptcha);
    /**
     * 登陆
     *
     * @param existHmId       盘口会员存在id
     * @param memberAccount   会员账号
     * @param memberPassword  会员密码
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     * @return
     */
    JsonResultBeanPlus login(String existHmId, String memberAccount, String memberPassword,
							 String handicapUrl, String handicapCaptcha);
    /**
     * 登陆
     *
     * @param httpConfig       http请求配置类
     * @param accountInfo      账号信息
     * @return
     */
    JsonResultBeanPlus login(HttpClientConfig httpConfig, AccountInfo accountInfo);
    /**
     * 登陆
     * @param existHmId 盘口会员存在id
     * @return
     */
    JsonResultBeanPlus login(String existHmId);
    /**
     * 验证登录
     *
     * @param handicapUrl     盘口地址
     * @param handicapCaptcha 盘口验证码
     * @param memberAccount   会员账号
     * @param memberPassword  会员密码
     * @return
     */
    JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String memberAccount,
                                                 String memberPassword);
    /**
     * 获取用户信息
     *
     * @param existHmId 已存在盘口会员id
     * @param flag      执行状态
     * @return
     */
    MemberUserInfo getUserInfo(String existHmId, boolean flag);
    /**
     * 获取游戏限额信息
     * @param existHmId     已存在盘口会员id
     * @param gameCode      游戏code
     * @return
     */
    JsonResultBeanPlus getQuotaList(String existHmId, GameUtil.Code gameCode);

    /**
     * 检验信息
     *
     * @param existHmId 已存在盘口会员id
     * @return
     */
    JsonResultBeanPlus checkInfo(String existHmId);
}
