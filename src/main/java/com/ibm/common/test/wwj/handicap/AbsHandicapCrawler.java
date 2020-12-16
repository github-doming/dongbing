package com.ibm.common.test.wwj.handicap;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.Map;
/**
 * @Description: 盘口爬虫 预实现类
 * @Author: Dongming
 * @Date: 2019-11-22 10:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class AbsHandicapCrawler<T extends AbsHandicapGrab> implements CrawlerHandicap {

	protected Logger log = LogManager.getLogger(this.getClass());

	DataCustomer customer;
	protected T grab;

	private IbmTypeEnum customerType;
	private HandicapUtil.Code handicap;

	/**
	 * 登陆
	 *
	 * @param httpConfig      请求配置类
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @param account         盘口账户
	 * @param password        盘口密码
	 * @return 登录结果
	 */
	protected abstract JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
												String account, String password);

	/**
	 * 传递数据钩子
	 *
	 * @param customer 客户信息
	 */
	protected abstract void customerHold(DataCustomer customer);
	/**
	 * 带锁登录，不允许同时调用-如果前一次登录成功，则直接返回结果
	 */
	@Override public synchronized JsonResultBeanPlus login() throws HandicapException {
		if (customer == null) {
			throw new HandicapException(getMsgTitle() + "不存在用户数据，无法登录");

		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (customer.crawler() == null) {
			return login(customer.account(), customer.password(), customer.handicapUrl(), customer.handicapCaptcha());
		}

		return bean.success();
	}

	/**
	 * 提供参数登方法
	 */
	@Override public JsonResultBeanPlus login(String handicapUrl, String handicapCaptcha, String account,
			String password) throws HandicapException {
		if (customer == null) {
			throw new HandicapException(getMsgTitle() + "不存在用户数据，无法登录");
		}
		if (StringTool.isEmpty(customer.existId())){
			throw new HandicapException(getMsgTitle() + "该爬虫没有绑定客户，无法登录");
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (customer.crawler() != null) {
			bean.success();
			return bean;
		} else {
			customer.source(account, password, handicapUrl, handicapCaptcha);
		}
		try {
			//获取配置类
			bean = login(httpConfig(), handicapUrl, handicapCaptcha, account, password);
			if (!bean.isSuccess()) {
				return bean;
			}

			//存储登录后爬虫
			customer.crawler((Map<String, String>) bean.getData());

			//传递信息钩子
			customerHold(customer);

			//传递http配置到爬虫抓取类中
			if (grab.httpConfig() == null){
				grab.httpConfig(httpConfig());
				grab.existId(customer.existId());
			}

			bean.setData(null);
		} catch (Exception e) {
			log.error(getMsgTitle() + "登录失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 登录成功则记录数据
	 */
	@Override public JsonResultBeanPlus verifyLogin(String handicapUrl, String handicapCaptcha, String account,
			String password) {
		HttpClientConfig clientConfig = new HttpClientConfig();
		clientConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		JsonResultBeanPlus bean = login(clientConfig, handicapUrl, handicapCaptcha, account, password);
		if (bean.isSuccess()) {
			String existId = RandomTool.getNumLetter32();
			//存储账号信息
			customer = new DataCustomer(existId);

			//存储登录后爬虫
			customer.crawler((Map<String, String>) bean.getData());

			//存储http请求信息
			customer.httpConfig(clientConfig);

			//传递信息钩子
			customerHold(customer);

			//传递http配置到爬虫抓取类中
			grab.httpConfig(httpConfig());
			grab.existId(existId);


			bean.setData(existId);
		}
		return bean;
	}

	/**
	 * 获取http请求配置信息
	 *
	 * @return http请求配置信息
	 */
	protected final HttpClientConfig httpConfig() {
		if (customer.httpConfig() == null) {
			synchronized (AbsHandicapCrawler.class) {
				if (customer.httpConfig() == null) {
					HttpClientConfig httpConfig = new HttpClientConfig();
					httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
					customer.httpConfig(httpConfig);
				}
			}
		}
		return customer.httpConfig();
	}

	/**
	 * 设置存在主键
	 *
	 * @param existId 存在主键
	 */
	@Override public boolean putExistId(String existId) {
		if (customer == null) {
			customer = new DataCustomer(existId);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 放入爬虫抓取类
	 *
	 * @param grab 爬虫抓取类
	 * @return 放入结果
	 */
	public boolean putPort(T grab) {
		if (this.grab != null) {
			return false;
		}
		this.grab = grab;
		return true;
	}

	protected final AbsHandicapCrawler customerType(IbmTypeEnum customerType) {
		this.customerType = customerType;
		return this;
	}

	protected final AbsHandicapCrawler handicap(HandicapUtil.Code handicap) {
		this.handicap = handicap;
		return this;
	}

	protected String getMsgTitle() {
		if (customer == null) {
			return String.format("{%s}盘口%s", handicap.name(), customerType.getMsg());
		}
		return String.format("{%s}盘口%s主键[%s]", handicap.name(), customerType.getMsg(), customer.existId());
	}
}
