package com.ibs.plan.connector.app.main;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.core.CommCoreAction;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 用户消息信息
 * @Author: admin1
 * @Date: 2020/7/17 10:57
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/main/notifyInfo", method = HttpConfig.Method.GET)
public class HomeNotifyInfoAction extends CommCoreAction {
	public HomeNotifyInfoAction() {
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

		String cmsNotifyId = StringTool.getString(dataMap,"CMS_NOTIFY_ID_", "");
		try {

			IbspCmsNotifyService notifyService = new IbspCmsNotifyService();
			Map<String, Object> notifyInfo = notifyService.announceInfo(cmsNotifyId);
			bean.success(notifyInfo);
		} catch (Exception e) {
			log.error("加载主题框架错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

}
