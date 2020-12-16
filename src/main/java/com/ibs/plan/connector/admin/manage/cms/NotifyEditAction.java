package com.ibs.plan.connector.admin.manage.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_cms_announce.service.IbspCmsAnnounceService;
import com.ibs.plan.module.cloud.ibsp_cms_notify.entity.IbspCmsNotify;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 消息修改
 * @Author: admin1
 * @Date: 2020/4/2 15:45
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/cms/notify/edit", method = HttpConfig.Method.POST)
public class NotifyEditAction extends CommAdminAction {

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

		String notifyTitle = StringTool.getString(dataMap.get("notifyTitle"), "");
		String notifyContent = StringTool.getString(dataMap.get("notifyContent"), "");
		String cancelTimeLong = StringTool.getString(dataMap.get("cancelTimeLong"), "");
		String notifyChannel = StringTool.getString(dataMap.get("notifyChannel"), "");
		String notifySite = StringTool.getString(dataMap.get("notifySite"), "");
		String notifyLevel = StringTool.getString(dataMap.get("notifyLevel"), "");
		String notifyState = StringTool.getString(dataMap.get("notifyState"), "");

		long timeLong = NumberTool.getLong(cancelTimeLong);
		if (StringTool.isEmpty(cmsNotifyId, notifyType)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}

		Date date = new Date();
		try {
			IbsTypeEnum type = IbsTypeEnum.valueOf(notifyType);

			IbspCmsNotifyService cmsNotifyService = new IbspCmsNotifyService();
			IbspCmsNotify cmsNotify = cmsNotifyService.find(cmsNotifyId);
			if (cmsNotify == null) {
				bean.putEnum(CodeEnum.IBS_401_DATA);
				bean.putSysEnum(CodeEnum.CODE_401);
				return bean;
			}
			cmsNotify.setNotifyTitle(notifyTitle);
			cmsNotify.setNotifyContent(notifyContent);
			cmsNotify.setNotifyLevel(NumberTool.getInteger(notifyLevel));
			cmsNotify.setNotifySite(notifySite);
			cmsNotify.setUpdateTime(date);
			cmsNotify.setUpdateTimeLong(System.currentTimeMillis());
			cmsNotify.setState(notifyState);
			cmsNotifyService.update(cmsNotify);
			switch (type) {
				case REMIND:
					cmsNotifyService.updateNotify(IbsTypeEnum.REMIND, cmsNotifyId, notifyState, new Date());
					break;
				case ANNOUNCE:
					new IbspCmsAnnounceService().updateAnnounce(cmsNotifyId, notifyChannel, timeLong, notifyState, date);
					break;
				case MESSAGE:
					cmsNotifyService.updateNotify(IbsTypeEnum.MESSAGE, cmsNotifyId, notifyState, new Date());
					break;
				default:
					throw new Exception("消息类型错误：" + notifyType);
			}
			bean.success();
		} catch (Exception e) {
			log.error("修改消息出错！", e);
			throw e;
		}
		return bean;
	}
}

