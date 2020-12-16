package com.ibm.follow.connector.admin.manage2.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_announce.service.IbmCmsAnnounceService;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.entity.IbmCmsNotify;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 消息删除/撤销
 * @Author: wwj
 * @Date: 2020/4/2 15:45
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/cms/notify/del")
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
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean.toJsonString();
		}
		try {
			IbmCmsNotifyService cmsNotifyService = new IbmCmsNotifyService();
			cmsNotifyService.updateNotify(IbmTypeEnum.valueOf(notifyType),cmsNotifyId,notifyState,new Date());
			bean.success();
		} catch (Exception e) {
			log.error("消息删除/撤销出错！", e);
			throw e;
		}
		return bean.toJsonString();
	}
}

