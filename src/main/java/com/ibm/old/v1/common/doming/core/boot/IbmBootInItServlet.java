package com.ibm.old.v1.common.doming.core.boot;
import c.a.config.SysConfig;
import c.a.config.core.ContextThreadLocal;
import c.a.config.core.ContextUtil;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import c.a.util.job.QuartzUtil;
import com.ibm.old.v1.client.core.controller.manage.LogoutClientController;
import com.ibm.old.v1.client.core.controller.manage.RegisterClientController;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import com.ibm.old.v1.common.doming.utils.mq.RabbitMqIbmUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.concurrent.ExecutorService;
/**
 * @Description: 初始化智能投注信息
 * @Author: Dongming
 * @Date: 2019-01-28 11:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
//@WebServlet(name = "IbmBootInItServlet", loadOnStartup = 2, urlPatterns = {})
public class IbmBootInItServlet
		extends HttpServlet {

	protected Logger log = LogManager.getLogger(this.getClass());
	private boolean isStart = false;
	private String clientId;

	@Override public void init() throws ServletException {
		super.init();
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		try {
			String start = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("rabbitmq.ibm.start"), "false");
			isStart = Boolean.parseBoolean(start);
			if (!isStart) {
				return;
			}
			String server = BeanThreadLocal.find(SysConfig.findInstance().findMap().get("rabbitmq.ibm.server"), "");
			if ("client".equals(server)) {
				clientId = BeanThreadLocal
						.find(SysConfig.findInstance().findMap().get("rabbitmq.ibm.id"), RandomTool.getNumLetter32());

				RabbitMqIbmUtil.receiveExchange4Manage(clientId);

				//将注册的客户机编号发送到中心端
				new RegisterClientController().execute(clientId);
				//开启清理冗余数据定时器
				QuartzIbmUtil.clientDataCleaning();

			}
			log.info("初始化智能投注信息完成");

		} catch (Exception e) {
			log.error("初始化智能投注信息错误", e);
		}
		contextUtil.remove();

	}

	@Override public void destroy() {
		super.destroy();
		if (isStart) {
			if (StringTool.notEmpty(clientId)) {
				try {
					new LogoutClientController().execute(clientId);
				} catch (Exception e) {
					log.error("关闭客户端失败", e);
				}
			}
			try {
				RabbitMqIbmUtil.destroy();
				HttpClientUtil.destroy();
				QuartzIbmUtil.destroy();

			} catch (Exception e) {
				e.printStackTrace();
				log.error("销毁智能投注信息失败", e);
			}
			log.info("销毁智能投注信息完成");
		}
		try {
			//停止quartz
			if(QuartzUtil.getScheduler() != null){
				QuartzUtil quartzUtil = QuartzUtil.findInstance();
				if (quartzUtil.doSchedulerIsStart()) {
					quartzUtil.doSchedulerPauseAll();
					quartzUtil.doSchedulerShutdow(true);
					log.info("关闭定时器完成");
				}
			}
			if (ThreadPoolExecutorServiceListUtil.getMap() != null){
				ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
				ExecutorService executorService = threadExecutorService.findExecutorService();
				executorService.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
