package com.common.handicap.crawler;

import com.alibaba.fastjson.JSONObject;
import com.common.handicap.crawler.entity.UserCrawlerImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 抓取盘口
 *
 * @Author: Dongming
 * @Date: 2020-05-11 18:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface GrabHandicap {
	Logger log = LogManager.getLogger(GrabHandicap.class);
	Logger httpLog = LogManager.getLogger("http_log");

	Integer MAX_RECURSIVE_SIZE = 5;
	String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";
	String HTTP_LOG_FORMAT = "线程:{}，请求地址[{}]，请求参数[{}]，循环次数[{}]，请求结果：【{}】";

	/**
	 * 用户画像
	 *
	 * @param userImage 用户画像
	 */
	void userImage(UserCrawlerImage userImage);

	/**
	 * 线程睡眠
	 */
	default void sleep() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			log.info("睡眠失败：{}", e.getMessage());
		}
	}

	/**
	 * 线程睡眠
	 */
	default void longSleep() {
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			log.info("睡眠失败：{}", e.getMessage());
		}
	}

	/**
	 * 将结果转换为json
	 *
	 * @param html 结果界面
	 * @return 结果json
	 */
	default JSONObject parseObject(String html) {
		try {
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("转换结果页面失败【" + html + "】", e);
		}
		return null;
	}
}
