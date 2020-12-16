package com.cloud.user.connector.core;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.common.core.BaseMvcData;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.user.app_session.service.AppSessionService;
/**
 * 用户获取sessionId
 * @Author: Dongming
 * @Date: 2020-06-15 15:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SessionAction extends BaseMvcData {
	private ChannelTypeEnum channelType;
	public SessionAction(ChannelTypeEnum channelType) {
		this.channelType = channelType;
	}
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			//获取一个新的用户session
			String sessionId = new AppSessionService().newAppSessionByType(channelType);
			bean.setData(sessionId);
			bean.success();
		} catch (Exception e) {
			log.error("获取session失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
		}
		return bean;
	}
}
