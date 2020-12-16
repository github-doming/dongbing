package com.cloud.recharge.connector.platform;

import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.cloud.recharge.connector.core.BaseUserAction;
import com.cloud.user.user_login_log.service.UserLoginLogService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.NumberTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 用户登录日志
 * @Author: admin1
 * @Date: 2020-09-26 16:50
 */
@ActionMapping(value = "/cloud/recharge/pc/platform/loginLog")
public class PlatformUserLoginLogAction extends BaseUserAction {
	@Override
	public Object run() throws Exception {
		super.findUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}

		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		String channelType = dataMap.getOrDefault("channelType","").toString();
		Map<String, Object> data = new HashMap<>(3);
		try {

			UserLoginLogService loginLogService = new UserLoginLogService();
			PageCoreBean<Map<String, Object>> pageCoreBean = loginLogService.listLog(user.getUserPath(),channelType,pageIndex, pageSize);

			for (Map<String, Object> map : pageCoreBean.getList()) {
				map.put("CHANNEL_TYPE_",getChannelType(map.getOrDefault("CHANNEL_TYPE_","IBS").toString()));
				if(StateEnum.LOGOUT.name().equals(map.get("OPERT_CONTENT_"))){
					map.put("OPERT_CONTENT_","登出");
				}else{
					map.put("OPERT_CONTENT_","登录");
				}
			}
			data.put("rows", pageCoreBean.getList());
			data.put("total", pageCoreBean.getTotalCount());
			data.put("index", pageIndex);
			data.put("userType", user.getUserType());
		} catch (Exception e) {
			log.error("下级管理列表出错", e);
			data.put("rows", null);
			data.put("index", pageIndex);
			data.put("total", 0);
		}
		return data;
	}

	private String getChannelType(String channelType) {
		switch (channelType) {
			case "IBS":
			case "IBS_APP":
				return "智能投注系统";
			case "IBS_ADMIN":
				return "智能投注管理系统";
			case "IBM":
			case "IBM_APP":
				return "智能跟单系统";
			case "IBM_ADMIN":
				return "智能跟单管理系统";
			default:
				return "开卡平台";
		}
	}
}
