package com.ibs.plan.module.client.core.job;
import com.alibaba.fastjson.JSONObject;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.plan.common.core.servlet.boot.IbsModuleListener;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.client.ibsc_config.service.IbscConfigService;
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.DateTool;
import org.doming.develop.system.bean.MonitorInfoBean;
import org.doming.develop.system.service.MonitorService;
import org.doming.develop.system.service.impl.MonitorServiceImpl;
import org.quartz.JobExecutionContext;

import java.util.Map;
/**
 *  客户端心跳检测
 *
 * @Author: Dongming
 * @Date: 2020-05-09 14:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientHeartbeatJob  extends BaseCommJob {

	public static long monitorTime = 0;

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		//客户机定时推送服务器状态信息

		// 获取容量信息
		Map<String, Object>  capacity = new IbscConfigService().capacity();

		// 获取盘口会员状态
		Map<String, Map<String,Object>> exitsHmInfo = new IbscExistHmService().mapExitsInfo();

		JSONObject message = new JSONObject();

		JSONObject data=new JSONObject();
		data.put("capacity", capacity);
		data.put("exitsHmInfo", exitsHmInfo);
		message.put("METHOD_", IbsMethodEnum.CLIENT_HEARTBEAT.name());
		message.put("data", data);
		message.put("clientCode", IbsModuleListener.servletCode());
		//发送校验信息
		RabbitMqTool.sendInfo(message.toJSONString(), "client");


		// 发送监控信息
		if (System.currentTimeMillis() - monitorTime < DateTool.getMillisecondsMinutes(5)) {
			return;
		}
		message.clear();
		//监控信息
		MonitorService monitorService = new MonitorServiceImpl();
		MonitorInfoBean monitorInfo = monitorService.getMonitorInfoBean();

		message.put("METHOD_", IbsMethodEnum.CLIENT_MONITOR.name());
		message.put("clientCode", IbsModuleListener.servletCode());
		message.put("monitorInfo", monitorInfo);
		RabbitMqTool.sendInfo(message.toJSONString(), "client");
		monitorTime = System.currentTimeMillis();


	}
}
