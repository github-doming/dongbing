package com.ibm.follow.connector.admin.manage.core.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.ibm_log_monitor_client.service.IbmLogMonitorClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 客户端详情图表信息
 * @Author: null
 * @Date: 2020-05-13 15:17
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/client/chart")
public class ClientItemChartAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//客户机code
		String clientCode = dataMap.getOrDefault("clientCode", "").toString();
		String startTime =  dataMap.getOrDefault("startTime", "").toString();
		String endTime =  dataMap.getOrDefault("endTime", "").toString();
		if(StringTool.isEmpty(clientCode)){
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try{
			IbmLogMonitorClientService monitorClientService = new IbmLogMonitorClientService();

			List<String> monitorInfos = monitorClientService.listRate(clientCode, startTime, endTime);
			List<Double> infos = new ArrayList<>(12);
			int len = monitorInfos.size();
			if (len > 12) {
				int index = 0, endIndex, step;
				double jvmRate = 0;
				for (int i = 1; i <= 12; i++) {
					endIndex = (len * i) / 12;
					step = endIndex - index;
					for (; index <= endIndex; index++) {
						jvmRate += NumberTool.getDouble(monitorInfos.get(index), 0.0);
					}
					infos.add(jvmRate/step);
				}
			}
			Map<String, Object> info = monitorClientService.findInfo(clientCode);
			Map<String, Object> data = new HashMap<>(2);

			data.put("monitorInfos", infos);
			data.put("info", info);
			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error("客户端详情列表错误", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
