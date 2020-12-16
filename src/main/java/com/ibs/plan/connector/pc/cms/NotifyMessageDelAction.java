package com.ibs.plan.connector.pc.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import com.ibs.plan.module.cloud.ibsp_cms_remind.service.IbspCmsRemindService;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.service.IbspCmsUserNotifyService;
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
@ActionMapping(value = "/ibs/pc/cms/notify/del")
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

			if (IbsTypeEnum.REMIND.equal(notifyType)) {
				new IbspCmsRemindService().updateRemind(cmsNotifyId, date);
			}
			IbspCmsNotifyService cmsNotifyService = new IbspCmsNotifyService();
			// 删除一条消息
			if (StringTool.notEmpty(cmsNotifyId)) {
				cmsNotifyService.delUserMsg(cmsNotifyId, date);
				new IbspCmsUserNotifyService().delUserMsg(cmsNotifyId, date);
			} else if(Boolean.parseBoolean(isAll)){
				// 清空所有消息
				cmsNotifyService.delAllMessage(appUserId, date);
			}
			bean.success();
		} catch (Exception e) {
			log.error("删除消息出错！", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean.toJsonString();
	}
}
