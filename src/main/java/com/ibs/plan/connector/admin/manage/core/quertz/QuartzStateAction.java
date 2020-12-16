package com.ibs.plan.connector.admin.manage.core.quertz;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpTool;

/**
 * 开启或关闭定时器
 *
 * @Author: Dongming
 * @Date: 2020-05-16 14:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/core/quartz/state")
public class QuartzStateAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String state = StringTool.getString(dataMap, "state", "");

		if (StringTool.isEmpty(state)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		if ((!IbsStateEnum.OPEN.equal(state) || !IbsStateEnum.CLOSE.equal(state))) {
			bean.putEnum(CodeEnum.IBS_401_STATE);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			// TODO
			String url = SysConfig.findInstance().findMap()
					.getOrDefault("ibs.server.local", IbsConfig.SERVER_LOCAL).toString() + "/all/job/trigger/state.do";
			JSONObject map = new JSONObject();
			String time = System.currentTimeMillis() + "";
			map.put("time", time);
			map.put("valiDate", Md5Tool.generate(time));
			map.put("state", state);
			JSONObject result = JSON.parseObject(HttpTool.doGet(url,HttpTool.paramJson(map)));
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
