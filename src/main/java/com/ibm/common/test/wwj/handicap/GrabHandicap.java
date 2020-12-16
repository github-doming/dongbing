package com.ibm.common.test.wwj.handicap;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.Map;
/**
 * @Description: 盘口爬虫抓取类
 * @Author: Dongming
 * @Date: 2019-11-22 10:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface GrabHandicap {

	/**
	 * 根据盘口导航地址和验证码打开获取线路界面
	 *
	 * @param httpConfig      请求配置
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 获取线路界面
	 */
	String selectRoute(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, int... index);

	/**
	 * 根据线路页面解析的到线路并完成测速排序后输出
	 *
	 * @param httpConfig 请求配置
	 * @param routeHtml  获取线路界面
	 * @param index      循环次数
	 * @return 测速后的线路数组
	 */
	String[] routes(HttpClientConfig httpConfig, String routeHtml, int... index);

	/**
	 * 获取登录信息
	 *
	 * @param httpConfig 请求配置
	 * @param routes     线路数组
	 * @param index      循环次数
	 * @return 登录信息
	 */
	Map<String, String> loginInfo(HttpClientConfig httpConfig, String[] routes, int... index);

	/**
	 * 登录
	 *
	 * @param account    盘口账号
	 * @param password   盘口密码
	 * @param verifyCode 验证码
	 * @param loginUrl   登录地址
	 * @return 登录结果
	 */
	String login(HttpClientConfig httpConfig, String loginUrl,String verifyCode,String account, String password);

	/**
	 * 进入主页面
	 *
	 * @param homeUrl 主页面地址
	 * @return 主页面
	 */
	String home(HttpClientConfig httpConfig,String homeUrl);




}
