package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import com.ibm.follow.servlet.cloud.ibm_log_monitor_client.entity.IbmLogMonitorClient;
import com.ibm.follow.servlet.cloud.ibm_log_monitor_client.service.IbmLogMonitorClientService;
import com.ibm.follow.servlet.cloud.ibm_log_monitor_servlet.entity.IbmLogMonitorServlet;
import com.ibm.follow.servlet.cloud.ibm_log_monitor_servlet.service.IbmLogMonitorServletService;
import com.ibm.follow.servlet.cloud.ibm_sys_monitor_client.service.IbmSysMonitorClientService;
import com.ibm.follow.servlet.cloud.ibm_sys_monitor_servlet.entity.IbmSysMonitorServlet;
import com.ibm.follow.servlet.cloud.ibm_sys_monitor_servlet.service.IbmSysMonitorServletService;
import com.ibm.follow.servlet.cloud.ibm_sys_thread_pool.service.IbmSysThreadPoolService;
import org.doming.core.tools.*;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
import org.doming.develop.system.bean.DiskInfoBean;
import org.doming.develop.system.bean.MemoryInfoBean;
import org.doming.develop.system.bean.MonitorInfoBean;
import org.doming.develop.system.service.MonitorService;
import org.doming.develop.system.service.impl.MonitorServiceImpl;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 客户端监控
 *
 * @Author: Dongming
 * @Date: 2020-05-14 17:06
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientMonitorController {

	public static long monitorTime = 0;

	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		String clientCode = msgObj.getString("clientCode");
		MonitorInfoBean monitorInfo = JSONObject.parseObject(msgObj.getString("monitorInfo"), MonitorInfoBean.class);
		Date nowTime = new Date();
		/*
			1.存储jvm信息到客户端信息中
			2.存储CPU信息，内存信息到客户机信息中

		 */
		//客户端监控
		MemoryInfoBean memoryInfo = monitorInfo.getMemoryInfo();
		new IbmSysMonitorClientService().update(clientCode, memoryInfo, nowTime);

		//客户端监控日志
		IbmLogMonitorClient monitorClient = new IbmLogMonitorClient();
		monitorClient.setClientCode(clientCode);
		monitorClient.setJvmRate(reckonRate(memoryInfo.getUsedJvmMemory() ,memoryInfo.getTotalJvmMemory()));
		monitorClient.setJvmTotal(memoryInfo.getTotalJvmMemory());
		monitorClient.setJvmMax(memoryInfo.getMaxJvmMemory());
		monitorClient.setJvmFree(memoryInfo.getFreeJvmMemory());
		monitorClient.setCreateTime(nowTime);
		monitorClient.setCreateTimeLong(System.currentTimeMillis());
		monitorClient.setUpdateTime(nowTime);
		monitorClient.setUpdateTimeLong(System.currentTimeMillis());
		monitorClient.setState(IbmStateEnum.OPEN.name());
		new IbmLogMonitorClientService().save(monitorClient);


		//服务器监控
		String ip = new IbmClientService().findIp(clientCode);
		saveMonitorInfo(monitorInfo, IbmMainConfig.Module.CLIENT, ip);

		//记录本机信息
		if (System.currentTimeMillis() - monitorTime > DateTool.getMillisecondsMinutes(5)) {
			synchronized (ClientMonitorController.class) {
				if (System.currentTimeMillis() - monitorTime > DateTool.getMillisecondsMinutes(5)) {
					monitorTime = System.currentTimeMillis();
					//保存监控信息
					MonitorService monitorService = new MonitorServiceImpl();
					monitorInfo = monitorService.getMonitorInfoBean();
					ip = IpTool.getIpExtranet();
					saveMonitorInfo(monitorInfo, IbmMainConfig.Module.MQ, ip);
					//保存线程信息
					ThreadPoolExecutor messageExecutor = RabbitMqUtil.findInstance().getExecutor();
					Map<String, Object> config = ThreadTool.getConfig(messageExecutor);
					config.put("moduleCode", IbmMainConfig.Module.MQ.name());
					config.put("moduleName", IbmMainConfig.Module.MQ.getName());
					config.put("threadCode", "messageExecutor");
					IbmSysThreadPoolService threadPoolService = new IbmSysThreadPoolService();
					threadPoolService.update(ip, config);
				}
			}
		}
		return new JsonResultBeanPlus().success();

	}

	/**
	 * 保存监控信息
	 *
	 * @param monitorInfo 监控信息
	 * @param module      模块
	 * @param ip          IP
	 */
	private void saveMonitorInfo(MonitorInfoBean monitorInfo, IbmMainConfig.Module module, String ip) throws Exception {
		long usable = 0;
		long total = 1;
		for (DiskInfoBean diskInfoBean : monitorInfo.getDiskInfoBeans()) {
			total += diskInfoBean.getTotalSpace();
			usable += diskInfoBean.getUsableSpace();
		}
		MemoryInfoBean memoryInfo = monitorInfo.getMemoryInfo();
		Date nowTime = new Date();
		IbmSysMonitorServletService monitorServletService = new IbmSysMonitorServletService();
		String monitorServletId = monitorServletService.findId(ip, module.name());
		if (StringTool.isEmpty(monitorServletId)) {
			IbmSysMonitorServlet monitorServlet = new IbmSysMonitorServlet();
			monitorServlet.setServerIp(ip);
			monitorServlet.setModuleCode(module.name());
			monitorServlet.setModuleName(module.getName());
			monitorServlet.setCpuRate(monitorInfo.getCpuInfo().getCpuRatio());
			monitorServlet.setJvmRate(0);
			monitorServlet.setRamRate(reckonRate(memoryInfo.getUsedPhysicalMemory() , memoryInfo.getTotalPhysicalMemory()));
			monitorServlet.setDiskRate(reckonRate(usable ,total));
			monitorServlet.setCreateTime(nowTime);
			monitorServlet.setCreateTimeLong(System.currentTimeMillis());
			monitorServlet.setUpdateTime(nowTime);
			monitorServlet.setUpdateTimeLong(System.currentTimeMillis());
			monitorServlet.setState(IbmStateEnum.OPEN.name());
			monitorServletService.save(monitorServlet);
		} else {
			monitorServletService.update(monitorServletId, monitorInfo.getCpuInfo().getCpuRatio(), 0, reckonRate(memoryInfo.getUsedPhysicalMemory() , memoryInfo.getTotalPhysicalMemory()), reckonRate(usable ,total), nowTime);
		}

		//服务器监控日志
		IbmLogMonitorServlet monitorServlet = new IbmLogMonitorServlet();
		monitorServlet.setServerIp(ip);
		monitorServlet.setModuleCode(module.name());
		monitorServlet.setModuleName(module.getName());
		monitorServlet.setCpuRate(monitorInfo.getCpuInfo().getCpuRatio());
		monitorServlet.setJvmRate(reckonRate(memoryInfo.getUsedJvmMemory(),memoryInfo.getTotalJvmMemory()));
		monitorServlet.setJvmTotal(memoryInfo.getTotalJvmMemory());
		monitorServlet.setJvmMax(memoryInfo.getMaxJvmMemory());
		monitorServlet.setJvmFree(memoryInfo.getFreeJvmMemory());
		monitorServlet.setRamRate(reckonRate(memoryInfo.getUsedPhysicalMemory(),memoryInfo.getTotalPhysicalMemory()));

		for (int i = 0; i < monitorInfo.getDiskInfoBeans().size(); i++) {
			DiskInfoBean diskInfoBean = monitorInfo.getDiskInfoBeans().get(i);
			ClassTool.set(monitorServlet, "setDisk" + (i + 1) + "Rate", reckonRate(diskInfoBean.getUsableSpace(),diskInfoBean.getTotalSpace()));
			ClassTool.set(monitorServlet, "setDisk" + (i + 1) + "Total", diskInfoBean.getTotalSpace());
			ClassTool.set(monitorServlet, "setDisk" + (i + 1) + "Free", diskInfoBean.getFreeSpace());
		}

		monitorServlet.setCreateTime(nowTime);
		monitorServlet.setCreateTimeLong(System.currentTimeMillis());
		monitorServlet.setUpdateTime(nowTime);
		monitorServlet.setUpdateTimeLong(System.currentTimeMillis());
		monitorServlet.setState(IbmStateEnum.OPEN.name());
		new IbmLogMonitorServletService().save(monitorServlet);

	}

	private String reckonRate(long usedLong, long totalLong) {
		double result = NumberTool.getDouble(usedLong) / NumberTool.getDouble(totalLong);
		NumberFormat nf = NumberFormat.getPercentInstance();
		//设置保留几位小数
		nf.setMaximumFractionDigits(2);
		return nf.format(result);
	}
}
