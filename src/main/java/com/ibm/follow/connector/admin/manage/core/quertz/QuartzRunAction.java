package com.ibm.follow.connector.admin.manage.core.quertz;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpTool;

/**
 * 定时器运行
 *
 * @Author: Dongming
 * @Date: 2020-05-15 15:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/quartz/run")
public class QuartzRunAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String quartzTriggerIds = StringTool.getString(dataMap, "quartzTriggerIds", "");

		try {
			String url = SysConfig.findInstance().findMap()
					.getOrDefault("ibm.server.local", IbmMainConfig.SERVER_LOCAL).toString() + "/all/job/trigger/run.do";
			JSONObject json = new JSONObject();
			if (StringTool.notEmpty(quartzTriggerIds)) {
				json.put("id", quartzTriggerIds);

			}
			String time = System.currentTimeMillis() + "";
			json.put("time", time);
			json.put("valiDate", Md5Tool.generate(time));
			JSONObject result = JSON.parseObject(HttpTool.doGet(url, HttpTool.paramJson(json)));
			if (!result.getBoolean("success")) {
				return bean.error(result.getString("msg"));
			}
			bean.success();

		} catch (Exception e) {
			log.error("定时器暂停错误", e);
			bean.error(e.getMessage());
		}


		return bean;
	}
}
