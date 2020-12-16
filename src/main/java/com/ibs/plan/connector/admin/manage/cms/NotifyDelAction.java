package com.ibs.plan.connector.admin.manage.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 消息删除/撤销
 * @Author: admin1
 * @Date: 2020/4/2 15:45
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/cms/notify/del", method = HttpConfig.Method.POST)
public class NotifyDelAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String notifyType = StringTool.getString(dataMap.get("notifyType"), "").toUpperCase();
		String cmsNotifyId = StringTool.getString(dataMap.get("IBM_CMS_NOTIFY_ID_"), "");

		String notifyState = StringTool.getString(dataMap.get("notifyState"), "");

		if (StringTool.isEmpty(cmsNotifyId, notifyType, notifyState)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			new IbspCmsNotifyService().updateNotify(IbsTypeEnum.valueOf(notifyType), cmsNotifyId, notifyState, new Date());
			bean.success();
		} catch (Exception e) {
			log.error("消息删除/撤销出错！", e);
			throw e;
		}
		return bean;
	}
}

