package com.ibs.plan.connector.admin.manage.client.server;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_log_monitor_servlet.service.IbspLogMonitorServletService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务器详情
 *
 * @Author: Dongming
 * @Date: 2020-05-15 10:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/server/item",method = HttpConfig.Method.GET)
public class ServerItemAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String servletIp = StringTool.getString(dataMap, "SERVLET_IP_", "");
		long startTime = NumberTool.getLong(dataMap, "startTime", DateTool.getYesterdayStart().getTime());
		long endTime = NumberTool.getLong(dataMap, "endTime", System.currentTimeMillis());
		if (StringTool.isEmpty(servletIp)){
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			IbspLogMonitorServletService monitorServletService = new IbspLogMonitorServletService();
			List<Map<String, Object>> monitorInfos = monitorServletService.listRate(servletIp, startTime, endTime);
			List<Map<String, Object>> infos = new ArrayList<>(12);
			int len = monitorInfos.size();
			if (len > 12) {
				int index = 0, endIndex, step;
				Map<String, Object> monitorInfo;
				for (int i = 1; i <= 12; i++) {
					endIndex = (len * i) / 12;
					step = endIndex - index;
					double cpuRate = 0, ramRate = 0, disk1Rate = 0, disk2Rate = 0, disk3Rate = 0, disk4Rate = 0;
					for (; index <= endIndex; index++) {
						monitorInfo = monitorInfos.get(index);
						cpuRate += NumberTool.getDouble(monitorInfo, "CPU_RATE_", 0.0);
						ramRate += NumberTool.getDouble(monitorInfo, "RAM_RATE_", 0.0);
						disk1Rate += NumberTool.getDouble(monitorInfo, "DISK_RATE_", 0.0);
					}
					monitorInfo = new HashMap<>(6);
					monitorInfo.put("CPU_RATE_", cpuRate / step);
					monitorInfo.put("RAM_RATE_", ramRate / step);
					monitorInfo.put("DISK_RATE_", disk1Rate / step);
					infos.add(monitorInfo);
				}

			}


			Map<String, Object> info = monitorServletService.findInfo(servletIp);
			Map<String, Object> data = new HashMap<>(2);


			data.put("monitorInfos", infos);
			data.put("info", info);
			bean.success(data);
		} catch (Exception e) {
			log.error("服务器信息列表错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
