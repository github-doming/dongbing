package com.ibm.follow.connector.admin.manage.core.client;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_client.service.IbmClientService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

/**
 * @Description: 清理客户端数据
 * @Author: null
 * @Date: 2020-05-13 16:21
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/core/client/clean")
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
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		String days="";
		//清理类型 ALL,WEEK,delimit,只有自定义类型才有指定时间
		if(IbmMethodEnum.DELIMIT.name().equals(cleanType)){
			days=dataMap.getOrDefault("days", "").toString();
		}
		try{
			IbmClientService clientService=new IbmClientService();
			if(!IbmStateEnum.OPEN.name().equals(clientService.findState(clientCode))||
					!IbmStateEnum.CLOSE.name().equals(clientService.findState(clientCode))){
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			//发消息到发送端
			JSONObject content = new JSONObject();
			content.put("METHOD_", IbmMethodEnum.CLEAR.name());
			content.put("methodType", cleanType);
			if(StringTool.notEmpty(days)){
				content.put("days", days);
			}
			String result = RabbitMqTool.sendClientConfig(content.toString(), clientCode, "info");
			if (StringTool.isEmpty(result)) {
				bean.putEnum(IbmCodeEnum.IBM_401_MESSAGE);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean.toJsonString();
			}
			if (!Boolean.parseBoolean(result)) {
				bean.putEnum(IbmCodeEnum.IBM_403_MQ);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean.toJsonString();
			}
			bean.success();
		} catch (Exception e) {
			log.error("清理客户端数据错误", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();


	}
}
