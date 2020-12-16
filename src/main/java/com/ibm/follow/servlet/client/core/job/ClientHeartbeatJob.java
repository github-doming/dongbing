package com.ibm.follow.servlet.client.core.job;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.servlet.boot.IbmModuleListener;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.client.ibmc_config.service.IbmcConfigService;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.DateTool;
import org.doming.develop.system.bean.MonitorInfoBean;
import org.doming.develop.system.service.MonitorService;
import org.doming.develop.system.service.impl.MonitorServiceImpl;
import org.quartz.JobExecutionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 客户端 心跳检测
 * @Author: Dongming
 * @Date: 2019-08-27 10:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientHeartbeatJob extends BaseCommJob {

	public static long monitorTime = 0;

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		//服务器定时推送服务器状态信息

		// 获取容量信息
		Map<String, Object> capacityMax = new IbmcConfigService().findAllMaxCapacity();

		// 获取盘口代理状态
		Map<String, Object> exitsHaInfo = new IbmcExistHaService().findExitsInfo();

		// 获取盘口会员状态
		Map<String, Object> exitsHmInfo = new IbmcExistHmService().findExitsInfo();

		JSONObject message = new JSONObject();

		Map<String, Map<String, Object>> data = new HashMap<>(3);
		data.put("capacityMax", capacityMax);
		data.put("exitsHaInfo", exitsHaInfo);
		data.put("exitsHmInfo", exitsHmInfo);
		message.put("method", IbmMethodEnum.CLIENT_HEARTBEAT.name());
		message.put("data", data);
		message.put("clientCode", IbmModuleListener.servletCode());

		//发送校验信息
		RabbitMqTool.sendInfoReceipt(message.toJSONString(), "client");

		// 发送监控信息
		if (System.currentTimeMillis() - monitorTime < DateTool.getMillisecondsMinutes(5)) {
			return;
		}
		MonitorService monitorService = new MonitorServiceImpl();
		MonitorInfoBean monitorInfo = monitorService.getMonitorInfoBean();

		message.clear();
		message.put("method", IbmMethodEnum.CLIENT_MONITOR.name());
		message.put("clientCode", IbmModuleListener.servletCode());
		message.put("monitorInfo", monitorInfo);
		RabbitMqTool.sendInfoReceipt(message.toJSONString(), "client");
		monitorTime = System.currentTimeMillis();
	}
}
