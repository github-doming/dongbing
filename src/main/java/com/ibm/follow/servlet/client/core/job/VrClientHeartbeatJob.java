package com.ibm.follow.servlet.client.core.job;

import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.servlet.boot.IbmModuleListener;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.client.ibmc_config.service.IbmcConfigService;
import com.ibm.follow.servlet.vrc.vrc_exist_member.service.VrcExistMemberService;
import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobExecutionContext;

import java.util.Map;

/**
 * @Description: 客户端 心跳检测
 * @Author: Dongming
 * @Date: 2019-08-27 10:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class VrClientHeartbeatJob extends BaseCommJob {

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		//服务器定时推送服务器状态信息

		// 获取容量信息
		Map<String,Object> capacityMax =new IbmcConfigService().findMaxCapacity();

		// 获取盘口会员状态
		Map<String, Object> exitsHmInfo =new VrcExistMemberService().findExitsInfo();

		JSONObject message = new JSONObject();
		message.put("capacityMax",capacityMax.get("CAPACITY_MAX"));
		message.put("exitsHmInfo", exitsHmInfo);
		message.put("method", IbmMethodEnum.VR_CLIENT_HEARTBEAT.name());
		message.put("clientCode", IbmModuleListener.servletCode());

		//发送校验信息
		RabbitMqTool.sendInfoReceipt(message.toJSONString(), "client");
	}
}
