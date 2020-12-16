package com.ibm.common.utils.http.utils.member2;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.game.GameUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 会员工具类
 * @Author: null
 * @Date: 2019-12-27 18:20
 * @Version: v1.0
 */
public abstract class BaseMemberUtil2 {
	protected Logger log = LogManager.getLogger(this.getClass());

	public static final long MAX_CHECK_TIME = 120 * 1000;
	public static final long MIN_CHECK_TIME = 10 * 1000;

	public static final Integer MAX_RECURSIVE_SIZE = 5;

	public Map<String, HttpClientConfig> hcConfigMap;
	/**
	 * 账户名-密码-网址-验证码
	 */
	public Map<String, Map<String, String>> accountMap;
	/**
	 * 用户信息-余额-盈亏额度
	 */
	public Map<String, Map<String, String>> userMap;
	/**
	 * projectHost：主机地址
	 */
	public Map<String, Map<String, String>> memberMap;
	/**
	 * 上次成功检验时间
	 */
	public Map<String, Long> checkMap;
	/**
	 * 赔率信息
	 */
	public Map<String, Map<GameUtil.Code, Map<String, Object>>> oddsMap;

	public void init() {
		hcConfigMap = new HashMap<>(10);
		userMap = new HashMap<>(10);
		accountMap = new HashMap<>(10);
		memberMap = new HashMap<>(10);
		oddsMap = new HashMap<>(10);
		checkMap = new HashMap<>(10);
	}

	/**
	 * 清除盘口会员用户数据
	 *
	 * @param existHmId 已存在盘口会员用户id
	 */
	public void removeHmInfo(String existHmId) {
		if (StringTool.isEmpty(existHmId)) {
			return;
		}
		userMap.remove(existHmId);
		accountMap.remove(existHmId);
		hcConfigMap.remove(existHmId);
		memberMap.remove(existHmId);
		oddsMap.remove(existHmId);
		checkMap.remove(existHmId);
	}

	/**
	 * 放入账户信息
	 *
	 * @param existHmId       盘口会员存在id
	 * @param memberAccount   会员账号
	 * @param memberPassword  会员密码
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 */
	public void accountInfo(String existHmId, String memberAccount, String memberPassword, String handicapUrl,
			String handicapCaptcha) {
		if (accountMap.containsKey(existHmId)) {
			Map<String, String> data = accountMap.get(existHmId);
			data.put("memberAccount", memberAccount);
			data.put("memberPassword", memberPassword);
			data.put("handicapUrl", handicapUrl);
			data.put("handicapCaptcha", handicapCaptcha);
		} else {
			Map<String, String> data = new HashMap<>(4);
			data.put("memberAccount", memberAccount);
			data.put("memberPassword", memberPassword);
			data.put("handicapUrl", handicapUrl);
			data.put("handicapCaptcha", handicapCaptcha);
			accountMap.put(existHmId, data);
		}
	}

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
	public abstract JsonResultBeanPlus login(String existHmId, String memberAccount, String memberPassword,
											 String handicapUrl, String handicapCaptcha);

	/**
	 * 验证登录
	 *
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @param memberAccount   会员账号
	 * @param memberPassword  会员密码
	 * @return
	 */
	public abstract JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String memberAccount,
			String memberPassword);

	/**
	 * 获取用户信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param flag      执行状态
	 * @return
	 */
	public abstract Map<String, String> getUserInfo(String existHmId, boolean flag);

	/**
	 * 检验信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return
	 */
	public abstract JsonResultBeanPlus checkInfo(String existHmId);

    /**
     * 获取游戏限额信息
     * @param existHmId     已存在盘口会员id
     * @param gameCode      游戏code
     * @return
     */
    public abstract JsonResultBeanPlus getQuotaList(String existHmId, GameUtil.Code gameCode);

}
