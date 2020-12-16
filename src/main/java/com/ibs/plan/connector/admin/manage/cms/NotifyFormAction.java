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

import java.util.Map;

/**
 * @Description: 消息详情
 * @Author: admin1
 * @Date: 2020/5/12 16:41
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/cms/notify/form", method = HttpConfig.Method.GET)
public class NotifyFormAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		String notifyType = StringTool.getString(dataMap.get("notifyType"), "").toUpperCase();
		String notifyId = StringTool.getString(dataMap.get("IBM_CMS_NOTIFY_ID_"), "");

		if (StringTool.isEmpty(notifyType, notifyId)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		try {
			IbsTypeEnum type = IbsTypeEnum.valueOf(notifyType);
			IbspCmsNotifyService cmsNotifyService = new IbspCmsNotifyService();
			Map<String, Object> notifyInfo;
			switch (type) {
				case REMIND:
					notifyInfo = cmsNotifyService.remindInfo(notifyId);
					break;
				case ANNOUNCE:
					notifyInfo = cmsNotifyService.announceInfo(notifyId);
					break;
				case MESSAGE:
					notifyInfo = cmsNotifyService.messageInfo(notifyId);
					break;
				default:
					throw new Exception("消息类型错误：" + notifyType);
			}

			bean.success(notifyInfo);
		} catch (Exception e) {
			log.error("获取消息详情出错！", e);
			bean.error(e.getMessage());
		}

		return bean;
	}
}
