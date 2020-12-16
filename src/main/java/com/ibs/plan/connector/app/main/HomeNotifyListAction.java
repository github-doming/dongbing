package com.ibs.plan.connector.app.main;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommCoreAction;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.develop.http.HttpConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 用户消息信息
 * @Author: admin1
 * @Date: 2020/7/17 10:57
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/main/notify", method = HttpConfig.Method.GET)
public class HomeNotifyListAction extends CommCoreAction {
	public HomeNotifyListAction() {
		super.isTime = false;
	}

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		try {
			Map<String, Object> data = new HashMap<>(1);

			// 用户系统消息
			IbspCmsNotifyService notifyService = new IbspCmsNotifyService();
			data.put("announce", notifyService.listTopAnnounce());

			bean.success();
			bean.setData(data);
		} catch (Exception e) {
			log.error("加载主题框架错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

}
