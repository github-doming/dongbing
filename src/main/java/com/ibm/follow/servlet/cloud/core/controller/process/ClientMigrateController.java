package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.core.thread.EventThreadDefine;
import com.ibm.follow.servlet.cloud.ibm_event_config_info.service.IbmEventConfigInfoService;

import java.util.Date;
/**
 * 客户端迁移数据
 *
 * @Author: Dongming
 * @Date: 2020-01-10 14:18
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ClientMigrateController {

	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		//存储会员迁移 事件
		IbmEventConfigInfoService configInfoService = new IbmEventConfigInfoService();


		JSONArray migrateInfo = msgObj.getJSONArray("hmMigrateInfo");
		Date nowTime = new Date();
		for(int i = 0; i < migrateInfo.size(); i++) {
			JSONObject info = migrateInfo.getJSONObject(i);
			info.put("CUSTOMER_TYPE_", IbmTypeEnum.MEMBER.name());
			String eventId=EventThreadDefine.saveConfigInfoEvent(info,nowTime, IbmMethodEnum.CLIENT_MIGRATE, "客户端迁移数据",configInfoService);
			info.put("EVENT_ID_", eventId);
			info.put("METHOD_", IbmMethodEnum.CLIENT_MIGRATE.name());

			String clientCode=info.remove("CLIENT_CODE_").toString();

			RabbitMqTool.sendClientConfig(info.toString(), clientCode, "info");
		}
		migrateInfo = msgObj.getJSONArray("haMigrateInfo");
		for(int i = 0; i < migrateInfo.size(); i++) {
			JSONObject info = migrateInfo.getJSONObject(i);
			info.put("CUSTOMER_TYPE_", IbmTypeEnum.AGENT.name());
			String eventId=EventThreadDefine.saveConfigInfoEvent(info,nowTime, IbmMethodEnum.CLIENT_MIGRATE,"客户端迁移数据",configInfoService);
			info.put("EVENT_ID_", eventId);
			String clientCode=info.remove("CLIENT_CODE_").toString();
			RabbitMqTool.sendClientConfig(info.toString(), clientCode, "info");
		}
		return new JsonResultBeanPlus().success();
	}
}
