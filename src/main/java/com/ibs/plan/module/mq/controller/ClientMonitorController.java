package com.ibs.plan.module.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.core.configs.PlanMainConfig;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import com.ibs.plan.module.cloud.ibsp_log_monitor_client.entity.IbspLogMonitorClient;
import com.ibs.plan.module.cloud.ibsp_log_monitor_client.service.IbspLogMonitorClientService;
import com.ibs.plan.module.cloud.ibsp_log_monitor_servlet.entity.IbspLogMonitorServlet;
import com.ibs.plan.module.cloud.ibsp_log_monitor_servlet.service.IbspLogMonitorServletService;
import com.ibs.plan.module.cloud.ibsp_sys_monitor_client.service.IbspSysMonitorClientService;
import com.ibs.plan.module.cloud.ibsp_sys_monitor_servlet.entity.IbspSysMonitorServlet;
import com.ibs.plan.module.cloud.ibsp_sys_monitor_servlet.service.IbspSysMonitorServletService;
import org.doming.core.tools.ClassTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.system.bean.DiskInfoBean;
import org.doming.develop.system.bean.MemoryInfoBean;
import org.doming.develop.system.bean.MonitorInfoBean;

import java.util.Date;
/**
 * 客户端监控
 * @Author: Dongming
 * @Date: 2020-05-22 11:28
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientMonitorController {
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		String clientCode = msgObj.getString("clientCode");
		MonitorInfoBean monitorInfo = JSONObject.parseObject(msgObj.getString("monitorInfo"), MonitorInfoBean.class);


		Date nowTime = new Date();
		MemoryInfoBean memoryInfo = monitorInfo.getMemoryInfo();
		new IbspSysMonitorClientService().update(clientCode, memoryInfo, nowTime);

		//客户端监控日志
		IbspLogMonitorClient monitorClient = new IbspLogMonitorClient();
		monitorClient.setClientCode(clientCode);
		monitorClient.setJvmRate(memoryInfo.getUsedJvmMemory() / memoryInfo.getTotalJvmMemory());
		monitorClient.setJvmTotal(memoryInfo.getTotalJvmMemory());
		monitorClient.setJvmMax(memoryInfo.getMaxJvmMemory());
		monitorClient.setJvmFree(memoryInfo.getFreeJvmMemory());
		monitorClient.setCreateTime(nowTime);
		monitorClient.setCreateTimeLong(System.currentTimeMillis());
		monitorClient.setUpdateTime(nowTime);
		monitorClient.setUpdateTimeLong(System.currentTimeMillis());
		monitorClient.setState(IbsStateEnum.OPEN.name());
		new IbspLogMonitorClientService().save(monitorClient);


		JsonResultBeanPlus bean=new JsonResultBeanPlus();
		//服务器监控
		String ip = new IbspClientService().findIp(clientCode);
		if(ContainerTool.isEmpty(ip)){
			bean.putEnum(CodeEnum.IBS_403_DATA_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		saveMonitorInfo(monitorInfo, ip);

		return bean.success();
	}
	private void saveMonitorInfo(MonitorInfoBean monitorInfo, String ip)
			throws Exception {
		long usable = 0;
		long total = 1;
		for (DiskInfoBean diskInfoBean : monitorInfo.getDiskInfoBeans()) {
			total += diskInfoBean.getTotalSpace();
			usable += diskInfoBean.getUsableSpace();
		}
		MemoryInfoBean memoryInfo = monitorInfo.getMemoryInfo();
		Date nowTime = new Date();
		IbspSysMonitorServletService monitorServletService = new IbspSysMonitorServletService();
		String monitorServletId = monitorServletService.findId(ip, PlanMainConfig.Module.CLIENT.name());
		if (StringTool.isEmpty(monitorServletId)) {
			IbspSysMonitorServlet monitorServlet = new IbspSysMonitorServlet();
			monitorServlet.setServerIp(ip);
			monitorServlet.setModuleCode(PlanMainConfig.Module.CLIENT.name());
			monitorServlet.setModuleName(PlanMainConfig.Module.CLIENT.getName());
			monitorServlet.setCpuRate(monitorInfo.getCpuInfo().getCpuRatio());
			monitorServlet.setJvmRate(0);
			monitorServlet.setRamRate(memoryInfo.getUsedPhysicalMemory() / memoryInfo.getTotalPhysicalMemory());
			monitorServlet.setDiskRate(usable / total);
			monitorServlet.setCreateTime(nowTime);
			monitorServlet.setCreateTimeLong(System.currentTimeMillis());
			monitorServlet.setUpdateTime(nowTime);
			monitorServlet.setUpdateTimeLong(System.currentTimeMillis());
			monitorServlet.setState(IbsStateEnum.OPEN.name());
			monitorServletService.save(monitorServlet);
		} else {
			monitorServletService.update(monitorServletId, monitorInfo.getCpuInfo().getCpuRatio(), 0, memoryInfo.getUsedPhysicalMemory() / memoryInfo.getTotalPhysicalMemory(), usable / total, nowTime);
		}

		//服务器监控日志
		IbspLogMonitorServlet monitorServlet = new IbspLogMonitorServlet();
		monitorServlet.setServerIp(ip);
		monitorServlet.setModuleCode(PlanMainConfig.Module.CLIENT.name());
		monitorServlet.setModuleName(PlanMainConfig.Module.CLIENT.getName());
		monitorServlet.setCpuRate(monitorInfo.getCpuInfo().getCpuRatio());
		monitorServlet.setJvmRate(memoryInfo.getUsedJvmMemory() / memoryInfo.getTotalJvmMemory());
		monitorServlet.setJvmTotal(memoryInfo.getTotalJvmMemory());
		monitorServlet.setJvmMax(memoryInfo.getMaxJvmMemory());
		monitorServlet.setJvmFree(memoryInfo.getFreeJvmMemory());
		monitorServlet.setRamRate(memoryInfo.getUsedPhysicalMemory() / memoryInfo.getTotalPhysicalMemory());

		for (int i = 0; i < monitorInfo.getDiskInfoBeans().size(); i++) {
			DiskInfoBean diskInfoBean = monitorInfo.getDiskInfoBeans().get(0);
			ClassTool.set(monitorServlet, "setDisk" + (i + 1) + "Rate", diskInfoBean.getUsableSpace() / diskInfoBean.getTotalSpace());
			ClassTool.set(monitorServlet, "setDisk" + (i + 1) + "Total", diskInfoBean.getTotalSpace());
			ClassTool.set(monitorServlet, "setDisk" + (i + 1) + "Free", diskInfoBean.getFreeSpace());
		}

		monitorServlet.setCreateTime(nowTime);
		monitorServlet.setCreateTimeLong(System.currentTimeMillis());
		monitorServlet.setUpdateTime(nowTime);
		monitorServlet.setUpdateTimeLong(System.currentTimeMillis());
		monitorServlet.setState(IbsStateEnum.OPEN.name());
		new IbspLogMonitorServletService().save(monitorServlet);
	}
}
