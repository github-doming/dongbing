package com.ibs.plan.connector.pc.cms;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.plan.module.cloud.ibsp_cms_notify.service.IbspCmsNotifyService;
import com.ibs.plan.module.cloud.ibsp_cms_user_notify.service.IbspCmsUserNotifyService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 阅读消息
 * @Author: wwj
 * @Date: 2020/4/2 14:02
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/cms/notify/read", method = HttpConfig.Method.GET)
public class NotifyMessageReadAction extends CommCoreAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		// 消息类型
		String notifyType = StringTool.getString(dataMap.get("notifyType"), "").toUpperCase();
		String cmsNotifyId = StringTool.getString(dataMap.get("cmsNotifyId"), "");
		String isAll = StringTool.getString(dataMap.get("isAll"), "");
		if (StringTool.isEmpty(notifyType)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		Date date = new Date();
		try {
			IbsTypeEnum type = IbsTypeEnum.valueOf(notifyType);
			Map<String, Object> data = new HashMap<>(1);
			IbspCmsUserNotifyService cmsUserNotifyService = new IbspCmsUserNotifyService();
			IbspCmsNotifyService cmsNotifyService = new IbspCmsNotifyService();
			Map<String, Object> message = new HashMap<>(9);
			switch (type) {
				case ANNOUNCE:
					message = cmsNotifyService.announceInfo(cmsNotifyId);
					break;
				case MESSAGE:
					// 标记某一条消息为已读
					if (StringTool.notEmpty(cmsNotifyId)) {
						cmsUserNotifyService.updateReadState(cmsNotifyId, date);
						message = cmsNotifyService.messageInfo(cmsNotifyId);

					}else if(Boolean.parseBoolean(isAll)){
						// 标记所有消息为已读
						cmsUserNotifyService.updateReadAll(appUserId, date);
					}
					break;
				default:
					throw new Exception("消息类型错误：" + notifyType);
			}

			data.put("message", message);
			bean.success(data);
		} catch (Exception e) {
			log.error("打开消息失败！", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean.toJsonString();
	}
}
