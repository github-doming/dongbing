package com.ibm.follow.connector.admin.manage.core.servlet;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_log_monitor_servlet.service.IbmLogMonitorServletService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务器详情
 *
 * @Author: null
 * @Date: 2020-05-14 11:28
 * @Version: v1.0
 * @see com.ibm.follow.connector.admin.manage.client.server.ServerItemAction
 * @deprecated
 */
@ActionMapping(value = "/ibm/admin/manage/core/server/item")
public class ServerItemAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String servletIp = StringTool.getString(dataMap, "SERVLET_IP_", "");
		long startTime = NumberTool.getLong(dataMap, "startTime", DateTool.getYesterdayStart().getTime());
		long endTime = NumberTool.getLong(dataMap, "endTime", System.currentTimeMillis());
		if (StringTool.isEmpty(servletIp)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			Map<String, Object> map = new HashMap<>(4);
			IbmLogMonitorServletService monitorServletService = new IbmLogMonitorServletService();
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
						disk1Rate += NumberTool.getDouble(monitorInfo, "DISK1_RATE_", 0.0);
						disk2Rate += NumberTool.getDouble(monitorInfo, "DISK2_RATE_", 0.0);
						disk3Rate += NumberTool.getDouble(monitorInfo, "DISK3_RATE_", 0.0);
						disk4Rate += NumberTool.getDouble(monitorInfo, "DISK4_RATE_", 0.0);

					}
					monitorInfo = new HashMap<>(6);
					monitorInfo.put("CPU_RATE_", cpuRate / step);
					monitorInfo.put("RAM_RATE_", ramRate / step);
					monitorInfo.put("DISK1_RATE_", disk1Rate / step);
					monitorInfo.put("DISK2_RATE_", disk2Rate / step);
					monitorInfo.put("DISK3_RATE_", disk3Rate / step);
					monitorInfo.put("DISK4_RATE_", disk4Rate / step);
					infos.add(monitorInfo);
				}

			}


			Map<String, Object> info = monitorServletService.findInfo(servletIp);
			Map<String, Object> data = new HashMap<>(2);


			data.put("monitorInfos", infos);
			data.put("info", info);
			bean.success(data);
		} catch (Exception e) {
			log.error("服务器图表信息错误", e);
		}
		return bean.toJsonString();
	}
}
