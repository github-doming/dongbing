package com.ibs.plan.module.client.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.client.ibsc_config.entity.IbscConfig;
import com.ibs.plan.module.client.ibsc_config.service.IbscConfigService;
import org.doming.core.tools.ContainerTool;

import java.util.Date;
import java.util.Map;

/**
 * 客户端公用定义
 *
 * @Author: null
 * @Date: 2020-05-18 17:38
 * @Version: v1.0
 */
public class ClientDefineController {
	/**
	 * 封盘时间
	 */
	public JsonResultBeanPlus sealTime(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		JSONObject sealTime = msgObj.getJSONObject("sealTime");
		if (ContainerTool.isEmpty(sealTime)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		Date nowTime = new Date();
		IbscConfigService configService = new IbscConfigService();
		Map<String, String> sealTimeMap=configService.mapByType("SEAL_TIME");
		//本机存在-修改信息
		for(Object key:sealTimeMap.keySet()){
			if(sealTime.containsKey(key)){
				configService.updateByKeys(sealTime,sealTimeMap);
				break;
			}
		}
		//添加新信息
		for (String handicap : sealTime.keySet()){
			IbscConfig config = new IbscConfig();
			config.setConfigType("SEAL_TIME");
			config.setCreateUser("doming");
			config.setCreateTime(nowTime);
			config.setCreateTimeLong(nowTime.getTime());
			config.setUpdateTime(nowTime);
			config.setUpdateTimeLong(nowTime.getTime());
			config.setState(IbsStateEnum.OPEN.name());
			for (Map.Entry<String,Object> entry : sealTime.getJSONObject(handicap).entrySet()){
				String configKey=handicap+"#"+entry.getKey();
				if(sealTimeMap.containsKey(configKey)){
					continue;
				}
				config.setClientConfigKey(configKey);
				config.setClientConfigValue(entry.getValue());
				configService.save(config);
			}
		}
		return bean.success();
	}


	/**
	 * 保存用户信息
	 * @param existHmId 存在会员主键
	 * @param userInfo 用户信息
	 */
	public void sendUserInfo(String existHmId, MemberUser userInfo) throws Exception {
		new MemberManageController().existId(existHmId).saveUserInfo(userInfo);
		JSONObject content = new JSONObject();
		content.put("EXIST_HM_ID_", existHmId);
		content.put("usedQuota", userInfo.usedQuota());
		content.put("profitAmount", userInfo.profitAmount());
		content.put("memberAccount",userInfo.memberAccount());
		content.put("METHOD_", IbsMethodEnum.CUSTOMER_INFO.name());
		RabbitMqTool.sendInfo(content.toString(), "member");
	}
}
