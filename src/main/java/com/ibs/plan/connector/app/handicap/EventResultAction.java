package com.ibs.plan.connector.app.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommQueryAction;
import com.ibs.plan.module.cloud.ibsp_event_login.service.IbspEventLoginService;
import com.ibs.plan.module.cloud.ibsp_event_login_vali.service.IbspEventLoginValiService;
import com.ibs.plan.module.cloud.ibsp_event_logout.service.IbspEventLogoutService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * 登录结果
 *
 * @Author: Dongming
 * @Date: 2020-05-25 10:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/handicap/eventResult", method = HttpConfig.Method.POST)
public class EventResultAction
		extends CommQueryAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		Object loginCode = dataMap.getOrDefault("loginCode", "");
		String eventId = dataMap.getOrDefault("eventId", "").toString();
		if (StringTool.isEmpty(eventId, loginCode)) {
			return bean.put401Data();
		}
		try {
			Map<String, Object> result;
			if ("valiLogin".equals(loginCode)) {
				//验证登录
				result = new IbspEventLoginValiService().findResult(eventId);
			} else if ("login".equals(loginCode)) {
				//登录
				result = new IbspEventLoginService().findResult(eventId);
			} else if ("logout".equals(loginCode)) {
				//登出
				result = new IbspEventLogoutService().findResult(eventId);
			} else {
				//不存在的登录code
				bean.putSysEnum(CodeEnum.IBS_403_ERROR);
				bean.putEnum(CodeEnum.CODE_403);
				return bean;
			}
			// 登录中
			if (ContainerTool.isEmpty(result)) {
				return bean.process(CodeEnum.IBS_202_EVENT);
			}
			JSONObject eventResult = JSONObject.parseObject(result.get("EVENT_RESULT_").toString());
			eventResult.put("data", result.get("HANDICAP_MEMBER_ID_"));
			bean.success(eventResult);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "查找登录结果信息错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
