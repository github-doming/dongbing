package com.ibm.follow.connector.admin.manage2.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 消息详情
 * @Author: wwj
 * @Date: 2020/5/12 16:41
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/cms/notify/form")
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
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			IbmTypeEnum type = IbmTypeEnum.valueOf(notifyType);
			IbmCmsNotifyService cmsNotifyService = new IbmCmsNotifyService();
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

		return bean.toJsonString();
	}
}
