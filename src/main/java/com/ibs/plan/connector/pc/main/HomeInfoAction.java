package com.ibs.plan.connector.pc.main;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommQueryAction;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.service.IbspCmsUserNotifyService;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;
/**
 * 首页自动刷新 30s 一次
 *
 * @Author: null
 * @Date: 2020-05-23 15:17
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/home/info", method = HttpConfig.Method.GET) public class HomeInfoAction
		extends CommQueryAction {
	public HomeInfoAction() {
		super.isTime = false;
	}
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}

		try {
			Map<String, Object> data = new HashMap<>(2);
			// 用户信息
			Map<String, Object> userInfo =new IbspUserService().findHomeInfo(appUserId);
			data.put("userInfo", userInfo);

			// 用户系统消息
			data.put("unread", new IbspCmsUserNotifyService().getUnreadMsgByUserId(appUserId));

			bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "首页自动刷新", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
