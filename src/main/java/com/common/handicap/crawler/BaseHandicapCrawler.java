package com.common.handicap.crawler;

import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.TypeEnum;
import com.common.handicap.crawler.entity.HandicapUser;
import com.common.handicap.crawler.entity.UserCrawlerImage;
import com.common.util.BaseHandicapUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.AssertTool;
import org.doming.core.tools.RandomTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 盘口爬虫预实现类
 *
 * @Author: Dongming
 * @Date: 2020-05-09 16:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseHandicapCrawler<T extends GrabHandicap> implements CrawlerHandicap {

	protected static final Logger log = LogManager.getLogger(BaseHandicapCrawler.class);

	/**
	 * 用户爬虫画像
	 * 记录着用户的爬虫信息，基础信息
	 */
	private UserCrawlerImage userImage;
	/**
	 * 爬虫抓取类
	 * 所有的抓取操作都要靠它实现 之前的TOOL
	 */
	protected T crawlerGrab;
	protected TypeEnum customerType;
	protected BaseHandicapUtil.Code handicapCode;

	protected Lock lock = new ReentrantLock();

	BaseHandicapCrawler() {
		crawlerGrab = crawlerGrab();
	}

	/**
	 * 登陆
	 *
	 * @param httpConfig  http请求配置对象
	 * @param accountInfo 登录请求参数
	 * @return 登录结果
	 */
	protected abstract JsonResultBeanPlus login(HttpClientConfig httpConfig, HandicapUser accountInfo);

	/**
	 * 传递用户画像钩子
	 *
	 * @param userImage 用户画像
	 */
	abstract void userImageHolder(UserCrawlerImage userImage);

	/**
	 * 会员抓取
	 *
	 * @return 会员抓取
	 */
	protected abstract T crawlerGrab();

	/**
	 * 到这里都是要登录的不需要什么判断
	 */
	@Override public JsonResultBeanPlus login(String existId, String handicapUrl, String handicapCaptcha,
			String account, String password) {
		if (userImage == null) {
			userImage = new UserCrawlerImage(existId);
			return login(handicapUrl, handicapCaptcha, account, password);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		bean.putSysEnum(CodeEnum.CODE_403);
		bean.putEnum(CodeEnum.IBS_403_EXIST);
		return bean;
	}

	/**
	 * 到这里都是要登录的不需要什么判断
	 *
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @param account         盘口账户
	 * @param password        盘口密码
	 * @return 登录结果
	 */
	@Override public JsonResultBeanPlus login(String handicapUrl, String handicapCaptcha, String account,
			String password) {
		AssertTool.notNull(userImage, getMsgTitle() + "不存在用户爬虫画像，无法登录");
		AssertTool.notEmpty(userImage.existId(), getMsgTitle() + "不存在用户爬虫画像，无法登录");
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//更新登录信息
		userImage.handicapInfo(handicapUrl, handicapCaptcha, account, password);
		try {
			//去登录
			bean = login(userImage.httpConfig(), userImage.accountInfo());
			if (bean.notSuccess()) {
				return bean;
			}
			//更新登录结果
			userImage.loginCrawlerInfo((Map<String, String>) bean.getData());
			//传递正确的用户画像
			userImageHolder(userImage);

			bean.setData(null);
		} catch (Exception e) {
			log.error(getMsgTitle() + "登录失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 抛开用户画像的登录，自主产生爬虫对象
	 */
	@Override public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String account,
			String password) {
		AssertTool.isNull(userImage, getMsgTitle() + "已存在用户爬虫画像信息,不能进行验证登录");
		HttpClientConfig clientConfig = new HttpClientConfig();
		clientConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		HandicapUser accountInfo = new HandicapUser(handicapUrl, handicapCaptcha, account, password);
		JsonResultBeanPlus bean = login(clientConfig, accountInfo);
		if (bean.isSuccess()) {
			String existId = RandomTool.getNumLetter32();
			//存储账号信息
			userImage = new UserCrawlerImage(existId);

			userImage.loginCrawlerInfo((Map<String, String>) bean.getData());
			//存储http请求信息
			userImage.httpConfig(clientConfig);
			userImage.accountInfo(accountInfo);
			//传递正确的用户画像
			userImageHolder(userImage);

			bean.setData(existId);
		}
		return bean;
	}

	/**
	 * 放入用户信息
	 */
	@Override public void userInfo(String existId, String handicapUrl, String handicapCaptcha, String account,
			String password) {
		//存储账号信息
		userImage = new UserCrawlerImage(existId);
		userImage.handicapInfo(handicapUrl, handicapCaptcha, account, password);
		//传递正确的用户画像
		userImageHolder(userImage);
		//需要登录
		needLogin();

	}

	/**
	 * 加锁登录 - 如果锁内部判断需要登录，则重新登录，否则直接返回成功
	 *
	 * @return 登录结果
	 */
	protected JsonResultBeanPlus login() {
		lock.lock();
		try {
			AssertTool.notNull(userImage, getMsgTitle() + "不存在用户爬虫画像，无法登录");
			if (userImage.needLogin()) {
				AssertTool.notNull(userImage.accountInfo(), getMsgTitle() + "不存在用户盘口信息，无法登录");
				HandicapUser accountInfo = userImage.accountInfo();
				return login(accountInfo.handicapUrl(), accountInfo.handicapCaptcha(), accountInfo.account(),
						accountInfo.password());
			}
			return JsonResultBeanPlus.successConstant();
		} finally {
			lock.unlock();
		}
	}

	public void needLogin() {
		userImage.needLogin(true);
	}

	protected String accountName() {
		return userImage.accountInfo().account();
	}

	/**
	 * 获取消息头
	 *
	 * @return 消息头
	 */
	private String getMsgTitle() {
		if (userImage == null) {
			return String.format("[%s]盘口%s,", handicapCode.getName(), customerType.getMsg());
		}
		return String.format("[%s]盘口%s主键[%s],", handicapCode.getName(), customerType.getMsg(), userImage.existId());
	}

	/**
	 * 获取消息头
	 *
	 * @return 消息头
	 */
	protected String getLogTitle() {
		return getMsgTitle() + "{}";
	}
}
