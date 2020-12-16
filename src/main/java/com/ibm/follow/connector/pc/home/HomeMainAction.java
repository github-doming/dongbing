package com.ibm.follow.connector.pc.home;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import com.ibm.follow.servlet.cloud.ibm_manage_time.service.IbmManageTimeService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 首页，加载主题框架
 * @Author: Dongming
 * @Date: 2019-08-29 15:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/main", method = HttpConfig.Method.GET) public class HomeMainAction
		extends CommCoreAction {
	public HomeMainAction() {
		super.isTime = false;
	}
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			Map<String, Object> data = new HashMap<>(3);

			// 会员盘口
			List<Map<String, Object>> memberHandicapInfo = new IbmHmUserService().listUserHandicap(appUserId);
			data.put("memberHandicapInfo", memberHandicapInfo);

			// 代理盘口
			List<Map<String, Object>> agentHandicapInfo = new IbmHaUserService().listUserHandicap(appUserId);
			data.put("agentHandicapInfo", agentHandicapInfo);

			// 用户信息
			Map<String,Object> userInfo = new HashMap<>(1);
			userInfo.put("endTimeLong",new IbmManageTimeService().getEndTime(appUserId));

			data.put("userInfo",userInfo);
			bean.success();
			bean.setData(data);

		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("加载主题框架错误"),e);
			bean.error(e.getMessage());
		}
		return bean;

	}
}
