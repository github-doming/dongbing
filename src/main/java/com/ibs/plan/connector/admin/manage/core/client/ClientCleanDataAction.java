package com.ibs.plan.connector.admin.manage.core.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_client.service.IbspClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description: 清理客户端数据
 * @Author: null
 * @Date: 2020-05-13 16:21
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/core/client/clean")
public class ClientCleanDataAction extends CommAdminAction {
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
		String cleanType = dataMap.getOrDefault("cleanType", "").toString();

		if (StringTool.isEmpty(clientCode, cleanType)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		String days = "";
		//清理类型 ALL,WEEK,delimit,只有自定义类型才有指定时间
		if ("DELIMIT".equals(cleanType)) {
			days = dataMap.getOrDefault("days", "").toString();
		}
		try {
			IbspClientService clientService = new IbspClientService();
			String state = clientService.findInfo(clientCode).get("STATE_").toString();
			if (!IbsStateEnum.OPEN.name().equals(state) ||!IbsStateEnum.CLOSE.name().equals(state)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			//发消息到发送端
			JSONObject content = new JSONObject();
			content.put("METHOD_", IbsMethodEnum.CLEAR.name());
			content.put("methodType", cleanType);
			if (StringTool.notEmpty(days)) {
				content.put("days", days);
			}
			String result = RabbitMqTool.sendClientConfig(content.toString(), clientCode, "info");
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
			log.error("清理客户端数据错误", e);
			return bean.error(e.getMessage());
		}
		return bean;


	}
}
