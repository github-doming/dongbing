package com.ibm.follow.connector.admin.manage2.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_announce.service.IbmCmsAnnounceService;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.entity.IbmCmsNotify;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.service.IbmCmsRemindService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 消息修改
 * @Author: wwj
 * @Date: 2020/4/2 15:45
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/cms/notify/edit")
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
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean.toJsonString();
		}

		Date date = new Date();
		try {
			IbmTypeEnum type = IbmTypeEnum.valueOf(notifyType);

			IbmCmsNotifyService cmsNotifyService = new IbmCmsNotifyService();
			IbmCmsNotify cmsNotify = cmsNotifyService.find(cmsNotifyId);
			if(cmsNotify == null){
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean.toJsonString();
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
					cmsNotifyService.updateNotify(IbmTypeEnum.REMIND,cmsNotifyId, notifyState,new Date());
					break;
				case ANNOUNCE:
					new IbmCmsAnnounceService().updateAnnounce(cmsNotifyId,notifyChannel,timeLong,notifyState,date);
					break;
				case MESSAGE:
					cmsNotifyService.updateNotify(IbmTypeEnum.MESSAGE,cmsNotifyId, notifyState,new Date());
					break;
				default:
					throw new Exception("消息类型错误：" + notifyType);
			}
			bean.success();
		} catch (Exception e) {
			log.error("修改消息出错！", e);
			throw e;
		}
		return bean.toJsonString();
	}
}

