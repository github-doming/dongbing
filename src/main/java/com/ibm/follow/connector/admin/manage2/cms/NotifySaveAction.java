package com.ibm.follow.connector.admin.manage2.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.connector.admin.manage3.handicap.user.IbmAdminAppUserService;
import com.ibm.follow.servlet.cloud.ibm_cms_announce.entity.IbmCmsAnnounce;
import com.ibm.follow.servlet.cloud.ibm_cms_announce.service.IbmCmsAnnounceService;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.entity.IbmCmsRemind;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.service.IbmCmsRemindService;
import com.ibm.follow.servlet.cloud.ibm_cms_user_notify.entity.IbmCmsUserNotify;
import com.ibm.follow.servlet.cloud.ibm_cms_user_notify.service.IbmCmsUserNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 创建消息
 * @Author: wwj
 * @Date: 2020/4/1 11:25
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/cms/notify/save")
public class NotifySaveAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String notifyType = StringTool.getString(dataMap.get("notifyType"), "").toUpperCase();
		String notifyTitle = StringTool.getString(dataMap.get("notifyTitle"), "");
		String notifyContent = StringTool.getString(dataMap.get("notifyContent"), "");
		String cancelTimeLong = StringTool.getString(dataMap.get("cancelTimeLong"), "");
		String notifyChannel = StringTool.getString(dataMap.get("notifyChannel"), "");
		String notifySite = StringTool.getString(dataMap.get("notifySite"), "").toUpperCase();

		String notifyCategory = StringTool.getString(dataMap.get("notifyCategory"), "");
		String userUserName = StringTool.getString(dataMap.get("userName"), "");
		String notifyState = StringTool.getString(dataMap.get("notifyState"), "");

		int notifyLevel = NumberTool.getInteger(dataMap.get("notifyLevel"), 0);
		if (StringTool.isEmpty(notifyType, notifyTitle, notifyContent)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}

		IbmTypeEnum type = IbmTypeEnum.valueOf(notifyType);
		Date date = new Date();
		try {

			String cmsNotifyId = RecordNotifyDefine.createNotify(new IbmCmsNotifyService(), notifyType, notifyTitle,
					notifyContent, notifyLevel, notifySite, adminUser.getUserName(), date);
			String notifyCode,userId="";
			if(StringTool.notEmpty(userUserName)){
				userId = new IbmAdminAppUserService().findUserIdByUserName(userUserName);
				if(StringTool.isEmpty(userId)){
					bean.putEnum(IbmCodeEnum.IBM_403_USER);
					bean.putSysEnum(IbmCodeEnum.CODE_403);
					return bean.toJsonString();
				}
			}

			switch (type) {
				case REMIND:
					notifyCode = RecordNotifyDefine.getNotifyCode(type,notifyCategory);
					IbmCmsRemind cmsRemind = new IbmCmsRemind();
					cmsRemind.setCmsNotifyId(cmsNotifyId);
					cmsRemind.setNotifyCode(notifyCode);
					cmsRemind.setUserId(userId);
					cmsRemind.setUserName(userUserName);
					cmsRemind.setRemindCategory(notifyCategory);
					cmsRemind.setIsCancel(false);
					cmsRemind.setCreateTime(date);
					cmsRemind.setCreateTimeLong(System.currentTimeMillis());
					cmsRemind.setUpdateTime(date);
					cmsRemind.setUpdateTimeLong(System.currentTimeMillis());
					cmsRemind.setState(notifyState);
					new IbmCmsRemindService().save(cmsRemind);
					break;
				case ANNOUNCE:
					notifyCode = RecordNotifyDefine.getNotifyCode(type,"SYS");
					IbmCmsAnnounce cmsAnnounce = new IbmCmsAnnounce();
					cmsAnnounce.setCmsNotifyId(cmsNotifyId);
					cmsAnnounce.setNotifyCode(notifyCode);
					cmsAnnounce.setAnnounceChannel(notifyChannel);
					cmsAnnounce.setIsCancel(false);
					cmsAnnounce.setCancelTimeLong(NumberTool.getLong(cancelTimeLong));
					cmsAnnounce.setCreateTime(date);
					cmsAnnounce.setCreateTimeLong(System.currentTimeMillis());
					cmsAnnounce.setUpdateTime(date);
					cmsAnnounce.setUpdateTimeLong(System.currentTimeMillis());
					cmsAnnounce.setState(notifyState);
					new IbmCmsAnnounceService().save(cmsAnnounce);
					break;
				case MESSAGE:
					notifyCode = RecordNotifyDefine.getNotifyCode(type,"SYS");
					IbmCmsUserNotify userNotify = new IbmCmsUserNotify();
					userNotify.setCmsNotifyId(cmsNotifyId);
					userNotify.setNotifyCode(notifyCode);
					userNotify.setUserId(userId);
					userNotify.setUserName(userUserName);
					userNotify.setIsRead(false);
					userNotify.setCreateTime(date);
					userNotify.setCreateTimeLong(System.currentTimeMillis());
					userNotify.setUpdateTime(date);
					userNotify.setUpdateTimeLong(System.currentTimeMillis());
					userNotify.setState(IbmStateEnum.OPEN.name());
					new IbmCmsUserNotifyService().save(userNotify);
					break;
				default:
					throw new Exception("错误的消息类型：" + notifyType);
			}


			bean.success();
		} catch (Exception e) {
			log.error("发布公告出错！", e);
			throw e;
		}

		return bean.toJsonString();
	}


}
