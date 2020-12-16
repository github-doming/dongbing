package com.ibm.follow.connector.admin.manage.core.quertz;

import all.job.service.SysQuartzTriggerService;
import c.a.config.SysConfig;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.job.TriggerStateEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定时器列表
 *
 * @Author: Dongming
 * @Date: 2020-05-15 15:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/quartz/list")
public class QuartzListAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String jobName = StringTool.getString(dataMap, "jobName", "");
		String state = StringTool.getString(dataMap, "state", "");

		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(5);
		try {
			SysQuartzTriggerService sysQuartzTriggerService = new SysQuartzTriggerService();
			PageCoreBean<Map<String, Object>> basePage = sysQuartzTriggerService.listShow(jobName, state, pageIndex, pageSize);
			;
			List<Map<String, Object>> triggerList = basePage.getList();

			String url = SysConfig.findInstance().findMap()
					.getOrDefault("ibm.server.local", IbmMainConfig.SERVER_LOCAL).toString() + "/all/job/trigger/infos.do";
			JSONObject json = new JSONObject();
			String time = System.currentTimeMillis() + "";
			json.put("time", time);
			json.put("valiDate", Md5Tool.generate(time));

			JSONObject result = JSON.parseObject(HttpTool.doGet(url, HttpTool.paramJson(json)));

			if (!result.getBoolean("success")) {
				return bean.error(result.getString("msg"));
			}
			JSONObject dataJson = result.getJSONObject("data");
			JSONArray quartzTriggerList = dataJson.getJSONArray("list");
			for (Map<String, Object> triggerMap : triggerList) {
				String sysTriggerName =
						StringTool.getString(triggerMap, "TRIGGER_NAME_", "");
				for (int i = 0; i < quartzTriggerList.size(); i++) {
					JSONObject trigger = quartzTriggerList.getJSONObject(i);
					String triggerName = trigger.getString("triggerName");
					TriggerStateEnum triggerState = TriggerStateEnum.valueOf(trigger.getString("triggerState"));

					if (sysTriggerName.equals(triggerName)) {
						triggerMap.put("TRIGGER_STATE_CN_", triggerState.getMsgCn());
						triggerMap.put("TRIGGER_STATE_", triggerState.name());
						break;
					}
				}
			}

			data.put("rows", triggerList);
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
			data.put("started", dataJson.get("started"));
		} catch (Exception e) {
			log.error("定时器列表错误", e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}

		return data;
	}
}
