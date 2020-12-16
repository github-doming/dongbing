package com.ibm.common.test.wwj.handicap;
import com.alibaba.fastjson.JSON;
import com.ibm.common.test.wwj.handicap.logger.HandicapLogger;
import com.ibm.common.test.wwj.handicap.logger.OptionLogger;
import com.ibm.common.test.wwj.handicap.logger.SaveLogThread;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.ThreadExecuteUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-11-27 10:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class AbsHandicapGrab implements GrabHandicap {

	protected static final Logger log = LogManager.getLogger(GrabHandicap.class);

	protected static final Integer MAX_RECURSIVE_SIZE = 5;
	protected static final Long SLEEP = 500L;
	protected static final Long LONG_SLEEP = 1000L;
	private static volatile ThreadPoolExecutor executor = null;

	private HttpClientConfig httpConfig;
	private HttpClientUtil util;
	private HandicapUtil.Code handicap;
	private String existId;

	public AbsHandicapGrab( HandicapUtil.Code handicap) {
		this.handicap = handicap;
		this.util = HttpClientUtil.findInstance();
	}
	/**
	 * 睡眠
	 *
	 * @param msg 失败消息
	 */
	protected void sleep(String msg) {
		sleep(msg, SLEEP);
	}

	/**
	 * 睡眠
	 *
	 * @param msg 失败消息
	 */
	protected void longSleep(String msg) {
		sleep(msg, LONG_SLEEP);
	}

	/**
	 * 睡眠
	 *
	 * @param msg  失败消息
	 * @param time 睡眠时间
	 */
	protected void sleep(String msg, long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			log.error(msg + "睡眠失败", e);
		}
	}

	public HttpClientConfig httpConfig() {
		return httpConfig;
	}
	public AbsHandicapGrab httpConfig(HttpClientConfig httpConfig) {
		this.httpConfig = httpConfig;
		return this;
	}
	public String existId() {
		return existId;
	}
	public AbsHandicapGrab existId(String existId) {
		this.existId = existId;
		return this;
	}

	/**
	 * 获取页面信息
	 * @param httpConfig http请求配置类
	 * @param type 请求类型
	 * @return 页面
	 */
	public String html(HttpClientConfig httpConfig, HttpType type) {
		String html = "";
		try {
			html = util.sendHtml(httpConfig);
		} finally {
			if (type == HttpType.Normal) {
				// 存储错误信息 - 每个盘口单独存储
				HandicapLogger logger = new HandicapLogger(httpConfig.url(), JSON.toJSONString(httpConfig.map()), html,
						  handicap);
				getLogExecutor().execute(new SaveLogThread().logger(logger));
			} else {
				// 以 existId 为表存储
				OptionLogger logger = new OptionLogger(httpConfig.url(), JSON.toJSONString(httpConfig.map()), html,
						  existId,handicap, type);
				getLogExecutor().execute(new SaveLogThread().logger(logger));
			}
		}

		return html;
	}
	/**
	 * 获取页面信息
	 * @param httpConfig http请求配置类
	 * @param type 请求类型
	 * @return 页面
	 */
	public HttpResult httpResult(HttpClientConfig httpConfig, HttpType type) {
		HttpResult result = null;
		try {
			result = util.send(httpConfig);
		} finally {
			if (type == HttpType.Normal) {
				// 存储错误信息 - 每个盘口单独存储
				HandicapLogger logger = new HandicapLogger(httpConfig.url(), JSON.toJSONString(httpConfig.map()),result, handicap);
				getLogExecutor().execute(new SaveLogThread().logger(logger));
			} else {
				// 以 existId 为表存储
				OptionLogger logger = new OptionLogger(httpConfig.url(), JSON.toJSONString(httpConfig.map()),result, existId,handicap, type);
				getLogExecutor().execute(new SaveLogThread().logger(logger));
			}
		}

		return result;
	}

	/**
	 * 获取图片base64字符串
	 *
	 * @param config 请求参数配置
	 * @return base64字符串
	 */
	public String getImage(HttpClientConfig config) throws IOException {
		String image = null;
		HttpResponse httpResponse = null;
		try {
			httpResponse = util.execute(config.method(HttpConfig.Method.GET));
			if (httpResponse == null) {
				return null;
			}
			image = httpResponse2Image(httpResponse);
		} catch (IOException e) {
			log.error("获取请求结果页面失败", e);
		} finally {
			if (httpResponse != null) {
				//如果CloseableHttpResponse 是resp的父类，则支持关闭
				if (CloseableHttpResponse.class.isAssignableFrom(httpResponse.getClass())) {
					((CloseableHttpResponse) httpResponse).close();
				}
			}
		}
		return image;
	}

	/**
	 * 获取请求结果，將圖片進行base64轉換
	 *
	 * @param httpResponse 请求结果
	 * @return base64字符串
	 */
	private String httpResponse2Image(HttpResponse httpResponse) throws IOException {
		String image;
		try {
			if (httpResponse.getEntity() != null) {
				// 將结果实体转换为base64字符串类型
				byte[] body = EntityUtils.toByteArray(httpResponse.getEntity());
				BASE64Encoder encoder = new BASE64Encoder();
				image = encoder.encode(body);
			} else {//有可能是head请求
				image = httpResponse.getStatusLine().toString();
			}
		} catch (IOException e) {
			log.error("获取请求结果页面失败,IO错误");
			throw e;
		}
		return image;
	}


	/**
	 * 获取保存日志的线程池执行器
	 *
	 * @return 线程池执行器
	 */
	private ThreadPoolExecutor getLogExecutor() {
		return ThreadExecuteUtil.findInstance().getLogExecutor();
	}

}
