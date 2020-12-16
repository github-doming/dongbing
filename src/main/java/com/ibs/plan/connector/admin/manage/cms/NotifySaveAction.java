package com.ibs.plan.connector.admin.manage.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_cms_announce.entity.IbspCmsAnnounce;
import com.ibs.plan.module.cloud.ibsp_cms_announce.service.IbspCmsAnnounceService;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import com.ibs.plan.module.cloud.ibsp_cms_remind.entity.IbspCmsRemind;
import com.ibs.plan.module.cloud.ibsp_cms_remind.service.IbspCmsRemindService;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.entity.IbspCmsUserNotify;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.service.IbspCmsUserNotifyService;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 创建消息
 * @Author: admin1
 * @Date: 2020/4/1 11:25
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/cms/notify/save", method = HttpConfig.Method.POST)
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
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}

		IbsTypeEnum type = IbsTypeEnum.valueOf(notifyType);
		Date date = new Date();
		try {

			String cmsNotifyId = RecordNotifyDefine.createNotify(new IbspCmsNotifyService(), notifyType, notifyTitle,
					notifyContent, notifyLevel, notifySite, appUser.getNickname(), date);
			String notifyCode, userId = "";
			if (StringTool.notEmpty(userUserName)) {
				userId = new IbspUserService().findUserIdByName(userUserName);
				if (StringTool.isEmpty(userId)) {
					bean.putEnum(CodeEnum.IBS_403_USER);
					bean.putSysEnum(CodeEnum.CODE_403);
					return bean;
				}
			}

			switch (type) {
				case REMIND:
					notifyCode = RecordNotifyDefine.getNotifyCode(type, notifyCategory);
					IbspCmsRemind cmsRemind = new IbspCmsRemind();
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
					new IbspCmsRemindService().save(cmsRemind);
					break;
				case ANNOUNCE:
					notifyCode = RecordNotifyDefine.getNotifyCode(type, "SYS");
					IbspCmsAnnounce cmsAnnounce = new IbspCmsAnnounce();
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
					new IbspCmsAnnounceService().save(cmsAnnounce);
					break;
				case MESSAGE:
					notifyCode = RecordNotifyDefine.getNotifyCode(type, "SYS");
					IbspCmsUserNotify userNotify = new IbspCmsUserNotify();
					userNotify.setCmsNotifyId(cmsNotifyId);
					userNotify.setNotifyCode(notifyCode);
					userNotify.setUserId(userId);
					userNotify.setUserName(userUserName);
					userNotify.setIsRead(false);
					userNotify.setCreateTime(date);
					userNotify.setCreateTimeLong(System.currentTimeMillis());
					userNotify.setUpdateTime(date);
					userNotify.setUpdateTimeLong(System.currentTimeMillis());
					userNotify.setState(IbsStateEnum.OPEN.name());
					new IbspCmsUserNotifyService().save(userNotify);
					break;
				default:
					throw new Exception("错误的消息类型：" + notifyType);
			}


			bean.success();
		} catch (Exception e) {
			log.error("发布公告出错！", e);
			throw e;
		}

		return bean;
	}


}
