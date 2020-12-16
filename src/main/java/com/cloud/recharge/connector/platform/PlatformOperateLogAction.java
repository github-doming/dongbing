package com.cloud.recharge.connector.platform;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.recharge.connector.core.BaseUserAction;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 管理用户操作日志
 * @Author: admin1
 * @Date: 2020-09-26 16:50
 */
@ActionMapping(value = "/cloud/recharge/pc/platform/log")
public class PlatformOperateLogAction extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}

		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		String opertType = dataMap.getOrDefault("opertType","").toString();
		Map<String, Object> data = new HashMap<>(3);
		try {
			CardOperateLogService logService = new CardOperateLogService();
			PageCoreBean<Map<String, Object>> pageCoreBean = logService.listLog(user.getUserPath(),opertType,pageIndex,pageSize);

			data.put("rows", pageCoreBean.getList());
			data.put("total", pageCoreBean.getTotalCount());
			data.put("index", pageIndex);
		} catch (Exception e) {
			log.error("管理用户操作日志列表出错", e);
			data.put("rows", null);
			data.put("index", pageIndex);
			data.put("total", 0);
		}
		return data;
	}
}
