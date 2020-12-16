package com.ibm.follow.connector.pc.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_cms_notify.service.IbmCmsNotifyService;
import com.ibm.follow.servlet.cloud.ibm_cms_remind.service.IbmCmsRemindService;
import com.ibm.follow.servlet.cloud.ibm_cms_user_notify.service.IbmCmsUserNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 删除用户消息
 * @Author: wwj
 * @Date: 2020/4/2 14:26
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/cms/notify/del")
public class NotifyMessageDelAction extends CommCoreAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String notifyType = StringTool.getString(dataMap.get("notifyType"), "").toUpperCase();
		String cmsNotifyId = StringTool.getString(dataMap.get("cmsNotifyId"), "");
		String isAll = StringTool.getString(dataMap.get("isAll"), "");
		Date date = new Date();
		try {

			if (IbmTypeEnum.REMIND.equal(notifyType)) {
				new IbmCmsRemindService().updateRemind(cmsNotifyId, date);
			}
			IbmCmsNotifyService cmsNotifyService = new IbmCmsNotifyService();
			// 删除一条消息
			if (StringTool.notEmpty(cmsNotifyId)) {
				cmsNotifyService.delUserMsg(cmsNotifyId, date);
				new IbmCmsUserNotifyService().delUserMsg(cmsNotifyId, date);
			} else if(Boolean.parseBoolean(isAll)){
				// 清空所有消息
				cmsNotifyService.delAllMessage(appUserId, date);
			}
			bean.success();
		} catch (Exception e) {
			log.error("删除消息出错！", e);
			bean.error(e.getMessage());
		}
		return bean.toJsonString();
	}
}
