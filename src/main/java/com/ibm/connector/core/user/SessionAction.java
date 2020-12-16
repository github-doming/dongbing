package com.ibm.connector.core.user;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.connector.service.user.AppVerifyAccountService;
/**
 * @Description: 用户获取 sessionId
 * @Author: Dongming
 * @Date: 2019-08-28 16:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SessionAction extends CommCoreAction {
	private ChannelTypeEnum channelType;
	public SessionAction(ChannelTypeEnum channelType) {
		super.isTime = false;
		this.channelType = channelType;
	}

	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			//获取一个新的用户session
			String sessionId = new AppVerifyAccountService().newAppSessionByType(channelType);
			bean.setData(sessionId);
			bean.success();
		} catch (Exception e) {
			log.error("获取session失败，", e);
			throw e;
		}
		return bean;

	}
}
