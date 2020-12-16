package com.ibs.plan.connector.admin.manage.client.seal;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_config.service.IbspConfigService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 发送封盘时间
 * @Author: wwj
 * @Date: 2020/5/12 10:36
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/client/seal/send", method = HttpConfig.Method.POST)
public class SealTimeSendAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String configId = dataMap.getOrDefault("configId", "").toString();

		try {
			Map<Object, Object> sealTimeMap;
			IbspConfigService configService = new IbspConfigService();
			if (StringTool.notEmpty(configId)) {
				// 发送指定
				sealTimeMap = configService.mapSealTime(configId);
			} else {
				// 发送所有
				sealTimeMap = configService.mapAllSealTime();
			}
			//发送config.*.info消息
			JSONObject content = new JSONObject();
			content.put("sealTime", sealTimeMap);
			content.put("METHOD_", IbsMethodEnum.SEAL_TIME.name());
			String result = RabbitMqTool.sendClientConfig(content.toString());
			if (StringTool.isEmpty(result)) {
				bean.putEnum(CodeEnum.IBS_401_MESSAGE);
				bean.putSysEnum(CodeEnum.CODE_401);
				return bean;
			}
			if (!Boolean.parseBoolean(result)) {
				bean.putEnum(CodeEnum.IBS_403_MQ);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			bean.success();

		} catch (Exception e) {
			log.error("封盘时间发送错误");
			bean.error(e.getMessage());
		}
		return bean;
	}
}
